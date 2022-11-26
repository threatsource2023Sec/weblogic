package netscape.ldap;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

public class LDAPSearchResults implements Enumeration, Serializable {
   static final long serialVersionUID = -501692208613904825L;
   private Vector entries;
   private LDAPSearchListener resultSource;
   private boolean searchComplete;
   private LDAPConnection connectionToClose;
   private LDAPConnection currConn;
   private boolean persistentSearch;
   private LDAPSearchConstraints currCons;
   private String currBase;
   private int currScope;
   private String currFilter;
   private String[] currAttrs;
   private boolean currAttrsOnly;
   private Vector referralResults;
   private Vector exceptions;
   private int msgID;
   private boolean firstResult;

   public LDAPSearchResults() {
      this.entries = null;
      this.searchComplete = false;
      this.persistentSearch = false;
      this.referralResults = new Vector();
      this.msgID = -1;
      this.firstResult = false;
      this.entries = new Vector();
      this.connectionToClose = null;
      this.searchComplete = true;
      this.currCons = new LDAPSearchConstraints();
   }

   LDAPSearchResults(LDAPConnection var1, LDAPSearchConstraints var2, String var3, int var4, String var5, String[] var6, boolean var7) {
      this();
      this.currConn = var1;
      this.currCons = var2;
      this.currBase = var3;
      this.currScope = var4;
      this.currFilter = var5;
      this.currAttrs = var6;
      this.currAttrsOnly = var7;
   }

   LDAPSearchResults(Vector var1) {
      this();
      this.entries = (Vector)var1.clone();
      if (this.entries != null && this.entries.size() >= 1) {
         this.entries.removeElementAt(0);
      }

   }

   LDAPSearchResults(Vector var1, LDAPConnection var2, LDAPSearchConstraints var3, String var4, int var5, String var6, String[] var7, boolean var8) {
      this(var1);
      this.currConn = var2;
      this.currCons = var3;
      this.currBase = var4;
      this.currScope = var5;
      this.currFilter = var6;
      this.currAttrs = var7;
      this.currAttrsOnly = var8;
   }

   void add(LDAPMessage var1) {
      if (var1 instanceof LDAPSearchResult) {
         this.entries.addElement(((LDAPSearchResult)var1).getEntry());
      } else if (var1 instanceof LDAPSearchResultReference) {
         String[] var2 = ((LDAPSearchResultReference)var1).getUrls();
         if (var2 != null) {
            if (this.exceptions == null) {
               this.exceptions = new Vector();
            }

            this.exceptions.addElement(new LDAPReferralException((String)null, 0, var2));
         }
      }

   }

   void add(LDAPException var1) {
      if (this.exceptions == null) {
         this.exceptions = new Vector();
      }

      this.exceptions.addElement(var1);
   }

   void associate(LDAPSearchListener var1) {
      this.resultSource = var1;
      this.searchComplete = false;
   }

   void associatePersistentSearch(LDAPSearchListener var1) {
      this.resultSource = var1;
      this.persistentSearch = true;
      this.searchComplete = false;
      this.firstResult = true;
   }

   void addReferralEntries(LDAPSearchResults var1) {
      this.referralResults.addElement(var1);
   }

   void closeOnCompletion(LDAPConnection var1) {
      if (this.searchComplete) {
         try {
            var1.disconnect();
         } catch (LDAPException var3) {
         }
      } else {
         this.connectionToClose = var1;
      }

   }

   void quicksort(LDAPEntry[] var1, LDAPEntryComparator var2, int var3, int var4) {
      if (var3 < var4) {
         LDAPEntry var5 = var1[var3];
         int var6 = var3 - 1;
         int var7 = var4 + 1;

         while(true) {
            do {
               --var7;
            } while(var2.isGreater(var1[var7], var5));

            do {
               ++var6;
            } while(var2.isGreater(var5, var1[var6]));

            if (var6 >= var7) {
               this.quicksort(var1, var2, var3, var7);
               this.quicksort(var1, var2, var7 + 1, var4);
               return;
            }

            LDAPEntry var8 = var1[var6];
            var1[var6] = var1[var7];
            var1[var7] = var8;
         }
      }
   }

   void setMsgID(int var1) {
      this.msgID = var1;
   }

   public LDAPControl[] getResponseControls() {
      return this.currConn.getResponseControls(this.msgID);
   }

