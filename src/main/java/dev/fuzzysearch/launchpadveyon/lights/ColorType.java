package dev.fuzzysearch.launchpadveyon.lights;

public enum ColorType {
	/**
	 * Black color. (no color)
	 */
	DEFAULT,
	/**
	 * Shows that the Veyon device is loaded on the Launchpad.
	 */
	DEVICE_LOADED,
	/**
	 * Shows that the Veyon device is currently selected.
	 */
	DEVICE_ACTIVE,
	/**
	 * Shows that the Veyon device was previously not accessible.
	 */
	DEVICE_FAILED,
	/**
	 * Shows that the action is not selected.
	 */
	CONTEXT_INACTIVE,
	/**
	 * Shows that the action is selected.
	 */
	CONTEXT_ACTIVE,
	/**
	 * Color for remote access stop button.
	 */
	CONTEXT_STOP
}
