MovieTracker.Router = Ember.Router.extend({
  root: Ember.Route.extend({
    index: Ember.Route.extend({
      route: '/',
      // Redirect to the movies state once the application loads
      redirectsTo: 'movies'
    }),

    movies: Ember.Route.extend({
      route: '/movies',
      showMovie: Ember.Route.transitionTo('movie')
      /*,
      Try connecting an outlet into your index to display the movie listings
      connectOutlets: function(router){
        router.get('applicationController').connectOutlet('details', MovieTracker.visibleMoviesController.content);
      }*/
    })
  })
})

// Start our Ember Application
MovieTracker.initialize();