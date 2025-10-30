## 🌿 Easeon – Pickup Radius
**Minecraft:** `1.21.10`, `1.21.9`  
**Loader:** `Fabric`  
**Side:** `Server-Side`, `Singleplayer`

**This mod requires <a href="https://modrinth.com/mod/easeon-ss-core" target="_blank">EaseonSS-Core</a>**


## Overview

**Pickup Radius** is a lightweight Fabric mod that lets you control how far away players can collect items and experience orbs.  
It’s perfect for servers or solo worlds that want smoother, faster, and more convenient resource gathering — no client mod required.


## ✨ Key Features

- **Adjustable Range** – Set your pickup radius from **1 to 15 blocks** *(default: 5)*  
- **Unified Control** – Applies to both items and experience orbs  
- **Server-Side Simplicity** – Players don’t need to install anything  


## 🧭 Commands
All commands require OP level 2 permission.

**View current setting:**
```
/easeon pickup-radius
```
**Change pickup radius:**
```
/easeon pickup-radius <1–15>
```
**Example:**
```
/easeon pickup-radius 10
```
→ sets the collection range to 10 blocks.



## Configuration
```json
{
  "pickupRadius": 5,
  "requiredOpLevel": 2 // Requires a server restart to take effect.
}
```
`config/easeon/easeon.ss.pickupradius.json`
---