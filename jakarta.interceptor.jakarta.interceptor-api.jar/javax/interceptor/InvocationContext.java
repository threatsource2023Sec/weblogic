package javax.interceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

public interface InvocationContext {
   Object getTarget();

   Object getTimer();

   Method getMethod();

   Constructor getConstructor();

   Object[] getParameters();

   void setParameters(Object[] var1);

   Map getContextData();

   Object proceed() throws Exception;
}
