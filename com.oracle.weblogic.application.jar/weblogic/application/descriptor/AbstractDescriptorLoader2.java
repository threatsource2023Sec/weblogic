package weblogic.application.descriptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ResourceDeploymentPlanBean;
import weblogic.management.provider.internal.DescriptorManagerHelper;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.jars.VirtualJarFile;

public class AbstractDescriptorLoader2 {
   protected boolean debug;
   private DescriptorManager dm;
   private DescriptorBean rootBean;
   protected BasicMunger2 munger;
   private File altDD;
   private VirtualJarFile vjar;
   private GenericClassLoader gcl;
   private DeploymentPlanBean plan;
   private ResourceDeploymentPlanBean resourcePlan;
   private File configDir;
   private String moduleName;
   private String documentURI;
   private String resourceName;
   private String namespaceURI;
   private Map elementNameChanges;
   private InputStream inputStream;
   protected String versionInfo;
   private static boolean schemaValidationEnabled = getBooleanProperty("weblogic.descriptor.schemaValidationEnabled", true);
   private boolean validateSchema;
   private List errorHolder;

   private static boolean getBooleanProperty(String prop, boolean _default) {
      String value = System.getProperty(prop);
      return value != null ? Boolean.parseBoolean(value) : _default;
   }

   protected AbstractDescriptorLoader2() {
      this.debug = Debug.getCategory("weblogic.descriptor.loader").isEnabled();
      this.elementNameChanges = Collections.EMPTY_MAP;
      this.validateSchema = schemaValidationEnabled;
      this.errorHolder = null;
   }

   public AbstractDescriptorLoader2(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName, String documentURI) {
      this();
      this.vjar = vjar;
      this.configDir = configDir;
      this.plan = plan;
      this.moduleName = moduleName;
      this.documentURI = documentURI;
   }

   public AbstractDescriptorLoader2(File altDD, File configDir, DeploymentPlanBean plan, String moduleName, String documentURI) {
      this();
      this.altDD = altDD;
      this.configDir = configDir;
      this.plan = plan;
      this.moduleName = moduleName;
      this.documentURI = documentURI;
   }

   public AbstractDescriptorLoader2(GenericClassLoader gcl, String documentURI) {
      this();
      this.gcl = gcl;
      this.documentURI = documentURI;
   }

   public AbstractDescriptorLoader2(GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName, String documentURI) {
      this();
      this.gcl = gcl;
      this.configDir = configDir;
      this.plan = plan;
      this.moduleName = moduleName;
      this.documentURI = documentURI;
   }

   public AbstractDescriptorLoader2(DescriptorManager dm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName, String documentURI) {
      this();
      this.gcl = gcl;
      this.dm = dm;
      this.plan = plan;
      this.moduleName = moduleName;
      this.documentURI = documentURI;
   }

   public AbstractDescriptorLoader2(DescriptorManager dm, GenericClassLoader gcl, String documentURI) {
      this();
      this.gcl = gcl;
      this.dm = dm;
      this.documentURI = documentURI;
   }

   public AbstractDescriptorLoader2(VirtualJarFile vjar, String documentURI) {
      this();
      this.vjar = vjar;
      this.documentURI = documentURI;
   }

   public AbstractDescriptorLoader2(File altDD, String documentURI) {
      this();
      this.altDD = altDD;
      this.documentURI = documentURI;
   }

   public AbstractDescriptorLoader2(InputStream is) {
      this(is, (DescriptorManager)null, (List)null, true);
   }

   public AbstractDescriptorLoader2(InputStream is, DescriptorManager dm, List errorHolder, boolean validate) {
      this.debug = Debug.getCategory("weblogic.descriptor.loader").isEnabled();
      this.elementNameChanges = Collections.EMPTY_MAP;
      this.validateSchema = schemaValidationEnabled;
      this.errorHolder = null;
      this.inputStream = is;
      this.dm = dm;
      this.errorHolder = errorHolder;
      this.validateSchema = validate;
   }

   public AbstractDescriptorLoader2(InputStream is, String moduleName, String uri) {
      this(is, (DescriptorManager)null, (List)null, true);
      this.moduleName = moduleName;
      this.documentURI = uri;
   }

   public AbstractDescriptorLoader2(File descriptorFile, ResourceDeploymentPlanBean resourcePlan, String resourceName) {
      this();
      this.altDD = descriptorFile;
      this.resourcePlan = resourcePlan;
      this.resourceName = resourceName;
   }

   public DeploymentPlanBean getDeploymentPlan() {
      return this.plan;
   }

