package example.restservice.controller;

import example.restservice.entity.Greeting;
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
	private final AtomicLong counter = new AtomicLong(); // count the number of requests

	@GetMapping("/greeting")
	public Greeting greeting(
			@RequestParam(value = "name", defaultValue = "World") String name,
			@RequestParam(value = "long_name", required = false) String long_name
	) {
		String str_temp;
		System.out.println("name: " + name + " long_name: " + long_name);
		if (long_name == null) {
			str_temp = template + " long_name is not provided";
		} else {
			str_temp = template + " from %s";
		}
		System.out.println(str_temp);
		return new Greeting(counter.incrementAndGet(), String.format(str_temp, name, long_name));
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

	@GetMapping("/create/user")
	public String create_user(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "email", required = true) String email
	) {
		return "Create user with name: " + name + " and email: " + email;
	}

	@GetMapping("/get/user")
	public String get_user(
	) {
		return "Get user";
	}

	@GetMapping("/update/user")
	public String update_user(
	) {
		return "Update user";
	}

	@GetMapping("/delete/user")
	public String delete_user(
	) {
		return "Delete user";
	}

}
