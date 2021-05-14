var SearchProcess = function () {
	
return {
  //main function to initiate the module
  init: function (parametros) {

      var isRtl = $('html').attr('dir') === 'rtl';
      var activeSearch = 0;
      var viewMess = parametros.viewMessage;
      var editMess = parametros.editMessage;

      $('#labReceiptDate').datepicker({
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
      'specimenId': {
          required: false
      },

      'labReceiptDate': {
          required: false
      },

      'box': {
          required: false
      },

        'studyId': {
            required: false
        },

        'orthocode': {
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
                      columns: [0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 ]
                  }
              },
              {
                  extend: 'pdfHtml5',
                  orientation: 'landscape',
                  pageSize: 'LEGAL',
                  exportOptions: {
                      columns: [0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 ]
                  }
              }
          ],

        ajax:{
            url: parametros.searchUrl, // Change this URL to where your json data comes from
            type: "GET", // This is the default value, could also be POST, or anything you want.
            data: function (d) {

                var filtros = {};
                filtros['specimenId'] = $('#specimenId').val();
                filtros['orthocode'] =  $('#orthocode').val();
                filtros['labReceiptDate'] = $('#labReceiptDate').val();
                filtros['studyId'] = $('#studyId option:selected').val();
                filtros['box'] = $('#box').val();
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

        columns: [{data: 'specimenId'},
            { data: 'labReceiptDate' },
            { data: 'specimenType' },
            { data: 'specimenCondition' },
            { data: 'varA' },
            { data: 'varB' },
            { data: 'volume' },
            { data: 'volUnits' },
            { data: 'subjectName',
                fnCreatedCell: function (nTd, sData, oData, iRow, iCol) {
                    var name = oData.subjectName;
                    var viewSubjectUrl = parametros.viewSubjectUrl + oData.subjectId + '/' ;
                    $(nTd).html('<a title= ' + name + ' href=' + viewSubjectUrl + ' > '+ name +'</a> ');
                }
            },
            { data: 'inStorage'},

            { data: 'orthocode' },
            { data: 'obs' },
            { data: 'recordDate' },
            { data: 'recordUser' },
            { data: 'recordIp' },
            { data: 'desPasive',
                fnCreatedCell: function (nTd, sData, oData, iRow, iCol) {
                    if(oData.pasive === 'Yes') {
                        console.log('entra para no');
                        $(nTd).html( '<span class="badge badge-success">' + oData.desPasive +'</span>');

                    } else {
                        console.log('entra para si');
                        $(nTd).html( '<span class="badge badge-danger">' + oData.desPasive +'</span>');
                    }
                }

            },
            { data: null,
                fnCreatedCell: function (nTd, sData, oData, iRow, iCol) {
                    var editUrl = parametros.editUrl + oData.systemId + '/' ;
                    var viewUrl = parametros.viewUrl + oData.systemId + '/';
                    $(nTd).html('<a data-toggle="tooltip" data-placement="bottom" title= ' + viewMess + ' href=' + viewUrl + ' class="btn btn-outline-primary btn-sm"><i class="fa fa-search"></i></a> <a data-toggle="tooltip" data-placement="bottom" title= ' + editMess + ' href=' + editUrl + ' class="btn btn-outline-primary btn-sm"><i class="fa fa-edit"></i></a>');
                }
            }
        ]



    });



      function search() {



          $.blockUI({message: parametros.waitmessage});
          $.getJSON(parametros.searchUrl, {
              strFilter: JSON.stringify(filtros),
              ajax: 'true'
          }, function (dataToLoad) {
              table1.clear();
              var len = Object.keys(dataToLoad).length;
              var viewMess = parametros.viewMessage;
              var editMess = parametros.editMessage;
                console.log(len);

              if (len > 0) {
                  for (var i = 0; i < len; i++) {
                      var editUrl = parametros.editUrl + dataToLoad[i].systemId + '/' ;
                      var viewUrl = parametros.viewUrl + dataToLoad[i].systemId + '/';
                      var span = null;
                      if (dataToLoad[i].enabled == 'Yes' ){
                           span = '<span class="badge badge-success">' + dataToLoad[i].enabled +'</span>';
                      }else{
                          span = '<span class="badge badge-danger">' + dataToLoad[i].enabled +' </span>';
                      }

                      table1.row.add([
                          dataToLoad[i].specimenId, dataToLoad[i].labReceiptDate, dataToLoad[i].specimenType, dataToLoad[i].specimenCondition, dataToLoad[i].varA, dataToLoad[i].varB, dataToLoad[i].volume, dataToLoad[i].volUnits, dataToLoad[i].studyId, dataToLoad[i].inStorage,
                          dataToLoad[i].orthocode, dataToLoad[i].obs, dataToLoad[i].recordDate, dataToLoad[i].recordUser, dataToLoad[i].recordIp, span,
                          '<a data-toggle="tooltip" data-placement="bottom" title= ' + viewMess + ' href=' + viewUrl + ' class="btn btn-outline-primary btn-sm"><i class="fa fa-search"></i></a> <a data-toggle="tooltip" data-placement="bottom" title= ' + editMess + ' href=' + editUrl + ' class="btn btn-outline-primary btn-sm"><i class="fa fa-edit"></i></a> '
                      ]).draw();

                 /*     table1.fnAddData(
                          [dataToLoad[i].specimenId, dataToLoad[i].labReceiptDate, dataToLoad[i].specimenType, dataToLoad[i].specimenCondition, dataToLoad[i].varA, dataToLoad[i].varB, dataToLoad[i].volume, dataToLoad[i].volUnits, dataToLoad[i].studyId, dataToLoad[i].inStorage,
                              dataToLoad[i].orthocode, dataToLoad[i].obs, dataToLoad[i].recordDate, dataToLoad[i].recordUser, dataToLoad[i].recordIp, span,
                              '<a data-toggle="tooltip" data-placement="bottom" title= ' + viewMess + ' href=' + viewUrl + ' class="btn btn-outline-primary btn-sm"><i class="fa fa-search"></i></a> <a data-toggle="tooltip" data-placement="bottom" title= ' + editMess + ' href=' + editUrl + ' class="btn btn-outline-primary btn-sm"><i class="fa fa-edit"></i></a> ']);
                 */
                  }
                  console.log("Salida");

                  $.blockUI({message: parametros.successmessage});
              } else {
                  $.blockUI({message: parametros.notfound});
              }

          })

      .fail(function(XMLHttpRequest, textStatus, errorThrown) {
              alert( "error:" + errorThrown);
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
