package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.PropertyBean;
import com.oracle.weblogic.lifecycle.config.Runtime;
import com.oracle.weblogic.lifecycle.config.Runtimes;
import com.oracle.weblogic.lifecycle.properties.ConfidentialPropertyValue;
import com.oracle.weblogic.lifecycle.properties.PropertyValueFactory;
import com.oracle.weblogic.lifecycle.properties.StringPropertyValue;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.xml.api.XmlService;

@Singleton
public class RuntimesCustomizer {
   @Inject
   private XmlService xmlService;

   public Runtime getRuntimeByName(Runtimes runtimes, String name) {
      return runtimes.lookupRuntime(name);
   }

   public Runtime createRuntime(Runtimes runtimes, Map properties) {
      Objects.requireNonNull(properties);
      Runtime runtime = (Runtime)this.xmlService.createBean(Runtime.class);
      Iterator var4 = properties.keySet().iterator();

      while(true) {
         while(true) {
            String propertyName;
            do {
               if (!var4.hasNext()) {
                  return runtimes.addRuntime(runtime);
               }

               propertyName = (String)var4.next();
            } while(propertyName == null);

            Object value = properties.get(propertyName);
            Objects.requireNonNull(value);
            String propertyValue;
            if (value instanceof StringPropertyValue) {
               propertyValue = ((StringPropertyValue)value).getValue();
            } else if (value instanceof ConfidentialPropertyValue) {
               propertyValue = ((ConfidentialPropertyValue)value).getEncryptedValue();
            } else {
               propertyValue = value.toString();
            }

            if (propertyName.equalsIgnoreCase("type")) {
               runtime.setType(propertyValue);
            } else if (propertyName.equalsIgnoreCase("name")) {
               try {
                  runtime.setName(propertyValue);
               } catch (Exception var10) {
                  throw new RuntimeException(var10);
               }
            } else if (propertyName.equalsIgnoreCase("hostname")) {
               runtime.setHostname(propertyValue);
            } else if (propertyName.equalsIgnoreCase("port")) {
               runtime.setPort(propertyValue);
            } else if (propertyName.equalsIgnoreCase("protocol")) {
               runtime.setProtocol(propertyValue);
            } else {
               PropertyBean property = (PropertyBean)this.xmlService.createBean(PropertyBean.class);

               try {
                  if (propertyName.equalsIgnoreCase("password") && value instanceof StringPropertyValue) {
                     ConfidentialPropertyValue cp = PropertyValueFactory.getConfidentialPropertyValue(propertyValue);
                     propertyValue = cp.getEncryptedValue();
                  }

                  property.setName(propertyName);
                  property.setValue(propertyValue);
               } catch (Exception var11) {
                  throw new RuntimeException(var11);
               }

               runtime.addProperty(property);
            }
         }
      }
   }

   public Runtime deleteRuntime(Runtimes runtimes, Runtime runtime) {
      return runtimes.removeRuntime(runtime);
   }
}
