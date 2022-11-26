package com.octetstring.vde.backend.standard;

import com.asn1c.core.Int8;
import com.octetstring.ldapv3.Filter;
import com.octetstring.ldapv3.SubstringFilter_substrings_Seq;
import com.octetstring.nls.Messages;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntryChange;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.backend.Backend;
import com.octetstring.vde.operation.LDAPResult;
import com.octetstring.vde.schema.AttributeType;
import com.octetstring.vde.schema.SchemaChecker;
import com.octetstring.vde.syntax.BinarySyntax;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DNUtility;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.DirectorySchemaViolation;
import com.octetstring.vde.util.EncryptionHelper;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ParseFilter;
import com.octetstring.vde.util.PasswordEncryptor;
import com.octetstring.vde.util.TimedActivityThread;
import com.octetstring.vde.util.guid.Guid;
import com.octetstring.vde.util.guid.OrclGuid;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;
import org.apache.oro.text.perl.Perl5Util;

public class BackendStandard implements Backend {
   private Cache cache = null;
   private Map dntoidmap = null;
   private DataFile dataFile = null;
   private volatile int idCounter = 0;
   private KeyPtr[] idtobasemap = null;
   private KeyPtr[] idtodnmap = null;
   private Vector presenceIndices = null;
   private Vector exactIndices = null;
   private Vector orderingIndices = null;
   private Vector substringIndices = null;
   private volatile boolean wlock = false;
   private boolean readOnly = false;
   private int pwtype = 0;
   private int mapsize = 1024;
   private TransactionProcessor tp = null;
   private DirectoryString suffix = null;
   private static final DirectoryString EMPTYDIRSTRING = new DirectoryString("");
   private static final DirectoryString CREATE_TIMESTAMP = new DirectoryString("createTimestamp");
   private static final DirectoryString CREATORS_NAME = new DirectoryString("creatorsName");
   private static final DirectoryString MODIFY_TIMESTAMP = new DirectoryString("modifyTimeStamp");
   private static final DirectoryString MODIFIERS_NAME = new DirectoryString("modifiersName");
   private static final DirectoryString USERPASSWORD = new DirectoryString("userPassword");
   private static final DirectoryString DOT = new DirectoryString(".");
   public static final DirectoryString GUID = new DirectoryString("orclguid");
   public static final String CFG_PRESENCEINDEX = "presenceIndex";
   public static final String CFG_EXACTINDEX = "exactIndex";
   public static final String CFG_ORDERINGINDEX = "orderingIndex";
   public static final String CFG_SUBSTRINGINDEX = "substringIndex";
   public static final String CFG_DBNAME = "dbname";
   public static final String CFG_CACHESIZE = "cachesize";
   public static final String CFG_TLOGSIZE = "tlogsize";
   public static final String CFG_SUFFIX = "suffix";
   public static final String CFG_BACKUP_BASE = "backup-file";
   public static final String CFG_BACKUP_HOUR = "backup-hour";
   public static final String CFG_BACKUP_MIN = "backup-minute";
   public static final String CFG_BACKUP_MAX = "backup-max";
   public static final String CFG_READONLY = "readonly";
   public static final String CFG_PASSWORDENCRYPT = "passwordencrypt";
   private static int PW_PLAIN = 0;
   private static int PW_CRYPT = 1;
   private static int PW_SHA = 2;
   private static int PW_SSHA = 3;
   private static int PW_EXTERNAL = 4;
   private static int PW_SSHA256 = 5;
   private Hashtable backendConfig = null;
   private TransactionLog tlog = null;
   private Index index = null;

