package oluni.official.minecraft.autoTorch.config;

import oluni.official.minecraft.autoTorch.AutoTorch;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private static FileConfiguration cfg;
    public static void setupConfig(AutoTorch torch) {
        ConfigManager.cfg = torch.getConfig();
        torch.saveConfig();
    }
    public static void reloadConfig(AutoTorch torch) {
        torch.reloadConfig();
        ConfigManager.cfg = torch.getConfig();
    }

    public static int getLightLevel() {
        return cfg.getInt("level-light", 7);
    }

    public static FileConfiguration gCfg() {
        return cfg;
    }

    public static int getTime() {
        return cfg.getInt("auto-disable-time", 60);
    }
}
