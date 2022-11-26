package weblogic.j2ee.descriptor.wl;

public interface VariableAssignmentBean {
   String ADD = "add";
   String REMOVE = "remove";
   String REPLACE = "replace";
   String PLANBASED = "planbased";
   String NOTPLANBASED = "external";

   String getDescription();

   void setDescription(String var1);

   String getName();

   void setName(String var1);

   String getXpath();

   void setXpath(String var1);

   String getOrigin();

   void setOrigin(String var1);

   String getOperation();

   void setOperation(String var1);
}
