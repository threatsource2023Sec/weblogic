package com.googlecode.cqengine.index.sqlite.support;

public class SQLiteIndexFlags {
   public static String BULK_IMPORT = "BULK_IMPORT";
   public static String BULK_IMPORT_SUSPEND_SYNC_AND_JOURNALING = "BULK_IMPORT_SUSPEND_SYNC_AND_JOURNALING";

   public static enum BulkImportExternallyManged {
      NOT_LAST,
      LAST;
   }
}
