# 🎮 Java Tetris-Style Game

## 🧠 Overview
This project is a **Java-based Tetris-style block game** built using **Swing** and **AWT**.  
It features customizable game settings, clean object-oriented design, and a modular rendering system for blocks and game components.

The application demonstrates strong GUI programming, event-driven logic, and design principles suitable for beginner-to-intermediate Java game development.

---

## 🧱 Key Components

### 1️⃣ Configuration Screen  
Handles all adjustable game settings before the game starts.

**Features**
- Change **field width**, **field height**, and **level** via sliders  
- Toggle:
  - Music  
  - Sound effects  
  - AI mode  
  - Extend mode  
- Back button to return to main screen  
- Stores all settings in static fields for global access

**Technologies**
- `JFrame`, `JPanel`, `JSlider`, `JLabel`, `JCheckBox`, `JButton`
- Event listeners for real-time updates

---

### 2️⃣ Block Rendering System  
Represents a single Tetris block drawn on the board.

**Features**
- Extends `Rectangle` for position and bounds checking  
- Custom rendering via `Graphics2D`  
- Each block stores its own color  
- Clean, reusable drawing method  
- Forms the basis for tetromino shapes

**Technologies**
- Java2D graphics (`Graphics2D`)
- OOP-based game component structure

---

## 🚀 Running the Project

### **Prerequisites**
- Java **17+**
- Any IDE (IntelliJ / Eclipse / VS Code) or command-line tools

### **Compile**
```bash
javac -d out $(find . -name "*.java")
