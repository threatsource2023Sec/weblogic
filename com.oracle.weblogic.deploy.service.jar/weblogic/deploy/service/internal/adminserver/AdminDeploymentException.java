package weblogic.deploy.service.internal.adminserver;

import java.net.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.deploy.service.DeploymentException;
import weblogic.deploy.service.FailureDescription;

public class AdminDeploymentException extends Exception implements DeploymentException {
   private List failureDescriptions = Collections.synchronizedList(new ArrayList());

   public AdminDeploymentException() {
      this.failureDescriptions = new ArrayList();
   }

   public FailureDescription[] getFailures() {
      FailureDescription[] dummy = new FailureDescription[0];
      return (FailureDescription[])((FailureDescription[])this.failureDescriptions.toArray(dummy));
   }

   public void addFailureDescription(FailureDescription fd) {
      String server = fd.getServer();
      if (server != null && !this.hasFailureFor(server)) {
         this.failureDescriptions.add(fd);
      }

   }

   public void addFailureDescriptions(Set incomingFDs) {
      if (incomingFDs != null && incomingFDs.size() > 0) {
         Iterator iterator = incomingFDs.iterator();

         while(iterator.hasNext()) {
            this.addFailureDescription((FailureDescription)iterator.next());
         }

      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("AdminDeploymentException due to underlying exceptions : ");
      if (!this.failureDescriptions.isEmpty()) {
         Iterator iterator = this.failureDescriptions.iterator();
         int index = 1;

         while(true) {
            Throwable nestedThrowable;
            label33:
            do {
               while(iterator.hasNext()) {
                  sb.append(" Failure ");
                  sb.append(index);
                  ++index;
                  sb.append(": reason: ");
                  FailureDescription descr = (FailureDescription)iterator.next();
                  Exception exception = descr.getReason();
                  if (exception instanceof RemoteException) {
                     nestedThrowable = ((RemoteException)exception).detail;
                     continue label33;
                  }

                  sb.append(descr.toString());
               }

               return sb.toString();
            } while(!(nestedThrowable instanceof ConnectException) && !(nestedThrowable instanceof java.rmi.ConnectException) && !(nestedThrowable instanceof ConnectIOException) && !(nestedThrowable instanceof UnknownHostException));

            sb.append("ConnectException : " + nestedThrowable.toString());
         }
      } else {
         return sb.toString();
      }
   }

   private boolean hasFailureFor(String server) {
      Iterator iter = this.failureDescriptions.iterator();

      String fdServer;
      do {
         if (!iter.hasNext()) {
            return false;
         }

         FailureDescription fd = (FailureDescription)iter.next();
         fdServer = fd.getServer();
      } while(fdServer == null || !fdServer.equals(server));

      return true;
   }
}