   public synchronized void sort(LDAPEntryComparator var1) {
      while(!this.searchComplete) {
         this.fetchResult();
      }

      if (this.currCons.getReferrals()) {
         while(this.referralResults.size() > 0) {
            Object var2 = null;
            if ((var2 = this.nextReferralElement()) != null) {
               if (var2 instanceof LDAPException) {
                  this.add((LDAPException)var2);
               } else {
                  this.entries.addElement(var2);
               }
            }
         }
      }

      int var5 = this.entries.size();
      if (var5 > 0) {
         LDAPEntry[] var3 = new LDAPEntry[var5];
         this.entries.copyInto(var3);
         if (var3.length > 1) {
            this.quicksort(var3, var1, 0, var5 - 1);
         }

         this.entries.removeAllElements();

         for(int var4 = 0; var4 < var5; ++var4) {
            this.entries.addElement(var3[var4]);
         }

      }
   }

   public LDAPEntry next() throws LDAPException {
      Object var1 = this.nextElement();
      if (!(var1 instanceof LDAPReferralException) && !(var1 instanceof LDAPException)) {
         return var1 instanceof LDAPEntry ? (LDAPEntry)var1 : null;
      } else {
         throw (LDAPException)var1;
      }
   }

   public Object nextElement() {
      Object var1;
      if (this.entries.size() > 0) {
         var1 = this.entries.elementAt(0);
         this.entries.removeElementAt(0);
         return var1;
      } else if (this.referralResults.size() > 0) {
         return this.nextReferralElement();
      } else if (this.exceptions != null && this.exceptions.size() > 0) {
         var1 = this.exceptions.elementAt(0);
         this.exceptions.removeElementAt(0);
         return var1;
      } else {
         return null;
      }
   }

   Object nextReferralElement() {
      LDAPSearchResults var1 = (LDAPSearchResults)this.referralResults.elementAt(0);
      if ((var1.persistentSearch || !var1.hasMoreElements()) && !var1.persistentSearch) {
         this.referralResults.removeElementAt(0);
      } else {
         Object var2 = var1.nextElement();
         if (var2 != null) {
            return var2;
         }

         if (var2 == null || !var1.hasMoreElements()) {
            this.referralResults.removeElementAt(0);
         }
      }

      return null;
   }

   public boolean hasMoreElements() {
      while(this.entries.size() == 0 && !this.searchComplete) {
         this.fetchResult();
      }

      if (this.entries.size() == 0 && (this.exceptions == null || this.exceptions.size() == 0)) {
         while(this.referralResults.size() > 0) {
            LDAPSearchResults var1 = (LDAPSearchResults)this.referralResults.elementAt(0);
            if (var1.hasMoreElements()) {
               return true;
            }

            this.referralResults.removeElementAt(0);
         }
      }

      return this.entries.size() > 0 || this.exceptions != null && this.exceptions.size() > 0;
   }

   public int getCount() {
      while(this.resultSource != null && this.resultSource.getMessageCount() > 0) {
         this.fetchResult();
      }

      int var1 = this.entries.size();

      for(int var2 = 0; var2 < this.referralResults.size(); ++var2) {
         LDAPSearchResults var3 = (LDAPSearchResults)this.referralResults.elementAt(var2);
         var1 += var3.getCount();
      }

      if (this.exceptions != null) {
         var1 += this.exceptions.size();
      }

      return var1;
   }

   int getMessageID() {
      return this.resultSource == null ? -1 : this.resultSource.getMessageID();
   }

   private synchronized void fetchResult() {
      if (this.resultSource != null) {
         synchronized(this) {
            if (this.searchComplete || this.firstResult) {
               this.firstResult = false;
               return;
            }

            LDAPMessage var2 = null;

            try {
               var2 = this.resultSource.nextMessage();
            } catch (LDAPException var16) {
               this.add(var16);
               this.currConn.releaseSearchListener(this.resultSource);
               this.searchComplete = true;
               return;
            }

            if (var2 == null) {
               this.searchComplete = true;
               this.currConn.releaseSearchListener(this.resultSource);
               return;
            }

            if (var2 instanceof LDAPResponse) {
               try {
                  this.currConn.checkSearchMsg(this, var2, this.currCons, this.currBase, this.currScope, this.currFilter, this.currAttrs, this.currAttrsOnly);
               } catch (LDAPException var13) {
                  this.add(var13);
               } finally {
                  this.currConn.releaseSearchListener(this.resultSource);
               }

               this.searchComplete = true;
               if (this.connectionToClose != null) {
                  try {
                     this.connectionToClose.disconnect();
                  } catch (LDAPException var12) {
                  }

                  this.connectionToClose = null;
               }

               return;
            }

            try {
               this.currConn.checkSearchMsg(this, var2, this.currCons, this.currBase, this.currScope, this.currFilter, this.currAttrs, this.currAttrsOnly);
            } catch (LDAPException var15) {
               this.add(var15);
            }
         }
      }

   }
}
