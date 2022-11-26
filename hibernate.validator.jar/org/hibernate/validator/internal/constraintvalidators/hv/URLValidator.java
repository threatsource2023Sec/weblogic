package org.hibernate.validator.internal.constraintvalidators.hv;

import java.net.MalformedURLException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.URL;

public class URLValidator implements ConstraintValidator {
   private String protocol;
   private String host;
   private int port;

   public void initialize(URL url) {
      this.protocol = url.protocol();
      this.host = url.host();
      this.port = url.port();
   }

   public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
      if (value != null && value.length() != 0) {
         java.net.URL url;
         try {
            url = new java.net.URL(value.toString());
         } catch (MalformedURLException var5) {
            return false;
         }

         if (this.protocol != null && this.protocol.length() > 0 && !url.getProtocol().equals(this.protocol)) {
            return false;
         } else if (this.host != null && this.host.length() > 0 && !url.getHost().equals(this.host)) {
            return false;
         } else {
            return this.port == -1 || url.getPort() == this.port;
         }
      } else {
         return true;
      }
   }
}
