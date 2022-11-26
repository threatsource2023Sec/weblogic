package com.bea.common.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ValidationFailedException extends Exception {
   private Collection _errors = null;

   public ValidationFailedException() {
      this._errors = new ArrayList(5);
   }

   public ValidationFailedException(String message) {
      super(message);
      this._errors = new ArrayList(5);
      this.add(message);
   }

   public void add(String error) {
      if (error != null && error.trim().length() > 0) {
         this._errors.add(error);
      }

   }

   public void add(ValidationFailedException error) {
      if (error != null && error != this) {
         Collection cl = error.getErrors();
         if (cl != null) {
            Iterator ite = cl.iterator();

            while(ite.hasNext()) {
               this.add((String)ite.next());
            }
         }

      }
   }

   public Collection getErrors() {
      return this._errors;
   }

   public String getMessage() {
      String[] objs = (String[])((String[])this._errors.toArray(new String[0]));
      StringBuffer sb = new StringBuffer();

      for(int i = 0; objs != null && i < objs.length; ++i) {
         sb.append(i + 1);
         sb.append(". ");
         sb.append(objs[i]);
         sb.append("\r\n");
      }

      return sb.toString();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(this.getClass().toString());
      sb.append(":");
      sb.append(this.getMessage());
      return sb.toString();
   }
}
