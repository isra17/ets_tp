define([], function(){
	var HomeController = Backbone.Router.extend({

		routes: {
			"":	"init",
		},

		init: function(){
			$.mobile.changePage( "#home" , { reverse: false, changeHash: false } );
		}
	});

	return HomeController;
});
