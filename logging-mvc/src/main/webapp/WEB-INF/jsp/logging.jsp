<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page trimDirectiveWhitespaces="true" %>

<spring:url value="/logging/apply" var="applyUrl"/>

<c:set var="dest" value="PostgreSQL supports several methods for logging server messages, including stderr, csvlog and syslog. On Windows, eventlog is also supported. Set this parameter to a list of desired log destinations separated by commas. The default is to log to stderr only. This parameter can only be set in the postgresql.conffile or on the server command line."/>
<c:set var="coll" value="This parameter enables the logging collector, which is a background process that captures log messages sent to stderr and redirects them into log files. This approach is often more useful than logging to syslog, since some types of messages might not appear in syslog output. (One common example is dynamic-linker failure messages; another is error messages produced by scripts such as archive_command.) This parameter can only be set at server start."/>
<c:set var="dir" value="When logging_collector is enabled, this parameter determines the directory n which log files will be created. It can be specified as an absolute path, or relative to the cluster data directory. This parameter can only be set in the postgresql.conf file or on the server command line. The default is pg_log."/>
<c:set var="file" value="When logging_collector is enabled,this parameter sets the file names of the created log files. The value is treated as a strftime pattern, so %-escapes can be used to specify time-varying file names. (Note that if there are any time-zone-dependent %-escapes, the computation is done in the zone specified by log_timezone.) The supported %-escapes are similar to those listed in the Open Group's strftime specification. Note that the system's strftime is not used directly, so platform-specific (nonstandard) extensions do not work. The default is postgresql-%Y-%m-%d_%H%M%S.log."/>
<c:set var="mode" value="On Unix systems this parameter sets the permissions for log files when logging_collector is enabled. (On Microsoft Windows this parameter is ignored.) The parameter value is expected to be a numeric mode specified in the format accepted by the chmod and umask system calls. (To use the customary octal format the number must start with a 0 (zero).) The default permissions are 0600, meaning only the server owner can read or write the log files. The other commonly useful setting is 0640, allowing members of the owner's group to read the file"/>
<c:set var="rotAge" value="When logging_collector is enabled, this parameter determines the directory n which log files will be created. It can be specified as an absolute path, or relative to the cluster data directory. This parameter can only be set in the postgresql.conf file or on the server command line. The default is pg_log."/>
<c:set var="size" value="When logging_collector is enabled, this parameter determines the maximum size of an individual log file. After this many kilobytes have been emitted into a log file, a new log file will be created. Set to zero to disable size-based creation of new log files. This parameter can only be set in the postgresql.conf file or on the server command line."/>
<c:set var="minmsg" value="Controls which message levels are written to the server log. Valid values are DEBUG5, DEBUG4, DEBUG3, DEBUG2, DEBUG1, INFO, NOTICE, WARNING, ERROR, LOG, FATAL, and PANIC. Each level includes all the levels that follow it. The later the level, the fewer messages are sent to the log. The default is WARNING. Note that LOG has a different rank here than in client_min_messages. Only superusers can change this setting."/>
<c:set var="errstate" value="Controls which SQL statements that cause an error condition are recorded in the server log. The current SQL statement is included in the log entry for any message of the specified severity or higher. Valid values are DEBUG5, DEBUG4, DEBUG3, DEBUG2, DEBUG1, INFO, NOTICE, WARNING, ERROR, LOG, FATAL, and PANIC. The default is ERROR, which means statements causing errors, log messages, fatal errors, or panics will be logged. To effectively turn off logging of failing statements, set this parameter to PANIC. Only superusers can change this setting."/>
<c:set var="mindur" value="Causes the duration of each completed statement to be logged if the statement ran for at least the specified number of milliseconds. Setting this to zero prints all statement durations. Minus-one (the default) disables logging statement durations. For example, if you set it to 250ms then all SQL statements that run 250ms or longer will be logged. Enabling this parameter can be helpful in tracking down unoptimized queries in your applications. Only superusers can change this setting"/>
<c:set var="logdur" value="Causes the duration of every completed statement to be logged. The default is off. Only superusers can change this setting. For clients using extended query protocol, durations of the Parse, Bind, and Execute steps are logged independently."/>
<c:set var="pref" value="This is a printf-style string that is output at the beginning of each log line. % characters begin 'escape sequences' that are replaced with status information as outlined below. Unrecognized escapes are ignored. Other characters are copied straight to the log line. Some escapes are only recognized by session processes, and will be treated as empty by background processes such as the main server process. Status information may be aligned either left or right by specifying a numeric literal after the % and before the option. A negative value will cause the status information to be padded on the right with spaces to give it a minimum width, whereas a positive value will pad on the left. Padding can be useful to aid human readability in log files. This parameter can only be set in the postgresql.conf file or on the server command line. The default is an empty string."/>


