package weblogic.servlet.jsp;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class BeanUtils {
   static void p(String s) {
      System.out.println(s);
   }

   public static boolean canBeInstantiated(Class c) {
      int mods = c.getModifiers();
      if ((mods & 1024) == 1024) {
         return false;
      } else if ((mods & 1) != 1) {
         return false;
      } else {
         Class[] NO_ARGS = new Class[0];
         Constructor cons = null;

         try {
            cons = c.getConstructor(NO_ARGS);
         } catch (NoSuchMethodException var5) {
            return false;
         } catch (SecurityException var6) {
            return false;
         }

         mods = cons.getModifiers();
         return (mods & 1) == 1;
      }
   }

   public static String doReflection(String name, String type, Properties params) throws IOException {
      try {
         return doReflection0(name, type, params);
      } catch (RuntimeException var4) {
         throw var4;
      } catch (IOException var5) {
         throw var5;
      } catch (Exception var6) {
         throw new IOException("reflection failure: " + var6.toString());
      }
   }

   public static boolean isStringConvertible(Class propertyType) {
      return propertyType == String.class || propertyType == Boolean.class || propertyType == Boolean.TYPE || propertyType == Byte.class || propertyType == Byte.TYPE || propertyType == Double.class || propertyType == Double.TYPE || propertyType == Integer.class || propertyType == Integer.TYPE || propertyType == Float.class || propertyType == Float.TYPE || propertyType == Long.class || propertyType == Long.TYPE || propertyType == Character.class || propertyType == Character.TYPE || propertyType == Object.class || propertyType == Short.class || propertyType == Short.TYPE;
   }

   public static String complexConversion(Class propType, String propStr) {
      PropertyEditor ed = PropertyEditorManager.findEditor(propType);
      if (ed == null) {
         return null;
      } else {
         ed.setAsText(propStr);
         return ed.getJavaInitializationString();
      }
   }

   public static String complexConversion0(Class propType, String propStr) {
      PropertyEditor ed = PropertyEditorManager.findEditor(propType);
      return ed == null ? null : ed.getClass().getName();
   }

   public static void doComplex(Object bean, String prop, String value, ServletRequest req, String param) throws javax.servlet.jsp.JspException {
      if (param == null) {
         param = prop;
      }

      BeanInfo bi = null;

      try {
         bi = Introspector.getBeanInfo(bean.getClass());
      } catch (Exception var14) {
         throw new JspException("Exception occured while getting the beanInfo for bean '" + bean.getClass() + "'");
      }

      PropertyDescriptor[] pds = bi.getPropertyDescriptors();
      PropertyDescriptor pd = null;

      int i;
      for(i = 0; i < pds.length; ++i) {
         if (prop.equals(pds[i].getName())) {
            pd = pds[i];
            break;
         }
      }

      if (pd == null) {
         for(i = 0; i < pds.length; ++i) {
            if (prop.equalsIgnoreCase(pds[i].getName())) {
               pd = pds[i];
               break;
            }
         }
      }

      if (pd != null) {
         try {
            Class pEditorClass = pd.getPropertyEditorClass();
            Class type = pd.getPropertyType();
            PropertyEditor pe;
            if (pEditorClass == null) {
               Class cType = type.isArray() ? type.getComponentType() : type;
               pe = PropertyEditorManager.findEditor(cType);
               if (pe != null) {
                  pEditorClass = pe.getClass();
               }
            }

            Method method = pd.getWriteMethod();
            pe = null;
            if (type.isArray()) {
               if (req == null) {
                  throw new JspException("request is needed for the type '" + type.getName() + "'");
               }

               String[] val = ((HttpServletRequest)req).getParameterValues(param);
               if (val == null || val.length == 0) {
                  return;
               }

               Object[] values = (Object[])((Object[])Array.newInstance(type.getComponentType(), val.length));

               for(int i = 0; i < val.length; ++i) {
                  values[i] = getValueFromEditor(pEditorClass, pd, val[i]);
               }

               if (method != null) {
                  method.invoke(bean, (Object)values);
               }
            } else {
               if (value == null && req != null) {
                  value = ((HttpServletRequest)req).getParameter(param);
               }

               if (value == null) {
                  return;
               }

               Object converted = getValueFromEditor(pEditorClass, pd, value);
               if (method != null) {
                  method.invoke(bean, converted);
               }
            }

         } catch (RuntimeException var15) {
            throw new javax.servlet.jsp.JspException(var15.toString(), var15);
         } catch (Exception var16) {
            throw new javax.servlet.jsp.JspException("Exception occured while conversion :" + var16.toString());
         }
      } else {
         throw new javax.servlet.jsp.JspException("Found no PropertyDescriptor for property '" + prop + "' of bean class '" + bean.getClass().getName() + "'");
      }
   }

   public static Object getValueFromEditor(Class editor, PropertyDescriptor pd, String value) throws JspException {
      try {
         PropertyEditor pEdit = (PropertyEditor)editor.newInstance();
         pEdit.setAsText(value);
         return pEdit.getValue();
      } catch (Exception var4) {
         throw new JspException("unable to convert '" + value + "' to type '" + pd.getPropertyType().getName() + "'. reason: " + var4.toString());
      }
   }

   public static String convert(Class propertyType, String value) {
      if (propertyType == String.class) {
         return "weblogic.utils.StringUtils.valueOf(" + value + ")";
      } else if (propertyType == Boolean.class) {
         return "Boolean.valueOf(weblogic.utils.StringUtils.valueOf(" + value + "))";
      } else if (propertyType == Boolean.TYPE) {
         return "Boolean.valueOf(weblogic.utils.StringUtils.valueOf(" + value + ")).booleanValue()";
      } else if (propertyType == Byte.class) {
         return "Byte.valueOf(weblogic.utils.StringUtils.valueOf(" + value + "))";
      } else if (propertyType == Byte.TYPE) {
         return "Byte.valueOf(weblogic.utils.StringUtils.valueOf(" + value + ")).byteValue()";
      } else if (propertyType == Double.class) {
         return "Double.valueOf(weblogic.utils.StringUtils.valueOf(" + value + "))";
      } else if (propertyType == Double.TYPE) {
         return "Double.valueOf(weblogic.utils.StringUtils.valueOf(" + value + ")).doubleValue()";
      } else if (propertyType == Integer.class) {
         return "Integer.valueOf(weblogic.utils.StringUtils.valueOf(" + value + "))";
      } else if (propertyType == Integer.TYPE) {
         return "Integer.valueOf(weblogic.utils.StringUtils.valueOf(" + value + ")).intValue()";
      } else if (propertyType == Float.class) {
         return "Float.valueOf(weblogic.utils.StringUtils.valueOf(" + value + "))";
      } else if (propertyType == Float.TYPE) {
         return "Float.valueOf(weblogic.utils.StringUtils.valueOf(" + value + ")).floatValue()";
      } else if (propertyType == Long.class) {
         return "Long.valueOf(weblogic.utils.StringUtils.valueOf(" + value + "))";
      } else if (propertyType == Long.TYPE) {
         return "Long.valueOf(weblogic.utils.StringUtils.valueOf(" + value + ")).longValue()";
      } else if (propertyType == Character.class) {
         return "new Character(" + value + ".toString().length() > 0 ? " + value + ".toString().charAt(0): ' ')";
      } else if (propertyType == Character.TYPE) {
         return value + ".toString().length() > 0 ? " + value + ".toString().charAt(0): ' '";
      } else if (propertyType == Object.class) {
         return "weblogic.utils.StringUtils.valueOf(" + value + ")";
      } else if (propertyType == Short.TYPE) {
         return "Short.valueOf(weblogic.utils.StringUtils.valueOf(" + value + ")).shortValue()";
      } else {
         return propertyType == Short.class ? "Short.valueOf(weblogic.utils.StringUtils.valueOf(" + value + "))" : null;
      }
   }

   public static String convertArray(Class propertyType, String size) {
      if (propertyType == String.class) {
         return "String[] _propertyArray = new String[" + size + "];";
      } else if (propertyType == Boolean.class) {
         return "Boolean[] _propertyArray = new Boolean[" + size + "];";
      } else if (propertyType == Boolean.TYPE) {
         return "boolean[] _propertyArray = new boolean[" + size + "];";
      } else if (propertyType == Byte.class) {
         return "Byte[] _propertyArray = new Byte[" + size + "];";
      } else if (propertyType == Byte.TYPE) {
         return "byte[] _propertyArray = new byte[" + size + "];";
      } else if (propertyType == Double.class) {
         return "Double[] _propertyArray = new Double[" + size + "];";
      } else if (propertyType == Double.TYPE) {
         return "double[] _propertyArray = new double[" + size + "];";
      } else if (propertyType == Integer.class) {
         return "Integer[] _propertyArray = new Integer[" + size + "];";
      } else if (propertyType == Integer.TYPE) {
         return "int[] _propertyArray = new int[" + size + "];";
      } else if (propertyType == Float.class) {
         return "Float[] _propertyArray = new Float[" + size + "];";
      } else if (propertyType == Float.TYPE) {
         return "float[] _propertyArray = new float[" + size + "];";
      } else if (propertyType == Long.class) {
         return "Long[] _propertyArray = new Long[" + size + "];";
      } else if (propertyType == Long.TYPE) {
         return "long[] _propertyArray = new long[" + size + "];";
      } else if (propertyType == Character.class) {
         return "Character[] _propertyArray = new Character[" + size + "];";
      } else if (propertyType == Character.TYPE) {
         return "char[] _propertyArray = new char[" + size + "];";
      } else if (propertyType == Object.class) {
         return "Object[] _propertyArray = new Object[" + size + "];";
      } else if (propertyType == Short.TYPE) {
         return "short[] _propertyArray = new short[" + size + "];";
      } else {
         return propertyType == Short.class ? "Short[] _propertyArray = new Short[" + size + "];" : null;
      }
   }

   public static String doReflection0(String name, String type, Properties params) throws Exception {
      Vector v = new Vector();
      Class c = Class.forName(type);
      v.addElement(c);
      recurseClasses(c, v);
      Hashtable m = new Hashtable();
      getMethods(v, m);
      StringBuffer code = new StringBuffer();
      Enumeration enum_ = params.keys();
      int numSetCalls = 0;

      while(true) {
         String paramVal;
         MethodEntry me;
         Class paramType;
         do {
            do {
               String paramName;
               do {
                  do {
                     if (!enum_.hasMoreElements()) {
                        if (numSetCalls == 0) {
                           return null;
                        }

                        return code.toString();
                     }

                     paramName = (String)enum_.nextElement();
                     paramVal = params.getProperty(paramName);
                  } while(paramVal == null);
               } while(paramVal.length() == 0);

               paramName = paramName.toLowerCase();
               me = (MethodEntry)m.get(paramName);
            } while(me == null);

            paramType = me.paramTypes[0];
         } while(!paramType.isPrimitive() && "java.lang.String".getClass() != paramType);

         String arg = paramVal;

         try {
            if ("java.lang.String".getClass() == paramType) {
               arg = '"' + paramVal + '"';
            } else if (paramType == Byte.TYPE) {
               Byte.decode(paramVal);
            } else if (paramType == Short.TYPE) {
               Short.parseShort(paramVal);
            } else if (paramType == Integer.TYPE) {
               Integer.parseInt(paramVal);
            } else if (paramType == Long.TYPE) {
               Long.parseLong(paramVal);
            } else if (paramType == Float.TYPE) {
               Float.valueOf(paramVal);
            } else if (paramType == Double.TYPE) {
               Double.valueOf(paramVal);
            } else if (paramType == Boolean.TYPE) {
               if (!"true".equalsIgnoreCase(arg) && !"yes".equalsIgnoreCase(arg) && !"1".equalsIgnoreCase(arg) && !"on".equalsIgnoreCase(arg)) {
                  if (!"false".equalsIgnoreCase(arg) && !"no".equalsIgnoreCase(arg) && !"0".equalsIgnoreCase(arg) && !"off".equalsIgnoreCase(arg) && !"null".equalsIgnoreCase(arg)) {
                     throw new IllegalArgumentException("cannot convert String '" + arg + "' into a boolean");
                  }

                  arg = "false";
               } else {
                  arg = "true";
               }
            } else if (paramType == Character.TYPE) {
               arg = '\'' + paramVal + '\'';
            }
         } catch (NumberFormatException var15) {
            throw new IllegalArgumentException("cannot convert String '" + paramVal + "' into a " + paramType.getName());
         }

         code.append(name);
         code.append('.');
         code.append(me.m.getName());
         code.append('(');
         code.append(arg);
         code.append(')');
         code.append(';');
         code.append('\n');
         ++numSetCalls;
      }
   }

   public static boolean implementsInterface(Class c, String iName) throws Exception {
      Vector v = new Vector();
      v.addElement(c);
      recurseClasses(c, v);

      for(int i = 0; i < v.size(); ++i) {
         c = (Class)v.elementAt(i);
         Class[] ints = c.getInterfaces();
         if (ints != null) {
            for(int j = 0; j < ints.length; ++j) {
               if (iName.equals(ints[j].getName())) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private static void getMethods(Vector classes, Hashtable mhash) throws Exception {
      for(int i = 0; i < classes.size(); ++i) {
         Class c = (Class)classes.elementAt(i);
         Method[] methods = c.getDeclaredMethods();
         if (methods != null) {
            for(int j = 0; j < methods.length; ++j) {
               Method m = methods[j];
               String name = m.getName();
               if (!Modifier.isStatic(m.getModifiers()) && name.startsWith("set") && name.length() > 3) {
                  Class[] params = m.getParameterTypes();
                  if (params != null && params.length == 1) {
                     String paramPart = name.substring(3).toLowerCase();
                     MethodEntry me = new MethodEntry();
                     me.m = m;
                     me.paramTypes = params;
                     me.paramPart = paramPart;
                     mhash.put(paramPart, me);
                  }
               }
            }
         }
      }

   }

   private static void recurseClasses(Class c, Vector v) throws Exception {
      Class csuper = c.getSuperclass();
      if (!csuper.getName().equals("java.lang.Object")) {
         v.addElement(csuper);
         recurseClasses(csuper, v);
      }
   }
}
