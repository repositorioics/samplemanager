var ProcessEntity = function () {

    return {
        //main function to initiate the module
        init: function (parametros) {
            language:parametros.lenguaje;
            var isRtl = $('html').attr('dir') === 'rtl';

            $('#date_transfection').datepicker({
                orientation: isRtl ? 'auto right' : 'auto left',
                format: 'yyyy-mm-dd',
                autoclose: true,
                language:parametros.lenguaje
            });

            $('#date_purification').datepicker({
                orientation: isRtl ? 'auto right' : 'auto left',
                format:'yyyy-mm-dd',
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
                    'protrecombinante_id1' : {
                        required: true,
                        maxlength:50
                    },
                    'protein_name2': {
                        required: true,
                        maxlength:20
                    },
                    'protein_origin3': {
                        required: true,
                        maxlength:12
                    },
                    'Construct_name4': {
                        required: true,
                        maxlength:10
                    },
                    'date_transfection': {
                        required: true
                    },
                    'vol_sn5': {
                        required: true,
                        maxlength:40
                    },
                    'pur_method6': {
                        required: true,
                        maxlength:30
                    },
                    'frac_retained7': {
                        required: true,
                        maxlength:30
                    },
                    'vol_usable8': {
                        required: true
                    },
                    'dialysis_buffer9': {
                        required: true,
                        maxlength:30
                    },
                    'date_purification': {
                        required: true
                    },
                    'num_aliquot10': {
                        required: true,
                        min:0,
                        max:2000
                    },
                    'vol_aliquot11': {
                        required: true,
                        min:0,
                        max:2000
                    },
                    'conc_protein12': {
                        required: true

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
                    },
                    'comments': {
                        required: false,
                        maxlength:40
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
                       alert( "error Recombinant Protein:" + errorThrown);
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
