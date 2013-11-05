var attendeesApp = angular.module('app', []);

attendeesApp.config(function($routeProvider) {
		$routeProvider.when('/', {controller:'AttendeesListCtrl', templateUrl: 'partials/attendees-list.html'}).
		when('/edit/:attendeeId', {controller:'AttendeeEditCtrl', templateUrl: 'partials/attendee-detail.html'}).
		when('/new', {controller:'AttendeeCreateCtrl', templateUrl: 'partials/attendee-detail.html'}).
		otherwise({redirectTo:'/'});
	});

attendeesApp.controller('AttendeesListCtrl', function($scope, $http) {
	$http.get('content/attendees').success(function(data) {
		$scope.attendees = data;
	});
});

attendeesApp.controller('AttendeeCreateCtrl', function($scope, $http, $location) {
	$scope.save = function() {
		$http.post('content/attendees/', $scope.attendee).success(function(data) {
			console.log("Created attendee " + data);
			$location.path('/');
		});
	};
}); 

attendeesApp.controller('AttendeeEditCtrl', function($scope, $http, $routeParams, $location) {
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


