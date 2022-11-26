package weblogic.application.internal.flow;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.DeploymentContext;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.application.internal.AppDDHolder;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.IOUtils;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.utils.jars.VirtualJarFile;

public final class DescriptorParsingFlow extends BaseFlow {
   public DescriptorParsingFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      this.parseDDs();
      if (this.appCtx.getWLApplicationDD() != null) {
         this.appCtx.addUpdateListener(new WLAppUpdateListener(this.appCtx));
      } else {
         this.appCtx.addUpdateListener(new NullListener());
      }

   }

   public void start(String[] uris) throws DeploymentException {
      this.parseDDs();
   }

   private String getApplicationNameForPlan(String applicationName, String applicationFileName, DeploymentPlanBean planBean) {
      return planBean != null && planBean.findModuleOverride(applicationFileName) == null && planBean.findModuleOverride(applicationName) != null ? applicationName : applicationFileName;
   }

   private ApplicationDescriptor createAppDescriptor() throws ModuleException {
      if (this.isDebugEnabled()) {
         this.debug("Parsing application level descriptors");
      }

      VirtualJarFile vjar = null;
      AppDeploymentMBean dmb = this.appCtx.getAppDeploymentMBean();
      DeploymentPlanBean plan = dmb.getDeploymentPlanDescriptor();
      String altDDPath = dmb.getAltDescriptorPath();
      String altWlsDDPath = dmb.getAltWLSDescriptorPath();
      File altDD = null;
      File altWlsDD = null;
      if (altDDPath != null) {
         altDD = new File(altDDPath);
      }

      if (altWlsDDPath != null) {
         altWlsDD = new File(altWlsDDPath);
      }

      ApplicationDescriptor var10;
      try {
         if (this.isDebugEnabled()) {
            this.debug("Making a choice between app name & file name for plan overrides: " + this.appCtx.getApplicationName() + ", " + this.appCtx.getApplicationFileName());
         }

         vjar = this.appCtx.getApplicationFileManager().getVirtualJarFile();
         String appName = this.getApplicationNameForPlan(this.appCtx.getApplicationName(), this.appCtx.getApplicationFileName(), plan);
         if (this.isDebugEnabled()) {
            this.debug("Plan override choice made: " + appName);
         }

         ApplicationDescriptor appDesc = new ApplicationDescriptor(altDD, altWlsDD, vjar, EarUtils.getConfigDir(this.appCtx), plan, appName);
         if (this.isDebugEnabled()) {
            this.say("Parsed descriptor", appDesc);
         }

         var10 = appDesc;
      } catch (IOException var14) {
         throw new ModuleException(var14);
      } finally {
         IOUtils.forceClose(vjar);
      }

      return var10;
   }

   public void validateRedeploy(DeploymentContext deplCtx) throws DeploymentException {
      AppDDHolder appDDs = null;
      appDDs = this.parseDDs(this.createAppDescriptor());
      this.appCtx.setProposedPartialRedeployDDs(appDDs);
   }

   private void parseDDs() throws DeploymentException {
      ApplicationDescriptor appDesc = this.createAppDescriptor();

      try {
         this.appCtx.setApplicationDescriptor(appDesc);
      } catch (IOException var3) {
         throw new DeploymentException(var3);
      } catch (XMLStreamException var4) {
         throw new DeploymentException(var4);
      }
   }

   private AppDDHolder parseDDs(ApplicationDescriptor appDesc) throws DeploymentException {
      try {
         return new AppDDHolder(appDesc.getApplicationDescriptor(), appDesc.getWeblogicApplicationDescriptor(), appDesc.getWeblogicExtensionDescriptor());
      } catch (IOException var3) {
         throw new DeploymentException(var3);
      } catch (XMLStreamException var4) {
         throw new DeploymentException(var4);
      }
   }

   private void say(String msg, ApplicationDescriptor appDesc) {
      try {
         DescriptorBean app_xml = (DescriptorBean)appDesc.getApplicationDescriptor();
         DescriptorBean wlapp_xml = (DescriptorBean)appDesc.getWeblogicApplicationDescriptor();
         DescriptorBean wlext_xml = (DescriptorBean)appDesc.getWeblogicExtensionDescriptor();
         ByteArrayOutputStream baos;
         if (app_xml != null) {
            baos = new ByteArrayOutputStream();
            (new DescriptorManager()).writeDescriptorAsXML(app_xml.getDescriptor(), baos);
            this.debug(msg + "\n" + baos.toString());
            baos.close();
         }

         if (wlapp_xml != null) {
            baos = new ByteArrayOutputStream();
            (new DescriptorManager()).writeDescriptorAsXML(wlapp_xml.getDescriptor(), baos);
            this.debug(msg + "\n" + baos.toString());
            baos.close();
         }

         if (wlext_xml != null) {
            baos = new ByteArrayOutputStream();
            (new DescriptorManager()).writeDescriptorAsXML(wlext_xml.getDescriptor(), baos);
            this.debug(msg + "\n" + baos.toString());
            baos.close();
         }
      } catch (IOException var7) {
         var7.printStackTrace();
      } catch (XMLStreamException var8) {
         var8.printStackTrace();
      }

   }

   private static class NullListener implements UpdateListener {
      private NullListener() {
      }

      public boolean acceptURI(String uri) {
         return "META-INF/weblogic-application.xml".equals(uri);
      }

      public void prepareUpdate(String uri) {
      }

      public void activateUpdate(String uri) {
      }

      public void rollbackUpdate(String uri) {
      }

      // $FF: synthetic method
      NullListener(Object x0) {
         this();
      }
   }

   private class WLAppUpdateListener implements UpdateListener {
      private static final boolean DEBUG = false;
      private final ApplicationContextInternal appCtx;
      private Descriptor proposedDescriptor;
      private Descriptor currentDescriptor;

      private WLAppUpdateListener(ApplicationContextInternal appCtx) {
         this.appCtx = appCtx;
         WeblogicApplicationBean wldd = appCtx.getWLApplicationDD();
         this.currentDescriptor = ((DescriptorBean)wldd).getDescriptor();
      }

      private WeblogicApplicationBean parseNewWLDD() throws ModuleException {
         ApplicationDescriptor appDesc = DescriptorParsingFlow.this.createAppDescriptor();

         try {
            return appDesc.getWeblogicApplicationDescriptor();
         } catch (IOException var3) {
            throw new ModuleException(var3);
         } catch (XMLStreamException var4) {
            throw new ModuleException(var4);
         }
      }

      public boolean acceptURI(String uri) {
         return "META-INF/weblogic-application.xml".equals(uri);
      }

      public void prepareUpdate(String uri) throws ModuleException {
         WeblogicApplicationBean wldd = this.parseNewWLDD();
         this.proposedDescriptor = ((DescriptorBean)wldd).getDescriptor();

         try {
            this.currentDescriptor.prepareUpdate(this.proposedDescriptor);
         } catch (DescriptorUpdateRejectedException var4) {
            throw new ModuleException(var4);
         }
      }

      public void activateUpdate(String uri) throws ModuleException {
         try {
            this.currentDescriptor.activateUpdate();
         } catch (DescriptorUpdateFailedException var6) {
            throw new ModuleException(var6);
         } finally {
            this.proposedDescriptor = null;
         }

      }

      public void rollbackUpdate(String uri) {
         try {
            this.currentDescriptor.rollbackUpdate();
         } finally {
            this.proposedDescriptor = null;
         }

      }

      // $FF: synthetic method
      WLAppUpdateListener(ApplicationContextInternal x1, Object x2) {
         this(x1);
      }
   }
}
