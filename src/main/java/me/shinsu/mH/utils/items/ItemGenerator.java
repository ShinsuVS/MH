//V1
//package me.shinsu.mH.utils.items;
//
//import me.shinsu.mH.MH;
//import org.bukkit.Bukkit;
//import org.bukkit.inventory.ItemStack;
//
//import java.io.File;
//import java.util.*;
//
//public class ItemGenerator {
//    private static final Random random = new Random();
//    private static final List<DropEntry> dropEntries = new ArrayList<>();
//    private static final String DROP_FILE_NAME = "drop_chances.yml";
//
//    private static class DropEntry {
//        private final List<ItemStack> items;
//        private final int fixedAmount;
//        private final int minAmount;
//        private final int maxAmount;
//
//        DropEntry(List<ItemStack> items) {
//            this(items, 0, 0, 0);
//        }
//
//        DropEntry(List<ItemStack> items, int fixedAmount) {
//            this(items, fixedAmount, 0, 0);
//        }
//
//        DropEntry(List<ItemStack> items, int minAmount, int maxAmount) {
//            this(items, 0, minAmount, maxAmount);
//        }
//
//        private DropEntry(List<ItemStack> items, int fixedAmount, int minAmount, int maxAmount) {
//            this.items = new ArrayList<>(items);
//            this.fixedAmount = fixedAmount;
//            this.minAmount = minAmount;
//            this.maxAmount = maxAmount;
//        }
//
//        List<ItemStack> generateDrop() {
//            List<ItemStack> result = new ArrayList<>();
//            int amount = determineAmount();
//
//            for (ItemStack item : items) {
//                ItemStack clone = item.clone();
//                if (amount > 0) {
//                    clone.setAmount(amount);
//                }
//                result.add(clone);
//            }
//            return result;
//        }
//
//        private int determineAmount() {
//            if (fixedAmount > 0) return fixedAmount;
//            if (minAmount > 0 && maxAmount >= minAmount) {
//                return random.nextInt(minAmount, maxAmount + 1);
//            }
//            return 0;
//        }
//        @Override
//        public String toString() {
//            StringBuilder sb = new StringBuilder();
//            sb.append("DropEntry{");
//
//            // Добавляем информацию о предметах
//            sb.append("items=[");
//            for (int i = 0; i < items.size(); i++) {
//                ItemStack item = items.get(i);
//                if (i > 0) sb.append(", ");
//
//                sb.append("{type=").append(item.getType());
//                sb.append(", amount=").append(item.getAmount());
//
//                // Добавляем информацию о метаданных, если они есть
//                if (item.hasItemMeta()) {
//                    sb.append(", meta={");
//                    if (item.getItemMeta() instanceof org.bukkit.inventory.meta.BookMeta) {
//                        org.bukkit.inventory.meta.BookMeta meta = (org.bukkit.inventory.meta.BookMeta) item.getItemMeta();
//                        sb.append("bookMeta, title=").append(meta.getTitle());
//                        sb.append(", author=").append(meta.getAuthor());
//                        sb.append(", pages=").append(meta.getPageCount());
//                    } else if (item.getItemMeta() instanceof org.bukkit.inventory.meta.PotionMeta) {
//                        org.bukkit.inventory.meta.PotionMeta meta = (org.bukkit.inventory.meta.PotionMeta) item.getItemMeta();
//                        sb.append("potionMeta, effects=[");
//                        for (org.bukkit.potion.PotionEffect effect : meta.getCustomEffects()) {
//                            sb.append(effect.getType().getName())
//                                    .append("(duration=").append(effect.getDuration())
//                                    .append(", amplifier=").append(effect.getAmplifier())
//                                    .append("), ");
//                        }
//                        if (!meta.getCustomEffects().isEmpty()) {
//                            sb.setLength(sb.length() - 2); // Удаляем лишнюю запятую и пробел
//                        }
//                        sb.append("]");
//                    } else {
//                        sb.append("itemMeta, displayName=").append(item.getItemMeta().getDisplayName());
//                        sb.append(", lore=").append(item.getItemMeta().getLore());
//                    }
//                    sb.append("}");
//                }
//                sb.append("}");
//            }
//            sb.append("]");
//
//            // Добавляем информацию о количествах
//            if (fixedAmount > 0) {
//                sb.append(", fixedAmount=").append(fixedAmount);
//            } else if (minAmount > 0 && maxAmount >= minAmount) {
//                sb.append(", minAmount=").append(minAmount);
//                sb.append(", maxAmount=").append(maxAmount);
//            }
//
//            sb.append("}");
//            return sb.toString();
//        }
//
//    }
//
//    public static void addItem(ItemStack item, int amount, int chance) {
//        addItems(Collections.singletonList(item), amount, chance);
//    }
//
//    public static void addItem(ItemStack item, int minAmount, int maxAmount, int chance) {
//        addItems(Collections.singletonList(item), minAmount, maxAmount, chance);
//    }
//
//    public static void addItems(List<ItemStack> items, int chance) {
//        for (int i = 0; i < chance; i++) {
//            dropEntries.add(new DropEntry(items));
//        }
//    }
//
//    public static void addItems(List<ItemStack> items, int amount, int chance) {
//        for (int i = 0; i < chance; i++) {
//            dropEntries.add(new DropEntry(items, amount));
//        }
//    }
//
//    public static void addItems(List<ItemStack> items, int minAmount, int maxAmount, int chance) {
//        for (int i = 0; i < chance; i++) {
//            dropEntries.add(new DropEntry(items, minAmount, maxAmount));
//        }
//    }
//
//    public static List<ItemStack> getRandomDrop() {
//        if (dropEntries.isEmpty()) {
//            return null;
//        }
//
//        int index = random.nextInt(dropEntries.size());
//        Bukkit.getOnlinePlayers().forEach(player -> {
//            player.sendMessage(dropEntries.get(index).toString());
//        });
//        return dropEntries.get(index).generateDrop();
//    }
//
//    public static boolean isDropFileExists(MH plugin) {
//        if (plugin == null) {
//            Bukkit.getLogger().warning("[ItemGenerator] Plugin is null! Cannot check file existence.");
//            return false;
//        }
//        return new File(plugin.getDataFolder(), "drop_chances.yml").exists();
//    }
//
//    public static void clear() {
//        dropEntries.clear();
//    }
//}
//V2

