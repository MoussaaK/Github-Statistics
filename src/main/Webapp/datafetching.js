
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
var resource_url = "http://localhost:8080/graphql?query={allUsersCount}";
$.ajax({
	contentType: "",
	async: false,
	method: "GET",
	url: resource_url,
	dataType: "json",
	success: function(data) {
		console.log(data);
		$("numberOfUsers").

</script>


