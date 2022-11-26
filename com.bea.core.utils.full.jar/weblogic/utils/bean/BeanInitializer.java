package weblogic.utils.bean;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.utils.collections.AggregateKey;
import weblogic.utils.enumerations.ArrayEnumerator;
import weblogic.utils.reflect.ReflectUtils;

public class BeanInitializer {
   private static final boolean debug = true;
   private static final boolean verbose = false;
   private static final Converter DICT_2_DATA = new Dictionary2DataRetriever();
   private Hashtable converters = new Hashtable();
   private Vector initializationFailures = new Vector();
   private boolean conversionExceptionsFatal;

   public InitializationFailure[] getInitializationFailures() {
      int size = this.initializationFailures.size();
      InitializationFailure[] failures = new InitializationFailure[size];
      this.initializationFailures.copyInto(failures);
      return failures;
   }

   public void setInitializationFailures(InitializationFailure[] failures) {
      this.initializationFailures = new Vector();

      for(int i = 0; i < failures.length; ++i) {
         this.initializationFailures.addElement(failures[i]);
      }

   }

   public void resetInitializationFailures() {
      this.initializationFailures = new Vector();
   }

   public boolean isConversionExceptionsFatal() {
      return this.conversionExceptionsFatal;
   }

   public void setConversionExceptionsFatal(boolean b) {
      this.conversionExceptionsFatal = b;
   }

   public BeanInitializer() {
      this.addConverter(DICT_2_DATA);
   }

   public final synchronized void initializeBean(Object bean, DataRetriever data) throws ConversionException {
      try {
         PropertyDescriptor[] descs = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();

         for(int i = 0; i < descs.length; ++i) {
            PropertyDescriptor prop = descs[i];
            Object o = data.get(prop.getName());

            try {
               if (o != null) {
                  this.setValue(bean, prop, o);
               }
            } catch (ConversionException var9) {
               if (this.conversionExceptionsFatal) {
                  throw var9;
               }

               InitializationFailure fail = new InitializationFailure(prop, var9);
               this.initializationFailures.addElement(fail);
            }
         }

      } catch (IntrospectionException var10) {
         throw new ConversionException("Caught IntrospectionException" + var10);
      }
   }

   public final void initializeBean(Object bean, Object data) throws ConversionException {
      DataRetriever dataR = (DataRetriever)this.convert(data, DataRetriever.class);
      this.initializeBean(bean, dataR);
   }

   public final void addConverter(Converter converter) {
      Object key = new ConverterKey(converter);
      this.converters.put(key, converter);
   }

   private final void setValue(Object instance, PropertyDescriptor prop, Object o) throws ConversionException {
      Method m = prop.getWriteMethod();
      Object arg = this.convert(o, prop);

      try {
         m.invoke(instance, arg);
      } catch (InvocationTargetException var7) {
         throw new AssertionError("Couldn't invoke method, " + m + " returned by PropertyDescriptor.");
      } catch (IllegalAccessException var8) {
         throw new AssertionError("Couldn't invoke method, " + m + " returned by PropertyDescriptor.");
      }
   }

