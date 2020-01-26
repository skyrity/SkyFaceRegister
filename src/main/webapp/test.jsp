<%--
  Created by IntelliJ IDEA.
  User: VULCAN
  Date: 2020-1-14
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!--描述：设置页面的缩放式样-->
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <!--描述：bootstrap的核心式样库-->
    <link rel="stylesheet" type="text/css" href="statics/bootstrap-3.3.7-dist/css/bootstrap.min.css"/>
    <!--描述：bootstrap的主题式样库-->
    <link rel="stylesheet" type="text/css" href="statics/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css" />
    <!--描述：bootstrapvalidator的式样库-->
    <link rel="stylesheet" type="text/css" href="statics/bootstrapvalidator/css/bootstrapValidator.min.css" />


</head>

<body>

<nav class="navbar navbar-inverse " id="top" role="导航">
    <!--role只用于屏幕读取器使用-->
    <div class="container-fluid">
        <!--宽带固定容器-->
        <!--导航头部-->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">
                <!--标题 -->
                <img src="../statics/images/logo.png" style="width: 24px;height: 24px;display: inline-block;" />SkyFace微信小程序人脸注册后台</a>
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#top-navbar">
                <!--菜单 -->
                <span class="sr-only">切换菜单 </span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span>
            </button>
        </div>

        <!--导航主菜单部分-->
        <div class="collapse navbar-collapse" id="top-navbar">
            <a data-toggle="modal" href="" data-target="#repwdModal">修改密码</a>


            <!--
                 作者：macai@skyrity.com
                时间：2017-04-18
                描述：修改密码对话框
            -->
            <div id="repwdModal" tabindex="-1" class="modal fade">
                <div class="modal-dialog" style="z-index: 0;">
                    <div class="modal-content">

                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Modal title</h4>
                        </div>
                        <div class="modal-body">
                            <p>One fine body&hellip;</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary">Save changes</button>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</nav>




     <script src="statics/js/jquery.min.js" > </script>
     <script src="statics/bootstrap-3.3.7-dist/js/bootstrap.min.js" ></script>
     <script src="statics/bootstrap-paginator/bootstrap-paginator.min.js"></script>
     <script src="statics/bootstrapvalidator/js/bootstrapValidator.min.js"></script>

</body>
</html>
