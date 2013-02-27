// Create the application level view
MovieTracker.ApplicationView = Ember.View.extend({
  templateName: 'application'
});

// Movie Listings View
MovieTracker.MovieListingsView = Ember.View.extend({
    // Handle clicks on an item summary
    click: function(event) {
        // Figure out what the user just clicked on, then set selectedMovieController
        var content = this.get('content');
        MovieTracker.selectedMovieController.select(content);
    },

    nextMovie: function(event) {
        alert("here");
        MovieTracker.selectedMovieController.next();
    }
});

MovieTracker.ActionPanelView = Ember.View.extend({
    templateName: 'action_panel',

    toggleWatched: function(event) {
        MovieTracker.selectedMovieController.toggleWatched();
    },

    nextMovie: function(event) {
        MovieTracker.selectedMovieController.next();
    }
});

MovieTracker.SelectedMovieView = Ember.View.extend({});

MovieTracker.MovieDetailsView = Ember.View.extend({
    contentBinding: 'MovieTracker.selectedMovieController.selectedMovie'
});
