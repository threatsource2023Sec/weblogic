package weblogic.jdbc.common.internal;

import weblogic.common.ResourceException;

public class FeatureNotSupportedException extends ResourceException {
   private static final long serialVersionUID = 8194418875386957933L;

   public FeatureNotSupportedException(String s) {
      super(s);
   }
}
