package weblogic.management.runtime;

public interface PageFlowError {
   long getTimeStamp();

   String getStackTraceAsString();

   String getMessage();
}
