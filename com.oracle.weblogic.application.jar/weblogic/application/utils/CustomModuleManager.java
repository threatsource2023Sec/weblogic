package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import weblogic.application.CustomModuleFactory;
import weblogic.application.ModuleContext;
import weblogic.application.ParentModule;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ModuleState;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.ToolsModuleWrapper;
import weblogic.application.custom.internal.DescriptorModuleFactory;
import weblogic.application.internal.flow.CustomModuleHelper;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.j2ee.descriptor.wl.CustomModuleBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.management.DeploymentException;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFile;

public class CustomModuleManager extends ToolsModuleWrapper {
   private final ToolsModule parentModule;
   private ToolsModule[] scopedModules;
   private Map moduleStateMap = new HashMap();
   private CompilerCtx ctx;
   private ModuleContext modCtx;
   private WeblogicExtensionBean extDD;
   private final String extDescriptorUri;
   private WeblogicExtensionBean earExtDD = null;

   public CustomModuleManager(ToolsModule parentModule) {
      this.parentModule = parentModule;
      if (!(parentModule instanceof ParentModule)) {
         throw new AssertionError("Scoping module must be an instance of ParentModule " + parentModule);
      } else {
         String wlExtensionDirectory = ((ParentModule)parentModule).getWLExtensionDirectory();
         if (wlExtensionDirectory == null) {
            this.extDescriptorUri = null;
         } else {
            this.extDescriptorUri = wlExtensionDirectory + "/" + "weblogic-extension.xml";
         }

      }
   }

   public ToolsModule getDelegate() {
      return this.parentModule;
   }

   public ClassFinder init(ModuleContext modCtx, ToolsContext ctx, GenericClassLoader parentClassLoader) throws ToolFailureException {
      this.ctx = (CompilerCtx)ctx;
      this.modCtx = modCtx;
      return super.init(modCtx, ctx, parentClassLoader);
   }

   public Map merge() throws ToolFailureException {
      Map parsedDescriptors = super.merge();
      if (this.extDD != null) {
         parsedDescriptors.put(this.extDescriptorUri, (DescriptorBean)this.extDD);
      }

      if (!this.ctx.isBasicView()) {
         try {
            this.scopedModules = this.createScopedCustomModules(this.modCtx.getClassLoader());
            ToolsModule[] var2 = this.scopedModules;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               ToolsModule customModule = var2[var4];
               ModuleState state = (ModuleState)this.moduleStateMap.get(customModule);
               ClassFinder finder = customModule.init(state, this.ctx, this.modCtx.getClassLoader());
               state.init(this.modCtx.getClassLoader(), finder);
               state.setOutputDir(this.ctx.getModuleState(this.parentModule).getOutputDir());
               state.setParsedDescriptors(customModule.merge());
               String[] descriptorUris = state.getDescriptorUris();

               for(int index = 0; index < descriptorUris.length; ++index) {
                  parsedDescriptors.put(descriptorUris[index], state.getDescriptor(descriptorUris[index]));
               }
            }
         } catch (ToolFailureException var10) {
            if (this.extDD == null && this.earExtDD != null) {
               this.scopedModules = new ToolsModule[0];
            }

            throw var10;
         }
      }

