package weblogic.deploy.service.internal;

import weblogic.logging.LogOutputStream;

public final class DeploymentServiceDebug {
   public static final boolean DEBUG = false;
   private static final LogOutputStream LOG = new LogOutputStream("DeploymentService");

   public static final void log(String message) {
      LOG.debug(message);
   }

   public static final void error(String message) {
      LOG.error(message);
   }
}
