package weblogic.application.descriptor;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import weblogic.descriptor.DescriptorException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.Debug;
import weblogic.utils.StringUtils;

public class ReaderEvent implements Munger.ReaderEventInfo {
   private static boolean debug = Debug.getCategory("weblogic.descriptor").isEnabled();
   private String localName;
   private boolean isKey;
   private boolean isKeyComponent;
   private char[] characters;
   private ReaderEvent parent;
   private boolean discard;
   private Stack children;
   private BasicMunger munger;
   private Location location;
   private ReaderEvent beanEvent;
   private AbstractDescriptorBean bean;
   private SchemaHelper helper;
   private int eventType;
   private int attributeCount;
   private String[] attributeNames;
   private String[] attributeValues;
   private String[] attributeUris;
   private String[] attributePrefixes;
   private String charEncodingScheme;
   private String inputEncoding;
   private int namespaceCount;
   private int operation;

   public ReaderEvent(int type, Object data, Location loc, BasicMunger munger) {
      this.isKey = false;
      this.isKeyComponent = false;
      this.discard = false;
      this.children = new Stack();
      this.operation = -1;
      this.eventType = type;
      if (data != null) {
         if (data instanceof String) {
            this.localName = (String)data;
         } else {
            this.characters = (char[])((char[])data);
         }
      }

      this.munger = munger;
      this.location = loc != null ? loc : new Location() {
         public int getLineNumber() {
            return 0;
         }

         public int getColumnNumber() {
            return 0;
         }

         public int getCharacterOffset() {
            return 0;
         }

         public String getPublicId() {
            return "Start of document";
         }

         public String getSystemId() {
            return "Start of document";
         }
      };
   }

   private ReaderEvent(String localName, ReaderEvent parent, BasicMunger munger, Location location) {
      this.isKey = false;
      this.isKeyComponent = false;
      this.discard = false;
      this.children = new Stack();
      this.operation = -1;
      this.localName = localName;
      this.parent = parent;
      this.munger = munger;
      this.location = location;
   }

   public ReaderEvent(String localName, ReaderEvent parent, BasicMunger munger, Location location, ReaderEvent beanEvent) {
      this(localName, parent, munger, location);
      this.beanEvent = beanEvent;
      if (localName != null && beanEvent != null && beanEvent.getBean() != null) {
         this.isKey = beanEvent.getBean()._isPropertyAKey(this);
         if (!this.isKey) {
            SchemaHelper helper = beanEvent.getBean()._getSchemaHelper2();
            this.isKeyComponent = helper.isKeyComponent(helper.getPropertyIndex(this.getElementName()));
         }
      }

   }

   public ReaderEvent(StringBuffer xpath, ReaderEvent parent, String value, int operation, BasicMunger munger, Location location) {
      this.isKey = false;
      this.isKeyComponent = false;
      this.discard = false;
      this.children = new Stack();
      this.operation = -1;
      this.parent = parent;
      this.munger = munger;
      this.location = location;
      this.eventType = 1;
      this.consumeElement(xpath);
      if (xpath.length() > 0 && xpath.charAt(0) == '/') {
         this.children.push(new ReaderEvent(xpath, this, value, operation, munger, location));
      } else {
         if (debug) {
            System.out.println("Adding " + value + " to " + this.localName);
         }

         this.setOperation(operation);
         if (value == null) {
            return;
         }

         value = value.trim();
         if (value.indexOf("\",\"") > 0) {
            String[] values = StringUtils.splitCompletely(value, ",", false);
            this.characters = values[values.length - 1].substring(1, values[values.length - 1].length() - 1).toCharArray();

            for(int i = 0; i < values.length - 1; ++i) {
               if (debug) {
                  System.out.println("xpath = " + xpath + ", val = " + values[i]);
               }

               StringBuffer tmpXpath = new StringBuffer("/" + this.localName);
               String tmpVal = values[i].substring(1, values[i].length() - 1);
               parent.children.push(new ReaderEvent(tmpXpath, parent, tmpVal, operation, munger, location));
            }
         } else {
            if (value.startsWith("\"")) {
               value = value.substring(1, value.length() - 1);
            }

            this.characters = value.toCharArray();
         }
      }

   }

