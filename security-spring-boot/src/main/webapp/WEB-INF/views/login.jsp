<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
</head>
<body>
<form action="login" method="post">
    <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">--%>
    <%--使用此标签屏蔽CSRF控制时，logout会出现404，暂时不知什么原因--%>
    用户名：<input type="text" name="username"><br>
    密&nbsp;&nbsp;&nbsp;码:
    <input type="password" name="password"><br>
    <input type="submit" value="登录">
</form>
</body>
</html>