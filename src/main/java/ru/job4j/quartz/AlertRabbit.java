package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.service.*;

import java.io.InputStream;
import java.sql.*;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) throws ClassNotFoundException {

        Settings settings = new Settings();
        ClassLoader loader = Settings.class.getClassLoader();
        InputStream inputStream = loader.getResourceAsStream("rabbit.properties");
        settings.load(inputStream);
        int interval = Integer.parseInt(settings.getValue("rabbit.interval"));

        String url = settings.getValue("url");
        String login = settings.getValue("username");
        String password = settings.getValue("password");
        String driver = settings.getValue("driver-class-name");
        Class.forName(driver);

        try (Connection cn = DriverManager.getConnection(url, login, password)) {
            JobDataMap data = new JobDataMap();
            data.put("connection", cn);

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = newJob(Rabbit.class).usingJobData(data).build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(interval)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (SchedulerException | SQLException | InterruptedException se) {
            se.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
//            System.out.println("Rabbit runs here ...");
            Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO RABBIT (CREATED_DATE) VALUES (?)")) {
                statement.setDate(1, new Date(System.currentTimeMillis()));
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}