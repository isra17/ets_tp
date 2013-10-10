define([
    'text!app/models/fixtures/library.json'
], function(libraryJson){
    var getQueue = function(library){
        return _.chain(library)
            .filter(function(track){
                return track.enabled
            })
            .sortBy(function(track){
                return track.vote;
            })
            .value()
            .reverse();
    };

    var libraryCache = JSON.parse(libraryJson);
    var player = {
            nowPlayingId: getQueue(libraryCache)[0].id,
            progress: 0
        };

    var updater = setInterval(function(){

        player.progress++;

        var playingTrack = _.find(libraryCache, function(track){
            return track.id == player.nowPlayingId;
        });

        if(!playingTrack){
            return;
        }

        if(player.progress > playingTrack.length) {
            playingTrack.enabled = false;

            var next = getQueue(libraryCache)[0];
            if(!next){
                clearInterval(updater);
                player.progress = 0;
                return;
            }

            _.extend(player, {
                nowPlayingId: next.id,
                progress: 0
            });
        }

    }, 1000);

    var LibraryFixture = {
        read: function(method, model, options) {
            options.success(libraryCache);
        }
    };

    var TrackFixture = {
        read: function(method, model, options) {
            options.success(_.find(libraryCache, function(track) {
                return track.id == model.id;
            }));
        },

        update: function(method, model, options) {
            var track = _.find(libraryCache, function(track) {
                return track.id == model.id;
            });

            options.success(_.extend(track, model.toJSON()));
        }
    };

    var PlaylistFixture = {
        read: function(method, model, options) {
            var queue = _.reject(getQueue(libraryCache), function(track){
                    return track.id == player.nowPlayingId;
                })

            options.success(queue);
        }
    };

    var PlayerFixture = {
        read: function(method, model, options) {
            options.success(player);
        }
    };

    return {
        LibraryFixture: LibraryFixture,
        TrackFixture: TrackFixture,
        PlaylistFixture: PlaylistFixture,
        PlayerFixture: PlayerFixture
    };
});
