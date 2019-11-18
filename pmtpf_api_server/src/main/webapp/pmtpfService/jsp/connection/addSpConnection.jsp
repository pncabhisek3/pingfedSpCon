<%@include file="/pmtpfService/jsp/head.jsp"%>
<head>
<title>Add Sp-Conn</title>

<script type="text/javascript" src="<%= rootPath%>/pmtpfService/js/constants/model.js"></script>
<script type="text/javascript" src="<%= rootPath%>/pmtpfService/js/validation/customValidator.js"></script>

<script type="text/javascript" src="<%=rootPath%>/pmtpfService/js/components/fileupload/fileuploadComponent.js"></script>
<script type="text/javascript" src="<%= rootPath%>/pmtpfService/js/components/connection/addSpConnectionComponent.js"></script>
</head>
<body>
	<div id="_target">ADD Connection Component renders here...</div>

	<script type="text/javascript">
		var addSpConnectionInstace = new SpConnectionComponent({
			target: "#_target",
		});
	</script>
</body>
