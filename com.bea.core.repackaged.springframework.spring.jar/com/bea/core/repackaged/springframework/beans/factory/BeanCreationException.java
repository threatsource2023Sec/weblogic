package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.beans.FatalBeanException;
import com.bea.core.repackaged.springframework.core.NestedRuntimeException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeanCreationException extends FatalBeanException {
   @Nullable
   private final String beanName;
   @Nullable
   private final String resourceDescription;
   @Nullable
   private List relatedCauses;

   public BeanCreationException(String msg) {
      super(msg);
      this.beanName = null;
      this.resourceDescription = null;
   }

   public BeanCreationException(String msg, Throwable cause) {
      super(msg, cause);
      this.beanName = null;
      this.resourceDescription = null;
   }

   public BeanCreationException(String beanName, String msg) {
      super("Error creating bean with name '" + beanName + "': " + msg);
      this.beanName = beanName;
      this.resourceDescription = null;
   }

   public BeanCreationException(String beanName, String msg, Throwable cause) {
      this(beanName, msg);
      this.initCause(cause);
   }

   public BeanCreationException(@Nullable String resourceDescription, @Nullable String beanName, String msg) {
      super("Error creating bean with name '" + beanName + "'" + (resourceDescription != null ? " defined in " + resourceDescription : "") + ": " + msg);
      this.resourceDescription = resourceDescription;
      this.beanName = beanName;
      this.relatedCauses = null;
   }

   public BeanCreationException(@Nullable String resourceDescription, String beanName, String msg, Throwable cause) {
      this(resourceDescription, beanName, msg);
      this.initCause(cause);
   }

   @Nullable
   public String getResourceDescription() {
      return this.resourceDescription;
   }

   @Nullable
   public String getBeanName() {
      return this.beanName;
   }

   public void addRelatedCause(Throwable ex) {
      if (this.relatedCauses == null) {
         this.relatedCauses = new ArrayList();
      }

      this.relatedCauses.add(ex);
   }

   @Nullable
   public Throwable[] getRelatedCauses() {
      return this.relatedCauses == null ? null : (Throwable[])this.relatedCauses.toArray(new Throwable[0]);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(super.toString());
      if (this.relatedCauses != null) {
         Iterator var2 = this.relatedCauses.iterator();

         while(var2.hasNext()) {
            Throwable relatedCause = (Throwable)var2.next();
            sb.append("\nRelated cause: ");
            sb.append(relatedCause);
         }
      }

      return sb.toString();
   }

   public void printStackTrace(PrintStream ps) {
      synchronized(ps) {
         super.printStackTrace(ps);
         if (this.relatedCauses != null) {
            Iterator var3 = this.relatedCauses.iterator();

            while(var3.hasNext()) {
               Throwable relatedCause = (Throwable)var3.next();
               ps.println("Related cause:");
               relatedCause.printStackTrace(ps);
            }
         }

      }
   }

   public void printStackTrace(PrintWriter pw) {
      synchronized(pw) {
         super.printStackTrace(pw);
         if (this.relatedCauses != null) {
            Iterator var3 = this.relatedCauses.iterator();

            while(var3.hasNext()) {
               Throwable relatedCause = (Throwable)var3.next();
               pw.println("Related cause:");
               relatedCause.printStackTrace(pw);
            }
         }

      }
   }

   public boolean contains(@Nullable Class exClass) {
      if (super.contains(exClass)) {
         return true;
      } else {
         if (this.relatedCauses != null) {
            Iterator var2 = this.relatedCauses.iterator();

            while(var2.hasNext()) {
               Throwable relatedCause = (Throwable)var2.next();
               if (relatedCause instanceof NestedRuntimeException && ((NestedRuntimeException)relatedCause).contains(exClass)) {
                  return true;
               }
            }
         }

         return false;
      }
   }
}
