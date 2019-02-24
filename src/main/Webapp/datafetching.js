


//This do the Ajax request when the DOM is ready, not when the window's loading is done
//It would have been too long
document.onreadystatechange = function(e)
{
	if (document.readyState === 'complete')
	{
		//dom is ready, window.onload fires later
		//Doing all Ajax simultaneously
		//For the top page data
		$.when(
				$.getJSON("http://localhost:8080/graphql?query={allUsersCount}", { 
					format: "json"
				}),
				$.getJSON("http://localhost:8080/graphql?query={allRepositoryCount}", {
					format: "json"
				}),
				$.getJSON("http://localhost:8080/graphql?query={allOpenIssueCount}", {
					format: "json"
				}),
				$.getJSON("http://localhost:8080/graphql?query={allClosedIssueCount}", {
					format: "json"
				})).then(function (res1, res2, res3, res4) {
					$("#numberOfUsers").text(res1[0].data.allUsersCount.toLocaleString('en', {useGrouping:true}));
					$("#numberOfRepositories").text(res2[0].data.allRepositoryCount.toLocaleString('en', {useGrouping:true}));
					$("#numberOfOpenedIssues").text(res3[0].data.allOpenIssueCount.toLocaleString('en', {useGrouping:true}));
					$("#numberOfClosedIssues").text(res4[0].data.allClosedIssueCount.toLocaleString('en', {useGrouping:true}));

				});
		
		//For companies data
//		$.when(
//				$.getJSON("http://localhost:8080/graphql?query={allUsersCount}", { 
//					format: "json"
//				}),
//				$.getJSON("http://localhost:8080/graphql?query={allRepositoryCount}", {
//					format: "json"
//				}),
//				$.getJSON("http://localhost:8080/graphql?query={allOpenIssueCount}", {
//					format: "json"
//				}),
//				$.getJSON("http://localhost:8080/graphql?query={allClosedIssueCount}", {
//					format: "json"
//				})).then(function (res1, res2, res3, res4) {
//					$("#numberOfUsers").text(res1[0].data.allUsersCount.toLocaleString('en', {useGrouping:true}));
//					$("#numberOfRepositories").text(res2[0].data.allRepositoryCount.toLocaleString('en', {useGrouping:true}));
//					$("#numberOfOpenedIssues").text(res3[0].data.allOpenIssueCount.toLocaleString('en', {useGrouping:true}));
//					$("#numberOfClosedIssues").text(res4[0].data.allClosedIssueCount.toLocaleString('en', {useGrouping:true}));
//
//				});
	}
};

//window.onload = function () {

//}





