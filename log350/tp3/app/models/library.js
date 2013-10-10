define([
    'app/models/track',
    'app/models/fixtures/music-fixtures'
], function(TrackModel, fixtures){

	var Library = Backbone.Collection.extend({
		model: TrackModel,
		url: 'api/library',
		fixture: fixtures.LibraryFixture,
	});

	return Library;
});
