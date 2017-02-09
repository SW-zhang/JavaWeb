<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.framework.listener.SessionCounterListener"%>
<%@page import="com.framework.listener.SessionCounterListener.Counter"%>
<%@page import="com.framework.context.ContextHolder" %>
<%@page import="org.hibernate.stat.SecondLevelCacheStatistics"%>
<%@page import="org.hibernate.stat.Statistics" %>
<%@page import="org.hibernate.SessionFactory" %>
<%@page import="java.util.Date" %>
<%@page import="java.text.DateFormat" %>
<%@page import="java.text.SimpleDateFormat" %>
<jsp:directive.page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" session="false"/>
<%!
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

%>
<%
	SessionFactory sessionFactory = ContextHolder.getBean(SessionFactory.class);
	Statistics statistics = sessionFactory.getStatistics();
	String evictSecondLevelCache = request.getParameter("evictSecondLevelCache");
	if (evictSecondLevelCache!=null&&evictSecondLevelCache.equals("true")) {
		sessionFactory.getCache().evictEntityRegions();
		response.sendRedirect(request.getRequestURI());
	}
	Counter counter = (Counter)application.getAttribute(SessionCounterListener.KEY_COUNTER);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Refresh" content="60;" />
<link rel="stylesheet" href="../css/st.css" />
<title>Hibernate Statistics</title>
</head>
<body>
<center>
<div class="main">
<div class="title"><span>Hibernate Statistics</span></div>
<div class="content">
<table class="data" cellpadding="1" cellspacing="1">
	<caption>Hibernate Statistics</caption>
	<tbody>
		<tr>
			<th>Connect:</th>
			<td><%=statistics.getConnectCount()%></td>
			<th>Prepare Statement:</th>
			<td><%=statistics.getPrepareStatementCount()%></td>
			<th>Close Statement:</th>
			<td><%=statistics.getCloseStatementCount()%></td>
			<th>Session Open:</th>
			<td><%=statistics.getSessionOpenCount()%></td>
			<th>Session Close:</th>
			<td><%=statistics.getSessionCloseCount()%></td>
		</tr>
		<tr>
			<th>Start Time:</th>
			<td title="<%=dateFormat.format(new Date(statistics.getStartTime()))%>"><%=statistics.getStartTime()%></td>
			<th>Flush:</th>
			<td><%=statistics.getFlushCount()%></td>
			<th>Transaction:</th>
			<td><%=statistics.getTransactionCount()%></td>
			<th>Successful Transaction:</th>
			<td><%=statistics.getSuccessfulTransactionCount()%></td>
			<th>Optimistic Failure:</th>
			<td><%=statistics.getOptimisticFailureCount()%></td>
		</tr>
		<tr>
			<th>Collection Fetch:</th>
			<td><%=statistics.getCollectionFetchCount()%></td>
			<th>Collection Load:</th>
			<td><%=statistics.getCollectionLoadCount()%></td>
			<th>Collection Recreate:</th>
			<td><%=statistics.getCollectionRecreateCount()%></td>
			<th>Collection Remove:</th>
			<td><%=statistics.getCollectionRemoveCount()%></td>
			<th>Collection Update:</th>
			<td><%=statistics.getCollectionUpdateCount()%></td>
		</tr>
		<tr>
			<th>Entity Fetch:</th>
			<td><%=statistics.getEntityFetchCount()%></td>
			<th>Entity Load:</th>
			<td><%=statistics.getEntityLoadCount()%></td>
			<th>Entity Insert:</th>
			<td><%=statistics.getEntityInsertCount()%></td>
			<th>Entity Update:</th>
			<td><%=statistics.getEntityUpdateCount()%></td>
			<th>Entity Delete:</th>
			<td><%=statistics.getEntityDeleteCount()%></td>
		</tr>
		<tr>
			<th>Query Cache Hit:</th>
			<td><%=statistics.getQueryCacheHitCount()%></td>
			<th>Query Cache Miss:</th>
			<td><%=statistics.getQueryCacheMissCount()%></td>
			<th>Query Cache Put:</th>
			<td><%=statistics.getQueryCachePutCount()%></td>
			<th>Query Execution:</th>
			<td><%=statistics.getQueryExecutionCount()%></td>
			<th>Query Execution Max Time:</th>
			<td title="<%=statistics.getQueryExecutionMaxTimeQueryString()%>"><%=statistics.getQueryExecutionMaxTime()%></td>
		</tr>
		<tr>
			<th>Collection Role:</th>
			<td><%=statistics.getCollectionRoleNames().length%></td>
			<th>Entity Names:</th>
			<td><%=statistics.getEntityNames().length%></td>
			<th>Queries:</th>
			<td><%=statistics.getQueries().length%></td>
			<th>SecondLevel CacheRegion Names:</th>
			<td><%=statistics.getSecondLevelCacheRegionNames().length%></td>
			<th></th>
			<td></td>
		</tr>
		<tr>
			<th style="text-align: center;" colspan="10">SecondLevelCacheStatistics <a href="?evictSecondLevelCache=true" style="color: red;font-weight: bold;">Evict</a></th>
		</tr>
		<tr>
			<% long hit = statistics.getSecondLevelCacheHitCount();
			   long miss = statistics.getSecondLevelCacheMissCount();
			   long put = statistics.getSecondLevelCachePutCount();
			%>
			<th>Hit%:</th>
			<td><%=hit+miss!=0?hit*100/(hit+miss):0%>%</td>
			<th>SecondLevelCacheHit:</th>
			<td><%=hit%></td>
			<th>SecondLevelCacheMiss:</th>
			<td><%=miss%></td>
			<th>SecondLevelCachePut:</th>
			<td><%=put%></td>
			<th></th>
			<td></td>
		</tr>
		<% String[] names = statistics.getSecondLevelCacheRegionNames();
			for (String regionName:names) {
				SecondLevelCacheStatistics slCacheStatistics = statistics.getSecondLevelCacheStatistics(regionName);
				hit = slCacheStatistics.getHitCount();
				miss = slCacheStatistics.getMissCount();
				put = slCacheStatistics.getPutCount();
		%>
		<tr>
			<th><%=regionName%></th>
			<td><%=hit+miss!=0?hit*100/(hit+miss):0%>%</td>
			<th>Hit:</th>
			<td><%=hit%></td>
			<th>Miss:</th>
			<td><%=miss%></td>
			<th>Put:</th>
			<td><%=put%></td>
			<th></th>
			<td></td>
		</tr>
		<%} %>
	</tbody>
</table>
<table class="data" cellpadding="1" cellspacing="1">
	<caption>Session Info</caption>
	<tr>
		<th width="100">Online Count:</th>
		<td width="60"><%=counter.alive%></td>
		<th width="100">Logon Count:</th>
		<td width="60"><%=counter.online%></td>
		<td>&nbsp;</td>
	</tr>
</table>
<div class="gray_bottom"></div>
<div class="blue_bottom"></div>
</div>
</div>
</center>
</body>
</html>