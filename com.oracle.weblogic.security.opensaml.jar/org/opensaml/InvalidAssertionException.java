package org.opensaml;

import java.util.Collection;
import org.w3c.dom.Element;

public class InvalidAssertionException extends FatalProfileException implements Cloneable {
   protected InvalidAssertionException(Element var1) throws SAMLException {
      super(var1);
   }

   public InvalidAssertionException(String var1) {
      super(var1);
   }

   public InvalidAssertionException(String var1, Exception var2) {
      super(var1, var2);
   }

   public InvalidAssertionException(Collection var1, String var2) {
      super(var1, var2);
   }

   public InvalidAssertionException(Collection var1, Exception var2) {
      super(var1, var2);
   }

   public InvalidAssertionException(Collection var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }

   public InvalidAssertionException(QName var1, String var2) {
      super(var1, var2);
   }

   public InvalidAssertionException(QName var1, Exception var2) {
      super(var1, var2);
   }

   public InvalidAssertionException(QName var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }
}
