package org.glassfish.grizzly.http.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Session {
   private final ConcurrentMap attributes;
   private String id;
   private boolean isValid;
   private boolean isNew;
   private final long creationTime;
   private long sessionTimeout;
   private long timestamp;

   public Session() {
      this((String)null);
   }

   public Session(String id) {
      this.attributes = new ConcurrentHashMap();
      this.id = null;
      this.isValid = true;
      this.isNew = true;
      this.sessionTimeout = -1L;
      this.timestamp = -1L;
      this.id = id;
      this.creationTime = this.timestamp = System.currentTimeMillis();
   }

   public boolean isValid() {
      return this.isValid;
   }

   public void setValid(boolean isValid) {
      this.isValid = isValid;
      if (!isValid) {
         this.timestamp = -1L;
      }

   }

   public boolean isNew() {
      return this.isNew;
   }

   public String getIdInternal() {
      return this.id;
   }

   protected void setIdInternal(String id) {
      this.id = id;
   }

   public void setAttribute(String key, Object value) {
      this.attributes.put(key, value);
   }

   public Object getAttribute(String key) {
      return this.attributes.get(key);
   }

   public Object removeAttribute(String key) {
      return this.attributes.remove(key);
   }

   public ConcurrentMap attributes() {
      return this.attributes;
   }

   public long getCreationTime() {
      return this.creationTime;
   }

   public long getSessionTimeout() {
      return this.sessionTimeout;
   }

   public void setSessionTimeout(long sessionTimeout) {
      this.sessionTimeout = sessionTimeout;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
   }

   public long access() {
      long localTimeStamp = System.currentTimeMillis();
      this.timestamp = localTimeStamp;
      this.isNew = false;
      return localTimeStamp;
   }
}
