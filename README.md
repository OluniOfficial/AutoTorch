# 🔥 AutoTorch Plugin

A simple **Minecraft Paper Plugin** that allows players to automatically place torches in dark areas while moving.

## 📜 Features
- Automatically places torches when the player's light level is too low.
- Works only if the player has torches in their inventory.
- Togglable via `/autotorch toggle` command.
- Auto mode disables itself after **one minute** to prevent excessive torch placement.
- Supports permission-based access for commands and mechanics.
- Configurable via `config.yml`.

## ⚙️ Configuration (`config.yml`)
### 🔹 Default configuration:
```yaml
light-level: 7
```

### 🔹 Config Options:
light-level: Sets the minimum light level before a torch is placed.

## 📚 Other information
### 🛠 Commands
| Command | Permission | Description |
|----------------|:---------:|----------------:|
| /autotorch toggle | torch.toggle | Switches auto-torch mode. |
| /autotorch reload | torch.reload | It simply reloads the plugin config. |

### 🗂️ Versions

| Plugin Version | Version | Download |
|----------------|:---------:|----------------:|
| 1.1 | 1.21.4 | [Download v1.1](https://github.com/OluniOfficial/AutoTorch/releases/download/v1.1/AutoTorch-1.1.jar) |

### ❗ Bugs report

If you see a bug, you can write about it in the Issue. Or, you can write to me in discord - `oluni_official`. Or, if you have an idea for updating a plugin, or any other plugin, you can also write to me.
