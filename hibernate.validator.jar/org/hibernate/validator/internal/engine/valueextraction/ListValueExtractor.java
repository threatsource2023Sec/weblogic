package org.hibernate.validator.internal.engine.valueextraction;

import java.util.List;
import javax.validation.valueextraction.ValueExtractor;

class ListValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ListValueExtractor());

   private ListValueExtractor() {
   }

   public void extractValues(List originalValue, ValueExtractor.ValueReceiver receiver) {
      for(int i = 0; i < originalValue.size(); ++i) {
         receiver.indexedValue("<list element>", i, originalValue.get(i));
      }

   }
}
