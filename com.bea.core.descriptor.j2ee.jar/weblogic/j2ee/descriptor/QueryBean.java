package weblogic.j2ee.descriptor;

public interface QueryBean {
   String getDescription();

   void setDescription(String var1);

   QueryMethodBean getQueryMethod();

   QueryMethodBean createQueryMethod();

   void destroyQueryMethod(QueryMethodBean var1);

   String getResultTypeMapping();

   void setResultTypeMapping(String var1);

   String getEjbQl();

   void setEjbQl(String var1);

   String getId();

   void setId(String var1);

   void setNamedQueryName(String var1);

   String getNamedQueryName();
}
