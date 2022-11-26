package weblogic.management.rest.lib.utils;

public interface ActivationTaskWrapper {
   void waitForTaskCompletion() throws Exception;

   Exception getError() throws Exception;
}