   public String getLocalName() {
      return this.localName;
   }

   public boolean isKey() {
      return this.isKey;
   }

   public boolean isKeyComponent() {
      return this.isKeyComponent;
   }

   public boolean isKeyAnAttribute() {
      if (this.getAttributeCount() == 0) {
         return false;
      } else {
         for(int i = 0; i < this.getAttributeCount(); ++i) {
            int propIndex = this.helper.getPropertyIndex(this.getAttributeLocalName(i));
            if (this.helper.isAttribute(propIndex)) {
               return true;
            }
         }

         return false;
      }
   }

   public String getPath() {
      String path = this.localName;
      String parentPath;
      if (this.isKey() || this.isKeyComponent()) {
         if (this.attributeValues != null) {
            parentPath = this.attributeValues[0];
         } else {
            parentPath = this.getTrimmedTextCharacters();
         }

         path = path + "/" + parentPath;
      }

      if (this.parent != null) {
         parentPath = this.parent.getPath();
         if (parentPath != null) {
            path = parentPath + "/" + path;
         }
      }

      return path;
   }

   public char[] getCharacters() {
      return this.characters;
   }

   public String getCharactersAsString() {
      return this.characters == null ? null : new String(this.characters);
   }

   public void setCharacters(char[] c) {
      if (c != null) {
         this.characters = new char[c.length];
         System.arraycopy(c, 0, this.characters, 0, c.length);
      }
   }

   public ReaderEvent getParent() {
      return this.munger.getParentReaderEvent(this);
   }

   public ReaderEvent getParentReaderEvent() {
      return this.parent;
   }

   public boolean isDiscarded() {
      return this.discard;
   }

   public void setDiscard() {
      this.discard = true;
   }

   public Stack getChildren() {
      return this.children;
   }

   public BasicMunger getBasicMunger() {
      return this.munger;
   }

   protected void setBasicMunger(BasicMunger munger) {
      this.munger = munger;
   }

   public Location getLocation() {
      return this.location;
   }

   public void setLocation(Location l) {
      this.location = l;
   }

   public ReaderEvent getBeanEvent() {
      return this.beanEvent;
   }

   AbstractDescriptorBean getBean() {
      return this.bean;
   }

   void setBean(AbstractDescriptorBean bean) {
      this.bean = bean;
   }

   public int getEventType() {
      return this.eventType;
   }

   public int getAttributeCount() {
      return this.attributeCount;
   }

   public void setAttributeCount(int c) {
      this.attributeCount = c;
      if (c > 0) {
         this.ensureSpaceAvail(c);
      }

   }

   public String getAttributeLocalName(int index) {
      if (index >= 0 && index < this.attributeCount) {
         return this.attributeNames[index];
      } else {
         throw new IndexOutOfBoundsException("attribute position must be 0.." + (this.attributeCount - 1) + " and not " + index);
      }
   }

   public void setAttributeLocalName(String s, int index) {
      if (s.equals("name")) {
         this.isKey = true;
      }

      this.attributeNames[index] = s;
   }

   public String getAttributeNamespace(int index) {
      if (index >= 0 && index < this.attributeCount) {
         return this.attributeUris[index];
      } else {
         throw new IndexOutOfBoundsException("attribute position must be 0.." + (this.attributeCount - 1) + " and not " + index);
      }
   }

   public void setAttributeNamespace(String s, int index) {
      this.attributeUris[index] = s;
   }

   public String getAttributePrefix(int index) {
      if (index >= 0 && index < this.attributeCount) {
         return this.attributePrefixes[index];
      } else {
         throw new IndexOutOfBoundsException("attribute position must be 0.." + (this.attributeCount - 1) + " and not " + index);
      }
   }

   public void setAttributePrefix(String prefix, int index) {
      this.attributePrefixes[index] = prefix;
   }

   public String getAttributeType(int index) {
      return "CDATA";
   }