<form:form class="form-horizontal" modelAttribute="logger" action="${applyUrl}" method="POST">
    <%--<div class = "panel panel-default">--%>
        <%--<div class = "panel-heading">--%>
            <%--<h3 class = "panel-title">--%>
                <%--<b>Application logging</b>--%>
            <%--</h3>--%>
        <%--</div>--%>
        <%--<div class = "panel-body">--%>
    <p>
        <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="true" aria-controls="collapseExample">
            Application logging
        </button>
    </p>
    <div class="collapse" id="collapseExample">
        <div class="card card-block">
    <spring:bind path="logDir">
    <div class="form-group form-group-lg" title="${dir}">
        <form:label path="logDir" class="col-sm-3 control-label">
            <spring:message code="logging.directory"/>
        </form:label>
        <div class="col-sm-5">
            <form:input path="logDir" class="form-control"/>
        </div>
    </div>
    </spring:bind>

    <spring:bind path="logFile">
        <div class="form-group form-group-lg" title="${dir}">
            <form:label path="logFile" class="col-sm-3 control-label">
                <spring:message code="logging.filename"/>
            </form:label>
            <div class="col-sm-5">
                <form:input path="logFile" class="form-control"/>
            </div>
        </div>
    </spring:bind>

    <%--<spring:bind path="rootLevel">--%>
        <%--<div class="form-group form-group-lg" title="${dir}">--%>
            <%--<form:label path="rootLevel" class="col-sm-3 control-label">--%>
                <%--<spring:message code="logging.rootLevel"/>--%>
            <%--</form:label>--%>
            <%--<div class="col-sm-5">--%>
                <%--<form:input path="rootLevel" class="form-control"/>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</spring:bind>--%>
            <spring:bind path="rootLevel">
                <div class="form-group form-group-lg" title="${minmsg}">
                    <form:label path="rootLevel" class="col-sm-3 control-label">
                        <spring:message code="logging.rootLevel"/>
                    </form:label>
                    <div class="col-sm-5">
                        <form:select path="rootLevel" class="form-control">
                            <form:options items="${logLevels2}"/>
                        </form:select>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="pa036Level">
                <div class="form-group form-group-lg" title="${minmsg}">
                    <form:label path="pa036Level" class="col-sm-3 control-label">
                        <spring:message code="logging.pa036Level"/>
                    </form:label>
                    <div class="col-sm-5">
                        <form:select path="pa036Level" class="form-control">
                            <form:options items="${logLevels2}"/>
                        </form:select>
                    </div>
                </div>
            </spring:bind>


            <spring:bind path="springLevel">
            <div class="form-group form-group-lg" title="${minmsg}">
                <form:label path="springLevel" class="col-sm-3 control-label">
                    <spring:message code="logging.springLevel"/>
                </form:label>
                <div class="col-sm-5">
                    <form:select path="springLevel" class="form-control">
                        <form:options items="${logLevels2}"/>
                    </form:select>
                </div>
            </div>
            </spring:bind>

            <spring:bind path="hibernateLevel">
                <div class="form-group form-group-lg" title="${minmsg}">
                    <form:label path="hibernateLevel" class="col-sm-3 control-label">
                        <spring:message code="logging.hibernateLevel"/>
                    </form:label>
                    <div class="col-sm-5">
                        <form:select path="hibernateLevel" class="form-control">
                            <form:options items="${logLevels2}"/>
                        </form:select>
                    </div>
                </div>
            </spring:bind>


            <spring:bind path="hibernateTypeLevel">
                <div class="form-group form-group-lg" title="${minmsg}">
                    <form:label path="hibernateTypeLevel" class="col-sm-3 control-label">
                        <spring:message code="logging.hibernateTypeLevel"/>
                    </form:label>
                    <div class="col-sm-5">
                        <form:select path="hibernateTypeLevel" class="form-control">
                            <form:options items="${logLevels2}"/>
                        </form:select>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="hibernateSQLLevel">
                <div class="form-group form-group-lg" title="${minmsg}">
                    <form:label path="hibernateSQLLevel" class="col-sm-3 control-label">
                        <spring:message code="logging.hibernateSQLLevel"/>
                    </form:label>
                    <div class="col-sm-5">
                        <form:select path="hibernateSQLLevel" class="form-control">
                            <form:options items="${logLevels2}"/>
                        </form:select>
                    </div>
                </div>
            </spring:bind>

    </div>
    </div>
    <p>
        <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse" data-target="#collapseExample2" aria-expanded="false" asria-controls="collapseExample2">
            DBS logging
        </button>
    </p>
    <div class="collapse" id="collapseExample2">
        <div class="card card-block">


        <spring:bind path="destination">
        <div class="form-group form-group-lg" title="${dest}">
            <form:label path="destination" class="col-sm-3 control-label">
                <spring:message code="logging.destination"/>
            </form:label>
            <div class="col-sm-5">
                <%--<form:select path="destination"  items="${dests}" itemLabel="type" itemValue="destination" class="form-control">--%>
                    <%--<c:forEach var="item" items="${LogDestination.values}">--%>
                        <%--<form:option value="{item.value}"><spring:eval expression="item"/></form:option>--%>
                    <%--</c:forEach>--%>
                <%--</form:select>--%>
                    <form:select path="destination" class="form-control">
                        <form:option value="">-</form:option>
                        <form:options items="${logDestinations}"/>
                    </form:select>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="directory">
        <div class="form-group form-group-lg" title="${dir}">
            <form:label path="directory" class="col-sm-3 control-label">
                <spring:message code="logging.directory"/>
            </form:label>
            <div class="col-sm-5">
                <form:input path="directory" class="form-control"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="fileName">
        <div class="form-group form-group-lg" title="${file}">
            <form:label path="fileName" class="col-sm-3 control-label">
                <spring:message code="logging.filename"/>
            </form:label>
            <div class="col-sm-5">
                <form:input path="fileName" class="form-control"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="fileMode">
        <div class="form-group form-group-lg" title="${mode}">
            <form:label path="fileMode" class="col-sm-3 control-label">
                <spring:message code="logging.filemode"/>
            </form:label>
            <div class="col-sm-5">
                <form:input path="fileMode" class="form-control"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="rotationAge">
        <div class="form-group form-group-lg" title="${rotAge}">
            <form:label path="rotationAge" class="col-sm-3 control-label">
                <spring:message code="logging.rotationage"/>
            </form:label>
            <div class="col-sm-5">
                <form:input path="rotationAge" class="form-control"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="rotationSize">
        <div class="form-group form-group-lg" title="${size}">
            <form:label path="rotationSize" class="col-sm-3 control-label">
                <spring:message code="logging.rotationsize"/>
            </form:label>
            <div class="col-sm-5">
                <form:input path="rotationSize" class="form-control"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="minMessage">
        <div class="form-group form-group-lg" title="${minmsg}">
            <form:label path="minMessage" class="col-sm-3 control-label">
                <spring:message code="logging.minmessage"/>
            </form:label>
            <div class="col-sm-5">
                <form:select path="minMessage" class="form-control">
                    <form:option value="">-</form:option>
                    <form:options items="${logLevels}"/>
                </form:select>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="minErrorState">
        <div class="form-group form-group-lg" title="${errstate}">
            <form:label path="minErrorState" class="col-sm-3 control-label">
                <spring:message code="logging.minerror"/>
            </form:label>
            <div class="col-sm-5">
                <form:select path="minErrorState" class="form-control">
                    <form:option value="">-</form:option>
                    <form:options items="${logLevels}"/>
                </form:select>
            </div>
        </div>
    </spring:bind>


    <spring:bind path="minDuration">
        <div class="form-group form-group-lg" title="${mindur}">
            <form:label path="minDuration" class="col-sm-3 control-label">
                <spring:message code="logging.minduration"/>
            </form:label>
            <div class="col-sm-5">
                <form:input path="minDuration" class="form-control"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="logDuration">
        <div class="form-group form-group-lg" title="${logdur}">
            <form:label path="logDuration" class="col-sm-3 control-label">
                <spring:message code="logging.logduration"/>
            </form:label>
            <div class="col-sm-5">
                <form:checkbox path="logDuration"/>
            </div>
        </div>
    </spring:bind>


    <spring:bind path="prefix">
        <div class="form-group form-group-lg" title="${pref}">
            <form:label path="prefix" class="col-sm-3 control-label">
                <spring:message code="logging.prefix"/>
            </form:label>
            <div class="col-sm-5">

                <form:input path="prefix" class="form-control"/>
            </div>
        </div>
    </spring:bind>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-3 col-sm-offset-3">
            <form:button class="btn btn-primary btn-lg">
                <spring:message code="logging.submit"/>
            </form:button>
        </div>
        <div class="col-sm-2 text-right">
            <button type="button" class="btn btn-warning btn-lg" onclick="window.history.back()">
                <spring:message code="link.back"/>
            </button>
        </div>
    </div>
</form:form>