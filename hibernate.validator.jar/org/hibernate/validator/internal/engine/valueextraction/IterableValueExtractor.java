package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Iterator;
import javax.validation.valueextraction.ValueExtractor;

class IterableValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new IterableValueExtractor());

   private IterableValueExtractor() {
   }

   public void extractValues(Iterable originalValue, ValueExtractor.ValueReceiver receiver) {
      Iterator var3 = originalValue.iterator();

      while(var3.hasNext()) {
         Object object = var3.next();
         receiver.iterableValue("<iterable element>", object);
      }

   }
}