   public BackendStandard(Hashtable config) throws IOException {
      this.backendConfig = config;
      this.suffix = (DirectoryString)this.backendConfig.get("suffix");
      this.dntoidmap = Collections.synchronizedMap(new HashMap());
      this.idtobasemap = new KeyPtr[this.mapsize];
      this.idtodnmap = new KeyPtr[this.mapsize];
      String readOnlyStr = (String)this.backendConfig.get("readonly");
      if (readOnlyStr != null && readOnlyStr.equals("1")) {
         this.readOnly = true;
      }

      String docryptstr = (String)this.backendConfig.get("passwordencrypt");
      if (docryptstr.equalsIgnoreCase("crypt")) {
         this.pwtype = PW_CRYPT;
      } else if (docryptstr.equalsIgnoreCase("sha")) {
         this.pwtype = PW_SHA;
      } else if (docryptstr.equalsIgnoreCase("ssha")) {
         this.pwtype = PW_SSHA;
      } else if (docryptstr.equalsIgnoreCase("ssha256")) {
         this.pwtype = PW_SSHA256;
      }

      this.presenceIndices = new Vector();
      this.exactIndices = new Vector();
      this.orderingIndices = new Vector();
      this.substringIndices = new Vector();
      String presenceIndex = (String)this.backendConfig.get("presenceIndex");
      String exactIndex = (String)this.backendConfig.get("exactIndex");
      String orderingIndex = (String)this.backendConfig.get("orderingIndex");
      String substringIndex = (String)this.backendConfig.get("substringIndex");
      StringTokenizer st;
      String dbfilename;
      if (presenceIndex != null) {
         st = new StringTokenizer(presenceIndex, ",");
         Logger.getInstance().log(9, this, Messages.getString("Presence_index____27") + presenceIndex);

         while(st.hasMoreTokens()) {
            dbfilename = st.nextToken();
            this.presenceIndices.addElement(new DirectoryString(dbfilename));
         }
      }

      if (exactIndex != null) {
         st = new StringTokenizer(exactIndex, ",");
         Logger.getInstance().log(9, this, Messages.getString("Exact_index____29") + exactIndex);

         while(st.hasMoreTokens()) {
            dbfilename = st.nextToken();
            this.exactIndices.addElement(new DirectoryString(dbfilename));
         }
      }

      if (orderingIndex != null) {
         st = new StringTokenizer(orderingIndex, ",");
         Logger.getInstance().log(9, this, Messages.getString("Ordering_index____31") + orderingIndex);

         while(st.hasMoreTokens()) {
            this.orderingIndices.addElement(new DirectoryString(st.nextToken()));
         }
      }

      if (substringIndex != null) {
         st = new StringTokenizer(substringIndex, ",");
         Logger.getInstance().log(9, this, Messages.getString("Substring_index____33") + substringIndex);

         while(st.hasMoreTokens()) {
            this.substringIndices.addElement(new DirectoryString(st.nextToken()));
         }
      }

      this.index = new Index(this, this.presenceIndices, this.exactIndices, this.orderingIndices, this.substringIndices);
      File dbfile = new File((String)this.backendConfig.get("dbname"));
      dbfilename = null;
      String ihome = System.getProperty("vde.home");
      if (!dbfile.isAbsolute() && ihome != null) {
         dbfilename = ihome + "/" + dbfile.toString();
      } else {
         dbfilename = dbfile.toString();
      }

      File backbasefile = new File((String)this.backendConfig.get("backup-file"));
      String backupbase = null;
      if (!backbasefile.isAbsolute() && ihome != null) {
         backupbase = ihome + "/" + backbasefile.toString();
      } else {
         backupbase = backbasefile.toString();
      }

      String bhs = (String)this.backendConfig.get("backup-hour");
      String bms = (String)this.backendConfig.get("backup-minute");
      String bmax = (String)this.backendConfig.get("backup-max");
      BackupTask bt = new BackupTask(this, Integer.parseInt(bhs), Integer.parseInt(bms), Integer.parseInt(bmax), dbfilename, backupbase);
      TimedActivityThread.getInstance().addActivity(bt);
      this.tlog = new TransactionLog(dbfilename);
      String ts = (String)config.get("tlogsize");
      long tlogsize = Long.parseLong(ts);
      String csizestring = (String)config.get("cachesize");
      int cachesize = Integer.parseInt(csizestring);
      this.cache = new Cache(cachesize);
      this.dataFile = new DataFile(dbfilename);
      int high = this.dataFile.getHighEid();
      this.tp = new TransactionProcessor(this.dataFile, dbfilename, this.tlog, tlogsize);
      high += this.tp.transactionCount();
      this.idCounter = high;
      this.tp.start();
      if (this.tp.isMoreTransactions()) {
         Logger.getInstance().log(5, this, Messages.getString("Processing_Outstanding_Transactions_37"));
      }

      this.tp.setTLogSize(-1L);
      this.tp.waitTransactionsCompleted();
      this.tp.setTLogSize(tlogsize);
      Logger.getInstance().log(5, this, Messages.getString("Initializing_Memory_Indices_38"));
      Entry oneEntry = null;

      for(int i = 1; i <= high; ++i) {
         Integer eid = new Integer(i);
         oneEntry = this.dataFile.getEntry(eid);
         if (oneEntry != null) {
            this.dntoidmap.put(KeyPool.getInstance().get(this.getUTFBytes(oneEntry.getName().normalize())), eid);
            if (i >= this.mapsize) {
               while(i >= this.mapsize) {
                  this.mapsize *= 2;
               }

               KeyPtr[] newDnmap = new KeyPtr[this.mapsize];
               KeyPtr[] newBasemap = new KeyPtr[this.mapsize];
               System.arraycopy(this.idtodnmap, 0, newDnmap, 0, this.idtodnmap.length);
               System.arraycopy(this.idtobasemap, 0, newBasemap, 0, this.idtobasemap.length);
               this.idtodnmap = newDnmap;
               this.idtobasemap = newBasemap;
            }

            try {
               this.idtodnmap[i] = new KeyPtr(this.getUTFBytes(DNUtility.getInstance().normalize(oneEntry.getName()).normalize()));
               this.idtobasemap[i] = KeyPool.getInstance().get(this.getUTFBytes(oneEntry.getBase().normalize()));
            } catch (InvalidDNException var28) {
               Logger.getInstance().log(0, this, Messages.getString("Entry_found_with_invalid_DN___39") + oneEntry.getName());
            }

            this.index.index(oneEntry);
         }
      }

   }

   public synchronized void lockWrites() {
      this.lockWriteLocally();
      this.tlog.lock();
      this.dataFile.lockWrites();
   }

   public synchronized void unlockWrites() {
      this.tlog.unlock();
      this.dataFile.unlockWrites();
      this.unlockWriteLocally();
   }

   private synchronized void lockWriteLocally() {
      while(this.wlock) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

      this.wlock = true;
   }

   private synchronized void unlockWriteLocally() {
      this.wlock = false;
      this.notify();
   }

