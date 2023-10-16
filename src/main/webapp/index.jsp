<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<h2>Hello from the other side</h2>

<jsp:include page="html/profile.html" />

<a href="html/login.html">UserLogin</a>

<br/> <hr />

<form action="/profile" method="get">
    <input type="hidden" name="action" value="get_users">
    <input type="submit" value="Get Users">
</form>

</body>
</html>