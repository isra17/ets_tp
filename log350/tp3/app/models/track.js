define([
	'app/models/fixtures/music-fixtures'
], function(Fixtures){
	var Track = Backbone.Model.extend({
		defaults: {
			title: '<unknown track>',
			artist: '<unknown artist>',
			album: '<unknown album>',
			vote: 0,
			hasVotedFor: false,
			voters: [],
			length: 0,
			enabled: true
		},

		toggleVote: function(){
			if(this.get("enabled")) {
				var hasVotedFor = this.get('hasVotedFor')
					vote = this.get('vote');

				this.set('hasVotedFor', !hasVotedFor);
				this.set('vote', vote + (hasVotedFor? -1: 1));
				this.save();
			}
		},

		url: 'api/track',
		fixture: Fixtures.TrackFixture
	});

	return Track;
});
