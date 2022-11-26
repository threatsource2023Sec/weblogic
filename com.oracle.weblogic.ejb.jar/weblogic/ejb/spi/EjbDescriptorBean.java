package weblogic.ejb.spi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.ejb.container.EJBLogger;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.EntityDescriptorBean;
import weblogic.j2ee.descriptor.wl.PersistenceBean;
import weblogic.j2ee.descriptor.wl.PersistenceUseBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean;
import weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBean;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.TopLevelDescriptorMBean;
import weblogic.utils.jars.RandomAccessJarFile;

public class EjbDescriptorBean implements TopLevelDescriptorMBean {
   private static final long serialVersionUID = 4598155809773582508L;
   private final boolean readOnly;
   private EjbJarBean ejbJar;
   private WeblogicEjbJarBean wlEjbJar;
   private final Set rdbms11Jars;
   private final Set rdbms20Jars;
   private String appName;
   private String uri;
   private final DescriptorManager descriptorManager;
   private boolean isEjb30;
   private boolean isVersionGreaterThan30;
   private DeploymentPlanBean plan;
   private File configDir;
   private boolean isWeblogicEjbJarSynthetic;
   private String destination;

   public EjbDescriptorBean() {
      this(false);
   }

   public EjbDescriptorBean(boolean readOnly) {
      this.rdbms11Jars = new HashSet();
      this.rdbms20Jars = new HashSet();
      this.isEjb30 = false;
      this.isVersionGreaterThan30 = false;
      this.isWeblogicEjbJarSynthetic = false;
      this.readOnly = readOnly;
      this.descriptorManager = (DescriptorManager)(readOnly ? new DescriptorManager() : new EditableDescriptorManager());
   }

   public boolean isReadOnly() {
      return this.readOnly;
   }

   public EjbJarBean getEjbJarBean() {
      return this.ejbJar;
   }

   public void setEjbJarBean(EjbJarBean ejbJar) {
      this.ejbJar = ejbJar;
      Descriptor desc = ((DescriptorBean)ejbJar).getDescriptor();
      if (desc.getOriginalVersionInfo() == null) {
         desc.setOriginalVersionInfo(ejbJar.getVersion());
      }

      String ver = desc.getOriginalVersionInfo();
      this.isEjb30 = ver.equals("3.0") || ver.equals("3.1") || ver.equals("3.2");
      this.isVersionGreaterThan30 = ver.equals("3.1") || ver.equals("3.2");
   }

   public EjbJarBean createEjbJarBean() {
      EjbJarBean ejbJar = (EjbJarBean)this.descriptorManager.createDescriptorRoot(EjbJarBean.class).getRootBean();
      this.setEjbJarBean(ejbJar);
      return ejbJar;
   }

   public WeblogicEjbJarBean getWeblogicEjbJarBean() {
      return this.wlEjbJar;
   }

   public void setWeblogicEjbJarBean(WeblogicEjbJarBean wlEjbJar) {
      this.wlEjbJar = wlEjbJar;
   }

   public WeblogicEjbJarBean createWeblogicEjbJarBean() {
      return this.createWeblogicEjbJarBean((String)null);
   }

   public WeblogicEjbJarBean createWeblogicEjbJarBean(String encoding) {
      WeblogicEjbJarBean wlJar = (WeblogicEjbJarBean)this.descriptorManager.createDescriptorRoot(WeblogicEjbJarBean.class, encoding).getRootBean();
      this.setWeblogicEjbJarBean(wlJar);
      return wlJar;
   }

   public boolean verSupportsAnnotatedEjbs() {
      return this.isEjb30();
   }

   public boolean verSupportsBeanRedeploy() {
      return !this.isEjb30();
   }

   public void setEjb30(boolean ejb30) {
      this.isEjb30 = ejb30;
   }

   public boolean isEjb30() {
      this.ensureEjbJarBeanSet();
      return this.isEjb30;
   }

   public boolean isVersionGreaterThan30() {
      this.ensureEjbJarBeanSet();
      return this.isVersionGreaterThan30;
   }

   private void ensureEjbJarBeanSet() {
      if (this.ejbJar == null) {
         throw new IllegalStateException(EJBLogger.logEjBJarBeanNotSetLoggable().getMessage());
      }
   }

   public WeblogicRdbmsJarBean[] getWeblogicRdbmsJarBeans() {
      return (WeblogicRdbmsJarBean[])this.rdbms20Jars.toArray(new WeblogicRdbmsJarBean[0]);
   }

