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
<spring:url value="/capture/recombinantProtein/saveEntity/" var="saveUrl"></spring:url>
<spring:url value="/capture/recombinantProtein/newEntity" var="newUrl"></spring:url>
<spring:url value="/capture/recombinantProtein/" var="listUrl"></spring:url>
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
			                <div class="card-header-title"><spring:message code="RecombinantProtein" /></div>
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
                                                    <input type="text" id="protrecombinante_id1" name="protrecombinante_id1"
                                                           data-toggle="tooltip" data-state="danger"
                                                           data-placement="right"
                                                           title="<spring:message code="protrecombinante_id_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="protrecombinante_id" />">
                                                </div>
                                            </div>
                                        </div>



                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="protein_name2"
                                                           name="protein_name2" data-toggle="tooltip"
                                                           data-state="primary" data-placement="right"
                                                           title="<spring:message code="protein_name_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="protein_name" />">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="protein_origin3" name="protein_origin3"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="protein_origin_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="protein_origin" />">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="Construct_name4" name="Construct_name4"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="Construct_name_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="Construct_name" />">
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
                                                    <input type="text" id="date_transfection" name="date_transfection" value="${fecEnr}"
                                                           data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="date_transfection" />"
                                                           class="form-control"  data-date-end-date="0d" placeholder="<spring:message code="date_transfection" />">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                    </div>
                                                    <input type="text" id="vol_sn5" name="vol_sn5"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="vol_sn_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="vol_sn" />">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="pur_method6" name="pur_method6"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="pur_method_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="pur_method" />">
                                                </div>
                                            </div>
                                        </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="frac_retained7" name="frac_retained7"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="frac_retained_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="frac_retained" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                        </div>
                                                        <input type="text" id="vol_usable8" name="vol_usable8"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="vol_usable_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="vol_usable" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="dialysis_buffer9" name="dialysis_buffer9"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="dialysis_buffer_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="dialysis_buffer" />">
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
                                                        <input type="text" id="date_purification" name="date_purification" value="${fecEnr}"
                                                               data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="date_purification" />"
                                                               class="form-control"  data-date-end-date="0d" placeholder="<spring:message code="date_purification" />">
                                                    </div>
                                                </div>
                                            </div>

                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                    </div>
                                                    <input type="text" id="num_aliquot10" name="num_aliquot10"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="num_aliquot_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="num_aliquot" />">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                    </div>
                                                    <input type="text" id="vol_aliquot11" name="vol_aliquot11"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="vol_aliquot_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="vol_aliquot" />">
                                                </div>
                                            </div>
                                        </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                        </div>
                                                        <input type="text" id="conc_protein12" name="conc_protein12"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="conc_protein_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="conc_protein" />">
                                                    </div>
                                                </div>
                                            </div>

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

                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
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
  <spring:url value="/resources/js/views/EntityRprotein.js" var="processEntity" />
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
    	$("li.recombinantProtein").addClass("active");
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
                        $('#protrecombinante_id').val('');
                        $('#protein_name').val('');
                        $('#protein_origin').val('');
                        $('#Construct_name').val('');
                        $('#vol_sn').val('');
                        $('#pur_method').val('');
                        $('#frac_retained').val('');
                        $('#vol_usable').val('');
                        $('#dialysis_buffer').val('');
                        $('#num_aliquot').val('');
                        $('#vol_aliquot').val('');
                        $('#conc_protein').val('');


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

  	$('#inStorage').change(function() {
        if($('#inStorage').val()=="1") {
        	$( "#storageDateDiv" ).show();
        }else{
        	$( "#storageDateDiv" ).hide();
        }
              
    });



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
