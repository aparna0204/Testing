package com.example.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MarkdownTableGenerator {

    public static String toMarkdownTable(String jsonArray) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rows = mapper.readTree(jsonArray);

        if (!rows.isArray() || rows.isEmpty()) {
            return "No data available";
        }

        StringBuilder sb = new StringBuilder();

        JsonNode firstRow = rows.get(0);

        // Header
        sb.append("|");
        firstRow.fieldNames().forEachRemaining(col ->
                sb.append(" ").append(col).append(" |"));
        sb.append("\n");

        // Separator
        sb.append("|");
        firstRow.fieldNames().forEachRemaining(col ->
                sb.append(" --- |"));
        sb.append("\n");

        // Data rows
        for (JsonNode row : rows) {
            sb.append("|");
            firstRow.fieldNames().forEachRemaining(col ->
                    sb.append(" ")
                      .append(row.path(col).asText())
                      .append(" |"));
            sb.append("\n");
        }

        return sb.toString();
    }
    
    
    public static void main(String[] args) {
				String jsonArray = "[{\"name\":\"Alice\",\"age\":30},{\"name\":\"Bob\",\"age\":25}]";
		try {
			String markdownTable = toMarkdownTable(jsonArray);
			System.out.println(markdownTable);
			
			String jsonArrayWithSeverity = """
					{
  "testCoverageReport": {
    "linesCovered": 0,
    "totalLinesAddedOrModified": 1,
    "coveragePercentage": 0.0,
    "status": "FAILED",
    "summary": "The single modified line introduces a hardcoded string for a sensitive variable ('password') and lacks any accompanying unit tests to validate its change or usage. This results in 0% coverage for the introduced/modified code, which is a significant coverage gap for a security-related change."
  },
  "issuesIdentified": [
    {
      "severity": "critical",
      "category": "security",
      "description": "Hardcoded string assigned to a variable named 'password'. This is a severe security vulnerability as it could lead to exposure of sensitive credentials or patterns used for generating them. Even if this is test data, it sets a dangerous precedent and violates security best practices for handling sensitive information.",
      "file_path": "src/main/java/com/example/test/TestingApplication.java",
      "line_number": 8,
      "evidence": "String password = \"12345678897234ghjg9843278687134546789687685gjhfsgjvnvn\";",
      "suggestion": "Remove hardcoded password. Implement a secure mechanism for handling credentials, such as environment variables, a secrets management service (e.g., AWS Secrets Manager, HashiCorp Vault), or a secure configuration file. If this is placeholder test data, it should be clearly documented and/or replaced with non-sensitive mock data or a testing secret management approach."
    }
  ]
}
					""";
			
			
			// 1. CLEAN THE STRING: Fix the unescaped inner quotes in the "evidence" line
            // This replaces patterns like = "text"; with = 'text'; so Jackson doesn't crash
            String cleanedJson = jsonArrayWithSeverity.replaceAll("=\\s*\"([^\"]+)\"\\s*;", "= '$1';");

            ObjectMapper objectMapper = new ObjectMapper();

            // 2. Parse the safely cleaned JSON string into a tree node
            JsonNode rootNode = objectMapper.readTree(cleanedJson);
            JsonNode issuesNode = rootNode.path("issuesIdentified");
			System.out.println( findSeverityfromJSonArray(issuesNode.toString()));
		} catch (Exception e) {
			e.printStackTrace();
	}
		
		
}
    
    public static boolean findSeverityfromJSonArray(String jsonArray) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rows = mapper.readTree(jsonArray);
		for (JsonNode node : rows) {
			if (node.has("severity") && ("HIGH".equalsIgnoreCase(node.get("severity").asText()) || "CRITICAL".equalsIgnoreCase(node.get("severity").asText()))) {
				return true;
			}
		}
		return false;

	}
}
