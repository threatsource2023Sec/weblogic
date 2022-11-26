package weblogic.diagnostics.instrumentation;

public interface CustomMonitor extends DelegatingMonitor {
   String getPointcut();

   void setPointcut(String var1) throws InvalidPointcutException;
}
