package weblogic.jms.common;

import java.util.Date;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.messaging.dispatcher.DispatcherException;

public final class JMSDebug {
   public static final DebugLogger JMSBackEnd = DebugLogger.getDebugLogger("DebugJMSBackEnd");
   public static final DebugLogger JMSFrontEnd = DebugLogger.getDebugLogger("DebugJMSFrontEnd");
   public static final DebugLogger JMSCommon = DebugLogger.getDebugLogger("DebugJMSCommon");
   public static final DebugLogger JMSConfig = DebugLogger.getDebugLogger("DebugJMSConfig");
   public static final DebugLogger JMSLocking = DebugLogger.getDebugLogger("DebugJMSLocking");
   public static final DebugLogger JMSXA = DebugLogger.getDebugLogger("DebugJMSXA");
   public static final DebugLogger JMSStore = DebugLogger.getDebugLogger("DebugJMSStore");
   public static final DebugLogger JMSBoot = DebugLogger.getDebugLogger("DebugJMSBoot");
   public static final DebugLogger JMSDurSub = DebugLogger.getDebugLogger("DebugJMSDurSub");
   public static final DebugLogger JMSDispatcher = DebugLogger.getDebugLogger("DebugJMSDispatcher");
   public static final DebugLogger JMSDispatcherLifecycle = DebugLogger.getDebugLogger("DebugJMSDispatcherLifecycle");
   public static final DebugLogger JMSDispatcherProxy = DebugLogger.getDebugLogger("DebugJMSDispatcherProxy");
   public static final DebugLogger JMSDispatcherVerbose = DebugLogger.getDebugLogger("DebugJMSDispatcherVerbose");
   public static final DebugLogger JMSDispatcherRMI = DebugLogger.getDebugLogger("DebugJMSDispatcherRMI");
   public static final DebugLogger JMSDistTopic = DebugLogger.getDebugLogger("DebugJMSDistTopic");
   public static final DebugLogger JMSAME = DebugLogger.getDebugLogger("DebugJMSAME");
   public static final DebugLogger JMSPauseResume = DebugLogger.getDebugLogger("DebugJMSPauseResume");
   public static final DebugLogger JMSModule = DebugLogger.getDebugLogger("DebugJMSModule");
   public static final DebugLogger JMSMessagePath = DebugLogger.getDebugLogger("DebugJMSMessagePath");
   public static final DebugLogger JMSSAF = DebugLogger.getDebugLogger("DebugJMSSAF");
   public static final DebugLogger JMSCDS = DebugLogger.getDebugLogger("DebugJMSCDS");
   public static final DebugLogger JMSCrossDomainSecurity = DebugLogger.getDebugLogger("DebugJMSCrossDomainSecurity");
   public static final DebugLogger JMSDotNetProxy = DebugLogger.getDebugLogger("DebugJMSDotNetProxy");
   public static final DebugLogger JMSDotNetTransport = DebugLogger.getDebugLogger("DebugJMSDotNetTransport");
   public static final DebugLogger JMSDotNetT3Server = DebugLogger.getDebugLogger("DebugJMSDotNetT3Server");
   public static final DebugLogger JMSDispatherUtilsVerbose = DebugLogger.getDebugLogger("DebugJMSDispatcherUtilsVerbose");
   public static final DebugLogger JMSInvocableVerbose = DebugLogger.getDebugLogger("DebugJMSInvocableVerbose");
   public static final DebugLogger JMSCICHelper = DebugLogger.getDebugLogger("DebugJMSCICHelper");
   public static final DebugLogger JMSOBS = DebugLogger.getDebugLogger("DebugJMSOBS");
   private static final boolean DEBUG = true;
   private long lastTimeThrew;
   private int millisecBetweenThrows = 50000;
   private int countMatchMask = 15;
   private int throwCount;

   public JMSDebug() {
   }

   public JMSDebug(int millisecBetweenThrows, int countMatchMask) {
      this.millisecBetweenThrows = millisecBetweenThrows;
      this.countMatchMask = countMatchMask;
   }

   public String shouldThrow(String reason) {
      long currentTime;
      String ret;
      label58: {
         try {
            if ((this.throwCount & this.countMatchMask) == 0) {
               currentTime = System.currentTimeMillis();
               if (currentTime - (long)this.millisecBetweenThrows >= this.lastTimeThrew) {
                  break label58;
               }

               debug("too soon to throw: " + reason + new Date());
               ret = null;
               return ret;
            }

            debug("mask not satisfied " + reason + (this.throwCount & this.countMatchMask));
            ret = null;
         } finally {
            ++this.throwCount;
         }

         return ret;
      }

      this.lastTimeThrew = currentTime;
      ret = "Testing resilience to: " + reason + ", " + this.throwCount + " " + new Date();
      debug(ret);
      return ret;
   }

   public void periodicallyThrowJMSException(String reason) throws javax.jms.JMSException {
      String label = this.shouldThrow(reason);
      if (label != null) {
         throw new JMSException(label);
      }
   }

   public void periodicallyThrowDispatcherException(String reason) throws DispatcherException {
      String label = this.shouldThrow(reason);
      if (label != null) {
         throw new DispatcherException(label);
      }
   }

   public static final void unlocked(String text, Object object) {
      if (JMSLocking.isDebugEnabled()) {
         JMSLocking.debug("LCK!" + Thread.currentThread().getName() + ": " + text + " : unlocked " + object);
      }

   }

   private static final void debug(String msg) {
      JMSDistTopic.debug(msg);
   }
}
