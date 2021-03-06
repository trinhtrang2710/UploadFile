<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <title>File Managemnet</title>
    <style>
        table,
        th,
        td {
            border: 1px solid rgb(92, 92, 92);
            border-collapse: collapse;
        }

        .menu {
            display: flex;
            flex-direction: column
            /* or inline-flex */
        }
    </style>
</head>

<body>
<h2 style="text-align: center;">Files Managment</h2>
<div class="container"
     style="border: 1px rgb(221, 218, 218) solid; border-radius: 5px; width: 50%; margin: 0 auto; padding: 20px;">
    <div class="alert alert-success">
        <strong>${message}</strong>
    </div>

    <div class="row">
        <div class="col-sm-12">
            <button class="btn" style="float: right;" data-toggle="modal" data-target="#myModal"><i
                    class="glyphicon glyphicon-cog"></i>
            </button>
        </div>
        <div class="col-sm-9">
            <form:form id="formUploadFile" method="POST" action="upload" enctype="multipart/form-data"
                       modelAttribute="myFile">
                <div style="margin-bottom:10px;">
                    <div class="col-md-5">
                        <label>Select File</label>
                        <input type="file" name="multipartFile" multiple/>
                    </div>
                    <input style="margin-top:20px;" type="submit" value="Submit"/>
                </div>
            </form:form>
        </div>
    </div>


    <div class="table-responsive">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Index</th>
                <th>File Name</th>
                <th>Version</th>
                <th>File size (Kb)</th>
                <th>Created Time</th>
                <th>Download</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${files}" varStatus="loop">
            <tr>
                <!-- <%-- c:out => for expressions --%> -->
                <td>
                    <c:out value=""/>${loop.index+1}
                </td>
                <td>
                    <c:out value=""/>${item.name}
                </td>
                <td>
                    <c:out value=""/>${item.version}
                </td>
                <td>
                    <c:out value=""/>${item.fileSize}
                </td>
                <td>
                    <c:out value=""/>${item.createdDateTime}
                </td>
                <td>
                    <c:out value=""/>${item.numberOfDownload}
                </td>
                <td>
                    <a class="btn" style="background-color: white" href="/download?id=${item.id}">
                        <i class="glyphicon glyphicon-download-alt"></i>
                    </a>
                    <a class="btn" style="background-color: rgb(189, 17, 17)" href="/delete?id=${item.id}">
                        <i class="glyphicon glyphicon-trash" style="color: white;"></i>
                    </a>
                </td>
                </c:forEach>
            </tbody>
        </table>
        <c:if test="${!empty files}">
            <footer class="footer bg-white b-t">
                <c:set var="j"
                       value='<%=request.getParameter("p") == null ? 1 : request
					.getParameter("p")%>'/>

                <div class="row text-center-xs">
                    <div class="col-md-4 hidden-sm">
                        <p class="m-t" style="color: #2e3e4e;">
                            T???ng s??? <strong>${total}</strong> d??? li???u
                        </p>
                    </div>
                    <div class="col-md-4 hidden-sm">
                        <p class="m-t" style="color: #2e3e4e; text-align: center;"></p>
                    </div>
                    <div class="col-md-12 col-sm-12 text-right">
                        <ul class="pagination pagination-sm m-t-sm m-b-none m-r-xs">
                            <c:choose>
                                <c:when test="${j == 1}">
                                    <li class="disabled"><a href="#">&lt;&lt;</a></li>
                                    <li class="disabled"><a href="#">&lt;</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="/file?page=1">&lt;&lt;</a></li>
                                    <li><a href="/file?page=${p-1}">&lt;</a></li>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach var="i" begin="${startPage}" end="${endPage}">
                                <c:choose>
                                    <c:when test="${i == j}">
                                        <li ><a href="/file?page=${i}"><c:out value="${i}" /></a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href="/file?page=${i}"><c:out
                                                value="${i}"/></a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${j == totalPage}">
                                    <li class="disabled"><a href="#">&gt;</a></li>
                                    <li class="disabled"><a href="#">&gt;&gt;</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="/file?page=${p+1}">&gt;</a></li>
                                    <li><a href="/file?page=${totalPage}">&gt;&gt;</a></li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>
            </footer>
        </c:if>
    </div>

    <!-- The Modal -->
    <div class="modal" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title" style="text-align: center;">Setting</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <form:form action="setting/update" method="POST" modelAttribute="settingg">
                        <div class="form-group">
                            <label for="usr">Max file size (MB)</label>
                            <input type="number" class="form-control" id="usr" name="maxFileSize" path="maxFileSize">
                        </div>
                        <div class="form-group">
                            <label for="pwd">Item per page</label>
                            <input type="text" class="form-control" id="pwd" name="itemPerPage" path="itemPerPage">
                        </div>

                        <label for="sel1">Allowed upload type</label>
                        <select class="form-control" id="sel1" name="mimeType" path="mimeType">
                            <option value="image">Image</option>
                            <option value="word">Word</option>
                            <option value="excel">Excel</option>
                            <option value="pdf">Pdf</option>
                        </select>
                        <br>
                        <div class="modal-footer">
                            <input type="submit" class="btn btn-default" style="background-color: green; color: white;"
                                   data-dismiss="modal">
                            </input>
                        </div>
                    </form:form>
                </div>

                <!-- Modal footer -->
                <%--                    <div class="modal-footer">--%>
                <%--                        <button type="submit" class="btn btn-default" style="background-color: green; color: white;"--%>
                <%--                                data-dismiss="modal">Save--%>
                <%--                        </button>--%>
                <%--                    </div>--%>
            </div>
        </div>
    </div>
</div>
</body>

</html>