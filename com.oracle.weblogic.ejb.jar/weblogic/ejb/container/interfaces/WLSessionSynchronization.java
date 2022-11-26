package weblogic.ejb.container.interfaces;

public interface WLSessionSynchronization {
   void __WL_afterBegin() throws Exception;

   void __WL_beforeCompletion() throws Exception;

   void __WL_afterCompletion(boolean var1) throws Exception;
}
