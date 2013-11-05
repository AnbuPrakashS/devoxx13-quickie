var attendeesApp = angular.module('app', ['restangular']).
config(function(RestangularProvider) {
	  RestangularProvider.setBaseUrl('c');
	  });

attendeesApp.config(function($routeProvider) {
		$routeProvider.when('/', {controller:'AttendeesListCtrl', templateUrl: 'partials/attendees-list.html'}).
		when('/edit/:attendeeId', {controller:'AttendeeEditCtrl', templateUrl: 'partials/attendee-detail.html'}).
		when('/new', {controller:'AttendeeCreateCtrl', templateUrl: 'partials/attendee-detail.html'}).
		otherwise({redirectTo:'/'});
	});

attendeesApp.controller('AttendeesListCtrl', function(Restangular) {
	var baseAttendees = Restangular.all('attendees');
	$scope.attendees = baseAttendees.getList();
});

attendeesApp.controller('AttendeeCreateCtrl', function($scope, $location, Restangular) {
	$scope.save = function() {
		var baseAttendees = Restangular.all('attendees');
		var attendee = {firstName: $scope.attendee.firstName};
		baseAttendees.post(attendee).then(function(attendee) {
			console.log("Created attendee " + attendee);
			$location.path('/');
		});
	};
});

attendeesApp.controller('AttendeeEditCtrl', function($scope, $location, $routeParams, Restangular) {
	var original = Restangular.one('attendees', $routeParams.attendeeId).get();
	var editVersion = Restangular.copy(original);
	$scope.attendee = editVersion;
	
	$scope.save = function() {
		original.put().then(function(attendee) {
			console.log("Updated attendee " + attendee);
			$location.path('/');
		});
	};
	
	$scope.destroy = function() {
		$scope.attendee.remove().then(function(attendee) {
			console.log("Removed attendee " + attendee);
			$location.path('/');
		});
	};
});


