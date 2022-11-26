package weblogic.application.custom;

import weblogic.descriptor.Descriptor;

public interface DescriptorLookup {
   Descriptor get(String var1);

   Descriptor get(String var1, String var2);
}
