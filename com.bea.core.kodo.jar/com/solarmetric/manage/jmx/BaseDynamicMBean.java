package com.solarmetric.manage.jmx;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.ReflectionException;
import javax.management.RuntimeErrorException;
import javax.management.RuntimeMBeanException;
import org.apache.openjpa.lib.util.Localizer;

public abstract class BaseDynamicMBean implements DynamicMBean {
   private static final Localizer _loc = Localizer.forPackage(BaseDynamicMBean.class);
   private MBeanInfo info;
   private Object resource;

   protected BaseDynamicMBean() {
   }

   public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
      if (attribute == null) {
         throw new AttributeNotFoundException(_loc.get("attr-null").getMessage());
      } else {
         MBeanInfo info = this.getMBeanInfo();
         MBeanAttributeInfo[] attrs = info.getAttributes();
         if (attrs != null && attrs.length != 0) {
            for(int i = 0; i < attrs.length; ++i) {
               MBeanAttributeInfo attr = attrs[i];
               if (attr != null && attribute.equals(attr.getName())) {
                  if (!attr.isReadable()) {
                     throw new ReflectionException(new NoSuchMethodException(_loc.get("no-getter", attribute).getMessage()));
                  }

                  String prefix = null;
                  if (attr.isIs()) {
                     prefix = "is";
                  } else {
                     prefix = "get";
                  }

                  try {
                     return this.invoke(prefix + attr.getName(), new Class[0], new Object[0]);
                  } catch (InvalidAttributeValueException var8) {
                     throw new ReflectionException(var8);
                  }
               }
            }

            throw new AttributeNotFoundException(_loc.get("no-attr", attribute).getMessage());
         } else {
            throw new AttributeNotFoundException(_loc.get("no-attrs").getMessage());
         }
      }
   }

   public AttributeList getAttributes(String[] attributes) {
      AttributeList list = new AttributeList();
      if (attributes != null) {
         for(int i = 0; i < attributes.length; ++i) {
            String attribute = attributes[i];

            try {
               Object result = this.getAttribute(attribute);
               list.add(new Attribute(attribute, result));
            } catch (AttributeNotFoundException var6) {
            } catch (MBeanException var7) {
            } catch (ReflectionException var8) {
            }
         }
      }

      return list;
   }

   public synchronized MBeanInfo getMBeanInfo() {
      if (this.info == null) {
         this.setMBeanInfo(this.createMBeanInfo());
      }

      return this.info;
   }

   public Object invoke(String method, Object[] arguments, String[] params) throws MBeanException, ReflectionException {
      if (method == null) {
         throw new IllegalArgumentException(_loc.get("null-method").getMessage());
      } else {
         if (arguments == null) {
            arguments = new Object[0];
         }

         if (params == null) {
            params = new String[0];
         }

         MBeanInfo info = this.getMBeanInfo();
         MBeanOperationInfo[] opers = info.getOperations();
         if (opers != null && opers.length != 0) {
            for(int i = 0; i < opers.length; ++i) {
               MBeanOperationInfo oper = opers[i];
               if (oper != null && method.equals(oper.getName())) {
                  MBeanParameterInfo[] parameters = oper.getSignature();
                  if (params.length == parameters.length) {
                     String[] signature = new String[parameters.length];

                     for(int j = 0; j < signature.length; ++j) {
                        MBeanParameterInfo param = parameters[j];
                        if (param == null) {
                           signature[j] = null;
                        } else {
                           signature[j] = param.getType();
                        }
                     }

                     if (Arrays.equals(params, signature)) {
                        try {
                           Class[] classes = load(this.getClass().getClassLoader(), signature);
                           return this.invoke(method, classes, arguments);
                        } catch (ClassNotFoundException var12) {
                           throw new ReflectionException(var12);
                        } catch (InvalidAttributeValueException var13) {
                           throw new ReflectionException(var13);
                        }
                     }
                  }
               }
            }

            throw new ReflectionException(new NoSuchMethodException(_loc.get("op-not-defined", method, Arrays.asList(params)).getMessage()));
         } else {
            throw new ReflectionException(new NoSuchMethodException(_loc.get("no-operations").getMessage()));
         }
      }
   }

   public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
      if (attribute == null) {
         throw new AttributeNotFoundException(_loc.get("attr-null").getMessage());
      } else {
         MBeanInfo info = this.getMBeanInfo();
         MBeanAttributeInfo[] attrs = info.getAttributes();
         if (attrs != null && attrs.length != 0) {
            for(int i = 0; i < attrs.length; ++i) {
               MBeanAttributeInfo attr = attrs[i];
               if (attr != null && attribute.getName().equals(attr.getName())) {
                  if (!attr.isWritable()) {
                     throw new ReflectionException(new NoSuchMethodException(_loc.get("no-setter", attribute).getMessage()));
                  }

                  try {
                     String signature = attr.getType();
                     Class cls = load(this.getClass().getClassLoader(), signature);
                     this.invoke("set" + attr.getName(), new Class[]{cls}, new Object[]{attribute.getValue()});
                     return;
                  } catch (ClassNotFoundException var8) {
                     throw new ReflectionException(var8);
                  }
               }
            }

            throw new AttributeNotFoundException(_loc.get("no-attr", attribute).getMessage());
         } else {
            throw new AttributeNotFoundException(_loc.get("no-attrs").getMessage());
         }
      }
   }

   public AttributeList setAttributes(AttributeList attributes) {
      AttributeList list = new AttributeList();
      if (attributes != null) {
         for(int i = 0; i < attributes.size(); ++i) {
            Attribute attribute = (Attribute)attributes.get(i);

            try {
               this.setAttribute(attribute);
               list.add(attribute);
            } catch (AttributeNotFoundException var6) {
            } catch (InvalidAttributeValueException var7) {
            } catch (MBeanException var8) {
            } catch (ReflectionException var9) {
            }
         }
      }

      return list;
   }

   protected Object invoke(String name, Class[] params, Object[] args) throws InvalidAttributeValueException, MBeanException, ReflectionException {
      try {
         Object resource = this.getResource();
         if (resource == null) {
            resource = this;
         }

         Class cls = resource.getClass();
         Method method = this.findMethod(cls, name, params);
         return this.invokeMethod(method, resource, args);
      } catch (NoSuchMethodException var7) {
         throw new ReflectionException(var7);
      } catch (IllegalAccessException var8) {
         throw new ReflectionException(var8);
      } catch (IllegalArgumentException var9) {
         throw new InvalidAttributeValueException(var9.toString());
      } catch (InvocationTargetException var10) {
         Throwable t = var10.getTargetException();
         if (t instanceof RuntimeException) {
            throw new RuntimeMBeanException((RuntimeException)t);
         } else if (t instanceof Exception) {
            throw new MBeanException((Exception)t);
         } else {
            throw new RuntimeErrorException((Error)t);
         }
      }
   }

   protected Method findMethod(Class cls, String name, Class[] params) throws NoSuchMethodException {
      return cls.getMethod(name, params);
   }

   protected Object invokeMethod(Method method, Object resource, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return method.invoke(resource, args);
   }

   protected Object getResource() {
      return this.resource;
   }

   public void setResource(Object resource) {
      this.resource = resource;
   }

   protected void setMBeanInfo(MBeanInfo info) {
      this.info = info;
   }

   protected MBeanInfo createMBeanInfo() {
      MBeanAttributeInfo[] attrs = this.createMBeanAttributeInfo();
      MBeanConstructorInfo[] ctors = this.createMBeanConstructorInfo();
      MBeanOperationInfo[] opers = this.createMBeanOperationInfo();
      MBeanNotificationInfo[] notifs = this.createMBeanNotificationInfo();
      String className = this.getMBeanClassName();
      String description = this.getMBeanDescription();
      return new MBeanInfo(className, description, attrs, ctors, opers, notifs);
   }

   protected MBeanAttributeInfo[] createMBeanAttributeInfo() {
      return new MBeanAttributeInfo[0];
   }

   protected MBeanConstructorInfo[] createMBeanConstructorInfo() {
      return new MBeanConstructorInfo[0];
   }

   protected MBeanOperationInfo[] createMBeanOperationInfo() {
      return new MBeanOperationInfo[0];
   }

   protected MBeanNotificationInfo[] createMBeanNotificationInfo() {
      return new MBeanNotificationInfo[0];
   }

   protected String getMBeanClassName() {
      return this.getClass().getName();
   }

   protected String getMBeanDescription() {
      return null;
   }

   private static Class[] load(ClassLoader loader, String[] names) throws ClassNotFoundException {
      int n = names.length;
      Class[] cls = new Class[n];

      for(int i = 0; i < n; ++i) {
         cls[i] = load(loader, names[i]);
      }

      return cls;
   }

   private static Class load(ClassLoader loader, String name) throws ClassNotFoundException {
      if (name == null) {
         throw new ClassNotFoundException("null");
      } else {
         name = name.trim();
         if (name.equals(Boolean.TYPE.getName())) {
            return Boolean.TYPE;
         } else if (name.equals(Byte.TYPE.getName())) {
            return Byte.TYPE;
         } else if (name.equals(Character.TYPE.getName())) {
            return Character.TYPE;
         } else if (name.equals(Short.TYPE.getName())) {
            return Short.TYPE;
         } else if (name.equals(Integer.TYPE.getName())) {
            return Integer.TYPE;
         } else if (name.equals(Long.TYPE.getName())) {
            return Long.TYPE;
         } else if (name.equals(Float.TYPE.getName())) {
            return Float.TYPE;
         } else if (name.equals(Double.TYPE.getName())) {
            return Double.TYPE;
         } else if (name.equals(String.class.getName())) {
            return String.class;
         } else if (name.equals(Object.class.getName())) {
            return Object.class;
         } else if (!name.startsWith("[")) {
            return loader.loadClass(name);
         } else {
            int dimension;
            for(dimension = 0; name.charAt(dimension) == '['; ++dimension) {
            }

            char type = name.charAt(dimension);
            Class cls = null;
            switch (type) {
               case 'B':
                  cls = Byte.TYPE;
                  break;
               case 'C':
                  cls = Character.TYPE;
                  break;
               case 'D':
                  cls = Double.TYPE;
               case 'E':
               case 'G':
               case 'H':
               case 'K':
               case 'M':
               case 'N':
               case 'O':
               case 'P':
               case 'Q':
               case 'R':
               case 'T':
               case 'U':
               case 'V':
               case 'W':
               case 'X':
               case 'Y':
               default:
                  break;
               case 'F':
                  cls = Float.TYPE;
                  break;
               case 'I':
                  cls = Integer.TYPE;
                  break;
               case 'J':
                  cls = Long.TYPE;
                  break;
               case 'L':
                  cls = load(loader, name.substring(dimension + 1, name.length() - 1));
                  break;
               case 'S':
                  cls = Short.TYPE;
                  break;
               case 'Z':
                  cls = Boolean.TYPE;
            }

            if (cls == null) {
               throw new ClassNotFoundException(name);
            } else {
               return Array.newInstance(cls, new int[dimension]).getClass();
            }
         }
      }
   }
}
