# 📱 Student Grade Calculator

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Platform">
  <img src="https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/Min%20SDK-26-00C853?style=for-the-badge&logo=android&logoColor=white" alt="Min SDK">
  <img src="https://img.shields.io/badge/Target%20SDK-35-00C853?style=for-the-badge&logo=android&logoColor=white" alt="Target SDK">
</p>

<p align="center">
  <img src="https://img.shields.io/badge/License-GPL%20v3-blue?style=for-the-badge" alt="License">
  <img src="https://img.shields.io/badge/Jetpack%20Compose-2024.09-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/Material%20Design%203-6200EE?style=for-the-badge&logo=materialdesign&logoColor=white" alt="Material Design 3">
  <img src="https://img.shields.io/badge/Room%20DB-2.6.1-00897B?style=for-the-badge" alt="Room Database">
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Build%20Tools-35.0.0-orange?style=for-the-badge" alt="Build Tools">
  <img src="https://img.shields.io/badge/Gradle-8.13.2-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle">
  <img src="https://img.shields.io/badge/Version-1.0-brightgreen?style=for-the-badge" alt="Version">
  <img src="https://img.shields.io/github/stars/Maheswara660/Student-Grade-Calculator?style=for-the-badge" alt="Stars">
</p>

---

A comprehensive Android application built with Jetpack Compose and Kotlin that helps students calculate grades, manage CGPA, and track their academic performance. This project was developed as a learning experience to master modern Android development practices and Material Design 3.

## 📦 Technologies

- `Kotlin`
- `Jetpack Compose`
- `Material Design 3`
- `Room Database`
- `Navigation Compose`
- `ViewModel & LiveData`
- `Coroutines`
- `Gson`
- `Exp4j` (Expression Evaluator)

## 🦄 Features

Here's what you can do with Student Grade Calculator:

- **Grade Calculator**: Calculate your grades based on marks and weights. Input your assignment scores, exam marks, and their respective weights to get your final grade instantly.

- **CGPA Calculator**: Track your Cumulative Grade Point Average across multiple semesters. Add courses with their credits and grades to calculate your overall CGPA.

- **Simple Calculator**: A built-in calculator for quick mathematical operations. Perfect for calculating percentages, averages, and other academic computations.

- **Student Manager**: Manage multiple student profiles. Store and organize information for different students, making it easy to track grades for tutoring or group projects.

- **History Tracking**: All your calculations are automatically saved. Review past calculations and track your academic progress over time.

- **Beautiful UI**: Modern Material Design 3 interface with smooth animations and intuitive navigation. The app features a clean, professional design that makes grade calculation enjoyable.

- **Dark Mode Support**: Seamlessly switch between light and dark themes based on your system preferences.

- **Persistent Storage**: All data is stored locally using Room Database, ensuring your information is always available offline.

## 👩🏽‍🍳 The Process

I started by designing the app architecture, focusing on a clean separation of concerns using MVVM (Model-View-ViewModel) pattern. This ensured the codebase would be maintainable and scalable.

Next, I implemented the Room Database to handle persistent storage for student data and calculation history. This was crucial for providing a seamless user experience where data persists across app sessions.

I then built the core calculation features - the grade calculator, CGPA calculator, and simple calculator. Each required careful mathematical logic to ensure accurate results. The grade calculator needed to handle weighted averages, while the CGPA calculator had to manage credit hours and grade points.

To make navigation intuitive, I implemented Navigation Compose with a bottom navigation bar and a smooth splash screen. The navigation structure allows users to easily switch between different calculators and view their history.

The student manager feature was added to support multiple profiles, which involved creating a comprehensive database schema and implementing CRUD operations with proper state management.

Throughout development, I focused on creating a polished UI using Material Design 3 components. I implemented custom color schemes, typography, and animations to make the app visually appealing and user-friendly.

Finally, I added the settings screen with options to view the source code and learn more about the project, encouraging open-source collaboration.

## 📚 What I Learned

