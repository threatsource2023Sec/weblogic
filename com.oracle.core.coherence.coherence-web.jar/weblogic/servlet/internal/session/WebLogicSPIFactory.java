package weblogic.servlet.internal.session;

import com.tangosol.coherence.servlet.HttpSessionCollection;
import com.tangosol.coherence.servlet.SessionHelper;
import com.tangosol.coherence.servlet.api25.DefaultFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class WebLogicSPIFactory extends DefaultFactory {
   public static final String CTX_INIT_SESSION_CONTEXT = "weblogic-session-context";
   protected CoherenceWebSessionContextImpl sessionContext;

   public SessionHelper instantiateSessionHelper(ServletContext ctx) {
      azzert(ctx != null);
      azzert(this.getServletContext() == null);
      SessionHelper helper = this.createSessionHelper(ctx);
      this.setServletContext(ctx);
      this.setSessionHelper(helper);
      this.setSessionContext((CoherenceWebSessionContextImpl)ctx.getAttribute("weblogic-session-context"));
      return helper;
   }

   protected SessionHelper createSessionHelper(ServletContext ctx) {
      return new WebLogicSPISessionHelper(this, ctx);
   }

   public HttpSession instantiateHttpSession(HttpSessionCollection collection) {
      return new CoherenceWebSessionData(this.getSessionContext(), this.getSessionHelper(), collection);
   }

   public HttpSession instantiateHttpSession(HttpSessionCollection collection, String sId) {
      return this.instantiateHttpSession(collection, sId, false);
   }

   protected HttpSession instantiateHttpSession(HttpSessionCollection collection, String sId, boolean createModel) {
      return new CoherenceWebSessionData(this.getSessionContext(), this.getSessionHelper(), collection, sId, createModel);
   }

   public void setSessionContext(CoherenceWebSessionContextImpl sc) {
      this.sessionContext = sc;
   }

   public CoherenceWebSessionContextImpl getSessionContext() {
      return this.sessionContext;
   }

   public String toString() {
      return "WeblogicSPIFactory\n" + indentString(this.getDescription(), "  ");
   }
}
