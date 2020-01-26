<%--
  Created by IntelliJ IDEA.
  User: VULCAN
  Date: 2020-1-25
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String accessToken= (String) session.getAttribute("accessToken");
    if(accessToken==null ){
        String basePath=((HttpServletRequest) request).getContextPath();
        request.getRequestDispatcher(basePath+"/veiws/login.jsp").forward(request, response);
        return;
    }
%>


<html>
<head>
    <title>获得注册人脸</title>
    <!--描述：设置页面的缩放式样-->
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <!--描述：bootstrap的核心式样库-->
    <link rel="stylesheet" type="text/css" href="../statics/bootstrap-3.3.7-dist/css/bootstrap.min.css"/>
    <!--描述：bootstrap的主题式样库-->
    <link rel="stylesheet" type="text/css" href="../statics/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css" />
    <!--描述：bootstrapvalidator的式样库-->
    <link rel="stylesheet" type="text/css" href="../statics/bootstrapvalidator/css/bootstrapValidator.min.css" />
    <link rel="stylesheet" type="text/css" href="../statics/css/main.css">

    <script>
        var $accessToken="<%=accessToken%>"
    </script>
</head>
<body>

<!--引入网页头部 -->
<jsp:include page="header.jsp"/>
<!--内容 -->
<div class="container-fluid main">
    <!--标题 -->
    <div class="panel panel-primary">
        <div class="panel-heading">注册人脸信息</div>
    </div>

    <!-- 搜索 -->
    <jsp:include page="search.jsp"/>

    <!-- 错误提示框-->
    <div class="alert alert-danger hide" id="error_PutIn">
        <a href="#" class="close" data-dismiss="alert">
            &times;
        </a>
        <strong>错误！</strong><span></span>
    </div>

    <!--表格内容 -->
    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>编号</th>
                <th>照片</th>
                <th>姓名</th>
                <th>电话号码</th>
                <th>申请时间</th>
                <th>状态</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>

    <!--分页-->

    <!--引入分页paginator组件 -->
    <div id="paginator"></div>
    <!--引入分页paginator组件 -->
    <div class="row">
        <div class="col-md-9 text-center">
            <ul id="pagination" style="margin: 0;padding: 0;">
            </ul>
        </div>
        <div class="col-md-3">
            <!--页号 -->
            <form class="form-inline" action="#" method="get" id="select_pagerno">
                <span class="cl_recordtext"></span>
                <span>每页显示：</span>
                <select class="form-control input-sm" id="select_pageSize" name="select_pageSize">
                    <option value="20">20</option>
                    <option value="20">40</option>
                    <option value="20">80</option>
                </select>
            </form>
        </div>
    </div>
</div>

<!--引入网页尾部 -->
<jsp:include page="footer.jsp"/>

<script src="../statics/js/jquery.min.js" > </script>
<script src="../statics/bootstrap-3.3.7-dist/js/bootstrap.min.js" ></script>
<script src="../statics/bootstrap-paginator/bootstrap-paginator.min.js"></script>
<script src="../statics/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script src="../statics/js/header.js?id=1"></script>
<script src="../statics/js/utils.js?id=1"> </script>
<script src="../statics/js/getfaces.js?id=1" ></script>

</body>
</html>