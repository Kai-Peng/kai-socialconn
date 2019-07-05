<%--
  Created by IntelliJ IDEA.
  User: 72429
  Date: 2018/12/13
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>购买产品测试</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<script type="text/javascript">
    for (var i=1; i<=5; i++) {
        var params = {
            userId: 1,
            productId: 1,
            quantity: 1
        };
        // 通过POST请求后端，这里的JavaScript会采用异步请求
        $.post("./purchase", params, function (result) {
            alert(result.success);
        });
    }
</script>
<body>
    <h1>抢购产品测试</h1>
</body>
</html>
