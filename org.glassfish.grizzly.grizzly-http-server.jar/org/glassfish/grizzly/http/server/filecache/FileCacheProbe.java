package org.glassfish.grizzly.http.server.filecache;

public interface FileCacheProbe {
   void onEntryAddedEvent(FileCache var1, FileCacheEntry var2);

   void onEntryRemovedEvent(FileCache var1, FileCacheEntry var2);

   void onEntryHitEvent(FileCache var1, FileCacheEntry var2);

   void onEntryMissedEvent(FileCache var1, String var2, String var3);

   void onErrorEvent(FileCache var1, Throwable var2);

   public static class Adapter implements FileCacheProbe {
      public void onEntryAddedEvent(FileCache fileCache, FileCacheEntry entry) {
      }

      public void onEntryRemovedEvent(FileCache fileCache, FileCacheEntry entry) {
      }

      public void onEntryHitEvent(FileCache fileCache, FileCacheEntry entry) {
      }

      public void onEntryMissedEvent(FileCache fileCache, String host, String requestURI) {
      }

      public void onErrorEvent(FileCache fileCache, Throwable error) {
      }
   }
}
