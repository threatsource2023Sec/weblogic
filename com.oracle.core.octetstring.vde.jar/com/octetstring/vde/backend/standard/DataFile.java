package com.octetstring.vde.backend.standard;

import com.octetstring.nls.Messages;
import com.octetstring.vde.Entry;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.replication.BackendChangeLog;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.Logger;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.TreeMap;
import java.util.Vector;

public class DataFile {
   private volatile int highEid = 0;
   private volatile int highDel = 0;
   private RAFPool dataFP = null;
   private RAFPool deleteFP = null;
   private RAFPool indexFP = null;
   private int[] lenmap = null;
   private long[] locmap = null;
   private String fileBase = null;
   private int mapsize = 1024;
   TreeMap freeLoc = new TreeMap();
   TreeMap freeSize = new TreeMap();
   private long[] freeMap = null;
   private volatile boolean wlock = false;

   public DataFile(String file) {
      this.fileBase = new String(file);
      File dataFileName = new File(file + ".data");
      File indexFileName = new File(file + ".index");
      File delFileName = new File(file + ".delete");
      this.lenmap = new int[this.mapsize];
      this.locmap = new long[this.mapsize];
      this.freeMap = new long[this.mapsize];

      try {
         this.dataFP = new RAFPool(dataFileName);
         this.indexFP = new RAFPool(indexFileName);
         this.deleteFP = new RAFPool(delFileName);
         this.initFreemap();
         RandomAccessFile indexFile = null;
         if (!indexFileName.exists()) {
            indexFile = (RandomAccessFile)this.indexFP.checkOut();
            indexFile.seek(0L);
            indexFile.writeLong(0L);
            indexFile.writeInt(0);
            this.indexFP.checkIn(indexFile);
         } else {
            indexFile = (RandomAccessFile)this.indexFP.checkOut();

            for(this.highEid = (int)(indexFile.length() / 12L) - 1; this.mapsize <= this.highEid; this.mapsize *= 2) {
            }

            long[] newLocmap = new long[this.mapsize];
            int[] newLenmap = new int[this.mapsize];
            System.arraycopy(this.locmap, 0, newLocmap, 0, this.locmap.length);
            System.arraycopy(this.lenmap, 0, newLenmap, 0, this.lenmap.length);
            this.lenmap = newLenmap;
            this.locmap = newLocmap;
            long[] newFreeMap = new long[this.mapsize];
            System.arraycopy(this.freeMap, 0, newFreeMap, 0, this.freeMap.length);
            this.freeMap = newFreeMap;
            indexFile.seek(12L);

            for(int i = 1; i <= this.highEid; ++i) {
               this.locmap[i] = indexFile.readLong();
               this.lenmap[i] = indexFile.readInt();
            }

            this.indexFP.checkIn(indexFile);
         }
      } catch (IOException var10) {
         BackendHandler.setReplicaInvalid();
         Logger.getInstance().log(0, this, Messages.getString("IO_Problem_initializing_DataFile___4") + var10.getMessage());
      } catch (Exception var11) {
         BackendHandler.setReplicaInvalid();
         Logger.getInstance().log(0, this, Messages.getString("Problem_initializing_DataFile___5") + var11.getMessage());
      }

   }

   private void initFreemap() {
      try {
         RandomAccessFile deleteFile = (RandomAccessFile)this.deleteFP.checkOut();

         for(this.highDel = (int)(deleteFile.length() / 12L); this.mapsize <= this.highDel; this.mapsize *= 2) {
         }

         long[] newLocmap = new long[this.mapsize];
         int[] newLenmap = new int[this.mapsize];
         System.arraycopy(this.locmap, 0, newLocmap, 0, this.locmap.length);
         System.arraycopy(this.lenmap, 0, newLenmap, 0, this.lenmap.length);
         this.lenmap = newLenmap;
         this.locmap = newLocmap;
         long[] newFreeMap = new long[this.mapsize];
         System.arraycopy(this.freeMap, 0, newFreeMap, 0, this.freeMap.length);
         this.freeMap = newFreeMap;

         for(int i = 0; i < this.highDel; ++i) {
            this.freeMap[i] = deleteFile.readLong();
            int size = deleteFile.readInt();
            Integer intsize = new Integer(size);
            Long longloc = new Long(this.freeMap[i]);
            this.freeLoc.put(longloc, intsize);
            Vector av = (Vector)this.freeSize.get(intsize);
            if (av == null) {
               av = new Vector();
               this.freeSize.put(intsize, av);
            }

            av.addElement(new Integer(i));
         }

         if (deleteFile.length() % 12L != 0L) {
            Logger.getInstance().log(0, this, Messages.getString("Repairing_truncated_file"));
            deleteFile.writeLong(-1L);
            deleteFile.writeInt(0);
         }

         this.deleteFP.checkIn(deleteFile);
      } catch (IOException var10) {
         Logger.getInstance().log(0, this, Messages.getString("Unable_to_read_from_free_space_map_file._6"));
      } catch (Exception var11) {
         Logger.getInstance().log(0, this, var11.getMessage());
      }

   }

