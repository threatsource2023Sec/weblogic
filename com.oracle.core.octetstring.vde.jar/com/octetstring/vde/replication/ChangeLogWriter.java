package com.octetstring.vde.replication;

import com.octetstring.nls.Messages;
import com.octetstring.vde.EntryChanges;
import com.octetstring.vde.util.Logger;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;

public class ChangeLogWriter {
   RandomAccessFile indexFile = null;
   RandomAccessFile changeFile = null;
   private BackendChangeLog bcl = null;
   private boolean running = false;
   private int lowChange = 0;
   private int highChange = -1;
   private volatile Vector changeLog = null;
   private volatile boolean endWriter = false;
   public static final long FIRST_VER = 0L;
   public static final long SECOND_VER = 1L;

   public ChangeLogWriter(String changeFileName, BackendChangeLog bcl) {
      this.bcl = bcl;
      if (this.changeLog == null) {
         this.changeLog = new Vector();
      }

      try {
         this.indexFile = new RandomAccessFile(changeFileName + ".index", "rw");
         this.changeFile = new RandomAccessFile(changeFileName + ".data", "rw");
         if (this.indexFile.length() > 4L) {
            this.indexFile.seek(0L);
            this.lowChange = this.indexFile.readInt();
            long version = this.indexFile.readLong();
            if (version == 0L) {
               this.convert(changeFileName);
            }
         } else {
            this.indexFile.seek(0L);
            this.indexFile.writeInt(0);
            this.indexFile.writeLong(1L);
            this.lowChange = 0;
         }

         this.highChange = (int)this.indexFile.length() / 12 - 2 + this.lowChange;
         this.indexFile.seek(this.indexFile.length());
         this.changeFile.seek(this.changeFile.length());
      } catch (FileNotFoundException var5) {
         Logger.getInstance().log(0, this, Messages.getString("File_Not_Found___5") + var5.getMessage());
      } catch (IOException var6) {
         Logger.getInstance().log(0, this, Messages.getString("IO_Error___6") + var6.getMessage());
      } catch (IndexOutOfBoundsException var7) {
         Logger.getInstance().log(0, this, Messages.getString("Error_converting_change_log_file") + var7.getMessage());
      }

   }

   public int getHighChange() {
      return this.highChange;
   }

   public int getLowChange() {
      return this.lowChange;
   }

   public void setEndWriter() {
      this.endWriter = true;
      this.close();
      this.running = false;
   }

   public synchronized int addChange(EntryChanges entryChanges) {
      this.notifyChange();
      byte[] ecBytes = entryChanges.getAsByteArray();

      try {
         long changeFilePtr = this.changeFile.length();
         this.changeFile.write(ecBytes);

         try {
            this.indexFile.writeLong(changeFilePtr);
            this.indexFile.writeInt(ecBytes.length);
            ++this.highChange;
         } catch (IOException var6) {
            this.changeFile.setLength(changeFilePtr);
            throw var6;
         }
      } catch (IOException var7) {
         Logger.getInstance().log(0, this, Messages.getString("Error_writing_to_changelog_file___8") + var7.getMessage());
      }

      return this.highChange;
   }

   public void finalize() {
      this.close();
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
         Logger.getInstance().log(0, this, Messages.getString("Error_Closing_Files___7") + var2.getMessage());
      }

   }

   private synchronized void notifyChange() {
      this.notify();
   }

   public boolean isRunning() {
      return this.running;
   }

   public void run() {
      this.running = true;

      while(true) {
         for(; this.changeLog.isEmpty(); this.waitChange()) {
            if (this.endWriter) {
               this.close();
               this.running = false;
            }
         }

         EntryChanges ec = (EntryChanges)this.changeLog.elementAt(0);
         this.changeLog.removeElement(ec);
         byte[] ecBytes = ec.getAsByteArray();

         try {
            this.indexFile.writeLong(this.changeFile.length());
            this.indexFile.writeInt(ecBytes.length);
            this.changeFile.write(ecBytes);
         } catch (IOException var4) {
            Logger.getInstance().log(0, this, Messages.getString("Error_writing_to_changelog_file___8") + var4.getMessage());
         }
      }
   }

   private synchronized void waitChange() {
      try {
         this.wait();
      } catch (InterruptedException var2) {
      }

   }

   public void shutdown() {
      this.close();
   }

   private synchronized void convert(String changeFileName) throws IOException, IndexOutOfBoundsException {
      try {
         RandomAccessFile nif = new RandomAccessFile(changeFileName + ".index-n", "rw");
         RandomAccessFile ncf = new RandomAccessFile(changeFileName + ".data-n", "rw");
         nif.seek(0L);
         nif.writeInt(this.lowChange);
         nif.writeLong(1L);
         ncf.seek(0L);
         this.indexFile.seek(12L);
         int cnum = this.lowChange;
         boolean eof = false;

         File newif;
         while(!eof) {
            newif = null;
            long cfSize = null == this.changeFile ? 0L : this.changeFile.length();

            byte[] cbytes;
            try {
               long pos = this.indexFile.readLong();
               int len = this.indexFile.readInt();

               try {
                  BackendChangeLog.checkChangeFileEntryLength(cfSize, pos, len, "changelog_entry#:" + cnum);
               } catch (IndexOutOfBoundsException var14) {
                  BackendChangeLog.markManagedAsInvalid(this.bcl.getCurrentAgreementName());
                  throw var14;
               }

               this.changeFile.seek(pos);
               cbytes = new byte[len];
               this.changeFile.readFully(cbytes, 0, len);
               ++cnum;
            } catch (EOFException var15) {
               eof = true;
               continue;
            } catch (IOException var16) {
               Logger.getInstance().log(0, this, Messages.getString("Error_reading_changelog_entry#___46") + cnum);
               throw var16;
            } catch (IndexOutOfBoundsException var17) {
               Logger.getInstance().log(0, this, Messages.getString("Error_reading_changelog_entry#___46") + cnum);
               throw var17;
            }

            EntryChanges ec = new EntryChanges(cbytes, true);
            byte[] ecBytes = ec.getAsByteArray();

            try {
               nif.writeLong(ncf.length());
               nif.writeInt(ecBytes.length);
               ncf.write(ecBytes);
            } catch (IOException var13) {
               Logger.getInstance().log(0, this, Messages.getString("Error_writing_to_changelog_file___8") + var13.getMessage());
               throw var13;
            }
         }

         nif.close();
         ncf.close();
         this.close();
         newif = new File(changeFileName + ".index-n");
         File newcf = new File(changeFileName + ".data-n");
         File oldif = new File(changeFileName + ".index");
         File oldcf = new File(changeFileName + ".data");
         oldif.delete();
         oldcf.delete();
         newif.renameTo(oldif);
         newcf.renameTo(oldcf);
         this.indexFile = new RandomAccessFile(changeFileName + ".index", "rw");
         this.changeFile = new RandomAccessFile(changeFileName + ".data", "rw");
         this.indexFile.seek(0L);
      } catch (IOException var18) {
         Logger.getInstance().log(0, this, Messages.getString("Error_converting_change_log_file") + var18.getMessage());
      }

   }
}
