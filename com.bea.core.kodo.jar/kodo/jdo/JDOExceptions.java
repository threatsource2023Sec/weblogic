package kodo.jdo;

import javax.jdo.JDOException;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.lib.util.JavaVersions;
import org.apache.openjpa.util.Exceptions;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.RuntimeExceptionTranslator;

public class JDOExceptions extends Exceptions {
   public static final RuntimeExceptionTranslator TRANSLATOR = new RuntimeExceptionTranslator() {
      public RuntimeException translate(RuntimeException re) {
         return JDOExceptions.toJDOException(re);
      }
   };

   public static RuntimeException toJDOException(Throwable t) {
      return (RuntimeException)translateException(t, true);
   }

   private static Throwable translateException(Throwable t, boolean checked) {
      if (t instanceof JDOException) {
         return t;
      } else if (t instanceof Error) {
         throw (Error)t;
      } else if (!(t instanceof OpenJPAException)) {
         if (checked && !(t instanceof RuntimeException)) {
            JDOException je = new GeneralException(t.getMessage(), getNestedThrowables(t, (OpenJPAException)null, false), (Object)null);
            JavaVersions.transferStackTrace(t, je);
            return je;
         } else {
            return t;
         }
      } else {
         OpenJPAException ke = (OpenJPAException)t;
         if (ke.getNestedThrowables().length == 1 && ke.getCause() instanceof JDOException) {
            return ke.getCause();
         } else {
            Object je;
            switch (ke.getType()) {
               case 1:
                  je = new FatalInternalException(ke.getMessage(), getNestedThrowables(ke, ke, false));
                  break;
               case 2:
                  je = translateStoreException(ke);
                  break;
               case 3:
                  je = new UnsupportedOptionException(ke.getMessage(), getNestedThrowables(ke, ke, false));
                  break;
               case 4:
                  if (ke.getSubtype() == 5) {
                     return ke;
                  }

                  je = translateUserException(ke);
                  break;
               default:
                  je = new GeneralException(ke.getMessage(), getNestedThrowables(ke, ke, true), getFailedObject(ke));
            }

            JavaVersions.transferStackTrace(ke, (Throwable)je);
            return (Throwable)je;
         }
      }
   }

   private static JDOException translateStoreException(OpenJPAException ke) {
      switch (ke.getSubtype()) {
         case 1:
            int to = ((org.apache.openjpa.util.LockException)ke).getTimeout();
            return new LockException(ke.getMessage(), getNestedThrowables(ke, ke, true), getFailedObject(ke), to);
         case 2:
            if (ke.getFailedObject() != null) {
               return new ObjectNotFoundException(ke.getMessage(), getFailedObject(ke));
            }

            return new ObjectNotFoundException(ke.getMessage(), getNestedThrowables(ke, ke, true));
         case 3:
            if (getFailedObject(ke) != null) {
               return new OptimisticVerificationException(ke.getMessage(), getFailedObject(ke));
            }

            return new OptimisticVerificationException(ke.getMessage(), getNestedThrowables(ke, ke, true));
         case 4:
            int iv = ((org.apache.openjpa.util.ReferentialIntegrityException)ke).getIntegrityViolation();
            return new ReferentialIntegrityException(ke.getMessage(), getNestedThrowables(ke, ke, true), getFailedObject(ke), iv);
         default:
            if (!ke.isFatal()) {
               return new DataStoreException(ke.getMessage(), getNestedThrowables(ke, ke, true), getFailedObject(ke));
            } else {
               return ke.getFailedObject() != null ? new FatalDataStoreException(ke.getMessage(), getFailedObject(ke)) : new FatalDataStoreException(ke.getMessage(), getNestedThrowables(ke, ke, false));
            }
      }
   }

   private static JDOException translateUserException(OpenJPAException ke) {
      return (JDOException)(!ke.isFatal() ? new UserException(ke.getMessage(), getNestedThrowables(ke, ke, true), getFailedObject(ke)) : new FatalUserException(ke.getMessage(), getNestedThrowables(ke, ke, true), getFailedObject(ke)));
   }

   private static Throwable[] getNestedThrowables(Throwable t, OpenJPAException ke, boolean stripFailed) {
      Throwable[] nested = null;
      if (ke != null) {
         nested = ke.getNestedThrowables();
      } else {
         Throwable cause = JavaVersions.getCause(t);
         if (cause != null) {
            nested = new Throwable[]{cause};
         }
      }

      if (nested != null && nested.length != 0) {
         Throwable[] trans = new Throwable[nested.length];

         for(int i = 0; i < nested.length; ++i) {
            trans[i] = translateException(nested[i], false);
         }

         return trans;
      } else if (JavaVersions.VERSION >= 4) {
         return null;
      } else {
         if (ke != null && stripFailed) {
            ke.setFailedObject((Object)null);
         }

         return new Throwable[]{t};
      }
   }

   private static Object getFailedObject(OpenJPAException ke) {
      Object o = ke.getFailedObject();
      if (o == null) {
         return null;
      } else {
         return o instanceof Broker ? KodoJDOHelper.toPersistenceManager((Broker)o) : KodoJDOHelper.fromKodoObjectId(o);
      }
   }
}
