package org.hibernate.validator.internal.engine.valueextraction;

import javax.validation.valueextraction.ValueExtractor;

class FloatArrayValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new FloatArrayValueExtractor());

   private FloatArrayValueExtractor() {
   }

   public void extractValues(float[] originalValue, ValueExtractor.ValueReceiver receiver) {
      for(int i = 0; i < originalValue.length; ++i) {
         receiver.indexedValue("<iterable element>", i, originalValue[i]);
      }

   }
}
