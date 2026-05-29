# ShelfTracker: Product Supply and Monitoring System

ShelfTracker is a lightweight, real-time web application developed for small business owners and community pharmacies to manage their medicine inventories, monitor critical thresholds, and track product expirations dynamically. 

The system utilizes a split architecture combining a lightweight JavaScript frontend with a dynamic, robust Spring Boot REST API backend engine.

---

## 🚀 Key Features

* *Dynamic User Management (No Limits):* Supports multiple simultaneous users. Accounts can be registered as either ADMIN or STAFF dynamically at runtime without any hardcoded constraints.
* *Role-Based Access Control (RBAC):*
  * *ADMIN:* Full CRUD (Create, Read, Update, Delete) privileges over medicine records and control over system-wide configurations.
  * *STAFF:* Access to inventory dashboards with localized tools for quick Stock-In and Stock-Out adjustments.
* *Real-Time Telemetry Dashboard:* A centralized tracking display showcasing total medicine entries, active low-stock item counts, near-expiry metrics, and system alerts.
* *Smart Monitoring Gates:* Categorizes tracked items automatically into intuitive status indicators:
  * 🔴 *Critical/Near Expiry:* Quantity is 0 or product expires within 7 days.
  * 🟡 *Low Stock:* Quantity drops below or equals the dynamic threshold configuration.
  * 🟢 *High Stock:* Safe operational supply limits.

---

## 🛠️ Tech Stack & Architecture

### Frontend
* *Core:* Pure HTML5, CSS3, and Modern JavaScript (Vanilla JS ES6).
* *Communication Layer:* Native browser Fetch API communicating with RESTful endpoints via JSON.
* *State Management:* Session-persistent web storage matrices for authentication states.

### Backend
* *Framework:* Java Spring Boot (v3.x)
* *Architecture Design:* Controller-Repository-Model Structural Pattern.
* *Data Layer:* Multi-threaded Concurrent In-Memory Collections (ArrayList), maximizing runtime efficiency during live evaluations and capstone demonstrations.

---

## 📁 System Folder Structure

```text
├── ShelfTracker-Backend/
│   ├── src/main/java/com/ShelfTracker/api/
│   │   ├── controller/      # Auth, Dashboard, and Product REST Controllers
│   │   ├── model/           # User and Product Data Structural Outlines
│   │   └── repository/      # In-Memory DataStore management classes
│   └── build.gradle         # Dependencies and Build configurations
│
└── ShelfTracker-Frontend/
    ├── login.html           # Authentication Portal Entry UI
    ├── index.html           # Core Metrics & Expiry Monitoring Panel
    ├── settings.html        # Dynamic Thresholds & Multi-Admin Creation Page
    └── script.js            # Core Frontend Logic & Fetch Routing Engine
