package com.googlecode.cqengine.query.option;

import java.util.HashMap;
import java.util.Map;

public class QueryOptions {
   final Map options;

   public QueryOptions(Map options) {
      this.options = options;
   }

   public QueryOptions() {
      this.options = new HashMap();
   }

   public Map getOptions() {
      return this.options;
   }

   public Object get(Class optionType) {
      Object optionValue = this.get((Object)optionType);
      return optionValue;
   }

   public Object get(Object key) {
      return this.options.get(key);
   }

   public void put(Object key, Object value) {
      this.options.put(key, value);
   }

   public void remove(Object key) {
      this.options.remove(key);
   }

   public String toString() {
      return "queryOptions(" + this.options + ')';
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof QueryOptions)) {
         return false;
      } else {
         QueryOptions that = (QueryOptions)o;
         return this.options.equals(that.options);
      }
   }

   public int hashCode() {
      return this.options.hashCode();
   }
}
