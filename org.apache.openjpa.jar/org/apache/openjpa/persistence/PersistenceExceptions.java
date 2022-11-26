package org.apache.openjpa.persistence;

import java.lang.reflect.InvocationTargetException;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.util.Exceptions;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.RuntimeExceptionTranslator;

public class PersistenceExceptions extends Exceptions {
   public static final RuntimeExceptionTranslator TRANSLATOR = new RuntimeExceptionTranslator() {
      public RuntimeException translate(RuntimeException re) {
         return PersistenceExceptions.toPersistenceException(re);
      }
   };

   public static RuntimeExceptionTranslator getRollbackTranslator(final OpenJPAEntityManager em) {
      return new RuntimeExceptionTranslator() {
         private boolean throwing = false;

         public RuntimeException translate(RuntimeException re) {
            RuntimeException ex = PersistenceExceptions.toPersistenceException(re);
            if (!(ex instanceof NonUniqueResultException) && !(ex instanceof NoResultException) && !this.throwing) {
               try {
                  this.throwing = true;
                  if (em.isOpen() && ((EntityManagerImpl)em).isActive()) {
                     ((EntityManagerImpl)em).setRollbackOnly(re);
                  }
               } finally {
                  this.throwing = false;
               }
            }

            return ex;
         }
      };
   }

   public static RuntimeException toPersistenceException(Throwable t) {
      return (RuntimeException)translateException(t, true);
   }

   private static Throwable translateException(Throwable t, boolean checked) {
      if (isPersistenceException(t)) {
         return t;
      } else if (t instanceof Error) {
         throw (Error)t;
      } else if (!(t instanceof OpenJPAException)) {
         if (checked && !(t instanceof RuntimeException)) {
            OpenJPAException ke = new GeneralException(t.getMessage());
            ke.setStackTrace(t.getStackTrace());
            return ke;
         } else {
            return t;
         }
      } else {
         OpenJPAException ke = (OpenJPAException)t;
         if (ke.getNestedThrowables().length == 1 && isPersistenceException(ke.getCause())) {
            return ke.getCause();
         } else {
            if (ke.getType() == 4 && ke.getSubtype() == 4 && ke.getNestedThrowables().length == 1) {
               Throwable e = ke.getCause();
               if (e instanceof InvocationTargetException) {
                  e = e.getCause();
               }

               if (e instanceof RuntimeException) {
                  return e;
               }
            }

            switch (ke.getType()) {
               case 2:
                  return translateStoreException(ke);
               case 4:
                  return translateUserException(ke);
               default:
                  return translateGeneralException(ke);
            }
         }
      }
   }

   private static Throwable translateStoreException(OpenJPAException ke) {
      Object e;
      switch (ke.getSubtype()) {
         case 1:
         case 3:
            e = new OptimisticLockException(ke.getMessage(), getNestedThrowables(ke), getFailedObject(ke), ke.isFatal());
            break;
         case 2:
            e = new EntityNotFoundException(ke.getMessage(), getNestedThrowables(ke), getFailedObject(ke), ke.isFatal());
            break;
         case 4:
         default:
            e = new PersistenceException(ke.getMessage(), getNestedThrowables(ke), getFailedObject(ke), ke.isFatal());
            break;
         case 5:
            e = new EntityExistsException(ke.getMessage(), getNestedThrowables(ke), getFailedObject(ke), ke.isFatal());
      }

      ((Exception)e).setStackTrace(ke.getStackTrace());
      return (Throwable)e;
   }

   private static Exception translateUserException(OpenJPAException ke) {
      Object e;
      switch (ke.getSubtype()) {
         case 2:
            e = new InvalidStateException(ke.getMessage(), getNestedThrowables(ke), getFailedObject(ke), ke.isFatal());
            break;
         case 3:
            e = new TransactionRequiredException(ke.getMessage(), getNestedThrowables(ke), getFailedObject(ke), ke.isFatal());
            break;
         case 4:
         default:
            e = new ArgumentException(ke.getMessage(), getNestedThrowables(ke), getFailedObject(ke), ke.isFatal());
            break;
         case 5:
            e = new NoResultException(ke.getMessage(), getNestedThrowables(ke), getFailedObject(ke), ke.isFatal());
            break;
         case 6:
            e = new NonUniqueResultException(ke.getMessage(), getNestedThrowables(ke), getFailedObject(ke), ke.isFatal());
      }

      ((Exception)e).setStackTrace(ke.getStackTrace());
      return (Exception)e;
   }

   private static Throwable translateGeneralException(OpenJPAException ke) {
      Exception e = new PersistenceException(ke.getMessage(), getNestedThrowables(ke), getFailedObject(ke), ke.isFatal());
      e.setStackTrace(ke.getStackTrace());
      return e;
   }

   private static boolean isPersistenceException(Throwable t) {
      return t.getClass().getName().startsWith("org.apache.openjpa.persistence.");
   }

   private static Throwable[] getNestedThrowables(OpenJPAException ke) {
      Throwable[] nested = ke.getNestedThrowables();
      if (nested.length == 0) {
         return nested;
      } else {
         Throwable[] trans = new Throwable[nested.length];

         for(int i = 0; i < nested.length; ++i) {
            trans[i] = translateException(nested[i], false);
         }

         return trans;
      }
   }

   private static Object getFailedObject(OpenJPAException ke) {
      Object o = ke.getFailedObject();
      if (o == null) {
         return null;
      } else {
         return o instanceof Broker ? JPAFacadeHelper.toEntityManager((Broker)o) : JPAFacadeHelper.fromOpenJPAObjectId(o);
      }
   }

   static Throwable getCause(Throwable[] nested) {
      return nested != null && nested.length != 0 ? nested[0] : null;
   }
}
