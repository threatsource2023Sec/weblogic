package com.bea.common.security.service;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import weblogic.security.service.ContextHandler;

public interface JAASLoginService {
   Identity login(String var1, CallbackHandler var2, ContextHandler var3) throws LoginException;
}
