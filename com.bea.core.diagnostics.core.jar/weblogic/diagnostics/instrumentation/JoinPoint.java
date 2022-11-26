package weblogic.diagnostics.instrumentation;

import java.util.Map;

public interface JoinPoint {
   String getModuleName();

   String getSourceFile();

   String getClassName();

   String getMethodName();

   String getMethodDescriptor();

   int getLineNumber();

   String getCallerClassName();

   String getCallerMethodName();

   String getCallerMethodDescriptor();

   boolean isReturnGathered(String var1);

   GatheredArgument[] getGatheredArguments(String var1);

   Map getPointcutHandlingInfoMap();

   boolean isStatic();
}
