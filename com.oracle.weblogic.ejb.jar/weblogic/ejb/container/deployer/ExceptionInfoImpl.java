package weblogic.ejb.container.deployer;

import weblogic.ejb.container.interfaces.ExceptionInfo;

public class ExceptionInfoImpl implements ExceptionInfo {
   private final boolean appException;
   private final boolean inherited;
   private final boolean rollback;

   ExceptionInfoImpl(boolean appException, boolean inherited, boolean rollback) {
      this.appException = appException;
      this.inherited = inherited;
      this.rollback = rollback;
   }

   public boolean isAppException() {
      return this.appException;
   }

   public boolean isInherited() {
      return this.inherited;
   }

   public boolean isRollback() {
      return this.rollback;
   }
}
