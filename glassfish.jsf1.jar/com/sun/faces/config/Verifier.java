package com.sun.faces.config;

import com.sun.faces.RIConstants;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Verifier {
   private static final ThreadLocal VERIFIER = new ThreadLocal();
   private List messages = new ArrayList(4);

   Verifier() {
   }

   public static Verifier getCurrentInstance() {
      return (Verifier)VERIFIER.get();
   }

   public static void setCurrentInstance(Verifier verifier) {
      if (verifier == null) {
         VERIFIER.remove();
      } else {
         VERIFIER.set(verifier);
      }

   }

   public boolean isApplicationValid() {
      return this.messages.isEmpty();
   }

   public List getMessages() {
      return this.messages;
   }

   public void validateObject(ObjectType type, String className, Class assignableTo) {
      Class c = null;

      try {
         c = Util.loadClass(className, this);
      } catch (ClassNotFoundException var7) {
         this.messages.add(MessageUtils.getExceptionMessageString("com.sun.faces.verifier.CLASS_NOT_FOUND", type, className));
      } catch (NoClassDefFoundError var8) {
         this.messages.add(MessageUtils.getExceptionMessageString("com.sun.faces.verifier.CLASS_MISSING_DEP", type, className, var8.getMessage()));
      }

      if (c != null) {
         try {
            Constructor ctor = c.getConstructor(RIConstants.EMPTY_CLASS_ARGS);
            if (!Modifier.isPublic(ctor.getModifiers())) {
               this.messages.add(MessageUtils.getExceptionMessageString("com.sun.faces.verifier.NON_PUBLIC_DEF_CTOR", type, className));
            }
         } catch (NoSuchMethodException var6) {
            this.messages.add(MessageUtils.getExceptionMessageString("com.sun.faces.verifier.NO_DEF_CTOR", type, className));
         }

         if (!assignableTo.isAssignableFrom(c)) {
            this.messages.add(MessageUtils.getExceptionMessageString("com.sun.faces.verifier.WRONG_TYPE", type, className));
         }
      }

   }

   public static enum ObjectType {
      COMPONENT,
      CONVERTER,
      VALIDATOR;
   }
}
