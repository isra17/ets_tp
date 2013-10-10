
define([
    'text!app/views/library/track-item.html',
    'app/views/popup-menu-view',
    'simpledialog2'
], function(itemHtml, PopupMenuView){
    var TrackItemView = Backbone.View.extend({
        itemTemplate: _.template(itemHtml),
        tagName: 'li',
        attributes: {
            'data-icon': 'check'
        },

        initialize: function() {
            _.defaults(this.options, {
                onlyTitle: true
            });
            this.listenTo(this.model, 'change', this.updateView);
        },

        events: {
            'tap': function(event) {
                event.preventDefault();

                if(!this.taphold) {
                    this.model.toggleVote();
                }

                this.taphold = false;
            },
            'taphold': function(event) {
                event.preventDefault();
                var self = this;
                this.taphold = true;

                var buttonsOptions = {
                    'View info': function(){
                        location.hash = '#track-' + self.model.id;
                    },
                    'Reset vote': function(){
                        self.model.set('vote', 0);
                        self.model.set('hasVotedFor', false);
                        self.model.save();
                    }
                };

                if(this.model.get('enabled')) {
                    buttonsOptions.Vote = _.bind(this.model.toggleVote, this.model);
                }

                var menu = new PopupMenuView({
                    title: this.model.get('title'),
                    buttons: buttonsOptions
                });

                menu.show();
            }
        },

        render: function() {

            this.$el.html(this.itemTemplate({
                track: this.model,
                onlyTitle: this.options.onlyTitle
            }));

            if(this.model.get('hasVotedFor')) {
                this.$el.addClass('voted');
            }

            return this;
        },

        updateView: function(){
            if(this.model.get('hasVotedFor')) {
                this.$el.addClass('voted');
            } else {
                this.$el.removeClass('voted');
            }

            this.$el.find('.vote-count').text(this.model.get('vote'));
        }
    });

    return TrackItemView;
});
