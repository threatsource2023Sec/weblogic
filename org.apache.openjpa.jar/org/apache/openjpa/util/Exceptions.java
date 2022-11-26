package org.apache.openjpa.util;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAVersion;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.JavaVersions;

public class Exceptions {
   public static final Throwable[] EMPTY_THROWABLES = new Throwable[0];
   static final String SEP = J2DoPrivHelper.getLineSeparator();
   private static final OutputStream DEV_NULL = new OutputStream() {
      public void write(int b) {
      }
   };

   private static boolean isSerializable(Object ob) {
      if (!(ob instanceof Serializable)) {
         return false;
      } else if (!ImplHelper.isManagedType((OpenJPAConfiguration)null, ob.getClass())) {
         return false;
      } else {
         try {
            (new ObjectOutputStream(DEV_NULL)).writeObject(ob);
            return true;
         } catch (Throwable var2) {
            return false;
         }
      }
   }

   public static String toString(Object ob) {
      if (ob == null) {
         return "null";
      } else {
         Object oid = getObjectId(ob);
         if (oid != null) {
            return oid instanceof Id ? oid.toString() : ob.getClass().getName() + "-" + oid.toString();
         } else if (ImplHelper.isManagedType((OpenJPAConfiguration)null, ob.getClass())) {
            return ob.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(ob));
         } else {
            try {
               String s = ob.toString();
               if (s.indexOf(ob.getClass().getName()) == -1) {
                  s = s + " [" + ob.getClass().getName() + "]";
               }

               return s;
            } catch (Throwable var3) {
               return ob.getClass().getName();
            }
         }
      }
   }

   public static String toString(Collection failed) {
      StringBuffer buf = new StringBuffer();
      buf.append("[");
      Iterator itr = failed.iterator();

      while(itr.hasNext()) {
         buf.append(toString(itr.next()));
         if (itr.hasNext()) {
            buf.append(", ");
         }
      }

      buf.append("]");
      return buf.toString();
   }

   public static String toString(ExceptionInfo e) {
      int type = e.getType();
      StringBuffer buf = new StringBuffer();
      buf.append("<").append(OpenJPAVersion.VERSION_ID).append(' ').append(e.isFatal() ? "fatal " : "nonfatal ").append(type == 0 ? "general error" : (type == 1 ? "internal error" : (type == 2 ? "store error" : (type == 3 ? "unsupported error" : (type == 4 ? "user error" : type + " error"))))).append("> ");
      buf.append(e.getClass().getName()).append(": ").append(e.getMessage());
      Object failed = e.getFailedObject();
      if (failed != null) {
         buf.append(SEP).append("FailedObject: ").append(toString(failed));
      }

      return buf.toString();
   }

   public static void printNestedThrowables(ExceptionInfo e, PrintStream out) {
      Throwable[] nested = e.getNestedThrowables();
      int i = JavaVersions.VERSION >= 4 ? 1 : 0;
      if (i < nested.length) {
         out.println("NestedThrowables:");

         for(; i < nested.length; ++i) {
            if (nested[i] != null) {
               nested[i].printStackTrace(out);
            }
         }
      }

   }

   public static void printNestedThrowables(ExceptionInfo e, PrintWriter out) {
      Throwable[] nested = e.getNestedThrowables();
      int i = JavaVersions.VERSION >= 4 ? 1 : 0;
      if (i < nested.length) {
         out.println("NestedThrowables:");

         for(; i < nested.length; ++i) {
            if (nested[i] != null) {
               nested[i].printStackTrace(out);
            }
         }
      }

   }

   public static Object replaceFailedObject(Object ob) {
      if (ob == null) {
         return null;
      } else if (isSerializable(ob)) {
         return ob;
      } else {
         Object oid = getObjectId(ob);
         return oid != null && isSerializable(oid) ? oid : toString(ob);
      }
   }

   public static Throwable[] replaceNestedThrowables(Throwable[] nested) {
      if (nested != null && nested.length != 0) {
         if (isSerializable(nested)) {
            return nested;
         } else {
            Throwable[] newNested = new Throwable[nested.length];

            for(int i = 0; i < nested.length; ++i) {
               if (isSerializable(nested[i])) {
                  newNested[i] = nested[i];
               } else {
                  newNested[i] = new Exception(String.valueOf(nested[i]));
               }
            }

            return newNested;
         }
      } else {
         return nested;
      }
   }

   private static Object getObjectId(Object ob) {
      if (!ImplHelper.isManageable(ob)) {
         return null;
      } else {
         PersistenceCapable pc = ImplHelper.toPersistenceCapable(ob, (Object)null);
         return pc != null && !pc.pcIsNew() ? pc.pcFetchObjectId() : null;
      }
   }
}
