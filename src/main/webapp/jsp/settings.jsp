<%--
  Created by IntelliJ IDEA.
  User: Tom
  Date: 10/2/2023
  Time: 8:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Settings</title>
</head>
<body>

<h1 style="color: ${cookie.get("color").value}">Hiya!</h1>

<div>
  <form action="/setting" method="post">
      <select size="8" name="color">
          <option value="red">Red</option>
          <option value="blue">Blue</option>
          <option value="yellow">Yellow</option>
          <option value="green">Green</option>
          <option value="black">Black</option>
          <option value="white">White</option>
          <option value="brown">Brown</option>
          <option value="cyan">Cyan</option>
      </select>
      <input type="submit" value="Save Color">
  </form>
</div>

</body>
</html>
