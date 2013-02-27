// Inherit outlet Support
MovieTracker.ApplicationController = Ember.Controller.extend();

// Controller to store Movie Objects
MovieTracker.moviesController = Ember.ArrayController.create({
    content: [],

    // Property that adds an item to content[]
    addItem: function(item) {
        this.addObject(item);
    },

    // Property that returns the length of content[]
    itemCount: function() {
        return this.get('length');
    }.property('@each'),

    // Property that returns all objects in content[] with watched = false
    unwatchedCount: function() {
      return this.filterProperty('watched', false).get('length');
    }.property('@each.watched')
});

// Controller used for filtering movies if it ever needed. Should not be used for editing moviesController content[]
MovieTracker.visibleMoviesController = Ember.ObjectController.create({
    content: [],

    // Sets content[] to filtered results of moviesController
    filterBy: function(key, value) {
      this.set('content', MovieTracker.moviesController.filterProperty(key, value));
    },

    // Sets content[] to all items in moviesController
    clearFilter: function() {
      this.set('content', MovieTracker.moviesController.get('content'));
    }
});

// Provides functionality related to selecting a Movie
MovieTracker.selectedMovieController = Ember.ObjectController.create({
    selectedMovie: [],
    hasPrev: false,
    hasNext: false,

    // This property is called when selecting an item
    select: function(item) {
        this.set('selectedMovie', item);
        if (item) {
          // Determine if we have a previous/next item in the array
          var currentIndex = MovieTracker.visibleMoviesController.content.indexOf(this.get('selectedMovie'));
          if (currentIndex + 1 >= MovieTracker.visibleMoviesController.get('itemCount')) {
            this.set('hasNext', false);
          } else {
            this.set('hasNext', true);
          }
          if (currentIndex === 0) {
            this.set('hasPrev', false);
          } else {
            this.set('hasPrev', true);
          }

        } else {
          this.set('hasPrev', false);
          this.set('hasNext', false);
        }
    },

    // This property called when you want to toggle the "watched" property of a Movie object
    toggleWatched: function() {
       this.selectedMovie.toggleProperty('watched');
    },

    // Selects the next item in moviesController content[]
    next: function() {
        // Get's the current index in case we've changed the list of items, if the
        // item is no longer visible, it will return -1.
        var currentIndex = MovieTracker.visibleMoviesController.content.indexOf(this.get('selectedMovie'));
        // Figure out the next item by adding 1, which will put it at the start
        // of the newly selected items if they've changed.
        var nextItem = MovieTracker.visibleMoviesController.content[currentIndex + 1];
        if (nextItem) {
          this.select(nextItem);
        }
    },

    // Selects the previous item in moviesController content[]
    prev: function() {
        // Get's the current index in case we've changed the list of items, if the
        // item is no longer visible, it will return -1.
        var currentIndex = MovieTracker.visibleMoviesController.content.indexOf(this.get('selectedMovie'));
        // Figure out the previous item by subtracting 1, which will result in an
        // item not found if we're already at 0
        var prevItem = MovieTracker.visibleMoviesController.content[currentIndex - 1];
        if (prevItem) {
          this.select(prevItem);
        }
    }

});

