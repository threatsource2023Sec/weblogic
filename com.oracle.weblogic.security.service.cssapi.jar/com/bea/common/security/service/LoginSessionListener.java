package com.bea.common.security.service;

public interface LoginSessionListener {
   int TERMINATE_LOGOUT = 0;
   int TERMINATE_TIMEOUT = 1;
   int TERMINATE_ADMIN = 2;
   int CREATE_FAIL = 3;
   String SESSION_CONTEXT_REQUEST_KEY = "HttpServletRequest";
   String SESSION_CONTEXT_DATA_KEY = "AssociatedData";

   boolean sessionCreated(LoginSession var1, Object var2);

   void sessionTerminated(LoginSession var1, int var2);
}
