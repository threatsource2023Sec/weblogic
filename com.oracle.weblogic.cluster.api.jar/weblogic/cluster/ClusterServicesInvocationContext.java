package weblogic.cluster;

import org.jvnet.hk2.annotations.Contract;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;

@Contract
public interface ClusterServicesInvocationContext {
   ManagedInvocationContext setInvocationContext(String var1);

   ManagedInvocationContext setInvocationContext(ComponentInvocationContext var1);

   ManagedInvocationContext setInvocationContext(String var1, String var2);
}
