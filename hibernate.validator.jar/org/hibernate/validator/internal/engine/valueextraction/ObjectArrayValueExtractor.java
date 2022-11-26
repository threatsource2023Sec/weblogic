package org.hibernate.validator.internal.engine.valueextraction;

import javax.validation.valueextraction.ValueExtractor;

class ObjectArrayValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ObjectArrayValueExtractor());

   private ObjectArrayValueExtractor() {
   }

   public void extractValues(Object[] originalValue, ValueExtractor.ValueReceiver receiver) {
      for(int i = 0; i < originalValue.length; ++i) {
         receiver.indexedValue("<iterable element>", i, originalValue[i]);
      }

   }
}
