package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.SessionService;
import javax.servlet.http.HttpSession;

public class SessionServiceImpl implements ServiceLifecycleSpi, SessionService {
   private LoggerSpi logger;

   public Object init(Object config, Services services) throws ServiceInitializationException {
      this.logger = ((LoggerService)services.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.SessionService");
      String method = this.getClass().getName() + ".init()";
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(method);
      }

      return Delegator.getProxy((Class)SessionService.class, this);
   }

   public void shutdown() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".shutdown()");
      }

   }

   public void setIdentity(HttpSession session, Identity identity) {
      if (session == null) {
         this.logger.error("session is null, can't save the identity.");
      } else {
         session.setAttribute("com.bea.common.security.service.SessionService.Identity", identity);
      }
   }

   public Identity getIdentity(HttpSession session) {
      if (session == null) {
         this.logger.error("session is null, can't retrieve the identity.");
         return null;
      } else {
         return (Identity)session.getAttribute("com.bea.common.security.service.SessionService.Identity");
      }
   }
}
