This is a simple social media app centered around movies. Users can search for movies, rate, review, create lists, and get movie recommendations.

# Landing Page(HOME):

When the user opens the website using (http://localhost:8080/) they see the Home page, where they see the Popular movies listed(which are the most searched movies by the users of the application).
The Home page also displays the latest four reviews by registered users.

# Setting up the relational database:
The database currently has the following tables:
app_user
movie
review
user_favourite_movies
user_to_watch_movies
Of these, `AppUser`, `Movie` and `Review` are entity classes which represent the above tables.
`AbstractEntity` is the super class used by the above mentioned classes to generate their primary key 'Id'. This key is Generated using strategy = GenerationType.IDENTITY, which indicates that the persistence provider must assign primary keys for the entity using a database identity column.
AppUser and Movie classes are related through ManyToMany relationship and this relationship can be seen within join tables `user_favourite_movies` and `user_to_watch_movies`.
Similarly AppUser has a OneToMany relationship with the Review entity.
and Movie entity also has a OneToMany relationship with the Review entity.

# Search:
## Search Movies:
This project is using the OMDB api to retrieve movie details. Movie search functionality has been added that returns 10 (or less) movies The users search string is sent to an api call and returns a json file with the most relevant movies. From there the unique movie api ids are returned by string. The movie class is then able to find a specific movie based on this Id and stores the movie details.

## Search-Users:
Users can search for registered users and view their profile page to check their `Favorite List, To-Watch List, Reviews/ratings given by this searched user`. Search-User feature can be accessed even when a user is not logged in.
A warning message is displayed, if a blank field is searched, or no user is found for a search string.
This feature ensures that the resultant profile page has only view access when you are looking at someone else's profile, but has edit access when you are viewing your own profile.

# Add Review:
A user can search for a movie from Search-Movie dropdown option, and select to view the details of the desired movie.
On this details page, the user can select to review/rate that movie. The review/rate feature is available only to logged-in users. If the user is not logged in, they will be led to the login page and be redirected to this reviews page after login.
The user can now add a review and rating for this movie and Submit it. A warning message is displayed, if an empty review is submitted.

If the movie was already reviewed by this same user, the app shows the user's old review and lets them know that they have already reviewed this movie and that they can update this review. They also have the option to delete their reviews.

Reviews are displayed on the following pages:
Home page: latest 4 reviews by users within the app.
Movie-View page(movie detail page): all Reviews for that particular movie, by users within the app, with the latest reviews on top.
Profile page: all Reviews by that user, for various movies, with the latest reviews on top.
The users can choose to update/delete their reviews from their profile page. If they do so, they are given a warning message before the action is performed.


# User Account Creation and Management
Creating an account is pretty simple, requiring a unique username, email and password. In order to avoid naming conflicts with the User class in Spring Security, our app has an AppUser class. A data transfer object representing an AppUser is used to retrieve user data in the signup process fields like role, isEnabled, and theme are set to default values. All new accounts are disabled by default. A verification code is generated and stored in the verificationCode field and sent to the email provided in order to verify the email before allowing the new user on the app. The user is prompted to enter the code they received by email. When the correct code is entered, the account isEnabled field is set to true and the verificationCode field is set to null (this field may later be used in the password reset process). The user is then told they can now sign in for the first time. Successful authentication will redirect them to their profile page.

## AppUser Repository
AppUsers are stored in a MySQL database using the AppUserRepository. There are two methods, `findByUsername()`, which takes a username and returns an `Optional<AppUser>` (This is in order to check if the AppUser actually exists) and `findByVerificationCode()` which is used when setting up a new account in order to locate the corresponding user when they enter their verification code from their email to enable their account.

## User Settings
User settings are handled in the SettingsController and users can manage their account by clicking the cog in the navigation menu which will trigger a settings dropdown menu:

#### Change Theme:
This gives the user the option to choose from one of four themes, two dark and two light variants.
#### Update Email:
Users can update the email they have on file.
#### Change Password:
Users can change their password after supplying their current password. If the current password is correct and the new password matches the verifyNewPassword field, the new password is set and a message is displayed to let the user know they were successful.
#### Delete Account:
If users wish to leave the app, they may delete their account after supplying their password. If the password is correct, their account is deleted and they are signed out and returned to the home page.


# Customizing Themes
Users have the option to choose from one of four themes: Dark, Light, Seafoam, and Slate. The app's theming comes from the Theme Enum. Each option contains fields for a css path and class values for the navigation bar. The navigation bar class values are because two of the themes rely on bootstrap classes for the navigation styling. The theme is controlled by the ThemeController which uses the @ControllerAdvice annotation to create a global theme model attribute by passing it to all the controllers, which then passes it to all the Thymeleaf templates. This is cleaner and less tedious than manually adding model attributes for themes to each controller. The value of `theme` is either determined by user preference, if a user is present, or defaults to the dark theme if no user is present.


# Authentication:
Authentication is handled using Spring Security, which intercepts requests before sending them to the `DispatcherServlet`, where they would normally go.

## Authentication Flow

When the user signs in, a request is sent to the `securityFilterChain`, the username and password are extracted from the request and a `UsernamePasswordAuthenticationToken` with the user's credentials is created

The Authentication Manager attempts to authenticate the request by invoking an Authentication Provider (`DaoAuthenticationProvider`). If successful, it returns the `UsernamePasswordAuthenticationToken` complete with authorities and the provider sets the user the Security Context.

The provider then sends the request to the `DispatcherServlet`

Future requests are checked against the credentials stored in the Security Context, if present

## CSRF
It is important to note that CSRF protection is enabled by default in Spring Security, so while there is no configuration for it, it is still working behind the scenes. While probably not necessary, this was done as a best-practice. It's important to note because all forms require a `th:action="@{/postmapping-path}"` in the form element which creates a CSRF token. If this token is absent, the form will be rejected by the filter and the user will receive a 403 HTTP status code when trying to submit the form.

## Email Service
This service class handles preparing and sending emails using `MimeMessagePreparator` to prepare the email and `JavaMailSender` to send it. The class has a single method: `sendEmail()` which takes a subject, recipient(email), and message body. This is used when signing up for a new account and when resetting a password. It works by making use of Gmail SMTP and a unique app password for this app in order to send emails using their domain name. The settings for this are in application.properties.

## AuthenticationSuccessHandler & Roles
This sets which page the user is taken to upon successful authentication. It also allows you to set different pages for different roles. For this app, we only have a `USER` role so it just takes them to their profile. An `ADMIN` role wasn't necessary for our app but it is set up so that that can be added in later if needed.

## AppUserDetailsService
This is what creates the users in a session when they sign in. It implements the `UserDetailsService` and uses the `loadByUsername()` method to search the AppUser Repository for the user and if found, it builds a User in the session. If the user is not found, or the user is not enabled, it will throw a `UsernameNotFoundException`.

## Passwords
All passwords are encoded before being stored in the database using the `passwordEncoder` Bean which returns a `BCryptPasswordEncoder`. For operations requiring a password like deleting an account, the password must be checked against the encoded password using `matches(plaintextPassword, encodedPassword)` method.

## Authentication Provider
Our App uses `DaoAuthenticationProvider` to authenticate the request against the username and password stored in MySQL by calling both the `AppUserDetailsService` and the `PasswordEncoder`. We used this option because our users are stored in a database and this option allows Spring Security to authenticate against users in a database.

## Principal Service
The principal is the current user that is signed in. This service class greatly simplifies the process of retrieving that information and it is used for operations that pertain only to the signed in user, such as clicking Profile. It knows which profile to pull up by invoking the `getPrincipal()` method by getting the username from the Security Context and searching the AppUser Repository for the user. There is also a `getAuthentication()` method which is used in the `getPrincipal()` as well as the `ThemeController` to set the `theme` model attribute because the app needs a theme, whether or not someone is logged in, so an optional check is necessary first.


## Thymeleaf Spring Security
Thymeleaf allows us to change how the template is displayed based on if someone is signed in or not. In the `fragments.html` template, the nav element makes use of `sec:authorize="isAuthenticated()"` which only shows the content if the user is authenticated. This automatically hides and shows the PROFILE and settings navbar buttons if no one is signed in because they don't apply. The same idea is applied in the SIGN IN/ SIGN OUT button which will show one or the other depending on which case is true. 

## Resetting a Password
In the event that a user can't remember their password when signing in, there is an option for forgotten password which takes them to a page where they can enter their username and it will send a new password, consisting of 15 random alphanumeric characters, to the email on file for that username. Since this process requires email verification, the "password" is temporarily stored in the AppUser `verificationCode` field, rather than immediately overwriting the user's password. If the user does nothing, or didn't mean to reset their password, the password is unchanged and the verificationCode will just contain the new password until the next time they try to reset their password when it will be replaced by a different password. If the user wishes to complete the process of resetting the password, they enter the password in a form, similar to the verification code form when enabling a new account. If the code is correct, the user's password is reset to that 15 character code and the `verificationCode` field is set to `null`. After signing in, they have the option to change it to something else, although the random password is probably more secure than what most users would come up with.

