package weblogic.diagnostics.instrumentation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;

public interface EventDispatcher {
   void dispatch(String var1, EventPayload var2);

   public static final class Helper {
      private static EventDispatcher singleton = null;

      private static synchronized EventDispatcher getInstance() {
         if (singleton == null) {
            try {
               Class clz = Class.forName("weblogic.diagnostics.instrumentation.EventQueue");
               Class[] argTypes = new Class[0];
               Object[] args = new Object[0];
               Method method = clz.getDeclaredMethod("getInstance", argTypes);
               singleton = (EventDispatcher)method.invoke((Object)null, args);
            } catch (ClassNotFoundException var4) {
               UnexpectedExceptionHandler.handle("Unexpected exception", var4);
            } catch (NoSuchMethodException var5) {
               UnexpectedExceptionHandler.handle("Unexpected exception", var5);
            } catch (IllegalAccessException var6) {
               UnexpectedExceptionHandler.handle("Unexpected exception", var6);
            } catch (InvocationTargetException var7) {
               UnexpectedExceptionHandler.handle("Unexpected exception", var7);
            }
         }

         return singleton;
      }

      public static void dispatch(String eventType, EventPayload payload) {
         EventDispatcher dispatcher = getInstance();
         if (dispatcher != null) {
            dispatcher.dispatch(eventType, payload);
         }

      }
   }
}
