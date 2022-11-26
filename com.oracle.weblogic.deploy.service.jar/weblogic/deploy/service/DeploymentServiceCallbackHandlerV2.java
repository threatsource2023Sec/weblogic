package weblogic.deploy.service;

public interface DeploymentServiceCallbackHandlerV2 extends DeploymentServiceCallbackHandler {
   String PREPARE_SUCCESS_RECEIVED = "PrepareSuccessReceived";
   String PREPARE_FAILED_RECEIVED = "PrepareFailedReceived";
   String COMMIT_SUCCESS_RECEIVED = "CommitSuccessReceived";
   String COMMIT_FAILED_RECEIVED = "CommitFailedReceived";
   String CANCEL_SUCCESS_RECEIVED = "CancelSuccessReceived";
   String CANCEL_FAILED_RECEIVED = "CancelFailedReceived";

   void requestStatusUpdated(long var1, String var3, String var4);
}
