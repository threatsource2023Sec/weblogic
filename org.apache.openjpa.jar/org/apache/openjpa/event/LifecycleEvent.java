package org.apache.openjpa.event;

import java.util.EventObject;

public class LifecycleEvent extends EventObject {
   public static final int BEFORE_PERSIST = 0;
   public static final int AFTER_PERSIST = 1;
   public static final int AFTER_PERSIST_PERFORMED = 18;
   public static final int AFTER_LOAD = 2;
   public static final int BEFORE_STORE = 3;
   public static final int AFTER_STORE = 4;
   public static final int BEFORE_CLEAR = 5;
   public static final int AFTER_CLEAR = 6;
   public static final int BEFORE_DELETE = 7;
   public static final int AFTER_DELETE = 8;
   public static final int AFTER_DELETE_PERFORMED = 19;
   public static final int BEFORE_DIRTY = 9;
   public static final int AFTER_DIRTY = 10;
   public static final int BEFORE_DIRTY_FLUSHED = 11;
   public static final int AFTER_DIRTY_FLUSHED = 12;
   public static final int BEFORE_DETACH = 13;
   public static final int AFTER_DETACH = 14;
   public static final int BEFORE_ATTACH = 15;
   public static final int AFTER_ATTACH = 16;
   public static final int AFTER_REFRESH = 17;
   public static final int BEFORE_UPDATE = 20;
   public static final int AFTER_UPDATE_PERFORMED = 21;
   public static final int[] ALL_EVENTS = new int[]{0, 1, 18, 2, 3, 4, 5, 6, 7, 8, 19, 9, 10, 11, 12, 13, 14, 15, 16, 17, 20, 21};
   private final int _type;
   private final Object _related;

   public LifecycleEvent(Object pc, int type) {
      this(pc, (Object)null, type);
   }

   public LifecycleEvent(Object pc, Object related, int type) {
      super(pc);
      this._type = type;
      this._related = related;
   }

   public int getType() {
      return this._type;
   }

   public Object getRelated() {
      return this._related;
   }
}
