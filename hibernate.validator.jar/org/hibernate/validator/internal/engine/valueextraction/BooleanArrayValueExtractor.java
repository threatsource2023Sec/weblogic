package org.hibernate.validator.internal.engine.valueextraction;

import javax.validation.valueextraction.ValueExtractor;

class BooleanArrayValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new BooleanArrayValueExtractor());

   private BooleanArrayValueExtractor() {
   }

   public void extractValues(boolean[] originalValue, ValueExtractor.ValueReceiver receiver) {
      for(int i = 0; i < originalValue.length; ++i) {
         receiver.indexedValue("<iterable element>", i, originalValue[i]);
      }

   }
}
