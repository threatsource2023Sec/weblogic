package com.bea.core.repackaged.aspectj.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PartialOrder {
   private static void addNewPartialComparable(List graph, PartialComparable o) {
      SortObject so = new SortObject(o);
      Iterator i = graph.iterator();

      while(i.hasNext()) {
         SortObject other = (SortObject)i.next();
         so.addDirectedLinks(other);
      }

      graph.add(so);
   }

   private static void removeFromGraph(List graph, SortObject o) {
      SortObject other;
      for(Iterator i = graph.iterator(); i.hasNext(); other.removeSmallerObject(o)) {
         other = (SortObject)i.next();
         if (o == other) {
            i.remove();
         }
      }

   }

   public static List sort(List objects) {
      if (objects.size() < 2) {
         return objects;
      } else {
         List sortList = new LinkedList();
         Iterator i = objects.iterator();

         while(i.hasNext()) {
            addNewPartialComparable(sortList, (PartialComparable)i.next());
         }

         int N = objects.size();

         label44:
         for(int index = 0; index < N; ++index) {
            SortObject leastWithNoSmallers = null;
            Iterator i = sortList.iterator();

            while(true) {
               SortObject so;
               do {
                  do {
                     if (!i.hasNext()) {
                        if (leastWithNoSmallers == null) {
                           return null;
                        }

                        removeFromGraph(sortList, leastWithNoSmallers);
                        objects.set(index, leastWithNoSmallers.object);
                        continue label44;
                     }

                     so = (SortObject)i.next();
                  } while(!so.hasNoSmallerObjects());
               } while(leastWithNoSmallers != null && so.object.fallbackCompareTo(leastWithNoSmallers.object) >= 0);

               leastWithNoSmallers = so;
            }
         }

         return objects;
      }
   }

   public static void main(String[] args) {
      List l = new ArrayList();
      l.add(new Token("a1"));
      l.add(new Token("c2"));
      l.add(new Token("b3"));
      l.add(new Token("f4"));
      l.add(new Token("e5"));
      l.add(new Token("d6"));
      l.add(new Token("c7"));
      l.add(new Token("b8"));
      l.add(new Token("z"));
      l.add(new Token("x"));
      l.add(new Token("f9"));
      l.add(new Token("e10"));
      l.add(new Token("a11"));
      l.add(new Token("d12"));
      l.add(new Token("b13"));
      l.add(new Token("c14"));
      System.out.println(l);
      sort(l);
      System.out.println(l);
   }

   static class Token implements PartialComparable {
      private String s;

      Token(String s) {
         this.s = s;
      }

      public int compareTo(Object other) {
         Token t = (Token)other;
         int cmp = this.s.charAt(0) - t.s.charAt(0);
         if (cmp == 1) {
            return 1;
         } else {
            return cmp == -1 ? -1 : 0;
         }
      }

      public int fallbackCompareTo(Object other) {
         return -this.s.compareTo(((Token)other).s);
      }

      public String toString() {
         return this.s;
      }
   }

   private static class SortObject {
      PartialComparable object;
      List smallerObjects = new LinkedList();
      List biggerObjects = new LinkedList();

      public SortObject(PartialComparable o) {
         this.object = o;
      }

      boolean hasNoSmallerObjects() {
         return this.smallerObjects.size() == 0;
      }

      boolean removeSmallerObject(SortObject o) {
         this.smallerObjects.remove(o);
         return this.hasNoSmallerObjects();
      }

      void addDirectedLinks(SortObject other) {
         int cmp = this.object.compareTo(other.object);
         if (cmp != 0) {
            if (cmp > 0) {
               this.smallerObjects.add(other);
               other.biggerObjects.add(this);
            } else {
               this.biggerObjects.add(other);
               other.smallerObjects.add(this);
            }

         }
      }

      public String toString() {
         return this.object.toString();
      }
   }

   public interface PartialComparable {
      int compareTo(Object var1);

      int fallbackCompareTo(Object var1);
   }
}
