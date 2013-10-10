
define([
    'simpledialog2'
], function(){
    var PopupMenuView = Backbone.View.extend({
        id: 'popup-menu',

        show: function(){
            $('#' + this.id).remove();
            $('body').append(this.el);
            this.render();

            $('#' + this.id).simpledialog2();
        },

        render: function() {
            this.$el.attr('data-options',
                '{"mode":"blank","headerText":"'+ this.options.title +
                '","headerClose":true,"blankContent":true,"blankContentAdopt":true}');
            this.$el.css('display','none');
            _.each(this.options.buttons, function(action, label){
                var button = $('<a rel="close" data-theme="d" data-role="button">'+label+'</a>');
                if(_.isFunction(action)) {
                    button.click(action);
                }

                this.$el.append(button);
            }, this);

            this.$el.append('<a rel="close" class="cancel" data-theme="a" data-role="button">Cancel</a>');

            return this;
        }
    });

    return PopupMenuView;
});
