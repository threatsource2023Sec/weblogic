package weblogic.ant.taskdefs.antline;

import java.util.StringTokenizer;

class Text extends Tag {
   private String clean(String line) {
      if ("\n".equals(line)) {
         return "\\n";
      } else {
         StringTokenizer st = new StringTokenizer(line, "\"", true);
         StringBuffer sb = new StringBuffer();

         while(st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.equals("\"")) {
               sb.append("\\\"");
            } else {
               sb.append(token);
            }
         }

         return sb.toString();
      }
   }

   public String getJavaScript() {
      if (this.getContent() == null) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         StringTokenizer st = new StringTokenizer(this.getContent(), "\r\n\f", true);

         while(st.hasMoreTokens()) {
            String token = st.nextToken();
            if (!token.equals("\r") && !token.equals("\f")) {
               sb.append("  out.print( \"");
               sb.append(this.clean(token));
               sb.append("\" );\n");
            }
         }

         return sb.toString();
      }
   }
}
