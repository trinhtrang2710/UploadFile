<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/views" var="url"></c:url>
<!DOCTYPE html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>File Manage</title>
    <!-- base:css -->
    <link rel="stylesheet"
          href="${url}/vendors/mdi/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="${url}/vendors/css/vendor.bundle.base.css">
    <!-- endinject -->
    <!-- plugin css for this page -->
    <!-- End plugin css for this page -->
    <!-- inject:css -->
    <link rel="stylesheet" href="${url}/css/style.css">
    <!-- endinject -->
    <link rel="shortcut icon" href="${url}/images/favicon.png" />
</head>

<body>
<div class="container-scroller d-flex">
    <!-- partial:./partials/sidebar.jsp -->
<%--    <jsp:include page="sidebar.jsp"></jsp:include>--%>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
        <!-- partial:./partials/navbar.jsp -->
<%--        <jsp:include page="navbar.jsp"></jsp:include>--%>


        <!-- partial -->
        <div class="main-panel">
            <div class="content-wrapper">
                <div class="row">
                    <div class="col-lg-12 grid-margin stretch-card">
                        <div class="card">
                            <div class="card-body">
                                <div>

                                    <h4 class="card-title">User table</h4>

                                </div>
                                <div class="table-responsive pt-3">
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>User ID</th>
                                            <th>Family name</th>
                                            <th>Given Name</th>
                                            <th>Phone number</th>
                                            <th>Email</th>
                                            <th>Options</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <%-- c:forEach => basic iteration tag --%>
                                        <c:forEach var="users" items="${userList}">
                                        <tr>
                                                <%-- c:out => for expressions --%>
                                            <td><c:out value="" /></td>
                                            <td><c:out value="" /></td>
                                            <td><c:out value="" /></td>
                                            <td><c:out value="" /></td>
                                            <td><c:out value="" /></td>

                                            <td><a class="btn btn-outline-danger btn-icon-text"
                                                   href="delete?id=<c:out value='${users.id}' />"
                                                   role="button"> <i
                                                    class="mdi mdi-delete-sweep btn-icon-prepend"></i>
                                                Delete
                                            </a></td>
                                            </c:forEach>
                                        </tbody>

                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
            <!-- main-panel ends -->
            <!-- content-wrapper ends -->
            <!-- partial:./partials/footer.jsp -->
<%--            <jsp:include page="footer.jsp"></jsp:include>--%>
            <!-- partial -->
        </div>
        <!-- main-panel ends -->
    </div>
    <!-- page-body-wrapper ends -->
</div>
<!-- container-scroller -->
<!-- base:js -->
<script src="../../vendors/js/vendor.bundle.base.js"></script>
<!-- endinject -->
<!-- Plugin js for this page-->
<!-- End plugin js for this page-->
<!-- inject:js -->
<script src="${url}/js/off-canvas.js"></script>
<script src="${url}/js/hoverable-collapse.js"></script>
<script src="${url}/js/template.js"></script>
<!-- endinject -->
<!-- End custom js for this page-->
</body>

</html>