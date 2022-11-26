package weblogic.jdbc.rowset;

import java.io.Serializable;
import java.lang.reflect.Field;
import weblogic.jdbc.JDBCLogger;

class LifeCycle {
   private static final int DESIGNING_CODE = 0;
   private static final int POPULATING_CODE = 1;
   private static final int CONFIGURING_METADATA_CODE = 2;
   private static final int MANIPULATING_CODE = 3;
   private static final int INSERTING_CODE = 4;
   private static final int UPDATING_CODE = 5;
   public static final int DESIGN = 0;
   public static final int CONFIGURE_QUERY = 1;
   public static final int POPULATE = 2;
   public static final int CONFIGURE_METADATA = 3;
   public static final int NAVIGATE = 4;
   public static final int MOVE_TO_INSERT = 5;
   public static final int MOVE_TO_CURRENT = 6;
   public static final int UPDATE = 7;
   public static final int FINISH_UPDATE = 8;
   public static final int ACCEPT_CHANGES = 9;
   public static final int ACCEPT_CHANGES_REUSE = 10;
   private static final int OP_COUNT = 11;
   public static final State DESIGNING = new State(0, new int[]{4, 0, 1, 5, 2});
   public static final State POPULATING = new State(1, new int[]{1, 2, 3, 0, 4, 5, 7, 9, 10});
   public static final State CONFIGURING_METADATA = new State(2, new int[]{3, 4, 5, 7});
   public static final State MANIPULATING = new State(3, new int[]{0, 3, 4, 5, 7, 9, 10});
   public static final State INSERTING = new State(4, new int[]{5, 7, 8, 6, 9, 10}) {
      private static final long serialVersionUID = 7393324511593576778L;

      State next(int op) {
         switch (op) {
            case 7:
            case 8:
               return LifeCycle.INSERTING;
            default:
               return LifeCycle.NEXT[op];
         }
      }
   };
   public static final State UPDATING = new State(5, new int[]{7, 8});
   private static final State[] NEXT = new State[11];
   private static final int PFS = 25;

   private static String getOpName(int op) {
      Field[] f = LifeCycle.class.getFields();

      try {
         for(int i = 0; i < f.length; ++i) {
            if ((f[i].getModifiers() & 25) == 25 && f[i].getType() == Integer.TYPE && f[i].getInt((Object)null) == op) {
               return f[i].getName();
            }
         }
      } catch (IllegalAccessException var3) {
         JDBCLogger.logStackTrace(var3);
      }

      return "unknown op " + op;
   }

   private static String getStateName(State s) {
      Field[] f = LifeCycle.class.getFields();

      try {
         for(int i = 0; i < f.length; ++i) {
            if ((f[i].getModifiers() & 25) == 25 && f[i].getType() == State.class && f[i].get((Object)null) == s) {
               return f[i].getName();
            }
         }
      } catch (IllegalAccessException var3) {
         JDBCLogger.logStackTrace(var3);
      }

      return "unknown state " + s;
   }

   private static State lookupState(int code) {
      Field[] f = LifeCycle.class.getFields();

      try {
         for(int i = 0; i < f.length; ++i) {
            if ((f[i].getModifiers() & 25) == 25 && f[i].getType() == State.class) {
               State s = (State)f[i].get((Object)null);
               if (s.code == code) {
                  return s;
               }
            }
         }
      } catch (IllegalAccessException var4) {
         JDBCLogger.logStackTrace(var4);
      }

      throw new AssertionError(code);
   }

   static {
      NEXT[0] = DESIGNING;
      NEXT[1] = POPULATING;
      NEXT[2] = POPULATING;
      NEXT[3] = CONFIGURING_METADATA;
      NEXT[4] = MANIPULATING;
      NEXT[5] = INSERTING;
      NEXT[6] = MANIPULATING;
      NEXT[7] = UPDATING;
      NEXT[8] = MANIPULATING;
      NEXT[9] = DESIGNING;
      NEXT[10] = POPULATING;
   }

   static class State implements Serializable {
      private static final long serialVersionUID = 7867643086257295621L;
      private final int code;
      private final int validOps;
      private String name;

      State(int code, int[] validOpArray) {
         this.code = code;
         int ops = 0;

         for(int i = 0; i < validOpArray.length; ++i) {
            ops |= 1 << validOpArray[i];
         }

         this.validOps = ops;
      }

      State next(int op) {
         return LifeCycle.NEXT[op];
      }

      String getName() {
         if (this.name == null) {
            this.name = LifeCycle.getStateName(this);
         }

         return this.name;
      }

      State checkOp(int op) {
         if ((this.validOps & 1 << op) == 0) {
            throw new UnsupportedOperationException("Operation class " + LifeCycle.getOpName(op) + " not supported in state " + this.getName());
         } else {
            return this.next(op);
         }
      }

      Object readResolve() {
         return LifeCycle.lookupState(this.code);
      }
   }
}
