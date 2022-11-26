package com.bea.core.repackaged.springframework.cglib.core;

import com.bea.core.repackaged.springframework.asm.Label;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.core.internal.CustomizerRegistry;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmitUtils {
   private static final Signature CSTRUCT_NULL = TypeUtils.parseConstructor("");
   private static final Signature CSTRUCT_THROWABLE = TypeUtils.parseConstructor("Throwable");
   private static final Signature GET_NAME = TypeUtils.parseSignature("String getName()");
   private static final Signature HASH_CODE = TypeUtils.parseSignature("int hashCode()");
   private static final Signature EQUALS = TypeUtils.parseSignature("boolean equals(Object)");
   private static final Signature STRING_LENGTH = TypeUtils.parseSignature("int length()");
   private static final Signature STRING_CHAR_AT = TypeUtils.parseSignature("char charAt(int)");
   private static final Signature FOR_NAME = TypeUtils.parseSignature("Class forName(String)");
   private static final Signature DOUBLE_TO_LONG_BITS = TypeUtils.parseSignature("long doubleToLongBits(double)");
   private static final Signature FLOAT_TO_INT_BITS = TypeUtils.parseSignature("int floatToIntBits(float)");
   private static final Signature TO_STRING = TypeUtils.parseSignature("String toString()");
   private static final Signature APPEND_STRING = TypeUtils.parseSignature("StringBuffer append(String)");
   private static final Signature APPEND_INT = TypeUtils.parseSignature("StringBuffer append(int)");
   private static final Signature APPEND_DOUBLE = TypeUtils.parseSignature("StringBuffer append(double)");
   private static final Signature APPEND_FLOAT = TypeUtils.parseSignature("StringBuffer append(float)");
   private static final Signature APPEND_CHAR = TypeUtils.parseSignature("StringBuffer append(char)");
   private static final Signature APPEND_LONG = TypeUtils.parseSignature("StringBuffer append(long)");
   private static final Signature APPEND_BOOLEAN = TypeUtils.parseSignature("StringBuffer append(boolean)");
   private static final Signature LENGTH = TypeUtils.parseSignature("int length()");
   private static final Signature SET_LENGTH = TypeUtils.parseSignature("void setLength(int)");
   private static final Signature GET_DECLARED_METHOD = TypeUtils.parseSignature("java.lang.reflect.Method getDeclaredMethod(String, Class[])");
   public static final ArrayDelimiters DEFAULT_DELIMITERS = new ArrayDelimiters("{", ", ", "}");

   private EmitUtils() {
   }

   public static void factory_method(ClassEmitter ce, Signature sig) {
      CodeEmitter e = ce.begin_method(1, sig, (Type[])null);
      e.new_instance_this();
      e.dup();
      e.load_args();
      e.invoke_constructor_this(TypeUtils.parseConstructor(sig.getArgumentTypes()));
      e.return_value();
      e.end_method();
   }

   public static void null_constructor(ClassEmitter ce) {
      CodeEmitter e = ce.begin_method(1, CSTRUCT_NULL, (Type[])null);
      e.load_this();
      e.super_invoke_constructor();
      e.return_value();
      e.end_method();
   }

   public static void process_array(CodeEmitter e, Type type, ProcessArrayCallback callback) {
      Type componentType = TypeUtils.getComponentType(type);
      Local array = e.make_local();
      Local loopvar = e.make_local(Type.INT_TYPE);
      Label loopbody = e.make_label();
      Label checkloop = e.make_label();
      e.store_local(array);
      e.push(0);
      e.store_local(loopvar);
      e.goTo(checkloop);
      e.mark(loopbody);
      e.load_local(array);
      e.load_local(loopvar);
      e.array_load(componentType);
      callback.processElement(componentType);
      e.iinc(loopvar, 1);
      e.mark(checkloop);
      e.load_local(loopvar);
      e.load_local(array);
      e.arraylength();
      e.if_icmp(155, loopbody);
   }

   public static void process_arrays(CodeEmitter e, Type type, ProcessArrayCallback callback) {
      Type componentType = TypeUtils.getComponentType(type);
      Local array1 = e.make_local();
      Local array2 = e.make_local();
      Local loopvar = e.make_local(Type.INT_TYPE);
      Label loopbody = e.make_label();
      Label checkloop = e.make_label();
      e.store_local(array1);
      e.store_local(array2);
      e.push(0);
      e.store_local(loopvar);
      e.goTo(checkloop);
      e.mark(loopbody);
      e.load_local(array1);
      e.load_local(loopvar);
      e.array_load(componentType);
      e.load_local(array2);
      e.load_local(loopvar);
      e.array_load(componentType);
      callback.processElement(componentType);
      e.iinc(loopvar, 1);
      e.mark(checkloop);
      e.load_local(loopvar);
      e.load_local(array1);
      e.arraylength();
      e.if_icmp(155, loopbody);
   }

   public static void string_switch(CodeEmitter e, String[] strings, int switchStyle, ObjectSwitchCallback callback) {
      try {
         switch (switchStyle) {
            case 0:
               string_switch_trie(e, strings, callback);
               break;
            case 1:
               string_switch_hash(e, strings, callback, false);
               break;
            case 2:
               string_switch_hash(e, strings, callback, true);
               break;
            default:
               throw new IllegalArgumentException("unknown switch style " + switchStyle);
         }

      } catch (RuntimeException var5) {
         throw var5;
      } catch (Error var6) {
         throw var6;
      } catch (Exception var7) {
         throw new CodeGenerationException(var7);
      }
   }

   private static void string_switch_trie(final CodeEmitter e, String[] strings, final ObjectSwitchCallback callback) throws Exception {
      final Label def = e.make_label();
      final Label end = e.make_label();
      final Map buckets = CollectionUtils.bucket(Arrays.asList(strings), new Transformer() {
         public Object transform(Object value) {
            return new Integer(((String)value).length());
         }
      });
      e.dup();
      e.invoke_virtual(Constants.TYPE_STRING, STRING_LENGTH);
      e.process_switch(getSwitchKeys(buckets), new ProcessSwitchCallback() {
         public void processCase(int key, Label ignore_end) throws Exception {
            List bucket = (List)buckets.get(new Integer(key));
            EmitUtils.stringSwitchHelper(e, bucket, callback, def, end, 0);
         }

         public void processDefault() {
            e.goTo(def);
         }
      });
      e.mark(def);
      e.pop();
      callback.processDefault();
      e.mark(end);
   }

   private static void stringSwitchHelper(final CodeEmitter e, List strings, final ObjectSwitchCallback callback, final Label def, final Label end, final int index) throws Exception {
      final int len = ((String)strings.get(0)).length();
      final Map buckets = CollectionUtils.bucket(strings, new Transformer() {
         public Object transform(Object value) {
            return new Integer(((String)value).charAt(index));
         }
      });
      e.dup();
      e.push(index);
      e.invoke_virtual(Constants.TYPE_STRING, STRING_CHAR_AT);
      e.process_switch(getSwitchKeys(buckets), new ProcessSwitchCallback() {
         public void processCase(int key, Label ignore_end) throws Exception {
            List bucket = (List)buckets.get(new Integer(key));
            if (index + 1 == len) {
               e.pop();
               callback.processCase(bucket.get(0), end);
            } else {
               EmitUtils.stringSwitchHelper(e, bucket, callback, def, end, index + 1);
            }

         }

         public void processDefault() {
            e.goTo(def);
         }
      });
   }

   static int[] getSwitchKeys(Map buckets) {
      int[] keys = new int[buckets.size()];
      int index = 0;

      for(Iterator it = buckets.keySet().iterator(); it.hasNext(); keys[index++] = (Integer)it.next()) {
      }

      Arrays.sort(keys);
      return keys;
   }

   private static void string_switch_hash(final CodeEmitter e, String[] strings, final ObjectSwitchCallback callback, final boolean skipEquals) throws Exception {
      final Map buckets = CollectionUtils.bucket(Arrays.asList(strings), new Transformer() {
         public Object transform(Object value) {
            return new Integer(value.hashCode());
         }
      });
      final Label def = e.make_label();
      final Label end = e.make_label();
      e.dup();
      e.invoke_virtual(Constants.TYPE_OBJECT, HASH_CODE);
      e.process_switch(getSwitchKeys(buckets), new ProcessSwitchCallback() {
         public void processCase(int key, Label ignore_end) throws Exception {
            List bucket = (List)buckets.get(new Integer(key));
            Label next = null;
            String string;
            if (skipEquals && bucket.size() == 1) {
               if (skipEquals) {
                  e.pop();
               }

               callback.processCase((String)bucket.get(0), end);
            } else {
               for(Iterator it = bucket.iterator(); it.hasNext(); callback.processCase(string, end)) {
                  string = (String)it.next();
                  if (next != null) {
                     e.mark(next);
                  }

                  if (it.hasNext()) {
                     e.dup();
                  }

                  e.push(string);
                  e.invoke_virtual(Constants.TYPE_OBJECT, EmitUtils.EQUALS);
                  CodeEmitter var10001;
                  if (it.hasNext()) {
                     var10001 = e;
                     e.if_jump(153, next = e.make_label());
                     e.pop();
                  } else {
                     var10001 = e;
                     e.if_jump(153, def);
                  }
               }
            }

         }

         public void processDefault() {
            e.pop();
         }
      });
      e.mark(def);
      callback.processDefault();
      e.mark(end);
   }

   public static void load_class_this(CodeEmitter e) {
      load_class_helper(e, e.getClassEmitter().getClassType());
   }

   public static void load_class(CodeEmitter e, Type type) {
      if (TypeUtils.isPrimitive(type)) {
         if (type == Type.VOID_TYPE) {
            throw new IllegalArgumentException("cannot load void type");
         }

         e.getstatic(TypeUtils.getBoxedType(type), "TYPE", Constants.TYPE_CLASS);
      } else {
         load_class_helper(e, type);
      }

   }

   private static void load_class_helper(CodeEmitter e, Type type) {
      if (e.isStaticHook()) {
         e.push(TypeUtils.emulateClassGetName(type));
         e.invoke_static(Constants.TYPE_CLASS, FOR_NAME);
      } else {
         ClassEmitter ce = e.getClassEmitter();
         String typeName = TypeUtils.emulateClassGetName(type);
         String fieldName = "CGLIB$load_class$" + TypeUtils.escapeType(typeName);
         if (!ce.isFieldDeclared(fieldName)) {
            ce.declare_field(26, fieldName, Constants.TYPE_CLASS, (Object)null);
            CodeEmitter hook = ce.getStaticHook();
            hook.push(typeName);
            hook.invoke_static(Constants.TYPE_CLASS, FOR_NAME);
            hook.putstatic(ce.getClassType(), fieldName, Constants.TYPE_CLASS);
         }

         e.getfield(fieldName);
      }

   }

   public static void push_array(CodeEmitter e, Object[] array) {
      e.push(array.length);
      e.newarray(Type.getType(remapComponentType(array.getClass().getComponentType())));

      for(int i = 0; i < array.length; ++i) {
         e.dup();
         e.push(i);
         push_object(e, array[i]);
         e.aastore();
      }

   }

   private static Class remapComponentType(Class componentType) {
      return componentType.equals(Type.class) ? Class.class : componentType;
   }

   public static void push_object(CodeEmitter e, Object obj) {
      if (obj == null) {
         e.aconst_null();
      } else {
         Class type = obj.getClass();
         if (type.isArray()) {
            push_array(e, (Object[])((Object[])obj));
         } else if (obj instanceof String) {
            e.push((String)obj);
         } else if (obj instanceof Type) {
            load_class(e, (Type)obj);
         } else if (obj instanceof Class) {
            load_class(e, Type.getType((Class)obj));
         } else if (obj instanceof BigInteger) {
            e.new_instance(Constants.TYPE_BIG_INTEGER);
            e.dup();
            e.push(obj.toString());
            e.invoke_constructor(Constants.TYPE_BIG_INTEGER);
         } else {
            if (!(obj instanceof BigDecimal)) {
               throw new IllegalArgumentException("unknown type: " + obj.getClass());
            }

            e.new_instance(Constants.TYPE_BIG_DECIMAL);
            e.dup();
            e.push(obj.toString());
            e.invoke_constructor(Constants.TYPE_BIG_DECIMAL);
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public static void hash_code(CodeEmitter e, Type type, int multiplier, Customizer customizer) {
      hash_code(e, type, multiplier, CustomizerRegistry.singleton(customizer));
   }

   public static void hash_code(CodeEmitter e, Type type, int multiplier, CustomizerRegistry registry) {
      if (TypeUtils.isArray(type)) {
         hash_array(e, type, multiplier, registry);
      } else {
         e.swap(Type.INT_TYPE, type);
         e.push(multiplier);
         e.math(104, Type.INT_TYPE);
         e.swap(type, Type.INT_TYPE);
         if (TypeUtils.isPrimitive(type)) {
            hash_primitive(e, type);
         } else {
            hash_object(e, type, registry);
         }

         e.math(96, Type.INT_TYPE);
      }

   }

   private static void hash_array(final CodeEmitter e, Type type, final int multiplier, final CustomizerRegistry registry) {
      Label skip = e.make_label();
      Label end = e.make_label();
      e.dup();
      e.ifnull(skip);
      process_array(e, type, new ProcessArrayCallback() {
         public void processElement(Type type) {
            EmitUtils.hash_code(e, type, multiplier, registry);
         }
      });
      e.goTo(end);
      e.mark(skip);
      e.pop();
      e.mark(end);
   }

   private static void hash_object(CodeEmitter e, Type type, CustomizerRegistry registry) {
      Label skip = e.make_label();
      Label end = e.make_label();
      e.dup();
      e.ifnull(skip);
      boolean customHashCode = false;
      Iterator var6 = registry.get(HashCodeCustomizer.class).iterator();

      while(var6.hasNext()) {
         HashCodeCustomizer customizer = (HashCodeCustomizer)var6.next();
         if (customizer.customize(e, type)) {
            customHashCode = true;
            break;
         }
      }

      if (!customHashCode) {
         var6 = registry.get(Customizer.class).iterator();

         while(var6.hasNext()) {
            Customizer customizer = (Customizer)var6.next();
            customizer.customize(e, type);
         }

         e.invoke_virtual(Constants.TYPE_OBJECT, HASH_CODE);
      }

      e.goTo(end);
      e.mark(skip);
      e.pop();
      e.push(0);
      e.mark(end);
   }

   private static void hash_primitive(CodeEmitter e, Type type) {
      switch (type.getSort()) {
         case 1:
            e.push(1);
            e.math(130, Type.INT_TYPE);
         case 2:
         case 3:
         case 4:
         case 5:
         default:
            break;
         case 6:
            e.invoke_static(Constants.TYPE_FLOAT, FLOAT_TO_INT_BITS);
            break;
         case 8:
            e.invoke_static(Constants.TYPE_DOUBLE, DOUBLE_TO_LONG_BITS);
         case 7:
            hash_long(e);
      }

   }

   private static void hash_long(CodeEmitter e) {
      e.dup2();
      e.push(32);
      e.math(124, Type.LONG_TYPE);
      e.math(130, Type.LONG_TYPE);
      e.cast_numeric(Type.LONG_TYPE, Type.INT_TYPE);
   }

   /** @deprecated */
   @Deprecated
   public static void not_equals(CodeEmitter e, Type type, Label notEquals, Customizer customizer) {
      not_equals(e, type, notEquals, CustomizerRegistry.singleton(customizer));
   }

   public static void not_equals(final CodeEmitter e, Type type, final Label notEquals, final CustomizerRegistry registry) {
      (new ProcessArrayCallback() {
         public void processElement(Type type) {
            EmitUtils.not_equals_helper(e, type, notEquals, registry, this);
         }
      }).processElement(type);
   }

   private static void not_equals_helper(CodeEmitter e, Type type, Label notEquals, CustomizerRegistry registry, ProcessArrayCallback callback) {
      if (TypeUtils.isPrimitive(type)) {
         e.if_cmp(type, 154, notEquals);
      } else {
         Label end = e.make_label();
         nullcmp(e, notEquals, end);
         if (TypeUtils.isArray(type)) {
            Label checkContents = e.make_label();
            e.dup2();
            e.arraylength();
            e.swap();
            e.arraylength();
            e.if_icmp(153, checkContents);
            e.pop2();
            e.goTo(notEquals);
            e.mark(checkContents);
            process_arrays(e, type, callback);
         } else {
            List customizers = registry.get(Customizer.class);
            if (!customizers.isEmpty()) {
               Iterator var7 = customizers.iterator();

               Customizer customizer;
               while(var7.hasNext()) {
                  customizer = (Customizer)var7.next();
                  customizer.customize(e, type);
               }

               e.swap();
               var7 = customizers.iterator();

               while(var7.hasNext()) {
                  customizer = (Customizer)var7.next();
                  customizer.customize(e, type);
               }
            }

            e.invoke_virtual(Constants.TYPE_OBJECT, EQUALS);
            e.if_jump(153, notEquals);
         }

         e.mark(end);
      }

   }

   private static void nullcmp(CodeEmitter e, Label oneNull, Label bothNull) {
      e.dup2();
      Label nonNull = e.make_label();
      Label oneNullHelper = e.make_label();
      Label end = e.make_label();
      e.ifnonnull(nonNull);
      e.ifnonnull(oneNullHelper);
      e.pop2();
      e.goTo(bothNull);
      e.mark(nonNull);
      e.ifnull(oneNullHelper);
      e.goTo(end);
      e.mark(oneNullHelper);
      e.pop2();
      e.goTo(oneNull);
      e.mark(end);
   }

   /** @deprecated */
   @Deprecated
   public static void append_string(CodeEmitter e, Type type, ArrayDelimiters delims, Customizer customizer) {
      append_string(e, type, delims, CustomizerRegistry.singleton(customizer));
   }

   public static void append_string(final CodeEmitter e, Type type, ArrayDelimiters delims, final CustomizerRegistry registry) {
      final ArrayDelimiters d = delims != null ? delims : DEFAULT_DELIMITERS;
      ProcessArrayCallback callback = new ProcessArrayCallback() {
         public void processElement(Type type) {
            EmitUtils.append_string_helper(e, type, d, registry, this);
            e.push(d.inside);
            e.invoke_virtual(Constants.TYPE_STRING_BUFFER, EmitUtils.APPEND_STRING);
         }
      };
      append_string_helper(e, type, d, registry, callback);
   }

   private static void append_string_helper(CodeEmitter e, Type type, ArrayDelimiters delims, CustomizerRegistry registry, ProcessArrayCallback callback) {
      Label skip = e.make_label();
      Label end = e.make_label();
      if (TypeUtils.isPrimitive(type)) {
         switch (type.getSort()) {
            case 1:
               e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_BOOLEAN);
               break;
            case 2:
               e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_CHAR);
               break;
            case 3:
            case 4:
            case 5:
               e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_INT);
               break;
            case 6:
               e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_FLOAT);
               break;
            case 7:
               e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_LONG);
               break;
            case 8:
               e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_DOUBLE);
         }
      } else if (TypeUtils.isArray(type)) {
         e.dup();
         e.ifnull(skip);
         e.swap();
         if (delims != null && delims.before != null && !"".equals(delims.before)) {
            e.push(delims.before);
            e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_STRING);
            e.swap();
         }

         process_array(e, type, callback);
         shrinkStringBuffer(e, 2);
         if (delims != null && delims.after != null && !"".equals(delims.after)) {
            e.push(delims.after);
            e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_STRING);
         }
      } else {
         e.dup();
         e.ifnull(skip);
         Iterator var7 = registry.get(Customizer.class).iterator();

         while(var7.hasNext()) {
            Customizer customizer = (Customizer)var7.next();
            customizer.customize(e, type);
         }

         e.invoke_virtual(Constants.TYPE_OBJECT, TO_STRING);
         e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_STRING);
      }

      e.goTo(end);
      e.mark(skip);
      e.pop();
      e.push("null");
      e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_STRING);
      e.mark(end);
   }

   private static void shrinkStringBuffer(CodeEmitter e, int amt) {
      e.dup();
      e.dup();
      e.invoke_virtual(Constants.TYPE_STRING_BUFFER, LENGTH);
      e.push(amt);
      e.math(100, Type.INT_TYPE);
      e.invoke_virtual(Constants.TYPE_STRING_BUFFER, SET_LENGTH);
   }

   public static void load_method(CodeEmitter e, MethodInfo method) {
      load_class(e, method.getClassInfo().getType());
      e.push(method.getSignature().getName());
      push_object(e, method.getSignature().getArgumentTypes());
      e.invoke_virtual(Constants.TYPE_CLASS, GET_DECLARED_METHOD);
   }

   public static void method_switch(CodeEmitter e, List methods, ObjectSwitchCallback callback) {
      member_switch_helper(e, methods, callback, true);
   }

   public static void constructor_switch(CodeEmitter e, List constructors, ObjectSwitchCallback callback) {
      member_switch_helper(e, constructors, callback, false);
   }

   private static void member_switch_helper(final CodeEmitter e, List members, final ObjectSwitchCallback callback, boolean useName) {
      try {
         final Map cache = new HashMap();
         final ParameterTyper cached = new ParameterTyper() {
            public Type[] getParameterTypes(MethodInfo member) {
               Type[] types = (Type[])((Type[])cache.get(member));
               if (types == null) {
                  cache.put(member, types = member.getSignature().getArgumentTypes());
               }

               return types;
            }
         };
         final Label def = e.make_label();
         final Label end = e.make_label();
         if (useName) {
            e.swap();
            final Map buckets = CollectionUtils.bucket(members, new Transformer() {
               public Object transform(Object value) {
                  return ((MethodInfo)value).getSignature().getName();
               }
            });
            String[] names = (String[])((String[])buckets.keySet().toArray(new String[buckets.size()]));
            string_switch(e, names, 1, new ObjectSwitchCallback() {
               public void processCase(Object key, Label dontUseEnd) throws Exception {
                  EmitUtils.member_helper_size(e, (List)buckets.get(key), callback, cached, def, end);
               }

               public void processDefault() throws Exception {
                  e.goTo(def);
               }
            });
         } else {
            member_helper_size(e, members, callback, cached, def, end);
         }

         e.mark(def);
         e.pop();
         callback.processDefault();
         e.mark(end);
      } catch (RuntimeException var10) {
         throw var10;
      } catch (Error var11) {
         throw var11;
      } catch (Exception var12) {
         throw new CodeGenerationException(var12);
      }
   }

   private static void member_helper_size(final CodeEmitter e, List members, final ObjectSwitchCallback callback, final ParameterTyper typer, final Label def, final Label end) throws Exception {
      final Map buckets = CollectionUtils.bucket(members, new Transformer() {
         public Object transform(Object value) {
            return new Integer(typer.getParameterTypes((MethodInfo)value).length);
         }
      });
      e.dup();
      e.arraylength();
      e.process_switch(getSwitchKeys(buckets), new ProcessSwitchCallback() {
         public void processCase(int key, Label dontUseEnd) throws Exception {
            List bucket = (List)buckets.get(new Integer(key));
            EmitUtils.member_helper_type(e, bucket, callback, typer, def, end, new BitSet());
         }

         public void processDefault() throws Exception {
            e.goTo(def);
         }
      });
   }

   private static void member_helper_type(final CodeEmitter e, List members, final ObjectSwitchCallback callback, final ParameterTyper typer, final Label def, final Label end, final BitSet checked) throws Exception {
      int i;
      if (members.size() == 1) {
         MethodInfo member = (MethodInfo)members.get(0);
         Type[] types = typer.getParameterTypes(member);

         for(i = 0; i < types.length; ++i) {
            if (checked == null || !checked.get(i)) {
               e.dup();
               e.aaload(i);
               e.invoke_virtual(Constants.TYPE_CLASS, GET_NAME);
               e.push(TypeUtils.emulateClassGetName(types[i]));
               e.invoke_virtual(Constants.TYPE_OBJECT, EQUALS);
               e.if_jump(153, def);
            }
         }

         e.pop();
         callback.processCase(member, end);
      } else {
         Type[] example = typer.getParameterTypes((MethodInfo)members.get(0));
         final Map buckets = null;
         i = -1;

         for(final int i = 0; i < example.length; ++i) {
            Map test = CollectionUtils.bucket(members, new Transformer() {
               public Object transform(Object value) {
                  return TypeUtils.emulateClassGetName(typer.getParameterTypes((MethodInfo)value)[i]);
               }
            });
            if (buckets == null || test.size() > buckets.size()) {
               buckets = test;
               i = i;
            }
         }

         if (buckets != null && buckets.size() != 1) {
            checked.set(i);
            e.dup();
            e.aaload(i);
            e.invoke_virtual(Constants.TYPE_CLASS, GET_NAME);
            String[] names = (String[])((String[])buckets.keySet().toArray(new String[buckets.size()]));
            string_switch(e, names, 1, new ObjectSwitchCallback() {
               public void processCase(Object key, Label dontUseEnd) throws Exception {
                  EmitUtils.member_helper_type(e, (List)buckets.get(key), callback, typer, def, end, checked);
               }

               public void processDefault() throws Exception {
                  e.goTo(def);
               }
            });
         } else {
            e.goTo(def);
         }
      }

   }

   public static void wrap_throwable(Block block, Type wrapper) {
      CodeEmitter e = block.getCodeEmitter();
      e.catch_exception(block, Constants.TYPE_THROWABLE);
      e.new_instance(wrapper);
      e.dup_x1();
      e.swap();
      e.invoke_constructor(wrapper, CSTRUCT_THROWABLE);
      e.athrow();
   }

   public static void add_properties(ClassEmitter ce, String[] names, Type[] types) {
      for(int i = 0; i < names.length; ++i) {
         String fieldName = "$cglib_prop_" + names[i];
         ce.declare_field(2, fieldName, types[i], (Object)null);
         add_property(ce, names[i], types[i], fieldName);
      }

   }

   public static void add_property(ClassEmitter ce, String name, Type type, String fieldName) {
      String property = TypeUtils.upperFirst(name);
      CodeEmitter e = ce.begin_method(1, new Signature("get" + property, type, Constants.TYPES_EMPTY), (Type[])null);
      e.load_this();
      e.getfield(fieldName);
      e.return_value();
      e.end_method();
      e = ce.begin_method(1, new Signature("set" + property, Type.VOID_TYPE, new Type[]{type}), (Type[])null);
      e.load_this();
      e.load_arg(0);
      e.putfield(fieldName);
      e.return_value();
      e.end_method();
   }

   public static void wrap_undeclared_throwable(CodeEmitter e, Block handler, Type[] exceptions, Type wrapper) {
      Set set = exceptions == null ? Collections.EMPTY_SET : new HashSet(Arrays.asList(exceptions));
      if (!((Set)set).contains(Constants.TYPE_THROWABLE)) {
         boolean needThrow = exceptions != null;
         if (!((Set)set).contains(Constants.TYPE_RUNTIME_EXCEPTION)) {
            e.catch_exception(handler, Constants.TYPE_RUNTIME_EXCEPTION);
            needThrow = true;
         }

         if (!((Set)set).contains(Constants.TYPE_ERROR)) {
            e.catch_exception(handler, Constants.TYPE_ERROR);
            needThrow = true;
         }

         if (exceptions != null) {
            for(int i = 0; i < exceptions.length; ++i) {
               e.catch_exception(handler, exceptions[i]);
            }
         }

         if (needThrow) {
            e.athrow();
         }

         e.catch_exception(handler, Constants.TYPE_THROWABLE);
         e.new_instance(wrapper);
         e.dup_x1();
         e.swap();
         e.invoke_constructor(wrapper, CSTRUCT_THROWABLE);
         e.athrow();
      }
   }

   public static CodeEmitter begin_method(ClassEmitter e, MethodInfo method) {
      return begin_method(e, method, method.getModifiers());
   }

   public static CodeEmitter begin_method(ClassEmitter e, MethodInfo method, int access) {
      return e.begin_method(access, method.getSignature(), method.getExceptionTypes());
   }

   private interface ParameterTyper {
      Type[] getParameterTypes(MethodInfo var1);
   }

   public static class ArrayDelimiters {
      private String before;
      private String inside;
      private String after;

      public ArrayDelimiters(String before, String inside, String after) {
         this.before = before;
         this.inside = inside;
         this.after = after;
      }
   }
}
