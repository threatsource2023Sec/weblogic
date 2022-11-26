package org.python.google.common.util.concurrent;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.Ordering;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtIncompatible
final class FuturesGetChecked {
   private static final Ordering WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf(new Function() {
      public Boolean apply(Constructor input) {
         return Arrays.asList(input.getParameterTypes()).contains(String.class);
      }
   }).reverse();

   @CanIgnoreReturnValue
   static Object getChecked(Future future, Class exceptionClass) throws Exception {
      return getChecked(bestGetCheckedTypeValidator(), future, exceptionClass);
   }

   @CanIgnoreReturnValue
   @VisibleForTesting
   static Object getChecked(GetCheckedTypeValidator validator, Future future, Class exceptionClass) throws Exception {
      validator.validateClass(exceptionClass);

      try {
         return future.get();
      } catch (InterruptedException var4) {
         Thread.currentThread().interrupt();
         throw newWithCause(exceptionClass, var4);
      } catch (ExecutionException var5) {
         wrapAndThrowExceptionOrError(var5.getCause(), exceptionClass);
         throw new AssertionError();
      }
   }

   @CanIgnoreReturnValue
   static Object getChecked(Future future, Class exceptionClass, long timeout, TimeUnit unit) throws Exception {
      bestGetCheckedTypeValidator().validateClass(exceptionClass);

      try {
         return future.get(timeout, unit);
      } catch (InterruptedException var6) {
         Thread.currentThread().interrupt();
         throw newWithCause(exceptionClass, var6);
      } catch (TimeoutException var7) {
         throw newWithCause(exceptionClass, var7);
      } catch (ExecutionException var8) {
         wrapAndThrowExceptionOrError(var8.getCause(), exceptionClass);
         throw new AssertionError();
      }
   }

   private static GetCheckedTypeValidator bestGetCheckedTypeValidator() {
      return FuturesGetChecked.GetCheckedTypeValidatorHolder.BEST_VALIDATOR;
   }

   @VisibleForTesting
   static GetCheckedTypeValidator weakSetValidator() {
      return FuturesGetChecked.GetCheckedTypeValidatorHolder.WeakSetValidator.INSTANCE;
   }

   @VisibleForTesting
   static GetCheckedTypeValidator classValueValidator() {
      return FuturesGetChecked.GetCheckedTypeValidatorHolder.ClassValueValidator.INSTANCE;
   }

   private static void wrapAndThrowExceptionOrError(Throwable cause, Class exceptionClass) throws Exception {
      if (cause instanceof Error) {
         throw new ExecutionError((Error)cause);
      } else if (cause instanceof RuntimeException) {
         throw new UncheckedExecutionException(cause);
      } else {
         throw newWithCause(exceptionClass, cause);
      }
   }

   private static boolean hasConstructorUsableByGetChecked(Class exceptionClass) {
      try {
         newWithCause(exceptionClass, new Exception());
         return true;
      } catch (Exception var2) {
         return false;
      }
   }

   private static Exception newWithCause(Class exceptionClass, Throwable cause) {
      List constructors = Arrays.asList(exceptionClass.getConstructors());
      Iterator var3 = preferringStrings(constructors).iterator();

      Exception instance;
      do {
         if (!var3.hasNext()) {
            throw new IllegalArgumentException("No appropriate constructor for exception of type " + exceptionClass + " in response to chained exception", cause);
         }

         Constructor constructor = (Constructor)var3.next();
         instance = (Exception)newFromConstructor(constructor, cause);
      } while(instance == null);

      if (instance.getCause() == null) {
         instance.initCause(cause);
      }

      return instance;
   }

   private static List preferringStrings(List constructors) {
      return WITH_STRING_PARAM_FIRST.sortedCopy(constructors);
   }

   @Nullable
   private static Object newFromConstructor(Constructor constructor, Throwable cause) {
      Class[] paramTypes = constructor.getParameterTypes();
      Object[] params = new Object[paramTypes.length];

      for(int i = 0; i < paramTypes.length; ++i) {
         Class paramType = paramTypes[i];
         if (paramType.equals(String.class)) {
            params[i] = cause.toString();
         } else {
            if (!paramType.equals(Throwable.class)) {
               return null;
            }

            params[i] = cause;
         }
      }

      try {
         return constructor.newInstance(params);
      } catch (IllegalArgumentException var6) {
         return null;
      } catch (InstantiationException var7) {
         return null;
      } catch (IllegalAccessException var8) {
         return null;
      } catch (InvocationTargetException var9) {
         return null;
      }
   }

   @VisibleForTesting
   static boolean isCheckedException(Class type) {
      return !RuntimeException.class.isAssignableFrom(type);
   }

   @VisibleForTesting
   static void checkExceptionClassValidity(Class exceptionClass) {
      Preconditions.checkArgument(isCheckedException(exceptionClass), "Futures.getChecked exception type (%s) must not be a RuntimeException", (Object)exceptionClass);
      Preconditions.checkArgument(hasConstructorUsableByGetChecked(exceptionClass), "Futures.getChecked exception type (%s) must be an accessible class with an accessible constructor whose parameters (if any) must be of type String and/or Throwable", (Object)exceptionClass);
   }

   private FuturesGetChecked() {
   }

   @VisibleForTesting
   static class GetCheckedTypeValidatorHolder {
      static final String CLASS_VALUE_VALIDATOR_NAME = GetCheckedTypeValidatorHolder.class.getName() + "$ClassValueValidator";
      static final GetCheckedTypeValidator BEST_VALIDATOR = getBestValidator();

      static GetCheckedTypeValidator getBestValidator() {
         try {
            Class theClass = Class.forName(CLASS_VALUE_VALIDATOR_NAME);
            return (GetCheckedTypeValidator)theClass.getEnumConstants()[0];
         } catch (Throwable var1) {
            return FuturesGetChecked.weakSetValidator();
         }
      }

      static enum WeakSetValidator implements GetCheckedTypeValidator {
         INSTANCE;

         private static final Set validClasses = new CopyOnWriteArraySet();

         public void validateClass(Class exceptionClass) {
            Iterator var2 = validClasses.iterator();

            WeakReference knownGood;
            do {
               if (!var2.hasNext()) {
                  FuturesGetChecked.checkExceptionClassValidity(exceptionClass);
                  if (validClasses.size() > 1000) {
                     validClasses.clear();
                  }

                  validClasses.add(new WeakReference(exceptionClass));
                  return;
               }

               knownGood = (WeakReference)var2.next();
            } while(!exceptionClass.equals(knownGood.get()));

         }
      }

      @IgnoreJRERequirement
      static enum ClassValueValidator implements GetCheckedTypeValidator {
         INSTANCE;

         private static final ClassValue isValidClass = new ClassValue() {
            protected Boolean computeValue(Class type) {
               FuturesGetChecked.checkExceptionClassValidity(type.asSubclass(Exception.class));
               return true;
            }
         };

         public void validateClass(Class exceptionClass) {
            isValidClass.get(exceptionClass);
         }
      }
   }

   @VisibleForTesting
   interface GetCheckedTypeValidator {
      void validateClass(Class var1);
   }
}
