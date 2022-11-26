package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Iterator;
import java.util.Map;
import javax.validation.valueextraction.ValueExtractor;

class MapValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new MapValueExtractor());

   private MapValueExtractor() {
   }

   public void extractValues(Map originalValue, ValueExtractor.ValueReceiver receiver) {
      Iterator var3 = originalValue.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         receiver.keyedValue("<map value>", entry.getKey(), entry.getValue());
      }

   }
}
