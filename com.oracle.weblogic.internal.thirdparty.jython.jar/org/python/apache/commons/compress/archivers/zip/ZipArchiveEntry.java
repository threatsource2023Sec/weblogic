package org.python.apache.commons.compress.archivers.zip;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import org.python.apache.commons.compress.archivers.ArchiveEntry;
import org.python.apache.commons.compress.archivers.EntryStreamOffsets;

public class ZipArchiveEntry extends ZipEntry implements ArchiveEntry, EntryStreamOffsets {
   public static final int PLATFORM_UNIX = 3;
   public static final int PLATFORM_FAT = 0;
   public static final int CRC_UNKNOWN = -1;
   private static final int SHORT_MASK = 65535;
   private static final int SHORT_SHIFT = 16;
   private static final byte[] EMPTY = new byte[0];
   private int method;
   private long size;
   private int internalAttributes;
   private int versionRequired;
   private int versionMadeBy;
   private int platform;
   private int rawFlag;
   private long externalAttributes;
   private int alignment;
   private ZipExtraField[] extraFields;
   private UnparseableExtraFieldData unparseableExtra;
   private String name;
   private byte[] rawName;
   private GeneralPurposeBit gpb;
   private static final ZipExtraField[] noExtraFields = new ZipExtraField[0];
   private long localHeaderOffset;
   private long dataOffset;
   private boolean isStreamContiguous;

   public ZipArchiveEntry(String name) {
      super(name);
      this.method = -1;
      this.size = -1L;
      this.internalAttributes = 0;
      this.platform = 0;
      this.externalAttributes = 0L;
      this.alignment = 0;
      this.unparseableExtra = null;
      this.name = null;
      this.rawName = null;
      this.gpb = new GeneralPurposeBit();
      this.localHeaderOffset = -1L;
      this.dataOffset = -1L;
      this.isStreamContiguous = false;
      this.setName(name);
   }

   public ZipArchiveEntry(ZipEntry entry) throws ZipException {
      super(entry);
      this.method = -1;
      this.size = -1L;
      this.internalAttributes = 0;
      this.platform = 0;
      this.externalAttributes = 0L;
      this.alignment = 0;
      this.unparseableExtra = null;
      this.name = null;
      this.rawName = null;
      this.gpb = new GeneralPurposeBit();
      this.localHeaderOffset = -1L;
      this.dataOffset = -1L;
      this.isStreamContiguous = false;
      this.setName(entry.getName());
      byte[] extra = entry.getExtra();
      if (extra != null) {
         this.setExtraFields(ExtraFieldUtils.parse(extra, true, ExtraFieldUtils.UnparseableExtraField.READ));
      } else {
         this.setExtra();
      }

      this.setMethod(entry.getMethod());
      this.size = entry.getSize();
   }

   public ZipArchiveEntry(ZipArchiveEntry entry) throws ZipException {
      this((ZipEntry)entry);
      this.setInternalAttributes(entry.getInternalAttributes());
      this.setExternalAttributes(entry.getExternalAttributes());
      this.setExtraFields(this.getAllExtraFieldsNoCopy());
      this.setPlatform(entry.getPlatform());
      GeneralPurposeBit other = entry.getGeneralPurposeBit();
      this.setGeneralPurposeBit(other == null ? null : (GeneralPurposeBit)other.clone());
   }

   protected ZipArchiveEntry() {
      this("");
   }

   public ZipArchiveEntry(File inputFile, String entryName) {
      this(inputFile.isDirectory() && !entryName.endsWith("/") ? entryName + "/" : entryName);
      if (inputFile.isFile()) {
         this.setSize(inputFile.length());
      }

      this.setTime(inputFile.lastModified());
   }

   public Object clone() {
      ZipArchiveEntry e = (ZipArchiveEntry)super.clone();
      e.setInternalAttributes(this.getInternalAttributes());
      e.setExternalAttributes(this.getExternalAttributes());
      e.setExtraFields(this.getAllExtraFieldsNoCopy());
      return e;
   }

   public int getMethod() {
      return this.method;
   }

   public void setMethod(int method) {
      if (method < 0) {
         throw new IllegalArgumentException("ZIP compression method can not be negative: " + method);
      } else {
         this.method = method;
      }
   }