   public Int8 add(DirectoryString binddn, Entry entry) {
      if (this.readOnly) {
         return LDAPResult.UNWILLING_TO_PERFORM;
      } else {
         int myid = false;

         Int8 var5;
         try {
            this.lockWriteLocally();
            if (this.dntoidmap.get(new KeyPtr(this.getUTFBytes(entry.getName().normalize()))) == null) {
               replaceOIDs(entry);
               boolean createGuid = true;
               if (entry.get(GUID) != null && entry.get(GUID).size() != 0) {
                  Enumeration valsEnum = entry.get(GUID).elements();

                  label248:
                  while(true) {
                     while(true) {
                        if (!valsEnum.hasMoreElements()) {
                           break label248;
                        }

                        Syntax guid = (Syntax)valsEnum.nextElement();
                        int[] result = this.searchExact((DirectoryString)null, 2, GUID.getDirectoryString(), guid, true);
                        if (result != null && result.length > 0) {
                           Logger.getInstance().log(3, this, "ORCLGUID already exists. Entry : " + entry.getName() + ". Skip the existent ORCLGUID and generate new one.");
                        } else {
                           createGuid = false;
                        }
                     }
                  }
               }

               Vector creatorsName;
               if (createGuid) {
                  DirectoryString generatedGuid = new DirectoryString(OrclGuid.compactGuidString((new Guid()).toString()));
                  creatorsName = new Vector();
                  creatorsName.addElement(generatedGuid);
                  entry.put(GUID, creatorsName);
               }

               try {
                  SchemaChecker.getInstance().checkEntry(entry);
               } catch (DirectorySchemaViolation var25) {
                  Int8 var33 = LDAPResult.OBJECT_CLASS_VIOLATION;
                  return var33;
               }

               Int8 var31;
               if (!this.checkRDN(entry)) {
                  var31 = LDAPResult.NAMING_VIOLATION;
                  return var31;
               }

               if (this.dntoidmap.get(new KeyPtr(this.getUTFBytes(entry.getBase().normalize()))) == null && !entry.getName().equals(this.suffix)) {
                  var31 = LDAPResult.NO_SUCH_OBJECT;
                  return var31;
               }

               Vector createTimestamp = new Vector();
               creatorsName = new Vector();
               creatorsName.addElement(binddn);
               TimeZone tz = TimeZone.getTimeZone("GMT");
               Calendar cal = Calendar.getInstance(tz);
               int year = cal.get(1);
               int mo = cal.get(2);
               String sMo = null;
               if (mo < 10) {
                  sMo = "0" + mo;
               } else {
                  sMo = "" + mo;
               }

               int day = cal.get(5);
               String sDay = null;
               if (day < 10) {
                  sDay = "0" + day;
               } else {
                  sDay = "" + day;
               }

               int hour = cal.get(11);
               String sHour = null;
               if (hour < 10) {
                  sHour = "0" + hour;
               } else {
                  sHour = "" + hour;
               }

               int min = cal.get(12);
               String sMin = null;
               if (min < 10) {
                  sMin = "0" + min;
               } else {
                  sMin = "" + min;
               }

               DirectoryString ctime = new DirectoryString(year + sMo + sDay + sHour + sMin + "Z");
               createTimestamp.addElement(ctime);
               entry.put(CREATE_TIMESTAMP, createTimestamp);
               entry.put(CREATORS_NAME, creatorsName);
               if (this.pwtype != PW_PLAIN) {
                  Vector up = entry.get(USERPASSWORD);
                  if (up != null) {
                     for(int i = 0; i < up.size(); ++i) {
                        this.cryptpass((Syntax)up.elementAt(i));
                     }
                  }
               }

               ++this.idCounter;
               int myid = this.idCounter;
               entry.setID(myid);
               Integer eid = new Integer(myid);
               this.dntoidmap.put(KeyPool.getInstance().get(this.getUTFBytes(entry.getName().normalize())), eid);
               if (myid >= this.mapsize) {
                  while(true) {
                     if (myid < this.mapsize) {
                        KeyPtr[] newDnmap = new KeyPtr[this.mapsize];
                        KeyPtr[] newBasemap = new KeyPtr[this.mapsize];
                        System.arraycopy(this.idtodnmap, 0, newDnmap, 0, this.idtodnmap.length);
                        System.arraycopy(this.idtobasemap, 0, newBasemap, 0, this.idtobasemap.length);
                        this.idtodnmap = newDnmap;
                        this.idtobasemap = newBasemap;
                        break;
                     }

                     this.mapsize *= 2;
                  }
               }

               this.idtodnmap[myid] = new KeyPtr(this.getUTFBytes(entry.getName().normalize()));
               this.idtobasemap[myid] = KeyPool.getInstance().get(this.getUTFBytes(entry.getBase().normalize()));
               this.cache.add(eid, entry);
               this.index.index(entry);
               if (!this.tlog.add(entry)) {
                  Int8 var36 = LDAPResult.UNAVAILABLE;
                  return var36;
               }

               this.tp.noteChange();
               return LDAPResult.SUCCESS;
            }

            var5 = LDAPResult.ENTRY_ALREADY_EXISTS;
         } finally {
            this.unlockWriteLocally();
         }

         return var5;
      }
   }

   public boolean bind(DirectoryString dn, BinarySyntax password) {
      return false;
   }

   public Int8 delete(DirectoryString binddn, DirectoryString name) {
      if (this.readOnly) {
         return LDAPResult.UNWILLING_TO_PERFORM;
      } else {
         if (this.tp.isMoreTransactions()) {
            Logger.getInstance().log(5, this, "Processing Outstanding Transactions");
            this.tp.waitTransactionsCompleted();
         }

         try {
            this.lockWriteLocally();
            Entry entry = null;

            try {
               entry = this.getByDN(binddn, DNUtility.getInstance().normalize(name));
            } catch (DirectoryException var10) {
               Int8 var5 = LDAPResult.NO_SUCH_OBJECT;
               return var5;
            }

            if (entry == null) {
               Int8 var12 = LDAPResult.NO_SUCH_OBJECT;
               return var12;
            }

            this.index.delete(entry);
            Integer eid = new Integer(entry.getID());
            this.cache.remove(eid);
            Integer tmpO = (Integer)this.dntoidmap.remove(new KeyPtr(this.getUTFBytes(entry.getName().normalize())));
            this.idtodnmap[tmpO] = null;
            this.idtobasemap[tmpO] = null;
            if (!this.tlog.delete(entry.getID())) {
               Int8 var6 = LDAPResult.UNAVAILABLE;
               return var6;
            }

            this.tp.noteChange();
         } finally {
            this.unlockWriteLocally();
         }

         return LDAPResult.SUCCESS;
      }
   }

   public boolean doBind() {
      return false;
   }

   public DataFile getDataFile() {
      return this.dataFile;
   }

   private static DirectoryString convertOID(DirectoryString attrname) {
      if (attrname.indexOf(DOT) != -1) {
         DirectoryString tmp = SchemaChecker.getInstance().nameFromOID(attrname);
         return tmp == null ? attrname : tmp;
      } else {
         return attrname;
      }
   }

   private static void replaceOIDs(Entry myentry) {
      Enumeration keys = myentry.keys();
      Entry clonedEntry = null;

      while(keys.hasMoreElements()) {
         DirectoryString nextkey = (DirectoryString)keys.nextElement();
         if (nextkey.indexOf(DOT) != -1) {
            if (clonedEntry == null) {
               clonedEntry = (Entry)myentry.clone();
            }

            Vector values = myentry.get(nextkey);
            clonedEntry.remove(nextkey);
            clonedEntry.put(convertOID(nextkey), values);
         }
      }

      if (clonedEntry != null) {
         myentry.setAttributes(clonedEntry.getAttributes());
      }

   }