   public ResourceDeploymentPlanBean getResourceDeploymentPlan() {
      return this.resourcePlan;
   }

   public String getResourceName() {
      return this.resourceName;
   }

   public File getConfigDir() {
      return this.configDir;
   }

   public String getModuleType() {
      return "";
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getAbsolutePath() {
      if (this.altDD == null) {
         if (this.vjar == null && this.gcl == null) {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            return cl.toString() + "/" + this.getDocumentURI();
         } else {
            return this.vjar == null ? this.gcl.toString() + "/" + this.getDocumentURI() : this.vjar.getName() + "/" + this.getDocumentURI();
         }
      } else {
         return this.altDD.getAbsolutePath();
      }
   }

   public String getDocumentURI() {
      return this.documentURI;
   }

   public String getNamespaceURI() {
      return this.namespaceURI;
   }

   public void setNamespaceURI(String namespaceURI) {
      this.namespaceURI = namespaceURI;
   }

   public Map getElementNameChanges() {
      return this.elementNameChanges;
   }

   public void setElementNameChanges(Map elementNameChanges) {
      this.elementNameChanges = elementNameChanges;
   }

   private File getPlanFile() {
      if (this.getDeploymentPlan() != null && this.getConfigDir() != null) {
         ModuleDescriptorBean md = this.getDeploymentPlan().findModuleDescriptor(this.getModuleName(), this.getDocumentURI());
         if (md == null) {
            return null;
         } else {
            File config;
            if (this.getDeploymentPlan().rootModule(this.getModuleName())) {
               config = this.getConfigDir();
            } else {
               config = new File(this.getConfigDir(), this.getModuleName());
            }

            return new File(config, md.getUri());
         }
      } else {
         return null;
      }
   }

   protected DescriptorManager getDescriptorManager() {
      if (this.dm == null) {
         this.dm = AbstractDescriptorLoader2.READONLY_SINGLETON.instance;
      }

      return this.dm;
   }

   public InputStream getInputStream() throws IOException {
      if (this.inputStream != null) {
         return this.inputStream;
      } else if (this.altDD == null) {
         if (this.vjar == null && this.gcl == null) {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            return cl.getResourceAsStream(this.getDocumentURI());
         } else if (this.vjar == null) {
            return this.gcl.getResourceAsStream(this.getDocumentURI());
         } else {
            ZipEntry ze = this.vjar.getEntry(this.getDocumentURI());
            return ze == null ? null : this.vjar.getInputStream(ze);
         }
      } else {
         return new FileInputStream(this.altDD);
      }
   }

   public void toXML(PrintStream out) throws IOException, XMLStreamException {
      this.getMunger().toXML(out);
   }

   protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
      return new BasicMunger2(is, this);
   }

   protected DescriptorBean createDescriptorBean(InputStream is) throws IOException, XMLStreamException {
      DescriptorBean var2;
      try {
         this.munger = (BasicMunger2)this.createXMLStreamReader(is);
         this.versionInfo = this.munger.getVersionInfo();
         if (!this.munger.hasDTD() && !(this.munger instanceof VersionMunger)) {
            var2 = this.getDescriptorBeanFromReader(this.munger);
            return var2;
         }

         var2 = this.getDescriptorBeanFromReader(this.munger.getPlaybackReader());
      } finally {
         if (this.getMunger() != null) {
            this.getMunger().close();
         }

      }

      return var2;
   }

   protected void setValidateSchema(boolean validateSchema) {
      this.validateSchema = validateSchema;
   }

   InputStream getInputStreamFromPlan() throws IOException {
      File dd = this.getPlanFile();
      if (dd == null) {
         return null;
      } else {
         return dd.exists() && dd.isFile() ? new FileInputStream(dd) : null;
      }
   }

   public DescriptorBean mergeDescriptors(VirtualJarFile[] vjars) throws IOException, XMLStreamException {
      return this._mergeDescriptors(VirtualJarFile.class, vjars, (File[])null);
   }

   public DescriptorBean mergeDescriptors(Object[] sources) throws IOException, XMLStreamException {
      ArrayList libList = new ArrayList();

      for(int i = 0; i < sources.length; ++i) {
         Source source = (Source)sources[i];
         URL url = source.getURL();
         String protocol = url.getProtocol();
         if ("file".equals(protocol)) {
            libList.add(new File(url.getPath()));
         }
      }

      File[] files = (File[])((File[])libList.toArray(new File[0]));
      return this.mergeDescriptors(files);
   }

   public DescriptorBean mergeDescriptors(File[] files) throws IOException, XMLStreamException {
      return this._mergeDescriptors(File.class, (VirtualJarFile[])null, files);
   }

