package org.opensaml;

import java.util.Collection;
import org.w3c.dom.Element;

public class InvalidCryptoException extends SAMLException implements Cloneable {
   protected InvalidCryptoException(Element var1) throws SAMLException {
      super(var1);
   }

   public InvalidCryptoException(String var1) {
      super(var1);
   }

   public InvalidCryptoException(String var1, Exception var2) {
      super(var1, var2);
   }

   public InvalidCryptoException(Collection var1, String var2) {
      super(var1, var2);
   }

   public InvalidCryptoException(Collection var1, Exception var2) {
      super(var1, var2);
   }

   public InvalidCryptoException(Collection var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }

   public InvalidCryptoException(QName var1, String var2) {
      super(var1, var2);
   }

   public InvalidCryptoException(QName var1, Exception var2) {
      super(var1, var2);
   }

   public InvalidCryptoException(QName var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }
}
