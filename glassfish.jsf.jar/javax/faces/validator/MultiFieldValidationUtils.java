package javax.faces.validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.validation.groups.Default;

class MultiFieldValidationUtils {
   static final String MULTI_FIELD_VALIDATION_CANDIDATES = "javax.faces.Bean.MULTI_FIELD_VALIDATION_CANDIDATES";
   static final String FAILED_FIELD_LEVEL_VALIDATION = "javax.faces.Bean.FAILED_FIELD_LEVEL_VALIDATION";

   static Map getMultiFieldValidationCandidates(FacesContext context, boolean create) {
      Map attrs = context.getAttributes();
      Map result = (Map)attrs.get("javax.faces.Bean.MULTI_FIELD_VALIDATION_CANDIDATES");
      if (null == result) {
         if (create) {
            result = new HashMap();
            attrs.put("javax.faces.Bean.MULTI_FIELD_VALIDATION_CANDIDATES", result);
         } else {
            result = Collections.emptyMap();
         }
      }

      return (Map)result;
   }

   static boolean wholeBeanValidationEnabled(FacesContext context, Class[] validationGroupsArray) {
      Map attrs = context.getAttributes();
      if (attrs.containsKey("javax.faces.validator.ENABLE_VALIDATE_WHOLE_BEAN") && (Boolean)attrs.get("javax.faces.validator.ENABLE_VALIDATE_WHOLE_BEAN")) {
         boolean result = 1 != validationGroupsArray.length || Default.class != validationGroupsArray[0];
         return result;
      } else {
         return false;
      }
   }
}
