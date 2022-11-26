package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface PersistenceUseMBean extends XMLElementMBean {
   String getTypeIdentifier();

   void setTypeIdentifier(String var1);

   String getTypeVersion();

   void setTypeVersion(String var1);

   String getTypeStorage();

   void setTypeStorage(String var1);
}
