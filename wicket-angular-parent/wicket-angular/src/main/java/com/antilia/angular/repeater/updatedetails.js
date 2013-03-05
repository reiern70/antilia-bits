$scope.element  = null;

$scope.$on('${updateEvent}', function(event, args) {
		var url = '${url}';
		var selected = args['selected'];
		$scope.element  = null;
		if(selected != null) {
			var url1 = url+ '&' + '${elementId}' + '=' + selected.id + "&_acache="+ (new Date().getTime());
		}	
		$http.get(url1).success(function(data) {
			$scope.element  = data;
		});
});
