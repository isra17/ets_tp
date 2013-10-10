define([
	'app/views/library/track-list-view',
	'app/views/library/filter-list-view',
	'app/views/library/grouped-list-view',
	'app/models/track',
	'app/models/library'
], function(TracksView, FilterListView, GroupedView, TrackModel, LibraryModel){

	var LibraryController = Backbone.Router.extend({
		allTrack: new LibraryModel(),

		routes: {
			"library": 					"browseAll",
			"library-all": 				"browseAll",
			"library-artist": 			"browseArtists",
			"library-album": 			"browseAlbums",
			"library-artist-:artist": 	"browseByArtist",
			"library-album-:album": 	"browseByAlbum"
		},

		initialize: function(){
			this.allTrack.fetch();
			$('#library .up').click(_.bind(this.goUp, this));
		},

		init: function(){
			this.allTrack.fetch();
			if(!$('#library').hasClass('ui-page-active')) {
				$.mobile.changePage( "#library" , { reverse: false, changeHash: false } );
			}
		},

		browseAll: function(){
			this.init();
			this.setTitle('Music library');
			this.setActiveTab('all');

			this.setContent(new TracksView({
				model: this.allTrack,
				onlyTitle: false
			}));
		},

		browseArtists: function(){
			this.init();
			this.setTitle('Music library');
			this.setActiveTab('artist');

			this.setContent(new FilterListView({
				model: this.allTrack,
				filter: 'artist'
			}));
		},

		browseAlbums: function(){
			this.init();
			this.setTitle('Music library');
			this.setActiveTab('album');

			this.setContent(new FilterListView({
				model: this.allTrack,
				filter: 'album'
			}));
		},

		browseByArtist: function(artist){
			this.init();
			this.setTitle(artist);
			this.setActiveTab('artist');
			var filteredTracks = new LibraryModel(this.allTrack.where({artist: artist}));

			this.setContent(new GroupedView({
				model: filteredTracks,
				groupBy: 'album'
			}));
		},

		browseByAlbum: function(album){
			this.init();
			this.setTitle(album);
			this.setActiveTab('album');
			var filteredTracks = new LibraryModel(this.allTrack.where({album: album}));

			this.setContent(new TracksView({
				model: filteredTracks,
				onlyTitle: true
			}));
		},

		goUp: function(event){
            event.preventDefault();

			var fragments = Backbone.history.fragment.split('-'),
				dest = '';

			fragments.pop();

			if(fragments.length > 1) {
				dest = fragments.join('-');
			}

			location.hash = '#' + dest;
		},

		setActiveTab: function(tab){
			$('.library-filters .ui-btn-active').removeClass('ui-btn-active');
			$('.library-filters [href=#library-'+ tab +']').addClass('ui-btn-active');
		},

		setContent: function(view){
			var contentEl = $('#library [data-role=content]');
			contentEl.html('');
			contentEl.append(view.el);
			view.render();
		},

		setTitle: function(title){
			$('#library [data-role=header] h1').text(title);
		}
	});

	return LibraryController;
});
