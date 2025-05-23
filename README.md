﻿# ♟️ Chess Android App

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-blueviolet?logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-orange?logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

An open-source Android Chess game built with Kotlin and Jetpack Compose. This project is an **expansion** of Part 1 from the [Kotlin Design Patterns & Best Practices series](https://youtu.be/G6FY8jHiDVY?si=6FHcGttuRLB0lBJ4), applying clean architecture, design patterns, and best practices.

---

## 📚 About This Project

This app started as an exercise in understanding design patterns through a real-world project — chess! ♟️ The base structure and inspiration are from the Part 1 video of the Kotlin Design Patterns series. This version builds upon that foundation, adding more complete functionality and refinements.

The goal is to showcase the application of clean architecture principles and various design patterns in a modern Android development context using Kotlin and Jetpack Compose.

### 📹 Original Video & Figma Design

* 📺 **[YouTube Video: Kotlin Design Patterns & Best Practices - Part 1](https://youtu.be/G6FY8jHiDVY?si=6FHcGttuRLB0lBJ4)**: The video that inspired this project, covering core concepts like OOP, FP, TDD, Factory, and Builder patterns in the context of building a chess game.
* 🎨 **[Figma Assets](https://www.figma.com/community/file/971870797656870866/chess-simple-assets)**: Visual design assets used in the app.

---

## 📱 Features

* ✔️ Standard chess rules implemented
* ✔️ Local two-player mode 🧑‍🤝‍🧑
* ✔️ Elegant UI built with Jetpack Compose ✨
* ✔️ Move validation and turn-based logic

---

## 🧠 Concepts & Patterns Used

This project demonstrates several important software design concepts and patterns:

* ✅ **Object-Oriented Programming**: Leveraging Inheritance and Polymorphism for piece representation and behavior.
* ✅ **Functional Programming**: Incorporating functional styles where appropriate.
* ✅ **Factory Method Design Pattern**: Used for piece creation.
* ✅ **Builder Pattern**: Implemented via Kotlin DSLs for flexible object construction.
* ✅ **Kotlin DSLs**: Creating domain-specific languages for clearer configuration.
* ✅ **Test-Driven Development (TDD)**: Applied for validating specific piece logic.
* ✅ **MVVM Architecture**: Structuring the app for better separation of concerns and testability.

---

## 🚀 Getting Started

Follow these steps to get the project running on your local machine.

### Prerequisites

* Android Studio Hedgehog  hedgehog or later
* Kotlin >= 1.9
* Gradle >= 8.0

### Installation

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/niikeshdahal/chess_android.git](https://github.com/niikeshdahal/chess_android.git)
    ```
2.  **Navigate to the project directory:**
    ```bash
    cd chess_android
    ```
3.  **Open the project in Android Studio:**
    * Select `File > Open` and choose the cloned `chess_android` directory.
4.  **Run the application:**
    * Build and run the app on an Android emulator or a physical device.

---

## 🛠️ Tech Stack

* **[Kotlin](https://kotlinlang.org/)**: Primary programming language.
* **[Jetpack Compose](https://developer.android.com/jetpack/compose)**: Modern UI toolkit for building native Android UIs.
* **[MVVM Architecture](https://developer.android.com/topic/architecture#modern-app-architecture)**: Recommended architectural pattern for Android development.
