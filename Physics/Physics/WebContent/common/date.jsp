<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<title>Html日历插件</title>
<style type="text/css">
.gldp-default {
	position: absolute;
	font-family: 'helvetica';
}

/* Core style for every cell */
.gldp-default .core {
	box-sizing: border-box;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	position: relative;
	float: left;
	padding: 0;
	margin: 0;
	font-size: 14px;
	text-align: center;
	cursor: pointer;
	color: #222;
	background: #ffffff;
	background: -moz-linear-gradient(top,  #ffffff 0%, #dadada 98%, #ffffff 99%, #dadada 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#ffffff), color-stop(98%,#dadada), color-stop(99%,#ffffff), color-stop(100%,#dadada));
	background: -webkit-linear-gradient(top,  #ffffff 0%,#dadada 98%,#ffffff 99%,#dadada 100%);
	background: -o-linear-gradient(top,  #ffffff 0%,#dadada 98%,#ffffff 99%,#dadada 100%);
	background: -ms-linear-gradient(top,  #ffffff 0%,#dadada 98%,#ffffff 99%,#dadada 100%);
	background: linear-gradient(to bottom,  #ffffff 0%,#dadada 98%,#ffffff 99%,#dadada 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ffffff', endColorstr='#dadada',GradientType=0 );
}

/* Cell border */
.gldp-default .border {
	border-style: solid;
	border-width: 0;
	border-color: #888;
}

/* Month/Year text and select */
.gldp-default .monyear,
.gldp-default .monyear select {
	font-size: 16px !important;
	font-weight: bold;
	text-shadow: 1px 1px 0 rgba(255, 255, 255, 0.75);
}

/* Month/Year text */
.gldp-default .monyear span {
	margin: 0 5px 0 5px;
}

/* Prev/Next arrows */
.gldp-default .prev-arrow,
.gldp-default .next-arrow {
	color: #222;
	text-shadow: 1px 1px 0 rgba(255, 255, 255, 0.75);
}

.gldp-default .prev-arrow:active,
.gldp-default .next-arrow:active {
	color: #f00;
}

.gldp-default .prev-arrow-off,
.gldp-default .next-arrow-off {
	color: #222;
	opacity: 0.15;
}

/* Days of the week */
.gldp-default .dow {
	color: #fff;
	font-weight: bold;
	cursor: wait !important;
	background: #ff0000;
	background: -moz-linear-gradient(top,  #ff0000 0%, #ffcccc 1%, #ff0000 2%, #aa0000 98%, #dd4444 99%, #880000 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#ff0000), color-stop(1%,#dd4444), color-stop(2%,#ff0000), color-stop(98%,#aa0000), color-stop(99%,#ffcccc), color-stop(100%,#880000));
	background: -webkit-linear-gradient(top,  #ff0000 0%,#ffcccc 1%,#ff0000 2%,#aa0000 98%,#dd4444 99%,#880000 100%);
	background: -o-linear-gradient(top,  #ff0000 0%,#ffcccc 1%,#ff0000 2%,#aa0000 98%,#dd4444 99%,#880000 100%);
	background: -ms-linear-gradient(top,  #ff0000 0%,#ffcccc 1%,#ff0000 2%,#aa0000 98%,#dd4444 99%,#880000 100%);
	background: linear-gradient(to bottom,  #ff0000 0%,#ffcccc 1%,#ff0000 2%,#aa0000 98%,#dd4444 99%,#880000 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ff0000', endColorstr='#880000',GradientType=0 );
}

/* Weekdays */
.gldp-default .mon,
.gldp-default .tue,
.gldp-default .wed,
.gldp-default .thu,
.gldp-default .fri {
	font-weight: bold;
	text-shadow: 1px 1px 0 rgba(255, 255, 255, 0.75);
}

/* Weekend days */
.gldp-default .sat,
.gldp-default .sun {
	color: #3858a8;
	font-weight: bold;
	text-shadow: 1px 1px 0 rgba(255, 255, 255, 0.75);
}

/* Selectable days that are outside of current month being shown */
.gldp-default .outday {
	color: #666 !important;
}

/* Hover */
.gldp-default .mon:hover,
.gldp-default .tue:hover,
.gldp-default .wed:hover,
.gldp-default .thu:hover,
.gldp-default .fri:hover,
.gldp-default .sat:hover,
.gldp-default .sun:hover {
	background: #fcfff4;
	background: -moz-linear-gradient(top,  #fcfff4 0%, #e9e9ce 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#fcfff4), color-stop(100%,#e9e9ce));
	background: -webkit-linear-gradient(top,  #fcfff4 0%,#e9e9ce 100%);
	background: -o-linear-gradient(top,  #fcfff4 0%,#e9e9ce 100%);
	background: -ms-linear-gradient(top,  #fcfff4 0%,#e9e9ce 100%);
	background: linear-gradient(to bottom,  #fcfff4 0%,#e9e9ce 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#fcfff4', endColorstr='#e9e9ce',GradientType=0 );
}

/* Non-Selectable days */
.gldp-default .noday {
	color: #444;
	font-weight: normal;
	cursor: wait !important;
	background: #aaaaaa;
	background: -moz-linear-gradient(top,  #aaaaaa 0%, #8a8a8a 98%, #aaaaaa 99%, #8a8a8a 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#aaaaaa), color-stop(98%,#8a8a8a), color-stop(99%,#aaaaaa), color-stop(100%,#8a8a8a)); /* Chrome,Safari4+ */
	background: -webkit-linear-gradient(top,  #aaaaaa 0%,#8a8a8a 98%,#aaaaaa 99%,#8a8a8a 100%);
	background: -o-linear-gradient(top,  #aaaaaa 0%,#8a8a8a 98%,#aaaaaa 99%,#8a8a8a 100%);
	background: -ms-linear-gradient(top,  #aaaaaa 0%,#8a8a8a 98%,#aaaaaa 99%,#8a8a8a 100%);
	background: linear-gradient(to bottom,  #aaaaaa 0%,#8a8a8a 98%,#aaaaaa 99%,#8a8a8a 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#aaaaaa', endColorstr='#8a8a8a',GradientType=0 );
}

/* Currently selected day */
.gldp-default .selected {
	color: #fff;
	font-weight: bold;
	border-color: #c00;
	background: #e8bf88;
	background: -moz-linear-gradient(top,  #e8bf88 0%, #ffc19d 1%, #fd8642 2%, #ab2412 98%, #ef753f 99%, #ef753f 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#e8bf88), color-stop(1%,#ffc19d), color-stop(2%,#fd8642), color-stop(98%,#ab2412), color-stop(99%,#ef753f), color-stop(100%,#ef753f));
	background: -webkit-linear-gradient(top,  #e8bf88 0%,#ffc19d 1%,#fd8642 2%,#ab2412 98%,#ef753f 99%,#ef753f 100%);
	background: -o-linear-gradient(top,  #e8bf88 0%,#ffc19d 1%,#fd8642 2%,#ab2412 98%,#ef753f 99%,#ef753f 100%);
	background: -ms-linear-gradient(top,  #e8bf88 0%,#ffc19d 1%,#fd8642 2%,#ab2412 98%,#ef753f 99%,#ef753f 100%);
	background: linear-gradient(to bottom,  #e8bf88 0%,#ffc19d 1%,#fd8642 2%,#ab2412 98%,#ef753f 99%,#ef753f 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#e8bf88', endColorstr='#ef753f',GradientType=0 );
}

/* Today */
.gldp-default .today {
	color: #fff;
	font-weight: bold;
	border-color: #00c;
	background: #88bfe8;
	background: -moz-linear-gradient(top,  #88bfe8 0%, #9dc1ff 1%, #4286fd 2%, #0124ab 98%, #3775ef 99%, #3775ef 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#88bfe8), color-stop(1%,#9dc1ff), color-stop(2%,#4286fd), color-stop(98%,#0124ab), color-stop(99%,#3775ef), color-stop(100%,#3775ef));
	background: -webkit-linear-gradient(top,  #88bfe8 0%,#9dc1ff 1%,#4286fd 2%,#0124ab 98%,#3775ef 99%,#3775ef 100%);
	background: -o-linear-gradient(top,  #88bfe8 0%,#9dc1ff 1%,#4286fd 2%,#0124ab 98%,#3775ef 99%,#3775ef 100%);
	background: -ms-linear-gradient(top,  #88bfe8 0%,#9dc1ff 1%,#4286fd 2%,#0124ab 98%,#3775ef 99%,#3775ef 100%);
	background: linear-gradient(to bottom,  #88bfe8 0%,#9dc1ff 1%,#4286fd 2%,#0124ab 98%,#3775ef 99%,#3775ef 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#88bfe8', endColorstr='#3775ef',GradientType=0 );
}

/* Special */
.gldp-default .special {
	color: #fff;
	font-weight: bold;
	border-color: #0c0;
	background: #88e888;
	background: -moz-linear-gradient(top,  #88e888 0%, #9dff9d 1%, #42fd42 2%, #01ab01 98%, #37ef37 99%, #37ef37 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#88e888), color-stop(1%,#9dff9d), color-stop(2%,#42fd42), color-stop(98%,#01ab01), color-stop(99%,#37ef37), color-stop(100%,#37ef37));
	background: -webkit-linear-gradient(top,  #88e888 0%,#9dff9d 1%,#42fd42 2%,#01ab01 98%,#37ef37 99%,#37ef37 100%);
	background: -o-linear-gradient(top,  #88e888 0%,#9dff9d 1%,#42fd42 2%,#01ab01 98%,#37ef37 99%,#37ef37 100%);
	background: -ms-linear-gradient(top,  #88e888 0%,#9dff9d 1%,#42fd42 2%,#01ab01 98%,#37ef37 99%,#37ef37 100%);
	background: linear-gradient(to bottom,  #88e888 0%,#9dff9d 1%,#42fd42 2%,#01ab01 98%,#37ef37 99%,#37ef37 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#88e888', endColorstr='#37ef37',GradientType=0 );
}
</style>
</head>
<body>
<input type="text" id="example" style="
    height: 0px;
    padding-bottom: 0px;
    border-top-width: 0px;
    border-bottom-width: 0px;
    border-left-width: 0px;
    border-right-width: 0px;" />

</body>
<script type="text/javascript">
$(window).load(function() {
	$('#example').glDatePicker({
		showAlways : true,
		format : 'yyyy-mm-dd'
	});
});
(function() {
    $.fn.glDatePicker = function(options) {
        var pluginName = "glDatePicker";
        var instance = this.data(pluginName);
        if (!instance) {
            return this.each(function() {
                return $(this).data(pluginName, new glDatePicker(this,options))
            }
            )
        }
        return (options === true) ? instance : this
    }
    ;
    $.fn.glDatePicker.defaults = {
        format: "yyyy-mm-dd",
        cssName: "default",
        zIndex: 1000,
        borderSize: 1,
        calendarOffset: {
            x: 0,
            y: 1
        },
        showAlways: false,
        hideOnClick: true,
        allowMonthSelect: true,
        allowYearSelect: true,
        todayDate: new Date(),
        selectedDate: null ,
        prevArrow: "\u25c4",
        nextArrow: "\u25ba",
        selectableDates: null ,
        selectableDateRange: null ,
        specialDates: null ,
        selectableMonths: null ,
        selectableYears: null ,
        selectableDOW: null ,
        monthNames: null ,
        dowNames: null ,
        dowOffset: 0,
        onClick: (function(el, cell, date, data) {
            el.val(date.format(this.format))
        }),
        onHover: function(el, cell, date, data) {},
        onShow: function(calendar) {
            calendar.show()
        },
        onHide: function(calendar) {
            calendar.hide()
        },
        firstDate: null 
    };
    var glDatePicker = (function() {
        function glDatePicker(element, userOptions) {
            var self = this;
            self.el = $(element);
            var el = self.el;
            self.options = $.extend(true, {}, $.fn.glDatePicker.defaults, userOptions);
            var options = self.options;
            self.calendar = $($.find("[gldp-el=" + el.attr("gldp-id") + " ]"));
            options.selectedDate = options.selectedDate || options.todayDate;
            options.firstDate = (new Date((options.firstDate || options.selectedDate)))._first();
            if (!(el.attr("gldp-id") || "").length) {
                el.attr("gldp-id", "gldp-" + Math.round(Math.random() * 10000000000))
            }
            el.addClass("gldp-el").bind("click", function(e) {
                self.show(e)
            }
            ).bind("focus", function(e) {
                self.show(e)
            }
            );
            if (self.calendar.length && !options.showAlways) {
                self.calendar.hide()
            }
            $(document).bind("mouseup", function(e) {
                var target = e.target;
                var calendar = self.calendar;
                if (!el.is(target) && !calendar.is(target) && calendar.has(target).length === 0 && calendar.is(":visible")) {
                    self.hide()
                }
            }
            );
            self.render()
        }
        glDatePicker.prototype = {
            show: function() {
                $.each($(".gldp-el").not(this.el), function(i, o) {
                    if (o.length) {
                        o.options.onHide(o.calendar)
                    }
                }
                );
                this.options.onShow(this.calendar)
            },
            hide: function() {
                if (this.options && !this.options.showAlways) {
                    this.options.onHide(this.calendar)
                }
            },
            render: function(renderCalback) {
                var self = this;
                var el = self.el;
                var options = self.options;
                var calendar = self.calendar;
                var coreClass = " core border ";
                var cssName = "gldp-" + options.cssName;
                var todayVal = options.todayDate._val();
                var todayTime = todayVal.time;
                var maxRow = 6;
                var maxCol = 7;
                var borderSize = options.borderSize + "px";
                var getSelectableList = function(min, max, userList) {
                    var resultList = [];
                    for (var i = min; i <= max; i++) {
                        resultList.push(i)
                    }
                    if (userList) {
                        var newList = [];
                        $.each(userList, function(i, v) {
                            if (v >= min && v <= max && newList.indexOf(v) < 0) {
                                newList.push(v)
                            }
                        }
                        );
                        resultList = newList.length ? newList : resultList
                    }
                    resultList.sort();
                    return resultList
                }
                ;
                var selectableMonths = getSelectableList(0, 11, options.selectableMonths);
                var selectableYears = getSelectableList(todayVal.year - 5, todayVal.year + 5, options.selectableYears);
                var selectableDOW = getSelectableList(0, 6, options.selectableDOW);
                var dowNames = options.dowNames || ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
                var monthNames = options.monthNames || ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
                var containerWidth = el.outerWidth();
                var containerHeight = containerWidth;
                var getCellSize = function(_size, _count) {
                    return (_size / _count) + ((options.borderSize / _count) * (_count - 1))
                }
                ;
                var cellWidth = getCellSize(containerWidth, maxCol);
                var cellHeight = getCellSize(containerHeight, maxRow + 2);
                if (!calendar.length) {
                    self.calendar = calendar = $("<div/>").attr("gldp-el", el.attr("gldp-id")).data("is", true).css({
                        display: (options.showAlways ? undefined : "none"),
                        zIndex: options.zIndex,
                        width: (cellWidth * maxCol) + "px"
                    });
                    $("body").append(calendar)
                } else {
                    if (!eval(calendar.data("is"))) {
                        containerWidth = calendar.outerWidth();
                        containerHeight = calendar.outerHeight();
                        cellWidth = getCellSize(containerWidth, maxCol);
                        cellHeight = getCellSize(containerHeight, maxRow + 2)
                    }
                }
                if (!el.is(":visible")) {
                    calendar.hide()
                }
                calendar.removeClass().addClass(cssName).children().remove();
                var onResize = function() {
                    var elPos = el.offset();
                    calendar.css({
                        top: (elPos.top + el.outerHeight() + options.calendarOffset.y) + "px",
                        left: (elPos.left + options.calendarOffset.x) + "px"
                    })
                }
                ;
                $(window).resize(onResize);
                onResize();
                var cellCSS = {
                    width: cellWidth + "px",
                    height: cellHeight + "px",
                    lineHeight: cellHeight + "px"
                };
                var setFirstDate = function(_date) {
                    if (_date) {
                        options.firstDate = _date;
                        self.render()
                    }
                }
                ;
                var getFirstDate = function(_offset) {
                    var _date = new Date(options.firstDate);
                    _offset = _offset || 0;
                    while (true) {
                        _date.setMonth(_date.getMonth() + _offset);
                        _date.setDate(Math.min(1, _date._max()));
                        if (_offset == 0) {
                            break
                        }
                        var dateVal = _date._val();
                        var dateMonth = dateVal.month;
                        var dateYear = dateVal.year;
                        if (selectableMonths.indexOf(dateMonth) != -1) {
                            if (selectableYears.indexOf(dateYear) != -1) {
                                break
                            } else {
                                if (dateYear < selectableYears[0] || dateYear > selectableYears[selectableYears.length - 1]) {
                                    return null 
                                }
                            }
                        }
                    }
                    return _date
                }
                ;
                var prevFirstDate = getFirstDate(-1);
                var nextFirstDate = getFirstDate(1);
                var firstDate = (options.firstDate = getFirstDate());
                var firstDateVal = firstDate._val();
                var firstDateMonth = firstDateVal.month;
                var firstDateYear = firstDateVal.year;
                var startDate = new Date(firstDate);
                var dowOffset = Math.abs(Math.min(6, Math.max(0, options.dowOffset)));
                var startOffset = startDate.getDay() - dowOffset;
                startOffset = startOffset < 1 ? -7 - startOffset : -startOffset;
                dowNames = (dowNames.concat(dowNames)).slice(dowOffset, dowOffset + 7);
                startDate._add(startOffset);
                var showPrev = (prevFirstDate);
                var showNext = (nextFirstDate);
                var monyearClass = coreClass + "monyear ";
                var prevCell = $("<div/>").addClass(monyearClass).css($.extend({}, cellCSS, {
                    borderWidth: borderSize + " 0 0 " + borderSize
                })).append($("<a/>").addClass("prev-arrow" + (showPrev ? "" : "-off")).html(options.prevArrow)).mousedown(function() {
                    return false
                }
                ).click(function(e) {
                    if (options.prevArrow != "" && showPrev) {
                        e.stopPropagation();
                        setFirstDate(prevFirstDate)
                    }
                }
                );
                var titleCellCount = maxCol - 2;
                var titleWidth = (cellWidth * titleCellCount) - (titleCellCount * options.borderSize) + (options.borderSize);
                var titleCell = $("<div/>").addClass(monyearClass + "title").css($.extend({}, cellCSS, {
                    width: titleWidth + "px",
                    borderTopWidth: borderSize,
                    marginLeft: "-" + (borderSize)
                }));
                var nextCell = $("<div/>").addClass(monyearClass).css($.extend({}, cellCSS, {
                    marginLeft: "-" + (borderSize),
                    borderWidth: borderSize + " " + borderSize + " 0 0"
                })).append($("<a/>").addClass("next-arrow" + (showNext ? "" : "-off")).html(options.nextArrow)).mousedown(function() {
                    return false
                }
                ).click(function(e) {
                    if (options.nextArrow != "" && showNext) {
                        e.stopPropagation();
                        setFirstDate(nextFirstDate)
                    }
                }
                );
                calendar.append(prevCell).append(titleCell).append(nextCell);
                for (var row = 0, cellIndex = 0; row < maxRow + 1; row++) {
                    for (var col = 0; col < maxCol; col++,
                    cellIndex++) {
                        var cellDate = new Date(startDate);
                        var cellClass = "day";
                        var cellZIndex = options.zIndex + (cellIndex);
                        var cell = $("<div/>");
                        if (!row) {
                            cellClass = "dow";
                            cell.html(dowNames[col]);
                            cellDate = null 
                        } else {
                            cellDate._add(col + ((row - 1) * maxCol));
                            var cellDateVal = cellDate._val();
                            var cellDateTime = cellDateVal.time;
                            var specialData = null ;
                            var isSelectable = true;
                            var getRepeatDate = function(v, date) {
                                if (v.repeatYear === true) {
                                    date.setYear(cellDateVal.year)
                                }
                                if (v.repeatMonth === true) {
                                    date.setMonth(cellDateVal.month)
                                }
                                return date._val()
                            }
                            ;
                            cell.html(cellDateVal.date);
                            if (options.selectableDateRange) {
                                isSelectable = false;
                                $.each(options.selectableDateRange, function(i, v) {
                                    var dateFrom = v.from;
                                    var dateTo = (v.to || null );
                                    dateTo = dateTo || new Date(v.from.getFullYear(),v.from.getMonth(),v.from._max());
                                    dateFrom = getRepeatDate(v, dateFrom);
                                    dateTo = getRepeatDate(v, dateTo);
                                    if (cellDateTime >= dateFrom.time && cellDateTime <= dateTo.time) {
                                        isSelectable = true;
                                        return true
                                    }
                                }
                                )
                            }
                            if (options.selectableDates) {
                                if ((options.selectableDateRange && !isSelectable) || (isSelectable && !options.selectableDateRange)) {
                                    isSelectable = false
                                }
                                $.each(options.selectableDates, function(i, v) {
                                    var vDate = getRepeatDate(v, v.date);
                                    if (vDate.time == cellDateTime) {
                                        return ( isSelectable = true) 
                                    }
                                }
                                )
                            }
                            if (!isSelectable || selectableYears.indexOf(cellDateVal.year) < 0 || selectableMonths.indexOf(cellDateVal.month) < 0 || selectableDOW.indexOf(cellDateVal.day) < 0) {
                                cellClass = "noday"
                            } else {
                                cellClass = (["sun", "mon", "tue", "wed", "thu", "fri", "sat"])[cellDateVal.day];
                                if (firstDateMonth != cellDateVal.month) {
                                    cellClass += " outday"
                                }
                                if (todayTime == cellDateTime) {
                                    cellClass = "today";
                                    cellZIndex += 50
                                }
                                if (options.selectedDate._time() == cellDateTime) {
                                    cellClass = "selected";
                                    cellZIndex += 51
                                }
                                if (options.specialDates) {
                                    $.each(options.specialDates, function(i, v) {
                                        var vDate = getRepeatDate(v, v.date);
                                        if (vDate.time == cellDateTime) {
                                            cellClass = (v.cssClass || "special");
                                            cellZIndex += 52;
                                            specialData = v.data
                                        }
                                    }
                                    )
                                }
                                cell.mousedown(function() {
                                    return false
                                }
                                ).hover(function(e) {
                                    e.stopPropagation();
                                    var hoverData = $(this).data("data");
                                    options.onHover(el, cell, hoverData.date, hoverData.data)
                                }
                                ).click(function(e) {
                                    e.stopPropagation();
                                    var clickedData = $(this).data("data");
                                    options.selectedDate = options.firstDate = clickedData.date;
                                    self.render(function() {
                                        if (!options.showAlways && options.hideOnClick) {
                                            self.hide()
                                        }
                                    }
                                    );
                                    options.onClick(el, $(this), clickedData.date, clickedData.data)
                                }
                                )
                            }
                        }
                        $.extend(cellCSS, {
                            borderTopWidth: borderSize,
                            borderBottomWidth: borderSize,
                            borderLeftWidth: (row > 0 || (!row && !col)) ? borderSize : 0,
                            borderRightWidth: (row > 0 || (!row && col == 6)) ? borderSize : 0,
                            marginLeft: (col > 0) ? "-" + (borderSize) : 0,
                            marginTop: (row > 0) ? "-" + (borderSize) : 0,
                            zIndex: cellZIndex
                        });
                        cell.data("data", {
                            date: cellDate,
                            data: specialData
                        }).addClass(coreClass + cellClass).css(cellCSS);
                        calendar.append(cell)
                    }
                }
                var toggleYearMonthSelect = function(showYear) {
                    var show = "inline-block";
                    var hide = "none";
                    if (options.allowMonthSelect) {
                        monthText.css({
                            display: !showYear ? hide : show
                        });
                        monthSelect.css({
                            display: !showYear ? show : hide
                        })
                    }
                    if (options.allowYearSelect) {
                        yearText.css({
                            display: showYear ? hide : show
                        });
                        yearSelect.css({
                            display: showYear ? show : hide
                        })
                    }
                }
                ;
                var onYearMonthSelect = function() {
                    options.firstDate = new Date(yearSelect.val(),monthSelect.val(),1);
                    self.render()
                }
                ;
                var monthSelect = $("<select/>").hide().change(onYearMonthSelect);
                var yearSelect = $("<select/>").hide().change(onYearMonthSelect);
                var monthText = $("<span/>").html(monthNames[firstDateMonth]).mousedown(function() {
                    return false
                }
                ).click(function(e) {
                    e.stopPropagation();
                    toggleYearMonthSelect(false)
                }
                );
                var yearText = $("<span/>").html(firstDateYear).mousedown(function() {
                    return false
                }
                ).click(function(e) {
                    e.stopPropagation();
                    toggleYearMonthSelect(true)
                }
                );
                $.each(monthNames, function(i, v) {
                    if (options.allowMonthSelect && selectableMonths.indexOf(i) != -1) {
                        var o = $("<option/>").html(v).attr("value", i);
                        if (i == firstDateMonth) {
                            o.attr("selected", "selected")
                        }
                        monthSelect.append(o)
                    }
                }
                );
                $.each(selectableYears, function(i, v) {
                    if (options.allowYearSelect) {
                        var o = $("<option/>").html(v).attr("value", v);
                        if (v == firstDateYear) {
                            o.attr("selected", "selected")
                        }
                        yearSelect.append(o)
                    }
                }
                );
                var titleYearMonth = $("<div/>").append(monthText).append(monthSelect).append(yearText).append(yearSelect);
                titleCell.children().remove();
                titleCell.append(titleYearMonth);
                renderCalback = renderCalback || (function() {}
                );
                renderCalback()
            }
        };
        return glDatePicker
    }
    )();
    var dateFormat = function() {
        var token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g
          , timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g
          , timezoneClip = /[^-+\dA-Z]/g
          , pad = function(val, len) {
            val = String(val);
            len = len || 2;
            while (val.length < len) {
                val = "0" + val
            }
            return val
        }
        ;
        return function(date, mask, utc) {
            var dF = dateFormat;
            if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
                mask = date;
                date = undefined
            }
            date = date ? new Date(date) : new Date;
            if (isNaN(date)) {
                throw SyntaxError("invalid date")
            }
            mask = String(dF.masks[mask] || mask || dF.masks["default"]);
            if (mask.slice(0, 4) == "UTC:") {
                mask = mask.slice(4);
                utc = true
            }
            var _ = utc ? "getUTC" : "get"
              , d = date[_ + "Date"]()
              , D = date[_ + "Day"]()
              , m = date[_ + "Month"]()
              , y = date[_ + "FullYear"]()
              , H = date[_ + "Hours"]()
              , M = date[_ + "Minutes"]()
              , s = date[_ + "Seconds"]()
              , L = date[_ + "Milliseconds"]()
              , o = utc ? 0 : date.getTimezoneOffset()
              , flags = {
                d: d,
                dd: pad(d),
                ddd: dF.i18n.dayNames[D],
                dddd: dF.i18n.dayNames[D + 7],
                m: m + 1,
                mm: pad(m + 1),
                mmm: dF.i18n.monthNames[m],
                mmmm: dF.i18n.monthNames[m + 12],
                yy: String(y).slice(2),
                yyyy: y,
                h: H % 12 || 12,
                hh: pad(H % 12 || 12),
                H: H,
                HH: pad(H),
                M: M,
                MM: pad(M),
                s: s,
                ss: pad(s),
                l: pad(L, 3),
                L: pad(L > 99 ? Math.round(L / 10) : L),
                t: H < 12 ? "a" : "p",
                tt: H < 12 ? "am" : "pm",
                T: H < 12 ? "A" : "P",
                TT: H < 12 ? "AM" : "PM",
                Z: utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
                o: (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
                S: ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
            };
            return mask.replace(token, function($0) {
                return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1)
            }
            )
        }
    }
    ();
    dateFormat.masks = {
        "default": "ddd mmm dd yyyy HH:MM:ss",
        shortDate: "m/d/yy",
        mediumDate: "mmm d, yyyy",
        longDate: "mmmm d, yyyy",
        fullDate: "dddd, mmmm d, yyyy",
        shortTime: "h:MM TT",
        mediumTime: "h:MM:ss TT",
        longTime: "h:MM:ss TT Z",
        isoDate: "yyyy-mm-dd",
        isoTime: "HH:MM:ss",
        isoDateTime: "yyyy-mm-dd'T'HH:MM:ss",
        isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
    };
    dateFormat.i18n = {
        dayNames: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
        monthNames: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
    };
    (function() {
        Date.prototype._clear = function() {
            this.setHours(0);
            this.setMinutes(0);
            this.setSeconds(0);
            this.setMilliseconds(0);
            return this
        }
        ;
        Date.prototype.format = function(mask, utc) {
            return dateFormat(this, mask, utc)
        }
        ;
        Date.prototype._time = function() {
            return this._clear().getTime()
        }
        ;
        Date.prototype._max = function() {
            var isLeapYear = (new Date(this.getYear(),1,29).getMonth() == 1) ? 1 : 0;
            var days = [31, 28 + isLeapYear, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
            return days[this.getMonth()]
        }
        ;
        Date.prototype._add = function(days) {
            this.setDate(this.getDate() + days)
        }
        ;
        Date.prototype._first = function() {
            var date = new Date(this);
            date.setDate(1);
            return date
        }
        ;
        Date.prototype._val = function() {
            this._clear();
            return {
                year: this.getFullYear(),
                month: this.getMonth(),
                date: this.getDate(),
                time: this.getTime(),
                day: this.getDay()
            }
        }
    }
    )()
}
)();
</script>