   private int[] evaluateFilter(Filter currentFilter, DirectoryString base, int scope) {
      int[] matchThisFilter = null;
      DirectoryString matchType;
      AttributeType at;
      Object synVal;
      int[] matched;
      int[] matched;
      switch (currentFilter.getSelector()) {
         case 0:
            boolean firstAnd = true;
            Iterator andEnum = currentFilter.getAnd().iterator();

            while(andEnum.hasNext()) {
               matched = this.evaluateFilter((Filter)andEnum.next(), base, scope);
               if (firstAnd) {
                  firstAnd = false;
                  matchThisFilter = matched;
               } else {
                  matchThisFilter = this.getAnd(matchThisFilter, matched);
               }
            }

            return matchThisFilter == null ? new int[0] : matchThisFilter;
         case 1:
            boolean firstOr = true;
            Iterator orEnum = currentFilter.getOr().iterator();

            while(orEnum.hasNext()) {
               matched = this.evaluateFilter((Filter)orEnum.next(), base, scope);
               if (firstOr) {
                  firstOr = false;
                  matchThisFilter = matched;
               } else {
                  matchThisFilter = this.getOr(matchThisFilter, matched);
               }
            }

            return matchThisFilter == null ? new int[0] : matchThisFilter;
         case 2:
            matched = this.evaluateFilter(currentFilter.getNot(), base, scope);
            matched = this.searchExact(base, scope, "objectclass", (Syntax)null, false);
            matchThisFilter = this.getNot(matched, matched);
            break;
         case 3:
            matchType = convertOID(new DirectoryString(currentFilter.getEqualityMatch().getAttributeDesc().toByteArray()));
            at = SchemaChecker.getInstance().getAttributeType(matchType);
            synVal = null;
            if (at != null) {
               synVal = at.getSyntaxInstance();
            } else {
               synVal = new DirectoryString();
            }

            ((Syntax)synVal).setValue(currentFilter.getEqualityMatch().getAssertionValue().toByteArray());
            matchThisFilter = this.searchExact(base, scope, matchType.toString(), (Syntax)synVal, true);
            break;
         case 4:
            matchType = convertOID(new DirectoryString(currentFilter.getSubstrings().getType().toByteArray()));
            at = SchemaChecker.getInstance().getAttributeType(matchType);
            Syntax subinitial = null;
            Syntax subany = null;
            Syntax subfinal = null;
            Iterator substrEnum = currentFilter.getSubstrings().getSubstrings().iterator();

            while(substrEnum.hasNext()) {
               SubstringFilter_substrings_Seq oneSubFilter = (SubstringFilter_substrings_Seq)substrEnum.next();
               if (oneSubFilter.getSelector() == 0) {
                  if (at != null) {
                     subinitial = at.getSyntaxInstance();
                  } else {
                     subinitial = new DirectoryString();
                  }

                  ((Syntax)subinitial).setValue(oneSubFilter.getInitial().toByteArray());
               } else if (oneSubFilter.getSelector() == 1) {
                  if (at != null) {
                     subany = at.getSyntaxInstance();
                  } else {
                     subany = new DirectoryString();
                  }

                  ((Syntax)subany).setValue(oneSubFilter.getAny().toByteArray());
               } else if (oneSubFilter.getSelector() == 2) {
                  if (at != null) {
                     subfinal = at.getSyntaxInstance();
                  } else {
                     subfinal = new DirectoryString();
                  }

                  ((Syntax)subfinal).setValue(oneSubFilter.getFinal_().toByteArray());
               }
            }

            matchThisFilter = this.searchSubstring(base, scope, matchType.toString(), (Syntax)subinitial, (Syntax)subany, (Syntax)subfinal);
            break;
         case 5:
            matchType = convertOID(new DirectoryString(currentFilter.getGreaterOrEqual().getAttributeDesc().toByteArray()));
            at = SchemaChecker.getInstance().getAttributeType(matchType);
            if (at != null) {
               synVal = at.getSyntaxInstance();
            } else {
               synVal = new DirectoryString();
            }

            ((Syntax)synVal).setValue(currentFilter.getGreaterOrEqual().getAssertionValue().toByteArray());
            matchThisFilter = this.searchOrder(base, scope, matchType.toString(), (Syntax)synVal, true);
            break;
         case 6:
            matchType = convertOID(new DirectoryString(currentFilter.getLessOrEqual().getAttributeDesc().toByteArray()));
            at = SchemaChecker.getInstance().getAttributeType(matchType);
            if (at != null) {
               synVal = at.getSyntaxInstance();
            } else {
               synVal = new DirectoryString();
            }

            ((Syntax)synVal).setValue(currentFilter.getLessOrEqual().getAssertionValue().toByteArray());
            matchThisFilter = this.searchOrder(base, scope, matchType.toString(), (Syntax)synVal, false);
            break;
         case 7:
            matchType = convertOID(new DirectoryString(currentFilter.getPresent().toByteArray()));
            matchThisFilter = this.searchExact(base, scope, matchType.toString(), (Syntax)null, false);
            break;
         case 8:
            matchType = convertOID(new DirectoryString(currentFilter.getApproxMatch().getAttributeDesc().toByteArray()));
            at = SchemaChecker.getInstance().getAttributeType(matchType);
            if (at != null) {
               synVal = at.getSyntaxInstance();
            } else {
               synVal = new DirectoryString();
            }

            ((Syntax)synVal).setValue(currentFilter.getApproxMatch().getAssertionValue().toByteArray());
            matchThisFilter = this.searchExact(base, scope, matchType.toString(), (Syntax)synVal, true);
            break;
         case 9:
            if (currentFilter.getExtensibleMatch().getType() != null) {
               matchType = convertOID(new DirectoryString(currentFilter.getExtensibleMatch().getType().toByteArray()));
               at = SchemaChecker.getInstance().getAttributeType(matchType);
               if (at != null) {
                  synVal = at.getSyntaxInstance();
               } else {
                  synVal = new DirectoryString();
               }

               ((Syntax)synVal).setValue(currentFilter.getExtensibleMatch().getMatchValue().toByteArray());
               matchThisFilter = this.searchExact(base, scope, matchType.toString(), (Syntax)synVal, true);
            } else {
               matchThisFilter = new int[0];
            }
      }

      return matchThisFilter == null ? new int[0] : matchThisFilter;
   }