//package me.shinsu.mH.utils.items;
//
//import me.shinsu.mH.MH;
//import org.bukkit.Bukkit;
//import org.bukkit.Material;
//import org.bukkit.NamespacedKey;
//import org.bukkit.configuration.ConfigurationSection;
//import org.bukkit.configuration.file.YamlConfiguration;
//import org.bukkit.enchantments.Enchantment;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.BookMeta;
//import org.bukkit.inventory.meta.ItemMeta;
//import org.bukkit.inventory.meta.PotionMeta;
//import org.bukkit.inventory.meta.components.CustomModelDataComponent;
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.potion.PotionEffectType;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.*;
//
//public class ItemGenerator {
//    private static final Random random = new Random();
//    private static final List<DropEntry> dropEntries = new ArrayList<>();
//    private static int totalTickets = 0;
//    private static final String DROP_FILE_NAME = "drop_chances.yml";
//
//    private static class DropEntry {
//        private final List<ItemStack> items;
//        private final int fixedAmount;
//        private final int minAmount;
//        private final int maxAmount;
//        private final int tickets;
//
//        DropEntry(List<ItemStack> items, int tickets) {
//            this(items, 0, 0, 0, tickets);
//        }
//
//        DropEntry(List<ItemStack> items, int amount, int tickets) {
//            this(items, amount, 0, 0, tickets);
//        }
//
//        DropEntry(List<ItemStack> items, int minAmount, int maxAmount, int tickets) {
//            this(items, 0, minAmount, maxAmount, tickets);
//        }
//
//        private DropEntry(List<ItemStack> items, int fixedAmount, int minAmount, int maxAmount, int tickets) {
//            this.items = new ArrayList<>(items);
//            this.fixedAmount = fixedAmount;
//            this.minAmount = minAmount;
//            this.maxAmount = maxAmount;
//            this.tickets = tickets;
//        }
//
//        List<ItemStack> generateDrop() {
//            List<ItemStack> result = new ArrayList<>();
//            int amount = determineAmount();
//
//            for (ItemStack item : items) {
//                ItemStack clone = item.clone();
//                if (amount > 0) {
//                    clone.setAmount(amount);
//                }
//                result.add(clone);
//            }
//            return result;
//        }
//
//        private int determineAmount() {
//            if (fixedAmount > 0) return fixedAmount;
//            if (minAmount > 0 && maxAmount >= minAmount) {
//                return random.nextInt(minAmount, maxAmount + 1);
//            }
//            return 0;
//        }
//
//        @Override
//        public String toString() {
//            StringBuilder sb = new StringBuilder();
//            sb.append("DropEntry{tickets=").append(tickets);
//
//            // Добавляем информацию о предметах
//            sb.append(", items=[");
//            for (int i = 0; i < items.size(); i++) {
//                ItemStack item = items.get(i);
//                if (i > 0) sb.append(", ");
//
//                sb.append("{type=").append(item.getType());
//                sb.append(", amount=").append(item.getAmount());
//
//                // Добавляем информацию о метаданных, если они есть
//                if (item.hasItemMeta()) {
//                    sb.append(", meta={");
//                    ItemMeta meta = item.getItemMeta();
//
//                    if (meta instanceof BookMeta) {
//                        BookMeta bookMeta = (BookMeta) meta;
//                        sb.append("bookMeta, title=").append(bookMeta.getTitle());
//                        sb.append(", author=").append(bookMeta.getAuthor());
//                        sb.append(", pages=").append(bookMeta.getPageCount());
//                    } else if (meta instanceof PotionMeta) {
//                        PotionMeta potionMeta = (PotionMeta) meta;
//                        sb.append("potionMeta, effects=[");
//                        for (PotionEffect effect : potionMeta.getCustomEffects()) {
//                            sb.append(effect.getType().getName())
//                                    .append("(duration=").append(effect.getDuration())
//                                    .append(", amplifier=").append(effect.getAmplifier())
//                                    .append("), ");
//                        }
//                        if (!potionMeta.getCustomEffects().isEmpty()) {
//                            sb.setLength(sb.length() - 2);
//                        }
//                        sb.append("]");
//                    } else {
//                        sb.append("itemMeta, displayName=").append(meta.getDisplayName());
//                        sb.append(", lore=").append(meta.getLore());
//                        if (meta.hasEnchants()) {
//                            sb.append(", enchants=").append(meta.getEnchants());
//                        }
//                        if (meta.hasCustomModelData()) {
//                            sb.append(", customModelData=").append(meta.getCustomModelData());
//                        }
//                    }
//                    sb.append("}");
//                }
//                sb.append("}");
//            }
//            sb.append("]");
//
//            // Добавляем информацию о количествах
//            if (fixedAmount > 0) {
//                sb.append(", fixedAmount=").append(fixedAmount);
//            } else if (minAmount > 0 && maxAmount >= minAmount) {
//                sb.append(", minAmount=").append(minAmount);
//                sb.append(", maxAmount=").append(maxAmount);
//            }
//
//            sb.append("}");
//            return sb.toString();
//        }
//    }
//
//    // Методы добавления предметов с билетами вместо шансов
//    public static void addItem(ItemStack item, int amount, int tickets) {
//        addItems(Collections.singletonList(item), amount, tickets);
//    }
//
//    public static void addItem(ItemStack item, int minAmount, int maxAmount, int tickets) {
//        addItems(Collections.singletonList(item), minAmount, maxAmount, tickets);
//    }
//
//    public static void addItems(List<ItemStack> items, int tickets) {
//        dropEntries.add(new DropEntry(items, tickets));
//        totalTickets += tickets;
//    }
//
//    public static void addItems(List<ItemStack> items, int amount, int tickets) {
//        dropEntries.add(new DropEntry(items, amount, tickets));
//        totalTickets += tickets;
//    }
//
//    public static void addItems(List<ItemStack> items, int minAmount, int maxAmount, int tickets) {
//        dropEntries.add(new DropEntry(items, minAmount, maxAmount, tickets));
//        totalTickets += tickets;
//    }
//
//    public static List<ItemStack> getRandomDrop() {
//        if (dropEntries.isEmpty()) {
//            return null;
//        }
//
//        // Взвешенный случайный выбор на основе билетов
//        int randomValue = random.nextInt(totalTickets);
//        int cumulativeWeight = 0;
//
//        for (DropEntry entry : dropEntries) {
//            cumulativeWeight += entry.tickets;
//            if (randomValue < cumulativeWeight) {
//                Bukkit.getOnlinePlayers().forEach(player -> {
//                    player.sendMessage(entry.toString());
//                });
//                return entry.generateDrop();
//            }
//        }
//
//        // Fallback на последний элемент
//        DropEntry last = dropEntries.get(dropEntries.size() - 1);
//        Bukkit.getOnlinePlayers().forEach(player -> {
//            player.sendMessage(last.toString());
//        });
//        return last.generateDrop();
//    }
//
//    // 1. Метод для записи в файл
//    public static void saveToFile(MH plugin) throws IOException {
//        File file = new File(plugin.getDataFolder(), DROP_FILE_NAME);
//        YamlConfiguration config = new YamlConfiguration();
//
//        // Рассчитываем общее количество билетов
//        config.set("total_tickets", totalTickets);
//
//        // Сериализуем одиночные предметы
//        ConfigurationSection singleSection = config.createSection("single");
//        int singleCount = 0;
//
//        // Сериализуем группы
//        ConfigurationSection groupsSection = config.createSection("groups");
//        int groupCount = 0;
//
//        for (DropEntry entry : dropEntries) {
//            if (entry.items.size() == 1) {
//                singleCount++;
//                ConfigurationSection itemSection = singleSection.createSection("item_" + singleCount);
//                serializeItemStack(entry.items.get(0), itemSection, entry);
//            } else {
//                groupCount++;
//                ConfigurationSection groupSection = groupsSection.createSection("group_" + groupCount);
//                groupSection.set("tickets", entry.tickets);
//
//                if (entry.fixedAmount > 0) {
//                    groupSection.set("amount", entry.fixedAmount);
//                } else if (entry.minAmount > 0 && entry.maxAmount >= entry.minAmount) {
//                    groupSection.set("amount", entry.minAmount + "-" + entry.maxAmount);
//                }
//
//                ConfigurationSection itemsSection = groupSection.createSection("items");
//                for (int i = 0; i < entry.items.size(); i++) {
//                    ConfigurationSection itemSection = itemsSection.createSection("item_" + (i + 1));
//                    serializeItemStack(entry.items.get(i), itemSection, null);
//                }
//            }
//        }
//
//        config.save(file);
//    }
//
//    private static void serializeItemStack(ItemStack item, ConfigurationSection section, DropEntry entry) {
//        section.set("material", item.getType().name());
//
//        // Обработка количества
//        if (entry != null) {
//            if (entry.fixedAmount > 0) {
//                section.set("amount", String.valueOf(entry.fixedAmount));
//            } else if (entry.minAmount > 0 && entry.maxAmount >= entry.minAmount) {
//                section.set("amount", entry.minAmount + "-" + entry.maxAmount);
//            } else {
//                section.set("amount", String.valueOf(item.getAmount()));
//            }
//            section.set("tickets", entry.tickets);
//        } else {
//            section.set("amount", item.getAmount());
//        }
//
//        if (!item.hasItemMeta()) return;
//
//        ItemMeta meta = item.getItemMeta();
//
//        if (meta.hasDisplayName()) {
//            section.set("display_name", meta.getDisplayName());
//        }
//
//        if (meta.hasLore()) {
//            section.set("lore", meta.getLore());
//        }
//
//        if (meta.hasEnchants()) {
//            ConfigurationSection enchantsSection = section.createSection("enchants");
//            for (Map.Entry<Enchantment, Integer> enchant : meta.getEnchants().entrySet()) {
//                enchantsSection.set(enchant.getKey().getKey().getKey(), enchant.getValue());
//            }
//        }
//
//        if (meta instanceof BookMeta) {
//            BookMeta bookMeta = (BookMeta) meta;
//            section.set("title", bookMeta.getTitle());
//            section.set("author", bookMeta.getAuthor());
//            section.set("pages", bookMeta.getPages());
//        }
//
//        if (meta instanceof PotionMeta) {
//            PotionMeta potionMeta = (PotionMeta) meta;
//            List<Map<String, Object>> effects = new ArrayList<>();
//            for (PotionEffect effect : potionMeta.getCustomEffects()) {
//                Map<String, Object> effectData = new HashMap<>();
//                effectData.put("type", effect.getType().getName());
//                effectData.put("amplifier", effect.getAmplifier());
//                effectData.put("duration", effect.getDuration());
//                effects.add(effectData);
//            }
//            section.set("potion_effects", effects);
//        }
//
//        // ПРАВИЛЬНАЯ обработка CustomModelDataComponent для 1.21.4
//        if (meta.hasCustomModelData()) {
//            section.set("custom_model_data", meta.getCustomModelDataComponent().getStrings());
//        }
//    }
//    // 2. Метод для проверки данных из файла
//    public static boolean validateFile(MH plugin) {
//        File file = new File(plugin.getDataFolder(), DROP_FILE_NAME);
//        if (!file.exists()) return false;
//
//        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
//
//        // Проверка обязательных полей
//        if (!config.contains("total_tickets") || !config.isInt("total_tickets")) {
//            return false;
//        }
//
//        // Проверка структуры single
//        if (config.contains("single")) {
//            ConfigurationSection singleSection = config.getConfigurationSection("single");
//            for (String key : singleSection.getKeys(false)) {
//                if (!key.startsWith("item_")) return false;
//
//                ConfigurationSection itemSection = singleSection.getConfigurationSection(key);
//                if (!itemSection.contains("material") ||
//                        !itemSection.contains("amount") ||
//                        !itemSection.contains("tickets")) {
//                    return false;
//                }
//            }
//        }
//
//        // Проверка структуры groups
//        if (config.contains("groups")) {
//            ConfigurationSection groupsSection = config.getConfigurationSection("groups");
//            for (String key : groupsSection.getKeys(false)) {
//                if (!key.startsWith("group_")) return false;
//
//                ConfigurationSection groupSection = groupsSection.getConfigurationSection(key);
//                if (!groupSection.contains("tickets") ||
//                        !groupSection.contains("amount") ||
//                        !groupSection.contains("items")) {
//                    return false;
//                }
//
//                ConfigurationSection itemsSection = groupSection.getConfigurationSection("items");
//                for (String itemKey : itemsSection.getKeys(false)) {
//                    ConfigurationSection itemSection = itemsSection.getConfigurationSection(itemKey);
//                    if (!itemSection.contains("material") || !itemSection.contains("amount")) {
//                        return false;
//                    }
//                }
//            }
//        }
//
//        return true;
//    }
//
//    // 3. Метод для загрузки данных из файла
//    public static void loadFromFile(MH plugin) throws IOException {
//        File configFile = new File(plugin.getDataFolder(), "config.yml");
//        YamlConfiguration mainConfig = YamlConfiguration.loadConfiguration(configFile);
//        boolean reWriteDrops = mainConfig.getBoolean("reWriteDrops", false);
//
//        if (reWriteDrops) {
//            dropEntries.clear();
//            totalTickets = 0;
//        }
//
//        File dropFile = new File(plugin.getDataFolder(), DROP_FILE_NAME);
//        if (!dropFile.exists()) return;
//
//        YamlConfiguration config = YamlConfiguration.loadConfiguration(dropFile);
//        totalTickets = config.getInt("total_tickets", 0);
//
//        // Загрузка одиночных предметов
//        if (config.contains("single")) {
//            ConfigurationSection singleSection = config.getConfigurationSection("single");
//            for (String key : singleSection.getKeys(false)) {
//                ConfigurationSection itemSection = singleSection.getConfigurationSection(key);
//                ItemStack item = deserializeItemStack(itemSection);
//
//                int tickets = itemSection.getInt("tickets");
//                String amountStr = itemSection.getString("amount");
//
//                if (amountStr.contains("-")) {
//                    String[] parts = amountStr.split("-");
//                    int min = Integer.parseInt(parts[0]);
//                    int max = Integer.parseInt(parts[1]);
//                    addItem(item, min, max, tickets);
//                } else {
//                    int amount = Integer.parseInt(amountStr);
//                    addItem(item, amount, tickets);
//                }
//            }
//        }
//
//        // Загрузка групп
//        if (config.contains("groups")) {
//            ConfigurationSection groupsSection = config.getConfigurationSection("groups");
//            for (String key : groupsSection.getKeys(false)) {
//                ConfigurationSection groupSection = groupsSection.getConfigurationSection(key);
//                int tickets = groupSection.getInt("tickets");
//                String amountStr = groupSection.getString("amount");
//
//                List<ItemStack> groupItems = new ArrayList<>();
//                ConfigurationSection itemsSection = groupSection.getConfigurationSection("items");
//
//                for (String itemKey : itemsSection.getKeys(false)) {
//                    ConfigurationSection itemSection = itemsSection.getConfigurationSection(itemKey);
//                    groupItems.add(deserializeItemStack(itemSection));
//                }
//
//                if (amountStr.contains("-")) {
//                    String[] parts = amountStr.split("-");
//                    int min = Integer.parseInt(parts[0]);
//                    int max = Integer.parseInt(parts[1]);
//                    addItems(groupItems, min, max, tickets);
//                } else {
//                    int amount = Integer.parseInt(amountStr);
//                    addItems(groupItems, amount, tickets);
//                }
//            }
//        }
//    }
//
//    private static ItemStack deserializeItemStack(ConfigurationSection section) {
//        Material material = Material.valueOf(section.getString("material"));
//        int amount = section.getInt("amount", 1);
//        ItemStack item = new ItemStack(material, amount);
//
//        ItemMeta meta = item.getItemMeta();
//        if (meta == null) return item;
//
//        if (section.contains("display_name")) {
//            meta.setDisplayName(section.getString("display_name"));
//        }
//
//        if (section.contains("lore")) {
//            meta.setLore(section.getStringList("lore"));
//        }
//
//        if (section.contains("enchants")) {
//            ConfigurationSection enchantsSection = section.getConfigurationSection("enchants");
//            for (String enchantKey : enchantsSection.getKeys(false)) {
//                Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(enchantKey));
//                if (enchant != null) {
//                    meta.addEnchant(enchant, enchantsSection.getInt(enchantKey), true);
//                }
//            }
//        }
//
//        if (section.contains("title") && meta instanceof BookMeta) {
//            BookMeta bookMeta = (BookMeta) meta;
//            bookMeta.setTitle(section.getString("title"));
//            bookMeta.setAuthor(section.getString("author"));
//            if (section.contains("pages")) {
//                bookMeta.setPages(section.getStringList("pages"));
//            }
//        }
//
//        if (section.contains("potion_effects") && meta instanceof PotionMeta) {
//            PotionMeta potionMeta = (PotionMeta) meta;
//            for (Map<?, ?> effectData : section.getMapList("potion_effects")) {
//                PotionEffectType type = PotionEffectType.getByName((String) effectData.get("type"));
//                if (type != null) {
//                    int amplifier = (int) effectData.get("amplifier");
//                    int duration = (int) effectData.get("duration");
//                    potionMeta.addCustomEffect(new PotionEffect(type, duration, amplifier), true);
//                }
//            }
//        }
//
//        // ПРАВИЛЬНАЯ обработка CustomModelDataComponent для 1.21.4
//        if (section.contains("custom_model_data")) {
//            String value = section.getString("custom_model_data");
//            CustomModelDataComponent component = meta.getCustomModelDataComponent();
//            component.setStrings(Collections.singletonList(value));
//            meta.setCustomModelDataComponent(component);
//        }
//
//        item.setItemMeta(meta);
//        return item;
//    }
//    public static boolean isDropFileExists(MH plugin) {
//        if (plugin == null) {
//            Bukkit.getLogger().warning("[ItemGenerator] Plugin is null! Cannot check file existence.");
//            return false;
//        }
//        return new File(plugin.getDataFolder(), DROP_FILE_NAME).exists();
//    }
//
//    public static void clear() {
//        dropEntries.clear();
//        totalTickets = 0;
//    }
//}

