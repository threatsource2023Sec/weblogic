package com.bea.staxb.runtime.internal;

import com.bea.staxb.runtime.internal.util.ReflectionUtils;
import com.bea.staxb.runtime.internal.util.collections.Accumulator;
import com.bea.xml.XmlException;
import java.util.ArrayList;
import java.util.List;

final class CtorArgsMultiIntermediary implements ObjectIntermediary {
   private final ByNameRuntimeBindingType runtimeBindingType;
   private Accumulator[] accumulators;
   private final Object[] ctorArgs;
   private final List setterQueue;

   CtorArgsMultiIntermediary(ByNameRuntimeBindingType type) {
      this.runtimeBindingType = type;
      int ctor_param_cnt = type.getCtorParamCount();

      assert ctor_param_cnt > 0 : "type = " + type;

      this.ctorArgs = new Object[ctor_param_cnt];
      this.setterQueue = new ArrayList(type.getPropertyCount());
   }

   public Object getFinalValue() throws XmlException {
      if (this.accumulators != null) {
         AttributeRuntimeBindingType.QNameRuntimeProperty[] props = this.runtimeBindingType.getElementProperties();
         int i = 0;

         for(int len = this.accumulators.length; i < len; ++i) {
            Accumulator accum = this.accumulators[i];
            if (accum != null) {
               AttributeRuntimeBindingType.QNameRuntimeProperty prop = props[i];

               assert prop.isMultiple();

               this.setValue(prop, accum.getFinalArray());
            }
         }
      }

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

   public void addItem(int elem_idx, Object value) throws XmlException {
      if (this.accumulators == null) {
         this.accumulators = new Accumulator[this.runtimeBindingType.getElementPropertyCount()];
      }

      IntermediaryUtils.initAccumulator(this.runtimeBindingType, elem_idx, this.accumulators);
      this.accumulators[elem_idx].append(value);
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
