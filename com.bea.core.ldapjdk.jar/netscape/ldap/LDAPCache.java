package netscape.ldap;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.zip.CRC32;
import netscape.ldap.util.DN;

public class LDAPCache implements Serializable {
   static final long serialVersionUID = 6275167993337814294L;
   private Hashtable m_cache;
   private Vector m_orderedStruct;
   private long m_timeToLive;
   private long m_maxSize;
   private String[] m_dns;
   private long m_remainingSize = 0L;
   private int m_refCnt = 0;
   public static final String DELIM = "#";
   private TTLTimer m_timer = null;
   private long m_totalOpers = 0L;
   private long m_hits = 0L;
   private long m_flushes = 0L;
   private static boolean m_debug = false;

   public LDAPCache(long var1, long var3) {
      this.init(var1, var3);
   }

   public LDAPCache(long var1, long var3, String[] var5) {
      this.init(var1, var3);
      this.m_dns = new String[var5.length];
      if (var5 != null && var5.length > 0) {
         for(int var6 = 0; var6 < var5.length; ++var6) {
            this.m_dns[var6] = (new DN(var5[var6])).toString();
         }
      }

   }

   public long getSize() {
      return this.m_maxSize;
   }

   public long getTimeToLive() {
      return this.m_timeToLive / 1000L;
   }

   public String[] getBaseDNs() {
      return this.m_dns;
   }

   public synchronized boolean flushEntries(String var1, int var2) {
      if (m_debug) {
         System.out.println("DEBUG: User request for flushing entry: dn " + var1 + " and scope " + var2);
      }

      if (var1 == null) {
         this.m_remainingSize = this.m_maxSize;
         this.m_cache.clear();
         this.m_orderedStruct.removeAllElements();
         this.m_totalOpers = this.m_hits = this.m_flushes = 0L;
         return true;
      } else {
         DN var3 = new DN(var1);
         Enumeration var4 = this.m_cache.keys();

         Long var5;
         int var7;
         int var8;
         do {
            if (!var4.hasMoreElements()) {
               if (m_debug) {
                  System.out.println("DEBUG: The number of keys in the cache is " + this.m_cache.size());
               }

               return false;
            }

            var5 = (Long)var4.nextElement();
            Vector var6 = (Vector)this.m_cache.get(var5);
            var7 = 1;

            for(var8 = var6.size(); var7 < var8; ++var7) {
               String var9 = ((LDAPEntry)var6.elementAt(var7)).getDN();
               DN var10 = new DN(var9);
               if (var10.equals(var3)) {
                  break;
               }

               if (var2 == 1) {
                  DN var11 = var10.getParent();
                  if (var11.equals(var3)) {
                     break;
                  }
               }

               if (var2 == 2 && var10.isDescendantOf(var3)) {
                  break;
               }
            }
         } while(var7 >= var8);

         for(int var12 = 0; var12 < this.m_orderedStruct.size(); ++var12) {
            Vector var14 = (Vector)this.m_orderedStruct.elementAt(var12);
            if (var5.equals((Long)var14.elementAt(0))) {
               this.m_orderedStruct.removeElementAt(var12);
               break;
            }
         }

         Vector var13 = (Vector)this.m_cache.remove(var5);
         this.m_remainingSize += (Long)var13.firstElement();
         if (m_debug) {
            System.out.println("DEBUG: Successfully removed entry ->" + var5);
         }

         return true;
      }
   }

   public long getAvailableSize() {
      return this.m_remainingSize;
   }

   public long getTotalOperations() {
      return this.m_totalOpers;
   }

   public long getNumMisses() {
      return this.m_totalOpers - this.m_hits;
   }

   public long getNumHits() {
      return this.m_hits;
   }

   public long getNumFlushes() {
      return this.m_flushes;
   }

