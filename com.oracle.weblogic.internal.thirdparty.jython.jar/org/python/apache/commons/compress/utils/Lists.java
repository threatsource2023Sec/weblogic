package org.python.apache.commons.compress.utils;

import java.util.ArrayList;
import java.util.Iterator;

public class Lists {
   public static ArrayList newArrayList() {
      return new ArrayList();
   }

   public static ArrayList newArrayList(Iterator iterator) {
      ArrayList list = newArrayList();
      Iterators.addAll(list, iterator);
      return list;
   }

   private Lists() {
   }
}
