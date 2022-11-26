package weblogic.utils.wrapper;

public interface Wrapper {
   void setVendorObj(Object var1);

   Object getVendorObj();

   void preInvocationHandler(String var1, Object[] var2) throws Exception;

   Object postInvocationHandler(String var1, Object[] var2, Object var3) throws Exception;

   Object invocationExceptionHandler(String var1, Object[] var2, Throwable var3) throws Exception;
}
