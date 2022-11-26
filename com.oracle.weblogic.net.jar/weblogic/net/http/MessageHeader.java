package weblogic.net.http;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class MessageHeader {
   private static final byte[] HTTP_PROTOCOL_BYTES = new byte[]{72, 84, 84, 80, 47, 49, 46};
   private static final int HTTP_PROTOCOL_LEN = 7;
   private String[] keys;
   private String[] vals;
   private int nkeys;
   private byte[] byteBuffer = new byte[7];

   public MessageHeader() {
      this.grow();
   }

   public MessageHeader(InputStream is) throws IOException {
      this.parseHeader(is);
   }

   public String findValue(String k) {
      int i;
      if (k == null) {
         i = this.nkeys;

         while(true) {
            --i;
            if (i < 0) {
               break;
            }

            if (this.keys[i] == null) {
               return this.vals[i];
            }
         }
      } else {
         i = this.nkeys;

         while(true) {
            --i;
            if (i < 0) {
               break;
            }

            if (k.equalsIgnoreCase(this.keys[i])) {
               return this.vals[i];
            }
         }
      }

      return null;
   }

   public String getKey(int n) {
      return n >= 0 && n < this.nkeys ? this.keys[n] : null;
   }

   public String getValue(int n) {
      return n >= 0 && n < this.nkeys ? this.vals[n] : null;
   }

   public Map getHeaders(String[] excludeList) {
      return this.filterAndAddHeaders(excludeList, (Map)null);
   }

   public Map filterAndAddHeaders(String[] excludeList, Map include) {
      boolean skipIt = false;
      Map m = new HashMap();
      int i = this.nkeys;

      while(true) {
         --i;
         if (i < 0) {
            Object l;
            Iterator entries;
            Map.Entry entry;
            if (include != null) {
               for(entries = include.entrySet().iterator(); entries.hasNext(); ((List)l).add(entry.getValue())) {
                  entry = (Map.Entry)entries.next();
                  l = (List)m.get(entry.getKey());
                  if (l == null) {
                     l = new ArrayList();
                     m.put((String)entry.getKey(), l);
                  }
               }
            }

            entries = m.keySet().iterator();

            while(entries.hasNext()) {
               String key = (String)entries.next();
               m.put(key, Collections.unmodifiableList((List)m.get(key)));
            }

            return Collections.unmodifiableMap(m);
         }

         if (excludeList != null) {
            for(int j = 0; j < excludeList.length; ++j) {
               if (excludeList[j] != null && excludeList[j].equalsIgnoreCase(this.keys[i])) {
                  skipIt = true;
                  break;
               }
            }
         }

         if (!skipIt) {
            List l = (List)m.get(this.keys[i]);
            if (l == null) {
               l = new ArrayList();
               m.put(this.keys[i], l);
            }

            ((List)l).add(this.vals[i]);
         } else {
            skipIt = false;
         }
      }
   }

   public void print(HttpOutputStream p) throws IOException {
      for(int i = 0; i < this.nkeys; ++i) {
         if (this.keys[i] != null) {
            p.print(this.keys[i]);
            if (this.vals[i] != null) {
               p.print(": ");
               p.print(this.vals[i]);
            }

            p.print("\r\n");
         }
      }

      p.print("\r\n");
   }

   public void print(PrintStream p) {
      for(int i = 0; i < this.nkeys; ++i) {
         if (this.keys[i] != null) {
            p.print(this.keys[i] + (this.vals[i] != null ? ": " + this.vals[i] : "") + "\r\n");
         }
      }

      p.print("\r\n");
      p.flush();
   }

   public void add(String k, String v) {
      this.grow();
      this.keys[this.nkeys] = k;
      this.vals[this.nkeys] = v;
      ++this.nkeys;
   }

   public void prepend(String k, String v) {
      this.grow();

      for(int i = this.nkeys; i > 0; --i) {
         this.keys[i] = this.keys[i - 1];
         this.vals[i] = this.vals[i - 1];
      }

      this.keys[0] = k;
      this.vals[0] = v;
      ++this.nkeys;
   }

   public void set(int i, String k, String v) {
      this.grow();
      if (i >= 0) {
         if (i > this.nkeys) {
            this.add(k, v);
         } else {
            this.keys[i] = k;
            this.vals[i] = v;
         }

      }
   }

   private void grow() {
      if (this.keys == null || this.nkeys >= this.keys.length) {
         String[] nk = new String[this.nkeys + 4];
         String[] nv = new String[this.nkeys + 4];
         if (this.keys != null) {
            System.arraycopy(this.keys, 0, nk, 0, this.nkeys);
         }

         if (this.vals != null) {
            System.arraycopy(this.vals, 0, nv, 0, this.nkeys);
         }

         this.keys = nk;
         this.vals = nv;
      }

   }

   public void set(String k, String v) {
      int i = this.nkeys;

      do {
         --i;
         if (i < 0) {
            this.add(k, v);
            return;
         }
      } while(!k.equalsIgnoreCase(this.keys[i]));

      this.vals[i] = v;
   }

   public void remove(String k) {
      int i;
      int j;
      if (k == null) {
         for(i = 0; i < this.nkeys; ++i) {
            while(this.keys[i] == null && i < this.nkeys) {
               for(j = i; j < this.nkeys - 1; ++j) {
                  this.keys[j] = this.keys[j + 1];
                  this.vals[j] = this.vals[j + 1];
               }

               --this.nkeys;
            }
         }
      } else {
         for(i = 0; i < this.nkeys; ++i) {
            while(k.equalsIgnoreCase(this.keys[i]) && i < this.nkeys) {
               for(j = i; j < this.nkeys - 1; ++j) {
                  this.keys[j] = this.keys[j + 1];
                  this.vals[j] = this.vals[j + 1];
               }

               --this.nkeys;
            }
         }
      }

   }

   public void setIfNotSet(String k, String v) {
      if (this.findValue(k) == null) {
         this.add(k, v);
      }

   }

   public void parseHeader(InputStream is) throws IOException {
      this.nkeys = 0;
      if (is != null && this.isHTTP(is)) {
         char[] s = new char[10];
         int firstc = is.read();
         if (firstc < 0) {
            throw new IOException("Response contained no data");
         } else {
            String v;
            String k;
            for(; firstc != 10 && firstc != 13 && firstc >= 0; this.add(k, v)) {
               int len = 0;
               int keyend = -1;
               boolean inKey = firstc > 32;
               s[len++] = (char)firstc;

               label105:
               while(true) {
                  int c;
                  if ((c = is.read()) < 0) {
                     firstc = -1;
                     break;
                  }

                  switch (c) {
                     case 9:
                        c = 32;
                     case 32:
                        inKey = false;
                        break;
                     case 10:
                     case 13:
                        firstc = is.read();
                        if (c == 13 && firstc == 10) {
                           firstc = is.read();
                           if (firstc == 13) {
                              firstc = is.read();
                           }
                        }

                        if (firstc == 10 || firstc == 13 || firstc > 32) {
                           break label105;
                        }

                        c = 32;
                        break;
                     case 58:
                        if (inKey && len > 0) {
                           keyend = len;
                        }

                        inKey = false;
                  }

                  if (len >= s.length) {
                     char[] ns = new char[s.length * 2];
                     System.arraycopy(s, 0, ns, 0, len);
                     s = ns;
                  }

                  s[len++] = (char)c;
               }

               while(len > 0 && s[len - 1] <= ' ') {
                  --len;
               }

               if (keyend <= 0) {
                  k = null;
                  keyend = 0;
               } else {
                  k = String.copyValueOf(s, 0, keyend);
                  if (keyend < len && s[keyend] == ':') {
                     ++keyend;
                  }

                  while(keyend < len && s[keyend] <= ' ') {
                     ++keyend;
                  }
               }

               if (keyend >= len) {
                  v = new String();
               } else {
                  v = String.copyValueOf(s, keyend, len - keyend);
               }
            }

         }
      }
   }

   private boolean isHTTP(InputStream is) throws IOException {
      if (!is.markSupported()) {
         return true;
      } else {
         is.mark(7);
         int total = 0;

         boolean var7;
         try {
            while(total < 7) {
               int bytesRead = is.read(this.byteBuffer, total, 7 - total);
               if (bytesRead == -1) {
                  throw new EOFException("Response had end of stream after " + total + " bytes");
               }

               total += bytesRead;
            }

            var7 = Arrays.equals(HTTP_PROTOCOL_BYTES, this.byteBuffer);
         } finally {
            is.reset();
         }

         return var7;
      }
   }

   public String toString() {
      String result = super.toString();

      for(int i = 0; i < this.keys.length; ++i) {
         result = result + "{" + this.keys[i] + ": " + this.vals[i] + "}";
      }

      return result;
   }

   public synchronized Map getHeaders() {
      HashMap hashmap = new HashMap();
      int i = this.nkeys;

      while(true) {
         --i;
         Object obj;
         if (i < 0) {
            Set set1 = hashmap.keySet();
            Iterator iterator = set1.iterator();

            while(iterator.hasNext()) {
               obj = iterator.next();
               List list = (List)hashmap.get(obj);
               hashmap.put(obj, Collections.unmodifiableList(list));
            }

            return Collections.unmodifiableMap(hashmap);
         }

         obj = hashmap.get(this.keys[i]);
         if (obj == null) {
            obj = new ArrayList();
            hashmap.put(this.keys[i], obj);
         }

         ((List)obj).add(this.vals[i]);
      }
   }
}
