package com.sun.faces.config.initfacescontext;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class NoOpFlash extends Flash {
   public void doPostPhaseActions(FacesContext ctx) {
   }

   public void doPrePhaseActions(FacesContext ctx) {
   }

   public boolean isKeepMessages() {
      return false;
   }

   public boolean isRedirect() {
      return false;
   }

   public void keep(String key) {
   }

   public void putNow(String key, Object value) {
   }

   public void setKeepMessages(boolean newValue) {
   }

   public void setRedirect(boolean newValue) {
   }

   public void clear() {
   }

   public boolean containsKey(Object key) {
      return false;
   }

   public boolean containsValue(Object value) {
      return false;
   }

   public Set entrySet() {
      return Collections.emptySet();
   }

   public Object get(Object key) {
      return null;
   }

   public boolean isEmpty() {
      return true;
   }

   public Set keySet() {
      return Collections.emptySet();
   }

   public Object put(String key, Object value) {
      return null;
   }

   public void putAll(Map m) {
   }

   public Object remove(Object key) {
      return null;
   }

   public int size() {
      return 0;
   }

   public Collection values() {
      return Collections.emptyList();
   }
}
