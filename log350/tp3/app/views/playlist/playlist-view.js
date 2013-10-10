define([
    'app/views/library/track-list-view',
    'app/views/playlist/player-view',
    'text!app/views/library/separator-item.html'
], function(TracksView, PlayerView, separatorHtml){
    var PlaylistView = Backbone.View.extend({
        separatorTemplate: _.template(separatorHtml),

        render: function() {
            //this.model.playlist <-- queue collection
            //this.model.player <-- {progress, nowPlayingTrack}
            this.stopListening(this.model.player, 'change:nowPlayingTrack', this.updatePlaylist);

            this.$el.html('');

            tracksView = new TracksView({
                model: this.model.playlist,
                onlyTitle: false
            });

            this.$el.append(tracksView.$el);
            tracksView.render();

            var queueSeparator = this.separatorTemplate({title: 'Queue'})
            tracksView.$el.prepend(queueSeparator);

            if(this.model.player.get('nowPlayingTrack')) {
                var nowPlayingSeparator = this.separatorTemplate({title: 'Now Playing'})
                var playerView = new PlayerView({
                    model: this.model.player,
                    tagName: 'li'
                });

                tracksView.$el.prepend(playerView.$el);
                tracksView.$el.prepend(nowPlayingSeparator);
                playerView.render();
            }

            tracksView.$el.listview('refresh');

            this.listenTo(this.model.player, 'change:nowPlayingTrack', this.updatePlaylist);

            return this;
        },

        updatePlaylist: function(){
            if(this.$el.closest('body').length) {
                this.model.playlist.fetch({reset: true});
                this.render();
            }else{
                this.stopListening(this.model.player, 'change:nowPlayingTrack', this.updatePlaylist);
            }
        }
    });

    return PlaylistView;
});
