package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListFactoryBean extends AbstractFactoryBean {
   @Nullable
   private List sourceList;
   @Nullable
   private Class targetListClass;

   public void setSourceList(List sourceList) {
      this.sourceList = sourceList;
   }

   public void setTargetListClass(@Nullable Class targetListClass) {
      if (targetListClass == null) {
         throw new IllegalArgumentException("'targetListClass' must not be null");
      } else if (!List.class.isAssignableFrom(targetListClass)) {
         throw new IllegalArgumentException("'targetListClass' must implement [java.util.List]");
      } else {
         this.targetListClass = targetListClass;
      }
   }

   public Class getObjectType() {
      return List.class;
   }

   protected List createInstance() {
      if (this.sourceList == null) {
         throw new IllegalArgumentException("'sourceList' is required");
      } else {
         List result = null;
         if (this.targetListClass != null) {
            result = (List)BeanUtils.instantiateClass(this.targetListClass);
         } else {
            result = new ArrayList(this.sourceList.size());
         }

         Class valueType = null;
         if (this.targetListClass != null) {
            valueType = ResolvableType.forClass(this.targetListClass).asCollection().resolveGeneric();
         }

         if (valueType != null) {
            TypeConverter converter = this.getBeanTypeConverter();
            Iterator var4 = this.sourceList.iterator();

            while(var4.hasNext()) {
               Object elem = var4.next();
               ((List)result).add(converter.convertIfNecessary(elem, valueType));
            }
         } else {
            ((List)result).addAll(this.sourceList);
         }

         return (List)result;
      }
   }
}
