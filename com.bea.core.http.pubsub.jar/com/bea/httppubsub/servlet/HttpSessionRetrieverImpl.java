package com.bea.httppubsub.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpSessionRetrieverImpl implements HttpSessionRetriever {
   private final boolean replicatedSessionEnable;

   public HttpSessionRetrieverImpl(boolean replicatedSessionEnable) {
      this.replicatedSessionEnable = replicatedSessionEnable;
   }

   public HttpSession retrieve(HttpServletRequest request) {
      if (request == null) {
         return null;
      } else {
         return this.replicatedSessionEnable ? request.getSession(true) : null;
      }
   }
}
