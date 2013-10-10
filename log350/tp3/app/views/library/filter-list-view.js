define([
    'text!app/views/library/filter-item.html'
], function(itemHtml){
    var GroupedList = Backbone.View.extend({
        tagName: 'ul',
        itemTemplate: _.template(itemHtml),

        render: function() {
            var filter = this.options.filter;
            var filterValue = _.uniq(this.model.pluck(filter)).sort();

            _.each(filterValue, function(value){
                this.$el.append(this.itemTemplate({
                    title: value,
                    filter: filter
                }));
            }, this);

            this.$el.listview();

            return this;
        }
    });

    return GroupedList;
});
