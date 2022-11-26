package weblogic.servlet;

import javax.servlet.http.HttpServletResponse;
import weblogic.servlet.spi.WebServerRegistry;

public final class SendFileUtility {
   public static FileSender getZeroCopyFileSender(HttpServletResponse res) {
      return WebServerRegistry.getInstance().getContainerSupportProvider().getZeroCopyFileSender(res);
   }

   public static FileSender getFileSender(HttpServletResponse res) {
      return WebServerRegistry.getInstance().getContainerSupportProvider().getFileSender(res);
   }
}
