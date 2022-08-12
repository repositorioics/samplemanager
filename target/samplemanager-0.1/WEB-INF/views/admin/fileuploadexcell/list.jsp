<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html class="default-style">
<head>
    <jsp:include page="../../fragments/headTag.jsp"/>
    <spring:url value="/resources/vendor/libs/jquery-ui/jquery-ui.css" var="jqui" />
    <link href="${jqui}" rel="stylesheet" type="text/css"/>
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
                    <spring:url value="/Admin/fileuploadexcell/newEntity/" var="newEntity"/>
                    <spring:url value="/Admin/fileuploadexcell/uploadEntity/" var="uploadEntity"/>

                    <button id="lista_entidades_import" data-toggle="tooltip" data-placement="bottom"
                            title="<spring:message code="uploadEntityToolTip" />"
                            onclick="location.href='${fn:escapeXml(uploadEntity)}'" type="button"
                            class="btn rounded-pill btn-outline-primary"><i class="fa fa-file-upload"></i>&nbsp;
                        <spring:message code="UploadExcel" /></button>
                    <br><br>

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

<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<!-- jQuery UI-->
<spring:url value="/resources/vendor/libs/jquery-ui/jquery-ui.js" var="jQueryUI" />
<script src="${jQueryUI}" type="text/javascript"></script>
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

<spring:url value="/capture/specimens/getBoxes"
            var="boxNameUrl"/>

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
        $("li.storage").addClass("open");
        $("li.storage").addClass("active");
        $("li.fileuploadexcel").addClass("active");

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
            boxNameUrl: "${boxNameUrl}",
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
