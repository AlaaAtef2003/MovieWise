# MovieWise – Personalized Movie Suggestion App (Java)

## 📌 Overview
MovieWise is a Java-based application that reads movies and users from external text files, validates all input data, and generates personalized movie recommendations based on the genres the user likes.  
The system enforces strict validation rules for movie titles, movie IDs, user names, and user IDs.  

If the user likes at least one movie from a given genre, MovieWise recommends **all other movies** belonging to the same genre.

In case **any invalid input** is detected, MovieWise stops processing and outputs the **first encountered error** in the file `recommendations.txt`.

---

## 🎯 Features

### ✔️ **Input Validation**
- **Movie Title** must have each word starting with a capital letter  
- **Movie ID** must consist of:
  - All capital letters from the title  
  - Followed by **3 unique digits**
- **Movie Genre** is a list of genres (Action, Drama, Horror, etc.)
- **User Name**
  - Alphabetic and spaces only  
  - Cannot start with a space
- **User ID**
  - Exactly **9 alphanumeric characters**
  - Must start with numbers
  - Can end with **one optional letter**
  - Must be **unique**

---

## 📂 Input File Structure
### **movies.txt**
{movie_title},{movie_id}
{genre1},{genre2},{genre3}
(repeat for each movie)


### **users.txt**
User Name,User ID
Recommended Movie 1,Recommended Movie 2,Recommended Movie 3
(repeat for each user)


---

## 🎥 Recommendation Logic
1. Detect all genres of movies that the user likes  
2. Collect all movies that belong to those genres  
3. Exclude movies already liked by the user  
4. Output the recommended movie titles

---

## 🧪 Testing Strategy

### ✔️ **Unit Tests (JUnit)**
- Movie title validator  
- Movie ID validator  
- User name validator  
- User ID validator  
- Genre list parser  
- Recommendation generator  

### ✔️ **Integration Tests**
- Parsing → Validation → Recommendation workflow  

### ✔️ **Bug Tracking**
- All bugs are logged in **Jira**

---

## ▶️ How to Run

1. Place `movies.txt` and `users.txt` in the project root folder.
2. Compile all Java files:



### **movies.txt**
