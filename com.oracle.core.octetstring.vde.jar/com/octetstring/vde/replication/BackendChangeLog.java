package com.octetstring.vde.replication;

import com.octetstring.ldapv3.Filter;
import com.octetstring.nls.Messages;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntryChange;
import com.octetstring.vde.EntryChanges;
import com.octetstring.vde.EntryChangesListener;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.backend.BaseBackend;
import com.octetstring.vde.backend.GenericEntrySet;
import com.octetstring.vde.syntax.BinarySyntax;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.IntegerSyntax;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class BackendChangeLog extends BaseBackend implements EntryChangesListener {
   DirectoryString eclBase = null;
   Vector changesVector = null;
   private String changeFileName = null;
   private String suffix = null;
   private ChangeLogWriter clw = null;
   private volatile int changeLow = 0;
   private volatile int changeHigh = -1;
   private volatile int lowKeep = 0;
   private int changeLogThreshold = 5000;
   private RandomAccessFile indexFile = null;
   private RandomAccessFile changeFile = null;
   private static final String CONFIG_SUFFIX = "suffix";
   private Replication replicationThread = null;

   public BackendChangeLog(Hashtable config, Replication replication) {
      super(config);
      this.suffix = ((DirectoryString)config.get("suffix")).toString();
      this.replicationThread = replication;
      this.changeLogThreshold = Integer.getInteger("weblogic.security.ldap.changeLogThreshold", 5000);
      Logger.getInstance().log(7, this, Messages.getString("Changelog_threshold_6") + this.changeLogThreshold);
      this.changesVector = new Vector();
      this.eclBase = new DirectoryString("");
      String propName = (String)ServerConfig.getInstance().get("vde.changelog.file");
      String ihome = System.getProperty("vde.home");
      if (ihome == null) {
         this.changeFileName = propName;
      } else {
         this.changeFileName = ihome + "/" + propName;
      }

      this.clw = new ChangeLogWriter(this.changeFileName, this);
      this.changeHigh = this.clw.getHighChange();
      this.changeLow = this.clw.getLowChange();
      this.open();
   }

   private synchronized void truncate() {
      if (this.changeLow + this.changeLogThreshold >= this.lowKeep) {
         Logger.getInstance().log(7, this, Messages.getString("Changelog_threshold_7") + this.lowKeep);
      } else {
         Logger.getInstance().log(5, this, Messages.getString("Truncating_Changelog_5"));
         Logger.getInstance().log(7, this, Messages.getString("Changelog_threshold_8") + this.lowKeep);
         this.clw.setEndWriter();

         while(this.clw.isRunning()) {
            this.wait1sec();
         }

         try {
            File ifNew = new File(this.changeFileName + ".index-n");
            File cfNew = new File(this.changeFileName + ".data-n");
            if (ifNew.exists() || cfNew.exists()) {
               ifNew.delete();
               ifNew = null;
               cfNew.delete();
               cfNew = null;
            }

            RandomAccessFile nif = new RandomAccessFile(this.changeFileName + ".index-n", "rw");
            RandomAccessFile ncf = new RandomAccessFile(this.changeFileName + ".data-n", "rw");
            nif.seek(0L);
            nif.writeInt(this.lowKeep + 1);
            nif.writeLong(1L);
            if (this.changeHigh > this.lowKeep) {
               this.indexFile.seek(0L);
               int oldLowKeep = this.indexFile.readInt();
               long baseloc = 0L;
               this.indexFile.seek((long)((this.lowKeep - oldLowKeep + 2) * 12));
               baseloc = this.indexFile.readLong();
               this.indexFile.seek((long)((this.lowKeep - oldLowKeep + 2) * 12));

               for(int i = this.lowKeep + 1; i <= this.changeHigh; ++i) {
                  nif.writeLong(this.indexFile.readLong() - baseloc);
                  nif.writeInt(this.indexFile.readInt());
               }

               this.changeFile.seek(baseloc);
               ncf.seek(0L);
               byte[] buf = new byte[10000];
               int len = false;

               int len;
               while((len = this.changeFile.read(buf)) != -1) {
                  ncf.write(buf, 0, len);
               }
            }

            nif.close();
            ncf.close();
            this.close();
            File newif = new File(this.changeFileName + ".index-n");
            File newcf = new File(this.changeFileName + ".data-n");
            File oldif = new File(this.changeFileName + ".index");
            File oldcf = new File(this.changeFileName + ".data");
            oldif.delete();
            oldcf.delete();
            newif.renameTo(oldif);
            newcf.renameTo(oldcf);
            this.clw = new ChangeLogWriter(this.changeFileName, this);
            this.changeHigh = this.clw.getHighChange();
            this.changeLow = this.clw.getLowChange();
            this.open();
         } catch (IOException var10) {
            Logger.getInstance().log(0, this, Messages.getString("Error_truncating_change_log___14") + var10.getMessage());
            this.close();
            this.clw = new ChangeLogWriter(this.changeFileName, this);
            this.changeHigh = this.clw.getHighChange();
            this.changeLow = this.clw.getLowChange();
            this.open();
         }

      }
   }

   private void open() {
      try {
         this.indexFile = new RandomAccessFile(this.changeFileName + ".index", "r");
         this.changeFile = new RandomAccessFile(this.changeFileName + ".data", "r");
      } catch (FileNotFoundException var2) {
         Logger.getInstance().log(3, this, Messages.getString("Error_opening_changelog_database___19") + var2.getMessage());
      }

   }

   private void close() {
      try {
         if (this.indexFile != null) {
            this.indexFile.close();
            this.indexFile = null;
         }

         if (this.changeFile != null) {
            this.changeFile.close();
            this.changeFile = null;
         }
      } catch (IOException var2) {
         Logger.getInstance().log(0, this, Messages.getString("Error_closing_changelog_database___20") + var2.getMessage());
      }

   }

   public int getChangeHigh() {
      return this.changeHigh;
   }

   public void setLowKeep(int lowKeep) {
      this.lowKeep = lowKeep;
   }

   public boolean bind(DirectoryString dn, BinarySyntax password) {
      return false;
   }

   public boolean doBind() {
      return false;
   }

   private Vector evaluateFilter(Filter currentFilter, DirectoryString base, int scope) {
      Vector matchThisFilter = new Vector();
      String matchType;
      String matchVal;
      int i;
      int i;
      Vector matched;
      Enumeration matchEnum;
      Iterator orEnum;
      switch (currentFilter.getSelector()) {
         case 0:
            boolean firstAnd = true;
            orEnum = currentFilter.getAnd().iterator();

            while(true) {
               while(orEnum.hasNext()) {
                  matched = this.evaluateFilter((Filter)orEnum.next(), base, scope);
                  if (firstAnd) {
                     firstAnd = false;
                     matchEnum = matched.elements();

                     while(matchEnum.hasMoreElements()) {
                        matchThisFilter.addElement(matchEnum.nextElement());
                     }
                  } else {
                     Vector inBoth = new Vector();
                     Enumeration matchEnum = matched.elements();

                     while(matchEnum.hasMoreElements()) {
                        Object matchObj = matchEnum.nextElement();
                        if (matchThisFilter.contains(matchObj)) {
                           inBoth.addElement(matchObj);
                        }
                     }

                     matchThisFilter = inBoth;
                  }
               }

               return matchThisFilter;
            }
         case 1:
            orEnum = currentFilter.getOr().iterator();

            while(orEnum.hasNext()) {
               matched = this.evaluateFilter((Filter)orEnum.next(), base, scope);
               matchEnum = matched.elements();

               while(matchEnum.hasMoreElements()) {
                  matchThisFilter.addElement(matchEnum.nextElement());
               }
            }
         case 2:
         case 4:
         default:
            break;
         case 3:
            matchType = new String(currentFilter.getEqualityMatch().getAttributeDesc().toByteArray());
            matchVal = new String(currentFilter.getEqualityMatch().getAssertionValue().toByteArray());
            if (Logger.getInstance().isLogable(7)) {
               Logger.getInstance().log(7, this, "Filter: " + matchType + "=" + matchVal);
               Logger.getInstance().log(7, this, "Base: " + base);
            }

            if (matchType.equalsIgnoreCase("changenumber")) {
               i = new Integer(matchVal);
               if (i >= this.changeLow && i <= this.changeHigh) {
                  matchThisFilter.addElement(new Integer(i));
               }
            }

            if (matchType.equalsIgnoreCase("objectclass") && matchVal.equalsIgnoreCase("changeLogEntry")) {
               for(i = this.changeLow; i <= this.changeHigh; ++i) {
                  matchThisFilter.addElement(new Integer(i));
               }
            }
            break;
         case 5:
            matchType = new String(currentFilter.getGreaterOrEqual().getAttributeDesc().toByteArray());
            matchVal = new String(currentFilter.getGreaterOrEqual().getAssertionValue().toByteArray());
            if (matchType.equalsIgnoreCase("changenumber")) {
               i = new Integer(matchVal);

               for(i = i; i <= this.changeHigh; ++i) {
                  matchThisFilter.addElement(new Integer(i));
               }
            }
            break;
         case 6:
            matchType = new String(currentFilter.getLessOrEqual().getAttributeDesc().toByteArray());
            matchVal = new String(currentFilter.getLessOrEqual().getAssertionValue().toByteArray());
            if (matchType.equalsIgnoreCase("changenumber")) {
               i = new Integer(matchVal);

               for(i = 0; i <= this.changeHigh && i <= i; ++i) {
                  matchThisFilter.addElement(new Integer(i));
               }
            }
            break;
         case 7:
            matchType = new String(currentFilter.getPresent().toByteArray());
            if (matchType.equalsIgnoreCase("changenumber") || matchType.equalsIgnoreCase("objectclass")) {
               for(i = this.changeLow; i <= this.changeHigh; ++i) {
                  matchThisFilter.addElement(new Integer(i));
               }
            }
      }

      return matchThisFilter;
   }

   public void finalize() {
      this.close();
   }

   public EntrySet get(DirectoryString binddn, DirectoryString base, int scope, Filter filter, boolean attrsOnly, Vector attrs) throws DirectoryException {
      Vector entries = this.evaluateFilter(filter, base, scope);
      return new GenericEntrySet(this, entries);
   }

   public synchronized Entry getByID(Integer id) {
      if (this.clw == null) {
         throw new IllegalStateException("BackendChangeLog is closed");
      } else if (id <= this.changeHigh && id >= this.changeLow) {
         Entry oneChange = null;

         try {
            oneChange = new Entry(new DirectoryString("changeNumber=" + id + "," + this.suffix));
         } catch (InvalidDNException var18) {
         }

         Vector ocVec = new Vector();
         ocVec.addElement(new DirectoryString("changeLogEntry"));
         ocVec.addElement(new DirectoryString("top"));
         oneChange.put(new DirectoryString("objectClass"), ocVec);
         Vector cnumVec = new Vector();
         cnumVec.addElement(new IntegerSyntax(id));
         oneChange.put(new DirectoryString("changeNumber"), cnumVec);
         EntryChanges ec = null;
         byte[] cbytes = null;
         int ce;
         synchronized(this.indexFile) {
            try {
               long cfSize = null == this.changeFile ? 0L : this.changeFile.length();
               this.indexFile.seek((long)((id + 1 - this.changeLow) * 12));
               long pos = this.indexFile.readLong();
               ce = this.indexFile.readInt();

               try {
                  checkChangeFileEntryLength(cfSize, pos, ce, "changelog_entry#:" + id);
               } catch (IndexOutOfBoundsException var15) {
                  markManagedAsInvalid(this.getCurrentAgreementName());
                  throw var15;
               }

               this.changeFile.seek(pos);
               cbytes = new byte[ce];
               this.changeFile.readFully(cbytes, 0, ce);
            } catch (IOException var16) {
               markManagedAsInvalid(this.getCurrentAgreementName());
               Logger.getInstance().log(0, this, Messages.getString("Error_reading_changelog_entry#___37") + id);
            }
         }

         ec = new EntryChanges(cbytes);
         DirectoryString dn = null;
         DirectoryString cType = null;
         byte[] changes = null;
         if (ec.getChangeType() == 1) {
            dn = ec.getFullEntry().getName();
            cType = new DirectoryString("add");
            changes = ec.getFullEntry().toLDIF().getBytes();
         } else {
            dn = ec.getName();
            if (ec.getChangeType() == 2) {
               cType = new DirectoryString("modify");
               StringBuffer sb = new StringBuffer();
               EntryChange[] entch = ec.getEntryChanges();

               for(ce = 0; ce < entch.length; ++ce) {
                  sb.append(entch[ce].toLDIF());
               }

               changes = sb.toString().getBytes();
            } else if (ec.getChangeType() == 3) {
               cType = new DirectoryString("delete");
            } else if (ec.getChangeType() == 4) {
               cType = new DirectoryString("rename");
            } else {
               cType = new DirectoryString("unknown");
            }
         }

         Vector tdnVec = new Vector();
         tdnVec.addElement(dn);
         oneChange.put(new DirectoryString("targetDN"), tdnVec);
         Vector ctypeVec = new Vector();
         ctypeVec.addElement(cType);
         oneChange.put(new DirectoryString("changeType"), ctypeVec);
         if (changes != null) {
            Vector changesVec = new Vector();
            changesVec.addElement(new BinarySyntax(changes));
            oneChange.put(new DirectoryString("changes"), changesVec);
         }

         return oneChange;
      } else {
         markManagedAsInvalid(this.getCurrentAgreementName());
         Logger.getInstance().log(0, this, Messages.getString("Invalid_changelog_replication_index#___86") + id);
         return null;
      }
   }

   public synchronized EntryChanges getChange(int cnum) throws Exception {
      if (this.clw == null) {
         throw new IllegalStateException("BackendChangeLog is closed");
      } else if (cnum >= this.changeLow && cnum <= this.changeHigh) {
         EntryChanges ec = null;
         byte[] cbytes = null;
         long cfSize = null == this.changeFile ? 0L : this.changeFile.length();
         byte[] cbytes;
         synchronized(this.indexFile) {
            try {
               this.indexFile.seek((long)((1 + cnum - this.changeLow) * 12));
               long pos = this.indexFile.readLong();
               int len = this.indexFile.readInt();

               try {
                  checkChangeFileEntryLength(cfSize, pos, len, "changelog_entry#:" + cnum);
               } catch (IndexOutOfBoundsException var13) {
                  markManagedAsInvalid(this.getCurrentAgreementName());
                  throw var13;
               }

               this.changeFile.seek(pos);
               cbytes = new byte[len];
               this.changeFile.readFully(cbytes, 0, len);
            } catch (IOException var14) {
               markManagedAsInvalid(this.getCurrentAgreementName());
               Logger.getInstance().log(0, this, Messages.getString("Error_reading_changelog_entry#___46") + cnum);
               throw var14;
            } catch (IndexOutOfBoundsException var15) {
               Logger.getInstance().log(0, this, Messages.getString("Error_reading_changelog_entry#___46") + cnum);
               throw var15;
            } catch (NegativeArraySizeException var16) {
               markManagedAsInvalid(this.getCurrentAgreementName());
               Logger.getInstance().log(0, this, Messages.getString("Error_reading_changelog_entry#___46") + cnum);
               throw var16;
            }
         }

         try {
            return new EntryChanges(cbytes);
         } catch (ArrayIndexOutOfBoundsException var12) {
            markManagedAsInvalid(this.getCurrentAgreementName());
            Logger.getInstance().log(0, this, Messages.getString("Error_creating_entrychanges_from_changelog_entry#___87") + cnum);
            throw var12;
         }
      } else {
         markManagedAsInvalid(this.getCurrentAgreementName());
         Logger.getInstance().log(0, this, Messages.getString("Invalid_changelog_replication_index#___86") + cnum);
         throw new InvalidChangeLogIndexException(Messages.getString("Invalid_changelog_replication_index#___86") + cnum);
      }
   }

   public DirectoryString getECLBase() {
      return this.eclBase;
   }

   public synchronized void receiveEntryChanges(EntryChanges entryChanges) {
      if (this.clw == null) {
         throw new IllegalStateException("BackendChangeLog is closed");
      } else {
         this.truncate();
         this.changeHigh = this.clw.addChange(entryChanges);
         this.replicationThread.wakeUp();
      }
   }

   private synchronized void wait1sec() {
      try {
         this.wait(1000L);
      } catch (InterruptedException var2) {
      }

   }

   public synchronized void shutdown() {
      if (this.clw != null) {
         this.clw.shutdown();
         this.clw = null;
      }

      this.close();
   }

   public String getCurrentAgreementName() {
      return this.replicationThread.getCurrentAgreementName();
   }

   public static void checkChangeFileEntryLength(long fileSize, long fileOffset, int length, String fieldName) {
      if (length < 0) {
         throw new IndexOutOfBoundsException(MessageFormat.format(Messages.getString("Entry_checkEntryFieldLength_negativeLength"), fieldName, length));
      } else {
         if (fileSize >= 0L && fileOffset >= 0L) {
            long remaining = fileSize - fileOffset;
            if ((long)length > remaining) {
               throw new IndexOutOfBoundsException(MessageFormat.format(Messages.getString("Entry_checkEntryFieldLength_nonNegativeLength"), fieldName, length, remaining));
            }
         }

      }
   }

   public static void markManagedAsInvalid(String msName) {
      try {
         Class cls = Class.forName("weblogic.ldap.PreEmbeddedLDAPService");
         String methodName = "sendInvalidToManagedServer";
         Class[] params = new Class[]{String.class};
         Method send = cls.getMethod(methodName, params);
         Object[] parameters = new Object[]{msName};
         send.invoke((Object)null, parameters);
      } catch (Exception var6) {
         Throwable cause = var6.getCause();
         if (cause == null) {
            ;
         }

      }
   }
}
