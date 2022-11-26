package weblogic.security;

import com.bea.common.security.jdkutils.X509CertificateFactory;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.security.service.SecurityServiceRuntimeException;
import weblogic.security.shared.LoggerWrapper;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.annotation.Secure;

@Service
@Named
@RunLevel(10)
@Secure
public final class X509CertRegisterService extends AbstractServerService {
   private boolean isPerfDebug;
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityService");

   public X509CertRegisterService() {
      this.isPerfDebug = PreSecurityService.isPerfDebug;
      if (log.isDebugEnabled()) {
         log.debug("X509CertRegisterService init");
      }

   }

   public void start() throws ServiceFailureException {
      if (log.isDebugEnabled()) {
         log.debug("starting X509CertRegisterService");
      }

      long startTime = 0L;
      if (this.isPerfDebug) {
         PreSecurityService.dbgPsr("X509CertRegisterService start");
         startTime = System.currentTimeMillis();
      }

      try {
         X509CertificateFactory.register();
         if (this.isPerfDebug) {
            long currentTime = System.currentTimeMillis();
            long runTime = currentTime - startTime;
            PreSecurityService.dbgPsr("X509CertRegisterService start - X509CertificateFactory.register() = " + runTime);
         }
      } catch (SecurityServiceRuntimeException var7) {
         throw new ServiceFailureException(var7);
      } catch (RuntimeException var8) {
         throw var8;
      } catch (Exception var9) {
         SecurityLogger.logStackTrace(var9);
         throw new ServiceFailureException(var9);
      }

      if (log.isDebugEnabled()) {
         log.debug("finished starting X509CertRegisterService");
      }

   }

   public void stop() throws ServiceFailureException {
      if (log.isDebugEnabled()) {
         log.debug("X509CertRegisterService stop");
      }

   }

   public void halt() throws ServiceFailureException {
      if (log.isDebugEnabled()) {
         log.debug("X509CertRegisterService halt");
      }

   }
}
