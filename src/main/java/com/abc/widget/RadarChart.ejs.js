var com_abc_widget_RadarChart = function(objId, className) {

    var value = mw3.objects[objId];

    var ctx = document.getElementById("myChart_" + objId);
    var ctx2 = document.getElementById("myChart_" + objId).getContext("2d");
    var myRadarChart = new Chart(ctx2, {
        type: "radar",
        data: value,
        options: {
            scale: {
                reverse: true,
                ticks: {
                    beginAtZero: true
                }
            }
        }
    });

}