//V3
package me.shinsu.mH.utils.items;

import me.shinsu.mH.MH;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ItemGenerator {
    private static final Random random = new Random();
    private static final List<DropEntry> dropEntries = new ArrayList<>();
    private static int totalTickets = 0;
    private static final String DROP_FILE_NAME = "drop_chances.yml";

    private static class DropEntry {
        private final List<ItemStack> items;
        private final int fixedAmount;
        private final int minAmount;
        private final int maxAmount;
        private final int tickets;

        DropEntry(List<ItemStack> items, int tickets) {
            this(items, 0, 0, 0, tickets);
        }

        DropEntry(List<ItemStack> items, int amount, int tickets) {
            this(items, amount, 0, 0, tickets);
        }

        DropEntry(List<ItemStack> items, int minAmount, int maxAmount, int tickets) {
            this(items, 0, minAmount, maxAmount, tickets);
        }

        private DropEntry(List<ItemStack> items, int fixedAmount, int minAmount, int maxAmount, int tickets) {
            this.items = new ArrayList<>(items);
            this.fixedAmount = fixedAmount;
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
            this.tickets = tickets;
        }

        List<ItemStack> generateDrop() {
            List<ItemStack> result = new ArrayList<>();
            int amount = determineAmount();

            for (ItemStack item : items) {
                ItemStack clone = item.clone();
                if (amount > 0) {
                    clone.setAmount(amount);
                }
                result.add(clone);
            }
            return result;
        }

        private int determineAmount() {
            if (fixedAmount > 0) return fixedAmount;
            if (minAmount > 0 && maxAmount >= minAmount) {
                return random.nextInt(minAmount, maxAmount + 1);
            }
            return 0;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("DropEntry{tickets=").append(tickets);

            // Добавляем информацию о предметах
            sb.append(", items=[");
            for (int i = 0; i < items.size(); i++) {
                ItemStack item = items.get(i);
                if (i > 0) sb.append(", ");

                sb.append("{type=").append(item.getType());
                sb.append(", amount=").append(item.getAmount());

                // Добавляем информацию о метаданных, если они есть
                if (item.hasItemMeta()) {
                    sb.append(", meta={");
                    ItemMeta meta = item.getItemMeta();

                    if (meta instanceof BookMeta) {
                        BookMeta bookMeta = (BookMeta) meta;
                        sb.append("BookMeta, title=").append(bookMeta.getTitle());
                        sb.append(", author=").append(bookMeta.getAuthor());
                        sb.append(", pages=").append(bookMeta.getPageCount());
                    } else if (meta instanceof PotionMeta) {
                        PotionMeta potionMeta = (PotionMeta) meta;
                        sb.append("PotionMeta, effects=[");
                        for (PotionEffect effect : potionMeta.getCustomEffects()) {
                            sb.append(effect.getType().getName())
                                    .append("(duration=").append(effect.getDuration())
                                    .append(", amplifier=").append(effect.getAmplifier())
                                    .append("), ");
                        }
                        if (!potionMeta.getCustomEffects().isEmpty()) {
                            sb.setLength(sb.length() - 2);
                        }
                        sb.append("]");
                    } else {
                        sb.append("ItemMeta, displayName=").append(meta.getDisplayName());
                        sb.append(", lore=").append(meta.getLore());
                        if (meta.hasEnchants()) {
                            sb.append(", enchants={");
                            for (Map.Entry<Enchantment, Integer> enchant : meta.getEnchants().entrySet()) {
                                sb.append(enchant.getKey().getKey())
                                        .append(": ")
                                        .append(enchant.getValue())
                                        .append(", ");
                            }
                            sb.setLength(sb.length() - 2);
                            sb.append("}");
                        }
                    }

                    // Вывод CustomModelDataComponent
                    CustomModelDataComponent customModelData = meta.getCustomModelDataComponent();
                    if (customModelData != null && !customModelData.getStrings().isEmpty()) {
                        sb.append(", customModelData=").append(customModelData.getStrings().get(0));
                    }

                    sb.append("}");
                }
                sb.append("}");
            }
            sb.append("]");

            // Добавляем информацию о количествах
            if (fixedAmount > 0) {
                sb.append(", fixedAmount=").append(fixedAmount);
            } else if (minAmount > 0 && maxAmount >= minAmount) {
                sb.append(", minAmount=").append(minAmount);
                sb.append(", maxAmount=").append(maxAmount);
            }

            sb.append("}");
            return sb.toString();
        }
    }

    // Методы добавления предметов с билетами вместо шансов
    public static void addItem(ItemStack item, int amount, int tickets) {
        addItems(Collections.singletonList(item), amount, tickets);
    }

    public static void addItem(ItemStack item, int minAmount, int maxAmount, int tickets) {
        addItems(Collections.singletonList(item), minAmount, maxAmount, tickets);
    }

    public static void addItems(List<ItemStack> items, int tickets) {
        dropEntries.add(new DropEntry(items, tickets));
        totalTickets += tickets;
    }

    public static void addItems(List<ItemStack> items, int amount, int tickets) {
        dropEntries.add(new DropEntry(items, amount, tickets));
        totalTickets += tickets;
    }

    public static void addItems(List<ItemStack> items, int minAmount, int maxAmount, int tickets) {
        dropEntries.add(new DropEntry(items, minAmount, maxAmount, tickets));
        totalTickets += tickets;
    }

    public static List<ItemStack> getRandomDrop() {
        if (dropEntries.isEmpty()) {
            return null;
        }

        // Взвешенный случайный выбор на основе билетов
        int randomValue = random.nextInt(totalTickets);
        int cumulativeWeight = 0;

        for (DropEntry entry : dropEntries) {
            cumulativeWeight += entry.tickets;
            if (randomValue < cumulativeWeight) {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.sendMessage(entry.toString());
                });
                return entry.generateDrop();
            }
        }

        // Fallback на последний элемент
        DropEntry last = dropEntries.get(dropEntries.size() - 1);
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage(last.toString());
        });
        return last.generateDrop();
    }

    // 1. Метод для записи в файл
    public static void saveToFile(MH plugin) throws IOException {
        File file = new File(plugin.getDataFolder(), DROP_FILE_NAME);
        YamlConfiguration config = new YamlConfiguration();

        // Рассчитываем общее количество билетов
        config.set("total_tickets", totalTickets);

        // Сериализуем одиночные предметы
        ConfigurationSection singleSection = config.createSection("single");
        int singleCount = 0;

        // Сериализуем группы
        ConfigurationSection groupsSection = config.createSection("groups");
        int groupCount = 0;

        for (DropEntry entry : dropEntries) {
            if (entry.items.size() == 1) {
                singleCount++;
                ConfigurationSection itemSection = singleSection.createSection("item_" + singleCount);
                serializeItemStack(entry.items.get(0), itemSection, entry);
            } else {
                groupCount++;
                ConfigurationSection groupSection = groupsSection.createSection("group_" + groupCount);
                groupSection.set("tickets", entry.tickets);

                if (entry.fixedAmount > 0) {
                    groupSection.set("amount", String.valueOf(entry.fixedAmount));
                } else if (entry.minAmount > 0 && entry.maxAmount >= entry.minAmount) {
                    groupSection.set("amount", entry.minAmount + "-" + entry.maxAmount);
                } else {
                    groupSection.set("amount", "1");
                }

                ConfigurationSection itemsSection = groupSection.createSection("items");
                for (int i = 0; i < entry.items.size(); i++) {
                    ConfigurationSection itemSection = itemsSection.createSection("item_" + (i + 1));
                    serializeItemStack(entry.items.get(i), itemSection, null);
                }
            }
        }

        config.save(file);
    }

    private static void serializeItemStack(ItemStack item, ConfigurationSection section, DropEntry entry) {
        section.set("material", item.getType().name());

        // Обработка количества
        if (entry != null) {
            if (entry.fixedAmount > 0) {
                section.set("amount", String.valueOf(entry.fixedAmount));
            } else if (entry.minAmount > 0 && entry.maxAmount >= entry.minAmount) {
                section.set("amount", entry.minAmount + "-" + entry.maxAmount);
            } else {
                section.set("amount", String.valueOf(item.getAmount()));
            }
            section.set("tickets", entry.tickets);
        } else {
            section.set("amount", item.getAmount());
        }

        if (!item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();

        if (meta.hasDisplayName()) {
            section.set("display_name", meta.getDisplayName());
        }

        if (meta.hasLore()) {
            section.set("lore", meta.getLore());
        }

        if (meta.hasEnchants()) {
            ConfigurationSection enchantsSection = section.createSection("enchants");
            for (Map.Entry<Enchantment, Integer> enchant : meta.getEnchants().entrySet()) {
                enchantsSection.set(enchant.getKey().getKey().getKey(), enchant.getValue());
            }
        }

        if (meta instanceof BookMeta) {
            BookMeta bookMeta = (BookMeta) meta;
            section.set("title", bookMeta.getTitle());
            section.set("author", bookMeta.getAuthor());
            section.set("pages", bookMeta.getPages());
        }

        if (meta instanceof PotionMeta) {
            PotionMeta potionMeta = (PotionMeta) meta;
            List<Map<String, Object>> effects = new ArrayList<>();
            for (PotionEffect effect : potionMeta.getCustomEffects()) {
                Map<String, Object> effectData = new HashMap<>();
                effectData.put("type", effect.getType().getName());
                effectData.put("amplifier", effect.getAmplifier());
                effectData.put("duration", effect.getDuration());
                effects.add(effectData);
            }
            section.set("potion_effects", effects);
        }

        // Обработка CustomModelDataComponent
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        if (component != null && !component.getStrings().isEmpty()) {
            section.set("custom_model_data", component.getStrings());
        }
    }

    // 2. Метод для проверки данных из файла
    public static boolean validateFile(MH plugin) {
        File file = new File(plugin.getDataFolder(), DROP_FILE_NAME);
        if (!file.exists()) {
            Bukkit.getLogger().warning("[ItemGenerator] File not exists: " + file.getPath());
            return false;
        }

        try {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            // Проверка обязательных полей
            if (!config.contains("total_tickets") || !config.isInt("total_tickets")) {
                Bukkit.getLogger().warning("[ItemGenerator] Invalid total_tickets");
                return false;
            }

            // Создаем временный список для DropEntry из файла
            List<DropEntry> fileEntries = new ArrayList<>();

            // Загрузка одиночных предметов
            if (config.contains("single")) {
                ConfigurationSection singleSection = config.getConfigurationSection("single");
                for (String key : singleSection.getKeys(false)) {
                    ConfigurationSection itemSection = singleSection.getConfigurationSection(key);
                    ItemStack item = deserializeItemStack(itemSection);

                    int tickets = itemSection.getInt("tickets");
                    String amountStr = itemSection.getString("amount");

                    DropEntry entry;
                    if (amountStr.contains("-")) {
                        String[] parts = amountStr.split("-");
                        int min = Integer.parseInt(parts[0]);
                        int max = Integer.parseInt(parts[1]);
                        entry = new DropEntry(Collections.singletonList(item), min, max, tickets);
                    } else {
                        int amount = Integer.parseInt(amountStr);
                        entry = new DropEntry(Collections.singletonList(item), amount, tickets);
                    }
                    fileEntries.add(entry);
                }
            }

            // Загрузка групп
            if (config.contains("groups")) {
                ConfigurationSection groupsSection = config.getConfigurationSection("groups");
                for (String key : groupsSection.getKeys(false)) {
                    ConfigurationSection groupSection = groupsSection.getConfigurationSection(key);
                    int tickets = groupSection.getInt("tickets");
                    String amountStr = groupSection.getString("amount");

                    List<ItemStack> groupItems = new ArrayList<>();
                    ConfigurationSection itemsSection = groupSection.getConfigurationSection("items");

                    for (String itemKey : itemsSection.getKeys(false)) {
                        ConfigurationSection itemSection = itemsSection.getConfigurationSection(itemKey);
                        groupItems.add(deserializeItemStack(itemSection));
                    }

                    DropEntry entry;
                    if (amountStr.contains("-")) {
                        String[] parts = amountStr.split("-");
                        int min = Integer.parseInt(parts[0]);
                        int max = Integer.parseInt(parts[1]);
                        entry = new DropEntry(groupItems, min, max, tickets);
                    } else {
                        int amount = Integer.parseInt(amountStr);
                        entry = new DropEntry(groupItems, amount, tickets);
                    }
                    fileEntries.add(entry);
                }
            }

            // Сравниваем количество записей
            if (fileEntries.size() != dropEntries.size()) {
                Bukkit.getLogger().warning("[ItemGenerator] Entry count mismatch: " +
                        "File has " + fileEntries.size() +
                        ", Current has " + dropEntries.size());
                return false;
            }

            // Сравниваем каждую запись
            for (int i = 0; i < fileEntries.size(); i++) {
                DropEntry fileEntry = fileEntries.get(i);
                DropEntry currentEntry = dropEntries.get(i);

                if (!fileEntry.toString().equals(currentEntry.toString())) {
                    Bukkit.getLogger().warning("[ItemGenerator] Entry mismatch at index " + i);
                    Bukkit.getLogger().warning("File entry: " + fileEntry.toString());
                    Bukkit.getLogger().warning("Current entry: " + currentEntry.toString());
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            Bukkit.getLogger().warning("[ItemGenerator] Validation error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 3. Метод для загрузки данных из файла
    public static void loadFromFile(MH plugin) throws IOException {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        YamlConfiguration mainConfig = YamlConfiguration.loadConfiguration(configFile);
        boolean reWriteDrops = mainConfig.getBoolean("reWriteDrops", false);

        if (reWriteDrops) {
            dropEntries.clear();
            totalTickets = 0;
        }

        File dropFile = new File(plugin.getDataFolder(), DROP_FILE_NAME);
        if (!dropFile.exists()) return;

        YamlConfiguration config = YamlConfiguration.loadConfiguration(dropFile);
        totalTickets = config.getInt("total_tickets", 0);

        // Загрузка одиночных предметов
        if (config.contains("single")) {
            ConfigurationSection singleSection = config.getConfigurationSection("single");
            for (String key : singleSection.getKeys(false)) {
                ConfigurationSection itemSection = singleSection.getConfigurationSection(key);
                ItemStack item = deserializeItemStack(itemSection);

                int tickets = itemSection.getInt("tickets");
                String amountStr = itemSection.getString("amount");

                if (amountStr.contains("-")) {
                    String[] parts = amountStr.split("-");
                    int min = Integer.parseInt(parts[0]);
                    int max = Integer.parseInt(parts[1]);
                    addItem(item, min, max, tickets);
                } else {
                    int amount = Integer.parseInt(amountStr);
                    addItem(item, amount, tickets);
                }
            }
        }

        // Загрузка групп
        if (config.contains("groups")) {
            ConfigurationSection groupsSection = config.getConfigurationSection("groups");
            for (String key : groupsSection.getKeys(false)) {
                ConfigurationSection groupSection = groupsSection.getConfigurationSection(key);
                int tickets = groupSection.getInt("tickets");
                String amountStr = groupSection.getString("amount");

                List<ItemStack> groupItems = new ArrayList<>();
                ConfigurationSection itemsSection = groupSection.getConfigurationSection("items");

                for (String itemKey : itemsSection.getKeys(false)) {
                    ConfigurationSection itemSection = itemsSection.getConfigurationSection(itemKey);
                    groupItems.add(deserializeItemStack(itemSection));
                }

                if (amountStr.contains("-")) {
                    String[] parts = amountStr.split("-");
                    int min = Integer.parseInt(parts[0]);
                    int max = Integer.parseInt(parts[1]);
                    addItems(groupItems, min, max, tickets);
                } else {
                    int amount = Integer.parseInt(amountStr);
                    addItems(groupItems, amount, tickets);
                }
            }
        }
    }

    private static ItemStack deserializeItemStack(ConfigurationSection section) {
        Material material = Material.valueOf(section.getString("material"));
        int amount = section.getInt("amount", 1);
        ItemStack item = new ItemStack(material, amount);

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        if (section.contains("display_name")) {
            meta.setDisplayName(section.getString("display_name"));
        }

        if (section.contains("lore")) {
            meta.setLore(section.getStringList("lore"));
        }

        if (section.contains("enchants")) {
            ConfigurationSection enchantsSection = section.getConfigurationSection("enchants");
            for (String enchantKey : enchantsSection.getKeys(false)) {
                Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(enchantKey));
                if (enchant != null) {
                    meta.addEnchant(enchant, enchantsSection.getInt(enchantKey), true);
                }
            }
        }

        if (section.contains("title") && meta instanceof BookMeta) {
            BookMeta bookMeta = (BookMeta) meta;
            bookMeta.setTitle(section.getString("title"));
            bookMeta.setAuthor(section.getString("author"));
            if (section.contains("pages")) {
                bookMeta.setPages(section.getStringList("pages"));
            }
        }

        if (section.contains("potion_effects") && meta instanceof PotionMeta) {
            PotionMeta potionMeta = (PotionMeta) meta;
            for (Map<?, ?> effectData : section.getMapList("potion_effects")) {
                PotionEffectType type = PotionEffectType.getByName((String) effectData.get("type"));
                if (type != null) {
                    int amplifier = (int) effectData.get("amplifier");
                    int duration = (int) effectData.get("duration");
                    potionMeta.addCustomEffect(new PotionEffect(type, duration, amplifier), true);
                }
            }
        }

        // Обработка CustomModelDataComponent
        if (section.contains("custom_model_data")) {
            String value = section.getString("custom_model_data");
            CustomModelDataComponent component = meta.getCustomModelDataComponent();
            component.setStrings(Collections.singletonList(value));
            meta.setCustomModelDataComponent(component);
        }

        item.setItemMeta(meta);
        return item;
    }

    public static boolean isDropFileExists(MH plugin) {
        if (plugin == null) {
            Bukkit.getLogger().warning("[ItemGenerator] Plugin is null! Cannot check file existence.");
            return false;
        }
        return new File(plugin.getDataFolder(), DROP_FILE_NAME).exists();
    }

    public static void clear() {
        dropEntries.clear();
        totalTickets = 0;
    }
}