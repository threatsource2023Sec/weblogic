package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface VariableMBean extends WebElementMBean {
   String getName();

   void setName(String var1);

   String getType();

   void setType(String var1);

   boolean getNameFromAttribute();

   void setNameFromAttribute(boolean var1);

   boolean getDeclare();

   void setDeclare(boolean var1);

   int getScope();

   void setScope(int var1);

   String getScopeStr();

   void setScopeStr(String var1);

   String getDescription();

   void setDescription(String var1);
}
