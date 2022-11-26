package weblogic.ejb.container.persistence;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.SAXParseException;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.Deployer;
import weblogic.ejb.container.cmp.rdbms.EJBQLParsingException;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.CMPInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.CMPCodeGenerator;
import weblogic.ejb.container.persistence.spi.CMPDeployer;
import weblogic.ejb.container.persistence.spi.JarDeployment;
import weblogic.ejb.container.persistence.spi.PersistenceManager;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.logging.Loggable;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.utils.Getopt2;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.process.XMLParsingException;

public final class PersistenceType {
   private static final boolean debug = System.getProperty("weblogic.ejb.container.persistence.debug") != null;
   private VirtualJarFile sourceJarFile;
   private PersistenceVendor vendor = null;
   private String typeIdentifier = null;
   private String typeVersion = null;
   private String weblogicVersion = null;
   private String cmpVersion = null;
   private String cmpDeployerClassName = null;
   private String persistenceManagerClassName = null;
   private String exclusiveManagerClassName = null;
   private String databaseManagerClassName = null;
   private String readonlyManagerClassName = null;
   private String jarDeploymentClassName = null;
   private String codeGeneratorClassName = null;
   private JarDeployment jarDeployment = null;
   private Class codeGeneratorClass;
   private Constructor codeGeneratorConstructor;
   private Getopt2 options;

   public PersistenceVendor getVendor() {
      return this.vendor;
   }

   public void setVendor(PersistenceVendor vendor) {
      if (debug) {
         Debug.assertion(null != vendor);
      }

      this.vendor = vendor;
   }

   public String getIdentifier() {
      return this.typeIdentifier;
   }

   public void setIdentifier(String identifier) {
      if (debug) {
         Debug.assertion(null != identifier);
      }

      this.typeIdentifier = identifier;
   }

   public String getVersion() {
      return this.typeVersion;
   }

   public void setVersion(String version) {
      if (debug) {
         Debug.assertion(null != version);
      }

      this.typeVersion = version;
   }

   public String getWeblogicVersion() {
      return this.weblogicVersion;
   }

   public void setWeblogicVersion(String version) {
      this.weblogicVersion = version;
   }

   public String getCmpVersion() {
      return this.cmpVersion;
   }

   public void setCmpVersion(String version) {
      this.cmpVersion = version;
   }

   public void setCmpDeployerClassName(String name) {
      this.cmpDeployerClassName = name;
   }

   public CMPDeployer getCmpDeployer() {
      return (CMPDeployer)this.newInstance(this.cmpDeployerClassName);
   }

   public void setPersistenceManagerClassName(String name) {
      this.persistenceManagerClassName = name;
   }

   public PersistenceManager getPersistenceManager() {
      return (PersistenceManager)this.newInstance(this.persistenceManagerClassName);
   }

   public void setExclusiveManagerClassName(String name) {
      this.exclusiveManagerClassName = name;
   }

   public BeanManager getExclusiveManager() {
      return (BeanManager)this.newInstance(this.exclusiveManagerClassName);
   }

   public boolean hasExclusiveManager() {
      return this.exclusiveManagerClassName != null;
   }

   public void setDatabaseManagerClassName(String name) {
      this.databaseManagerClassName = name;
   }

   public BeanManager getDatabaseManager() {
      return (BeanManager)this.newInstance(this.databaseManagerClassName);
   }

   public boolean hasDatabaseManager() {
      return this.databaseManagerClassName != null;
   }

   public void setReadonlyManagerClassName(String name) {
      this.readonlyManagerClassName = name;
   }

   public BeanManager getReadonlyManager() {
      return (BeanManager)this.newInstance(this.readonlyManagerClassName);
   }

   public boolean hasReadonlyManager() {
      return this.readonlyManagerClassName != null;
   }

   public boolean hasJarDeployment() {
      return this.jarDeploymentClassName != null;
   }

   public void setJarDeploymentClassName(String name) {
      this.jarDeploymentClassName = name;
   }

   public JarDeployment getJarDeployment() {
      if (this.jarDeployment == null) {
         this.jarDeployment = (JarDeployment)this.newInstance(this.jarDeploymentClassName);
      }

      return this.jarDeployment;
   }

   public void setOptions(Getopt2 options) {
      this.options = options;
   }

   public boolean hasCodeGenerator() {
      return this.codeGeneratorClassName != null;
   }

