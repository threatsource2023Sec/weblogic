package weblogic.diagnostics.instrumentation.gathering;

import java.util.Iterator;
import java.util.Map;
import weblogic.diagnostics.instrumentation.ValueRenderer;

public class MapRenderer implements ValueRenderer {
   private static final String entryStart = "<\"";
   private static final String nullStr = "null";
   private static final String entryDelimiter = "\",\"";
   private static final String entryEnd = "\">";

   public Object render(Object inputObject) {
      if (inputObject != null && inputObject instanceof Map) {
         Map map = (Map)inputObject;
         StringBuffer sb = new StringBuffer();
         Iterator iter = map.entrySet().iterator();

         while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            sb.append("<\"");
            sb.append(entry.getKey() == null ? "null" : entry.getKey().toString());
            sb.append("\",\"");
            sb.append(entry.getValue() == null ? "null" : entry.getValue().toString());
            sb.append("\">");
         }

         return sb.toString();
      } else {
         return null;
      }
   }
}
