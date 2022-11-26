package weblogic.messaging.path;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import weblogic.utils.encoders.BASE64Decoder;
import weblogic.utils.encoders.BASE64Encoder;
import weblogic.utils.io.FilteringObjectInputStream;

public class PSEntryInfo {
   private static final String ITEM_VERSION_NUMBER = "VersionNumber";
   private static final String ITEM_CURSOR_HANDLE = "CursorHandle";
   private static final String ITEM_SERIALIZED_KEY = "SerializedKey";
   private static final String OPEN_TYPE_NAME = "PSEntryInfo";
   private static final String OPEN_DESCRIPTION = "This object represents information about a path service entry.  It is used by the Path Service cursor to return batches of path service entries to an administration client";
   private static final String[] itemNames = new String[]{"VersionNumber", "CursorHandle", "SerializedKey"};
   private static final String[] itemDescriptions = new String[]{"The version number.", "The handle to the entry in the cursor.", "The serialized representation of a path service Key"};
   private static final OpenType[] itemTypes;
   private static final int VERSION = 1;
   private Key key;
   private int cursorHandle;

   public PSEntryInfo(CompositeData cd) throws OpenDataException {
      this.readCompositeData(cd);
   }

   public PSEntryInfo(Key key, int cursorIndex) {
      this.key = key;
      this.cursorHandle = cursorIndex;
   }

   public Key getKey() {
      return this.key;
   }

   public long getCursorHandle() {
      return (long)this.cursorHandle;
   }

   public CompositeData toCompositeData() throws OpenDataException {
      CompositeDataSupport cds = new CompositeDataSupport(this.getCompositeType(), this.getCompositeDataMap());
      return cds;
   }

   protected void readCompositeData(CompositeData cd) throws OpenDataException {
      Long cdCursorHandle = (Long)cd.get("CursorHandle");
      if (cdCursorHandle != null) {
         this.cursorHandle = cdCursorHandle.intValue();
      }

      OpenDataException e;
      try {
         String serializedKey = (String)cd.get("SerializedKey");
         BASE64Decoder decoder = new BASE64Decoder();
         byte[] bytes = decoder.decodeBuffer(serializedKey);
         ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
         ObjectInputStream ois = new FilteringObjectInputStream(bais);
         this.key = (Key)ois.readObject();
      } catch (IOException var8) {
         e = new OpenDataException("Unable to deserialize path service entry.");
         e.initCause(var8);
         throw e;
      } catch (ClassNotFoundException var9) {
         e = new OpenDataException("Unable to deserialize path service entry.");
         e.initCause(var9);
         throw e;
      }
   }

   private Map getCompositeDataMap() throws OpenDataException {
      Map data = new HashMap();
      data.put("VersionNumber", new Integer(1));
      data.put("CursorHandle", new Long(this.getCursorHandle()));

      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(baos);
         oos.writeObject(this.getKey());
         BASE64Encoder encoder = new BASE64Encoder();
         String value = encoder.encodeBuffer(baos.toByteArray());
         data.put("SerializedKey", value);
         return data;
      } catch (IOException var6) {
         OpenDataException e = new OpenDataException("Unable to serialize path service entry.");
         e.initCause(var6);
         throw e;
      }
   }

   private CompositeType getCompositeType() throws OpenDataException {
      CompositeType ct = new CompositeType("PSEntryInfo", "This object represents information about a path service entry.  It is used by the Path Service cursor to return batches of path service entries to an administration client", itemNames, itemDescriptions, itemTypes);
      return ct;
   }

   static {
      itemTypes = new OpenType[]{SimpleType.INTEGER, SimpleType.LONG, SimpleType.STRING};
   }
}
