package weblogic.jms.common;

import java.security.AccessControlException;
import java.security.AccessController;
import java.util.Hashtable;
import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContext;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.j2ee.descriptor.wl.NamedEntityBean;
import weblogic.jms.JMSService;
import weblogic.jms.backend.BEDestinationImpl;
import weblogic.jms.backend.BackEnd;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.JMSDispatcherManager;
import weblogic.jms.dispatcher.Request;
import weblogic.jndi.factories.java.javaURLContextFactory;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.DispatcherProxy;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.rmi.spi.HostID;
import weblogic.security.HMAC;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.subject.SubjectManager;
import weblogic.utils.StringUtils;

public final class JMSServerUtilities {
   private static final String APPSCOPED_JNDI_PREFIX = "weblogic.applications";
   private static final AuthenticatedSubject kernelID = getKernelIdentity();

   private static final AuthenticatedSubject getKernelIdentity() {
      try {
         return (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
      } catch (AccessControlException var1) {
         return null;
      }
   }

   public static Context getLocalJNDIContext() {
      try {
         javaURLContextFactory contextFactory = new javaURLContextFactory();
         return (Context)contextFactory.getObjectInstance((Object)null, (Name)null, (Context)null, (Hashtable)null);
      } catch (NamingException var1) {
         return null;
      }
   }

   public static void pushLocalJNDIContext(Context c) {
      javaURLContextFactory.pushContext(c);
   }

   public static void popLocalJNDIContext() {
      javaURLContextFactory.popContext();
   }

   public static HostID getHostId(Context context, DestinationImpl destination) {
      try {
         DispatcherId dispatcherId = destination.getDispatcherId();
         String name = JMSDispatcherManager.getDispatcherJNDIName(dispatcherId);
         DispatcherWrapper dispatcherWrapper = (DispatcherWrapper)context.lookup(name);
         return dispatcherWrapper.getRemoteDispatcher() != null && dispatcherWrapper.getRemoteDispatcher() instanceof DispatcherProxy ? ((DispatcherProxy)dispatcherWrapper.getRemoteDispatcher()).getRJVM().getHostID() : RemoteHelper.getHostID(dispatcherWrapper.getRemoteDispatcher());
      } catch (NamingException var5) {
         return null;
      }
   }

   public static String getAppscopedGlobalJNDIName(String applicationName, String moduleName, NamedEntityBean nBean) {
      return applicationName != null && moduleName != null && nBean != null ? new String("weblogic.applications" + applicationName + "." + moduleName + "#" + nBean.getName()) : null;
   }

   public static String getPortableJNDIName(String jndiName) {
      if (jndiName == null) {
         return jndiName;
      } else if (jndiName.startsWith("java:global")) {
         return jndiName.substring("java:global".length() + 1);
      } else if (jndiName.startsWith("java:comp")) {
         return jndiName.substring("java:comp".length() + 1);
      } else if (jndiName.startsWith("java:app")) {
         return jndiName.substring("java:app".length() + 1);
      } else {
         return jndiName.startsWith("java:module") ? jndiName.substring("java:module".length() + 1) : jndiName;
      }
   }

   public static ConnectionFactory getXAConnectionFactory(JMSService jmsService) {
      return jmsService.getDefaultConnectionFactory("DefaultXAConnectionFactory").getJMSConnectionFactory();
   }

   public static ConnectionFactory getXAConnectionFactory0(JMSService jmsService) {
      return jmsService.getDefaultConnectionFactory("DefaultXAConnectionFactory0").getJMSConnectionFactory();
   }

   public static ConnectionFactory getXAConnectionFactory1(JMSService jmsService) {
      return jmsService.getDefaultConnectionFactory("DefaultXAConnectionFactory1").getJMSConnectionFactory();
   }

   public static ConnectionFactory getXAConnectionFactory2(JMSService jmsService) {
      return jmsService.getDefaultConnectionFactory("DefaultXAConnectionFactory2").getJMSConnectionFactory();
   }

   public static ConnectionFactory getConnectionFactory(JMSService jmsService) {
      return jmsService.getDefaultConnectionFactory("DefaultConnectionFactory").getJMSConnectionFactory();
   }

   public static BEDestinationImpl findBEDestinationByJNDIName(JMSService jmsService, String jndiName) {
      BackEnd[] backends = jmsService.getBEDeployer().getBackEnds();

      int j;
      for(int i = 0; i < backends.length; ++i) {
         BEDestinationImpl[] destinations = backends[i].getBEDestinations();

         for(j = 0; j < destinations.length; ++j) {
            if (destinations[j] != null && jndiName.equals(destinations[j].getJNDIName())) {
               return destinations[j];
            }

            if (destinations[j] != null && jndiName.equals(destinations[j].getLocalJNDIName())) {
               return destinations[j];
            }
         }
      }

      BackEnd[] var14 = backends;
      int var15 = backends.length;

      for(j = 0; j < var15; ++j) {
         BackEnd backend = var14[j];
         BEDestinationImpl[] destinations = backend.getBEDestinations();
         BEDestinationImpl[] var8 = destinations;
         int var9 = destinations.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            BEDestinationImpl destination = var8[var10];
            String memberJNDIName = destination.getJNDIName();
            int index = memberJNDIName != null ? memberJNDIName.lastIndexOf(64) : -1;
            if (index != -1 && jndiName.equals(memberJNDIName.substring(index + 1))) {
               return destination;
            }
         }
      }

      return null;
   }

