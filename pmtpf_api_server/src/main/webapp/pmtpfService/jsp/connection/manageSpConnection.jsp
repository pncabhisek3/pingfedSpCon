<%@include file="/pmtpfService/jsp/head.jsp"%>
<head>
	<title>Modify Sp-Conn</title>
	<script type="text/javascript" src="<%= rootPath%>/pmtpfService/js/constants/model.js"></script>
	<script type="text/javascript" src="<%= rootPath%>/pmtpfService/js/validation/customValidator.js"></script>
	<script type="text/javascript" src="<%= rootPath%>/pmtpfService/js/components/connection/addSpConnectionComponent.js"></script>
	<script type="text/javascript" src="<%=rootPath%>/pmtpfService/js/components/fileupload/fileuploadComponent.js"></script>
	<script type="text/javascript" src="<%= rootPath%>/pmtpfService/js/components/connection/manageSpConnectionComponent.js"></script>
</head>
<body>
	<div id="_target">
	</div>
	
	<script type="text/javascript">
		var modifyConnInstance = new ManageSpComponent({
			target: "#_target"
		});
	</script>
</body>
