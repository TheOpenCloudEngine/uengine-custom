mw3.importScript("https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.2.1/Chart.js");




var com_abc_widget_RadarChart = function(objId, className){


    var value = mw3.objects[objId];

    var ctx = document.getElementById("myChart_" + objId);
    var ctx2 = document.getElementById("myChart_" + objId).getContext("2d");
    var myRadarChart = new Chart(ctx2, {
        type: "radar",
        data: value,
        options: {
            scale: {
                reverse: false,
                ticks: {
                    beginAtZero: true
                }
            }
        }
    });

    $("#myChart_" + objId).bind("mousedown", function(){


        //var canvas = mw3.getAutowiredObject("org.uengine.modeling.Canvas");
        //canvas.__getFaceHelper().xxx();

        var symbol = mw3.getAutowiredObject("org.uengine.modeling.Symbol");
        var clipboard = mw3.getAutowiredObject("org.metaworks.widget.Clipboard");
        clipboard.content = symbol;

        //value.refresh();





        //var slave = mw3.getAutowiredObject("com.abc.widget.SlaveRadarChart");

        //slave.refresh();
    });



}