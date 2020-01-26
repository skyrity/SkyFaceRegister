/**
 * Created by VULCAN on 2020-1-15.
 */

//注销按钮
$("#logout").on("click", function() {
    $.ajax({
        type: "POST",
        url: "../logout.do",
        data:{
          accessToken:$accessToken
        },
        success: function(res) {
            console.log(res);
            var data = JSON.parse(res);
            location.href = "login.jsp";
        }
    });
});

//修改密码
$("#repwdModal").on("shown.bs.modal", function(e) {

    //提交按钮，修改密码
    $("#btnRepwd").on("click", function() {
        var bootstrapValidator = $("#repwdform").data("bootstrapValidator").validate();
        if(bootstrapValidator.isValid()) {
            modifyPassword("#repwdModal");
        }
    });


    fromValidator_repwd("#repwdform");
    $("#repwdform")[0].reset();

});

//输入项校验
function fromValidator_repwd(formname) {
    if($(formname).data('bootstrapValidator')) {
        $(formname).data('bootstrapValidator').destroy();
        $(formname).data('bootstrapValidator', null);
    }
    $(formname).bootstrapValidator({
        message: '值无效',
        feedbackIcons: { /* 输入框不同状态，显示图片的样式 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            oldPwd: {
                validators: {
                    notEmpty: {
                        message: '原始密码不能为空'
                    },
                }
            },
            newPwd: {
                validators: {
                    notEmpty: {
                        message: '新密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: '新密码长度必须在6到30之间'
                    },
                    regexp: { // 匹配规则
                        regexp:/(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{6,30}/,
                        message: '密码中必须包含字母、数字、特称字符，至少8个字符，最多30个字符'
                    }

                }
            },
            confirmPwd: {
                validators: {
                    notEmpty: {
                        message: '确认密码不能为空'
                    },

                    identical: { // 相同
                        field: 'newPwd', // 需要进行比较的input name值
                        message: '两次密码不一致'
                    },

                }
            }
        }
    });
};


//修改密码
function modifyPassword(modal) {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "../modifyPassword.do",
        data: $(modal + " form").serialize(),
        success: function(res) {
            if(window.console && console.log) {
                console.log("modifyPassword.do", res);
            }
            // 成功
            if(res.result_code == 0) {
                $(modal).modal("hide");
                sessionStorage.setItem("isReppwd",1);
            } else {
                $("#error_reppwd span").html(result.result_msg);
                $("#error_reppwd").removeClass("hide");
            }

        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $("#error_reppwd span").html(textStatus);
            $("#error_reppwd").removeClass("hide");
        }

    });
}
