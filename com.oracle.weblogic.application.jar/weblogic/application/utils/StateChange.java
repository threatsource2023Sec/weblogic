package weblogic.application.utils;

public interface StateChange {
   void next(Object var1) throws Exception;

   void previous(Object var1) throws Exception;

   void logRollbackError(StateChangeException var1);
}
