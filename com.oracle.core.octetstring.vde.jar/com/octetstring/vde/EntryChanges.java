package com.octetstring.vde;

import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.syntax.DirectoryString;

public class EntryChanges {
   public static final int CHANGE_ADD = 1;
   public static final int CHANGE_MODIFY = 2;
   public static final int CHANGE_DELETE = 3;
   public static final int CHANGE_RENAME = 4;
   private static final int byte1 = 255;
   private static final int byte2 = 65280;
   private static final int byte3 = 16711680;
   private static final int byte4 = -16777216;
   private int changeType = 0;
   private Entry fullEntry = null;
   private EntryChange[] entryChanges = null;
   private DirectoryString name = null;
   private DirectoryString newname = null;

   public EntryChanges(byte[] ecBytes) {
      this.readBytes(ecBytes, false);
   }

   public EntryChanges(byte[] ecBytes, boolean oldVersion) {
      this.readBytes(ecBytes, oldVersion);
   }

   public EntryChanges(Entry fullEntry) {
      this.fullEntry = fullEntry;
      this.changeType = 1;
   }

   public EntryChanges(DirectoryString name) {
      this.name = name;
      this.changeType = 3;
   }

   public EntryChanges(DirectoryString name, EntryChange[] entryChanges) {
      this.entryChanges = entryChanges;
      this.name = name;
      this.changeType = 2;
   }

   public EntryChanges(DirectoryString name, DirectoryString newname) {
      this.name = name;
      this.newname = newname;
      this.changeType = 4;
   }

   public int getChangeType() {
      return this.changeType;
   }

   public EntryChange[] getEntryChanges() {
      return this.entryChanges;
   }

   public Entry getFullEntry() {
      return this.fullEntry;
   }

   public DirectoryString getName() {
      return this.name;
   }

   public DirectoryString getNewName() {
      return this.newname;
   }

   private int bytesToInt(byte[] eb, int bc) {
      return (eb[bc] & 255) << 24 | (eb[bc + 1] & 255) << 16 | (eb[bc + 2] & 255) << 8 | eb[bc + 3] & 255;
   }

   public byte[] getAsByteArray() {
      byte[] changeBytes = null;
      byte[] oldnamebytes;
      if (this.changeType == 1) {
         oldnamebytes = this.fullEntry.getAsByteArray();
         changeBytes = new byte[oldnamebytes.length + 1];
         changeBytes[0] = 1;
         System.arraycopy(oldnamebytes, 0, changeBytes, 1, oldnamebytes.length);
      }

      if (this.changeType == 2) {
         oldnamebytes = this.name.getBytes();

         int nl;
         for(nl = 0; nl < this.entryChanges.length; ++nl) {
            byte[] currentChangeBytes = this.entryChanges[nl].getAsByteArray();
            byte[] newArray = new byte[oldnamebytes.length + currentChangeBytes.length + 4];
            System.arraycopy(oldnamebytes, 0, newArray, 0, oldnamebytes.length);
            int cl = currentChangeBytes.length;
            int fbl = oldnamebytes.length;
            newArray[fbl] = (byte)((cl & -16777216) >> 24);
            newArray[fbl + 1] = (byte)((cl & 16711680) >> 16);
            newArray[fbl + 2] = (byte)((cl & '\uff00') >> 8);
            newArray[fbl + 3] = (byte)(cl & 255);
            System.arraycopy(currentChangeBytes, 0, newArray, fbl + 4, cl);
            oldnamebytes = newArray;
         }

         changeBytes = new byte[oldnamebytes.length + 6];
         changeBytes[0] = 2;
         nl = this.name.length();
         changeBytes[1] = (byte)((nl & -16777216) >> 24);
         changeBytes[2] = (byte)((nl & 16711680) >> 16);
         changeBytes[3] = (byte)((nl & '\uff00') >> 8);
         changeBytes[4] = (byte)(nl & 255);
         changeBytes[5] = (byte)this.entryChanges.length;
         System.arraycopy(oldnamebytes, 0, changeBytes, 6, oldnamebytes.length);
      }

      if (this.changeType == 3) {
         oldnamebytes = this.name.getBytes();
         changeBytes = new byte[oldnamebytes.length + 5];
         changeBytes[0] = 3;
         changeBytes[1] = (byte)((oldnamebytes.length & -16777216) >> 24);
         changeBytes[2] = (byte)((oldnamebytes.length & 16711680) >> 16);
         changeBytes[3] = (byte)((oldnamebytes.length & '\uff00') >> 8);
         changeBytes[4] = (byte)(oldnamebytes.length & 255);
         System.arraycopy(oldnamebytes, 0, changeBytes, 5, oldnamebytes.length);
      }

      if (this.changeType == 4) {
         oldnamebytes = this.name.getBytes();
         byte[] newnamebytes = this.newname.getBytes();
         changeBytes = new byte[oldnamebytes.length + newnamebytes.length + 9];
         changeBytes[0] = 4;
         changeBytes[1] = (byte)((oldnamebytes.length & -16777216) >> 24);
         changeBytes[2] = (byte)((oldnamebytes.length & 16711680) >> 16);
         changeBytes[3] = (byte)((oldnamebytes.length & '\uff00') >> 8);
         changeBytes[4] = (byte)(oldnamebytes.length & 255);
         System.arraycopy(oldnamebytes, 0, changeBytes, 5, oldnamebytes.length);
         int nb = oldnamebytes.length + 5;
         changeBytes[nb] = (byte)((newnamebytes.length & -16777216) >> 24);
         changeBytes[nb + 1] = (byte)((newnamebytes.length & 16711680) >> 16);
         changeBytes[nb + 2] = (byte)((newnamebytes.length & '\uff00') >> 8);
         changeBytes[nb + 3] = (byte)(newnamebytes.length & 255);
         System.arraycopy(newnamebytes, 0, changeBytes, nb + 4, newnamebytes.length);
      }

      return changeBytes;
   }

