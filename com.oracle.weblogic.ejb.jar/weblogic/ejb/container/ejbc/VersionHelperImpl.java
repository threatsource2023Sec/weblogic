package weblogic.ejb.container.ejbc;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import weblogic.version;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.ejbc.codegen.MethodSignature;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.CMPInfo;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.VersionHelper;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.InterceptorsBean;
import weblogic.j2ee.descriptor.RelationshipsBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean;
import weblogic.utils.Getopt2;
import weblogic.utils.jars.VirtualJarFile;

public final class VersionHelperImpl implements VersionHelper {
   private static final DebugLogger debugLogger;
   private static final String CHECKSUM_FILE = "_WL_GENERATED";
   private static final String VERSION_TAG = "WLS_RELEASE_BUILD_VERSION_49";
   private static final String[] SAVED_OPTIONS;
   private final DeploymentInfo di;
   private final Map classesToBeans;
   private final Properties currentJarHash;
   private final Getopt2 opts;

   public VersionHelperImpl(DeploymentInfo di, Getopt2 opts) throws ClassNotFoundException {
      this.di = di;
      this.opts = opts;
      this.classesToBeans = new HashMap();
      this.currentJarHash = this.makeFileHash();
   }

   public boolean needsRecompile(String className, ClassLoader cl) throws ClassNotFoundException {
      if (debugLogger.isDebugEnabled()) {
         debug("calculating hash for: " + className);
      }

      String oldHash = this.currentJarHash.getProperty(className);
      if (oldHash == null) {
         if (debugLogger.isDebugEnabled()) {
            debug("Hmm.  The old hash for class " + className + " was null");
         }

         return true;
      } else {
         boolean isEntity = ((BeanInfo)((List)this.classesToBeans.get(className)).get(0)).isEntityBean();
         long newHash = this.computeCRC(className, cl, isEntity, false);
         if (debugLogger.isDebugEnabled()) {
            debug("new hash: " + newHash + " oldHash: " + oldHash);
         }

         return !String.valueOf(newHash).equals(oldHash);
      }
   }

   public Collection needsRecompile(VirtualJarFile jf, boolean ignoreSavedOptions) {
      Properties oldHash = this.getChecksum(jf, ignoreSavedOptions);
      return this.needsRecompile(oldHash);
   }

   public Collection needsRecompile(File dir, boolean ignoreSavedOptions) {
      Properties oldHash = this.getChecksum(dir, ignoreSavedOptions);
      return this.needsRecompile(oldHash);
   }

   private Collection needsRecompile(Properties oldHash) {
      assert this.currentJarHash != null;

      if (oldHash == null) {
         if (debugLogger.isDebugEnabled()) {
            debug("Recompiling because no previous hashes found");
         }

         return this.di.getBeanInfos();
      } else if (this.currentJarHash.size() != oldHash.size()) {
         if (debugLogger.isDebugEnabled()) {
            debug("Recompiling because number of hashes different");
            debug("current size: " + this.currentJarHash.size() + " old size: " + oldHash.size());
            debug("Dumping hashes:");
            debug("CurrentHashes:");
            this.dumpHashes(this.currentJarHash);
            debug("OldHashes:");
            this.dumpHashes(oldHash);
         }

         return this.di.getBeanInfos();
      } else {
         Collection values = new HashSet();
         Enumeration en = this.currentJarHash.propertyNames();

         while(true) {
            String propName;
            String curValue;
            String oldValue;
            do {
               if (!en.hasMoreElements()) {
                  if (debugLogger.isDebugEnabled() && values.isEmpty()) {
                     debug("Need not recompile EJB component " + this.di.getModuleId());
                  }

                  return values;
               }

               propName = (String)en.nextElement();
               curValue = this.currentJarHash.getProperty(propName);
               oldValue = oldHash.getProperty(propName);
            } while(curValue.equals(oldValue));

            if (propName.endsWith(".xml") || "WLS_RELEASE_BUILD_VERSION_49".equals(propName)) {
               if (debugLogger.isDebugEnabled()) {
                  debug("Recompiling all due to different hash for " + propName);
               }

               return this.di.getBeanInfos();
            }

            if (!this.classesToBeans.containsKey(propName)) {
               return this.di.getBeanInfos();
            }

            List li = (List)this.classesToBeans.get(propName);
            if (debugLogger.isDebugEnabled()) {
               StringBuilder beans = new StringBuilder();
               Iterator var9 = li.iterator();

               while(var9.hasNext()) {
                  BeanInfo bi = (BeanInfo)var9.next();
                  beans.append(bi.getEJBName()).append(" ");
               }

               debug("Recompiling the following due to different hash for " + propName + ": " + beans);
            }

            values.addAll(li);
         }
      }
   }

