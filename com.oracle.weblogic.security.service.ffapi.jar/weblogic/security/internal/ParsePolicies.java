package weblogic.security.internal;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.security.SecurityLogger;
import weblogic.security.service.InvalidParameterException;
import weblogic.security.service.SecurityServiceException;

public class ParsePolicies {
   private static final String PERMISSION_SYNTAX = "(permission[\\s|\\S]+?\\s*;)";
   private static final String OR = "|\\s*";
   private static final String LINE_END = "\\s*;";
   private static final String GRANT_BEGIN = "^\\s*grant\\s*\\{\\s*(\\s*";
   private static final String PERMISSION_ONLY = "permission\\s+(\\S+)";
   private static final String PERMISSION_AND_TARGET = "permission\\s+(\\S+)\\s+\"([\\S&&[^,\"]]+)\"";
   private static final String PERMISSION_AND_TARGET_AND_ACTIONS = "permission\\s+(\\S+)\\s+\"([\\S||[ ]]+)\"\\s*,\\s*\"([\\S|\\s]*)\"";
   private static final String GRANT_END = "\\s*)*\\s*}\\s*;\\s*$";
   private static final String GRANT_SYNTAX = "^\\s*grant\\s*\\{\\s*(\\s*permission\\s+(\\S+)\\s*;|\\s*permission\\s+(\\S+)\\s+\"([\\S&&[^,\"]]+)\"\\s*;|\\s*permission\\s+(\\S+)\\s+\"([\\S||[ ]]+)\"\\s*,\\s*\"([\\S|\\s]*)\"\\s*;\\s*)*\\s*}\\s*;\\s*$";

   public static PermissionCollection parseGrantPermissionsFromPolicyFile(String filePath, String codeBase) throws SecurityServiceException {
      PermissionCollection pc = null;
      if (filePath != null && codeBase != null) {
         FileInputStream fis = null;

         try {
            fis = new FileInputStream(filePath);
            pc = parseGrantPermissionsFromPolicyFile((InputStream)fis, codeBase);
         } catch (FileNotFoundException var12) {
            throw new InvalidParameterException(var12);
         } finally {
            if (fis != null) {
               try {
                  fis.close();
               } catch (IOException var11) {
                  throw new SecurityServiceException(var11);
               }
            }

         }
      }

      return pc;
   }

   static PermissionCollection parseGrantPermissionsFromPolicyFile(InputStream inputStream, String codeBase) throws SecurityServiceException {
      PermissionCollection pc = null;
      if (inputStream != null) {
         InputStreamReader isr = new InputStreamReader(inputStream);
         BufferedReader br = new BufferedReader(isr);

         try {
            String line = null;
            boolean grantFound = false;
            String grantPattern = "^\\s*grant\\s+code[B|b]ase\\s+\"" + codeBase + "\"\\s*\\{\\s*$";
            String endingBlockPattern = "^\\s*\\}\\s*;\\s*$";

            while(true) {
               while((line = br.readLine()) != null) {
                  if (!grantFound && line.matches(grantPattern)) {
                     pc = new Permissions();
                     grantFound = true;
                  } else if (grantFound) {
                     if (line.matches(endingBlockPattern)) {
                        return pc;
                     }

                     String trimmedLine = line.trim();
                     if (trimmedLine != null && trimmedLine.startsWith("permission")) {
                        pc.add(parsePermission(trimmedLine.contains("\\\"") ? trimmedLine.replace("\\\"", "\"") : trimmedLine));
                     }
                  }
               }

               return pc;
            }
         } catch (IOException var17) {
            throw new InvalidParameterException(var17);
         } finally {
            try {
               br.close();
               isr.close();
            } catch (IOException var16) {
               throw new InvalidParameterException(var16);
            }
         }
      } else {
         return pc;
      }
   }

   public static PermissionCollection parseGrantStatement(String statement) {
      if (statement == null) {
         return null;
      } else {
         String prop = System.getProperty("weblogic.security.oldGrantParse", "false");
         if (prop != null && prop.equals("true")) {
            PermissionCollectionHolder h = new PermissionCollectionHolder();
            parseGrantStatement(statement, 0, true, h);
            return h.get();
         } else {
            PermissionCollection pc = new Permissions();
            if (!Pattern.matches("^\\s*grant\\s*\\{\\s*(\\s*permission\\s+(\\S+)\\s*;|\\s*permission\\s+(\\S+)\\s+\"([\\S&&[^,\"]]+)\"\\s*;|\\s*permission\\s+(\\S+)\\s+\"([\\S||[ ]]+)\"\\s*,\\s*\"([\\S|\\s]*)\"\\s*;\\s*)*\\s*}\\s*;\\s*$", statement)) {
               throw new InvalidParameterException(SecurityLogger.getInvalidGrantSyntax(statement));
            } else {
               Matcher grantMatcher = Pattern.compile("(permission[\\s|\\S]+?\\s*;)").matcher(statement);

               while(grantMatcher.find()) {
                  String permStr = grantMatcher.group(1);
                  Permission permission = null;

                  try {
                     permission = parsePermission(permStr);
                  } catch (SecurityServiceException var7) {
                     throw new InvalidParameterException(var7);
                  }

                  if (permission != null) {
                     pc.add(permission);
                  }
               }

               return pc;
            }
         }
      }
   }

