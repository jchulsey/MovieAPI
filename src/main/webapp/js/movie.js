var movieapp = angular.module('movieapp',['ngRoute']);

movieapp.config(function($routeProvider) {
	$routeProvider
	.when('/welcome', {
		templateUrl: 'welcome.html'
	})
	.when('/fancysearch', {
		templateUrl: 'fancysearch.html',
		controller: 'movieFancyController'
	})
	.when('/search', {
		templateUrl: 'search.html',
		controller: "moviecontroller"
	})
	.when('/create', {
		templateUrl: 'create.html'
	})
	.when('/stack', {
		templateUrl: 'stack.html'
	})
	.when('/resume', {
		templateUrl: 'resume.html'
	})
	.when('/link1', {
		templateUrl: 'link1.html'
	})
	.when('/link2', {
		templateUrl: 'link2.html'
	})
	.when('/create', {
		templateUrl : 'create.html',
		controller : 'movieCreateController'
	})
	.when('/notify', {
		templateUrl : 'notify.html',
		controller : 'movieNotifyController'
	})
	.otherwise({
		templateUrl: 'welcome.html'
	})
})

movieapp.controller('movieNotifyController', function($scope, $http) {

		$scope.emailCustomers = function() {
			var email = {
					emailSubject : $scope.emailSubject,
					emailText : $scope.emailText	
					
			};
			
			$scope.jsonEmailObject = angular.toJson(email, false);
			console.log('email customers: ' + $scope.jsonEmailObject);				
				
			$http.post("/movieapi/rest/v1/movie/email", $scope.jsonEmailObject)
			.then(
					function success(response) {
						$scope.emailStatus = "success. press 'Clear' to enter new email";								
					},
					function error(response) {
						console.log('error sending email, status: ' + response.status);
						$scope.emailStatus = "error. press 'Clear' to try again";						
					}				
			);			
		};

	
	$scope.emailCustomersClear = function() {
		$scope.emailSubject = '';
		$scope.emailText = '';
		$scope.emailStatus = '';
		console.log('email customers clear');
	};
	
	$scope.textCustomer = function() {
		console.log('text customer');
		var text = {
				textNumber : $scope.textNumber,
				textContent : $scope.textContent				
		};
		
		$scope.jsonTextObject = angular.toJson(text, false);
		console.log('email customers: ' + $scope.jsonTextObject);				
			
		$http.post("/movieapi/v1/movie/text", $scope.jsonTextObject)
		.then(
				function success(response) {
					$scope.textStatus = "success. press 'Clear' to enter new text";								
				},
				function error(response) {
					console.log('error sending text, status: ' + response.status);
					$scope.textStatus = "error. press 'Clear' to try again";						
				}				
		);
	};
	
	$scope.textCustomerClear = function() {
		$scope.textNumber = '';
		$scope.textContent = '';
		$scope.textStatus = '';
	};
})


movieapp.controller('movieFancyController', function($scope, $http) {
	$scope.getFancySearch = function() {
		console.log('fancy movie searchUpd');
		console.log('fancysearch: ' + angular.toJson($scope.fancysearch, false));
		
		var config = { params: $scope.fancysearch }
		
		$http.get("/movieapi/rest/v1/movie/fancysearch", config)
			.then(
					function(response) {
						$scope.searchResults = response.data;
					},
					function error(response) {
						console.log('error, return status: ' + response.status);
					}
			);
	};
	
	$scope.clearFancySearch = function(){
		console.log('clearFancySearch() called')
		$scope.fancysearch.english = false;
		$scope.fancysearch.french = false;
		$scope.fancysearch.german = false;
		$scope.fancysearch.spanish = false;
		
		$scope.fancysearch.media = '';
		
		$scope.fancysearch.startdate = '';
		$scope.fancysearch.enddate = '';
		
		$scope.searchResults = '';
	}
	
});

movieapp.controller('movieCreateController', function($scope, $http) {
	
	$scope.postMovie = function() {
		$scope.jsonObject = angular.toJson($scope.newMovie);
		console.log('new movie: ' + $scope.jsonObject);
		
		$http.post("/movieapi/rest/v1/movie", $scope.jsonObject)
		.then(
				function(response) {
					console.log('status: ' + response.status);
					$scope.createStatus = 'successful insert of new movie';
					$scope.successfullInsert = true;
				}, function(response) {
					console.log('error HTTP post movies: ' + response.status);
				}
		);	
	};
	
	$scope.clearMovie = function() {
		$scope.createStatus = 'Enter new movie information';
		$scope.successfulInsert = false;
		$scope.newMovie = {
				title : '',
				description : '',
				genre : '',
				releaseYear : ''
		};
	}
});

movieapp.controller('moviecontroller', function($scope, $http) {
	//$scope.appName = 'My Movie Application, by J. Hulsey';
	
	$scope.showSearch = true;
	$scope.minReleaseYear = 1920;
	$scope.maxReleaseYear = 2020;
	
	$scope.genreValues = ['Comedy', 'SyFy', 'Action'];
	
	$scope.getMovies = function() {
		console.log('getMovies()');
		$http.get("/movieapi/rest/v1/movie")
		.then(function(response) {
			$scope.movies = response.data;
			console.log('number of movies: ' + $scope.movies.length);
		}, function(response) {
			console.log('error HTTP GET movies: ' + response.status);
		});
	}
	
	$scope.selectMovie = function(movieToUpdate) {
		$scope.movieToUpdate = angular.copy(movieToUpdate);
		console.log('updateMovie() call' + angular.toJson(movieToUpdate));
		$scope.showSearch = false;
	}
	
	$scope.updateMovie = function() {
		$scope.jsonObject = angular.toJson($scope.movieToUpdate);
		console.log('updateMovie: ' + $scope.jsonObject);
		
		$http.put("/movieapi/rest/v1/movie", $scope.jsonObject)
		.then(
			function(response) {
				$scope.updateStatus = 'update success';
			}, 
			function(response) {
				$scope.updateStatus = 'update error, ' + response.data.message;
			});
	}
	
	$scope.deleteMovie = function() {
		console.log('delete movie: ' + $scope.movieToUpdate.id);
		$http.delete("/movieapi/rest/v1/movie/id/" + $scope.movieToUpdate.id)
		.then(function(response) {
			$scope.isUpdateButtonDisabled = true;
			$scope.isDeleteButtonDisabled = true;
			$scope.updateStatus = 'delete successful';
			console.log('number of movies deleted: ' + response.data.length);
		}, function(response) {
			console.log('error HTTP DELETE movies: ' +response.status);
			$scope.updateStatus = 'delete error, ' +response.data.message;
		});
	}
	
	$scope.cancelMovie = function() {
		console.log('cancelMovie() call' + angular.toJson($scope.movieToUpdate));
		$scope.showSearch = true;
		$scope.getMovies();
	}
	
})