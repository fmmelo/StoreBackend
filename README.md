# Backend Store Project

<b>Backend for a small personal project.</b>

Personal project to help me learn and develop my experience in what I find to be my passion in programming, which is Web Development. 
With this project I aim to improve my code writing abilities, library/api usage, readability, stack and logical thinking.

### Description

This project is a mock-up of an ecomerce app with made up products fetched from the <a href="https://fakestoreapi.com/">fakestoreapi</a>.
It allows a user to register, login, view all products and their descriptions, place an order and view all orders made since.

### Authentication Server

REST authentication server: <code>http://localhost:5050/auth</code>
Endpoints:
- <code>POST /register</code>
- <code>POST /login</code>
- <code>POST /token_verify</code>
- <code>POST /logout</code>
- <code>POST /user_exists</code>

<b>Technologies used:</b>
- Javascript
- NodeJS
- mongodb 
- JsonWebToken

#### .env
For safety reasons I decided not to include usernames and passwords.

The <code>MAILER</code> variables require a functioning Gmail account with a generated API Password for third party usage. For that you will need to activate 2FA and then generate your APP password <a href="https://myaccount.google.com/apppasswords">here</a>. Use it to change the value of <code>MAILER_PWD</code>.

Inside <code>./AuthenticationServer</code> create a <code>.env</code> file and paste this template and replace each variable with your own values.

<code>PORT={YOUR_LOCAL_PORT}</code>
<code>MONGO_USER={YOUR_LOCAL_MONGO_USER}</code>
<code>MONGO_PWD={YOUR_LOCAL_MONGO_PWD}</code>
<code>MONGO_URL=mongodb://{YOUR_LOCAL_MONGO_USER}:{YOUR_LOCAL_MONGO_PWD}@mongo:27017/</code>
<code>ACCESS_TOKEN_SECRET={YOUR_RANDOM_SECRET}</code>
<code>MAILER_USER={REAL_GMAIL_USER}</code>
<code>MAILER_PWD={REAL_GOOGLE_API_GENERATED_PWD}</code>

### Business Logic Server

REST business server: <code>http://localhost:8080/storeapi</code>
Endpoints:
- <code>GET /product/{id}</code>
- <code>GET /products</code>
- <code>GET /order/{id}</code>
- <code>GET /orders</code>
- <code>POST /checkout</code>
- <code>GET /cart</code>
- <code>POST /cart/add</code>
- <code>POST /cart/remove</code>


<b>Technologies used:</b>
- Java
- Springboot 
- postgressql
- swagger

## Running the Servers (locally)

<b>Everything is dockerized using Docker Compose.</b>

In each server directory run: <code>docker-compose up --build -d</code> to build and run all services for each server application.

<code>cd AuthenticationServer
docker-compose up --build -d</code>
<b>&</b>
<code>cd BusinessLogic
docker-compose up --build -d</code>

