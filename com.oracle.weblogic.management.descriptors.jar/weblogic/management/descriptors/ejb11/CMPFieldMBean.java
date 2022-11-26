package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBean;

public interface CMPFieldMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getFieldName();

   void setFieldName(String var1);
}
