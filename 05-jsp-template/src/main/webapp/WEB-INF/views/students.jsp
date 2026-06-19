<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Students</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>

<main class="container">
    <h1>Student List</h1>

    <%-- Flash messages --%>
    <c:if test="${not empty successMsg}">
        <div class="alert alert-success">${successMsg}</div>
    </c:if>
    <c:if test="${not empty errorMsg}">
        <div class="alert alert-error">${errorMsg}</div>
    </c:if>

    <a href="${pageContext.request.contextPath}/students/new" class="btn btn-primary">
        + Add Student
    </a>

    <table class="table">
        <thead>
            <tr>
                <th>#</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Date of Birth</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty students}">
                    <tr><td colspan="6" class="text-center">No students found.</td></tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="s" items="${students}" varStatus="loop">
                        <tr>
                            <td>${loop.count}</td>
                            <td><c:out value="${s.firstName}"/></td>
                            <td><c:out value="${s.lastName}"/></td>
                            <td><c:out value="${s.email}"/></td>
                            <td>
                                <fmt:formatDate value="${s.dob}" pattern="dd/MM/yyyy"/>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/students/${s.id}/edit">Edit</a>
                                <form method="post"
                                      action="${pageContext.request.contextPath}/students/${s.id}/delete"
                                      style="display:inline">
                                    <button type="submit"
                                            onclick="return confirm('Delete this student?')">
                                        Delete
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</main>

<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
