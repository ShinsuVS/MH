package me.shinsu.mH.utils.resourcePack;

import org.bukkit.plugin.java.JavaPlugin;
import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;
import java.security.MessageDigest;

public class ResourcePackManager {
    private final JavaPlugin plugin;
    private String resourcePackUrl;
    private byte[] resourcePackHash;
    private File resourcePackFile;

    public ResourcePackManager(JavaPlugin plugin, String resourcePackUrl) {
        this.plugin = plugin;
        this.resourcePackUrl = resourcePackUrl;
        this.resourcePackFile = new File(plugin.getDataFolder(), "downloaded_resourcepack.zip");
    }

    public void initialize() {
        // Создаем папку плагина, если ее нет
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        try {
            // Скачиваем ресурспак
            downloadResourcePack();

            // Вычисляем хеш
            resourcePackHash = calculateSHA1(resourcePackFile);
            plugin.getLogger().info("Ресурспак успешно загружен. SHA-1: " + bytesToHex(resourcePackHash));

        } catch (IOException e) {
            plugin.getLogger().severe("Не удалось скачать ресурспак: " + e.getMessage());
        } catch (Exception e) {
            plugin.getLogger().severe("Ошибка при вычислении хеша: " + e.getMessage());
        }
    }

    private void downloadResourcePack() throws IOException {
        plugin.getLogger().info("Загрузка ресурспака...");

        HttpURLConnection connection = (HttpURLConnection) new URL(resourcePackUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(30000);

        try (InputStream in = connection.getInputStream();
             FileOutputStream out = new FileOutputStream(resourcePackFile)) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        connection.disconnect();
    }

    private byte[] calculateSHA1(File file) throws Exception {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                sha1.update(buffer, 0, bytesRead);
            }
            return sha1.digest();
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public byte[] getResourcePackHash() {
        return resourcePackHash;
    }

    public String getResourcePackUrl() {
        return resourcePackUrl;
    }
}
