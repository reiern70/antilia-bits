function ${scope} ($scope, $http ${additionalParams}) {		
	
	${extraContributions};	
	
	// selected row.
	$scope.selected=null;
	
	$scope.selectRow = function(id) {
		$scope.selected = id; 
		var args = {'selected': $scope.elements[id].id};
		var event = '${scope}-' + 'RowSelected'; 
		$scope.$emit(event, args);
	};
}