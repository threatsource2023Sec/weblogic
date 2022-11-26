package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.JavaTypeName;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.staxb.types.IndigoChar;
import com.bea.staxb.types.IndigoDuration;
import com.bea.staxb.types.IndigoGuid;
import com.bea.staxb.types.UnsignedByte;
import com.bea.staxb.types.UnsignedInt;
import com.bea.staxb.types.UnsignedLong;
import com.bea.staxb.types.UnsignedShort;
import com.bea.staxb.types.XMLGregorianCalendar;
import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;

public class BindingTypeNameRegistry {
   private static BindingTypeNameRegistry instance = new BindingTypeNameRegistry();
   private static Map typed = new HashMap();
   private static Map unTyped = new HashMap();

   private static BindingTypeName newBindingTypeNameFor(QName xName, String jName) {
      return BindingTypeName.forPair(JavaTypeName.forString(jName), XmlTypeName.forTypeNamed(xName));
   }

   public static BindingTypeNameRegistry getInstance() {
      return instance;
   }

   private BindingTypeNameRegistry() {
   }

   public BindingTypeName getBTNForUnTyped(QName xsi_type) {
      return (BindingTypeName)unTyped.get(xsi_type);
   }

   public BindingTypeName getBTNForTyped(BindingTypeName btn) {
      return (BindingTypeName)typed.get(btn);
   }

   static {
      unTyped.put(UnsignedByte.QNAME, newBindingTypeNameFor(UnsignedByte.QNAME, UnsignedByte.class.getName()));
      unTyped.put(UnsignedShort.QNAME, newBindingTypeNameFor(UnsignedShort.QNAME, UnsignedShort.class.getName()));
      unTyped.put(UnsignedInt.QNAME, newBindingTypeNameFor(UnsignedInt.QNAME, UnsignedInt.class.getName()));
      unTyped.put(UnsignedLong.QNAME, newBindingTypeNameFor(UnsignedLong.QNAME, UnsignedLong.class.getName()));
      unTyped.put(IndigoChar.QNAME, newBindingTypeNameFor(IndigoChar.QNAME, IndigoChar.class.getName()));
      unTyped.put(IndigoGuid.QNAME, newBindingTypeNameFor(IndigoGuid.QNAME, IndigoGuid.class.getName()));
      unTyped.put(IndigoDuration.QNAME, newBindingTypeNameFor(IndigoDuration.QNAME, IndigoDuration.class.getName()));
      QName anyqName = new QName("http://www.w3.org/2001/XMLSchema", "anyURI");
      unTyped.put(anyqName, newBindingTypeNameFor(anyqName, URI.class.getName()));
      BindingTypeName btnForDotNetCalendar = newBindingTypeNameFor(XMLGregorianCalendar.QNAME, XMLGregorianCalendar.class.getName());
      unTyped.put(XMLGregorianCalendar.QNAME, btnForDotNetCalendar);
      BindingTypeName btnForJavaCalendar = newBindingTypeNameFor(XMLGregorianCalendar.QNAME, Calendar.class.getName());
      typed.put(btnForJavaCalendar, btnForDotNetCalendar);
   }
}
