# Twitter backend
This project is a Twitter-like backend application built using Spring Boot and Maven. It provides RESTful APIs to perform various Twitter-like functionalities such as posting tweets, following/unfollowing users, and fetching timelines.
## Technologies Used
### Backend :
* Java (version : JDK 22)
* Spring Boot (version 3.2.6)
* Dependencies
    * Spring Web
    * Spring Security
    * Spring Data Jpa
    * MySQL Driver
    * Lombok
### Database :
- MySQL
### TESTING 
- [POSTMAN](https://www.postman.com/downloads/)
### Installation :
JAVA :
To Install Java in Windows, Go to The Official JAVA Website by [Click Here](https://www.java.com/en/download) and download the latest java Package (used : JDK22). If you Don't Know the Complete Installation Process, then Watch this [Youtube Video](https://youtu.be/jPwrWjEwtrw?si=ubPgTNFYeCaHAK-X).

### MySQL :
To Install MySql in Windows, Visit The The Official MySQL site by [Click Here](https://dev.mysql.com/downloads/installer/) and Choose the Latest Supported version (used : V8.0.36) click the MSI installer and Download it. the follow the steps By Watching This [Youtube Video](https://youtu.be/uj4OYk5nKCg?si=FhETuZG7weRMPlYU).

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
### ER - Diagram
![Screenshot 2024-05-25 185038](https://github.com/vishwaravi/Twitter/assets/128621045/7761b25d-b81e-4598-8931-df833c1862be)
---
## DDL commands
### USER TABLE
```javascript
    CREATE TABLE users_table (
    id bigint NOT NULL AUTO_INCREMENT,
    user_id varchar(50) NOT NULL UNIQUE,
    user_name varchar(50) NOT NULL,
    user_dob date DEFAULT NULL,
    user_email varchar(50) NOT NULL,
    user_passwd varchar(100) NOT NULL,
    time_stamp varchar(255) DEFAULT NULL,
    user_pic varchar(255) DEFAULT NULL,
    banner_pic varchar(255) DEFAULT NULL,
    followers bigint DEFAULT NULL,
    following bigint DEFAULT NULL,
    PRIMARY KEY (id)
);
```
---
### TWEET TABLE
```javascript
    CREATE TABLE tweets_table (
    tweet_id bigint NOT NULL AUTO_INCREMENT,
    user_id varchar(255) DEFAULT NULL,
    hashtags varchar(255) DEFAULT NULL,
    time_stamp varchar(255) DEFAULT NULL,
    tweet_content varchar(255) DEFAULT NULL,
    likes_count bigint DEFAULT NULL,
    PRIMARY KEY (tweet_id)
);
```
---
### COMMENTS TABLE
```javascript
    CREATE TABLE comments_table (
    id bigint NOT NULL AUTO_INCREMENT,
    comment_content varchar(255) DEFAULT NULL,
    time_stamp varchar(255) DEFAULT NULL,
    tweet_id bigint DEFAULT NULL,
    user_id varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);
```
---
### LIKES TABLE
```javascript
    CREATE TABLE likes_table (
    id bigint NOT NULL AUTO_INCREMENT,
    liked_by varchar(255) DEFAULT NULL,
    tweet_id bigint DEFAULT NULL,
    time_stamp varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);
```
---
### FOLLOWERS TABLE
```javascript
    CREATE TABLE followers_table (
    id bigint NOT NULL AUTO_INCREMENT,
    followed_by varchar(255) DEFAULT NULL,
    time_stamp varchar(255) DEFAULT NULL,
    user_id varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);
```
---
### FOLLOWING TABLE
```javascript
    CREATE TABLE following_table (
    id bigint NOT NULL AUTO_INCREMENT,
    following varchar(255) DEFAULT NULL,
    time_stamp varchar(255) DEFAULT NULL,
    user_id varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);
```

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