   public boolean isAttributeSpecified(int index) {
      return false;
   }

   public String getAttributeValue(int index) {
      if (index >= 0 && index < this.attributeCount) {
         return this.attributeValues[index];
      } else {
         throw new IndexOutOfBoundsException("attribute position must be 0.." + (this.attributeCount - 1) + " and not " + index);
      }
   }

   public void setAttributeValue(String s, int index) {
      this.attributeValues[index] = s;
   }

   public String getAttributeValue(String namespace, String name) {
      int i;
      if (namespace != null) {
         for(i = 0; i < this.attributeCount; ++i) {
            if (namespace.equals(this.attributeUris[i]) && name.equals(this.attributeNames[i])) {
               return this.attributeValues[i];
            }
         }
      } else {
         for(i = 0; i < this.attributeCount; ++i) {
            if (name.equals(this.attributeNames[i])) {
               return this.attributeValues[i];
            }
         }
      }

      return null;
   }

   public void setAttributeValue(String val, String namespace, String name) {
      if (val != null && this.attributeCount != 0) {
         int i;
         if (namespace != null) {
            for(i = 0; i < this.attributeCount; ++i) {
               if (namespace.equals(this.attributeUris[i]) && name.equals(this.attributeNames[i]) && val.equals(this.attributeValues[i])) {
                  return;
               }
            }
         } else {
            for(i = 0; i < this.attributeCount; ++i) {
               if (name.equals(this.attributeNames[i]) && val.equals(this.attributeValues[i])) {
                  return;
               }
            }
         }

         if (namespace != null) {
            this.attributeUris[this.attributeCount - 1] = namespace;
         }

         this.attributeNames[this.attributeCount - 1] = name;
         this.attributeValues[this.attributeCount - 1] = val;
      }
   }

   public QName getAttributeName(int index) {
      return new QName(this.getAttributeNamespace(index), this.getAttributeLocalName(index), this.getAttributePrefix(index));
   }

   public String getCharacterEncodingScheme() {
      return this.charEncodingScheme;
   }

   public void setCharacterEncodingScheme(String s) {
      this.charEncodingScheme = s;
   }

   public int getNamespaceCount() {
      return this.namespaceCount;
   }

   public void setNamespaceCount(int c) {
      this.namespaceCount = c;
   }

   public String getElementText() {
      return null;
   }

   public void setElementText(String s) {
   }

   public String getEncoding() {
      return this.inputEncoding;
   }

   public void setEncoding(String s) {
      this.inputEncoding = s;
   }

   private void ensureSpaceAvail(int size) {
      int curSize = this.attributeValues == null ? 0 : this.attributeValues.length;
      if (size > curSize) {
         int newSize = size > 10 ? size * 2 : 10;
         if (this.attributeValues == null) {
            this.attributeNames = new String[newSize];
            this.attributeValues = new String[newSize];
            this.attributeUris = new String[newSize];
            this.attributePrefixes = new String[newSize];
            return;
         }

         String[] s = null;
         s = new String[newSize];
         System.arraycopy(this.attributeNames, 0, s, 0, curSize);
         this.attributeNames = s;
         s = new String[newSize];
         System.arraycopy(this.attributePrefixes, 0, s, 0, curSize);
         this.attributePrefixes = s;
         s = new String[newSize];
         System.arraycopy(this.attributeUris, 0, s, 0, curSize);
         this.attributeUris = s;
         s = new String[newSize];
         System.arraycopy(this.attributeValues, 0, s, 0, curSize);
         this.attributeValues = s;
      }

   }

