package org.glassfish.grizzly.http.util;

import java.io.Serializable;

public final class TimeStamp implements Serializable {
   private long creationTime = 0L;
   private long lastAccessedTime;
   private long thisAccessedTime;
   private boolean isNew;
   private long maxInactiveInterval;
   private boolean isValid;
   MessageBytes name;
   int id;
   Object parent;

   public TimeStamp() {
      this.lastAccessedTime = this.creationTime;
      this.thisAccessedTime = this.creationTime;
      this.isNew = true;
      this.maxInactiveInterval = -1L;
      this.isValid = false;
      this.id = -1;
   }

   public void touch(long time) {
      this.lastAccessedTime = this.thisAccessedTime;
      this.thisAccessedTime = time;
      this.isNew = false;
   }

   public MessageBytes getName() {
      if (this.name == null) {
         this.name = MessageBytes.newInstance();
      }

      return this.name;
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public void setParent(Object o) {
      this.parent = o;
   }

   public Object getParent() {
      return this.parent;
   }

   public void setCreationTime(long time) {
      this.creationTime = time;
      this.lastAccessedTime = time;
      this.thisAccessedTime = time;
   }

   public long getLastAccessedTime() {
      return this.lastAccessedTime;
   }

   public long getMaxInactiveInterval() {
      return this.maxInactiveInterval;
   }

   public void setMaxInactiveInterval(long interval) {
      this.maxInactiveInterval = interval;
   }

   public boolean isValid() {
      return this.isValid;
   }

   public void setValid(boolean isValid) {
      this.isValid = isValid;
   }

   public boolean isNew() {
      return this.isNew;
   }

   public void setNew(boolean isNew) {
      this.isNew = isNew;
   }

   public long getCreationTime() {
      return this.creationTime;
   }

   public void recycle() {
      this.creationTime = 0L;
      this.lastAccessedTime = 0L;
      this.maxInactiveInterval = -1L;
      this.isNew = true;
      this.isValid = false;
      this.id = -1;
      if (this.name != null) {
         this.name.recycle();
      }

   }
}
