var floor_Name;
var wing_name;
$(document).ready(function () {

	$.ajax({
		type: "get",
		url: "/building/" + building_id,
		success: function (building) {
			console.log(building)
			$('#buildingName').html(building.buildingName)
			
			$('#floors > option').remove();
			$('#floor').append('<option value="" disabled selected>Select Floor</option>')
			building.floorList.forEach(floor => {
				$('#floors').append("<option value='" + floor.floorName + "'>" + floor.floorName + "</option>")
			})
			
			for (const floor in building.floorList) {
				var floorname = building.floorList[floor].floorName.split(" ");

				var floorrow = '<tr><td class="py px-floor"><h4> ' + building.floorList[floor].floorName + ' </h4></td></tr>'
				$("#floornames").prepend(floorrow)
				var row = "<tr id='" + floorname[1] + "'>"
				$('#grid').prepend(row);


				building.floorList[floor].wingList.forEach(element => {
					var total = element.noOfProperMaskOnWing + element.noOfNoMaskOnWing + element.noOfImproperMaskOnWing;
					var wing_name = element.wingName.replace(" ","_")
					if (total == 0) {
						var td = '<td class="py px-grid '+wing_name+'"><h5>'+element.wingName+'</h5></td>'
						$('tr#' + floorname[1]).append(td)

					}
					else {
						var td = '<td class="py px-grid safe ' + wing_name + ' "><h5>' + element.wingName + '</h5></td>'
						$('tr#' + floorname[1]).append(td)
						var risk = element.noOfNoMaskOnWing + element.noOfImproperMaskOnWing;

						var threshold_unsafe = 0.7 * total;
						if (risk >= threshold_unsafe) {
							$('#' + floorname[1] + ' .' + wing_name).removeClass('safe')
							$('#' + floorname[1] + ' .' + wing_name).addClass('unsafe')
						}
					}
				})
				var addwingbuttonrow = '<td class="px-grid"><i data-mdb-toggle="tooltip" title="Click to add Wing" class="fas fa-plus-circle addWing" style="font-size:30px;"></i></td>'
				$('tr#' + floorname[1]).append(addwingbuttonrow);
			}

			// populating wing list option
			$("form").on('change', '#floors', function () {
				$('#wings > option').remove();
				$('#wings').append('<option value="" disabled selected>Select Wing</option>')
				var selected_floor = $("#floors :selected").text().replace(" ","_")
				 $.ajax({
				 	type: "get",
				 	url: "/getwings/"+building_id+"/"+selected_floor,
				 	success: function (wings) {
				 		wings.forEach(wing => {
				 			$('#wings').append('<option value="' + wing + '">' + wing + '</option>')
				 			})
				 	}
			});
		})
		$("#content").fadeIn('slow');
		$('#preloader').fadeOut('slow');
		}
	});

	$('#addFloor').on('mouseover', function () {
		$(this).css('cursor', 'pointer');
	})
	
	$('#deleteFloor').on('mouseover', function () {
		$(this).css('cursor', 'pointer');
	})

	$("#grid").on("mouseover",'td[class*="Wing_"]', function () {
		$(this).css('cursor', 'pointer');
		wing_name = $(this).children().text() 
		$(this).children().html('<i style="font-size: 30px;" class="fas fa-trash-alt"></i>')
	});

	$("#grid").on("mouseout",'td[class*="Wing_"]', function () {
		$(this).css('cursor', 'pointer');
		$(this).children().text(wing_name)
	});	

	$("#grid").on("click", 'td[class*="Wing_"]',function () {
		var fname = "Floor "+$(this).parent()[0].id
		$.ajax({
			type: "delete",
			url: "/building/"+building_id+"/"+fname+"/"+wing_name,
			success: function (response) {
				alert(response)
				location.reload();
			}
		});
	});	


	$('#grid').on('mouseover', '.addWing', function () {
		$(this).css('cursor', 'pointer');
	})

	$('#addFloor').on('click', function () {
		$.ajax({
			url: "/getfloorcount/" + building_id,  //url = "/getfloorcount/3"
			method: "post",
			success: function (response) {
				// response is for eg cnt i.e response = 4
				var cnt = response;
				var floor_name = "Floor " + response;
				$.ajax({
					url: "/building/floor",
					method: "post",
					data: { "id": building_id, "floorName": floor_name },
					ContentType: "application/json;charset=utf-8",
					success: function (response) {
						$('#floornames').prepend("<tr><td class='py px-floor'><h4> Floor " + cnt + " </h4></td></tr>")
						$('#grid').prepend('<tr id=""><td class="py px-grid "><h5>NA</h5></td><td class="px-grid"><i class="addWing" data-mdb-toggle="tooltip" title="Click to add Wing" style="font-size:30px;"></i></td></tr>')
						alert(response);
						location.reload();
					}
				})

			}
		})
	})

	$("#deleteFloor").click(function () { 
		var fNumber = $('#grid:first-child').children()[0].id
		var fName = "Floor "+fNumber
		$.ajax({
			type: "delete",
			url: "/building/"+building_id+"/"+fName,
			success: function (response) {
				alert(response);
				location.reload();
			}
		});
	});


	$('#grid').on('click', '.addWing', function () {
		floor_Name = "Floor " + this.parentNode.parentNode.id
		console.log(floor_Name);
		$('#wingModal').modal('show');
	})
	
});

function addWing() {
	$('#wingModal').modal('hide');
	var wingName = $('#wingName').val()
	console.log(building_id);
	$.ajax({
		url: "/building/wing",
		method: "post",
		data: { "id": building_id, "floorName": floor_Name, "wingName": wingName },
		ContenType: "application/json;charset=utf-8",
		success: function (response) {
			alert(response);
			location.reload();
		}
	})
}

