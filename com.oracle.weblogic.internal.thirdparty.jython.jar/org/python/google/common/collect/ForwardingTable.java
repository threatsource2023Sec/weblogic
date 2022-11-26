package org.python.google.common.collect;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public abstract class ForwardingTable extends ForwardingObject implements Table {
   protected ForwardingTable() {
   }

   protected abstract Table delegate();

   public Set cellSet() {
      return this.delegate().cellSet();
   }

   public void clear() {
      this.delegate().clear();
   }

   public Map column(Object columnKey) {
      return this.delegate().column(columnKey);
   }

   public Set columnKeySet() {
      return this.delegate().columnKeySet();
   }

   public Map columnMap() {
      return this.delegate().columnMap();
   }

   public boolean contains(Object rowKey, Object columnKey) {
      return this.delegate().contains(rowKey, columnKey);
   }

   public boolean containsColumn(Object columnKey) {
      return this.delegate().containsColumn(columnKey);
   }

   public boolean containsRow(Object rowKey) {
      return this.delegate().containsRow(rowKey);
   }

   public boolean containsValue(Object value) {
      return this.delegate().containsValue(value);
   }

   public Object get(Object rowKey, Object columnKey) {
      return this.delegate().get(rowKey, columnKey);
   }

   public boolean isEmpty() {
      return this.delegate().isEmpty();
   }

   @CanIgnoreReturnValue
   public Object put(Object rowKey, Object columnKey, Object value) {
      return this.delegate().put(rowKey, columnKey, value);
   }

   public void putAll(Table table) {
      this.delegate().putAll(table);
   }

   @CanIgnoreReturnValue
   public Object remove(Object rowKey, Object columnKey) {
      return this.delegate().remove(rowKey, columnKey);
   }

   public Map row(Object rowKey) {
      return this.delegate().row(rowKey);
   }

   public Set rowKeySet() {
      return this.delegate().rowKeySet();
   }

   public Map rowMap() {
      return this.delegate().rowMap();
   }

   public int size() {
      return this.delegate().size();
   }

   public Collection values() {
      return this.delegate().values();
   }

   public boolean equals(Object obj) {
      return obj == this || this.delegate().equals(obj);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }
}
