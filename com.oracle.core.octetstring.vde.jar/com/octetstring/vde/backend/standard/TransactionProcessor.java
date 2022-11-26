package com.octetstring.vde.backend.standard;

import com.octetstring.nls.Messages;
import com.octetstring.vde.Entry;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.replication.BackendChangeLog;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.Logger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TransactionProcessor extends Thread {
   private RandomAccessFile tranfile = null;
   private RandomAccessFile posfile = null;
   private DataFile dataFile = null;
   private TransactionLog tlog = null;
   private long tlogsize = 1000000L;
   private String fileName = null;
   private static final String TRANFILE_NAME = ".tran";
   private static final String POSFILE_NAME = ".trpos";
   private static final int CHANGE_ADD = 1;
   private static final int CHANGE_MOD = 2;
   private static final int CHANGE_DEL = 3;
   private static final int CHANGE_REN = 4;
   private boolean moreTransactions = true;

   public TransactionProcessor(DataFile dataFile, String fileName, TransactionLog tlog, long tlogsize) {
      super("VDE Transaction Processor Thread");
      this.setPriority(2);
      this.dataFile = dataFile;
      this.tlog = tlog;
      this.tlogsize = tlogsize;
      this.fileName = fileName;
      this.open();
   }

   public void setTLogSize(long tlogsize) {
      this.tlogsize = tlogsize;
   }

   public int transactionCount() {
      long pos = 0L;
      int count = 0;

      try {
         if (this.posfile.length() > 0L) {
            pos = this.posfile.readLong();
         }

         this.tranfile.seek(pos);
      } catch (IOException var11) {
         Logger.getInstance().printStackTrace(var11);
         return 0;
      }

      int numneg = 0;
      boolean optype = true;
      int j = false;
      byte[] ctbytes = new byte[4096];
      int mylen = false;

      int mylen;
      try {
         while((mylen = this.tranfile.read(ctbytes)) > 0) {
            for(int j = 0; j < mylen; ++j) {
               if (optype) {
                  if (ctbytes[j] == 1) {
                     ++count;
                  }

                  optype = false;
               } else if (ctbytes[j] == -1) {
                  ++numneg;
                  if (numneg == 4) {
                     optype = true;
                     numneg = 0;
                  }
               } else {
                  numneg = 0;
               }
            }
         }
      } catch (IOException var10) {
      }

      return count;
   }

   private void close() {
      try {
         if (this.tranfile != null) {
            this.tranfile.close();
            this.tranfile = null;
         }
      } catch (IOException var3) {
         Logger.getInstance().printStackTrace(var3);
      }

      try {
         if (this.posfile != null) {
            this.posfile.close();
            this.posfile = null;
         }
      } catch (IOException var2) {
         Logger.getInstance().printStackTrace(var2);
      }

   }

   protected void finalize() {
      this.close();
   }

   public synchronized void noteChange() {
      this.notify();
   }

   public boolean isMoreTransactions() {
      return this.moreTransactions;
   }

   private void open() {
      try {
         this.tranfile = new RandomAccessFile(this.fileName + ".tran", "rw");
         this.posfile = new RandomAccessFile(this.fileName + ".trpos", "rw");
      } catch (FileNotFoundException var2) {
         Logger.getInstance().printStackTrace(var2);
      }

   }

   public void run() {
      try {
         long pos = 0L;

         try {
            if (this.posfile.length() > 0L) {
               this.posfile.seek(0L);
               pos = this.posfile.readLong();
            }

            this.tranfile.seek(pos);
         } catch (IOException var26) {
            Logger.getInstance().printStackTrace(var26);
         }

         byte[] ctbyte = new byte[1];
         int changeType = false;
         long startPos = 0L;

         while(true) {
            while(true) {
               try {
                  if (this.tlogsize != -1L && pos >= this.tlogsize) {
                     this.close();
                     this.tlog.truncate(pos);
                     this.open();
                     pos = 0L;
                     this.writePos(pos);
                  }

                  if (pos + 1L >= this.tlog.getWritepos()) {
                     if (this.tlogsize == -1L) {
                        this.close();
                        this.tlog.truncate(pos);
                        this.open();
                        pos = 0L;
                        this.writePos(pos);
                     }

                     this.waitChange();
                  }

                  this.moreTransactions = true;
                  this.tranfile.seek(pos);
                  this.tranfile.readFully(ctbyte, 0, 1);
                  ++pos;
                  int changeType = ctbyte[0];
                  int renameId;
                  byte[] newname;
                  if (changeType != 1 && changeType != 2) {
                     if (changeType == 3) {
                        while(pos + 8L > this.tlog.getWritepos()) {
                           this.waitwrite();
                        }

                        renameId = this.tranfile.readInt();
                        byte[] endbytes = new byte[4];
                        this.tranfile.readFully(endbytes, 0, 4);
                        pos += 8L;
                        if (endbytes[0] == -1 && endbytes[1] == -1 && endbytes[2] == -1 && endbytes[3] == -1) {
                           this.dataFile.deleteEntry(renameId);
                           this.writePos(pos);
                           Logger.getInstance().log(7, this, Messages.getString("Parsed_Transaction/Wrote_7") + Messages.getString("_to_DataFile._8"));
                        } else {
                           pos = this.tlog.getWritepos();
                           BackendHandler.setReplicaInvalid();
                           Logger.getInstance().log(0, this, Messages.getString("Corrupt_Transaction_@__9") + pos);
                        }
                     }

                     if (changeType == 4) {
                        try {
                           while(pos + 8L > this.tlog.getWritepos()) {
                              this.waitwrite();
                           }

                           renameId = this.tranfile.readInt();
                           int newnamelen = this.tranfile.readInt();
                           long tfSize = null == this.tranfile ? 0L : this.tranfile.length();
                           long writePos = this.tlog.getWritepos();

                           try {
                              BackendChangeLog.checkChangeFileEntryLength(tfSize < writePos ? writePos : tfSize, pos, newnamelen + 12, "transaction_file_rename_id:" + renameId);
                           } catch (IndexOutOfBoundsException var24) {
                              BackendHandler.setReplicaInvalid();
                              throw var24;
                           }

                           while(pos + 4L + (long)newnamelen > this.tlog.getWritepos()) {
                              this.waitwrite();
                           }

                           newname = new byte[newnamelen];
                           this.tranfile.readFully(newname, 0, newnamelen);
                           byte[] endbytes = new byte[4];
                           this.tranfile.readFully(endbytes, 0, 4);
                           pos = pos + 12L + (long)newnamelen;
                           if (endbytes[0] == -1 && endbytes[1] == -1 && endbytes[2] == -1 && endbytes[3] == -1) {
                              this.dataFile.renameEntry(renameId, new DirectoryString(newname));
                              this.writePos(pos);
                              Logger.getInstance().log(7, this, Messages.getString("Parsed_Transaction/Wrote_to_DataFile._10"));
                           } else {
                              pos = this.tlog.getWritepos();
                              BackendHandler.setReplicaInvalid();
                              Logger.getInstance().log(0, this, Messages.getString("Corrupt_Transaction_@__11") + pos);
                           }
                        } catch (IndexOutOfBoundsException var27) {
                           pos = this.tlog.getWritepos();
                           Logger.getInstance().log(0, this, Messages.getString("Corrupt_Transaction_@__11") + pos + " " + var27);
                        }
                     }
                  } else {
                     try {
                        while(pos + 4L > this.tlog.getWritepos()) {
                           this.waitwrite();
                        }

                        renameId = this.tranfile.readInt();
                        long tfSize = null == this.tranfile ? 0L : this.tranfile.length();
                        long writePos = this.tlog.getWritepos();

                        try {
                           BackendChangeLog.checkChangeFileEntryLength(tfSize < writePos ? writePos : tfSize, pos, renameId + 8, "transaction_file_add_remove");
                        } catch (IndexOutOfBoundsException var25) {
                           BackendHandler.setReplicaInvalid();
                           throw var25;
                        }

                        byte[] entbytes = new byte[renameId];

                        while(pos + (long)renameId + 8L > this.tlog.getWritepos()) {
                           this.waitwrite();
                        }

                        this.tranfile.readFully(entbytes, 0, renameId);
                        newname = new byte[4];
                        this.tranfile.readFully(newname, 0, 4);
                        pos = pos + (long)renameId + 8L;
                        Entry ent = new Entry(entbytes);
                        if (newname[0] == -1 && newname[1] == -1 && newname[2] == -1 && newname[3] == -1) {
                           if (changeType == 1) {
                              this.dataFile.addEntry(ent);
                           } else if (changeType == 2) {
                              this.dataFile.modifyEntry(ent);
                           }

                           this.writePos(pos);
                           Logger.getInstance().log(7, this, Messages.getString("Parsed_Transaction/Wrote_to_DataFile._5"));
                        } else {
                           pos = this.tlog.getWritepos();
                           BackendHandler.setReplicaInvalid();
                           Logger.getInstance().log(0, this, Messages.getString("Corrupt_Transaction_@__6") + pos);
                        }
                     } catch (IndexOutOfBoundsException var28) {
                        pos = this.tlog.getWritepos();
                        Logger.getInstance().log(0, this, Messages.getString("Corrupt_Transaction_@__6") + pos + " " + var28);
                     }
                  }
               } catch (IOException var29) {
                  pos = this.tlog.getWritepos();
                  this.waitChange();
               } catch (Exception var30) {
                  pos = this.tlog.getWritepos();
                  BackendHandler.setReplicaInvalid();
                  Logger.getInstance().printStackTrace(var30);
                  Logger.getInstance().log(0, this, Messages.getString("Corrupt_Transaction_@__6") + pos + " " + var30);
                  this.waitChange();
               }
            }
         }
      } finally {
         this.moreTransactions = false;
         this.noteChange();
      }
   }

   public void shutdown() {
      this.waitTransactionsCompleted();
      this.close();
   }

   public synchronized void waitTransactionsCompleted() {
      while(this.moreTransactions) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

   }

   private synchronized void waitChange() {
      this.moreTransactions = false;
      this.notifyAll();

      try {
         this.wait();
      } catch (InterruptedException var2) {
         Logger.getInstance().printStackTrace(var2);
      }

   }

   private synchronized void waitwrite() {
      try {
         this.wait(100L);
      } catch (InterruptedException var2) {
         Logger.getInstance().printStackTrace(var2);
      }

   }

   private void writePos(long pos) {
      try {
         this.posfile.seek(0L);
         this.posfile.writeLong(pos);
      } catch (IOException var4) {
         Logger.getInstance().printStackTrace(var4);
      }

   }
}
