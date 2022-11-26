package org.opensaml;

import java.util.Collection;
import org.w3c.dom.Element;

public class RetryableProfileException extends ProfileException implements Cloneable {
   protected RetryableProfileException(Element var1) throws SAMLException {
      super(var1);
   }

   public RetryableProfileException(String var1) {
      super(var1);
   }

   public RetryableProfileException(String var1, Exception var2) {
      super(var1, var2);
   }

   public RetryableProfileException(Collection var1, String var2) {
      super(var1, var2);
   }

   public RetryableProfileException(Collection var1, Exception var2) {
      super(var1, var2);
   }

   public RetryableProfileException(Collection var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }

   public RetryableProfileException(QName var1, String var2) {
      super(var1, var2);
   }

   public RetryableProfileException(QName var1, Exception var2) {
      super(var1, var2);
   }

   public RetryableProfileException(QName var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }
}
