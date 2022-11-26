package weblogic.application.descriptor;

import weblogic.descriptor.DescriptorBean;

public interface PredicateMatcher {
   boolean match(DescriptorBean var1, Object var2, Object var3);
}
