package weblogic.xml.schema.types;

import javax.xml.namespace.QName;
import weblogic.xml.schema.types.util.XSDDurationDeserializer;
import weblogic.xml.schema.types.util.XSDDurationSerializer;

public final class XSDDuration implements XSDBuiltinType {
   private final Duration javaValue;
   private final String xmlValue;
   public static final QName XML_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "duration");
   private static final XSDDurationDeserializer deser = new XSDDurationDeserializer();

   public static XSDDuration createFromXml(String xml) {
      return new XSDDuration(xml);
   }

   public static XSDDuration createFromDuration(Duration d) {
      Duration our_dur = new Duration(d);
      return new XSDDuration(our_dur);
   }

   private XSDDuration(String xml) {
      this.xmlValue = xml;
      this.javaValue = convertXml(xml);
   }

   private XSDDuration(Duration f) {
      this.javaValue = f;
      this.xmlValue = getXml(f);
   }

   public String getXml() {
      return this.xmlValue;
   }

   public String getCanonicalXml() {
      return getCanonicalXml(this.javaValue);
   }

   public QName getTypeName() {
      return XML_TYPE_NAME;
   }

   public Object getJavaObject() {
      return this.javaValue;
   }

   public Duration getDuration() {
      return this.javaValue;
   }

   public static Duration convertXml(String xsd_duration) {
      if (xsd_duration == null) {
         throw new NullPointerException();
      } else {
         try {
            XSDDurationDeserializer var10000 = deser;
            return XSDDurationDeserializer.getDuration(xsd_duration);
         } catch (NumberFormatException var3) {
            String msg = TypeUtils.createInvalidArgMsg(xsd_duration, XML_TYPE_NAME, var3);
            throw new IllegalLexicalValueException(msg, xsd_duration, XML_TYPE_NAME, var3);
         }
      }
   }

   public static void validurationXml(String xsd_duration) {
      convertXml(xsd_duration);
   }

   public static String getXml(Duration cal) {
      return XSDDurationSerializer.getString(cal);
   }

   public static String getCanonicalXml(Duration dur) {
      return XSDDurationSerializer.getString(dur);
   }

   public String toString() {
      return this.getXml();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else {
         return obj instanceof XSDDuration ? this.javaValue.equals(((XSDDuration)obj).javaValue) : false;
      }
   }

   public final int hashCode() {
      return this.javaValue.hashCode();
   }
}
