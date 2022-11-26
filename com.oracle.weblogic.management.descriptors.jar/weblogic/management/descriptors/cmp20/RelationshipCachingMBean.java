package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBean;

public interface RelationshipCachingMBean extends XMLElementMBean {
   String getCachingName();

   void setCachingName(String var1);

   CachingElementMBean[] getCachingElements();

   void setCachingElements(CachingElementMBean[] var1);

   void addCachingElement(CachingElementMBean var1);

   void removeCachingElement(CachingElementMBean var1);
}
