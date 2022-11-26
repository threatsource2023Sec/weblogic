package weblogic.diagnostics.lifecycle;

public interface DiagnosticLifecycleConstants {
   int INITIALIZED = 0;
   int ENABLED = 1;
   int DISABLED = 2;
   int FAILED = 3;
   int UNAVAILABLE = 4;
   String[] STATES = new String[]{"Initialized", "Enabled", "Disabled", "Failed", "Unavailable"};
}
