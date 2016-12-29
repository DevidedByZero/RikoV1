<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
	function validateForm(){
		
		
		var phone = document.getElementById("phoneID").value;
		
		if(phone.length < 9 || phone.length > 10){
			window.alert("טלפון: אורך שגוי");
			return false;	
		}
		
		else{
			document.getElementById("formID").submit();
		}
	}


</script>

<title>הוספת הזמנה</title>
</head>



<body dir=rtl>


	<p>${message}</p>

	<datalist id="suppliers">
		<option value="1">טי אנד איי</option>
		<option value="2">קל גב</option>

		<option value="3">שרש</option>

		<option value="4">אפוד</option>

		<option value="5">גו נייצ'ר</option>

		<option value="6">טופגאן</option>

		<option value="7">ולוסיטי</option>

		<option value="8">קולמן</option>
		<option value="9">כללי</option>

	</datalist>

	<form action="welcome" method="post" id="formID">
		שם פרטי: <input type="text" name="firstName" lang="he"> <br>
		שם משפחה: <input type="text" name="lastname" lang="he"><br>

		טלפון: <input type="tel" name="phone" id="phoneID"> <input type="submit"
			name="serach_phone" value="חפש"><br> תיאור: <input
			type="text" name="description" lang="he"> <br> ספק: <input
			type="text" list="suppliers" name="supplier" lang="he"><input
			type="submit" name="searchSupp" value="חפש"> <br> <input
			type="submit" name="insert_button" value="הוסף" onclick = "return validateForm()">
	</form>


	<div dir=rtl>
		<h4>כמות רשומות לפי מצב </h4>
	
		<ul style="list-style-type:square">
  			<li>${custCount} לקוח</li>
  			<li>${orderedCount} הוזמן</li>
  			<li>${arrivedCount} הגיע</li>
  			<li>${notifiedCount} נאמר</li>
</ul>
	
	</div>

</body>
</html>