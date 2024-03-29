<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="task.title"/></title>
    <meta name="menu" content="UserMenu"/>
</head>

<c:if test="${not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<div class="col-sm-10">
    <h2><fmt:message key="task.heading"/></h2>

    <form method="get" action="${ctx}/tasklist" id="searchForm" class="form-inline">
        <div id="search" class="text-right">
        <span class="col-sm-9">
            <input type="text" size="20" name="q" id="query" value="${param.q}"
                   placeholder="<fmt:message key="search.enterTerms"/>" class="form-control input-sm">
        </span>
            <button id="button.search" class="btn btn-default btn-sm" type="submit">
                <i class="icon-search"></i> <fmt:message key="button.search"/>
            </button>
        </div>
    </form>

    <div id="actions" class="btn-group">
        <a class="btn btn-primary" href="<c:url value='/tasks?method=Add&from=list'/>">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>

        <a class="btn btn-default" href="<c:url value='/home'/>">
            <i class="icon-ok"></i> <fmt:message key="button.done"/></a>
    </div>

    <display:table name="tasklist" cellspacing="0" cellpadding="0" requestURI=""
                   defaultsort="1" id="tasks" pagesize="25" class="table table-condensed table-striped table-hover" export="true">

        <display:column property="id" escapeXml="true" sortable="true" titleKey="task.id" style="width: 25%"
                        url="/tasks?from=list" paramId="id" paramProperty="id"/>
        <display:column property="description" escapeXml="true" sortable="true" titleKey="task.description" style="width: 25%" paramId="id" paramProperty="id"/>
        <display:column property="action" escapeXml="true" sortable="true" titleKey="action.description" style="width: 25%"  autolink="true"/>
        <display:column property="usersNames" escapeXml="true" sortable="true" titleKey="task.user" style="width: 25%"  autolink="true"/>

        <display:setProperty name="paging.banner.item_name"><fmt:message key="task.description"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="menu.user.tasks"/></display:setProperty>

        <display:setProperty name="export.excel.filename" value="Task List.xls"/>
        <display:setProperty name="export.csv.filename" value="Task List.csv"/>
        <display:setProperty name="export.pdf.filename" value="Task List.pdf"/>
    </display:table>
</div>
