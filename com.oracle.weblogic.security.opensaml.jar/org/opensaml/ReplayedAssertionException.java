package org.opensaml;

import java.util.Collection;
import org.w3c.dom.Element;

public class ReplayedAssertionException extends RetryableProfileException implements Cloneable {
   protected ReplayedAssertionException(Element var1) throws SAMLException {
      super(var1);
   }

   public ReplayedAssertionException(String var1) {
      super(var1);
   }

   public ReplayedAssertionException(String var1, Exception var2) {
      super(var1, var2);
   }

   public ReplayedAssertionException(Collection var1, String var2) {
      super(var1, var2);
   }

   public ReplayedAssertionException(Collection var1, Exception var2) {
      super(var1, var2);
   }

   public ReplayedAssertionException(Collection var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }

   public ReplayedAssertionException(QName var1, String var2) {
      super(var1, var2);
   }

   public ReplayedAssertionException(QName var1, Exception var2) {
      super(var1, var2);
   }

   public ReplayedAssertionException(QName var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }
}
