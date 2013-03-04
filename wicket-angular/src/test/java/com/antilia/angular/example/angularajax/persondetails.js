$scope.element  = null;

$scope.$on('PersonsList-RowSelected-1', function(event, args) {
		var url = '${url}';
		var selected = args['selected'];
		$scope.element  = null;
		if(selected != null) {
			var url1 = url+ '&' + '${userID}' + '=' + selected;
		}	
		$http.get(url1).success(function(data) {
			$scope.element  = data;
		});
});
