<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- 搜索 -->
<div class="panel panel-default">
    <div class="panel-heading">功能选择</div>
    <div class="panel-body">

        <form class="form form-inline">
            <div class="form-group">
                <label class="sr-only">查询条件</label>
                <div class="input-group ">
                    <span class="input-group-addon">查询条件</span>
                    <input type="text" class="form-control" placeholder="" id="search_field"
                           name="search_field" style="width:500px;" value=""/>
                </div>

            </div>

            <div class="form-group" id="groupSearchState">
                <div class="input-group ">
                    <span class="input-group-addon">状态</span>
                    <select class="form-control" id="search_state">
                        <option value="-1">所有</option>
                        <option value="0">待审核</option>
                        <option value="1">已审核</option>
                        <option value="2">拒绝</option>
                        <option value="3">已处理</option>
                    </select>
                </div>

            </div>

            <div class="form-group">
                <a class="btn btn-success form-control" id="btnsearch"> <i
                        class="glyphicon glyphicon-search"></i>查询 </a>
            </div>
            <div class="form-group" id="btnadd"></div>
            <div class="form-group" id="btnedit"></div>
            <div class="form-group" id="btnprint"></div>
            <div class="form-group" id="btnexport"></div>
        </form>

    </div>
</div>

