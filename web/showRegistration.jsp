<%-- 
    Document   : addCustomer
    Created on : Jan 16, 2019, 1:39:15 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
    </head>
    <body>
        <p>${info}</p>
        <h1>Sign up</h1>
        <form action="registration" method="POST">
        Name:<br>
        <input type="text" name="name" value="${name}"><br>
        Surname:<br>
        <input type="text" name="surname" value="${surname}"><br>
        Money:<br>
        <input type="text" name="money" value="${money}"><br>
        Email:<br>
        <input type="text" name="email" value="${email}"><br>
        Login:<br>
        <input type="text" name="login" value="${login}"><br>
        Password:<br>
        <input type="password" name="password1"><br>
        Password:<br>
        <input type="password" name="password2"><br>
        <br><br>
        <input type="submit" name="Add Customer"><br>
        </form>
    </body>
</html>
