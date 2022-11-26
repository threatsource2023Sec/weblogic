package com.bea.util.jam.internal.elements;

import java.io.StringWriter;
import java.util.Properties;
import java.util.StringTokenizer;

class LinebreakTagPropertyParser {
   private static LinebreakTagPropertyParser INSTANCE = new LinebreakTagPropertyParser();
   private static final String VALUE_QUOTE = "\"";
   private static final String LINE_DELIMS = "\n\f\r";

   public static LinebreakTagPropertyParser getInstance() {
      return INSTANCE;
   }

   private LinebreakTagPropertyParser() {
   }

   public void parse(Properties out, String tagText) {
      if (out == null) {
         throw new IllegalArgumentException("null out");
      } else if (tagText == null) {
         throw new IllegalArgumentException("null text");
      } else {
         StringTokenizer st = new StringTokenizer(tagText, "\n\f\r");

         while(st.hasMoreTokens()) {
            String pair = st.nextToken();
            int eq = pair.indexOf(61);
            if (eq > 0) {
               String name = pair.substring(0, eq).trim();
               if (eq < pair.length() - 1) {
                  String value = pair.substring(eq + 1).trim();
                  if (value.startsWith("\"")) {
                     value = this.parseQuotedValue(value.substring(1), st);
                  }

                  out.setProperty(name, value);
               }
            }
         }

      }
   }

   private String parseQuotedValue(String line, StringTokenizer st) {
      StringWriter out = new StringWriter();

      while(true) {
         int endQuote = line.indexOf("\"");
         if (endQuote != -1) {
            out.write(line.substring(0, endQuote).trim());
            return out.toString();
         }

         out.write(line);
         if (!st.hasMoreTokens()) {
            return out.toString();
         }

         out.write(10);
         line = st.nextToken().trim();
      }
   }
}
