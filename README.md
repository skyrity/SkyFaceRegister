# 微信小程序人脸注册系统开发方案说明

## 一、 项目功能要求
1. 用户登陆云服务器后，可通过微信小程序直接上传人脸注册信息到云服务器；
2. 服务器管理系统登陆云服务器后，可从云端获取人脸注册信息，同时存储并开通对应门禁设备；
3. PC端登陆后，可浏览云服务器人脸注册请求信息，并对注册信息进行在线审核。



## 二、数据库结构说明

**Face_Register:人脸注册信息表 **

| 列名      | 数据类型        | IsNULL | 说明                                                         | 备注 |
| --------- | --------------- | ------ | ------------------------------------------------------------ | ---- |
| ID        | BigInt          | No     | 主键-可自增                                                  |      |
| Name      | Nvarchar（30）  | No     | 访客姓名                                                     |      |
| OpenId    | Nvarchar（200） | No     | 小程序用户唯一标志                                           |      |
| TelNo     | Nvarchar（20）  | No     | 访客手机号                                                   |      |
| ApplyTime | DateTime        | No     | 申请时间                                                     |      |
| State     | Int             | No     | 申请状态：0-未处理；1-已审核；2-已处理；3-审核不通过；-1-全部获取 |      |
| ImgUrl    | varchar（200）  | No     | 人脸图片地址                                                 |      |
| CardNo    | BigInt          | Yes    | 访客卡号，默认截取手机后9位                                  |      |

**Face_System:人脸系统配置表**

| 列名     | 数据类型 | IsNULL | 说明                     | 备注 |
| -------- | -------- | ------ | ------------------------ | ---- |
| ID       | BigInt   | No     | 主键-可自增              |      |
| State    | Bit      | No     | 是否需要审核：0-否；1-是 |      |
| userName | nvarchar | 100    | 用户名称                 |      |
| password | nvarchar | 20     | 密码。MD5加密            |      |



## 三、接口说明

  ### 3.1 小程序登陆服务器接口规范



**接口地址：https://${host}/SkyFaceRegister/wxlogin.do **

**输入参数：** 

```
code:”微信小程序用户登录凭证”,

encryptedData:”用户信息加密数据”,

iv:”加密算法初始向量”

```



**返回参数：**

```
Data:{

“result_code”:”0”,               //返回码

”result_msg”:”返回信息”,          //信息描述

“result_time”:”执行时间”,         //登陆时间 

“result_data”: {

“accessToken”:”访问令牌”

}

}
```


![wxLogin](https://raw.githubusercontent.com/skyrity/SkyFaceRegister/master/src/main/doc/images/wxLogin.png)

### 3.2 微信小程序人脸信息提交接口规范



**接口地址：https://${host}/SkyFaceRegister/register.do **

**输入参数：** 

```
accessToken:”访问令牌”,

fileStream:”图片二进制流数据”,

name:”用户名”,

telNo:”用户手机号”

```



**返回参数：**

```
Data:{

“result_code”:”0”,				   //返回码

”result_msg”:”返回信息”,			//信息描述

“result_time”:”执行时间”			//申请时间 

“result_data”: {

“imgUrl”:”图片下载地址”,

  }
  
}

```


![register](https://raw.githubusercontent.com/skyrity/SkyFaceRegister/master/src/main/doc/images/register.png)



### 3.3 PC端登陆服务器接口规范

**接口地址：https://${host}/SkyFaceRegister/login.do **

**输入参数：** 

```
userName:”用户名称”,

password:”用户密码”

```



**返回参数：**

```
Data:{

“result_code”:”0”,				   //返回码

”result_msg”:”返回信息”,			//信息描述

“result_time”:”执行时间”			//登陆时间 

“result_data”: {

“accessToken”:”访问令牌”      //1个小时有效
}

}


```

![login](https://raw.githubusercontent.com/skyrity/SkyFaceRegister/master/src/main/doc/images/login.png)



### 3.4 人脸注册信息获取接口规范

**接口地址：https://${host}/SkyFaceRegister/getfaces.do **

**输入参数：** 

```
accessToken:”访问令牌”,

state:”注册状态”,

fields:”查询字段（名称或电话）”，

pageSize:”页大小默认20”

pageNum:”第几页，默认第1页” 


```



**返回参数：**

```
Data:{

“result_code”:”0”,					//返回码

”result_msg”:”返回信息”,			//信息描述

“result_time”:”执行时间”			//获取时间 

“result_data”:{                   //返回数据

   “currentPage”:1,               //当前页号
   
   “pageSize”:30,                 //页记录大小
   
   “totalPage”:1,                 //总页数
   
   “totalRecord”:1,               //总记录数
   
   “dataList”:[                   //记录数组
   
{ “id”:”记录ID”,

“name”:”访客名”,

“telNo”:”访客手机号”,

“applyTime”:”申请时间”, 

“imgUrl”:”图片下载地址”,

“cardNo”:”卡号”,

“state”:”处理状态”

                     },
...]
}
}



```



![getfaces](https://raw.githubusercontent.com/skyrity/SkyFaceRegister/master/src/main/doc/images/getfaces.png)

### 3.5 注册信息审核提交接口规范

**接口地址：https://${host}/SkyFaceRegister/approve.do**

**输入参数：** 

```
accessToken:”访问令牌”,

ids:”记录ID，多个ID，中间用”,”分隔”,

isPass:”是否通过0=拒绝，1=通过，2=已处理”

```



**返回参数：**

```
Data:{

“result_code”:”0”,					//返回码

”result_msg”:”返回信息”,			//信息描述

“result_time”:”执行时间”			//执行时间 

“result_data”:””

}


```


![approve](https://raw.githubusercontent.com/skyrity/SkyFaceRegister/master/src/main/doc/images/approve.png)

### 3.6 小程序注销服务器接口规范

**接口地址：https://${host}/SkyFaceRegister/logout.do**
**输入参数： **
```
accessToken:”访问令牌”,
```
**返回参数：**
```
Data:{
“result_code”:”0”,					//返回码

”result_msg”:”返回信息”,			//信息描述

“result_time”:”执行时间”,			//登陆时间 

“result_data”: {}
}
```
### 3.7 修改用户密码接口规范

**接口地址：https://${host}/SkyFaceRegister/editPassword.do**
**输入参数： **

```
accessToken:”访问令牌”,
oldPassword:”旧密码”
newPassword:“新密码”
```
**返回参数：**

```
Data:{
“result_code”:”0”,					//返回码
”result_msg”:”返回信息”,			//信息描述
“result_time”:”执行时间”,			//登陆时间 
“result_data”: {}
}
```
![editpassword](https://raw.githubusercontent.com/skyrity/SkyFaceRegister/master/src/main/doc/images/editpassword.png)



## 四、逻辑访问流程图示

![流程图](https://raw.githubusercontent.com/skyrity/SkyFaceRegister/master/src/main/doc/images/flow.png)

## 五、云服务器反馈code信息说明

| 错误码 | 信息描述                   |
| ------ | -------------------------- |
| 0      | 操作数据成功               |
| -1     | 用户未登录                 |
| -2     | 获取数据失败               |
| -3     | 参数输入错误               |
| -4     | 用户登录失败               |
| -5     | 数据异常错误               |
| -6     | 获得密码键值错误           |
| -7     | 数据解密错误               |
| -8     | 用户已注册，请勿重复注册   |
| -9     | 用户审核失败！             |
| -10    | 修改密码失败！             |
| -11    | 旧密码不符，修改密码失败！ |