/**
 * 
 */
$(document).ready(function () {
	var ctx = document.getElementById("buildingStats");
	fetch("/building/").then(
		res => {
			res.json().then(building => {
				if (building.length > 0) {

					var buildingChartData = []
					buildingLabels = ["Building Name", "Proper Masks", "Improper Masks", "No Masks"]
					buildingChartData.push(buildingLabels)

					for (i in building) {
						var buildingData = []
						buildingData.push(building[i].buildingName)
						buildingData.push(building[i].noOfProperMaskInBuilding)
						buildingData.push(building[i].noOfImproperMaskInBuilding)
						buildingData.push(building[i].noOfNoMaskInBuilding)
//						$('#side-buildings').append('<li><a href="/building/grid/'+building[i].id+'" class="collapsible-header waves-effect"><i class="far fa-building"></i>'+building[i].buildingName+'</a></li>')
						buildingChartData.push(buildingData)

					}

					google.charts.load('current', { 'packages': ['corechart'] });
					google.charts.setOnLoadCallback(drawVisualization);

					function drawVisualization() {

						// FOR BUILDINGS
						var building_chart = new google.visualization.ColumnChart(ctx);
						var building_data = google.visualization.arrayToDataTable(buildingChartData);
						// console.log(buildingChartData);

						var options = {
							vAxis: { title: 'People', textStyle: { fontSize: 18 } },
							hAxis: { title: 'Buildings', textStyle: { fontSize: 18 } },
							seriesType: 'bars',
							series: { 3: { type: 'line' } },
							backgroundColor: { fill: 'transparent' }
						};
						building_chart.draw(building_data, options);
					}
				}
			})
			$("#content").fadeIn('slow');
		}
	)
	$('#preloader').fadeOut('slow');
})
