<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="default-style">
<head>
    <jsp:include page="../../fragments/headTag.jsp" />
    <link rel="stylesheet" href="/AlertSampleManager.css" type="text/css">
    <script src="dist/sweetalert.min.js"></script>
    <link rel="stylesheet" type="text/css" href="dist/sweetalert.css">

</head>
<body>
<div class="page-loader">
    <div class="bg-primary"></div>
</div>
<!-- urls -->
<spring:url value="/capture/RVPs/saveEntity/" var="saveUrl"></spring:url>
<spring:url value="/capture/RVPs/newEntity" var="newUrl"></spring:url>
<spring:url value="/capture/RVPs/" var="listUrl"></spring:url>
<!-- Layout wrapper -->
<div class="layout-wrapper layout-2">
<div class="layout-inner">
<jsp:include page="../../fragments/sideNav.jsp" />
<!-- Layout container -->
<div class="layout-container">
<!-- Layout navbar -->
<nav class="layout-navbar navbar navbar-expand-lg align-items-lg-center bg-white container-p-x" id="layout-navbar">
    <jsp:include page="../../fragments/navHeader.jsp" />
    <jsp:include page="../../fragments/navHeader2.jsp" />
</nav>
<!-- / Layout navbar -->
<!-- Layout content -->
<div class="layout-content">
<!-- Content -->
<div class="container-fluid flex-grow-1 container-p-y">
<jsp:useBean id="now" class="java.util.Date"/>
<h4 class="font-weight-bold py-3 mb-4">
    <spring:message code="heading" />
    <div class="text-muted text-tiny mt-1"><small class="font-weight-normal"><fmt:formatDate value="${now}" dateStyle="long"/></small></div>
