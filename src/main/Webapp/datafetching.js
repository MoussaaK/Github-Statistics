window.onload = function () {
	var resource_url = "http://localhost:8080/graphql?query={allUsersCount}";
	$("document").ready(function(){
		$.ajax({
			contentType: "",
			async: true,
			method: "GET",
			url: resource_url,
			dataType: "json",
			success: function(response) {
				$("#numberOfUsers").text(response.data.allUsersCount.toString());
			}
		})
	})
}





