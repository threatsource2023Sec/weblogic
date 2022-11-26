package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationProperty;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Factory;

public abstract class AbstractProvisioningOperationPropertyValueFactory implements Factory {
   private final ActiveDescriptor myDescriptor;
   private final ProvisioningOperationPropertyValueProvider provider;
   private final Type type;
   private volatile String name;
   private volatile String defaultValue;

   protected AbstractProvisioningOperationPropertyValueFactory(ActiveDescriptor myDescriptor, ProvisioningOperationPropertyValueProvider provider) {
      Objects.requireNonNull(myDescriptor);
      Objects.requireNonNull(provider);
      Type supertype = this.getClass().getGenericSuperclass();

      assert supertype != null;

      if (supertype instanceof Class) {
         throw new IllegalStateException();
      } else {
         this.type = ((ParameterizedType)supertype).getActualTypeArguments()[0];

         assert this.type != null;

         this.myDescriptor = myDescriptor;
         this.provider = provider;
      }
   }

   protected String getValue() {
      assert this.provider != null;

      return this.provider.getProvisioningOperationPropertyValue(this.getName());
   }

   public Object provide() {
      Object returnValue = this.convert(this.getValue());
      return returnValue;
   }

   public final String getName() {
      String name = this.name;
      if (name == null) {
         assert this.myDescriptor != null;

         Set qualifierAnnotations = this.myDescriptor.getQualifierAnnotations();
         if (qualifierAnnotations != null && !qualifierAnnotations.isEmpty()) {
            Iterator var3 = qualifierAnnotations.iterator();

            while(var3.hasNext()) {
               Annotation qualifierAnnotation = (Annotation)var3.next();
               if (qualifierAnnotation instanceof ProvisioningOperationProperty) {
                  name = ((ProvisioningOperationProperty)qualifierAnnotation).value();
                  this.name = name;
                  break;
               }
            }
         }

         if (name == null) {
            throw new IllegalStateException();
         }
      }

      return name;
   }

   public final String getDefaultValue() {
      String defaultValue = this.defaultValue;
      if (defaultValue == null) {
         assert this.myDescriptor != null;

         Set qualifierAnnotations = this.myDescriptor.getQualifierAnnotations();
         if (qualifierAnnotations != null && !qualifierAnnotations.isEmpty()) {
            Iterator var3 = qualifierAnnotations.iterator();

            while(var3.hasNext()) {
               Annotation qualifierAnnotation = (Annotation)var3.next();
               if (qualifierAnnotation instanceof ProvisioningOperationProperty) {
                  defaultValue = ((ProvisioningOperationProperty)qualifierAnnotation).defaultValue();
                  this.defaultValue = defaultValue;
                  break;
               }
            }
         }

         if (defaultValue == null) {
            throw new IllegalStateException();
         }
      }

      return defaultValue;
   }

   protected Object convert(String value) {
      Object returnValue = null;

      assert this.type != null;

      if (this.type instanceof Class) {
         Class cls = (Class)this.type;
         PropertyEditor propertyEditor = PropertyEditorManager.findEditor(cls);
         if (propertyEditor != null) {
            propertyEditor.setAsText(value);
            returnValue = cls.cast(propertyEditor.getValue());
         }
      }

      return returnValue;
   }

   public final void dispose(Object ignored) {
   }
}
