package com.oracle.weblogic.diagnostics.timerservice;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public class TimerServiceFactory {
   @Inject
   @Optional
   TimerService timerService;

   private TimerServiceFactory() {
   }

   public TimerService getTimerService() {
      if (this.timerService == null) {
         this.timerService = new DefaultTimerServiceImpl();
      }

      return this.timerService;
   }
}
