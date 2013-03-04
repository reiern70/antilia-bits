
$scope.$on('PersonsList-RowSelected', function(event, args) {
	$scope.$broadcast('PersonsList-RowSelected-1', args);
});
