package com.octetstring.vde.backend.standard;

import com.octetstring.nls.Messages;
import com.octetstring.vde.Entry;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TransactionLog {
   private String fname = null;
   private boolean lock = false;
   private RandomAccessFile raf = null;
   private RandomAccessFile wlf = null;
   private String TRANSLOG_NAME = ".tran";
   private String WRITELOC_NAME = ".twpos";
   private long writepos = 0L;
   private static byte[] transend = new byte[4];
   private static final int CHANGE_ADD = 1;
   private static final int CHANGE_MOD = 2;
   private static final int CHANGE_DEL = 3;
   private static final int CHANGE_REN = 4;

   public TransactionLog(String fname) throws IOException {
      transend[0] = -1;
      transend[1] = -1;
      transend[2] = -1;
      transend[3] = -1;
      this.fname = fname;
      if (!this.open()) {
         throw new IOException(Messages.getString("Unable_to_open_files_3"));
      } else {
         if (this.wlf.length() != 0L) {
            this.writepos = this.wlf.readLong();
         }

      }
   }

   private void setWritepos(long curWritepos) {
      this.writepos = curWritepos;

      try {
         this.wlf.seek(0L);
         this.wlf.writeLong(this.writepos);
      } catch (IOException var4) {
         Logger.getInstance().printStackTrace(var4);
      }

   }

   public long getWritepos() {
      return this.writepos;
   }

   public synchronized boolean add(Entry entry) {
      byte[] entryBytes = entry.getAsByteArray();
      this.lock();
      boolean wroteok = false;

      try {
         if (this.raf == null) {
            boolean var4 = wroteok;
            return var4;
         }

         this.raf.seek(this.writepos);
         this.raf.write(1);
         this.raf.writeInt(entryBytes.length);
         this.raf.write(entryBytes, 0, entryBytes.length);
         this.raf.write(transend);
         this.writepos = this.writepos + 9L + (long)entryBytes.length;
         this.setWritepos(this.writepos);
         if (Logger.getInstance().isLogable(7)) {
            Logger.getInstance().log(7, this, Messages.getString("Wrote_Add_Transaction___4") + entry.getName());
         }

         wroteok = true;
      } catch (IOException var8) {
         Logger.getInstance().log(0, this, Messages.getString("Error_writing____5") + entry.getName() + Messages.getString("___ADD_to_Transaction_Log___6") + var8.getMessage());
      } finally {
         this.unlock();
      }

      return wroteok;
   }

   private void close() {
      try {
         if (this.raf != null) {
            this.raf.close();
            this.raf = null;
         }
      } catch (IOException var3) {
         Logger.getInstance().printStackTrace(var3);
      }

      try {
         if (this.wlf != null) {
            this.wlf.close();
            this.wlf = null;
         }
      } catch (IOException var2) {
         Logger.getInstance().printStackTrace(var2);
      }

   }

   public synchronized boolean delete(int id) {
      this.lock();
      boolean wroteok = false;

      try {
         if (this.raf == null) {
            boolean var3 = wroteok;
            return var3;
         }

         this.raf.seek(this.writepos);
         this.raf.write(3);
         this.raf.writeInt(id);
         this.raf.write(transend);
         this.writepos += 9L;
         this.setWritepos(this.writepos);
         if (Logger.getInstance().isLogable(7)) {
            Logger.getInstance().log(7, this, Messages.getString("Wrote_Delete_Transaction__entry#_7") + id);
         }

         wroteok = true;
      } catch (IOException var7) {
         Logger.getInstance().log(0, this, Messages.getString("Error_writing___entry#_8") + id + Messages.getString("___DELETE_to_Transaction_Log___9") + var7.getMessage());
      } finally {
         this.unlock();
      }

      return wroteok;
   }

   protected void finalize() {
      this.close();
   }

   public synchronized void lock() {
      if (this.lock) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
            return;
         }
      }

      this.lock = true;
   }

   public synchronized boolean modify(Entry entry) {
      byte[] entryBytes = entry.getAsByteArray();
      this.lock();
      boolean wroteok = false;

      try {
         if (this.raf == null) {
            boolean var4 = wroteok;
            return var4;
         }

         this.raf.seek(this.writepos);
         this.raf.write(2);
         this.raf.writeInt(entryBytes.length);
         this.raf.write(entryBytes, 0, entryBytes.length);
         this.raf.write(transend);
         this.writepos = this.writepos + 9L + (long)entryBytes.length;
         this.setWritepos(this.writepos);
         if (Logger.getInstance().isLogable(7)) {
            Logger.getInstance().log(7, this, Messages.getString("Wrote_Modify_Transaction___10") + entry.getName());
         }

         wroteok = true;
      } catch (IOException var8) {
         Logger.getInstance().log(0, this, Messages.getString("Error_writing____11") + entry.getName() + Messages.getString("___MODIFY_to_Transaction_Log___12") + var8.getMessage());
      } finally {
         this.unlock();
      }

      return wroteok;
   }

   public synchronized boolean rename(int id, DirectoryString newname) {
      this.lock();
      boolean wroteok = false;

      try {
         if (this.raf == null) {
            boolean var4 = wroteok;
            return var4;
         }

         this.raf.seek(this.writepos);
         this.raf.write(4);
         this.raf.writeInt(id);
         this.raf.writeInt(newname.length());
         this.raf.write(newname.getBytes(), 0, newname.length());
         this.raf.write(transend);
         this.writepos = this.writepos + 13L + (long)newname.length();
         this.setWritepos(this.writepos);
         if (Logger.getInstance().isLogable(7)) {
            Logger.getInstance().log(7, this, Messages.getString("Wrote_Rename_Transaction___13") + id + Messages.getString("_to__14") + newname);
         }

         wroteok = true;
      } catch (IOException var8) {
         Logger.getInstance().log(0, this, Messages.getString("Error_writing____15") + id + Messages.getString("___RENAME_to_Transaction_Log___16") + var8.getMessage());
      } finally {
         this.unlock();
      }

      return wroteok;
   }

   private boolean open() {
      try {
         this.raf = new RandomAccessFile(this.fname + this.TRANSLOG_NAME, "rw");
         this.wlf = new RandomAccessFile(this.fname + this.WRITELOC_NAME, "rw");
         return true;
      } catch (FileNotFoundException var2) {
         Logger.getInstance().log(0, this, Messages.getString("Error_opening_the_Transaction_Log___19") + var2.getMessage());
         return false;
      }
   }

   public synchronized void truncate(long skipBytes) {
      this.lock();
      Logger.getInstance().log(5, this, Messages.getString("Truncating_Transaction_Log_20"));
      this.close();

      try {
         FileInputStream fis = new FileInputStream(this.fname + this.TRANSLOG_NAME);
         FileOutputStream fos = new FileOutputStream(this.fname + this.TRANSLOG_NAME + ".new");
         fis.skip(skipBytes);
         byte[] buffer = new byte[10000];
         int bread = false;

         int bread;
         while((bread = fis.read(buffer)) != -1) {
            fos.write(buffer, 0, bread);
         }

         fis.close();
         fos.close();
         File oldfile = new File(this.fname + this.TRANSLOG_NAME);
         oldfile.delete();
         File newfile = new File(this.fname + this.TRANSLOG_NAME + ".new");
         this.writepos = newfile.length();
         newfile.renameTo(oldfile);
         this.open();
         this.setWritepos(this.writepos);
      } catch (IOException var12) {
         Logger.getInstance().log(0, this, Messages.getString("Error_truncating_transaction_log._22"));
      } finally {
         this.unlock();
      }

   }

   public synchronized void unlock() {
      this.lock = false;
      this.notify();
   }

   public void shutdown() {
      this.close();
   }
}
