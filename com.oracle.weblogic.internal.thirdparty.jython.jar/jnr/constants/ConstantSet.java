package jnr.constants;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import jnr.constants.platform.Errno;

public class ConstantSet extends AbstractSet {
   private final Map nameToConstant;
   private final Map valueToConstant;
   private final Set constants;
   private final Class enumClass;
   private volatile Long minValue;
   private volatile Long maxValue;
   private static final ConcurrentMap constantSets = new ConcurrentHashMap();
   private static final Object lock = new Object();
   private static final ClassLoader LOADER;
   private static final boolean CAN_LOAD_RESOURCES;
   private static volatile Throwable RESOURCE_READ_ERROR;

   public static ConstantSet getConstantSet(String name) {
      ConstantSet constants = (ConstantSet)constantSets.get(name);
      return constants != null ? constants : loadConstantSet(name);
   }

   private static ConstantSet loadConstantSet(String name) {
      synchronized(lock) {
         ConstantSet constants = (ConstantSet)constantSets.get(name);
         if (constants == null) {
            Class enumClass = getEnumClass(name);
            if (enumClass == null) {
               return null;
            }

            if (!Constant.class.isAssignableFrom(enumClass)) {
               throw new ClassCastException("class for " + name + " does not implement Constant interface");
            }

            constantSets.put(name, constants = new ConstantSet(enumClass));
         }

         return constants;
      }
   }

   private static final Class getEnumClass(String name) {
      String[] prefixes = Platform.getPlatform().getPackagePrefixes();
      String[] var2 = prefixes;
      int var3 = prefixes.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String prefix = var2[var4];
         String fullName = prefix + "." + name;
         boolean doClass = true;
         if (CAN_LOAD_RESOURCES) {
            String path = fullName.replace('.', '/') + ".class";
            URL resource = LOADER.getResource(path);
            if (resource == null) {
               doClass = false;
            }
         }

         if (doClass) {
            try {
               return Class.forName(fullName, true, LOADER).asSubclass(Enum.class);
            } catch (ClassNotFoundException var10) {
            }
         }
      }

      return null;
   }

   private ConstantSet(Class enumClass) {
      this.enumClass = enumClass;
      this.constants = EnumSet.allOf(enumClass);
      Map names = new HashMap();
      Map values = new HashMap();
      Iterator var4 = this.constants.iterator();

      while(var4.hasNext()) {
         Enum e = (Enum)var4.next();
         if (e instanceof Constant) {
            Constant c = (Constant)e;
            names.put(e.name(), c);
            values.put(c.longValue(), c);
         }
      }

      this.nameToConstant = Collections.unmodifiableMap(names);
      this.valueToConstant = Collections.unmodifiableMap(values);
   }

   public final Constant getConstant(String name) {
      return (Constant)this.nameToConstant.get(name);
   }

   public Constant getConstant(long value) {
      return (Constant)this.valueToConstant.get(value);
   }

   public long getValue(String name) {
      Constant c = this.getConstant(name);
      return c != null ? c.longValue() : 0L;
   }

   public String getName(int value) {
      Constant c = this.getConstant((long)value);
      return c != null ? c.name() : "unknown";
   }

   private Long getLongField(String name, long defaultValue) {
      try {
         Field f = this.enumClass.getField(name);
         return (Long)f.get(this.enumClass);
      } catch (NoSuchFieldException var5) {
         return defaultValue;
      } catch (RuntimeException var6) {
         throw var6;
      } catch (Exception var7) {
         throw new RuntimeException(var7);
      }
   }

   public long minValue() {
      if (this.minValue == null) {
         this.minValue = this.getLongField("MIN_VALUE", -2147483648L);
      }

      return (long)this.minValue.intValue();
   }

   public long maxValue() {
      if (this.maxValue == null) {
         this.maxValue = this.getLongField("MAX_VALUE", 2147483647L);
      }

      return (long)this.maxValue.intValue();
   }

   public Iterator iterator() {
      return new ConstantIterator(this.constants);
   }

   public int size() {
      return this.constants.size();
   }

   public boolean contains(Object o) {
      return o != null && o.getClass().equals(this.enumClass);
   }

   public static void main(String[] args) {
      System.out.println(Errno.values().length);
   }

   static {
      ClassLoader _loader = ConstantSet.class.getClassLoader();
      if (_loader != null) {
         LOADER = _loader;
      } else {
         LOADER = ClassLoader.getSystemClassLoader();
      }

      boolean canLoadResources = false;

      try {
         URL thisClass = (URL)AccessController.doPrivileged(new PrivilegedAction() {
            public URL run() {
               return ConstantSet.LOADER.getResource("jnr/constants/ConstantSet.class");
            }
         });
         InputStream stream = thisClass.openStream();

         try {
            stream.read();
         } catch (Throwable var14) {
            RESOURCE_READ_ERROR = var14;
         } finally {
            try {
               stream.close();
            } catch (Exception var13) {
            }

         }

         canLoadResources = true;
      } catch (Throwable var16) {
         if (RESOURCE_READ_ERROR == null) {
            RESOURCE_READ_ERROR = var16;
         }
      }

      CAN_LOAD_RESOURCES = canLoadResources;
   }

   private final class ConstantIterator implements Iterator {
      private final Iterator it;
      private Constant next = null;

      ConstantIterator(Collection constants) {
         this.it = constants.iterator();
         this.next = this.it.hasNext() ? (Constant)this.it.next() : null;
      }

      public boolean hasNext() {
         return this.next != null && !this.next.name().equals("__UNKNOWN_CONSTANT__");
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public Constant next() {
         Constant prev = this.next;
         this.next = this.it.hasNext() ? (Constant)this.it.next() : null;
         return prev;
      }
   }
}
