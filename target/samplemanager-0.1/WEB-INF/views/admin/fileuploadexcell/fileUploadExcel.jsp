<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
    <title>Subir Documento</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-md-6">
            <form method="POST" action="file/uploadFile" enctype="multipart/form-data">
                <div class="form-group">
                    <label class="control-label">Documento: </label>
                    <input type="file" class="form-control" name="file">
                </div>
                <div class="form-group">
                    <input type="submit" class="form-control btn btn-success" value="Subir">
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
uploadMultiple.jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
    <title>Subir Documento</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-md-6">
            <form method="POST" action="file/uploadMultipleFile" enctype="multipart/form-data">
                <div class="form-group">
                    <label class="control-label">Documento1: </label>
                    <input type="file" class="form-control" name="file">
                </div>
                <div class="form-group">
                    <label class="control-label">Documento2: </label>
                    <input type="file" class="form-control" name="file">
                </div>
                <div class="form-group">
                    <input type="submit" class="form-control btn btn-success" value="Subir">
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>