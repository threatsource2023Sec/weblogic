package weblogic.diagnostics.instrumentation;

public interface DyeFilterable {
   boolean isDyeFilteringEnabled();

   void setDyeFilteringEnabled(boolean var1);

   long getDyeMask();

   void setDyeMask(long var1);
}
