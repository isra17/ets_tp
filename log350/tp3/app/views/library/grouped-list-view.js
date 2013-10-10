define([
    'app/views/library/track-item-view',
    'text!app/views/library/separator-item.html'
], function(ItemView, separatorHtml){
    var GroupedList = Backbone.View.extend({
        tagName: 'ul',
        separatorTemplate: _.template(separatorHtml),

        render: function() {
            var groupedData = this.model.groupBy(this.options.groupBy);

            _.each(groupedData, function(tracks, group){
                this.$el.append(this.separatorTemplate({
                    title: group
                }));

                _.each(tracks, function(track){
                    var itemView = new ItemView({
                        model: track
                    });
                    this.$el.append(itemView.render().el);
                }, this);
            }, this);

            this.$el.listview();

            return this;
        }
    });

    return GroupedList;
});
