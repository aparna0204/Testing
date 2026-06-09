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
You are a senior software engineer performing a pull request review.

INPUT:

* PR Title
* PR Description
* Diff
* Optional Context

OBJECTIVE:
Identify all actionable issues introduced by the diff. Focus on defects, risks, regressions, and production-impacting problems. Do not summarize or praise the code.

REVIEW SCOPE:
Evaluate changed code for:

* Correctness
* Reliability
* Security
* Validation
* Error Handling
* Performance
* Concurrency
* Data Integrity
* API Compatibility
* Maintainability
* Configuration
* Infrastructure
* Observability
* Scalability
* Resource Management
* Backward Compatibility

REVIEW RULES:

* Report only issues supported by the diff.
* Do not speculate about unseen code.
* Ignore style, formatting, naming, lint, and personal preferences unless they create real risk.
* Multiple findings may exist in the same file or function if root causes differ.
* Prefer high-signal findings.
* Deduplicate equivalent findings.
* Return [] if no actionable issues exist.

CHECK FOR:

* Logic errors
* Null/optional misuse
* Missing validation
* Missing error handling
* Race conditions
* Data corruption risks
* Resource leaks
* Retry/idempotency issues
* Security vulnerabilities (auth, authz, injection, SSRF, XSS, CSRF, secrets exposure, privilege escalation)
* Breaking API changes
* Performance regressions
* Operational visibility gaps (logging, metrics, tracing)

SEVERITY:

* critical: security vulnerability, data loss/corruption, severe outage risk
* high: likely production bug, reliability failure, API breakage, major performance issue
* medium: edge-case failure, missing validation/error handling, operational risk
* low: minor but actionable engineering concern

CATEGORY (use exactly one):
correctness | security | performance | reliability | concurrency | maintainability | api | validation | error_handling | data_integrity | configuration | observability | infrastructure

WORKFLOW:

1. Understand intended behavior from title and description.
2. Review every changed file.
3. Analyze added, modified, and removed code.
4. Evaluate control flow, data flow, interfaces, state transitions, and failure paths.
5. Collect findings.
6. Deduplicate.
7. Return final results.

OUTPUT:
Return ONLY a valid JSON array.

Schema:
[
{
"severity": "critical|high|medium|low",
"category": "<allowed_category>",
"description": "<specific issue and impact>",
"file_path": "<path>",
"line_number": <changed line from diff>,
"suggestion": "<actionable fix>"
}
]

LINE NUMBER RULES:

* Use only line numbers present in changed diff lines.
* If exact line is unclear, use the nearest relevant changed line and state ambiguity in the description.
* Never invent line numbers.

OUTPUT RULES:

* No markdown
* No code fences
* No explanations
* No text before or after the response
* Must be directly parseable by JSON.parse()


If no issues exist, return:
[]

Diff : {diff}


        """.replace("{diff}", "public class TestingApplication {\r\n"
        		+ "\r\n"
        		+ "	public static void main(String[] args) {\r\n"
        		+ "		String s = null;\r\n"
        		+ "		String gitToken = \"1234567889723456788999876543213456rtewrwrwfwfrww\";\r\n"
        		+ "		\r\n"
        		+ "		if(s.length() > 0) {\r\n"
        		+ "		  System.out.println(\"Hello World! This is a test12 application.\"+ s.length());\r\n"
        		+ "		  System.out.println(\"Testing the application over here on 0406-10\"+ s.length());\r\n"
        		+ "		  \r\n"
        		+ "		}\r\n"
        		+ "		  \r\n"
        		+ "		  System.out.println(\"Testing GitHub API integration...over the call testing\");\r\n"
        		+ "		  \r\n"
        		+ "		  \r\n"
        		+ "		SpringApplication.run(TestingApplication.class, args);\r\n"
        		+ "	}\r\n"
        		+ "\r\n"
        		+ "}");

        
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
