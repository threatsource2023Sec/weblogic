package org.apache.xml.security.stax.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventListener;

public class AbstractSecurityContextImpl {
   private final Map content = Collections.synchronizedMap(new HashMap());
   private final List securityEventListeners = new ArrayList(2);

   public void addSecurityEventListener(SecurityEventListener securityEventListener) {
      if (securityEventListener != null) {
         this.securityEventListeners.add(securityEventListener);
      }

   }

   public synchronized void registerSecurityEvent(SecurityEvent securityEvent) throws XMLSecurityException {
      this.forwardSecurityEvent(securityEvent);
   }

   protected void forwardSecurityEvent(SecurityEvent securityEvent) throws XMLSecurityException {
      for(int i = 0; i < this.securityEventListeners.size(); ++i) {
         SecurityEventListener securityEventListener = (SecurityEventListener)this.securityEventListeners.get(i);
         securityEventListener.registerSecurityEvent(securityEvent);
      }

   }

   public void put(String key, Object value) {
      this.content.put(key, value);
   }

   public Object get(String key) {
      return this.content.get(key);
   }

   public Object remove(String key) {
      return this.content.remove(key);
   }

   public void putList(Object key, List value) {
      if (value != null) {
         List entry = (List)this.content.get(key);
         if (entry == null) {
            entry = new ArrayList();
            this.content.put(key, entry);
         }

         ((List)entry).addAll(value);
      }
   }

   public void putAsList(Object key, Object value) {
      List entry = (List)this.content.get(key);
      if (entry == null) {
         entry = new ArrayList();
         this.content.put(key, entry);
      }

      ((List)entry).add(value);
   }

   public List getAsList(Object key) {
      return (List)this.content.get(key);
   }

   public void putAsMap(Object key, Object mapKey, Object mapValue) {
      Map entry = (Map)this.content.get(key);
      if (entry == null) {
         entry = new HashMap();
         this.content.put(key, entry);
      }

      ((Map)entry).put(mapKey, mapValue);
   }

   public Map getAsMap(Object key) {
      return (Map)this.content.get(key);
   }
}
