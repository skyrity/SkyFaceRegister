<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--导航部分-->
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


            <ul class="nav navbar-nav">
                <li class="dropdown" id="faceManage">
                    <a class="dropdown-toggle" data-toggle="dropdown">人脸管理 <span class="caret"></span> </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="approvefaces.jsp" >待审核信息</a>
                        </li>
                        <li>
                            <a href="getfaces.jsp" >注册信息</a>
                        </li>
                    </ul>
                </li>
            </ul>



            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a id="logout">当前用户:<span name="username"></span> <em>| ${userName} </em> 注销
                    </a>
                </li>
                <li>
                    <a data-toggle="modal" href="" data-target="#repwdModal">修改密码</a>
                </li>
            </ul>

            <!--修改密码对话框-->
            <div id="repwdModal" tabindex="-1" class="modal fade">
                <div class="modal-dialog">
                    <div class="modal-content">
                       <jsp:include page="repwdmodal.jsp"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</nav>



