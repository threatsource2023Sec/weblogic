package com.bea.common.security.internal.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PasswordCredentialHelper {
   private boolean passwordCredentialClassDetected = false;
   private Class passwordCredentialClass = null;
   private Method pcGetUserName = null;
   private Constructor pcConstructor = null;

   public PasswordCredentialHelper() {
      try {
         char[] fooChar = new char[1];
         String fooStr = "f";
         Class[] paramArray = new Class[]{fooStr.getClass(), fooChar.getClass()};
         this.passwordCredentialClass = Class.forName("javax.resource.spi.security.PasswordCredential", false, this.getClass().getClassLoader());
         this.pcGetUserName = this.passwordCredentialClass.getMethod("getUserName", (Class[])null);
         this.pcConstructor = this.passwordCredentialClass.getConstructor(paramArray);
         if (this.passwordCredentialClass != null && this.pcGetUserName != null && this.pcConstructor != null) {
            this.passwordCredentialClassDetected = true;
         }
      } catch (ClassNotFoundException var4) {
      } catch (NoSuchMethodException var5) {
      }

   }

   public Object mapToNewPasswordCredential(Object credential) {
      if (!this.isPasswordCredential(credential)) {
         return null;
      } else {
         String username = this.getPasswordCredentialUsername(credential);
         Object newPasswordCredential = null;

         try {
            Object[] args = new Object[]{username, null};
            newPasswordCredential = this.pcConstructor.newInstance(args);
         } catch (InstantiationException var5) {
         } catch (IllegalAccessException var6) {
         } catch (IllegalArgumentException var7) {
         } catch (InvocationTargetException var8) {
         }

         return newPasswordCredential;
      }
   }

   public String getPasswordCredentialUsername(Object credential) {
      return !this.isPasswordCredential(credential) ? null : this.getUsername(credential);
   }

   private boolean isPasswordCredential(Object credential) {
      return this.passwordCredentialClassDetected && !this.passwordCredentialClass.isAssignableFrom(credential.getClass());
   }

   private String getUsername(Object credential) {
      String username = null;

      try {
         username = (String)this.pcGetUserName.invoke(credential, (Object[])null);
      } catch (IllegalAccessException var4) {
      } catch (IllegalArgumentException var5) {
      } catch (InvocationTargetException var6) {
      }

      return username;
   }
}
