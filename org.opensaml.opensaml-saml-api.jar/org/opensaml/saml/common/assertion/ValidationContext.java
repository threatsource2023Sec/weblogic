package org.opensaml.saml.common.assertion;

import java.util.Collections;
import java.util.Map;
import javax.annotation.concurrent.NotThreadSafe;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

@NotThreadSafe
public class ValidationContext {
   private Map staticParameters;
   private Map dynamicParameters;
   private String validationFailureMessage;

   public ValidationContext() {
      this((Map)null);
   }

   public ValidationContext(Map newStaticParameters) {
      if (newStaticParameters == null) {
         this.staticParameters = Collections.unmodifiableMap(Collections.EMPTY_MAP);
      } else {
         this.staticParameters = Collections.unmodifiableMap(newStaticParameters);
      }

      this.dynamicParameters = new LazyMap();
   }

   public Map getStaticParameters() {
      return this.staticParameters;
   }

   public Map getDynamicParameters() {
      return this.dynamicParameters;
   }

   public String getValidationFailureMessage() {
      return this.validationFailureMessage;
   }

   public void setValidationFailureMessage(String message) {
      this.validationFailureMessage = StringSupport.trimOrNull(message);
   }
}
