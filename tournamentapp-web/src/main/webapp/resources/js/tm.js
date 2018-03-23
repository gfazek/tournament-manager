$(function(){
    $("#elimination").hide();
    $("#roundrobin").show();
});

function showTournamentType(type) {
    switch (type) {
        case "ROUNDROBIN":
            $("#elimination").hide();
            $("#roundrobin").show();
            break;
        case "ELIMINATION":
            $("#roundrobin").hide();
            $("#elimination").show();
            break;
        default :
            $("#roundrobin").hide();
            $("#elimination").hide();
    }
}