   private void readBytes(byte[] cb, boolean oldVersion) {
      int cbSize = null == cb ? 0 : cb.length;
      int bc = 0;
      this.changeType = cb[bc];
      ++bc;
      if (this.changeType == 1) {
         try {
            Entry.checkEntryFieldLength(cbSize, bc, cb.length - bc, "name");
         } catch (IndexOutOfBoundsException var16) {
            BackendHandler.setReplicaInvalid();
            throw var16;
         }

         byte[] fullent = new byte[cb.length - bc];
         System.arraycopy(cb, bc, fullent, 0, cb.length - 1);
         this.fullEntry = new Entry(fullent);
      }

      byte[] newName;
      int len;
      if (this.changeType == 2) {
         len = this.bytesToInt(cb, bc);
         bc += 4;

         try {
            Entry.checkEntryFieldLength(cbSize, bc, len, "name");
         } catch (IndexOutOfBoundsException var15) {
            BackendHandler.setReplicaInvalid();
            throw var15;
         }

         byte atct = cb[bc];
         ++bc;

         try {
            Entry.checkEntryFieldLength(cbSize, bc, atct, "changeCount");
         } catch (IndexOutOfBoundsException var14) {
            BackendHandler.setReplicaInvalid();
            throw var14;
         }

         newName = new byte[len];
         System.arraycopy(cb, bc, newName, 0, len);
         this.name = new DirectoryString(newName);
         bc += len;
         this.entryChanges = new EntryChange[atct];

         for(int ct = 0; ct < atct; ++ct) {
            len = this.bytesToInt(cb, bc);
            bc += 4;

            try {
               Entry.checkEntryFieldLength(cbSize, bc, len, "EntryChange" + ct);
            } catch (IndexOutOfBoundsException var13) {
               BackendHandler.setReplicaInvalid();
               throw var13;
            }

            byte[] ocb = new byte[len];
            System.arraycopy(cb, bc, ocb, 0, len);
            bc += len;
            this.entryChanges[ct] = new EntryChange(ocb, oldVersion);
         }
      }

      byte[] oldName;
      if (this.changeType == 3) {
         len = this.bytesToInt(cb, bc);
         bc += 4;

         try {
            Entry.checkEntryFieldLength(cbSize, bc, len, "name");
         } catch (IndexOutOfBoundsException var12) {
            BackendHandler.setReplicaInvalid();
            throw var12;
         }

         oldName = new byte[len];
         System.arraycopy(cb, bc, oldName, 0, len);
         bc += len;
         this.name = new DirectoryString(oldName);
      }

      if (this.changeType == 4) {
         len = this.bytesToInt(cb, bc);
         bc += 4;

         try {
            Entry.checkEntryFieldLength(cbSize, bc, len, "oldName");
         } catch (IndexOutOfBoundsException var11) {
            BackendHandler.setReplicaInvalid();
            throw var11;
         }

         oldName = new byte[len];
         System.arraycopy(cb, bc, oldName, 0, len);
         bc += len;
         this.name = new DirectoryString(oldName);
         len = this.bytesToInt(cb, bc);
         bc += 4;

         try {
            Entry.checkEntryFieldLength(cbSize, bc, len, "newName");
         } catch (IndexOutOfBoundsException var10) {
            BackendHandler.setReplicaInvalid();
            throw var10;
         }

         newName = new byte[len];
         System.arraycopy(cb, bc, newName, 0, len);
         int var10000 = bc + len;
         this.newname = new DirectoryString(newName);
      }

   }
}
