package com.octetstring.vde.backend.standard;

import java.io.UnsupportedEncodingException;

public class KeyPtr implements Comparable {
   public int first = 0;
   public int last = 0;
   public int hashCode = 0;
   public byte[] myKey = null;

   public KeyPtr(int first, int last) {
      this.first = first;
      this.last = last;
      int len = last - first;
      KeyPool kp = KeyPool.getInstance();
      int hc = 0;

      for(int byteNo = 0; byteNo < len; ++byteNo) {
         hc += kp.charAt(byteNo) * 31 ^ len - byteNo + 1;
      }

      this.hashCode = hc;
   }

   public KeyPtr(byte[] key) {
      this.myKey = key;
      int hc = 0;

      for(int byteNo = 0; byteNo < key.length; ++byteNo) {
         hc += key[byteNo] * 31 ^ key.length - byteNo + 1;
      }

      this.hashCode = hc;
      this.first = -1;
   }

   public int compareTo(KeyPtr key) {
      int len;
      int klen;
      int diff;
      if (this.first == -1 && key.first == -1) {
         len = this.myKey.length;
         klen = key.myKey.length;
         int diff = false;

         for(diff = 0; diff < klen && diff < len; ++diff) {
            int diff = this.myKey[diff] - key.myKey[diff];
            if (diff != 0) {
               if (diff > 0) {
               }

               return diff;
            }
         }

         if (len == klen) {
            return 0;
         } else {
            return len > klen ? 1 : -1;
         }
      } else {
         if (this.first != -1) {
            len = this.last - this.first;
         } else {
            len = this.myKey.length;
         }

         if (key.first != -1) {
            klen = key.last - key.first;
         } else {
            klen = key.myKey.length;
         }

         KeyPool kp = KeyPool.getInstance();
         int diff = false;
         byte nc1 = false;
         byte nc2 = false;

         for(int i = 0; i < klen && i < len; ++i) {
            byte nc1;
            if (this.first == -1) {
               nc1 = this.myKey[i];
            } else {
               nc1 = kp.charAt(this.first + i);
            }

            byte nc2;
            if (key.first == -1) {
               nc2 = key.myKey[i];
            } else {
               nc2 = kp.charAt(key.first + i);
            }

            diff = nc1 - nc2;
            if (diff != 0) {
               if (diff > 0) {
               }

               return diff;
            }
         }

         if (len == klen) {
            return 0;
         } else {
            return len > klen ? 1 : -1;
         }
      }
   }

   public int compareTo(Object obj) {
      return obj instanceof byte[] ? this.compareTo((byte[])((byte[])obj)) : this.compareTo((KeyPtr)obj);
   }

   public int compareTo(byte[] key) {
      int len;
      int klen;
      int diff;
      if (this.first == -1) {
         len = this.myKey.length;
         klen = key.length;
         int diff = false;

         for(diff = 0; diff < klen && diff < len; ++diff) {
            int diff = this.myKey[diff] - key[diff];
            if (diff != 0) {
               if (diff > 0) {
               }

               return diff;
            }
         }

         if (len == klen) {
            return 0;
         } else {
            return len > klen ? 1 : -1;
         }
      } else {
         len = this.last - this.first;
         klen = key.length;
         KeyPool kp = KeyPool.getInstance();
         int diff = false;

         for(int i = 0; i < klen && i < len; ++i) {
            diff = kp.charAt(this.first + i) - key[i];
            if (diff != 0) {
               if (diff > 0) {
               }

               return diff;
            }
         }

         if (len == klen) {
            return 0;
         } else {
            return len > klen ? 1 : -1;
         }
      }
   }

   public boolean endsWith(byte[] suffix) {
      int i;
      if (this.first == -1) {
         if (this.myKey.length < suffix.length) {
            return false;
         } else {
            for(i = 0; i < suffix.length; ++i) {
               if (this.myKey[this.myKey.length - i - 1] != suffix[suffix.length - i - 1]) {
                  return false;
               }
            }

            return true;
         }
      } else {
         i = this.last - this.first;
         int slen = suffix.length;
         if (i < slen) {
            return false;
         } else {
            KeyPool kp = KeyPool.getInstance();

            for(int i = 0; i < slen; ++i) {
               if (kp.charAt(this.last - i - 1) != suffix[slen - i - 1]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public boolean equals(KeyPtr key) {
      if (this.hashCode != key.hashCode()) {
         return false;
      } else if (this.first != -1 && key.first != -1) {
         return this.first == key.first && this.last == key.last;
      } else {
         int len;
         if (this.first == -1 && key.first == -1) {
            if (this.myKey.length != key.myKey.length) {
               return false;
            } else {
               for(len = 0; len < this.myKey.length; ++len) {
                  if (this.myKey[len] != key.myKey[len]) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            if (this.first == -1) {
               len = this.myKey.length;
            } else {
               len = this.last - this.first;
            }

            int klen;
            if (key.first == -1) {
               klen = key.myKey.length;
            } else {
               klen = key.last - key.first;
            }

            if (len != klen) {
               return false;
            } else {
               KeyPool kp;
               int i;
               if (this.first != -1) {
                  kp = KeyPool.getInstance();

                  for(i = 0; i < len; ++i) {
                     if (kp.charAt(i + this.first) != key.myKey[i]) {
                        return false;
                     }
                  }

                  return true;
               } else {
                  kp = KeyPool.getInstance();

                  for(i = 0; i < len; ++i) {
                     if (this.myKey[i] != kp.charAt(key.first + i)) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         }
      }
   }

   public boolean equals(Object obj) {
      return obj instanceof byte[] ? this.equals((byte[])((byte[])obj)) : this.equals((KeyPtr)obj);
   }

   public boolean equals(byte[] key) {
      int i;
      if (this.first == -1) {
         if (this.myKey.length != key.length) {
            return false;
         } else {
            for(i = 0; i < key.length; ++i) {
               if (this.myKey[i] != key[i]) {
                  return false;
               }
            }

            return true;
         }
      } else {
         i = this.last - this.first;
         int klen = key.length;
         if (i != klen) {
            return false;
         } else {
            KeyPool kp = KeyPool.getInstance();

            for(int i = 0; i < i; ++i) {
               if (kp.charAt(i + this.first) != key[i]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public int hashCode() {
      return this.hashCode;
   }

   public boolean startsWith(byte[] prefix) {
      int i;
      if (this.first == -1) {
         if (this.myKey.length < prefix.length) {
            return false;
         } else {
            for(i = 0; i < prefix.length; ++i) {
               if (this.myKey[i] != prefix[i]) {
                  return false;
               }
            }

            return true;
         }
      } else {
         i = this.last - this.first;
         if (i < prefix.length) {
            return false;
         } else {
            KeyPool kp = KeyPool.getInstance();

            for(int i = 0; i < prefix.length; ++i) {
               if (kp.charAt(this.first + i) != prefix[i]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public String toString() {
      if (this.first != -1) {
         return KeyPool.getInstance().getString(this.first, this.last);
      } else {
         try {
            return new String(this.myKey, "UTF8");
         } catch (UnsupportedEncodingException var2) {
            return new String(this.myKey);
         }
      }
   }

   public byte[] toByteArray() {
      return this.first != -1 ? KeyPool.getInstance().getBytes(this.first, this.last) : this.myKey;
   }
}