   private void reinitFreeMap() {
      this.freeLoc = new TreeMap();
      this.freeSize = new TreeMap();
      long[] newFreeMap = new long[this.mapsize];
      this.freeMap = newFreeMap;
   }

   public int getHighEid() {
      return this.highEid;
   }

   private long allocFree(int size) {
      Vector av = (Vector)this.freeSize.get(new Integer(size));
      long where = -1L;
      int wsize = true;
      int witem = true;
      Integer lastKey;
      int wsize;
      int witem;
      if (av == null) {
         lastKey = null;

         try {
            lastKey = (Integer)this.freeSize.lastKey();
         } catch (Exception var13) {
         }

         if (lastKey == null || lastKey < size) {
            return -1L;
         }

         Vector vals = (Vector)this.freeSize.get(lastKey);
         Integer val = (Integer)vals.firstElement();
         if (vals.size() == 1) {
            this.freeSize.remove(lastKey);
         } else {
            vals.removeElementAt(0);
         }

         witem = val;
         where = this.freeMap[witem];
         wsize = lastKey;
      } else {
         lastKey = (Integer)av.firstElement();
         if (av.size() == 1) {
            this.freeSize.remove(new Integer(size));
         } else {
            av.removeElementAt(0);
         }

         witem = lastKey;
         where = this.freeMap[witem];
         wsize = size;
      }

      long nloc = where + (long)wsize - (long)size;
      Long wherelong = new Long(where);
      if (wsize == size) {
         this.freeMap[witem] = -1L;
         this.freeLoc.remove(wherelong);
      } else {
         this.freeLoc.put(wherelong, new Integer(wsize - size));
      }

      try {
         RandomAccessFile deleteFile = (RandomAccessFile)this.deleteFP.checkOut();
         deleteFile.seek((long)witem * 12L);
         deleteFile.writeLong(this.freeMap[witem]);
         deleteFile.writeInt(wsize - size);
         this.deleteFP.checkIn(deleteFile);
      } catch (IOException var11) {
         Logger.getInstance().log(0, this, Messages.getString("Error_updating_free_space_file._7"));
      } catch (Exception var12) {
         Logger.getInstance().log(0, this, Messages.getString("Error___8") + var12.getMessage());
      }

      return nloc;
   }

   public synchronized void lockWrites() {
      while(this.wlock) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

      this.wlock = true;
   }

   public synchronized void unlockWrites() {
      this.wlock = false;
      this.notify();
   }

