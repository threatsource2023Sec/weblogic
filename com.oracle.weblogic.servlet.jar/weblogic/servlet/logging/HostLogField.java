package weblogic.servlet.logging;

import weblogic.protocol.ServerChannel;

public final class HostLogField implements LogField {
   private int type;
   private int prefix;

   HostLogField(String pfx, String id) {
      if ("ip".equals(id)) {
         this.type = 5;
      } else if ("dns".equals(id)) {
         this.type = 6;
      } else {
         this.type = 0;
      }

      if ("c".equals(pfx)) {
         this.prefix = 1;
      } else if ("s".equals(pfx)) {
         this.prefix = 2;
      } else {
         this.prefix = 0;
      }

   }

   public void logField(HttpAccountingInfo metrics, FormatStringBuffer buff) {
      ServerChannel sc = metrics.getServerChannel();
      String localhost_name = sc.getInetAddress().getHostName();
      String localhost_ip_port = sc.getAddress() + ":" + sc.getPublicPort();
      if (this.type == 0 || this.prefix == 0) {
         buff.appendValueOrDash((String)null);
      }

      if (this.prefix == 1) {
         switch (this.type) {
            case 5:
               buff.appendValueOrDash(metrics.getRemoteAddr());
               return;
            case 6:
               buff.appendValueOrDash(metrics.getRemoteHost());
               return;
         }
      } else if (this.prefix == 2) {
         switch (this.type) {
            case 5:
               buff.appendValueOrDash(localhost_ip_port);
               return;
            case 6:
               buff.appendValueOrDash(localhost_name);
               return;
         }
      }

      buff.appendValueOrDash((String)null);
   }
}
