package weblogic.ejb.container.deployer;

import weblogic.utils.NestedException;

public final class IncomprehensibleMethodSignatureException extends NestedException {
   private static final long serialVersionUID = -8474212152431635624L;

   public IncomprehensibleMethodSignatureException() {
   }

   public IncomprehensibleMethodSignatureException(String msg) {
      super(msg);
   }

   public IncomprehensibleMethodSignatureException(Throwable th) {
      super(th);
   }

   public IncomprehensibleMethodSignatureException(String msg, Throwable th) {
      super(msg, th);
   }
}
