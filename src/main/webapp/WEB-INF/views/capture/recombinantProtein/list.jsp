<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html >
<head>

    <jsp:include page="../../fragments/headTag.jsp"/>
    <spring:url value="/resources/vendor/libs/jquery-ui/jquery-ui.css" var="jqui" />
    <link href="${jqui}" rel="stylesheet" type="text/css"/>

    <style>
       #div2{
           /* border: 1px solid black;*/
            width: 400;
           display:inline-block;
            margin-bottom: 12px;
        }
        #div2 { overflow-x: scroll;}
    </style>
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
                    <spring:url value="/capture/recombinantProtein/newEntity/" var="newEntity"/>
                    <spring:url value="/capture/recombinantProtein/uploadEntity/" var="uploadEntity"/>
                    <button id="lista_entidades_new" data-toggle="tooltip" data-placement="bottom"
                            title="<spring:message code="addEntityToolTip" />"
                            onclick="location.href='${fn:escapeXml(newEntity)}'" type="button"
                            class="btn rounded-pill btn-outline-primary"><i class="fa fa-plus"></i>&nbsp;
                        <spring:message code="add"/></button>

                    <br><br>

                    <div class="card mb-4" id="entity-element">
                        <h6 class="card-header with-elements">
                            <div class="card-title-elements">
                            </div>
                            <div class="card-header-title"><spring:message code="findrecombinantprotein"/></div>
                        </h6>

                        <div class="col-md-12 col-lg-12 col-xl-12">
                            <div class="card-body">
                                <%--<div class="row">--%>

                                <div class="col-md-12 col-lg-12 col-xl-12">
                                    <form action="#" autocomplete="off" id="search-form">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i
                                                                            class="fa fa-key"></i></span>
                                                        </div>
                                                        <input type="text" id="antibody_id" name="antibody_id"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="protrecombinante_id" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="protrecombinante_id" />">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                                    <span class="input-group-text"></span>
                                                        </div>
                                                        <input type="text" id="protein_name"
                                                               name="protein_name" data-toggle="tooltip"
                                                               data-state="primary" data-placement="right"
                                                               title="<spring:message code="protein_name_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="protein_name" />">
                                                    </div>
                                                </div>

                                            </div>


                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"></span>
                                                            </div>
                                                            <input type="text" id="Construct_name" name="Construct_name"
                                                                   data-toggle="tooltip" data-state="primary"
                                                                   data-placement="right"
                                                                   title="<spring:message code="Construct_name_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="Construct_name" />">
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"></span>
                                                            </div>
                                                            <input type="text" id="protein_origin"
                                                                   name="protein_origin" data-toggle="tooltip"
                                                                   data-state="primary" data-placement="right"
                                                                   title="<spring:message code="protein_origin_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="protein_origin" />">
                                                        </div>
                                                    </div>

                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i
                                                                            class="fa fa-calendar-alt"></i></span>
                                                                </div>
                                                                <input type="text" id="date_transfection"
                                                                       name="date_transfection" data-toggle="tooltip"
                                                                       data-state="primary" data-placement="right"
                                                                       title="<spring:message code="date_transfection_tool_tip" />"
                                                                       class="form-control" data-date-end-date="0d"
                                                                       placeholder="<spring:message code="date_transfection" />">
                                                            </div>

                                                    </div>
                                                </div>




                                        </div>
                                        <div class="form-group" align="right">
                                            <button type="submit" class="btn rounded-pill btn-outline-primary"
                                                    data-toggle="tooltip" data-placement="bottom"
                                                    title="<spring:message code="findrecombinantprotein" />"
                                                    id="search"><i class="ion ion-md-search"></i> <spring:message
                                                    code="findrecombinantprotein"/></button>
                                        </div>
                                        <div>


                                        </div>

                                    </form>
                                </div>
                                <%--</div>--%>

                            </div>
                        </div>


                </div>
                    <li><code></code>
                        <div id="wrap" >
                            <div id="div2" >
                              <table id="lista_entidades"  class="table table-striped table-bordered datatable"   >
                                <thead>
                                <tr>
                                    <th><spring:message code="protrecombinante_id"/></th>
                                    <th><spring:message code="protein_name"/></th>
                                    <th><spring:message code="protein_origin"/></th>
                                    <th><spring:message code="Construct_name"/></th>
                                    <th><spring:message code="date_transfection"/></th>
                                    <th><spring:message code="vol_sn"/></th>
                                    <th><spring:message code="pur_method"/></th>
                                    <th><spring:message code="frac_retained"/></th>
                                    <th><spring:message code="vol_usable"/></th>
                                    <th><spring:message code="dialysis_buffer"/></th>
                                    <th><spring:message code="date_purification"/></th>
                                    <th class="none" ><spring:message code="num_aliquot"/></th>
                                    <th class="none" ><spring:message code="vol_aliquot"/></th>
                                    <th class="none" ><spring:message code="conc_protein"/></th>
                                    <th class="none" ><spring:message code="loc_freezer"/></th>
                                    <th class="none" ><spring:message code="loc_rack"/></th>
                                    <th class="none" ><spring:message code="loc_box"/></th>
                                    <th class="none" ><spring:message code="loc_pos"/></th>
                                    <th class="none" ><spring:message code="comments"/></th>


                                    <th><spring:message code="actions"/></th>
                                </tr>
                                </thead>
                                <tbody >

                                </tbody>
                            </table>
                            </div>
                        </div>
                    </li>
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
<script src="${bdLang}">
</script>



<spring:url value="/capture/recombinantProtein/search/"
            var="searchUrl"/>

<spring:url value="/capture/recombinantProtein/editEntity/"
            var="editUrl"/>
<spring:url value="/capture/recombinantProtein/"
            var="viewUrl"/>

<spring:url value="/capture/subjects/"
            var="viewSubjectUrl"/>

<spring:url value="/capture/specimens/getBoxes"
            var="boxNameUrl"/>

<!-- Custom scripts required by this view -->
    <spring:url value="/resources/js/views/RecombinantproteinFilter.js" var="searchProcess"/>
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

<script defer>

    jQuery(document).ready(function () {
        $("li.capture").addClass("open");
        $("li.capture").addClass("active");
        $("li.recombinantProtein").addClass("active");

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
