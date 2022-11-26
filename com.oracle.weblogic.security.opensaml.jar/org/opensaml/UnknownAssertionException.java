package org.opensaml;

import java.util.Collection;
import org.w3c.dom.Element;

public class UnknownAssertionException extends BindingException implements Cloneable {
   protected UnknownAssertionException(Element var1) throws SAMLException {
      super(var1);
   }

   public UnknownAssertionException(String var1) {
      super(var1);
   }

   public UnknownAssertionException(String var1, Exception var2) {
      super(var1, var2);
   }

   public UnknownAssertionException(Collection var1, String var2) {
      super(var1, var2);
   }

   public UnknownAssertionException(Collection var1, Exception var2) {
      super(var1, var2);
   }

   public UnknownAssertionException(Collection var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }

   public UnknownAssertionException(QName var1, String var2) {
      super(var1, var2);
   }

   public UnknownAssertionException(QName var1, Exception var2) {
      super(var1, var2);
   }

   public UnknownAssertionException(QName var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }
}
