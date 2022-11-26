package com.bea.xbeanmarshal.runtime.internal.util;

import java.util.Iterator;
import java.util.Map;

public class ToStringWriter {
   StringBuffer sb = new StringBuffer();
   int level = 0;

   public void start(Object object) {
      this.sb.append("(");
      this.sb.append(this.shortName(object.getClass().getName()));
      this.sb.append("@");
      this.sb.append(System.identityHashCode(object));
   }

   private String shortName(String name) {
      int index = name.lastIndexOf(".");
      return index == -1 ? name : name.substring(index + 1, name.length());
   }

   public void end() {
      this.sb.append(")");
   }

   public void writeField(String name, int value) {
      this.writeField(name, "" + value);
   }

   public void writeField(String name, Object object) {
      this.sb.append(" <");
      this.sb.append(name);
      this.sb.append("=");
      if (object != null) {
         this.sb.append(object);
      } else {
         this.sb.append("null");
      }

      this.sb.append(">");
   }

   public String toString() {
      return this.sb.toString();
   }

   public void writeArray(String name, Iterator iterator) {
      this.sb.append(" <");
      this.sb.append(name);
      this.sb.append("[]{");

      while(iterator.hasNext()) {
         this.sb.append(iterator.next());
         if (iterator.hasNext()) {
            this.sb.append(",");
         }
      }

      this.sb.append("}");
      this.sb.append(">");
   }

   public void writeMap(String name, Map map) {
      this.sb.append(" <");
      this.sb.append(name);
      this.sb.append("{");
      Iterator it = map.entrySet().iterator();

      while(it.hasNext()) {
         Map.Entry entry = (Map.Entry)it.next();
         String key = entry.getKey() == null ? "" : entry.getKey().toString();
         this.writeField(key, entry.getValue());
      }

      this.sb.append("}");
   }
}
