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
		} catch (Exception e) {
			e.printStackTrace();
	}
}
}
