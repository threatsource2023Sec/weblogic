package weblogic.connector.configuration.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import weblogic.connector.common.Debug;
import weblogic.connector.compliance.RAComplianceTextFormatter;
import weblogic.connector.utils.PropertyNameNormalizer;
import weblogic.connector.utils.ValidationMessage;

public abstract class DefaultValidator implements Validator {
   private ValidationContext context;
   protected static final RAComplianceTextFormatter fmt;
   private final ValidatingMessageImpl validatingMessages;

   public DefaultValidator(ValidationContext context) {
      this.context = context;
      this.validatingMessages = context.getValidatingMessages();
   }

   public PropertyNameNormalizer getPropertyNameNormalizer() {
      return this.context.getPropertyNameNormalizer();
   }

   public void validate() {
      Debug.enter(this, "validate()");
      this.doValidate();
      Debug.exit(this, "validate()");
   }

   public void warning(String message) {
      this.validatingMessages.warning(message, this.order());
   }

   public void error(String subComponent, String key, String messsage) {
      this.validatingMessages.error(subComponent, key, messsage, this.order());
   }

   protected void reportPropertyIssue(String subComponent, String key, String message) {
      if (this.context.isJCA16()) {
         this.error(subComponent, key, message);
      } else {
         this.warning(message);
      }

   }

   public abstract void doValidate();

   public List getDuplicateProperties(String subComponent, String key, String elementName, Object[] props, Comparator comparator) {
      List duplicatedProperties = new ArrayList();
      if (props != null && comparator != null) {
         Arrays.sort(props, comparator);

         int j;
         for(int i = 0; i < props.length - 1; i = j) {
            List sameProps = new ArrayList();
            boolean found = false;

            for(j = i + 1; j < props.length && comparator.compare(props[i], props[j]) == 0; ++j) {
               found = true;
               sameProps.add(props[j]);
            }

            if (found) {
               sameProps.add(props[i]);
               this.reportDuplicateProperties(subComponent, key, elementName, sameProps);
               duplicatedProperties.addAll(sameProps);
            }
         }

         return duplicatedProperties;
      } else {
         return duplicatedProperties;
      }
   }

   protected abstract void reportDuplicateProperties(String var1, String var2, String var3, List var4);

   static {
      fmt = ValidationMessage.RAComplianceTextMsg;
   }
}
