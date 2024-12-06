# Graduate Admissions Management System (GAMS)

[[Java CI with Maven]](https://github.com/users/Ruangfafa/projects/1)

## Project Overview

The Graduate Admissions Management System (GAMS) is a web application designed to allow applicants to submit their files including personal info, research field and various documents. The professors can submit their profiles specifying their research fields. The  applicants to submit applications, professors to review submissions, and administrators to make admissions decisions. This project is built with Spring Boot, AWS MySQL Database, and deployed on Azure. 

## Features

- Applicants can submit personal information, desired field of research, list of preferred professors, and application documents.
- Profs submit a profile specifying their fields of research and rate the applications assigned to them after reviewing. 
- Administrators filter and select applicants, route applications, view professor recommendations, and make final decisions.
- GitHub Actions is used for CI/CD to Azure.

## Prerequisites

- Java 17
- Maven (for local builds)
- Azure account (for deployment)

## UML Class Diagram of Models
![Updated UML](https://github.com/user-attachments/assets/5bcef09d-81ad-4e6c-9b9a-0166031fec9d)

The UML diagram illustrates the relationships between models, such as User, Application, Event, and ProfProfile. This provides an overview of the system's structure and the mapping to the database schema.

## Database Schema
- All Tables

![image](https://github.com/user-attachments/assets/0c8650ce-ee81-4ab0-aa8c-cd373e49aeae)


- Users
- 
![image](https://github.com/user-attachments/assets/cdc7ad4c-e732-422c-b4a5-c7c981d9a666)

![image](https://github.com/user-attachments/assets/258b3752-dc47-4ee7-9349-b4cae37f7580)

- Events
  
![image](https://github.com/user-attachments/assets/b755c0c2-b918-4a76-bb59-503b3be22ef6)

![image](https://github.com/user-attachments/assets/b496ef52-20db-4be2-9829-b8ff92119d68)

- ProfProfiles

![image](https://github.com/user-attachments/assets/f24dd9c7-220f-47f3-94fd-8780da276bed)

![image](https://github.com/user-attachments/assets/a8eef9f2-c491-405b-bda6-41cf13637194)

- Applications

![image](https://github.com/user-attachments/assets/b7e509ad-666d-4e78-8a4c-db45e8ac4474)

![image](https://github.com/user-attachments/assets/6878d3f2-e30b-45e1-ac46-d4bb333eb50a)



## Contributors

- Eric Mao
- Zeid Alwash
- Rebecca Li	
- Chia-Yu Liu
