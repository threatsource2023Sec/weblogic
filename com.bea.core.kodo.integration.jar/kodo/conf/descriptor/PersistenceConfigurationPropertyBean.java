package kodo.conf.descriptor;

public interface PersistenceConfigurationPropertyBean {
   String getName();

   void setName(String var1) throws IllegalArgumentException;

   String getValue();

   void setValue(String var1) throws IllegalArgumentException;
}
