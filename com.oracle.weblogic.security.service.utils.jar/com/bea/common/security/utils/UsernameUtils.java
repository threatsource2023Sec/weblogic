package com.bea.common.security.utils;

import weblogic.security.auth.callback.IdentityDomainNames;

public class UsernameUtils {
   private static final int MAX_USERNAME_LENGTH = 256;
   private static final int IDD_FORMAT_LENGTH = 3;
   private static final int ELLIPSES_LENGTH = 3;
   private static final int HALF_LENGTH = 128;

   public static String getTruncatedUsername(String username) {
      if (username == null) {
         return null;
      } else {
         return username.length() <= 256 ? username : username.substring(0, 253) + "...";
      }
   }

   public static String getTruncatedFormattedName(String username, String identityDomain) {
      int unameLen = username.length();
      int iddLen = identityDomain != null ? identityDomain.length() : 0;
      if (iddLen == 0) {
         return getTruncatedUsername(username);
      } else {
         int maxUnameLen = unameLen;
         int maxIddLen = iddLen;
         if (unameLen + iddLen > 253) {
            maxUnameLen = unameLen > 128 ? 128 : unameLen;
            maxIddLen = iddLen > 128 ? 128 : iddLen;
            int maxCombo = maxUnameLen + maxIddLen;
            if (maxCombo < 256) {
               if (maxUnameLen > maxIddLen) {
                  maxUnameLen += 256 - maxCombo;
               } else {
                  maxIddLen += 256 - maxCombo;
               }
            }
         }

         String truncatedName = unameLen <= maxUnameLen ? username : username.substring(0, maxUnameLen - 3) + "...";
         String truncatedIdd = iddLen <= maxIddLen ? identityDomain : identityDomain.substring(0, maxIddLen - 3 - 3) + "...";
         String name = formatUserName(truncatedName, truncatedIdd);
         return name == null ? null : name;
      }
   }

   public static String formatUserName(String username, String identityDomain) {
      return identityDomain != null && !identityDomain.isEmpty() ? username + " [" + identityDomain + "]" : username;
   }

   public static String formatUserName(IdentityDomainNames user) {
      return user == null ? null : formatUserName(user.getName(), user.getIdentityDomain());
   }
}
