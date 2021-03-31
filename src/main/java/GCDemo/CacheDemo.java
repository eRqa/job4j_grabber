package GCDemo;

import java.io.*;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class CacheDemo {

    private final Map<String, SoftReference<String>> cache = new HashMap<>();
    private final String workingDirectory;

    public CacheDemo(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public void start() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String in = "";
            while (!in.equals("exit")) {
                System.out.println("Enter the file name or exit");
                in = reader.readLine();
                if (in.equals("exit")) {
                    break;
                }
                System.out.println(get(in));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String put(String key) {
        String result = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(workingDirectory + "/" + key))) {
            StringBuilder fileData = new StringBuilder();
            while (reader.ready()) {
                fileData.append(reader.readLine());
            }
            result = fileData.toString();
            cache.put(key, new SoftReference<>(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String get(String key) {
        String result = "";
        if (cache.containsKey(key)) {
            result = cache.get(key).get();
            if (result == null) {
                System.out.println("File was in cache, but now is aren't");
                result = put(key);
            } else {
                System.out.println("File " + key + " was received from cache");
            }
        } else {
            System.out.println("Cache not contains file " + key + ". Added to cache");
            result = put(key);
        }
        return result;
    }

    public static void main(String[] args) {
        CacheDemo cache = new CacheDemo("C:/Users/p.vlasov/Desktop/tmp");
        cache.start();
    }

}
