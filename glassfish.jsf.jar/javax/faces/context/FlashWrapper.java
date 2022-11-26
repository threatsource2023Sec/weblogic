package javax.faces.context;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.faces.FacesWrapper;

public abstract class FlashWrapper extends Flash implements FacesWrapper {
   private Flash wrapped;

   /** @deprecated */
   @Deprecated
   public FlashWrapper() {
   }

   public FlashWrapper(Flash wrapped) {
      this.wrapped = wrapped;
   }

   public Flash getWrapped() {
      return this.wrapped;
   }

   public void doPostPhaseActions(FacesContext ctx) {
      this.getWrapped().doPostPhaseActions(ctx);
   }

   public void doPrePhaseActions(FacesContext ctx) {
      this.getWrapped().doPrePhaseActions(ctx);
   }

   public boolean isKeepMessages() {
      return this.getWrapped().isKeepMessages();
   }

   public boolean isRedirect() {
      return this.getWrapped().isRedirect();
   }

   public void keep(String key) {
      this.getWrapped().keep(key);
   }

   public void putNow(String key, Object value) {
      this.getWrapped().putNow(key, value);
   }

   public void setKeepMessages(boolean newValue) {
      this.getWrapped().setKeepMessages(newValue);
   }

   public void setRedirect(boolean newValue) {
      this.getWrapped().setRedirect(newValue);
   }

   public void clear() {
      this.getWrapped().clear();
   }

   public boolean containsKey(Object key) {
      return this.getWrapped().containsKey(key);
   }

   public boolean containsValue(Object value) {
      return this.getWrapped().containsValue(value);
   }

   public Set entrySet() {
      return this.getWrapped().entrySet();
   }

   public Object get(Object key) {
      return this.getWrapped().get(key);
   }

   public boolean isEmpty() {
      return this.getWrapped().isEmpty();
   }

   public Set keySet() {
      return this.getWrapped().keySet();
   }

   public Object put(String key, Object value) {
      return this.getWrapped().put(key, value);
   }

   public void putAll(Map m) {
      this.getWrapped().putAll(m);
   }

   public Object remove(Object key) {
      return this.getWrapped().remove(key);
   }

   public int size() {
      return this.getWrapped().size();
   }

   public Collection values() {
      return this.getWrapped().values();
   }
}
