package org.apache.openjpa.lib.conf;

import java.beans.BeanInfo;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Map;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.log.LogFactory;
import org.apache.openjpa.lib.util.Closeable;

public interface Configuration extends BeanInfo, Serializable, Closeable, Cloneable {
   String ATTRIBUTE_ALLOWED_VALUES = "allowedValues";
   String ATTRIBUTE_TYPE = "propertyType";
   String ATTRIBUTE_CATEGORY = "propertyCategory";
   String ATTRIBUTE_ORDER = "propertyCategoryOrder";
   String ATTRIBUTE_INTERFACE = "propertyInterface";
   String ATTRIBUTE_XML = "xmlName";
   int INIT_STATE_LIQUID = 0;
   int INIT_STATE_FREEZING = 1;
   int INIT_STATE_FROZEN = 2;

   String getProductName();

   void setProductName(String var1);

   LogFactory getLogFactory();

   void setLogFactory(LogFactory var1);

   String getLog();

   void setLog(String var1);

   Log getLog(String var1);

   Log getConfigurationLog();

   String getId();

   void setId(String var1);

   Value getValue(String var1);

   Value[] getValues();

   Value addValue(Value var1);

   boolean removeValue(Value var1);

   Map toProperties(boolean var1);

   void fromProperties(Map var1);

   void addPropertyChangeListener(PropertyChangeListener var1);

   void removePropertyChangeListener(PropertyChangeListener var1);

   void setReadOnly(int var1);

   boolean isReadOnly();

   void instantiateAll();

   void close();

   Object clone();
}
