package com.bea.core.repackaged.jdt.internal.compiler.env;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;

public class AccessRuleSet {
   private AccessRule[] accessRules;
   public byte classpathEntryType;
   public String classpathEntryName;

   public AccessRuleSet(AccessRule[] accessRules, byte classpathEntryType, String classpathEntryName) {
      this.accessRules = accessRules;
      this.classpathEntryType = classpathEntryType;
      this.classpathEntryName = classpathEntryName;
   }

   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof AccessRuleSet)) {
         return false;
      } else {
         AccessRuleSet otherRuleSet = (AccessRuleSet)object;
         if (this.classpathEntryType == otherRuleSet.classpathEntryType && (this.classpathEntryName != null || otherRuleSet.classpathEntryName == null) && this.classpathEntryName.equals(otherRuleSet.classpathEntryName)) {
            int rulesLength = this.accessRules.length;
            if (rulesLength != otherRuleSet.accessRules.length) {
               return false;
            } else {
               for(int i = 0; i < rulesLength; ++i) {
                  if (!this.accessRules[i].equals(otherRuleSet.accessRules[i])) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }

   public AccessRule[] getAccessRules() {
      return this.accessRules;
   }

   public AccessRestriction getViolatedRestriction(char[] targetTypeFilePath) {
      int i = 0;

      for(int length = this.accessRules.length; i < length; ++i) {
         AccessRule accessRule = this.accessRules[i];
         if (CharOperation.pathMatch(accessRule.pattern, targetTypeFilePath, true, '/')) {
            switch (accessRule.getProblemId()) {
               case 16777496:
               case 16777523:
                  return new AccessRestriction(accessRule, this.classpathEntryType, this.classpathEntryName);
               default:
                  return null;
            }
         }
      }

      return null;
   }

   public int hashCode() {
      int result = 1;
      result = 31 * result + this.hashCode(this.accessRules);
      result = 31 * result + (this.classpathEntryName == null ? 0 : this.classpathEntryName.hashCode());
      result = 31 * result + this.classpathEntryType;
      return result;
   }

   private int hashCode(AccessRule[] rules) {
      if (rules == null) {
         return 0;
      } else {
         int result = 1;
         int i = 0;

         for(int length = rules.length; i < length; ++i) {
            result = 31 * result + (rules[i] == null ? 0 : rules[i].hashCode());
         }

         return result;
      }
   }

   public String toString() {
      return this.toString(true);
   }

   public String toString(boolean wrap) {
      StringBuffer buffer = new StringBuffer(200);
      buffer.append("AccessRuleSet {");
      if (wrap) {
         buffer.append('\n');
      }

      int i = 0;

      for(int length = this.accessRules.length; i < length; ++i) {
         if (wrap) {
            buffer.append('\t');
         }

         AccessRule accessRule = this.accessRules[i];
         buffer.append(accessRule);
         if (wrap) {
            buffer.append('\n');
         } else if (i < length - 1) {
            buffer.append(", ");
         }
      }

      buffer.append("} [classpath entry: ");
      buffer.append(this.classpathEntryName);
      buffer.append("]");
      return buffer.toString();
   }
}
