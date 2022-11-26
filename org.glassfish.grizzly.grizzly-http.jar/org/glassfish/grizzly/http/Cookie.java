package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.http.util.CookieSerializerUtils;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.utils.Charsets;

public class Cookie implements Cloneable, Cacheable {
   public static final int UNSET = Integer.MIN_VALUE;
   protected String name;
   protected String value;
   protected String comment;
   protected String domain;
   protected int maxAge = -1;
   protected String path;
   protected boolean secure;
   protected int version = Integer.MIN_VALUE;
   protected boolean isHttpOnly;
   protected LazyCookieState lazyCookieState;
   protected boolean usingLazyCookieState;
   private static final String tspecials = ",; ";

   protected Cookie() {
   }

   public Cookie(String name, String value) {
      this.name = name;
      this.value = value;
   }

   public void setComment(String purpose) {
      this.comment = purpose;
   }

   public String getComment() {
      if (this.comment == null && this.usingLazyCookieState) {
         String commentStr = this.lazyCookieState.getComment().toString(Charsets.ASCII_CHARSET);
         this.comment = this.version == 1 ? this.unescape(commentStr) : null;
      }

      return this.comment;
   }

   public void setDomain(String pattern) {
      if (pattern != null) {
         this.domain = pattern.toLowerCase();
      }

   }

   public String getDomain() {
      if (this.domain == null && this.usingLazyCookieState) {
         String domainStr = this.lazyCookieState.getDomain().toString(Charsets.ASCII_CHARSET);
         if (domainStr != null) {
            this.domain = this.unescape(domainStr);
         }
      }

      return this.domain;
   }

   public void setMaxAge(int expiry) {
      this.maxAge = expiry;
   }

   public int getMaxAge() {
      return this.maxAge;
   }

   public void setPath(String uri) {
      this.path = uri;
   }

   public String getPath() {
      if (this.path == null && this.usingLazyCookieState) {
         this.path = this.unescape(this.lazyCookieState.getPath().toString(Charsets.ASCII_CHARSET));
      }

      return this.path;
   }

   public void setSecure(boolean flag) {
      this.secure = flag;
   }

   public boolean isSecure() {
      return this.secure;
   }

   public String getName() {
      if (this.name == null && this.usingLazyCookieState) {
         this.name = this.lazyCookieState.getName().toString(Charsets.ASCII_CHARSET);
      }

      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setValue(String newValue) {
      this.value = newValue;
   }

   public String getValue() {
      if (this.value == null && this.usingLazyCookieState) {
         this.value = this.unescape(this.lazyCookieState.getValue().toString(Charsets.ASCII_CHARSET));
      }

      return this.value;
   }

   public int getVersion() {
      return this.version;
   }

   public void setVersion(int v) {
      if (v >= 0 && v <= 1) {
         this.version = v;
      } else {
         throw new IllegalArgumentException("Illegal Cookie Version");
      }
   }

   public boolean isVersionSet() {
      return this.version != Integer.MIN_VALUE;
   }

   public boolean isHttpOnly() {
      return this.isHttpOnly;
   }

   public void setHttpOnly(boolean isHttpOnly) {
      this.isHttpOnly = isHttpOnly;
   }

   public String asServerCookieString() {
      StringBuilder sb = new StringBuilder();
      CookieSerializerUtils.serializeServerCookie(sb, this);
      return sb.toString();
   }

   public Buffer asServerCookieBuffer() {
      return this.asServerCookieBuffer((MemoryManager)null);
   }

   public Buffer asServerCookieBuffer(MemoryManager memoryManager) {
      if (memoryManager == null) {
         memoryManager = MemoryManager.DEFAULT_MEMORY_MANAGER;
      }

      Buffer buffer = memoryManager.allocate(4096);
      CookieSerializerUtils.serializeServerCookie(buffer, this);
      buffer.trim();
      return buffer;
   }

   public String asClientCookieString() {
      StringBuilder sb = new StringBuilder();
      CookieSerializerUtils.serializeClientCookies(sb, this);
      return sb.toString();
   }

   public Buffer asClientCookieBuffer() {
      return this.asClientCookieBuffer((MemoryManager)null);
   }

   public Buffer asClientCookieBuffer(MemoryManager memoryManager) {
      if (memoryManager == null) {
         memoryManager = MemoryManager.DEFAULT_MEMORY_MANAGER;
      }

      Buffer buffer = memoryManager.allocate(4096);
      CookieSerializerUtils.serializeClientCookies(buffer, this);
      buffer.trim();
      return buffer;
   }

   public LazyCookieState getLazyCookieState() {
      this.usingLazyCookieState = true;
      if (this.lazyCookieState == null) {
         this.lazyCookieState = new LazyCookieState();
      }

      return this.lazyCookieState;
   }

   public String getCookieHeaderName() {
      return getCookieHeaderName(this.version);
   }

   public static String getCookieHeaderName(int version) {
      return version == 1 ? "Set-Cookie" : "Set-Cookie";
   }

   protected boolean lazyNameEquals(String name) {
      return this.name.equals(name);
   }

   protected String unescape(String s) {
      if (s == null) {
         return null;
      } else if (s.indexOf(92) == -1) {
         return s;
      } else {
         StringBuilder buf = new StringBuilder();

         for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c != '\\') {
               buf.append(c);
            } else {
               ++i;
               if (i >= s.length()) {
                  throw new IllegalArgumentException();
               }

               c = s.charAt(i);
               buf.append(c);
            }
         }

         return buf.toString();
      }
   }

   private static boolean isToken(String value) {
      int len = value.length();

      for(int i = 0; i < len; ++i) {
         char c = value.charAt(i);
         if (c < ' ' || c >= 127 || ",; ".indexOf(c) != -1) {
            return false;
         }
      }

      return true;
   }

   public Object clone() throws CloneNotSupportedException {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new RuntimeException(var2.getMessage());
      }
   }

   public void recycle() {
      this.name = null;
      this.value = null;
      this.comment = null;
      this.domain = null;
      this.maxAge = -1;
      this.path = null;
      this.secure = false;
      this.version = Integer.MIN_VALUE;
      this.isHttpOnly = false;
      if (this.usingLazyCookieState) {
         this.usingLazyCookieState = false;
         this.lazyCookieState.recycle();
      }

   }

   public String toString() {
      StringBuilder sb = new StringBuilder("Cookie{");
      sb.append("name='").append(this.name).append('\'');
      sb.append(", value='").append(this.value).append('\'');
      sb.append(", comment='").append(this.comment).append('\'');
      sb.append(", domain='").append(this.domain).append('\'');
      sb.append(", maxAge=").append(this.maxAge);
      sb.append(", path='").append(this.path).append('\'');
      sb.append(", secure=").append(this.secure);
      sb.append(", version=").append(this.version);
      sb.append(", isHttpOnly=").append(this.isHttpOnly);
      sb.append(", usingLazyCookieState=").append(this.usingLazyCookieState);
      sb.append('}');
      return sb.toString();
   }
}
