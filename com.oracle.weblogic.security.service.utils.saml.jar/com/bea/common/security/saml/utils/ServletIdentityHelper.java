package com.bea.common.security.saml.utils;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;

public interface ServletIdentityHelper {
   boolean runAs(Subject var1, HttpServletRequest var2);
}