   void consumeElement(StringBuffer xpath) {
      int start = xpath.indexOf("/");
      if (start == -1) {
         throw new AssertionError("Invalid xpath-- [" + xpath + "] -- does not start with a \"/\"");
      } else {
         int end = xpath.indexOf("/", start + 1);
         if (debug) {
            System.out.println("... starting xpath = " + xpath + ", start = " + start + ", end = " + end);
         }

         if (end == -1) {
            end = xpath.length();
         }

         this.localName = xpath.substring(start + 1, end);
         int attrStart = this.localName.indexOf("[");
         int j;
         if (attrStart > -1) {
            j = xpath.indexOf("]");
            if (end < j) {
               end = xpath.indexOf("/", j);
               this.localName = xpath.substring(start + 1, end);
            }

            StringBuffer attrs = new StringBuffer(this.localName.substring(attrStart));
            attrs = attrs.deleteCharAt(0).deleteCharAt(attrs.length() - 1);
            this.localName = this.localName.substring(0, attrStart);
            int many = attrs.indexOf(",");
            if (many > -1) {
               String[] vals = StringUtils.splitCompletely(attrs.toString(), ",", false);

               for(int i = 0; i < vals.length; ++i) {
                  this.parseAttribute(vals[i]);
               }
            } else {
               this.parseAttribute(attrs.toString());
               this.isKey = true;
            }
         }

         xpath.delete(start, end);
         if (xpath.length() > 0 && Character.isWhitespace(xpath.charAt(0))) {
            throw new AssertionError("Whitespace not allowed: [" + xpath + "]");
         } else {
            if (xpath.length() > 2 && xpath.charAt(0) == '/' && xpath.charAt(1) == '[') {
               j = xpath.indexOf("]");
               if (j == -1) {
                  throw new AssertionError("Invalid key definition-- [" + xpath + "]");
               }

               String pairStr = xpath.substring(2, j);
               if (pairStr.indexOf(",") > -1) {
                  String[] pairs = StringUtils.splitCompletely(pairStr, ",", false);

                  for(int count = 0; count < pairs.length; ++count) {
                     ReaderEvent childEvent = this.parseChild(pairs[count]);
                     childEvent.isKeyComponent = true;
                     this.children.add(childEvent);
                  }
               } else {
                  ReaderEvent childEvent = this.parseChild(pairStr);
                  childEvent.isKey = true;
                  this.children.add(childEvent);
               }

               xpath.delete(0, j + 1);
               if (debug) {
                  System.out.println(".. resulting xpath = " + xpath);
               }
            }

         }
      }
   }

   private ReaderEvent parseChild(String valuePair) {
      String[] tokens = StringUtils.splitCompletely(valuePair, "=", false);
      if (tokens.length != 2) {
         throw new AssertionError("Keys must be defined as name value pairs, eg, name=\"value\" -- [" + valuePair + "]");
      } else {
         String cName = tokens[0];
         String val = tokens[1];
         if (val.startsWith("\"") && val.charAt(val.length() - 1) == '"') {
            val = val.substring(1, val.length() - 1);
            ReaderEvent c = new ReaderEvent(cName, this, this.munger, this.location);
            c.eventType = 1;
            c.characters = val.toCharArray();
            return c;
         } else {
            throw new AssertionError("Key values must be quoted strings: [" + val + "]");
         }
      }
   }

   private void parseAttribute(String valuePair) {
      String[] tokens = StringUtils.splitCompletely(valuePair, "=", false);
      if (tokens.length != 2) {
         throw new AssertionError("Attributes must be defined as name value pairs, eg, name=\"value\" -- [" + valuePair + "]");
      } else {
         String name = tokens[0];
         String value = tokens[1];
         if (value.startsWith("\"") && value.charAt(value.length() - 1) == '"') {
            value = value.substring(1, value.length() - 1);
            this.setAttributeCount(this.getAttributeCount() + 1);
            this.setAttributeValue(value, (String)null, name);
         } else {
            throw new AssertionError("Attributes must be defined as name value pairs, eg, name=\"value\" -- [" + valuePair + "]");
         }
      }
   }