   private DescriptorBean _mergeDescriptors(Class type, VirtualJarFile[] vjars, File[] files) throws IOException, XMLStreamException {
      assert type != null && (type == VirtualJarFile.class || type == File.class);

      try {
         BasicMunger2 aggregatingMunger = null;
         XMLStreamReader reader = null;
         int size = 0;
         if (type == VirtualJarFile.class) {
            size = vjars.length;
         } else if (type == File.class) {
            size = files.length;
         }

         assert size != 0;

         for(int i = size - 1; i > -1; --i) {
            Constructor c = this.getClass().getConstructor(type, File.class, DeploymentPlanBean.class, String.class, String.class);
            AbstractDescriptorLoader2 otherLoader = null;
            if (type == VirtualJarFile.class) {
               otherLoader = (AbstractDescriptorLoader2)c.newInstance(vjars[i], null, null, null, this.getDocumentURI());
            } else {
               otherLoader = (AbstractDescriptorLoader2)c.newInstance(files[i], null, null, null, this.getDocumentURI());
            }

            otherLoader.setDescriptorManager(this.getDescriptorManager());
            otherLoader.loadDescriptorBean();
            if (aggregatingMunger == null) {
               aggregatingMunger = otherLoader.getMunger();
               reader = aggregatingMunger.getPlaybackReader();
            } else {
               reader = aggregatingMunger.merge(otherLoader.getMunger(), (DescriptorBean)null);
            }
         }

         DescriptorBean var21;
         if (aggregatingMunger == null) {
            var21 = this.loadDescriptorBean();
            return var21;
         } else {
            this.loadDescriptorBeanWithoutPlan();
            if (this.getMunger() != null) {
               reader = aggregatingMunger.merge(this.getMunger(), (DescriptorBean)null);
            }

            this.munger = aggregatingMunger;
            if (this.plan != null) {
               var21 = this.mergeDescriptorBeanWithPlan(this.getDeploymentPlan(), this.getModuleName(), this.getDocumentURI(), reader);
               return var21;
            } else if (this.resourcePlan != null) {
               var21 = this.mergeDescriptorBeanWithResourcePlan(this.getResourceDeploymentPlan(), this.getResourceName(), reader);
               return var21;
            } else {
               var21 = this.getDescriptorBeanFromReader(reader);
               return var21;
            }
         }
      } catch (NoSuchMethodException var16) {
         throw new AssertionError("Descriptor loader subclasses must define a constructor with a \"" + type.toString().substring(type.toString().lastIndexOf(".") + 1) + ",File,DeploymentPlanBean,String,String\" signature: " + this.getClass().getName() + ": " + var16);
      } catch (InstantiationException var17) {
         throw new AssertionError("Descriptor loader subclasses must define a constructor with a \"" + type.toString().substring(type.toString().lastIndexOf(".") + 1) + ",File,DeploymentPlanBean,String,String\" signature: " + var17);
      } catch (IllegalAccessException var18) {
         throw new AssertionError("Descriptor loader subclasses must define a constructor with a \"" + type.toString().substring(type.toString().lastIndexOf(".") + 1) + ",File,DeploymentPlanBean,String,String\" signature: " + var18);
      } catch (InvocationTargetException var19) {
         throw new AssertionError("Descriptor loader subclasses must define a constructor with a \"" + type.toString().substring(type.toString().lastIndexOf(".") + 1) + ",File,DeploymentPlanBean,String,String\" signature: " + var19.getTargetException());
      } finally {
         ;
      }
   }

   public DescriptorBean mergeDescriptorBean(AbstractDescriptorLoader2 l) throws IOException, XMLStreamException {
      return this.mergeDescriptorBean(l, (DescriptorBean)null);
   }

   public DescriptorBean mergeDescriptorBean(AbstractDescriptorLoader2 l, DescriptorBean o) throws IOException, XMLStreamException {
      BasicMunger2 tempMunger = null;
      DescriptorBean tmp;
      if (o != null && o.getDescriptor().isModified()) {
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         this.dm.writeDescriptorAsXML(o.getDescriptor(), out);
         byte[] bits = out.toByteArray();
         InputStream in = new ByteArrayInputStream(bits);
         tempMunger = (BasicMunger2)this.createXMLStreamReader(in);
         tmp = null;
         if (tempMunger instanceof VersionMunger) {
            this.getDescriptorBeanFromReader(tempMunger.getPlaybackReader());
         } else {
            this.getDescriptorBeanFromReader(tempMunger);
         }
      }

      BasicMunger2 otherMunger = l.getOrCreateMunger();
      BasicMunger2 myMunger = this.getOrCreateMunger();
      if (myMunger == null && otherMunger == null && tempMunger == null) {
         return null;
      } else {
         otherMunger = this.merge(tempMunger, otherMunger);
         this.munger = this.merge(myMunger, otherMunger);
         XMLStreamReader reader = this.munger.getPlaybackReader();

         try {
            tmp = this.getDescriptorBeanFromReader(reader);
         } finally {
            this.getMunger().close();
         }

         return tmp;
      }
   }

