package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface AttributeMBean extends WebElementMBean {
   String getName();

   void setName(String var1);

   String getType();

   void setType(String var1);

   boolean isRequired();

   void setRequired(boolean var1);

   boolean isRtexpr();

   void setRtexpr(boolean var1);

   String getDescription();

   void setDescription(String var1);
}
