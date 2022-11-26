package weblogic.management.workflow;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DescriptorLock {
   DescriptorLockHandle lock(long var1);
}
