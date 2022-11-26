package org.stringtemplate.v4.misc;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ModelAdaptor;
import org.stringtemplate.v4.ST;

public class ObjectModelAdaptor implements ModelAdaptor {
   protected static final Member INVALID_MEMBER;
   protected static final Map membersCache;

   public synchronized Object getProperty(Interpreter interp, ST self, Object o, Object property, String propertyName) throws STNoSuchPropertyException {
      if (o == null) {
         throw new NullPointerException("o");
      } else {
         Class c = o.getClass();
         if (property == null) {
            return this.throwNoSuchProperty(c, propertyName, (Exception)null);
         } else {
            Member member = findMember(c, propertyName);
            if (member != null) {
               try {
                  if (member instanceof Method) {
                     return ((Method)member).invoke(o);
                  }

                  if (member instanceof Field) {
                     return ((Field)member).get(o);
                  }
               } catch (Exception var9) {
                  this.throwNoSuchProperty(c, propertyName, var9);
               }
            }

            return this.throwNoSuchProperty(c, propertyName, (Exception)null);
         }
      }
   }

   protected static Member findMember(Class clazz, String memberName) {
      if (clazz == null) {
         throw new NullPointerException("clazz");
      } else if (memberName == null) {
         throw new NullPointerException("memberName");
      } else {
         synchronized(membersCache) {
            Map members = (Map)membersCache.get(clazz);
            Member member = null;
            if (members != null) {
               member = (Member)((Map)members).get(memberName);
               if (member != null) {
                  return member != INVALID_MEMBER ? member : null;
               }
            } else {
               members = new HashMap();
               membersCache.put(clazz, members);
            }

            String methodSuffix = Character.toUpperCase(memberName.charAt(0)) + memberName.substring(1, memberName.length());
            Member member = tryGetMethod(clazz, "get" + methodSuffix);
            if (member == null) {
               member = tryGetMethod(clazz, "is" + methodSuffix);
               if (member == null) {
                  member = tryGetMethod(clazz, "has" + methodSuffix);
               }
            }

            if (member == null) {
               member = tryGetField(clazz, memberName);
            }

            ((Map)members).put(memberName, member != null ? member : INVALID_MEMBER);
            return (Member)member;
         }
      }
   }

   protected static Method tryGetMethod(Class clazz, String methodName) {
      try {
         Method method = clazz.getMethod(methodName);
         if (method != null) {
            method.setAccessible(true);
         }

         return method;
      } catch (NoSuchMethodException var3) {
      } catch (SecurityException var4) {
      }

      return null;
   }

   protected static Field tryGetField(Class clazz, String fieldName) {
      try {
         Field field = clazz.getField(fieldName);
         if (field != null) {
            field.setAccessible(true);
         }

         return field;
      } catch (NoSuchFieldException var3) {
      } catch (SecurityException var4) {
      }

      return null;
   }

   protected Object throwNoSuchProperty(Class clazz, String propertyName, Exception cause) {
      throw new STNoSuchPropertyException(cause, (Object)null, clazz.getName() + "." + propertyName);
   }

   static {
      Field invalidMember;
      try {
         invalidMember = ObjectModelAdaptor.class.getDeclaredField("INVALID_MEMBER");
      } catch (NoSuchFieldException var2) {
         invalidMember = null;
      } catch (SecurityException var3) {
         invalidMember = null;
      }

      INVALID_MEMBER = invalidMember;
      membersCache = new HashMap();
   }
}