   public void addEntry(Entry entry) {
      this.lockWrites();
      byte[] entryBytes = entry.getAsByteArray();
      int entryid = entry.getID();
      if (entryid >= this.mapsize) {
         while(true) {
            if (this.mapsize > entryid) {
               long[] newLocmap = new long[this.mapsize];
               int[] newLenmap = new int[this.mapsize];
               System.arraycopy(this.locmap, 0, newLocmap, 0, this.locmap.length);
               System.arraycopy(this.lenmap, 0, newLenmap, 0, this.lenmap.length);
               this.lenmap = newLenmap;
               this.locmap = newLocmap;
               long[] newFreeMap = new long[this.mapsize];
               System.arraycopy(this.freeMap, 0, newFreeMap, 0, this.freeMap.length);
               this.freeMap = newFreeMap;
               break;
            }

            this.mapsize *= 2;
         }
      }

      long writeat = this.allocFree(entryBytes.length);
      Logger.getInstance().log(9, this, Messages.getString("Allocated_space_at___9") + writeat);
      RandomAccessFile dataFile = null;
      RandomAccessFile indexFile = null;

      try {
         dataFile = (RandomAccessFile)this.dataFP.checkOut();
         indexFile = (RandomAccessFile)this.indexFP.checkOut();
         if (writeat == -1L) {
            writeat = dataFile.length();
         }

         if (writeat < 0L) {
            writeat = dataFile.length();
         }

         dataFile.seek(writeat);
         indexFile.seek((long)(entryid * 12));
         indexFile.writeLong(writeat);
         indexFile.writeInt(entryBytes.length);
         this.locmap[entryid] = writeat;
         this.lenmap[entryid] = entryBytes.length;
         dataFile.write(entryBytes);
         this.dataFP.checkIn(dataFile);
         this.indexFP.checkIn(indexFile);
         this.highEid = entryid;
      } catch (IOException var9) {
         BackendHandler.setReplicaInvalid();
         Logger.getInstance().log(0, this, Messages.getString("IO_Error_writing_EID#_10") + entryid + Messages.getString("_to_DataFile___11") + var9.getMessage());
         if (dataFile != null) {
            this.dataFP.checkIn(dataFile);
         }

         if (indexFile != null) {
            this.indexFP.checkIn(indexFile);
         }
      } catch (Exception var10) {
         BackendHandler.setReplicaInvalid();
         Logger.getInstance().log(0, this, Messages.getString("Error_writing_EID#_12") + entryid + Messages.getString("to_DataFile___13") + var10.getMessage());
         if (dataFile != null) {
            this.dataFP.checkIn(dataFile);
         }

         if (indexFile != null) {
            this.indexFP.checkIn(indexFile);
         }
      }

      this.unlockWrites();
   }

   public void deleteEntry(int entryid) {
      RandomAccessFile indexFile = null;
      RandomAccessFile deleteFile = null;
      this.lockWrites();

      try {
         indexFile = (RandomAccessFile)this.indexFP.checkOut();
         deleteFile = (RandomAccessFile)this.deleteFP.checkOut();
         indexFile.seek((long)(entryid * 12));
         long oldLoc = indexFile.readLong();
         int oldLen = indexFile.readInt();
         indexFile.seek((long)(entryid * 12));
         indexFile.writeLong(-1L);
         indexFile.writeInt(0);
         ++this.highDel;
         if (this.highDel >= this.mapsize) {
            while(this.mapsize <= this.highDel) {
               this.mapsize *= 2;
            }

            long[] newLocmap = new long[this.mapsize];
            int[] newLenmap = new int[this.mapsize];
            System.arraycopy(this.locmap, 0, newLocmap, 0, this.locmap.length);
            System.arraycopy(this.lenmap, 0, newLenmap, 0, this.lenmap.length);
            this.lenmap = newLenmap;
            this.locmap = newLocmap;
            long[] newFreeMap = new long[this.mapsize];
            System.arraycopy(this.freeMap, 0, newFreeMap, 0, this.freeMap.length);
            this.freeMap = newFreeMap;
         }

         deleteFile.seek(deleteFile.length());
         int delpos = (int)deleteFile.length() / 12;
         deleteFile.writeLong(oldLoc);
         deleteFile.writeInt(oldLen);
         this.freeMap[delpos] = oldLoc;
         Integer intsize = new Integer(oldLen);
         Long longloc = new Long(oldLoc);
         this.freeLoc.put(longloc, intsize);
         Vector av = (Vector)this.freeSize.get(intsize);
         if (av == null) {
            av = new Vector();
            this.freeSize.put(intsize, av);
         }

         av.addElement(new Integer(delpos));
         this.indexFP.checkIn(indexFile);
         this.deleteFP.checkIn(deleteFile);
         this.locmap[entryid] = -1L;
         this.lenmap[entryid] = 0;
      } catch (IOException var11) {
         BackendHandler.setReplicaInvalid();
         Logger.getInstance().log(0, this, Messages.getString("Error_deleting_EID#_14") + entryid + Messages.getString("from_DataFile___15") + var11.getMessage());
         Logger.getInstance().printStackTrace(var11);
      } catch (Exception var12) {
         BackendHandler.setReplicaInvalid();
         Logger.getInstance().log(0, this, Messages.getString("Error_deleting_EID#_16") + entryid + Messages.getString("from_DataFile___17") + var12.getMessage());
         Logger.getInstance().printStackTrace(var12);
      }

      this.unlockWrites();
   }

