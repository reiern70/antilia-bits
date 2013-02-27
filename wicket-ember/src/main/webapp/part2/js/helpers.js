// Method is called to populate our moviesController content[]
MovieTracker.GetMovieItems = function() {
	MovieTracker.moviesController.addItem(MovieTracker.Movie.create({
        title: 'The Avengers',
        rating: 4,
        watched: false
    }));

    MovieTracker.moviesController.addItem(MovieTracker.Movie.create({
        title: 'Spiderman',
        rating: 1,
        watched: true
    }));

    MovieTracker.moviesController.addItem(MovieTracker.Movie.create({
        title: 'The Hulk',
        rating: 2,
        watched: true
    }));

    MovieTracker.moviesController.addItem(MovieTracker.Movie.create({
        title: 'Thor',
        rating: 3,
        watched: false
    }));

    MovieTracker.moviesController.addItem(MovieTracker.Movie.create({
        title: 'Batman',
        rating: 5,
        watched: false
    }));  
};