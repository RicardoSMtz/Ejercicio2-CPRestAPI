# Proyecto-2-Computo-Movil
# Android Dragon Ball Characters App 
This is a mobile application developed using the official IDE "Android Studio" for an academic project in the Mobile Computing course.

## 📱 What Is It?
This app is a Dragon Ball character viewer that allows users to explore and learn more about characters from the Dragon Ball universe by consuming a public REST API. It retrieves a full list of characters and displays their details, including transformations, with images and stats.

## ✨ Features
List of 78 characters from the Dragon Ball API

Each list item shows:

- Character image
- Character name
- Character affiliation

Tap on a character to view detailed information
Detail view includes:
- Image
- Name
- Ki and max Ki
- Race and gender
- Description
- Affiliation
- Transformations (name, image, and Ki, if available)
- Error handling with retry button and user feedback
- No hardcoded UI strings – all text is managed through Android resource files
- Modern, responsive, and user-friendly interface

## 🔗 API Endpoints 
Character list (78 entries):
https://dragonball-api.com/api/characters?limit=78

Character details by ID:
https://dragonball-api.com/api/characters/{id}

## 🛠️ Technologies Used 
- **Language:** Kotlin
- **IDE:** Android Studio
- **Networking:** Retrofit (REST API consumption)
- **Image Loading:** Glide
- **Minimum API level:** 26

## 🎯 Purpose 
The main objective of this project is to demonstrate the use of REST API consumption in Android, proper UI management with RecyclerView and dynamic layouts, as well as good practices such as error handling and clean interface design.

---
📦 You can clone or download this project and open it directly with **Android Studio**.

