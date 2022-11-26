package weblogic.security.service.internal;

public interface UserLockoutService {
   UserLockoutRuntimeService getRuntimeService();

   UserLockoutAdministrationService getAdministrationService();

   UserLockoutCoordinationService getCoordinationService();
}
