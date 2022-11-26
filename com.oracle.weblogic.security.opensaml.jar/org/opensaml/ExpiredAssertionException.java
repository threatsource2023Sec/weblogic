package org.opensaml;

import java.util.Collection;
import org.w3c.dom.Element;

public class ExpiredAssertionException extends FatalProfileException implements Cloneable {
   protected ExpiredAssertionException(Element var1) throws SAMLException {
      super(var1);
   }

   public ExpiredAssertionException(String var1) {
      super(var1);
   }

   public ExpiredAssertionException(String var1, Exception var2) {
      super(var1, var2);
   }

   public ExpiredAssertionException(Collection var1, String var2) {
      super(var1, var2);
   }

   public ExpiredAssertionException(Collection var1, Exception var2) {
      super(var1, var2);
   }

   public ExpiredAssertionException(Collection var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }

   public ExpiredAssertionException(QName var1, String var2) {
      super(var1, var2);
   }

   public ExpiredAssertionException(QName var1, Exception var2) {
      super(var1, var2);
   }

   public ExpiredAssertionException(QName var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }
}
