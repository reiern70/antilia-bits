Wicket.SignOut = function(timeout, url, onChange) {
    this.timeout = timeout; // Timeout in miliseconds.
    this.url = url;
    this.onChange = onChange;
    // Add timeout (in seconds) to current time to get absolute timeout time.
    this.timeoutTime  = new Date();
    this.timeoutTime.setSeconds(this.timeoutTime.getSeconds() + this.timeout / 1000);
    this.init();
};

Wicket.SignOut.prototype.init = function() {
    var self = this;
    Wicket.Event.subscribe('/ajax/call/before', function(jqEvent, attributes, jqXHR, errorThrown, textStatus) {
        self.reset();
    });

    Wicket.Event.subscribe('/ajax/call/after', function(jqEvent, attributes, jqXHR, errorThrown, textStatus) {
        self.reset();
    });
    this.reset();
    // Set interval to one quarter of the timeout time.
    var interval = this.timeout / 4;
    // console.log("interval", interval);
    setInterval(function(){self.countDown();}, interval);
};

Wicket.SignOut.prototype.reset = function() {
    // console.log("Reset Called");
    this.timeoutTime  = new Date();
    this.timeoutTime.setSeconds(this.timeoutTime.getSeconds() + this.timeout / 1000);
};

Wicket.SignOut.prototype.countDown = function() {
    now = new Date();
    // console.log("Countdown Called");
    // console.log("now, timeoutTime", now, this.timeoutTime);
    try {
    	this.onChange(this.timeoutTime, now);
    } catch(e) {
    	
    }
    if (now > this.timeoutTime){
        //trigger the server side sign out
        Wicket.Ajax.get({"u": this.url});
    }
};