package org.apache.oro.util;

public interface Cache {
   void addElement(Object var1, Object var2);

   Object getElement(Object var1);

   int size();

   int capacity();
}
