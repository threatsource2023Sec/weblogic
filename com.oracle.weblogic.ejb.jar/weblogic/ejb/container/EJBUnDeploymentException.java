package weblogic.ejb.container;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18ntools.L10nLookup;

public final class EJBUnDeploymentException extends RemoteException {
   private static final long serialVersionUID = 1326360028294935864L;

   public EJBUnDeploymentException(String s) {
      super(s);
   }

   public EJBUnDeploymentException(String s, Throwable t) {
      super(s, t);
   }

   public void printStackTrace(PrintWriter out) {
      super.printStackTrace(out);
      if (this.detail != null) {
         Localizer l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.ejb.container.EJBTextTextLocalizer");
         out.println(l10n.get("NestedException"));
         this.detail.printStackTrace(out);
      }

   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("weblogic.ejb.container.UnDeploymentException: ");
      sb.append(this.getMessage());
      return sb.toString();
   }
}
