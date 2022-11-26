package org.python.sizeof;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ObjectTree {
   public static void dump(PrintWriter pw, Object root) {
      Node nodeTree = ObjectTree.Node.create(root);
      printTree(new StringBuilder(), new StringBuilder(), pw, nodeTree);
   }

   public static String dump(Object root) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      dump(pw, root);
      pw.flush();
      return sw.toString();
   }

   private static void printTree(StringBuilder prefix, StringBuilder line, PrintWriter pw, Node node) {
      line.append(node.getName());
      pw.println(String.format(Locale.ENGLISH, "%,8d %,8d  %s", node.deepSize, node.shallowSize, line.toString()));
      line.setLength(0);
      if (node.hasChildren()) {
         int pLen = prefix.length();
         Iterator i = node.getChildren().iterator();

         while(i.hasNext()) {
            Node next = (Node)i.next();
            line.append(prefix.toString());
            line.append("+- ");
            prefix.append(i.hasNext() ? "|  " : "   ");
            printTree(prefix, line, pw, next);
            prefix.setLength(pLen);
         }
      }

   }

   private static class Node {
      private String name;
      private List children;
      private long shallowSize;
      private long deepSize;

      public Node(String name, Object delegate) {
         this.name = name;
         if (delegate != null) {
            this.shallowSize = RamUsageEstimator.shallowSizeOf(delegate);
            this.deepSize = this.shallowSize;
         }

      }

      private void addChild(Node node) {
         if (this.children == null) {
            this.children = new ArrayList();
         }

         this.children.add(node);
         this.deepSize += node.deepSize;
      }

      public static Node create(Object delegate) {
         return create("root", delegate, new IdentityHashMap());
      }

      public static Node create(String prefix, Object delegate, IdentityHashMap seen) {
         if (delegate == null) {
            throw new IllegalArgumentException();
         } else if (seen.containsKey(delegate)) {
            return new Node("[seen " + uniqueName(delegate, seen) + "]", (Object)null);
         } else {
            seen.put(delegate, seen.size());
            Class clazz = delegate.getClass();
            if (clazz.isArray()) {
               Node parent = new Node(prefix + " => " + clazz.getSimpleName(), delegate);
               if (clazz.getComponentType().isPrimitive()) {
                  return parent;
               } else {
                  int length = Array.getLength(delegate);

                  for(int i = 0; i < length; ++i) {
                     Object value = Array.get(delegate, i);
                     if (value != null) {
                        parent.addChild(create("[" + i + "]", value, seen));
                     }
                  }

                  return parent;
               }
            } else {
               List declaredFields = new ArrayList();

               for(Class c = clazz; c != null; c = c.getSuperclass()) {
                  Field[] fields = c.getDeclaredFields();
                  AccessibleObject.setAccessible(fields, true);
                  declaredFields.addAll(Arrays.asList(fields));
               }

               Collections.sort(declaredFields, new Comparator() {
                  public int compare(Field o1, Field o2) {
                     return o1.getName().compareTo(o2.getName());
                  }
               });
               Node parent = new Node(prefix + " => " + uniqueName(delegate, seen), delegate);
               Iterator i$ = declaredFields.iterator();

               while(i$.hasNext()) {
                  Field f = (Field)i$.next();

                  try {
                     if (!Modifier.isStatic(f.getModifiers()) && !f.getType().isPrimitive()) {
                        Object fValue = f.get(delegate);
                        if (fValue != null) {
                           parent.addChild(create(f.getType().getSimpleName() + " " + f.getName(), fValue, seen));
                        } else {
                           parent.addChild(new Node(f.getType().getSimpleName() + " " + f.getName() + " => null", (Object)null));
                        }
                     }
                  } catch (Exception var9) {
                     throw new RuntimeException(var9);
                  }
               }

               return parent;
            }
         }
      }

      private static String uniqueName(Object t, IdentityHashMap seen) {
         return "<" + t.getClass().getSimpleName() + "#" + seen.get(t) + ">";
      }

      public String getName() {
         return this.name;
      }

      public boolean hasChildren() {
         return this.children != null && !this.children.isEmpty();
      }

      public List getChildren() {
         return this.children;
      }
   }
}
