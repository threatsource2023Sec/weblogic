package weblogic.deploy.service;

public interface CallbackHandler {
   String CONFIGURATION = "Configuration";
   String APPLICATION = "Application";
   String TESTS = "Tests";

   String getHandlerIdentity();
}
