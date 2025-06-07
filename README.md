## FlagExplorer

FlagExplorer is a modern Android app built using Jetpack Compose, Kotlin, and Clean Architecture. It allows users to browse and learn details about countries around the world, including their flags, capitals, and population data.

---

## Features

- Browse a grid of country flags
- Tap to view detailed information (capital, population)
- Built with Jetpack Compose for responsive UI
- SVG image support with Coil
- Uses clean architecture: domain, data, and presentation layers
- ViewModel with state management via Kotlin Flows
- Unit and integration test support

---

## Architecture

This app follows Clean Architecture principles and includes:

- **Presentation Layer**: UI built with Jetpack Compose and ViewModel (state-driven)
- **Domain Layer**: UseCases and domain models
- **Data Layer**: API service (Retrofit), DTOs, and repository pattern
- **Dependency Injection**: Koin
- **Navigation**: Jetpack Compose Navigation

---

## Tech Stack

| Tech               | Usage                      |
|--------------------|----------------------------|
| Kotlin             | Language                   |
| Jetpack Compose    | UI                         |
| Retrofit           | Network calls              |
| Coil (SVG)         | Image loading              |
| Koin               | Dependency Injection       |
| Coroutine / Flow   | Asynchronous State         |
| Material 3         | Modern UI Components       |
| JUnit / MockK      | Testing                    |

---

## Testing

The app includes:

- Unit tests for UseCases
- Integration tests for ViewModels using FakeRepositories
- Mocks using MockK

To run tests:

```bash
./gradlew test
```

---

## Getting started

### Prerequisites

- Android Studio Giraffe or later
- Android SDK 33 or above
- Internet connection (uses live REST API)

### Clone the repo

```bash
git clone https://github.com/yourusername/FlagExplorer.git
cd FlagExplorer
```

### Run the app

Open the project in Android Studio and click Run.

---

## Screenshots

### Home Screen

![Home Screen](docs/home.png)

### Detail Screen

![Detail Screen](docs/details.png)

---

## License

This project is licensed under the MIT License.

---

## Acknowledgements

- REST Countries API
- Jetpack Compose
- Coil

## Author
Built by Sashen Govender