   public int getInternalAttributes() {
      return this.internalAttributes;
   }

   public void setInternalAttributes(int value) {
      this.internalAttributes = value;
   }

   public long getExternalAttributes() {
      return this.externalAttributes;
   }

   public void setExternalAttributes(long value) {
      this.externalAttributes = value;
   }

   public void setUnixMode(int mode) {
      this.setExternalAttributes((long)(mode << 16 | ((mode & 128) == 0 ? 1 : 0) | (this.isDirectory() ? 16 : 0)));
      this.platform = 3;
   }

   public int getUnixMode() {
      return this.platform != 3 ? 0 : (int)(this.getExternalAttributes() >> 16 & 65535L);
   }

   public boolean isUnixSymlink() {
      return (this.getUnixMode() & '\uf000') == 40960;
   }

   public int getPlatform() {
      return this.platform;
   }

   protected void setPlatform(int platform) {
      this.platform = platform;
   }

   protected int getAlignment() {
      return this.alignment;
   }

   public void setAlignment(int alignment) {
      if ((alignment & alignment - 1) == 0 && alignment <= 65535) {
         this.alignment = alignment;
      } else {
         throw new IllegalArgumentException("Invalid value for alignment, must be power of two and no bigger than 65535 but is " + alignment);
      }
   }

   public void setExtraFields(ZipExtraField[] fields) {
      List newFields = new ArrayList();
      ZipExtraField[] var3 = fields;
      int var4 = fields.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ZipExtraField field = var3[var5];
         if (field instanceof UnparseableExtraFieldData) {
            this.unparseableExtra = (UnparseableExtraFieldData)field;
         } else {
            newFields.add(field);
         }
      }

