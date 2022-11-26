package weblogic.diagnostics.descriptor;

public interface WLDFResourceBean extends WLDFBean {
   String SCHEMA_CONFIG = "schema/diagnostics-binding.jar";

   WLDFInstrumentationBean getInstrumentation();

   WLDFHarvesterBean getHarvester();

   WLDFWatchNotificationBean getWatchNotification();
}
