<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="default-style">
<head>
  <jsp:include page="../../fragments/headTag.jsp" />

</head>
<body>
	<div class="page-loader">
    	<div class="bg-primary"></div>
  	</div>
  	<!-- urls -->
    <spring:url value="/capture/specimens/saveEntity/" var="saveUrl"></spring:url>
    <spring:url value="/capture/specimens/newEntity" var="newUrl"></spring:url>	
    <spring:url value="/capture/specimens/" var="listUrl"></spring:url>	
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
			                <div class="card-header-title"><spring:message code="specimens" /></div>
			              </h6>
			              <div>
			                <div class="col-md-12 col-lg-12 col-xl-12">
			                  <div class="card-body demo-vertical-spacing">
			                  		<div class="row">

				                    <div class="col-md-8">
				                      <form action="#" autocomplete="off" id="edit-form">
				                      	<div class="form-group">
					                    </div>                      
										<div class="form-group">
					                      <div class="input-group">
					                      	<div class="input-group-prepend">
							                  <span class="input-group-text"><i class="fa fa-key"></i></span>
							                </div>
					                        <input type="text" id="specimenId" name="specimenId"
					                        	data-toggle="tooltip" data-state="danger" data-placement="right" title="<spring:message code="specimenId" />" 
					                        		class="form-control" placeholder="<spring:message code="specimenId" />">
					                      </div>
					                    </div>	
					                    
					                    <div class="form-group">
					                      <div class="input-group">
					                      	<div class="input-group-prepend">
							                  <span class="input-group-text"><i class="fa fa-calendar-alt"></i></span>
							                </div>
							                <fmt:formatDate value="${now}" var="fecEnr" pattern="yyyy-MM-dd" />
					                        <input type="text" id="labReceiptDate" name="labReceiptDate" value="${fecEnr}"  
					                        	data-toggle="tooltip" data-state="danger" data-placement="right" title="<spring:message code="labReceiptDate" />" 
					                        		class="form-control"  data-date-end-date="0d" placeholder="<spring:message code="labReceiptDate" />">
					                      </div>
					                    </div>
					                    
					                    <div class="form-group">
					                    	<label class="form-label"><spring:message code="specimenType" /></label> <span class="badge badge-dot badge-danger"></span>
						                	<select class="select2-control form-control" id="specimenType" name="specimenType" style="width: 100%" data-allow-clear="false">
						                		<option value=""></option>
					                        	<c:forEach items="${types}" var="type">
													<option value="${type.catKey}"><spring:message code="${type.messageKey}" /></option>
												</c:forEach>
						                	</select>
					                    </div>
					                    
					                    <div class="form-group">
					                    	<label class="form-label"><spring:message code="specimenCondition" /></label>
						                	<select class="select2-control form-control" id="specimenCondition" name="specimenCondition" style="width: 100%" data-allow-clear="true">
						                		<option value=""></option>
					                        	<c:forEach items="${conditions}" var="condition">
													<option value="${condition.catKey}"><spring:message code="${condition.messageKey}" /></option>
												</c:forEach>
						                	</select>
					                    </div>
					                    
					                    <div class="form-group">
					                      <div class="input-group">
					                      	<div class="input-group-prepend">
							                  <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
							                </div>
					                        <input type="text" id="varA" name="varA"
					                        	data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="varA" />" 
					                        		class="form-control" placeholder="<spring:message code="varA" />">
					                      </div>
					                    </div>
					                    
					                    <div class="form-group">
					                      <div class="input-group">
					                      	<div class="input-group-prepend">
							                  <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
							                </div>
					                        <input type="text" id="varB" name="varB"
					                        	data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="varB" />" 
					                        		class="form-control" placeholder="<spring:message code="varB" />">
					                      </div>
					                    </div>
					                    
					                    
					                    <div class="form-group">
					                      <div class="input-group">
					                      	<div class="input-group-prepend">
							                  <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
							                </div>
					                        <input type="text" id="volume" name="volume"
					                        	data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="volume" />" 
					                        		class="form-control" placeholder="<spring:message code="volume" />">
					                      </div>
					                    </div>
					                    
					                    
					                    <div class="form-group">
					                    	<label class="form-label"><spring:message code="volUnits" /></label>
						                	<select class="select2-control form-control" id="volUnits" name="volUnits" style="width: 100%" data-allow-clear="false">
						                		<option  value="" ></option>
					                        	<c:forEach items="${volUnits}" var="volUnit">
													<option   value="${volUnit.catKey}"> <spring:message code="${volUnit.messageKey}"  />  </option>
												</c:forEach>
						                	</select>
					                    </div>



					                    <div class="form-group">
					                    	<label class="form-label"><spring:message code="studyId" /></label>
						                	<select  class="select2-control form-control" id="subjectSpecId" name="subjectSpecId" style="width: 100%" data-allow-clear="true">
						                		<option value=""></option>
					                        	<c:forEach items="${subjects}" var="subject">
													<option value="${subject.systemId}"><spring:message code="subjectId" />: ${subject.subjectId} <spring:message code="study" />: ${subject.studyId.name}</option>
												</c:forEach>

						                	</select>
					                    </div>

										  <div class="form-group">
											  <label class="form-label"><spring:message code="substudy" /></label>
											  <select class="select2-control form-control" id="substudy" name="substudy" style="width: 100%" data-allow-clear="true">
												  <option value=""></option>
												  <c:forEach items="${substudies}" var="substudy">
													  <option value="${substudy.catKey}"><spring:message code="${substudy.messageKey}" /></option>
												  </c:forEach>
											  </select>
										  </div>
					                    
					                    <div class="form-group">
					                      <div class="input-group">
					                      	<div class="input-group-prepend">
							                  <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
							                </div>
					                        <input type="text" id="orthocode" name="orthocode"
					                        	data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="orthocode" />" 
					                        		class="form-control" placeholder="<spring:message code="orthocode" />">
					                      </div>
					                    </div>
					                    
					                    <div class="form-group">
					                      <div class="input-group">
					                      	<div class="input-group-prepend">
							                  <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
							                </div>
					                        <input type="text" id="obs" name="obs"
					                        	data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="obs" />" 
					                        		class="form-control" placeholder="<spring:message code="obs" />">
					                      </div>
					                    </div>
					                    
					                    <div class="form-group">
					                    	<label class="form-label"><spring:message code="inStorage" /></label> <span class="badge badge-dot badge-danger"></span>
					                    </div>
					                    
					                    <div class="select2-primary">
						                	<select class="select2-control form-control" id="inStorage" name="inStorage" style="width: 100%" data-allow-clear="false">
						                		<option value=""></option>
					                        	<c:forEach items="${sinos}" var="sino">
													<option value="${sino.catKey}"><spring:message code="${sino.messageKey}" /></option>
												</c:forEach>
						                	</select>
					                    </div>
					                    		
					                    <div class="form-group">
					                    </div>
					                    
					                    <div id="storageDateDiv">
						                    <div class="form-group">
						                      <div class="input-group">
						                      	<div class="input-group-prepend">
								                  <span class="input-group-text"><i class="fa fa-calendar-alt"></i></span>
								                </div>
								                <fmt:formatDate value="${now}" var="fecSto" pattern="yyyy-MM-dd" />
						                        <input type="text" id="storageDate" name="storageDate" value="${fecSto}"  
						                        	data-toggle="tooltip" data-state="danger" data-placement="right" title="<spring:message code="storageDate" />" 
						                        		class="form-control"  data-date-end-date="0d" placeholder="<spring:message code="storageDate" />">
						                      </div>
						                    </div>
						                    
						                    <div class="form-group">
						                    	<label class="form-label"><spring:message code="equip" /></label> <span class="badge badge-dot badge-danger"></span>
							                	<select class="select2-control form-control" id="equip" name="equip" style="width: 100%" data-allow-clear="false">
							                		<option value=""></option>
						                        	<c:forEach items="${equips}" var="equip">
														<option value="${equip.systemId}"><spring:message code="equip" />: ${equip.id} - ${equip.name}</option>
													</c:forEach>
							                	</select>
						                    </div>
						                    
						                    <div class="select2-primary">
						                    	<label class="form-label"><spring:message code="rack" /></label> <span class="badge badge-dot badge-danger"></span>
							                	<select class="select2-control form-control" id="rack" name="rack" style="width: 100%" data-allow-clear="false">
							                		<option value=""></option>
							                	</select>
						                    </div>
						                    
						                    <div class="form-group">
						                    	<label class="form-label"><spring:message code="box" /></label> <span class="badge badge-dot badge-danger"></span>
							                	<select class="select2-control form-control" id="boxSpecId" name="boxSpecId" style="width: 100%" data-allow-clear="false">
							                		<option value=""></option>
							                	</select>
						                    </div>
						                    
						                    <div class="form-group">
						                    	<label class="form-label"><spring:message code="pos" /></label> <span class="badge badge-dot badge-danger"></span>
							                	<select class="select2-control form-control" id="position" name="position" style="width: 100%" data-allow-clear="false">
							                		<option value=""></option>
							                	</select>
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
  <spring:url value="/resources/js/views/EntitySpecimen.js" var="processEntity" />
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
    	$("li.specimens").addClass("active");
		var parametros = {saveUrl: "${saveUrl}", successmessage: "${successmessage}",
				errormessage: "${errormessage}",waitmessage: "${waitmessage}",
				listUrl: "${listUrl}" ,seleccionar: "${seleccionar}" ,lenguaje: "${lenguaje}",
				racksUrl: "${racksUrl}" ,boxesUrl: "${boxesUrl}" ,agregando: "${agregando}" ,positionsUrl: "${positionsUrl}"
		};
		ProcessEntity.init(parametros);


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


    $(function(){
        $("#volUnits").val ('ul');
        $('#volUnits').trigger('change');


    });

  	$('#specimenId').focus();
	</script>
    <script>
        document.ready = document.getElementById("volUnits").value = 'ul';
    </script>
</body>
</html>
