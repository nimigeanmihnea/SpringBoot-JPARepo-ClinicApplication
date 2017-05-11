(function poll() {
    setTimeout(function() {
        $.ajax({
            url: "/home/polling",
            type: "POST",
            success: function() {
                alert("Your next patient has arrived");
            },
            dataType: "json",
            complete: poll,
            timeout: 2000
        })
    }, 5000);
})();
