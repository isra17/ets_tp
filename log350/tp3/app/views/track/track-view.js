define([
    'text!app/views/track/track.html'
], function(trackHtml){
    var TrackListView = Backbone.View.extend({
        trackemplate: _.template(trackHtml),

        initialize: function(){
            _.defaults(this.options, {
                onlyTitle: true
            });
        },

        render: function() {

            this.$el.html(this.trackemplate({
                track: this.model
            }));

            this.$el.find('ul, ol').listview();

            return this;
        }
    });

    return TrackListView;
});
