package org.apache.openjpa.util;

import java.sql.Timestamp;
import java.util.Date;

public final class DateId extends OpenJPAId {
   private final Date key;

   public DateId(Class cls, String key) {
      this(cls, new Date(Long.parseLong(key)));
   }

   public DateId(Class cls, Date key) {
      super(cls);
      this.key = key == null ? new Date(0L) : key;
   }

   public DateId(Class cls, java.sql.Date key) {
      this(cls, (Date)key);
   }

   public DateId(Class cls, Timestamp key) {
      this(cls, (Date)key);
   }

   public DateId(Class cls, Date key, boolean subs) {
      super(cls, subs);
      this.key = key == null ? new Date(0L) : key;
   }

   public Date getId() {
      return this.key;
   }

   public Object getIdObject() {
      return this.getId();
   }

   public String toString() {
      return Long.toString(this.key.getTime());
   }

   protected int idHash() {
      return this.key.hashCode();
   }

   protected boolean idEquals(OpenJPAId o) {
      return this.key.equals(((DateId)o).key);
   }
}
