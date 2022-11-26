package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBean;

public interface CachingElementMBean extends XMLElementMBean {
   String getCmrField();

   void setCmrField(String var1);

   String getGroupName();

   void setGroupName(String var1);

   CachingElementMBean[] getCachingElements();

   void setCachingElements(CachingElementMBean[] var1);

   void addCachingElement(CachingElementMBean var1);

   void removeCachingElement(CachingElementMBean var1);
}
