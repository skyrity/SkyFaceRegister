<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form name="repwdform" id="repwdform">

    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">
            <span>&times;</span>
        </button>
        <h4 class="modal-title" id="modal_title">修改口令</h4>
    </div>
    <!--
    作者：macai@skyrity.com
    时间：2017-04-06
    描述：对话框的内容
-->
    <div class="modal-body">
        <!-- 错误提示框-->
        <div class="alert alert-danger hide" id="error_reppwd">
            <a href="#" class="close" data-dismiss="alert">
                &times;
            </a>
            <strong>错误！</strong><span></span>
        </div>
        <div class="form-group">

            <label >原始密码</label>
            <input type="password" name="oldPwd" class="form-control" />
        </div>
        <div class="form-group">

            <label >新密码</label>
            <input type="password" name="newPwd" class="form-control" />
        </div>
        <div class="form-group">

            <label >确认密码</label>
            <input type="password" name="confirmPwd" class="form-control" />
        </div>
        <!--<div class="form-group">
            <label hidden class="control-label text-danger modifypassword_error"></label>
        </div>-->

    </div>
    <!--
    作者：macai@skyrity.com
    时间：2017-04-06
    描述：对话框的尾部
-->
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭(ESC)</button>
        <button type="button" class="btn btn-success" id="btnRepwd">修改</button>
    </div>
</form>
