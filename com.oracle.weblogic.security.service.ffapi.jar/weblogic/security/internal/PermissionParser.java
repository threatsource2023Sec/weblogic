package weblogic.security.internal;

import java.io.IOException;
import java.security.Permission;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.security.service.SecurityServiceException;

public class PermissionParser {
   private final String PERMISSION_ONLY_PATTERN = "\\s*permission\\s+(\\S+)\\s*;";
   private final String PERMISSION_TARGET_PATTERN = "\\s*permission\\s+(\\S+)\\s+\"(.*)\"\\s*;";
   private final String PERMISSION_TARGET_ACTION_PATTERN = "\\s*permission\\s+(\\S+)\\s+\"(.*)\"\\s*,\\s*\"(.*)\"\\s*;";
   private final String[] PERMISSION_PATTERNS = new String[]{"\\s*permission\\s+(\\S+)\\s*;", "\\s*permission\\s+(\\S+)\\s+\"(.*)\"\\s*,\\s*\"(.*)\"\\s*;", "\\s*permission\\s+(\\S+)\\s+\"(.*)\"\\s*;"};
   private String permStr;

   public static Permission parsePermission(String perm) throws SecurityServiceException {
      PermissionParser parser = new PermissionParser(perm);

      try {
         return parser.parse();
      } catch (IOException var3) {
         throw new SecurityServiceException(var3);
      }
   }

   private PermissionParser() {
   }

   public PermissionParser(String perm) {
      this.permStr = perm;
   }

   public Permission parse() throws SecurityServiceException, IOException {
      String[] permissionParts = new String[3];
      String[] var2 = this.PERMISSION_PATTERNS;
      int var3 = var2.length;

      label23:
      for(int var4 = 0; var4 < var3; ++var4) {
         String p = var2[var4];
         Matcher permissionMatcher = Pattern.compile(p).matcher(this.permStr);
         if (permissionMatcher.find()) {
            int i = 1;

            while(true) {
               if (i > permissionMatcher.groupCount()) {
                  break label23;
               }

               permissionParts[i - 1] = permissionMatcher.group(i);
               ++i;
            }
         }
      }

      Permission permission = MakePermission.makePermission(permissionParts[0], permissionParts[1], permissionParts[2]);
      return permission;
   }
}
