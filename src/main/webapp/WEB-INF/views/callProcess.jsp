<%@ page language="java" contentType="text/html; charset=EUC-KR"
		 pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>Insert title here</title>
	<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script>

		$(document).ready(function(){

			$("#clicked").bind('click', function(){
				onAjax();
			});

		});

		function onAjax() {

			var isSim = "0";
			if($("#chk").is(":checked")) {
				isSim = "1";
			}

			var url = '/test/runProcess';
			var data = {"defId":"TESTProcess.process",
				"userId":"1",
				"userName":"test",
				"tenantId":"1",
				"isSim":isSim};
			$.ajax({
				type     :'post',
				url      : url,
				dataType : 'json',
				data     : data,
				async    : false,
				success  : function(data) {
					console.log(data);
				},
				complete : function(data) {

				},
				error    : function(xhr, status, error) {
					console.log(error);
				}
			});
		}

	</script>
</head>
<body>
<input type="checkbox" value="1" name="chk" id="chk"/>
<button id="clicked">click</button>
</body>
</html>