// Create our Application
MovieTracker = Ember.Application.create({
	ready: function() {
	    // Override the read method.
	    this._super();

	    // Populate content[] in moviesController
	    // This method is located in js/helpers.js
		MovieTracker.GetMovieItems();
	}
});