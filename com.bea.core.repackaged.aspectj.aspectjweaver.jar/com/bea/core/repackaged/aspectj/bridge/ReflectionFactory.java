package com.bea.core.repackaged.aspectj.bridge;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class ReflectionFactory {
   public static final String OLD_AJC = "bridge.tools.impl.OldAjc";
   public static final String ECLIPSE = "com.bea.core.repackaged.aspectj.ajdt.ajc.AjdtCommand";
   private static final Object[] NONE = new Object[0];

   public static ICommand makeCommand(String cname, IMessageHandler errorSink) {
      return (ICommand)make(ICommand.class, cname, NONE, errorSink);
   }

   private static Object make(Class c, String cname, Object[] args, IMessageHandler errorSink) {
      boolean makeErrors = null != errorSink;
      Object result = null;

      String error;
      Message mssg;
      try {
         Class cfn = Class.forName(cname);
         error = null;
         if (args == NONE) {
            result = cfn.newInstance();
         } else {
            Class[] types = getTypes(args);
            Constructor constructor = cfn.getConstructor(types);
            if (null != constructor) {
               result = constructor.newInstance(args);
            } else if (makeErrors) {
               error = "no constructor for " + c + " using " + Arrays.asList(types);
            }
         }

         if (null != result && !c.isAssignableFrom(result.getClass())) {
            if (makeErrors) {
               error = "expecting type " + c + " got " + result.getClass();
            }

            result = null;
         }

         if (null != error) {
            mssg = new Message(error, IMessage.FAIL, (Throwable)null, (ISourceLocation)null);
            errorSink.handleMessage(mssg);
         }
      } catch (Throwable var10) {
         if (makeErrors) {
            error = "ReflectionFactory unable to load " + cname + " as " + c.getName();
            mssg = new Message(error, IMessage.FAIL, var10, (ISourceLocation)null);
            errorSink.handleMessage(mssg);
         }
      }

      return result;
   }

   private static Class[] getTypes(Object[] args) {
      if (null != args && 0 >= args.length) {
         Class[] result = new Class[args.length];

         for(int i = 0; i < result.length; ++i) {
            if (null != args[i]) {
               result[i] = args[i].getClass();
            }
         }

         return result;
      } else {
         return new Class[0];
      }
   }

   private ReflectionFactory() {
   }
}
