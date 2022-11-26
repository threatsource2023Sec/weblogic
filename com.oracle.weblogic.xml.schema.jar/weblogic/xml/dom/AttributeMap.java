package weblogic.xml.dom;

import java.util.Iterator;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import weblogic.utils.collections.Iterators;

public final class AttributeMap implements NamedNodeMap {
   private Element owner;
   private int length;
   private String[] data;
   private Attr[] attData;
   private static final int WIDTH = 4;
   private static final int LOCAL_NAME = 0;
   private static final int PREFIX = 1;
   private static final int NAMESPACE_URI = 2;
   private static final int VALUE = 3;
   private int[] attributes = new int[1];
   private int[] namespaces = new int[1];
   private int numAttr = 0;
   private int numNS = 0;
   private boolean dirty = true;

   public AttributeMap() {
      this.length = 0;
      this.data = new String[0];
      this.attData = new Attr[0];
   }

   public AttributeMap(int initialLength) {
      this.length = initialLength;
      this.data = new String[this.length * 4];
      this.attData = new Attr[this.length];
   }

   public Element getOwner() {
      return this.owner;
   }

   public void setOwner(Element e) {
      this.owner = e;
   }

   public int length() {
      return this.length;
   }

   public String getValue(String localName) {
      if (localName == null) {
         throw new IllegalArgumentException("localName may not be null");
      } else {
         for(int i = 0; i < this.length; ++i) {
            if (localName.equals(this.getLocalName(i))) {
               return this.getValue(i);
            }
         }

         return null;
      }
   }

   public String getValue(String namespaceURI, String localName) {
      if (localName == null) {
         throw new IllegalArgumentException("localName may not be null");
      } else if (namespaceURI == null) {
         return this.getValue(localName);
      } else {
         for(int i = 0; i < this.length; ++i) {
            if (localName.equals(this.getLocalName(i)) && namespaceURI.equals(this.getNamespaceURI(i))) {
               return this.getValue(i);
            }
         }

         return null;
      }
   }

   public void setLocalName(int index, String localName) {
      this.dirty = true;
      this.data[index * 4 + 0] = localName;
   }

   public String getLocalName(int index) {
      return this.data[index * 4 + 0];
   }

   public void setPrefix(int index, String localName) {
      this.dirty = true;
      this.data[index * 4 + 1] = localName;
   }

   public String getPrefix(int index) {
      return this.data[index * 4 + 1];
   }

   public void setNamespaceURI(int index, String uri) {
      this.dirty = true;
      this.data[index * 4 + 2] = uri;
   }

   public String getNamespaceURI(int index) {
      return this.data[index * 4 + 2];
   }

   public void setValue(int index, String uri) {
      this.data[index * 4 + 3] = uri;
   }

   public String getValue(int index) {
      return this.data[index * 4 + 3];
   }

   public int addAttribute(String uri, String localName, String prefix, String value) {
      this.ensureCapacity(this.length + 1);
      this.setAttribute(this.length, uri, localName, prefix, value);
      ++this.length;
      return this.length - 1;
   }

   public void setAttribute(int index, String uri, String localName, String prefix, String value) {
      this.setNamespaceURI(index, uri);
      this.setLocalName(index, localName);
      this.setPrefix(index, prefix);
      this.setValue(index, value);
      this.dirty = true;
   }

   public int getAttributeIndex(String localName) {
      for(int i = 0; i < this.length; ++i) {
         if (localName.equals(this.getLocalName(i))) {
            return i;
         }
      }

      return -1;
   }

   public int getNamespaceIndex(String prefix) {
      return prefix == null ? -1 : this.getAttributeIndex("http://www.w3.org/2000/xmlns/", prefix);
   }

   public int getAttributeIndexByPrefix(String prefix, String localName) {
      if (prefix == null) {
         return this.getAttributeIndex(localName);
      } else {
         for(int i = 0; i < this.length; ++i) {
            if (localName.equals(this.getLocalName(i)) && prefix.equals(this.getPrefix(i))) {
               return i;
            }
         }

         return -1;
      }
   }

