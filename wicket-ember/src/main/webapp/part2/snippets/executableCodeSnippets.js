var actionMovie = MovieTracker.Movie.create({
  title: "Action Movie 2",
  helloWorld: function() {
    alert("Hi my name is " + this.get('title'));
  }
});
actionMovie.helloWorld();

MovieTracker.Movie = Ember.Object.extend({
	id: null,
	title: null,
	watched: false,
	rating: 0,
	titleAndRating: function() {
		return this.get("title") + " has a rating of " + this.get("rating");
	}.property(“title”, “rating”),
	ratingChanged: function() {
		return('title changed!');
	}.observes('title')
});

var comedyMovie = MovieTracker.Movie.create({
  title: 'A Comedy Movie',
  rating: 5
});

comedyMovie.get('titleAndRating');

MovieTracker.Movie = Ember.Object.extend(Watched);

var watchedMovie = MovieTracker.Movie.create({
  title: 'A Watched Movie',
  rating: 5,
  watched: true
});

watchedMovie.isWatched();

{{action "nextMovie"}}
