package antlr.collections;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public interface List {
   void add(Object var1);

   void append(Object var1);

   Object elementAt(int var1) throws NoSuchElementException;

   Enumeration elements();

   boolean includes(Object var1);

   int length();
}
