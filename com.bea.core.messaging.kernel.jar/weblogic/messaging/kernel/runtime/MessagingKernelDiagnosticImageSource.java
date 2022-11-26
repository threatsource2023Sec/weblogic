package weblogic.messaging.kernel.runtime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.messaging.runtime.DiagnosticImageSource;
import weblogic.timers.TimerManager;

public abstract class MessagingKernelDiagnosticImageSource extends DiagnosticImageSource {
   private static String[] stateNames = new String[]{"send", "receive", "transaction", "ordered", "delayed", "expired", "redelivery_count_exceeded", "paused", "deleted"};

   public static void dumpTimerManager(XMLStreamWriter xsw, TimerManager timerManager) throws XMLStreamException {
      String name = "";
      String workManagerName = "";
      if (timerManager != null) {
         try {
            Class cls = Class.forName("weblogic.timers.internal.TimerManagerImpl");
            if (cls.isInstance(timerManager)) {
               Method meth = cls.getMethod("getName", (Class[])null);
               Object retobj = meth.invoke(timerManager, (Object[])null);
               name = (String)retobj;
               meth = cls.getMethod("getExecutorName", (Class[])null);
               retobj = meth.invoke(timerManager, (Object[])null);
               workManagerName = (String)retobj;
            }
         } catch (ClassNotFoundException var7) {
            throw new XMLStreamException("Got ClassNotFoundException " + var7);
         } catch (NoSuchMethodException var8) {
            throw new XMLStreamException("Got NoSuchMethodException " + var8);
         } catch (IllegalAccessException var9) {
            throw new XMLStreamException("Got IllegalAccessException " + var9);
         } catch (InvocationTargetException var10) {
            throw new XMLStreamException("Got InvocationTargetException " + var10);
         }
      }

      xsw.writeStartElement("TimerManager");
      xsw.writeAttribute("name", name);
      xsw.writeAttribute("workManagerName", workManagerName);
      xsw.writeEndElement();
   }

   private static String getStateNames(int states) {
      StringBuffer sb = new StringBuffer();
      if (states == 0) {
         return "visible";
      } else {
         for(int i = 0; i < stateNames.length; ++i) {
            if ((states & 1 << i) != 0) {
               if (sb.length() > 0) {
                  sb.append(" ");
               }

               sb.append(stateNames[i]);
            }
         }

         return sb.toString();
      }
   }

   public static void dumpMessageStatesAttribute(XMLStreamWriter xsw, int state) throws XMLStreamException {
      xsw.writeAttribute("states", getStateNames(state));
   }
}
