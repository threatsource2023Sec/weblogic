package weblogicx.jsp.tags;

import javax.servlet.jsp.tagext.TagSupport;
import weblogic.work.Work;
import weblogic.work.WorkManagerFactory;

public class PrefetchTag extends TagSupport {
   private String uri;

   public void setUri(String uri) {
      this.uri = uri;
   }

   public String getUri() {
      return this.uri;
   }

   public int doStartTag() {
      Work er = new PrefetchExecutable(this.uri, this.pageContext);
      WorkManagerFactory.getInstance().getSystem().schedule(er);
      return 6;
   }
}
