
function PersonDetail($scope, $http) {
	var url = '${url}';
	var userID = "${userID}";
	if($scope.selected) {		
		$http.get(url).success(function(data) {
			$scope.element  = data[0];
		});
	}				
	
	$scope.$on('RowSelected', function(event, args) {
		var selected = args['selected'];
		if(selected != null) {
			var url1 = url+ '&' + userID + '=' + selected;
		}	
		$http.get(url1).success(function(data) {
			$scope.element  = data;
		});
	});
}