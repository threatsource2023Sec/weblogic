package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBean;

public interface CMRFieldMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getCMRFieldName();

   void setCMRFieldName(String var1);

   void setCMRFieldType(String var1);

   String getCMRFieldType();
}
