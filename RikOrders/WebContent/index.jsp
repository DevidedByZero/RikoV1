<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>התחברות</title>
</head>
<body dir=rtl>

	<h1>הזן שם משתמש וסיסמא</h1>

	<p>${error}</p>

	<form action="login" method="post">
	
		שם משתמש: <input type="text" name="userName"> <br>
		סיסמא: <input type="text" name="password"><br> <input
			type="submit" name="loginBtn" value="התחבר">
	</form>



</body>
</html>