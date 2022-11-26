package com.kenai.constantine;

import com.kenai.constantine.platform.Errno;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/** @deprecated */
@Deprecated
public class ConstantSet extends AbstractSet {
   private final ConcurrentMap nameToConstant = new ConcurrentHashMap();
   private final ConcurrentMap valueToConstant = new ConcurrentHashMap();
   private final jnr.constants.ConstantSet constants;
   private static final ConcurrentMap constantSets = new ConcurrentHashMap();
   private static final Object lock = new Object();

   public static ConstantSet getConstantSet(String name) {
      ConstantSet constants = (ConstantSet)constantSets.get(name);
      if (constants == null) {
         synchronized(lock) {
            if (!constantSets.containsKey(name)) {
               constants = new ConstantSet(jnr.constants.ConstantSet.getConstantSet(name));
               constantSets.put(name, constants);
            }
         }
      }

      return constants;
   }

   private ConstantSet(jnr.constants.ConstantSet constants) {
      this.constants = constants;
   }

   public Constant getConstant(String name) {
      Constant c = (Constant)this.nameToConstant.get(name);
      if (c == null) {
         synchronized(lock) {
            if (!this.nameToConstant.containsKey(name)) {
               jnr.constants.Constant jnrConstant = this.constants.getConstant(name);
               if (jnrConstant != null) {
                  this.nameToConstant.put(name, c = new ConstantImpl(jnrConstant));
                  this.valueToConstant.put(jnrConstant.intValue(), c);
               }
            }
         }
      }

      return (Constant)c;
   }

   public Constant getConstant(int value) {
      Constant c = (Constant)this.valueToConstant.get(value);
      return c != null ? c : this.getConstant(this.constants.getConstant((long)value).name());
   }

   public int getValue(String name) {
      return (int)this.constants.getValue(name);
   }

   public String getName(int value) {
      return this.constants.getName(value);
   }

   public long minValue() {
      return this.constants.minValue();
   }

   public long maxValue() {
      return this.constants.maxValue();
   }

   public Iterator iterator() {
      return new ConstantIterator();
   }

   public int size() {
      return this.constants.size();
   }

   public boolean contains(Object o) {
      return o != null && o.getClass().equals(ConstantImpl.class) && this.nameToConstant.values().contains(o);
   }

   public static void main(String[] args) {
      ConstantSet errnos = getConstantSet("Errno");
      Iterator var2 = errnos.iterator();

      while(var2.hasNext()) {
         Constant c = (Constant)var2.next();
         System.out.println(c.name() + "=" + c.value());
      }

      Errno errno = Errno.valueOf(22);
      System.out.println("errno for 22=" + errno);
      System.out.println("errno for 101=" + Errno.valueOf(101));
      System.out.println("errno for 22=" + Errno.valueOf(22));
      System.out.println("EINVAL.value() = " + Errno.EINVAL.value());
      System.out.println("E2BIG.value() = " + Errno.E2BIG.value());
   }

   private final class ConstantIterator implements Iterator {
      private final Iterator it;

      ConstantIterator() {
         this.it = ConstantSet.this.constants.iterator();
      }

      public boolean hasNext() {
         return this.it.hasNext();
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public Constant next() {
         return ConstantSet.this.getConstant(((jnr.constants.Constant)this.it.next()).name());
      }
   }

   private final class ConstantImpl implements Constant {
      private final jnr.constants.Constant constant;

      ConstantImpl(jnr.constants.Constant constant) {
         this.constant = constant;
      }

      public int value() {
         return this.constant.intValue();
      }

      public int intValue() {
         return this.constant.intValue();
      }

      public long longValue() {
         return this.constant.longValue();
      }

      public String name() {
         return this.constant.name();
      }

      public boolean defined() {
         return true;
      }

      public int hashCode() {
         return this.constant.hashCode();
      }

      public boolean equals(Object other) {
         return other instanceof ConstantImpl && ((ConstantImpl)other).constant.equals(this.constant);
      }

      public final String toString() {
         return this.constant.toString();
      }
   }
}
