package weblogic.diagnostics.instrumentation;

public interface PointcutHandlingInfo {
   ValueHandlingInfo getClassValueHandlingInfo();

   ValueHandlingInfo getReturnValueHandlingInfo();

   ValueHandlingInfo[] getArgumentValueHandlingInfo();
}
