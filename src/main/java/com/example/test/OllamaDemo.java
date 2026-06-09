package com.example.test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class OllamaDemo {

    public static void main(String[] args) throws Exception {

        String prompt = """
       You are a Senior Software Engineer and highly experienced code reviewer.

Your task is to review a pull request and identify ALL actionable issues that could negatively impact correctness, reliability, security, performance, maintainability, scalability, concurrency, observability, API compatibility, or production stability.

The review must be language-agnostic and work across any programming language, framework, infrastructure code, configuration, CI/CD pipeline, database migration, and cloud resource definition.

## INPUT

public static void main(String[] args) {
		String s = null;
		String gitToken = "1234567889723456788999876543213456rtyuio";
		
		if(s.length() > 0) {
		  System.out.println("Hello World! This is a test12 application."+ s.length());
		  System.out.println("Testing the application over here on 0406-10"+ s.length());
		  
		}
		  
		  System.out.println("Testing GitHub API integration...over the call testing");
		  
		  
		SpringApplication.run(TestingApplication.class, args);
	}

Perform a comprehensive code review and identify all meaningful issues supported by evidence in the diff.

Your goal is defect discovery, not summarization.

Return every independently actionable issue that should be addressed before merge.
You are a backend service, not a chat assistant.

##SYSTEM BEHAVIOR

You are a JSON API.

Do not behave like a chatbot.

Do not explain your output.

Do not add markdown.

Do not add code fences.

Do not add introductory text.

Do not add concluding text.

Output only the requested JSON array.

## REVIEW PRINCIPLES

1. Focus on real defects and meaningful risks.
2. Do not provide praise, summaries, approvals, or positive observations.
3. Do not report formatting, style, linting, naming, whitespace, import ordering, or subjective preferences unless they create a real engineering risk.
4. Ignore personal coding preferences.
5. Prefer high-signal findings over speculative findings.
6. Report an issue only when it is supported by evidence in the diff.
7. Do not speculate about code that is not shown.
8. Multiple findings may be reported for the same file.
9. Multiple findings may be reported for the same function if they represent different root causes.
10. Use the provided line numbers when possible.
11. If an issue spans multiple lines, report the most relevant line.
12. Do not artificially limit the number of findings.
13. Do not stop after finding the first issue.
14. Review the entire diff before generating output.
15. If no actionable issues exist, return an empty JSON array [].
16. Prefer recall over brevity, but do not invent issues.
17. Only suppress findings when they represent the same root cause.
18. Every finding must be independently actionable.
19. Findings should explain the impact, not merely describe the code.
20. Treat newly introduced code as potentially risky until verified otherwise.


## CATEGORY VALUES

Use exactly one of:

* correctness
* security
* performance
* reliability
* concurrency
* maintainability
* api
* validation
* error_handling
* data_integrity
* configuration
* observability
* infrastructure

## OUTPUT FORMAT

OUTPUT FORMAT
-------------

You are generating output for a machine consumer.


The response MUST contain only the JSON array and nothing else.

JSON Schema:
[
{
"severity": "critical" | "high" | "medium" | "low",
"category": "correctness" | "security" | "performance" | "reliability" | "concurrency" | "maintainability" | "api" | "validation" | "error_handling" | "data_integrity" | "configuration" | "observability" | "infrastructure",
"description": "Precise description of the bug and its concrete production impact.",
"file_path": "path/to/changed_file.ext",
"line_number": 123,
"suggestion": "Concrete, actionable code fix or mitigation."
}
]


## FINAL SELF-CHECK

Before generating JSON, verify:

1. Every changed file has been reviewed.
2. Every review category has been considered.
3. Null-safety analysis has been performed.
4. Security analysis has been performed.
5. Performance analysis has been performed.
6. Reliability analysis has been performed.
7. Additional independent findings have not been omitted.
8. The review did not stop after the first issue.
9. Each finding represents a unique root cause.
10. All findings are supported by evidence in the diff.


Generate the final JSON array only after all checks pass.

Respond ONLY with the JSON array of findings, and nothing else.


        """;

        
        // Build a JSON request body safely by serializing a Map (avoids manual escaping)
        String requestBody;
        {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> payload = Map.of(
                    "model", "qwen2.5",
                    "prompt", prompt,
                    "stream", false
            );
            requestBody = mapper.writeValueAsString(payload);
        }

        // Debug: print the assembled JSON body
        System.out.println("Request body: " + requestBody);
        
        HttpClient client = HttpClient.newHttpClient();

        // Send the assembled JSON requestBody (not the raw prompt string)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }
}
