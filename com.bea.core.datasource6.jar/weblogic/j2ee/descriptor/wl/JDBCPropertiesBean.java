package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface JDBCPropertiesBean extends SettableBean {
   JDBCPropertyBean[] getProperties();

   JDBCPropertyBean createProperty(String var1);

   JDBCPropertyBean createProperty(String var1, String var2);

   JDBCPropertyBean lookupProperty(String var1);

   void destroyProperty(JDBCPropertyBean var1);
}
