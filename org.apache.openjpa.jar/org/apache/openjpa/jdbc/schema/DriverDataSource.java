package org.apache.openjpa.jdbc.schema;

import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.openjpa.jdbc.sql.DBDictionary;

public interface DriverDataSource extends DataSource {
   void setConnectionURL(String var1);

   String getConnectionURL();

   void setConnectionDriverName(String var1);

   String getConnectionDriverName();

   void setConnectionUserName(String var1);

   String getConnectionUserName();

   void setConnectionPassword(String var1);

   void setClassLoader(ClassLoader var1);

   ClassLoader getClassLoader();

   void setConnectionFactoryProperties(Properties var1);

   Properties getConnectionFactoryProperties();

   void setConnectionProperties(Properties var1);

   Properties getConnectionProperties();

   List createConnectionDecorators();

   void initDBDictionary(DBDictionary var1);
}
