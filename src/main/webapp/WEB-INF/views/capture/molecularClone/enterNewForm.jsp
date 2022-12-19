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
    <spring:url value="/capture/molecularClone/saveEntity/" var="saveUrl"></spring:url>
    <spring:url value="/capture/molecularClone/newEntity" var="newUrl"></spring:url>
    <spring:url value="/capture/molecularClone/" var="listUrl"></spring:url>
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
			                <div class="card-header-title"><spring:message code="molecularClone" /></div>
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
                                                        <input type="text" id="specimen_id" name="specimen_id"
                                                               data-toggle="tooltip" data-state="danger"
                                                               data-placement="right"
                                                               title="<spring:message code="specimen_id" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="specimen_id" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                        </div>
                                                        <input type="text" id="passage_number"
                                                               name="passage_number" data-toggle="tooltip"
                                                               data-state="danger" data-placement="right"
                                                               title="<spring:message code="passage_number_tool_tip" />"
                                                               class="form-control"
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
                                                        <input type="text" id="date_s" name="date_s" value="${fecEnr}"
                                                               data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="date" />"
                                                               class="form-control"  data-date-end-date="0d" placeholder="<spring:message code="date_s" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <label class="form-label"><spring:message code="General_Info" /></label>

                                            <div class="form-group">
                                                <label class="form-label"><spring:message code="type_of_culture" /></label> <span class="badge badge-dot badge-danger"></span>
                                                <select class="select2-control form-control" id="specimen_contidion" name="specimen_contidion" style="width: 100%" data-allow-clear="false">
                                                    <option value=""></option>
                                                    <option value="Non_concentrated">Non concentrated</option>
                                                    <option value="Concentrated">Concentrated</option>
                                                    <option value="Purified">Purified</option>
                                                </select>
                                            </div>

                                            <div class="form-group">
                                                <label class="form-label"><spring:message code="specimen_type" /></label> <span class="badge badge-dot badge-danger"></span>
                                                <select class="select2-control form-control" id="specimen_type" name="specimen_type" style="width: 100%" data-allow-clear="false">
                                                    <option value=""></option>
                                                    <option value="Clinical_Isolate">Clinical Isolate</option>
                                                    <option value="Infectious_Molecular_Clone">Infectious Molecular Clone (Wild Type, WT)</option>
                                                    <option value="Mutant">Mutant (IMC)</option>
                                                    <option value="Chimera">Chimera (IMC)</option>
                                                </select>
                                            </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="cell_line" name="cell_line"
                                                               data-toggle="tooltip" data-state="danger"
                                                               data-placement="right"
                                                               title="<spring:message code="cell_line_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="cell_line" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="experiment_id" name="experiment_id"
                                                               data-toggle="tooltip" data-state="primary"
                                                               data-placement="right"
                                                               title="<spring:message code="experiment_id_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="experiment_id" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="research_name" name="research_name"
                                                               data-toggle="tooltip" data-state="danger"
                                                               data-placement="right"
                                                               title="<spring:message code="research_name_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="research_name" />">
                                                    </div>
                                                </div>
                                            </div>

                                            <div id="clinical_isolate">

                                            <label class="form-label"><spring:message code="Specimen_Information" /></label>

                                            <div class="form-group">
                                                <label class="form-label"><spring:message code="genus" /></label> <span class="badge badge-dot badge-danger"></span>
                                                <select class="select2-control form-control" id="genus" name="genus" style="width: 100%" data-allow-clear="false">
                                                    <option value=""></option>
                                                    <option value="Flavivirus">Flavivirus</option>
                                                    <option value="Alphavirus">Alphavirus</option>
                                                    <option value="Coronavirus">Coronavirus</option>
                                                    <option value="Influenza_virus">Influenza virus</option>
                                                    <option value="Lentivirus">Lentivirus</option>
                                                    <option value="Other">Other</option>
                                                </select>
                                            </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="organism_name" name="organism_name"
                                                                   data-toggle="tooltip" data-state="danger"
                                                                   data-placement="right"
                                                                   title="<spring:message code="organism_name_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="organism_name" />">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="strain_name" name="strain_name"
                                                                   data-toggle="tooltip" data-state="danger"
                                                                   data-placement="right"
                                                                   title="<spring:message code="strain_name_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="strain_name" />">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="isolation_country" name="isolation_country"
                                                               data-toggle="tooltip" data-state="danger"
                                                               data-placement="right"
                                                               title="<spring:message code="isolation_country_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="isolation_country" />">
                                                    </div>
                                                </div>
                                            </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="collection_date" name="collection_date"
                                                                   data-toggle="tooltip" data-state="primary"
                                                                   data-placement="right"
                                                                   title="<spring:message code="collection_date_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="collection_date" />">
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>

                                            <div id="mutant">

                                                <label class="form-label"><spring:message code="Specimen_Information" /></label>

                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="genus_imc" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="genus_imc" name="genus_imc" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <option value="Flavivirus">Flavivirus</option>
                                                        <option value="Alphavirus">Alphavirus</option>
                                                        <option value="Coronavirus">Coronavirus</option>
                                                        <option value="Influenza_virus">Influenza virus</option>
                                                        <option value="Lentivirus">Lentivirus</option>
                                                        <option value="Other">Other</option>
                                                    </select>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="organism_name_imc" name="organism_name_imc"
                                                                   data-toggle="tooltip" data-state="danger"
                                                                   data-placement="right"
                                                                   title="<spring:message code="organism_name_imc_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="organism_name_imc" />">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="strain_name_imc" name="strain_name_imc"
                                                                   data-toggle="tooltip" data-state="danger"
                                                                   data-placement="right"
                                                                   title="<spring:message code="strain_name_imc_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="strain_name_imc" />">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="isolation_country_imc" name="isolation_country_imc"
                                                                   data-toggle="tooltip" data-state="danger"
                                                                   data-placement="right"
                                                                   title="<spring:message code="isolation_country_imc_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="isolation_country_imc" />">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="collection_date_imc" name="collection_date_imc"
                                                                   data-toggle="tooltip" data-state="primary"
                                                                   data-placement="right"
                                                                   title="<spring:message code="collection_date_imc_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="collection_date_imc" />">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="mutation_type_imc" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="mutation_type_imc" name="mutation_type_imc" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <option value="Substitution">Substitution</option>
                                                        <option value="Insertion">Insertion</option>
                                                        <option value="Deletion">Deletion</option>

                                                    </select>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="region" name="region"
                                                                   data-toggle="tooltip" data-state="danger"
                                                                   data-placement="right"
                                                                   title="<spring:message code="region_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="region" />">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="note" name="note"
                                                                   data-toggle="tooltip" data-state="danger"
                                                                   data-placement="right"
                                                                   title="<spring:message code="note_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="note" />">
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>


                                            <div id="chimera">

                                                <label class="form-label"><spring:message code="Specimen_Information" /></label>

                                                <label class="form-label"><spring:message code="Primary_sequence" /></label>

                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="genus" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="genus" name="genus" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <option value="Flavivirus">Flavivirus</option>
                                                        <option value="Alphavirus">Alphavirus</option>
                                                        <option value="Coronavirus">Coronavirus</option>
                                                        <option value="Influenza_virus">Influenza virus</option>
                                                        <option value="Lentivirus">Lentivirus</option>
                                                        <option value="Other">Other</option>
                                                    </select>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="organism" name="organism"
                                                                   data-toggle="tooltip" data-state="danger"
                                                                   data-placement="right"
                                                                   title="<spring:message code="organism_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="organism" />">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="strain_name" name="strain_name"
                                                                   data-toggle="tooltip" data-state="danger"
                                                                   data-placement="right"
                                                                   title="<spring:message code="strain_name_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="strain_name" />">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="region" name="region"
                                                                   data-toggle="tooltip" data-state="danger"
                                                                   data-placement="right"
                                                                   title="<spring:message code="region_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="region" />">
                                                        </div>
                                                    </div>
                                                </div>

                                                <label class="form-label"><spring:message code="Secundary_sequence" /></label>

                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="genus" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="genus" name="genus" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <option value="Flavivirus">Flavivirus</option>
                                                        <option value="Alphavirus">Alphavirus</option>
                                                        <option value="Coronavirus">Coronavirus</option>
                                                        <option value="Influenza_virus">Influenza virus</option>
                                                        <option value="Lentivirus">Lentivirus</option>
                                                        <option value="Other">Other</option>
                                                    </select>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="organism" name="organism"
                                                                   data-toggle="tooltip" data-state="danger"
                                                                   data-placement="right"
                                                                   title="<spring:message code="organism_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="organism" />">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="strain_name" name="strain_name"
                                                                   data-toggle="tooltip" data-state="danger"
                                                                   data-placement="right"
                                                                   title="<spring:message code="strain_name_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="strain_name" />">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="region" name="region"
                                                                   data-toggle="tooltip" data-state="danger"
                                                                   data-placement="right"
                                                                   title="<spring:message code="region_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="region" />">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="Additional_Mutation" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="Additional_Mutation" name="Additional_Mutation" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <option value="NO">NO</option>
                                                        <option value="YES">YES</option>

                                                    </select>
                                                </div>

                                                <div id="mutation_type_yes">
                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="Mutation_Type" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="mutation_type" name="mutation_type" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <option value="Substitution">Substitution</option>
                                                        <option value="Insertion">Insertion</option>
                                                        <option value="Deletion">Deletion</option>

                                                    </select>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="region" name="region"
                                                                   data-toggle="tooltip" data-state="primary"
                                                                   data-placement="right"
                                                                   title="<spring:message code="region_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="region" />">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                            </div>
                                                            <input type="text" id="note" name="note"
                                                                   data-toggle="tooltip" data-state="primary"
                                                                   data-placement="right"
                                                                   title="<spring:message code="note_tool_tip" />"
                                                                   class="form-control"
                                                                   placeholder="<spring:message code="note" />">
                                                        </div>
                                                    </div>
                                                </div>
                                                </div>

                                                <label class="form-label"><spring:message code="Quantification_Titration" /></label>

                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="Infectious_Unit" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="Infectious_Unit" name="Infectious_Unit" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <option value="NO">NO</option>
                                                        <option value="YES">YES</option>

                                                    </select>
                                                </div>

                                                <div id="Infectious_Unit_yes">

                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                                </div>
                                                                <input type="text" id="viral_titer" name="viral_titer"
                                                                       data-toggle="tooltip" data-state="danger"
                                                                       data-placement="right"
                                                                       title="<spring:message code="viral_titer_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="viral_titer" />">
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="form-label"><spring:message code="unit" /></label> <span class="badge badge-dot badge-danger"></span>
                                                        <select class="select2-control form-control" id="unit" name="unit" style="width: 100%" data-allow-clear="false">
                                                            <option value=""></option>
                                                            <option value="Substitution">FFU/mL</option>
                                                            <option value="Insertion">PFU/mL</option>

                                                        </select>
                                                    </div>

                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                                </div>
                                                                <input type="text" id="cell_line" name="cell_line"
                                                                       data-toggle="tooltip" data-state="danger"
                                                                       data-placement="right"
                                                                       title="<spring:message code="cell_line_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="cell_line" />">
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
                                                                <input type="text" id="date_infectious" name="date_infectious" value="${fecEnr}"
                                                                       data-toggle="tooltip" data-state="danger" data-placement="right" title="<spring:message code="date" />"
                                                                       class="form-control"  data-date-end-date="0d" placeholder="<spring:message code="date" />">
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>

                                                <label class="form-label"><spring:message code="Protein_quantification" /></label>

                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="Protein_quantification" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="Protein_quantification" name="Protein_quantification" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <option value="NO">NO</option>
                                                        <option value="YES">YES</option>

                                                    </select>
                                                </div>

                                                <div id="Protein_quantification_yes">

                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                                </div>
                                                                <input type="text" id="Concentration_protein" name="Concentration_protein "
                                                                       data-toggle="tooltip" data-state="danger"
                                                                       data-placement="right"
                                                                       title="<spring:message code="Concentration_protein_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="Concentration_protein" />">
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="form-label"><spring:message code="unit_protein" /></label> <span class="badge badge-dot badge-danger"></span>
                                                        <select class="select2-control form-control" id="unit_protein" name="unit_protein" style="width: 100%" data-allow-clear="false">
                                                            <option value=""></option>
                                                            <option value="mg/mL">mg/mL</option>
                                                            <option value="µg/mL">µg/mL</option>
                                                            <option value="ng/mL">ng/mL</option>

                                                        </select>
                                                    </div>

                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                                </div>
                                                                <input type="text" id="technique_protein" name="technique_protein"
                                                                       data-toggle="tooltip" data-state="danger"
                                                                       data-placement="right"
                                                                       title="<spring:message code="technique_protein_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="technique_protein" />">
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
                                                                <input type="text" id="date_protein" name="date_protein" value="${fecEnr}"
                                                                       data-toggle="tooltip" data-state="danger" data-placement="right" title="<spring:message code="date" />"
                                                                       class="form-control"  data-date-end-date="0d" placeholder="<spring:message code="date" />">
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>


                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="other_quantification" /></label> <span class="badge badge-dot badge-primary"></span>
                                                    <select class="select2-control form-control" id="other_quantification" name="other_quantification" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <option value="NO">NO</option>
                                                        <option value="YES">YES</option>

                                                    </select>
                                                </div>
                                                <div id="Other_yes">

                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                                </div>
                                                                <input type="text" id="technique_other" name="technique_other"
                                                                       data-toggle="tooltip" data-state="primary"
                                                                       data-placement="right"
                                                                       title="<spring:message code="technique_other_protein_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="technique_other" />">
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                                </div>
                                                                <input type="text" id="value_other" name="value_other"
                                                                       data-toggle="tooltip" data-state="primary"
                                                                       data-placement="right"
                                                                       title="<spring:message code="value_other_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="value_other" />">
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                                </div>
                                                                <input type="text" id="unit_other" name="unit_other"
                                                                       data-toggle="tooltip" data-state="primary"
                                                                       data-placement="right"
                                                                       title="<spring:message code="unit_other_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="unit_other" />">
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
                                                                <input type="text" id="date_other" name="date_other" value="${fecEnr}"
                                                                       data-toggle="tooltip" data-state="primary" data-placement="right" title="<spring:message code="date" />"
                                                                       class="form-control"  data-date-end-date="0d" placeholder="<spring:message code="date" />">
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>

                                                <label class="form-label"><spring:message code="Quality_Control_Status" /></label>

                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="Virus_Cross_contamination" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="Virus_Cross_contamination" name="Virus_Cross_contamination" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <option value="Not_performed">Not performed</option>
                                                        <option value="YES">Yes</option>

                                                    </select>
                                                </div>

                                                <div id="Virus_Cross_contamination_Unit_yes">

                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                                </div>
                                                                <input type="text" id="technique_Virus_Cross" name="technique_Virus_Cross"
                                                                       data-toggle="tooltip" data-state="danger"
                                                                       data-placement="right"
                                                                       title="<spring:message code="technique_Virus_Cross_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="technique_Virus_Cross" />">
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                                </div>
                                                                <input type="text" id="specimen_tested" name="specimen_tested"
                                                                       data-toggle="tooltip" data-state="danger"
                                                                       data-placement="right"
                                                                       title="<spring:message code="specimen_tested_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="specimen_tested" />">
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
                                                                <input type="text" id="date_Virus_Cross" name="date_Virus_Cross" value="${fecEnr}"
                                                                       data-toggle="tooltip" data-state="danger" data-placement="right" title="<spring:message code="date" />"
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
                                                                <input type="text" id="note_Virus_Cross" name="note_Virus_Cross"
                                                                       data-toggle="tooltip" data-state="danger"
                                                                       data-placement="right"
                                                                       title="<spring:message code="note_Virus_Cross_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="note_Virus_Cross" />">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>



                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="Mycoplasma" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="Mycoplasma" name="Mycoplasma" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <option value="Not_performed">Not performed</option>
                                                        <option value="YES">Yes</option>

                                                    </select>
                                                </div>

                                                <div id="Mycoplasma_yes">

                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                                </div>
                                                                <input type="text" id="technique_Mycoplasma" name="technique_Mycoplasma"
                                                                       data-toggle="tooltip" data-state="danger"
                                                                       data-placement="right"
                                                                       title="<spring:message code="technique_Mycoplasma_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="technique_Mycoplasma" />">
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
                                                                <input type="text" id="date_mycoplasma" name="date_mycoplasma" value="${fecEnr}"
                                                                       data-toggle="tooltip" data-state="danger" data-placement="right" title="<spring:message code="date" />"
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
                                                                <input type="text" id="note_mycoplasma" name="note_mycoplasma"
                                                                       data-toggle="tooltip" data-state="danger"
                                                                       data-placement="right"
                                                                       title="<spring:message code="note_mycoplasma_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="note_mycoplasma" />">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>


                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="Antigenic_Integrity	" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="Antigenic_Integrity" name="Antigenic_Integrity" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <option value="Not_performed">Not performed</option>
                                                        <option value="YES">Yes</option>

                                                    </select>
                                                </div>
                                                <div id="Antigenic_Integrity_yes">

                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                                </div>
                                                                <input type="text" id="technique_Antigenic_Integrity" name="technique_Antigenic_Integritya"
                                                                       data-toggle="tooltip" data-state="danger"
                                                                       data-placement="right"
                                                                       title="<spring:message code="technique_Antigenic_Integrity_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="technique_Antigenic_Integrity" />">
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                                </div>
                                                                <input type="text" id="specimen_tested_Antigenic_Integrity" name="specimen_tested_Antigenic_Integrity"
                                                                       data-toggle="tooltip" data-state="danger"
                                                                       data-placement="right"
                                                                       title="<spring:message code="specimen_tested_Antigenic_Integrity_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="specimen_tested_Antigenic_Integrity" />">
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
                                                                <input type="text" id="date_mycoplasma" name="date_mycoplasma" value="${fecEnr}"
                                                                       data-toggle="tooltip" data-state="danger" data-placement="right" title="<spring:message code="date" />"
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
                                                                <input type="text" id="note_Antigenic_Integrity" name="note_Antigenic_Integrity"
                                                                       data-toggle="tooltip" data-state="danger"
                                                                       data-placement="right"
                                                                       title="<spring:message code="note_Antigenic_Integrity_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="note_Antigenic_Integrity" />">
                                                            </div>
                                                        </div>
                                                    </div>


                                                </div>


                                                <div class="form-group">
                                                    <label class="form-label"><spring:message code="other_quality_control	" /></label> <span class="badge badge-dot badge-danger"></span>
                                                    <select class="select2-control form-control" id="other_quality_control" name="other_quality_control" style="width: 100%" data-allow-clear="false">
                                                        <option value=""></option>
                                                        <option value="Not_performed">Not performed</option>
                                                        <option value="YES">Yes</option>

                                                    </select>
                                                </div>
                                                <div id="other_quality_control_yes">

                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                                </div>
                                                                <input type="text" id="technique_other_quality_control" name="technique_other_quality_control"
                                                                       data-toggle="tooltip" data-state="primary"
                                                                       data-placement="right"
                                                                       title="<spring:message code="technique_other_quality_control_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="technique_other_quality_control" />">
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                                </div>
                                                                <input type="text" id="specimen_tested_other_quality_control" name="specimen_other_quality_control"
                                                                       data-toggle="tooltip" data-state="primary"
                                                                       data-placement="right"
                                                                       title="<spring:message code="specimen_tested_other_quality_control_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="specimen_tested_other_quality_control" />">
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
                                                                <input type="text" id="date_other_quality_control" name="date_other_quality_control" value="${fecEnr}"
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
                                                                <input type="text" id="note_other_quality_control" name="note_other_quality_control"
                                                                       data-toggle="tooltip" data-state="primary"
                                                                       data-placement="right"
                                                                       title="<spring:message code="note_Aother_quality_control_tool_tip" />"
                                                                       class="form-control"
                                                                       placeholder="<spring:message code="note_other_quality_control" />">
                                                            </div>
                                                        </div>
                                                    </div>


                                                </div>


                                            </div>


                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-numeric-up"></i></span>
                                                    </div>
                                                    <input type="text" id="Aliquot_Volume" name="Aliquot_Volume"
                                                           data-toggle="tooltip" data-state="danger"
                                                           data-placement="right" onkeypress="return valideKey(event);"
                                                           title="<spring:message code="Aliquot_Volume_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="Aliquot_Volume" />">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="buffer" name="buffer"
                                                           data-toggle="tooltip" data-state="danger"
                                                           data-placement="right"
                                                           title="<spring:message code="buffer_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="buffer" />">
                                                </div>
                                            </div>
                                        </div>

                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                        </div>
                                                        <input type="text" id="vial_id" name="vial_id"
                                                               data-toggle="tooltip" data-state="danger"
                                                               data-placement="right"
                                                               title="<spring:message code="vial_id_tool_tip" />"
                                                               class="form-control"
                                                               placeholder="<spring:message code="vial_id" />">
                                                    </div>
                                                </div>
                                            </div>

                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fa fa-sort-alpha-up"></i></span>
                                                    </div>
                                                    <input type="text" id="volume_mL" name="cap_color"
                                                           data-toggle="tooltip" data-state="danger"
                                                           data-placement="right"
                                                           title="<spring:message code="cap_color_tool_tip" />"
                                                           class="form-control"
                                                           placeholder="<spring:message code="cap_color" />">
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
    	$("li.molecularClone").addClass("active");
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
    $( "#clinical_isolate" ).hide();
    $( "#mutant" ).hide();
    $( "#chimera" ).hide();




    $('#specimen_type').change(function() {
        if($('#specimen_type').val()=="Clinical_Isolate") {
            $( "#clinical_isolate" ).show();
            $( "#mutant" ).hide();
            $( "#chimera" ).hide();
        }else{
            $( "#clinical_isolate" ).hide();

        }

        if($('#specimen_type').val()=="Infectious_Molecular_Clone") {
            $( "#clinical_isolate" ).show();
            $( "#mutant" ).hide();
            $( "#chimera" ).hide();
        }else{


        }

        if($('#specimen_type').val()=="Mutant") {
            $( "#mutant" ).show();
            $( "#clinical_isolate" ).hide();
            $( "#chimera" ).hide();

        }else{
            $( "#mutant" ).hide();

        }

        if($('#specimen_type').val()=="Chimera") {
            $( "#chimera" ).show();
            $( "#clinical_isolate" ).hide();
            $( "#mutant" ).hide();

        }else{
            $( "#chimera" ).hide();

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
