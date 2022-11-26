package com.bea.staxb.runtime.internal;

import com.bea.staxb.runtime.internal.util.collections.Accumulator;
import com.bea.staxb.runtime.internal.util.collections.AccumulatorFactory;
import com.bea.xml.XmlException;

final class IntermediaryUtils {
   protected static void initAccumulator(ByNameRuntimeBindingType runtimeBindingType, int elem_idx, Accumulator[] accs) throws XmlException {
      assert accs != null;

      if (accs[elem_idx] == null) {
         AttributeRuntimeBindingType.QNameRuntimeProperty[] props = runtimeBindingType.getElementProperties();
         AttributeRuntimeBindingType.QNameRuntimeProperty p = props[elem_idx];
         accs[elem_idx] = AccumulatorFactory.createAccumulator(p.getPropertyClass(), p.getCollectionElementClass());
      }

   }
}
