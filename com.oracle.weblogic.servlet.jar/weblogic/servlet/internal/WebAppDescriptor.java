package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.CachingDescriptorLoader2;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.LibraryRefBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.HTTPLogger;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.jars.VirtualJarFile;

public final class WebAppDescriptor implements WebAppInternalParser {
   private static final String STANDARD_DD = "WEB-INF/web.xml";
   private static final String WEBLOGIC_DD = "WEB-INF/weblogic.xml";
   private MyWebAppDescriptor webAppDescriptor;
   private MyWlsWebAppDescriptor wlsWebAppDescriptor;
   private boolean hasWebDescriptorFile;
   private boolean validateSchema;

   public WebAppDescriptor(File altDD, File wlsDD, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.hasWebDescriptorFile = true;
      this.validateSchema = true;
      this.webAppDescriptor = new MyWebAppDescriptor(altDD, configDir, plan, moduleName);
      this.wlsWebAppDescriptor = new MyWlsWebAppDescriptor(wlsDD, configDir, plan, moduleName);
   }

   public WebAppDescriptor(File altDD, VirtualJarFile war, File configDir, DeploymentPlanBean plan, String moduleName) {
      this(altDD, war, configDir, plan, moduleName, (File)null);
   }

   public WebAppDescriptor(File altDD, VirtualJarFile war, File configDir, DeploymentPlanBean plan, String moduleName, File descriptorCacheDir) {
      this.hasWebDescriptorFile = true;
      this.validateSchema = true;
      this.webAppDescriptor = new MyWebAppDescriptor(altDD, configDir, plan, moduleName, descriptorCacheDir);
      this.wlsWebAppDescriptor = new MyWlsWebAppDescriptor(war, configDir, plan, moduleName, descriptorCacheDir);
   }

   public WebAppDescriptor(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.hasWebDescriptorFile = true;
      this.validateSchema = true;
      this.webAppDescriptor = new MyWebAppDescriptor(edm, gcl, configDir, plan, moduleName);
      this.wlsWebAppDescriptor = new MyWlsWebAppDescriptor(edm, gcl, configDir, plan, moduleName);
   }

   public WebAppDescriptor(DescriptorManager edm, GenericClassLoader gcl) {
      this.hasWebDescriptorFile = true;
      this.validateSchema = true;
      this.webAppDescriptor = new MyWebAppDescriptor(edm, gcl);
      this.wlsWebAppDescriptor = new MyWlsWebAppDescriptor(edm, gcl);
   }

