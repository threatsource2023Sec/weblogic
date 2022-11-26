package weblogic.diagnostics.instrumentation;

import weblogic.diagnostics.debug.DebugLogger;

public class InstrumentationSupportBase {
   protected static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticInstrumentationActions");
   private static final DiagnosticActionState[] EMPTY_ACTION_STATES = new DiagnosticActionState[0];

   public static DiagnosticActionState[] getActionStates(DiagnosticAction[] actions) {
      if (actions == null) {
         return EMPTY_ACTION_STATES;
      } else {
         int size = actions.length;
         DiagnosticActionState[] states = new DiagnosticActionState[size];

         for(int i = 0; i < size; ++i) {
            try {
               states[i] = ((AroundDiagnosticAction)actions[i]).createState();
            } catch (Throwable var5) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("failure calling createState for action class: " + ((AroundDiagnosticAction)actions[i]).getClass().getName(), var5);
               }
            }
         }

         return states;
      }
   }

   public static void applyActionStates(LocalHolder holder) {
      DiagnosticAction[] actions = holder.monitorHolder[holder.monitorIndex].actions;
      if (actions != null) {
         int size = actions.length;
         DiagnosticActionState[] states = new DiagnosticActionState[size];

         for(int i = 0; i < size; ++i) {
            try {
               states[i] = ((AroundDiagnosticAction)actions[i]).createState();
            } catch (Throwable var6) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("failure calling createState for action class: " + ((AroundDiagnosticAction)actions[i]).getClass().getName(), var6);
               }
            }
         }

         holder.monitorHolder[holder.monitorIndex].states = states;
      }
   }

   public static Object convertToObject(byte b) throws ClassCastException {
      return b;
   }

   public static Object convertToObject(char c) throws ClassCastException {
      return c;
   }

   public static Object convertToObject(short s) throws ClassCastException {
      return s;
   }

   public static Object convertToObject(int i) throws ClassCastException {
      return i;
   }

   public static Object convertToObject(long l) throws ClassCastException {
      return l;
   }

   public static Object convertToObject(float f) throws ClassCastException {
      return f;
   }

   public static Object convertToObject(double d) throws ClassCastException {
      return d;
   }

   public static Object convertToObject(boolean b) throws ClassCastException {
      return b;
   }

   public static byte convertFromObject(Byte val) {
      return val;
   }

   public static char convertFromObject(Character val) {
      return val;
   }

   public static short convertFromObject(Short val) {
      return val;
   }

   public static int convertFromObject(Integer val) {
      return val;
   }

   public static long convertFromObject(Long val) {
      return val;
   }

   public static float convertFromObject(Float val) {
      return val;
   }

   public static double convertFromObject(Double val) {
      return val;
   }

   public static boolean convertFromObject(Boolean val) {
      return val;
   }
}
