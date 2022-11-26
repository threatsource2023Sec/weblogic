package org.opensaml;

import java.util.Collection;
import org.w3c.dom.Element;

public class TrustException extends InvalidCryptoException implements Cloneable {
   protected TrustException(Element var1) throws SAMLException {
      super(var1);
   }

   public TrustException(String var1) {
      super(var1);
   }

   public TrustException(String var1, Exception var2) {
      super(var1, var2);
   }

   public TrustException(Collection var1, String var2) {
      super(var1, var2);
   }

   public TrustException(Collection var1, Exception var2) {
      super(var1, var2);
   }

   public TrustException(Collection var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }

   public TrustException(QName var1, String var2) {
      super(var1, var2);
   }

   public TrustException(QName var1, Exception var2) {
      super(var1, var2);
   }

   public TrustException(QName var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }
}
