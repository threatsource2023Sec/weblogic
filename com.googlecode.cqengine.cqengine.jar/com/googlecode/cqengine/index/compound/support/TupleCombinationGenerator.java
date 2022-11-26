package com.googlecode.cqengine.index.compound.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TupleCombinationGenerator {
   public static List generateCombinations(List inputLists) {
      if (inputLists.isEmpty()) {
         return Collections.emptyList();
      } else {
         List results = new ArrayList();
         Iterable currentList = (Iterable)inputLists.get(0);
         Iterator var3;
         Object object;
         if (inputLists.size() == 1) {
            var3 = currentList.iterator();

            while(var3.hasNext()) {
               object = var3.next();
               results.add(new LinkedList(Collections.singleton(object)));
            }
         } else {
            var3 = currentList.iterator();

            while(var3.hasNext()) {
               object = var3.next();
               List tail = inputLists.subList(1, inputLists.size());
               Iterator var6 = generateCombinations(tail).iterator();

               while(var6.hasNext()) {
                  List permutations = (List)var6.next();
                  permutations.add(0, object);
                  results.add(permutations);
               }
            }
         }

         return results;
      }
   }

   TupleCombinationGenerator() {
   }
}
