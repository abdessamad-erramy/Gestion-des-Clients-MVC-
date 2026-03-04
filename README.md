Client Management App – MVC Architecture – v1.0

Client Management App is a lightweight Android application built with Java that demonstrates the MVC (Model–View–Controller) architectural pattern using a local SQLite database.

The application provides a clean and structured interface for managing client records, including adding, updating, displaying, and deleting clients.

Overview

This project is designed for students learning Android architecture patterns.

It focuses on:

Clear separation of responsibilities

Clean Java structure

Proper package organization

SQLite database integration

Traditional Android MVC implementation (without ViewModel or LiveData)

The Activity acts as both the View and the Controller, while all database logic is isolated inside the Model layer.

Screenshot
<div align="center"> <img src="screenshots/home_screen.png" width="300"/> </div>
Features

Display all clients stored in SQLite

Add a new client (name and city)

Update an existing client

Delete a client using long press

Automatic refresh of the client list after each operation

Clean and consistent grey UI theme

Technologies Used

Java

Android SDK

SQLite

SQLiteOpenHelper

ListView

ArrayAdapter

Architecture (MVC)
Model

Client.java – Data class representing a client

ClientDbHelper.java – Handles:

Database creation

Insert operation

Update operation

Delete operation

Fetch all clients

All business logic and database operations are strictly located in this layer.

View

activity_main.xml

Contains:

Name input field

City input field

Add button

Update button

ListView

Responsible only for UI rendering.

Controller

MainActivity.java

Responsible for:

Handling user interactions

Calling database methods

Updating the ListView using ArrayAdapter

Project Structure
MVC_APP/
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/example/mvc_app/
│           │       ├── model/
│           │       │   └── Client.java
│           │       ├── data/
│           │       │   └── ClientDbHelper.java
│           │       └── ui/
│           │           └── MainActivity.java
│           └── res/
│               └── layout/
│                   └── activity_main.xml
├── screenshots/
├── README.md
└── LICENSE
Installation
1. Clone the repository
git clone https://github.com/abdessamad-erramy/Gestion-des-Clients-MVC-.git
cd Gestion-des-Clients-MVC-
2. Open in Android Studio

Launch Android Studio

Click Open

Select the MVC_APP folder

Wait for Gradle synchronization

3. Build the Project

Make sure Gradle finishes building successfully.

If needed:

Build → Rebuild Project
Running the Application

Start an Android Emulator
or connect a physical Android device

Click Run in Android Studio

The application will launch automatically.

How to Use

Enter the client name.

Enter the city.

Click Add to insert the client.

Tap on a client to load its information into the input fields.

Modify the data and click Update.

Long press on a client to delete it.

The ListView updates automatically after each action.

Educational Objective

This project helps students:

Understand classic MVC architecture in Android

Practice SQLite database integration

Learn separation of concerns

Prepare for comparison with MVVM architecture
