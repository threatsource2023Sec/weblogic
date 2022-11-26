package com.bea.core.repackaged.aspectj.weaver.tools;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

public abstract class AbstractTrace implements Trace {
   private static final Pattern packagePrefixPattern = Pattern.compile("([^.])[^.]*(\\.)");
   protected Class tracedClass;
   private static SimpleDateFormat timeFormat;

   protected AbstractTrace(Class clazz) {
      this.tracedClass = clazz;
   }

   public abstract void enter(String var1, Object var2, Object[] var3);

   public abstract void enter(String var1, Object var2);

   public abstract void exit(String var1, Object var2);

   public abstract void exit(String var1, Throwable var2);

   public void enter(String methodName) {
      this.enter(methodName, (Object)null, (Object[])null);
   }

   public void enter(String methodName, Object thiz, Object arg) {
      this.enter(methodName, thiz, new Object[]{arg});
   }

   public void enter(String methodName, Object thiz, boolean z) {
      this.enter(methodName, thiz, (Object)(new Boolean(z)));
   }

   public void exit(String methodName, boolean b) {
      this.exit(methodName, (Object)(new Boolean(b)));
   }

   public void exit(String methodName, int i) {
      this.exit(methodName, (Object)(new Integer(i)));
   }

   public void event(String methodName, Object thiz, Object arg) {
      this.event(methodName, thiz, new Object[]{arg});
   }

   public void warn(String message) {
      this.warn(message, (Throwable)null);
   }

   public void error(String message) {
      this.error(message, (Throwable)null);
   }

   public void fatal(String message) {
      this.fatal(message, (Throwable)null);
   }

   protected String formatMessage(String kind, String className, String methodName, Object thiz, Object[] args) {
      StringBuffer message = new StringBuffer();
      Date now = new Date();
      message.append(formatDate(now)).append(" ");
      message.append(Thread.currentThread().getName()).append(" ");
      message.append(kind).append(" ");
      message.append(this.formatClassName(className));
      message.append(".").append(methodName);
      if (thiz != null) {
         message.append(" ").append(this.formatObj(thiz));
      }

      if (args != null) {
         message.append(" ").append(this.formatArgs(args));
      }

      return message.toString();
   }

   private String formatClassName(String className) {
      return packagePrefixPattern.matcher(className).replaceAll("$1.");
   }

   protected String formatMessage(String kind, String text, Throwable th) {
      StringBuffer message = new StringBuffer();
      Date now = new Date();
      message.append(formatDate(now)).append(" ");
      message.append(Thread.currentThread().getName()).append(" ");
      message.append(kind).append(" ");
      message.append(text);
      if (th != null) {
         message.append(" ").append(this.formatObj(th));
      }

      return message.toString();
   }

   private static String formatDate(Date date) {
      if (timeFormat == null) {
         timeFormat = new SimpleDateFormat("HH:mm:ss.SSS");
      }

      return timeFormat.format(date);
   }

   protected Object formatObj(Object obj) {
      if (obj != null && !(obj instanceof String) && !(obj instanceof Number) && !(obj instanceof Boolean) && !(obj instanceof Exception) && !(obj instanceof Character) && !(obj instanceof Class) && !(obj instanceof File) && !(obj instanceof StringBuffer) && !(obj instanceof URL) && !(obj instanceof IMessage.Kind)) {
         if (obj.getClass().isArray()) {
            return this.formatArray(obj);
         } else if (obj instanceof Collection) {
            return this.formatCollection((Collection)obj);
         } else {
            try {
               return obj instanceof Traceable ? ((Traceable)obj).toTraceString() : this.formatClassName(obj.getClass().getName()) + "@" + Integer.toHexString(System.identityHashCode(obj));
            } catch (Exception var3) {
               return obj.getClass().getName() + "@FFFFFFFF";
            }
         }
      } else {
         return obj;
      }
   }

   protected String formatArray(Object obj) {
      return obj.getClass().getComponentType().getName() + "[" + Array.getLength(obj) + "]";
   }

   protected String formatCollection(Collection c) {
      return c.getClass().getName() + "(" + c.size() + ")";
   }

   protected String formatArgs(Object[] args) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < args.length; ++i) {
         sb.append(this.formatObj(args[i]));
         if (i < args.length - 1) {
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   protected Object[] formatObjects(Object[] args) {
      for(int i = 0; i < args.length; ++i) {
         args[i] = this.formatObj(args[i]);
      }

      return args;
   }
}
