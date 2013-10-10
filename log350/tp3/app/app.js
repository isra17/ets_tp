require.config({
    paths : {
        backbone : 'lib/backbone',
        'backbone-fixture' : 'lib/backbone-fixtures',
        underscore : 'lib/underscore',
        jquery : 'lib/jquery-1.8.2',
        jqm : 'lib/jquery.mobile-1.3.0',
        text: 'lib/text',
        'simpledialog2': 'lib/jquery.mobile.simpledialog2.min'
    }
});

require([
	'text',
	'require',
	'jquery',
	'underscore',
], function(){
	//Config JQM
	$(document).bind("mobileinit", function () {
		$.mobile.ajaxEnabled = false;
		$.mobile.linkBindingEnabled = false;
		$.mobile.hashListeningEnabled = false;
		$.mobile.pushStateEnabled = false;
	});

	require([
		'underscore',
		'backbone',
		'jqm',
		'app/models/fixtures/backbone-fixtures'
	], function() {
		Backbone.fixtures = true;
		Backbone.fixturesRoot = 'app/models/fixtures/';

		//load and initialize controllers
		require([
			'app/controllers/library',
			'app/controllers/track',
			'app/controllers/playlist',
			'app/controllers/home'
		], function(){
			_.each(arguments, function(Controller){
				new Controller();
			});

			Backbone.history.start();
		})

		$(document).ready(function(){
			/*$(".library-filters a").click(function(event){
				event.preventDefault();


				$.mobile.navigate("#library-page", {filterBy: $(this).attr( "data-filter-by" )});
			});*/

		});
	});
});