   private BasicMunger2 merge(BasicMunger2 source, BasicMunger2 target) throws IOException, XMLStreamException {
      if (source != null && target != null) {
         target.merge(source, (DescriptorBean)null);
         return target;
      } else if (target != null) {
         return target;
      } else {
         return source != null ? source : null;
      }
   }

   public void updateDescriptorWithBean(DescriptorBean bean) throws IOException, XMLStreamException {
      BasicMunger2 tempMunger = null;
      if (bean != null) {
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         this.getDescriptorManager().writeDescriptorAsXML(bean.getDescriptor(), out);
         out.flush();
         byte[] bits = out.toByteArray();
         InputStream in = new ByteArrayInputStream(bits);
         tempMunger = (BasicMunger2)this.createXMLStreamReader(in);
         this.munger = tempMunger;
         this.versionInfo = tempMunger.getVersionInfo();
         DescriptorBean tmp = null;
         if (tempMunger instanceof VersionMunger) {
            this.getDescriptorBeanFromReader(tempMunger.getPlaybackReader());
         } else {
            this.getDescriptorBeanFromReader(tempMunger);
         }
      }

   }

   protected DescriptorBean parseBean() throws XMLStreamException, IOException {
      DescriptorBean var1;
      try {
         var1 = this.getDescriptorBeanFromReader(this.getMunger().getPlaybackReader());
      } finally {
         this.getMunger().close();
      }

      return var1;
   }

   DescriptorBean mergeDescriptorBeanWithPlan(DescriptorBean bean) throws IOException, XMLStreamException {
      if (!Boolean.getBoolean("weblogic.useoldplan")) {
         DeploymentPlanProcessor planProcessor = new DeploymentPlanProcessor(this.getDeploymentPlan(), this.getModuleName(), this.getDocumentURI(), bean, false);
         return planProcessor.applyPlanOverrides();
      } else {
         XMLStreamReader reader = this.getMunger().mergeDescriptorBeanWithPlan(bean);

         DescriptorBean var3;
         try {
            var3 = this.getDescriptorBeanFromReader(reader);
         } finally {
            this.getMunger().close();
         }

         return var3;
      }
   }

   private DescriptorBean mergeDescriptorBeanWithPlan(DeploymentPlanBean plan, String moduleName, String documentURI, XMLStreamReader mergedReader) throws IOException, XMLStreamException {
      if (!Boolean.getBoolean("weblogic.useoldplan")) {
         DescriptorBean bean = this.getDescriptorBeanFromReader(mergedReader);
         DeploymentPlanProcessor planProcessor = new DeploymentPlanProcessor(plan, moduleName, documentURI, bean, false);
         return planProcessor.applyPlanOverrides();
      } else {
         XMLStreamReader reader = this.getMunger().mergeDescriptorBeanWithPlan(plan, moduleName, documentURI);

         DescriptorBean var6;
         try {
            var6 = this.getDescriptorBeanFromReader(reader);
         } finally {
            this.getMunger().close();
         }

         return var6;
      }
   }

   DescriptorBean mergeDescriptorBeanWithResourcePlan(DescriptorBean bean) throws IOException, XMLStreamException {
      DeploymentPlanProcessor planProcessor = new DeploymentPlanProcessor(this.getResourceDeploymentPlan(), this.getResourceName(), bean, false);
      return planProcessor.applyPlanOverrides();
   }

   private DescriptorBean mergeDescriptorBeanWithResourcePlan(ResourceDeploymentPlanBean resourcePlan, String resourceName, XMLStreamReader mergedReader) throws IOException, XMLStreamException {
      DescriptorBean bean = this.getDescriptorBeanFromReader(mergedReader);
      DeploymentPlanProcessor planProcessor = new DeploymentPlanProcessor(resourcePlan, resourceName, bean, false);
      return planProcessor.applyPlanOverrides();
   }

   protected BasicMunger2 getMunger() {
      return this.munger;
   }

   private BasicMunger2 getOrCreateMunger() throws IOException, XMLStreamException {
      if (this.munger == null) {
         this.loadDescriptorBean();
      }

      return this.munger;
   }

