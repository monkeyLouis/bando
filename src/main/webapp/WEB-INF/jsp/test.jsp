<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html; charset=utf-8"%>

  
<html>  
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no"/>
<title>Reservation Form</title>  
<style>  
.error {  
    color: #ff0000;  
    font-weight: bold;  
}  
</style>  
</head>  
  
<body>  

HAPPY
    <form:form method="post" modelAttribute="vm">  
        <form:errors path="*" cssClass="error" />  
        <table>  
            <tr>  
                <td>Id</td>  
                <td><form:input path="memId" />  
                </td>  
                <td><form:errors path="memId" cssClass="error" />  
                </td>  
            </tr>  
            <tr>  
                <td>Name</td>  
                <td><form:input path="memName" />  
                </td>  
                <td><form:errors path="memName" cssClass="error" />  
                </td>  
            </tr>  
      
            <tr>  
                <td colspan="3"><input type="submit" />  
                </td>  
            </tr>  
        </table>  
    </form:form>  
</body>  
</html>  