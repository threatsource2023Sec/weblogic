package weblogic.application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.jar.JarFile;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.CachingDescriptorLoader2;
import weblogic.application.internal.ApplicationReader;
import weblogic.application.internal.WlsApplicationReader;
import weblogic.application.internal.WlsExtensionReader;
import weblogic.application.utils.ModuleDiscovery;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class ApplicationDescriptor {
   private final MyApplicationDescriptor appDescriptor;
   private final MyWlsApplicationDescriptor wlsAppDescriptor;
   private final MyWlsExtensionDescriptor wlsExtDescriptor;
   private VirtualJarFile vjf;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private boolean validateSchema;
   private ApplicationBean discoveredModuleAppBean;
   private boolean scannedForModules;

   public ApplicationDescriptor(String applicationId, boolean isInternalApp, VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String appName) {
      this(vjar, configDir, plan, appName);
   }

   public ApplicationDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String appName) {
      this.vjf = null;
      this.validateSchema = true;
      this.discoveredModuleAppBean = null;
      this.scannedForModules = false;
      this.appDescriptor = new MyApplicationDescriptor(vjar, configDir, plan, appName);
      this.wlsAppDescriptor = new MyWlsApplicationDescriptor(vjar, configDir, plan, appName);
      this.wlsExtDescriptor = new MyWlsExtensionDescriptor(vjar, configDir, plan, appName);
      this.vjf = vjar;
   }

   public ApplicationDescriptor(InputStream appDD, InputStream wlAppDD) {
      this.vjf = null;
      this.validateSchema = true;
      this.discoveredModuleAppBean = null;
      this.scannedForModules = false;
      this.appDescriptor = new MyApplicationDescriptor(appDD);
      this.wlsAppDescriptor = new MyWlsApplicationDescriptor(wlAppDD);
      this.wlsExtDescriptor = new MyWlsExtensionDescriptor((File)null);
   }

   public ApplicationDescriptor(File altDD, File altWLDD, File altWLExtDD, File configDir, DeploymentPlanBean plan, String appName) {
      this.vjf = null;
      this.validateSchema = true;
      this.discoveredModuleAppBean = null;
      this.scannedForModules = false;
      this.appDescriptor = new MyApplicationDescriptor(altDD, configDir, plan, appName);
      this.wlsAppDescriptor = new MyWlsApplicationDescriptor(altWLDD, configDir, plan, appName);
      this.wlsExtDescriptor = new MyWlsExtensionDescriptor(altWLExtDD);
   }

   public ApplicationDescriptor(File altDD, File altWLDD, VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String appName) {
      this(altDD, altWLDD, vjar, configDir, plan, appName, (File)null);
   }

   public ApplicationDescriptor(File altDD, File altWLDD, VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String appName, File descriptorCacheDir) {
      this.vjf = null;
      this.validateSchema = true;
      this.discoveredModuleAppBean = null;
      this.scannedForModules = false;
      if (altDD != null) {
         this.appDescriptor = new MyApplicationDescriptor(altDD, configDir, plan, appName, (String)null, descriptorCacheDir);
      } else {
         this.appDescriptor = new MyApplicationDescriptor(vjar, configDir, plan, appName, (String)null, descriptorCacheDir);
      }

      if (altWLDD != null) {
         this.wlsAppDescriptor = new MyWlsApplicationDescriptor(altWLDD, configDir, plan, appName, (String)null, descriptorCacheDir);
      } else {
         this.wlsAppDescriptor = new MyWlsApplicationDescriptor(vjar, configDir, plan, appName, (String)null, descriptorCacheDir);
      }

      this.wlsExtDescriptor = new MyWlsExtensionDescriptor(vjar, configDir, plan, appName, (String)null, descriptorCacheDir);
      this.vjf = vjar;
   }

   public ApplicationDescriptor(VirtualJarFile vjar) {
      this(vjar, (File)null, (DeploymentPlanBean)null, (String)null);
   }

   public ApplicationDescriptor(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.vjf = null;
      this.validateSchema = true;
      this.discoveredModuleAppBean = null;
      this.scannedForModules = false;
      this.appDescriptor = new MyApplicationDescriptor(edm, gcl, configDir, plan, moduleName);
      this.wlsAppDescriptor = new MyWlsApplicationDescriptor(edm, gcl, configDir, plan, moduleName);
      this.wlsExtDescriptor = new MyWlsExtensionDescriptor(edm, gcl);
   }

   public ApplicationDescriptor(DescriptorManager edm, GenericClassLoader gcl) {
      this.vjf = null;
      this.validateSchema = true;
      this.discoveredModuleAppBean = null;
      this.scannedForModules = false;
      this.appDescriptor = new MyApplicationDescriptor(edm, gcl);
      this.wlsAppDescriptor = new MyWlsApplicationDescriptor(edm, gcl);
      this.wlsExtDescriptor = new MyWlsExtensionDescriptor(edm, gcl);
   }

   public ApplicationDescriptor() {
      this.vjf = null;
      this.validateSchema = true;
      this.discoveredModuleAppBean = null;
      this.scannedForModules = false;
      byte[] bytes = (new String("<jav:application version=\"5\" xmlns:jav=\"http://java.sun.com/xml/ns/javaee\"></jav:application>")).getBytes();
      this.appDescriptor = new MyApplicationDescriptor(new ByteArrayInputStream(bytes));
      byte[] wlsBytes = (new String("<ns:weblogic-application xmlns:ns=\"http://www.bea.com/ns/weblogic/90\"></ns:weblogic-application>")).getBytes();
      this.wlsAppDescriptor = new MyWlsApplicationDescriptor(new ByteArrayInputStream(wlsBytes));
      byte[] wlsExtBytes = (new String("<ns:weblogic-extension xmlns:ns=\"http://www.bea.com/ns/weblogic/90\"></ns:weblogic-extension>")).getBytes();
      this.wlsExtDescriptor = new MyWlsExtensionDescriptor(new ByteArrayInputStream(wlsExtBytes));
   }

   public void setValidateSchema(boolean v) {
      this.validateSchema = v;
   }

   public void writeDescriptors(File root) throws IOException, XMLStreamException {
      DescriptorManager descMgr = new DescriptorManager();
      File f = null;
      DescriptorBean bean = (DescriptorBean)this.getApplicationDescriptor();
      if (bean != null && this.discoveredModuleAppBean == null) {
         f = new File(root, this.appDescriptor.getDocumentURI());
         DescriptorUtils.writeDescriptor(descMgr, bean, f);
      }

      bean = (DescriptorBean)this.getWeblogicApplicationDescriptor();
      if (bean != null) {
         f = new File(root, this.wlsAppDescriptor.getDocumentURI());
         DescriptorUtils.writeDescriptor(descMgr, bean, f);
      }

      bean = (DescriptorBean)this.getWeblogicExtensionDescriptor();
      if (bean != null) {
         f = new File(root, this.wlsExtDescriptor.getDocumentURI());
         DescriptorUtils.writeDescriptor(descMgr, bean, f);
      }

   }

   public void writeInferredApplicationDescriptor(File root) throws IOException, XMLStreamException {
      if (this.discoveredModuleAppBean != null) {
         this.doWriteInferredApplicationDescriptor(root);
      }

   }

   public void doWriteInferredApplicationDescriptor(File root) throws IOException, XMLStreamException {
      DescriptorManager descMgr = new DescriptorManager();
      File f = null;
      f = new File(root, this.appDescriptor.getDocumentURI());
      DescriptorUtils.writeDescriptor(descMgr, (DescriptorBean)this.getApplicationDescriptor(), f);
   }

   private String getDescriptorAsString(DescriptorBean rootBean) {
      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         (new DescriptorManager()).writeDescriptorAsXML(rootBean.getDescriptor(), baos);
         return baos.toString();
      } catch (IOException var3) {
         return "Unable to marshal descriptor";
      }
   }

   public String dumpAllApplicationDescriptors() {
      try {
         return "The application.xml descriptor is ...\n" + this.getDescriptorAsString((DescriptorBean)this.getApplicationDescriptor()) + "********************************************\nThe weblogic-application.xml is ...\n" + this.getDescriptorAsString((DescriptorBean)this.getWeblogicApplicationDescriptor()) + "********************************************\nThe weblogic-extension.xml is ...\n" + this.getDescriptorAsString((DescriptorBean)this.getWeblogicExtensionDescriptor());
      } catch (Exception var2) {
         return "Unable to dump one of the descriptors";
      }
   }

   public ApplicationBean getApplicationDescriptor() throws IOException, XMLStreamException {
      if (!this.validateSchema) {
         this.appDescriptor.setValidate(false);
      }

      ApplicationBean appBean;
      IOException ioe;
      try {
         appBean = (ApplicationBean)this.appDescriptor.loadDescriptorBean();
      } catch (IOException var7) {
         ioe = new IOException("Error parsing META-INF/application.xml");
         ioe.initCause(var7);
         throw ioe;
      } catch (XMLStreamException var8) {
         throw new XMLStreamException("Error parsing META-INF/application.xml", var8);
      }

      if (appBean == null) {
         if (!this.scannedForModules) {
            this.scannedForModules = true;
            if (this.vjf != null) {
               try {
                  this.discoveredModuleAppBean = ModuleDiscovery.discoverModules(this.vjf);
               } catch (IOException var6) {
                  ioe = new IOException("In the absence of META-INF/application.xml module discovery was attempted but failed");
                  ioe.initCause(var6);
                  throw ioe;
               }
            }
         }

         appBean = this.discoveredModuleAppBean;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Modules discovery complete. Inferred descriptor: " + this.getDescriptorAsString((DescriptorBean)this.discoveredModuleAppBean));
         }

         if (appBean != null) {
            try {
               this.updateApplicationDescriptor(appBean);
               appBean = (ApplicationBean)this.appDescriptor.loadDescriptorBean();
            } catch (IOException var4) {
               ioe = new IOException("Error parsing META-INF/application.xml");
               ioe.initCause(var4);
               throw ioe;
            } catch (XMLStreamException var5) {
               throw new XMLStreamException("Error parsing META-INF/application.xml", var5);
            }
         }
      }

      return appBean;
   }

   public WeblogicApplicationBean getWeblogicApplicationDescriptor() throws IOException, XMLStreamException {
      if (!this.validateSchema) {
         this.wlsAppDescriptor.setValidate(false);
      }

      try {
         return (WeblogicApplicationBean)this.wlsAppDescriptor.loadDescriptorBean();
      } catch (IOException var3) {
         IOException ioe = new IOException("Error parsing META-INF/weblogic-application.xml");
         ioe.initCause(var3);
         throw ioe;
      } catch (XMLStreamException var4) {
         throw new XMLStreamException("Error parsing META-INF/weblogic-application.xml", var4);
      }
   }

   public WeblogicExtensionBean getWeblogicExtensionDescriptor() throws IOException, XMLStreamException {
      if (!this.validateSchema) {
         this.wlsExtDescriptor.setValidate(false);
      }

      try {
         return (WeblogicExtensionBean)this.wlsExtDescriptor.loadDescriptorBean();
      } catch (IOException var3) {
         IOException ioe = new IOException("Error parsing META-INF/weblogic-extension.xml");
         ioe.initCause(var3);
         throw ioe;
      } catch (XMLStreamException var4) {
         throw new XMLStreamException("Error parsing META-INF/weblogic-extension.xml", var4);
      }
   }

   public void mergeDescriptors(VirtualJarFile vjar) throws IOException, XMLStreamException {
      try {
         this.appDescriptor.mergeDescriptors(new VirtualJarFile[]{vjar});
         if (vjar.getEntry("META-INF/weblogic-application.xml") != null) {
            this.wlsAppDescriptor.mergeDescriptors(new VirtualJarFile[]{vjar});
         }

         if (vjar.getEntry("META-INF/weblogic-extension.xml") != null) {
            this.wlsExtDescriptor.mergeDescriptors(new VirtualJarFile[]{vjar});
         }

      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IOException(var3.getMessage());
      }
   }

   public void mergeDescriptors(ApplicationDescriptor appDesc) throws IOException, XMLStreamException {
      ApplicationBean appB = appDesc.getApplicationDescriptor();
      if (appB != null && appB.getModules() != null && appB.getModules().length > 0) {
         this.appDescriptor.mergeDescriptorBean(appDesc.getApplicationDescriptorLoader());
      }

      this.wlsAppDescriptor.mergeDescriptorBean(appDesc.getWlsApplicationDescriptorLoader());
      this.wlsExtDescriptor.mergeDescriptorBean(appDesc.getWlsExtensionDescriptorLoader());
   }

   private ApplicationBean mergeApplicationDescriptor(File altDD) throws IOException, XMLStreamException {
      return (ApplicationBean)this.appDescriptor.mergeDescriptors(new File[]{altDD});
   }

   private WeblogicApplicationBean mergeWlsApplicationDescriptor(File altDD) throws IOException, XMLStreamException {
      return (WeblogicApplicationBean)this.wlsAppDescriptor.mergeDescriptors(new File[]{altDD});
   }

   private WeblogicExtensionBean mergeWlsExtentionDescriptor(File altDD) throws IOException, XMLStreamException {
      return (WeblogicExtensionBean)this.wlsExtDescriptor.mergeDescriptors(new File[]{altDD});
   }

   public void updateApplicationDescriptor(ApplicationBean modifiedAppBean) throws IOException, XMLStreamException {
      this.appDescriptor.updateDescriptorWithBean((DescriptorBean)modifiedAppBean);
   }

   public void updateWeblogicApplicationDescriptor(WeblogicApplicationBean modifiedWlAppBean) throws IOException, XMLStreamException {
      this.wlsAppDescriptor.updateDescriptorWithBean((DescriptorBean)modifiedWlAppBean);
   }

   public AbstractDescriptorLoader2 getApplicationDescriptorLoader() {
      return this.appDescriptor;
   }

   public AbstractDescriptorLoader2 getWlsApplicationDescriptorLoader() {
      return this.wlsAppDescriptor;
   }

   public AbstractDescriptorLoader2 getWlsExtensionDescriptorLoader() {
      return this.wlsExtDescriptor;
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 1) {
         usage();
      }

      DescriptorManager dm = new DescriptorManager();
      if (args[0].lastIndexOf("create") > -1) {
         Descriptor d = dm.createDescriptorRoot(ApplicationBean.class);
         dm.writeDescriptorAsXML(d, System.out);
         System.out.println("\n\n\n");
         d = dm.createDescriptorRoot(WeblogicApplicationBean.class);
         dm.writeDescriptorAsXML(d, System.out);
         System.exit(0);
      }

      try {
         String earPath = args[0];
         File f = new File(earPath);
         if (!f.getName().endsWith(".ear") && !f.isDirectory()) {
            ApplicationDescriptor ad;
            if (f.getPath().endsWith("weblogic-application.xml")) {
               System.out.println("\n\n... getting WeblogicApplicationBean:");
               ad = new ApplicationDescriptor((File)null, f, (File)null, (File)null, (DeploymentPlanBean)null, (String)null);
               dm.writeDescriptorAsXML(((DescriptorBean)ad.getWeblogicApplicationDescriptor()).getDescriptor(), System.out);
               if (args.length > 1) {
                  File f2 = new File(args[1]);
                  if (f2.getPath().endsWith("weblogic-application.xml")) {
                     System.out.println("\n\n... merged WeblogicApplicationBean:");
                     dm.writeDescriptorAsXML(((DescriptorBean)ad.mergeWlsApplicationDescriptor(f2)).getDescriptor(), System.out);
                  }

                  if (f2.getPath().endsWith("plan.xml")) {
                     System.out.println("\n\n... plan:");
                     Descriptor planDescriptor = dm.createDescriptor(new FileInputStream(f2));
                     DeploymentPlanBean plan = (DeploymentPlanBean)planDescriptor.getRootBean();
                     dm.writeDescriptorAsXML(planDescriptor, System.out);
                     ApplicationDescriptor ad2 = new ApplicationDescriptor((File)null, f, (File)null, new File(plan.getConfigRoot()), plan, plan.getApplicationName());
                     System.out.println("\n\n... plan merged with WeblogicApplicationBean:");
                     dm.writeDescriptorAsXML(((DescriptorBean)ad2.getWeblogicApplicationDescriptor()).getDescriptor(), System.out);
                  }
               }
            } else {
               DescriptorBean b;
               if (f.getPath().endsWith("application.xml")) {
                  System.out.println("\n\n... getting WeblogicApplicationBean:");
                  ad = new ApplicationDescriptor(f, (File)null, (File)null, (File)null, (DeploymentPlanBean)null, (String)null);
                  b = (DescriptorBean)ad.getApplicationDescriptor();
                  dm.writeDescriptorAsXML(b.getDescriptor(), System.out);
                  if (args.length > 1) {
                     File f2 = new File(args[1]);
                     ApplicationDescriptor ad2 = new ApplicationDescriptor(f2, (File)null, (File)null, (File)null, (DeploymentPlanBean)null, (String)null);
                     System.out.println("\n\n... dump 2nd bean:");
                     DescriptorBean b2 = (DescriptorBean)ad2.getApplicationDescriptor();
                     dm.writeDescriptorAsXML(b2.getDescriptor(), System.out);
                     System.out.println("\n\n... compared to:");
                     dm.writeDescriptorAsXML(((DescriptorBean)ad.mergeApplicationDescriptor(f2)).getDescriptor(), System.out);
                     if (args.length > 2) {
                        File f3 = new File(args[2]);
                        ApplicationDescriptor ad3 = new ApplicationDescriptor(f3, (File)null, (File)null, (File)null, (DeploymentPlanBean)null, (String)null);
                        System.out.println("\n\n... dump 2nd bean:");
                        DescriptorBean b3 = (DescriptorBean)ad3.getApplicationDescriptor();
                        dm.writeDescriptorAsXML(b3.getDescriptor(), System.out);
                        ApplicationDescriptor nullAd = new ApplicationDescriptor();
                        DescriptorBean nullBean = (DescriptorBean)nullAd.getApplicationDescriptor();
                        System.out.println("\n\n... getting null application bean :");
                        dm.writeDescriptorAsXML(nullBean.getDescriptor(), System.out);
                        nullAd.mergeApplicationDescriptor(f);
                        nullAd.mergeApplicationDescriptor(f2);
                        nullAd.mergeApplicationDescriptor(f3);
                        nullBean = (DescriptorBean)nullAd.getApplicationDescriptor();
                        System.out.println("\n\n... getting null application bean after merge:");
                        dm.writeDescriptorAsXML(nullBean.getDescriptor(), System.out);
                        ApplicationBean appBean = ad.getApplicationDescriptor();
                        ModuleBean mb = appBean.createModule();
                        mb.setConnector("my-made-up-connector");
                        System.out.println("\n\n... dump first bean-- should have my-made-up-connector: " + ((DescriptorBean)appBean).getDescriptor());
                        dm.writeDescriptorAsXML(((DescriptorBean)appBean).getDescriptor(), System.out);
                        System.out.println("\n\n... compared to:");
                        dm.writeDescriptorAsXML(((DescriptorBean)ad.mergeApplicationDescriptor(f3)).getDescriptor(), System.out);
                     }
                  }
               } else if (f.getPath().endsWith("weblogic-extension.xml")) {
                  System.out.println("\n\n... getting WeblogicExtensionBean:");
                  ad = new ApplicationDescriptor();
                  ad.mergeWlsExtentionDescriptor(f);
                  b = (DescriptorBean)ad.getWeblogicExtensionDescriptor();
                  dm.writeDescriptorAsXML(b.getDescriptor(), System.out);
               } else {
                  System.out.println("\n\n... neither application nor weblogic-application xml specified");
               }
            }
         } else {
            VirtualJarFile vEarJarFile = f.getName().endsWith(".ear") ? VirtualJarFactory.createVirtualJar(new JarFile(earPath)) : VirtualJarFactory.createVirtualJar(f);
            ApplicationDescriptor ad = new ApplicationDescriptor(vEarJarFile, (File)null, (DeploymentPlanBean)null, (String)null);
            dm.writeDescriptorAsXML(((DescriptorBean)ad.getApplicationDescriptor()).getDescriptor(), System.out);
            dm.writeDescriptorAsXML(((DescriptorBean)ad.getWeblogicApplicationDescriptor()).getDescriptor(), System.out);
            if (args.length > 1) {
               for(int i = 1; i < args.length; ++i) {
                  JarFile earJarFile2 = new JarFile(args[i]);
                  VirtualJarFile vEarJarFile2 = VirtualJarFactory.createVirtualJar(earJarFile2);
                  System.out.println("\n\n... output lib descriptors to merge:");
                  ApplicationDescriptor adxxx = new ApplicationDescriptor(vEarJarFile2, (File)null, (DeploymentPlanBean)null, (String)null);
                  dm.writeDescriptorAsXML(((DescriptorBean)adxxx.getApplicationDescriptor()).getDescriptor(), System.out);
                  ad.getApplicationDescriptor().setDescriptions(new String[]{"from depths so profound come enduring change..."});
                  ad.mergeDescriptors(vEarJarFile2);
                  System.out.println("\n\n... getting merged ApplicationBean:");
                  dm.writeDescriptorAsXML(((DescriptorBean)ad.getApplicationDescriptor()).getDescriptor(), System.out);
               }
            }
         }
      } catch (Exception var16) {
         System.out.println(var16.toString());
         System.out.println(var16.getMessage());
         System.out.println(var16.getCause());
         var16.printStackTrace();
         System.exit(1);
      }

   }

   private static void usage() {
      System.err.println("usage: java weblogic.application.ApplicationDescriptor <descriptor file name>");
      System.err.println("\n\n example:\n java weblogic.application.ApplicationDescriptor ear or file name ");
      System.exit(0);
   }

   public void printAppDescriptors(PrintStream out) {
      try {
         out.println("----------------------------------");
      } catch (Exception var3) {
         debugLogger.debug("Got this: " + var3);
      }

   }

   public static class MyWlsExtensionDescriptor extends CachingDescriptorLoader2 {
      private InputStream is;

      public MyWlsExtensionDescriptor(VirtualJarFile vjar) {
         super((VirtualJarFile)vjar, (File)null, (DeploymentPlanBean)null, (String)null, "META-INF/weblogic-extension.xml", (File)null);
      }

      public MyWlsExtensionDescriptor(File altDD) {
         super((File)altDD, (File)null, (DeploymentPlanBean)null, (String)null, "META-INF/weblogic-extension.xml", (File)null);
      }

      public MyWlsExtensionDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String appName) {
         this((File)altDD, configDir, plan, appName, (String)null);
      }

      public MyWlsExtensionDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String appName, String documentURI) {
         super((File)altDD, configDir, plan, appName, "META-INF/weblogic-extension.xml", (File)null);
      }

      public MyWlsExtensionDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String appName) {
         this(vjar, configDir, plan, appName, (String)null, (File)null);
      }

      public MyWlsExtensionDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String appName, String documentURI) {
         this(vjar, configDir, plan, appName, (String)null, (File)null);
      }

      public MyWlsExtensionDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String appName, String documentURI, File descriptorCacheDir) {
         super((VirtualJarFile)vjar, configDir, plan, appName, "META-INF/weblogic-extension.xml", (File)null);
      }

      public MyWlsExtensionDescriptor(DescriptorManager edm, GenericClassLoader gcl) {
         super(edm, gcl, (File)null, (DeploymentPlanBean)null, (String)null, "META-INF/weblogic-extension.xml", (File)null);
      }

      public MyWlsExtensionDescriptor(InputStream is) {
         super((VirtualJarFile)((VirtualJarFile)null), (File)null, (DeploymentPlanBean)null, (String)null, "META-INF/weblogic-extension.xml", (File)null);
         this.is = is;
         this.setValidateSchema(false);
      }

      public InputStream getInputStream() throws IOException {
         return this.is == null ? super.getInputStream() : this.is;
      }

      public String getDocumentURI() {
         return "META-INF/weblogic-extension.xml";
      }

      protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
         return new WlsExtensionReader(is, this);
      }

      void setValidate(boolean validate) {
         super.setValidateSchema(validate);
      }
   }

   public static class MyWlsApplicationDescriptor extends CachingDescriptorLoader2 {
      private InputStream is;

      public MyWlsApplicationDescriptor(File altDD) {
         super((File)altDD, (File)null, (DeploymentPlanBean)null, (String)null, "META-INF/weblogic-application.xml", (File)null);
         this.is = null;
      }

      public MyWlsApplicationDescriptor(VirtualJarFile vjar) {
         super((VirtualJarFile)vjar, (File)null, (DeploymentPlanBean)null, (String)null, "META-INF/weblogic-application.xml", (File)null);
         this.is = null;
      }

      public MyWlsApplicationDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String appName) {
         this((File)altDD, configDir, plan, appName, (String)null, (File)null);
      }

      public MyWlsApplicationDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String appName, String documentURI) {
         this((File)altDD, configDir, plan, appName, "META-INF/weblogic-application.xml", (File)null);
      }

      public MyWlsApplicationDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String appName, String documentURI, File descriptorCacheDir) {
         super(altDD, configDir, plan, appName, "META-INF/weblogic-application.xml", descriptorCacheDir);
         this.is = null;
      }

      public MyWlsApplicationDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String appName) {
         this((VirtualJarFile)vjar, configDir, plan, appName, (String)null, (File)null);
      }

      public MyWlsApplicationDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String appName, String documentURI) {
         this((VirtualJarFile)vjar, configDir, plan, appName, (String)null, (File)null);
      }

      public MyWlsApplicationDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String appName, String documentURI, File descriptorCacheDir) {
         super(vjar, configDir, plan, appName, "META-INF/weblogic-application.xml", descriptorCacheDir);
         this.is = null;
      }

      public MyWlsApplicationDescriptor(InputStream is) {
         super((VirtualJarFile)((VirtualJarFile)null), (File)null, (DeploymentPlanBean)null, (String)null, "META-INF/weblogic-application.xml", (File)null);
         this.is = null;
         this.is = is;
         this.setValidateSchema(false);
      }

      public MyWlsApplicationDescriptor(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
         super(edm, gcl, configDir, plan, moduleName, "META-INF/weblogic-application.xml", (File)null);
         this.is = null;
      }

      public MyWlsApplicationDescriptor(DescriptorManager edm, GenericClassLoader gcl) {
         super(edm, gcl, "META-INF/weblogic-application.xml", (File)null);
         this.is = null;
      }

      public InputStream getInputStream() throws IOException {
         return this.is == null ? super.getInputStream() : this.is;
      }

      public String getDocumentURI() {
         return "META-INF/weblogic-application.xml";
      }

      protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
         return new WlsApplicationReader(is, this);
      }

      void setValidate(boolean validate) {
         super.setValidateSchema(validate);
      }
   }

   public static class MyApplicationDescriptor extends CachingDescriptorLoader2 {
      private InputStream is;

      public MyApplicationDescriptor(File altDD) {
         super((File)altDD, (File)null, (DeploymentPlanBean)null, (String)null, "META-INF/application.xml", (File)null);
         this.is = null;
      }

      public MyApplicationDescriptor(VirtualJarFile vjar) {
         super((VirtualJarFile)vjar, (File)null, (DeploymentPlanBean)null, (String)null, "META-INF/application.xml", (File)null);
         this.is = null;
      }

      public MyApplicationDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String appName) {
         this((File)altDD, configDir, plan, appName, (String)null, (File)null);
      }

      public MyApplicationDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String appName, String documentURI) {
         this((File)altDD, configDir, plan, appName, (String)null, (File)null);
      }

      public MyApplicationDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String appName, String documentURI, File descriptorCacheDir) {
         super(altDD, configDir, plan, appName, "META-INF/application.xml", descriptorCacheDir);
         this.is = null;
      }

      public MyApplicationDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String appName) {
         this((VirtualJarFile)vjar, configDir, plan, appName, (String)null, (File)null);
      }

      public MyApplicationDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String appName, String documentURI) {
         this((VirtualJarFile)vjar, configDir, plan, appName, (String)null, (File)null);
      }

      public MyApplicationDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String appName, String documentURI, File descriptorCacheDir) {
         super(vjar, configDir, plan, appName, "META-INF/application.xml", descriptorCacheDir);
         this.is = null;
      }

      public MyApplicationDescriptor(InputStream is) {
         super((VirtualJarFile)((VirtualJarFile)null), (File)null, (DeploymentPlanBean)null, (String)null, "META-INF/application.xml", (File)null);
         this.is = null;
         this.is = is;
         this.setValidateSchema(false);
      }

      public MyApplicationDescriptor(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
         super(edm, gcl, configDir, plan, moduleName, "META-INF/application.xml", (File)null);
         this.is = null;
      }

      public MyApplicationDescriptor(DescriptorManager edm, GenericClassLoader gcl) {
         this((DescriptorManager)edm, (GenericClassLoader)gcl, (File)null, (DeploymentPlanBean)null, (String)null);
      }

      public InputStream getInputStream() throws IOException {
         return this.is == null ? super.getInputStream() : this.is;
      }

      protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
         return new ApplicationReader(is, this);
      }

      void setValidate(boolean validate) {
         super.setValidateSchema(validate);
      }
   }
}