   public void toXML(PrintStream out) {
      out.print("<");
      out.print(this.getLocalName());
      if (this.getAttributeCount() > 0) {
         for(int i = 0; i < this.getAttributeCount(); ++i) {
            out.print(" " + this.getAttributeLocalName(i) + " = " + this.getAttributeValue(i));
         }
      }

      SchemaHelper h = null;
      if (this.helper == null && this.getBean() != null) {
         h = this.getBean()._getSchemaHelper2();
      } else {
         h = this.helper;
      }

      if (h == null) {
         out.print(", helper is not set!!!");
      }

      out.print(">");
      if (this.characters != null) {
         out.print(new String(this.characters));
      }

      if (this.children != null) {
         Iterator i = this.children.iterator();

         while(i.hasNext()) {
            ReaderEvent v = (ReaderEvent)i.next();
            if (!v.isDiscarded()) {
               v.toXML(out);
            }
         }
      }

      out.print("</");
      out.print(this.getLocalName());
      out.print(">\n");
   }

   public void discard() {
      this.discard = true;
      if (this.children != null) {
         Iterator i = this.children.iterator();

         while(i.hasNext()) {
            ReaderEvent child = (ReaderEvent)i.next();
            child.discard();
         }
      }

   }

   public void fixParents(ReaderEvent p) {
      this.parent = p;
      if (this.children != null) {
         Iterator i = this.children.iterator();

         while(i.hasNext()) {
            ReaderEvent child = (ReaderEvent)i.next();
            child.fixParents(this);
         }

      }
   }

   public String getElementName() {
      return this.localName;
   }

   public Munger.ReaderEventInfo getParentReaderInfo() {
      return this.parent;
   }

   public boolean compareXpaths(String xpath) {
      if (xpath.length() == this.localName.length()) {
         return xpath.equals(this.localName);
      } else {
         int slash = xpath.lastIndexOf("/");
         if (slash == -1) {
            return false;
         } else {
            String subPath = xpath.substring(slash + 1);
            return subPath.length() == this.localName.length() ? subPath.equals(this.localName) : false;
         }
      }
   }

   public void validate(SchemaHelper h) throws DescriptorException {
      this.helper = h;
      Iterator i = this.children.iterator();

      while(i.hasNext()) {
         ReaderEvent v = (ReaderEvent)i.next();
         int propIndex = h.getPropertyIndex(v.getElementName());
         if (propIndex == -1) {
            throw new DescriptorException("plan xpath defines an element [" + v.getPath() + "] that is not valid!");
         }

         SchemaHelper childHelper = h.getSchemaHelper(propIndex);
         if (childHelper != null) {
            v.validate(childHelper);
         } else {
            v.helper = h;
         }
      }

   }

   public SchemaHelper getSchemaHelper() {
      return this.helper;
   }

   public void toQueuedEvents(ArrayList events) {
      ReaderEvent qe = this.getQueuedEvent(1, this.getLocalName(), this.getLocation());
      qe.beanEvent = this.beanEvent;
      qe.bean = this.bean;
      qe.helper = this.helper;
      events.add(qe);
      if (this.characters != null) {
         events.add(this.getQueuedEvent(4, this.getCharacters(), this.getLocation()));
      }

      if (this.children != null) {
         Iterator i = this.children.iterator();

         while(i.hasNext()) {
            ReaderEvent v = (ReaderEvent)i.next();
            if (!v.isDiscarded()) {
               v.toQueuedEvents(events);
            }
         }
      }

      if (this.attributeCount > 0) {
         qe.attributeCount = this.attributeCount;
         qe.attributeNames = this.attributeNames;
         qe.attributeValues = this.attributeValues;
         qe.attributeUris = this.attributeUris;
         qe.attributePrefixes = this.attributePrefixes;
      }

      events.add(this.getQueuedEvent(2, this.getLocalName(), this.getLocation()));
   }

   protected ReaderEvent getQueuedEvent(int type, Object data, Location l) {
      return new ReaderEvent(type, data, l, this.munger);
   }

   public void setOperation(int operation) {
      this.operation = operation;
   }

   public int getOperation() {
      return this.operation;
   }

   public void removeNamedChildren(String elementName) {
      Stack childrenStack = this.getChildren();
      int index = 0;

      while(index < childrenStack.size()) {
         ReaderEvent child = (ReaderEvent)childrenStack.elementAt(index);
         if (child.getLocalName().equals(elementName)) {
            childrenStack.removeElementAt(index);
         } else {
            ++index;
         }
      }

   }

