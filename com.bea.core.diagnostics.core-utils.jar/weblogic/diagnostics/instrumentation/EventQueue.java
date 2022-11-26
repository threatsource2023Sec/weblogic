package weblogic.diagnostics.instrumentation;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import weblogic.diagnostics.accessor.DiagnosticAccessRuntime;
import weblogic.diagnostics.accessor.DiagnosticDataAccessRuntime;
import weblogic.diagnostics.archive.DataWriter;
import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.context.CorrelationFactory;
import weblogic.diagnostics.context.CorrelationHelper;
import weblogic.diagnostics.context.InvalidDyeException;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.diagnostics.utils.ArchiveHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.PropertyHelper;

public final class EventQueue implements TimerListener, InstrumentationConstants, EventDispatcher {
   private static EventQueue singleton;
   private static final String WORK_MANAGER_NAME = "InstrumentationEvents";
   private static final String INST_TIMER_MANAGER = "InstrumentationTimer";
   private static final int MAX_THREADS = 1;
   private static final boolean noEventArchive = PropertyHelper.getBoolean("weblogic.diagnostics.internal.noEventArchive");
   private static final boolean noQueueEvent = PropertyHelper.getBoolean("weblogic.diagnostics.internal.noQueueEvent");
   private static final boolean noEventPropagation = PropertyHelper.getBoolean("weblogic.diagnostics.internal.noEventPropagation");
   private int eventThreadPool = -1;
   private final Object mutex = new Object();
   private List savedEventsList = new ArrayList();
   private TimerManagerFactory timerManagerFactory;
   private TimerManager timerManager;
   private Timer eventsTimer;
   private long timerInterval;
   private long timerPeriod = 5000L;
   private boolean isInProgress;
   private boolean archiveNotAvailable;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private EventQueue() {
      TimerManagerFactory timerManagerFactory = TimerManagerFactory.getTimerManagerFactory();
      this.timerManager = timerManagerFactory.getTimerManager("InstrumentationTimer");
      this.archiveNotAvailable = ArchiveHelper.isFilestoreNeededAndNotAvailable();
   }

   public static synchronized EventQueue getInstance() {
      if (singleton == null) {
         singleton = new EventQueue();
      }

      return singleton;
   }

   public void dispatch(String eventType, EventPayload payload) {
      InstrumentationEvent event = new InstrumentationEvent(eventType, payload);
      this.enqueue(event);
   }

   public void enqueue(InstrumentationEvent event) {
      if (!noQueueEvent) {
         if (this.archiveNotAvailable) {
            if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_ACTIONS.debug("Unable to archive event since archive is not available");
            }

         } else {
            if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_ACTIONS.debug("EventQueue:enqueue " + event);
            }

            try {
               if (CorrelationHelper.isDyedWith((byte)31)) {
                  return;
               }
            } catch (InvalidDyeException var6) {
            }

            boolean synchronous = InstrumentationManager.getInstrumentationManager().isSynchronousEventPersistenceEnabled();
            if (synchronous) {
               this.handleSynchronously(event);
            } else {
               synchronized(this.mutex) {
                  this.savedEventsList.add(event);
               }
            }

         }
      }
   }

   public synchronized void setTimerInterval(long period) {
      if (period < 1000L) {
         period = 1000L;
      }

      if (this.eventsTimer == null) {
         this.eventsTimer = this.timerManager.scheduleAtFixedRate(this, 0L, period);
      } else {
         this.eventsTimer.cancel();
         this.eventsTimer = this.timerManager.scheduleAtFixedRate(this, 0L, period);
      }

      this.timerInterval = period;
   }

   public long getTimerInterval() {
      return this.timerInterval;
   }

   private void handleSynchronously(InstrumentationEvent record) {
      Correlation ctx = CorrelationFactory.findOrCreateCorrelation(true);
      boolean oldDyeValue = false;

      try {
         oldDyeValue = ctx.isDyedWith(31);
         ctx.setDye(31, true);
      } catch (InvalidDyeException var7) {
      }

      ArrayList eventsList = new ArrayList();
      eventsList.add(record);
      SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new ArchiveEventsAction(eventsList));

      try {
         ctx.setDye(31, oldDyeValue);
      } catch (InvalidDyeException var6) {
      }

   }

   public void timerExpired(Timer timer) {
      if (this.isInProgress) {
         if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_ACTIONS.debug("Previous archival session is in progress");
         }

      } else {
         this.isInProgress = true;
         List eventsList = this.savedEventsList;
         synchronized(this.mutex) {
            if (this.savedEventsList.size() == 0) {
               this.isInProgress = false;
               return;
            }

            this.savedEventsList = new ArrayList();
         }

         if (InstrumentationDebug.DEBUG_EVENTS.isDebugEnabled()) {
            int size = eventsList.size();

            for(int i = 0; i < size; ++i) {
               InstrumentationDebug.DEBUG_EVENTS.debug("EventsWork-EVENT " + i + ": " + eventsList.get(i));
            }
         }

         Correlation ctx = CorrelationFactory.findOrCreateCorrelation(true);
         boolean oldDyeValue = false;

         try {
            oldDyeValue = ctx.isDyedWith(31);
            ctx.setDye(31, true);
         } catch (InvalidDyeException var7) {
         }

         SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new ArchiveEventsAction(eventsList));

         try {
            ctx.setDye(31, oldDyeValue);
         } catch (InvalidDyeException var6) {
         }

         this.isInProgress = false;
      }
   }

   private static DataWriter getArchive() {
      DataWriter archive = null;

      try {
         DiagnosticAccessRuntime accessor = DiagnosticAccessRuntime.getInstance();
         DiagnosticDataAccessRuntime runtime = (DiagnosticDataAccessRuntime)accessor.lookupWLDFDataAccessRuntime("EventsDataArchive");
         archive = (DataWriter)runtime.getDiagnosticDataAccessService();
      } catch (Exception var3) {
         if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_ACTIONS.debug("Could not find events archive", var3);
         }
      }

      return archive;
   }

   final class ArchiveEventsAction implements PrivilegedAction {
      private List eventsList;

      ArchiveEventsAction(List eventsList) {
         this.eventsList = eventsList;
      }

      public Object run() {
         try {
            int size = this.eventsList.size();

            for(int i = 0; i < size; ++i) {
               InstrumentationEvent event = (InstrumentationEvent)this.eventsList.get(i);
               this.eventsList.set(i, event.getDataRecord());
            }

            if (!EventQueue.noEventPropagation) {
               InstrumentationManager.getInstrumentationManager().propagateInstrumentationEvents(this.eventsList);
            }

            if (!EventQueue.noEventArchive) {
               DataWriter archive = EventQueue.getArchive();
               if (archive != null) {
                  archive.writeData(this.eventsList);
               }
            }
         } catch (Exception var4) {
            UnexpectedExceptionHandler.handle("Error writing data to archive", var4);
         }

         return null;
      }
   }
}