   public void setWeblogicRdbmsJarBeans(WeblogicRdbmsJarBean[] cmpDescs) {
      this.rdbms20Jars.clear();
      if (cmpDescs != null) {
         WeblogicRdbmsJarBean[] var2 = cmpDescs;
         int var3 = cmpDescs.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WeblogicRdbmsJarBean cmpDesc = var2[var4];
            this.rdbms20Jars.add(cmpDesc);
         }
      }

   }

   public void addWeblogicRdbmsJarBean(WeblogicRdbmsJarBean cmpDesc) {
      this.rdbms20Jars.add(cmpDesc);
   }

   public void removeWeblogicRdbmsJarBean(WeblogicRdbmsJarBean cmpDesc) {
      this.rdbms20Jars.remove(cmpDesc);
   }

   public WeblogicRdbmsJarBean createWeblogicRdbmsJarBean() {
      return this.createWeblogicRdbmsJarBean((String)null);
   }

   public WeblogicRdbmsJarBean createWeblogicRdbmsJarBean(String encoding) {
      WeblogicRdbmsJarBean rdbmsJar = (WeblogicRdbmsJarBean)this.descriptorManager.createDescriptorRoot(WeblogicRdbmsJarBean.class, encoding).getRootBean();
      this.addWeblogicRdbmsJarBean(rdbmsJar);
      return rdbmsJar;
   }

   public WeblogicRdbmsJarBean getWeblogicRdbmsJarBean(String descriptorName) {
      Object o = this.getFileNameToRdbmsDescriptorMap().get(descriptorName);
      return o instanceof WeblogicRdbmsJarBean ? (WeblogicRdbmsJarBean)o : null;
   }

   public weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean[] getWeblogicRdbms11JarBeans() {
      return (weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean[])this.rdbms11Jars.toArray(new weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean[0]);
   }

   public void setWeblogicRdbms11JarBeans(weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean[] cmpDescs) {
      this.rdbms11Jars.clear();
      if (cmpDescs != null) {
         weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean[] var2 = cmpDescs;
         int var3 = cmpDescs.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean cmpDesc = var2[var4];
            this.rdbms11Jars.add(cmpDesc);
         }
      }

   }

   public void addWeblogicRdbms11JarBean(weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean cmpDesc) {
      this.rdbms11Jars.add(cmpDesc);
   }

   public void removeWeblogicRdbms11JarBean(weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean cmpDesc) {
      this.rdbms11Jars.remove(cmpDesc);
   }

   public weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean createWeblogicRdbms11JarBean() {
      return this.createWeblogicRdbms11JarBean((String)null);
   }

   public weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean createWeblogicRdbms11JarBean(String encoding) {
      weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean rdbmsJar = (weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean)this.descriptorManager.createDescriptorRoot(weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean.class, encoding).getRootBean();
      this.addWeblogicRdbms11JarBean(rdbmsJar);
      return rdbmsJar;
   }

   public String getParsingErrorMessage() {
      return null;
   }

   public void setParsingErrorMessage(String errorMessage) {
   }

   public String getAppName() {
      return this.appName;
   }

   public void setAppName(String name) {
      this.appName = name;
   }

   public String getUri() {
      return this.uri;
   }

   public void setUri(String uri) {
      this.uri = uri;
   }

   public DeploymentPlanBean getDeploymentPlan() {
      return this.plan;
   }

   public void setDeploymentPlan(DeploymentPlanBean plan) {
      this.plan = plan;
   }

   public File getConfigDirectory() {
      return this.configDir;
   }

   public void setConfigDirectory(File configDir) {
      this.configDir = configDir;
   }

   public String getName() {
      return null;
   }

   public void setName(String n) {
   }

   public String toXML(int indentLevel) {
      return "";
   }

   public void register() {
   }

   public void unregister() {
   }

   public void validate() throws DescriptorValidationException {
   }

   public void usePersistenceDestination(String dir) {
      this.destination = dir;
   }

   public void persist() throws IOException {
      if (this.destination == null) {
         throw new IllegalStateException("No persistentDestination set!");
      } else {
         File f = new File(this.destination);
         if (f.isDirectory()) {
            this.persistToDirectory(f, "META-INF");
         } else {
            this.persistToJarFile(f);
         }

      }
   }

   private void persistDescriptor(File dir, String fileName, Object descriptor) throws IOException {
      if (!((DescriptorBean)descriptor).isEditable()) {
         throw new IOException("Error: " + fileName + " is not editable");
      } else {
         File file = new File(dir, fileName);
         file.getParentFile().mkdirs();
         OutputStream out = null;

         try {
            out = new FileOutputStream(file);
            DescriptorBean bean = (DescriptorBean)descriptor;
            bean.getDescriptor().toXML(out);
         } finally {
            if (out != null) {
               out.close();
            }

         }

      }
   }

   public void persistToDirectory(File dir, String ejbDescsRoot) throws IOException {
      this.persistDescriptor((File)dir, ejbDescsRoot + "/ejb-jar.xml", this.ejbJar);
      if (this.wlEjbJar != null) {
         this.persistDescriptor((File)dir, ejbDescsRoot + "/weblogic-ejb-jar.xml", this.wlEjbJar);
      }

      Iterator var3 = this.getFileNameToRdbmsDescriptorMap().entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry e = (Map.Entry)var3.next();
         this.persistDescriptor(dir, (String)e.getKey(), e.getValue());
      }

   }

   private void persistDescriptor(RandomAccessJarFile raj, String fileName, Object descriptor) throws IOException {
      OutputStream out = null;

      try {
         DescriptorBean bean = (DescriptorBean)descriptor;
         if (bean.isEditable()) {
            out = raj.writeEntry(fileName, true);
            bean.getDescriptor().toXML(out);
         }
      } finally {
         if (out != null) {
            out.close();
         }

      }

   }

   private void persistToJarFile(File jarFile) throws IOException {
      String tempDir = ".";
      RandomAccessJarFile raj = new RandomAccessJarFile(new File(tempDir), jarFile);

      try {
         this.persistDescriptor((RandomAccessJarFile)raj, "META-INF/ejb-jar.xml", this.ejbJar);
         if (this.wlEjbJar != null) {
            this.persistDescriptor((RandomAccessJarFile)raj, "META-INF/weblogic-ejb-jar.xml", this.wlEjbJar);
         }

         Iterator var4 = this.getFileNameToRdbmsDescriptorMap().entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry e = (Map.Entry)var4.next();
            this.persistDescriptor(raj, (String)e.getKey(), e.getValue());
         }
      } finally {
         raj.close();
      }

   }

   public Map getFileNameToRdbmsDescriptorMap() {
      if (this.wlEjbJar == null) {
         return new HashMap();
      } else {
         Map ejbNameToFileNameMap = new HashMap();
         WeblogicEnterpriseBeanBean[] var2 = this.wlEjbJar.getWeblogicEnterpriseBeans();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WeblogicEnterpriseBeanBean wlBean = var2[var4];
            if (wlBean.isEntityDescriptorSet()) {
               EntityDescriptorBean ed = wlBean.getEntityDescriptor();
               if (ed.isPersistenceSet()) {
                  PersistenceBean persist = ed.getPersistence();
                  if (persist.isPersistenceUseSet()) {
                     PersistenceUseBean use = persist.getPersistenceUse();
                     ejbNameToFileNameMap.put(wlBean.getEjbName(), use.getTypeStorage());
                  }
               }
            }
         }

         Map fileNameToDescriptorMap = new HashMap();
         Iterator var10 = this.rdbms11Jars.iterator();

         while(var10.hasNext()) {
            weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean wlJar = (weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean)var10.next();
            WeblogicRdbmsBeanBean[] beans = wlJar.getWeblogicRdbmsBeans();
            if (beans.length != 0) {
               fileNameToDescriptorMap.put(ejbNameToFileNameMap.get(beans[0].getEjbName()), wlJar);
            }
         }

         var10 = this.rdbms20Jars.iterator();

         while(var10.hasNext()) {
            WeblogicRdbmsJarBean wlJar = (WeblogicRdbmsJarBean)var10.next();
            weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBean[] beans = wlJar.getWeblogicRdbmsBeans();
            if (beans.length != 0) {
               fileNameToDescriptorMap.put(ejbNameToFileNameMap.get(beans[0].getEjbName()), wlJar);
            }
         }

         return fileNameToDescriptorMap;
      }
   }

   public void persist(Properties changelist) throws IOException {
   }

   public boolean isWeblogicEjbJarSynthetic() {
      return this.isWeblogicEjbJarSynthetic;
   }

   public void markWeblogicEjbJarSynthetic() {
      this.isWeblogicEjbJarSynthetic = true;
   }
}
