<head>
<title>head</title>
</head>
<body>
	<% String rootPath = request.getContextPath();%>
	<% String serverName= System.getenv("BLACKBOX_PMTPF_ORIGIN");
		/* if(null == serverName) */
			serverName= request.getServerName();
	%>
	<script type="text/javascript">
		var _pmtpfServer = '<%= "//"+ serverName +":"+request.getServerPort()+ rootPath %>'
		console.log("host::", _pmtpfServer);
	</script>
</body>
