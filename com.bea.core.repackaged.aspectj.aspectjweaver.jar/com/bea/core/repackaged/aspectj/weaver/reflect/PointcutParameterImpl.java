package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.weaver.tools.PointcutParameter;

public class PointcutParameterImpl implements PointcutParameter {
   String name;
   Class type;
   Object binding;

   public PointcutParameterImpl(String name, Class type) {
      this.name = name;
      this.type = type;
   }

   public String getName() {
      return this.name;
   }

   public Class getType() {
      return this.type;
   }

   public Object getBinding() {
      return this.binding;
   }

   void setBinding(Object boundValue) {
      this.binding = boundValue;
   }
}
