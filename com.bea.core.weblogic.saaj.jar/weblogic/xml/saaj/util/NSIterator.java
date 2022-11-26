package weblogic.xml.saaj.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class NSIterator implements Iterator {
   private Node context;
   private NamedNodeMap attributes;
   private int attributesLength;
   private int attributeIndex;
   private Attr next;
   private Attr last;
   private boolean traverseStack;
   private static final int XMLNS_LEN = "xmlns".length();

   protected void findContextAttributes() {
      while(this.context != null) {
         int type = this.context.getNodeType();
         if (type == 1) {
            this.attributes = this.context.getAttributes();
            this.attributesLength = this.attributes.getLength();
            this.attributeIndex = 0;
            return;
         }

         this.context = null;
      }

   }

   protected void findNext() {
      while(this.next == null && this.context != null) {
         while(this.attributeIndex < this.attributesLength) {
            Node currentAttribute = this.attributes.item(this.attributeIndex);
            String attributeName = currentAttribute.getNodeName();
            if (attributeName.startsWith("xmlns") && (attributeName.length() == XMLNS_LEN || attributeName.charAt(XMLNS_LEN) == ':')) {
               this.next = (Attr)currentAttribute;
               ++this.attributeIndex;
               return;
            }

            ++this.attributeIndex;
         }

         if (this.traverseStack) {
            this.context = this.context.getParentNode();
            this.findContextAttributes();
         } else {
            this.context = null;
         }
      }

   }

   public void remove() {
      if (this.last == null) {
         throw new IllegalStateException();
      } else {
         ((Element)this.context).removeAttributeNode(this.last);
      }
   }

   public boolean hasNext() {
      this.findNext();
      return this.next != null;
   }

   public Object next() {
      return this.getNext();
   }

   protected Attr getNext() {
      this.findNext();
      if (this.next == null) {
         throw new NoSuchElementException();
      } else {
         this.last = this.next;
         this.next = null;
         return this.last;
      }
   }

   public Attr nextNamespaceAttr() {
      return this.getNext();
   }

   public NSIterator(Node context) {
      this.attributes = null;
      this.next = null;
      this.last = null;
      this.traverseStack = true;
      this.context = context;
      this.findContextAttributes();
   }

   public NSIterator(Node context, boolean traverseStack) {
      this(context);
      this.traverseStack = traverseStack;
   }
}
