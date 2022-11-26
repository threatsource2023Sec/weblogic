package kodo.jdbc.conf.descriptor;

public interface KodoSQLFactoryBean extends SQLFactoryBean {
   String getAdvancedSQL();

   void setAdvancedSQL(String var1);
}
