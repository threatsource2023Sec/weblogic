package weblogic.store.io.jdbc;

import weblogic.utils.collections.NumericArraySet;

final class HandleTracking {
   private static int NO_HANDLE = Integer.MIN_VALUE;
   private IntArray[] rowid;
   private int[] freeHandle;
   private NumericArraySet allocatedRowIds;
   private int[] nextRowId;
   private int[] minRowId;
   private int[] maxRowId;
   private int totalNumKeys;
   private int numLockPartitions;
   private int lockPartition;
   private int lockPartitionSize;
   private int cursorTypeCode;
   private int cursorHandle;

   HandleTracking(int _numLockPartitions) {
      this(_numLockPartitions, 2147483646);
   }

   HandleTracking(int _numLockPartitions, int _totalNumKeys) {
      this.rowid = new IntArray[0];
      this.freeHandle = new int[0];
      this.numLockPartitions = _numLockPartitions;
      this.lockPartitionSize = _totalNumKeys / this.numLockPartitions;
      this.totalNumKeys = this.lockPartitionSize * this.numLockPartitions;
      this.allocatedRowIds = new NumericArraySet();
      this.nextRowId = new int[this.numLockPartitions];
      this.minRowId = new int[this.numLockPartitions];
      this.maxRowId = new int[this.numLockPartitions];
      int prevRowMax = 0;

      for(int i = 0; i < this.numLockPartitions; ++i) {
         this.nextRowId[i] = prevRowMax + 1;
         this.minRowId[i] = prevRowMax + 1;
         int max = prevRowMax + this.lockPartitionSize;
         this.maxRowId[i] = max - 1;
         prevRowMax = max;
         if (i != (this.minRowId[i] - 1) / this.lockPartitionSize) {
            throw new AssertionError();
         }

         if (i != (this.nextRowId[i] - 1) / this.lockPartitionSize) {
            throw new AssertionError();
         }

         if (i != (this.maxRowId[i] - 1) / this.lockPartitionSize) {
            throw new AssertionError();
         }

         if (i != (max - 1) / this.lockPartitionSize) {
            throw new AssertionError();
         }
      }

   }

   int getTotalNumKeys() {
      return this.totalNumKeys;
   }

   int getNumLockPartitions() {
      return this.numLockPartitions;
   }

   int getLockPartition() {
      return this.lockPartition;
   }

   int getLockPartitionSize() {
      return this.lockPartitionSize;
   }

   private void setupTypeCode(int typeCode) {
      if (typeCode < 0) {
         throw new AssertionError("Invalid type");
      } else {
         if (typeCode >= this.rowid.length) {
            IntArray[] tmp = new IntArray[typeCode + 1];
            System.arraycopy(this.rowid, 0, tmp, 0, this.rowid.length);
            this.rowid = tmp;
            int[] tmp2 = new int[typeCode + 1];
            System.arraycopy(this.freeHandle, 0, tmp2, 0, this.freeHandle.length);
            this.freeHandle = tmp2;
         }

         if (this.rowid[typeCode] == null) {
            this.rowid[typeCode] = new IntArray();
            this.rowid[typeCode].set(0, NO_HANDLE);
            this.freeHandle[typeCode] = NO_HANDLE;
         }
      }
   }

   int[] getLockPartitionRowIds() {
      int[] ret = new int[this.numLockPartitions];

      for(int i = 0; i < this.numLockPartitions; ++i) {
         ret[i] = this.maxRowId[i] + 1;
      }

      return ret;
   }

   synchronized int allocHandle(int typeCode) {
      this.setupTypeCode(typeCode);
      int h;
      if (this.rowid[typeCode].get(0) == NO_HANDLE) {
         h = 0;
      } else {
         h = this.freeHandle[typeCode];
         if (h == NO_HANDLE) {
            h = this.rowid[typeCode].size();
         } else {
            h = -h;
            this.freeHandle[typeCode] = this.rowid[typeCode].get(h);
         }
      }

      this.rowid[typeCode].set(h, 0);
      return h;
   }

   synchronized void ensureHandleAllocated(int typeCode, int handle) {
      if (handle < 0) {
         throw new AssertionError("Illegal handle value");
      } else {
         this.setupTypeCode(typeCode);
         this.rowid[typeCode].set(handle, 0);
      }
   }

   synchronized void freeHandle(int typeCode, int handle) {
      if (typeCode >= 0 && typeCode < this.rowid.length) {
         if (handle < 0) {
            throw new AssertionError("Freeing illegal handle value");
         } else if (handle >= this.rowid[typeCode].size()) {
            throw new AssertionError("Freeing unallocated handle");
         } else if (this.rowid[typeCode].get(handle) < 0) {
            throw new AssertionError("Freeing freed handle");
         } else {
            if (handle == 0) {
               this.rowid[typeCode].set(handle, NO_HANDLE);
            } else {
               this.rowid[typeCode].set(handle, this.freeHandle[typeCode]);
               this.freeHandle[typeCode] = -handle;
            }

         }
      } else {
         throw new AssertionError("Invalid type");
      }
   }

