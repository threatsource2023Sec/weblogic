package weblogic.xml.process;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Node {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   public static final String ELEMENT_NAME_SEPARATOR = ".";
   public static final String TEXT_NODE_TAG_NAME = "#text";
   public static final short ELEMENT_NODE = 1;
   public static final short TEXT_NODE = 2;
   protected String name;
   protected short type;
   protected String path;
   protected final Map attributes;
   protected StringBuffer value;
   protected Node parent;
   protected final List children;
   protected int level;

   public Node(String tagName) throws XMLProcessingException {
      this.attributes = new HashMap();
      this.value = new StringBuffer();
      this.children = new LinkedList();
      this.level = 0;
      this.name = tagName;
      this.path = "." + tagName + ".";
      this.parent = null;
      if (tagName.equals("#text")) {
         this.type = 2;
      } else {
         this.type = 1;
      }

   }

   protected Node(Node parentNode, String tagName) throws XMLProcessingException {
      this(tagName);
      if (parentNode != null) {
         this.parent = parentNode;
         this.path = this.parent.getPath() + tagName + ".";
         this.parent.children.add(this);
         this.level = this.parent.level + 1;
      }

   }

   public Node release() throws XMLProcessingException {
      boolean removed = true;
      if (this.parent != null) {
         removed = this.parent.children.remove(this);
      }

      return this.parent;
   }

   public void appendValue(char[] ch, int start, int length) {
      this.value.append(ch, start, length);
   }

   public void setAttribute(String attrName, String attrValue) {
      this.attributes.put(attrName, attrValue);
   }

   public Collection getAttributeNames() {
      return this.attributes.keySet();
   }

   public String getAttribute(String attrName) {
      return (String)this.attributes.get(attrName);
   }

   public String getName() {
      return this.name;
   }

   public String getPath() {
      return this.path;
   }

   public String getValue() {
      return this.value.toString().trim();
   }

   public void setValue(String val) {
      this.value = new StringBuffer(val != null ? val : "");
   }

   public int getDepth() {
      return this.level;
   }

   public boolean isText() {
      return this.type == 2;
   }

   public List getChildNodes() {
      return this.children;
   }

   public Node getParent() {
      return this.parent;
   }
}
