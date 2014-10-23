<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="task.title"/></title>
    <meta name="menu" content="UserMenu"/>
</head>

<body class="signup"/>

<div class="col-sm-2">
    <h2><fmt:message key="task.add.heading"/></h2>

    <p><fmt:message key="task.add.message"/></p>
</div>
<div class="col-sm-7">
    <spring:bind path="task.*">
        <c:if test="${not empty status.errorMessages}">
            <div class="alert alert-danger alert-dismissable">
                <a href="#" data-dismiss="alert" class="close">&times;</a>
                <c:forEach var="error" items="${status.errorMessages}">
                    <c:out value="${error}" escapeXml="false"/><br/>
                </c:forEach>
            </div>
        </c:if>
    </spring:bind>

    <form:form commandName="task" method="post" action="tasks" id="taskForm" autocomplete="off"
               cssClass="well" onsubmit="return validateTasks(this)">
        <form:hidden path="id"/>
        <form:hidden path="version"/>
        <input type="hidden" name="from" value="<c:out value="${param.from}"/>"/>

        <spring:bind path="task.description">
            <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
        <appfuse:label styleClass="control-label" key="task.description"/>
        <form:input cssClass="form-control" path="description" id="description" autofocus="true"/>
        <form:errors path="description" cssClass="help-block"/>
        </div>

        <div class="form-group">
            <label for="action.description" class="control-label"><fmt:message key="task.action"/></label>
            <select id="action.description" name="action.description" multiple="false" class="form-control">
                <c:forEach items="${availableActions}" var="action">
                    <option value="${action.value}" ${fn:contains(task.action, action.label) ? 'selected' : ''}>${action.label}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
                <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
            </button>
            <button type="submit" class="btn btn-default" name="cancel" onclick="bCancel=true">
                <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
            </button>
        </div>
    </form:form>
</div>

<c:set var="scripts" scope="request">
    <v:javascript formName="task" staticJavascript="false"/>
    <script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
</c:set>