package oluni.official.minecraft.autoTorch;

import oluni.official.minecraft.autoTorch.config.ConfigManager;
import oluni.official.minecraft.autoTorch.listener.TorchMechanic;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class AutoTorch extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        ConfigManager.setupConfig(this);
        Bukkit.getPluginManager().registerEvents(new TorchMechanic(this), this);
        Objects.requireNonNull(getCommand("autotorch")).setExecutor(new TorchMechanic(this));
        Objects.requireNonNull(getCommand("autotorch")).setTabCompleter(new TorchMechanic(this));
    }
}
