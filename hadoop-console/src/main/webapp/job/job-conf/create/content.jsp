<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="o" %>
<%@ taglib tagdir="/WEB-INF/tags/gaoshin" prefix="g" %>

<%
	String jobId = request.getParameter("jobId");
	String url = "/ws/v1/job/details?format=object&var=job&id=" + jobId;
%>
<jsp:include page="<%=url %>"></jsp:include>

<div class="page-header">Add Job Configuration for <g:job-link job="${job}"/></div>

<form method="post" onSubmit="return createJobConf();">
	<table>
		<o:text-input-tr label="Key" id="name" ></o:text-input-tr>
		<o:textarea-tr label="Value" id="value" style="width:660px;height:400px;"></o:textarea-tr>
		<o:text-input-tr label="Is Password?" id="password"></o:text-input-tr>
		<o:submit-tr value="Add" cancel="window.history.back()"></o:submit-tr>
	</table>
</form>

<script type="text/javascript">
function createJobConf() {
	var req = {
		name: $("#name").val(),
		value: $("#value").val(),
		password: $("#password").val(),
		ownerId: "<%=jobId%>"
	};
	
    var json = JSON.stringify(req);
    $.ajax({
            url : base + "/ws/v1/job/job-conf/create",
            type : "POST",
            data : json,
            contentType : "application/json; charset=utf-8",
            dataType : "json",
            complete : function(transport) {
                    if (transport.status == 200) {
                            window.location = base + "/job/details/index.jsp.oo?id=<%=jobId%>";
                    } else {
                            alert('Error: ' + transport.status + ", "
                                            + transport.responseText);
                    }
            }
    });

    return false;
}
</script>