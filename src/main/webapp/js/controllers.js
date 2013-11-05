var attendeesServices = angular.module('attendeesService', ['ngResource'])
	.factory('Attendee', function($resource) {
		// get, query, save, remove and delete actions are provided by default.
		// bind the 'id' URL parameter to the 'id' attribute in the Attendee object
		return $resource('./c/attendees/:id', {id: '@id'});
	});


var attendeesControllers = angular.module('attendeesControllers', ['attendeesService']);

attendeesControllers.controller('AttendeesListCtrl', ['$scope', 'Attendee', function ($scope, Attendee) {
	$scope.attendees = Attendee.query();
	console.log("Attendees: " + $scope.attendees + " (" + $scope.attendees.length + ")");
}]);

attendeesControllers.controller('AttendeeCreateCtrl', ['$scope', '$location', 'Attendee', function($scope, $location, Attendee) {
	$scope.save = function() {
		var attendee = Attendee.get(function() {
			attendee.firstName = $scope.attendee.firstName;
			console.log("Creating attendee " + attendee.firstName);
			attendee.$save();
		});
		$location.path('/'); 
	};
}]);

attendeesControllers.controller('AttendeeEditCtrl', ['$scope', '$routeParams', '$location', 'Attendee', function($scope, $location, $routeParams, Attendee) {
	$scope.attendee.id = $routeParams.attendeeId;
	save = function() {
		var attendee = Attendee.get({id:'$scope.attendeeId'});
		attendee.$save();
	};
	
}]);

function lookupAttendee(attendees, attendeeId) {
	for(var i = 0; i < attendees.length; i++) {
		if(attendees[i].id == attendeeId) {
			return attendees[i];
		}
	}
	return "undefined";
}