   public EntrySet get(DirectoryString binddn, DirectoryString base, int scope, Filter filter, boolean typesOnly, Vector attributes) {
      if (Logger.getInstance().isLogable(9)) {
         Logger.getInstance().log(9, this, Messages.getString("STANDARD_Adapter_Search_using__n__BindDN____51") + binddn + "\n  Base:    " + base + "\n  Scope:   " + scope + "\n  Attribs: " + attributes + "\n  Filter:  " + ParseFilter.filterToString(filter));
      }

      if (this.tp.isMoreTransactions()) {
         Logger.getInstance().log(5, this, "Processing Outstanding Transactions");
         this.tp.waitTransactionsCompleted();
      }

      int[] matchingEntries;
      if (base.length() >= this.suffix.length() && this.dntoidmap.get(new KeyPtr(this.getUTFBytes(base.normalize()))) == null) {
         matchingEntries = new int[]{-1};
         return new StandardEntrySet(this, matchingEntries);
      } else {
         matchingEntries = this.evaluateFilter(filter, base, scope);
         if (matchingEntries == null) {
            matchingEntries = new int[0];
         }

         if (scope == 0) {
            return new StandardEntrySet(this, matchingEntries);
         } else {
            int[] results = new int[matchingEntries.length];
            int totalcount = 0;
            byte[] normalbase = this.getUTFBytes(base.normalize());

            for(int i = 0; i < matchingEntries.length; ++i) {
               int currentEid = matchingEntries[i];
               KeyPtr myName = this.idtodnmap[currentEid];
               KeyPtr myBase = this.idtobasemap[currentEid];
               if (myName != null && myBase != null && (myName.endsWith(normalbase) || base.equals(EMPTYDIRSTRING)) && (scope == 2 || myBase.equals(normalbase))) {
                  results[totalcount] = currentEid;
                  ++totalcount;
               }
            }

            if (totalcount == results.length) {
               return new StandardEntrySet(this, results);
            } else {
               int[] finalresults = new int[totalcount];
               System.arraycopy(results, 0, finalresults, 0, totalcount);
               return new StandardEntrySet(this, finalresults);
            }
         }
      }
   }

   private int[] getAnd(int[] first, int[] second) {
      int[] smallmatches = null;
      int[] largematches = null;
      int[] smallmatches;
      int[] largematches;
      if (first.length > second.length) {
         smallmatches = second;
         largematches = first;
      } else {
         smallmatches = first;
         largematches = second;
      }

      int[] inBoth = new int[smallmatches.length];
      int count = 0;

      for(int i = 0; i < smallmatches.length; ++i) {
         for(int j = 0; j < largematches.length; ++j) {
            if (smallmatches[i] == largematches[j]) {
               inBoth[count] = smallmatches[i];
               ++count;
               break;
            }
         }
      }

      int[] result = new int[count];
      System.arraycopy(inBoth, 0, result, 0, count);
      return result;
   }

   private int[] getNot(int[] match, int[] all) {
      if (match.length == all.length) {
         return new int[0];
      } else {
         int[] result = new int[all.length - match.length];
         int count = 0;

         for(int i = 0; i < all.length; ++i) {
            boolean inMatch = false;

            for(int j = 0; j < match.length; ++j) {
               if (all[i] == match[j]) {
                  inMatch = true;
                  break;
               }
            }

            if (!inMatch) {
               result[count] = all[i];
               ++count;
            }
         }

         return result;
      }
   }

   private int[] getOr(int[] first, int[] second) {
      int[] combined = new int[first.length + second.length];
      System.arraycopy(first, 0, combined, 0, first.length);
      System.arraycopy(second, 0, combined, first.length, second.length);
      return combined;
   }

   public Entry getByDN(DirectoryString binddn, DirectoryString dn) throws DirectoryException {
      Integer id = (Integer)this.dntoidmap.get(new KeyPtr(this.getUTFBytes(dn.normalize())));
      if (id != null) {
         Entry current = this.cache.get(id);
         if (current == null) {
            current = this.dataFile.getEntry(id);
         }

         return current;
      } else {
         return null;
      }
   }

   public Entry getByID(Integer id) {
      Entry current = null;
      current = this.cache.get(id);
      if (current == null) {
         current = this.dataFile.getEntry(id);
         if (current != null) {
            this.cache.add(id, current);
         }
      }

      return current;
   }

   private static void replaceOIDs(Vector changeEntries) {
      Enumeration changes = changeEntries.elements();

      while(changes.hasMoreElements()) {
         EntryChange ec = (EntryChange)changes.nextElement();
         if (ec.getAttr().indexOf(DOT) != -1) {
            ec.setAttr(convertOID(ec.getAttr()));
         }
      }

   }

   public void modify(DirectoryString binddn, DirectoryString name, Vector changeEntries) throws DirectorySchemaViolation, DirectoryException {
      if (this.readOnly) {
         throw new DirectoryException(53);
      } else {
         if (this.tp.isMoreTransactions()) {
            Logger.getInstance().log(5, this, "Processing Outstanding Transactions");
            this.tp.waitTransactionsCompleted();
         }

         try {
            this.lockWriteLocally();
            Entry entry = this.getByDN(binddn, DNUtility.getInstance().normalize(name));
            if (entry == null) {
               throw new DirectoryException(32);
            }

            Entry current = (Entry)entry.clone();
            replaceOIDs(changeEntries);
            Enumeration changeEnum = changeEntries.elements();

            while(changeEnum.hasMoreElements()) {
               this.oneChange(current, (EntryChange)changeEnum.nextElement());
            }

            if (!this.checkRDN(current)) {
               throw new DirectoryException(67);
            }

            SchemaChecker.getInstance().checkEntry(current);
            Vector modifyTimestamp = new Vector();
            Vector modifiersName = new Vector();
            modifiersName.addElement(binddn);
            TimeZone tz = TimeZone.getTimeZone("GMT");
            Calendar cal = Calendar.getInstance(tz);
            int year = cal.get(1);
            int mo = cal.get(2);
            String sMo = null;
            if (mo < 10) {
               sMo = "0" + mo;
            } else {
               sMo = "" + mo;
            }

            int day = cal.get(5);
            String sDay = null;
            if (day < 10) {
               sDay = "0" + day;
            } else {
               sDay = "" + day;
            }

            int hour = cal.get(11);
            String sHour = null;
            if (hour < 10) {
               sHour = "0" + hour;
            } else {
               sHour = "" + hour;
            }

            int min = cal.get(12);
            String sMin = null;
            if (min < 10) {
               sMin = "0" + min;
            } else {
               sMin = "" + min;
            }

            DirectoryString mtime = new DirectoryString(year + sMo + sDay + sHour + sMin + "Z");
            modifyTimestamp.addElement(mtime);
            current.put(MODIFY_TIMESTAMP, modifyTimestamp);
            current.put(MODIFIERS_NAME, modifiersName);
            EntryChange modchg = new EntryChange(2, MODIFY_TIMESTAMP, modifyTimestamp);
            changeEntries.addElement(modchg);
            modchg = new EntryChange(2, MODIFIERS_NAME, modifiersName);
            changeEntries.addElement(modchg);
            this.index.index(entry, changeEntries);
            Integer eid = new Integer(entry.getID());
            this.cache.remove(eid);
            this.cache.add(eid, current);
            if (!this.tlog.modify(current)) {
               throw new DirectoryException(52);
            }

            this.tp.noteChange();
         } finally {
            this.unlockWriteLocally();
         }

      }
   }

