package weblogic.management.patching.commands;

public interface StatusPoller {
   boolean checkStatus();

   String getPollingDescription();
}
