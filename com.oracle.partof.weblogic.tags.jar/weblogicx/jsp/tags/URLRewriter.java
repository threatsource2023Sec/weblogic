package weblogicx.jsp.tags;

public class URLRewriter extends Rewriter {
   private String linkStr;
   private String formStr;

   public URLRewriter(String id, String cookie) {
      this.linkStr = cookie + "=" + id;
      this.formStr = "<input type=\"hidden\" name=\"" + cookie + "\" value=\"" + id + "\">";
   }

   protected String getLinkString() {
      return this.linkStr;
   }

   protected String getFormString() {
      return this.formStr;
   }
}