   private DescriptorBean loadDescriptorBeanWithoutPlan() throws IOException, XMLStreamException {
      DescriptorBean bean = this.rootBean;
      if (bean != null) {
         return bean;
      } else if (this.munger != null) {
         return this.parseBean();
      } else {
         InputStream in = this.getInputStream();
         if (in == null) {
            in = this.getInputStreamFromPlan();
         }

         if (in != null) {
            bean = this.createDescriptorBean(in);
         }

         return bean;
      }
   }

   public DescriptorBean loadDescriptorBean() throws IOException, XMLStreamException {
      DescriptorBean var2;
      try {
         DescriptorBean bean = this.loadDescriptorBeanWithoutPlan();
         if (bean == null || this.getMunger().hasDTD() || this.plan == null && this.resourcePlan == null) {
            if (this.plan != null && this.getMunger() != null && this.getMunger().hasDTD()) {
               MungerLogger.logPlanNotSupportedForDTD(this.getDocumentURI(), this.getModuleName());
            }

            var2 = bean;
            return var2;
         }

         if (this.resourcePlan == null) {
            var2 = this.mergeDescriptorBeanWithPlan(bean);
            return var2;
         }

         var2 = this.mergeDescriptorBeanWithResourcePlan(bean);
      } finally {
         if (this.getMunger() != null) {
            this.getMunger().close();
         }

      }

      return var2;
   }

   private DescriptorBean getDescriptorBeanFromReader(XMLStreamReader reader) throws IOException {
      try {
         this.rootBean = this.getDescriptorManager().createDescriptor(reader, this.errorHolder, this.validateSchema).getRootBean();
         if (this.versionInfo != null) {
            this.rootBean.getDescriptor().setOriginalVersionInfo(this.versionInfo);
         }

         return this.rootBean;
      } catch (IOException var3) {
         MungerLogger.logUnableToValidateDescriptor(this.moduleName, this.getAbsolutePath(), StackTraceUtils.throwable2StackTrace(var3));
         throw var3;
      }
   }

   public static void main(String[] args) throws Exception {
      if (args.length == 0) {
         usage();
         System.exit(-1);
      }

      String ddPath = args[0];
      String planPath = args.length > 1 && args[1].endsWith("plan.xml") ? args[1] : null;
      File altDD = new File(ddPath);
      File configDir = new File(".");
      DeploymentPlanBean plan = null;
      String moduleName = args.length > 2 ? args[2] : null;
      if (planPath != null) {
         if (moduleName == null) {
            usage();
            System.exit(-1);
         }

         AbstractDescriptorLoader2 planLoader = new AbstractDescriptorLoader2(new File(planPath), planPath) {
         };
         plan = (DeploymentPlanBean)planLoader.loadDescriptorBean();
      }

      AbstractDescriptorLoader2 loader = new TestAbstractDescriptorLoader2(altDD, configDir, plan, moduleName, ddPath);
      int descriptorIndex = plan == null ? 1 : 3;
      File[] otherDescriptors = null;
      if (args.length > descriptorIndex && args[descriptorIndex].endsWith(".xml")) {
         System.out.print("\nmerged descriptor, Descriptor.toXML():");
         ArrayList libList = new ArrayList();

         for(int i = descriptorIndex; i < args.length; ++i) {
            libList.add(new File(args[i]));
         }

         otherDescriptors = (File[])((File[])libList.toArray(new File[0]));
      }

      DescriptorBean db;
      if (otherDescriptors == null) {
         db = loader.loadDescriptorBean();
      } else {
         db = loader.mergeDescriptors(otherDescriptors);
      }

      Descriptor d = db.getDescriptor();
      System.out.print("\nDescriptor.toXML():");
      d.toXML(System.out);
   }

   private static void usage() {
      System.out.print("java weblogic.application.descriptor.AbstractDescriptorLoader2  <dd-filename> <plan-filename> <module-name>");
   }

   public void setDescriptorManager(DescriptorManager descManager) {
      this.dm = descManager;
   }

   protected File getAltDD() {
      return this.altDD;
   }

   public static class TestAbstractDescriptorLoader2 extends AbstractDescriptorLoader2 {
      public TestAbstractDescriptorLoader2(File altDD, File configDir, DeploymentPlanBean plan, String moduleName, String documentURI) {
         super(altDD, configDir, plan, moduleName, documentURI);
      }
   }

   private static class READONLY_SINGLETON {
      static DescriptorManager instance = new DescriptorManager();

      static {
         instance.setProductionMode(DescriptorManagerHelper.isProductionMode());
      }
   }
}
