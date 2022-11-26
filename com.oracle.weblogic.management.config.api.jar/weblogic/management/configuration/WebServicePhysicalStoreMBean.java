package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.ManagementException;

public interface WebServicePhysicalStoreMBean extends ConfigurationMBean {
   String STANDALONE_CLIENT_STORE_DIR_PROP = "weblogic.wsee.persistence.webservice-client.dir";
   String FILE = "FILE";
   String JDBC = "JDBC";
   String DISABLED = "DISABLED";
   String CACHE_FLUSH = "CACHE_FLUSH";
   String DIRECT_WRITE = "DIRECT_WRITE";

   String getName();

   void setName(String var1) throws InvalidAttributeValueException, ManagementException;

   void setStoreType(String var1);

   String getStoreType();

   String getLocation();

   void setLocation(String var1);

   String getSynchronousWritePolicy();

   void setSynchronousWritePolicy(String var1);
}
