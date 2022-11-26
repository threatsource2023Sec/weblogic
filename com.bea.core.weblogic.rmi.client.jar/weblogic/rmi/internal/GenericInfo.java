package weblogic.rmi.internal;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
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
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.utils.Utilities;

public class GenericInfo {
   private Class remoteClass = null;
   private Class[] interfaces;
   private Map typeVariableMap = new HashMap();
   private static final DebugLogger debug = DebugLogger.getDebugLogger("DebugGenericMethodDescriptor");
   private static boolean DEBUG = false;

   public GenericInfo(Class remoteClass) {
      this.remoteClass = remoteClass;
      this.interfaces = Utilities.getRemoteInterfaces(remoteClass);
      this.generateGenericInfo();
   }

   public GenericInfo(Class[] remoteInterfaces) {
      this.interfaces = remoteInterfaces;
      this.generateGenericInfo();
   }

   public void generateGenericInfo() {
      for(int i = this.interfaces.length - 1; i >= 0; --i) {
         Class c = this.interfaces[i];
         this.processTypeParametersForClass(c);
         this.processGenericParameterTypes(c);
      }

      if (this.remoteClass != null) {
         this.processTypeParametersForClass(this.remoteClass);
         this.processGenericParameterTypes(this.remoteClass);
      }

   }

   private void processTypeParametersForClass(Class c) {
      TypeVariable[] typeParams = c.getTypeParameters();
      List paramList = new ArrayList();
      GenericDeclaration gd = null;

      for(int i = 0; i < typeParams.length; ++i) {
         TypeVariable typeVar = typeParams[i];
         if (gd == null) {
            gd = typeVar.getGenericDeclaration();
         } else if (!gd.equals(typeVar.getGenericDeclaration())) {
            throw new AssertionError("Type params for same class " + c + " have different GenericDeclaration");
         }

         paramList.add(new TypeRecord(typeVar));
      }

      if (gd != null) {
         this.typeVariableMap.put(gd.toString(), paramList);
      }

   }

