package com.octetstring.vde.util.guid;

public final class Guid implements Cloneable {
   public static final int GUID_BYTE_SIZE = 16;
   public static final int GUID_STRING_SIZE = 35;
   public static final int GUID_STRING_SIZE_NEW = 32;
   private static final String ALLOWED_CHARACTERS = "-0123456789ABCDEF";
   private static final int HASH_CODE_PRIME_MULTIPLIER = 37;
   private static final String GUID_STRING_INVALID_LENGTH_MESSAGE_PART1 = "Length of GUID String = ";
   private static final String GUID_STRING_INVALID_LENGTH_MESSAGE_PART2 = ",should be 32 or 35";
   private static final String GUID_BYTES_INVALID_LENGTH_MESSAGE_PART1 = "Length of Byte Array = ";
   private static final String GUID_BYTES_INVALID_LENGTH_MESSAGE_PART2 = ",should be 16";
   private final byte[] guid_bytes;
   private final String guid_string;

   public static Guid newInstance() {
      return new Guid();
   }

   public Guid() {
      this.guid_bytes = GuidGenerator.getInstance().nextGuidInBytes();
      this.guid_string = OrclGuid.expandGuidString(GuidUtils.toHexString(this.guid_bytes));
   }

   public Guid(String guidHexString) {
      if (!this.isValidGUIDString(guidHexString)) {
         throw new InvalidGuidException("Invalid GUID String");
      } else {
         String guidHexStringCopy = new String(guidHexString);
         guidHexStringCopy = OrclGuid.expandGuidString(guidHexStringCopy);
         if (guidHexStringCopy.length() != 35) {
            throw new InvalidGuidException("Length of GUID String = " + guidHexStringCopy.length() + ",should be 32 or 35");
         } else {
            this.guid_string = guidHexStringCopy;
            this.guid_bytes = GuidUtils.toByteArray(OrclGuid.compactGuidString(guidHexStringCopy));
         }
      }
   }

   private boolean isValidGUIDString(String guidString) {
      if (!this.hasValidLength(guidString)) {
         return false;
      } else {
         return this.hasValidCharacters(guidString);
      }
   }

   private boolean hasValidLength(String guidString) {
      return guidString != null && (guidString.length() == 32 || guidString.length() == 35);
   }

   private boolean hasValidCharacters(String guidString) {
      guidString = guidString.toUpperCase();
      char[] guidChars = guidString.toCharArray();
      int length = guidChars.length;

      for(int i = 0; i < length; ++i) {
         if ("-0123456789ABCDEF".indexOf(guidChars[i]) == -1) {
            return false;
         }
      }

      return true;
   }

   public Guid(byte[] byte_array) {
      if (byte_array.length != 16) {
         throw new InvalidGuidException("Length of Byte Array = " + byte_array.length + ",should be 16");
      } else {
         byte[] byte_array_copy = new byte[16];
         System.arraycopy(byte_array, 0, byte_array_copy, 0, 16);
         this.guid_bytes = byte_array_copy;
         this.guid_string = OrclGuid.expandGuidString(GuidUtils.toHexString(this.guid_bytes));
      }
   }

   public byte[] getBytes() {
      byte[] guid_bytes_copy = new byte[16];
      System.arraycopy(this.guid_bytes, 0, guid_bytes_copy, 0, 16);
      return guid_bytes_copy;
   }

   public final String toString() {
      return GuidUtils.toHexString(this.guid_bytes);
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else {
         return !(o instanceof Guid) ? false : this.equals((Guid)o);
      }
   }

   public boolean equals(Guid guid) {
      return guid.toString().equals(this.toString());
   }

   public int hashCode() {
      int result = 43;
      result = 37 * result + this.guid_string.hashCode();
      return result;
   }

   public Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   public static void main(String[] argv) {
      int loop_count = 1;
      if (argv.length >= 1) {
         loop_count = Integer.parseInt(argv[0]);
      }

      for(int i = 0; i < loop_count; ++i) {
         Guid my_guid = new Guid();
         System.out.println(my_guid);
      }

   }
}
