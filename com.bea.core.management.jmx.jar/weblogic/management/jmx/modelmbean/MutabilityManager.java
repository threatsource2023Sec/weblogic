package weblogic.management.jmx.modelmbean;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface MutabilityManager {
   boolean isImmutableSubtreeRoot(Object var1);
}
