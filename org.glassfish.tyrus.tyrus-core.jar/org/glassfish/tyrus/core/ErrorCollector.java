package org.glassfish.tyrus.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DeploymentException;

public class ErrorCollector {
   private static final Logger LOGGER = Logger.getLogger(ErrorCollector.class.getName());
   private final List exceptionsToPublish = new ArrayList();

   public void addException(Exception exception) {
      LOGGER.log(Level.FINE, "Adding exception", exception);
      this.exceptionsToPublish.add(exception);
   }

   public DeploymentException composeComprehensiveException() {
      StringBuilder sb = new StringBuilder();
      Iterator var2 = this.exceptionsToPublish.iterator();

      while(var2.hasNext()) {
         Exception exception = (Exception)var2.next();
         sb.append(exception.getMessage());
         sb.append("\n");
      }

      return new DeploymentException(sb.toString());
   }

   public boolean isEmpty() {
      return this.exceptionsToPublish.isEmpty();
   }
}
