package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface TypeMappingMBean extends XMLElementMBean {
   TypeMappingEntryMBean[] getTypeMappingEntries();

   void setTypeMappingEntries(TypeMappingEntryMBean[] var1);

   void addTypeMappingEntry(TypeMappingEntryMBean var1);

   void removeTypeMappingEntry(TypeMappingEntryMBean var1);
}
