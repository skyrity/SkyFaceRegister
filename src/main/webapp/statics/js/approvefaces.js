/**
 * Created by macai on 2020-1-14.
 */
$(function() {

    //点击页号事件
    $("#select_pageSize").change(function() {
        loadTable({
            fields: $("#search_field").val(),
            pageNum: 1,
            pageSize: $("#select_pageSize").val()

        });
    });
    // 搜索按钮
    $("#btnsearch").click(function() {
        loadTable({
            fields: $("#search_field").val(),
            pageNum: 1,
            pageSize: $("#select_pageSize").val()
        });

    });
    $("#groupSearchState").hide();

    loadTable({
        fields: $("#search_field").val(),
        pageNum: 1,
        pageSize: $("#select_pageSize").val()
    })

})



//获得表格数据
function loadTable(obj) {
    obj.state=0;
    $("tbody").empty();
    $.ajax({
        type: "post",
        url: "../getfaces.do",
        data: {
            accessToken: $accessToken,
            fields: obj.fields,
            state: obj.state, /*获得待审核的人脸*/
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
               $("<tr> " +
                   "<td>" + record.id + "</td>" +
                   "<td> <img src='" + record.imgUrl  + "'></td>" +
                   "<td>" + record.name + "</td>" +
                   "<td>" + record.telNo + "</td>" +
                   "<td>" + new Date(record.applyTime.time).format("yyyy-MM-dd")  + "</td>" +
                   "<td><a class='btn btn-success' data-id='"+record.id+"'><i class='glyphicon glyphicon-ok'/>通过</a>&nbsp;&nbsp;&nbsp;" +
                   "<a class='btn btn-danger' data-id='"+record.id+"'><i class='glyphicon glyphicon-remove'/>拒绝</a>"+
                   "</td>"+
                   "</tr>").appendTo("tbody");
           };

           //通过按钮
           $("td .btn-success").click(function (e) {
               //alert(e.currentTarget.dataset.id)
               approve({
                 ids:e.currentTarget.dataset.id,
                 isPass:1 //通过
               })

           })
           //拒绝按钮
           $("td .btn-danger").click(function (e) {
               //alert(e.currentTarget.dataset.id)
               approve({
                   ids:e.currentTarget.dataset.id,
                   isPass:0//拒绝
               })
           })

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

    //用户审核
  function approve(obj) {
      $.ajax({
          type: "post",
          url: "../approve.do",
          data: {
              accessToken:$accessToken,
              ids:obj.ids,
              isPass:obj.isPass //通过
          },
          success: function(res) {
              var data = JSON.parse(res);
              console.log("appove.do", res);
              if(data.result_code != 0) {
                  alert(data.result_msg);
              } else {
                  loadTable({
                      fields: $("#search_field").val(),
                      pageNum: 1,
                      pageSize: $("#select_pageSize").val()
                  });
              }
          }
      });

  }






