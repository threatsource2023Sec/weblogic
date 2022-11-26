package weblogic.security.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.security.spi.Resource;
import weblogic.security.spi.SelfDescribingResourceV2;
import weblogic.utils.collections.SecondChanceCacheMap;

public abstract class ResourceBase implements SelfDescribingResourceV2 {
   private static final long FACTOR = 31L;
   private static final char[] SPECIAL_CHARS = new char[]{',', '{', '}', '\\'};
   private static Pattern SPECIAL_CHARS_PATTERN = Pattern.compile("[,{}\\\\]");
   private static final String SCOPE_RESOURCE_ACTION_PROPERTY = "weblogic.security.scopeResourceAction";
   protected static final boolean SCOPE_RESOURCE_ACTION;
   private static final String RESOURCE_CACHE_SIZE_PROPERTY = "weblogic.security.service.resourceCacheSize";
   private static final String DISABLE_ESCAPING_PROPERTY = "weblogic.security.service.disableEscaping";
   private static final boolean DISABLE_ESCAPING;
   private static SecondChanceCacheMap resCache = null;
   protected static final Resource NO_PARENT;
   protected Resource parent = null;
   protected String resStr = null;
   protected String[] values = null;
   protected int length = 0;
   protected long id = 0L;

   public ResourceBase() {
      this.init((String[])null, 0, 0L);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof ResourceBase)) {
         return false;
      } else {
         ResourceBase rsrc = (ResourceBase)obj;
         if (this.id == rsrc.id && this.length == rsrc.length && this.getType().equals(rsrc.getType())) {
            if (this.values != rsrc.values) {
               for(int i = 0; i < this.length; ++i) {
                  if (this.values[i] == null) {
                     if (rsrc.values[i] != null) {
                        return false;
                     }
                  } else if (!this.values[i].equals(rsrc.values[i])) {
                     return false;
                  }
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public String toString() {
      if (this.resStr == null) {
         ResourceBase entry = (ResourceBase)resCache.get(this);
         if (entry != null && entry.resStr != null) {
            this.resStr = entry.resStr;
         } else {
            StringBuffer buf = new StringBuffer(256);
            this.writeResourceString(buf);
            this.resStr = buf.toString();
            if (entry == null) {
               resCache.put(this, this);
            } else {
               entry.resStr = this.resStr;
            }
         }

         if (entry != null) {
            if (entry.parent != null) {
               this.parent = entry.parent;
            } else if (this.parent != null) {
               entry.parent = this.parent;
            }
         }
      }

      return this.resStr;
   }

   protected void writeResourceString(StringBuffer buf) {
      buf.append("type=").append(this.getType());
      String[] keys = this.getKeys();

      for(int i = 0; i < this.length; ++i) {
         if (this.values[i] != null || !this.isTransitiveField(keys[i])) {
            buf.append(", ").append(keys[i]).append('=');
            appendValue(buf, this.values[i]);
         }
      }

   }

   public Resource getParentResource() {
      if (this.parent == null) {
         ResourceBase entry = (ResourceBase)resCache.get(this);
         if (entry != null && entry.parent != null) {
            this.parent = entry.parent;
         } else {
            this.parent = this.makeParent();
            if (this.parent == null) {
               this.parent = NO_PARENT;
            }

            if (entry == null) {
               resCache.put(this, this);
            } else {
               entry.parent = this.parent;
            }
         }

         if (entry != null) {
            if (entry.resStr != null) {
               this.resStr = entry.resStr;
            } else if (this.resStr != null) {
               entry.resStr = this.resStr;
            }
         }
      }

      return this.parent == NO_PARENT ? null : this.parent;
   }

   protected Resource makeParent() {
      return null;
   }

   public long getID() {
      return this.id;
   }

   public int hashCode() {
      return (int)(this.id ^ this.id >>> 32);
   }

   public abstract String[] getKeys();

   public int getFieldType(String fieldName) {
      return 1;
   }

   public int getRepeatingFieldIndex() {
      return -1;
   }

   public int getRepeatingFieldTerminatingIndex() {
      return this.getKeys().length;
   }

   public boolean isTransitiveField(String fieldName) {
      return false;
   }

   public String[] getValues() {
      String[] result = new String[this.length];
      if (this.length > 0) {
         System.arraycopy(this.values, 0, result, 0, this.length);
      }

      return result;
   }

   protected static StringBuffer appendValue(StringBuffer buf, String value) {
      if (value == null) {
         return buf;
      } else if (DISABLE_ESCAPING) {
         return buf.append(value);
      } else {
         Matcher matcher = SPECIAL_CHARS_PATTERN.matcher(value);

         while(matcher.find()) {
            matcher.appendReplacement(buf, "");
            buf.append("\\" + matcher.group());
         }

         matcher.appendTail(buf);
         return buf;
      }
   }

   protected static StringBuffer appendArrayValue(StringBuffer buf, String[] values, int len) {
      buf.append('{');
      if (len > 0) {
         appendValue(buf, values[0]);

         for(int i = 1; i < len; ++i) {
            buf.append(',');
            appendValue(buf, values[i]);
         }
      }

      buf.append('}');
      return buf;
   }

   protected void init(String[] values, long seed) {
      this.init(values, values.length, seed);
   }

   protected void init(String[] values, int len, long seed) {
      for(this.values = values; len > 0 && values[len - 1] == null; --len) {
      }

      this.length = len;
      long hash = 31L * seed + (long)this.getType().hashCode();

      for(int i = 0; i < len; ++i) {
         hash *= 31L;
         if (values[i] != null) {
            hash += (long)(1 + values[i].hashCode());
         }
      }

      this.id = hash;
   }

   static {
      int resCacheSize = 2048;
      boolean scopeResAction = false;
      boolean disableEscaping = false;

      try {
         String sra = System.getProperty("weblogic.security.scopeResourceAction", "false");
         scopeResAction = sra.length() == 0 || sra.equals("true");
         String rcs = System.getProperty("weblogic.security.service.resourceCacheSize");
         if (rcs != null) {
            try {
               resCacheSize = Integer.parseInt(rcs);
               if (resCacheSize < 0) {
                  resCacheSize = 0;
               }
            } catch (NumberFormatException var6) {
            }
         }

         disableEscaping = Boolean.getBoolean("weblogic.security.service.disableEscaping");
      } catch (SecurityException var7) {
      }

      SCOPE_RESOURCE_ACTION = scopeResAction;
      resCache = new SecondChanceCacheMap(resCacheSize);
      DISABLE_ESCAPING = disableEscaping;
      NO_PARENT = new ResourceBase() {
         public String[] getKeys() {
            return null;
         }

         public String getType() {
            return "";
         }
      };
   }
}
