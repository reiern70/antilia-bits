MovieTracker.Movie = Ember.Object.extend({
	id: null,
	title: null,
	watched: false,
	rating: 0,
	titleAndRating: function() {
		// Computed Property
		return this.get('title') + ' has a rating of ' + this.get('rating');
	}.property('title', 'rating'),
	titleChanged: function() {
		// Observer
		console.log('Title changed!');
	}.observes('title')
});

// Create a subclass of Movie called ActionMovie
MovieTracker.ActionMovie = MovieTracker.Movie.extend({
	genre: 'action'
}); 

// Create a new mixin
Watched = Ember.Mixin.create({
	isWatched: function() {
		var title = this.get('title'),
			watched = this.get('watched');
		
		return('Has ' + title + ' been watched? ' + watched);
	}
});

// Add our mixin to the Movie object
MovieTracker.Movie = Ember.Object.extend(Watched);