function showTournamentType(type) {
    switch (type) {
        case "RoundRobin":
            $("#roundrobin").show();
            break;
        case "Elimination":
            $("#roundrobin").hide();
            break;
        default :
            $("#roundrobin").hide();
    }
}