package weblogic.xml.dom;

import org.w3c.dom.CDATASection;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Text;

public class TextNode extends ChildNode implements CharacterData, CDATASection, Text, Comment {
   private String dataCache;
   private StringBuffer modifiableData;

   public TextNode() {
      this.setNodeType((short)3);
   }

   public TextNode(String data) {
      this();
      this.dataCache = data;
   }

   public String getData() throws DOMException {
      return this.dataCache != null ? this.dataCache : this.modifiableData.toString();
   }

   public void setData(String data) throws DOMException {
      this.dataCache = data;
      this.modifiableData = null;
   }

   public int getLength() {
      if (this.dataCache != null) {
         return this.dataCache.length();
      } else {
         return this.modifiableData != null ? this.modifiableData.length() : 0;
      }
   }

   public String substringData(int offset, int count) throws DOMException {
      if (this.dataCache != null) {
         return this.dataCache.substring(offset, count);
      } else {
         return this.modifiableData != null ? this.modifiableData.substring(offset, count) : null;
      }
   }

   private void modifyOnWrite() {
      if (this.dataCache != null) {
         this.modifiableData = new StringBuffer(this.dataCache);
         this.dataCache = null;
      }

   }

   public void appendData(String newData) throws DOMException {
      this.modifiableData.append(newData);
   }

   public void insertData(int offset, String newData) throws DOMException {
      this.modifiableData.insert(offset, newData);
   }

   public void deleteData(int offset, int count) throws DOMException {
      this.modifiableData.delete(offset, count);
   }

   public void replaceData(int offset, int count, String data) throws DOMException {
      this.modifiableData.replace(offset, count, data);
   }

   public Text splitText(int offset) throws DOMException {
      TextNode newNode = new TextNode();
      String first = this.substringData(0, offset);
      String second = this.substringData(offset, this.getLength() - offset);
      this.setData(first);
      newNode.setData(second);
      this.getParentNode().insertBefore(newNode, this.getNextSibling());
      return newNode;
   }

   public String getNodeName() {
      switch (this.getNodeType()) {
         case 4:
            return "#cdata-section";
         case 8:
            return "#comment";
         default:
            return "#text";
      }
   }

   public String getNodeValue() {
      return this.getData();
   }

   public void setNodeValue(String value) {
      this.setData(value);
   }

   public Comment asComment() {
      this.setNodeType((short)8);
      return this;
   }

   public CDATASection asCDATA() {
      this.setNodeType((short)4);
      return this;
   }

   public void print(StringBuffer b, int tab) {
      b.append(this.toString());
   }

   public String toString() {
      String val = this.getData();
      if (val == null) {
         val = "";
      }

      switch (this.getNodeType()) {
         case 4:
            return "<![CDATA[" + val + "]]>";
         case 8:
            return "<!--" + val + "-->";
         default:
            return val;
      }
   }

   public Text replaceWholeText(String a) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public String getWholeText() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }

   public boolean isElementContentWhitespace() throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }
}