   public void oneChange(Entry current, EntryChange change) throws DirectoryException {
      int changeType = change.getModType();
      DirectoryString attr = change.getAttr();
      Vector vals = change.getValues();
      if (this.pwtype != PW_PLAIN && attr.equals(USERPASSWORD)) {
         for(int i = 0; i < vals.size(); ++i) {
            this.cryptpass((Syntax)vals.elementAt(i));
         }
      }

      Enumeration enumVals;
      Object oneVal;
      Vector oldvals;
      if (changeType == 0) {
         oldvals = null;
         if (!current.containsKey(attr)) {
            oldvals = new Vector();
         } else {
            oldvals = current.get(attr);
         }

         enumVals = vals.elements();

         while(enumVals.hasMoreElements()) {
            oneVal = enumVals.nextElement();
            if (!oldvals.contains(oneVal)) {
               oldvals.addElement(oneVal);
            }
         }

         current.put(attr, oldvals);
      }

      if (changeType == 1) {
         oldvals = null;
         if (!current.containsKey(attr)) {
            throw new DirectoryException(16);
         }

         oldvals = current.get(attr);
         if (vals.isEmpty()) {
            current.remove(attr);
            return;
         }

         enumVals = vals.elements();

         while(enumVals.hasMoreElements()) {
            oneVal = enumVals.nextElement();
            if (oldvals.contains(oneVal)) {
               oldvals.removeElement(oneVal);
            }
         }

         if (oldvals.size() > 0) {
            current.put(attr, oldvals);
         } else {
            current.remove(attr);
         }
      }

      if (changeType == 2) {
         if (vals.isEmpty()) {
            current.remove(attr);
         } else {
            current.put(attr, vals);
         }
      }

   }

   private boolean checkRDN(Entry entry) {
      if (entry != null && entry.getName() != null && entry.getBase() != null) {
         Vector rdns = DNUtility.getInstance().explodeDN(entry.getName());
         if (rdns.isEmpty()) {
            return false;
         } else {
            String myrdn = (String)rdns.elementAt(0);
            StringTokenizer rdntok = new StringTokenizer(myrdn, "+");

            Vector vals;
            Object synVal;
            do {
               if (!rdntok.hasMoreTokens()) {
                  return true;
               }

               String nextatval = rdntok.nextToken();
               StringTokenizer atvaltok = new StringTokenizer(nextatval, "=");
               if (!atvaltok.hasMoreTokens()) {
                  return false;
               }

               String attr = atvaltok.nextToken();
               if (!atvaltok.hasMoreTokens()) {
                  return false;
               }

               String value = atvaltok.nextToken();
               DirectoryString dsattr = new DirectoryString(attr);
               vals = entry.get(dsattr);
               if (vals == null) {
                  return false;
               }

               synVal = null;
               AttributeType at = SchemaChecker.getInstance().getAttributeType(dsattr);
               if (at != null) {
                  synVal = at.getSyntaxInstance();
               } else {
                  synVal = new DirectoryString();
               }

               try {
                  ((Syntax)synVal).setValue(value.getBytes("UTF8"));
               } catch (UnsupportedEncodingException var14) {
                  ((Syntax)synVal).setValue(value.getBytes());
               }
            } while(vals.contains(synVal));

            return false;
         }
      } else {
         return false;
      }
   }