   public int getAttributeIndex(String namespaceURI, String localName) {
      if (namespaceURI == null) {
         return this.getAttributeIndex(localName);
      } else {
         for(int i = 0; i < this.length; ++i) {
            if (localName.equals(this.getLocalName(i)) && namespaceURI.equals(this.getNamespaceURI(i))) {
               return i;
            }
         }

         return -1;
      }
   }

   public Attr getAttribute(int index) {
      if (this.attData[index] == null) {
         this.attData[index] = new AttributeReference(this, index);
      }

      return this.attData[index];
   }

   public void removeAttribute(int index) {
      if (index < this.length - 1) {
         System.arraycopy(this.data, (index + 1) * 4, this.data, index * 4, (this.length - index - 1) * 4);
         System.arraycopy(this.attData, index + 1, this.attData, index, this.length - index - 1);
      }

      this.attData[this.length - 1] = null;
      index = (this.length - 1) * 4;
      this.data[index++] = null;
      this.data[index++] = null;
      this.data[index++] = null;
      this.data[index] = null;
      --this.length;
   }

   public void clear() {
      if (this.data != null) {
         for(int i = 0; i < this.length * 4; ++i) {
            this.data[i] = null;
            this.attData[i] = null;
         }
      }

      this.length = 0;
   }

   private void ensureCapacity(int n) {
      if (this.attData.length < n) {
         int attMax;
         for(attMax = this.attData.length; attMax < n; attMax = attMax * 2 + 1) {
         }

         String[] newData = new String[attMax * 4];
         Attr[] newAttData = new Attr[attMax];
         if (this.length > 0) {
            System.arraycopy(this.data, 0, newData, 0, this.length * 4);
            System.arraycopy(this.attData, 0, newAttData, 0, this.length);
         }

         this.data = newData;
         this.attData = newAttData;
      }
   }

   public int getLength() {
      return this.length;
   }

   public Node getNamedItem(String name) {
      int index = this.getAttributeIndex(name);
      return index == -1 ? null : this.getAttribute(index);
   }

   public Node getNamedItemNS(String namespaceURI, String localName) {
      int index = this.getAttributeIndex(namespaceURI, localName);
      return index == -1 ? null : this.getAttribute(index);
   }

   public Node item(int index) {
      return this.getAttribute(index);
   }

   public Node removeNamedItem(String name) {
      int index = this.getAttributeIndex(name);
      if (index == -1) {
         return null;
      } else {
         Attr a = (Attr)this.getAttribute(index).cloneNode(false);
         this.removeAttribute(index);
         return a;
      }
   }

   public Node removeNamedItemNS(String namespaceURI, String localName) {
      int index = this.getAttributeIndex(namespaceURI, localName);
      if (index == -1) {
         return null;
      } else {
         Attr a = (Attr)this.getAttribute(index).cloneNode(false);
         this.removeAttribute(index);
         return a;
      }
   }

   public Node setNamedItem(Node arg) {
      AttributeReference oldAtt = (AttributeReference)this.getNamedItemNS(arg.getNamespaceURI(), arg.getLocalName());
      Attr newAtt = (Attr)arg;
      if (oldAtt == null) {
         int index = this.addAttribute(newAtt.getNamespaceURI(), newAtt.getLocalName(), newAtt.getPrefix(), newAtt.getValue());
         return this.getAttribute(index);
      } else {
         this.setAttribute(oldAtt.getIndex(), newAtt.getNamespaceURI(), newAtt.getLocalName(), newAtt.getPrefix(), newAtt.getValue());
         return oldAtt;
      }
   }

   public Node setNamedItemNS(Node arg) {
      return this.setNamedItem(arg);
   }

   public String toString() {
      StringBuffer b = new StringBuffer();
      if (this.length > 0) {
         b.append(" ");
      }

      for(int i = 0; i < this.length; ++i) {
         String ns = this.getNamespaceURI(i);
         String p = this.getPrefix(i);
         if (ns != null) {
            b.append("['" + this.getNamespaceURI(i) + "']:");
         }

         if (p != null) {
            b.append(this.getPrefix(i) + ":");
         }

         b.append(this.getLocalName(i) + "='" + this.getValue(i) + "' ");
      }

      return b.toString();
   }

