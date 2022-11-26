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

public class MemberInfo {
   static final long serialVersionUID = -6246813905189434496L;
   private static final String ITEM_VERSION_NUMBER = "VersionNumber";
   private static final String ITEM_SERIALIZED_MEMBER = "SerializedMember";
   private static final String OPEN_TYPE_NAME = "MemberInfo";
   private static final String OPEN_DESCRIPTION = "This object represents information about a path service member.A Member is an entry in an Assembly. For example, Queues are Members of a JMS Distributed Queue Assembly.  A 'path' is a particular Assembly member given a key.  A single Member will be identified by many different 'path' keys";
   private static final String[] itemNames = new String[]{"VersionNumber", "SerializedMember"};
   private static final String[] itemDescriptions = new String[]{"The version number.", "The serialized representation of a path service Member"};
   private static final OpenType[] itemTypes;
   private static final int VERSION = 1;
   private Member member;

   public MemberInfo(CompositeData cd) throws OpenDataException {
      this.readCompositeData(cd);
   }

   public MemberInfo(Member member) {
      this.member = member;
   }

   public Member getMember() {
      return this.member;
   }

   public CompositeData toCompositeData() throws OpenDataException {
      CompositeDataSupport cds = new CompositeDataSupport(this.getCompositeType(), this.getCompositeDataMap());
      return cds;
   }

   protected void readCompositeData(CompositeData cd) throws OpenDataException {
      OpenDataException e;
      try {
         String serializedMember = (String)cd.get("SerializedMember");
         BASE64Decoder decoder = new BASE64Decoder();
         byte[] bytes = decoder.decodeBuffer(serializedMember);
         ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
         ObjectInputStream ois = new FilteringObjectInputStream(bais);
         this.member = (Member)ois.readObject();
      } catch (IOException var7) {
         e = new OpenDataException("Unable to deserialize member.");
         e.initCause(var7);
         throw e;
      } catch (ClassNotFoundException var8) {
         e = new OpenDataException("Unable to deserialize member.");
         e.initCause(var8);
         throw e;
      }
   }

   private Map getCompositeDataMap() throws OpenDataException {
      Map data = new HashMap();

      try {
         data.put("VersionNumber", new Integer(1));
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(baos);
         oos.writeObject(this.member);
         BASE64Encoder encoder = new BASE64Encoder();
         String value = encoder.encodeBuffer(baos.toByteArray());
         data.put("SerializedMember", value);
         return data;
      } catch (IOException var9) {
         OpenDataException e = new OpenDataException("Unable to serialize member.");
         e.initCause(var9);
         throw e;
      } finally {
         ;
      }
   }

   private CompositeType getCompositeType() throws OpenDataException {
      CompositeType ct = new CompositeType("MemberInfo", "This object represents information about a path service member.A Member is an entry in an Assembly. For example, Queues are Members of a JMS Distributed Queue Assembly.  A 'path' is a particular Assembly member given a key.  A single Member will be identified by many different 'path' keys", itemNames, itemDescriptions, itemTypes);
      return ct;
   }

   static {
      itemTypes = new OpenType[]{SimpleType.INTEGER, SimpleType.STRING};
   }
}
