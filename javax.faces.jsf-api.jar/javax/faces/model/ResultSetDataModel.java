package javax.faces.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.faces.FacesException;

public class ResultSetDataModel extends DataModel {
   private int index;
   private ResultSetMetaData metadata;
   private ResultSet resultSet;
   private boolean updated;

   public ResultSetDataModel() {
      this((ResultSet)null);
   }

   public ResultSetDataModel(ResultSet resultSet) {
      this.index = -1;
      this.metadata = null;
      this.resultSet = null;
      this.updated = false;
      this.setWrappedData(resultSet);
   }

   public boolean isRowAvailable() {
      if (this.resultSet == null) {
         return false;
      } else if (this.index < 0) {
         return false;
      } else {
         try {
            return this.resultSet.absolute(this.index + 1);
         } catch (SQLException var2) {
            throw new FacesException(var2);
         }
      }
   }

   public int getRowCount() {
      return -1;
   }

   public Object getRowData() {
      if (this.resultSet == null) {
         return null;
      } else if (!this.isRowAvailable()) {
         throw new NoRowAvailableException();
      } else {
         try {
            this.getMetaData();
            return new ResultSetMap(String.CASE_INSENSITIVE_ORDER);
         } catch (SQLException var2) {
            throw new FacesException(var2);
         }
      }
   }

   public int getRowIndex() {
      return this.index;
   }

   public void setRowIndex(int rowIndex) {
      if (rowIndex < -1) {
         throw new IllegalArgumentException();
      } else {
         if (this.updated && this.resultSet != null) {
            try {
               if (!this.resultSet.rowDeleted()) {
                  this.resultSet.updateRow();
               }

               this.updated = false;
            } catch (SQLException var8) {
               throw new FacesException(var8);
            }
         }

         int old = this.index;
         this.index = rowIndex;
         if (this.resultSet != null) {
            DataModelListener[] listeners = this.getDataModelListeners();
            if (old != this.index && listeners != null) {
               Object rowData = null;
               if (this.isRowAvailable()) {
                  rowData = this.getRowData();
               }

               DataModelEvent event = new DataModelEvent(this, this.index, rowData);
               int n = listeners.length;

               for(int i = 0; i < n; ++i) {
                  if (null != listeners[i]) {
                     listeners[i].rowSelected(event);
                  }
               }
            }

         }
      }
   }

   public Object getWrappedData() {
      return this.resultSet;
   }

   public void setWrappedData(Object data) {
      if (data == null) {
         this.metadata = null;
         this.resultSet = null;
         this.setRowIndex(-1);
      } else {
         this.metadata = null;
         this.resultSet = (ResultSet)data;
         this.index = -1;
         this.setRowIndex(0);
      }

   }

   private ResultSetMetaData getMetaData() {
      if (this.metadata == null) {
         try {
            this.metadata = this.resultSet.getMetaData();
         } catch (SQLException var2) {
            throw new FacesException(var2);
         }
      }

      return this.metadata;
   }

   private void updated() {
      this.updated = true;
   }

   private static class ResultSetValuesIterator implements Iterator {
      private ResultSetMap map;
      private Iterator keys;

      public ResultSetValuesIterator(ResultSetMap map) {
         this.map = map;
         this.keys = map.keySet().iterator();
      }

      public boolean hasNext() {
         return this.keys.hasNext();
      }

