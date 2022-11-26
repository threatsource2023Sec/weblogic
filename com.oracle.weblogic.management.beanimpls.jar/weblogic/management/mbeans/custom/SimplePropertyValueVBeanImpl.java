package weblogic.management.mbeans.custom;

import weblogic.management.configuration.PropertyValueVBean;
import weblogic.management.configuration.SimplePropertyValueVBean;

public class SimplePropertyValueVBeanImpl implements SimplePropertyValueVBean {
   private String propertyName;
   private Object effectiveValue;

   public SimplePropertyValueVBeanImpl() {
      this.effectiveValue = null;
   }

   public SimplePropertyValueVBeanImpl(String propertyName, Object value) {
      this.effectiveValue = null;
      this.setPropertyName(propertyName);
      this.effectiveValue = value;
   }

   public SimplePropertyValueVBeanImpl(String propertyName) {
      this.effectiveValue = null;
      this.setPropertyName(propertyName);
   }

   public SimplePropertyValueVBeanImpl(PropertyValueVBean propValue) {
      this(propValue.getPropertyName(), propValue.getEffectiveValue());
   }

   public void setPropertyName(String propertyName) {
      this.propertyName = propertyName;
   }

   public String getPropertyName() {
      return this.propertyName;
   }

   public Object getEffectiveValue() {
      return this.effectiveValue;
   }
}
