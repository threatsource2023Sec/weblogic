package weblogic.deployment.jms;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.j2ee.descriptor.wl.ForeignConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.ForeignJNDIObjectBean;
import weblogic.j2ee.descriptor.wl.ForeignServerBean;
import weblogic.j2ee.descriptor.wl.PropertyBean;
import weblogic.jndi.OpaqueReference;
import weblogic.jndi.internal.JNDIEnvironment;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.security.SubjectUtils;
import weblogic.security.WLSPrincipals;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;

public class ForeignOpaqueReference implements ForeignOpaqueTag, OpaqueReference, Serializable {
   static final long serialVersionUID = 4404892619941441265L;
   private static final transient AbstractSubject KERNEL_ID = (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   private boolean isFactory = false;
   private String username;
   private String password;
   private String connectionHealthChecking = "enabled";
   private Hashtable jndiEnvironment;
   private String remoteJNDIName;
   private static String AQJMS_ICF = "oracle.jms.AQjmsInitialContextFactory";
   private static String AQJMS_QPREFIX = "Queues/";
   private static String AQJMS_TPREFIX = "Topics/";
   private transient Object cachedReferent;
   private static final String MACROS_ENABLED_PROP = "weblogic.jms.foreign.EnableMacros";
   private static final boolean MACROS_ENABLED = macrosEnabled();
   private static final String SERVER_NAME_MACRO = "_SVNAME_";
   private static final String SERVER_NUM_MACRO = "_SVNUM_";

   public ForeignOpaqueReference() {
   }

   public ForeignOpaqueReference(ForeignServerBean foreignServerBean, ForeignJNDIObjectBean fJndiObject) {
      this.remoteJNDIName = fJndiObject.getRemoteJNDIName();
      if (fJndiObject instanceof ForeignConnectionFactoryBean) {
         this.isFactory = true;
         this.username = ((ForeignConnectionFactoryBean)fJndiObject).getUsername();
         this.password = ((ForeignConnectionFactoryBean)fJndiObject).getPassword();
         this.connectionHealthChecking = ((ForeignConnectionFactoryBean)fJndiObject).getConnectionHealthChecking();
      }

      PropertyBean[] properties = foreignServerBean.getJNDIProperties();
      if (properties != null && properties.length != 0) {
         this.jndiEnvironment = new Hashtable(properties.length);

         for(int i = 0; i < properties.length; ++i) {
            this.jndiEnvironment.put(properties[i].getKey(), properties[i].getValue());
         }
      }

      if (this.jndiEnvironment == null) {
         this.jndiEnvironment = new Hashtable(3);
      }

      String icf = foreignServerBean.getInitialContextFactory();
      if (icf != null && icf.trim().length() != 0 && !icf.equals("weblogic.jndi.WLInitialContextFactory")) {
         this.jndiEnvironment.put("java.naming.factory.initial", icf);
      }

      String url = foreignServerBean.getConnectionURL();
      if (url != null && url.trim().length() != 0) {
         this.jndiEnvironment.put("java.naming.provider.url", url);
      }

      byte[] credential = foreignServerBean.getJNDIPropertiesCredentialEncrypted();
      if (credential != null) {
         AbstractSubject kernelId = (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
         String cString = new String(JNDIEnvironment.getJNDIEnvironment().encryptionHelperDecrypt(credential, (AuthenticatedSubject)kernelId));
         JNDIEnvironment.getJNDIEnvironment().encryptionHelperClear(credential);
         if (cString.trim().length() > 0) {
            this.jndiEnvironment.put("java.naming.security.credentials", cString);
         }
      }

      if (this.jndiEnvironment.size() == 0) {
         this.jndiEnvironment = null;
      }

   }

   public Object getReferent(Name name, Context ctx) throws NamingException {
      AbstractSubject originalSubject = SubjectManager.getSubjectManager().getCurrentSubject(KERNEL_ID);
      InitialContext context;
      if (this.jndiEnvironment == null) {
         context = new InitialContext();
      } else {
         if (this.jndiEnvironment.get("java.naming.factory.initial") == null) {
            this.jndiEnvironment.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
         }

         context = new InitialContext(this.jndiEnvironment);
      }

      AbstractSubject subject = SubjectManager.getSubjectManager().getCurrentSubject(KERNEL_ID);
      if (KernelStatus.isServer()) {
         String subjectUserName = SubjectUtils.getUsername(subject.getSubject());
         String originalSubjectUserName = SubjectUtils.getUsername(originalSubject.getSubject());
         if (WLSPrincipals.isKernelUsername(subjectUserName)) {
            if (WLSPrincipals.isKernelUsername(originalSubjectUserName)) {
               subject = SubjectManager.getSubjectManager().getAnonymousSubject();
            } else {
               subject = originalSubject;
            }
         }

         String url = null;
         if (this.jndiEnvironment != null && this.jndiEnvironment.get("java.naming.security.principal") == null) {
            url = (String)this.jndiEnvironment.get("java.naming.provider.url");
            if (url != null && this.jndiEnvironment.get("java.naming.factory.initial") != null && ((String)this.jndiEnvironment.get("java.naming.factory.initial")).indexOf("weblogic") != -1) {
               try {
                  if (RMIEnvironment.getEnvironment().isRemoteDomain(url)) {
                     subject = SubjectManager.getSubjectManager().getAnonymousSubject();
                  }
               } catch (IOException var16) {
                  throw new NamingException(var16.getMessage());
               }
            }
         }
      }

      SubjectManager.getSubjectManager().pushSubject(KERNEL_ID, subject);

      Object retVal;
      try {
         if (this.jndiEnvironment != null && AQJMS_ICF.equals(this.jndiEnvironment.get("java.naming.factory.initial")) && this.remoteJNDIName != null && (this.remoteJNDIName.startsWith(AQJMS_QPREFIX) || this.remoteJNDIName.startsWith(AQJMS_TPREFIX))) {
            synchronized(this) {
               if (this.cachedReferent == null) {
                  this.cachedReferent = context.lookup(evalMacros(this.remoteJNDIName));
               }
            }

            retVal = this.cachedReferent;
         } else {
            retVal = context.lookup(evalMacros(this.remoteJNDIName));
         }
      } finally {
         SubjectManager.getSubjectManager().popSubject(KERNEL_ID);
         context.close();
      }

      if (retVal instanceof ObjectBasedSecurityAware && ((ObjectBasedSecurityAware)retVal).isOBSEnabled()) {
         throw new NamingException(JMSPoolLogger.logUnsupportedConnectionFactoryReferencedByForeignSeverLoggable().getMessage());
      } else {
         if (retVal instanceof ForeignJMSServerAware) {
            ((ForeignJMSServerAware)retVal).setReferencedByFS(true);
         }

         return retVal;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("ForeignOpaqueReference: target=\"");
      buf.append(this.remoteJNDIName);
      buf.append('"');
      if (this.jndiEnvironment != null) {
         Enumeration allProps = this.jndiEnvironment.keys();

         while(allProps.hasMoreElements()) {
            String key = (String)allProps.nextElement();
            String value = (String)this.jndiEnvironment.get(key);
            buf.append(' ');
            buf.append(key);
            buf.append('=');
            buf.append(value);
         }
      }

      return buf.toString();
   }

   public boolean isFactory() {
      return this.isFactory;
   }

   public String getUsername() {
      return this.username;
   }

   public String getPassword() {
      return this.password;
   }

   public String getConnectionHealthChecking() {
      return this.connectionHealthChecking;
   }

   public Hashtable getJNDIEnvironment() {
      return this.jndiEnvironment;
   }

   public String getRemoteJNDIName() {
      return this.remoteJNDIName;
   }

   private static String evalMacros(String jndiName) throws NamingException {
      if (MACROS_ENABLED && jndiName != null && (jndiName.contains("_SVNAME_") || jndiName.contains("_SVNUM_"))) {
         String sName = getServerName();
         String sNum = "";

         for(int i = sName.length() - 1; i >= 0 && Character.isDigit(sName.charAt(i)); --i) {
            sNum = sName.charAt(i) + sNum;
         }

         if (jndiName.contains("_SVNUM_") && sNum.length() == 0) {
            throw new NamingException("Cannot resolve Foreign JMS Destination '" + jndiName + "' on server '" + sName + "', the macro '" + "_SVNUM_" + "' requires the server name to have a numeric suffix.");
         } else {
            sName = Matcher.quoteReplacement(sName);
            String ret = jndiName.replaceAll("_SVNAME_", sName).replaceAll("_SVNUM_", sNum);
            return ret;
         }
      } else {
         return jndiName;
      }
   }

   private static boolean macrosEnabled() {
      try {
         return System.getProperty("weblogic.jms.foreign.EnableMacros", "false").equals("true");
      } catch (Throwable var1) {
         return false;
      }
   }

   private static String getServerName() throws NamingException {
      if (!KernelStatus.isServer()) {
         throw new NamingException("Cannot determine server name on a client.");
      } else {
         try {
            Class mgmtService = Class.forName("weblogic.management.provider.ManagementService");
            Class authSubj = Class.forName("weblogic.security.acl.internal.AuthenticatedSubject");
            Method rtAccess = mgmtService.getMethod("getRuntimeAccess", authSubj);
            Object returnObject = rtAccess.invoke((Object)null, KERNEL_ID);
            Class rtAccessClass = Class.forName("weblogic.management.provider.RuntimeAccess");
            Method getServerNameMethod = rtAccessClass.getMethod("getServerName");
            Object returnObject1 = getServerNameMethod.invoke(returnObject);
            return (String)returnObject1;
         } catch (Throwable var7) {
            NamingException ne = new NamingException("Cannot determine server name");
            ne.initCause(var7);
            throw ne;
         }
      }
   }
}
