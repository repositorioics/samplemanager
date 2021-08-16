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


                   <div id="wrap" >
                    <br><br>
                    <div class="import-results col-md-12 col-lg-12 col-xl-12">
                    <table id="lista_entidades11" class="table table-striped table-bordered datatable" data-show-toggle="true"   >
                        <thead>
                        <tr>
                            <th><spring:message code="specimenId" /></th>
                            <th><spring:message code="orthocode" /></th>
                            <th><spring:message code="specimenCondition" /></th>
                            <th><spring:message code="volUnits" /></th>
                            <th><spring:message code="volume" /></th>
                            <th><spring:message code="varA" /></th>
                            <th><spring:message code="varB" /></th>
                            <th><spring:message code="date" /></th>
                            <th><spring:message code="recordUser" /></th>
                            <th><spring:message code="specimenType" /></th>
                            <th><spring:message code="position" /></th>
                            <th><spring:message code="study_id" /></th>
                            <th><spring:message code="study_id2" /></th>
                            <th><spring:message code="box_id" /></th>
                            <th><spring:message code="rack_name" /></th>
                            <th><spring:message code="equip_id" /></th>
                            <th><spring:message code="equip_name" /></th>

                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${lista_entidades1}" var="lista_entidades1">
                            <tr>
                                <td><c:out value="${lista_entidades1.specimenId}" /></td>
                                <td><c:out value="${lista_entidades1.orthocode}" /></td>
                                <td><c:out value="${lista_entidades1.specimenCondition}" /></td>
                                <td><c:out value="${lista_entidades1.volUnits}" /></td>
                                <td><c:out value="${lista_entidades1.volume}" /></td>
                                <td><c:out value="${lista_entidades1.varA}" /></td>
                                <td><c:out value="${lista_entidades1.varB}" /></td>
                                <td><c:out value="${lista_entidades1.labReceiptDate}" /></td>
                                <td><c:out value="${lista_entidades1.recordUser}" /></td>
                                <td><c:out value="${lista_entidades1.specimenType}" /></td>
                                <td><c:out value="${lista_entidades1.position}" /></td>
                                <td><c:out value="${lista_entidades1.study_id}" /></td>
                                <td><c:out value="${lista_entidades1.study_id2}" /></td>
                                <td><c:out value="${lista_entidades1.box_id}" /></td>
                                <td><c:out value="${lista_entidades1.rack_name}" /></td>
                                <td><c:out value="${lista_entidades1.equip_id}" /></td>
                                <td><c:out value="${lista_entidades1.equip_name}" /></td>

                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
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

<spring:url value="/resources/vendor/libs/datatables/label_{language}.json" var="dataTablesLang">
    <spring:param name="language" value="${lenguaje}"/>
</spring:url>
    <script src="${bdLang}">
    </script>




<script>

    jQuery(document).ready(function () {
        $("li.capture").addClass("open");
        $("li.capture").addClass("active");
        $("li.specimens").addClass("active");

        $('#lista_entidades11').DataTable({
            dom: 'lBfrtip',
            "oLanguage": {
                "sUrl": "${dataTablesLang}"
            },
            "bFilter": false,
            "bInfo": false,
            "bPaginate": false,
            "bDestroy": false,
            "responsive": false,
            "pageLength": 5,
            "lengthMenu": [5, 10, 20, 50, 100],
            "bLengthChange": false,
            "responsive": false,
            "buttons": [
                {
                    extend: 'excel',
                    text: "Download Data to Update Inventory",
                    className: "btn rounded-pill btn-outline-primary"
                }
            ]
        }) ;
    });

    if ($('html').attr('dir') === 'rtl') {
        $('.tooltip-demo [data-placement=right]').attr('data-placement', 'left').addClass('rtled');
        $('.tooltip-demo [data-placement=left]:not(.rtled)').attr('data-placement', 'right').addClass('rtled');
    }
    $('[data-toggle="tooltip"]').tooltip();
</script>
</body>
</html>
