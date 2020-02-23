/**
 * Created by macai on 2020-1-25.
 */
$(function() {

    $(document).keydown(function (event) {
        var code = event.keyCode;
        if(code ==13){ //这是键盘的enter监听事件
            $("#btnsearch").click()
            event.preventDefault();

        }
    })


    //点击页号事件
    $("#select_pageSize").change(function() {
        loadTable({
            fields: $("#search_field").val(),
            state:$("#search_state").val(),
            pageNum: 1,
            pageSize: $("#select_pageSize").val()

        });
    });
    // 搜索按钮
    $("#btnsearch").click(function() {
        loadTable({
            fields: $("#search_field").val(),
            state:$("#search_state").val(),
            pageNum: 1,
            pageSize: $("#select_pageSize").val()
        });

    });


    loadTable({
        fields: $("#search_field").val(),
        state:$("#search_state").val(),
        pageNum: 1,
        pageSize: $("#select_pageSize").val()
    })

})


//获得表格数据
function loadTable(obj) {
    $("tbody").empty();
    $.ajax({
        type: "post",
        url: "../getfaces.do",
        data: {
            accessToken: $accessToken,
            fields: obj.fields,
            state: obj.state, /*获得所有注册人脸*/
            pageSize: obj.pageSize,
            pageNum: obj.pageNum
        },
        success: function (res) {
            var data = JSON.parse(res);
            console.log("getfaces.do", data);
            if (data.result_code != 0) {
                alert(data.result_msg);
            } else {
                handler_getfaces(data);
            }
        }
    });
}


//页面处理
function handler_getfaces(data) {
    if(data && data.result_code == 0 && data.result_data) {
        for(var i = 0; i < data.result_data.dataList.length; i++) {
            var record = data.result_data.dataList[i];
            var sState;
            var clsColor;
            switch (record.state){
                case 0:
                    sState="待审核";
                    clsColor="clsBlue";
                    break;
                case 1:
                    sState="已审核";
                    clsColor="clsGreen";
                    break;
                case 2:
                    sState="已处理";
                    clsColor="clsBlack";
                    break;
                case 3:
                    sState="拒绝";
                    clsColor="clsRed";
                    break;
            }


            $("<tr> " +
                "<td>" + record.id + "</td>" +
                "<td> <img src='" + record.imgUrl  + "'></td>" +
                "<td>" + record.name + "</td>" +
                "<td>" + record.telNo + "</td>" +
                "<td>" + new Date(record.applyTime.time).format("yyyy-MM-dd")  + "</td>" +
                "<td><span class='"+clsColor+"'>"+ sState +"</span></td>"+
                "</tr>").appendTo("tbody");
        };
        // ===============分页控件======================
        hanlder_paginator(data)

    }
}

//分页控件处理
function hanlder_paginator(data) {
    //分页设置
    $(".cl_recordtext").text("总记录数:" + data.result_data.totalRecord);
    var options = {
        currentPage: data.result_data.currentPage, // 当前选择的页数
        totalPages: data.result_data.totalPage, // 总页数
        bootstrapMajorVersion: 3, // 必须设置Bootstrap版本
        size: 'small', // 控件大小
        alignment: "center", // 居中显示

        itemTexts: function(type, page, current) { // 按键显示的内容
            switch(type) {
                case "first":
                    return "首页";
                case "prev":
                    return "前一页";
                case "next":
                    return "下一页";
                case "last":
                    return "最后一页";
                case "page":
                    return "" + page;
            }
        },
        onPageClicked: function(event, originalEvent, type, page) {
            loadTable({
                accessToken: $accessToken,
                fields: $("#search_field").val(),
                state:$("#search_state").val(),
                pageNum: page,
                pageSize: $("#select_pageSize").val()
            });
        }
    };
    if(data.result_data.totalRecord > 0) {
        $("#pagination").bootstrapPaginator(options);
    } else {
        $("#pagination").css("display:none");
    }

}