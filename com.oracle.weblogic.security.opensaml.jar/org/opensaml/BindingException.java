package org.opensaml;

import java.util.Collection;
import org.w3c.dom.Element;

public class BindingException extends SAMLException implements Cloneable {
   protected BindingException(Element var1) throws SAMLException {
      super(var1);
   }

   public BindingException(String var1) {
      super(var1);
   }

   public BindingException(String var1, Exception var2) {
      super(var1, var2);
   }

   public BindingException(Collection var1, String var2) {
      super(var1, var2);
   }

   public BindingException(Collection var1, Exception var2) {
      super(var1, var2);
   }

   public BindingException(Collection var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }

   public BindingException(QName var1, String var2) {
      super(var1, var2);
   }

   public BindingException(QName var1, Exception var2) {
      super(var1, var2);
   }

   public BindingException(QName var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }
}
