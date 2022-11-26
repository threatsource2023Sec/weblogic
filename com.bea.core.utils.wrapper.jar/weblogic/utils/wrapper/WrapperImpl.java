package weblogic.utils.wrapper;

import java.sql.SQLException;

public class WrapperImpl implements Wrapper {
   public Object vendorObj;

   public void setVendorObj(Object obj) {
      this.vendorObj = obj;
   }

   public Object getVendorObj() {
      return this.vendorObj;
   }

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      System.out.print(this.getClass().getName() + ".preInvocationHandler(" + methodName + ", ");
      if (params != null && params.length > 0) {
         for(int i = 0; i < params.length - 1; ++i) {
            System.out.print(params[i] + ", ");
         }

         System.out.println(params[params.length - 1] + ")");
      } else {
         System.out.println("null)");
      }

   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      System.out.print(this.getClass().getName() + ".postInvocationHandler(" + methodName + ", ");
      if (params != null && params.length > 0) {
         for(int i = 0; i < params.length; ++i) {
            System.out.print(params[i] + ", ");
         }
      } else {
         System.out.print("null, ");
      }

      System.out.println(ret + ")");
      return ret;
   }

   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws Exception {
      System.out.print(this.getClass().getName() + ".invocationExceptionHandler(" + methodName + ", ");
      if (params != null && params.length > 0) {
         for(int i = 0; i < params.length; ++i) {
            System.out.print(params[i] + ", ");
         }
      } else {
         System.out.print("null, ");
      }

      System.out.println(t + ")");
      if (t instanceof SQLException) {
         throw (SQLException)t;
      } else {
         return null;
      }
   }
}
