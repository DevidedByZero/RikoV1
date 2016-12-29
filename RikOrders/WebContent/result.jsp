<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>

<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>

table {
    width: 100%;
    border: 1px solid black;
}

th, td {
    text-align: right;
    padding: 8px;
    border: 1px solid black;
}

tr:nth-child(even){background-color: #f2f2f2}
</style>

<script>
var newText;
var orderNumToUpdate;
function updateDesc(myCell){
	var row = myCell.parentNode;
	orderNumToUpdate = row.cells[0].firstChild.value;

	var btnId = '#'+orderNumToUpdate;
	$(btnId).show();
	
	newText = myCell.firstChild.innerHTML;
	
};

function edit(btn){
	if(newText != null){
			
		$(btn).hide();

    	document.getElementById("orderNumberId").value = orderNumToUpdate;
    	document.getElementById("newDescriptionId").value = newText;
    	document.getElementById("updateBtnId").click();
    	
		
	}
	else{
		return false;
	}
}


</script>

<title>הזמנות לקוח</title>
</head>
<body dir=rtl>

	<button type="submit" name="insert" value="הוסף"
		onclick="location.href = 'http://rikenviromentfinal.npthaappxw.us-west-2.elasticbeanstalk.com/welcome' ">
		הוסף</button>

	<form action="result" method="get">

		טווח <select name="byTime" id="byTime">
			<option value="week2">שבועיים</option>
			<option value="week">שבוע</option>
			<option value="month">חודש</option>
			<option value="all">הכל</option>
		</select> סטטוס <select name="byStatus" lang="he">
			<option value="all">הכל</option>
			<option value="customer">לקוח</option>
			<option value="ordered">הוזמן מספק</option>
			<option value="arrived">הגיע לחנות</option>
			<option value="notified">נאמר ללקוח</option>
			<option value="closed">נסגר</option>
		</select> ספק <select name="bySupp" lang="he">
			<option value="0">הכל</option>
			<option value="2">קל גב</option>
			<option value="3">שרש</option>
			<option value="1">טי אנד איי</option>
			<option value="6">טופגאן</option>
			<option value="5">גו נייצ'ר</option>
			<option value="4">אפוד</option>
			<option value="7">ולוסיטי</option>
			<option value="8">קולמן</option>
			<option value="999">כללי</option>
		</select> <input type="submit" name="search_params" value="חפש">

	</form>


	

	<p>
		טווח: ${time}  
		ססטוס: ${status}  
		ספק: ${supp}  
	</p>

	<div align="center" dir=rtl>
		<form id="formId" method="post" action="result">
			<table id="resultTable">
				<caption>
					<b> הזמנות </b>
				</caption>
				<tr>
					<th>שנה סטטוס</th>
					<th>תאריך הזמנה</th>
					<th>שם</th>
					<th>משפחה</th>
					<th>טלפון</th>
					<th>תיאור</th>
					<th>ספק</th>
					<th>מצב נוכחי</th>
					<th>תאריך מצב</th>
				</tr>

				<c:forEach items="${rows}" var="row">
					<tr>
					
						<td><input type=checkbox name=toChange
							VALUE="${row.orderNum}"></td>

						<td><c:out value="${row.orderDate}" /></td>
						<td><c:out value="${row.firstName}" /></td>
						<td><c:out value="${row.lastName}" /></td>
						<td><c:out value="${row.phone}" /></td>
						<td contenteditable="true" onkeyup="updateDesc(this)"><p style="float: right"><c:out value="${row.description}" /></p>
						<input type="button" id="${row.orderNum}" name="innerUpdateBtn" value="שנה" onclick="edit(this)" style="float: left" hidden='true'>
						</td>
						<td><c:out value="${row.supplierName}" /></td>
						<td><c:out value="${row.status}" /></td>
						<td><c:out value="${row.statusDate}" /></td>

					</tr>
				</c:forEach>
				<c:if test="${not empty error}">Error: ${error}</c:if>


			</table>
			<br> סטטוס חדש <select name="newStatus" dir="rtl" lang="he">
				<option value="לקוח">לקוח</option>
				<option value="הוזמן מספק">הוזמן מספק</option>
				<option value="הגיע לחנות">הגיע לחנות</option>
				<option value="נאמר ללקוח">נאמר ללקוח</option>
				<option value="נסגר">נסגר</option>
			</select> <input type="submit" name="changeBtn" value="שנה">

		</form>
		<p>${noneSelected}</p>
	</div>
	
	<form action="result" id="updateForm" method="post">
	<input type="hidden" id="orderNumberId" name="orderNumber" value=""> <br>
	<input type="hidden" id="newDescriptionId" name="newDescription" value="" ><br> 
	<input type="submit" id="updateBtnId" name="updateBtn" hidden='true'>
	</form>

</body>
</html>