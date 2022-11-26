package weblogic.transaction.internal;

import java.io.Serializable;
import java.security.AccessController;
import java.util.Iterator;
import java.util.Map;
import javax.transaction.xa.Xid;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.JTATransaction;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class JTATransactionImpl implements JTATransaction, Serializable {
   private static final long serialVersionUID = -7339816685706551825L;
   private String name;
   private Xid xid;
   private String status;
   private int statusCode;
   private Map userProperties;
   private int secondsActive;
   private String[] servers;
   private Map resourceNamesAndStatus;
   private String coordinatorURL;
   private Map serversAndStatus;
   private boolean transactionLogWritten;
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   JTATransactionImpl(ServerTransactionImpl tx) {
      this.name = tx.getName();
      this.xid = tx.getXID();
      this.status = tx.getStatusAsString();
      this.statusCode = tx.getStatus();
      this.userProperties = tx.getProperties();
      this.secondsActive = (int)(tx.getMillisSinceBegin() / 1000L);
      String[] scNames = tx.getSCNames();
      if (scNames == null) {
         this.servers = new String[1];
         this.servers[0] = ManagementService.getRuntimeAccess(kernelID).getServer().getName();
      } else {
         this.servers = new String[scNames.length];

         for(int i = 0; i < scNames.length; ++i) {
            this.servers[i] = scNames[i];
         }
      }

      this.resourceNamesAndStatus = tx.getResourceNamesAndState();
      this.coordinatorURL = tx.getCoordinatorURL();
      this.serversAndStatus = tx.getServersAndState();
      this.transactionLogWritten = tx.isLogWriteNecessary();
   }

   public String getName() {
      return this.name;
   }

   public Xid getXid() {
      return this.xid;
   }

   public String getStatus() {
      return this.status;
   }

   public Map getUserProperties() {
      return this.userProperties;
   }

   public int getSecondsActiveCurrentCount() {
      return this.secondsActive;
   }

   public String[] getServers() {
      return this.servers;
   }

   public Map getResourceNamesAndStatus() {
      return this.resourceNamesAndStatus;
   }

   public String getCoordinatorURL() {
      return this.coordinatorURL;
   }

   public Map getServersAndStatus() {
      return this.serversAndStatus;
   }

   public boolean isTransactionLogWritten() {
      return this.transactionLogWritten;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(128);
      sb.append("{");
      sb.append(this.getClass().getName());
      sb.append(": ");
      sb.append("name=" + this.name);
      sb.append(", ");
      sb.append("xid=" + this.xid.toString());
      sb.append(", ");
      sb.append("status=" + this.status);
      sb.append(", ");
      sb.append("userProperties=");
      if (this.userProperties != null) {
         sb.append(this.userProperties.toString());
      }

      sb.append(", ");
      sb.append("secondsActive=" + this.secondsActive);
      sb.append(", ");
      sb.append("servers=");

      for(int i = 0; i < this.servers.length; ++i) {
         sb.append(this.servers[i]);
         if (i < this.servers.length - 1) {
            sb.append("+");
         }
      }

      sb.append(", ");
      sb.append("resourceNamesAndStatus=");
      Iterator it = this.resourceNamesAndStatus.entrySet().iterator();

      Map.Entry entry;
      while(it.hasNext()) {
         entry = (Map.Entry)it.next();
         sb.append((String)entry.getKey());
         sb.append("/");
         sb.append((String)entry.getValue());
         if (it.hasNext()) {
            sb.append("+");
         }
      }

      sb.append(", ");
      sb.append("coordinatorURL=" + this.coordinatorURL);
      sb.append(", ");
      sb.append("serversAndStatus=");
      it = this.serversAndStatus.entrySet().iterator();

      while(it.hasNext()) {
         entry = (Map.Entry)it.next();
         sb.append((String)entry.getKey());
         sb.append("/");
         sb.append((String)entry.getValue());
         if (it.hasNext()) {
            sb.append("+");
         }
      }

      sb.append(", ");
      sb.append("transactionLogWritten=" + this.transactionLogWritten);
      sb.append("}");
      return sb.toString();
   }
}
