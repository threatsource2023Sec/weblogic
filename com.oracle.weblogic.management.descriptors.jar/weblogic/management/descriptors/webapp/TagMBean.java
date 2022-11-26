package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface TagMBean extends WebElementMBean {
   String getName();

   void setName(String var1);

   String getClassname();

   void setClassname(String var1);

   String getExtraInfoClassname();

   void setExtraInfoClassname(String var1);

   String getBodyContent();

   void setBodyContent(String var1);

   String getDescription();

   void setDescription(String var1);

   VariableMBean[] getVars();

   void setVars(VariableMBean[] var1);

   AttributeMBean[] getAtts();

   void setAtts(AttributeMBean[] var1);
}
