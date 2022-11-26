package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.PrintStream;
import java.io.PrintWriter;

public class PropertyBatchUpdateException extends BeansException {
   private final PropertyAccessException[] propertyAccessExceptions;

   public PropertyBatchUpdateException(PropertyAccessException[] propertyAccessExceptions) {
      super((String)null, (Throwable)null);
      Assert.notEmpty((Object[])propertyAccessExceptions, (String)"At least 1 PropertyAccessException required");
      this.propertyAccessExceptions = propertyAccessExceptions;
   }

   public final int getExceptionCount() {
      return this.propertyAccessExceptions.length;
   }

   public final PropertyAccessException[] getPropertyAccessExceptions() {
      return this.propertyAccessExceptions;
   }

   @Nullable
   public PropertyAccessException getPropertyAccessException(String propertyName) {
      PropertyAccessException[] var2 = this.propertyAccessExceptions;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PropertyAccessException pae = var2[var4];
         if (ObjectUtils.nullSafeEquals(propertyName, pae.getPropertyName())) {
            return pae;
         }
      }

      return null;
   }

   public String getMessage() {
      StringBuilder sb = new StringBuilder("Failed properties: ");

      for(int i = 0; i < this.propertyAccessExceptions.length; ++i) {
         sb.append(this.propertyAccessExceptions[i].getMessage());
         if (i < this.propertyAccessExceptions.length - 1) {
            sb.append("; ");
         }
      }

      return sb.toString();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getClass().getName()).append("; nested PropertyAccessExceptions (");
      sb.append(this.getExceptionCount()).append(") are:");

      for(int i = 0; i < this.propertyAccessExceptions.length; ++i) {
         sb.append('\n').append("PropertyAccessException ").append(i + 1).append(": ");
         sb.append(this.propertyAccessExceptions[i]);
      }

      return sb.toString();
   }

   public void printStackTrace(PrintStream ps) {
      synchronized(ps) {
         ps.println(this.getClass().getName() + "; nested PropertyAccessException details (" + this.getExceptionCount() + ") are:");

         for(int i = 0; i < this.propertyAccessExceptions.length; ++i) {
            ps.println("PropertyAccessException " + (i + 1) + ":");
            this.propertyAccessExceptions[i].printStackTrace(ps);
         }

      }
   }

   public void printStackTrace(PrintWriter pw) {
      synchronized(pw) {
         pw.println(this.getClass().getName() + "; nested PropertyAccessException details (" + this.getExceptionCount() + ") are:");

         for(int i = 0; i < this.propertyAccessExceptions.length; ++i) {
            pw.println("PropertyAccessException " + (i + 1) + ":");
            this.propertyAccessExceptions[i].printStackTrace(pw);
         }

      }
   }

   public boolean contains(@Nullable Class exType) {
      if (exType == null) {
         return false;
      } else if (exType.isInstance(this)) {
         return true;
      } else {
         PropertyAccessException[] var2 = this.propertyAccessExceptions;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PropertyAccessException pae = var2[var4];
            if (pae.contains(exType)) {
               return true;
            }
         }

         return false;
      }
   }
}
