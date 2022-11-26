package weblogic.diagnostics.instrumentation;

public interface DynamicJoinPoint extends JoinPoint {
   Object[] getArguments();

   Object getReturnValue();

   boolean isReturnGathered();

   GatheredArgument[] getGatheredArguments();

   JoinPoint getDelegate();
}
