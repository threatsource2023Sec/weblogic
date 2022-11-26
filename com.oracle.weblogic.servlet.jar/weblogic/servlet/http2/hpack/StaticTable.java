package weblogic.servlet.http2.hpack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.utils.StringUtils;

public class StaticTable {
   static final HeaderEntry[] STATIC_TABLE;
   static final int STATIC_TABLE_LENGTH;
   static final Map ENTRY_INDEX_MAP;

   public static int getHeaderIndex(HeaderEntry header) {
      if (!ENTRY_INDEX_MAP.containsKey(header.getName())) {
         return -1;
      } else {
         Map valueIndexMap = (Map)ENTRY_INDEX_MAP.get(header.getName());
         return !valueIndexMap.containsKey(StringUtils.getString(header.getValue())) ? -1 : (Integer)valueIndexMap.get(StringUtils.getString(header.getValue()));
      }
   }

   public static int getNameIndex(String name) {
      if (!ENTRY_INDEX_MAP.containsKey(name)) {
         return -1;
      } else {
         Map valueIndexMap = (Map)ENTRY_INDEX_MAP.get(name);
         int index = STATIC_TABLE_LENGTH;

         int i;
         for(Iterator var3 = valueIndexMap.values().iterator(); var3.hasNext(); index = Math.min(i, index)) {
            i = (Integer)var3.next();
         }

         return index;
      }
   }

   private static Map constructIndexMapping() {
      Map map = new HashMap();

      for(int i = 1; i < STATIC_TABLE.length; ++i) {
         String key = STATIC_TABLE[i].getName();
         if (map.containsKey(key)) {
            ((Map)map.get(key)).put(STATIC_TABLE[i].getValue(), i);
         } else {
            Map valueIndexMap = new HashMap();
            valueIndexMap.put(StringUtils.getString(STATIC_TABLE[i].getValue()), i);
            map.put(STATIC_TABLE[i].getName(), valueIndexMap);
         }
      }

      return map;
   }

   static {
      HeaderEntry[] entries = new HeaderEntry[]{null, new HeaderEntry(":authority", (byte[])null), new HeaderEntry(":method", "GET".getBytes()), new HeaderEntry(":method", "POST".getBytes()), new HeaderEntry(":path", "/".getBytes()), new HeaderEntry(":path", "/index.html".getBytes()), new HeaderEntry(":scheme", "http".getBytes()), new HeaderEntry(":scheme", "https".getBytes()), new HeaderEntry(":status", "200".getBytes()), new HeaderEntry(":status", "204".getBytes()), new HeaderEntry(":status", "206".getBytes()), new HeaderEntry(":status", "304".getBytes()), new HeaderEntry(":status", "400".getBytes()), new HeaderEntry(":status", "404".getBytes()), new HeaderEntry(":status", "500".getBytes()), new HeaderEntry("accept-charset", (byte[])null), new HeaderEntry("accept-encoding", "gzip, deflate".getBytes()), new HeaderEntry("accept-language", (byte[])null), new HeaderEntry("accept-ranges", (byte[])null), new HeaderEntry("accept", (byte[])null), new HeaderEntry("access-control-allow-origin", (byte[])null), new HeaderEntry("age", (byte[])null), new HeaderEntry("allow", (byte[])null), new HeaderEntry("authorization", (byte[])null), new HeaderEntry("cache-control", (byte[])null), new HeaderEntry("content-disposition", (byte[])null), new HeaderEntry("content-encoding", (byte[])null), new HeaderEntry("content-language", (byte[])null), new HeaderEntry("content-length", (byte[])null), new HeaderEntry("content-location", (byte[])null), new HeaderEntry("content-range", (byte[])null), new HeaderEntry("content-type", (byte[])null), new HeaderEntry("cookie", (byte[])null), new HeaderEntry("date", (byte[])null), new HeaderEntry("etag", (byte[])null), new HeaderEntry("expect", (byte[])null), new HeaderEntry("expires", (byte[])null), new HeaderEntry("from", (byte[])null), new HeaderEntry("host", (byte[])null), new HeaderEntry("if-match", (byte[])null), new HeaderEntry("if-modified-since", (byte[])null), new HeaderEntry("if-none-match", (byte[])null), new HeaderEntry("if-range", (byte[])null), new HeaderEntry("if-unmodified-since", (byte[])null), new HeaderEntry("last-modified", (byte[])null), new HeaderEntry("link", (byte[])null), new HeaderEntry("location", (byte[])null), new HeaderEntry("max-forwards", (byte[])null), new HeaderEntry("proxy-authenticate", (byte[])null), new HeaderEntry("proxy-authorization", (byte[])null), new HeaderEntry("range", (byte[])null), new HeaderEntry("referer", (byte[])null), new HeaderEntry("refresh", (byte[])null), new HeaderEntry("retry-after", (byte[])null), new HeaderEntry("server", (byte[])null), new HeaderEntry("set-cookie", (byte[])null), new HeaderEntry("strict-transport-security", (byte[])null), new HeaderEntry("transfer-encoding", (byte[])null), new HeaderEntry("user-agent", (byte[])null), new HeaderEntry("vary", (byte[])null), new HeaderEntry("via", (byte[])null), new HeaderEntry("www-authenticate", (byte[])null)};
      STATIC_TABLE = entries;
      STATIC_TABLE_LENGTH = STATIC_TABLE.length - 1;
      ENTRY_INDEX_MAP = constructIndexMapping();
   }
}
