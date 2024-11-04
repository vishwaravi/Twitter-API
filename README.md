# Twitter backend
This project is a Twitter-like backend application built using Spring Boot and Maven. It provides RESTful APIs to perform various Twitter-like functionalities such as posting tweets, following/unfollowing users, and fetching timelines.
## Technologies Used
### Backend :
* Java (version : JDK 21 used)
* Spring Boot (version 3.3.5)
* Dependencies
    * Spring Web
    * Spring Security
    * Spring Data Jpa
    * Postgres Driver
    * Lombok
### Database :
- [Neon DB ](https://console.neon.tech)(Postgres DB)
- [Cloudinary ](https://cloudinary.com)(for storing images)
### TESTING 
- [POSTMAN](https://www.postman.com/downloads/)
### Installation :
Step 1 : Clone the Repo. <br>
Step 2 : rename the ".env.example" file to ".env" <br>
Step 3 : Setup the Environment variables using .env file by Your [Cloudinary](https://cloudinary.com) Api url and db url <br> Example :
```javascript
DB_URL= "Write Your DB URL starts With JDBC"
DB_USERNAME= "username here"
DB_PASSWORD= "password here"
CLOUDINARY_URL= "Cloudinary Url"
```
step 4 : Run the application

### IDE : [VSCode extension and Spring Tool Suite](https://spring.io/tools) , [IntelliJ](https://www.jetbrains.com/idea/download/?section=windows)

## Key Features :
### User :
- Creating Accounts
- Deleting Accounts
- Following Users
- Followers
### Content :
- Posts
- Tweets
- Likes
- comments
### other Featues
- Authentication and Authorization

## API ENDPOINTS
### USER
**POST** - `localhost:8080/register` - *REGISTER USER* <br>
**GET** - `localhost:8080/{userId}` - *GET USER DETAILS*<br>
**DELETE** - `localhost:8080/{userId}` - *DELETE USER* <br>
**PUT** - `localhost:8080/{userId}/follow` - *FOLLOW USER*<br>
**PUT** - `localhost:8080/{userId}/unfollow` - *UNFOLLOW USER*<br>

### TWEET 
**GET** - `localhost:8080/home` - *FEED POSTS* <br>
**POST** - `localhost:8080/home` - *POST TWEET* <br>
**DELETE** - `localhost:8080/home/{tweetId}` - *DELETE TWEET* <br>
**PUT** - `localhost:8080/home/{tweetId}/comment` - *POST COMMENT* <br>
**DELETE** - `llocalhost:8080/home/{tweetId}/comment` - *DELETE COMMENT* <br>
**PUT** - `localhost:8080/home/{tweetId}/like` - *LIKE POST* <br>
**PUT** - `localhost:8080/home/{tweetId}/dislike` - *DISLIKE POST* <br>
