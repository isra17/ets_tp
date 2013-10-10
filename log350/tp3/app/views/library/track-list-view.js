define([
    'app/views/library/track-item-view'
], function(ItemView){
    var TrackListView = Backbone.View.extend({
        tagName: 'ul',

        initialize: function(){
            _.defaults(this.options, {
                onlyTitle: true
            });
        },

        render: function() {
            this.$el.html('');
            this.model.each(function(track){
                var itemView = new ItemView({
                    model: track,
                    onlyTitle: this.options.onlyTitle
                });
                this.$el.append(itemView.render().el);
            }, this);

            this.$el.listview();

            return this;
        }
    });

    return TrackListView;
});
