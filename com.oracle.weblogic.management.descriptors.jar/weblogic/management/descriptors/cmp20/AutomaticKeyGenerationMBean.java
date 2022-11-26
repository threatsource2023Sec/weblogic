package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBean;

public interface AutomaticKeyGenerationMBean extends XMLElementMBean {
   void setGeneratorType(String var1);

   String getGeneratorType();

   String getGeneratorName();

   void setGeneratorName(String var1);

   int getKeyCacheSize();

   void setKeyCacheSize(int var1);

   boolean getSelectFirstSequenceKeyBeforeUpdate();

   void setSelectFirstSequenceKeyBeforeUpdate(boolean var1);
}
