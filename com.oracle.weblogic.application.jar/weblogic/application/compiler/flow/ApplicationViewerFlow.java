package weblogic.application.compiler.flow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import javax.xml.stream.XMLStreamException;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ToolsExtension;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.deploymentview.EditableDeployableObjectFactory;
import weblogic.application.compiler.deploymentview.EditableJ2eeApplicationObject;
import weblogic.application.compiler.utils.ApplicationResourceFinder;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.compiler.ToolFailureException;

public class ApplicationViewerFlow extends ModuleViewerFlow {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainerTools");

   public ApplicationViewerFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      EditableDeployableObjectFactory objectFactory = this.ctx.getObjectFactory();
      ToolsModule[] modules = this.ctx.getModules();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("No. of regular modules found " + modules.length);
      }

      if (objectFactory != null) {
         List deployableObjects = new ArrayList();

         EditableJ2eeApplicationObject deployableApplication;
         try {
            deployableApplication = objectFactory.createApplicationObject();
            deployableApplication.setVirtualJarFile(this.ctx.getVSource());
            deployableApplication.setRootBean((DescriptorBean)this.ctx.getApplicationDescriptor().getApplicationDescriptor());
            deployableApplication.addRootBean("META-INF/application.xml", (DescriptorBean)this.ctx.getApplicationDescriptor().getApplicationDescriptor(), (ModuleType)null);
            deployableApplication.addRootBean("META-INF/weblogic-application.xml", (DescriptorBean)this.ctx.getApplicationDescriptor().getWeblogicApplicationDescriptor(), (ModuleType)null);
            deployableApplication.addRootBean("META-INF/weblogic-extension.xml", (DescriptorBean)this.ctx.getApplicationDescriptor().getWeblogicExtensionDescriptor(), (ModuleType)null);
         } catch (XMLStreamException var12) {
            throw new ToolFailureException("Unable to create Application Object", var12);
         } catch (IOException var13) {
            throw new ToolFailureException("Unable to create Application Object", var13);
         }

         EditableDeployableObject deployableObject;
         try {
            for(int count = 0; count < modules.length; ++count) {
               if (!modules[count].isDeployableObject()) {
                  this.addModuleDescriptor(deployableApplication, modules[count]);
               } else {
                  deployableObject = this.ctx.getModuleState(modules[count]).buildDeploymentView(objectFactory);
                  modules[count].enhanceDeploymentView(deployableObject);
                  deployableObjects.add(deployableObject);
               }
            }
         } catch (IOException var14) {
            throw new ToolFailureException("Unable to create deployable object", var14);
         }

         this.addCustomeModuleDescriptors(deployableApplication);
         deployableApplication.setClassLoader(this.ctx.getAppClassLoader());
         deployableApplication.setResourceFinder(new ApplicationResourceFinder(this.ctx.getEar().getURI(), this.ctx.getAppClassLoader().getClassFinder()));
         this.ctx.setDeployableApplication(deployableApplication);
         Iterator iter = deployableObjects.iterator();

         while(iter.hasNext()) {
            deployableObject = (EditableDeployableObject)iter.next();
            deployableApplication.addDeployableObject(deployableObject);
         }

         ToolsExtension[] var16 = this.ctx.getToolsExtensions();
         int var17 = var16.length;

         for(int var7 = 0; var7 < var17; ++var7) {
            ToolsExtension extension = var16[var7];
            Map rootBeans = extension.merge();
            if (rootBeans != null) {
               Iterator var10 = rootBeans.keySet().iterator();

               while(var10.hasNext()) {
                  String uri = (String)var10.next();
                  deployableApplication.addRootBean(uri, (DescriptorBean)rootBeans.get(uri), (ModuleType)null);
               }
            }
         }
      }

   }

   public void cleanup() {
   }

   private void addCustomeModuleDescriptors(EditableJ2eeApplicationObject applicationObject) {
      ToolsModule[] customModules = this.ctx.getCustomModules();
      if (customModules != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No. of custom modules found " + customModules.length);
         }

         for(int i = 0; i < customModules.length; ++i) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Adding descriptor for CustomModule " + customModules[i].getURI());
            }

            this.addModuleDescriptor(applicationObject, customModules[i]);
         }

      }
   }

   private void addModuleDescriptor(EditableJ2eeApplicationObject applicationObject, ToolsModule module) {
      String[] descriptorUris = this.ctx.getModuleState(module).getDescriptorUris();

      for(int count = 0; count < descriptorUris.length; ++count) {
         DescriptorBean descriptorBean = this.ctx.getModuleState(module).getDescriptor(descriptorUris[count]);
         if (descriptorBean != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Adding descriptor " + descriptorUris[count]);
            }

            applicationObject.addRootBean(descriptorUris[count], descriptorBean, module.getModuleType());
         }
      }

   }
}
