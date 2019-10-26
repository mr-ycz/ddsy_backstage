<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/10/25
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>无权登录</title>
</head>
<body>
<h2 align="center">你无权访问,请联系管理员</h2>
<h2 align="center"><span id="spantime">3</span></h2>
<script type="text/javascript">
    var count = 3;
    var time=document.getElementById("spantime");

    function jump() {
        count--;

        time.innerHTML=count;
        if (count<=0){
            location.href="login.jsp";
        }
    }

    window.setInterval(jump,1000);
</script>
</body>
</html>