   Long createKey(String var1, int var2, String var3, String var4, int var5, String[] var6, String var7, LDAPConstraints var8) throws LDAPException {
      DN var9 = new DN(var3);
      var3 = var9.toString();
      if (this.m_dns != null) {
         int var10;
         for(var10 = 0; var10 < this.m_dns.length && !var3.equals(this.m_dns[var10]); ++var10) {
         }

         if (var10 >= this.m_dns.length) {
            throw new LDAPException(var3 + " is not a cached base DN", 80);
         }
      }

      String var18 = null;
      var18 = this.appendString(var3);
      var18 = var18 + this.appendString(var5);
      var18 = var18 + this.appendString(var1);
      var18 = var18 + this.appendString(var2);
      var18 = var18 + this.appendString(var4);
      var18 = var18 + this.appendString(var6);
      var18 = var18 + this.appendString(var7);
      LDAPControl[] var11 = null;
      LDAPControl[] var12 = null;
      if (var8 != null) {
         var11 = var8.getServerControls();
         var12 = var8.getClientControls();
      }

      String[] var13;
      int var14;
      LDAPControl var15;
      long var16;
      if (var11 != null && var11.length > 0) {
         var13 = new String[var11.length];

         for(var14 = 0; var14 < var11.length; ++var14) {
            var15 = var11[var14];
            var16 = this.getCRC32(var15.getValue());
            var13[var14] = var15.getID() + var15.isCritical() + (new Long(var16)).toString();
         }

         var18 = var18 + this.appendString(var13);
      } else {
         var18 = var18 + this.appendString(0);
      }

      if (var12 != null && var12.length > 0) {
         var13 = new String[var12.length];

         for(var14 = 0; var14 < var12.length; ++var14) {
            var15 = var12[var14];
            var16 = this.getCRC32(var15.getValue());
            var13[var14] = var15.getID() + var15.isCritical() + (new Long(var16)).toString();
         }

         var18 = var18 + this.appendString(var13);
      } else {
         var18 = var18 + this.appendString(0);
      }

      long var19 = this.getCRC32(var18.getBytes());
      if (m_debug) {
         System.out.println("key=" + var19 + " for " + var18);
      }

      return new Long(var19);
   }

   synchronized Object getEntry(Long var1) {
      Object var2 = null;
      var2 = this.m_cache.get(var1);
      ++this.m_totalOpers;
      if (m_debug) {
         if (var2 == null) {
            System.out.println("DEBUG: Entry whose key -> " + var1 + " not found in the cache.");
         } else {
            System.out.println("DEBUG: Entry whose key -> " + var1 + " found in the cache.");
         }
      }

      if (var2 != null) {
         ++this.m_hits;
      }

      return var2;
   }

   synchronized void flushEntries() {
      Vector var1 = null;
      boolean var2 = false;
      long var3 = System.currentTimeMillis();

      for(this.m_flushes = 0L; this.m_orderedStruct.size() > 0; ++this.m_flushes) {
         var1 = (Vector)this.m_orderedStruct.firstElement();
         long var5 = var3 - (Long)var1.elementAt(1);
         if (var5 < this.m_timeToLive) {
            break;
         }

         Long var7 = (Long)var1.elementAt(0);
         if (m_debug) {
            System.out.println("DEBUG: Timer flush entry whose key is " + var7);
         }

         Vector var8 = (Vector)this.m_cache.remove(var7);
         this.m_remainingSize += (Long)var8.firstElement();
         this.m_orderedStruct.removeElementAt(0);
      }

      if (m_debug) {
         System.out.println("DEBUG: The number of keys in the cache is " + this.m_cache.size());
      }

   }

