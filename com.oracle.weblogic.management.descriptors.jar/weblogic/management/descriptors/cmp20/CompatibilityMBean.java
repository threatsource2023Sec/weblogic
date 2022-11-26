package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLDeclarationMBean;
import weblogic.management.descriptors.XMLElementMBean;

public interface CompatibilityMBean extends XMLElementMBean, XMLDeclarationMBean {
   boolean getByteArrayIsSerializedToOracleBlob();

   void setByteArrayIsSerializedToOracleBlob(boolean var1);

   boolean getAllowReadonlyCreateAndRemove();

   void setAllowReadonlyCreateAndRemove(boolean var1);

   boolean getLoadRelatedBeansFromDbInPostCreate();

   void setLoadRelatedBeansFromDbInPostCreate(boolean var1);
}
