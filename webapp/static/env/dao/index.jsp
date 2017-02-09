<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.framework.dao.GenericDAO"%>
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
	GenericDAOHibernateStatics daoStatics = (GenericDAOHibernateStatics)ContextHolder.getBean(GenericDAOHibernateStatics.class);
	GenericDaoStatistics statistics = daoStatics.getStatistics();
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
			<th>Method</th>
			<th>Count</th>
			<th>Cost</th>
			<th>Avg</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><a href="details.jsp?method=list">List</a></td>
			<td><%=statistics.getListCost().getCount()%></td>
			<td><%=statistics.getListCost().getCost()%></td>
			<td><%=statistics.getListCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=Get">Get</a></td>
			<td><%=statistics.getGetCost().getCount()%></td>
			<td><%=statistics.getGetCost().getCost()%></td>
			<td><%=statistics.getGetCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=Save">Save</a></td>
			<td><%=statistics.getSaveCost().getCount()%></td>
			<td><%=statistics.getSaveCost().getCost()%></td>
			<td><%=statistics.getSaveCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=Delete">Delete</a></td>
			<td><%=statistics.getDeleteCost().getCount()%></td>
			<td><%=statistics.getDeleteCost().getCost()%></td>
			<td><%=statistics.getDeleteCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=Update">Update</a></td>
			<td><%=statistics.getUpdateCost().getCount()%></td>
			<td><%=statistics.getUpdateCost().getCost()%></td>
			<td><%=statistics.getUpdateCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=SaveOrUpdate">SaveOrUpdate</a></td>
			<td><%=statistics.getSaveOrUpdateCost().getCount()%></td>
			<td><%=statistics.getSaveOrUpdateCost().getCost()%></td>
			<td><%=statistics.getSaveOrUpdateCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=Count">Count</a></td>
			<td><%=statistics.getCountCost().getCount()%></td>
			<td><%=statistics.getCountCost().getCost()%></td>
			<td><%=statistics.getCountCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=Page">Page</a></td>
			<td><%=statistics.getPageCost().getCount()%></td>
			<td><%=statistics.getPageCost().getCost()%></td>
			<td><%=statistics.getPageCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=UniqueResultHql">UniqueResultHql</a></td>
			<td><%=statistics.getUniqueResultHqlCost().getCount()%></td>
			<td><%=statistics.getUniqueResultHqlCost().getCost()%></td>
			<td><%=statistics.getUniqueResultHqlCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=UniqueResultHqlClass">UniqueResultHqlClass</a></td>
			<td><%=statistics.getUniqueResultHqlClassCost().getCount()%></td>
			<td><%=statistics.getUniqueResultHqlClassCost().getCost()%></td>
			<td><%=statistics.getUniqueResultHqlClassCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=UniqueResultSql">UniqueResultSql</a></td>
			<td><%=statistics.getUniqueResultSqlCost().getCount()%></td>
			<td><%=statistics.getUniqueResultSqlCost().getCost()%></td>
			<td><%=statistics.getUniqueResultSqlCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=UniqueResultSqlClass">UniqueResultSqlClass</a></td>
			<td><%=statistics.getUniqueResultSqlClassCost().getCount()%></td>
			<td><%=statistics.getUniqueResultSqlClassCost().getCost()%></td>
			<td><%=statistics.getUniqueResultSqlClassCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=Criteria">Criteria</a></td>
			<td><%=statistics.getCriteriaCost().getCount()%></td>
			<td><%=statistics.getCriteriaCost().getCost()%></td>
			<td><%=statistics.getCriteriaCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=Callback">Callback</a></td>
			<td><%=statistics.getCallbackCost().getCount()%></td>
			<td><%=statistics.getCallbackCost().getCost()%></td>
			<td><%=statistics.getCallbackCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=TopResultHql">TopResultHql</a></td>
			<td><%=statistics.getTopResultHqlCost().getCount()%></td>
			<td><%=statistics.getTopResultHqlCost().getCost()%></td>
			<td><%=statistics.getTopResultHqlCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=TopResultHqlClass">TopResultHqlClass</a></td>
			<td><%=statistics.getTopResultHqlClassCost().getCount()%></td>
			<td><%=statistics.getTopResultHqlClassCost().getCost()%></td>
			<td><%=statistics.getTopResultHqlClassCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=TopResultSql">TopResultSql</a></td>
			<td><%=statistics.getTopResultSqlCost().getCount()%></td>
			<td><%=statistics.getTopResultSqlCost().getCost()%></td>
			<td><%=statistics.getTopResultSqlCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=TopResultSqlClass">TopResultSqlClass</a></td>
			<td><%=statistics.getTopResultSqlClassCost().getCount()%></td>
			<td><%=statistics.getTopResultSqlClassCost().getCost()%></td>
			<td><%=statistics.getTopResultSqlClassCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=Hql">Hql</a></td>
			<td><%=statistics.getHqlCost().getCount()%></td>
			<td><%=statistics.getHqlCost().getCost()%></td>
			<td><%=statistics.getHqlCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=HqlClass">HqlClass</a></td>
			<td><%=statistics.getHqlClassCost().getCount()%></td>
			<td><%=statistics.getHqlClassCost().getCost()%></td>
			<td><%=statistics.getHqlClassCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=Sql">Sql</a></td>
			<td><%=statistics.getSqlCost().getCount()%></td>
			<td><%=statistics.getSqlCost().getCost()%></td>
			<td><%=statistics.getSqlCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=SqlClass">SqlClass</a></td>
			<td><%=statistics.getSqlClassCost().getCount()%></td>
			<td><%=statistics.getSqlClassCost().getCost()%></td>
			<td><%=statistics.getSqlClassCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=Iterate">Iterate</a></td>
			<td><%=statistics.getIterateCost().getCount()%></td>
			<td><%=statistics.getIterateCost().getCost()%></td>
			<td><%=statistics.getIterateCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=CloseIterator">CloseIterator</a></td>
			<td><%=statistics.getCloseIteratorCost().getCount()%></td>
			<td><%=statistics.getCloseIteratorCost().getCost()%></td>
			<td><%=statistics.getCloseIteratorCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=BulkUpdate">BulkUpdate</a></td>
			<td><%=statistics.getBulkUpdateCost().getCount()%></td>
			<td><%=statistics.getBulkUpdateCost().getCost()%></td>
			<td><%=statistics.getBulkUpdateCost().avg()%></td>
		</tr>
		<tr>
			<td><a href="details.jsp?method=BulkUpdateSql">BulkUpdateSql</a></td>
			<td><%=statistics.getBulkUpdateSqlCost().getCount()%></td>
			<td><%=statistics.getBulkUpdateSqlCost().getCost()%></td>
			<td><%=statistics.getBulkUpdateSqlCost().avg()%></td>
		</tr>
		
	</tbody>
</table>
<div class="gray_bottom"></div>
<div class="blue_bottom"></div>
</div>
</div>
</center>
</body>
</html>