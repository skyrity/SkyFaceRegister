/**
 * Created by VULCAN on 2020-2-10.
 */

//表单验证
function fromValidator(formname) {
    if($(formname).data('bootstrapValidator')) {
       $(formname).data('bootstrapValidator').destroy();
      $(formname).data('bootstrapValidator', null);
    }
    $(formname).bootstrapValidator({
        message: '该值无效',
        feedbackIcons: {
            /* 输入框不同状态，显示图片的样式 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: { // 验证
            username: {
                message: '用户名无效',
                validators: {
                    notEmpty: { // 非空提示
                        message: '用户名不能为空'
                    },
                }
            },
        }
    });

};


//登录
function login() {

    $.ajax({
        type: "POST",
        url: "../login.do",
        data: $("#loginform").serialize(),
        async: true,
        success: function (result) {
            if (window.console && console.log) {
                console.log("login.do", result);
            }
            var data = JSON.parse(result);

            // 成功
            if (data.result_code == 0) {
                //sessionStorage.clear();
                //sessionStorage.setItem("username",data.result_data.username);
                location.href = "approvefaces.jsp";

            } else {
                $("#error").removeAttr("hidden").html(data.result_msg);
                fromValidator("#loginform");
            }


        },

        error: function (XMLHttpRequest, textStatus, errorThrown) {

            $("#error").removeAttr("hidden").html("没有发现请求！");
            fromValidator("#loginform");
        }


    })


};

$(function () {

    $(document).keydown(function (event) {
        var code = event.keyCode;
        if(code ==13){ //这是键盘的enter监听事件
            $("#loginform button").click()
            event.preventDefault();

        }
    })

    fromValidator("#loginform");
    $("#loginform button").on("click", function () {
        // 手动触发验证
        var bootstrapValidator = $("#loginform").data("bootstrapValidator").validate();
        if (bootstrapValidator.isValid()) {
            login();
        }

    });
})


