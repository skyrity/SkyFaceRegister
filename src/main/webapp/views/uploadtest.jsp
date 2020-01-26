<%--
  Created by IntelliJ IDEA.
  User: VULCAN
  Date: 2020-1-6
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传文件</title>
</head>
<body>
   <form action="register.do" enctype="multipart/form-data" method="post" >
       名称：<input type="text" name="name" /> <br />
       电话：<input type="test" name="telNo" /> <br />

       文件： <input type="file" name="file"/> <br />
       <input type="submit" name="submit" value="提交">
   </form>
</body>
</html>