      public Object next() {
         return this.map.get(this.keys.next());
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private static class ResultSetValues extends AbstractCollection {
      private ResultSetMap map;

      public ResultSetValues(ResultSetMap map) {
         this.map = map;
      }

      public boolean add(Object o) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      public boolean contains(Object value) {
         return this.map.containsValue(value);
      }

      public Iterator iterator() {
         return new ResultSetValuesIterator(this.map);
      }

      public boolean remove(Object o) {
         throw new UnsupportedOperationException();
      }

      public boolean removeAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public boolean retainAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public int size() {
         return this.map.size();
      }
   }

   private static class ResultSetKeysIterator implements Iterator {
      private Iterator keys = null;

      public ResultSetKeysIterator(ResultSetMap map) {
         this.keys = map.realKeys();
      }

      public boolean hasNext() {
         return this.keys.hasNext();
      }

      public String next() {
         return (String)this.keys.next();
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private static class ResultSetKeys extends AbstractSet {
      private ResultSetMap map;

      public ResultSetKeys(ResultSetMap map) {
         this.map = map;
      }

      public boolean add(String o) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      public boolean contains(Object o) {
         return this.map.containsKey(o);
      }

      public boolean isEmpty() {
         return this.map.isEmpty();
      }

      public Iterator iterator() {
         return new ResultSetKeysIterator(this.map);
      }

      public boolean remove(Object o) {
         throw new UnsupportedOperationException();
      }

      public boolean removeAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public boolean retainAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public int size() {
         return this.map.size();
      }
   }

   private static class ResultSetEntry implements Map.Entry {
      private ResultSetMap map;
      private String key;

      public ResultSetEntry(ResultSetMap map, String key) {
         this.map = map;
         this.key = key;
      }

      public boolean equals(Object o) {
         if (o == null) {
            return false;
         } else if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            if (this.key == null) {
               if (e.getKey() != null) {
                  return false;
               }
            } else if (!this.key.equals(e.getKey())) {
               return false;
            }

            Object v = this.map.get(this.key);
            if (v == null) {
               if (e.getValue() != null) {
                  return false;
               }
            } else if (!v.equals(e.getValue())) {
               return false;
            }

            return true;
         }
      }

      public String getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.map.get(this.key);
      }

      public int hashCode() {
         Object value = this.map.get(this.key);
         return (this.key == null ? 0 : this.key.hashCode()) ^ (value == null ? 0 : value.hashCode());
      }

      public Object setValue(Object value) {
         Object previous = this.map.get(this.key);
         this.map.put(this.key, value);
         return previous;
      }
   }

   private static class ResultSetEntriesIterator implements Iterator {
      private ResultSetMap map = null;
      private Iterator keys = null;

      public ResultSetEntriesIterator(ResultSetMap map) {
         this.map = map;
         this.keys = map.keySet().iterator();
      }

      public boolean hasNext() {
         return this.keys.hasNext();
      }

      public Map.Entry next() {
         String key = (String)this.keys.next();
         return new ResultSetEntry(this.map, key);
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private static class ResultSetEntries extends AbstractSet {
      private ResultSetMap map;

      public ResultSetEntries(ResultSetMap map) {
         this.map = map;
      }

      public boolean add(Map.Entry o) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      public boolean contains(Object o) {
         if (o == null) {
            throw new NullPointerException();
         } else if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            Object k = e.getKey();
            Object v = e.getValue();
            if (!this.map.containsKey(k)) {
               return false;
            } else if (v == null) {
               return this.map.get(k) == null;
            } else {
               return v.equals(this.map.get(k));
            }
         }
      }

      public boolean isEmpty() {
         return this.map.isEmpty();
      }

      public Iterator iterator() {
         return new ResultSetEntriesIterator(this.map);
      }

      public boolean remove(Object o) {
         throw new UnsupportedOperationException();
      }

      public boolean removeAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public boolean retainAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public int size() {
         return this.map.size();
      }
   }

   private class ResultSetMap extends TreeMap {
      private int index;

      public ResultSetMap(Comparator comparator) throws SQLException {
         super(comparator);
         this.index = ResultSetDataModel.this.index;
         ResultSetDataModel.this.resultSet.absolute(this.index + 1);
         int n = ResultSetDataModel.this.metadata.getColumnCount();

         for(int i = 1; i <= n; ++i) {
            super.put(ResultSetDataModel.this.metadata.getColumnName(i), ResultSetDataModel.this.metadata.getColumnName(i));
         }

      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      public boolean containsValue(Object value) {
         Iterator i = this.entrySet().iterator();

         while(i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            Object contained = entry.getValue();
            if (value == null) {
               if (contained == null) {
                  return true;
               }
            } else if (value.equals(contained)) {
               return true;
            }
         }

         return false;
      }

      public Set entrySet() {
         return new ResultSetEntries(this);
      }

      public Object get(Object key) {
         if (!this.containsKey(key)) {
            return null;
         } else {
            try {
               ResultSetDataModel.this.resultSet.absolute(this.index + 1);
               return ResultSetDataModel.this.resultSet.getObject((String)this.realKey(key));
            } catch (SQLException var3) {
               throw new FacesException(var3);
            }
         }
      }

      public Set keySet() {
         return new ResultSetKeys(this);
      }

      public Object put(String key, Object value) {
         if (!this.containsKey(key)) {
            throw new IllegalArgumentException();
         } else {
            try {
               ResultSetDataModel.this.resultSet.absolute(this.index + 1);
               Object previous = ResultSetDataModel.this.resultSet.getObject((String)this.realKey(key));
               if (previous == null && value == null) {
                  return previous;
               } else if (previous != null && value != null && previous.equals(value)) {
                  return previous;
               } else {
                  ResultSetDataModel.this.resultSet.updateObject((String)this.realKey(key), value);
                  ResultSetDataModel.this.updated();
                  return previous;
               }
            } catch (SQLException var4) {
               throw new FacesException(var4);
            }
         }
      }

      public void putAll(Map map) {
         Iterator var2 = map.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            this.put((String)entry.getKey(), entry.getValue());
         }

      }

      public Object remove(Object key) {
         throw new UnsupportedOperationException();
      }

      public Collection values() {
         return new ResultSetValues(this);
      }

      Object realKey(Object key) {
         return super.get(key);
      }

      Iterator realKeys() {
         return super.keySet().iterator();
      }
   }
}
