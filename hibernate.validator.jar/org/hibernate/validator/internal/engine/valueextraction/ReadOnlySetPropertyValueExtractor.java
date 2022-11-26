package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Iterator;
import javafx.beans.property.ReadOnlySetProperty;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;

@IgnoreForbiddenApisErrors(
   reason = "Usage of JavaFX classes"
)
class ReadOnlySetPropertyValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ReadOnlySetPropertyValueExtractor());

   private ReadOnlySetPropertyValueExtractor() {
   }

   public void extractValues(ReadOnlySetProperty originalValue, ValueExtractor.ValueReceiver receiver) {
      Iterator var3 = originalValue.iterator();

      while(var3.hasNext()) {
         Object object = var3.next();
         receiver.iterableValue("<iterable element>", object);
      }

   }
}
