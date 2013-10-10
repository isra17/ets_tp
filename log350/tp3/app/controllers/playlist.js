define([
    'app/views/playlist/playlist-view',
    'app/models/playlist',
    'app/models/player'
], function(PlaylistView, PlaylistModel, PlayerModel){
    var playlist = new PlaylistModel();
    playlist.fetch();

    var player = new PlayerModel();
    player.fetch();

    setInterval(function(){
        player.fetch();
    }, 1000);

    var TrackController = Backbone.Router.extend({

        initialize: function(){
            $('#simple-page .back').click(function(e){
                e.preventDefault();
                history.back();
            });
        },

        routes: {
            "playlist": "view"
        },

        view: function(id){
            playlist.fetch({reset: true});
            player.fetch();

            $.mobile.changePage( "#simple-page" , { reverse: false, changeHash: false } );

            this.setTitle('Playlist');

            this.setContent(new PlaylistView({
                model: {
                    playlist: playlist,
                    player: player
                }
            }));
        },

        setTitle: function(title){
            $('#simple-page [data-role=header] h1').text(title);
        },

        setContent: function(view){
            var contentEl = $('#simple-page [data-role=content]');
            contentEl.html('');
            contentEl.append(view.el);
            view.render();
        }
    });

    return TrackController;
});
