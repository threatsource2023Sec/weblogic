package com.bea.common.security.saml.utils;

import com.bea.common.security.utils.CSSPlatformProxy;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;

public class ServletIdentityHelperImpl implements ServletIdentityHelper {
   public boolean runAs(Subject subject, HttpServletRequest request) {
      return CSSPlatformProxy.getInstance().runAs(subject, request);
   }
}
