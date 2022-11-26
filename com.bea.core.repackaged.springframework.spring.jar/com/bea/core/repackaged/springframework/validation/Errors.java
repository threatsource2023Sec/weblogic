package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.List;

public interface Errors {
   String NESTED_PATH_SEPARATOR = ".";

   String getObjectName();

   void setNestedPath(String var1);

   String getNestedPath();

   void pushNestedPath(String var1);

   void popNestedPath() throws IllegalStateException;

   void reject(String var1);

   void reject(String var1, String var2);

   void reject(String var1, @Nullable Object[] var2, @Nullable String var3);

   void rejectValue(@Nullable String var1, String var2);

   void rejectValue(@Nullable String var1, String var2, String var3);

   void rejectValue(@Nullable String var1, String var2, @Nullable Object[] var3, @Nullable String var4);

   void addAllErrors(Errors var1);

   boolean hasErrors();

   int getErrorCount();

   List getAllErrors();

   boolean hasGlobalErrors();

   int getGlobalErrorCount();

   List getGlobalErrors();

   @Nullable
   ObjectError getGlobalError();

   boolean hasFieldErrors();

   int getFieldErrorCount();

   List getFieldErrors();

   @Nullable
   FieldError getFieldError();

   boolean hasFieldErrors(String var1);

   int getFieldErrorCount(String var1);

   List getFieldErrors(String var1);

   @Nullable
   FieldError getFieldError(String var1);

   @Nullable
   Object getFieldValue(String var1);

   @Nullable
   Class getFieldType(String var1);
}
