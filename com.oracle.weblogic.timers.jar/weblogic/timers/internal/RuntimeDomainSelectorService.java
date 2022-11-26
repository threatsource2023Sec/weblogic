package weblogic.timers.internal;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.server.AbstractServerService;
import weblogic.timers.RuntimeDomainSelector;

@Service
@Named
@RunLevel(5)
public class RuntimeDomainSelectorService extends AbstractServerService {
   public void start() {
      RuntimeDomainSelector.setSelector(new RuntimeDomainSelector() {
         public String getRuntimeDomain() {
            ComponentInvocationContext componentInvocationContext = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
            return componentInvocationContext != null && !componentInvocationContext.isGlobalRuntime() ? componentInvocationContext.getPartitionId() : "weblogic.timers.defaultDomain";
         }
      });
   }
}
