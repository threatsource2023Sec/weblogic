package weblogic.management.internal;

public interface InvariantChecker {
   String getSubsystemName();

   String getErrorDetails();

   boolean check();
}
