<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01
Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Customer Manager</title>
</head>
<body>
<div align="center">
    <h2>Customer Manager</h2>
    <form method="get" action="search">
        <input type="text" name="keyword"/> &nbsp;
        <input type="submit" value="Search"/>
    </form>
    <h3><a href="/createCustomer">New Customer</a></h3>
    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>E-mail</th>
            <th>Address</th>
            <th>Action</th>
        </tr>
<%--        //L?i ? ?ây.--%>
<%--        qua bên này bà l?i g?i h?n th?ng cus ?y ra--%>
<%--        customerDTO gi? ch? là 1 th?ng thôi. bà ph?i g?i ListResult c?a nó ra.--%>
<%--        Mình l?u m?y th?ng cus kia ? trong ListResult mà--%>
        <c:forEach var="customer" items="${customerDTO.listResult}" >
            <tr>
                <td>${customer.id}</td>
                <td>${customer.name}</td>
                <td>${customer.email}</td>
                <td>${customer.address}</td>
                <td>
                    <a href="/edit?id=${customer.id}">Edit</a>
                    &nbsp;&nbsp;&nbsp;
                    <a href="/delete?id=${customer.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
