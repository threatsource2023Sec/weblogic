package org.glassfish.grizzly.utils;

public class Pair implements PoolableObject {
   private Object first;
   private Object second;

   public Pair() {
   }

   public Pair(Object first, Object second) {
      this.first = first;
      this.second = second;
   }

   public Object getFirst() {
      return this.first;
   }

   public void setFirst(Object first) {
      this.first = first;
   }

   public Object getSecond() {
      return this.second;
   }

   public void setSecond(Object second) {
      this.second = second;
   }

   public void prepare() {
   }

   public void release() {
      this.first = null;
      this.second = null;
   }

   public String toString() {
      return "Pair{key=" + this.first + " value=" + this.second + '}';
   }
}