   public void setCodeGeneratorClassName(String name) {
      this.codeGeneratorClassName = name;
      this.codeGeneratorClass = this.loadClass(this.codeGeneratorClassName);
      this.codeGeneratorConstructor = this.getConstructor(this.codeGeneratorClass, Getopt2.class);
   }

   public CMPCodeGenerator getCodeGenerator() {
      return this.codeGeneratorConstructor != null ? (CMPCodeGenerator)this.invokeConstructor(this.codeGeneratorConstructor, this.options) : (CMPCodeGenerator)this.newInstance(this.codeGeneratorClassName);
   }

   private Constructor getConstructor(Class clazz, Class parameterType) {
      try {
         return clazz.getConstructor(parameterType);
      } catch (NoSuchMethodException var4) {
         return null;
      } catch (Exception var5) {
         throw new AssertionError("should not reach.", var5);
      }
   }

   private Object invokeConstructor(Constructor constructor, Object parameter) {
      try {
         return constructor.newInstance(parameter);
      } catch (Exception var4) {
         throw new AssertionError("Should not reach.", var4);
      }
   }

   private Object newInstance(String className) {
      try {
         return this.loadClass(className).newInstance();
      } catch (Exception var3) {
         throw new AssertionError("Could not create an instance of class '" + className + "': " + StackTraceUtilsClient.throwable2StackTrace(var3));
      }
   }

   private Class loadClass(String className) {
      try {
         return Class.forName(className);
      } catch (ClassNotFoundException var3) {
         throw new AssertionError("Could not load class '" + className + "': " + StackTraceUtilsClient.throwable2StackTrace(var3));
      }
   }

   private Map createPersistenceParameters(EntityBeanInfo ebi, File targetDirectory, Getopt2 opts) {
      Map params = new HashMap();
      NamingConvention nc = new NamingConvention(ebi.getBeanClassName(), ebi.getEJBName());
      CMPInfo cmpi = ebi.getCMPInfo();
      Boolean cacheBetweenTransactions = new Boolean(ebi.getCacheBetweenTransactions());
      params.put(new String("bean.cacheBetweenTransactions"), cacheBetweenTransactions);
      Boolean findersLoadBean = new Boolean(cmpi.findersLoadBean());
      params.put(new String("bean.findersLoadBean"), findersLoadBean);
      String packageName = new String(nc.getBeanPackageName());
      params.put(new String("codegen.packageName"), packageName);
      String beanClassName = new String(nc.getSimpleCmpBeanClassName(this.getIdentifier()));
      params.put(new String("codegen.beanClassName"), beanClassName);
      String targetFileName = new String(beanClassName + ".java");
      if (!packageName.trim().equals("")) {
         targetFileName = packageName.replace('.', File.separatorChar) + File.separator + targetFileName;
      }

      File targetFile = null;
      if (targetDirectory.isDirectory()) {
         targetFile = new File(targetDirectory, targetFileName);
      } else {
         targetFile = new File(targetFileName);
      }

      params.put(new String("codegen.targetFile"), targetFile);
      params.put(new String("codegen.opts"), opts != null ? opts.asCommandArray() : null);
      return params;
   }

   public CMPDeployer setupDeployer(EntityBeanInfo ebi, File targetDirectory, Getopt2 opts, VirtualJarFile jf) throws WLDeploymentException {
      this.sourceJarFile = jf;
      CMPDeployer deployer = this.getCmpDeployer();

      try {
         if (this.hasJarDeployment()) {
            deployer.setup(this.getJarDeployment());
         } else {
            deployer.setup((JarDeployment)null);
         }

         Map allBeanMap = ebi.getCMPInfo().getAllBeanMap();
         deployer.setCMPBeanDescriptor((CMPBeanDescriptor)allBeanMap.get(ebi.getEJBName()));
         if (ebi.getCMPInfo().uses20CMP()) {
            deployer.setBeanMap(ebi.getCMPInfo().getBeanMap());
            deployer.setRelationships(ebi.getCMPInfo().getRelationships());
            deployer.setDependentMap((Map)null);
         }

         Map persistenceParameters = this.createPersistenceParameters(ebi, targetDirectory, opts);
         deployer.setParameters(persistenceParameters);
         this.setTypeSpecificFile(ebi, deployer);
         return deployer;
      } catch (WLDeploymentException var8) {
         throw var8;
      } catch (Exception var9) {
         Loggable l = EJBLogger.logPersistenceTypeSetupErrorLoggable();
         throw new WLDeploymentException(l.getMessageText(), var9);
      }
   }

