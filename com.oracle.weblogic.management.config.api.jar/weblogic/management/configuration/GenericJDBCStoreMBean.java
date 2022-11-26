package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface GenericJDBCStoreMBean extends ConfigurationMBean {
   String getPrefixName();

   void setPrefixName(String var1) throws InvalidAttributeValueException;

   String getCreateTableDDLFile();

   void setCreateTableDDLFile(String var1) throws InvalidAttributeValueException;
}
