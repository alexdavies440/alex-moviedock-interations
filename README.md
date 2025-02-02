# About the Project
This is a simple social media app centered around movies. Users rate, rewiew, and create lists of their favorite movies as well as a to-watch list. This app primarily solves the problem of choice paralysis by letting users see what other people are watching to give them ideas.

# Tech Stack
This is a Spring Boot application that uses Java for the backend and Thymeleaf for the front end. Styling is done using Bootstrap with some CSS. Authentication is handled by Spring Security and for our relational database we used MySQL.

# Getting Started
You will need to connect the project to a MySQL database by going to the Administration tab and then Users and Privileges > Add Account. Set the `Login Name:` and `Password:` to the values in the application.properties file in the project and set `Limit to Hosts Matching:` to `localhost`. Go to the Schema Privileges tab, click Add Entry and under `Selected schema:` select `moviedock`. Then click Select "ALL" to give the user all privileges and click Apply.

# Project Features and Contributions
## Aruna Nadar
* Relational Database
* Movie Reviews and Ratings
* User Search

## Michael Dyer
* API Service
* Movie Search
* Movie Details Page
* Movie Recommendations(optional)

## Nandini Doranal Shanthappa
* CRUD Functionality
* To-Watch Movie Lists
* Favorite Movie Lists

## Alex Davies
* User Account Creation and Management
* Authentication (Spring Security)
* User Themes