package weblogic.jdbc.rmi.internal;

import java.io.Serializable;

public final class RmiDriverSettings implements Serializable {
   private static final long serialVersionUID = -642147669176782253L;
   private boolean verbose = false;
   private int chunkSize = 256;
   private int rowCacheSize = 0;

   public RmiDriverSettings() {
   }

   public RmiDriverSettings(RmiDriverSettings s) {
      if (s != null) {
         this.verbose = s.verbose;
         this.chunkSize = s.chunkSize;
         this.rowCacheSize = s.rowCacheSize;
      }

   }

   public RmiDriverSettings(boolean is_verbose, int chunk_size, int row_cache_size) {
      this.verbose = is_verbose;
      this.chunkSize = chunk_size;
      this.rowCacheSize = row_cache_size;
   }

   public boolean isVerbose() {
      return this.verbose;
   }

   public int getChunkSize() {
      return this.chunkSize;
   }

   public int getRowCacheSize() {
      return this.rowCacheSize;
   }

   public void setVerbose(boolean is_verbose) {
      this.verbose = is_verbose;
   }

   public void setChunkSize(int c) {
      this.chunkSize = c;
   }

   public void setRowCacheSize(int s) {
      this.rowCacheSize = s;
   }

   public String toString() {
      String retval = this.getClass().getName() + "{verbose=" + this.verbose + " chunkSize=" + this.chunkSize + " rowCacheSize=" + this.rowCacheSize + "}";
      return retval;
   }
}