   private void setAttributeCapacity(int len) {
      this.numAttr = 0;
      if (len >= this.attributes.length) {
         this.attributes = new int[len];
      }
   }

   private void setNamespaceCapacity(int len) {
      this.numNS = 0;
      if (len >= this.namespaces.length) {
         this.namespaces = new int[len];
      }
   }

   protected void initializeAttributesAndNamespaces() {
      this.setAttributeCapacity(this.getLength());
      this.setNamespaceCapacity(this.getLength());

      for(int i = 0; i < this.getLength(); ++i) {
         if ("xmlns".equals(this.getPrefix(i))) {
            this.addNamespace(i);
         } else if ("xmlns".equals(this.getLocalName(i))) {
            this.addNamespace(i);
         } else {
            this.addAttribute(i);
         }
      }

      this.dirty = false;
   }

   private void addNamespace(int index) {
      this.namespaces[this.numNS] = index;
      ++this.numNS;
   }

   private void addAttribute(int index) {
      this.attributes[this.numAttr] = index;
      ++this.numAttr;
   }

   public Iterator getNamespacePrefixes() {
      if (this.dirty) {
         this.initializeAttributesAndNamespaces();
      }

      return (Iterator)(this.numNS == 0 ? Iterators.EMPTY_ITERATOR : new PrefixIterator(this, this.namespaces, this.numNS));
   }

   public Iterator getAttributeNames() {
      if (this.dirty) {
         this.initializeAttributesAndNamespaces();
      }

      return (Iterator)(this.numAttr == 0 ? Iterators.EMPTY_ITERATOR : new NameIterator(this, this.attributes, this.numAttr));
   }

   public static void main(String[] args) throws Exception {
      AttributeMap m = new AttributeMap(5);
      m.setAttribute(0, "a_uri", "a", "pa", "apple");
      m.setAttribute(1, "b_uri", "b", "pb", "banana");
      m.setAttribute(2, "c_uri", "c", "pc", "orange");
      m.setAttribute(3, "d_uri", "d", "pd", "dunce");
      m.setAttribute(4, "e_uri", "e", "pd", "eat");
      System.out.println(m);
      int n = m.length();

      for(int i = 0; i < n; ++i) {
         m.removeAttribute(0);
         System.out.println("--------------" + m.length() + "-----------");
         System.out.println(m);
      }

      m.addAttribute("a_uri", "a", "pa", "apple");
      m.addAttribute("b_uri", "b", "pb", "banana");
      m.addAttribute("c_uri", "c", "pc", "orange");
      m.addAttribute("d_uri", "d", "pd", "dunce");
      m.addAttribute("e_uri", "e", "pd", "eat");
      System.out.println(m);
   }

   public class PrefixIterator implements Iterator {
      private AttributeMap map;
      private int[] data;
      private int size;
      private int current;

      public PrefixIterator(AttributeMap m, int[] d, int s) {
         this.map = m;
         this.data = d;
         this.size = s;
         this.current = 0;
      }

      public Object next() {
         Object o = this.map.getPrefix(this.data[this.current]);
         ++this.current;
         return o;
      }

      public void remove() {
         throw new UnsupportedOperationException("remove() not supported");
      }

      public boolean hasNext() {
         return this.current < this.size;
      }
   }

   public class NameIterator implements Iterator {
      private AttributeMap map;
      private int[] data;
      private int size;
      private int current;

      public NameIterator(AttributeMap m, int[] d, int s) {
         this.map = m;
         this.data = d;
         this.size = s;
         this.current = 0;
      }

      public Object next() {
         Object o = this.map.item(this.data[this.current]);
         ++this.current;
         return o;
      }

      public void remove() {
         throw new UnsupportedOperationException("remove() not supported");
      }

      public boolean hasNext() {
         return this.current < this.size;
      }
   }
}