   private final Object convert(Object o, Object output) throws ConversionException {
      Class inType = o.getClass();
      if (output instanceof PropertyDescriptor) {
         try {
            Converter converter = this.findConverter(o.getClass(), output);
            if (converter != null) {
               return converter.convert(o);
            }

            return this.convert(o, ((PropertyDescriptor)output).getPropertyType());
         } catch (ConversionException var10) {
            if (this.conversionExceptionsFatal) {
               throw var10;
            }
         }
      } else if (output instanceof Class) {
         Class outType = (Class)output;
         if (outType.isAssignableFrom(inType)) {
            return o;
         }

         Converter toDataRetriever;
         try {
            toDataRetriever = this.findConverter(inType, outType);
            if (toDataRetriever != null) {
               return toDataRetriever.convert(o);
            }
         } catch (ConversionException var13) {
            if (this.conversionExceptionsFatal) {
               throw var13;
            }
         }

         Object res;
         try {
            if (outType.isArray()) {
               res = this.convertToArray(o, outType);
               if (res != null) {
                  return res;
               }
            }
         } catch (ConversionException var14) {
            if (this.conversionExceptionsFatal) {
               throw var14;
            }
         }

         try {
            if (inType == String.class && this.isPrimitiveOrWrapper(outType)) {
               res = this.convertToPrimitive((String)o, outType);
               if (res != null) {
                  return res;
               }
            }
         } catch (ConversionException var11) {
            if (this.conversionExceptionsFatal) {
               throw var11;
            }
         }

         try {
            res = this.convertViaConstructor(o, outType);
            if (res != null) {
               return res;
            }
         } catch (ConversionException var12) {
            if (this.conversionExceptionsFatal) {
               throw var12;
            }
         }

         toDataRetriever = this.findConverter(inType, DataRetriever.class);
         if (toDataRetriever != null) {
            try {
               Object obj = outType.newInstance();
               this.initializeBean(obj, (DataRetriever)toDataRetriever.convert(o));
               return obj;
            } catch (IllegalAccessException var7) {
            } catch (InstantiationException var8) {
            } catch (ConversionException var9) {
               throw new ConversionException(inType, outType, var9);
            }
         }
      }

      throw new ConversionException(o, output);
   }

   private boolean isPrimitiveOrWrapper(Class outType) {
      return outType.isPrimitive() || outType == Boolean.class || outType == Byte.class || outType == Character.class || outType == Double.class || outType == Float.class || outType == Integer.class || outType == Long.class || outType == Class.class;
   }

   private Object convertToPrimitive(String s, Class outType) throws ConversionException {
      try {
         if (outType != Boolean.class && outType != Boolean.TYPE) {
            if (outType != Byte.class && outType != Byte.TYPE) {
               if (outType != Character.class && outType != Character.TYPE) {
                  if (outType != Double.class && outType != Double.TYPE) {
                     if (outType != Float.class && outType != Float.TYPE) {
                        if (outType != Integer.class && outType != Integer.TYPE) {
                           if (outType != Long.class && outType != Long.TYPE) {
                              return outType == Class.class ? Class.forName(s) : null;
                           } else if (s.startsWith("0x")) {
                              return Long.valueOf(s.substring(2), 16);
                           } else if (s.startsWith("#")) {
                              return Long.valueOf(s.substring(1), 16);
                           } else {
                              return s.startsWith("0") && s.length() > 1 ? Long.valueOf(s.substring(1), 8) : Long.valueOf(s);
                           }
                        } else {
                           return Integer.decode(s);
                        }
                     } else {
                        return Float.valueOf(s);
                     }
                  } else {
                     return Double.valueOf(s);
                  }
               } else {
                  return new Character(s.charAt(0));
               }
            } else {
               return Byte.decode(s);
            }
         } else {
            return Boolean.valueOf(s);
         }
      } catch (ClassNotFoundException var4) {
         throw new ConversionException(s, outType, var4);
      } catch (NumberFormatException var5) {
         throw new ConversionException(s, outType, var5);
      }
   }

   private Converter findConverter(Object input, Object output) {
      ConverterKey key = new ConverterKey(input, output);
      Converter converter = (Converter)this.converters.get(key);
      if (converter != null) {
         return converter;
      } else {
         if (input instanceof Class && output instanceof Class) {
            Class inType = (Class)input;
            Class outType = (Class)output;
            Enumeration superclasses = ReflectUtils.allSuperclasses(inType);
            converter = this.findConverterForTypes(superclasses, outType);
            if (converter != null) {
               return converter;
            }

            Enumeration interfaces = ReflectUtils.allInterfaces(inType);
            converter = this.findConverter(interfaces, outType);
            if (converter != null) {
               return converter;
            }
         }

         return null;
      }
   }

   private Converter findConverterForTypes(Enumeration types, Class outType) {
      while(true) {
         if (types.hasMoreElements()) {
            Class c = (Class)types.nextElement();
            ConverterKey key = new ConverterKey(c, outType);
            Converter converter = (Converter)this.converters.get(key);
            if (converter == null) {
               continue;
            }

            return converter;
         }

         return null;
      }
   }

