package org.hibernate.validator.internal.engine.valueextraction;

import javax.validation.valueextraction.ValueExtractor;

class ShortArrayValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ShortArrayValueExtractor());

   private ShortArrayValueExtractor() {
   }

   public void extractValues(short[] originalValue, ValueExtractor.ValueReceiver receiver) {
      for(int i = 0; i < originalValue.length; ++i) {
         receiver.indexedValue("<iterable element>", i, originalValue[i]);
      }

   }
}
