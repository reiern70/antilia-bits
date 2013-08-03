;(function( undefined ) {
	'use strict';

	if(typeof Wicket.SmartLinks == 'undefined') {
		Wicket.SmartLinks = {};
	}

	Wicket.SmartLinks.map = {};

	Wicket.SmartLink = function(id, clazz, url) {
		this.id = id;
		this.clazz = clazz;
		this.url = url;

		Wicket.SmartLinks.map[id] = this;

		// class or the links
		var $select = '#' + id + ' .' + 'click' + clazz;
		$( $select ).click( function() {
			var id = $(this).attr('id');
			var context = $(this).attr('context');
			Wicket.SmartLinks.clickLink(id, context);
		})
	}

	Wicket.SmartLinks.clickLink = function( linkid, context ) {
		var c = Wicket.SmartLinks.map[context];
		Wicket.Ajax.get({"u":c.url, "e": "click", "c":linkid, "ep":{ "linkid": linkid }});
	}
})();
