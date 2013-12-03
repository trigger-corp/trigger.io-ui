if (!forge.is.android()) {
	forge['ui'] = {
		'enhanceInput': function (selector, success, error) {
			var message = "forge.ui.enhanceInput is only available on Android.";
			forge.logging.warning(message);
			success && success();
		},
		'enhanceAllInputs': function (success, error) {
			var message = "forge.ui.enhanceAllInputs is only available on Android.";
			forge.logging.warning(message);
			success && success();
		}
	};
} else {
	forge['ui'] = {
		'enhanceInput': function (selector, success, error) {
			var triggerBlur = function (input) {
				var event = document.createEvent("HTMLEvents");
				event.initEvent("blur", true, true);
				input.dispatchEvent(event);
			};
			var fixInput = function (input) {
				var type = input.getAttribute("type");
				if (input.getAttribute('data-forge-fixed') != "yes" && (type == 'date' || type == 'datetime' || type == 'time' || type == 'datetime-local')) {
					input.disabled = true;
					input.addEventListener('touchend', function () {
						if (type == "date") {
							forge.internal.call('ui.datePicker', {value: input.value || ""}, function (result) {
								input.value = result.year+"-"+result.month+"-"+result.day;
								triggerBlur(input);
							});
						} else if (type == "datetime" || type == "datetime-local") {
							forge.internal.call('ui.datePicker', {value: input.value || ""}, function (resultDate) {
								forge.internal.call('ui.timePicker', {value: input.value || ""}, function (result) {
									dateTime = resultDate.year+"-"+resultDate.month+"-"+resultDate.day+"T"+result.hour+":"+result.minute;
									if (type == "datetime") {
										forge.internal.call('ui.dateTimeToUTC', {local: dateTime}, function (result) {
											input.value = result;
											triggerBlur(input);
										});
									} else if (type == "datetime-local") {
										input.value = dateTime;
										triggerBlur(input);
									}
								});
							});
						} else if (type == "time") {
							forge.internal.call('ui.timePicker', {value: input.value || ""}, function (result) {
								input.value = result.hour+":"+result.minute;
								triggerBlur(input);
							});
						}
					}, false);
					input.setAttribute('data-forge-fixed', "yes");
				}
			};
			
			var inputs = document.querySelectorAll(selector);
			for (i = 0; i < inputs.length; i++) {
				fixInput(inputs[i]);
			}
			success && success();
		},
		'enhanceAllInputs': function (success, error) {
			forge['ui']['enhanceInput']('input[type="date"]');
			forge['ui']['enhanceInput']('input[type="datetime"]');
			forge['ui']['enhanceInput']('input[type="datetime-local"]');
			forge['ui']['enhanceInput']('input[type="time"]');
			success && success();
		}
	};
}


