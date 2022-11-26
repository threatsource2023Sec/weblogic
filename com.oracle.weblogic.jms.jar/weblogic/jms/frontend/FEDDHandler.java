package weblogic.jms.frontend;

import java.security.AccessController;
import java.util.Iterator;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSException;
import weblogic.jms.common.JMSFailover;
import weblogic.jms.common.JMSLoadBalancer;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.dd.DDHandler;
import weblogic.jms.dd.DDMember;
import weblogic.jms.dd.DDStatusListener;
import weblogic.management.ManagementException;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class FEDDHandler implements DDStatusListener {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   DDHandler ddHandler;
   JMSLoadBalancer loadBalancer = null;
   String boundJNDIName = null;

   public FEDDHandler(DDHandler ddHandler) {
      this.ddHandler = ddHandler;
      ddHandler.addStatusListener(this, 255);
   }

   public void statusChangeNotification(DDHandler notifier, int events) {
      if ((events & 16) != 0) {
         this.stop();
      } else {
         this.refresh();
      }

   }

   private boolean areAnyMembersUp() {
      Iterator iter = this.ddHandler.memberCloneIterator();

      DDMember member;
      do {
         if (!iter.hasNext()) {
            return false;
         }

         member = (DDMember)iter.next();
      } while(!member.isUp());

      return true;
   }

   private synchronized void stop() {
      this.unbindFromJNDI();
      this.loadBalancer = null;
   }

   private synchronized void refresh() {
      boolean isUp = this.areAnyMembersUp();
      if (!isUp) {
         this.stop();
      } else {
         if (this.loadBalancer == null) {
            this.loadBalancer = new JMSLoadBalancer(this.ddHandler);
            this.bindIntoJNDI();
         } else {
            String ddHandlerJNDIName = JMSServerUtilities.transformJNDIName(this.ddHandler.getJNDIName(), this.ddHandler.getApplicationName());
            if (this.boundJNDIName != null && !this.boundJNDIName.equals(ddHandlerJNDIName)) {
               this.unbindFromJNDI();
            }

            if (this.boundJNDIName == null && ddHandlerJNDIName != null) {
               this.bindIntoJNDI();
            }

            this.loadBalancer.refresh();
         }

      }
   }

   private void bindIntoJNDI() {
      String jndiName = this.ddHandler.getJNDIName();
      if (this.ddHandler.isJMSResourceDefinition()) {
         if (this.ddHandler.getNamingContext() == null) {
            this.boundJNDIName = null;
         } else {
            String portableJNDIName = JMSServerUtilities.getPortableJNDIName(jndiName);

            try {
               if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                  JMSDebug.JMSDistTopic.debug("Binding " + portableJNDIName + " into JNDI for " + this.ddHandler.getName());
               }

               PrivilegedActionUtilities.bindAsSU(this.ddHandler.getNamingContext(), portableJNDIName, this.ddHandler.getDDImpl(), kernelId);
               this.boundJNDIName = JMSServerUtilities.transformJNDIName(jndiName, this.ddHandler.getApplicationName());
            } catch (NamingException var4) {
               if (!(var4 instanceof NameAlreadyBoundException)) {
                  this.boundJNDIName = null;
                  throw new AssertionError(var4);
               }

               JMSLogger.logNameConflictBindingGlobalJNDIName(this.boundJNDIName, this.ddHandler.getName(), this.ddHandler.getEARModuleName());
               this.boundJNDIName = null;
            }

         }
      } else {
         jndiName = JMSServerUtilities.transformJNDIName(jndiName, this.ddHandler.getApplicationName());
         if (jndiName != null) {
            this.boundJNDIName = jndiName;

            try {
               if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                  JMSDebug.JMSDistTopic.debug("Binding " + this.boundJNDIName + " into JNDI for " + this.ddHandler.getName());
               }

               PrivilegedActionUtilities.bindAsSU(JMSService.getContextWithManagementException(false), this.boundJNDIName, this.ddHandler.getDDImpl(), kernelId);
            } catch (ManagementException | NamingException var5) {
               if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                  JMSDebug.JMSDistTopic.debug("Bind failure", var5);
               }

               if (!(var5 instanceof NameAlreadyBoundException)) {
                  this.boundJNDIName = null;
                  if (var5 instanceof ManagementException) {
                     throw new RuntimeException(var5.getMessage(), var5);
                  }

                  throw new AssertionError(var5);
               }

               JMSLogger.logNameConflictBindingGlobalJNDIName(this.boundJNDIName, this.ddHandler.getName(), this.ddHandler.getEARModuleName());
               this.boundJNDIName = null;
            }

         }
      }
   }

   private void unbindFromJNDI() {
      if (this.boundJNDIName != null) {
         if (this.ddHandler.isJMSResourceDefinition()) {
            if (this.ddHandler.getNamingContext() == null) {
               this.boundJNDIName = null;
            } else {
               String portableJNDIName = JMSServerUtilities.getPortableJNDIName(this.ddHandler.getJNDIName());

               try {
                  if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                     JMSDebug.JMSDistTopic.debug("Unbinding " + portableJNDIName + " from JNDI for " + this.ddHandler.getName());
                  }

                  PrivilegedActionUtilities.unbindAsSU(this.ddHandler.getNamingContext(), portableJNDIName, kernelId);
               } catch (NamingException var3) {
                  if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                     JMSDebug.JMSDistTopic.debug("Unbind failure", var3);
                  }
               }

               this.boundJNDIName = null;
            }
         } else {
            try {
               if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                  JMSDebug.JMSDistTopic.debug("Unbinding " + this.boundJNDIName + " into JNDI for " + this.ddHandler.getName());
               }

               PrivilegedActionUtilities.unbindAsSU(JMSService.getContextWithManagementException(false), this.boundJNDIName, kernelId);
            } catch (ManagementException | NamingException var4) {
               if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                  JMSDebug.JMSDistTopic.debug("Unbind failure", var4);
               }
            }

            this.boundJNDIName = null;
         }
      }
   }

   public int getLoadBalancingPolicy() {
      return this.ddHandler.getLoadBalancingPolicyAsInt();
   }

   public DestinationImpl producerLoadBalance(boolean isPersistent, FESession session, String ddDestinationName) throws JMSException {
      JMSLoadBalancer stableLoadBalancer;
      synchronized(this) {
         if (this.loadBalancer == null) {
            throw new JMSException("No stable LoadBalancer");
         }

         stableLoadBalancer = this.loadBalancer;
      }

      return stableLoadBalancer.producerLoadBalance(isPersistent, session, ddDestinationName);
   }

   public DestinationImpl consumerLoadBalance(FESession session, String ddDestinationName) throws JMSException {
      JMSLoadBalancer stableLoadBalancer;
      synchronized(this) {
         if (this.loadBalancer == null) {
            throw new JMSException("No stable LoadBalancer");
         }

         stableLoadBalancer = this.loadBalancer;
      }

      return stableLoadBalancer.consumerLoadBalance(session, ddDestinationName);
   }

   public DestinationImpl connectionConsumerLoadBalance(String ddDestinationName) throws JMSException {
      JMSLoadBalancer stableLoadBalancer;
      synchronized(this) {
         if (this.loadBalancer == null) {
            throw new JMSException("No stable LoadBalancer");
         }

         stableLoadBalancer = this.loadBalancer;
      }

      return stableLoadBalancer.connectionConsumerLoadBalance(ddDestinationName);
   }

   public JMSFailover getProducerFailover(DistributedDestinationImpl failedDest, Throwable t, boolean isPersistent, FESession sess) {
      JMSLoadBalancer stableLoadBalancer;
      synchronized(this) {
         if (this.loadBalancer == null) {
            return null;
         }

         stableLoadBalancer = this.loadBalancer;
      }

      return stableLoadBalancer.getProducerFailover(failedDest, t, isPersistent, sess);
   }

   public DDMember findDDMemberByMemberName(String memberName) {
      return this.ddHandler == null ? null : this.ddHandler.findMemberByName(memberName);
   }

   public String getUnitOfOrderRouting() {
      return this.ddHandler.getUnitOfOrderRouting();
   }

   public DDHandler getDDHandler() {
      return this.ddHandler;
   }

   public String getName() {
      return this.ddHandler.getName();
   }

   public boolean isDDPartitionedDistributedTopic() {
      return this.loadBalancer == null ? false : this.loadBalancer.isPartitionedDistributedTopic();
   }

   public String toString() {
      return "FEDDHandler: " + this.ddHandler.getName() + ", hash: " + this.hashCode();
   }
}
