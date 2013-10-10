
define([
    'text!app/views/playlist/player.html',
    'app/views/popup-menu-view',
    'simpledialog2'
], function(playerHtml, PopupMenuView){

    var TrackItemView = Backbone.View.extend({
        itemTemplate: _.template(playerHtml),
        attributes: {
            'data-icon': 'check'
        },

        initialize: function() {
            var self = this;

            this.listenTo(this.model, 'change:progress', this.updateProgress);
        },

        events: {
            'taphold': function(event) {
                event.preventDefault();
                var self = this;
                this.taphold = true;

                var menu = new PopupMenuView({
                    title: this.model.get('nowPlayingTrack').get('title'),
                    buttons: {
                        'View info': function(){
                            location.hash = '#track-' + self.model.id;
                        }
                    }
                });

                menu.show();
            }
        },

        render: function() {

            this.$el.html(this.itemTemplate({
                track: this.model.get('nowPlayingTrack')
            }));

            if(this.model.get('hasVotedFor')) {
                this.$el.addClass('voted');
            }

            this.$el.parent('ul').listview('refresh');

            return this;
        },

        updateProgress: function(){
			$('#progress-inner').css("width",((this.model.get('progress')*100)/this.model.get('nowPlayingTrack').get('length'))+"%");
        }
    });

    return TrackItemView;
});
