package oracle.jrockit.jfr;

import com.oracle.jrockit.jfr.InvalidEventDefinitionException;
import com.oracle.jrockit.jfr.NoSuchEventException;
import com.oracle.jrockit.jfr.Producer;
import java.lang.management.ManagementPermission;
import java.security.Permission;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import javax.management.MBeanServer;
import oracle.jrockit.jfr.events.EventDescriptor;
import oracle.jrockit.jfr.events.EventHandler;
import oracle.jrockit.jfr.events.JavaEventDescriptor;

public abstract class JFR {
   static final int NATIVE_ID_HIGHEST = 4096;
   public static final long INVALID_THRESHOLD = -1L;
   public static final long INVALID_PERIOD = -1L;
   public static final int JFR_PRODUCER_ID = 0;
   public static final int JVM_PRODUCER_ID = 1;
   private static volatile JFR jfr;
   private static final Permission controlPermission = new ManagementPermission("control");

   static void checkControl() {
      SecurityManager m = System.getSecurityManager();
      if (m != null) {
         m.checkPermission(controlPermission);
      }

   }

   static InternalError cannotHappen(Throwable t) {
      return (InternalError)(new InternalError(t.getMessage())).initCause(t);
   }

   public static JFR get() {
      checkControl();
      if (jfr != null) {
         return jfr;
      } else {
         Class var0 = JFR.class;
         synchronized(JFR.class) {
            if (jfr == null) {
               try {
                  jfr = VMJFR.create();
               } catch (NoClassDefFoundError var3) {
               } catch (UnsatisfiedLinkError var4) {
               }
            }

            if (jfr == null) {
               label35: {
                  JFR var10000;
                  try {
                     jfr = PureJavaJFR.create();
                     var10000 = jfr;
                  } catch (NoClassDefFoundError var5) {
                     break label35;
                  }

                  return var10000;
               }
            }

            if (jfr == null) {
               jfr = new DeactivatedJFR();
            }
         }

         return jfr;
      }
   }

   static void bind(JFR ajfr) {
      Class var1 = JFR.class;
      synchronized(JFR.class) {
         assert jfr == null;

         jfr = ajfr;
      }
   }

   JFR() {
      if (jfr != null) {
         throw new InternalError("JRA init sequence out of wack.");
      }
   }

   public abstract boolean active();

   public boolean isNativeImplementation() {
      return false;
   }

   public abstract FlightRecorder getMBean();

   public abstract void bind(MBeanServer var1);

   public abstract void unbind(MBeanServer var1);

   public abstract EventHandler createHandler(JavaEventDescriptor var1, Class var2, Map var3) throws InvalidEventDefinitionException;

   public abstract void addProducer(Producer var1, int var2, List var3, Map var4);

   public abstract void removeProducer(int var1);

   public abstract void addEventsToRegisteredProducer(Producer var1, int var2, List var3, Map var4);

   public abstract EventDescriptor getEvent(int var1) throws NoSuchEventException;

   public abstract ProducerDescriptor getProducer(int var1) throws NoSuchProducerException;

   public abstract Collection getProducers();

   public abstract Collection getEvents();

   public abstract int getpid();

   public abstract int nextID();

   public abstract Timer getTimer();

   protected abstract void storeConstpool(StringConstantPool var1);

   protected abstract void addConstpool(StringConstantPool var1);

   protected abstract void removeConstpool(StringConstantPool var1);
}
