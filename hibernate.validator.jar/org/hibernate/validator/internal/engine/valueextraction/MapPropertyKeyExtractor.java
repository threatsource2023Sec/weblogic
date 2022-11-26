package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Iterator;
import java.util.Map;
import javafx.beans.property.MapProperty;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;

@IgnoreForbiddenApisErrors(
   reason = "Usage of JavaFX classes"
)
class MapPropertyKeyExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new MapPropertyKeyExtractor());

   private MapPropertyKeyExtractor() {
   }

   public void extractValues(MapProperty originalValue, ValueExtractor.ValueReceiver receiver) {
      Iterator var3 = originalValue.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         receiver.keyedValue("<map key>", entry.getKey(), entry.getKey());
      }

   }
}
