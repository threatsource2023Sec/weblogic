package kodo.bea;

import org.apache.openjpa.lib.util.Localizer;
import weblogic.i18n.logging.CatalogMessage;

public class KodoCatalogMessage extends CatalogMessage {
   private final String prefix;

   public static KodoCatalogMessage newInstance(Localizer.Message msg, Throwable t, int severity, BEALogImpl log) {
      String id = BEALogFactory.toMessageId(msg);
      if (id != null) {
         Object[] args;
         if (t != null) {
            Object[] subs = msg.getSubstitutions();
            if (subs != null) {
               args = new Object[subs.length + 1];
               System.arraycopy(subs, 0, args, 0, subs.length);
            } else {
               args = new Object[1];
            }

            args[args.length - 1] = t;
         } else {
            args = msg.getSubstitutions();
         }

         return new KodoCatalogMessage(id, args, log.getBundleBaseName(), severity, log.getFactory().getDiagnosticContext(), log.getChannel());
      } else {
         return null;
      }
   }

   private KodoCatalogMessage(String id, Object[] args, String bundleBaseName, int severity, String diagnosticContext, String channel) {
      super(id, severity, args, bundleBaseName);
      StringBuffer buf = new StringBuffer();
      if (channel != null) {
         buf.append("[").append(channel.substring(channel.lastIndexOf(".") + 1)).append("] ");
      }

      if (diagnosticContext != null) {
         buf.append("[").append(diagnosticContext).append("] ");
      }

      this.prefix = buf.toString();
   }

   public String getMessage() {
      return this.prefix + super.getMessage();
   }
}
