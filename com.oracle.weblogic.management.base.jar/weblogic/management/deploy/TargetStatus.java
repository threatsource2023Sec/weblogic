package weblogic.management.deploy;

import java.io.Serializable;
import java.security.AccessController;
import java.util.ArrayList;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

/** @deprecated */
@Deprecated
public class TargetStatus implements Serializable {
   private static final long serialVersionUID = -8426317304673733688L;
   private int state = 0;
   private int type;
   private ArrayList messages = new ArrayList();
   private String targetName;
   private transient boolean targetListEmpty = false;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final int STATE_INIT = 0;
   public static final int STATE_IN_PROGRESS = 1;
   public static final int STATE_FAILED = 2;
   public static final int STATE_SUCCESS = 3;
   /** @deprecated */
   @Deprecated
   public static final int STATE_UNAVAILABLE = 4;
   public static final int TYPE_UNKNOWN = 0;
   public static final int TYPE_SERVER = 1;
   public static final int TYPE_CLUSTER = 2;
   public static final int TYPE_JMS_SERVER = 3;
   public static final int TYPE_VIRTUAL_HOST = 4;
   public static final int TYPE_SAF_AGENT = 5;
   public static final int TYPE_VIRTUAL_TARGET = 6;

   public TargetStatus(String target) {
      this.targetName = target;
      this.setTargetType();
   }

   private TargetStatus() {
   }

   public int getState() {
      return this.state;
   }

   public synchronized void setState(int newState) {
      boolean illegalTransition = false;
      switch (this.state) {
         case 0:
            if (newState != 1) {
               illegalTransition = true;
            }
            break;
         case 1:
         case 3:
            if (newState != 2 && newState != 3 && newState != 4) {
               illegalTransition = true;
            }
            break;
         case 2:
            illegalTransition = true;
            break;
         case 4:
            if (newState != 2) {
               return;
            }
      }

      if (!illegalTransition) {
         this.state = newState;
      }
   }

   public Exception[] getMessages() {
      return (Exception[])((Exception[])this.messages.toArray(new Exception[0]));
   }

   public synchronized void addMessage(Exception message) {
      this.messages.add(message);
   }

   public String getTarget() {
      return this.targetName;
   }

   public int getType() {
      return this.type;
   }

   public int getTargetType() {
      return this.getType();
   }

   private void setTargetType() {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      if (domain.lookupServer(this.getTarget()) != null) {
         this.type = 1;
      } else if (domain.lookupCluster(this.getTarget()) != null) {
         this.type = 2;
      } else if (domain.lookupJMSServer(this.getTarget()) != null) {
         this.type = 3;
      } else if (domain.lookupSAFAgent(this.getTarget()) != null) {
         this.type = 5;
      } else if (domain.lookupVirtualHost(this.getTarget()) != null) {
         this.type = 4;
      } else if (domain.lookupInAllVirtualTargets(this.getTarget()) != null) {
         this.type = 6;
      } else {
         this.type = 0;
      }

   }

   protected void setTargetListEmpty(boolean targetListEmpty) {
      this.targetListEmpty = targetListEmpty;
   }

   protected boolean isTargetListEmpty() {
      return this.targetListEmpty;
   }

   final synchronized TargetStatus copy() {
      TargetStatus clonedObj = new TargetStatus();
      clonedObj.messages = (ArrayList)this.messages.clone();
      clonedObj.state = this.state;
      clonedObj.type = this.type;
      clonedObj.targetName = this.targetName;
      return clonedObj;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(super.toString()).append("(");
      sb.append("  Name: ");
      sb.append(this.getTarget());
      sb.append('\n');
      sb.append("  Type: ");
      sb.append(this.getType());
      sb.append('\n');
      sb.append("  State: ");
      sb.append(this.getState());
      sb.append('\n');
      sb.append("  Exceptions:\n");
      Exception[] ee = this.getMessages();
      if (ee != null) {
         for(int i = 0; i < ee.length; ++i) {
            sb.append("  ");
            sb.append(ee[i].getMessage());
            sb.append('\n');
         }
      }

      sb.append(" )");
      sb.append('\n');
      return sb.toString();
   }
}
