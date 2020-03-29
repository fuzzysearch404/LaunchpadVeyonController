package dev.fuzzysearch.launchpadveyon.utils;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

import dev.fuzzysearch.launchpadveyon.launchpad.control.LaunchpadLightManager;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Pad;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;

/**
 * Creates a process by executing Veyon CLI command.
 * Includes {@link CountDownLatch} to make sure
 * that {@link Process} is only accessible 
 * after it is created.
 *
 * @author Roberts Ziediņš
*/
public class VeyonProcessExecutor implements Runnable {
	
	private Runtime run;
	private String cmd;
	private Pad pad;
	private volatile Process process;
	private final CountDownLatch latch = new CountDownLatch(1);

	public VeyonProcessExecutor(String cmd, Pad pad) {
		this.cmd = cmd;
		this.pad = pad;
		run = Runtime.getRuntime();
	}
	
	public Process getProcess() {
		return process;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	/**
	 * Creates, executes {@link Process}
	 * @return {@link String} - full output of the process.
	 */
	private String execute(String cmd) throws IOException, InterruptedException {
		process = run.exec(cmd);
		latch.countDown();
		
		BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String line = "";
		String fullOutput = "";
		
		while ((line=buf.readLine()) != null) {
			System.out.println(line);
			fullOutput += line;
		}

		process.waitFor();
		
		return fullOutput;
	}

	@Override
	public void run() {
		String processOutput = null;
		try {
			processOutput = execute(cmd);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		if(processOutput == null) return;
		finalizeProcess(processOutput);
		
	}
	
	/**
	 * After the {@link Process} ends, this method is
	 * called to update {@link MidiLaunchpad}'s lights.
	 * 
	 * @param processOutput - output of the process
	 */
	private void finalizeProcess(String processOutput) {
		ProgramManager manager = ProgramManager.getInstance();
		LaunchpadLightManager lightManager = manager.getLightManager();
		
		if(processOutput.equals(VEYON_CLI_FAIL)) {
			
			lightManager.blinkPad(pad, 
					COLOR_DEVICE_FAILED, COLOR_DEVICE_LOADED, COLOR_DEVICE_FAILED,
					DEFAULT_ERROR_BLINK_TIMES, DEFAULT_ERROR_BLINK_MILLISECONDS);
		
			lightManager.setPadLight(pad, COLOR_DEVICE_FAILED, BackBufferOperation.COPY);
		}
		else if(processOutput.equals(VEYON_CLI_OK)) {
			manager.getLightManager().setPadLight(
					pad, COLOR_DEVICE_LOADED, BackBufferOperation.COPY);
		}
	}
	
}