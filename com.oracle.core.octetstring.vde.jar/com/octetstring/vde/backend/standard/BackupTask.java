package com.octetstring.vde.backend.standard;

import com.octetstring.nls.Messages;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.TimedActivityTask;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BackupTask implements TimedActivityTask {
   private int hour;
   private int minute;
   private int numbackups;
   private String basefn = null;
   private String backupbase = null;
   private BackendStandard myback;
   private boolean srun;
   private static final String FILE_DATA = ".data";
   private static final String FILE_INDEX = ".index";
   private static final String FILE_DELETE = ".delete";
   private static final String FILE_TRAN = ".tran";
   private static final String FILE_TRANWP = ".twpos";
   private static final String FILE_TRANRP = ".trpos";

   public BackupTask(BackendStandard myback, int hour, int minute, int numbackups, String basefn, String backupbase) {
      this.hour = hour;
      this.minute = minute;
      this.numbackups = numbackups;
      this.basefn = basefn;
      this.backupbase = backupbase;
      this.myback = myback;
   }

   public void runTask() {
      this.myback.lockWrites();

      try {
         File newfile;
         File zout;
         for(int i = this.numbackups - 2; i >= 0; --i) {
            newfile = new File(this.backupbase + "." + i + ".zip");
            zout = new File(this.backupbase + "." + (i + 1) + ".zip");
            zout.delete();
            newfile.renameTo(zout);
         }

         File oldfile = new File(this.backupbase + ".zip");
         newfile = new File(this.backupbase + ".0.zip");
         newfile.delete();
         oldfile.renameTo(newfile);
         zout = null;

         try {
            ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(this.backupbase + ".zip"));
            this.writeFile(zout, this.basefn + ".data");
            this.writeFile(zout, this.basefn + ".index");
            this.writeFile(zout, this.basefn + ".delete");
            this.writeFile(zout, this.basefn + ".tran");
            this.writeFile(zout, this.basefn + ".twpos");
            this.writeFile(zout, this.basefn + ".trpos");
            zout.close();
         } catch (IOException var8) {
            Logger.getInstance().log(0, this, Messages.getString("Error_occurred_during_backups"));
            Logger.getInstance().printStackTraceLog(var8);
         }

         this.myback.getDataFile().truncate();
      } finally {
         this.myback.unlockWrites();
      }

   }

   private void writeFile(ZipOutputStream zout, String fname) {
      try {
         InputStream in = new FileInputStream(fname);
         ZipEntry e = new ZipEntry(fname);
         zout.putNextEntry(e);
         int len = false;
         byte[] b = new byte[2048];

         int len;
         while((len = in.read(b)) != -1) {
            zout.write(b, 0, len);
         }

         zout.closeEntry();
      } catch (IOException var7) {
         Logger.getInstance().log(0, this, Messages.getString("Error_occurred_during_backups"));
         Logger.getInstance().printStackTraceLog(var7);
      }

   }

   public int getHour() {
      return this.hour;
   }

   public int getMinute() {
      return this.minute;
   }

   public boolean hasRun() {
      return this.srun;
   }

   public void setRun(boolean srun) {
      this.srun = srun;
   }
}