</h4>
			            <div class="card mb-4" id="edit-element">
			              <h6 class="card-header with-elements">
			              	<div class="card-title-elements">
			              	</div>
			                <div class="card-header-title"><spring:message code="RVPs" /></div>
			              </h6>
			              <div>
			                <div class="col-md-12 col-lg-12 col-xl-12">
			                  <div class="card-body demo-vertical-spacing">
			                  		<div class="row">
				                    <div class="col-md-8">
                                        <form action="#" autocomplete="off" id="edit-form">

                                        <div class="form-group">
                                        </div>
                                        <div class="modal-body">

                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-key"></i></span>
                                                    </div>
                                                    <input type="text" id="primers_id" name="primers_id"
                                                           data-toggle="tooltip" data-state="danger"
                                                           data-placement="right"
                                                           title="<spring:message code="rvpId_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="rvpId" />">
                                                </div>
                                            </div>


                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                        </div>
                                                        <input type="text" id="batchName"
                                                               name="batchName" data-toggle="tooltip"
                                                               data-state="primary" data-placement="right"
                                                               title="<spring:message code="batchName_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="batchName" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="virusSerotype"
                                                               name="virusSerotype" data-toggle="tooltip"
                                                               data-state="primary" data-placement="right"
                                                               title="<spring:message code="virusSerotype_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="virusSerotype" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div id="sequencinPrimersDiv">
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="provider" name="provider"
                                                                   data-toggle="tooltip" data-state="primary"
                                                                   data-placement="right"
                                                                   title="<spring:message code="provider_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="provider" />">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>


                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-calendar-alt"></i></span>
                                                        </div>
                                                        <fmt:formatDate value="${now}" var="fecEnr" pattern="yyyy-MM-dd" />
                                                        <input type="text" id="date_exp" name="date_exp" value="${fecEnr}"
                                                               data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="date" />"
                                                               class="form-control"  data-date-end-date="0d" placeholder="<spring:message code="date" />">
                                                    </div>
                                                </div>
                                            </div>


                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="aliquot_volume" name="aliquot_volume"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="aliquot_volume_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="aliquot_volume" />">
                                                    </div>
                                                </div>
                                            </div>


                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="num_aliq" name="num_aliq"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="num_aliq_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="num_aliq" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                        </div>
                                                        <input type="text" id="working_dilution" name="working_dilution"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="working_dilution_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="working_dilution" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div id="midiprep_purifiedDiv">
                                                <div class="col-md-8">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="infection_percentage" name="infection_percentage"
                                                                   data-toggle="tooltip" data-state="primary"
                                                                   data-placement="right"
                                                                   title="<spring:message code="infection_percentage_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="infection_percentage" />">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>


                                                <div class="col-md-8">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="cellType" name="cellType"
                                                                   data-toggle="tooltip" data-state="primary"
                                                                   data-placement="right"
                                                                   title="<spring:message code="cellType_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="cellType" />">
                                                        </div>
                                                    </div>
                                                </div>

                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="Time_hpi" name="Time_hpi"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="Time_hpi_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="Time_hpi" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                        </div>
                                                        <input type="text" id="comments" name="comments"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="comments_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="comments" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div id="glycerol_stockDiv">
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="form-label"><spring:message code="loc_freezer" /></label> <span class="badge badge-dot badge-danger"></span>
                                                        <select class="select2-control form-control" id="equip" name="equip" style="width: 100%" data-allow-clear="false">
                                                            <option value=""></option>
                                                            <c:forEach items="${equips}" var="equip">
                                                                <option value="${equip.systemId}"><spring:message code="loc_freezer" />: ${equip.id} - ${equip.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="form-label"><spring:message code="loc_rack" /></label> <span class="badge badge-dot badge-danger"></span>
                                                        <select class="select2-control form-control" id="rack" name="rack" style="width: 100%" data-allow-clear="false">
                                                            <option value=""></option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="form-label"><spring:message code="loc_box" /></label> <span class="badge badge-dot badge-danger"></span>
                                                        <select class="select2-control form-control" id="boxSpecId" name="boxSpecId" style="width: 100%" data-allow-clear="false">
                                                            <option value=""></option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="form-label"><spring:message code="loc_pos" /></label> <span class="badge badge-dot badge-danger"></span>
                                                        <select class="select2-control form-control" id="position" name="position" style="width: 100%" data-allow-clear="false">
                                                            <option value=""></option>
                                                        </select>
                                                    </div>
                                                </div>



                                        </div>
                                        <div class="form-group">
                                            <button type="submit" class="btn rounded-pill btn-outline-primary"
                                                    data-toggle="tooltip" data-placement="bottom" title="<spring:message code="saveRecord" />"
                                                    id="guardar"><i class="ion ion-md-save"></i>     <spring:message code="save" /></button>
                                            <a href="${fn:escapeXml(listUrl)}" class="btn rounded-pill btn-outline-danger"
                                               data-toggle="tooltip" data-placement="bottom" title="<spring:message code="cancelRecord" />">
                                                <i class="ion ion-md-close-circle"></i>     <spring:message code="end" /></a>
                                        </div>

                                        </form>
				                    </div>
				                  </div>
			                  </div>
			                </div>
			              </div>
			            </div>
         			</div>
         			<!-- / Content -->

         			<jsp:include page="../../fragments/navFooter.jsp" />
        		</div>
        		<!-- Layout content -->
			</div>
			<!-- / Layout container -->
    	</div>
    </div>  	
    <!-- / Layout wrapper -->
	
  	<!-- Bootstrap and necessary plugins -->
  	<jsp:include page="../../fragments/corePlugins.jsp" />
  	<jsp:include page="../../fragments/bodyUtils.jsp" />
  	
  	<!-- Lenguaje -->
	<c:choose>
	<c:when test="${cookie.eSampleManagerLang.value == null}">
		<c:set var="lenguaje" value="en"/>
	</c:when>
	<c:otherwise>
		<c:set var="lenguaje" value="${cookie.eSampleManagerLang.value}"/>
	</c:otherwise>
	</c:choose>
    
    <spring:url value="/resources/vendor/libs/bootstrap-datepicker/bootstrap-datepicker_{language}.js" var="bdLang">
  		<spring:param name="language" value="${lenguaje}" />
  	</spring:url>
  	<script src="${bdLang}"></script>
  	
    <spring:url value="/resources/vendor/libs/select2/select2_{language}.js" var="select2Lang">
  		<spring:param name="language" value="${lenguaje}" />
  	</spring:url>
  	<script src="${select2Lang}"></script>
	
  <!-- Custom scripts required by this view -->
  <spring:url value="/resources/js/views/EntityPrimers.js" var="processEntity" />
	<script src="${processEntity}"></script>
  
  <spring:url value="/resources/vendor/libs/validate/messages_{language}.js" var="validateLang">
	<spring:param name="language" value="${lenguaje}" />
  </spring:url>
  <script src="${validateLang}"></script>

  <!-- Mensajes -->

  	<c:set var="seleccionar"><spring:message code="select" /></c:set>
  	<c:set var="successmessage"><spring:message code="process.success" /></c:set>
  	<c:set var="errormessage"><spring:message code="process.errors" /></c:set>
  	<c:set var="waitmessage"><spring:message code="process.wait" /></c:set>
  	<c:set var="agregando">true</c:set>
  	
  	<spring:url value="/api/racks" var="racksUrl"/>
  	<spring:url value="/api/boxes" var="boxesUrl"/>

  	<spring:url value="/api/positions" var="positionsUrl"/>

	<script>

	jQuery(document).ready(function() {
		$("li.capture").addClass("open");
    	$("li.capture").addClass("active");
    	$("li.primers").addClass("active");
        var parametros = {saveUrl: "${saveUrl}", successmessage: "${successmessage}",
            errormessage: "${errormessage}",waitmessage: "${waitmessage}",
            listUrl: "${listUrl}" ,seleccionar: "${seleccionar}" ,lenguaje: "${lenguaje}",
            racksUrl: "${racksUrl}" ,boxesUrl: "${boxesUrl}" ,agregando: "${agregando}" ,positionsUrl: "${positionsUrl}"
		};
		ProcessEntity.init(parametros);

        $('#guardar').click(function(){

            $.ajax({
                success:function(r){
                    if(r==1){

                        //¿Aquí iría el borrar contenido del formulario no?

                    }else{
                       // $('#protrecombinante_id').val('');

                    }
                }
            });
        });

	});
	if ($('html').attr('dir') === 'rtl') {
	    $('.tooltip-demo [data-placement=right]').attr('data-placement', 'left').addClass('rtled');
	    $('.tooltip-demo [data-placement=left]:not(.rtled)').attr('data-placement', 'right').addClass('rtled');
  	}
  	$('[data-toggle="tooltip"]').tooltip();






    function valideKey(evt){

        // code is the decimal ASCII representation of the pressed key.
        var code = (evt.which) ? evt.which : evt.keyCode;

        if(code==8) { // backspace.
            return true;
        } else if(code>=48 && code<=57) { // is a number.

            return true;
        } else{ // other keys.
            return false;
        }
    }




	</script>
</body>
</html>
