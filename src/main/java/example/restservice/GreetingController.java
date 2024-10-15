package example.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@GetMapping("/version_current")
	public String version_current() {
		return get_current_version_git_using_bash_cmd();
	}

	public String get_current_version_git_using_bash_cmd() {
		String cmd = "git log -1 --pretty=format:\"%H\"";
		StringBuilder version = new StringBuilder();
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmd);
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					version.append(line);
				}
			}
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (process != null) {
				process.destroy();
			}
		}
		return version.toString();
	}
}
