package weblogic.jms.saf;

import java.util.HashMap;
import java.util.List;
import weblogic.application.ModuleException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.SAFLoginContextBean;
import weblogic.j2ee.descriptor.wl.SAFRemoteContextBean;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.utils.BeanListenerCustomizer;
import weblogic.management.utils.GenericBeanListener;

public class RemoteContext implements JMSModuleManagedEntity, BeanListenerCustomizer {
   public static final String CONNECTION_URL = "java.naming.provider.url";
   public static final String INITIAL_CONTEXT_FACTORY = "java.naming.factory.initial";
   public static final String JNDI_SECURITY_PRINCIPAL = "java.naming.security.principal";
   public static final String JNDI_SECURITY_CREDENTIALS = "java.naming.security.credentials";
   public static final String DESTINATION_URL = "DestinationURL";
   private SAFLoginContextBean safLoginContextBean;
   private SAFRemoteContextBean safRemoteContextBean;
   private static final HashMap safRemoteContextBeanSignatures = new HashMap();
   private GenericBeanListener safRemoteContextBeanListener;
   private int compressionThreshold = Integer.MAX_VALUE;
   private String replyToSAFRemoteContextName;
   private String fullyQualifiedName;

   public RemoteContext(SAFRemoteContextBean remoteContext, String fullyQualifiedName) throws ModuleException {
      this.safRemoteContextBean = remoteContext;
      this.fullyQualifiedName = fullyQualifiedName;
      this.initialize();
   }

   private void initialize() throws ModuleException {
      this.safLoginContextBean = this.safRemoteContextBean.getSAFLoginContext();
      this.compressionThreshold = this.safRemoteContextBean.getCompressionThreshold();
      this.replyToSAFRemoteContextName = this.safRemoteContextBean.getReplyToSAFRemoteContextName();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug(" RemoteContext:\n =================\n   compressionThreshold = " + this.compressionThreshold + "   replyto SAFRemoteContext Name = " + this.replyToSAFRemoteContextName);
      }

      this.safRemoteContextBeanListener = JMSSAFManager.initializeGenericBeanListener((DescriptorBean)this.safRemoteContextBean, this, this, safRemoteContextBeanSignatures, (HashMap)null);
      JMSSAFManager.manager.addRemoteSAFContext(this.fullyQualifiedName, this);
   }

   public String getFullyQualifiedName() {
      return this.fullyQualifiedName;
   }

   public void prepare() {
   }

   public void activate(JMSBean realModule) throws ModuleException {
      this.safRemoteContextBean = realModule.lookupSAFRemoteContext(this.getEntityName());
      this.safRemoteContextBeanListener = JMSSAFManager.initializeGenericBeanListener((DescriptorBean)this.safRemoteContextBean, this, this, safRemoteContextBeanSignatures, (HashMap)null);
      if (this.safRemoteContextBeanListener != null) {
         this.safRemoteContextBeanListener.open();
      }

   }

   public void deactivate() {
      if (this.safRemoteContextBeanListener != null) {
         this.safRemoteContextBeanListener.close();
      }

   }

   public void unprepare() {
   }

   public void destroy() {
      JMSSAFManager.manager.removeRemoteSAFContext(this.fullyQualifiedName);
   }

   public void remove() {
   }

   public String getEntityName() {
      return this.safRemoteContextBean.getName();
   }

   public void setTargets(List targets, DomainMBean proposedDomain) {
   }

   public void prepareChangeOfTargets(List targets, DomainMBean proposedDomain) {
   }

   public void activateChangeOfTargets() {
   }

   public void rollbackChangeOfTargets() {
   }

   public void activateFinished() {
   }

   public void setCompressionThreshold(Integer compressionThreshold) {
      this.compressionThreshold = compressionThreshold;
   }

   public String getReplyToSAFRemoteContextName() {
      return this.replyToSAFRemoteContextName;
   }

   public void setReplyToSAFRemoteContextName(String replytoSAFRemoteContextName) {
      this.replyToSAFRemoteContextName = replytoSAFRemoteContextName;
   }

   public SAFRemoteContextBean getSafRemoteContextBean() {
      return this.safRemoteContextBean;
   }

   public String getLoginURL() {
      return this.safLoginContextBean == null ? null : this.safLoginContextBean.getLoginURL();
   }

   static {
      safRemoteContextBeanSignatures.put("CompressionThreshold", Integer.class);
      safRemoteContextBeanSignatures.put("ReplyToSAFRemoteContextName", String.class);
   }
}
