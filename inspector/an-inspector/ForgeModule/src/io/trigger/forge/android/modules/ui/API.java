package io.trigger.forge.android.modules.ui;

import io.trigger.forge.android.core.ForgeApp;
import io.trigger.forge.android.core.ForgeParam;
import io.trigger.forge.android.core.ForgeTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.gson.JsonObject;

public class API {
	
	public static void dateTimeToUTC(final ForgeTask task, @ForgeParam("local") String local) {
		final Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		try {
			Date existing = df.parse(local);
			c.setTime(existing);
			df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			task.success(df.format(c.getTime()));
		} catch (ParseException e) {
			task.error("Could not parse date", "EXPECTED_FAILURE", null);
		}
	}
	
	public static void datePicker(final ForgeTask task, @ForgeParam("value") String existingValue) {
		final Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		try {
			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date existing = df.parse(existingValue);
			c.setTime(existing);
		} catch (ParseException e) {
			df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			try {
				Date existing = df.parse(existingValue);
				c.setTime(existing);
			} catch (ParseException e2) {
				df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date existing = df.parse(existingValue);
					c.setTime(existing);
				} catch (ParseException e3) { /* use now */ }
			}
		}
		final int year = c.get(Calendar.YEAR), month = c.get(Calendar.MONTH), day = c.get(Calendar.DAY_OF_MONTH);
		
		task.performUI(new Runnable() {
			public void run() {
				final DatePickerDialog dialog = new DatePickerDialog(ForgeApp.getActivity(), new OnDateSetListener() {
					public void onDateSet(DatePicker view, final int year, final int monthOfYear,
							final int dayOfMonth) {
						JsonObject result = new JsonObject();
						result.addProperty("year", String.format("%04d", year));
						result.addProperty("month", String.format("%02d", (monthOfYear+1)));
						result.addProperty("day", String.format("%02d", dayOfMonth));
						task.success(result);
					}
				}, year, month, day);
				dialog.setOnCancelListener(new OnCancelListener() {
					public void onCancel(DialogInterface dialog) {
						task.error("User cancelled", "EXPECTED_FAILURE", null);
					}
				});
				dialog.setOnDismissListener(new OnDismissListener() {
					public void onDismiss(DialogInterface dialog) {
						if (!task.returned) {
							task.error("User cancelled", "EXPECTED_FAILURE", null);
						}
					}
				});
				dialog.show();
			}
		});
	}

	public static void timePicker(final ForgeTask task, @ForgeParam("value") String existingValue) {
		final Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("HH:mm");
		try {
			Date existing = df.parse(existingValue);
			c.setTime(existing);
		} catch (ParseException e) {
			df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
			try {
				df.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date existing = df.parse(existingValue);
				c.setTime(existing);
			} catch (ParseException e2) {
				df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
				try {
					Date existing = df.parse(existingValue);
					c.setTime(existing);
				} catch (ParseException e3) { /* use now */ }
			}
		}
		final int hour = c.get(Calendar.HOUR_OF_DAY), minute = c.get(Calendar.MINUTE);

		task.performUI(new Runnable() {
			public void run() {
				final TimePickerDialog dialog = new TimePickerDialog(ForgeApp
						.getActivity(), new OnTimeSetListener() {
					public void onTimeSet(TimePicker view, final int hourOfDay,
							final int minute) {
						JsonObject result = new JsonObject();
						result.addProperty("hour", String.format("%02d", hourOfDay));
						result.addProperty("minute", String.format("%02d", minute));
						task.success(result);
					}
				}, hour, minute, false);
				dialog.setOnCancelListener(new OnCancelListener() {
					public void onCancel(DialogInterface dialog) {
						task.error("User cancelled", "EXPECTED_FAILURE", null);
					}
				});
				dialog.setOnDismissListener(new OnDismissListener() {
					public void onDismiss(DialogInterface dialog) {
						if (!task.returned) {
							task.error("User cancelled", "EXPECTED_FAILURE", null);
						}
					}
				});
				dialog.show();
			}
		});
	}
}
