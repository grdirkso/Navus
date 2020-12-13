    function displayRoute() {

        var start = new google.maps.LatLng("insert start destination here");

        // Set to jbhunt
        var end = new google.maps.LatLng(36.06712139893772, 94.17374858900389);

        var directionsDisplay = new google.maps.DirectionsRenderer();// also, constructor can get "DirectionsRendererOptions" object
        directionsDisplay.setMap(map); // map should be already initialized.

        var request = {
                origin : start,
                destination : end,
                travelMode : google.maps.TravelMode.DRIVING
    };
        var directionsService = new google.maps.DirectionsService();
        directionsService.route(request, function(response, status) {
            if (status == google.maps.DirectionsStatus.OK) {
                directionsDisplay.setDirections(response);
            }
        });
    }
