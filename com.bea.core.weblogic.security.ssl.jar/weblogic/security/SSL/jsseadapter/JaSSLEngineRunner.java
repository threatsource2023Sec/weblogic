package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import weblogic.security.utils.SSLIOContext;

final class JaSSLEngineRunner {
   private static final int APP_IN_BUFFER_MARGIN_BYTES = 50;
   private static final int NET_BUFFER_MARGIN_BYTES = 50;
   private static final SSLEngineStateTransition TRANSITION_NEED_TASK = new Transition_NeedTask();
   private static final SSLEngineStateTransition TRANSITION_NEED_UNWRAP = new Transition_NeedUnwrap();
   private static final SSLEngineStateTransition TRANSITION_NEED_WRAP = new Transition_NeedWrap();
   private static final SSLEngineStateTransition TRANSITION_HANDSHAKE_FINISHED = new Transition_HandshakeFinished();
   private static final SSLEngineStateTransition TRANSITION_BUFFER_OVERFLOW_WRAP = new Transition_BufferOverflow_Wrap();
   private static final SSLEngineStateTransition TRANSITION_BUFFER_OVERFLOW_UNWRAP = new Transition_BufferOverflow_Unwrap();
   private static final SSLEngineStateTransition TRANSITION_BUFFER_UNDERFLOW_UNWRAP = new Transition_BufferUnderflow_Unwrap();
   private static final Map sslEngineTransitions;

   private JaSSLEngineRunner() {
   }

   private static boolean isTransitioning(Method method, Context context, TransitionResult transitionResult) {
      if (null != method && null != context && null != transitionResult) {
         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "isTransitioning: method={0}, transitionResult={1}.", method, transitionResult);
         }

         RunnerResult runnerResult = transitionResult.getRunnerResult();
         switch (runnerResult) {
            case INCOMPLETE_NETWORK_READ:
            case INCOMPLETE_NETWORK_WRITE:
            case NEED_APPLICATION_READ:
               if (JaLogger.isLoggable(Level.FINEST)) {
                  JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "Need data transfer: method={0}, transitionResult={1}.", method, transitionResult);
               }

