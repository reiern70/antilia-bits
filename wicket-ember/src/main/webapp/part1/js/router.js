MovieTracker.Router = Ember.Router.extend({
  root: Ember.Route.extend({
	// Create the index route
    index: Ember.Route.extend({
      route: '/'
    })
  })
});