package org.apache.openjpa.slice;

import java.util.List;
import org.apache.openjpa.conf.OpenJPAConfiguration;

public interface DistributedConfiguration extends OpenJPAConfiguration {
   List getActiveSliceNames();

   List getAvailableSliceNames();

   List getSlices(Slice.Status... var1);

   Slice getSlice(String var1);

   DistributionPolicy getDistributionPolicyInstance();
}
