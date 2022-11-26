package weblogic.application.descriptor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.zip.ZipEntry;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.utils.Debug;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.stax.XMLStreamInputFactory;

public abstract class AbstractDescriptorLoader {
   private boolean dump = Debug.getCategory("weblogic.application.descriptor").isEnabled();
   private DescriptorManager dm;
   private final XMLInputFactory xiFactory;
   private Descriptor descriptor;
   private Descriptor planMergedDescriptor;
   private File altDD;
   private VirtualJarFile vjar;
   private GenericClassLoader gcl;
   private DeploymentPlanBean plan;
   private String moduleName;
   private File configDir;
   protected boolean debug;
   protected BasicMunger munger;
   private boolean doInit;

   public AbstractDescriptorLoader(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.dm = AbstractDescriptorLoader.READONLY_SINGLETON.instance;
      this.xiFactory = new XMLStreamInputFactory();
      this.debug = false;
      this.doInit = true;
      this.vjar = vjar;
      this.configDir = configDir;
      this.plan = plan;
      this.moduleName = moduleName;
   }

   public AbstractDescriptorLoader(File altDD, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.dm = AbstractDescriptorLoader.READONLY_SINGLETON.instance;
      this.xiFactory = new XMLStreamInputFactory();
      this.debug = false;
      this.doInit = true;
      this.altDD = altDD;
      this.configDir = configDir;
      this.plan = plan;
      this.moduleName = moduleName;
   }

   public AbstractDescriptorLoader(GenericClassLoader gcl) {
      this.dm = AbstractDescriptorLoader.READONLY_SINGLETON.instance;
      this.xiFactory = new XMLStreamInputFactory();
      this.debug = false;
      this.doInit = true;
      this.gcl = gcl;
   }

   public AbstractDescriptorLoader(GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.dm = AbstractDescriptorLoader.READONLY_SINGLETON.instance;
      this.xiFactory = new XMLStreamInputFactory();
      this.debug = false;
      this.doInit = true;
      this.gcl = gcl;
      this.configDir = configDir;
      this.plan = plan;
      this.moduleName = moduleName;
   }

   public AbstractDescriptorLoader(DescriptorManager dm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.dm = AbstractDescriptorLoader.READONLY_SINGLETON.instance;
      this.xiFactory = new XMLStreamInputFactory();
      this.debug = false;
      this.doInit = true;
      this.gcl = gcl;
      this.dm = dm;
      this.plan = plan;
      this.moduleName = moduleName;
   }

   public AbstractDescriptorLoader(DescriptorManager dm, GenericClassLoader gcl) {
      this.dm = AbstractDescriptorLoader.READONLY_SINGLETON.instance;
      this.xiFactory = new XMLStreamInputFactory();
      this.debug = false;
      this.doInit = true;
      this.gcl = gcl;
      this.dm = dm;
   }

   public AbstractDescriptorLoader(VirtualJarFile vjar) {
      this.dm = AbstractDescriptorLoader.READONLY_SINGLETON.instance;
      this.xiFactory = new XMLStreamInputFactory();
      this.debug = false;
      this.doInit = true;
      this.vjar = vjar;
   }

   public AbstractDescriptorLoader(File altDD) {
      this.dm = AbstractDescriptorLoader.READONLY_SINGLETON.instance;
      this.xiFactory = new XMLStreamInputFactory();
      this.debug = false;
      this.doInit = true;
      this.altDD = altDD;
   }

   public DeploymentPlanBean getDeploymentPlan() {
      return this.plan;
   }

