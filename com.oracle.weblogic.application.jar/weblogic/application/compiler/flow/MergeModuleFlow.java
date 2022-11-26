package weblogic.application.compiler.flow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ModuleState;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.ToolsModuleExtension;
import weblogic.application.compiler.utils.ContextUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.utils.compiler.ToolFailureException;

public final class MergeModuleFlow extends CompilerFlow {
   public MergeModuleFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      Map allDescriptors = new HashMap();
      ToolsModule[] modules = this.ctx.getModules();

      for(int i = 0; i < modules.length; ++i) {
         ModuleState state = this.ctx.getModuleState(modules[i]);
         Map parsedDescriptors = modules[i].merge();
         Map moduleDescriptors = new HashMap(parsedDescriptors);
         state.initExtensions();
         Iterator var7 = state.getExtensions().iterator();

         while(var7.hasNext()) {
            ToolsModuleExtension modExtension = (ToolsModuleExtension)var7.next();
            Map extensionDescriptors = modExtension.merge(new HashMap(parsedDescriptors));
            if (extensionDescriptors != null) {
               parsedDescriptors.putAll(extensionDescriptors);
            }
         }

         state.setParsedDescriptors(parsedDescriptors);
         allDescriptors.put(state, moduleDescriptors.values().toArray(new DescriptorBean[0]));
      }

      ContextUtils.updateModuleContexts(allDescriptors, new HashSet());
   }

   public void cleanup() {
   }
}
