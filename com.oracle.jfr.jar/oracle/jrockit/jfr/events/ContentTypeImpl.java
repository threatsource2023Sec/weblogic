package oracle.jrockit.jfr.events;

import com.oracle.jrockit.jfr.ContentType;
import com.oracle.jrockit.jfr.DataType;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class ContentTypeImpl {
   public static final int JVM_CONTENT_TYPES_START = 30;
   public static final int JVM_CONTENT_TYPES_END = 100;
   public static final int CONTENT_TYPE_NONE = 0;
   public static final int CONTENT_TYPE_BYTES = 1;
   public static final int CONTENT_TYPE_EPOCHMILLIS = 2;
   public static final int CONTENT_TYPE_MILLIS = 3;
   public static final int CONTENT_TYPE_NANOS = 4;
   public static final int CONTENT_TYPE_TICKS = 5;
   public static final int CONTENT_TYPE_ADDRESS = 6;
   public static final int CONTENT_TYPE_JVM_THREADID = 7;
   public static final int CONTENT_TYPE_JVM_JAVATHREADID = 8;
   public static final int CONTENT_TYPE_JVM_STACKTRACEID = 9;
   public static final int CONTENT_TYPE_JVM_CLASSID = 10;
   public static final int CONTENT_TYPE_PERCENTAGE = 11;
   public static final int CONTENT_TYPE_JVM_VMTHREADID = 101;
   public static final int CONTENT_TYPE_JVM_METHODID = 105;
   public static final int CONTENT_TYPE_JVM_FIELDID = 106;
   public static final int CONTENT_TYPE_JVM_UTFID = 107;
   private final int ordinal;
   private final DataType type;
   private final String name;
   private final ContentType mapped;
   private final DataType[] accepted;
   private static HashMap systemTypes = new HashMap();
   private static final DataType[] INTS;
   public static final ContentTypeImpl NONE;
   public static final ContentTypeImpl BYTES;
   public static final ContentTypeImpl TIMESTAMP;
   public static final ContentTypeImpl MILLIS;
   public static final ContentTypeImpl NANOS;
   public static final ContentTypeImpl TICKS;
   public static final ContentTypeImpl ADDRESS;
   public static final ContentTypeImpl PERCENTAGE;
   public static final ContentTypeImpl OSTHREAD;
   public static final ContentTypeImpl JAVATHREAD;
   public static final ContentTypeImpl STACKTRACE;
   public static final ContentTypeImpl CLASS;

   public ContentTypeImpl(int ordinal, DataType type, String name) {
      this.ordinal = ordinal;
      this.type = type;
      this.name = name;
      this.mapped = ContentType.None;
      this.accepted = null;
   }

   private ContentTypeImpl(int ordinal, DataType type, String name, ContentType mapped, DataType... accepted) {
      this.ordinal = ordinal;
      this.type = type;
      this.name = name;
      this.mapped = mapped;
      this.accepted = accepted;
      systemTypes.put(ordinal, this);
   }

   public int getOrdinal() {
      return this.ordinal;
   }

   public DataType getType() {
      return this.type;
   }

   public String getName() {
      return this.name;
   }

   public boolean isCompatible(DataType t) {
      if (this.type == null) {
         return true;
      } else if (t == this.type) {
         return true;
      } else {
         if (this.accepted != null) {
            DataType[] arr$ = this.accepted;
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               DataType t2 = arr$[i$];
               if (t2 == t) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static ContentTypeImpl getBuiltIn(int index) {
      return (ContentTypeImpl)systemTypes.get(index);
   }

   public ContentType getMapped() {
      return this.mapped;
   }

   public static Collection getSystemTypes() {
      return Collections.unmodifiableCollection(systemTypes.values());
   }

   public static ContentTypeImpl forContentType(ContentType t) {
      if (t != ContentType.None) {
         Iterator i$ = systemTypes.values().iterator();

         while(i$.hasNext()) {
            ContentTypeImpl ct = (ContentTypeImpl)i$.next();
            if (ct.mapped == t) {
               return ct;
            }
         }
      }

      return NONE;
   }

   public static ContentTypeImpl forClass(Class c) {
      if (c == Class.class) {
         return CLASS;
      } else {
         return Thread.class.isAssignableFrom(c) ? JAVATHREAD : NONE;
      }
   }

   public String toString() {
      return this.name + " (" + this.type + ')';
   }

   static {
      INTS = new DataType[]{DataType.U1, DataType.BYTE, DataType.U2, DataType.SHORT, DataType.U4, DataType.INTEGER, DataType.U8, DataType.LONG};
      NONE = new ContentTypeImpl(0, (DataType)null, "None", ContentType.None, new DataType[0]);
      BYTES = new ContentTypeImpl(1, DataType.U4, "Bytes", ContentType.Bytes, INTS);
      TIMESTAMP = new ContentTypeImpl(2, DataType.LONG, "Epochms", ContentType.Timestamp, new DataType[0]);
      MILLIS = new ContentTypeImpl(3, DataType.LONG, "ms", ContentType.Millis, new DataType[]{DataType.U8});
      NANOS = new ContentTypeImpl(4, DataType.LONG, "ns", ContentType.Nanos, new DataType[]{DataType.U8});
      TICKS = new ContentTypeImpl(5, DataType.LONG, "ticks", ContentType.Ticks, new DataType[]{DataType.U8});
      ADDRESS = new ContentTypeImpl(6, DataType.U8, "Address", ContentType.Address, new DataType[]{DataType.LONG});
      PERCENTAGE = new ContentTypeImpl(11, DataType.FLOAT, "Percentage", ContentType.Percentage, new DataType[]{DataType.DOUBLE});
      OSTHREAD = new ContentTypeImpl(7, DataType.U4, "OSThread", ContentType.OSThread, new DataType[0]);
      JAVATHREAD = new ContentTypeImpl(8, DataType.LONG, "JavaThread", ContentType.JavaThread, new DataType[0]);
      STACKTRACE = new ContentTypeImpl(9, DataType.U8, "StackTrace", ContentType.None, new DataType[0]);
      CLASS = new ContentTypeImpl(10, DataType.U8, "Class", ContentType.Class, new DataType[0]);
   }
}
