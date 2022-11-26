package weblogic.servlet.internal;

public interface ServletWorkContext {
   String getApplicationName();

   String getAppDisplayName();

   String getVersionId();

   boolean isAdminMode();
}
