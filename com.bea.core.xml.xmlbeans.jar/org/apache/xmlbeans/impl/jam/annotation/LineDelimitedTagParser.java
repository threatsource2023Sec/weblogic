package org.apache.xmlbeans.impl.jam.annotation;

import com.sun.javadoc.Tag;
import java.io.StringWriter;
import java.util.StringTokenizer;
import org.apache.xmlbeans.impl.jam.mutable.MAnnotatedElement;
import org.apache.xmlbeans.impl.jam.mutable.MAnnotation;

public class LineDelimitedTagParser extends JavadocTagParser {
   private static final String VALUE_QUOTE = "\"";
   private static final String LINE_DELIMS = "\n\f\r";

   public void parse(MAnnotatedElement target, Tag tag) {
      if (target == null) {
         throw new IllegalArgumentException("null tagText");
      } else if (tag == null) {
         throw new IllegalArgumentException("null tagName");
      } else {
         MAnnotation[] anns = this.createAnnotations(target, tag);
         String tagText = tag.text();
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

                  this.setValue(anns, name, value);
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
