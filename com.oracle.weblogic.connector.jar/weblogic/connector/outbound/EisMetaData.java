package weblogic.connector.outbound;

import java.security.AccessController;
import javax.resource.ResourceException;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionMetaData;
import weblogic.connector.common.Debug;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class EisMetaData {
   public String productName;
   public String productVersion;
   public String maxConnections;
   public String userName;
   private static String unavailable = Debug.getStringUnavailable();

   EisMetaData() {
      this.productName = unavailable;
      this.productVersion = unavailable;
      this.maxConnections = unavailable;
      this.userName = unavailable;
   }

   public static EisMetaData getMetaData(Object managedConnection, ConnectionPool connectionPool) {
      ManagedConnectionMetaData md = null;
      EisMetaData result = new EisMetaData();
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      String exString;
      try {
         md = connectionPool.getRAInstanceManager().getAdapterLayer().getMetaData((ManagedConnection)managedConnection, kernelId);
         if (md != null) {
            try {
               result.productName = connectionPool.getRAInstanceManager().getAdapterLayer().getEISProductName(md, kernelId);
            } catch (ResourceException var10) {
            }

            try {
               result.productVersion = connectionPool.getRAInstanceManager().getAdapterLayer().getEISProductVersion(md, kernelId);
            } catch (ResourceException var9) {
            }

            try {
               result.maxConnections = String.valueOf(connectionPool.getRAInstanceManager().getAdapterLayer().getMaxConnections(md, kernelId));
            } catch (ResourceException var8) {
            }

            try {
               result.userName = connectionPool.getRAInstanceManager().getAdapterLayer().getUserName(md, kernelId);
            } catch (ResourceException var7) {
            }
         }
      } catch (ResourceException var11) {
         exString = connectionPool.getRAInstanceManager().getAdapterLayer().toString(var11, kernelId);
         Debug.connections("For pool " + connectionPool.getName() + ", ResourceException while getting meta data for ManagedConnection.  Reason: " + exString);
      } catch (Exception var12) {
         exString = connectionPool.getRAInstanceManager().getAdapterLayer().toString(var12, kernelId);
         Debug.connections("For pool " + connectionPool.getName() + ", unexpected Exception while getting meta data for ManagedConnection.  Reason: " + exString);
      }

      return result;
   }
}
