<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
	<div style="height:65px;align: center;background: #DB5227;font-family: Arial;color: white;">
		<br><b>
		<a href="" style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">Marked Web</a>
		</b>          
	</div>
	<br><br>
	<form method="POST" action="<%=request.getContextPath()%>/register">
      <fieldset>
	    <legend>Register</legend>	    
	    <table>
	    	<tr>
	    		<td><label>First Name:</label></td>
        		<td>
        			<input type="text" name="firstName"><br>        			
        		</td>
        	</tr>
        	<tr>
	    		<td><label>Last Name:</label></td>
        		<td>
        			<input type="text" name="lastName"><br>        			
        		</td>
        	</tr>
	    	<tr>
	    		<td><label>Email:</label></td>
        		<td>
        			<input type="text" name="email"><br>        			
        		</td>
        	</tr>
        	<tr>
        		<td><label>Password:</label></td>
        		<td>
        			<input type="password" name="password"><br>
        		</td>        
        	</tr>
        	
        	<tr>
        		<td>&nbsp;</td>
        		<td><input type="submit" name="submitLoginForm" value="Register"></td>
        	</tr>
        </table>
	  </fieldset>      
    </form>
</body>
</html>