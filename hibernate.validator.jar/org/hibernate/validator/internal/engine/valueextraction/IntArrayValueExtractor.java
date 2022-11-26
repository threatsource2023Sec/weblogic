package org.hibernate.validator.internal.engine.valueextraction;

import javax.validation.valueextraction.ValueExtractor;

class IntArrayValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new IntArrayValueExtractor());

   private IntArrayValueExtractor() {
   }

   public void extractValues(int[] originalValue, ValueExtractor.ValueReceiver receiver) {
      for(int i = 0; i < originalValue.length; ++i) {
         receiver.indexedValue("<iterable element>", i, originalValue[i]);
      }

   }
}
