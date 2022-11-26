package weblogic.jms.saf;

import java.util.HashMap;
import java.util.List;
import javax.jms.JMSException;
import weblogic.application.ModuleException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.SAFDestinationBean;
import weblogic.j2ee.descriptor.wl.SAFErrorHandlingBean;
import weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBean;
import weblogic.jms.JMSService;
import weblogic.jms.backend.BEDestinationImpl;
import weblogic.jms.backend.BackEnd;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSMessageExpirationHelper;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.deployer.BEDeployer;
import weblogic.jms.module.JMSBeanHelper;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.utils.BeanListenerCustomizer;
import weblogic.management.utils.GenericBeanListener;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.RedirectionListener;
import weblogic.messaging.kernel.SendOptions;

public class ErrorHandler implements JMSModuleManagedEntity, BeanListenerCustomizer {
   public static final int DISCARD = 1;
   public static final int DISCARD_AND_LOG = 2;
   public static final int REDIRECT = 3;
   public static final int ALWAYS_FORWARD = 4;
   private SAFErrorHandlingBean ehBean;
   private SAFDestinationBean safErrorDestinationBean;
   private short type;
   private String name;
   private String policy;
   private String logFormat;
   private int policyAsInt = -1;
   private String fullyQualifiedName;
   private static final HashMap safErrorHandlingBeanSignatures = new HashMap();
   private GenericBeanListener safErrorHandlingBeanListener;
   private HashMap importedDestinations;
   private String safErrorDestinationNamePrefix;
   private final BEDeployer beDeployer;

   public ErrorHandler() throws ModuleException {
      this.beDeployer = JMSService.getJMSServiceWithModuleException().getBEDeployer();
   }

   public ErrorHandler(SAFErrorHandlingBean ehBean, String fullyQualifiedName, List localTargets, String moduleName) throws ModuleException {
      this.ehBean = ehBean;
      this.name = ehBean.getName();
      this.fullyQualifiedName = fullyQualifiedName;
      this.type = 2;
      this.beDeployer = JMSService.getJMSServiceWithModuleException().getBEDeployer();
      this.initialize(moduleName);
   }

   private void initialize(String moduleName) throws ModuleException {
      this.policy = this.ehBean.getPolicy();
      this.policyAsInt = this.getSafErrorHandlingPolicyAsInt();
      this.logFormat = this.ehBean.getLogFormat();
      this.safErrorDestinationBean = this.ehBean.getSAFErrorDestination();
      if (this.safErrorDestinationBean != null) {
         String idGroupName = ((SAFImportedDestinationsBean)((DescriptorBean)this.safErrorDestinationBean).getParentBean()).getName();
         this.safErrorDestinationNamePrefix = JMSBeanHelper.getDecoratedName(moduleName, idGroupName);
      }

      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("ErrorHandling " + this.fullyQualifiedName + ": Policy = " + this.policy);
      }

