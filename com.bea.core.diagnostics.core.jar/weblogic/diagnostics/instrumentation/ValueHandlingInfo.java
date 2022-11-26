package weblogic.diagnostics.instrumentation;

public interface ValueHandlingInfo {
   String getName();

   String getRendererClassName();

   boolean isSensitive();

   boolean isGathered();
}
