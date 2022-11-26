package net.shibboleth.utilities.java.support.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class LazyMap implements Map, Serializable {
   private static final long serialVersionUID = 121425595164176639L;
   private Map delegate = Collections.emptyMap();

   public void clear() {
      this.delegate = Collections.emptyMap();
   }

   public boolean containsKey(Object key) {
      return this.delegate.containsKey(key);
   }

   public boolean containsValue(Object value) {
      return this.delegate.containsValue(value);
   }

   public Set entrySet() {
      this.delegate = this.buildMap();
      return this.delegate.entrySet();
   }

   public Object get(Object key) {
      return this.delegate.get(key);
   }

   public boolean isEmpty() {
      return this.delegate.isEmpty();
   }

   public Set keySet() {
      this.delegate = this.buildMap();
      return this.delegate.keySet();
   }

   public Object put(Object key, Object value) {
      if (this.delegate.isEmpty()) {
         this.delegate = Collections.singletonMap(key, value);
         return null;
      } else {
         this.delegate = this.buildMap();
         return this.delegate.put(key, value);
      }
   }

   public void putAll(Map t) {
      this.delegate = this.buildMap();
      this.delegate.putAll(t);
   }

   public Object remove(Object key) {
      this.delegate = this.buildMap();
      return this.delegate.remove(key);
   }

   public int size() {
      return this.delegate.size();
   }

   public Collection values() {
      this.delegate = this.buildMap();
      return this.delegate.values();
   }

   protected Map buildMap() {
      return (Map)(this.delegate instanceof HashMap ? this.delegate : new HashMap(this.delegate));
   }

   public String toString() {
      return this.delegate.toString();
   }

   public int hashCode() {
      return this.delegate.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj != null && this.getClass() == obj.getClass() ? this.delegate.equals(((LazyMap)obj).delegate) : false;
      }
   }
}
