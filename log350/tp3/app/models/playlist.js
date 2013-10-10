define([
    'app/models/fixtures/music-fixtures',
    'app/models/track'
], function(fixtures, TrackModel){

    var Playlist = Backbone.Collection.extend({
        model: TrackModel,
        url: 'api/playlist',
        fixture: fixtures.PlaylistFixture
    });

    return Playlist;
});