   private Constructor findConstructor(Class inType, Class outType) throws NoSuchMethodException {
      Constructor cons = outType.getConstructor(inType);
      if (cons != null) {
         return cons;
      } else {
         cons = this.findConstructor(ReflectUtils.allSuperclasses(inType), outType);
         if (cons != null) {
            return cons;
         } else {
            cons = this.findConstructor(ReflectUtils.allInterfaces(inType), outType);
            return cons;
         }
      }
   }

   private Object convertViaConstructor(Object o, Class outType) throws ConversionException {
      Class inType = o.getClass();

      try {
         Constructor cons = this.findConstructor(inType, outType);
         return cons != null ? cons.newInstance(o) : null;
      } catch (Exception var5) {
         throw new ConversionException(o, outType, var5);
      }
   }

   private Constructor findConstructor(Enumeration types, Class outType) {
      while(types.hasMoreElements()) {
         Class c = (Class)types.nextElement();

         try {
            Constructor cons = outType.getConstructor(c);
            if (cons != null) {
               return cons;
            }
         } catch (NoSuchMethodException var5) {
         }
      }

      return null;
   }

   private boolean isEnumerable(Class type) {
      return type.isArray() || type == Vector.class || type == Enumeration.class || this.findConverter(type, Enumeration.class) != null;
   }

   private Enumeration enumerate(Object o) throws ConversionException {
      Class type = o.getClass();
      if (type == Enumeration.class) {
         return (Enumeration)o;
      } else if (type == Vector.class) {
         return ((Vector)o).elements();
      } else if (type.isArray()) {
         return new ArrayEnumerator((Object[])((Object[])o));
      } else {
         Converter converter = this.findConverter(type, Enumeration.class);
         return converter != null ? (Enumeration)converter.convert(o) : null;
      }
   }

   private Object convertToArray(Object o, Class outType) throws ConversionException {
      Debug.assertion(outType.isArray());
      Enumeration e = this.enumerate(o);
      if (e == null) {
         return null;
      } else {
         Class componentType = outType.getComponentType();
         Vector v = new Vector();

         while(e.hasMoreElements()) {
            Object item = e.nextElement();
            v.addElement(this.convert(item, componentType));
         }

         int size = v.size();
         Object array = Array.newInstance(componentType, size);

         for(int i = 0; i < size; ++i) {
            Array.set(array, i, v.elementAt(i));
         }

         return array;
      }
   }

   private static class Dictionary2DataRetriever extends ConverterHelper {
      Dictionary2DataRetriever() {
         super(Dictionary.class, DataRetriever.class);
      }

      public Object convert(Object o) throws ConversionException {
         Debug.assertion(o instanceof Dictionary);
         final Dictionary dict = (Dictionary)o;
         return new DataRetrieverHelper(dict) {
            public Object get(String key) {
               return dict.get(key);
            }
         };
      }
   }

   public abstract static class DataRetrieverHelper implements DataRetriever {
      public Object data;

      public DataRetrieverHelper(Object data) {
         this.data = data;
      }

      public abstract Object get(String var1);
   }

   public interface DataRetriever {
      Object get(String var1);
   }

   private static class ConverterKey extends AggregateKey {
      ConverterKey(Object input, Object output) {
         super(SpecialKeys.getKey(input), SpecialKeys.getKey(output));
      }

      ConverterKey(Converter converter) {
         this(converter.getInput(), converter.getOutput());
      }
   }

   public abstract static class ConverterHelper implements Converter {
      private Object input;
      private Object output;

      public Object getInput() {
         return this.input;
      }

      public void setInput(Object o) {
         this.input = o;
      }

      public Object getOutput() {
         return this.output;
      }

      public void setOutput(Object o) {
         this.output = o;
      }

      public ConverterHelper(Object input, Object output) {
         this.input = input;
         this.output = output;
      }

      public abstract Object convert(Object var1) throws ConversionException;
   }
}
