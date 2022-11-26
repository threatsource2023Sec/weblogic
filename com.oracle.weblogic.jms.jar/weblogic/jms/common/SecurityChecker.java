package weblogic.jms.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.jms.JMSService;
import weblogic.security.SubjectUtils;
import weblogic.security.service.JMSResource;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;

public final class SecurityChecker implements TimerListener {
   private JMSService jmsService;
   private ConcurrentHashMap checkMe;

   public SecurityChecker(JMSService paramJMSService) {
      this.jmsService = paramJMSService;
      this.checkMe = new ConcurrentHashMap();
   }

   public void registerWithChecker(JMSResource resource, TimedSecurityParticipant participant) {
      synchronized(this.checkMe) {
         if (this.checkMe.size() <= 0) {
            this.jmsService.fireUpSecurityChecks();
         }

         this.checkMe.put(participant, resource);
      }

      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("Registered " + JMSSecurityHelper.getSimpleAuthenticatedName() + " for security checks on " + resource + " participant " + participant);
      }

   }

   public void unregisterWithChecker(TimedSecurityParticipant participant) {
      synchronized(this.checkMe) {
         this.checkMe.remove(participant);
      }

      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("Unregistered for security checks, " + participant);
      }

   }

   public void timerExpired(Timer timer) {
      HashMap toProcess = new HashMap();
      synchronized(this.checkMe) {
         Iterator var4 = this.checkMe.keySet().iterator();

         while(var4.hasNext()) {
            TimedSecurityParticipant participant = (TimedSecurityParticipant)var4.next();
            if (participant.isClosed()) {
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  JMSDebug.JMSCommon.debug("Removing closed participant " + participant);
               }

               this.checkMe.remove(participant);
            } else {
               toProcess.put(participant, this.checkMe.get(participant));
            }
         }

         if (this.checkMe.size() <= 0) {
            this.jmsService.stopSecurityChecks();
         }
      }

      Iterator var3 = toProcess.keySet().iterator();

      while(var3.hasNext()) {
         TimedSecurityParticipant participant = (TimedSecurityParticipant)var3.next();
         JMSResource resource = (JMSResource)toProcess.get(participant);

         try {
            JMSSecurityHelper.checkPermission(resource, participant.getSubject());
         } catch (javax.jms.JMSSecurityException var7) {
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug("Security lapsed for " + resource + SubjectUtils.getUsername(participant.getSubject()));
            }

            participant.securityLapsed();
         }
      }

      toProcess.clear();
   }
}
