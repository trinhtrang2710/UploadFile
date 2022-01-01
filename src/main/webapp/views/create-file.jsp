<!DOCTYPE html>
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

    <div class="row">
        <%--        <div class="col-sm-1" data-toggle="modal" data-target="#myModal">--%>
        <%--            <button class="btn" ><i class="glyphicon glyphicon-cog"></i></button>--%>
        <%--        </div>--%>
        <div class="col-sm-9">
            <form:form action="/fileCtrl/upload" method="post" enctype="multipart/form-data">
                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-md-5">
                        <label for="sel1">File type:</label>
                        <select class="form-control" id="type">
                            <option value="1">Image</option>
                            <option value="2">Word</option>
                            <option value="3">Excel</option>
                            <option value="4">Pdf</option>
                        </select>
                    </div>
                    <div class="col-md-5">
                        <label for="sel1">Select File</label>
                        <div class="col-sm-10">
                            <input class="form-control" type="file" id="file" path="file"/>
                        </div>
                    </div>
                    <div style="">
                        <div style="margin-top: -40px;margin-bottom: 10px;margin-right:-33%;float: right;">
                            <button class="btn" data-toggle="modal" data-target="#myModal"><i
                                    class="glyphicon glyphicon-cog"></i></button>
                        </div>
                        <div style="    margin-top: 25px;">
                            <button type="submit" class="btn btn-primary">Upload</button>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </div>



    <div class="table-responsive pt-3">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Index</th>
                <th>File Name</th>
                <th>Version</th>
                <th>File size</th>
                <th>Created Time</th>
                <th>Download</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <!-- <%-- c:forEach => basic iteration tag --%>
            <%--                    <c:forEach var="users" items="${userList}">--%> -->
            <tr>
                <!-- <%-- c:out => for expressions --%> -->
                <td>
                    <c:out value="" />1
                </td>
                <td>
                    <c:out value="" />HI
                </td>
                <td>
                    <c:out value="" />1
                </td>
                <td>
                    <c:out value="" />30kb
                </td>
                <td>
                    <c:out value="" />20/12/2021
                </td>
                <td>
                    <c:out value="" />1
                </td>
                <td>
                    <button class="btn" style="background-color: white"><i
                            class="glyphicon glyphicon-download-alt"></i></button>
                    <button class="btn" style="background-color: rgb(189, 17, 17)"><i
                            class="glyphicon glyphicon-trash" style="color: white;"></i></button>
                </td>
                <!-- <%--                        </c:forEach>--%> -->
            </tbody>

        </table>
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
                    <form>
                        <div class="form-group">
                            <label for="usr">Max file size (MB)</label>
                            <input type="number" class="form-control" id="usr">
                        </div>
                        <div class="form-group">
                            <label for="pwd">Item per page</label>
                            <input type="text" class="form-control" id="pwd">
                        </div>

                        <label for="sel1">Allowed upload type</label>
                        <select class="form-control" id="sel1">
                            <option>Image</option>
                            <option>Word</option>
                            <option>Excel</option>
                            <option>Pdf</option>
                        </select>
                    </form>
                </div>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" style="background-color: green; color: white;"
                            data-dismiss="modal">Save</button>
                </div>

            </div>
        </div>
    </div>

</body>

</html>