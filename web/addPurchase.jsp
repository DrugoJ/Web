<%-- 
    Document   : addPurchase
    Created on : Jan 16, 2019, 1:39:32 PM
    Author     : User
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Purchase</title>
    </head>
    <body>
        <h1>Purchase</h1>
        <form action="addPurchase" method="POST">
            Product list:<br>
            <select name="productId">
                <c:forEach var="product" items="${listProducts}">
                    <option value="${product.ID}">${product.name}</option>
                </c:forEach>
            </select><br>
<!--            Customer list:<br>
            <select name="customerId">
                <c:forEach var="customer" items="${listCustomers}">
                    <option value="${customer.ID}">${customer.name}</option>
                </c:forEach>
            </select><br>-->
            Amount:<br>
            <input type="text" name="quantity"><br><br>
            <input type="submit" value="Buy">
        
    </body>
</html>
