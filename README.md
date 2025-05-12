# 🛍️ Digital Store Application

A powerful and user-friendly **Spring Boot-based digital product store** where users can register, log in, browse categories, and buy/download digital content like **eBooks, Music, Photography, and Software**.

---

## 🚀 Technologies Used

- **Spring Boot**
- **Spring MVC**
- **Spring Data JPA**
- **Hibernate**
- **MySQL**
- **Thymeleaf**
- **HTML, CSS, Bootstrap**
- **Java**
- **RESTful APIs**
- **Postman**
- **Multipart File Upload**
- **JSON**
- **Tomcat (Embedded)**
- **IntelliJ IDEA**
- **MySQL Workbench**

---

## 📱 Features

### 🔐 User Registration & Login
- Unique **5-digit user ID** generation
- Session creation after successful login
- Validations for **phone number**, **account number**, and **4-digit PIN**

### 🛒 Product Browsing
- Explore 4 product types: **eBooks**, **Music**, **Photography**, **Software**
- Each product displays image, type, price, and description

### 💾 Buy & Download
- One product download at a time
- **Download count** is tracked per user
- Simulated payment via **account number** and **PIN**

### 🧑‍💼 Admin Dashboard
- Secure admin login
- **Add / Delete** products
- Search users or products by ID
- View all users and product records

---

## 📁 Project Structure
```
com.app.digital_store
│
├── controller
│     ├── UserController.java
│     ├── ProductController.java
│     ├── AdminController.java
│
├── entity
│     ├── User.java
│     ├── Product.java
│
├── repository
│     ├── UserRepository.java
│     ├── ProductRepository.java
│
├── service
│     ├── UserService.java
│     ├── ProductService.java
│
└── DigitalStoreApplication.java
```

---

## 📸 Screens

| ID   | Page / Panel Description             |
|------|--------------------------------------|
| 1.1  | 🔑 Login Page                        |
| 1.2  | 📝 Register Page                     |
| 1.3  | 🙌 Welcome Dashboard                 |
| 1.4  | 🗂️ Product Categories View          |
| 1.5  | 📥 Product Download Page             |
| 1.6  | 💳 Payment Page                      |
| 1.7  | 📊 Admin Dashboard                   |
| 1.8  | 🛠️ Product Management Panel         |
| 1.9  | 👥 User Management Panel             |

---

## 📲 Mobile Responsive Design

- Fully responsive using **Bootstrap**
- Product cards are displayed **vertically** on smaller screens
- Scrollable product sections with adaptive layout
- Every key functionality is preserved across devices

---

## 📝 Notes

- Only **one product** can be downloaded per transaction
- User’s **download count** is updated after each successful download
- Admin dashboard is **password protected** and not publicly accessible

---