   public WebAppDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName) {
      this((VirtualJarFile)vjar, (File)configDir, (DeploymentPlanBean)plan, (String)moduleName, (File)null);
   }

   public WebAppDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName, File descriptorCacheDir) {
      this.hasWebDescriptorFile = true;
      this.validateSchema = true;
      this.webAppDescriptor = new MyWebAppDescriptor(vjar, configDir, plan, moduleName, descriptorCacheDir);
      this.wlsWebAppDescriptor = new MyWlsWebAppDescriptor(vjar, configDir, plan, moduleName, descriptorCacheDir);
   }

   public WebAppDescriptor(File altDD) {
      this((File)altDD, (File)((File)null), (File)null, (DeploymentPlanBean)null, (String)null);
   }

   public WebAppDescriptor(VirtualJarFile vjar) {
      this(vjar, (File)null, (DeploymentPlanBean)null, (String)null);
   }

   public void setValidateSchema(boolean v) {
      this.validateSchema = v;
   }

   public void writeDescriptors(File root) throws IOException, XMLStreamException {
      DescriptorManager descMgr = new DescriptorManager();
      File f = null;
      DescriptorBean bean = (DescriptorBean)this.getWebAppBean();
      if (bean != null) {
         f = new File(root, this.webAppDescriptor.getDocumentURI());
         DescriptorUtils.writeDescriptor(descMgr, bean, f);
      }

      bean = (DescriptorBean)this.getWeblogicWebAppBean();
      if (bean != null) {
         f = new File(root, this.wlsWebAppDescriptor.getDocumentURI());
         DescriptorUtils.writeDescriptor(descMgr, bean, f);
      }

   }

   public WebAppBean getWebAppBean() throws IOException, XMLStreamException {
      if (!this.validateSchema) {
         this.webAppDescriptor.setValidate(this.validateSchema);
      }

      WebAppBean bean = (WebAppBean)this.webAppDescriptor.loadDescriptorBean();
      if (bean == null) {
         this.hasWebDescriptorFile = false;
         bean = createWebAppBean();
         this.webAppDescriptor.updateDescriptorWithBean((DescriptorBean)bean);
      }

      return bean;
   }

   public boolean hasWebDescriptorFile() {
      return this.hasWebDescriptorFile;
   }

   private boolean ignoreDeprecatedSharedLibrary(LibraryRefBean lib) {
      if ("jsf".equals(lib.getLibraryName()) && !"1.2".equals(lib.getSpecificationVersion())) {
         HTTPLogger.logIgnoredSharedLibrary("jsf", "2.0");
         return true;
      } else if ("jstl".equals(lib.getLibraryName())) {
         HTTPLogger.logIgnoredSharedLibrary("jstl", lib.getSpecificationVersion());
         return true;
      } else {
         return false;
      }
   }

   private void filterLibraryBeans(WeblogicWebAppBean wlWebAppBean) {
      LibraryRefBean[] libRefs = wlWebAppBean.getLibraryRefs();
      LibraryRefBean[] var3 = libRefs;
      int var4 = libRefs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         LibraryRefBean libRef = var3[var5];
         if (this.ignoreDeprecatedSharedLibrary(libRef)) {
            wlWebAppBean.destroyLibraryRef(libRef);
         }
      }

   }

   public WeblogicWebAppBean getWeblogicWebAppBean() throws IOException, XMLStreamException {
      if (this.wlsWebAppDescriptor == null) {
         return null;
      } else {
         if (!this.validateSchema) {
            this.wlsWebAppDescriptor.setValidate(this.validateSchema);
         }

         WeblogicWebAppBean wlWebAppBean = (WeblogicWebAppBean)this.wlsWebAppDescriptor.loadDescriptorBean();
         if (wlWebAppBean != null && wlWebAppBean.getLibraryRefs() != null) {
            this.filterLibraryBeans(wlWebAppBean);
         }

         return wlWebAppBean;
      }
   }

   public DescriptorBean mergeLibaryDescriptors(Source[] sources, String uri) throws IOException, XMLStreamException {
      if (uri == null) {
         return null;
      } else if (uri.equals("WEB-INF/web.xml")) {
         return this.webAppDescriptor.mergeDescriptors(sources);
      } else {
         return uri.equals("WEB-INF/weblogic.xml") ? this.wlsWebAppDescriptor.mergeDescriptors(sources) : null;
      }
   }

   public AbstractDescriptorLoader2 getWebAppDescriptorLoader() {
      return this.webAppDescriptor;
   }

   public AbstractDescriptorLoader2 getWlsWebAppDescriptorLoader() {
      return this.wlsWebAppDescriptor;
   }

   public static WebAppBean createWebAppBean() {
      WebAppBean bean = (WebAppBean)(new DescriptorManager()).createDescriptorRoot(WebAppBean.class).getRootBean();
      bean.setVersion("4.0");
      Descriptor descriptor = ((DescriptorBean)bean).getDescriptor();
      descriptor.setOriginalVersionInfo("4.0");
      return bean;
   }

   public static class MyWlsWebAppDescriptor extends CachingDescriptorLoader2 {
      public MyWlsWebAppDescriptor(File altDD) {
         this((File)altDD, (File)null, (DeploymentPlanBean)null, (String)null);
      }

      public MyWlsWebAppDescriptor(VirtualJarFile vjar) {
         this((VirtualJarFile)vjar, (File)null, (DeploymentPlanBean)null, (String)null);
      }

      public MyWlsWebAppDescriptor(DescriptorManager edm, GenericClassLoader gcl) {
         this((DescriptorManager)edm, (GenericClassLoader)gcl, (File)null, (DeploymentPlanBean)null, (String)null);
      }

      public MyWlsWebAppDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String moduleName) {
         super(altDD, configDir, plan, moduleName, "WEB-INF/weblogic.xml", (File)null);
      }

      public MyWlsWebAppDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String moduleName, String documentUri) {
         super(altDD, configDir, plan, moduleName, "WEB-INF/weblogic.xml", (File)null);
      }

      public MyWlsWebAppDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName) {
         this(vjar, configDir, plan, moduleName, (File)null);
      }

      public MyWlsWebAppDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName, File descriptorCacheDir) {
         super(vjar, configDir, plan, moduleName, "WEB-INF/weblogic.xml", descriptorCacheDir);
      }

      public MyWlsWebAppDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName, String descriptorUri) {
         super(vjar, configDir, plan, moduleName, "WEB-INF/weblogic.xml", (File)null);
      }

      public MyWlsWebAppDescriptor(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
         super(edm, gcl, configDir, plan, moduleName, "WEB-INF/weblogic.xml", (File)null);
      }

      protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
         return new WlsWebAppReader2(is, this);
      }

      void setValidate(boolean validate) {
         super.setValidateSchema(validate);
      }
   }

   public static class MyWebAppDescriptor extends CachingDescriptorLoader2 {
      public MyWebAppDescriptor(File altDD) {
         this((File)altDD, (File)null, (DeploymentPlanBean)null, (String)null);
      }

      public MyWebAppDescriptor(VirtualJarFile vjar) {
         this((VirtualJarFile)vjar, (File)null, (DeploymentPlanBean)null, (String)null);
      }

      public MyWebAppDescriptor(DescriptorManager edm, GenericClassLoader gcl) {
         this((DescriptorManager)edm, (GenericClassLoader)gcl, (File)null, (DeploymentPlanBean)null, (String)null);
      }

      public MyWebAppDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String moduleName) {
         this(altDD, configDir, plan, moduleName, (File)null);
      }

      public MyWebAppDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String moduleName, File descriptorCacheDir) {
         super(altDD, configDir, plan, moduleName, "WEB-INF/web.xml", descriptorCacheDir);
      }

      public MyWebAppDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String moduleName, String documentUri) {
         super(altDD, configDir, plan, moduleName, "WEB-INF/web.xml", (File)null);
      }

      public MyWebAppDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName) {
         this(vjar, configDir, plan, moduleName, (File)null);
      }

      public MyWebAppDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName, File descriptorCacheDir) {
         super(vjar, configDir, plan, moduleName, "WEB-INF/web.xml", descriptorCacheDir);
      }

      public MyWebAppDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName, String descriptorUri) {
         super(vjar, configDir, plan, moduleName, "WEB-INF/web.xml", (File)null);
      }

      public MyWebAppDescriptor(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
         super(edm, gcl, configDir, plan, moduleName, "WEB-INF/web.xml", (File)null);
      }

      protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
         return new WebAppReader2(is, this);
      }

      void setValidate(boolean validate) {
         super.setValidateSchema(validate);
      }
   }
}
