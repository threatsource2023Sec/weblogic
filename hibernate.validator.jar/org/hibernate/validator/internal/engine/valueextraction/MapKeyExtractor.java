package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Iterator;
import java.util.Map;
import javax.validation.valueextraction.ValueExtractor;

class MapKeyExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new MapKeyExtractor());

   private MapKeyExtractor() {
   }

   public void extractValues(Map originalValue, ValueExtractor.ValueReceiver receiver) {
      Iterator var3 = originalValue.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         receiver.keyedValue("<map key>", entry.getKey(), entry.getKey());
      }

   }
}