   public Entry getEntry(Integer id) {
      RandomAccessFile indexFile = null;
      RandomAccessFile dataFile = null;
      int myid = id;
      if (myid >= 0 && myid <= this.locmap.length) {
         try {
            dataFile = (RandomAccessFile)this.dataFP.checkOut();
            long dfSize = null == dataFile ? 0L : dataFile.length();
            long loc = this.locmap[myid];
            int len = this.lenmap[myid];
            if (loc == -1L) {
               this.dataFP.checkIn(dataFile);
               return null;
            }

            if (loc == 0L) {
               indexFile = (RandomAccessFile)this.indexFP.checkOut();
               indexFile.seek(12L * (long)myid);
               this.locmap[myid] = indexFile.readLong();
               this.lenmap[myid] = indexFile.readInt();
               this.indexFP.checkIn(indexFile);
               loc = this.locmap[myid];
               len = this.lenmap[myid];
               if (len == 0) {
                  Logger.getInstance().log(5, this, Messages.getString("Error_reading_Entry_#_18") + myid);
                  return null;
               }
            }

            try {
               BackendChangeLog.checkChangeFileEntryLength(dfSize, loc, len, "data_file_entry#:" + myid);
            } catch (IndexOutOfBoundsException var12) {
               BackendHandler.setReplicaInvalid();
               throw var12;
            }

            dataFile.seek(loc);
            byte[] entryBytes = new byte[len];
            dataFile.read(entryBytes, 0, len);
            Entry current = new Entry(entryBytes);
            byte[] entryBytes = null;
            this.dataFP.checkIn(dataFile);
            return current;
         } catch (IOException var13) {
            BackendHandler.setReplicaInvalid();
            Logger.getInstance().log(7, this, Messages.getString("Error_reading_Entry_#_18") + id + ": " + var13.getMessage());
            if (Logger.getInstance().isLogable(7)) {
               Logger.getInstance().printStackTrace(var13);
            }
         } catch (Exception var14) {
            Logger.getInstance().log(7, this, Messages.getString("Error_parsing_Entry_#_20") + id + ": " + var14.getMessage());
            if (Logger.getInstance().isLogable(7)) {
               Logger.getInstance().printStackTrace(var14);
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public void modifyEntry(Entry entry) {
      this.lockWrites();
      byte[] entryBytes = entry.getAsByteArray();
      int entryid = entry.getID();
      RandomAccessFile indexFile = null;
      RandomAccessFile deleteFile = null;
      RandomAccessFile dataFile = null;

      try {
         dataFile = (RandomAccessFile)this.dataFP.checkOut();
         long oldLoc = this.locmap[entryid];
         int oldLen = this.lenmap[entryid];
         if (oldLen == entryBytes.length) {
            dataFile.seek(oldLoc);
            dataFile.write(entryBytes);
         } else {
            indexFile = (RandomAccessFile)this.indexFP.checkOut();
            deleteFile = (RandomAccessFile)this.deleteFP.checkOut();
            long writeat = this.allocFree(entryBytes.length);
            if (writeat == -1L) {
               writeat = dataFile.length();
            }

            if (writeat < 0L) {
               writeat = dataFile.length();
            }

            dataFile.seek(writeat);
            indexFile.seek((long)(entryid * 12));
            indexFile.writeLong(writeat);
            indexFile.writeInt(entryBytes.length);
            ++this.highDel;
            if (this.highDel >= this.mapsize) {
               while(this.mapsize <= this.highDel) {
                  this.mapsize *= 2;
               }

               long[] newLocmap = new long[this.mapsize];
               int[] newLenmap = new int[this.mapsize];
               System.arraycopy(this.locmap, 0, newLocmap, 0, this.locmap.length);
               System.arraycopy(this.lenmap, 0, newLenmap, 0, this.lenmap.length);
               this.lenmap = newLenmap;
               this.locmap = newLocmap;
               long[] newFreeMap = new long[this.mapsize];
               System.arraycopy(this.freeMap, 0, newFreeMap, 0, this.freeMap.length);
               this.freeMap = newFreeMap;
            }

            deleteFile.seek(deleteFile.length());
            int delpos = (int)deleteFile.length() / 12;
            deleteFile.writeLong(oldLoc);
            deleteFile.writeInt(oldLen);
            this.freeMap[delpos] = oldLoc;
            Integer intsize = new Integer(oldLen);
            Long longloc = new Long(oldLoc);
            this.freeLoc.put(longloc, intsize);
            Vector av = (Vector)this.freeSize.get(intsize);
            if (av == null) {
               av = new Vector();
               this.freeSize.put(intsize, av);
            }

            av.addElement(new Integer(delpos));
            dataFile.write(entryBytes);
            this.indexFP.checkIn(indexFile);
            this.deleteFP.checkIn(deleteFile);
            this.locmap[entryid] = writeat;
            this.lenmap[entryid] = entryBytes.length;
         }

         this.dataFP.checkIn(dataFile);
      } catch (IOException var16) {
         BackendHandler.setReplicaInvalid();
         Logger.getInstance().log(0, this, Messages.getString("Error_modifying_EID#_22") + entryid + Messages.getString("in_DataFile___23") + var16.getMessage());
      } catch (Exception var17) {
         BackendHandler.setReplicaInvalid();
         Logger.getInstance().log(0, this, Messages.getString("Error_modifying_EID#_24") + entryid + Messages.getString("in_DataFile___25") + var17.getMessage());
      }

      this.unlockWrites();
   }

   public void renameEntry(int id, DirectoryString newname) {
      this.lockWrites();
      Entry entry = this.getEntry(new Integer(id));

      try {
         entry.setName(newname, true);
      } catch (InvalidDNException var18) {
      }

      byte[] entryBytes = entry.getAsByteArray();
      int entryid = entry.getID();
      RandomAccessFile indexFile = null;
      RandomAccessFile deleteFile = null;
      RandomAccessFile dataFile = null;

      try {
         dataFile = (RandomAccessFile)this.dataFP.checkOut();
         long oldLoc = this.locmap[entryid];
         int oldLen = this.lenmap[entryid];
         if (oldLen == entryBytes.length) {
            dataFile.seek(oldLoc);
            dataFile.write(entryBytes);
         } else {
            indexFile = (RandomAccessFile)this.indexFP.checkOut();
            deleteFile = (RandomAccessFile)this.deleteFP.checkOut();
            long writeat = this.allocFree(entryBytes.length);
            if (writeat == -1L) {
               writeat = dataFile.length();
            }

            if (writeat < 0L) {
               writeat = dataFile.length();
            }

            dataFile.seek(writeat);
            indexFile.seek((long)(entryid * 12));
            indexFile.writeLong(writeat);
            indexFile.writeInt(entryBytes.length);
            ++this.highDel;
            if (this.highDel >= this.mapsize) {
               while(this.mapsize <= this.highDel) {
                  this.mapsize *= 2;
               }

               long[] newLocmap = new long[this.mapsize];
               int[] newLenmap = new int[this.mapsize];
               System.arraycopy(this.locmap, 0, newLocmap, 0, this.locmap.length);
               System.arraycopy(this.lenmap, 0, newLenmap, 0, this.lenmap.length);
               this.lenmap = newLenmap;
               this.locmap = newLocmap;
               long[] newFreeMap = new long[this.mapsize];
               System.arraycopy(this.freeMap, 0, newFreeMap, 0, this.freeMap.length);
               this.freeMap = newFreeMap;
            }

            deleteFile.seek(deleteFile.length());
            int delpos = (int)deleteFile.length() / 12;
            deleteFile.writeLong(oldLoc);
            deleteFile.writeInt(oldLen);
            this.freeMap[delpos] = oldLoc;
            Integer intsize = new Integer(oldLen);
            Long longloc = new Long(oldLoc);
            this.freeLoc.put(longloc, intsize);
            Vector av = (Vector)this.freeSize.get(intsize);
            if (av == null) {
               av = new Vector();
               this.freeSize.put(intsize, av);
            }

            av.addElement(new Integer(delpos));
            dataFile.write(entryBytes);
            this.indexFP.checkIn(indexFile);
            this.deleteFP.checkIn(deleteFile);
            this.locmap[entryid] = writeat;
            this.lenmap[entryid] = entryBytes.length;
         }

         this.dataFP.checkIn(dataFile);
      } catch (IOException var19) {
         BackendHandler.setReplicaInvalid();
         Logger.getInstance().log(0, this, Messages.getString("Error_renaming_EID#_26") + entryid + Messages.getString("in_DataFile___27") + var19.getMessage());
      } catch (Exception var20) {
         BackendHandler.setReplicaInvalid();
         Logger.getInstance().log(0, this, Messages.getString("Error_renaming_EID#_28") + entryid + Messages.getString("in_DataFile___29") + var20.getMessage());
      }

      this.unlockWrites();
   }

   public void truncate() {
      if (this.doTruncation()) {
         Logger.getInstance().log(7, this, "Truncating data file");
         RandomAccessFile indexFile = null;
         RandomAccessFile dataFile = null;
         RandomAccessFile deleteFile = null;

         try {
            this.deleteFile(this.fileBase + ".index-n");
            RandomAccessFile nif = new RandomAccessFile(this.fileBase + ".index-n", "rw");
            this.deleteFile(this.fileBase + ".data-n");
            RandomAccessFile ndf = new RandomAccessFile(this.fileBase + ".data-n", "rw");
            this.deleteFile(this.fileBase + ".delete-n");
            RandomAccessFile ndelf = new RandomAccessFile(this.fileBase + ".delete-n", "rw");
            indexFile = (RandomAccessFile)this.indexFP.checkOut();
            dataFile = (RandomAccessFile)this.dataFP.checkOut();
            deleteFile = (RandomAccessFile)this.deleteFP.checkOut();
            if (!this.backupFile(indexFile, ".index-backup")) {
               Logger.getInstance().log(5, this, Messages.getString("Error_backing_up_index_file"));
               this.indexFP.checkIn(indexFile);
               this.dataFP.checkIn(dataFile);
               this.deleteFP.checkIn(deleteFile);
               return;
            }

            if (!this.backupFile(dataFile, ".data-backup")) {
               Logger.getInstance().log(5, this, Messages.getString("Error_backing_up_data_file"));
               this.indexFP.checkIn(indexFile);
               this.dataFP.checkIn(dataFile);
               this.deleteFP.checkIn(deleteFile);
               return;
            }

            if (!this.backupFile(deleteFile, ".delete-backup")) {
               Logger.getInstance().log(5, this, Messages.getString("Error_backing_up_delete_file"));
               this.indexFP.checkIn(indexFile);
               this.dataFP.checkIn(dataFile);
               this.deleteFP.checkIn(deleteFile);
               return;
            }

            Logger.getInstance().log(7, this, "Reinitializing FreeMap during data file truncation");
            this.reinitFreeMap();
            nif.seek(0L);
            indexFile.seek(0L);
            int lowIndex = indexFile.readInt();
            nif.writeInt(lowIndex);
            long version = indexFile.readLong();
            nif.writeLong(version);
            long entryCnt = indexFile.length() / 12L;
            long dfSize = null == dataFile ? 0L : dataFile.length();

            for(long i = 1L; i < entryCnt; ++i) {
               indexFile.seek(i * 12L);
               nif.seek(i * 12L);
               long dataLoc = indexFile.readLong();
               int dataSize = indexFile.readInt();

               try {
                  BackendChangeLog.checkChangeFileEntryLength(dfSize, dataLoc, dataSize, "data_entry#:" + i);
               } catch (IndexOutOfBoundsException var24) {
                  BackendHandler.setReplicaInvalid();
                  throw var24;
               }

               if (dataLoc != -1L) {
                  long newData = this.allocFree(dataSize);
                  if (newData == -1L) {
                     newData = ndf.length();
                  }

                  if (newData < 0L) {
                     newData = ndf.length();
                  }

                  ndf.seek(newData);
                  dataFile.seek(dataLoc);
                  byte[] data = new byte[dataSize];
                  int len = dataFile.read(data);
                  if (len != dataSize) {
                     Logger.getInstance().log(0, this, Messages.getString("Error_cannot_read_data_file"));
                     BackendHandler.setReplicaInvalid();
                     this.indexFP.checkIn(indexFile);
                     this.dataFP.checkIn(dataFile);
                     this.deleteFP.checkIn(deleteFile);
                     return;
                  }

                  ndf.write(data, 0, len);
                  Entry ent = new Entry(data);
                  nif.writeLong(newData);
                  nif.writeInt(len);
                  this.locmap[ent.getID()] = newData;
                  this.lenmap[ent.getID()] = data.length;
               } else {
                  nif.writeLong(-1L);
                  nif.writeInt(0);
               }
            }

            ndelf.seek(0L);
            ndelf.writeLong(-1L);
            ndelf.writeInt(0);
            Logger.getInstance().log(5, this, Messages.getString("Writing_new_ldap_files"));
            this.moveFile(indexFile, nif);
            this.moveFile(dataFile, ndf);
            this.moveFile(deleteFile, ndelf);
            nif.close();
            this.deleteFile(this.fileBase + ".index-n");
            ndf.close();
            this.deleteFile(this.fileBase + ".data-n");
            ndelf.close();
            this.deleteFile(this.fileBase + ".delete-n");
            Logger.getInstance().log(7, this, "Removing temporary truncation files");
            this.deleteFile(this.fileBase + ".index-backup");
            this.deleteFile(this.fileBase + ".data-backup");
            this.deleteFile(this.fileBase + ".delete-backup");
            Logger.getInstance().log(7, this, "Truncation completed");
         } catch (IOException var25) {
            Logger.getInstance().log(0, this, Messages.getString("Error_IO_Exception: " + var25.getMessage()));
         } catch (Exception var26) {
            Logger.getInstance().log(0, this, Messages.getString("Error_Exception: " + var26.getMessage()));
         }

         this.dataFP.checkIn(dataFile);
         this.indexFP.checkIn(indexFile);
         this.deleteFP.checkIn(deleteFile);
      }
   }

   public boolean doTruncation() {
      String value = System.getProperty("weblogic.security.ldap.maxSize");
      if (value == null) {
         return false;
      } else {
         long maxSize = Long.getLong(value, 0L);
         File f = new File(this.fileBase + ".data");
         return f.exists() && maxSize <= f.length();
      }
   }

   private void deleteFile(String name) {
      File f = new File(name);
      if (f.exists()) {
         f.delete();
      }

   }

   private boolean backupFile(RandomAccessFile src, String backup) {
      try {
         File dst = new File(this.fileBase + backup);
         if (dst.exists()) {
            dst.delete();
         }

         if (dst.createNewFile()) {
            long len = src.length();
            byte[] b = new byte[Integer.getInteger("ldapBackup.block.size", 500) * 1024];
            RandomAccessFile dstraf = new RandomAccessFile(dst, "rw");
            src.seek(0L);

            while(src.read(b) > 0) {
               dstraf.write(b);
            }

            dstraf.setLength(len);
            dstraf.close();
            return true;
         }
      } catch (IOException var8) {
         Logger.getInstance().log(5, this, Messages.getString("IO_exception_on_truncation" + var8.getMessage()));
      }

      return false;
   }

   private void moveFile(RandomAccessFile f, RandomAccessFile newF) {
      try {
         long newLen = newF.length();
         byte[] newB = new byte[Integer.getInteger("ldapBackup.block.size", 500) * 1024];
         newF.seek(0L);
         f.seek(0L);

         while(newF.read(newB) > 0) {
            f.write(newB);
         }

         f.setLength(newLen);
      } catch (IOException var6) {
         Logger.getInstance().log(7, this, Messages.getString("IO_exception_on_truncation" + var6.getMessage()));
      }

   }

   public void cleanupPools() {
      if (this.dataFP != null) {
         this.dataFP.cleanUp();
      }

      if (this.deleteFP != null) {
         this.deleteFP.cleanUp();
      }

      if (this.indexFP != null) {
         this.indexFP.cleanUp();
      }

   }

   public void shutdown() {
      if (this.dataFP != null) {
         this.dataFP.expireAll();
      }

      if (this.deleteFP != null) {
         this.deleteFP.expireAll();
      }

      if (this.indexFP != null) {
         this.indexFP.expireAll();
      }

   }
}
