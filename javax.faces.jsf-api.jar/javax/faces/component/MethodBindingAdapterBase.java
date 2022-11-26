package javax.faces.component;

abstract class MethodBindingAdapterBase {
   Throwable getExpectedCause(Class expectedExceptionClass, Throwable exception) {
      Throwable result = exception.getCause();
      if (null != result && !expectedExceptionClass.isAssignableFrom(result.getClass())) {
         result = this.getExpectedCause(expectedExceptionClass, result);
      }

      return result;
   }
}
