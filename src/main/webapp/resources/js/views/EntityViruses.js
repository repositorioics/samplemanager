var ProcessEntity = function () {

    return {
        //main function to initiate the module
        init: function (parametros) {
            language:parametros.lenguaje;
            var isRtl = $('html').attr('dir') === 'rtl';
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
            $(function() {
                $('.select2-control').each(function() {
                    $(this)
                        .wrap('<div class="position-relative"></div>')
                        .select2({
                            placeholder: parametros.seleccionar,
                            dropdownParent: $(this).parent(),
                            language:parametros.lenguaje
                        });
                })
            });

            $.validator.setDefaults( {
                submitHandler: function () {
                    processEntity();
                }
            } );
            jQuery.validator.addMethod("noSpace", function(value, element) {
                return value.indexOf(" ") < 0 && value != "";
            }, "Invalid");
            $( '#edit-form' ).validate( {
                rules: {
                    'systemId': {
                        required: true
                    },
                    'virusId' : {
                        required: true,
                        maxlength:50
                    },
                    'virus_Serotype': {
                        required: true,
                        maxlength:30
                    },
                    'strain': {
                        required: true,
                        maxlength:50
                    },
                    'batch_number': {
                        required: true,
                        maxlength:50
                    },
                    'passage': {
                        required: true
                    },
                    'experiment_id': {
                        required: true
                    },
                    'stage_of_production': {
                        required: true
                    },
                    'amount_of_Unconc_virus': {
                        required: true,
                        maxlength:50
                    },
                    'date_Uncon_Collected': {
                        required: true
                    },
                    'num_aliquots': {
                        required: true
                    },
                    'aliquot_volume': {
                        required: true
                    },
                    'date_of_Conc': {
                        required: true
                    },
                    'amount_of_Purified': {
                        required: true
                    },
                    'date_of_Purification': {
                        required: true
                    },
                    'qc_PCR_Check': {
                        required: true
                    },
                    'qc_ELISA_Check': {
                        required: true
                    },
                    'notes_on_QC': {
                        required: true
                    },
                    'num_of_collections': {
                        required: true
                    },
                    'day_Virus_Collected': {
                        required: true
                    },
                    'cell_line_and_passage': {
                        required: true
                    },
                    'viral_inoculum_vial_ID': {
                        required: true
                    },
                    'bca_Concentration': {
                        required: true
                    },
                    'fluorospot_check': {
                        required: true
                    },
                    'notes_on_FS_check': {
                        required: true
                    },
                    'viral_Titer': {
                        required: true
                    },
                    'storage_temperature': {
                        required: true
                    },
                    'comments': {
                        required: true,
                        maxlength:30
                    },
                    'loc_freezer': {
                        required: true,
                        maxlength:40
                    },
                    'loc_rack': {
                        required: true,
                        maxlength:10
                    },
                    'loc_box': {
                        required: true,
                        maxlength:40
                    },
                    'loc_pos': {
                        required: true,
                        maxlength:10
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

            $('#equip').change(
                function() {
                    $.blockUI({ message: parametros.waitmessage });

                    $.getJSON(parametros.racksUrl, {
                        equipId : $('#equip').val(),
                        ajax : 'true'
                    }, function(data) {
                        var html='<option value=""></option>';
                        var len = data.length;
                        for ( var i = 0; i < len; i++) {
                            html += '<option value="' + data[i].systemId + '">'
                                + data[i].name + '</option>';
                        }
                        $('#rack').html(html);
                        $('#rack').focus();
                        $('#rack').select2('open');
                    });
                    $.unblockUI();
                });

            $('#rack').change(
                function() {
                    $.blockUI({ message: parametros.waitmessage });

                    $.getJSON(parametros.boxesUrl, {
                        rackId : $('#rack').val(),
                        ajax : 'true'
                    }, function(data) {
                        var html='<option value=""></option>';
                        var len = data.length;
                        for ( var i = 0; i < len; i++) {
                            html += '<option value="' + data[i].systemId + '">'
                                + data[i].name + '</option>';
                        }
                        $('#boxSpecId').html(html);
                        $('#boxSpecId').focus();
                        $('#boxSpecId').select2('open');
                    });
                    $.unblockUI();
                });

            $('#boxSpecId').change(
                function() {
                    $.blockUI({ message: parametros.waitmessage });

                    $.getJSON(parametros.positionsUrl, {
                        boxId : $('#boxSpecId').val(),
                        ajax : 'true'
                    }, function(data) {
                        var html='<option value=""></option>';
                        var len = data.length;
                        for ( var i = 0; i < len; i++) {
                            html += '<option value="' + data[i] + '">'
                                + data[i] + '</option>';
                        }
                        $('#position').html(html);
                        //$('#position').focus();
                        //$('#position').select2('open');
                    });
                    $.unblockUI();
                });

            function processEntity(){
                $.blockUI({ message: parametros.waitmessage });

                $.post( parametros.saveUrl
                    , $( '#edit-form' ).serialize()
                    , function( data )
                    {
                        entidad = JSON.parse(data);
                        if (entidad.recordUser === undefined) {
                            data = data.replace(/u0027/g,"");

                            toastr.error(data, parametros.errormessage, {
                                closeButton: true,
                                progressBar: true
                            });
                            $.unblockUI();
                        }
                        else{
                            $.blockUI({ message: parametros.successmessage });
                            setTimeout(function() {
                                $.unblockUI({
                                    onUnblock: function(){
                                        if(parametros.editandosto){
                                            window.location.href = parametros.viewUrl;
                                        }

                                    }
                                });
                            }, 500);
                        }
                    }
                    , 'text' )
                    .fail(function(XMLHttpRequest, textStatus, errorThrown) {
                       alert( "error Viruses:" + errorThrown);
                        $.unblockUI();
                    });
            }


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