   public File getConfigDir() {
      return this.configDir;
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

   public DescriptorBean getRootDescriptorBean() throws IOException, XMLStreamException {
      if (this.doInit) {
         this.descriptor = this.getDescriptor();
         if (this.descriptor == null) {
            DescriptorBean db = this.getDescriptorBeanFromPlan();
            if (db != null) {
               this.descriptor = db.getDescriptor();
            }

            return db;
         }
      }

      return this.descriptor == null ? null : this.descriptor.getRootBean();
   }

   public Descriptor getDescriptor() throws IOException, XMLStreamException {
      if (this.doInit) {
         this.doInit = false;
         this.descriptor = this.createDescriptor();
      }

      return this.descriptor;
   }

   protected void setDescriptor(Descriptor descriptor) throws IOException, XMLStreamException {
      this.doInit = false;
      this.descriptor = descriptor;
   }

   public abstract String getDocumentURI();

   public String getNamespaceURI() {
      return null;
   }

   public Map getElementNameChanges() {
      return Collections.EMPTY_MAP;
   }

   public DescriptorBean getDescriptorBeanFromPlan() throws IOException, XMLStreamException {
      File dd = this.getPlanFile();
      if (dd == null) {
         return null;
      } else if (dd.exists() && dd.isFile()) {
         InputStream in = new FileInputStream(dd);

         DescriptorBean var3;
         try {
            var3 = this.createDescriptor(in).getRootBean();
         } finally {
            try {
               in.close();
            } catch (Exception var10) {
            }

         }

         return var3;
      } else {
         return null;
      }
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

   protected Descriptor createDescriptor() throws IOException, XMLStreamException {
      InputStream is = this.getInputStream();
      if (is == null) {
         return null;
      } else {
         Descriptor var2;
         try {
            var2 = this.createDescriptor(is);
         } finally {
            try {
               is.close();
            } catch (Exception var9) {
            }

         }

         return var2;
      }
   }

   protected Descriptor createDescriptor(InputStream is) throws IOException, XMLStreamException {
      Descriptor var3;
      try {
         XMLStreamReader reader = this.getXMLStreamReader(is);
         var3 = this.getDescriptorManager().createDescriptor(reader);
      } finally {
         try {
            is.close();
         } catch (Exception var10) {
         }

      }

      return var3;
   }

   protected Descriptor createDescriptor(InputStream is, boolean validate) throws IOException, XMLStreamException {
      Descriptor var4;
      try {
         XMLStreamReader reader = this.getXMLStreamReader(is);
         var4 = this.getDescriptorManager().createDescriptor(reader, validate);
      } finally {
         try {
            is.close();
         } catch (Exception var11) {
         }

      }

      return var4;
   }

   protected DescriptorManager getDescriptorManager() {
      return this.dm;
   }

   public InputStream getInputStream() throws IOException {
      if (this.altDD == null) {
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

   protected XMLStreamReader getXMLStreamReader(InputStream is) throws XMLStreamException {
      if (this.munger == null) {
         this.munger = this.createXMLStreamReader(is);
      }

      return this.munger;
   }

   protected BasicMunger createXMLStreamReader(InputStream is) throws XMLStreamException {
      return new BasicMunger(this.createXMLStreamReaderDelegate(is), this);
   }

   public XMLStreamReader createXMLStreamReaderDelegate(InputStream is) throws XMLStreamException {
      return this.xiFactory.createXMLStreamReader(is);
   }

   public BasicMunger createNullMunger() {
      String nsURI = this.getMunger().getNamespaceURI();
      ArrayList q = new ArrayList();
      this.getMunger().toQueuedEvents(q);
      int i = 0;

      while(i < q.size() && ((ReaderEvent)((ReaderEvent)q.get(i))).getEventType() != 1) {
      }

      String root = ((ReaderEvent)((ReaderEvent)q.get(i))).getLocalName();
      String dummyXML = "<ns:" + root + " xmlns:ns=\"" + this.getMunger().getNamespaceURI() + "\"></ns:" + root + ">";
      XMLStreamReader delegate = null;

      try {
         delegate = this.createXMLStreamReaderDelegate(new ByteArrayInputStream(dummyXML.getBytes()));
      } catch (Exception var8) {
      }

      BasicMunger bm = new BasicMunger(delegate, this);
      bm.setDtdNamespaceURI(this.getMunger().getNamespaceURI());
      return bm;
   }

   public void toXML(PrintStream out) throws IOException, XMLStreamException {
      if (this.munger == null) {
         this.getRootDescriptorBean();
      }

      this.munger.toXML(out);
   }

   protected BasicMunger getMergingMunger() throws IOException, XMLStreamException {
      return this.munger;
   }

   public void merge(AbstractDescriptorLoader that) throws IOException, XMLStreamException {
      BasicMunger myMunger = this.getMergingMunger();
      if (myMunger == null) {
         this.getRootDescriptorBean();
         myMunger = this.getMergingMunger();
      }

      if (myMunger != null) {
         myMunger.merge(that.getMergingMunger());
      }
   }

   public void mergeDescriptor(AbstractDescriptorLoader from) throws IOException, XMLStreamException {
      DescriptorBean b = this.getMergedDescriptorBean(from);
      if (b != null) {
         this.setDescriptor(b.getDescriptor());
      }

   }

   public DescriptorBean getMergedDescriptorBean(AbstractDescriptorLoader from) throws IOException, XMLStreamException {
      DescriptorBean b = from.getRootDescriptorBean();
      if (b == null) {
         return null;
      } else {
         this.merge(from);
         b = this.getMergedDescriptorBean();
         if (b != null) {
            this.setDescriptor(b.getDescriptor());
         }

         return b;
      }
   }

   public DescriptorBean getMergedDescriptorBean() throws IOException, XMLStreamException {
      ArrayList q = new ArrayList();

      try {
         Descriptor d;
         try {
            BasicMunger dummy = this.createNullMunger();
            this.munger.toQueuedEvents(q);
            dummy.setQueuedEvents(q);
            dummy.setForceNoBaseStreamHasNext(true);
            d = this.getDescriptorManager().createDescriptor(dummy);
            DescriptorBean var4 = d.getRootBean();
            return var4;
         } catch (Exception var8) {
            var8.printStackTrace();
            d = null;
            return d;
         }
      } finally {
         ;
      }
   }

   public DescriptorBean getPlanMergedDescriptorBean() throws IOException, XMLStreamException {
      if (this.planMergedDescriptor == null) {
         this.planMergedDescriptor = this.getPlanMergedDescriptor();
      }

      if (this.planMergedDescriptor == null) {
         return null;
      } else {
         if (this.debug || this.dump) {
            this.planMergedDescriptor.toXML(System.out);
         }

         return this.planMergedDescriptor.getRootBean();
      }
   }

   public Descriptor getPlanMergedDescriptor() throws IOException, XMLStreamException {
      InputStream is = this.getInputStream();

      try {
         Descriptor var4;
         if (is == null) {
            DescriptorBean b = this.getRootDescriptorBean();
            File dd;
            if (b == null) {
               dd = null;
               return dd;
            }

            dd = this.getPlanFile();
            if (dd == null) {
               var4 = null;
               return var4;
            }

            if (!dd.exists() || !dd.isFile()) {
               var4 = null;
               return var4;
            }

            is = new FileInputStream(dd);
         }

         ArrayList q = new ArrayList();
         if (this.munger == null) {
            this.getRootDescriptorBean();
         }

         if (!Boolean.getBoolean("weblogic.useoldplan")) {
            DescriptorBean bean = this.getRootDescriptorBean();
            DeploymentPlanProcessor planProcessor = new DeploymentPlanProcessor(this.getDeploymentPlan(), this.getModuleName(), this.getDocumentURI(), bean, false, this.getPredicateMatcher());
            DescriptorBean mergedBean = planProcessor.applyPlanOverrides();
            Descriptor var6 = mergedBean.getDescriptor();
            return var6;
         } else {
            this.munger.mergePlan(q);
            if (q.size() == 0) {
               Descriptor var22 = this.getRootDescriptorBean().getDescriptor();
               return var22;
            } else {
               BasicMunger reader = this.createXMLStreamReader((InputStream)is);
               reader.setQueuedEvents(q);
               var4 = this.getDescriptorManager().createDescriptor(reader, false);
               return var4;
            }
         }
      } finally {
         if (is != null) {
            try {
               ((InputStream)is).close();
            } catch (Exception var18) {
            }
         }

      }
   }

   protected PredicateMatcher getPredicateMatcher() {
      return null;
   }

   BasicMunger getMunger() {
      return this.munger;
   }

   private static class READONLY_SINGLETON {
      static DescriptorManager instance = new DescriptorManager();
   }
}
