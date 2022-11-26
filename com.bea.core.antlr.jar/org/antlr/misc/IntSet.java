package org.antlr.misc;

import java.util.List;
import org.antlr.tool.Grammar;

public interface IntSet {
   void add(int var1);

   void addAll(IntSet var1);

   IntSet and(IntSet var1);

   IntSet complement(IntSet var1);

   IntSet or(IntSet var1);

   IntSet subtract(IntSet var1);

   int size();

   boolean isNil();

   boolean equals(Object var1);

   int getSingleElement();

   boolean member(int var1);

   void remove(int var1);

   List toList();

   String toString();

   String toString(Grammar var1);
}
