package org.python.sizeof;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public final class RamUsageEstimator {
   public static final String JVM_INFO_STRING;
   public static final long ONE_KB = 1024L;
   public static final long ONE_MB = 1048576L;
   public static final long ONE_GB = 1073741824L;
   public static final int NUM_BYTES_BOOLEAN = 1;
   public static final int NUM_BYTES_BYTE = 1;
   public static final int NUM_BYTES_CHAR = 2;
   public static final int NUM_BYTES_SHORT = 2;
   public static final int NUM_BYTES_INT = 4;
   public static final int NUM_BYTES_FLOAT = 4;
   public static final int NUM_BYTES_LONG = 8;
   public static final int NUM_BYTES_DOUBLE = 8;
   public static final int NUM_BYTES_OBJECT_REF;
   public static final int NUM_BYTES_OBJECT_HEADER;
   public static final int NUM_BYTES_ARRAY_HEADER;
   public static final int NUM_BYTES_OBJECT_ALIGNMENT;
   private static final Map primitiveSizes = new IdentityHashMap();
   private static final Object theUnsafe;
   private static final Method objectFieldOffsetMethod;
   private static final EnumSet supportedFeatures;

   private RamUsageEstimator() {
   }

   public static boolean isSupportedJVM() {
      return supportedFeatures.size() == RamUsageEstimator.JvmFeature.values().length;
   }

   public static long alignObjectSize(long size) {
      size += (long)NUM_BYTES_OBJECT_ALIGNMENT - 1L;
      return size - size % (long)NUM_BYTES_OBJECT_ALIGNMENT;
   }

   public static long sizeOf(byte[] arr) {
      return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + (long)arr.length);
   }

   public static long sizeOf(boolean[] arr) {
      return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + (long)arr.length);
   }

   public static long sizeOf(char[] arr) {
      return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + 2L * (long)arr.length);
   }

   public static long sizeOf(short[] arr) {
      return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + 2L * (long)arr.length);
   }

   public static long sizeOf(int[] arr) {
      return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + 4L * (long)arr.length);
   }

   public static long sizeOf(float[] arr) {
      return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + 4L * (long)arr.length);
   }

   public static long sizeOf(long[] arr) {
      return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + 8L * (long)arr.length);
   }

   public static long sizeOf(double[] arr) {
      return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + 8L * (long)arr.length);
   }

   public static long sizeOf(Object obj) {
      ArrayList stack = new ArrayList();
      stack.add(obj);
      return measureSizeOf(stack);
   }

   public static long sizeOfAll(Object... objects) {
      return sizeOfAll((Iterable)Arrays.asList(objects));
   }

   public static long sizeOfAll(Iterable objects) {
      ArrayList stack;
      if (objects instanceof Collection) {
         stack = new ArrayList(((Collection)objects).size());
      } else {
         stack = new ArrayList();
      }

      Iterator i$ = objects.iterator();

      while(i$.hasNext()) {
         Object o = i$.next();
         stack.add(o);
      }

      return measureSizeOf(stack);
   }

   public static long shallowSizeOf(Object obj) {
      if (obj == null) {
         return 0L;
      } else {
         Class clz = obj.getClass();
         return clz.isArray() ? shallowSizeOfArray(obj) : shallowSizeOfInstance(clz);
      }
   }

   public static long shallowSizeOfAll(Object... objects) {
      return shallowSizeOfAll((Iterable)Arrays.asList(objects));
   }

   public static long shallowSizeOfAll(Iterable objects) {
      long sum = 0L;

      Object o;
      for(Iterator i$ = objects.iterator(); i$.hasNext(); sum += shallowSizeOf(o)) {
         o = i$.next();
      }

      return sum;
   }

   public static long shallowSizeOfInstance(Class clazz) {
      if (clazz.isArray()) {
         throw new IllegalArgumentException("This method does not work with array classes.");
      } else if (clazz.isPrimitive()) {
         return (long)(Integer)primitiveSizes.get(clazz);
      } else {
         long size;
         for(size = (long)NUM_BYTES_OBJECT_HEADER; clazz != null; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            Field[] arr$ = fields;
            int len$ = fields.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               Field f = arr$[i$];
               if (!Modifier.isStatic(f.getModifiers())) {
                  size = adjustForField(size, f);
               }
            }
         }

         return alignObjectSize(size);
      }
   }

   public static EnumSet getUnsupportedFeatures() {
      EnumSet unsupported = EnumSet.allOf(JvmFeature.class);
      unsupported.removeAll(supportedFeatures);
      return unsupported;
   }

   public static EnumSet getSupportedFeatures() {
      return EnumSet.copyOf(supportedFeatures);
   }

   private static long shallowSizeOfArray(Object array) {
      long size = (long)NUM_BYTES_ARRAY_HEADER;
      int len = Array.getLength(array);
      if (len > 0) {
         Class arrayElementClazz = array.getClass().getComponentType();
         if (arrayElementClazz.isPrimitive()) {
            size += (long)len * (long)(Integer)primitiveSizes.get(arrayElementClazz);
         } else {
            size += (long)NUM_BYTES_OBJECT_REF * (long)len;
         }
      }

      return alignObjectSize(size);
   }

   private static long measureSizeOf(ArrayList stack) {
      IdentityHashSet seen = new IdentityHashSet();
      IdentityHashMap classCache = new IdentityHashMap();
      long totalSize = 0L;

      while(true) {
         while(true) {
            Object ob;
            do {
               do {
                  if (stack.isEmpty()) {
                     seen.clear();
                     stack.clear();
                     classCache.clear();
                     return totalSize;
                  }

                  ob = stack.remove(stack.size() - 1);
               } while(ob == null);
            } while(seen.contains(ob));

            seen.add(ob);
            Class obClazz = ob.getClass();
            int len$;
            Object o;
            if (obClazz.isArray()) {
               long size = (long)NUM_BYTES_ARRAY_HEADER;
               len$ = Array.getLength(ob);
               if (len$ > 0) {
                  Class componentClazz = obClazz.getComponentType();
                  if (componentClazz.isPrimitive()) {
                     size += (long)len$ * (long)(Integer)primitiveSizes.get(componentClazz);
                  } else {
                     size += (long)NUM_BYTES_OBJECT_REF * (long)len$;
                     int i = len$;

                     while(true) {
                        --i;
                        if (i < 0) {
                           break;
                        }

                        o = Array.get(ob, i);
                        if (o != null && !seen.contains(o)) {
                           stack.add(o);
                        }
                     }
                  }
               }

               totalSize += alignObjectSize(size);
            } else {
               try {
                  ClassCache cachedInfo = (ClassCache)classCache.get(obClazz);
                  if (cachedInfo == null) {
                     classCache.put(obClazz, cachedInfo = createCacheEntry(obClazz));
                  }

                  Field[] arr$ = cachedInfo.referenceFields;
                  len$ = arr$.length;

                  for(int i$ = 0; i$ < len$; ++i$) {
                     Field f = arr$[i$];
                     o = f.get(ob);
                     if (o != null && !seen.contains(o)) {
                        stack.add(o);
                     }
                  }

                  totalSize += cachedInfo.alignedShallowInstanceSize;
               } catch (IllegalAccessException var15) {
                  throw new RuntimeException("Reflective field access failed?", var15);
               }
            }
         }
      }
   }

   private static ClassCache createCacheEntry(Class clazz) {
      long shallowInstanceSize = (long)NUM_BYTES_OBJECT_HEADER;
      ArrayList referenceFields = new ArrayList(32);

      for(Class c = clazz; c != null; c = c.getSuperclass()) {
         if (c != Class.class) {
            Field[] fields = c.getDeclaredFields();
            Field[] arr$ = fields;
            int len$ = fields.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               Field f = arr$[i$];
               if (!Modifier.isStatic(f.getModifiers())) {
                  shallowInstanceSize = adjustForField(shallowInstanceSize, f);
                  if (!f.getType().isPrimitive()) {
                     f.setAccessible(true);
                     referenceFields.add(f);
                  }
               }
            }
         }
      }

      ClassCache cachedInfo = new ClassCache(alignObjectSize(shallowInstanceSize), (Field[])referenceFields.toArray(new Field[referenceFields.size()]));
      return cachedInfo;
   }

   private static long adjustForField(long sizeSoFar, Field f) {
      Class type = f.getType();
      int fsize = type.isPrimitive() ? (Integer)primitiveSizes.get(type) : NUM_BYTES_OBJECT_REF;
      if (objectFieldOffsetMethod != null) {
         try {
            long offsetPlusSize = ((Number)objectFieldOffsetMethod.invoke(theUnsafe, f)).longValue() + (long)fsize;
            return Math.max(sizeSoFar, offsetPlusSize);
         } catch (IllegalAccessException var9) {
            throw new RuntimeException("Access problem with sun.misc.Unsafe", var9);
         } catch (InvocationTargetException var10) {
            Throwable cause = var10.getCause();
            if (cause instanceof RuntimeException) {
               throw (RuntimeException)cause;
            } else if (cause instanceof Error) {
               throw (Error)cause;
            } else {
               throw new RuntimeException("Call to Unsafe's objectFieldOffset() throwed checked Exception when accessing field " + f.getDeclaringClass().getName() + "#" + f.getName(), cause);
            }
         }
      } else {
         return sizeSoFar + (long)fsize;
      }
   }

   public static String humanReadableUnits(long bytes) {
      return humanReadableUnits(bytes, new DecimalFormat("0.#", DecimalFormatSymbols.getInstance(Locale.ENGLISH)));
   }

   public static String humanReadableUnits(long bytes, DecimalFormat df) {
      if (bytes / 1073741824L > 0L) {
         return df.format((double)((float)bytes / 1.07374182E9F)) + " GB";
      } else if (bytes / 1048576L > 0L) {
         return df.format((double)((float)bytes / 1048576.0F)) + " MB";
      } else {
         return bytes / 1024L > 0L ? df.format((double)((float)bytes / 1024.0F)) + " KB" : bytes + " bytes";
      }
   }

   public static String humanSizeOf(Object object) {
      return humanReadableUnits(sizeOf(object));
   }

   static {
      primitiveSizes.put(Boolean.TYPE, 1);
      primitiveSizes.put(Byte.TYPE, 1);
      primitiveSizes.put(Character.TYPE, 2);
      primitiveSizes.put(Short.TYPE, 2);
      primitiveSizes.put(Integer.TYPE, 4);
      primitiveSizes.put(Float.TYPE, 4);
      primitiveSizes.put(Double.TYPE, 8);
      primitiveSizes.put(Long.TYPE, 8);
      int referenceSize = Constants.JRE_IS_64BIT ? 8 : 4;
      int objectHeader = Constants.JRE_IS_64BIT ? true : true;
      int arrayHeader = Constants.JRE_IS_64BIT ? true : true;
      supportedFeatures = EnumSet.noneOf(JvmFeature.class);
      Class unsafeClass = null;
      Object tempTheUnsafe = null;

      try {
         unsafeClass = Class.forName("sun.misc.Unsafe");
         Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
         unsafeField.setAccessible(true);
         tempTheUnsafe = unsafeField.get((Object)null);
      } catch (Exception var18) {
      }

      theUnsafe = tempTheUnsafe;

      Method tempObjectFieldOffsetMethod;
      try {
         tempObjectFieldOffsetMethod = unsafeClass.getMethod("arrayIndexScale", Class.class);
         referenceSize = ((Number)tempObjectFieldOffsetMethod.invoke(theUnsafe, Object[].class)).intValue();
         supportedFeatures.add(RamUsageEstimator.JvmFeature.OBJECT_REFERENCE_SIZE);
      } catch (Exception var17) {
      }

      int objectHeader = Constants.JRE_IS_64BIT ? 8 + referenceSize : 8;
      int arrayHeader = Constants.JRE_IS_64BIT ? 8 + 2 * referenceSize : 12;
      tempObjectFieldOffsetMethod = null;

      Method arrayBaseOffsetM;
      try {
         arrayBaseOffsetM = unsafeClass.getMethod("objectFieldOffset", Field.class);
         Field dummy1Field = DummyTwoLongObject.class.getDeclaredField("dummy1");
         int ofs1 = ((Number)arrayBaseOffsetM.invoke(theUnsafe, dummy1Field)).intValue();
         Field dummy2Field = DummyTwoLongObject.class.getDeclaredField("dummy2");
         int ofs2 = ((Number)arrayBaseOffsetM.invoke(theUnsafe, dummy2Field)).intValue();
         if (Math.abs(ofs2 - ofs1) == 8) {
            Field baseField = DummyOneFieldObject.class.getDeclaredField("base");
            objectHeader = ((Number)arrayBaseOffsetM.invoke(theUnsafe, baseField)).intValue();
            supportedFeatures.add(RamUsageEstimator.JvmFeature.FIELD_OFFSETS);
            tempObjectFieldOffsetMethod = arrayBaseOffsetM;
         }
      } catch (Exception var16) {
      }

      objectFieldOffsetMethod = tempObjectFieldOffsetMethod;

      try {
         arrayBaseOffsetM = unsafeClass.getMethod("arrayBaseOffset", Class.class);
         arrayHeader = ((Number)arrayBaseOffsetM.invoke(theUnsafe, byte[].class)).intValue();
         supportedFeatures.add(RamUsageEstimator.JvmFeature.ARRAY_HEADER_SIZE);
      } catch (Exception var15) {
      }

      NUM_BYTES_OBJECT_REF = referenceSize;
      NUM_BYTES_OBJECT_HEADER = objectHeader;
      NUM_BYTES_ARRAY_HEADER = arrayHeader;
      int objectAlignment = 8;

      try {
         Class beanClazz = Class.forName("com.sun.management.HotSpotDiagnosticMXBean");

         Object hotSpotBean;
         try {
            hotSpotBean = ManagementFactory.class.getMethod("getPlatformMXBean", Class.class).invoke((Object)null, beanClazz);
         } catch (Exception var13) {
            try {
               Class sunMF = Class.forName("sun.management.ManagementFactory");
               hotSpotBean = sunMF.getMethod("getDiagnosticMXBean").invoke((Object)null);
            } catch (Exception var12) {
               hotSpotBean = ManagementFactory.newPlatformMXBeanProxy(ManagementFactory.getPlatformMBeanServer(), "com.sun.management:type=HotSpotDiagnostic", beanClazz);
            }
         }

         if (hotSpotBean != null) {
            Method getVMOptionMethod = beanClazz.getMethod("getVMOption", String.class);
            Object vmOption = getVMOptionMethod.invoke(hotSpotBean, "ObjectAlignmentInBytes");
            objectAlignment = Integer.parseInt(vmOption.getClass().getMethod("getValue").invoke(vmOption).toString());
            supportedFeatures.add(RamUsageEstimator.JvmFeature.OBJECT_ALIGNMENT);
         }
      } catch (Exception var14) {
      }

      NUM_BYTES_OBJECT_ALIGNMENT = objectAlignment;
      JVM_INFO_STRING = "[JVM: " + Constants.JVM_NAME + ", " + Constants.JVM_VERSION + ", " + Constants.JVM_VENDOR + ", " + Constants.JAVA_VENDOR + ", " + Constants.JAVA_VERSION + "]";
   }

   private static final class DummyTwoLongObject {
      public long dummy1;
      public long dummy2;
   }

   private static final class DummyOneFieldObject {
      public byte base;
   }

   private static final class ClassCache {
      public final long alignedShallowInstanceSize;
      public final Field[] referenceFields;

      public ClassCache(long alignedShallowInstanceSize, Field[] referenceFields) {
         this.alignedShallowInstanceSize = alignedShallowInstanceSize;
         this.referenceFields = referenceFields;
      }
   }

   public static enum JvmFeature {
      OBJECT_REFERENCE_SIZE("Object reference size estimated using array index scale."),
      ARRAY_HEADER_SIZE("Array header size estimated using array based offset."),
      FIELD_OFFSETS("Shallow instance size based on field offsets."),
      OBJECT_ALIGNMENT("Object alignment retrieved from HotSpotDiagnostic MX bean.");

      public final String description;

      private JvmFeature(String description) {
         this.description = description;
      }

      public String toString() {
         return super.name() + " (" + this.description + ")";
      }
   }
}
