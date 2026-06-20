# News App  
The News app is an Android app built with Jetpack Compose, a clean and scalable architecture using NewsApi.  
This app fetches and displays top news headlines, allows users to save favorites, and includes a full-text search feature.  

## Features  
- Top headlines
- Favorite features
- Search functionality
- Error handling
- Secure Api Management

## Architecture  
- ### Presentation Layer(Module: app)
  The UI layer is built with Jetpack Compose. It uses ViewModels to manage UI state and a navigation component for screen flow.
- ### Domain Layer(Module: domain)
  Contains the core business logic of the app, including data models, repositories, and use cases.
- ### Data Layer(Module: data)
  Implements the repositories defined in the domain layer. It uses Retrofit for network communication and Room for local database caching and favorites management.

## Technologies Used
- Kotlin
- Jetpack Compose
- Retrofit
- Dagger Hilt
- Kotlin Coroutines & Flow
- Coil for Image loading from Api

## Video Demo



https://github.com/user-attachments/assets/0c2ff0df-1753-4571-af05-e95d8372e91b

