package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class SetFactoryBean extends AbstractFactoryBean {
   @Nullable
   private Set sourceSet;
   @Nullable
   private Class targetSetClass;

   public void setSourceSet(Set sourceSet) {
      this.sourceSet = sourceSet;
   }

   public void setTargetSetClass(@Nullable Class targetSetClass) {
      if (targetSetClass == null) {
         throw new IllegalArgumentException("'targetSetClass' must not be null");
      } else if (!Set.class.isAssignableFrom(targetSetClass)) {
         throw new IllegalArgumentException("'targetSetClass' must implement [java.util.Set]");
      } else {
         this.targetSetClass = targetSetClass;
      }
   }

   public Class getObjectType() {
      return Set.class;
   }

   protected Set createInstance() {
      if (this.sourceSet == null) {
         throw new IllegalArgumentException("'sourceSet' is required");
      } else {
         Set result = null;
         if (this.targetSetClass != null) {
            result = (Set)BeanUtils.instantiateClass(this.targetSetClass);
         } else {
            result = new LinkedHashSet(this.sourceSet.size());
         }

         Class valueType = null;
         if (this.targetSetClass != null) {
            valueType = ResolvableType.forClass(this.targetSetClass).asCollection().resolveGeneric();
         }

         if (valueType != null) {
            TypeConverter converter = this.getBeanTypeConverter();
            Iterator var4 = this.sourceSet.iterator();

            while(var4.hasNext()) {
               Object elem = var4.next();
               ((Set)result).add(converter.convertIfNecessary(elem, valueType));
            }
         } else {
            ((Set)result).addAll(this.sourceSet);
         }

         return (Set)result;
      }
   }
}