   public static String transformJNDIName(String jndiName) {
      try {
         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("In JMSServerUtilities.transformJNDIName with jndiName: " + jndiName);
         }

         String appName = getCurrentApplicationContext().getApplicationId();
         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("In JMSServerUtilities.transformJNDIName. Found app name: " + appName);
         }

         jndiName = transformJNDIName(jndiName, appName);
         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("In JMSServerUtilities.transformJNDIName. Calculated final jndiName: " + jndiName);
         }
      } catch (IllegalStateException var2) {
         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("In JMSServerUtilities.transformJNDIName. Didn't get app name due to exception: " + var2.toString());
         }
      }

      return jndiName;
   }

   public static String transformJNDIName(String jndiName, String appName) {
      if (jndiName == null) {
         return null;
      } else {
         return jndiName.indexOf("${APPNAME}") != -1 ? StringUtils.replaceGlobal(jndiName, "${APPNAME}", appName) : jndiName;
      }
   }

   public static ApplicationContext getCurrentApplicationContext() throws IllegalStateException {
      ApplicationContext appContext = ApplicationAccess.getApplicationAccess().getCurrentApplicationContext();
      if (appContext == null) {
         throw new IllegalStateException("Attempt to access current application context from a component which has no application context");
      } else {
         return appContext;
      }
   }

   public static boolean verifySignature(byte[] signature, byte[] data, byte[] secret) {
      return HMAC.verify(signature, data, secret, SerializedSystemIni.getSalt());
   }

   public static byte[] digest(byte[] data, byte[] secret) {
      return HMAC.digest(data, secret, SerializedSystemIni.getSalt());
   }

   public static byte[] generateSecret(String additionalSecretStr) {
      byte[] baseSecret = SerializedSystemIni.getEncryptedSecretKey();
      byte[] additionalSecret = additionalSecretStr.getBytes();
      byte[] signatureSecret = new byte[baseSecret.length + additionalSecret.length];
      System.arraycopy(baseSecret, 0, signatureSecret, 0, baseSecret.length);
      System.arraycopy(additionalSecret, 0, signatureSecret, baseSecret.length, additionalSecret.length);
      return signatureSecret;
   }

   public static void anonDispatchNoReply(Request request, JMSDispatcher dispatcher) throws javax.jms.JMSException {
      anonDispatchNoReply(request, dispatcher, false);
   }

   public static void anonDispatchNoReply(Request request, JMSDispatcher dispatcher, boolean forceAnonymous) throws javax.jms.JMSException {
      AuthenticatedSubject subjectToUse = null;

      try {
         if (forceAnonymous) {
            subjectToUse = SubjectUtils.getAnonymousSubject();
         } else if (!dispatcher.isLocal()) {
            AuthenticatedSubject currentSubject = SecurityServiceManager.getCurrentSubject(kernelID);
            if (SecurityServiceManager.isKernelIdentity(currentSubject) || SecurityServiceManager.isServerIdentity(currentSubject)) {
               subjectToUse = SubjectUtils.getAnonymousSubject();
            }
         }

         if (subjectToUse != null) {
            SubjectManager.getSubjectManager().pushSubject(kernelID, subjectToUse);
         }

         dispatcher.dispatchNoReply(request);
      } finally {
         if (subjectToUse != null) {
            SubjectManager.getSubjectManager().popSubject(kernelID);
         }

      }

   }

   public static boolean isBindVersioned() {
      return ApplicationVersionUtils.getBindApplicationId() != null;
   }

   public static void setBindApplicationVersionIdContext() {
      String appVersion = ApplicationAccess.getApplicationAccess().getApplicationVersion(Thread.currentThread().getContextClassLoader());
      if (appVersion != null) {
         ApplicationVersionUtils.setCurrentVersionId(ApplicationAccess.getApplicationAccess().getCurrentApplicationName());
         ApplicationVersionUtils.setBindApplicationId(ApplicationAccess.getApplicationAccess().getCurrentApplicationName());
      }
   }

   public static void unsetBindApplicationVersionIdContext() {
      String appVersion = ApplicationAccess.getApplicationAccess().getApplicationVersion(Thread.currentThread().getContextClassLoader());
      if (appVersion != null) {
         ApplicationVersionUtils.unsetCurrentVersionId(ApplicationAccess.getApplicationAccess().getCurrentApplicationName());
         ApplicationVersionUtils.unsetBindApplicationId();
      }
   }
}
