package weblogic.diagnostics.harvester;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;

public class InstanceNameNormalizer {
   private String instanceNameSpecification = null;
   private String normalizedName = null;
   private boolean objectNamePattern = false;
   private boolean regexPattern = false;
   private boolean normalized = false;

   public InstanceNameNormalizer(String name) {
      if (name != null) {
         this.instanceNameSpecification = name.trim();
      }

   }

   public boolean isPattern() {
      return this.isRegexPattern() || this.isObjectNamePattern();
   }

   public boolean isRegexPattern() {
      if (!this.normalized) {
         this.translateHarvesterSpec();
      }

      return this.regexPattern;
   }

   public boolean isObjectNamePattern() {
      if (!this.normalized) {
         this.translateHarvesterSpec();
      }

      return this.objectNamePattern;
   }

   public synchronized String translateHarvesterSpec() {
      if (this.normalized) {
         return this.normalizedName;
      } else {
         if (this.instanceNameSpecification != null && !this.instanceNameSpecification.equals("*")) {
            boolean isObjectName = false;
            ObjectName on = null;

            try {
               on = new ObjectName(this.instanceNameSpecification);
               isObjectName = true;
               this.normalizedName = on.getCanonicalName();
               this.objectNamePattern = on.isPattern();
            } catch (Exception var4) {
               isObjectName = false;
            }

            if (!isObjectName) {
               this.regexPattern = this.instanceNameSpecification.contains("*");
               if (!this.regexPattern) {
                  throw new InvalidHarvesterInstanceNameException(DiagnosticsTextTextFormatter.getInstance().getInvalidHarvesterInstanceNameText(this.instanceNameSpecification));
               }

               this.normalizedName = this.normalizePattern(this.instanceNameSpecification);
               this.normalizedName = this.normalizedName.replaceAll("\\[", "\\\\[");
               this.normalizedName = this.normalizedName.replaceAll("\\]", "\\\\]");
               this.normalizedName = this.normalizedName.replaceAll("\\*", "(.*?)");
            }
         } else {
            this.normalizedName = null;
         }

         this.normalized = true;
         return this.normalizedName;
      }
   }

   private String normalizePattern(String namePattern) throws InvalidHarvesterInstanceNameException {
      this.scanInstanceName(this.instanceNameSpecification);
      String regex = namePattern;
      String[] parts = namePattern.split(":");
      if (parts.length == 2) {
         try {
            regex = this.normalizeObjectNamePattern(parts[0], parts[1]);
         } catch (InvalidSequence var5) {
         }
      } else if (parts.length > 2) {
         throw new InvalidHarvesterInstanceNameException(this.instanceNameSpecification);
      }

      return regex;
   }

   private String normalizeObjectNamePattern(String domain, String propertyList) throws InvalidHarvesterInstanceNameException, InvalidSequence {
      if (domain != null && propertyList != null) {
         String regex = "";
         String[] keypairs = propertyList.split(",");
         ArrayList keys = new ArrayList(keypairs.length);
         HashMap keyMap = new HashMap(keypairs.length);
         boolean trailingWildcard = false;
         String[] var8 = keypairs;
         int i = keypairs.length;

         for(int var10 = 0; var10 < i; ++var10) {
            String keypair = var8[var10];
            if (keypair != null) {
               keypair = keypair.trim();
               String[] nvpair = keypair.split("=");
               if (nvpair.length < 2) {
                  throw new InvalidSequence(keypair);
               }

               String name = nvpair[0];
               if (name == null || name.length() == 0) {
                  throw new InvalidSequence(keypair);
               }

               name = name.trim();
               if (name.contains("*")) {
                  throw new InvalidSequence(keypair);
               }

               keys.add(name);
               keyMap.put(name, keypair);
            }
         }

         Collections.sort(keys);
         String orderedPropertyList = "";

         for(i = 0; i < keys.size(); ++i) {
            String sortKey = (String)keys.get(i);
            orderedPropertyList = orderedPropertyList + (String)keyMap.get(sortKey);
            if (i < keys.size() - 1) {
               orderedPropertyList = orderedPropertyList + ",";
            }
         }

         regex = domain + ":" + orderedPropertyList;
         if (trailingWildcard) {
            regex = regex + ",*";
         }

         return regex;
      } else {
         throw new InvalidHarvesterInstanceNameException(this.instanceNameSpecification);
      }
   }

   private void scanInstanceName(String name) throws InvalidHarvesterInstanceNameException {
      if (name != null && name.length() != 0) {
         char[] charArray = new char[name.length()];
         name.getChars(0, name.length(), charArray, 0);
         char[] var3 = charArray;
         int var4 = charArray.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            char c = var3[var5];
            if (!this.isValidChar(c)) {
               throw new InvalidHarvesterInstanceNameException(name);
            }
         }

      } else {
         throw new InvalidHarvesterInstanceNameException(name);
      }
   }

   private boolean isValidChar(char c) {
      return Character.isLetterOrDigit(c) || c == ':' || c == '.' || c == '=' || c == '*' || c == '_' || c == ',' || c == '\\' || c == '?' || c == '"' || c == '[' || c == ']';
   }

   public static void main(String[] args) {
      String[] var1 = args;
      int var2 = args.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String testName = var1[var3];
         DebugLogger.println("Normalizing name: " + testName);

         try {
            InstanceNameNormalizer n = new InstanceNameNormalizer(testName);
            String normalizedName = n.translateHarvesterSpec();
            DebugLogger.println("Normalized name: " + normalizedName);
         } catch (InvalidHarvesterInstanceNameException var7) {
            DebugLogger.println("Normalization failed: " + var7.getMessage());
            var7.printStackTrace();
         }
      }

   }

   private static class InvalidSequence extends Exception {
      public InvalidSequence() {
      }

      public InvalidSequence(String msg) {
         super(msg);
      }
   }
}
