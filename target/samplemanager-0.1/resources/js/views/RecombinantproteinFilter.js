var SearchProcess = function () {

    return {
        //main function to initiate the module
        init: function (parametros) {

            var isRtl = $('html').attr('dir') === 'rtl';
            var activeSearch = 0;
            var viewMess = parametros.viewMessage;
            var editMess = parametros.editMessage;

            $('#date_transfection').datepicker({
                orientation: isRtl ? 'auto right' : 'auto left',
                format: 'yyyy-mm-dd',
                autoclose: true,
                language: parametros.language
            });
            $('#date_purification').datepicker({
                orientation: isRtl ? 'auto right' : 'auto left',
                format: 'yyyy-mm-dd',
                autoclose: true,
                language: parametros.language
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
                'protrecombinante_id': {
                    required: false
                },

                'protein_name': {
                    required: false
                },
                    'protein_origin': {
                        required: false
                    },
                    'Construct_name': {
                        required: false
                    },
                    'date_transfection': {
                        required: false
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
                            columns: [0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,16,17,18 ]
                        }
                    },
                    {
                        extend: 'pdf',
                        orientation: 'landscape',
                        pageSize: 'LEGAL',
                        exportOptions: {
                            columns: [0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,16,17,18]
                        }
                    }
                ],

                ajax:{
                    url: parametros.searchUrl, // Change this URL to where your json data comes from
                    type: "GET", // This is the default value, could also be POST, or anything you want.

                    data: function (d) {

                        var filtros = {};
                        filtros['protrecombinante_id'] = $('#protrecombinante_id').val();
                        filtros['protein_name'] =  $('#protein_name').val();
                        filtros['protein_origin'] =  $('#protein_origin').val();
                        filtros['Construct_name'] =  $('#Construct_name').val();
                        filtros['date_transfection'] =  $('#date_transfection').val();

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

                columns: [{data: 'protrecombinante_id'},
                    { data: 'protein_name' },
                    { data: 'protein_origin'},
                    { data: 'construct_name'},
                    { data: 'date_transfection'},
                    { data: 'vol_sn' },
                    { data: 'pur_method' },
                    { data: 'frac_retained' },
                    { data: 'vol_usable'},
                    { data: 'dialysis_buffer'},
                    { data: 'date_purification' },
                    { data: 'num_aliquot' },
                    { data: 'vol_aliquot' },
                    { data: 'conc_protein' },
                    { data: 'loc_freezer' },
                    { data: 'loc_rack' },
                    { data: 'loc_box' },
                    { data: 'loc_pos' },

                    { data: 'comments' },

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
