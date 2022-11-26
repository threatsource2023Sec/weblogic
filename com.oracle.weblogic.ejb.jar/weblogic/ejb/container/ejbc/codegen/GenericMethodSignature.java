package weblogic.ejb.container.ejbc.codegen;

import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.utils.PlatformConstants;

public final class GenericMethodSignature implements PlatformConstants {
   private Method method;
   private List paramterizingHierarchy;

   public GenericMethodSignature(Method m, Class pClass) {
      this.setMethod(m);
      if (pClass == null) {
         this.paramterizingHierarchy = null;
      } else {
         this.paramterizingHierarchy = this.getMethodDeclareHierarchy(pClass, m);
      }

   }

   public GenericMethodSignature(Method m) {
      this.setMethod(m);
      this.paramterizingHierarchy = null;
   }

   public Method getMethod() {
      return this.method;
   }

   public void setMethod(Method m) {
      this.method = m;
   }

   private List getMethodDeclareHierarchy(Type current, Method m) {
      if (!hasMethodDeclared(current, m)) {
         return null;
      } else {
         ArrayList hierarchy = new ArrayList();
         hierarchy.add(current);
         Class c = getClassFromTypeIfPossible(current);
         Type[] mixSupers = null;

         do {
            if (c == null) {
               return hierarchy;
            }

            if (c.isInterface()) {
               mixSupers = c.getGenericInterfaces();
            } else {
               Type[] superIs = c.getGenericInterfaces();
               mixSupers = new Type[superIs.length + 1];
               mixSupers[0] = c.getGenericSuperclass();

               for(int i = 1; i < superIs.length + 1; ++i) {
                  mixSupers[i] = superIs[i - 1];
               }
            }

            if (mixSupers == null) {
               return hierarchy;
            }

            for(int j = 0; j < mixSupers.length; ++j) {
               c = getClassFromTypeIfPossible(mixSupers[j]);
               if (hasMethodDeclared(mixSupers[j], m)) {
                  if (m.getDeclaringClass().equals(c) && !(mixSupers[j] instanceof ParameterizedType)) {
                     return null;
                  }

                  if (m.getDeclaringClass().equals(c)) {
                     hierarchy.add(mixSupers[j]);
                     return hierarchy;
                  }

                  hierarchy.add(mixSupers[j]);
                  break;
               }
            }
         } while(mixSupers.length > 0);

         return hierarchy;
      }
   }

