
Wicket.SignOut = function(timeout, url, onChange) {
    this.timeout = timeout;
    this.url = url;
    this.onChange = onChange;
    this.init();
}

Wicket.SignOut.prototype.init = function() {
    var self = this;
    Wicket.Event.subscribe('/ajax/call/before', function(jqEvent, attributes, jqXHR, errorThrown, textStatus) {
        self.reset();
    });

    Wicket.Event.subscribe('/ajax/call/after', function(jqEvent, attributes, jqXHR, errorThrown, textStatus) {
        self.reset();
    });
    this.reset();
    setTimeout(function(){self.countDown()}, 50);
}

Wicket.SignOut.prototype.reset = function() {
    this.counter = this.timeout;
}

Wicket.SignOut.prototype.countDown = function() {
    this.counter = this.counter-40;
    this.onChange(this.counter);
    var self = this;
    if(this.counter <= 0) {
        //trigger the server side sign out
        Wicket.Ajax.get({"u": self.url});
    } else {
        setTimeout(function(){self.countDown()}, 50);
    }
}