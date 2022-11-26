package weblogic.xml.domimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import weblogic.xml.domimpl.util.EmptyNodeList;

public abstract class CharacterDataImpl extends ChildNode {
   protected String data;

   public CharacterDataImpl(DocumentImpl owner_doc, String data) {
      super(owner_doc);
      this.data = data;
   }

   public NodeList getChildNodes() {
      return EmptyNodeList.getInstance();
   }

   public String getNodeValue() {
      return this.data;
   }

   public void setNodeValue(String value) {
      if (this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else {
         this.data = value;
      }
   }

   public final boolean hasChildNodes() {
      return false;
   }

   public final Node getFirstChild() {
      return null;
   }

   public final Node getLastChild() {
      return null;
   }

   public String getData() {
      return this.data;
   }

   public void setData(String value) throws DOMException {
      this.setNodeValue(value);
   }

   public int getLength() {
      return this.data.length();
   }

   public void appendData(String data) {
      if (data != null) {
         this.setNodeValue(this.data + data);
      }
   }

   public void deleteData(int offset, int count) throws DOMException {
      if (count >= 0 && offset >= 0 && offset < this.data.length()) {
         String str1 = this.data.substring(0, offset);
         String str2 = this.data.substring(offset + count);
         this.setNodeValue(str1 + str2);
      } else {
         throw new DOMException((short)1, "Invalid count or offset.");
      }
   }

   public void insertData(int offset, String data) throws DOMException {
      if (offset >= 0 && offset < this.data.length()) {
         String str1 = this.data.substring(0, offset);
         String str2 = this.data.substring(offset);
         this.setNodeValue(str1 + data + str2);
      } else {
         throw new DOMException((short)1, "Invalid offset.");
      }
   }

   public void replaceData(int offset, int count, String data) throws DOMException {
      this.deleteData(offset, count);
      this.insertData(offset, data);
   }

   public String substringData(int offset, int count) throws DOMException {
      throw new AssertionError("UNIMP");
   }
}
