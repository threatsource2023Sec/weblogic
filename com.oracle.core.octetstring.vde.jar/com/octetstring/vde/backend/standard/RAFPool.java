package com.octetstring.vde.backend.standard;

import com.octetstring.nls.Messages;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ObjectPool;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

class RAFPool extends ObjectPool {
   private File fileName = null;

   public RAFPool() {
   }

   public RAFPool(File fileName) {
      this.fileName = fileName;
   }

   public File getFileName() {
      return this.fileName;
   }

   public void setFileName(File newFileName) {
      this.fileName = newFileName;
   }

   public Object create() throws Exception {
      return new RandomAccessFile(this.fileName, "rw");
   }

   public void expire(Object o) {
      try {
         ((RandomAccessFile)o).close();
      } catch (IOException var3) {
         Logger.getInstance().log(0, this, Messages.getString("Unable_to_Expire_RandomAccessFile___2") + var3.getMessage());
      }

   }

   public boolean validate(Object o) {
      return true;
   }
}
