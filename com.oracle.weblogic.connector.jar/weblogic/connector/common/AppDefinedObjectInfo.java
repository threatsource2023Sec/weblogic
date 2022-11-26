package weblogic.connector.common;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.resource.ResourceException;
import weblogic.connector.ConnectorLogger;
import weblogic.j2ee.descriptor.JavaEEPropertyBean;
import weblogic.j2ee.descriptor.PropertyBean;

public abstract class AppDefinedObjectInfo {
   private final UniversalResourceKey key;
   protected int refCount;
   protected final String resourceAdapter;
   private static final Comparator comparator = new Comparator() {
      public int compare(JavaEEPropertyBean o1, JavaEEPropertyBean o2) {
         return o1.getName().compareTo(o2.getName());
      }
   };

   public static final List createBeanProperties(String[] properties, Class cls) {
      List beanProperties = new ArrayList();
      String[] var3 = properties;
      int var4 = properties.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String property = var3[var5];

         try {
            Method readMethod = (new PropertyDescriptor(property, cls)).getReadMethod();
            beanProperties.add(new PropertyItem(readMethod, property, cls));
         } catch (Exception var8) {
            throw new IllegalStateException(var8);
         }
      }

      return beanProperties;
   }

   public AppDefinedObjectInfo(UniversalResourceKey key, String resourceAdapter) {
      this.key = key;
      this.refCount = 1;
      this.resourceAdapter = resourceAdapter;
   }

   public void checkCompatible(UniversalResourceKey targetKey, PropertyBean targetBean) throws ResourceException {
      if (!this.key.getDefApp().equals(targetKey.getDefApp())) {
         throw new ResourceException(ConnectorLogger.getExceptionDuplicatedResourceDefinition(this.resourceAdapter, this.key.getDefApp(), this.key.getDefComp(), this.key.getDefComp(), this.key.getJndi()));
      }
   }

   protected boolean compare(Object expectValue, Object value) {
      if (value != null && expectValue != null) {
         return value.equals(expectValue);
      } else {
         return value == expectValue;
      }
   }

   protected boolean compareProperties(JavaEEPropertyBean[] expectProperties, JavaEEPropertyBean[] properties) {
      if (properties != null && expectProperties != null) {
         if (expectProperties.length != properties.length) {
            return false;
         } else {
            Arrays.sort(expectProperties, comparator);
            Arrays.sort(properties, comparator);

            for(int i = 0; i < properties.length; ++i) {
               if (!this.compare(expectProperties[i].getName(), properties[i].getName())) {
                  return false;
               }

               if (!this.compare(expectProperties[i].getValue(), properties[i].getValue())) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return properties == expectProperties;
      }
   }

   public UniversalResourceKey getKey() {
      return this.key;
   }
}
