package dev.fuzzysearch.launchpadveyon.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

/**
 * Creates a process by executing shell command.
 * Includes {@link CountDownLatch} to make sure
 * that {@link Process} is only accessible 
 * after it is created.
 *
 * @author Roberts Ziediņš
*/
public class ProcessExecutor implements Runnable {
	
	private Runtime run;
	private String cmd;
	private volatile Process process;
	private final CountDownLatch latch = new CountDownLatch(1);

	public ProcessExecutor(String cmd) {
		this.cmd = cmd;
		run = Runtime.getRuntime();
	}
	
	public Process getProcess() {
		return process;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	private void execute(String cmd) throws IOException, InterruptedException {
		process = run.exec(cmd);
		latch.countDown();
		
		BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String line = "";
		while ((line=buf.readLine()) != null) {
			System.out.println(line);
		}

		process.waitFor();
	}

	@Override
	public void run() {
		try {
			execute(cmd);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
