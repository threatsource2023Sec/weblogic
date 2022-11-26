package org.glassfish.grizzly;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.localization.LogMessages;

public final class ProcessorExecutor {
   private static final Logger LOGGER = Grizzly.logger(ProcessorExecutor.class);

   public static void execute(Connection connection, IOEvent ioEvent, Processor processor, IOEventLifeCycleListener lifeCycleListener) {
      execute(Context.create(connection, processor, ioEvent, lifeCycleListener));
   }

   public static void execute(Context context) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "executing connection ({0}). IOEvent={1} processor={2}", new Object[]{context.getConnection(), context.getIoEvent(), context.getProcessor()});
      }

      try {
         boolean isRerun;
         ProcessorResult result;
         do {
            result = context.getProcessor().process(context);
            isRerun = result.getStatus() == ProcessorResult.Status.RERUN;
            if (isRerun) {
               Context newContext = (Context)result.getData();
               rerun(context, newContext);
               context = newContext;
            }
         } while(isRerun);

         complete0(context, result);
      } catch (Throwable var6) {
         Throwable t = var6;
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_PROCESSOR_ERROR(context.getConnection(), context.getIoEvent(), context.getProcessor()), var6);
         }

         try {
            error(context, t);
         } catch (Exception var5) {
         }
      }

   }

   public static void resume(Context context) throws IOException {
      execute(context);
   }

   private static void complete(Context context, Object data) throws IOException {
      int sz = context.lifeCycleListeners.size();
      IOEventLifeCycleListener[] listeners = (IOEventLifeCycleListener[])context.lifeCycleListeners.array();

      try {
         for(int i = 0; i < sz; ++i) {
            listeners[i].onComplete(context, data);
         }
      } finally {
         context.recycle();
      }

   }

   private static void leave(Context context) throws IOException {
      int sz = context.lifeCycleListeners.size();
      IOEventLifeCycleListener[] listeners = (IOEventLifeCycleListener[])context.lifeCycleListeners.array();

      try {
         for(int i = 0; i < sz; ++i) {
            listeners[i].onLeave(context);
         }
      } finally {
         context.recycle();
      }

   }

   private static void reregister(Context context, Object data) throws IOException {
      Context realContext = (Context)data;
      int sz = context.lifeCycleListeners.size();
      IOEventLifeCycleListener[] listeners = (IOEventLifeCycleListener[])context.lifeCycleListeners.array();

      try {
         for(int i = 0; i < sz; ++i) {
            listeners[i].onReregister(realContext);
         }
      } finally {
         realContext.recycle();
      }

   }

   private static void rerun(Context context, Context newContext) throws IOException {
      int sz = context.lifeCycleListeners.size();
      IOEventLifeCycleListener[] listeners = (IOEventLifeCycleListener[])context.lifeCycleListeners.array();

      for(int i = 0; i < sz; ++i) {
         listeners[i].onRerun(context, newContext);
      }

   }

   private static void error(Context context, Object description) throws IOException {
      int sz = context.lifeCycleListeners.size();
      IOEventLifeCycleListener[] listeners = (IOEventLifeCycleListener[])context.lifeCycleListeners.array();

      try {
         for(int i = 0; i < sz; ++i) {
            listeners[i].onError(context, description);
         }
      } finally {
         context.release();
      }

   }

   private static void notRun(Context context) throws IOException {
      int sz = context.lifeCycleListeners.size();
      IOEventLifeCycleListener[] listeners = (IOEventLifeCycleListener[])context.lifeCycleListeners.array();

      try {
         for(int i = 0; i < sz; ++i) {
            listeners[i].onNotRun(context);
         }
      } finally {
         context.recycle();
      }

   }

   static void complete(Context context, ProcessorResult result) {
      try {
         complete0(context, result);
      } catch (Throwable var5) {
         Throwable t = var5;

         try {
            error(context, t);
         } catch (Exception var4) {
         }
      }

   }

   private static void complete0(Context context, ProcessorResult result) throws IllegalStateException, IOException {
      ProcessorResult.Status status = result.getStatus();
      switch (status) {
         case COMPLETE:
            complete(context, result.getData());
            break;
         case LEAVE:
            leave(context);
         case TERMINATE:
            break;
         case REREGISTER:
            reregister(context, result.getData());
            break;
         case ERROR:
            error(context, result.getData());
            break;
         case NOT_RUN:
            notRun(context);
            break;
         default:
            throw new IllegalStateException();
      }

   }
}