During this project, I gained valuable experience in modern Android development and deepened my understanding of several key concepts.

### 🎨 Jetpack Compose:

- **Declarative UI**: Learned to build UIs declaratively, which is more intuitive and requires less boilerplate code compared to traditional XML layouts.
- **State Management**: Mastered state hoisting and managing UI state effectively in Compose.
- **Recomposition**: Understood how Compose recomposes UI elements efficiently when state changes.

### 🗄️ Room Database:

- **Database Design**: Created a normalized database schema with proper relationships between entities.
- **DAOs and Queries**: Implemented Data Access Objects with complex queries for filtering and sorting data.
- **Type Converters**: Used Gson to convert complex objects to JSON for storage in the database.

### 🧭 Navigation:

- **Navigation Compose**: Implemented type-safe navigation with arguments passing between screens.
- **Deep Linking**: Set up navigation graphs for a smooth user experience.

### 🏗️ Architecture:

- **MVVM Pattern**: Separated business logic from UI using ViewModels, making the code more testable and maintainable.
- **Repository Pattern**: Abstracted data sources to make the app more flexible and easier to test.

### 🔄 Coroutines & Flow:

- **Asynchronous Operations**: Used Kotlin Coroutines for database operations and background tasks.
- **State Flow**: Implemented StateFlow for reactive data streams from the database to the UI.

### 🎯 Material Design 3:

- **Modern UI Components**: Utilized Material 3 components like Cards, FABs, and NavigationBar.
- **Color System**: Implemented dynamic color theming with custom color palettes.
- **Typography**: Applied Material Design typography scales for consistent text styling.

### 📐 Mathematical Logic:

- **Grade Calculations**: Implemented weighted average calculations for accurate grade computation.
- **CGPA Algorithm**: Developed logic to calculate cumulative GPA based on credit hours and grade points.

### 📱 Android Best Practices:

- **Lifecycle Awareness**: Properly handled Android lifecycle events to prevent memory leaks.
- **Resource Management**: Organized resources efficiently with proper naming conventions.

### 📈 Overall Growth:

This project significantly improved my Android development skills, especially in modern declarative UI development with Jetpack Compose. I learned to think in terms of state and composition, which is a paradigm shift from traditional imperative UI development. The experience of building a complete app from scratch, including database design, business logic, and UI, has prepared me well for future Android projects.

## 💭 How can it be improved?

- Add export functionality to save calculation history as PDF or CSV files.
- Implement cloud sync to backup data across multiple devices.
- Add support for different grading systems (letter grades, percentage, etc.).
- Include data visualization with charts and graphs to show grade trends over time.
- Add reminder notifications for upcoming exams or assignment deadlines.
- Implement a GPA predictor to show what grades are needed to achieve target CGPA.
- Add support for multiple languages for international students.
- Include a grade comparison feature to compare performance across semesters.
- Add widgets for quick access to calculators from the home screen.
- Implement biometric authentication for privacy protection.

## 🚦 Running the Project

To run the project in your local environment, follow these steps:

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/Maheswara660/Student-Grade-Calculator.git
   ```

2. Open the project in Android Studio (Arctic Fox or later recommended).

3. Wait for Gradle to sync and download all dependencies.

4. Connect an Android device or start an emulator (API level 26 or higher).

5. Click the "Run" button or press `Shift + F10` to build and run the app.

6. The app should launch on your device/emulator.

### Requirements:
- Android Studio Arctic Fox or later
- Android SDK 26 or higher
- Kotlin 1.9+
- Gradle 8.0+

## 📄 License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](https://github.com/Maheswara660/Student-Grade-Calculator/issues).

## 👨‍💻 Author

**Maheswara660**

- GitHub: [@Maheswara660](https://github.com/Maheswara660)
- Repository: [Student-Grade-Calculator](https://github.com/Maheswara660/Student-Grade-Calculator)

## ⭐ Show your support

Give a ⭐️ if this project helped you learn something new or if you find it useful!

---

*Built with ❤️ using Jetpack Compose*
