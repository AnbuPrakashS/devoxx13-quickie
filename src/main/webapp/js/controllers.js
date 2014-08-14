var attendeesControllers = angular.module('attendeesControllers', []);

attendeesControllers.controller('AttendeesListCtrl', function($scope, $http) {
	$http.get('content/attendees').success(function(data) {
		$scope.attendees = data;
	});
});

attendeesControllers.controller('AttendeeCreateCtrl', function($scope, $http, $location) {
	$scope.save = function() {
		$http.post('content/attendees/', $scope.attendee).success(function(data) {
			console.log("Created attendee " + data);
			$location.path('/');
		});
	};
}); 

attendeesControllers.controller('AttendeeEditCtrl', function($scope, $http, $routeParams, $location) {
	$http.get('content/attendees/' + $routeParams.attendeeId).success(function(data) {
		$scope.attendee = data;
	});
	
	$scope.save = function() {
		$http.put('content/attendees/' + $routeParams.attendeeId, $scope.attendee).success(function(data, status, headers) {
			console.log("Updated attendee " + data);
			console.log("Response header: " + headers('ETag'));
			
			$location.path('/');
		});
	};
	
	$scope.destroy = function() {
		// JSDT reports an error on '$http.delete" because 'delete' is a reserved keyword.
		$http({'method':'DELETE', url:'content/attendees/' + $routeParams.attendeeId}).success(function() {
			//console.log("Removed attendee " + $scope.attendee);
			$location.path('/');
		});
	};
});
