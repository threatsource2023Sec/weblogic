package com.bea.common.security.service;

import javax.servlet.http.HttpSession;

public interface SessionService {
   String IDENTITY_ATTRIBUTE = "com.bea.common.security.service.SessionService.Identity";

   void setIdentity(HttpSession var1, Identity var2);

   Identity getIdentity(HttpSession var1);
}
