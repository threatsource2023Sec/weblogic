package org.glassfish.admin.rest.composite;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.utils.ExceptionUtil;
import org.glassfish.admin.rest.utils.JsonUtil;
import org.glassfish.admin.rest.utils.MessageUtil;
import org.glassfish.admin.rest.utils.WebAppUtil;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class CompositeUtil {
   private static final Map webAppToState = new HashMap();

   private CompositeUtil() {
   }

   public static CompositeUtil instance() {
      return CompositeUtil.LazyHolder.INSTANCE;
   }

   public synchronized Object getModel(Class modelIface) {
      WebAppState state = getWebAppState();
      String className = modelIface.getName() + "Impl";
      if (!state.getGeneratedClasses().containsKey(className)) {
         Map properties = new HashMap();
         Set interfaces = new HashSet();
         interfaces.add(modelIface);
         Iterator var6 = interfaces.iterator();

         Class newClass;
         while(var6.hasNext()) {
            newClass = (Class)var6.next();
            this.analyzeInterface(newClass, properties);
         }

         ClassWriter classWriter = new ClassWriter(1);
         this.visitClass(classWriter, className, interfaces, properties);
         Iterator var15 = properties.entrySet().iterator();

         while(var15.hasNext()) {
            Map.Entry entry = (Map.Entry)var15.next();
            String name = (String)entry.getKey();
            Map property = (Map)entry.getValue();
            Class type = (Class)property.get("type");
            this.createField(classWriter, name, type);
            this.createGettersAndSetters(classWriter, modelIface, className, name, property);
         }

         this.createConstructor(classWriter, className, properties);
         classWriter.visitEnd();

         try {
            newClass = this.defineClass(modelIface, className, classWriter.toByteArray());
         } catch (Exception var12) {
            throw var12 instanceof RuntimeException ? (RuntimeException)var12 : new RuntimeException(var12);
         }

         state.getGeneratedClasses().put(className, newClass);
      }

      try {
         return ((Class)state.getGeneratedClasses().get(className)).newInstance();
      } catch (Exception var13) {
         throw var13 instanceof RuntimeException ? (RuntimeException)var13 : new RuntimeException(var13);
      }
   }

   public Object unmarshallClass(Locale locale, Class modelClass, JSONObject json) throws JSONException {
      Object model = this.getModel(modelClass);
      Iterator var5 = this.getSetters(modelClass).iterator();

      while(true) {
         while(true) {
            Method setter;
            String name;
            String attribute;
            Type param0;
            Class class0;
            do {
               if (!var5.hasNext()) {
                  return model;
               }

               setter = (Method)var5.next();
               name = setter.getName();
               attribute = name.substring(3, 4).toLowerCase(Locale.getDefault()) + name.substring(4);
               param0 = setter.getGenericParameterTypes()[0];
               class0 = setter.getParameterTypes()[0];
            } while(!json.has(attribute));

            Object o = json.get(attribute);
            if (JSONArray.class.isAssignableFrom(o.getClass()) && (ParameterizedType.class.isAssignableFrom(param0.getClass()) || ((Class)param0).isArray())) {
               Object values = this.processJsonArray(locale, param0, (JSONArray)o);
               this.invoke(locale, setter, attribute, model, values);
            } else if (JSONObject.class.isAssignableFrom(o.getClass())) {
               this.invoke(locale, setter, attribute, model, this.unmarshallClass(locale, class0, (JSONObject)o));
            } else {
               if ("null".equals(o.toString())) {
                  o = null;
               }

               if (!this.isUnmodifiedConfidentialProperty(modelClass, name, o)) {
                  this.invoke(locale, setter, attribute, model, o);
               }
            }
         }
      }
   }

   private boolean isUnmodifiedConfidentialProperty(Class modelClass, String setterMethodName, Object value) {
      if (!(value instanceof String)) {
         return false;
      } else {
         String s = (String)value;
         if (!"@_Oracle_Confidential_Property_Set_V1.1_#".equals(s)) {
            return false;
         } else {
            String getterMethodName = "g" + setterMethodName.substring(1);
            return JsonUtil.isConfidentialProperty(modelClass, getterMethodName);
         }
      }
   }

   private Object processJsonArray(Locale locale, Type param0, JSONArray array) throws JSONException {
      boolean isArray = false;
      Object type;
      if (ParameterizedType.class.isAssignableFrom(param0.getClass())) {
         type = ((ParameterizedType)param0).getActualTypeArguments()[0];
      } else {
         isArray = ((Class)param0).isArray();
         type = ((Class)param0).getComponentType();
      }

      Object values = isArray ? Array.newInstance((Class)type, array.length()) : new ArrayList();

      for(int i = 0; i < array.length(); ++i) {
         Object element = array.get(i);
         if (JSONObject.class.isAssignableFrom(element.getClass())) {
            if (isArray) {
               Array.set(values, i, this.unmarshallClass(locale, (Class)type, (JSONObject)element));
            } else {
               ((List)values).add(this.unmarshallClass(locale, (Class)type, (JSONObject)element));
            }
         } else if (isArray) {
            Array.set(values, i, element);
         } else {
            ((List)values).add(element);
         }
      }

      return values;
   }

   private void invoke(Locale locale, Method m, String attribute, Object o, Object... args) {
      String message;
      try {
         m.invoke(o, args);
      } catch (IllegalArgumentException var8) {
         message = MessageUtil.formatter(locale).msgSetPropertyError(attribute, ExceptionUtil.getThrowableMessage(var8, false));
         throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(message).build());
      } catch (Exception var9) {
         message = MessageUtil.formatter(locale).msgSetPropertyError(attribute, ExceptionUtil.getThrowableMessage(var9, false));
         throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(message).build());
      }
   }

   public Set validateRestModel(Locale locale, Object model) {
      initBeanValidator();
      Set constraintViolations = getWebAppState().getBeanValidator().validate(model, new Class[0]);
      return constraintViolations != null && !constraintViolations.isEmpty() ? constraintViolations : Collections.EMPTY_SET;
   }

   public String getValidationFailureMessages(Locale locale, Set constraintViolations, Object model) {
      StringBuilder msg = new StringBuilder();
      msg.append(MessageUtil.formatter(locale).msgModelConstraintViolations(model.getClass().getSimpleName()));
      String sep = "";

      for(Iterator var6 = constraintViolations.iterator(); var6.hasNext(); sep = "\n") {
         ConstraintViolation cv = (ConstraintViolation)var6.next();
         String violation = MessageUtil.formatter(locale).msgPropertyConstraintViolation(cv.getPropertyPath().toString(), cv.getMessage());
         msg.append(sep).append(violation);
      }

      return msg.toString();
   }

   public Locale getLocale(MultivaluedMap requestHeaders) {
      String want = (String)requestHeaders.getFirst("Accept-Language");
      return want != null ? new Locale(want) : null;
   }

   private List getSetters(Class clazz) {
      List methods = new ArrayList();
      Method[] var3 = clazz.getMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method method = var3[var5];
         if (method.getName().startsWith("set")) {
            methods.add(method);
         }
      }

      return methods;
   }

   private void analyzeInterface(Class iface, Map properties) throws SecurityException {
      Method[] var3 = iface.getMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method method = var3[var5];
         String name = method.getName();
         boolean isGetter = name.startsWith("get");
         if (isGetter || name.startsWith("set")) {
            name = name.substring(3);
            Map property = (Map)properties.get(name);
            if (property == null) {
               property = new HashMap();
               properties.put(name, property);
            }

            Class type = isGetter ? method.getReturnType() : method.getParameterTypes()[0];
            ((Map)property).put("type", type);
         }
      }

   }

   private String getInternalTypeString(Class type) {
      return type.isPrimitive() ? Primitive.getPrimitive(type.getName()).getInternalType() : (type.isArray() ? this.getInternalName(type.getName()) : "L" + this.getInternalName(type.getName() + ";"));
   }

   private String getPropertyName(String name) {
      return name.substring(0, 1).toLowerCase(Locale.getDefault()) + name.substring(1);
   }

   private void visitClass(ClassWriter classWriter, String className, Set ifaces, Map properties) {
      String[] ifaceNames = new String[ifaces.size() + 1];
      int i = 1;
      ifaceNames[0] = this.getInternalName(RestModel.class.getName());

      Class iface;
      for(Iterator var7 = ifaces.iterator(); var7.hasNext(); ifaceNames[i++] = iface.getName().replace(".", "/")) {
         iface = (Class)var7.next();
      }

      className = this.getInternalName(className);
      classWriter.visit(50, 33, className, (String)null, "org/glassfish/admin/rest/composite/RestModelImpl", ifaceNames);
      classWriter.visitAnnotation("Ljavax/xml/bind/annotation/XmlRootElement;", true).visitEnd();
      AnnotationVisitor annotation = classWriter.visitAnnotation("Ljavax/xml/bind/annotation/XmlAccessorType;", true);
      annotation.visitEnum("value", "Ljavax/xml/bind/annotation/XmlAccessType;", "FIELD");
      annotation.visitEnd();
   }

   private void createConstructor(ClassWriter cw, String className, Map properties) {
      MethodVisitor method = cw.visitMethod(1, "<init>", "()V", (String)null, (String[])null);
      method.visitCode();
      method.visitVarInsn(25, 0);
      method.visitMethodInsn(183, "org/glassfish/admin/rest/composite/RestModelImpl", "<init>", "()V");
      Iterator var5 = properties.entrySet().iterator();

      while(var5.hasNext()) {
         Map.Entry property = (Map.Entry)var5.next();
         String fieldName = (String)property.getKey();
         String defaultValue = (String)((Map)property.getValue()).get("defaultValue");
         if (defaultValue != null && !defaultValue.isEmpty()) {
            this.setDefaultValue(method, className, fieldName, (Class)((Map)property.getValue()).get("type"), defaultValue);
         }
      }

      method.visitInsn(177);
      method.visitMaxs(1, 1);
      method.visitEnd();
   }

   private void setDefaultValue(MethodVisitor method, String className, String fieldName, Class fieldClass, String defaultValue) {
      String type = this.getInternalTypeString(fieldClass);
      Object value = defaultValue;
      fieldName = this.getPropertyName(fieldName);
      if (fieldClass.isPrimitive()) {
         switch (Primitive.getPrimitive(type)) {
            case SHORT:
               value = Short.valueOf(defaultValue);
               break;
            case LONG:
               value = Long.valueOf(defaultValue);
               break;
            case INT:
               value = Integer.valueOf(defaultValue);
               break;
            case FLOAT:
               value = Float.valueOf(defaultValue);
               break;
            case DOUBLE:
               value = Double.valueOf(defaultValue);
               break;
            case BYTE:
               value = Byte.valueOf(defaultValue);
               break;
            case BOOLEAN:
               value = Boolean.valueOf(defaultValue);
         }

         method.visitVarInsn(25, 0);
         method.visitLdcInsn(value);
         method.visitFieldInsn(181, this.getInternalName(className), fieldName, type);
      } else if (!fieldClass.equals(String.class)) {
         method.visitVarInsn(25, 0);
         String internalName = this.getInternalName(fieldClass.getName());
         method.visitTypeInsn(187, internalName);
         method.visitInsn(89);
         method.visitLdcInsn(defaultValue);
         method.visitMethodInsn(183, internalName, "<init>", "(Ljava/lang/String;)V");
         method.visitFieldInsn(181, this.getInternalName(className), fieldName, type);
      } else {
         method.visitVarInsn(25, 0);
         method.visitLdcInsn(defaultValue);
         method.visitFieldInsn(181, this.getInternalName(className), fieldName, type);
      }

   }

   private void createField(ClassWriter cw, String name, Class type) {
      String internalType = this.getInternalTypeString(type);
      FieldVisitor field = cw.visitField(2, this.getPropertyName(name), internalType, (String)null, (Object)null);
      field.visitAnnotation("Ljavax/xml/bind/annotation/XmlAttribute;", true).visitEnd();
      field.visitEnd();
   }

   private void createGettersAndSetters(ClassWriter cw, Class c, String className, String name, Map props) {
      Class type = (Class)props.get("type");
      String internalType = this.getInternalTypeString(type);
      className = this.getInternalName(className);
      MethodVisitor getter = cw.visitMethod(1, "get" + name, "()" + internalType, (String)null, (String[])null);
      getter.visitCode();
      getter.visitVarInsn(25, 0);
      getter.visitFieldInsn(180, className, this.getPropertyName(name), internalType);
      getter.visitInsn(type.isPrimitive() ? Primitive.getPrimitive(internalType).getReturnOpcode() : 176);
      getter.visitMaxs(0, 0);
      getter.visitEnd();
      Map annotations = (Map)props.get("annotations");
      AnnotationVisitor av;
      if (annotations != null) {
         label43:
         for(Iterator var10 = annotations.entrySet().iterator(); var10.hasNext(); av.visitEnd()) {
            Map.Entry entry = (Map.Entry)var10.next();
            String annotationClass = (String)entry.getKey();
            Map annotationValues = (Map)entry.getValue();
            av = getter.visitAnnotation("L" + this.getInternalName(annotationClass) + ";", true);
            Iterator var15 = annotationValues.entrySet().iterator();

            while(true) {
               String paramName;
               Object paramValue;
               do {
                  if (!var15.hasNext()) {
                     continue label43;
                  }

                  Map.Entry values = (Map.Entry)var15.next();
                  paramName = (String)values.getKey();
                  paramValue = values.getValue();
                  if (Class.class.isAssignableFrom(paramValue.getClass())) {
                     paramValue = org.objectweb.asm.Type.getType("L" + this.getInternalName(paramValue.getClass().getName()) + ";");
                  }
               } while(paramValue.getClass().isArray() && Array.getLength(paramValue) == 0);

               av.visit(paramName, paramValue);
            }
         }
      }

      MethodVisitor setter = cw.visitMethod(1, "set" + name, "(" + internalType + ")V", (String)null, (String[])null);
      setter.visitCode();
      setter.visitVarInsn(25, 0);
      setter.visitVarInsn(type.isPrimitive() ? Primitive.getPrimitive(internalType).getSetOpCode() : 25, 1);
      setter.visitFieldInsn(181, className, this.getPropertyName(name), internalType);
      setter.visitVarInsn(25, 0);
      setter.visitLdcInsn(name);
      setter.visitMethodInsn(182, className, "fieldSet", "(Ljava/lang/String;)V");
      setter.visitInsn(177);
      setter.visitMaxs(0, 0);
      setter.visitEnd();
   }

   private String getInternalName(String className) {
      return className.replace(".", "/");
   }

   private Class defineClass(Class similarClass, String className, byte[] classBytes) throws Exception {
      byte[] byteContent = classBytes;
      ProtectionDomain pd = similarClass.getProtectionDomain();
      Method jm = null;
      Method[] var7 = ClassLoader.class.getDeclaredMethods();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Method jm2 = var7[var9];
         if (jm2.getName().equals("defineClass") && jm2.getParameterTypes().length == 5) {
            jm = jm2;
            break;
         }
      }

      if (jm == null) {
         throw new RuntimeException("cannot find method called defineclass...");
      } else {
         final Method clM = jm;

         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  if (!clM.isAccessible()) {
                     clM.setAccessible(true);
                  }

                  return null;
               }
            });
            ClassLoader classLoader = similarClass.getClassLoader();

            try {
               clM.invoke(classLoader, className, byteContent, 0, byteContent.length, pd);
            } catch (Exception var12) {
               throw var12 instanceof RuntimeException ? (RuntimeException)var12 : new RuntimeException(var12);
            }

            try {
               return classLoader.loadClass(className);
            } catch (ClassNotFoundException var11) {
               throw new RuntimeException(var11);
            }
         } catch (Exception var13) {
            throw var13 instanceof RuntimeException ? (RuntimeException)var13 : new RuntimeException(var13);
         }
      }
   }

   private static synchronized void initBeanValidator() {
      WebAppState state = getWebAppState();
      if (state.getBeanValidator() == null) {
         ClassLoader cl = System.getSecurityManager() == null ? Thread.currentThread().getContextClassLoader() : (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
            public ClassLoader run() {
               return Thread.currentThread().getContextClassLoader();
            }
         });

         try {
            Thread.currentThread().setContextClassLoader(Validation.class.getClassLoader());
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            ValidatorContext validatorContext = validatorFactory.usingContext();
            state.setBeanValidator(validatorContext.getValidator());
         } finally {
            Thread.currentThread().setContextClassLoader(cl);
         }

      }
   }

   private static synchronized WebAppState getWebAppState() {
      String webAppId = getThreadWebAppId();
      WebAppState state = (WebAppState)webAppToState.get(webAppId);
      if (state == null) {
         state = new WebAppState();
         webAppToState.put(webAppId, state);
      }

      return state;
   }

   public static synchronized void removeWebApp() {
      webAppToState.remove(getThreadWebAppId());
   }

   private static String getThreadWebAppId() {
      return WebAppUtil.getThreadWebAppId();
   }

   // $FF: synthetic method
   CompositeUtil(Object x0) {
      this();
   }

   private static class WebAppState {
      private final Map generatedClasses;
      private volatile Validator beanValidator;

      private WebAppState() {
         this.generatedClasses = new HashMap();
         this.beanValidator = null;
      }

      private Map getGeneratedClasses() {
         return this.generatedClasses;
      }

      private Validator getBeanValidator() {
         return this.beanValidator;
      }

      private void setBeanValidator(Validator val) {
         this.beanValidator = val;
      }

      // $FF: synthetic method
      WebAppState(Object x0) {
         this();
      }
   }

   private static class LazyHolder {
      public static final CompositeUtil INSTANCE = new CompositeUtil();
   }
}
