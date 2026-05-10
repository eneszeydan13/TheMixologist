# The Mixologist 🍸

Welcome to **The Mixologist**, a modern Android application showcasing the power of Jetpack Compose, Clean Architecture, and modern Android development practices.

> [!NOTE]
> **Branch Information:**
> *   The `main` branch contains the original XML-based legacy implementation.
> *   The `compose-ui` branch contains the fully migrated Jetpack Compose application.

This repository serves as a prime example of migrating a legacy Android application to a fully declarative, type-safe, and highly modular architecture.

## 🚀 The Migration Journey

The migration of The Mixologist was conducted in phases, focusing on systematically replacing legacy components with modern alternatives while strictly adhering to **Clean Architecture** principles.

### Phase 1: Foundational Refactoring & Architecture Setup

During the initial phase, the focus was on establishing a solid foundation for the new UI and resolving core structural issues:

*   **Data Layer Refactoring:** Restructured the data layer for robust, structured parsing.
*   **Local Storage Integration:** Implemented a `Room` database to support persistent, offline-capable features, such as the "Favorites" page.
*   **Theming & Styling:** Introduced a dynamic, user-configurable light and dark mode theme system.
*   **Navigation Revamp (Part 1):** Removed the legacy top app bar and replaced it with a modern, Compose-driven bottom navigation system.
*   **Dependency Management:** Resolved build and runtime dependency conflicts, bringing all core libraries (Kotlin, Compose, Room, Hilt/Dagger) up to date.

### Phase 2: Full Jetpack Compose & Type-Safe Navigation

The second phase involved the complete removal of legacy UI systems and the finalization of the declarative UI:

*   **100% Jetpack Compose:** Completely eradicated all remaining XML layouts and legacy navigation dependencies. The entire presentation layer is now driven by Jetpack Compose.
*   **Type-Safe Navigation:** Migrated the routing system to use modern Type-Safe Navigation backed by **Kotlin Serialization**. This eliminates fragile string-based routes and ensures compile-time safety when passing arguments between screens.
*   **UI Polish & Layout Optimization:** 
    *   Implemented a responsive 2-column grid layout for the main cocktail list displays.
    *   Established consistent spacing, typography, and color tokens across the app to deliver a premium, cohesive user experience.
*   **Strict Clean Architecture:** Enforced strict boundaries between the `Domain`, `Data`, and `Presentation` layers. Business logic is isolated, making the application highly testable and scalable.

## 🛠️ Tech Stack & Architecture

*   **UI Toolkit:** Jetpack Compose
*   **Language:** Kotlin
*   **Architecture:** Clean Architecture (Domain, Data, Presentation layers) + MVI/MVVM
*   **Navigation:** Jetpack Navigation Compose (Type-Safe with Kotlin Serialization)
*   **Local Database:** Room
*   **Dependency Injection:** Hilt (Assumed based on standard modern Android setups)
*   **Asynchronous Programming:** Kotlin Coroutines & Flow

## 📖 Getting Started

To build and run this project:
1. Clone the repository.
2. Open the project in the latest version of Android Studio.
3. Sync the Gradle files.
4. Run the app on an emulator or physical device.

---
*Developed with a focus on modern Android excellence.*
