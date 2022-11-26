package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.PropertyBagBean;
import com.oracle.weblogic.lifecycle.config.PropertyBean;
import javax.inject.Singleton;

@Singleton
public class PropertyBagBeanCustomizer {
   public PropertyBean getProperty(PropertyBagBean me, String name) {
      return me.lookupProperty(name);
   }

   public String getPropertyValue(PropertyBagBean me, String name) {
      return this.getPropertyValue(me, name, (String)null);
   }

   public String getPropertyValue(PropertyBagBean me, String name, String defaultValue) {
      PropertyBean prop = this.getProperty(me, name);
      return prop != null ? prop.getValue() : defaultValue;
   }
}
