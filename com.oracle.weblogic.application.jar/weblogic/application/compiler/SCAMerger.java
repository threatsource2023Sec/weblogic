package weblogic.application.compiler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.model.J2eeApplicationObject;
import javax.enterprise.deploy.shared.ModuleType;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.deploymentview.EditableDeployableObjectFactory;
import weblogic.application.compiler.deploymentview.EditableScaApplicationObject;
import weblogic.application.utils.IOUtils;
import weblogic.application.utils.ModuleDiscovery;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.sca.descriptor.ComponentBean;
import weblogic.sca.descriptor.CompositeBean;
import weblogic.sca.descriptor.ScaContributionBean;
import weblogic.utils.Getopt2;
import weblogic.utils.XXEUtils;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

final class SCAMerger extends EarReader {
   private final CompilerCtx ctx;
   private static final String SCA_CONTRIBUTION_URI = "META-INF/sca-contribution.xml";

   SCAMerger(CompilerCtx ctx) {
      super(ctx);
      this.ctx = ctx;
   }

   public void merge() throws ToolFailureException {
      if (this.containsJ2EEModules()) {
         super.merge();
      }

      VirtualJarFile vjf = this.ctx.getVSource();
      ScaContributionBean scaContributionBean = this.parseSCAComposite(vjf);
      EditableDeployableObjectFactory objectFactory = this.ctx.getObjectFactory();
      if (objectFactory != null) {
         List deployableObjects = new ArrayList();

         EditableScaApplicationObject deployableApplication;
         try {
            deployableApplication = objectFactory.createScaApplicationObject();
            CompositeBean[] var6 = scaContributionBean.getComposites();
            int var7 = var6.length;

            int var8;
            for(var8 = 0; var8 < var7; ++var8) {
               CompositeBean compositeBean = var6[var8];
               ComponentBean[] var10 = compositeBean.getComponents();
               int var11 = var10.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  ComponentBean componentBean = var10[var12];
                  String uri = compositeBean.getName() + "/" + componentBean.getName();
                  EditableDeployableObject deployableObject = objectFactory.createDeployableObject(uri, (String)null, WebLogicModuleType.SCA_JAVA);
                  deployableObjects.add(deployableObject);
               }
            }

            J2eeApplicationObject j2eeDeployable = (J2eeApplicationObject)this.ctx.getDeployableApplication();
            if (j2eeDeployable != null) {
               DeployableObject[] var20 = j2eeDeployable.getDeployableObjects();
               var8 = var20.length;

               for(int var22 = 0; var22 < var8; ++var22) {
                  DeployableObject deployable = var20[var22];
                  deployableObjects.add((EditableDeployableObject)deployable);
               }
            }
         } catch (IOException var17) {
            throw new ToolFailureException("Unable to create deployable object", var17);
         }

         try {
            deployableApplication.setVirtualJarFile(this.ctx.getVSource());
            deployableApplication.setRootBean((DescriptorBean)scaContributionBean);
            deployableApplication.addRootBean("META-INF/sca-contribution.xml", (DescriptorBean)scaContributionBean, (ModuleType)null);
            this.ctx.setDeployableApplication(deployableApplication);
            Iterator var19 = deployableObjects.iterator();

            while(var19.hasNext()) {
               EditableDeployableObject deployableObject = (EditableDeployableObject)var19.next();
               deployableApplication.addDeployableObject(deployableObject);
            }
         } catch (Exception var16) {
            throw new ToolFailureException("Unable to create Application Object", var16);
         }
      }

   }

   private boolean containsJ2EEModules() throws ToolFailureException {
      Getopt2 opts = this.ctx.getOpts();
      String[] args = opts.args();
      if (args.length != 1) {
         throw new IllegalArgumentException("more than one argument specified!");
      } else {
         try {
            File sourceFile = (new File(args[0])).getCanonicalFile();
            this.ctx.setSourceFile(sourceFile);
            VirtualJarFile vjf = VirtualJarFactory.createVirtualJar(sourceFile);
            this.ctx.setVSource(vjf);
            ApplicationBean appBean = ModuleDiscovery.discoverModules(this.ctx.getVSource());
            return appBean != null;
         } catch (IOException var6) {
            throw new ToolFailureException("Error processing source " + args[0], var6);
         }
      }
   }

   public void cleanup() throws ToolFailureException {
      try {
         super.cleanup();
      } finally {
         IOUtils.forceClose(this.ctx.getVSource());
      }

   }

   private ScaContributionBean parseSCAComposite(VirtualJarFile vjf) throws ToolFailureException {
      try {
         SAXParserFactory factory = XXEUtils.createSAXParserFactoryInstance();
         SAXParser parser = factory.newSAXParser();
         ZipEntry scaContribution = vjf.getEntry("META-INF/sca-contribution.xml");
         if (scaContribution == null) {
            throw new IllegalArgumentException("no META-INF/sca-contribution.xml");
         } else {
            ContributionHandler contributionHandler = new ContributionHandler();
            parser.parse(vjf.getInputStream(scaContribution), contributionHandler);
            ScaContributionBean scaContributionBean = this.createScaContributionBean();
            Iterator it = vjf.entries();

            while(it.hasNext()) {
               ZipEntry compositeFile = (ZipEntry)it.next();
               if (compositeFile.getName().matches("META-INF/.*[.]composite")) {
                  CompositeHandler compositeHandler = new CompositeHandler(scaContributionBean.createComposite());
                  parser.parse(vjf.getInputStream(compositeFile), compositeHandler);
               }
            }

            Set deployables = contributionHandler.getDeployables();
            CompositeBean[] var16 = scaContributionBean.getComposites();
            int var17 = var16.length;

            for(int var10 = 0; var10 < var17; ++var10) {
               CompositeBean composite = var16[var10];
               if (!deployables.contains(composite.getName())) {
                  scaContributionBean.destroyComposite(composite);
               }
            }

            return scaContributionBean;
         }
      } catch (IOException var12) {
         throw new ToolFailureException("Error parsing SCA descriptors", var12);
      } catch (ParserConfigurationException var13) {
         throw new ToolFailureException("Error parsing SCA descriptors", var13);
      } catch (SAXException var14) {
         throw new ToolFailureException("Error parsing SCA descriptors", var14);
      }
   }

   private ScaContributionBean createScaContributionBean() {
      DescriptorManager descriptorManager = new DescriptorManager();
      return (ScaContributionBean)descriptorManager.createDescriptorRoot(ScaContributionBean.class).getRootBean();
   }

   private static class CompositeHandler extends DefaultHandler {
      private final CompositeBean composite;
      private ComponentBean current;

      public CompositeHandler(CompositeBean composite) {
         this.composite = composite;
      }

      public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
         if (this.current == null) {
            if (qName.equals("component")) {
               this.current = this.composite.createComponent();
               this.current.setName(attributes.getValue("name"));
            } else if (qName.equals("composite")) {
               this.composite.setName(attributes.getValue("name"));
            }
         } else if (qName.startsWith("implementation.")) {
            this.current.setImplementationType(qName);
         }

      }

      public void endElement(String uri, String localName, String qName) throws SAXException {
         if (qName.equals("component")) {
            this.current = null;
         }

      }
   }

   private static class ContributionHandler extends DefaultHandler {
      HashSet deployables;

      private ContributionHandler() {
         this.deployables = new HashSet();
      }

      public Set getDeployables() {
         return this.deployables;
      }

      public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
         if (qName.equals("deployable")) {
            this.deployables.add(attributes.getValue("composite"));
         }

      }

      // $FF: synthetic method
      ContributionHandler(Object x0) {
         this();
      }
   }
}