   public DeploymentInfo getDeploymentInfo() {
      return this.di;
   }

   private void removeCompilerOptions(Properties props) {
      String[] var2 = SAVED_OPTIONS;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String s = var2[var4];
         props.remove(s);
      }

   }

   private Properties makeFileHash() throws ClassNotFoundException {
      Properties p = new Properties();
      p.setProperty("WLS_RELEASE_BUILD_VERSION_49", version.getReleaseBuildVersion());
      HashSet nonRMIRemoteRbis = new HashSet();
      Collection beanInfos = this.di.getBeanInfos();
      Iterator var4 = beanInfos.iterator();

      while(true) {
         while(true) {
            BeanInfo bi;
            do {
               if (!var4.hasNext()) {
                  var4 = this.classesToBeans.entrySet().iterator();

                  while(var4.hasNext()) {
                     Map.Entry e = (Map.Entry)var4.next();
                     String className = (String)e.getKey();
                     BeanInfo bi = (BeanInfo)((List)e.getValue()).get(0);
                     long crc = this.computeCRC(className, bi.getClassLoader(), bi.isEntityBean(), nonRMIRemoteRbis.contains(className));
                     p.setProperty(className, String.valueOf(crc));
                  }

                  this.addHashForDDs(p);
                  String[] var10 = SAVED_OPTIONS;
                  int var12 = var10.length;

                  for(int var14 = 0; var14 < var12; ++var14) {
                     String s = var10[var14];
                     this.saveOptionIfPresent(s, p);
                  }

                  return p;
               }

               bi = (BeanInfo)var4.next();
               this.addBeanDependencyToClass(bi, bi.getBeanClassName());
            } while(!(bi instanceof ClientDrivenBeanInfo));

            ClientDrivenBeanInfo cdbi = (ClientDrivenBeanInfo)bi;
            if (cdbi.hasDeclaredRemoteHome()) {
               this.addBeanDependencyToClass(bi, cdbi.getRemoteInterfaceName());
               this.addBeanDependencyToClass(bi, cdbi.getHomeInterfaceName());
            }

            if (cdbi.hasDeclaredLocalHome()) {
               this.addBeanDependencyToClass(bi, cdbi.getLocalInterfaceName());
               this.addBeanDependencyToClass(bi, cdbi.getLocalHomeInterfaceName());
            }

            if (cdbi instanceof SessionBeanInfo) {
               SessionBeanInfo sbi = (SessionBeanInfo)cdbi;

               Class iface;
               Iterator var18;
               for(var18 = sbi.getBusinessRemotes().iterator(); var18.hasNext(); this.addBeanDependencyToClass(bi, iface.getName())) {
                  iface = (Class)var18.next();
                  if (!Remote.class.isAssignableFrom(iface)) {
                     nonRMIRemoteRbis.add(iface.getName());
                  }
               }

               var18 = sbi.getBusinessLocals().iterator();

               while(var18.hasNext()) {
                  iface = (Class)var18.next();
                  this.addBeanDependencyToClass(bi, iface.getName());
               }

               if (sbi.hasWebserviceClientView() && sbi.getServiceEndpointName() != null) {
                  this.addBeanDependencyToClass(bi, sbi.getServiceEndpointName());
               }
            } else if (cdbi instanceof EntityBeanInfo) {
               EntityBeanInfo ebi = (EntityBeanInfo)cdbi;
               CMPInfo cmpi = ebi.getCMPInfo();
               if (cmpi != null && !ebi.isUnknownPrimaryKey() && cmpi.getCMPrimaryKeyFieldName() == null) {
                  this.addBeanDependencyToClass(bi, ebi.getPrimaryKeyClassName());
               }
            }
         }
      }
   }

   private void addHashForDDs(Properties p) throws ClassNotFoundException {
      EjbDescriptorBean desc = this.di.getEjbDescriptorBean();
      String version = desc.getEjbJarBean().getVersion();
      if (version != null) {
         p.setProperty("version", version);
      }

      String ejbClientJar = desc.getEjbJarBean().getEjbClientJar();
      if (ejbClientJar != null) {
         p.setProperty("ejb-client-jar", ejbClientJar);
      }

      EnterpriseBeansBean ebb = desc.getEjbJarBean().getEnterpriseBeans();
      if (ebb != null) {
         p.setProperty("enterprise-beans", ((AbstractDescriptorBean)ebb).getHashValue());
      }

      RelationshipsBean rels = desc.getEjbJarBean().getRelationships();
      if (rels != null) {
         p.setProperty("relationships", ((AbstractDescriptorBean)rels).getHashValue());
      }

      InterceptorsBean icptrsBean = desc.getEjbJarBean().getInterceptors();
      if (icptrsBean != null && icptrsBean.getInterceptors() != null) {
         p.setProperty("interceptors(non-serializable)", this.nonSerIcptrs(icptrsBean));
      }

      p.setProperty("weblogic-ejb-jar.xml", ((AbstractDescriptorBean)desc.getWeblogicEjbJarBean()).getHashValue());
      WeblogicRdbmsJarBean[] wrjbs = desc.getWeblogicRdbmsJarBeans();
      String[] hashValues = new String[wrjbs.length];

      int i;
      for(i = 0; i < wrjbs.length; ++i) {
         hashValues[i] = ((AbstractDescriptorBean)wrjbs[i]).getHashValue();
      }

      Arrays.sort(hashValues);

      for(i = 0; i < wrjbs.length; ++i) {
         p.setProperty("weblogic-cmp-rdbms-jar.xml" + i, hashValues[i]);
      }

   }

   private String nonSerIcptrs(InterceptorsBean icptrsBean) throws ClassNotFoundException {
      StringBuilder val = new StringBuilder();
      Set set = new TreeSet();
      ClassLoader cl = this.di.getModuleClassLoader();
      InterceptorBean[] var5 = icptrsBean.getInterceptors();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         InterceptorBean ib = var5[var7];
         String clsName = ib.getInterceptorClass();
         Class c = cl.loadClass(clsName);
         if (!Serializable.class.isAssignableFrom(c)) {
            set.add(clsName);
         }
      }

      Iterator var11 = set.iterator();

      while(var11.hasNext()) {
         String s = (String)var11.next();
         val.append(s + " ");
      }

      return val.toString();
   }

   private void addBeanDependencyToClass(BeanInfo bi, String className) {
      List li = (List)this.classesToBeans.get(className);
      if (li == null) {
         List li = new LinkedList();
         li.add(bi);
         this.classesToBeans.put(className, li);
      } else {
         li.add(bi);
      }

   }

   private Properties getChecksum(VirtualJarFile jf, boolean filterSavedOptions) {
      ZipEntry propFile = jf.getEntry("_WL_GENERATED");
      if (propFile == null) {
         return null;
      } else {
         InputStream is = null;

         Properties var6;
         try {
            Properties p = new Properties();
            is = jf.getInputStream(propFile);
            p.load(is);
            if (filterSavedOptions) {
               this.removeCompilerOptions(p);
            }

            var6 = p;
            return var6;
         } catch (IOException var10) {
            EJBLogger.logExceptionLoadingTimestamp(var10);
            var6 = null;
         } finally {
            closeQuietly(is);
         }

         return var6;
      }
   }

   public boolean hasChecksum(File dir) {
      File propFile = new File(dir, "_WL_GENERATED");
      return propFile.exists();
   }

   private Properties getChecksum(File dir, boolean filterSavedOptions) {
      File propFile = new File(dir, "_WL_GENERATED");
      if (!propFile.exists()) {
         return null;
      } else {
         FileInputStream fis = null;

         Properties var6;
         try {
            Properties p = new Properties();
            fis = new FileInputStream(propFile);
            p.load(fis);
            if (filterSavedOptions) {
               this.removeCompilerOptions(p);
            }

            var6 = p;
            return var6;
         } catch (IOException var10) {
            EJBLogger.logExceptionLoadingTimestamp(var10);
            var6 = null;
         } finally {
            closeQuietly(fis);
         }

         return var6;
      }
   }

   private static void closeQuietly(Closeable c) {
      try {
         if (c != null) {
            c.close();
         }
      } catch (IOException var2) {
      }

   }

   public void writeChecksum(File dir) {
      FileOutputStream fos = null;

      try {
         File verFile = new File(dir, "_WL_GENERATED");
         if (verFile.exists()) {
            verFile.delete();
         }

         fos = new FileOutputStream(verFile);
         this.currentJarHash.store(fos, (String)null);
      } catch (Exception var7) {
         EJBLogger.logErrorSavingTimestamps(var7);
      } finally {
         closeQuietly(fos);
      }

   }

   private long computeCRC(String className, ClassLoader cl, boolean isEntity, boolean useToGenericString) throws ClassNotFoundException {
      CRC32 crc = new CRC32();
      Class clazz = cl.loadClass(className);
      List vals = new ArrayList();
      Method[] var8 = clazz.getMethods();
      int var9 = var8.length;

      int var10;
      for(var10 = 0; var10 < var9; ++var10) {
         Method m = var8[var10];
         if (m.getDeclaringClass() != Object.class) {
            String sig = null;
            if (isEntity) {
               sig = (new MethodSignature(m, clazz)).toString(false);
            } else {
               sig = useToGenericString ? m.toGenericString() : m.toString();
            }

            vals.add(sig);
            if (debugLogger.isDebugEnabled()) {
               debug("Signature" + sig);
            }
         }
      }

      Collections.sort(vals);
      Iterator var13 = vals.iterator();

      String s;
      while(var13.hasNext()) {
         s = (String)var13.next();
         crc.update(s.getBytes());
      }

      vals.clear();
      Field[] var14 = clazz.getFields();
      var9 = var14.length;

      for(var10 = 0; var10 < var9; ++var10) {
         Field field = var14[var10];
         if (field.getDeclaringClass() != Object.class) {
            vals.add(useToGenericString ? field.toGenericString() : field.toString());
         }
      }

      Collections.sort(vals);
      var13 = vals.iterator();

      while(var13.hasNext()) {
         s = (String)var13.next();
         crc.update(s.getBytes());
      }

      return crc.getValue();
   }

   private void saveOptionIfPresent(String option, Properties props) {
      if (this.isOptionPresent(option)) {
         props.setProperty(option, "true");
      }

   }

   private boolean isOptionPresent(String option) {
      return this.opts != null && this.opts.hasOption(option);
   }

   private void dumpHashes(Properties props) {
      Enumeration en = props.propertyNames();

      while(en.hasMoreElements()) {
         debug("Property name: " + en.nextElement());
      }

   }

   private static void debug(String s) {
      debugLogger.debug("[VersionHelper] " + s);
   }

   static {
      debugLogger = EJBDebugService.compilationLogger;
      SAVED_OPTIONS = new String[]{"keepgenerated", "g"};
   }
}
