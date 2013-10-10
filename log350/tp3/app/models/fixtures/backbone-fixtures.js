require(['underscore', 'backbone'], function(){
	var B = Backbone;
	if ( !B || B.syncFixtures === true ) return;

	var sync = B.sync;
	var fixtureCache;

	B.sync = function( method, model, options ){
		var useFixtures = B.fixtures === true || model.useFixtures === true || ( model.attributes && model.attributes.useFixtures === true );

		// Add fixture loading for models that has it defined
		if ( useFixtures && 'fixture' in model ){

			var success = options.success,
				error = options.error,
				defered = jQuery.Deferred();

			options.success = function(){
				defered.resolveWith(this, arguments);
				success.apply(this, arguments);
			};

			options.error = function(){
				defered.resolveWith(this, arguments);
				error.apply(this, arguments);
			}

			var syncFixture = model.fixture[method];
			if(_.isFunction(syncFixture)) {
				syncFixture(method, model, options);
			} else {
				options.error(new Error('No function defined for fixture'));
			}
			return defered;

		} else {
			return sync( method, model, options );
		}
	};
	B.syncFixtures = true;
})
