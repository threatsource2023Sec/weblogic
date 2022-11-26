package weblogic.connector.utils;

import java.util.Collections;
import java.util.List;

public interface ConnectorAPContext extends ValidationMessage {
   String RESOURCEADAPTERBEAN = "resourceadapter-class";
   String SMALL_ICON = "smallicon";
   String LARGE_ICON = "largeicon";
   String ADMIN_OBJECT = "adminObject";
   String MESSAGE_LISTENER = "messageListener";
   String CONNECTION_DEFINITION = "connectionDef";
   ConnectorAPContext NullContext = new ConnectorAPContext() {
      public boolean fromAnnotation(String name, String... type) {
         return false;
      }

      public List getErrors() {
         return Collections.emptyList();
      }

      public List getWarnings() {
         return Collections.emptyList();
      }

      public boolean annotationFound() {
         return false;
      }

      public List getErrorsOfMessageKey(ValidationMessage.SubComponentAndKey msgKey) {
         return Collections.emptyList();
      }

      public List getCriticalErrors() {
         return Collections.emptyList();
      }

      public List getNonCriticalErrors() {
         return Collections.emptyList();
      }

      public boolean hasCriticalError() {
         return false;
      }

      public void error(String subComponent, String key, String message, int order) {
      }

      public void clearErrorsOfMessageKey(ValidationMessage.SubComponentAndKey msgKey) {
      }
   };

   boolean fromAnnotation(String var1, String... var2);

   boolean annotationFound();
}
