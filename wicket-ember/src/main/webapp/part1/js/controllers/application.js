// Inherit outlet Support
MovieTracker.ApplicationController = Ember.Controller.extend();

// ArrayController to create some new Movies
MovieTracker.movieController = Ember.ArrayController.create({
	content: [], 
	init: function(){
        var kidsMovie = MovieTracker.Movie.create({
            title: 'Toy Story',
            rating: 4
        });
        this.pushObject(kidsMovie);

        var avengers = MovieTracker.Movie.create({
            title: 'The Avengers',
            rating: 5
        });
        this.pushObject(avengers);
    }
});

// Controller for Movie
MovieTracker.watchedController = Ember.ObjectController.create({
	watchController: null,
	contentBinding: 'watchController.selectedItem',

	setWatched: function() {
		this.toggleProperty('watched');
	}
});