   public Int8 rename(DirectoryString binddn, DirectoryString oldname, DirectoryString newname, DirectoryString newsuffix, boolean deleteoldrdn) {
      if (this.readOnly) {
         return LDAPResult.UNWILLING_TO_PERFORM;
      } else {
         if (this.tp.isMoreTransactions()) {
            Logger.getInstance().log(5, this, "Processing Outstanding Transactions");
            this.tp.waitTransactionsCompleted();
         }

         Entry entry = null;

         try {
            this.lockWriteLocally();

            Int8 fullname;
            try {
               entry = this.getByDN(binddn, oldname);
            } catch (DirectoryException var30) {
               fullname = new Int8((byte)var30.getLDAPErrorCode());
               return fullname;
            }

            if (entry == null) {
               Int8 var33 = LDAPResult.NO_SUCH_OBJECT;
               return var33;
            } else {
               DirectoryString oldentname = entry.getName();
               fullname = null;
               DirectoryString fullname;
               if (newsuffix == null) {
                  fullname = new DirectoryString(newname + "," + entry.getBase());
               } else {
                  fullname = new DirectoryString(newname + "," + newsuffix);
               }

               Entry current = (Entry)entry.clone();

               Int8 var11;
               try {
                  current.setName(fullname);
               } catch (InvalidDNException var31) {
                  var11 = new Int8((byte)var31.getLDAPErrorCode());
                  return var11;
               }

               if (this.dntoidmap.get(new KeyPtr(this.getUTFBytes(current.getName().normalize()))) != null) {
                  Int8 var36 = LDAPResult.ENTRY_ALREADY_EXISTS;
                  return var36;
               } else {
                  StringTokenizer atvaltok;
                  String attr;
                  DirectoryString dsattr;
                  String nextatval;
                  if (deleteoldrdn) {
                     Vector rdns = DNUtility.getInstance().explodeDN(oldentname);
                     if (rdns.isEmpty()) {
                        var11 = LDAPResult.NAMING_VIOLATION;
                        return var11;
                     }

                     nextatval = (String)rdns.elementAt(0);
                     atvaltok = new StringTokenizer(nextatval, "+");

                     while(atvaltok.hasMoreTokens()) {
                        attr = atvaltok.nextToken();
                        StringTokenizer atvaltok = new StringTokenizer(attr, "=");
                        if (atvaltok.hasMoreTokens()) {
                           String attr = atvaltok.nextToken();
                           if (atvaltok.hasMoreTokens()) {
                              String value = atvaltok.nextToken();
                              dsattr = new DirectoryString(attr);
                              Vector vals = current.get(dsattr);
                              if (vals != null) {
                                 Syntax synVal = null;
                                 AttributeType at = SchemaChecker.getInstance().getAttributeType(dsattr);
                                 if (at != null) {
                                    synVal = at.getSyntaxInstance();
                                 } else {
                                    synVal = new DirectoryString();
                                 }

                                 try {
                                    ((Syntax)synVal).setValue(value.getBytes("UTF8"));
                                 } catch (UnsupportedEncodingException var29) {
                                    ((Syntax)synVal).setValue(value.getBytes());
                                 }

                                 vals.removeElement(synVal);
                                 if (vals.isEmpty()) {
                                    current.remove(dsattr);
                                 }
                              }
                           }
                        }
                     }
                  }

                  StringTokenizer rdntok = new StringTokenizer(newname.toString(), "+");

                  while(rdntok.hasMoreTokens()) {
                     nextatval = rdntok.nextToken();
                     atvaltok = new StringTokenizer(nextatval, "=");
                     if (atvaltok.hasMoreTokens()) {
                        attr = atvaltok.nextToken();
                        if (atvaltok.hasMoreTokens()) {
                           String value = atvaltok.nextToken();
                           DirectoryString dsattr = new DirectoryString(attr);
                           Vector vals = current.get(dsattr);
                           if (vals == null) {
                              vals = new Vector();
                              current.put(dsattr, vals);
                           }

                           dsattr = null;
                           AttributeType at = SchemaChecker.getInstance().getAttributeType(dsattr);
                           Object synVal;
                           if (at != null) {
                              synVal = at.getSyntaxInstance();
                           } else {
                              synVal = new DirectoryString();
                           }

                           try {
                              ((Syntax)synVal).setValue(value.getBytes("UTF8"));
                           } catch (UnsupportedEncodingException var28) {
                              ((Syntax)synVal).setValue(value.getBytes());
                           }

                           if (!vals.contains(synVal)) {
                              vals.addElement(synVal);
                           }
                        }
                     }
                  }

                  if (!this.checkRDN(current)) {
                     var11 = LDAPResult.NAMING_VIOLATION;
                     return var11;
                  } else {
                     Integer eid = new Integer(current.getID());
                     this.dntoidmap.remove(KeyPool.getInstance().get(this.getUTFBytes(oldentname.normalize())));
                     this.dntoidmap.put(KeyPool.getInstance().get(this.getUTFBytes(fullname.normalize())), eid);
                     this.idtodnmap[current.getID()] = new KeyPtr(this.getUTFBytes(fullname.normalize()));
                     this.idtobasemap[current.getID()] = KeyPool.getInstance().get(this.getUTFBytes(current.getBase().normalize()));
                     this.index.delete(entry);
                     this.index.index(current);
                     this.cache.remove(eid);
                     this.cache.add(eid, current);
                     if (!this.tlog.modify(current)) {
                        Int8 var39 = LDAPResult.UNAVAILABLE;
                        return var39;
                     } else {
                        this.tp.noteChange();
                        return LDAPResult.SUCCESS;
                     }
                  }
               }
            }
         } finally {
            this.unlockWriteLocally();
         }
      }
   }

   public void cleanupPools() {
      if (this.dataFile != null) {
         this.dataFile.cleanupPools();
      }

   }

   public void shutdown() {
      if (this.dataFile != null) {
         this.dataFile.shutdown();
      }

      if (this.tp != null) {
         this.tp.shutdown();
      }

      if (this.tlog != null) {
         this.tlog.shutdown();
      }

   }

   public void setPasswordExternalEncryptionHelper(EncryptionHelper encryptor) {
      this.pwtype = PW_EXTERNAL;
      PasswordEncryptor.setExternalEncryptionHelper(encryptor);
   }

   private int[] searchExact(DirectoryString base, int scope, String attr, Syntax value, boolean matchValue) {
      int[] results = null;
      DirectoryString cisAttr = new DirectoryString(attr);
      if (Logger.getInstance().isLogable(11)) {
         Logger.getInstance().log(11, this, Messages.getString("__STANDARD_Adapter_EXACT_Search___n__Base______74") + base + "\n  Attr:    " + cisAttr + "\n  Value:   " + value + "\n  Match:   " + matchValue);
      }

      if (scope == 0) {
         Integer id = (Integer)this.dntoidmap.get(new KeyPtr(this.getUTFBytes(base.normalize())));
         if (id != null) {
            Entry current = this.cache.get(id);
            if (current == null) {
               current = this.dataFile.getEntry(id);
            }

            if (current != null && current.containsKey(cisAttr)) {
               if (matchValue) {
                  Vector values = current.get(cisAttr);
                  if (values.contains(value)) {
                     results = new int[]{current.getID()};
                  }
               } else {
                  results = new int[]{current.getID()};
               }
            }
         }

         return results != null ? results : new int[0];
      } else {
         Index var10001;
         if (matchValue) {
            var10001 = this.index;
            results = this.index.getCandidates(1, cisAttr, value);
         } else {
            var10001 = this.index;
            results = this.index.getCandidates(0, cisAttr, value);
         }

         return results != null ? results : new int[0];
      }
   }

