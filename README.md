

# Gaza Electricity Data Management System

## 📌 Overview
A Java-based application for managing and analyzing electricity supply data in the Gaza Strip, using a hierarchical AVL tree structure. Developed as part of the COMP242: Data Structures course, it supports efficient data operations and comprehensive statistical analysis via a Java GUI.

---

## 🚀 Features

### 🔄 Data Management
- Load electricity records from CSV files
- Add, update, delete, and search entries by date
- Print AVL tree levels and compute tree height for visual inspection

### 📊 Statistical Analysis
Generate statistics by:
- **Day**: Analyze values for a specific day across years
- **Month**: View monthly trends over different years
- **Year**: Display yearly summaries
- **Total**: Calculate min, max, sum, and average for:
  - Israeli Lines (MWs)
  - Gaza Power Plant (MWs)
  - Egyptian Lines (MWs)
  - Total Supply (MWs)
  - Overall Demand (MWs)
  - Power Cuts (hrs/day)
  - Temperature (°C)

### 💾 Data Persistence
- Save updated records to original or new CSV files

---

## 🧱 Technical Implementation

### 🔗 Data Structure
Uses a 3-level AVL tree:
1. **Year Tree** → AVL tree sorted by year
2. **Month Tree** → Nested AVL tree per year, sorted by month
3. **Day Tree** → Nested AVL tree per month, sorted by day  
Each leaf node holds an `ElectricityRecord` object.

✅ All operations (insert, delete, search, update) run in **O(log n)** time.

### 📦 Core Classes
- `AVL` – Handles AVL logic, rebalancing, and operations
- `AVLTreeNode` – Generic node class with height tracking
- `ElectricityRecord` – Stores daily electricity metrics

---

## 🗂️ Data Format
Each record contains:
- `Date` (YYYY-MM-DD)
- `Israeli Lines` (MWs)
- `Gaza Power Plant` (MWs)
- `Egyptian Lines` (MWs)
- `Total Supply` (MWs)
- `Overall Demand` (MWs)
- `Power Cuts` (hours/day)
- `Temperature` (°C)

---

## ▶️ How to Run
1. Install Java JDK (v8 or higher)
2. Compile all `.java` files
3. Run the `HelloApplication` class
4. Use the GUI menu to load CSV files and navigate features

---

## 💡 Example Usage

### Add New Record
```java
ElectricityRecord record = new ElectricityRecord("2022-05-15", 120.5f, 40.0f, 15.5f, 176.0f, 420.0f, 12.0f, 22.5f);
avlTree.add(15, 5, 2022, record);
```

### Generate Statistics
```java
String yearlyStats = avlTree.yearStatistic(2022);
double[] totalStats = avlTree.total();
// Indexes: 0–6 min, 7–13 max, 14–20 sum, 21–27 average
```

---

## 🌍 Background
The Gaza Strip faces ongoing energy shortages. This project uses real data provided by GEDCO and published by the [United Nations OCHA](https://www.ochaopt.org/page/gaza-strip-electricity-supply) to analyze trends and electricity metrics.

---

## 👨‍💻 Contributors
Developed as part of the **COMP242: Data Structures** course.

---