      this.extraFields = (ZipExtraField[])newFields.toArray(new ZipExtraField[newFields.size()]);
      this.setExtra();
   }

   public ZipExtraField[] getExtraFields() {
      return this.getParseableExtraFields();
   }

   public ZipExtraField[] getExtraFields(boolean includeUnparseable) {
      return includeUnparseable ? this.getAllExtraFields() : this.getParseableExtraFields();
   }

   private ZipExtraField[] getParseableExtraFieldsNoCopy() {
      return this.extraFields == null ? noExtraFields : this.extraFields;
   }

   private ZipExtraField[] getParseableExtraFields() {
      ZipExtraField[] parseableExtraFields = this.getParseableExtraFieldsNoCopy();
      return parseableExtraFields == this.extraFields ? this.copyOf(parseableExtraFields) : parseableExtraFields;
   }

   private ZipExtraField[] getAllExtraFieldsNoCopy() {
      if (this.extraFields == null) {
         return this.getUnparseableOnly();
      } else {
         return this.unparseableExtra != null ? this.getMergedFields() : this.extraFields;
      }
   }

   private ZipExtraField[] copyOf(ZipExtraField[] src) {
      return this.copyOf(src, src.length);
   }

   private ZipExtraField[] copyOf(ZipExtraField[] src, int length) {
      ZipExtraField[] cpy = new ZipExtraField[length];
      System.arraycopy(src, 0, cpy, 0, Math.min(src.length, length));
      return cpy;
   }

   private ZipExtraField[] getMergedFields() {
      ZipExtraField[] zipExtraFields = this.copyOf(this.extraFields, this.extraFields.length + 1);
      zipExtraFields[this.extraFields.length] = this.unparseableExtra;
      return zipExtraFields;
   }

   private ZipExtraField[] getUnparseableOnly() {
      return this.unparseableExtra == null ? noExtraFields : new ZipExtraField[]{this.unparseableExtra};
   }

   private ZipExtraField[] getAllExtraFields() {
      ZipExtraField[] allExtraFieldsNoCopy = this.getAllExtraFieldsNoCopy();
      return allExtraFieldsNoCopy == this.extraFields ? this.copyOf(allExtraFieldsNoCopy) : allExtraFieldsNoCopy;
   }

   public void addExtraField(ZipExtraField ze) {
      if (ze instanceof UnparseableExtraFieldData) {
         this.unparseableExtra = (UnparseableExtraFieldData)ze;
      } else if (this.extraFields == null) {
         this.extraFields = new ZipExtraField[]{ze};
      } else {
         if (this.getExtraField(ze.getHeaderId()) != null) {
            this.removeExtraField(ze.getHeaderId());
         }

         ZipExtraField[] zipExtraFields = this.copyOf(this.extraFields, this.extraFields.length + 1);
         zipExtraFields[zipExtraFields.length - 1] = ze;
         this.extraFields = zipExtraFields;
      }

      this.setExtra();
   }

   public void addAsFirstExtraField(ZipExtraField ze) {
      if (ze instanceof UnparseableExtraFieldData) {
         this.unparseableExtra = (UnparseableExtraFieldData)ze;
      } else {
         if (this.getExtraField(ze.getHeaderId()) != null) {
            this.removeExtraField(ze.getHeaderId());
         }

         ZipExtraField[] copy = this.extraFields;
         int newLen = this.extraFields != null ? this.extraFields.length + 1 : 1;
         this.extraFields = new ZipExtraField[newLen];
         this.extraFields[0] = ze;
         if (copy != null) {
            System.arraycopy(copy, 0, this.extraFields, 1, this.extraFields.length - 1);
         }
      }

      this.setExtra();
   }

   public void removeExtraField(ZipShort type) {
      if (this.extraFields == null) {
         throw new NoSuchElementException();
      } else {
         List newResult = new ArrayList();
         ZipExtraField[] var3 = this.extraFields;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ZipExtraField extraField = var3[var5];
            if (!type.equals(extraField.getHeaderId())) {
               newResult.add(extraField);
            }
         }

         if (this.extraFields.length == newResult.size()) {
            throw new NoSuchElementException();
         } else {
            this.extraFields = (ZipExtraField[])newResult.toArray(new ZipExtraField[newResult.size()]);
            this.setExtra();
         }
      }
   }

   public void removeUnparseableExtraFieldData() {
      if (this.unparseableExtra == null) {
         throw new NoSuchElementException();
      } else {
         this.unparseableExtra = null;
         this.setExtra();
      }
   }

   public ZipExtraField getExtraField(ZipShort type) {
      if (this.extraFields != null) {
         ZipExtraField[] var2 = this.extraFields;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ZipExtraField extraField = var2[var4];
            if (type.equals(extraField.getHeaderId())) {
               return extraField;
            }
         }
      }

      return null;
   }

   public UnparseableExtraFieldData getUnparseableExtraFieldData() {
      return this.unparseableExtra;
   }

   public void setExtra(byte[] extra) throws RuntimeException {
      try {
         ZipExtraField[] local = ExtraFieldUtils.parse(extra, true, ExtraFieldUtils.UnparseableExtraField.READ);
         this.mergeExtraFields(local, true);
      } catch (ZipException var3) {
         throw new RuntimeException("Error parsing extra fields for entry: " + this.getName() + " - " + var3.getMessage(), var3);
      }
   }

   protected void setExtra() {
      super.setExtra(ExtraFieldUtils.mergeLocalFileDataData(this.getAllExtraFieldsNoCopy()));
   }

   public void setCentralDirectoryExtra(byte[] b) {
      try {
         ZipExtraField[] central = ExtraFieldUtils.parse(b, false, ExtraFieldUtils.UnparseableExtraField.READ);
         this.mergeExtraFields(central, false);
      } catch (ZipException var3) {
         throw new RuntimeException(var3.getMessage(), var3);
      }
   }

   public byte[] getLocalFileDataExtra() {
      byte[] extra = this.getExtra();
      return extra != null ? extra : EMPTY;
   }

   public byte[] getCentralDirectoryExtra() {
      return ExtraFieldUtils.mergeCentralDirectoryData(this.getAllExtraFieldsNoCopy());
   }

   public String getName() {
      return this.name == null ? super.getName() : this.name;
   }

   public boolean isDirectory() {
      return this.getName().endsWith("/");
   }

   protected void setName(String name) {
      if (name != null && this.getPlatform() == 0 && !name.contains("/")) {
         name = name.replace('\\', '/');
      }

      this.name = name;
   }

   public long getSize() {
      return this.size;
   }

   public void setSize(long size) {
      if (size < 0L) {
         throw new IllegalArgumentException("invalid entry size");
      } else {
         this.size = size;
      }
   }

   protected void setName(String name, byte[] rawName) {
      this.setName(name);
      this.rawName = rawName;
   }

   public byte[] getRawName() {
      if (this.rawName != null) {
         byte[] b = new byte[this.rawName.length];
         System.arraycopy(this.rawName, 0, b, 0, this.rawName.length);
         return b;
      } else {
         return null;
      }
   }

   protected long getLocalHeaderOffset() {
      return this.localHeaderOffset;
   }

   protected void setLocalHeaderOffset(long localHeaderOffset) {
      this.localHeaderOffset = localHeaderOffset;
   }

   public long getDataOffset() {
      return this.dataOffset;
   }

   protected void setDataOffset(long dataOffset) {
      this.dataOffset = dataOffset;
   }

   public boolean isStreamContiguous() {
      return this.isStreamContiguous;
   }

   protected void setStreamContiguous(boolean isStreamContiguous) {
      this.isStreamContiguous = isStreamContiguous;
   }

   public int hashCode() {
      return this.getName().hashCode();
   }

   public GeneralPurposeBit getGeneralPurposeBit() {
      return this.gpb;
   }

   public void setGeneralPurposeBit(GeneralPurposeBit b) {
      this.gpb = b;
   }

   private void mergeExtraFields(ZipExtraField[] f, boolean local) throws ZipException {
      if (this.extraFields == null) {
         this.setExtraFields(f);
      } else {
         ZipExtraField[] var3 = f;
         int var4 = f.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ZipExtraField element = var3[var5];
            Object existing;
            if (element instanceof UnparseableExtraFieldData) {
               existing = this.unparseableExtra;
            } else {
               existing = this.getExtraField(element.getHeaderId());
            }

            if (existing == null) {
               this.addExtraField(element);
            } else {
               byte[] b;
               if (local) {
                  b = element.getLocalFileDataData();
                  ((ZipExtraField)existing).parseFromLocalFileData(b, 0, b.length);
               } else {
                  b = element.getCentralDirectoryData();
                  ((ZipExtraField)existing).parseFromCentralDirectoryData(b, 0, b.length);
               }
            }
         }

         this.setExtra();
      }

   }

   public Date getLastModifiedDate() {
      return new Date(this.getTime());
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj != null && this.getClass() == obj.getClass()) {
         ZipArchiveEntry other = (ZipArchiveEntry)obj;
         String myName = this.getName();
         String otherName = other.getName();
         if (myName == null) {
            if (otherName != null) {
               return false;
            }
         } else if (!myName.equals(otherName)) {
            return false;
         }

         String myComment = this.getComment();
         String otherComment = other.getComment();
         if (myComment == null) {
            myComment = "";
         }

         if (otherComment == null) {
            otherComment = "";
         }

         return this.getTime() == other.getTime() && myComment.equals(otherComment) && this.getInternalAttributes() == other.getInternalAttributes() && this.getPlatform() == other.getPlatform() && this.getExternalAttributes() == other.getExternalAttributes() && this.getMethod() == other.getMethod() && this.getSize() == other.getSize() && this.getCrc() == other.getCrc() && this.getCompressedSize() == other.getCompressedSize() && Arrays.equals(this.getCentralDirectoryExtra(), other.getCentralDirectoryExtra()) && Arrays.equals(this.getLocalFileDataExtra(), other.getLocalFileDataExtra()) && this.localHeaderOffset == other.localHeaderOffset && this.dataOffset == other.dataOffset && this.gpb.equals(other.gpb);
      } else {
         return false;
      }
   }

   public void setVersionMadeBy(int versionMadeBy) {
      this.versionMadeBy = versionMadeBy;
   }

   public void setVersionRequired(int versionRequired) {
      this.versionRequired = versionRequired;
   }

   public int getVersionRequired() {
      return this.versionRequired;
   }

   public int getVersionMadeBy() {
      return this.versionMadeBy;
   }

   public int getRawFlag() {
      return this.rawFlag;
   }

   public void setRawFlag(int rawFlag) {
      this.rawFlag = rawFlag;
   }
}
