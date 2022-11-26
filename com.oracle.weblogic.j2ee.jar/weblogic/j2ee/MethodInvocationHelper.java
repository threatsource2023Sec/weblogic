package weblogic.j2ee;

import java.util.EmptyStackException;
import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.ejb.spi.BeanInfo;
import weblogic.ejb.spi.SessionBeanInfo;
import weblogic.kernel.ThreadLocalStack;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

@Service
public final class MethodInvocationHelper implements FastThreadLocalMarker {
   private static final DebugCategory debug = Debug.getCategory("weblogic.j2ee.MethodInvocation.debug");
   private static final DebugCategory verbose = Debug.getCategory("weblogic.j2ee.MethodInvocation.verbose");
   private static final ThreadLocalStack threadMethodStorage = new ThreadLocalStack(true);

   public static void pushConnectionObject(TrackableConnection conn) {
      if (threadMethodStorage.peek() != null) {
         pushObject(conn);
      }
   }

   public static void pushMethodObject(BeanInfo beanInfo) {
      if (beanInfo == null || beanInfo.hasResourceRefs()) {
         Object obj = new Object();
         pushObject(obj);
      }
   }

   private static void pushObject(Object obj) {
      if (verbose.isEnabled()) {
         Debug.say("pushMethodObject to push: '" + obj.toString() + "' ',  currentMethod is: '" + getCurrentObject() + "' ' ");
      }

      threadMethodStorage.push(obj);
   }

   public static boolean popMethodObject(BeanInfo beanInfo) {
      if (beanInfo != null && !beanInfo.hasResourceRefs()) {
         return false;
      } else {
         boolean isLocalTransactionInProgress = false;
         boolean isStateless = beanInfo instanceof SessionBeanInfo && !((SessionBeanInfo)beanInfo).isStateful();

         for(Object obj = popObject(); obj instanceof TrackableConnection; obj = popObject()) {
            TrackableConnection conn = (TrackableConnection)obj;
            if (isStateless && conn.isLocalTransactionInProgress()) {
               isLocalTransactionInProgress = true;
            }

            conn.connectionClosed();
         }

         return isLocalTransactionInProgress;
      }
   }

   private static Object popObject() {
      if (verbose.isEnabled()) {
         Debug.say("popObject,  before pop is: '" + getCurrentObject() + "'");
      }

      Object obj = null;

      try {
         obj = threadMethodStorage.pop();
      } catch (EmptyStackException var2) {
      }

      if (verbose.isEnabled()) {
         Debug.say("popObject,  after  pop is: '" + getCurrentObject() + "'");
      }

      return obj;
   }

   public static Object getCurrentObject() {
      return threadMethodStorage.get();
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }
}
