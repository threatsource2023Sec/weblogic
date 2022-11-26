package weblogic.ant.taskdefs.antline;

import java.util.StringTokenizer;

class Include extends Tag {
   private ResourceProvider provider;

   public Include(ResourceProvider provider) {
      this.provider = provider;
   }

   public String getJavaScript() throws ScriptException {
      String content = this.getContent();
      content = content.substring(3, content.length() - 2);
      StringTokenizer st = new StringTokenizer(content);
      String token = null;
      if (st.hasMoreTokens()) {
         token = st.nextToken();
         if ("include".equals(token) && st.hasMoreTokens()) {
            token = st.nextToken();
            if (token.startsWith("file=")) {
               token = token.substring("file=".length(), token.length());
               if (token.charAt(0) == '"') {
                  token = token.substring(1, token.length());
               }

               if (token.charAt(token.length() - 1) == '"') {
                  token = token.substring(0, token.length() - 1);
               }

               return (new LightJspParser(this.provider.getResource(token), this.provider)).parse();
            }
         }
      }

      throw new ScriptException("failed to parse: usage <%@ include file=\"filename\" %>");
   }
}
