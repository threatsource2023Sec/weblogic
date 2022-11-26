package weblogic.management.configuration;

public interface ParameterMBean extends ConfigurationMBean {
   String getName();

   void setName(String var1);

   String getValue();

   void setValue(String var1);

   String getDescription();

   void setDescription(String var1);
}
