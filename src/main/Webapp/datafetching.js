function convertJsonIntoArray(jsonArray){
	var resultArray = [];
	var arrayHeader = ['Languages','Number of Repositories'];
	resultArray.push(arrayHeader);
	for(var obj in jsonArray){
		var fieldArray = [];
		fieldArray.push(jsonArray[obj].name);
		fieldArray.push(jsonArray[obj].languageFrequency);
		resultArray.push(fieldArray);
	}
	return resultArray;
}

function drawLanguagesPieChart(array){
	google.charts.load("current", {packages:["corechart"]});
	google.charts.setOnLoadCallback(drawChart);
	function drawChart() {
		var data = google.visualization.arrayToDataTable(array);

		var options = {
				title: 'Languages on Github'
		};

		var chart = new google.visualization.PieChart(document.getElementById('langagesPieChart'));
		chart.draw(data, options);

	}
}



function drawRepoPerYearChart(){
	google.charts.load('current', {'packages':['corechart']});
	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {
		var data = google.visualization.arrayToDataTable([
			['Year', 'Sales', 'Expenses'],
			['2004',  1000,      400],
			['2005',  1170,      460],
			['2006',  660,       1120],
			['2007',  1030,      540]
			]);

		var options = {
				title: 'Company Performance',
				curveType: 'function',
				legend: { position: 'bottom' }
		};

		var chart = new google.visualization.LineChart(document.getElementById('repoPerYear'));

		chart.draw(data, options);

	}
}

//This do the Ajax request when the DOM is ready, not when the window's loading is done
//It would have been too long
document.onreadystatechange = function(e)
{
	if (document.readyState === 'complete')
	{


		$.ajax({
			contentType: "",
			async: true,
			method: "GET",
			url: "http://localhost:8080/graphql?query={someLanguages{name,languageFrequency}}",
			dataType: "json",
			success: function(response) {
				var usefulResponse = response.data.someLanguages;
				drawLanguagesPieChart(convertJsonIntoArray(usefulResponse));		

			}
		})


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
		//Populating a table

		$.ajax({
			contentType: "",
			async: true,
			method: "GET",
			url: "http://localhost:8080/graphql?query={someCompaniesData{companyName,numberOfMembers,numberOfRepositories,avarageCommitCount,totalRepositoriesDiskUsage}}",
			dataType: "json",
			success: function(response) {

				var table = document.getElementById('companiesTable');
				response.data.someCompaniesData.forEach(function(object) {
					var tr = document.createElement('tr');
					tr.className ="row100 head";
					tr.innerHTML = '<td>' + ' ' + object.companyName + '</td>' 
					+
					'<td>' + object.numberOfMembers + '</td>' +
					'<td>' + object.numberOfRepositories + '</td>' +
					'<td>' + object.avarageCommitCount + '</td>' 
					+'<td>' + object.totalRepositoriesDiskUsage.toLocaleString('en', {useGrouping:true}) + ' bytes'+'</td>'
					;
					table.appendChild(tr);
				});
			}
		})


	}
};

//window.onload = function () {

//}