      this.safErrorHandlingBeanListener = JMSSAFManager.initializeGenericBeanListener((DescriptorBean)this.ehBean, this, this, safErrorHandlingBeanSignatures, (HashMap)null);
      JMSSAFManager.manager.addErrorHandler(this.fullyQualifiedName, this);
      this.importedDestinations = new HashMap();
   }

   public short getType() {
      return this.type;
   }

   public String getFullyQualifiedName() {
      return this.fullyQualifiedName;
   }

   public boolean isAlwaysForward() {
      return this.policyAsInt == 4;
   }

   synchronized void removeImportedDestination(String name) {
      this.importedDestinations.remove(name);
   }

   public void prepare() {
   }

   public void activate(JMSBean realWholeModule) throws ModuleException {
      this.ehBean = realWholeModule.lookupSAFErrorHandling(this.getEntityName());
      this.safErrorHandlingBeanListener = JMSSAFManager.initializeGenericBeanListener((DescriptorBean)this.ehBean, this, this, safErrorHandlingBeanSignatures, (HashMap)null);
      if (this.safErrorHandlingBeanListener != null) {
         this.safErrorHandlingBeanListener.open();
      }

   }

   public void deactivate() {
      if (this.safErrorHandlingBeanListener != null) {
         this.safErrorHandlingBeanListener.close();
      }

   }

   public void unprepare() {
   }

   public void destroy() {
      JMSSAFManager.manager.removeErrorHandler(this.fullyQualifiedName);
   }

   public void remove() {
   }

   public String getEntityName() {
      return this.ehBean.getName();
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

   public String toString() {
      String str = "Policy=" + this.getPolicy();
      if (this.getType() == 3) {
         str = str + ", ErrorDestination" + this.safErrorDestinationBean.getName();
      }

      return str;
   }

   public String getName() {
      return this.name;
   }

   private int getSafErrorHandlingPolicyAsInt() {
      int policyAsInt = 1;
      if (this.policy != null) {
         if ("Discard".equals(this.policy)) {
            policyAsInt = 1;
         } else if ("Log".equals(this.policy)) {
            policyAsInt = 2;
         } else if ("Redirect".equals(this.policy)) {
            policyAsInt = 3;
         } else if ("Always-Forward".equals(this.policy)) {
            policyAsInt = 4;
         }
      }

      return policyAsInt;
   }

   public String getPolicy() {
      return this.policy;
   }

   public int getPolicyAsInt() {
      return this.policyAsInt;
   }

   public synchronized void setPolicy(String policyStr) {
      this.policy = policyStr;
      this.policyAsInt = this.getSafErrorHandlingPolicyAsInt();
   }

   public String getLogFormat() {
      return this.logFormat;
   }

   public void setLogFormat(String logFormat) {
      this.logFormat = logFormat;
   }

   public SAFDestinationBean getSAFErrorDestination() {
      return this.safErrorDestinationBean;
   }

   public void setSAFErrorDestination(SAFDestinationBean dest) {
      this.safErrorDestinationBean = dest;
   }

   void handleFailure(RedirectionListener.Info redirectionInfo, String targetName, MessageImpl message) throws KernelException, JMSException {
      switch (this.getPolicyAsInt()) {
         case 1:
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("Handle failure: policy = " + this.getPolicyAsInt());
            }

            return;
         case 2:
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("Handle failure: policy = " + this.getPolicy() + " log policy = " + this.getLogFormat());
            }

            StringBuffer tempProperties = new StringBuffer(256);
            List expirationLoggingJMSHeaders = JMSMessageExpirationHelper.extractJMSHeaderAndProperty(this.getLogFormat(), tempProperties);
            List expirationLoggingUserProperties = JMSMessageExpirationHelper.convertStringToLinkedList(tempProperties.toString());
            JMSMessageExpirationHelper.logExpiredSAFMessage(message, expirationLoggingJMSHeaders, expirationLoggingUserProperties);
            return;
         case 3:
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("Handle failure: policy = " + this.getPolicy() + " Error destination = " + this.safErrorDestinationBean.getName());
            }

            BEDestinationImpl destination = this.findDestination(targetName, this.safErrorDestinationBean.getName());
            Destination kernelDestination = destination.getKernelDestination();
            if (redirectionInfo != null) {
               redirectionInfo.setRedirectDestination(kernelDestination);
               redirectionInfo.setSendOptions(this.createSendOptions(message, destination));
            } else {
               this.redirect(destination, message);
            }

            return;
         default:
      }
   }

   private SendOptions createSendOptions(MessageImpl message, BEDestinationImpl destination) throws JMSException {
      this.overrideMessageProperties(message);
      return destination.createSendOptions(0L, destination.findOrCreateKernelSequence(message), message);
   }

   private BEDestinationImpl findDestination(String beName, String destName) {
      String fullyQualifiedErrorDestinationName = JMSBeanHelper.getDecoratedName(this.safErrorDestinationNamePrefix, destName);
      BackEnd backEnd = this.beDeployer.findBackEnd(beName);
      fullyQualifiedErrorDestinationName = backEnd.getFullSAFDestinationName(fullyQualifiedErrorDestinationName);
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("Finding kernel destination " + destName + " in " + beName + " fully qualified error destination name = " + fullyQualifiedErrorDestinationName);
      }

      BEDestinationImpl dest = backEnd.findDestination(fullyQualifiedErrorDestinationName);
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("Found kernel destination " + destName + " in " + beName + " backend = " + backEnd + " dest = " + dest);
      }

      return dest;
   }

   private void overrideMessageProperties(MessageImpl message) {
      message.setDeliveryTime(0L);
      message._setJMSRedeliveryLimit(-1);
      message._setJMSExpiration(0L);
      message.setSAFSequenceName((String)null);
      message.setSAFSeqNumber(0L);
   }

   private void redirect(BEDestinationImpl destination, MessageImpl message) throws KernelException, JMSException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("Redirecting message: " + message.getJMSMessageID() + " to " + destination.getName());
      }

      Destination kernelDestination = destination.getKernelDestination();
      KernelRequest request = kernelDestination.send(message, this.createSendOptions(message, destination));
      if (request != null) {
         request.getResult();
      }

      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("Successfully redirected " + message.getJMSMessageID() + " to destination " + kernelDestination.getName());
      }

   }

   static {
      safErrorHandlingBeanSignatures.put("Policy", String.class);
      safErrorHandlingBeanSignatures.put("LogFormat", String.class);
      safErrorHandlingBeanSignatures.put("SAFErrorDestination", SAFDestinationBean.class);
   }
}
