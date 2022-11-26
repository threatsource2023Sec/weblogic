package weblogic.management.internal.mbean;

import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.HashMap;
import java.util.Map;

public class BeanInfoImpl extends SimpleBeanInfo {
   BeanDescriptor beanDescriptor = null;
   PropertyDescriptor[] propertyDescriptors = null;
   MethodDescriptor[] methodDescriptors = null;
   EventSetDescriptor[] eventSetDescriptors = null;
   long lastAccess = 0L;
   private static EventSetDescriptor[] emptyEventSetDescriptor = new EventSetDescriptor[0];
   private boolean methodsInitialized;
   private boolean propsInitialized;
   protected boolean readOnly = false;
   protected String targetVersion = null;
   private String[] disabledProperties;

   public BeanInfoImpl(boolean readOnly, String targetVersion) throws IntrospectionException {
      this.readOnly = readOnly;
      this.targetVersion = targetVersion;
      this.initialize();
   }

   public BeanInfoImpl() throws IntrospectionException {
      this.initialize();
   }

   private void initialize() throws IntrospectionException {
      this.beanDescriptor = this.buildBeanDescriptor();
      this.eventSetDescriptors = emptyEventSetDescriptor;
   }

   public void disableProperties(String... disabled) {
      this.disabledProperties = disabled;
   }

   private void initializePropertDescriptors() {
      if (!this.propsInitialized) {
         Map descriptors = new HashMap(32);

         try {
            this.buildPropertyDescriptors(descriptors);
            if (this.disabledProperties != null) {
               String[] var2 = this.disabledProperties;
               int var3 = var2.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  String disabled = var2[var4];
                  descriptors.remove(disabled);
               }
            }

            this.propertyDescriptors = (PropertyDescriptor[])((PropertyDescriptor[])descriptors.values().toArray(new PropertyDescriptor[descriptors.size()]));
         } catch (IntrospectionException var9) {
            throw new RuntimeException(var9);
         } finally {
            this.propsInitialized = true;
         }

      }
   }

   private void initializeMethodDescriptors() {
      if (!this.methodsInitialized) {
         Map descriptors = new HashMap(8);

         try {
            this.buildMethodDescriptors(descriptors);
            this.methodDescriptors = (MethodDescriptor[])((MethodDescriptor[])descriptors.values().toArray(new MethodDescriptor[descriptors.size()]));
         } catch (NoSuchMethodException var7) {
            throw new RuntimeException(new IntrospectionException(var7.getMessage()));
         } catch (IntrospectionException var8) {
            throw new RuntimeException(var8);
         } finally {
            this.methodsInitialized = true;
         }

      }
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }

   protected void buildMethodDescriptors(Map descriptors) throws NoSuchMethodException, IntrospectionException {
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      return null;
   }

   protected static ParameterDescriptor createParameterDescriptor(String name, String description) {
      ParameterDescriptor descriptor = new ParameterDescriptor();
      descriptor.setName(name);
      if (description != null) {
         descriptor.setShortDescription(description);
      }

      return descriptor;
   }

   protected static void setPropertyDescriptorDefault(PropertyDescriptor pd, Object value) {
      if (value == null) {
         pd.setValue("defaultValueNull", Boolean.TRUE);
      } else {
         pd.setValue("default", value);
      }

   }

   public BeanInfoImpl(BeanDescriptor bd, PropertyDescriptor[] pd, MethodDescriptor[] md, EventSetDescriptor[] esd) {
      this.beanDescriptor = bd;
      this.propertyDescriptors = pd;
      this.methodDescriptors = md;
      this.eventSetDescriptors = esd;
   }

   public BeanDescriptor getBeanDescriptor() {
      return this.beanDescriptor;
   }

   public EventSetDescriptor[] getEventSetDescriptors() {
      return this.eventSetDescriptors;
   }

   public MethodDescriptor[] getMethodDescriptors() {
      this.initializeMethodDescriptors();
      return this.methodDescriptors;
   }

   public PropertyDescriptor[] getPropertyDescriptors() {
      this.initializePropertDescriptors();
      return this.propertyDescriptors;
   }

   public void setLastAccess(long access) {
      this.lastAccess = access;
   }

   public long getLastAccess() {
      return this.lastAccess;
   }
}
