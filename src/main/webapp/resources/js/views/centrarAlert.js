/**
 * Created by Dell on 6/8/2021.
 */
$("<style type='text/css'>#boxMX{display:none;background: #333;padding: 10px;border: 2px solid #ddd;float: left;font-size: 1.2em;position: fixed;top: 50%; left: 50%;z-index: 99999;box-shadow: 0px 0px 20px #999; -moz-box-shadow: 0px 0px 20px #999; -webkit-box-shadow: 0px 0px 20px #999; border-radius:6px 6px 6px 6px; -moz-border-radius: 6px; -webkit-border-radius: 6px; font:13px Arial, Helvetica, sans-serif; padding:6px 6px 4px;width:300px; color: white;}</style>").appendTo("head");

function alertMX(t){
    $( "body" ).append( $( "<div id='boxMX'><p class='msgMX'></p><p>CLOSE</p></div>" ) );
    $('.msgMX').text(t); var popMargTop = ($('#boxMX').height() + 24) / 2, popMargLeft = ($('#boxMX').width() + 24) / 2;
    $('#boxMX').css({ 'margin-top' : -popMargTop,'margin-left' : -popMargLeft}).fadeIn(600);
    $("#boxMX").click(function() { $(this).remove(); });  };
