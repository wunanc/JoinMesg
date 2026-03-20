# JoinMesg

一款 Minecraft 服务器插件，可自定义玩家加入、退出和首次加入的消息。完全兼容 Folia、Bukkit/Paper 及其衍生服务端！

## 特性

- 自定义首次进入服务器的欢迎消息
- 自定义玩家加入消息
- 自定义玩家退出消息
- 完全支持 Folia、Bukkit/Paper 及其衍生服务端
- 支持颜色代码的可配置消息
- 内置数据统计（bStats）

## 使用教程

### 安装

1. 下载最新版本的 JoinMesg 插件
2. 将 `.jar` 文件放入服务器的 `plugins` 文件夹
3. 重启或重载服务器
4. 将自动生成 `config.yml` 配置文件

### 配置

编辑 `plugins/JoinMesg` 目录下的 `config.yml` 文件以自定义消息：

```yaml
#首次加入服务器的欢迎消息
welcomeFirstJoin:
  enabled: true
  message: "&d新玩家 &e%player% &d欢迎加入服务器！"

#如果不开启以下功能，则不会修改相应消息，而非关闭功能
#玩家加入服务器
join:
  enabled: true
  message: "&a[+]%player%"

#玩家离开服务器
quit:
  enabled: true
  message: "&c[-]%player%"

#兼容模式（如果功能无法正常工作，可尝试切换此选项）
compatible: true
```

### 消息占位符

- `%player%` - 显示玩家名称

### 颜色代码

在消息中使用 Minecraft 颜色代码：
- `&a` - 绿色
- `&c` - 红色
- `&d` - 亮紫色
- `&e` - 黄色
- `&f` - 白色
- 更多……

### 命令

| 命令 | 权限 | 描述 |
|------|------|------|
| `/joinmesg` | `joinmesg.maincmd` | 显示插件帮助 |
| `/joinmesg reload` | `joinmesg.reload` | 重载插件配置 |
| `/jm` | `joinmesg.maincmd` | `/joinmesg` 的别名 |

### 权限

| 权限 | 描述 | 默认 |
|------|------|------|
| `joinmesg.*` | 所有插件功能的完全访问权限 | OP |
| `joinmesg.admin` | 管理员权限 | OP |
| `joinmesg.maincmd` | 访问主命令的权限 | OP |
| `joinmesg.reload` | 重载插件的权限 | OP |

## 运行要求

- Java 17 或更高版本
- Minecraft 1.20+（Paper/Bukkit/Folia）

## 支持

- 网站：https://mc.wunanc.top
- 问题反馈：在仓库中创建 Issue

---

## bStats 统计

本插件使用 bStats 收集匿名使用统计数据。您可以在 bStats 网站上查看插件统计信息。

![bStats](https://bstats.org/signatures/bukkit/JoinMesg.svg)