   synchronized boolean addEntry(Long var1, Object var2) {
      if (this.m_cache.get(var1) != null) {
         return false;
      } else {
         Vector var3 = (Vector)var2;
         long var4 = (Long)var3.elementAt(0);
         if (var4 > this.m_maxSize) {
            if (m_debug) {
               System.out.println("Failed to add an entry to the cache since the new entry exceeds the cache size");
            }

            return false;
         } else {
            Vector var6;
            if (var4 > this.m_remainingSize) {
               do {
                  var6 = (Vector)this.m_orderedStruct.firstElement();
                  Long var7 = (Long)var6.elementAt(0);
                  Vector var8 = (Vector)this.m_cache.remove(var7);
                  if (m_debug) {
                     System.out.println("DEBUG: The spare size of the cache is not big enough to hold the new entry, deleting the entry whose key -> " + var7);
                  }

                  this.m_orderedStruct.removeElementAt(0);
                  this.m_remainingSize += (Long)var8.elementAt(0);
               } while(this.m_remainingSize < var4);
            }

            this.m_remainingSize -= var4;
            this.m_cache.put(var1, var3);
            var6 = new Vector(2);
            var6.addElement(var1);
            var6.addElement(new Long(System.currentTimeMillis()));
            this.m_orderedStruct.addElement(var6);
            if (this.m_orderedStruct.size() == 1) {
               this.scheduleTTLTimer();
            }

            if (m_debug) {
               System.out.println("DEBUG: Adding a new entry whose key -> " + var1);
               System.out.println("DEBUG: The current number of keys in the cache " + this.m_cache.size());
            }

            return true;
         }
      }
   }

   synchronized void scheduleTTLTimer() {
      if (this.m_orderedStruct.size() > 0) {
         if (this.m_timer == null) {
            this.m_timer = new TTLTimer(this);
         }

         Vector var1 = (Vector)this.m_orderedStruct.firstElement();
         long var2 = System.currentTimeMillis();
         long var4 = (Long)var1.elementAt(1);
         long var6 = var4 + this.m_timeToLive - var2;
         if (var6 > 0L) {
            this.m_timer.start(var6);
         } else {
            this.flushEntries();
            this.scheduleTTLTimer();
         }

      }
   }

   public int getNumEntries() {
      return this.m_cache.size();
   }

   int getRefCount() {
      return this.m_refCnt;
   }

   synchronized void addReference() {
      ++this.m_refCnt;
      if (m_debug) {
         System.err.println("Cache refCnt=" + this.m_refCnt);
      }

   }

   synchronized void removeReference() {
      if (this.m_refCnt > 0) {
         --this.m_refCnt;
         if (m_debug) {
            System.err.println("Cache refCnt=" + this.m_refCnt);
         }

         if (this.m_refCnt == 0) {
            this.cleanup();
         }
      }

   }

   synchronized void cleanup() {
      this.flushEntries((String)null, 0);
      if (this.m_timer != null) {
         this.m_timer.stop();
         this.m_timer = null;
      }

   }

   private void init(long var1, long var3) {
      this.m_cache = new Hashtable();
      this.m_timeToLive = var1 * 1000L;
      this.m_maxSize = var3;
      this.m_remainingSize = var3;
      this.m_dns = null;
      this.m_orderedStruct = new Vector();
   }

   private String appendString(String var1) {
      return var1 == null ? "null#" : var1.trim() + "#";
   }

   private String appendString(int var1) {
      return var1 + "#";
   }

   private String appendString(String[] var1) {
      if (var1 != null && var1.length >= 1) {
         String[] var2 = new String[var1.length];
         System.arraycopy(var1, 0, var2, 0, var1.length);
         this.sortStrings(var2);
         String var3 = var2.length + "#";

         for(int var4 = 0; var4 < var2.length; ++var4) {
            var3 = var3 + var2[var4].trim() + "#";
         }

         return var3;
      } else {
         return "0#";
      }
   }

   private void sortStrings(String[] var1) {
      int var2;
      for(var2 = 0; var2 < var1.length; ++var2) {
         var1[var2] = var1[var2].trim();
      }

      for(var2 = 0; var2 < var1.length - 1; ++var2) {
         for(int var3 = var2 + 1; var3 < var1.length; ++var3) {
            if (var1[var2].compareTo(var1[var3]) > 0) {
               String var4 = var1[var2];
               var1[var2] = var1[var3];
               var1[var3] = var4;
            }
         }
      }

   }

   private long getCRC32(byte[] var1) {
      if (var1 == null) {
         return 0L;
      } else {
         CRC32 var2 = new CRC32();
         var2.update(var1);
         return var2.getValue();
      }
   }

   static {
      try {
         String var0 = System.getProperty("debug.cache");
         m_debug = var0 != null;
      } catch (Exception var1) {
      }

   }
}
