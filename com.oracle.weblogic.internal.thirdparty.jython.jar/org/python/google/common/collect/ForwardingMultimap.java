package org.python.google.common.collect;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public abstract class ForwardingMultimap extends ForwardingObject implements Multimap {
   protected ForwardingMultimap() {
   }

   protected abstract Multimap delegate();

   public Map asMap() {
      return this.delegate().asMap();
   }

   public void clear() {
      this.delegate().clear();
   }

   public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
      return this.delegate().containsEntry(key, value);
   }

   public boolean containsKey(@Nullable Object key) {
      return this.delegate().containsKey(key);
   }

   public boolean containsValue(@Nullable Object value) {
      return this.delegate().containsValue(value);
   }

   public Collection entries() {
      return this.delegate().entries();
   }

   public Collection get(@Nullable Object key) {
      return this.delegate().get(key);
   }

   public boolean isEmpty() {
      return this.delegate().isEmpty();
   }

   public Multiset keys() {
      return this.delegate().keys();
   }

   public Set keySet() {
      return this.delegate().keySet();
   }

   @CanIgnoreReturnValue
   public boolean put(Object key, Object value) {
      return this.delegate().put(key, value);
   }

   @CanIgnoreReturnValue
   public boolean putAll(Object key, Iterable values) {
      return this.delegate().putAll(key, values);
   }

   @CanIgnoreReturnValue
   public boolean putAll(Multimap multimap) {
      return this.delegate().putAll(multimap);
   }

   @CanIgnoreReturnValue
   public boolean remove(@Nullable Object key, @Nullable Object value) {
      return this.delegate().remove(key, value);
   }

   @CanIgnoreReturnValue
   public Collection removeAll(@Nullable Object key) {
      return this.delegate().removeAll(key);
   }

   @CanIgnoreReturnValue
   public Collection replaceValues(Object key, Iterable values) {
      return this.delegate().replaceValues(key, values);
   }

   public int size() {
      return this.delegate().size();
   }

   public Collection values() {
      return this.delegate().values();
   }

   public boolean equals(@Nullable Object object) {
      return object == this || this.delegate().equals(object);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }
}
