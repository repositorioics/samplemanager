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
    <spring:url value="/capture/cells/saveEntity/" var="saveUrl"></spring:url>
    <spring:url value="/capture/cells/newEntity" var="newUrl"></spring:url>
    <spring:url value="/capture/cells/" var="listUrl"></spring:url>
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
			                <div class="card-header-title"><spring:message code="antibodies" /></div>
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
                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-key"></i></span>
                                                        </div>
                                                        <input type="text" id="cells_id" name="cells_id"
                                                               data-toggle="tooltip" data-state="danger"
                                                               data-placement="right"
                                                               title="<spring:message code="cells_id" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="cells_id" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="cell_line_name"
                                                               name="cell_line_name" data-toggle="tooltip"
                                                               data-state="danger" data-placement="right"
                                                               title="<spring:message code="cell_line_name_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="cell_line_name" />">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                        </div>
                                                        <input type="text" id="passage_number" name="passage_number"
                                                               data-toggle="tooltip" data-state="danger"
                                                               data-placement="right"
                                                               title="<spring:message code="passage_number_tool_tip" />"
                                                               class="form-control ui-autocomplete-input"
                                                               placeholder="<spring:message code="passage_number" />">
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
                                                        <input type="text" id="date_frozen" name="date_frozen" value="${fecEnr}"
                                                               data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="date" />"
                                                               class="form-control"  data-date-end-date="0d" placeholder="<spring:message code="date" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="operator_name" name="operator_name"
                                                               data-toggle="tooltip" data-state="danger"
                                                               data-placement="right"
                                                               title="<spring:message code="operator_name_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="operator_name" />">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="company_name" name="company_name"
                                                               data-toggle="tooltip" data-state="danger"
                                                               data-placement="right"
                                                               title="<spring:message code="company_name_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="company_name" />">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="catalog_code" name="catalog_code"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="catalog_code_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="catalog_code" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="form-label"><spring:message code="type_of_culture" /></label> <span class="badge badge-dot badge-danger"></span>
                                                <select class="select2-control form-control" id="type_of_culture" name="type_of_culture" style="width: 100%" data-allow-clear="false">
                                                    <option value=""></option>
                                                    <option value="adherent">adherent</option>
                                                    <option value="suspension">suspension</option>
                                                </select>
                                            </div>


                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="original_cell_type" name="original_cell_type"
                                                               data-toggle="tooltip" data-state="danger"
                                                               data-placement="right"
                                                               title="<spring:message code="original_cell_type_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="original_cell_type" />">
                                                    </div>
                                                </div>
                                            </div>



                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="original_organ" name="original_organ"
                                                           data-toggle="tooltip" data-state="danger"
                                                           data-placement="right"
                                                           title="<spring:message code="original_organ_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="original_organ" />">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="organism_name" name="organism_name"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="organism_name_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="organism_name" />">
                                                </div>
                                            </div>
                                        </div>

                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="cell_density" name="cell_density"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="cell_density_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="cell_density" />">
                                                    </div>
                                                </div>
                                            </div>

                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                    </div>
                                                    <input type="text" id="volume_mL" name="volume_mL"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right" onkeypress="return valideKey(event);"
                                                           title="<spring:message code="volume_mL_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="volume_mL" />">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="freezing_medium" name="freezing_medium"
                                                           data-toggle="tooltip" data-state="danger"
                                                           data-placement="right"
                                                           title="<spring:message code="freezing_medium_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="freezing_medium" />">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="culture_media" name="culture_media"
                                                           data-toggle="tooltip" data-state="danger"
                                                           data-placement="right"
                                                           title="<spring:message code="culture_media_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="culture_media" />">
                                                </div>
                                            </div>
                                        </div>

                                            <div class="form-group">
                                                <label class="form-label"><spring:message code="QC_test_name" /></label> <span class="badge badge-dot badge-danger"></span>
                                                <select class="select2-control form-control" id="QC_test_name" name="QC_test_name"  style="width: 100%" data-allow-clear="false">
                                                    <option value=""></option>
                                                    <option value="YES">YES</option>
                                                    <option value="NO">NO</option>
                                                    <option value="Virus">Virus</option>
                                                </select>

                                                <div id="virus">

                                                <select class="select2-control form-control" id="Virus" name="Virus"  style="width: 100%" data-allow-clear="false">
                                                <option value=""></option>
                                                <option value="DENV">DENV</option>
                                                <option value="ZIKV">ZIKV</option>
                                                <option value="Other">Other</option>
                                                </select>

                                                </div>
                                                <div id="other">
                                                <select class="select2-control form-control" id="Other" name="Other"style="width: 100%" data-allow-clear="false" >
                                                    <option value=""></option>
                                                    <option value="Bacteria">Bacteria</option>
                                                    <option value="Mycoplasma">Mycoplasma</option>
                                                    <option value="Cell identity">Cell identity</option>
                                                </select>
                                                </div>
                                            </div>

                                            <div id="Genetic_mod_div">
                                                <label class="form-label"><spring:message code="Genetic_modification" /></label> <span class="badge badge-dot badge-danger"></span>
                                                <select class="select2-control form-control" id="Genetic_mod" name="Genetic_mod"  style="width: 100%" data-allow-clear="false">
                                                    <option value=""></option>
                                                    <option value="NO">NO</option>
                                                    <option value="YES">YES</option>
                                                </select>

                                            </div>
                                            <div id="GYES">
                                                <select class="select2-control form-control" id="Gyes" name="Gyes"style="width: 100%" data-allow-clear="false" >
                                                    <option value=""></option>
                                                    <option value="transfection">transfection</option>
                                                    <option value="transduction">transduction</option>
                                                </select>
                                            </div>
                                        </div>


                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="selection_reagent" name="selection_reagent"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="selection_reagent_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="selection_reagent" />">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="selection_concentration" name="selection_concentration"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="selection_concentration_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="selection_concentration" />">
                                                    </div>
                                                </div>
                                            </div>


                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="storage_temperature" name="storage_temperature"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="storage_temperature_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="storage_temperature" />">
                                                </div>
                                            </div>
                                        </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="equip" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="equip" name="equip" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <c:forEach items="${equips}" var="equip">
                                                            <option value="${equip.systemId}"><spring:message code="equip" />: ${equip.id} - ${equip.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="form-label"><spring:message code="rack" /></label> <span class="badge badge-dot badge-danger"></span>
                                                        <select class="select2-control form-control" id="rack" name="rack" style="width: 100%" data-allow-clear="false">
                                                            <option value=""></option>
                                                        </select>
                                                    </div>
                                            </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="form-label"><spring:message code="box" /></label> <span class="badge badge-dot badge-danger"></span>
                                                        <select class="select2-control form-control" id="boxSpecId" name="boxSpecId" style="width: 100%" data-allow-clear="false">
                                                            <option value=""></option>
                                                        </select>
                                                    </div>
                                                </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="pos" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="position" name="position" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="additional_comments" name="additional_comments"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="additional_comments_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="additional_comments" />">
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
<br><br><br>
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
  <spring:url value="/resources/js/views/EntityAntibody.js" var="processEntity" />
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
    	$("li.cells").addClass("active");
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
                        $('#antibody_id').val('');
                        $('#antibody_name').val('');
                        $('#target_protein').val('');
                        $('#target_domain').val('');
                        $('#target_epitope').val('');
                        $('#antibody_isotype').val('');
                        $('#virus_serotype').val('');
                        $('#batch_number').val('');
                        $('#person_name').val('');
                        $('#source_name').val('');
                        $('#aliquot_volume').val('');
                        $('#medium_buffer').val('');
                        $('#concentration').val('');
                        $('#technique1').val('');
                        $('#technique1_concentration').val('');
                        $('#technique2').val('');
                        $('#technique2_concentration').val('');
                        $('#technique3').val('');
                        $('#technique3_concentration').val('');
                        $('#storage_temperature').val('');
                        $('#additional_comments').val('');

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
  	$( "#storageDateDiv" ).hide();
    $( "#virus" ).hide();
    $( "#other" ).hide();
    $( "#GYES" ).hide();


    $('#Genetic_mod').change(function() {
        if($('#Genetic_mod').val()=="YES") {
            $( "#GYES" ).show();
        }else{
            $( "#GYES" ).hide();

        }

    });

    $('#QC_test_name').change(function() {
        if($('#QC_test_name').val()=="Virus") {
            $( "#virus" ).show();
        }else{

            $( "#virus" ).hide();
            $( "#other" ).hide();

            $('#Virus').val()="";
            $('#Other').val()=""
        }

    });

    $('#Virus').change(function() {
        if($('#Virus').val()=="Other") {
            $( "#other" ).show();
        }else{
            $( "#other" ).hide();
        }

    });

  	$('#inStorage').change(function() {
        if($('#inStorage').val()=="1") {
        	$( "#storageDateDiv" ).show();
        }else{
        	$( "#storageDateDiv" ).hide();
        }
              
    });

    $(document).ready(function(){
        $("#volUnits").val("ul");
    });
  	$('#specimenId').focus();


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


    $('#aliquot_volume').change(function() {
        if(($('#aliquot_volume').val()<1) || ($('#aliquot_volume').val()>2000) ) {

            //swal(" Sample Manager - Antibodies ","Error, value ("+ $('#aliquot_volume').val().toString() +") out of range (1 a 2000)","error");
            $('#aliquot_volume').val("");
            $('#aliquot_volume').focus();

        }
    });

	</script>
</body>
</html>