   private int[] searchOrder(DirectoryString base, int scope, String attr, Syntax value, boolean greater) {
      int[] results = null;
      DirectoryString cisAttr = new DirectoryString(attr);
      Syntax synValue = value;
      if (Logger.getInstance().isLogable(11)) {
         Logger.getInstance().log(11, this, Messages.getString("__STANDARD_Adapter_ORDER_Search___n__Base______78") + base + "\n  Attr:    " + cisAttr + "\n  Greater: " + greater);
      }

      if (scope != 0) {
         Index var10001;
         if (greater) {
            var10001 = this.index;
            results = this.index.getCandidates(3, cisAttr, value);
         } else {
            var10001 = this.index;
            results = this.index.getCandidates(2, cisAttr, value);
         }

         return results != null ? results : new int[0];
      } else {
         Integer id = (Integer)this.dntoidmap.get(new KeyPtr(this.getUTFBytes(base.normalize())));
         if (id != null) {
            Entry current = this.cache.get(id);
            if (current == null) {
               current = this.dataFile.getEntry(id);
            }

            if (current != null && current.containsKey(cisAttr)) {
               Vector values = current.get(cisAttr);
               Enumeration valsEnum = values.elements();

               while(true) {
                  Syntax oneVal;
                  do {
                     if (!valsEnum.hasMoreElements()) {
                        return results != null ? results : new int[0];
                     }

                     oneVal = (Syntax)valsEnum.nextElement();
                  } while((!greater || synValue.compareTo(oneVal) < 0) && (greater || synValue.compareTo(oneVal) > 0));

                  results = new int[]{current.getID()};
               }
            }
         }

         return results != null ? results : new int[0];
      }
   }

   private int[] searchSubstring(DirectoryString base, int scope, String attr, Syntax subinitial, Syntax subany, Syntax subfinal) {
      int[] results = null;
      DirectoryString cisAttr = new DirectoryString(attr);
      if (Logger.getInstance().isLogable(11)) {
         Logger.getInstance().log(11, this, Messages.getString("__STANDARD_Adapter_SUBSTRING_Search___n__Base______81") + base + "\n  Attr:    " + cisAttr + "\n  Init:    " + subinitial + "\n  Any:     " + subany + "\n  Final:   " + subfinal);
      }

      StringBuffer regexbuf = new StringBuffer();
      regexbuf.append("/^");
      if (subinitial != null) {
         regexbuf.append(escapeRegExChars(subinitial.toString()));
         regexbuf.append(".*");
      }

      if (subany != null) {
         if (regexbuf.length() < 3) {
            regexbuf.append(".*");
         }

         regexbuf.append(escapeRegExChars(subany.toString()));
         regexbuf.append(".*");
      }

      if (subfinal != null) {
         if (regexbuf.length() < 3) {
            regexbuf.append(".*");
         }

         regexbuf.append(escapeRegExChars(subfinal.toString()));
      }

      regexbuf.append("$/i");
      String regex = regexbuf.toString();
      if (scope == 0) {
         Integer id = (Integer)this.dntoidmap.get(new KeyPtr(this.getUTFBytes(base.normalize())));
         if (id != null) {
            Entry current = this.cache.get(id);
            if (current == null) {
               current = this.dataFile.getEntry(id);
            }

            if (current != null && current.containsKey(cisAttr)) {
               Perl5Util p5u = new Perl5Util();
               Vector values = current.get(cisAttr);
               Enumeration valsEnum = values.elements();

               while(valsEnum.hasMoreElements()) {
                  Syntax oneVal = (Syntax)valsEnum.nextElement();
                  if (p5u.match(regex, oneVal.toString())) {
                     results = new int[]{current.getID()};
                  }
               }
            }
         }

         return results != null ? results : new int[0];
      } else {
         Index var10001;
         if (subinitial != null) {
            if (subany == null && subfinal == null) {
               var10001 = this.index;
               results = this.index.getCandidates(4, cisAttr, subinitial, (String)null);
            } else {
               var10001 = this.index;
               results = this.index.getCandidates(4, cisAttr, subinitial, regex);
            }
         } else if (subfinal != null) {
            if (subany == null && this.substringIndices.contains(cisAttr)) {
               var10001 = this.index;
               results = this.index.getCandidates(6, cisAttr, subfinal, (String)null);
            } else {
               var10001 = this.index;
               results = this.index.getCandidates(6, cisAttr, subfinal, regex);
            }
         } else if (subany != null) {
            var10001 = this.index;
            results = this.index.getCandidates(5, cisAttr, subany, regex);
         }

         return results != null ? results : new int[0];
      }
   }

   private void cryptpass(Syntax value) {
      if (value.getValue()[0] != 123) {
         if (this.pwtype == PW_CRYPT) {
            value.setValue(("{crypt}" + PasswordEncryptor.doCrypt(PasswordEncryptor.getUTFString(value.getValue()))).getBytes());
         } else if (this.pwtype == PW_SHA) {
            value.setValue(("{sha}" + PasswordEncryptor.doSHA(value.getValue())).getBytes());
         } else if (this.pwtype == PW_SSHA) {
            value.setValue(("{ssha}" + PasswordEncryptor.doSSHA(value.getValue())).getBytes());
         } else if (this.pwtype == PW_SSHA256) {
            value.setValue(("{ssha256}" + PasswordEncryptor.doSSHA256(value.getValue())).getBytes());
         } else if (this.pwtype == PW_EXTERNAL) {
            value.setValue(PasswordEncryptor.doExternal(PasswordEncryptor.getUTFString(value.getValue())).getBytes());
         }
      }

   }

   private byte[] getUTFBytes(String str) {
      try {
         return str.getBytes("UTF8");
      } catch (UnsupportedEncodingException var3) {
         return str.getBytes();
      }
   }

   private static String escapeRegExChars(String org) {
      if (org != null && !org.isEmpty()) {
         StringBuilder sbMod = new StringBuilder();
         int totalLen = org.length();

         for(int i = 0; i < totalLen; ++i) {
            char current = org.charAt(i);
            switch (current) {
               case '$':
               case '(':
               case ')':
               case '*':
               case '+':
               case '.':
               case '?':
               case '[':
               case '\\':
               case ']':
               case '^':
               case '{':
               case '|':
               case '}':
                  sbMod.append('\\');
                  break;
               case '/':
                  sbMod.append("\\\\");
            }

            sbMod.append(current);
         }

         return sbMod.toString();
      } else {
         return org;
      }
   }
}
