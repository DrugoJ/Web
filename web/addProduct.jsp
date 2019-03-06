<%-- 
    Document   : addProduct
    Created on : Jan 16, 2019, 1:38:45 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buy product</title>
    </head>
    <body>
        <h1>Write the Product</h1>
        <form action="createProduct" method="POST">
            Product name:<br>
            <input type="text" name="name"><br>
            Price:<br>
            <input type="text" name="price"><br>
            Amount:<br>
            <input type="text" name="amount"><br>
            <br><br>
            <input type="submit" name="Add Product"><br>
        </form>
    </body>
</html>
