<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html class="default-style">
<head>
    <jsp:include page="../../fragments/headTag.jsp"/>
</head>
<body>
<div class="page-loader">
    <div class="bg-primary"></div>
</div>

<!-- Layout wrapper -->
<div class="layout-wrapper layout-2">
    <div class="layout-inner">
        <jsp:include page="../../fragments/sideNav.jsp"/>
        <!-- Layout container -->
        <div class="layout-container">
            <!-- Layout navbar -->
            <nav class="layout-navbar navbar navbar-expand-lg align-items-lg-center bg-white container-p-x"
                 id="layout-navbar">
                <jsp:include page="../../fragments/navHeader.jsp"/>
                <jsp:include page="../../fragments/navHeader2.jsp"/>
            </nav>
            <!-- / Layout navbar -->
            <!-- Layout content -->
            <div class="layout-content">
                <!-- Content -->
                <div class="container-fluid flex-grow-1 container-p-y">
                    <jsp:useBean id="now" class="java.util.Date"/>
                    <h4 class="font-weight-bold py-3 mb-4">
                        <spring:message code="heading"/>
                        <div class="text-muted text-tiny mt-1">
                            <small class="font-weight-normal"><fmt:formatDate value="${now}" dateStyle="long"/></small>
                        </div>
                    </h4>
                    <spring:url value="/capture/specimens/newEntity/" var="newEntity"/>
                    <spring:url value="/capture/specimens/uploadEntity/" var="uploadEntity"/>
                    <button id="lista_entidades_new" data-toggle="tooltip" data-placement="bottom"
                            title="<spring:message code="addEntityToolTip" />"
                            onclick="location.href='${fn:escapeXml(newEntity)}'" type="button"
                            class="btn rounded-pill btn-outline-primary"><i class="fa fa-plus"></i>&nbsp;
                        <spring:message code="add"/></button>
                    <button id="lista_entidades_import" data-toggle="tooltip" data-placement="bottom"
                            title="<spring:message code="importEntityToolTip" />"
                            onclick="location.href='${fn:escapeXml(uploadEntity)}'" type="button"
                            class="btn rounded-pill btn-outline-primary"><i class="fa fa-file-upload"></i>&nbsp;
                        <spring:message code="import"/></button>
                    <br><br>
                    <div class="card mb-4" id="entity-element">
                        <h6 class="card-header with-elements">
                            <div class="card-title-elements">
                            </div>
                            <div class="card-header-title"><spring:message code="findspecimens"/></div>
                        </h6>
                        <div class="col-md-12 col-lg-12 col-xl-12">
                            <div class="card-body">
                                <%--<div class="row">--%>

                                <div class="col-md-12 col-lg-12 col-xl-12">
                                    <form action="#" autocomplete="off" id="search-form">
                                        <div class="row">
                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i
                                                                            class="fa fa-key"></i></span>
                                                        </div>
                                                        <input type="text" id="specimenId" name="specimenId"
                                                               data-toggle="tooltip" data-state="danger"
                                                               data-placement="right"
                                                               title="<spring:message code="specimenId" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="specimenId" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i
                                                                            class="fa fa-calendar-alt"></i></span>
                                                        </div>
                                                        <input type="text" id="labReceiptDate"
                                                               name="labReceiptDate" data-toggle="tooltip"
                                                               data-state="danger" data-placement="right"
                                                               title="<spring:message code="labReceiptDate" />"
                                                               class="form-control" data-date-end-date="0d"
                                                               placeholder="<spring:message code="labReceiptDate" />">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i
                                                                    class="fa fa-key"></i></span>
                                                        </div>
                                                        <input type="text" id="box" name="box"
                                                               data-toggle="tooltip" data-state="danger"
                                                               data-placement="right"
                                                               title="<spring:message code="box" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="box" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i
                                                                    class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="orthocode" name="orthocode"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="orthocode" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="orthocode" />">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>


                                        <div class="row">
                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="studyId"/></label>
                                                    <select class="select2-control form-control" id="studyId"
                                                            name="studyId" style="width: 100%"
                                                            data-allow-clear="true">
                                                        <option value=""></option>
                                                        <c:forEach items="${subjects}" var="subject">
                                                            <option value="${subject.systemId}"><spring:message
                                                                    code="studyId"/>: ${subject.subjectId}
                                                                <spring:message
                                                                        code="study"/>: ${subject.studyId.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                        </div>

                                        <div class="form-group" align="right">
                                            <button type="submit" class="btn rounded-pill btn-outline-primary"
                                                    data-toggle="tooltip" data-placement="bottom"
                                                    title="<spring:message code="findspecimens" />"
                                                    id="search"><i class="ion ion-md-search"></i> <spring:message
                                                    code="findspecimens"/></button>
                                        </div>
                                    </form>
                                </div>
                                <%--</div>--%>

                            </div>
                        </div>

                        <div class="row no-gutters row-bordered">
                            <div class="col-md-12 col-lg-12 col-xl-12">
                                <div class="card-body">
                                    <table id="lista_entidades" class="table table-striped table-bordered datatable"
                                           width="100%">
                                        <thead>
                                        <tr>
                                            <th><spring:message code="specimenId"/></th>
                                            <th><spring:message code="labReceiptDate"/></th>
                                            <th><spring:message code="specimenType"/></th>
                                            <th><spring:message code="specimenCondition"/></th>
                                            <th><spring:message code="varA"/></th>
                                            <th><spring:message code="varB"/></th>
                                            <th><spring:message code="volume"/></th>
                                            <th><spring:message code="volUnits"/></th>
                                            <th><spring:message code="subject"/></th>
                                            <th><spring:message code="inStorage"/></th>
                                            <th class="none"><spring:message code="orthocode"/></th>
                                            <th class="none"><spring:message code="obs"/></th>
                                            <th class="none"><spring:message code="recordDate"/></th>
                                            <th class="none"><spring:message code="recordUser"/></th>
                                            <th class="none"><spring:message code="recordIp"/></th>
                                            <th class="none"><spring:message code="enabled"/></th>
                                            <th><spring:message code="actions"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <%--<c:forEach items="${entidades}" var="entidad">
                                            <tr>
                                                <spring:url value="/capture/specimens/{systemId}/"
                                                            var="viewUrl">
                                                    <spring:param name="systemId" value="${entidad.systemId}" />
                                                </spring:url>
                                                <spring:url value="/capture/specimens/editEntity/{systemId}/"
                                                            var="editUrl">
                                                    <spring:param name="systemId" value="${entidad.systemId}" />
                                                </spring:url>
                                                <spring:url value="/capture/subjects/{systemId}/"
                                                            var="viewSubjectUrl">
                                                    <spring:param name="systemId" value="${entidad.subjectId.systemId}" />
                                                </spring:url>
                                                <td><c:out value="${entidad.specimenId}" /></td>
                                                <td><c:out value="${entidad.orthocode}" /></td>
                                                <td><fmt:formatDate value="${entidad.labReceiptDate}" var="fecRec" pattern="yyyy-MM-dd" /><c:out value="${fecRec}" /></td>
                                                <td><a href="${fn:escapeXml(viewSubjectUrl)}"><c:out value="${entidad.subjectId.subjectId}" /></a></td>
                                                <td><c:out value="${entidad.inStorage}" /></td>
                                                <td><c:out value="${entidad.orthocode}" /></td>
                                                <td><c:out value="${entidad.obs}" /></td>
                                                <td><c:out value="${entidad.recordDate}" /></td>
                                                <td><c:out value="${entidad.recordUser}" /></td>
                                                <td><c:out value="${entidad.recordIp}" /></td>

                                               &lt;%&ndash; <c:choose>
                                                    <c:when test="${entidad.pasive eq '0'.charAt(0)}">
                                                        <td><span class="badge badge-success"><spring:message code="CAT_SINO_SI" /></span></td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td><span class="badge badge-danger"><spring:message code="CAT_SINO_NO" /></span></td>
                                                    </c:otherwise>
                                                </c:choose>&ndash;%&gt;
                                                <td>
                                                    <a href="${fn:escapeXml(viewUrl)}" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="viewEntidadToolTip" />" class="btn btn-outline-primary btn-sm"><i class="fa fa-search"></i></a>
                                                    <a href="${fn:escapeXml(editUrl)}" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="editEntidadToolTip" />" class="btn btn-outline-primary btn-sm"><i class="fa fa-edit"></i></a>
                                                </td>
                                            </tr>
                                        </c:forEach>--%>
                                        </tbody>
                                    </table>

                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <!-- / Content -->
                <jsp:include page="../../fragments/navFooter.jsp"/>
            </div>
            <!-- Layout content -->
        </div>
        <!-- / Layout container -->
    </div>
</div>
<!-- / Layout wrapper -->

<!-- Bootstrap and necessary plugins -->
<jsp:include page="../../fragments/corePlugins.jsp"/>
<jsp:include page="../../fragments/bodyUtils.jsp"/>

<!-- Lenguaje -->
<c:choose>
    <c:when test="${cookie.eSampleManagerLang.value == null}">
        <c:set var="lenguaje" value="en"/>
    </c:when>
    <c:otherwise>
        <c:set var="lenguaje" value="${cookie.eSampleManagerLang.value}"/>
    </c:otherwise>
</c:choose>

<spring:url value="/resources/vendor/libs/datatables/label_{language}.json" var="dataTablesLang">
    <spring:param name="language" value="${lenguaje}"/>
</spring:url>
<script src="${bdLang}"></script>

<spring:url value="/capture/specimens/search/"
            var="searchUrl"/>

<spring:url value="/capture/specimens/editEntity/"
            var="editUrl"/>
<spring:url value="/capture/specimens/"
            var="viewUrl"/>

<spring:url value="/capture/subjects/"
            var="viewSubjectUrl"/>

<!-- Custom scripts required by this view -->
<spring:url value="/resources/js/views/SpecimenFilter.js" var="searchProcess"/>
<script src="${searchProcess}"></script>

<spring:url value="/resources/vendor/libs/validate/messages_{language}.js" var="validateLang">
    <spring:param name="language" value="${lenguaje}"/>
</spring:url>
<script src="${validateLang}"></script>

<!-- Mensajes -->

<c:set var="seleccionar"><spring:message code="select"/></c:set>
<c:set var="successmessage"><spring:message code="process.success"/></c:set>
<c:set var="errormessage"><spring:message code="process.errors"/></c:set>
<c:set var="waitmessage"><spring:message code="process.wait"/></c:set>
<c:set var="notfound"><spring:message code="notfound"/></c:set>
<c:set var="viewMessage"><spring:message code="viewEntidadToolTip"/></c:set>
<c:set var="editMessage"><spring:message code="editEntidadToolTip"/></c:set>

<script>
    jQuery(document).ready(function () {
        $("li.capture").addClass("open");
        $("li.capture").addClass("active");
        $("li.specimens").addClass("active");

        var parametros = {
            searchUrl: "${searchUrl}",
            editUrl: "${editUrl}",
            viewUrl: "${viewUrl}",
            successmessage: "${successmessage}",
            errormessage: "${errormessage}",
            waitmessage: "${waitmessage}",
            seleccionar: "${seleccionar}",
            editMessage: "${editMessage}",
            viewMessage: "${viewMessage}",
            lenguaje: "${lenguaje}",
            notfound: "${notfound}",
            viewSubjectUrl: "${viewSubjectUrl}",
            dataTablesLang: "${dataTablesLang}"
        };
        SearchProcess.init(parametros);

    });

    if ($('html').attr('dir') === 'rtl') {
        $('.tooltip-demo [data-placement=right]').attr('data-placement', 'left').addClass('rtled');
        $('.tooltip-demo [data-placement=left]:not(.rtled)').attr('data-placement', 'right').addClass('rtled');
    }
    $('[data-toggle="tooltip"]').tooltip();
</script>
</body>
</html>