               return false;
            case OK:
               SSLEngineResult engineResult = transitionResult.getSslEngineResult();
               SSLEngineResult.Status status = engineResult.getStatus();
               SSLEngineResult.HandshakeStatus hStatus = engineResult.getHandshakeStatus();
               SSLEngine engine = context.getSslEngine();
               context.ensureHandshakeLockIfNeeded(hStatus);
               switch (status) {
                  case BUFFER_UNDERFLOW:
                  case BUFFER_OVERFLOW:
                     return true;
                  case OK:
                  case CLOSED:
                     switch (hStatus) {
                        case NEED_UNWRAP:
                           if (engine.isInboundDone()) {
                              if (JaLogger.isLoggable(Level.FINER)) {
                                 JaLogger.log(Level.FINER, JaLogger.Component.SSLENGINE, "Need unwrap but inbound closed: method={0}, transitionResult={1}.", method, transitionResult);
                              }

                              return false;
                           }

                           return true;
                        case NEED_WRAP:
                           if (engine.isOutboundDone()) {
                              if (JaLogger.isLoggable(Level.FINER)) {
                                 JaLogger.log(Level.FINER, JaLogger.Component.SSLENGINE, "Need wrap but outbound closed: method={0}, transitionResult={1}.", method, transitionResult);
                              }

                              return false;
                           }

                           return true;
                        case NEED_TASK:
                        case FINISHED:
                           return true;
                        case NOT_HANDSHAKING:
                           if (Status.CLOSED == status) {
                              if (JaLogger.isLoggable(Level.FINER)) {
                                 JaLogger.log(Level.FINER, JaLogger.Component.SSLENGINE, "Not handshaking but status was CLOSED: method={0}, transitionResult={1}.", method, transitionResult);
                              }

                              return false;
                           } else {
                              if (Status.OK == status && HandshakeStatus.NOT_HANDSHAKING == hStatus) {
                                 return false;
                              }

                              throw new IllegalStateException("Unexpected status: " + engineResult);
                           }
                        default:
                           throw new IllegalStateException("Unexpected handshake status: " + hStatus);
                     }
                  default:
                     throw new IllegalStateException("Unexpected status: " + status);
               }
            default:
               throw new IllegalStateException("Unexpected RunnerResult: " + runnerResult);
         }
      } else {
         throw new IllegalArgumentException("Expected non-null arguments.");
      }
   }

   private static RunnerResult doTransitions(Context context, Method method) throws IOException {
      if (null == context) {
         throw new IllegalArgumentException("Expected non-null Context.");
      } else if (null == method) {
         throw new IllegalArgumentException("Expected non-null Method.");
      } else {
         SSLEngine engine = context.getSslEngine();
         SSLEngineStateTransition transition;
         switch (method) {
            case UNWRAP:
               if (engine.isInboundDone()) {
                  return JaSSLEngineRunner.RunnerResult.CLOSED;
               }

               transition = TRANSITION_NEED_UNWRAP;
               break;
            case WRAP:
               if (engine.isOutboundDone()) {
                  return JaSSLEngineRunner.RunnerResult.CLOSED;
               }

               transition = TRANSITION_NEED_WRAP;
               break;
            default:
               throw new IllegalArgumentException("Unexpected Method=" + method + ".");
         }

         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "Starting doTransitions: method={0}, starting transition={1}.", method, transition.getClass().getName());
         }

         SSLEngineResult engineResult = null;

         TransitionResult transitionResult;
         try {
            while(isTransitioning(method, context, transitionResult = transition.getNextState(method, context, engineResult))) {
               engineResult = transitionResult.getSslEngineResult();
               SSLEngineState state = new SSLEngineState(method, engineResult);
               transition = (SSLEngineStateTransition)sslEngineTransitions.get(state);
               if (null == transition) {
                  throw new IllegalStateException("Unexpected null transition for state: " + state);
               }

               if (JaLogger.isLoggable(Level.FINEST)) {
                  JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "Next transition: method={0}, last result={1}, next transition={2}.", method, engineResult, transition.getClass().getName());
               }
            }
         } finally {
            context.ensureHandshakeUnlock();
         }

         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "Ending doTransitions: method={0}, result={1}.", method, transitionResult);
         }

         engineResult = transitionResult.getSslEngineResult();
         return null != engineResult && engineResult.getStatus() == Status.CLOSED ? JaSSLEngineRunner.RunnerResult.CLOSED : transitionResult.getRunnerResult();
      }
   }

   private static void log_wrapException(Exception e) {
      if (JaLogger.isLoggable(Level.FINE)) {
         JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, e, "Unable to complete JaSSLEngineRunner.wrap.");
      }

   }

   private static void log_unwrapException(Exception e) {
      if (JaLogger.isLoggable(Level.FINE)) {
         JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, e, "Unable to complete JaSSLEngineRunner.unwrap.");
      }

   }

   private static void log_closeInboundException(Exception e, boolean expectCloseNotify) {
      if (JaLogger.isLoggable(Level.FINE)) {
         JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, e, "Unable to complete JaSSLEngineRunner.closeInbound, expectCloseNotify={0}.", expectCloseNotify);
      }

   }

   private static void log_closeOutboundException(Exception e) {
      if (JaLogger.isLoggable(Level.FINE)) {
         JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, e, "Unable to complete JaSSLEngineRunner.closeOutbound.");
      }

   }

   static RunnerResult unwrap(Context context) throws IOException {
      try {
         if (null == context) {
            throw new IllegalArgumentException("Expected non-null Context.");
         } else {
            SSLEngine engine = context.getSslEngine();
            RunnerResult result;
            if (null != engine && !engine.isInboundDone()) {
               context.getSync().lock(JaSSLEngineSynchronizer.LockState.INBOUND);

               try {
                  result = doTransitions(context, JaSSLEngineRunner.Method.UNWRAP);
               } finally {
                  context.getSync().unlock();
               }
            } else {
               result = JaSSLEngineRunner.RunnerResult.CLOSED;
            }

            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "JaSSLEngineRunner.unwrap result={0}.", result);
            }

            return result;
         }
      } catch (InterruptedIOException var10) {
         throw var10;
      } catch (SSLException var11) {
         log_unwrapException(var11);
         throw var11;
      } catch (IOException var12) {
         log_unwrapException(var12);
         throw var12;
      } catch (RuntimeException var13) {
         log_unwrapException(var13);
         throw var13;
      }
   }

   static RunnerResult wrap(Context context) throws IOException {
      try {
         if (null == context) {
            throw new IllegalArgumentException("Expected non-null Context.");
         } else {
            SSLEngine engine = context.getSslEngine();
            RunnerResult result;
            if (null != engine && !engine.isOutboundDone()) {
               context.getSync().lock(JaSSLEngineSynchronizer.LockState.OUTBOUND);

               try {
                  result = doTransitions(context, JaSSLEngineRunner.Method.WRAP);
               } finally {
                  context.getSync().unlock();
               }
            } else {
               result = JaSSLEngineRunner.RunnerResult.CLOSED;
            }

            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "JaSSLEngineRunner.wrap result={0}.", result);
            }

            return result;
         }
      } catch (InterruptedIOException var14) {
         throw var14;
      } catch (SSLException var15) {
         log_wrapException(var15);

         try {
            closeOutbound(context);
         } catch (Exception var11) {
         }

         throw var15;
      } catch (IOException var16) {
         log_wrapException(var16);

         try {
            closeOutbound(context);
         } catch (Exception var12) {
         }

         throw var16;
      } catch (RuntimeException var17) {
         log_wrapException(var17);
         throw var17;
      }
   }

   static RunnerResult closeOutbound(Context context) throws IOException {
      try {
         if (null == context) {
            throw new IllegalArgumentException("Expected non-null Context.");
         } else {
            SSLEngine engine = context.getSslEngine();
            RunnerResult result;
            if (null != engine && !engine.isOutboundDone()) {
               context.getSync().lock(JaSSLEngineSynchronizer.LockState.OUTBOUND);

               try {
                  engine.closeOutbound();
                  result = doTransitions(context, JaSSLEngineRunner.Method.WRAP);
               } finally {
                  context.getSync().unlock();
               }
            } else {
               result = JaSSLEngineRunner.RunnerResult.CLOSED;
            }

            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "JaSSLEngineRunner.closeOutbound result={0}.", result);
            }

            return result;
         }
      } catch (InterruptedIOException var9) {
         throw var9;
      } catch (IOException var10) {
         log_closeOutboundException(var10);
         throw var10;
      } catch (RuntimeException var11) {
         log_closeOutboundException(var11);
         throw var11;
      }
   }

   static boolean isOutboundDone(Context context) {
      try {
         if (null == context) {
            throw new IllegalArgumentException("Expected non-null Context.");
         } else {
            SSLEngine engine = context.getSslEngine();
            boolean result = null == engine || engine.isOutboundDone();
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "JaSSLEngineRunner.isOutboundDone result={0}, engine={1}", result, engine);
            }

            return result;
         }
      } catch (RuntimeException var3) {
         if (JaLogger.isLoggable(Level.FINE)) {
            JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, var3, "Unable to complete JaSSLEngineRunner.isOutboundDone.");
         }

         throw var3;
      }
   }

   static RunnerResult closeInbound(Context context, boolean expectCloseNotify) throws IOException {
      try {
         if (null == context) {
            throw new IllegalArgumentException("Expected non-null Context.");
         } else {
            SSLEngine engine = context.getSslEngine();
            RunnerResult result;
            if (null != engine && !engine.isInboundDone()) {
               context.getSync().lock(JaSSLEngineSynchronizer.LockState.INBOUND);

               try {
                  result = doTransitions(context, JaSSLEngineRunner.Method.UNWRAP);
                  if (expectCloseNotify && JaSSLEngineRunner.RunnerResult.CLOSED != result) {
                     engine.closeInbound();
                  }
               } finally {
                  context.getSync().unlock();
               }
            } else {
               result = JaSSLEngineRunner.RunnerResult.CLOSED;
            }

            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "JaSSLEngineRunner.closeInbound result={0}, expectCloseNotify={1}.", result, expectCloseNotify);
            }

            return result;
         }
      } catch (InterruptedIOException var10) {
         throw var10;
      } catch (IOException var11) {
         log_closeInboundException(var11, expectCloseNotify);
         throw var11;
      } catch (RuntimeException var12) {
         log_closeInboundException(var12, expectCloseNotify);
         throw var12;
      }
   }

   static boolean isInboundDone(Context context) {
      try {
         if (null == context) {
            throw new IllegalArgumentException("Expected non-null Context.");
         } else {
            SSLEngine engine = context.getSslEngine();
            boolean result = null == engine || engine.isInboundDone();
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "JaSSLEngineRunner.isInboundDone result={0}, engine={1}", result, engine);
            }

            return result;
         }
      } catch (RuntimeException var3) {
         if (JaLogger.isLoggable(Level.FINE)) {
            JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, var3, "Unable to complete JaSSLEngineRunner.isInboundDone.");
         }

         throw var3;
      }
   }

   static void close(Context context, boolean expectCloseNotify) throws IOException {
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "Attempting to execute JaSSLEngineRunner.close, expectCloseNotify={0}.", expectCloseNotify);
      }

      if (null == context) {
         if (JaLogger.isLoggable(Level.FINER)) {
            JaLogger.log(Level.FINER, JaLogger.Component.SSLENGINE, "JaSSLEngineRunner.Context is null, no SSL context to close.");
         }

      } else {
         RunnerResult result = closeOutbound(context);
         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "During JaSSLEngineRunner.close, closeOutbound result={0}", result);
         }

         result = closeInbound(context, expectCloseNotify);
         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "During JaSSLEngineRunner.close, closeInbound result={0}", result);
         }

      }
   }

   static {
      HashMap transitionsMap = new HashMap(64);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.OK, HandshakeStatus.NOT_HANDSHAKING), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.OK, HandshakeStatus.NEED_TASK), TRANSITION_NEED_TASK);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.OK, HandshakeStatus.NEED_UNWRAP), TRANSITION_NEED_UNWRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.OK, HandshakeStatus.NEED_WRAP), TRANSITION_NEED_WRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.OK, HandshakeStatus.FINISHED), TRANSITION_HANDSHAKE_FINISHED);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.BUFFER_OVERFLOW, HandshakeStatus.NOT_HANDSHAKING), TRANSITION_BUFFER_OVERFLOW_WRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.BUFFER_OVERFLOW, HandshakeStatus.NEED_TASK), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.BUFFER_OVERFLOW, HandshakeStatus.NEED_UNWRAP), TRANSITION_BUFFER_OVERFLOW_UNWRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.BUFFER_OVERFLOW, HandshakeStatus.NEED_WRAP), TRANSITION_BUFFER_OVERFLOW_WRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.BUFFER_OVERFLOW, HandshakeStatus.FINISHED), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.BUFFER_UNDERFLOW, HandshakeStatus.NOT_HANDSHAKING), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.BUFFER_UNDERFLOW, HandshakeStatus.NEED_TASK), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.BUFFER_UNDERFLOW, HandshakeStatus.NEED_UNWRAP), TRANSITION_BUFFER_UNDERFLOW_UNWRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.BUFFER_UNDERFLOW, HandshakeStatus.NEED_WRAP), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.BUFFER_UNDERFLOW, HandshakeStatus.FINISHED), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.CLOSED, HandshakeStatus.NOT_HANDSHAKING), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.CLOSED, HandshakeStatus.NEED_TASK), TRANSITION_NEED_TASK);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.CLOSED, HandshakeStatus.NEED_UNWRAP), TRANSITION_NEED_UNWRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.CLOSED, HandshakeStatus.NEED_WRAP), TRANSITION_NEED_WRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.WRAP, Status.CLOSED, HandshakeStatus.FINISHED), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.OK, HandshakeStatus.NOT_HANDSHAKING), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.OK, HandshakeStatus.NEED_TASK), TRANSITION_NEED_TASK);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.OK, HandshakeStatus.NEED_UNWRAP), TRANSITION_NEED_UNWRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.OK, HandshakeStatus.NEED_WRAP), TRANSITION_NEED_WRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.OK, HandshakeStatus.FINISHED), TRANSITION_HANDSHAKE_FINISHED);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.BUFFER_OVERFLOW, HandshakeStatus.NOT_HANDSHAKING), TRANSITION_BUFFER_OVERFLOW_UNWRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.BUFFER_OVERFLOW, HandshakeStatus.NEED_TASK), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.BUFFER_OVERFLOW, HandshakeStatus.NEED_UNWRAP), TRANSITION_BUFFER_OVERFLOW_UNWRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.BUFFER_OVERFLOW, HandshakeStatus.NEED_WRAP), TRANSITION_BUFFER_OVERFLOW_WRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.BUFFER_OVERFLOW, HandshakeStatus.FINISHED), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.BUFFER_UNDERFLOW, HandshakeStatus.NOT_HANDSHAKING), TRANSITION_BUFFER_UNDERFLOW_UNWRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.BUFFER_UNDERFLOW, HandshakeStatus.NEED_TASK), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.BUFFER_UNDERFLOW, HandshakeStatus.NEED_UNWRAP), TRANSITION_BUFFER_UNDERFLOW_UNWRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.BUFFER_UNDERFLOW, HandshakeStatus.NEED_WRAP), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.BUFFER_UNDERFLOW, HandshakeStatus.FINISHED), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.CLOSED, HandshakeStatus.NOT_HANDSHAKING), (Object)null);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.CLOSED, HandshakeStatus.NEED_TASK), TRANSITION_NEED_TASK);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.CLOSED, HandshakeStatus.NEED_UNWRAP), TRANSITION_NEED_UNWRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.CLOSED, HandshakeStatus.NEED_WRAP), TRANSITION_NEED_WRAP);
      transitionsMap.put(new SSLEngineState(JaSSLEngineRunner.Method.UNWRAP, Status.CLOSED, HandshakeStatus.FINISHED), (Object)null);
      sslEngineTransitions = Collections.unmodifiableMap(transitionsMap);
   }

   private static class Transition_BufferUnderflow_Unwrap implements SSLEngineStateTransition {
      private Transition_BufferUnderflow_Unwrap() {
      }

      public TransitionResult getNextState(Method method, Context context, SSLEngineResult previousResult) throws IOException {
         SSLSession session = context.getSslEngine().getSession();
         int netBufferMax = session.getPacketBufferSize() + 50;
         if (netBufferMax > context.getBufferNetIn().capacity()) {
            ByteBuffer newBuff = ByteBuffer.allocate(netBufferMax);
            context.getBufferNetIn().flip();
            newBuff.put(context.getBufferNetIn());
            context.setBufferNetIn(newBuff);
         }

         return 0 == context.fillBufferNetIn() ? new TransitionResult(JaSSLEngineRunner.RunnerResult.INCOMPLETE_NETWORK_READ, previousResult) : JaSSLEngineRunner.TRANSITION_NEED_UNWRAP.getNextState(method, context, previousResult);
      }

      // $FF: synthetic method
      Transition_BufferUnderflow_Unwrap(Object x0) {
         this();
      }
   }

   private static class Transition_BufferOverflow_Unwrap implements SSLEngineStateTransition {
      private Transition_BufferOverflow_Unwrap() {
      }

      public TransitionResult getNextState(Method method, Context context, SSLEngineResult previousResult) throws IOException {
         SSLSession session = context.getSslEngine().getSession();
         int appBufferMax = session.getApplicationBufferSize() + 50;
         if (appBufferMax > context.getBufferAppIn().capacity()) {
            ByteBuffer newBuff = ByteBuffer.allocate(appBufferMax);
            context.getBufferAppIn().flip();
            newBuff.put(context.getBufferAppIn());
            context.setBufferAppIn(newBuff);
            TransitionResult var7 = JaSSLEngineRunner.TRANSITION_NEED_UNWRAP.getNextState(method, context, previousResult);
            return var7;
         } else {
            return new TransitionResult(JaSSLEngineRunner.RunnerResult.NEED_APPLICATION_READ, previousResult);
         }
      }

      // $FF: synthetic method
      Transition_BufferOverflow_Unwrap(Object x0) {
         this();
      }
   }

   private static class Transition_BufferOverflow_Wrap implements SSLEngineStateTransition {
      private Transition_BufferOverflow_Wrap() {
      }

      public TransitionResult getNextState(Method method, Context context, SSLEngineResult previousResult) throws IOException {
         SSLSession session = context.getSslEngine().getSession();
         int netBufferMax = session.getPacketBufferSize() + 50;
         TransitionResult result;
         if (netBufferMax > context.getBufferNetOut().capacity()) {
            ByteBuffer newBuff = ByteBuffer.allocate(netBufferMax);
            context.getBufferNetOut().flip();
            newBuff.put(context.getBufferNetOut());
            context.setBufferNetOut(newBuff);
         } else if (!context.flushBufferNetOut()) {
            result = new TransitionResult(JaSSLEngineRunner.RunnerResult.INCOMPLETE_NETWORK_WRITE, previousResult);
            return result;
         }

         result = JaSSLEngineRunner.TRANSITION_NEED_WRAP.getNextState(method, context, previousResult);
         return result;
      }

      // $FF: synthetic method
      Transition_BufferOverflow_Wrap(Object x0) {
         this();
      }
   }

   private static class Transition_NeedTask implements SSLEngineStateTransition {
      private Transition_NeedTask() {
      }

      public TransitionResult getNextState(Method method, Context context, SSLEngineResult previousResult) throws IOException {
         Runnable task;
         for(; (task = context.getSslEngine().getDelegatedTask()) != null; task.run()) {
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "Transition_NeedTask: Running delegated task: method={0}, last result={1}, task={2}.", method, previousResult, task.getClass().getName());
            }
         }

         SSLEngineResult.HandshakeStatus hStatus = context.getSslEngine().getHandshakeStatus();
         return new TransitionResult(JaSSLEngineRunner.RunnerResult.OK, new SSLEngineResult(Status.OK, hStatus, 0, 0));
      }

      // $FF: synthetic method
      Transition_NeedTask(Object x0) {
         this();
      }
   }

   private static class Transition_HandshakeFinished implements SSLEngineStateTransition {
      private Transition_HandshakeFinished() {
      }

      public TransitionResult getNextState(Method method, Context context, SSLEngineResult previousResult) throws IOException {
         context.getSync().unlock();
         TransitionResult result;
         switch (method) {
            case UNWRAP:
               result = JaSSLEngineRunner.TRANSITION_NEED_UNWRAP.getNextState(method, context, previousResult);
               break;
            case WRAP:
               result = JaSSLEngineRunner.TRANSITION_NEED_WRAP.getNextState(method, context, previousResult);
               break;
            default:
               throw new IllegalStateException("Unsupported Method " + method);
         }

         return result;
      }

      // $FF: synthetic method
      Transition_HandshakeFinished(Object x0) {
         this();
      }
   }

   private static class Transition_NeedWrap implements SSLEngineStateTransition {
      private Transition_NeedWrap() {
      }

      public TransitionResult getNextState(Method method, Context context, SSLEngineResult previousResult) throws IOException {
         TransitionResult result;
         try {
            context.getBufferAppOut().flip();
            result = new TransitionResult(JaSSLEngineRunner.RunnerResult.OK, context.getSslEngine().wrap(context.getBufferAppOut(), context.getBufferNetOut()));
         } finally {
            context.getBufferAppOut().compact();
         }

         SSLEngineResult var5 = result.getSslEngineResult();
         if (var5.bytesProduced() > 0 && !context.flushBufferNetOut()) {
            result = new TransitionResult(JaSSLEngineRunner.RunnerResult.INCOMPLETE_NETWORK_WRITE, result.getSslEngineResult());
            return result;
         } else {
            return result;
         }
      }

      // $FF: synthetic method
      Transition_NeedWrap(Object x0) {
         this();
      }
   }

   private static class Transition_NeedUnwrap implements SSLEngineStateTransition {
      private Transition_NeedUnwrap() {
      }

      public TransitionResult getNextState(Method method, Context context, SSLEngineResult previousResult) throws IOException {
         int netInRemaining;
         try {
            context.getBufferNetIn().flip();
            netInRemaining = context.getBufferNetIn().remaining();
         } finally {
            context.getBufferNetIn().compact();
         }

         if (netInRemaining <= 0 && 0 == context.fillBufferNetIn()) {
            return new TransitionResult(JaSSLEngineRunner.RunnerResult.INCOMPLETE_NETWORK_READ, previousResult);
         } else {
            TransitionResult result;
            try {
               context.getBufferNetIn().flip();
               result = new TransitionResult(JaSSLEngineRunner.RunnerResult.OK, context.getSslEngine().unwrap(context.getBufferNetIn(), context.getBufferAppIn()));
            } finally {
               context.getBufferNetIn().compact();
            }

            return result;
         }
      }

      // $FF: synthetic method
      Transition_NeedUnwrap(Object x0) {
         this();
      }
   }

   private interface SSLEngineStateTransition {
      TransitionResult getNextState(Method var1, Context var2, SSLEngineResult var3) throws IOException;
   }

   static class SSLEngineState {
      private final Method method;
      private final SSLEngineResult.Status status;
      private final SSLEngineResult.HandshakeStatus handshakeStatus;

      private SSLEngineState(Method method, SSLEngineResult result) {
         this(method, result.getStatus(), result.getHandshakeStatus());
      }

      private SSLEngineState(Method method, SSLEngineResult.Status status, SSLEngineResult.HandshakeStatus handshakeStatus) {
         if (null != method && null != status && null != handshakeStatus) {
            this.method = method;
            this.status = status;
            this.handshakeStatus = handshakeStatus;
         } else {
            throw new IllegalArgumentException("Expected non-null arguments.");
         }
      }

      Method getMethod() {
         return this.method;
      }

      SSLEngineResult.Status getStatus() {
         return this.status;
      }

      SSLEngineResult.HandshakeStatus getHandshakeStatus() {
         return this.handshakeStatus;
      }

      public int hashCode() {
         return this.handshakeStatus.hashCode();
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof SSLEngineState)) {
            return false;
         } else {
            SSLEngineState other = (SSLEngineState)obj;
            return other.method == this.method && other.status == this.status && other.handshakeStatus == this.handshakeStatus;
         }
      }

      public String toString() {
         return MessageFormat.format("SSLEngine State: Method={0}, Status={1}, HandshakeStatus={2}", this.method, this.status, this.handshakeStatus);
      }

      // $FF: synthetic method
      SSLEngineState(Method x0, SSLEngineResult x1, Object x2) {
         this(x0, x1);
      }

      // $FF: synthetic method
      SSLEngineState(Method x0, SSLEngineResult.Status x1, SSLEngineResult.HandshakeStatus x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   static final class TransitionResult {
      private final RunnerResult runnerResult;
      private final SSLEngineResult sslEngineResult;

      private TransitionResult(RunnerResult runnerResult, SSLEngineResult sslEngineResult) {
         if (null == runnerResult) {
            throw new IllegalArgumentException("Expected non-null RunnerResult.");
         } else {
            this.runnerResult = runnerResult;
            this.sslEngineResult = sslEngineResult;
         }
      }

      RunnerResult getRunnerResult() {
         return this.runnerResult;
      }

      SSLEngineResult getSslEngineResult() {
         return this.sslEngineResult;
      }

      public String toString() {
         return MessageFormat.format("TransitionResult: RunnerResult={0}, SSLEngineResult={1}", this.runnerResult, this.sslEngineResult);
      }

      // $FF: synthetic method
      TransitionResult(RunnerResult x0, SSLEngineResult x1, Object x2) {
         this(x0, x1);
      }
   }

   static final class Context {
      private static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocate(0);
      private final JaSSLEngineSynchronizer sync = new JaSSLEngineSynchronizer();
      private SSLEngine sslEngine;
      private ByteBuffer bufferNetIn;
      private ByteBuffer bufferAppIn;
      private ByteBuffer bufferAppOut;
      private ByteBuffer bufferNetOut;
      private ReadableByteChannel networkReadableByteChannel;
      private WritableByteChannel networkWritableByteChannel;
      private SSLIOContext sslIoContext;

      Context() {
         this.bufferNetIn = EMPTY_BUFFER;
         this.bufferAppIn = EMPTY_BUFFER;
         this.bufferAppOut = EMPTY_BUFFER;
         this.bufferNetOut = EMPTY_BUFFER;
      }

      SSLEngine getSslEngine() {
         return this.sslEngine;
      }

      private void setSslEngine(SSLEngine sslEngine) {
         this.sslEngine = sslEngine;
      }

      private ByteBuffer getBufferNetIn() {
         return this.bufferNetIn;
      }

      private void setBufferNetIn(ByteBuffer bufferNetIn) {
         this.bufferNetIn = bufferNetIn;
      }

      ByteBuffer getBufferAppIn() {
         return this.bufferAppIn;
      }

      void setBufferAppIn(ByteBuffer bufferAppIn) {
         this.bufferAppIn = bufferAppIn;
      }

      ByteBuffer getBufferAppOut() {
         return this.bufferAppOut;
      }

      void setBufferAppOut(ByteBuffer bufferAppOut) {
         if (null == bufferAppOut) {
            bufferAppOut = EMPTY_BUFFER;
         }

         this.bufferAppOut = bufferAppOut;
      }

      private ByteBuffer getBufferNetOut() {
         return this.bufferNetOut;
      }

      private void setBufferNetOut(ByteBuffer bufferNetOut) {
         this.bufferNetOut = bufferNetOut;
      }

      private ReadableByteChannel getNetworkReadableByteChannel() {
         return this.networkReadableByteChannel;
      }

      private void setNetworkReadableByteChannel(ReadableByteChannel networkReadableByteChannel) {
         this.networkReadableByteChannel = networkReadableByteChannel;
      }

      private WritableByteChannel getNetworkWritableByteChannel() {
         return this.networkWritableByteChannel;
      }

      private void setNetworkWritableByteChannel(WritableByteChannel networkWritableByteChannel) {
         this.networkWritableByteChannel = networkWritableByteChannel;
      }

      JaSSLEngineSynchronizer getSync() {
         return this.sync;
      }

      SSLIOContext getSslIoContext() {
         return this.sslIoContext;
      }

      private void setSslIoContext(SSLIOContext sslIoContext) {
         this.sslIoContext = sslIoContext;
      }

      boolean notCompleteSSLRecord() {
         return this.sslIoContext != null && this.sslIoContext.isMuxerActivated() && !this.sslIoContext.hasSSLRecord();
      }

      InputStream getMuxerInputStream() {
         return this.sslIoContext == null ? null : this.sslIoContext.getMuxerIS();
      }

      boolean isMuxerActivated() {
         return this.sslIoContext != null && this.sslIoContext.isMuxerActivated();
      }

      void init(SSLEngine sslEngine, ReadableByteChannel networkReadableByteChannel, WritableByteChannel networkWritableByteChannel, SSLIOContext sslIoContext) throws IOException {
         if (null == sslEngine) {
            throw new IllegalArgumentException("Expected non-null SSLEngine.");
         } else if (null == networkReadableByteChannel) {
            throw new IllegalArgumentException("Expected non-null networkReadableByteChannel.");
         } else if (null == networkWritableByteChannel) {
            throw new IllegalArgumentException("Expected non-null networkWritableByteChannel.");
         } else {
            this.setSslEngine(sslEngine);
            SSLSession session = sslEngine.getSession();
            int appBufferMax = session.getApplicationBufferSize() + 50;
            int netBufferMax = session.getPacketBufferSize() + 50;
            this.setBufferAppIn(ByteBuffer.allocate(appBufferMax));
            this.setBufferAppOut(EMPTY_BUFFER);
            this.setBufferNetIn(ByteBuffer.allocate(netBufferMax));
            this.setBufferNetOut(ByteBuffer.allocate(netBufferMax));
            this.setNetworkReadableByteChannel(networkReadableByteChannel);
            this.setNetworkWritableByteChannel(networkWritableByteChannel);
            this.setSslIoContext(sslIoContext);
         }
      }

      boolean flushBufferNetOut() throws IOException {
         ByteBuffer bufferNetOut = this.getBufferNetOut();

         boolean var2;
         try {
            bufferNetOut.flip();
            if (bufferNetOut.remaining() > 0) {
               WritableByteChannel writable = this.getNetworkWritableByteChannel();
               int flushedCount = writable.write(bufferNetOut);
               int remaining = bufferNetOut.remaining();
               boolean var5;
               if (remaining > 0) {
                  if (JaLogger.isLoggable(Level.FINER)) {
                     JaLogger.log(Level.FINER, JaLogger.Component.SSLENGINE, "Unable to completely flush outbound network buffer. Remaining bytes={0}, Flushed bytes={1}", remaining, flushedCount);
                  }

                  var5 = false;
                  return var5;
               }

               if (JaLogger.isLoggable(Level.FINEST)) {
                  JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "Completely flushed outbound network buffer. Flushed bytes={0}", flushedCount);
               }

               var5 = true;
               return var5;
            }

            var2 = true;
         } finally {
            bufferNetOut.compact();
         }

         return var2;
      }

      int fillBufferNetIn() throws IOException {
         ByteBuffer bufferNetIn = this.getBufferNetIn();
         if (bufferNetIn.remaining() <= 0) {
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "No data read, bufferNetIn is full.");
            }

            return 0;
         } else {
            int bytesPending;
            label407: {
               try {
                  bufferNetIn.flip();
                  if (bufferNetIn.remaining() <= 0) {
                     break label407;
                  }

                  if (JaLogger.isLoggable(Level.FINER)) {
                     JaLogger.log(Level.FINER, JaLogger.Component.SSLENGINE, "Attempted to fill bufferNetIn before reading entire previous SSL record.");
                  }

                  bytesPending = 0;
               } finally {
                  bufferNetIn.compact();
               }

               return bytesPending;
            }

            if (this.notCompleteSSLRecord()) {
               throw new InterruptedIOException();
            } else {
               int bytesRead;
               do {
                  if (0 == (bytesPending = this.getBytesPending())) {
                     int bytesRead;
                     try {
                        bufferNetIn.flip();
                        bytesRead = bufferNetIn.remaining();
                     } finally {
                        bufferNetIn.compact();
                     }

                     return bytesRead;
                  }

                  if (bytesPending > bufferNetIn.remaining()) {
                     String msg = MessageFormat.format("Unexpectedly large SSL Record size, bytesPending={0}, bufferNetIn.remaining={1}.", bytesPending, bufferNetIn.remaining());
                     throw new IllegalStateException(msg);
                  }

                  InputStream muxerInputStream = this.getMuxerInputStream();
                  if (muxerInputStream == null) {
                     int origLimit = bufferNetIn.limit();

                     try {
                        bufferNetIn.limit(bufferNetIn.position() + bytesPending);
                        ReadableByteChannel readable = this.getNetworkReadableByteChannel();
                        bytesRead = readable.read(bufferNetIn);
                     } finally {
                        bufferNetIn.limit(origLimit);
                     }
                  } else {
                     byte[] fragment = new byte[bytesPending];
                     int offset = 0;
                     int remaining = fragment.length;

                     while((bytesRead = muxerInputStream.read(fragment, offset, remaining)) < remaining) {
                        if (0 == bytesRead) {
                           String msg = MessageFormat.format("Unable to read complete SSL record from muxer input stream, no bytes available, remaining={0} bytes.", remaining);
                           if (JaLogger.isLoggable(Level.FINE)) {
                              JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, msg);
                           }

                           throw new IllegalStateException(msg);
                        }

                        if (-1 == bytesRead) {
                           SSLEngine engine = this.getSslEngine();
                           if (null != engine) {
                              engine.closeInbound();
                           }

                           throw new ClosedChannelException();
                        }

                        remaining -= bytesRead;
                        offset += bytesRead;
                        Thread.currentThread();
                        Thread.yield();
                     }

                     bufferNetIn.put(fragment);
                  }

                  if (-1 == bytesRead) {
                     SSLEngine engine = this.getSslEngine();
                     if (null != engine) {
                        engine.closeInbound();
                     }

                     throw new ClosedChannelException();
                  }
               } while(0 != bytesRead);

               if (this.isMuxerActivated()) {
                  throw new IllegalStateException("Muxer is activated, but available bytes are not readable.");
               } else {
                  return 0;
               }
            }
         }
      }

      private int getBytesPending() throws IOException {
         ByteBuffer bufferNetIn = this.getBufferNetIn();
         int fragLen = bufferNetIn.position();
         int recLen = false;
         if (fragLen == 0) {
            return 1;
         } else {
            int recLen;
            String msg;
            if ((bufferNetIn.get(0) & 128) == 128) {
               if (fragLen < 2) {
                  return 2 - fragLen;
               }

               int firstByte = readUInt8(bufferNetIn.get(0));
               int secondByte = readUInt8(bufferNetIn.get(1));
               recLen = (firstByte & 127) << 8 | secondByte + 2;
            } else {
               if (fragLen < 5) {
                  return 5 - fragLen;
               }

               recLen = readUInt16(bufferNetIn.get(3), bufferNetIn.get(4)) + 5;
               if (recLen < 0) {
                  msg = MessageFormat.format("Illegal negative SSL record length field, value={0}.", recLen);
                  throw new IOException(msg);
               }
            }

            if (recLen < fragLen) {
               msg = MessageFormat.format("Buffer filled beyond SSL record length. SSLRecord length={0}, byte previously read={1}.", recLen, fragLen);
               throw new IllegalStateException(msg);
            } else {
               return recLen - fragLen;
            }
         }
      }

      private static int readUInt8(byte in) {
         return in & 255;
      }

      private static int readUInt16(byte msb, byte lsb) {
         int i1 = readUInt8(msb);
         int i2 = readUInt8(lsb);
         return (i1 << 8) + i2;
      }

      private void ensureHandshakeLockIfNeeded(SSLEngineResult.HandshakeStatus hs) {
         if (null == hs) {
            throw new IllegalArgumentException("Non-null HandshakeStatus expected.");
         } else if (this.getSync().getLockState() != JaSSLEngineSynchronizer.LockState.HANDSHAKE) {
            if (hs == HandshakeStatus.NEED_WRAP && this.getSync().getLockState() == JaSSLEngineSynchronizer.LockState.INBOUND) {
               this.getSync().lock(JaSSLEngineSynchronizer.LockState.HANDSHAKE);
            } else if (hs == HandshakeStatus.NEED_UNWRAP && this.getSync().getLockState() == JaSSLEngineSynchronizer.LockState.OUTBOUND) {
               this.getSync().lock(JaSSLEngineSynchronizer.LockState.HANDSHAKE);
            }

         }
      }

      private void ensureHandshakeUnlock() {
         while(this.getSync().getLockState() == JaSSLEngineSynchronizer.LockState.HANDSHAKE) {
            this.getSync().unlock();
         }

      }
   }

   static enum RunnerResult {
      OK,
      INCOMPLETE_NETWORK_READ,
      INCOMPLETE_NETWORK_WRITE,
      NEED_APPLICATION_READ,
      CLOSED;
   }

   static enum Method {
      WRAP,
      UNWRAP;
   }
}
