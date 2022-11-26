package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBean;

public interface ResourceRefMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getResRefName();

   void setResRefName(String var1);

   String getResType();

   void setResType(String var1);

   String getResAuth();

   void setResAuth(String var1);
}
