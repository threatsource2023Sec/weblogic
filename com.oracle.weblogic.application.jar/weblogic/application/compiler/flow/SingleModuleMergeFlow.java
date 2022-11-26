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

public final class SingleModuleMergeFlow extends SingleModuleFlow {
   public SingleModuleMergeFlow(CompilerCtx ctx) {
      super(ctx);
   }

   protected void proecessModule(ToolsModule module) throws ToolFailureException {
      ModuleState state = this.ctx.getModuleState(module);
      Map parsedDescriptors = module.merge();
      Map moduleDescriptors = new HashMap(parsedDescriptors);
      state.initExtensions();
      Iterator var5 = state.getExtensions().iterator();

      while(var5.hasNext()) {
         ToolsModuleExtension modExtension = (ToolsModuleExtension)var5.next();
         Map extensionDescriptors = modExtension.merge(new HashMap(parsedDescriptors));
         if (extensionDescriptors != null) {
            parsedDescriptors.putAll(extensionDescriptors);
         }
      }

      state.setParsedDescriptors(parsedDescriptors);
      Map allDescriptors = new HashMap();
      allDescriptors.put(state, moduleDescriptors.values().toArray(new DescriptorBean[0]));
      ContextUtils.updateModuleContexts(allDescriptors, new HashSet());
   }
}
