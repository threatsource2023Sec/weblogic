package com.octetstring.vde.util.guid;

public final class OrclGuid implements Cloneable {
   private Guid _guid = null;

   public static OrclGuid newInstance() {
      return new OrclGuid();
   }

   public OrclGuid() {
      this._guid = new Guid();
   }

   public OrclGuid(String string_guid) {
      if (string_guid.length() == 35) {
         this._guid = new Guid(string_guid);
      } else if (string_guid.length() == 32) {
         this._guid = new Guid(expandGuidString(string_guid));
      }

   }

   public OrclGuid(byte[] byte_array) {
      this._guid = new Guid(byte_array);
   }

   public byte[] getBytes() {
      return this._guid != null ? this._guid.getBytes() : null;
   }

   public final String toString() {
      return this._guid != null ? compactGuidString(this._guid.toString()) : null;
   }

   public boolean equals(Object o) {
      return this.getClass() == o.getClass() ? this.toString().equals(o.toString()) : false;
   }

   public int hashCode() {
      return this._guid != null ? this._guid.hashCode() : 0;
   }

   public Object clone() {
      OrclGuid new_guid = new OrclGuid(this.getBytes());
      return new_guid;
   }

   public static String compactGuidString(String s) {
      if (s != null && s.length() == 35) {
         return s.substring(0, 12) + s.substring(13, 17) + s.substring(18, 22) + s.substring(23, 35);
      } else {
         return s != null && s.length() == 32 ? s : "com.octetstring.vde.util.guid.OrclGuid: Invalid GUID String";
      }
   }

   public static String expandGuidString(String s) {
      if (s != null && s.length() == 32) {
         return s.substring(0, 12) + "-" + s.substring(12, 16) + "-" + s.substring(16, 20) + "-" + s.substring(20, 32);
      } else {
         return s != null && s.length() == 35 ? s : "com.octetstring.vde.util.guid.OrclGuid: Invalid GUID String";
      }
   }

   public static void main(String[] argv) {
      int loop_count = 1;
      if (argv.length >= 1) {
         loop_count = Integer.parseInt(argv[0]);
      }

      for(int i = 0; i < loop_count; ++i) {
         OrclGuid my_guid = new OrclGuid();
         System.out.println(my_guid);
      }

   }
}