   public static String getParameterizationForTypeVariables(Class c) {
      StringBuffer buf = new StringBuffer();
      TypeVariable[] tvs = c.getTypeParameters();
      if (tvs.length == 0) {
         return buf.toString();
      } else {
         GenericMethodSignature helper = new GenericMethodSignature((Method)null, c);
         buf.append("<");
         TypeVariable[] var4 = tvs;
         int var5 = tvs.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            TypeVariable tv = var4[var6];
            Type[] bounds = tv.getBounds();
            if (bounds[0] instanceof Class) {
               buf.append(getSimpleClassNameForType(bounds[0]));
            } else {
               buf.append(helper.getParameterizedName(bounds[0]));
            }

            buf.append(", ");
         }

         buf.delete(buf.lastIndexOf(", "), buf.length());
         buf.append(">");
         return buf.toString();
      }
   }

   public String getRealNameForTypeVariable(Type t, boolean appendBounds) {
      if (!(t instanceof TypeVariable)) {
         return null;
      } else {
         TypeVariable tv = (TypeVariable)t;
         StringBuffer nameBuffer = new StringBuffer();
         Type[] bounds = tv.getBounds();
         if (isDelcaredInTypes(tv, this.method.getTypeParameters())) {
            if (bounds.length <= 1 && bounds[0] instanceof Class && ((Class)bounds[0]).equals(Object.class)) {
               return getSimpleClassNameForType(tv);
            } else {
               nameBuffer.append(getSimpleClassNameForType(tv));
               if (appendBounds) {
                  nameBuffer.append(" extends ");

                  for(int i = 0; i < bounds.length; ++i) {
                     if (i > 0) {
                        nameBuffer.append(" & ");
                     }

                     nameBuffer.append(this.getParameterizedName(bounds[i]));
                  }
               }

               return nameBuffer.toString();
            }
         } else {
            Type mappedType = this.getMappedTypeForTypeVariable(tv, this.paramterizingHierarchy);
            if (mappedType == null) {
               int var8 = bounds.length;
               byte var9 = 0;
               if (var9 < var8) {
                  Type bound = bounds[var9];
                  nameBuffer.append(getSimpleClassNameForType(bound));
               }

               return nameBuffer.toString();
            } else {
               return this.getParameterizedName(mappedType);
            }
         }
      }
   }

   public String getRealNameForWildcardType(Type t) {
      if (!(t instanceof WildcardType)) {
         return null;
      } else {
         StringBuffer nameBuffer = new StringBuffer();
         if (t.toString().equals("?")) {
            nameBuffer.append("?");
         } else {
            Type[] upper = ((WildcardType)t).getUpperBounds();
            Type[] lower = ((WildcardType)t).getLowerBounds();
            int var6;
            byte var7;
            Type type;
            if (lower.length == 0) {
               nameBuffer.append("? extends ");
               var6 = upper.length;
               var7 = 0;
               if (var7 < var6) {
                  type = upper[var7];
                  nameBuffer.append(this.getParameterizedName(type));
               }
            } else {
               nameBuffer.append("? super ");
               var6 = lower.length;
               var7 = 0;
               if (var7 < var6) {
                  type = lower[var7];
                  nameBuffer.append(this.getParameterizedName(type));
               }
            }
         }

         return nameBuffer.toString();
      }
   }

   public String getRealNameForParameterizedType(Type t) {
      if (!(t instanceof ParameterizedType)) {
         return null;
      } else {
         StringBuffer nameBuffer = new StringBuffer();
         Type rawType = ((ParameterizedType)t).getRawType();
         if (rawType instanceof Class) {
            nameBuffer.append(getSimpleClassNameForType(rawType) + "<");
         }

         Type[] components = ((ParameterizedType)t).getActualTypeArguments();

         for(int i = 0; i < components.length; ++i) {
            if (components[i] instanceof Class) {
               nameBuffer.append(getSimpleClassNameForType(components[i]));
            } else if (components[i] instanceof TypeVariable) {
               nameBuffer.append(this.getRealNameForTypeVariable(components[i], false));
            } else if (components[i] instanceof ParameterizedType) {
               nameBuffer.append(this.getRealNameForParameterizedType(components[i]));
            } else if (components[i] instanceof WildcardType) {
               nameBuffer.append(this.getRealNameForWildcardType(components[i]));
            } else if (components[i] instanceof GenericArrayType) {
               nameBuffer.append(this.getParameterizedName(components[i]));
            }

            if (i < components.length - 1) {
               nameBuffer.append(",");
            }
         }

         nameBuffer.append(">");
         return nameBuffer.toString();
      }
   }

   public String getParameterizedName(Type t) {
      if (t == null) {
         return null;
      } else if (t instanceof Class) {
         return getSimpleClassNameForType(t);
      } else if (t instanceof TypeVariable) {
         return this.getRealNameForTypeVariable(t, false);
      } else if (t instanceof ParameterizedType) {
         return this.getRealNameForParameterizedType(t);
      } else {
         return t instanceof GenericArrayType ? this.getParameterizedName(((GenericArrayType)t).getGenericComponentType()) + "[]" : null;
      }
   }

   private Type findClassDeclarationInHierarchy(Type t, List hierarchy) {
      if (t instanceof TypeVariable && hierarchy != null) {
         GenericDeclaration gd = ((TypeVariable)t).getGenericDeclaration();
         if (!(gd instanceof Method) && !(gd instanceof Constructor)) {
            Type dec = null;
            Iterator it = hierarchy.iterator();

            while(it.hasNext()) {
               Type current = (Type)it.next();
               if (!(gd instanceof Class)) {
                  return null;
               }

               if (getSimpleClassNameForType(current).equals(getSimpleClassNameForType((Class)gd))) {
                  dec = current;
                  break;
               }
            }

            return dec;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private Map getSuperGenericMapping(Type superC) {
      if (superC == null) {
         return null;
      } else {
         Map genericToparameterizedTypes = new HashMap();
         if (superC instanceof ParameterizedType) {
            Type rawt = ((ParameterizedType)superC).getRawType();
            Class intfClass = getClassFromTypeIfPossible(rawt);
            TypeVariable[] tvs = intfClass.getTypeParameters();
            Type[] actuals = ((ParameterizedType)superC).getActualTypeArguments();
            if (tvs.length != actuals.length) {
               throw new MalformedParameterizedTypeException();
            }

            for(int i = 0; i < tvs.length; ++i) {
               genericToparameterizedTypes.put(tvs[i], actuals[i]);
            }
         }

         return genericToparameterizedTypes;
      }
   }

   private Type getMappedTypeForTypeVariable(Type t, List hierarchy) {
      if (!(t instanceof TypeVariable)) {
         return null;
      } else {
         Type realSuper = this.findClassDeclarationInHierarchy(t, hierarchy);
         Map mapping = this.getSuperGenericMapping(realSuper);
         return mapping == null ? null : (Type)mapping.get(t);
      }
   }

   public static String getSimpleClassNameForType(Type t) {
      Class c = null;
      if (t == null) {
         return null;
      } else if (!(t instanceof Class)) {
         if (t instanceof ParameterizedType) {
            Type rt = ((ParameterizedType)t).getRawType();
            if (rt instanceof Class) {
               return ((Class)rt).getName();
            }
         }

         return t.toString().replaceFirst("(class |interface )", "").trim();
      } else {
         c = (Class)t;

         int arrayDim;
         for(arrayDim = 0; c.isArray(); c = c.getComponentType()) {
            ++arrayDim;
         }

         StringBuffer sb = new StringBuffer(c.getCanonicalName());

         for(int i = 0; i < arrayDim; ++i) {
            sb.append("[]");
         }

         return sb.toString();
      }
   }

   public static Class getClassFromTypeIfPossible(Type t) {
      if (t instanceof ParameterizedType) {
         Type rt = ((ParameterizedType)t).getRawType();
         if (rt instanceof Class) {
            return (Class)rt;
         }
      }

      return t instanceof Class ? (Class)t : null;
   }

   public static boolean hasMethodDeclared(Type type, Method toMatch) {
      Class c = getClassFromTypeIfPossible(type);
      if (c != null) {
         Method[] var3 = c.getMethods();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method m = var3[var5];
            if (m.equals(toMatch)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean isDelcaredInTypes(Type t, Type[] typeVariables) {
      if (t != null && typeVariables != null) {
         Type[] var2 = typeVariables;
         int var3 = typeVariables.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Type typeVariable = var2[var4];
            if (typeVariable.equals(t)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
