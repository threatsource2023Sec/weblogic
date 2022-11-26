package com.bea.xbean.values;

import com.bea.xbean.common.PrefixResolver;
import com.bea.xbean.common.QNameHelper;
import com.bea.xbean.common.ValidationContext;
import com.bea.xbean.common.XMLChar;
import com.bea.xbean.schema.BuiltinSchemaTypeSystem;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlObject;
import javax.xml.namespace.QName;

public class JavaQNameHolder extends XmlObjectBase {
   private QName _value;
   private static final NamespaceManager PRETTY_PREFIXER = new PrettyNamespaceManager();

   public SchemaType schemaType() {
      return BuiltinSchemaTypeSystem.ST_QNAME;
   }

   protected int get_wscanon_rule() {
      return 1;
   }

   public String compute_text(NamespaceManager nsm) {
      if (nsm == null) {
         nsm = PRETTY_PREFIXER;
      }

      String namespace = this._value.getNamespaceURI();
      String localPart = this._value.getLocalPart();
      if (namespace != null && namespace.length() != 0) {
         String prefix = nsm.find_prefix_for_nsuri(namespace, (String)null);

         assert prefix != null;

         return "".equals(prefix) ? localPart : prefix + ":" + localPart;
      } else {
         return localPart;
      }
   }

   public static QName validateLexical(String v, ValidationContext context, PrefixResolver resolver) {
      QName name;
      try {
         name = parse(v, resolver);
      } catch (XmlValueOutOfRangeException var5) {
         context.invalid(var5.getMessage());
         name = null;
      }

      return name;
   }

   private static QName parse(String v, PrefixResolver resolver) {
      int end;
      for(end = v.length(); end > 0 && XMLChar.isSpace(v.charAt(end - 1)); --end) {
      }

      int start;
      for(start = 0; start < end && XMLChar.isSpace(v.charAt(start)); ++start) {
      }

      int firstcolon = v.indexOf(58, start);
      String prefix;
      String localname;
      if (firstcolon >= 0) {
         prefix = v.substring(start, firstcolon);
         localname = v.substring(firstcolon + 1, end);
      } else {
         prefix = "";
         localname = v.substring(start, end);
      }

      if (prefix.length() > 0 && !XMLChar.isValidNCName(prefix)) {
         throw new XmlValueOutOfRangeException("QName", new Object[]{"Prefix not a valid NCName in '" + v + "'"});
      } else if (!XMLChar.isValidNCName(localname)) {
         throw new XmlValueOutOfRangeException("QName", new Object[]{"Localname not a valid NCName in '" + v + "'"});
      } else {
         String uri = resolver == null ? null : resolver.getNamespaceForPrefix(prefix);
         if (uri == null) {
            if (prefix.length() > 0) {
               throw new XmlValueOutOfRangeException("QName", new Object[]{"Can't resolve prefix '" + prefix + "'"});
            }

            uri = "";
         }

         return prefix != null && prefix.length() > 0 ? new QName(uri, localname, prefix) : new QName(uri, localname);
      }
   }

   protected void set_text(String s) {
      PrefixResolver resolver = NamespaceContext.getCurrent();
      if (resolver == null && this.has_store()) {
         resolver = this.get_store();
      }

      this._value = parse(s, (PrefixResolver)resolver);
   }

   protected void set_QName(QName name) {
      assert name != null;

      if (this.has_store()) {
         this.get_store().find_prefix_for_nsuri(name.getNamespaceURI(), (String)null);
      }

      this._value = name;
   }

   protected void set_xmlanysimple(XmlAnySimpleType value) {
      this._value = parse(value.getStringValue(), NamespaceContext.getCurrent());
   }

   protected void set_nil() {
      this._value = null;
   }

   public QName getQNameValue() {
      this.check_dated();
      return this._value;
   }

   protected boolean equal_to(XmlObject obj) {
      return this._value.equals(((XmlObjectBase)obj).qNameValue());
   }

   protected int value_hash_code() {
      return this._value.hashCode();
   }

   private static class PrettyNamespaceManager implements NamespaceManager {
      private PrettyNamespaceManager() {
      }

      public String find_prefix_for_nsuri(String nsuri, String suggested_prefix) {
         return QNameHelper.suggestPrefix(nsuri);
      }

      public String getNamespaceForPrefix(String prefix) {
         throw new RuntimeException("Should not be called");
      }

      // $FF: synthetic method
      PrettyNamespaceManager(Object x0) {
         this();
      }
   }
}
