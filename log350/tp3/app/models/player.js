define([
    'app/models/fixtures/music-fixtures',
    'app/models/track'
], function(fixtures, TrackModel){

    var Playlist = Backbone.Model.extend({
        defaults: {
            progress: 0,
            nowPlayingId: null,
            nowPlayingTrack: null
        },

        initialize: function(){
            this.listenTo(this, 'change:nowPlayingId', function(){
                var track = new TrackModel({id:this.get('nowPlayingId')});
                track.fetch();
                this.set('nowPlayingTrack', track);
            });
        },

        url: 'api/playlist',
        fixture: fixtures.PlayerFixture,
    });

    return Playlist;
});
