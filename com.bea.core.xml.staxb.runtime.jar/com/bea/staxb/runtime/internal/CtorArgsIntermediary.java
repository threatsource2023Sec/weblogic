package com.bea.staxb.runtime.internal;

import com.bea.staxb.runtime.internal.util.ReflectionUtils;
import com.bea.xml.XmlException;
import java.util.ArrayList;
import java.util.List;

final class CtorArgsIntermediary implements ObjectIntermediary {
   private final AttributeRuntimeBindingType runtimeBindingType;
   private final Object[] ctorArgs;
   private final List setterQueue;

   CtorArgsIntermediary(AttributeRuntimeBindingType type) {
      this.runtimeBindingType = type;
      int ctor_param_cnt = type.getCtorParamCount();

      assert ctor_param_cnt > 0;

      this.ctorArgs = new Object[ctor_param_cnt];
      this.setterQueue = new ArrayList();
   }

   public Object getFinalValue() throws XmlException {
      Object final_value = this.constructFinalValue();
      this.fillValues(final_value);
      return final_value;
   }

   private void fillValues(Object final_value) throws XmlException {
      int sz = this.setterQueue.size();

      for(int i = 0; i < sz; ++i) {
         RuntimeBindingProperty property = (RuntimeBindingProperty)this.setterQueue.get(i);
         ++i;
         Object prop_obj = this.setterQueue.get(i);
         property.setValue(final_value, prop_obj);
      }

   }

   private Object constructFinalValue() throws XmlException {
      return ReflectionUtils.invokeConstructor(this.runtimeBindingType.getConstructor(), this.ctorArgs);
   }

   public void addItem(int elem_idx, Object value) {
      throw new AssertionError("unused");
   }

   public Object getActualValue() {
      throw new UnsupportedOperationException("type=" + this.runtimeBindingType);
   }

   public void setValue(RuntimeBindingProperty property, Object prop_obj) throws XmlException {
      int arg_idx = property.getCtorArgIndex();
      if (arg_idx < 0) {
         this.setterQueue.add(property);
         this.setterQueue.add(prop_obj);
      } else {
         this.ctorArgs[arg_idx] = prop_obj;
      }

   }
}
