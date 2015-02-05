(function($) {

    $.fn.pager = function(options) {
    	
        var opts = $.extend({}, $.fn.pager.defaults, options);

        return this.each(function() {
        	
        // empty out the destination element and then render out the pager with the supplied options
            $(this).empty().append(renderpager(parseInt(options.pagenumber), parseInt(options.pagecount), parseInt(options.pagesize), options.readonly, options.buttonClickCallback));
            
            // specify correct cursor activity
            $('.pages li').mouseover(function() { document.body.style.cursor = "pointer"; }).mouseout(function() { document.body.style.cursor = "auto"; });
        });
    };

    // render and return the pager with the supplied options
    function renderpager(pagenumber, pagecount, pagesize, pagesizereadonly, buttonClickCallback) {
    	
        // setup $pager to hold render
        var $pager = $('<ul class="pages"></ul>');

        // add in the previous and next buttons
        $pager.append(renderButton('first', pagenumber, pagecount, pagesize, buttonClickCallback)).append(renderButton('prev', pagenumber, pagecount, pagesize, buttonClickCallback));

        var stmp1 = '第 ' + '<input id="currPage" name="currPage" type="text" size=1 value="' + (pagenumber) + '" onchange="checkCurrent(this.value)" />' + ' 页    ';
        var stmp2 = '总共' + '<p0>0</p0>条   ' + '<p1>' + (pagecount) + '</p1>' + '页';
        var stmp3;
        if (pagesizereadonly != "true") {
        	stmp3 = '    每页 ' + '<input id="pageSize" name="pageSize" type="text" size=1 value="' + (pagesize) + '" onchange="checkPageSize(this.value)"/>' + ' 条';
        } else {
        	stmp3 = '    每页 ' + '<input id="pageSize" name="pageSize" type="text" size=1 value="' + (pagesize) + '" readonly="readonly" onchange="checkPageSize(this.value)"/>' + ' 条';
        }
        var pageReadonlyHidd  = '<input type="hidden" name="pageReadonlyHidd" id="pageReadonlyHidd" value="' + pagesizereadonly + '">';
        var currentButton = $('<li class="pgCurrent">' + (stmp1) + (stmp2) + (stmp3) + pageReadonlyHidd + '</li>');
        currentButton.appendTo($pager);

        // render in the next and last buttons before returning the whole rendered control back.
        $pager.append(renderButton('next', pagenumber, pagecount, pagesize, buttonClickCallback)).append(renderButton('last', pagenumber, pagecount, pagesize, buttonClickCallback));

        return $pager;
    }
    
    // renders and returns a 'specialized' button, ie 'next', 'previous' etc. rather than a page number button
    function renderButton(buttonLabel, pagenumber, pagecount, pagesize, buttonClickCallback) {
    	
        var img = '';

        // work out destination page for required button type
        switch (buttonLabel) {
            case "first":
                img = '<img src="img/Backward_01.png"/>';
                break;
            case "prev":
                img = '<img src="img/Backward.png"/>';
                break;
            case "next":
                img = '<img src="img/Forward.png"/>';
                break;
            case "last":
                img = '<img src="img/Forward_01.png"/>';
                break;
        }
        
        var $Button = $('<li class="pgNext">' + img + '</li>');

        // disable and 'grey' out buttons if not needed.
//        if (buttonLabel == "first" || buttonLabel == "prev") {        	
//        	pagenumber <= 1 ? $Button.addClass('pgEmpty') : $Button.click(function() { buttonClickCallback(buttonLabel); });        	
//        }
//        else {
//        	pagenumber >= pagecount ? $Button.addClass('pgEmpty') : $Button.click(function() { buttonClickCallback(buttonLabel); });     
//        }
                
        $Button.click(function() { buttonClickCallback(buttonLabel); });

        return $Button;
    }
})(jQuery);

