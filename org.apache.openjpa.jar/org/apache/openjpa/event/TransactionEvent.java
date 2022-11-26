package org.apache.openjpa.event;

import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import org.apache.openjpa.kernel.Broker;

public class TransactionEvent extends EventObject {
   public static final int AFTER_BEGIN = 0;
   public static final int BEFORE_FLUSH = 1;
   public static final int AFTER_FLUSH = 2;
   public static final int BEFORE_COMMIT = 3;
   public static final int AFTER_COMMIT = 4;
   public static final int AFTER_ROLLBACK = 5;
   public static final int AFTER_STATE_TRANSITIONS = 6;
   public static final int AFTER_COMMIT_COMPLETE = 7;
   public static final int AFTER_ROLLBACK_COMPLETE = 8;
   private final int _type;
   private final transient Collection _objs;
   private final transient Collection _addClss;
   private final transient Collection _updateClss;
   private final transient Collection _deleteClss;

   public TransactionEvent(Broker broker, int type, Collection objs, Collection addClss, Collection updateClss, Collection deleteClss) {
      super(broker);
      this._type = type;
      this._objs = (Collection)(objs == null ? Collections.EMPTY_LIST : objs);
      this._addClss = (Collection)(addClss == null ? Collections.EMPTY_SET : addClss);
      this._updateClss = (Collection)(updateClss == null ? Collections.EMPTY_SET : updateClss);
      this._deleteClss = (Collection)(deleteClss == null ? Collections.EMPTY_SET : deleteClss);
   }

   public int getType() {
      return this._type;
   }

   public Collection getTransactionalObjects() {
      return this._objs;
   }

   public Collection getPersistedTypes() {
      return this._addClss;
   }

   public Collection getUpdatedTypes() {
      return this._updateClss;
   }

   public Collection getDeletedTypes() {
      return this._deleteClss;
   }
}
