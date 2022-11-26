package weblogic.ejb.spi;

import java.io.PrintWriter;
import java.util.Locale;
import weblogic.ejb.container.EJBDebugService;
import weblogic.i18n.Localizer;
import weblogic.i18ntools.L10nLookup;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StackTraceUtilsClient;

public final class WLDeploymentException extends Exception {
   private static final long serialVersionUID = 570535192182504339L;
   private final String msg;
   private Throwable th;

   public WLDeploymentException(String m) {
      super(m);
      this.msg = m;
   }

   public WLDeploymentException(String m, Throwable t) {
      super(m);
      this.msg = m;
      this.th = t;
   }

   public Throwable getEmbeddedThrowable() {
      return this.th;
   }

   public String getErrorMessage() {
      return EJBDebugService.deploymentLogger.isDebugEnabled() && this.th != null ? this.msg + PlatformConstants.EOL + StackTraceUtilsClient.throwable2StackTrace(this.th) : this.msg;
   }

   public String toString() {
      return this.th != null ? this.msg + " NestedException Message is :" + this.th.getMessage() : this.msg;
   }

   public void printStackTrace(PrintWriter out) {
      super.printStackTrace(out);
      if (this.th != null) {
         Localizer l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.ejb.container.EJBTextTextLocalizer");
         out.println(l10n.get("NestedException"));
         this.th.printStackTrace(out);
      }

   }
}
