package weblogic.application.compiler.flow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.CustomModuleFactory;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ModuleState;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.custom.internal.DescriptorModuleFactory;
import weblogic.j2ee.descriptor.wl.CustomModuleBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.compiler.ToolFailureException;

public class CustomModuleFlow extends CompilerFlow {
   public CustomModuleFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      this.ctx.setCustomModules(this.initCustomModules());
   }

   public void cleanup() {
      ToolsModule[] customModules = this.ctx.getCustomModules();

      for(int i = 0; i < customModules.length; ++i) {
         customModules[i].cleanup();
         this.ctx.getModuleState(customModules[i]).cleanup();
      }

   }

   private ToolsModule[] initCustomModules() throws ToolFailureException {
      List moduleList = new ArrayList();
      WeblogicExtensionBean extDD = this.ctx.getWLExtensionDD();
      if (extDD != null) {
         this.initCustomModules(extDD.getCustomModules(), moduleList, this.ctx.getCustomModuleFactories());
      }

      Iterator var3 = DescriptorModuleFactory.createToolsModule((String)null, (String)null, ModuleType.EAR).iterator();

      while(var3.hasNext()) {
         ToolsModule descriptorModule = (ToolsModule)var3.next();
         this.setupCustomModule(descriptorModule);
         moduleList.add(descriptorModule);
      }

      return (ToolsModule[])((ToolsModule[])moduleList.toArray(new ToolsModule[0]));
   }

   private void initCustomModules(CustomModuleBean[] cm, List moduleList, Map customModuleFactories) throws ToolFailureException {
      if (cm != null && cm.length != 0) {
         for(int i = 0; i < cm.length; ++i) {
            CustomModuleFactory factory = (CustomModuleFactory)customModuleFactories.get(cm[i].getProviderName());
            if (factory == null) {
               throw new ToolFailureException("The custom module with the uri " + cm[i].getUri() + " specified a provider-name of " + cm[i].getProviderName() + ". However, there was no module-provider with this name in your weblogic-extension.xml.");
            }

            ToolsModule m = factory.createToolsModule(cm[i]);
            if (m != null) {
               this.setupCustomModule(m);
               moduleList.add(m);
            }
         }

      }
   }

   private void setupCustomModule(ToolsModule m) throws ToolFailureException {
      ModuleState state = this.ctx.createState(m);
      this.ctx.saveState(m, state);
      state.setOutputDir(this.ctx.getOutputDir());
      ClassFinder finder = m.init(state, this.ctx, this.ctx.getAppClassLoader());
      state.init(this.ctx.getAppClassLoader(), finder);
      state.setParsedDescriptors(m.merge());
   }
}
