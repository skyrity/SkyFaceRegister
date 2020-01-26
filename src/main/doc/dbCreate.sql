USE [master]
GO

/****** Object:  Database [SkyRegisterFace]    Script Date: 2020-1-5 19:19:10 ******/
CREATE DATABASE [SkyRegisterFace]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'SkyRegisterFace', FILENAME = N'D:\database\SkyRegisterFace.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'SkyRegisterFace_log', FILENAME = N'D:\database\SkyRegisterFace_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO

ALTER DATABASE [SkyRegisterFace] SET COMPATIBILITY_LEVEL = 120
GO

USE [SkyRegisterFace]
GO

/****** Object:  Table [dbo].[Face_Register]    Script Date: 2020-1-5 19:25:49 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Face_Register](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](30) NULL,
	[openId] [nvarchar](200) NULL,
	[telNo] [nvarchar](20) NULL,
	[applyTime] [date] NULL,
	[state] [int] NULL,
	[imgUrl] [nvarchar](200) NULL,
	[CardNo] [bigint] NULL,
 CONSTRAINT [PK_Face_Regsiter] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


/****** Object:  Table [dbo].[Face_System]    Script Date: 2020-1-5 19:27:34 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Face_System](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[state] [bigint] NULL,
	[userName] nvarchar(100) not null,
	[password] nvarchar(200) not null,
 CONSTRAINT [PK_Face_System] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


/****** Object:  StoredProcedure [dbo].[Page]    Script Date: 2020-1-5 20:05:48 ******/
DROP PROCEDURE [dbo].[Page]
GO

/****** Object:  StoredProcedure [dbo].[Page]    Script Date: 2020-1-5 20:05:48 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO







CREATE PROCEDURE [dbo].[Page]  
@Out_PageCount int output,		--总页数输出  
@Out_ResultCount int output,    --总记录数输出  
@Out_SQL nvarchar(4000) output, --返回数据库SQL查询语句
@In_Table nvarchar(4000),		--查询表名  
@In_Key varchar(50),			--主键  
@In_Fields nvarchar(3000),		--查询字段  
@In_Where nvarchar(3000),		--查询条件  
@In_Order nvarchar(100),		--排序字段  
@In_Begin int,					--开始位置  
@In_PageIndex int,				--当前页数  
@In_PageSize int				--页大小  
AS  
SET NOCOUNT ON  
SET ANSI_WARNINGS ON  
IF @In_PageSize < 0 OR @In_PageIndex < 0  
BEGIN          
RETURN  
END  
DECLARE @new_where1 NVARCHAR(3000)  
DECLARE @new_order1 NVARCHAR(100)  
DECLARE @new_order2 NVARCHAR(100)  
DECLARE @new_field1 NVARCHAR(100)
DECLARE @Sql NVARCHAR(4000)  
DECLARE @SqlCount NVARCHAR(4000)  
DECLARE @Top INT  
DECLARE @Index INT --页数
DECLARE @Size INT --查询数据的大小
DECLARE @Temp NVARCHAR(100)  

/********--筛选位置占时没用，备用**********/
SET @In_Begin=0
--IF(@In_Begin <=0)  
--    SET @In_Begin=0  
--ELSE  
--    SET @In_Begin=@In_Begin-1  

/********--Where语句**********/
IF ISNULL(@In_Where,'') = ''  
    SET @new_where1 = ' '  
ELSE  
    SET @new_where1 = ' WHERE ' + @In_Where  

/********--排序，如果没有就使用默认，有话需要判断排序规则，筛选部分要和总数部分反序**********/
IF ISNULL(@In_Order,'') <> ''   
BEGIN  
	IF PATINDEX ( '%DESC%' , @In_Order ) >0--检查字符串中是否有反序标识符
	BEGIN
		SET @new_order1 = ' ORDER BY ' + Replace(@In_Order,'desc','')  
		SET @new_order1 = @new_order1 + ' ASC'
	END
	ELSE
	BEGIN
		SET @new_order1 = ' ORDER BY ' + Replace(@In_Order,'asc','')  
		SET @new_order1 = @new_order1 + ' DESC'
	END	  
    SET @new_order2 = ' ORDER BY ' + @In_Order  
END  
ELSE  
BEGIN   
    SET @new_order1 = ' ORDER BY RecNum DESC'  
    SET @new_order2 = ' ORDER BY RecNum ASC'  
END 

SET @SqlCount = 'SELECT @Out_ResultCount=COUNT(1),@Out_PageCount=CEILING((COUNT(1)+0.0)/'  
            + CAST(@In_PageSize AS NVARCHAR)+') FROM ' + @In_Table + @new_where1  
EXEC SP_EXECUTESQL @SqlCount,N'@Out_ResultCount INT OUTPUT,@Out_PageCount INT OUTPUT',  
               @Out_ResultCount OUTPUT,@Out_PageCount OUTPUT  


/********--检查是否最后一页的少量数据**********/
--SET @DivNum = (@Out_ResultCount+0.0) % @In_PageSize

/********--如果输入的当前页数大于实际总页数,则把实际总页数赋值给当前页数**********/
SET @Index = CEILING((@Out_ResultCount+0.0) / @In_PageSize)--除不尽的取大的整数
IF @In_PageIndex > @Index      
BEGIN  
    SET @In_PageIndex = 0-- @Index --防止进入死循环，超过的就不显示了
END  

/********--设置查询的条数**********/
IF @In_PageSize*@In_PageIndex > @Out_ResultCount
BEGIN
	SET @Size = (@Out_ResultCount+0.0)- ((@In_PageIndex-1)*@In_PageSize)
END
ELSE
	SET @Size =@In_PageSize

SET @Temp = Replace(Replace(Replace(Replace(@In_Order,' ',''),',',''),'asc',''),'desc','') --替换字符串
IF @Temp <> @In_Key
	SET @new_field1 =@In_Key+','+@Temp
else
	SET @new_field1 =@In_Key

SET @sql = 'select '+ @In_fields +' from ' + @In_Table + ' HBin1 '  
        + ' where '+ @In_Key +' in ('  
		--+' where exists('
        +'select top '+ ltrim(str(@Size)) +' ' + @In_Key + ' from '  
        +'('  
            +'select top ' + ltrim(STR(@In_PageSize * @In_PageIndex + @In_Begin)) + ' ' + @new_field1 + ' FROM '  
        + @In_Table + @new_where1 + @new_order2   
        +') HBin ' + @new_order1  
    +') ' + @new_order2  
--print(@sql)
set @Out_SQL =@sql 
Exec(@sql)  






GO

Insert into Face_System(state,userName,password) values(0,'admin','21232f297a57a5a743894a0e4a801fc3') --MD5('admin')