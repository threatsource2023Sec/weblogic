package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;

public interface CharsetParamsMBean extends WebElementMBean {
   InputCharsetDescriptorMBean[] getInputCharsets();

   void setInputCharsets(InputCharsetDescriptorMBean[] var1);

   void addInputCharset(InputCharsetDescriptorMBean var1);

   void removeInputCharset(InputCharsetDescriptorMBean var1);

   CharsetMappingMBean[] getCharsetMappings();

   void setCharsetMappings(CharsetMappingMBean[] var1);

   void addCharsetMapping(CharsetMappingMBean var1);

   void removeCharsetMapping(CharsetMappingMBean var1);
}
