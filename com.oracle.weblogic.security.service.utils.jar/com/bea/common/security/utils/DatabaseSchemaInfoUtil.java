package com.bea.common.security.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class DatabaseSchemaInfoUtil {
   public static final String TABLE_NAME = "BEACSS_SCHEMA_VERSION";
   public static final String COLUMN_NAME = "CURRENT_VERSION";
   private static final int CURRENT_VERSION = 2;
   private static final String[] OLD_SAML_TABLE_NAMES = new String[]{"SAML2_IdP_AUDIENCEURI", "SAML2_IDP_PT_EP", "SAML2_IDP_REDIRECTURI", "SAML2_SP_AUDIENCEURI", "SAML2_SP_PT_EP", "SAML2_IDP_AUDIENCEURI"};
   private static final String[] NEW_SAML_TABLE_NAMES = new String[]{"BEASAML2_IDP_AUDIENCEURI", "BEASAML2_IDP_PT_EP", "BEASAML2_IDP_REDIRECTURI", "BEASAML2_SP_AUDIENCEURI", "BEASAML2_SP_PT_EP", "BEASAML2_IDP_AUDIENCEURI"};
   private static final Map renamedTableNameMapping = new HashMap();

   public static int getCurrentVersion() {
      return 2;
   }

   public static String getSQLForStoringVersion() {
      return "INSERT INTO BEACSS_SCHEMA_VERSION VALUES (" + getCurrentVersion() + ")";
   }

   public static void appenSqlToScriptFile(String filepath) throws IOException {
      PrintWriter writer = new PrintWriter(new FileWriter(filepath, true));

      try {
         writer.println(getSQLForStoringVersion() + ";");
         String schema = filepath.toLowerCase();
         if (schema.indexOf("sqlserver") < 0 && schema.indexOf("derby") < 0) {
            writer.println("COMMIT;");
         }

         writer.flush();
      } finally {
         writer.close();
      }

   }

   public static Map getRenamedTableNameMapping() {
      return renamedTableNameMapping;
   }

   public static void main(String[] args) throws IOException {
      if (args.length != 0 && args[0] != null && args[0].trim().length() != 0) {
         appenSqlToScriptFile(args[0]);
      } else {
         throw new IllegalArgumentException("No SQL script file specified.");
      }
   }

   static {
      for(int i = 0; i < OLD_SAML_TABLE_NAMES.length; ++i) {
         renamedTableNameMapping.put(OLD_SAML_TABLE_NAMES[i], NEW_SAML_TABLE_NAMES[i]);
      }

   }
}
