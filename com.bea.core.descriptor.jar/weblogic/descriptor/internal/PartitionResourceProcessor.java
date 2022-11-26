package weblogic.descriptor.internal;

import org.jvnet.hk2.annotations.Contract;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorPreNotifyProcessor;

@Contract
public interface PartitionResourceProcessor extends DescriptorPreNotifyProcessor {
   void process(DescriptorDiff var1) throws Exception;
}
