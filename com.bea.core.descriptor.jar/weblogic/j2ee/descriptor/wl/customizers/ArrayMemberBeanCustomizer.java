package weblogic.j2ee.descriptor.wl.customizers;

import weblogic.descriptor.beangen.Customizer;

public interface ArrayMemberBeanCustomizer extends Customizer {
   void _postCreate();

   String[] getOverrideValues();

   void setOverrideValues(String[] var1);

   String getShortDescription();
}
