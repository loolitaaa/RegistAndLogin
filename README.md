# RegistAndLogin

## V1.0    实现了基本的注册和登录

## 数据库表结构:
### 1. user:
	id int primary key auto_increment
	username varchar[50]
	password varchar[50]
	email varchar[50]
      id=1 存放了一个FLAG_USER(1,'1','1','1')
### 2. user_info:

## regist
### 1. 用户名:
	查询数据库是否已经占用
	格式:英文汉字数字下划线
### 2. 密码:   
	两次密码是否一致
	密码是否符合格式
	密码MD5加密
### 3. 邮箱: 
	是否符合格式
	是否被占用

### 4. 验证码:
## login
### 1. 用户名/邮箱登录
### 2. 密码
### 3. 密码找回/密码修改
### 4. 登陆成功跳转主页,登录失败保留原页面,保留用户名密码

## index
