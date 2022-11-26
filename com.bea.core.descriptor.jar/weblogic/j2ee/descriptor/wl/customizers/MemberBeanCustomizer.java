package weblogic.j2ee.descriptor.wl.customizers;

import weblogic.descriptor.beangen.Customizer;

public interface MemberBeanCustomizer extends Customizer {
   void _postCreate();

   String getOverrideValue();

   void setOverrideValue(String var1);

   String getShortDescription();
}
