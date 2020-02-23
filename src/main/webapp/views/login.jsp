<%--
  Created by IntelliJ IDEA.
  User: VULCAN
  Date: 2020-1-14
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" href="../statics/bootstrap-3.3.7-dist/css/bootstrap.min.css">

    <!--描述：bootstrap的核心式样库-->
    <link rel="stylesheet" type="text/css" href="../statics/bootstrap-3.3.7-dist/css/bootstrap.min.css"/>
    <!--描述：bootstrap的主题式样库-->
    <link rel="stylesheet" type="text/css" href="../statics/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css" />
    <!--描述：bootstrapvalidator的式样库-->
    <link rel="stylesheet" type="text/css" href="../statics/bootstrapvalidator/css/bootstrapValidator.min.css" />






    <% String basePath = ((HttpServletRequest) request).getContextPath();%>
</head>
<body>
<img src="../statics/images/facelogo.png">
<div class="container">
    <div class="form-horizontal col-md-offset-4">
        <h3 class="text-info">用户登录</h3>
        <div class="col-md-6">
            <form id="loginform"  method="post">
                <div class="form-group">
                    <input class="form-control" type="text" placeholder="请输入用户名" autofocus="autofocus" name="userName"/>
                </div>
                <div class="form-group">
                    <input class="form-control" type="password" placeholder="请输入密码" name="password"/></div>
                <div class="form-group">
                    <label hidden id="error" class="control-label text-danger"></label>
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-info btn-block">登录</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
<script src="../statics/js/jquery.min.js"></script>
<script src="../statics/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="../statics/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script src="../statics/js/login.js?id=1"></script>
</html>
