package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JField;
import com.bea.util.jam.JPackage;
import com.sun.javadoc.FieldDoc;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import weblogic.utils.Debug;

public class JamHelper {
   private static final boolean debug = false;
   private static final Map KNOWN_VALUES = new HashMap();

   private JamHelper() {
   }

   static JClass findJClass(String cname, JClass klass) {
      JClass c = null;
      if (klass.getSimpleName().endsWith(cname)) {
         c = klass;
      } else {
         c = klass.forName(cname);
      }

      if (c != null && !c.isUnresolvedType()) {
         return c;
      } else {
         String pkgeName = klass.getContainingPackage().getQualifiedName();
         if (cname.indexOf(".") == -1 && !pkgeName.equals("")) {
            c = klass.forName(pkgeName + "." + cname);
            if (c != null && !c.isUnresolvedType()) {
               return c;
            }
         }

         JClass theClass = null;
         JClass[] importedKlasses = klass.getImportedClasses();

         for(int i = 0; i < importedKlasses.length; ++i) {
            if (importedKlasses[i].getSimpleName().endsWith(cname)) {
               theClass = importedKlasses[i];
               break;
            }
         }

         if (theClass != null && !theClass.isUnresolvedType()) {
            return theClass;
         } else {
            JPackage[] pkgs = klass.getImportedPackages();
            if (pkgs != null) {
               for(int i = 0; i < pkgs.length; ++i) {
                  JPackage pkg = pkgs[i];
                  String pkgName = pkg.getQualifiedName();
                  String className = pkgName + "." + cname;
                  c = klass.forName(className);
                  if (c != null && !c.isUnresolvedType()) {
                     theClass = c;
                     break;
                  }
               }
            }

            JClass[] interfaces = klass.getInterfaces();

            for(int i = 0; i < interfaces.length; ++i) {
               JClass jkls = findJClass(cname, interfaces[i]);
               if (jkls != null) {
                  theClass = jkls;
                  break;
               }
            }

            if (theClass == null) {
               JClass superClass = klass.getSuperclass();
               if (superClass != null) {
                  theClass = findJClass(cname, superClass);
               }
            }

            return theClass != null && !theClass.isUnresolvedType() ? theClass : null;
         }
      }
   }

   static Object findNumber(String value, JClass klass, JClass returnType) {
      if (value.startsWith("\"") && value.endsWith("\"")) {
         value = value.substring(1, value.length() - 1).trim();
      }

      Class primitiveType = returnType.getPrimitiveClass();
      Debug.assertion(returnType.isPrimitiveType(), "Not a primitive type");

      try {
         Object ret = getValueOf(primitiveType, value);
         return ret;
      } catch (NumberFormatException var5) {
         return findValue(value, klass);
      }
   }

   private static Object getValueOf(Class primitive, String value) throws NumberFormatException {
      if (Integer.TYPE == primitive) {
         return Integer.valueOf(value);
      } else if (Long.TYPE == primitive) {
         return Long.valueOf(value);
      } else if (Short.TYPE == primitive) {
         return Short.valueOf(value);
      } else if (Byte.TYPE == primitive) {
         return Byte.valueOf(value);
      } else if (Float.TYPE == primitive) {
         return Float.valueOf(value);
      } else if (Double.TYPE == primitive) {
         return Double.valueOf(value);
      } else {
         throw new AssertionError("Unknown Number type" + primitive + " value " + value);
      }
   }

   static Object findDefaultValue(String value, JClass klass, JClass returnType) {
      Object ret = _findDefaultValue(value, klass, returnType);
      return ret;
   }

   static Object _findDefaultValue(String value, JClass klass, JClass returnType) {
      if (value == null) {
         return null;
      } else if (returnType.isPrimitiveType()) {
         if (returnType.getPrimitiveClass() == Boolean.TYPE) {
            return !"true".equals(value) && !"false".equals(value) ? findValue(value, klass) : value;
         } else {
            return findNumber(value, klass, returnType);
         }
      } else {
         return findValue(value, klass);
      }
   }

   static Object findValue(String value, JClass klass) {
      value = value.trim();
      if (value.startsWith("\"") && value.endsWith("\"")) {
         return value.substring(1, value.length() - 1);
      } else {
         Object val = KNOWN_VALUES.get(value);
         if (val != null) {
            return val;
         } else if (value.lastIndexOf(".") > -1) {
            String cname = value.substring(0, value.lastIndexOf("."));
            String field = value.substring(value.lastIndexOf(".") + 1);
            JClass c = findJClass(cname, klass);
            if (c == null) {
               return null;
            } else {
               JField jField = findJField(c, field);
               Object artifact = null;
               if (jField != null) {
                  artifact = jField.getArtifact();
               }

               return evaluateArtifact(artifact);
            }
         } else {
            JField field = findJField(klass, value);
            return field != null ? evaluateArtifact(field.getArtifact()) : null;
         }
      }
   }

   private static Object evaluateArtifact(Object artifact) {
      if (artifact == null) {
         return null;
      } else if (artifact instanceof FieldDoc) {
         FieldDoc f = (FieldDoc)artifact;
         return f.constantValue();
      } else if (artifact instanceof Field) {
         Field f = (Field)artifact;

         try {
            return f.get((Object)null);
         } catch (IllegalAccessException var3) {
            var3.printStackTrace();
            return null;
         }
      } else {
         throw new AssertionError(" unknown artifact " + artifact.getClass().getName());
      }
   }

   static JField findJField(JClass klass, String name) {
      if (klass == null) {
         return null;
      } else {
         JField[] fields = klass.getFields();
         if (fields != null) {
            for(int i = 0; i < fields.length; ++i) {
               if (name.equals(fields[i].getSimpleName()) && fields[i].isStatic() && fields[i].isPublic() && fields[i].isFinal()) {
                  return fields[i];
               }
            }
         }

         JClass[] interfaces = klass.getInterfaces();
         if (interfaces != null) {
            for(int i = 0; i < interfaces.length; ++i) {
               JField value = findJField(interfaces[i], name);
               if (value != null) {
                  return value;
               }
            }
         }

         JClass superClass = klass.getSuperclass();
         return superClass != null && !superClass.isUnresolvedType() ? findJField(superClass, name) : null;
      }
   }

   private static void p(String o) {
      System.err.println("[JamHelper] " + o);
   }

   static {
      KNOWN_VALUES.put("java.lang.Long.MAX_VALUE", "9223372036854775807");
      KNOWN_VALUES.put("java.lang.Integer.MAX_VALUE", "2147483647");
   }
}
