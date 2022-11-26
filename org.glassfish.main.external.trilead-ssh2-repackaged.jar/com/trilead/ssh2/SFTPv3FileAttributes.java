package com.trilead.ssh2;

public class SFTPv3FileAttributes {
   public Long size = null;
   public Integer uid = null;
   public Integer gid = null;
   public Integer permissions = null;
   public Integer atime = null;
   public Integer mtime = null;

   public boolean isDirectory() {
      if (this.permissions == null) {
         return false;
      } else {
         return (this.permissions & 16384) != 0;
      }
   }

   public boolean isRegularFile() {
      if (this.permissions == null) {
         return false;
      } else {
         return (this.permissions & '耀') != 0;
      }
   }

   public boolean isSymlink() {
      if (this.permissions == null) {
         return false;
      } else {
         return (this.permissions & 'ꀀ') != 0;
      }
   }

   public String getOctalPermissions() {
      if (this.permissions == null) {
         return null;
      } else {
         String res = Integer.toString(this.permissions & '\uffff', 8);
         StringBuffer sb = new StringBuffer();

         for(int leadingZeros = 7 - res.length(); leadingZeros > 0; --leadingZeros) {
            sb.append('0');
         }

         sb.append(res);
         return sb.toString();
      }
   }
}
