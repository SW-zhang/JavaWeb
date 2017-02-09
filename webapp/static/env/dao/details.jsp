<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.framework.dao.GenericDaoStatistics.Cost"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.lang.reflect.Method"%>
<%@page import="java.beans.PropertyDescriptor"%>
<%@page import="java.beans.Introspector"%>
<%@page import="java.util.Date"%>
<%@page import="com.framework.dao.GenericDaoStatistics"%>
<%@page import="com.framework.context.ContextHolder"%>
<%@page import="com.framework.dao.hibernate.GenericDAOHibernateStatics"%>
<%@page import="java.util.Map"%>
<%@page import="java.text.DateFormat" %>
<%@page import="java.text.SimpleDateFormat" %>
<jsp:directive.page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" session="false"/>
<%!
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

%>
<%
	GenericDAOHibernateStatics daoStatics = ContextHolder.getBean(GenericDAOHibernateStatics.class);
	GenericDaoStatistics statistics = daoStatics.getStatistics();
	
	String method = request.getParameter("method");

	PropertyDescriptor[] descriptors = Introspector.getBeanInfo(GenericDaoStatistics.class).getPropertyDescriptors();
	PropertyDescriptor descriptor = null;
	for (int i=0; i<descriptors.length ; i++) {
		if (method.equalsIgnoreCase(descriptors[i].getDisplayName())) {
			descriptor = descriptors[i];
			break;
		}
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Refresh" content="60;" />
<link rel="stylesheet" href="../css/st.css" />
<script type="text/javascript" src="../jquery/jquery-latest.js"></script>
<script type="text/javascript" src="../jquery/jquery.tablesorter.js"></script>

<script type="text/javascript">
$(document).ready(function() 
	    { 
	        $("table.data").tablesorter( {sortList: [[3,1]]} ); 
	    } 
	);
</script>
<title>Hibernate Statistics</title>
</head>
<body>
<center>
<div class="main">
<div class="title"><span>DAO Statistics</span></div>
<div class="content">
<table class="data" cellpadding="1" cellspacing="1" id="dataTabId">
	<caption>Statistics (Begin from <%=dateFormat.format(new Date(statistics.getStart()))%>)</caption>
	<thead>
		<tr>
			<th>Key</th>
			<th>Count</th>
			<th>Cost</th>
			<th>Avg</th>
		</tr>
	</thead>
	<tbody>
		
		<%	if (descriptor!=null&&descriptor.getReadMethod()!=null) {
			Method getter = descriptor.getReadMethod();
			@SuppressWarnings("unchecked")
			Map<Object, GenericDaoStatistics.Cost> details = (Map<Object, GenericDaoStatistics.Cost>)getter.invoke(statistics);
			Iterator<Map.Entry<Object,GenericDaoStatistics.Cost>> iterator = details.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Object,GenericDaoStatistics.Cost> entry = iterator.next();
				Cost cost = (Cost)entry.getValue();
		%>
		<tr>
			<td><%=entry.getKey()%></td>
			<td><%=cost.getCount()%></td>
			<td><%=cost.getCost()%></td>
			<td><%=cost.avg()%></td>
		</tr>
		<%}} %>
	</tbody>
</table>
<div class="gray_bottom"></div>
<div class="blue_bottom"></div>
</div>
</div>
</center>
</body>
</html>