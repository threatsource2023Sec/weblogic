package weblogicx.jsp.tags;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import weblogic.servlet.internal.session.SessionContext;

public class EncodeTag extends BodyTagSupport {
   private String sessionId;
   private String cookieName;

   public void setSessionId(String sessionId) {
      this.sessionId = sessionId;
   }

   public String getSessionId() {
      return this.sessionId;
   }

   public void setCookieName(String cookieName) {
      this.cookieName = cookieName;
   }

   public String getCookieName() {
      return this.cookieName;
   }

   public void release() {
      this.sessionId = null;
      this.cookieName = null;
   }

   public int doAfterBody() throws JspException {
      try {
         HttpSession session = this.pageContext.getSession();
         Writer writer = this.getBodyContent().getEnclosingWriter();
         HttpServletRequest req = (HttpServletRequest)this.pageContext.getRequest();
         if (session != null && !req.isRequestedSessionIdFromCookie()) {
            String id = this.sessionId == null ? session.getId() : this.sessionId;
            String cookie = this.cookieName == null ? ((SessionContext)session.getSessionContext()).getConfigMgr().getCookieName() : this.cookieName;
            Rewriter rewriter = new URLRewriter(id, cookie);
            Reader reader = this.getBodyContent().getReader();
            rewriter.setStreams(reader, writer);
            rewriter.parse();
         } else {
            try {
               this.getBodyContent().writeOut(writer);
            } catch (IOException var8) {
            }
         }

         return 6;
      } catch (Exception var9) {
         throw new JspException("Failed to encode your document: " + var9);
      }
   }
}
