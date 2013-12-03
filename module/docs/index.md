``ui``: Native UI enhancements
==============================

This module includes miscellaneous native UI enhancements. This
currently consists of enhancement to date, time and datetime form
elements on Android.

##API

!method: forge.ui.enhanceInput(selector)
!param: selector `string` any input elements of type date, time or datetime matching this CSS selector will be enhanced.
!description: This method can be used to enhance a specific or set of specific input elements, when called any input elements of type ``date``, ``time``, ``datetime`` or ``datetime-local`` matching the given selector will be updated to show a native date picker when the user taps on them. The value the user selects will be inserted into the input element so it can be treated as normal by other JavaScript. The input element will also be disabled to prevent other user input into the field.
!platforms: Android

After the user selects a value from a picker the ``blur`` event will be
triggered on the input element. This is the same behavior as the native
pickers built into iOS.

!method: forge.ui.enhanceAllInputs()
!description: This method will enhance all appropriate input elements currently in the DOM.
!platforms: Android