   private void setTypeSpecificFile(EntityBeanInfo ebi, CMPDeployer deployer) throws Exception {
      if (debug) {
         Debug.assertion(ebi != null);
         Debug.assertion(ebi.getCMPInfo() != null);
         Debug.assertion(deployer != null);
      }

      if (ebi.getPersistenceUseStorage() == null) {
         throw new RuntimeException("Couldn't find the CMP description for your Entity Bean.\nPlease make sure you specified a <persistence-type> tag in weblogic-ejb-jar.xml.");
      } else {
         String persistenceUseStorage = ebi.getPersistenceUseStorage();
         if (debug) {
            Debug.assertion(persistenceUseStorage != null);
         }

         String emsg;
         Loggable l;
         try {
            deployer.readTypeSpecificData(this.sourceJarFile, persistenceUseStorage);
            if (deployer instanceof Deployer) {
               ebi.setCategoryCmpField(((Deployer)deployer).getTypeSpecificData().getCategoryCmpField());
            }

         } catch (XMLParsingException var9) {
            emsg = var9.getMessage();
            if (emsg != null && !debug) {
               Throwable t = var9.getNestedException();
               if (t instanceof SAXParseException) {
                  int line = ((SAXParseException)t).getLineNumber();
                  Loggable l = EJBLogger.logPersistenceTypeSetupErrorWithFileNameAndLineNumberLoggable(emsg, persistenceUseStorage, line);
                  throw new WLDeploymentException(l.getMessageText(), var9);
               } else {
                  Loggable l = EJBLogger.logPersistenceTypeSetupErrorWithFileNameLoggable(emsg, persistenceUseStorage);
                  throw new WLDeploymentException(l.getMessageText(), var9);
               }
            } else {
               emsg = StackTraceUtilsClient.throwable2StackTrace(var9);
               l = EJBLogger.logPersistenceTypeSetupErrorWithFileNameLoggable(emsg, persistenceUseStorage);
               throw new WLDeploymentException(l.getMessageText(), var9);
            }
         } catch (EJBQLParsingException var10) {
            emsg = var10.getMessage();
            if (emsg == null || debug) {
               emsg = StackTraceUtilsClient.throwable2StackTrace(var10);
            }

            l = EJBLogger.logPersistenceTypeSetupEjbqlParsingErrorLoggable(emsg, persistenceUseStorage);
            throw new WLDeploymentException(l.getMessageText(), var10);
         } catch (Exception var11) {
            emsg = StackTraceUtilsClient.throwable2StackTrace(var11);
            l = EJBLogger.logPersistenceTypeSetupErrorWithFileNameLoggable(emsg, persistenceUseStorage);
            throw new WLDeploymentException(l.getMessageText(), var11);
         }
      }
   }

   public String toString() {
      StringBuilder str = new StringBuilder(300);
      str.append("PersistenceType: {");
      str.append("\n\tProvided by Vendor: " + this.vendor.getName());
      str.append("\n\ttypeIdentifier: " + this.typeIdentifier);
      str.append("\n\tversionNumber: " + this.typeVersion);
      str.append("\n\tweblogicVersion: " + this.weblogicVersion);
      str.append("\n\tcmpVersion: " + this.cmpVersion);
      str.append("\n\tpersistenceManager: " + this.persistenceManagerClassName);
      str.append("\n\texclusiveManagerClassName: " + this.exclusiveManagerClassName);
      str.append("\n\tdatabaseManagerClassName: " + this.databaseManagerClassName);
      str.append("\n\treadonlyManagerClassName: " + this.readonlyManagerClassName);
      str.append("\n\tjarDeploymentClassName: " + this.jarDeploymentClassName);
      str.append("\n\tcodeGeneratorClassName: " + this.codeGeneratorClassName);
      str.append("\n} end PersistenceType\n");
      return str.toString();
   }

   public boolean equals(Object other) {
      if (null == other) {
         return false;
      } else if (!(other instanceof PersistenceType)) {
         return false;
      } else {
         PersistenceType pt = (PersistenceType)((PersistenceType)other);
         if (!this.getVersion().equals(pt.getVersion())) {
            return false;
         } else {
            return this.getIdentifier().equals(pt.getIdentifier());
         }
      }
   }

   public int hashCode() {
      return this.typeVersion.hashCode() ^ this.typeIdentifier.hashCode();
   }
}
