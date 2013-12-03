module("forge.ui");

if (forge.is.mobile()) {
	$(function () {
		$('<input style="border: 2px solid red" type="datetime" class="enhance-one"></input>').insertAfter('#qunit-interact');
		$('<input style="border: 2px solid green" type="date" placeholder="date"></input>').insertAfter('#qunit-interact');
		$('<input style="border: 2px solid green" type="datetime" placeholder="datetime"></input>').insertAfter('#qunit-interact');
		$('<input style="border: 2px solid green" type="datetime-local" placeholder="datetime-local"></input>').insertAfter('#qunit-interact');
		$('<input style="border: 2px solid green" type="time" placeholder="time"></input>').insertAfter('#qunit-interact');
	});

	asyncTest("enhanceInput", 1, function () {
		forge.ui.enhanceInput('input.enhance-one', function () {
			askQuestion("Tap the red input: is it a nice datetime input?", {
				Yes: function () {
					ok(true);
					start();
				},
				No: function () {
					ok(false, "User claims bad datetime input");
					start();
				}
			});
		});
	});

	asyncTest("enhanceAllInputs", 1, function () {
		forge.ui.enhanceAllInputs(function () {
			askQuestion("Tap the green inputs: are they nice date / datetime inputs?", {
				Yes: function () {
					ok(true);
					start();
				},
				No: function () {
					ok(false, "User claims bad datetime input");
					start();
				}
			});
		});
	});
}
