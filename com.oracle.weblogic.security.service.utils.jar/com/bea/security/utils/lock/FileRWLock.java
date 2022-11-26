package com.bea.security.utils.lock;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileRWLock {
   private String fileName;
   private FileChannel channel = null;
   private FileLock lock = null;
   private FileInputStream in = null;
   private FileOutputStream out = null;

   public FileRWLock(String fileName) {
      this.fileName = fileName;
   }

   public FileLock readLock() throws IOException {
      this.in = new FileInputStream(this.fileName);
      this.channel = this.in.getChannel();
      this.lock = this.channel.lock(0L, Long.MAX_VALUE, true);
      return this.lock;
   }

   public void readUnlock() throws IOException {
      if (this.lock != null && this.lock.isValid()) {
         this.lock.release();
      }

      if (this.channel != null) {
         this.channel.close();
      }

      if (this.in != null) {
         this.in.close();
      }

   }

   public FileLock writeLock() throws IOException {
      this.out = new FileOutputStream(this.fileName);
      this.channel = this.out.getChannel();
      this.lock = this.channel.lock();
      return this.lock;
   }

   public void writeUnlock() throws IOException {
      if (this.lock != null && this.lock.isValid()) {
         this.lock.release();
      }

      if (this.channel != null) {
         this.channel.close();
      }

      if (this.out != null) {
         this.out.flush();
         this.out.close();
      }

   }

   public FileInputStream getFileInputStream() {
      return this.in;
   }

   public FileOutputStream getFileOutputStream() {
      return this.out;
   }
}
