package com.bea.staxb.runtime.internal;

import com.bea.staxb.runtime.internal.util.collections.Accumulator;
import com.bea.xml.XmlException;

final class MultiIntermediary implements ObjectIntermediary {
   private final ByNameRuntimeBindingType runtimeBindingType;
   private final Object value;
   private Accumulator[] accumulators;

   MultiIntermediary(ByNameRuntimeBindingType type, Object actual_obj) {
      this.runtimeBindingType = type;
      this.value = actual_obj;
   }

   MultiIntermediary(ByNameRuntimeBindingType type) {
      this(type, ClassLoadingUtils.newInstance(type.getJavaType()));
   }

   public Object getFinalValue() throws XmlException {
      if (this.accumulators != null) {
         AttributeRuntimeBindingType.QNameRuntimeProperty[] props = this.runtimeBindingType.getElementProperties();
         int i = 0;

         for(int len = this.accumulators.length; i < len; ++i) {
            Accumulator accum = this.accumulators[i];
            if (accum != null) {
               AttributeRuntimeBindingType.QNameRuntimeProperty prop = props[i];
               prop.fillCollection(this.value, accum.getFinalArray());
            }
         }
      }

      return this.value;
   }

   public void addItem(int elem_idx, Object value) throws XmlException {
      if (this.accumulators == null) {
         this.accumulators = new Accumulator[this.runtimeBindingType.getElementPropertyCount()];
      }

      IntermediaryUtils.initAccumulator(this.runtimeBindingType, elem_idx, this.accumulators);
      this.accumulators[elem_idx].append(value);
   }

   public Object getActualValue() {
      return this.value;
   }

   public void setValue(RuntimeBindingProperty property, Object prop_obj) throws XmlException {
      property.setValue(this.value, prop_obj);
   }
}
