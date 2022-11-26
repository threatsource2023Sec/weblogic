package org.opensaml.saml.common.binding;

import javax.annotation.Nullable;
import org.opensaml.saml.common.SAMLException;

public class BindingException extends SAMLException {
   private static final long serialVersionUID = 8759204244381246777L;

   public BindingException() {
   }

   public BindingException(@Nullable String message) {
      super(message);
   }

   public BindingException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public BindingException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
