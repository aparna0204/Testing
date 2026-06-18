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
					    "totalLinesAddedOrModified": 2,
					    "coveragePercentage": 0.0,
					    "status": "FAILED",
					    "summary": "The changes introduce critical runtime exceptions and security vulnerabilities without any accompanying test coverage."
					  },
					  "issuesIdentified": [
					    {
					      "severity": "critical",
					      "category": "security",
					      "description": "A hardcoded string value is directly assigned to a variable named 'password'.",
					      "file_path": "src/main/java/com/example/test/TestingApplication.java",
					      "line_number": 7,
					      "evidence": "String password = '12345678897234569843278687134546789687685gjhfsgjvnvn';",
					      "suggestion": "Remove hardcoded passwords."
					    },
					    {
					      "severity": "high",
					      "category": "reliability",
					      "description": "The variable 's' is explicitly initialized to null on line 6.",
					      "file_path": "src/main/java/com/example/test/TestingApplication.java",
					      "line_number": 8,
					      "evidence": "if( s.length() > 0) {",
					      "suggestion": "Implement a null-check."
					    }
					  ]
					}
					""";
			
			
	            ObjectMapper objectMapper = new ObjectMapper();

	            // 1. Parse JSON string into a generic JsonNode tree
	            JsonNode rootNode = objectMapper.readTree(jsonArrayWithSeverity);

	            // 2. Target the specific "issuesIdentified" node path
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
