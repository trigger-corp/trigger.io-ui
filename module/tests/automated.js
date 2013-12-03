module("forge.ui");

if (forge.is.mobile()) {
	$(function () {
		$('<input style="border: 2px solid red" type="datetime" class="enhance-one"></input>').insertAfter('#qunit-interact');
		$('<input style="border: 2px solid green" type="date"></input>').insertAfter('#qunit-interact');
		$('<input style="border: 2px solid green" type="datetime"></input>').insertAfter('#qunit-interact');
	});

	asyncTest("enhanceInput", 1, function () {
		forge.ui.enhanceInput('input.enhance-one', function () {
			var enhanced = $('input.enhance-one[data-forge-fixed="yes"]');
			if (forge.is.android()) {
				ok(enhanced.length === 1);
			} else {
				ok(enhanced.length === 0);
			}
			start();
			// nest this test as we want the selective one to run first
			asyncTest("enhanceAllInputs", 1, function () {
				forge.ui.enhanceAllInputs(function () {
					var enhanced = $('input[data-forge-fixed="yes"]');
					if (forge.is.android()) {
						ok(enhanced.length === 3);
					} else {
						ok(enhanced.length === 0);
					}
					start();
				}, function (res) {
					forge.logging.error(res);
					ok(false);
					start();
				});
			});
		}, function (res) {
			forge.logging.error(res);
			ok(false);
			start();
		});
	});
}

