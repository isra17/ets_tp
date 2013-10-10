define([
    'app/views/track/track-view',
    'app/models/track'
], function(TrackView, TrackModel){

    var TrackController = Backbone.Router.extend({

        initialize: function(){
            $('#simple-page .back').click(function(e){
                e.preventDefault();
                history.back();
            });
        },

        routes: {
            "track-:id": "view"
        },

        view: function(id){
            $.mobile.changePage( "#simple-page" , { reverse: false, changeHash: false } );

            var track = new TrackModel({id: id});
            track.fetch();

            this.setTitle(track.get('title'));

            this.setContent(new TrackView({
                model: track
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
