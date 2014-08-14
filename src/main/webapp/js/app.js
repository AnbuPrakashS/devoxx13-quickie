var attendeesApp = angular.module('attendeesApp', ['ngRoute', 'attendeesControllers']);

attendeesApp.config(['$routeProvider',
    function($routeProvider) {
		$routeProvider.
			when('/', {
				controller:'AttendeesListCtrl', 
				templateUrl: 'partials/attendees-list.html' 
			}).
			when('/edit/:attendeeId', {
				controller:'AttendeeEditCtrl', 
				templateUrl: 'partials/attendee-detail.html' 
			}).
			when('/new', {
				controller:'AttendeeCreateCtrl', 
				templateUrl: 'partials/attendee-detail.html'
			}).
			otherwise({redirectTo:'/'});
	}]
);

