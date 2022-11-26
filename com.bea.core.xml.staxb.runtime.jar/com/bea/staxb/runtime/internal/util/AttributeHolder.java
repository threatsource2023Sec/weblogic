package com.bea.staxb.runtime.internal.util;

import com.bea.staxb.runtime.internal.util.collections.StringList;
import javax.xml.namespace.QName;

public final class AttributeHolder {
   private final StringList data;
   private static final int LOCALNAME_OFFSET = 1;
   private static final int PREFIX_OFFSET = 2;
   private static final int VALUE_OFFSET = 3;

   public AttributeHolder(int initial_capacity) {
      this.data = new StringList(4 * initial_capacity);
   }

   public AttributeHolder() {
      this(4);
   }

   public void clear() {
      this.data.clear();

      assert this.data.getSize() == 0;

   }

   public void add(String namespaceURI, String localPart, String prefix, String value) {
      this.data.add(namespaceURI);
      this.data.add(localPart);
      this.data.add(prefix);
      this.data.add(value);

      assert this.data.getSize() % 4 == 0;

   }

   public void add(QName name, String value) {
      this.add(name.getNamespaceURI(), name.getLocalPart(), name.getPrefix(), value);
   }

   public int getAttributeCount() {
      assert this.data.getSize() % 4 == 0;

      return this.data.getSize() / 4;
   }

   public String getAttributeValue(int idx) {
      assert this.data.getSize() % 4 == 0;

      return this.data.get(3 + idx * 4);
   }

   public QName getAttributeName(int idx) {
      String uri = this.getAttributeNamespace(idx);
      String localName = this.getAttributeLocalName(idx);
      if (uri != null && uri.length() != 0) {
         String pfx = this.getAttributePrefix(idx);

         assert pfx != null;

         assert pfx.length() > 0;

         return new QName(uri.intern(), localName != null ? localName.intern() : null, pfx.intern());
      } else {
         return new QName(localName != null ? localName.intern() : null);
      }
   }

   public String getAttributeNamespace(int i) {
      assert this.data.getSize() % 4 == 0;

      return this.data.get(i * 4);
   }

   public String getAttributeLocalName(int i) {
      assert this.data.getSize() % 4 == 0;

      return this.data.get(1 + i * 4);
   }

   public String getAttributePrefix(int i) {
      assert this.data.getSize() % 4 == 0;

      return this.data.get(2 + i * 4);
   }

   public boolean isAttributeSpecified(int i) {
      throw new UnsupportedOperationException("UNIMPLEMENTED");
   }

   public String getAttributeValue(String uri, String lname) {
      int i = 0;

      for(int len = this.getAttributeCount(); i < len; ++i) {
         if (lname.equals(this.getAttributeLocalName(i)) && (uri == null || uri.equals(this.getAttributeNamespace(i)))) {
            return this.getAttributeValue(i);
         }
      }

      return null;
   }
}
