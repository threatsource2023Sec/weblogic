package org.apache.openjpa.event;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.Collections;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

public class RemoteCommitEvent implements Externalizable {
   public static final int PAYLOAD_OIDS = 0;
   public static final int PAYLOAD_OIDS_WITH_ADDS = 1;
   public static final int PAYLOAD_EXTENTS = 2;
   public static final int PAYLOAD_LOCAL_STALE_DETECTION = 3;
   private static final Localizer s_loc = Localizer.forPackage(RemoteCommitEvent.class);
   private int _payload = 0;
   private Collection _addIds = null;
   private Collection _addClasses = null;
   private Collection _updates = null;
   private Collection _deletes = null;

   public RemoteCommitEvent() {
   }

   public RemoteCommitEvent(int payloadType, Collection addIds, Collection addClasses, Collection updates, Collection deletes) {
      this._payload = payloadType;
      if (addIds != null) {
         this._addIds = Collections.unmodifiableCollection(addIds);
      }

      if (addClasses != null) {
         this._addClasses = Collections.unmodifiableCollection(addClasses);
      }

      if (updates != null) {
         this._updates = Collections.unmodifiableCollection(updates);
      }

      if (deletes != null) {
         this._deletes = Collections.unmodifiableCollection(deletes);
      }

   }

   public int getPayloadType() {
      return this._payload;
   }

   public Collection getPersistedObjectIds() {
      if (this._payload != 1) {
         if (this._payload == 0) {
            throw new UserException(s_loc.get("no-added-oids"));
         } else {
            throw new UserException(s_loc.get("extent-only-event"));
         }
      } else {
         return (Collection)(this._addIds == null ? Collections.EMPTY_LIST : this._addIds);
      }
   }

   public Collection getUpdatedObjectIds() {
      if (this._payload == 2) {
         throw new UserException(s_loc.get("extent-only-event"));
      } else {
         return (Collection)(this._updates == null ? Collections.EMPTY_LIST : this._updates);
      }
   }

   public Collection getDeletedObjectIds() {
      if (this._payload == 2) {
         throw new UserException(s_loc.get("extent-only-event"));
      } else {
         return (Collection)(this._deletes == null ? Collections.EMPTY_LIST : this._deletes);
      }
   }

   public Collection getPersistedTypeNames() {
      return (Collection)(this._addClasses == null ? Collections.EMPTY_LIST : this._addClasses);
   }

   public Collection getUpdatedTypeNames() {
      if (this._payload != 2) {
         throw new UserException(s_loc.get("nonextent-event"));
      } else {
         return (Collection)(this._updates == null ? Collections.EMPTY_LIST : this._updates);
      }
   }

   public Collection getDeletedTypeNames() {
      if (this._payload != 2) {
         throw new UserException(s_loc.get("nonextent-event"));
      } else {
         return (Collection)(this._deletes == null ? Collections.EMPTY_LIST : this._deletes);
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeInt(this._payload);
      out.writeObject(this._addClasses);
      if (this._payload == 1) {
         out.writeObject(this._addIds);
      }

      out.writeObject(this._updates);
      out.writeObject(this._deletes);
   }

   public void readExternal(ObjectInput in) throws IOException {
      try {
         this._payload = in.readInt();
         this._addClasses = (Collection)in.readObject();
         if (this._payload == 1) {
            this._addIds = (Collection)in.readObject();
         }

         this._updates = (Collection)in.readObject();
         this._deletes = (Collection)in.readObject();
      } catch (ClassNotFoundException var3) {
      }

   }
}
