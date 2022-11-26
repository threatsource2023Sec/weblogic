package weblogic.ejb.container.interfaces;

public interface Invokable {
   Object __WL_invoke(Object var1, Object[] var2, int var3) throws Throwable;

   void __WL_handleException(int var1, Throwable var2) throws Throwable;
}
