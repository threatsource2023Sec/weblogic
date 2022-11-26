package org.hibernate.validator.internal.metadata.descriptor;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstructorDescriptor;
import javax.validation.metadata.MethodDescriptor;
import javax.validation.metadata.MethodType;
import javax.validation.metadata.PropertyDescriptor;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.logging.Messages;

public class BeanDescriptorImpl extends ElementDescriptorImpl implements BeanDescriptor {
   private final Map constrainedProperties;
   private final Map constrainedMethods;
   private final Map constrainedConstructors;

   public BeanDescriptorImpl(Type beanClass, Set classLevelConstraints, Map constrainedProperties, Map constrainedMethods, Map constrainedConstructors, boolean defaultGroupSequenceRedefined, List defaultGroupSequence) {
      super(beanClass, classLevelConstraints, defaultGroupSequenceRedefined, defaultGroupSequence);
      this.constrainedProperties = CollectionHelper.toImmutableMap(constrainedProperties);
      this.constrainedMethods = CollectionHelper.toImmutableMap(constrainedMethods);
      this.constrainedConstructors = CollectionHelper.toImmutableMap(constrainedConstructors);
   }

   public final boolean isBeanConstrained() {
      return this.hasConstraints() || !this.constrainedProperties.isEmpty();
   }

   public final PropertyDescriptor getConstraintsForProperty(String propertyName) {
      Contracts.assertNotNull(propertyName, "The property name cannot be null");
      return (PropertyDescriptor)this.constrainedProperties.get(propertyName);
   }

   public final Set getConstrainedProperties() {
      return CollectionHelper.newHashSet(this.constrainedProperties.values());
   }

   public ConstructorDescriptor getConstraintsForConstructor(Class... parameterTypes) {
      return (ConstructorDescriptor)this.constrainedConstructors.get(ExecutableHelper.getSignature(this.getElementClass().getSimpleName(), parameterTypes));
   }

   public Set getConstrainedConstructors() {
      return CollectionHelper.newHashSet(this.constrainedConstructors.values());
   }

   public Set getConstrainedMethods(MethodType methodType, MethodType... methodTypes) {
      boolean includeGetters = MethodType.GETTER.equals(methodType);
      boolean includeNonGetters = MethodType.NON_GETTER.equals(methodType);
      if (methodTypes != null) {
         MethodType[] var5 = methodTypes;
         int var6 = methodTypes.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            MethodType type = var5[var7];
            if (MethodType.GETTER.equals(type)) {
               includeGetters = true;
            }

            if (MethodType.NON_GETTER.equals(type)) {
               includeNonGetters = true;
            }
         }
      }

      Set matchingMethodDescriptors = CollectionHelper.newHashSet();
      Iterator var10 = this.constrainedMethods.values().iterator();

      while(var10.hasNext()) {
         ExecutableDescriptorImpl constrainedMethod = (ExecutableDescriptorImpl)var10.next();
         boolean addToSet = false;
         if (constrainedMethod.isGetter() && includeGetters || !constrainedMethod.isGetter() && includeNonGetters) {
            addToSet = true;
         }

         if (addToSet) {
            matchingMethodDescriptors.add(constrainedMethod);
         }
      }

      return matchingMethodDescriptors;
   }

   public MethodDescriptor getConstraintsForMethod(String methodName, Class... parameterTypes) {
      Contracts.assertNotNull(methodName, Messages.MESSAGES.methodNameMustNotBeNull());
      return (MethodDescriptor)this.constrainedMethods.get(ExecutableHelper.getSignature(methodName, parameterTypes));
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("BeanDescriptorImpl");
      sb.append("{class='");
      sb.append(this.getElementClass().getSimpleName());
      sb.append("'}");
      return sb.toString();
   }
}
