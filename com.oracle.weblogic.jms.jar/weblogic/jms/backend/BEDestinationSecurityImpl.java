package weblogic.jms.backend;

import java.util.HashMap;
import javax.jms.JMSSecurityException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.jms.JMSLogger;
import weblogic.jms.backend.udd.SyntheticDestinationBean;
import weblogic.jms.common.EntityName;
import weblogic.jms.common.JMSDestinationSecurity;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.JMSResource;
import weblogic.security.service.SecurityServiceManager;

public class BEDestinationSecurityImpl implements JMSDestinationSecurity {
   private JMSResource jmsResource_send = null;
   private JMSResource jmsResource_receive = null;
   private JMSResource jmsResource_browse = null;
   private static final HashMap queuesByRealmName = new HashMap();
   private static final HashMap topicsByRealmName = new HashMap();

   public BEDestinationSecurityImpl(EntityName entityName, String type, boolean isClusterTargeted, DestinationBean specificBean) {
      if (entityName != null) {
         String applicationName = entityName.getApplicationName();
         String name = entityName.getEntityName();
         if (applicationName != null && applicationName.equals("interop-jms")) {
            applicationName = null;
         }

         if (name != null && name.indexOf("@") >= 0 && specificBean != null && specificBean instanceof SyntheticDestinationBean) {
            name = ((SyntheticDestinationBean)specificBean).getUDDestinationName();
         }

         if (applicationName != null && applicationName.equals("interop-jms")) {
            applicationName = null;
         }

         this.jmsResource_send = new JMSResource(applicationName, entityName.getEARModuleName(), type, name, "send");
         this.jmsResource_receive = new JMSResource(applicationName, entityName.getEARModuleName(), type, name, "receive");
         this.jmsResource_browse = new JMSResource(applicationName, entityName.getEARModuleName(), type, name, "browse");
         if (findAndAddJMSResource(this.jmsResource_send, type.equals("queue"), specificBean == null)) {
            JMSLogger.logMultiSameNamedDestinations(name, type + "s");
         }

      }
   }

   public void checkSendPermission(AuthenticatedSubject subject) throws JMSSecurityException {
      JMSSecurityHelper.checkPermission(this.jmsResource_send, subject);
   }

   public void checkSendPermission() throws JMSSecurityException {
      JMSSecurityHelper.checkPermission(this.jmsResource_send);
   }

   public void checkReceivePermission(AuthenticatedSubject subject) throws JMSSecurityException {
      JMSSecurityHelper.checkPermission(this.jmsResource_receive, subject);
   }

   public void checkReceivePermission() throws JMSSecurityException {
      JMSSecurityHelper.checkPermission(this.jmsResource_receive);
   }

   public void checkBrowsePermission(AuthenticatedSubject subject) throws JMSSecurityException {
      JMSSecurityHelper.checkPermission(this.jmsResource_browse, subject);
   }

   public void checkBrowsePermission() throws JMSSecurityException {
      JMSSecurityHelper.checkPermission(this.jmsResource_browse);
   }

   public JMSResource getJMSResourceForSend() {
      return this.jmsResource_send;
   }

   public JMSResource getJMSResourceForReceive() {
      return this.jmsResource_receive;
   }

   public JMSResource getJMSResourceForBrowse() {
      return this.jmsResource_browse;
   }

   private static boolean findAndAddJMSResource(JMSResource resource, boolean isQueue, boolean isSAF) {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String realmName = SecurityServiceManager.getRealmName(cic.getPartitionId());
      if (realmName == null) {
         realmName = SecurityServiceManager.getAdministrativeRealmName();
      }

      return isQueue ? findAndAddJMSResource(resource, queuesByRealmName, realmName, isSAF) : findAndAddJMSResource(resource, topicsByRealmName, realmName, isSAF);
   }

   private static boolean findAndAddJMSResource(JMSResource resource, HashMap activeMap, String realmName, boolean isSAF) {
      HashMap resources = null;
      synchronized(activeMap) {
         resources = (HashMap)activeMap.get(realmName);
         if (resources == null) {
            resources = new HashMap();
            activeMap.put(realmName, resources);
         }
      }

      JMSResourceReference ref = null;
      synchronized(resources) {
         ref = (JMSResourceReference)resources.get(resource);
         if (ref == null) {
            ref = new JMSResourceReference(resource);
         }

         boolean hasBoth = ref.addReference(isSAF);
         resources.put(resource, ref);
         return hasBoth;
      }
   }

   public static void removeJMSResource(JMSResource resource, boolean isQueue, boolean isSAF) {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String realmName = SecurityServiceManager.getRealmName(cic.getPartitionId());
      if (realmName == null) {
         realmName = SecurityServiceManager.getAdministrativeRealmName();
      }

      if (isQueue) {
         removeJMSResource(resource, queuesByRealmName, realmName, isSAF);
      } else {
         removeJMSResource(resource, topicsByRealmName, realmName, isSAF);
      }

   }

   private static void removeJMSResource(JMSResource resource, HashMap activeMap, String realmName, boolean isSAF) {
      HashMap resources = null;
      synchronized(activeMap) {
         resources = (HashMap)activeMap.get(realmName);
         if (resources == null) {
            return;
         }
      }

      JMSResourceReference ref = null;
      boolean needCheck = false;
      synchronized(resources) {
         ref = (JMSResourceReference)resources.get(resource);
         if (ref == null) {
            return;
         }

         if (ref.removeReference(isSAF)) {
            resources.remove(resource);
            needCheck = true;
         }
      }

      if (needCheck) {
         synchronized(activeMap) {
            resources = (HashMap)activeMap.get(realmName);
            if (resources != null && resources.size() == 0) {
               activeMap.remove(realmName);
            }
         }
      }

   }

   public JMSResource getJMSResource() {
      return this.jmsResource_send;
   }

   private static class JMSResourceReference {
      JMSResource resource;
      boolean hasSAF = false;
      boolean hasNonSAF = false;

      JMSResourceReference(JMSResource resource) {
         this.resource = resource;
      }

      synchronized boolean addReference(boolean isSAF) {
         boolean hadBoth = this.hasBoth();
         if (isSAF) {
            this.hasSAF = true;
         } else {
            this.hasNonSAF = true;
         }

         return this.hasBoth() && !hadBoth;
      }

      synchronized boolean removeReference(boolean isSAF) {
         boolean hadBoth = this.hasBoth();
         if (isSAF) {
            this.hasSAF = false;
         } else {
            this.hasNonSAF = false;
         }

         if (hadBoth && this.hasSAF ^ this.hasNonSAF) {
            JMSLogger.logMultiSameNamedDestinationsCleared(this.resource.getResourceName(), this.resource.getDestinationType());
         }

         return this.hasNon();
      }

      synchronized boolean hasBoth() {
         return this.hasSAF && this.hasNonSAF;
      }

      private synchronized boolean hasNon() {
         return !this.hasSAF && !this.hasNonSAF;
      }
   }
}
