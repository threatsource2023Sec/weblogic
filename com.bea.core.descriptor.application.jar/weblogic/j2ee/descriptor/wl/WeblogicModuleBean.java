package weblogic.j2ee.descriptor.wl;

public interface WeblogicModuleBean {
   String JDBC_TYPE = "JDBC";
   String JMS_TYPE = "JMS";
   String INTERCEPTION_TYPE = "Interception";
   String GAR_TYPE = "GAR";

   String getName();

   void setName(String var1);

   String getType();

   void setType(String var1);

   String getPath();

   void setPath(String var1);
}
