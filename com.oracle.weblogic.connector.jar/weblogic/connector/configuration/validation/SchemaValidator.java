package weblogic.connector.configuration.validation;

import java.util.List;

class SchemaValidator extends DefaultRAValidator {
   SchemaValidator(ValidationContext context) {
      super(context);
   }

   public void doValidate() {
   }

   public int order() {
      return 0;
   }

   protected void reportDuplicateProperties(String subComponent, String key, String elementName, List duplicatedProperties) {
   }
}
