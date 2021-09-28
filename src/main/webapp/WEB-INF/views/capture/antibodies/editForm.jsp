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
    <spring:url value="/capture/antibodies/saveEditEntity/" var="saveUrl"></spring:url>

    <spring:url value="/capture/antibodies/" var="listUrl"></spring:url>

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
			                <div class="card-header-title"><spring:message code="antibodies"  />  | <spring:message code="Antibodies_edit"  /> ${entidad.antibody_id}  </div>

			              </h6>
			              <div>
			                <div class="col-md-12 col-lg-12 col-xl-12">
			                  <div class="card-body demo-vertical-spacing">
			                  		<div class="row">

				                    <div class="col-md-8">
				                      <form action="#" autocomplete="off" id="edit-form">
                                      <div class="col-md-8">
                                      <div class="form-group">
                                          <div class="input-group">
                                              <div class="input-group-prepend">
                                                  <span class="input-group-text"><i class="fa fa-key"></i></span>
                                              </div>
                                              <input type="text" id="systemId" name="systemId" readonly value="${entidad.systemId}"
                                                     data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="systemId" />"
                                                     class="form-control" placeholder="<spring:message code="systemId" />">
                                              </div>
                                          </div>
                                      </div>

                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-key"></i></span>
                                                        </div>
                                                        <input type="text" id="antibody_id" name="antibody_id" readonly value="${entidad.antibody_id}"
                                                               data-toggle="tooltip" data-state="danger"
                                                               data-placement="right"
                                                               title="<spring:message code="antibody_id" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="antibody_id" />">
                                                    </div>
                                                </div>
                                            </div>


                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="antibody_name" value="${entidad.antibody_name}"
                                                               name="antibody_name" data-toggle="tooltip"
                                                               data-state="primary" data-placement="right"
                                                               title="<spring:message code="antibody_name_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="antibody_name" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-8">

                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="target_protein" name="target_protein" value="${entidad.target_protein}"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="target_protein_tool_tip" />"
                                                               class="form-control ui-autocomplete-input"
                                                               placeholder="<spring:message code="target_protein" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="target_domain" name="target_domain" value="${entidad.target_domain}"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="target_domain_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="target_domain" />">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="target_epitope" name="target_epitope" value="${entidad.target_epitope}"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="target_epitope_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="target_epitope" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="antibody_isotype" name="antibody_isotype" value="${entidad.antibody_isotype}"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="antibody_isotype_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="antibody_isotype" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="virus_serotype" name="virus_serotype" value="${entidad.virus_serotype}"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="virus_serotype_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="virus_serotype" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="batch_number" name="batch_number" value="${entidad.batch_number}"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="batch_number_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="batch_number" />">
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
					                        <input type="text" id="date_produced" name="date_produced" value="${entidad.date_produced}"
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
                                                    <input type="text" id="person_name" name="person_name" value="${entidad.person_name}"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="person_name_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="person_name" />">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="source_name" name="source_name" value="${entidad.source_name}"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="source_name_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="source_name" />">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                    </div>
                                                    <input type="text" id="aliquot_volume" name="aliquot_volume" value="${entidad.aliquot_volume}"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right" onkeypress="return valideKey(event);"
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
                                                    <input type="text" id="medium_buffer" name="medium_buffer" value="${entidad.medium_buffer}"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="medium_buffer_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="medium_buffer" />">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="concentration" name="concentration" value="${entidad.concentration}"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="concentration_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="concentration" />">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="technique1" name="technique1" value="${entidad.technique1}"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="technique1_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="technique1" />">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="technique1_concentration1" name="technique1_concentration1" value="${entidad.technique1_concentration1}"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="technique1_concentration_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="technique1_concentration" />">
                                                </div>
                                            </div>
                                        </div>

                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="technique2" name="technique2" value="${entidad.technique2}"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="technique2_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="technique2" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="technique2_concentration2" name="technique2_concentration2" value="${entidad.technique2_concentration2}"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="technique2_concentration_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="technique2_concentration" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="technique3" name="technique3" value="${entidad.technique3}"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="technique3_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="technique3" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="technique3_concentration3" name="technique3_concentration3" value="${entidad.technique3_concentration3}"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="technique3_concentration_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="technique3_concentration" />">
                                                    </div>
                                                </div>
                                            </div>

                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="storage_temperature" name="storage_temperature" value="${entidad.storage_temperature}"
                                                           data-toggle="tooltip" data-state="primary"
                                                           data-placement="right"
                                                           title="<spring:message code="storage_temperature_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="storage_temperature" />">
                                                </div>
                                            </div>
                                        </div>

                                            <div class="modal-body">
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="equip" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="equip" name="equip" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <c:forEach items="${equips}" var="equip">
                                                            <c:choose>
                                                                <c:when test="${equip.systemId eq entidad.freezer_name}">
                                                                    <option selected value="${equip.systemId}"><spring:message code="${equip.name}" /></option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${equip.systemId}"><spring:message code="${equip.name}" /></option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="col-md-4">

                                                    <div class="form-group">
                                                        <label class="form-label"><spring:message code="rack" /></label> <span class="badge badge-dot badge-danger"></span>
                                                        <select class="select2-control form-control" id="rack" name="rack" style="width: 100%" data-allow-clear="false">
                                                            <option value=""></option>
                                                            <c:forEach items="${racks}" var="rack">
                                                                <c:choose>
                                                                    <c:when test="${rack.systemId eq entidad.rack_name}">
                                                                        <option selected value="${rack.systemId}"><spring:message code="${rack.name}" /></option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${rack.systemId}"><spring:message code="${rack.name}" /></option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                            </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="form-label"><spring:message code="box" /></label> <span class="badge badge-dot badge-danger"></span>
                                                        <select class="select2-control form-control" id="boxSpecId" name="boxSpecId" style="width: 100%" data-allow-clear="false">
                                                            <option value=""></option>
                                                            <c:forEach items="${boxes}" var="box">
                                                                <c:choose>
                                                                    <c:when test="${box.systemId eq entidad.box_name}">
                                                                        <option selected value="${box.systemId}"><spring:message code="${box.name}" /></option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${box.systemId}"><spring:message code="${box.name}" /></option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="pos" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="positions" name="positions" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <c:forEach items="${posiciones}" var="pos1">
                                                            <c:choose>
                                                                <c:when test="${pos1 eq entidad.position_in_the_box}">
                                                                    <option selected value="${pos1}"><spring:message code="${pos1}" /></option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${pos1}"><spring:message code="${pos1}" /></option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            </div>

                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="additional_comments" name="additional_comments" value="${entidad.additional_comments}"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="additional_comments_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="additional_comments" />">
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

    <spring:url value="/api/positions_antibodies" var="positionsUrl"/>


	<script>

	jQuery(document).ready(function() {
		$("li.capture").addClass("open");
    	$("li.capture").addClass("active");
    	$("li.antibodies").addClass("active");
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

                        //¿Aquí iría el borrar contenido del formulario

                    }else{
                    //   $('#antibody_id').val('');
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
    $(document).ready(function(){
        $('#positions').prepend("<option value='1' selected = 'selected' > ${entidad.position_in_the_box} </option>");
        $('#positions').val("1");

    });
	</script>
</body>
</html>
