package weblogic.ejb.container.interfaces;

public interface ExceptionInfo {
   boolean isAppException();

   boolean isInherited();

   boolean isRollback();
}
