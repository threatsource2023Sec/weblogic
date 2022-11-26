package com.bea.core.repackaged.springframework.cglib.beans;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.asm.Label;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.core.ClassEmitter;
import com.bea.core.repackaged.springframework.cglib.core.CodeEmitter;
import com.bea.core.repackaged.springframework.cglib.core.Constants;
import com.bea.core.repackaged.springframework.cglib.core.EmitUtils;
import com.bea.core.repackaged.springframework.cglib.core.MethodInfo;
import com.bea.core.repackaged.springframework.cglib.core.ObjectSwitchCallback;
import com.bea.core.repackaged.springframework.cglib.core.ReflectUtils;
import com.bea.core.repackaged.springframework.cglib.core.Signature;
import com.bea.core.repackaged.springframework.cglib.core.TypeUtils;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class BeanMapEmitter extends ClassEmitter {
   private static final Type BEAN_MAP = TypeUtils.parseType("com.bea.core.repackaged.springframework.cglib.beans.BeanMap");
   private static final Type FIXED_KEY_SET = TypeUtils.parseType("com.bea.core.repackaged.springframework.cglib.beans.FixedKeySet");
   private static final Signature CSTRUCT_OBJECT = TypeUtils.parseConstructor("Object");
   private static final Signature CSTRUCT_STRING_ARRAY = TypeUtils.parseConstructor("String[]");
   private static final Signature BEAN_MAP_GET = TypeUtils.parseSignature("Object get(Object, Object)");
   private static final Signature BEAN_MAP_PUT = TypeUtils.parseSignature("Object put(Object, Object, Object)");
   private static final Signature KEY_SET = TypeUtils.parseSignature("java.util.Set keySet()");
   private static final Signature NEW_INSTANCE;
   private static final Signature GET_PROPERTY_TYPE;

   public BeanMapEmitter(ClassVisitor v, String className, Class type, int require) {
      super(v);
      this.begin_class(46, 1, className, BEAN_MAP, (Type[])null, "<generated>");
      EmitUtils.null_constructor(this);
      EmitUtils.factory_method(this, NEW_INSTANCE);
      this.generateConstructor();
      Map getters = this.makePropertyMap(ReflectUtils.getBeanGetters(type));
      Map setters = this.makePropertyMap(ReflectUtils.getBeanSetters(type));
      Map allProps = new HashMap();
      allProps.putAll(getters);
      allProps.putAll(setters);
      if (require != 0) {
         Iterator it = allProps.keySet().iterator();

         label26:
         while(true) {
            String name;
            do {
               if (!it.hasNext()) {
                  break label26;
               }

               name = (String)it.next();
            } while(((require & 1) == 0 || getters.containsKey(name)) && ((require & 2) == 0 || setters.containsKey(name)));

            it.remove();
            getters.remove(name);
            setters.remove(name);
         }
      }

      this.generateGet(type, getters);
      this.generatePut(type, setters);
      String[] allNames = this.getNames(allProps);
      this.generateKeySet(allNames);
      this.generateGetPropertyType(allProps, allNames);
      this.end_class();
   }

   private Map makePropertyMap(PropertyDescriptor[] props) {
      Map names = new HashMap();

      for(int i = 0; i < props.length; ++i) {
         names.put(props[i].getName(), props[i]);
      }

      return names;
   }

   private String[] getNames(Map propertyMap) {
      return (String[])((String[])propertyMap.keySet().toArray(new String[propertyMap.size()]));
   }

   private void generateConstructor() {
      CodeEmitter e = this.begin_method(1, CSTRUCT_OBJECT, (Type[])null);
      e.load_this();
      e.load_arg(0);
      e.super_invoke_constructor(CSTRUCT_OBJECT);
      e.return_value();
      e.end_method();
   }

   private void generateGet(Class type, final Map getters) {
      final CodeEmitter e = this.begin_method(1, BEAN_MAP_GET, (Type[])null);
      e.load_arg(0);
      e.checkcast(Type.getType(type));
      e.load_arg(1);
      e.checkcast(Constants.TYPE_STRING);
      EmitUtils.string_switch(e, this.getNames(getters), 1, new ObjectSwitchCallback() {
         public void processCase(Object key, Label end) {
            PropertyDescriptor pd = (PropertyDescriptor)getters.get(key);
            MethodInfo method = ReflectUtils.getMethodInfo(pd.getReadMethod());
            e.invoke(method);
            e.box(method.getSignature().getReturnType());
            e.return_value();
         }

         public void processDefault() {
            e.aconst_null();
            e.return_value();
         }
      });
      e.end_method();
   }

   private void generatePut(Class type, final Map setters) {
      final CodeEmitter e = this.begin_method(1, BEAN_MAP_PUT, (Type[])null);
      e.load_arg(0);
      e.checkcast(Type.getType(type));
      e.load_arg(1);
      e.checkcast(Constants.TYPE_STRING);
      EmitUtils.string_switch(e, this.getNames(setters), 1, new ObjectSwitchCallback() {
         public void processCase(Object key, Label end) {
            PropertyDescriptor pd = (PropertyDescriptor)setters.get(key);
            MethodInfo write;
            if (pd.getReadMethod() == null) {
               e.aconst_null();
            } else {
               write = ReflectUtils.getMethodInfo(pd.getReadMethod());
               e.dup();
               e.invoke(write);
               e.box(write.getSignature().getReturnType());
            }

            e.swap();
            e.load_arg(2);
            write = ReflectUtils.getMethodInfo(pd.getWriteMethod());
            e.unbox(write.getSignature().getArgumentTypes()[0]);
            e.invoke(write);
            e.return_value();
         }

         public void processDefault() {
         }
      });
      e.aconst_null();
      e.return_value();
      e.end_method();
   }

   private void generateKeySet(String[] allNames) {
      this.declare_field(10, "keys", FIXED_KEY_SET, (Object)null);
      CodeEmitter e = this.begin_static();
      e.new_instance(FIXED_KEY_SET);
      e.dup();
      EmitUtils.push_array(e, allNames);
      e.invoke_constructor(FIXED_KEY_SET, CSTRUCT_STRING_ARRAY);
      e.putfield("keys");
      e.return_value();
      e.end_method();
      e = this.begin_method(1, KEY_SET, (Type[])null);
      e.load_this();
      e.getfield("keys");
      e.return_value();
      e.end_method();
   }

   private void generateGetPropertyType(final Map allProps, String[] allNames) {
      final CodeEmitter e = this.begin_method(1, GET_PROPERTY_TYPE, (Type[])null);
      e.load_arg(0);
      EmitUtils.string_switch(e, allNames, 1, new ObjectSwitchCallback() {
         public void processCase(Object key, Label end) {
            PropertyDescriptor pd = (PropertyDescriptor)allProps.get(key);
            EmitUtils.load_class(e, Type.getType(pd.getPropertyType()));
            e.return_value();
         }

         public void processDefault() {
            e.aconst_null();
            e.return_value();
         }
      });
      e.end_method();
   }

   static {
      NEW_INSTANCE = new Signature("newInstance", BEAN_MAP, new Type[]{Constants.TYPE_OBJECT});
      GET_PROPERTY_TYPE = TypeUtils.parseSignature("Class getPropertyType(String)");
   }
}
