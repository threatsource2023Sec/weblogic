package weblogic.diagnostics.image.descriptor;

public interface LoggingImageSourceBean {
   LogEntryBean[] getLogEntries();

   LogEntryBean createLogEntry();
}
