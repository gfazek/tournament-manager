function showTournamentType(type) {
    switch (type) {
        case "ROUNDROBIN":
            $("#roundrobin").show();
            break;
        case "ELIMINATION":
            $("#roundrobin").hide();
            break;
        default :
            $("#roundrobin").hide();
    }
}