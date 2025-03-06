package oluni.official.minecraft.autoTorch.listener;

import oluni.official.minecraft.autoTorch.AutoTorch;
import oluni.official.minecraft.autoTorch.config.ConfigManager;
import oluni.official.minecraft.autoTorch.utils.MessageUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;


public class TorchMechanic implements CommandExecutor, TabCompleter, Listener {
    private final AutoTorch plugin;
    public static final Set<UUID> torch = new HashSet<>();

    public TorchMechanic(AutoTorch plugin) {
        this.plugin = plugin;
    }

    // Command

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender instanceof Player p) {
            if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
                if (p.hasPermission("torch.toggle")) {
                    if (torch.contains(p.getUniqueId())) {
                        torch.remove(p.getUniqueId());
                        MessageUtils.sendMessage(p, ConfigManager.gCfg().getString("messages.turn-off"), null);
                    } else {
                        torch.add(p.getUniqueId());
                        MessageUtils.sendMessage(p, ConfigManager.gCfg().getString("messages.turn-on"), null);
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            if (torch.contains(p.getUniqueId())) {
                                torch.remove(p.getUniqueId());
                                MessageUtils.sendMessage(p, ConfigManager.gCfg().getString("messages.auto-disable"), null);
                            }
                        }, ConfigManager.getTime() * 20L);
                    }
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (p.hasPermission("torch.reload")) {
                    ConfigManager.reloadConfig(plugin);
                    MessageUtils.sendMessage(p, "&#86DE7C[✔ | AutoTorch] → Config reloaded!", null);
                }
            }
        }
        return false;
    }

    // TabComplete

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("toggle", "reload"), new ArrayList<>());
        }
        return new ArrayList<>();
    }

    // Listener

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (!torch.contains(p.getUniqueId())) return;

        if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        Location loc = p.getLocation();
        for (int x = -2; x <= 2; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -2; z <= 2; z++) {
                    Block block = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);

                    if (block.getLightLevel() <= ConfigManager.getLightLevel() && canPlaceTorch(block)) {
                        placeTorch(p, block);
                        return;
                    }
                }
            }
        }
    }

    private boolean canPlaceTorch(Block b) {
        return b.getType() == Material.AIR && b.getRelative(0, -1, 0).getType().isSolid();
    }

    public void placeTorch(Player p, Block b) {
        if (p.getInventory().contains(Material.TORCH)) {
            b.setType(Material.TORCH);
            ItemStack t = new ItemStack(Material.TORCH, 1);
            p.getInventory().removeItem(t);
        }
    }
}