      return parsedDescriptors;
   }

   public void write() throws ToolFailureException {
      super.write();

      for(int count = 0; count < this.scopedModules.length; ++count) {
         this.scopedModules[count].write();
      }

      File f;
      if (this.extDD != null) {
         try {
            f = new File(this.ctx.getModuleState(this.parentModule).getOutputDir(), this.extDescriptorUri);
            DescriptorUtils.writeDescriptor(new EditableDescriptorManager(), (DescriptorBean)this.extDD, f);
         } catch (IOException var3) {
            throw new ToolFailureException("Unable to write out " + this.extDescriptorUri, var3);
         }
      } else if (this.earExtDD != null && this.scopedModules.length > 0) {
         try {
            f = new File(this.ctx.getModuleState(this.parentModule).getOutputDir(), this.extDescriptorUri);
            DescriptorUtils.writeDescriptor(new EditableDescriptorManager(), (DescriptorBean)this.earExtDD, f);
         } catch (IOException var2) {
            throw new ToolFailureException("Unable to write out " + this.extDescriptorUri, var2);
         }
      }

   }

   public void cleanup() {
      super.cleanup();
      if (this.scopedModules != null) {
         ToolsModule[] var1 = this.scopedModules;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            ToolsModule scopedModule = var1[var3];
            scopedModule.cleanup();
         }
      }

   }

   private ToolsModule[] createScopedCustomModules(GenericClassLoader parentModuleClassLoader) throws ToolFailureException {
      List modules = new ArrayList();
      if (this.extDescriptorUri != null) {
         ExtensionDescriptorParser extLoader = new ExtensionDescriptorParser(this.ctx.getModuleState(this.parentModule).getVirtualJarFile(), this.ctx.getConfigDir(), this.ctx.getPlanBean(), this.parentModule.getURI(), this.extDescriptorUri);
         VirtualJarFile[] libVjfs = null;

         try {
            libVjfs = LibraryUtils.getLibraryVjarsWithDescriptor(this.ctx.getLibraryProvider(this.parentModule.getURI()), this.extDescriptorUri);
            extLoader.mergeWlExtensionDescriptorsFromLibraries(libVjfs);
            this.extDD = extLoader.getWlExtensionDescriptor();
            this.earExtDD = this.ctx.getWLExtensionDD();
            Map factories;
            CustomModuleBean[] cmBeans;
            CustomModuleFactory factory;
            ToolsModule customModule;
            ModuleState state;
            int count;
            if (this.extDD != null) {
               factories = CustomModuleHelper.initFactories(this.extDD, parentModuleClassLoader, this.parentModule.getURI(), this.parentModule.getURI());
               if (factories != null) {
                  cmBeans = this.extDD.getCustomModules();

                  for(count = 0; count < cmBeans.length; ++count) {
                     factory = (CustomModuleFactory)factories.get(cmBeans[count].getProviderName());
                     customModule = factory.createToolsModule(cmBeans[count]);
                     if (customModule != null) {
                        modules.add(customModule);
                        state = this.ctx.createState(customModule);
                        this.moduleStateMap.put(customModule, state);
                     }
                  }
               }
            } else if (this.earExtDD != null) {
               factories = null;

               try {
                  factories = CustomModuleHelper.initFactories(this.earExtDD, parentModuleClassLoader, this.parentModule.getURI(), this.parentModule.getURI());
               } catch (DeploymentException var17) {
                  ToolsModule[] var7 = new ToolsModule[0];
                  return var7;
               }

               if (factories != null) {
                  cmBeans = this.earExtDD.getCustomModules();

                  for(count = 0; count < cmBeans.length; ++count) {
                     factory = (CustomModuleFactory)factories.get(cmBeans[count].getProviderName());
                     customModule = factory.createToolsModule(cmBeans[count]);
                     if (customModule != null) {
                        modules.add(customModule);
                        state = this.ctx.createState(customModule);
                        this.moduleStateMap.put(customModule, state);
                     }
                  }
               }
            }
         } catch (XMLStreamException var18) {
            throw new ToolFailureException("Problem loading or merging weblogic-extension.xml for parent module", var18);
         } catch (IOException var19) {
            throw new ToolFailureException("Problem loading or merging weblogic-extension.xml for parent module", var19);
         } catch (DeploymentException var20) {
            throw new ToolFailureException("Unable to create custom module factories", var20);
         } finally {
            IOUtils.forceClose(libVjfs);
         }
      }

      Iterator var22 = DescriptorModuleFactory.createToolsModule(this.parentModule.getURI(), this.parentModule.getURI(), this.parentModule.getModuleType()).iterator();

      while(var22.hasNext()) {
         ToolsModule descriptorModule = (ToolsModule)var22.next();
         this.moduleStateMap.put(descriptorModule, this.ctx.createState(descriptorModule));
         modules.add(descriptorModule);
      }

      return (ToolsModule[])((ToolsModule[])modules.toArray(new ToolsModule[0]));
   }
}
