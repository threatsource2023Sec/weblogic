package com.bea.common.security.service;

import java.util.Date;

public interface LoginSessionService {
   LoginSession create(Identity var1, Date var2, Object var3);

   void terminate(LoginSession var1);

   void terminate(String var1);

   void logout(LoginSession var1);

   void logout(String var1);

   LoginSession getSession(Identity var1);

   LoginSession getSession(String var1);

   void addListener(LoginSessionListener var1);

   void removeListener(LoginSessionListener var1);
}
