
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%--  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> --%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>  
<!DOCTYPE html>
<html lang="en">
<head>
<%@ page isELIgnored="false" %>
<meta charset="UTF-8">

<title>Keep-Board</title>
</head>

<body>
this is index
<%-- 	<!-- Create a form which will have text boxes for Note title, content and status along with a Add 
		 button. Handle errors like empty fields.  (Use dropdown-list for NoteStatus) -->
		
<form action="saveNote" method="post">

<ul>
<li> Title : <input type="text" name="noteTitle" /> </li>
<li> Content : <input type="text" name="noteContent" /> </li>
<li> Status : <input type="text" name="noteStatus" /> </li>
<li> <input type="submit" value="Submit" /> </li>

</ul>

</form>
	 
<table>
<thead>
	<!-- <td>Created Date</td>
	<td>Status</td>
	<td>Content</td>
	<td>Title</td>
	<td>Id</td> -->
</thead>

<c:forEach var="note" items="${saveNote}">
    <tr>
      <td>${note.noteStatus} </td>
      <td>${note.noteContent} </td>
      <td>${note.noteTitle} </td>
      <td>${note.noteId} </td>
      <td> <a href="deleteNote/">Action</a> </td>
    
    </tr>

</c:forEach>



</table> --%>
	<!-- display all existing notes in a tabular structure with Title,Content,Status, Created Date and Action -->
</body>

</html>