   public static PermissionCollection parseMultipleGrantStatements(String[] grantStatements) {
      PermissionCollection permissionCollection = null;
      if (grantStatements != null && grantStatements.length > 0) {
         permissionCollection = new Permissions();
         String[] var2 = grantStatements;
         int var3 = grantStatements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String grant = var2[var4];
            PermissionCollection pc = parseGrantStatement(grant);
            if (pc != null) {
               Enumeration permissionEnumeration = pc.elements();

               while(permissionEnumeration.hasMoreElements()) {
                  permissionCollection.add((Permission)permissionEnumeration.nextElement());
               }
            }
         }
      }

      return permissionCollection;
   }

   private static Permission parsePermission(String permStr) throws SecurityServiceException {
      Matcher permMatcher = null;
      String regEx = null;
      Permission permission = null;
      permission = PermissionParser.parsePermission(permStr);
      return permission;
   }

   private static int parseGrantStatement(String statement, int pos, boolean mustComplete, PermissionCollectionHolder h) {
      try {
         Permissions perms = new Permissions();
         int i = skipWhitespace(statement, pos);
         i = skipConstantString(statement, i, "grant");

         for(i = skipConstantString(statement, i, "{"); !lookingAt(statement, i, "}"); i = parsePermissionStatement(statement, i, perms)) {
         }

         i = skipConstantString(statement, i, "}");
         i = skipConstantString(statement, i, ";");
         if (mustComplete && i != statement.length()) {
            throw new InvalidParameterException(SecurityLogger.getTrailingTextAfterGrant());
         } else {
            h.set(perms);
            return i;
         }
      } catch (StringIndexOutOfBoundsException var6) {
         throw new InvalidParameterException(SecurityLogger.getUnexpectedEndOfGrant());
      }
   }

   public static int parsePermissionStatement(String statement, int pos, Permissions perms) {
      StringHolder holder = new StringHolder();
      int i = skipConstantString(statement, pos, "permission");
      i = getToken(statement, i, holder);
      String permissionType = holder.get();
      String target = null;
      String action = null;
      if (lookingAt(statement, i, "\"")) {
         int commaLoc = statement.indexOf(",", i);
         int endTarget;
         if (-1 != commaLoc) {
            endTarget = commaLoc;
         } else {
            endTarget = statement.indexOf(";", i);
         }

         target = statement.substring(i, endTarget);
         i += target.length();
         target = stripSlash(target);
         target = target.substring(1, target.length() - 1);
         if (lookingAt(statement, i, ",")) {
            i = skipConstantString(statement, i, ",");
            i = getString(statement, i, holder);
            action = holder.get();
         }
      }

      i = skipConstantString(statement, i, ";");

      Permission perm;
      try {
         perm = MakePermission.makePermissionOld(permissionType, target, action);
      } catch (SecurityServiceException var10) {
         throw new InvalidParameterException(var10);
      }

      perms.add(perm);
      return i;
   }

   public static String stripSlash(String s) {
      for(int index = s.indexOf("\\"); index != -1; index = s.indexOf("\\")) {
         s = s.substring(0, index) + s.substring(index + 1, s.length());
      }

      return s;
   }

   private static boolean lookingAt(String s, int pos, String constant) {
      int len = constant.length();
      String string = s.substring(pos, pos + len);
      return string.equals(constant);
   }

   private static int skipConstantString(String s, int pos, String constant) {
      int len = constant.length();
      String string = s.substring(pos, pos + len);
      if (!string.equals(constant)) {
         throw new InvalidParameterException(SecurityLogger.getExpectedConstantButFound(constant, string));
      } else {
         return skipWhitespace(s, pos + len);
      }
   }

   private static int getString(String s, int pos, StringHolder h) {
      if (!lookingAt(s, pos, "\"")) {
         throw new InvalidParameterException(SecurityLogger.getExpectedQuoteButFound(s.substring(pos, pos + 1)));
      } else {
         int i;
         for(i = pos + 1; s.charAt(i) != '"'; ++i) {
         }

         h.set(s.substring(pos + 1, i));
         ++i;
         return skipWhitespace(s, i);
      }
   }

   private static int getToken(String s, int pos, StringHolder h) {
      int i;
      char c;
      for(i = pos; !Character.isWhitespace(c = s.charAt(i)) && c != ';'; ++i) {
      }

      h.set(s.substring(pos, i));
      return skipWhitespace(s, i);
   }

   private static int skipWhitespace(String s, int pos) {
      int i = pos;

      try {
         while(Character.isWhitespace(s.charAt(i))) {
            ++i;
         }
      } catch (StringIndexOutOfBoundsException var4) {
      }

      return i;
   }

   private static void outln(String s) {
      System.out.println(s);
   }

   private static void out(String s) {
      System.out.print(s);
   }
}
