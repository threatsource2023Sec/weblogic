package kodo.jdbc.conf.descriptor;

public interface SchemasBean {
   String[] getSchemas();

   void addSchema(String var1);

   void removeSchema(String var1);

   void setSchemas(String[] var1);
}
