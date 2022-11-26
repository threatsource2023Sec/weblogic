package weblogic.connector.security.work;

import javax.security.auth.callback.CallbackHandler;

public interface CallbackHandlerFactory {
   CallbackHandler getCallBackHandler();
}
