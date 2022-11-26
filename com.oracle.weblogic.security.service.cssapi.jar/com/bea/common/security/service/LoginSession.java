package com.bea.common.security.service;

import java.util.Date;

public interface LoginSession {
   String getId();

   Identity getIdentity();

   Date getAuthenticationTime();
}