   synchronized int allocRowId() {
      boolean alreadyWentBack = false;
      if (this.allocatedRowIds.size() >= this.totalNumKeys) {
         throw new AssertionError("All row ids allocated");
      } else {
         int ret;
         do {
            if (this.nextRowId[this.lockPartition] > this.maxRowId[this.lockPartition]) {
               if (alreadyWentBack) {
                  this.lockPartition = (this.lockPartition + 1) % this.numLockPartitions;
                  alreadyWentBack = false;
               } else {
                  this.nextRowId[this.lockPartition] = this.minRowId[this.lockPartition];
                  alreadyWentBack = true;
               }
            }

            ret = this.nextRowId[this.lockPartition]++;
         } while(this.allocatedRowIds.contains(ret));

         this.allocatedRowIds.add(ret);
         return ret;
      }
   }

   synchronized void freeRowId(int id) {
      if (id < 1) {
         throw new AssertionError();
      } else {
         boolean p = this.allocatedRowIds.remove(id);
         if (!p) {
            throw new AssertionError();
         }
      }
   }

   synchronized void incLockPartition() {
      this.lockPartition = (this.lockPartition + 1) % this.numLockPartitions;
   }

   synchronized void setRowId(int typeCode, int handle, int id) {
      if (typeCode >= 0 && typeCode < this.rowid.length) {
         if (handle < 0) {
            throw new AssertionError("Illegal handle for id");
         } else if (handle >= this.rowid[typeCode].size()) {
            throw new AssertionError("Handle unallocated for id");
         } else if (this.rowid[typeCode].get(handle) < 0) {
            throw new AssertionError("Handle freed; can't be used for id");
         } else {
            this.rowid[typeCode].set(handle, id);
         }
      } else {
         throw new AssertionError("Invalid type");
      }
   }

   synchronized int getRowId(int typeCode, int handle) {
      if (typeCode >= 0 && typeCode < this.rowid.length) {
         if (handle < 0) {
            throw new AssertionError("Invalid handle for type");
         } else if (handle >= this.rowid[typeCode].size()) {
            throw new AssertionError("Handle unallocated for type");
         } else {
            int i = this.rowid[typeCode].get(handle);
            if (i < 0) {
               throw new AssertionError("Handle freed");
            } else {
               return i;
            }
         }
      } else {
         throw new AssertionError("Invalid type");
      }
   }

   synchronized boolean isValid(int typeCode, int handle) {
      return typeCode >= 0 && handle >= 0 && typeCode < this.rowid.length && this.rowid[typeCode] != null && handle < this.rowid[typeCode].size() && this.rowid[typeCode].get(handle) >= 0;
   }

   synchronized int getFirstHandle(int typeCode) {
      this.cursorTypeCode = typeCode;
      this.cursorHandle = -1;
      return this.cursorTypeCode < this.rowid.length && this.cursorTypeCode >= 0 && this.rowid[this.cursorTypeCode] != null ? this.getNextHandle() : -1;
   }

   synchronized int getNextHandle() {
      do {
         ++this.cursorHandle;
         if (this.cursorHandle >= this.rowid[this.cursorTypeCode].size()) {
            return -1;
         }
      } while(this.rowid[this.cursorTypeCode].get(this.cursorHandle) < 0);

      return this.cursorHandle;
   }

   synchronized void bootRowId(int rowId) {
      this.allocatedRowIds.add(rowId);
   }

   synchronized void bootHandle(int typeCode, int handle, int row) {
      this.setupTypeCode(typeCode);

      try {
         if (handle < 0) {
            throw new AssertionError("Booting invalid handle " + handle + " for typecode " + typeCode + " and row " + row);
         }

         if (handle != 0 && this.rowid[typeCode].get(handle) < 0) {
            throw new AssertionError("Booting freed handle, rowid[" + typeCode + "].get(" + handle + ")=" + this.rowid[typeCode].get(handle) + " size=" + this.rowid[typeCode].size() + ", row " + row);
         }

         if (this.rowid[typeCode].get(handle) > 0) {
            throw new AssertionError("Booting allocated handle, rowid[" + typeCode + "].get(" + handle + ")=" + this.rowid[typeCode].get(handle) + " size=" + this.rowid[typeCode].size() + ", row " + row);
         }

         if (row <= 0) {
            throw new AssertionError("Booting invalid row, rowid[" + typeCode + "].get(" + handle + ")=" + this.rowid[typeCode].get(handle) + " size=" + this.rowid[typeCode].size() + ", row " + row);
         }
      } catch (AssertionError var5) {
         throw var5;
      }

      this.rowid[typeCode].set(handle, row);
   }

   void bootFreeLists() {
      for(int typeCode = 0; typeCode < this.rowid.length; ++typeCode) {
         if (this.rowid[typeCode] != null) {
            this.freeHandle[typeCode] = NO_HANDLE;

            for(int handle = 1; handle < this.rowid[typeCode].size(); ++handle) {
               if (this.rowid[typeCode].get(handle) == 0) {
                  this.rowid[typeCode].set(handle, this.freeHandle[typeCode]);
                  this.freeHandle[typeCode] = -handle;
               }
            }
         }
      }

   }

   synchronized int size(int typeCode) {
      return this.rowid[typeCode].size();
   }
}
