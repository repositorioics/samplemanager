var SearchProcess = function () {

    return {
        //main function to initiate the module
        init: function (parametros) {

            var isRtl = $('html').attr('dir') === 'rtl';
            var activeSearch = 0;
            var viewMess = parametros.viewMessage;
            var editMess = parametros.editMessage;

            $('#date_Uncon_Collected').datepicker({
                orientation: isRtl ? 'auto right' : 'auto left',
                format: 'yyyy-mm-dd',
                autoclose: true,
                language:parametros.lenguaje
            });

            $('#date_of_Conc').datepicker({
                orientation: isRtl ? 'auto right' : 'auto left',
                format: 'yyyy-mm-dd',
                autoclose: true,
                language:parametros.lenguaje
            });

            $('#date_of_Purification').datepicker({
                orientation: isRtl ? 'auto right' : 'auto left',
                format: 'yyyy-mm-dd',
                autoclose: true,
                language:parametros.lenguaje
            });

            // Select2
            $(function () {
                $('.select2-control').each(function () {
                    $(this)
                        .select2({
                            placeholder: parametros.seleccionar,
                            dropdownParent: $(this).parent(),
                            language: parametros.lenguaje
                        });
                })
            });

            $.validator.setDefaults( {
                submitHandler: function () {
                    activeSearch = 1;
                    table1.ajax.reload();

                }
            } );
            jQuery.validator.addMethod("noSpace", function(value, element) {
                return value.indexOf(" ") < 0 && value != "";
            }, "Invalid");
            $( '#search-form' ).validate( {
                rules: {
                'virusId;': {
                    required: false,
                    maxlength:50
                },

                'experiment_id': {
                    required: false
                },
                    'virus_Serotype': {
                        required: false,
                        maxlength:30
                    },

                    'aliquot_volume': {
                        required: false,
                        maxlength:30
                    },

                    'num_aliquots': {
                        required: false,
                        maxlength:30
                    }

                },
                // Errors
                //

                errorPlacement: function errorPlacement(error, element) {
                    var $parent = $(element).parents('.form-group');

                    // Do not duplicate errors
                    if ($parent.find('.jquery-validation-error').length) { return; }

                    $parent.append(
                        error.addClass('jquery-validation-error small form-text invalid-feedback')
                    );
                },
                highlight: function(element) {
                    var $el = $(element);
                    var $parent = $el.parents('.form-group');

                    $el.addClass('is-invalid');

                    // Select2 and Tagsinput
                    if ($el.hasClass('select2-hidden-accessible') || $el.attr('data-role') === 'tagsinput') {
                        $el.parent().addClass('is-invalid');
                    }
                },
                unhighlight: function(element) {
                    $(element).parents('.form-group').find('.is-invalid').removeClass('is-invalid');
                }
            });

            var table1 =  $('#lista_entidades').DataTable({
                dom: 'lBfrtip',
                "oLanguage": {
                    "sUrl": parametros.dataTablesLang
                },
                "bFilter": true,
                "bInfo": true,
                "bPaginate": true,
                "bDestroy": true,
                "responsive": true,
                "pageLength": 10,
                "bLengthChange": true,
                "responsive": true,
                "buttons": [
                    {
                        extend: 'excel',
                        exportOptions: {
                            columns: [0, 1, 2, 4, 5, 6, 7, 8,9,10,11,12,13,14]
                        }
                    },
                    {
                        extend: 'pdf',
                        orientation: 'landscape',
                        pageSize: 'LEGAL',
                        exportOptions: {
                            columns: [0, 1, 2, 4, 5, 6, 7, 8,9,10,11,12,13,14]
                        }
                    }
                ],

                ajax:{
                    url: parametros.searchUrl, // Change this URL to where your json data comes from
                    type: "GET", // This is the default value, could also be POST, or anything you want.

                    data: function (d) {

                        var filtros = {};
                        filtros['rvpId'] = $('#rvpId').val();
                        filtros['num_aliq'] =  $('#num_aliq').val();
                        filtros['virusSerotype'] =  $('#virusSerotype').val();
                        filtros['batchName'] =  $('#batchName').val();


                        filtros['activeSearch'] = activeSearch;
                        d.strFilter = JSON.stringify(filtros);
                        return d;
                    },

                    beforeSend: function () {
                        $.blockUI({message: parametros.waitmessage});
                    },

                    complete: function () {
                        $.unblockUI();
                    },

                    contentType: "application/json",
                    dataSrc: ""


                },

                columns: [
                    {data: 'virusId'},
                    { data: 'virus_Serotype' },
                    { data: 'strain'},
                    { data: 'batch_number'},
                    { data: 'passage'},
                    { data: 'experiment_id' },
                    { data: 'stage_of_production' },
                    { data: 'amount_of_Unconc_virus' },
                    { data: 'date_Uncon_Collected'},
                    { data: 'num_aliquots'},
                    { data: 'aliquot_volume'},

                    { data: 'amount_of_Conc_virus'},
                    { data: 'date_of_Conc'},
                    { data: 'amount_of_Purified'},
                    { data: 'date_of_Purification'},
                    { data: 'qc_PCR_Check'},
                    { data: 'qc_ELISA_Check'},
                    { data: 'notes_on_QC'},
                    { data: 'num_of_collections'},
                    { data: 'day_Virus_Collected'},
                    { data: 'cell_line_and_passage'},
                    { data: 'viral_inoculum_vial_ID'},
                    { data: 'bca_Concentration'},
                    { data: 'fluorospot_check'},
                    { data: 'notes_on_FS_check'},
                    { data: 'viral_Titer'},
                    { data: 'storage_temperature'},


                    { data: 'comments'},
                    { data: 'loc_freezer' },
                    { data: 'loc_rack' },
                    { data: 'loc_box' },
                    { data: 'loc_pos' },

                    { data: null,
                        fnCreatedCell: function (nTd, sData, oData, iRow, iCol) {
                            var editUrl = parametros.editUrl + oData.systemId + '/' ;
                            // var viewUrl = parametros.viewUrl + oData.systemId + '/';
                            //   $(nTd).html('<a data-toggle="tooltip" data-placement="bottom" title= ' + viewMess + ' href=' + viewUrl + ' class="btn btn-outline-primary btn-sm"><i class="fa fa-search"></i></a> <a data-toggle="tooltip" data-placement="bottom" title= ' + editMess + ' href=' + editUrl + ' class="btn btn-outline-primary btn-sm"><i class="fa fa-edit"></i></a>');
                            $(nTd).html('<a data-toggle="tooltip" data-placement="bottom" title= '+' ></a> <a data-toggle="tooltip" data-placement="bottom" title= ' + editMess + ' href=' + editUrl + ' class="btn btn-outline-primary btn-sm"><i class="fa fa-edit"></i></a>');
                        }

                    }

                ]

            }
            );

            $('#box').on("input",
                function () {
                    if ($(this).val().length > 0) {
                        $.getJSON(parametros.boxNameUrl, {
                                ajax: 'true',
                                box : $(this).val()
                            }, function (dataToLoad) {
                                console.log(dataToLoad);
                                $( "#box" ).autocomplete({
                                    source: dataToLoad,
                                    minLength: 3
                                });
                                $.unblockUI();
                            }
                        ).fail(function(jqXHR) {
                                $.unblockUI();
                            });

                    }
                });



            $(document).on('keypress','form input',function(event)
            {
                event.stopImmediatePropagation();
                if( event.which == 13 )
                {
                    event.preventDefault();
                    var $input = $('form input');
                    if( $(this).is( $input.last() ) )
                    {
                        //Time to submit the form!!!!
                        //alert( 'Hooray .....' );
                    }
                    else
                    {
                        $input.eq( $input.index( this ) + 1 ).focus();
                    }
                }
            });
        }
    };
}();
