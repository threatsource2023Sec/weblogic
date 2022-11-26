package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Iterator;
import java.util.Map;
import javafx.beans.property.ReadOnlyMapProperty;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;

@IgnoreForbiddenApisErrors(
   reason = "Usage of JavaFX classes"
)
class ReadOnlyMapPropertyValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ReadOnlyMapPropertyValueExtractor());

   private ReadOnlyMapPropertyValueExtractor() {
   }

   public void extractValues(ReadOnlyMapProperty originalValue, ValueExtractor.ValueReceiver receiver) {
      Iterator var3 = originalValue.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         receiver.keyedValue("<map value>", entry.getKey(), entry.getValue());
      }

   }
}
