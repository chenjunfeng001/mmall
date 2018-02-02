<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<body>
	<h2>Hello World!</h2>

	<h3>登陆mall</h3>
	<form name="longForm" action="http://www.imooc.com/mmall/user/login.do" method="post">
		用户名：<input type = "text" name="username"><br>
		密码：<input type = "password" name="password"><br>
		<input type="submit" name="submit" value="登陆">
	</form>

	<h3>springmvc上传文件</h3>
	<form name="form1" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
		<input type="file" name="upload_file" /> 
		<input type="submit" value="springmvc上传文件" />
	</form>


	<h3>富文本图片上传文件</h3>
	<form name="form2" action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
		<input type="file" name="upload_file" /> 
		<input type="submit" value="富文本图片上传文件" />
	</form>

</body>
</html>