   public Object getBeanKey(SchemaHelper helper) {
      if (helper.hasKey()) {
         for(int count = 0; count < this.getChildren().size(); ++count) {
            ReaderEvent child = (ReaderEvent)this.getChildren().elementAt(count);
            if (helper.isKey(helper.getPropertyIndex(child.getElementName()))) {
               return child.getTrimmedTextCharacters();
            }
         }
      }

      return null;
   }

   public boolean isBeanKey(SchemaHelper parentHelper) {
      return parentHelper.hasKey() ? parentHelper.isKey(parentHelper.getPropertyIndex(this.getElementName())) : parentHelper.isKeyChoice(parentHelper.getPropertyIndex(this.getElementName()));
   }

   public boolean hasBeanCompositeKey() {
      return this.getBean() == null ? false : this.getBean()._getSchemaHelper2().getKeyElementNames().length > 1;
   }

   public String getBeanCompositeKey(SchemaHelper helper) {
      String[] keyElementNames = helper.getKeyElementNames();
      StringBuffer compositeKey = new StringBuffer("[");

      for(int count = 0; count < keyElementNames.length; ++count) {
         String elementName = keyElementNames[count];
         compositeKey.append("[");

         for(int index = 0; index < this.getChildren().size(); ++index) {
            ReaderEvent child = (ReaderEvent)this.getChildren().elementAt(index);
            if (elementName.equals(child.getLocalName())) {
               compositeKey.append("\"");
               String keyValue = child.getTrimmedTextCharacters();
               if (keyValue != null) {
                  compositeKey.append(keyValue);
               }

               compositeKey.append("\",");
            }
         }

         if (compositeKey.charAt(compositeKey.length() - 1) == ',') {
            compositeKey.deleteCharAt(compositeKey.length() - 1);
         }

         compositeKey.append("]");
         if (count < keyElementNames.length - 1) {
            compositeKey.append(",");
         }
      }

      compositeKey.append("]");
      return compositeKey.toString();
   }

   public String getBeanCompositeKey() {
      return this.getBeanCompositeKey(this.getBean()._getSchemaHelper2());
   }

   public boolean isSingleton() {
      AbstractDescriptorBean bean = this.munger.getCurrentOrEventBean(this);
      SchemaHelper helper = null;
      if (bean != null) {
         helper = bean._getSchemaHelper2();
      } else {
         helper = this.getSchemaHelper();
      }

      if (helper != null && !helper.isArray(helper.getPropertyIndex(this.getElementName()))) {
         return true;
      } else {
         return bean == null ? false : bean._isPropertyASingleton(this);
      }
   }

   public boolean isAdditive() {
      AbstractDescriptorBean bean = this.munger.getCurrentOrEventBean(this);
      return bean == null ? false : bean._isPropertyAdditive(this);
   }

   public static boolean compareKeys(Object key1, Object key2) {
      if (key1 == key2) {
         return true;
      } else {
         return key1 != null && key2 != null ? key1.equals(key2) : false;
      }
   }

   public boolean searchSubTree(ReaderEvent event) {
      Stack children = this.getChildren();

      for(int count = 0; count < children.size(); ++count) {
         ReaderEvent child = (ReaderEvent)children.elementAt(count);
         if (child == event) {
            return true;
         }

         if (child.searchSubTree(event)) {
            return true;
         }
      }

      return false;
   }

   public void replaceAndMoveChildren(ReaderEvent otherParent) {
      Stack children = this.getChildren();
      children.removeAllElements();
      Stack otherChildren = otherParent.getChildren();

      for(int count = 0; count < otherChildren.size(); ++count) {
         ReaderEvent otherChild = (ReaderEvent)otherChildren.elementAt(count);
         otherChild.fixParents(this);
         children.add(otherChild);
      }

      otherChildren.removeAllElements();
   }

   public String getTrimmedTextCharacters() {
      String text = this.getCharactersAsString();
      return text != null ? text.trim() : text;
   }
}