   private void processGenericParameterTypes(Class c) {
      Type[] types = c.getGenericInterfaces();

      for(int i = 0; i < types.length; ++i) {
         Type type = types[i];
         if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType)type;
            Type[] params = pType.getActualTypeArguments();
            String key = pType.getRawType().toString();
            List paramsList = (List)this.typeVariableMap.get(key);
            if (paramsList != null) {
               if (paramsList.size() != params.length) {
                  throw new AssertionError("Mismatch in number of parameters");
               }

               for(int j = 0; j < params.length; ++j) {
                  TypeRecord typeRec = (TypeRecord)paramsList.get(j);
                  typeRec.updateType(params[j]);
               }
            }
         } else {
            if (!(type instanceof Class)) {
               throw new AssertionError("GenericParameterType " + type + " not a ParameterizedType");
            }

            debug("Skipping processing GenericParameterType of type class " + type);
         }
      }

   }

   public String getMethodSignature(Method method) {
      return this.getMethodSignature(method, false);
   }

   public String getMethodSignature(Method method, boolean typeErased) {
      StringBuffer strBuf = new StringBuffer();
      strBuf.append(method.getName());
      strBuf.append('(');
      Type[] type = method.getGenericParameterTypes();

      for(int i = 0; i < type.length; ++i) {
         if (i > 0) {
            strBuf.append(",");
         }

         String parameter = this.stringifyType(type[i], typeErased);
         strBuf.append(parameter);
      }

      strBuf.append(')');
      String signature = strBuf.toString();
      debug("signature=" + signature);
      return signature;
   }

   private Type getActualParamsType(Type methodType) {
      Type actualType = null;
      if (methodType instanceof TypeVariable) {
         TypeVariable typeVar = (TypeVariable)methodType;
         String key = typeVar.getGenericDeclaration().toString();
         String typeName = typeVar.getName();
         TypeRecord rec = this.getTypeRecord(key, typeName);
         if (rec != null) {
            while(rec.getType() instanceof TypeVariable) {
               TypeRecord rec1 = this.getTypeRecord(((TypeVariable)rec.getType()).getGenericDeclaration().toString(), rec.getName());
               if (rec1 == rec) {
                  throw new UnresolvedTypeException("Generic type '" + rec.getName() + "' could not be resolved.");
               }

               rec = rec1;
               if (rec1 == null) {
                  return methodType;
               }
            }

            actualType = rec.getType();
         }
      }

      return actualType;
   }

   private String stringifyType(Type type, boolean typeErased) {
      String signature = null;
      StringBuffer strBuf = new StringBuffer();
      if (type instanceof Class) {
         return ((Class)type).getName();
      } else if (type instanceof TypeVariable) {
         Type actual = this.getActualParamsType(type);
         return actual == null ? type.toString() : this.stringifyType(actual, typeErased);
      } else {
         if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType)type;
            strBuf.append(this.stringifyType(pType.getRawType(), typeErased));
            if (!typeErased) {
               strBuf.append("<");
               Type[] actualTypeParams = pType.getActualTypeArguments();

               for(int i = 0; i < actualTypeParams.length; ++i) {
                  if (i > 0) {
                     strBuf.append(",");
                  }

                  String parameter = this.stringifyType(actualTypeParams[i], typeErased);
                  strBuf.append(parameter);
               }

               strBuf.append(">");
            }
         } else if (type instanceof WildcardType) {
            WildcardType wType = (WildcardType)type;
            boolean isLowerBounded = false;
            boolean isUpperBounded = false;
            Type[] bounds = wType.getLowerBounds();
            if (bounds.length > 0) {
               isLowerBounded = true;
               if (!typeErased) {
                  strBuf.append("? super ");
               }
            }

            String parameter;
            if (!isLowerBounded) {
               bounds = wType.getUpperBounds();
               parameter = this.stringifyType(bounds[0], typeErased);
               if (!parameter.equals("java.lang.Object")) {
                  isUpperBounded = true;
                  if (!typeErased) {
                     strBuf.append("? extends ");
                  }
               }
            }

            if (!isLowerBounded && !isUpperBounded) {
               if (!typeErased) {
                  strBuf.append("?");
               } else {
                  strBuf.append("java.lang.Object");
               }
            } else {
               parameter = this.stringifyType(bounds[0], typeErased);
               strBuf.append(parameter);
            }
         } else if (type instanceof GenericArrayType) {
            GenericArrayType gType = (GenericArrayType)type;
            String componentType = this.stringifyType(gType.getGenericComponentType(), typeErased);
            strBuf.append(componentType);
            strBuf.append("[]");
         } else {
            strBuf.append(type.toString());
         }

         signature = strBuf.toString();
         return signature;
      }
   }

   private TypeRecord getTypeRecord(String key, String typeName) {
      List typeVariablesList = (List)this.typeVariableMap.get(key);
      if (typeVariablesList != null) {
         Iterator itr = typeVariablesList.iterator();

         while(itr.hasNext()) {
            TypeRecord typeRec = (TypeRecord)itr.next();
            if (typeRec.getName().equals(typeName)) {
               return typeRec;
            }
         }
      }

      return null;
   }

   private static void debug(String str) {
      if (DEBUG) {
         debug.debug("[GenericInfo] " + str);
      }

   }

   static {
      if (!KernelStatus.isApplet()) {
         DEBUG = Boolean.getBoolean("weblogic.debug.DebugGenericMethodDescriptor") && debug.isDebugEnabled();
      }

   }

   private class TypeRecord {
      private String name;
      private Type type;

      public TypeRecord(Type var) {
         if (var instanceof TypeVariable) {
            this.name = ((TypeVariable)var).getName();
         } else {
            this.name = var.getClass().getName();
         }

         this.type = var;
      }

      public void updateType(Type var) {
         this.type = var;
      }

      public String getName() {
         return this.name;
      }

      public Type getType() {
         return this.type;
      }
   }
}
