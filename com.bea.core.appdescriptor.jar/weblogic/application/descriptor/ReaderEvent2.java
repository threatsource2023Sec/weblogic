package weblogic.application.descriptor;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamConstants;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.Debug;
import weblogic.utils.StringUtils;

public class ReaderEvent2 implements XMLStreamConstants {
   private static boolean debug = Debug.getCategory("weblogic.descriptor.merge").isEnabled();
   private static boolean test = Debug.getCategory("weblogic.descriptor.merge.test").isEnabled();
   private ReaderEventInfo info;
   private ReaderEvent2 parent;
   private Stack children = new Stack();
   private boolean childrenFromKey = false;
   SchemaHelper helper;
   private boolean discard = false;
   private ReaderEvent2 originalParent;

   public ReaderEvent2(int et, Location l) {
      this.info = new ReaderEventInfo(et, l);
      this.helper = new SchemaHelper() {
         SchemaHelper myHelper;

         public int getPropertyIndex(String propName) {
            return this.getRootSchemaHelper().getPropertyIndex(propName);
         }

         public SchemaHelper getSchemaHelper(int propIndex) {
            return this.getRootSchemaHelper().getSchemaHelper(propIndex);
         }

         public String getElementName(int propIndex) {
            return this.getRootSchemaHelper().getElementName(propIndex);
         }

         public boolean isArray(int propIndex) {
            return this.getRootSchemaHelper().isArray(propIndex);
         }

         public boolean isAttribute(int propIndex) {
            return this.getRootSchemaHelper().isAttribute(propIndex);
         }

         public String getAttributeName(int propIndex) {
            return this.getRootSchemaHelper().getAttributeName(propIndex);
         }

         public boolean isBean(int propIndex) {
            return this.getRootSchemaHelper().isBean(propIndex);
         }

         public boolean isConfigurable(int propIndex) {
            return this.getRootSchemaHelper().isConfigurable(propIndex);
         }

         public boolean isKey(int propIndex) {
            return this.getRootSchemaHelper().isKey(propIndex);
         }

         public boolean isKeyChoice(int propIndex) {
            return this.getRootSchemaHelper().isKeyChoice(propIndex);
         }

         public boolean isKeyComponent(int propIndex) {
            return this.getRootSchemaHelper().isKeyComponent(propIndex);
         }

         public boolean hasKey() {
            return this.getRootSchemaHelper().hasKey();
         }

         public String[] getKeyElementNames() {
            return this.getRootSchemaHelper().getKeyElementNames();
         }

         public String getRootElementName() {
            return this.getRootSchemaHelper().getRootElementName();
         }

         public boolean isMergeRulePrependDefined(int propIndex) {
            return this.getRootSchemaHelper().isMergeRulePrependDefined(propIndex);
         }

         public boolean isMergeRuleIgnoreSourceDefined(int propIndex) {
            return this.getRootSchemaHelper().isMergeRuleIgnoreSourceDefined(propIndex);
         }

         public boolean isMergeRuleIgnoreTargetDefined(int propIndex) {
            return this.getRootSchemaHelper().isMergeRuleIgnoreTargetDefined(propIndex);
         }

         private SchemaHelper getRootSchemaHelper() {
            if (this.myHelper == null) {
               Iterator i = ReaderEvent2.this.children.iterator();

               while(i.hasNext()) {
                  ReaderEvent2 v = (ReaderEvent2)i.next();
                  if (v.helper != null && v.isStartElement()) {
                     this.myHelper = v.helper;
                     break;
                  }
               }
            }

            if (this.myHelper == null) {
               throw new MissingRootElementException();
            } else {
               return this.myHelper;
            }
         }
      };
   }

   public ReaderEvent2(int et, String elementName, ReaderEvent2 parent, Location l) {
      this.info = new ReaderEventInfo(et, elementName, l);
      this.parent = parent;
      if (parent.info.namespaces != null) {
         this.info.getNamespaces().copy(parent.info.namespaces);
      }

   }

   public ReaderEvent2(StringBuffer xpath, ReaderEvent2 parent, SchemaHelper h, String value, Location location) {
      this.parent = parent;
      this.helper = h;
      this.consumeElements(xpath, value, location);
   }

   public ReaderEvent2 getParent() {
      return this.parent;
   }

   public void setParent(ReaderEvent2 parent) {
      this.parent = parent;
   }

   public ReaderEvent2 getOriginalParent() {
      return this.originalParent;
   }

   public void setOriginalParent(ReaderEvent2 parent) {
      this.originalParent = parent;
   }

   public ReaderEventInfo getReaderEventInfo() {
      return this.info;
   }

   public int getEventType() {
      return this.info.getEventType();
   }

   public String getElementName() {
      return this.info.getElementName();
   }

   public void setElementName(String e) {
      this.info.setElementName(e);
   }

   public char[] getCharacters() {
      return this.info.getCharacters();
   }

   public void setCharacters(char[] c) {
      this.info.setCharacters(c);
   }

   public String getTextCharacters() {
      return this.info.getCharactersAsString();
   }

   private String getTrimmedTextCharacters() {
      String text = this.getTextCharacters();
      return text != null ? text.trim() : text;
   }

   public Location getLocation() {
      return this.info.getLocation();
   }

   public String toString() {
      if (this.info != null) {
         String s = super.toString();
         s = s.substring(s.lastIndexOf("@"));
         return this.info.getElementName() != null ? this.info.getElementName() + s : "ReaderEvent2-type = " + this.info.getEventType() + s;
      } else {
         return super.toString();
      }
   }

   public boolean isDiscarded() {
      return this.discard;
   }

   public void setDiscard() {
      this.discard = true;
   }

   public void setDiscard(boolean d) {
      this.discard = d;
   }

   public Stack getChildren() {
      return this.children;
   }

   public void addChild(ReaderEvent2 c) {
      this.children.add(c);
   }

   public boolean isChildrenFromKey() {
      return this.childrenFromKey;
   }

   public void setChildrenFromKey(boolean isChildrenFromKey) {
      this.childrenFromKey = isChildrenFromKey;
   }

   public void validate(SchemaHelper h) {
      this.helper = h;
      Iterator i = this.children.iterator();

      while(i.hasNext()) {
         ReaderEvent2 v = (ReaderEvent2)i.next();
         int propIndex = h.getPropertyIndex(v.getReaderEventInfo().getElementName());
         if (propIndex == -1) {
            throw new AssertionError("plan xpath defines an element [" + v.getReaderEventInfo().getElementName() + "] that is not valid!");
         }

         SchemaHelper childHelper = h.getSchemaHelper(propIndex);
         if (childHelper != null) {
            v.validate(childHelper);
         } else {
            v.helper = h;
         }
      }

   }

   void consumeElements(StringBuffer xpath, String value, Location location) {
      this.consumeElement(xpath, location);
      int propIndex = this.helper.getPropertyIndex(this.getReaderEventInfo().getElementName());
      if (propIndex != -1) {
         this.helper = this.helper.getSchemaHelper(propIndex);
      }

      if (xpath.length() == 1 && xpath.charAt(0) == '/') {
         if (this.childrenFromKey && this.getChildren().size() == 1) {
            ReaderEvent2 child = (ReaderEvent2)this.getChildren().peek();
            ReaderEventInfo childInfo = child.info;
            if (childInfo.isKeyCharacters() && value != null && !Arrays.equals(value.toCharArray(), childInfo.getCharacters())) {
               childInfo.setKeyOverride(true);
               childInfo.setKeyOverrideCharacters(value.toCharArray());
            }
         }
      } else if (xpath.length() > 0 && xpath.charAt(0) == '/') {
         this.consumeChildren(xpath, value, location);
      } else {
         this.consumeText(xpath, value, location);
      }

   }

   void consumeChildren(StringBuffer xpath, String value, Location location) {
      this.getChildren().push(new ReaderEvent2(xpath, this, this.helper, value, location));
   }

   void consumeText(StringBuffer xpath, String value, Location location) {
      if (debug) {
         System.out.println("Adding " + value + " to " + this.info.getElementName());
      }

      if (value != null) {
         value = value.trim();
         if (value.length() != 0 || !this.getParent().isArray(this.getPropertyIndex())) {
            if (value.indexOf("\",\"") > 0) {
               String[] values = StringUtils.splitCompletely(value, ",", false);
               this.info.setCharacters(values[values.length - 1].substring(1, values[values.length - 1].length() - 1).toCharArray());

               for(int i = 0; i < values.length - 1; ++i) {
                  if (debug) {
                     System.out.println("xpath = " + xpath + ", val = " + values[i]);
                  }

                  StringBuffer tmpXpath = new StringBuffer("/" + this.info.getElementName());
                  this.getParent().getChildren().push(new ReaderEvent2(tmpXpath, this.parent, this.helper, values[i], location));
               }
            } else {
               if (value.startsWith("\"")) {
                  value = value.substring(1, value.length() - 1);
               }

               this.info.setCharacters(value.toCharArray());
            }

         }
      }
   }

   void consumeElement(StringBuffer xpath, Location location) {
      int start = xpath.indexOf("/");
      if (start == -1) {
         throw new AssertionError("Invalid xpath-- [" + xpath + "] -- does not start with a \"/\"");
      } else {
         int fromStart = start + 1;
         int end = xpath.indexOf("/", fromStart);
         int quote = xpath.indexOf("\"", fromStart);
         if (quote != -1 && quote < end) {
            fromStart = quote + 1;
            quote = xpath.indexOf("\"", fromStart);
            end = xpath.indexOf("/", quote);
         }

         if (debug) {
            System.out.println("... starting xpath = " + xpath + ", start = " + start + ", end = " + end);
         }

         if (end == -1) {
            end = xpath.length();
         }

         String elementName = xpath.substring(start + 1, end);
         int attrStart = elementName.indexOf("[");
         int i;
         if (attrStart == -1) {
            i = elementName.indexOf(" xmlns");
            String ns = null;
            if (i != -1) {
               ns = elementName.substring(i);
               elementName = elementName.substring(0, i);
            }

            this.info = new ReaderEventInfo(1, elementName, location);
            if (ns != null) {
               this.setNamespaces(this.info, ns);
            }
         } else {
            i = xpath.indexOf("]");
            if (end < i) {
               end = xpath.indexOf("/", i);
               elementName = xpath.substring(start + 1, end);
            }

            StringBuffer attrs = new StringBuffer(elementName.substring(attrStart));
            attrs = attrs.deleteCharAt(0).deleteCharAt(attrs.length() - 1);
            elementName = elementName.substring(0, attrStart);
            this.info = new ReaderEventInfo(1, elementName, location);
            int many = attrs.indexOf(",");
            if (many > -1) {
               String[] vals = StringUtils.splitCompletely(attrs.toString(), ",", false);
               int count = this.info.getAttributeCount();

               for(int i = 0; i < vals.length; ++i) {
                  this.parseAttribute(vals[i]);
               }
            } else {
               this.parseAttribute(attrs.toString());
            }
         }

         xpath.delete(start, end);
         if (xpath.length() > 0 && Character.isWhitespace(xpath.charAt(0))) {
            throw new AssertionError("Whitespace not allowed: [" + xpath + "]");
         } else {
            if (xpath.length() > 2 && xpath.charAt(0) == '/' && xpath.charAt(1) == '[') {
               this.childrenFromKey = true;
               i = xpath.indexOf("=");
               if (i < 3) {
                  throw new AssertionError("Key element name not found in [" + xpath + "]");
               }

               int j = xpath.indexOf("]");
               if (i == -1 || j == -1) {
                  throw new AssertionError("Invalid key definition-- [" + xpath + "]");
               }

               if (xpath.indexOf("\",") > -1) {
                  String[] pairs = StringUtils.splitCompletely(xpath.toString(), ",", false);

                  for(int count = 0; count < pairs.length; ++count) {
                     String str = null;
                     if (pairs[count].indexOf("\"]") > -1) {
                        str = pairs[count].substring(0, pairs[count].indexOf("\"]"));
                     } else {
                        str = pairs[count];
                     }

                     String[] tokens = StringUtils.splitCompletely(str, "=", false);

                     for(int tCount = 0; tCount < tokens.length; ++tCount) {
                        String eName = tokens[tCount];
                        ++tCount;
                        String val = tokens[tCount];
                        if (eName.startsWith("/")) {
                           eName = eName.substring(1);
                        }

                        if (eName.startsWith("[")) {
                           eName = eName.substring(1);
                        }

                        if (val.startsWith("\"")) {
                           val = val.substring(1);
                        }

                        int endQuote = val.indexOf("\"");
                        if (endQuote > -1) {
                           val = val.substring(0, endQuote);
                        }

                        ReaderEvent2 c = new ReaderEvent2(1, eName, this, location);
                        c.getReaderEventInfo().setCharacters(val.toCharArray());
                        c.getReaderEventInfo().setKeyCharacters(true);
                        this.getChildren().add(c);
                     }
                  }
               } else {
                  String val = xpath.substring(i + 1, j);
                  if (!val.startsWith("\"") || val.charAt(val.length() - 1) != '"') {
                     throw new AssertionError("Key values must be quoted strings: [" + val + "]");
                  }

                  val = val.substring(1, val.length() - 1);
                  String cName = xpath.substring(2, i);
                  ReaderEvent2 c = new ReaderEvent2(1, cName, this, location);
                  c.getReaderEventInfo().setCharacters(val.toCharArray());
                  c.getReaderEventInfo().setKeyCharacters(true);
                  this.getChildren().add(c);
               }

               xpath.delete(0, j + 1);
            }

            if (debug) {
               System.out.println(".. resulting xpath = " + xpath);
            }

         }
      }
   }

   void setNamespaces(ReaderEventInfo info, String ns) {
      String[] tokens = StringUtils.splitCompletely(ns, "=", false);
      if (tokens.length != 2) {
         throw new AssertionError("Namespaces must be defined as name value pairs, eg, name=\"value\" -- [" + ns + "]");
      } else {
         for(int i = 0; i < tokens.length; ++i) {
            String prefix = tokens[i];
            ++i;
            String value = tokens[i];
            int sep = prefix.indexOf(":");
            if (sep == -1) {
               prefix = null;
            } else {
               prefix = prefix.substring(sep + 1);
            }

            if (!value.startsWith("\"") || value.charAt(value.length() - 1) != '"') {
               throw new AssertionError("Namespaces must be defined as name and quoted value, eg, name=\"value\" -- [" + ns + "]");
            }

            value = value.substring(1, value.length() - 1);
            info.setNamespaceCount(info.getNamespaceCount() + 1);
            info.setNamespaceURI(prefix, value);
            info.setPrefix(prefix);
         }

      }
   }

   void parseAttribute(String valuePair) {
      String[] tokens = StringUtils.splitCompletely(valuePair, "=", false);
      if (tokens.length != 2) {
         throw new AssertionError("Attributes must be defined as name value pairs, eg, name=\"value\" -- [" + valuePair + "]");
      } else {
         String name = tokens[0];
         String value = tokens[1];
         if (value.startsWith("\"") && value.charAt(value.length() - 1) == '"') {
            value = value.substring(1, value.length() - 1);
            this.info.setAttributeCount(this.info.getAttributeCount() + 1);
            this.info.setAttributeValue(value, (String)null, name);
         } else {
            throw new AssertionError("Attributes must be defined as name value pairs, eg, name=\"value\" -- [" + valuePair + "]");
         }
      }
   }

   boolean isSimpleKey() {
      int propIndex = this.helper.getPropertyIndex(this.info.getElementName());
      if (this.helper.isKey(propIndex)) {
         return true;
      } else if (this.helper.isKeyChoice(propIndex)) {
         return true;
      } else {
         if (this.info.getAttributeCount() > 0) {
            for(int i = 0; i < this.info.getAttributeCount(); ++i) {
               propIndex = this.helper.getPropertyIndex(this.info.getAttributeLocalName(i));
               if (this.helper.isKey(propIndex)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   boolean isKeyComponent() {
      int propIndex = this.helper.getPropertyIndex(this.info.getElementName());
      return this.helper.isKeyComponent(propIndex);
   }

   boolean isKeyChoice() {
      int propIndex = this.helper.getPropertyIndex(this.info.getElementName());
      return this.helper.isKeyChoice(propIndex);
   }

   public void toXML(PrintStream out) {
      if (!this.isDiscarded()) {
         out.print("\n<");
         String s = super.toString();
         out.print(this.info.getElementName() + s.substring(s.lastIndexOf("@")));
         out.print(" " + (this.helper == null ? "null" : this.helper.toString().substring(this.helper.toString().lastIndexOf(".") + 1)));
         int i;
         if (this.info.getNamespaceCount() > 0) {
            for(i = 0; i < this.info.getNamespaceCount(); ++i) {
               out.print(" ");
               String prefix = this.info.getNamespacePrefix(i);
               if (prefix == null) {
                  prefix = "xmlns";
               } else {
                  prefix = "xmlns:" + prefix;
               }

               out.print(prefix);
               out.print("=\"");
               out.print(this.info.getNamespaceURI(i));
               out.print("\"");
            }
         }

         if (this.info.getAttributeCount() > 0) {
            for(i = 0; i < this.info.getAttributeCount(); ++i) {
               out.print(" " + this.info.getAttributeLocalName(i) + " = " + this.info.getAttributeValue(i));
            }
         }

         out.print(">");
         out.print(this.info.getCharactersAsString());
      }

      if (this.children != null) {
         Iterator i = this.children.iterator();

         while(i.hasNext()) {
            ReaderEvent2 v = (ReaderEvent2)i.next();
            v.toXML(out);
         }
      }

      if (!this.isDiscarded()) {
         out.print("</");
         out.print(this.info.getElementName());
         out.print(">\n");
      }

   }

   public void discard() {
      if (debug) {
         System.out.println("discard: " + this.getElementName());
      }

      this.discard = true;
      this.orphanChildren();
   }

   public void orphanChild(ReaderEvent2 sourceChild) {
      if (debug) {
         System.out.println("orphan: " + sourceChild.getElementName());
      }

      ReaderEvent2 sourceParent = sourceChild.getParent();
      if (sourceParent != null) {
         sourceParent.getChildren().remove(sourceChild);
         if (sourceParent.getChildren().size() == 0) {
            sourceParent.discard();
         }
      }

   }

   public void orphanChildren() {
      if (debug) {
         System.out.println("orphanChildren: " + this.getElementName());
      }

      this.getChildren().clear();
   }

   public SchemaHelper getSchemaHelper() {
      if (this.helper.getPropertyIndex(this.getElementName()) == -1) {
         return this.parent.helper != null && this.parent.helper.getPropertyIndex(this.getElementName()) == -1 ? this.helper : this.parent.helper;
      } else {
         return this.helper;
      }
   }

   public void setSchemaHelper(SchemaHelper h) {
      this.helper = h;
   }

   public boolean isRoot() {
      return this.getElementName() == null;
   }

   public boolean isBean() {
      assert this.helper != null && this.parent != null;

      return this.helper != this.parent.helper;
   }

   public boolean isBeanKey() {
      if (!this.isBean()) {
         return false;
      } else {
         SchemaHelper h = this.parent.helper;
         int propIndex = h.getPropertyIndex(this.getElementName());
         return h.isKey(propIndex) || h.isKeyChoice(propIndex) || h.isKeyComponent(propIndex);
      }
   }

   public boolean hasKey() {
      ReaderEvent2 start = this.getNextChild(-1);
      int i = this.getChildren().indexOf(start);

      for(ReaderEvent2 k = start; k != null; k = this.getChildOnIndex(i)) {
         if (k.isSimpleKey() || k.isKeyComponent() || k.isKeyChoice()) {
            return true;
         }

         ++i;
      }

      return this.helper.hasKey();
   }

   public String getKey() {
      return this.getKey(false);
   }

   public String getKey(boolean isReplace) {
      int i;
      if (this.info.getAttributeCount() > 0) {
         for(int i = 0; i < this.info.getAttributeCount(); ++i) {
            i = this.helper.getPropertyIndex(this.info.getAttributeLocalName(i));
            if (this.helper.isKey(i)) {
               return this.getXpath();
            }
         }
      }

      ReaderEvent2 start = this.getNextChild(-1);
      i = this.getChildren().indexOf(start);

      for(ReaderEvent2 k = start; k != null; k = this.getChildOnIndex(i)) {
         if (k.isSimpleKey()) {
            return k.getXpath();
         }

         if (k.isKeyChoice()) {
            return k.getXpath();
         }

         if (k.isKeyComponent()) {
            return k.getXpath(isReplace);
         }

         if (k.isBeanKey()) {
            return k.getXpath();
         }

         ++i;
      }

      return this.getXpath();
   }

   public ReaderEvent2 getMatchingKey(String otherKey) {
      if (debug) {
         System.out.println("getMatchingKey: " + otherKey);
      }

      ReaderEvent2 start = this.getNextChild(-1);
      int i = this.getChildren().indexOf(start);

      for(ReaderEvent2 k = start; k != null; k = this.getChildOnIndex(i)) {
         if (debug) {
            System.out.print("--" + k + ", hasKey ? " + k.hasKey() + ", getXpath = " + k.getXpath());
         }

         if (debug) {
            System.out.println(k.hasKey() ? ", getKey() = " + k.getKey() : "");
         }

         if (k.hasKey() && k.getKey().equals(otherKey)) {
            return k;
         }

         ++i;
      }

      if (this.hasKey()) {
         int j = this.getChildren().indexOf(start);

         for(ReaderEvent2 k = start; k != null; k = this.getChildOnIndex(j)) {
            if (debug) {
               System.out.println("---" + k + ", getXpath = " + k.getXpath());
            }

            if (k.getXpath().equals(otherKey)) {
               return k;
            }

            ++j;
         }
      }

      return null;
   }

   public ReaderEvent2 getMatchingElementName(String otherName) {
      if (debug) {
         System.out.println("getMatchingElementName");
      }

      ReaderEvent2 discardedElement = null;
      ReaderEvent2 start = this.getNextChild(-1);
      int i = this.getChildren().indexOf(start);

      for(ReaderEvent2 k = start; k != null; k = this.getChildOnIndex(i)) {
         if (debug) {
            System.out.println("--" + k + ", getXpath = " + k.getElementName());
         }

         if (k.getElementName().equals(otherName)) {
            if (!k.isDiscarded()) {
               return k;
            }

            discardedElement = k;
         }

         ++i;
      }

      return discardedElement;
   }

   public ReaderEvent2 getMatchingEventFromKey(ReaderEvent2 planEvent) {
      if (debug) {
         System.out.println("getMatchingEventFromKey");
      }

      ReaderEvent2 start = this.getNextChild(-1);
      int ii = this.getChildren().indexOf(start);

      for(ReaderEvent2 descEvent = start; descEvent != null; descEvent = this.getChildOnIndex(ii)) {
         if (debug) {
            System.out.println("--" + descEvent + ", getXpath = " + descEvent.getElementName());
         }

         if (descEvent.getElementName().equals(planEvent.getElementName())) {
            boolean allMatched = true;
            Iterator planEvents = planEvent.getChildren().iterator();

            while(planEvents.hasNext()) {
               ReaderEvent2 planChild = (ReaderEvent2)planEvents.next();
               if (debug) {
                  System.out.println("--" + planChild + ", getXpath = " + planChild.getXpath());
               }

               if (planChild.getReaderEventInfo().isKeyCharacters()) {
                  Iterator i = descEvent.getChildren().iterator();

                  while(i.hasNext()) {
                     ReaderEvent2 descChild = (ReaderEvent2)i.next();
                     if (debug) {
                        System.out.println("--" + descChild + ", getXpath = " + descChild.getXpath());
                     }

                     if (planChild.getElementName().equals(descChild.getElementName())) {
                        char[] planChildKey = planChild.getReaderEventInfo().getCharacters();
                        char[] descChildKey = descChild.getReaderEventInfo().getCharacters();
                        if (!Arrays.equals(planChildKey, descChildKey)) {
                           allMatched = false;
                        }
                     }
                  }
               }
            }

            if (allMatched) {
               return descEvent;
            }
         }

         ++ii;
      }

      return null;
   }

   public boolean isStartDoc() {
      return this.getEventType() == 7;
   }

   public boolean isStartElement() {
      return this.getEventType() == 1;
   }

   public SchemaHelper findRootSchemaHelper() {
      Iterator i = this.children.iterator();

      while(i.hasNext()) {
         ReaderEvent2 v = (ReaderEvent2)i.next();
         if (v.helper != null) {
            this.helper = v.helper;
         }
      }

      return this.helper;
   }

   public void merge(ReaderEvent2 other, int operation, boolean isPlanMerge) {
      if (debug) {
         System.out.println("merge, this = " + this + ", other = " + other);
      }

      if (this.getEventType() != 7) {
         throw new AssertionError("must start merge form a START_DOCUMENT event");
      } else {
         Iterator i = this.getChildren().iterator();

         while(i.hasNext()) {
            ReaderEvent2 target = (ReaderEvent2)i.next();
            switch (target.getEventType()) {
               case 1:
                  if (debug) {
                     System.out.println("\n target ReaderEvent to merge: ");
                     target.toXML(System.out);
                     System.out.println("----------- end ReaderEvent --------");
                  }

                  ReaderEvent2 source = null;
                  Iterator j = other.getChildren().iterator();

                  while(j.hasNext()) {
                     ReaderEvent2 s = (ReaderEvent2)j.next();
                     if (s.getEventType() == 1) {
                        source = s;
                        break;
                     }
                  }

                  if (source == null) {
                     throw new AssertionError("Could not find start element within event: " + other);
                  }

                  if (target.getElementName().equals(source.getElementName())) {
                     target.mergeBean(source, operation, isPlanMerge);
                  }

                  return;
               case 5:
               case 11:
                  break;
               default:
                  throw new AssertionError("Unknown event: " + target.getEventType());
            }
         }

      }
   }

   public void mergeBean(ReaderEvent2 source, int operation, boolean isPlanMerge) {
      if (debug) {
         System.out.println("mergeBean: target = " + this.getElementName() + ", source = " + source.getElementName());
      }

      HashSet discardedArrayNames = new HashSet();
      ReaderEvent2 start = source.getNextChild(-1);
      int i = source.getChildren().indexOf(start);

      for(ReaderEvent2 s = start; s != null; s = source.getChildOnIndex(i)) {
         if (debug) {
            System.out.println("mergeBean for source child: " + s.getElementName() + ", isBean " + s.isBean() + ", hasKey " + s.hasKey() + " prepend? " + s.isMergeRulePrependDefined());
            System.out.println("" + s.getTextCharacters());
         }

         if (s.isBean()) {
            if (s.hasKey()) {
               this.mergeBeanWithKey(s, operation, isPlanMerge);
            } else {
               this.mergeBeanWithoutKey(s, operation, isPlanMerge);
            }
         } else {
            this.mergePropertyImpl(s, operation, isPlanMerge, discardedArrayNames);
         }

         ++i;
      }

   }

   private void mergeBeanWithKey(ReaderEvent2 s, int operation, boolean isPlanMerge) {
      boolean isReplace = operation == 3;
      ReaderEvent2 t = this.getMatchingKey(s.getKey(isReplace));
      if (this.isArray(s.getPropertyIndex()) && s.isMergeRulePrependDefined()) {
         if (t != null) {
            t.mergeBean(s, operation, isPlanMerge);
            return;
         }

         if (t == null) {
            t = this.getMatchingElementName(s.getElementName());
         }

         if (t == null) {
            this.adopt(s, isPlanMerge);
         } else {
            this.adopt(s, isPlanMerge, true);
         }
      } else {
         if (t == null && isPlanMerge && isReplace) {
            return;
         }

         if (t == null) {
            this.adopt(s, isPlanMerge);
         } else if (!isPlanMerge && s.isMergeRuleIgnoreTargetDefined()) {
            t.discard();
            this.adopt(s, isPlanMerge);
         } else {
            if (!isPlanMerge && s.isMergeRuleIgnoreSourceDefined()) {
               return;
            }

            t.mergeBean(s, operation, isPlanMerge);
         }
      }

   }

   private void mergeBeanWithoutKey(ReaderEvent2 s, int operation, boolean isPlanMerge) {
      ReaderEvent2 t = null;
      if (isPlanMerge && this.isArray(s.getPropertyIndex()) && s.isChildrenFromKey() && operation == 1) {
         t = this.getMatchingEventFromKey(s);
      } else {
         t = this.getMatchingElementName(s.getElementName());
      }

      if (this.isArray(s.getPropertyIndex()) && s.isMergeRulePrependDefined()) {
         if (t == null) {
            this.adopt(s, isPlanMerge);
         } else {
            this.adopt(s, isPlanMerge, true);
         }
      } else if (t == null) {
         this.adopt(s, isPlanMerge);
      } else if (!isPlanMerge && s.isMergeRuleIgnoreTargetDefined()) {
         t.discard();
         this.adopt(s, isPlanMerge);
      } else {
         if (!isPlanMerge && s.isMergeRuleIgnoreSourceDefined()) {
            return;
         }

         if (this.isArray(s.getPropertyIndex()) && !isPlanMerge) {
            if (this.isArray(s.getPropertyIndex())) {
               this.adopt(s, isPlanMerge, true);
            } else {
               this.adopt(s, isPlanMerge);
            }
         } else {
            t.mergeBean(s, operation, isPlanMerge);
         }
      }

   }

   private void mergePropertyImpl(ReaderEvent2 s, int operation, boolean isPlanMerge, HashSet discardedArrayNames) {
      ReaderEvent2 t = this.getMatchingElementName(s.getElementName());
      int propIndex = s.getPropertyIndex();
      if (operation == 3 && this.isArray(propIndex)) {
         if (!discardedArrayNames.contains(s.getElementName())) {
            this.discardAllArrayElements(s);
            discardedArrayNames.add(s.getElementName());
         }

         operation = 1;
      }

      if (debug) {
         System.out.println("mergeProperty t is " + t);
      }

      if (t == null) {
         if (!isPlanMerge && s.isMergeRuleIgnoreTargetDefined() && this.isArray(propIndex) && !discardedArrayNames.contains(s.getElementName())) {
            discardedArrayNames.add(s.getElementName());
         }

         this.adopt(s, isPlanMerge);
      } else if (!isPlanMerge && s.isMergeRuleIgnoreTargetDefined()) {
         if (this.isArray(propIndex)) {
            if (!discardedArrayNames.contains(s.getElementName())) {
               this.discardAllArrayElements(s);
               discardedArrayNames.add(s.getElementName());
            }
         } else {
            t.discard();
         }

         this.adopt(s, isPlanMerge);
      } else {
         if (!isPlanMerge && s.isMergeRuleIgnoreSourceDefined()) {
            return;
         }

         switch (operation) {
            case -1:
            case 1:
               t.mergeProperty(s, operation, isPlanMerge);
            case 0:
            default:
               break;
            case 2:
               if (!t.isSimpleKey()) {
                  t.removeProperty(s);
               }
               break;
            case 3:
               if (s.info.isKeyOverride()) {
                  t.replacePropertyWithOverride(s);
               } else {
                  t.replaceProperty(s);
               }
         }
      }

   }

   private void discardAllArrayElements(ReaderEvent2 source) {
      Iterator i1 = this.getChildren().iterator();

      while(i1.hasNext()) {
         ReaderEvent2 t = (ReaderEvent2)i1.next();
         if (source.getElementName().equals(t.getElementName())) {
            t.discard();
         }
      }

   }

   public void adopt(ReaderEvent2 c, SchemaHelper h, boolean reorderOnAdopt) {
      if (h != null && this.getEventType() == 1) {
         this.adopt(c, false, false, reorderOnAdopt);
      } else {
         this.addChild(c);
      }

   }

   public void adopt(ReaderEvent2 c, SchemaHelper h) {
      this.adopt(c, h, true);
   }

   void adopt(ReaderEvent2 source, boolean isPlanMerge) {
      this.adopt(source, isPlanMerge, false);
   }

   void adopt(ReaderEvent2 source, boolean isPlanMerge, boolean prependArrayElements) {
      this.adopt(source, isPlanMerge, prependArrayElements, true);
   }

   void adopt(ReaderEvent2 source, boolean isPlanMerge, boolean prependArrayElements, boolean reorderOnAdopt) {
      if (debug) {
         System.out.println("adopt: " + source.getElementName() + " into parent = " + this.getElementName() + " isPlanMerge:" + isPlanMerge + " prependArrayElements:" + prependArrayElements + " reorderOnAdopt:" + reorderOnAdopt);
      }

      int propIndex = this.helper.getPropertyIndex(source.getElementName());
      ReaderEvent2 next = this.getNextChild(propIndex);
      if (next == null) {
         next = this.getPrevChild(propIndex);
      }

      if (debug) {
         System.out.println("source is " + source.getElementName() + ", next = " + next);
      }

      if (next == null) {
         this.getChildren().add(source);
         source.setOriginalParent(source.getParent());
         source.setParent(this);
      } else {
         int indx = this.getChildren().indexOf(next);
         int nextPropIndex = this.helper.getPropertyIndex(next.getElementName());
         if (debug) {
            System.out.println("indx = " + indx + " propIndex = " + propIndex + " nextPropIndex = " + nextPropIndex + " size = " + this.getChildren().size());
         }

         boolean isMatchingElementPresent = false;
         boolean isMatchingElementFromSameParent = false;
         int index;
         ReaderEvent2 xx;
         if (propIndex >= nextPropIndex) {
            if (this.helper.isArray(propIndex)) {
               for(index = 0; index < this.getChildren().size(); ++index) {
                  xx = (ReaderEvent2)this.getChildren().elementAt(index);
                  if (source.getElementName().equals(xx.getElementName())) {
                     indx = index;
                     isMatchingElementPresent = true;
                     if (debug) {
                        System.out.println("Element name: " + source.getElementName() + " indx = " + index + " matching element is true");
                     }

                     if (source.getParent() != xx.getOriginalParent()) {
                        isMatchingElementFromSameParent = false;
                     } else {
                        isMatchingElementFromSameParent = true;
                     }

                     if (prependArrayElements && source.getParent() != xx.getOriginalParent()) {
                        break;
                     }
                  }
               }
            }

            if (!prependArrayElements || !isMatchingElementPresent || isMatchingElementFromSameParent) {
               ++indx;
            }
         } else if (prependArrayElements && this.helper.isArray(propIndex)) {
            for(index = 0; index < this.getChildren().size(); ++index) {
               xx = (ReaderEvent2)this.getChildren().elementAt(index);
               if (source.getElementName().equals(xx.getElementName()) && source.getParent() != xx.getOriginalParent()) {
                  indx = index;
                  break;
               }
            }
         }

         if (indx < this.getChildren().size() && reorderOnAdopt) {
            this.getChildren().add(indx, source);
            if (debug) {
               System.out.println("Add element to children at indx " + indx);
            }
         } else {
            this.getChildren().add(source);
            if (debug) {
               System.out.println("Add element to children at end ");
            }
         }

         source.setOriginalParent(source.getParent());
         source.setParent(this);
         if (this.info.getPrefix() != null) {
            this.setPrefixOnAdopt(source, this.info.getPrefix());
         }

      }
   }

   public void orderChildren() {
      int index;
      ReaderEvent2 xx;
      int index;
      ReaderEvent2 xx;
      for(index = 0; index < this.getChildren().size(); ++index) {
         xx = (ReaderEvent2)this.getChildren().elementAt(index);
         if (xx.isDiscarded() && xx.getChildren().size() > 0) {
            this.getChildren().addAll(xx.getChildren());

            for(index = 0; index < xx.getChildren().size(); ++index) {
               xx = (ReaderEvent2)xx.getChildren().elementAt(index);
               xx.setParent(xx);
            }

            xx.getChildren().clear();
         }
      }

      for(index = this.getChildren().size() - 1; index > 1; --index) {
         xx = (ReaderEvent2)this.getChildren().elementAt(index);
         if (xx.isDiscarded()) {
            this.getChildren().remove(index);
         }
      }

      SchemaHelper h = this.helper;
      int size = this.getChildren().size();

      for(index = 0; index < this.getChildren().size(); ++index) {
         for(int i = index + 1; i < size; ++i) {
            ReaderEvent2 xx = (ReaderEvent2)this.getChildren().elementAt(index);
            int propIndex = h.getPropertyIndex(xx.getElementName());
            ReaderEvent2 yy = (ReaderEvent2)this.getChildren().elementAt(i);
            int yyPropIndex = h.getPropertyIndex(yy.getElementName());
            if (yyPropIndex != -1 && yyPropIndex < propIndex) {
               this.getChildren().removeElementAt(i);
               this.getChildren().insertElementAt(yy, index);
            }
         }
      }

      for(index = 0; index < this.getChildren().size(); ++index) {
         xx = (ReaderEvent2)this.getChildren().elementAt(index);
         xx.orderChildren();
      }

   }

   private void removeNullAttribIfValueNotNull(ReaderEvent2 source) {
      char[] value = source.getCharacters();
      if (value != null && String.valueOf(value).trim().length() != 0) {
         this.info.removeNillableAttribute();
      }
   }

   void mergeProperty(ReaderEvent2 source, int operation, boolean isPlanMerge) {
      if (debug) {
         System.out.println("mergeProperty: " + source);
      }

      int sourceIndex = source.getPropertyIndex();
      if (!this.isArray(sourceIndex)) {
         if (debug) {
            System.out.println("\nsimple property, not an array: " + source);
         }

         if (isPlanMerge) {
            this.removeNullAttribIfValueNotNull(source);
         }

         this.setCharacters(source.getCharacters());
         if (debug) {
            System.out.println(".. characters =  " + this.getTextCharacters());
         }

      } else {
         if (debug) {
            System.out.println("\narray property: " + source);
         }

         int indx = this.getParent().getChildren().indexOf(this);
         if (source.getCharacters() != null) {
            if (indx == this.getParent().getChildren().size() - 1) {
               this.getParent().getChildren().add(source);
               source.setOriginalParent(source.getParent());
               source.setParent(this.getParent());
            } else {
               ReaderEvent2 sibling = this;
               if (debug) {
                  System.out.println("evaluate, index = " + indx);
               }

               ReaderEvent2 start = this.getParent().getNextChild(-1);
               int i = this.getParent().getChildren().indexOf(start);

               ReaderEvent2 var10000;
               for(ReaderEvent2 next = start; next != null; next = var10000.getChildOnIndex(i)) {
                  if (debug) {
                     System.out.println("evaluate " + next + ", val = " + next.getTextCharacters());
                  }

                  if (next.getPropertyIndex() != this.getPropertyIndex()) {
                     break;
                  }

                  sibling = next;
                  var10000 = this.getParent();
                  ++i;
               }

               indx = this.getParent().getChildren().indexOf(sibling);
               if (debug) {
                  System.out.println("evaluate, new index = " + indx);
               }

               if (indx == this.getParent().getChildren().size() - 1) {
                  this.getParent().getChildren().add(source);
                  source.setOriginalParent(source.getParent());
                  source.setParent(this.getParent());
               } else {
                  if (!this.getParent().isMergeRulePrependDefined()) {
                     ++indx;
                  } else if (debug) {
                     System.out.println("***** prepend is defined");
                  }

                  this.getParent().getChildren().add(indx, source);
                  source.setOriginalParent(source.getParent());
                  source.setParent(this.getParent());
               }
            }
         }
      }
   }

   void removeProperty(ReaderEvent2 source) {
      if (debug) {
         System.out.println("removeProperty: " + this);
      }

      this.discard = true;
   }

   void replaceProperty(ReaderEvent2 source) {
      if (debug) {
         System.out.println("replaceProperty: " + this);
      }

      this.getReaderEventInfo().setCharacters(source.getReaderEventInfo().getCharacters());
   }

   void replacePropertyWithOverride(ReaderEvent2 source) {
      if (debug) {
         System.out.println("replacePropertyWithOverride: " + this);
      }

      this.getReaderEventInfo().setCharacters(source.getReaderEventInfo().getKeyOverrideCharacters());
   }

   boolean hasNextChild(int propIndex) {
      Iterator i = this.getChildren().iterator();

      ReaderEvent2 child;
      do {
         if (!i.hasNext()) {
            return false;
         }

         child = (ReaderEvent2)i.next();
      } while(child.getPropertyIndex() <= propIndex);

      return true;
   }

   boolean isArray(int propIndex) {
      return this.helper.isArray(propIndex);
   }

   boolean isBean(int propIndex) {
      return this.helper.isBean(propIndex);
   }

   boolean hasKey(int propIndex) {
      return this.helper.isKey(propIndex);
   }

   boolean isAttribute(int propIndex) {
      return this.helper.isAttribute(propIndex);
   }

   String getXpath() {
      return this.getXpath(false);
   }

   String getXpath(boolean specifiedKeysOnly) {
      if (this.getParent() == null) {
         return "";
      } else {
         int i;
         if (this.isSimpleKey()) {
            if (this.info.getAttributeCount() > 0) {
               for(int i = 0; i < this.info.getAttributeCount(); ++i) {
                  i = this.helper.getPropertyIndex(this.info.getAttributeLocalName(i));
                  if (this.helper.isKey(i)) {
                     StringBuffer sb = new StringBuffer(this.getParent().getXpath());
                     sb.append("/").append(this.getElementName()).append("[");
                     sb.append(this.info.getAttributeLocalName(i)).append("=").append(this.info.getAttributeValue(i));
                     sb.append("]");
                     return sb.toString();
                  }
               }
            }

            return this.getParent().getXpath() + "/[" + this.getElementName() + "=\"" + this.getTrimmedTextCharacters() + "\"]";
         } else if (this.isKeyChoice()) {
            return this.getParent().getXpath() + "/[" + this.getElementName() + "=\"" + this.getTrimmedTextCharacters() + "\"]";
         } else if (this.isKeyComponent()) {
            return this.getParent().getXpath() + "/" + this.getParent().getCompositeXpath(specifiedKeysOnly);
         } else {
            if (this.isBeanKey()) {
               ReaderEvent2 start = this.getNextChild(-1);
               i = this.getChildren().indexOf(start);

               for(ReaderEvent2 k = start; k != null; k = this.getChildOnIndex(i)) {
                  if (k.isSimpleKey() || k.isKeyComponent() || k.isKeyChoice()) {
                     return this.getParent().getXpath() + "/" + this.getElementName() + "/[" + k.getElementName() + "=\"" + k.getTrimmedTextCharacters() + "\"]";
                  }

                  ++i;
               }
            }

            return this.getParent().getXpath() + "/" + this.getElementName();
         }
      }
   }

   String getCompositeXpath(boolean specifiedKeysOnly) {
      String[] keyElementNames = this.helper.getKeyElementNames();
      StringBuffer compositeKey = new StringBuffer("[");

      for(int count = 0; count < keyElementNames.length; ++count) {
         String elementName = keyElementNames[count];
         compositeKey.append("[");

         for(int index = 0; index < this.getChildren().size(); ++index) {
            ReaderEvent2 child = (ReaderEvent2)this.getChildren().elementAt(index);
            boolean includeKey = specifiedKeysOnly ? child.getReaderEventInfo().isKeyCharacters() : true;
            if (elementName.equals(child.getElementName()) && includeKey) {
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
      if (debug) {
         System.out.println("getCompositeXpath return: " + compositeKey);
      }

      return compositeKey.toString();
   }

   ReaderEvent2 findEvent(String xpath) {
      Iterator i = this.getChildren().iterator();

      ReaderEvent2 child;
      do {
         if (!i.hasNext()) {
            return null;
         }

         child = (ReaderEvent2)i.next();
      } while(!child.getXpath().equals(xpath));

      return child;
   }

   ReaderEvent2 getChild(int propIndex) {
      Iterator i = this.getChildren().iterator();

      ReaderEvent2 child;
      do {
         if (!i.hasNext()) {
            return null;
         }

         child = (ReaderEvent2)i.next();
      } while(child.getPropertyIndex() != propIndex);

      return child;
   }

   ReaderEvent2 getMatchingChild(int propIndex) {
      Iterator i = this.getChildren().iterator();

      ReaderEvent2 child;
      do {
         if (!i.hasNext()) {
            return null;
         }

         child = (ReaderEvent2)i.next();
      } while(child.getPropertyIndex() != propIndex);

      return child;
   }

   ReaderEvent2 getPrevChild(int propIndex) {
      ReaderEvent2 retVal = null;
      Iterator i = this.getChildren().iterator();

      ReaderEvent2 child;
      do {
         if (!i.hasNext()) {
            return retVal;
         }

         child = (ReaderEvent2)i.next();
         if (child.getPropertyIndex() < propIndex) {
            retVal = child;
         }
      } while(child.getPropertyIndex() < propIndex);

      return retVal;
   }

   ReaderEvent2 getPrevChild(ReaderEvent2 prevChild) {
      int indx = this.getChildren().indexOf(prevChild);
      --indx;
      return indx < 0 ? null : (ReaderEvent2)this.getChildren().get(indx);
   }

   ReaderEvent2 getNextChild(int propIndex) {
      Iterator i = this.getChildren().iterator();

      ReaderEvent2 child;
      do {
         if (!i.hasNext()) {
            return null;
         }

         child = (ReaderEvent2)i.next();
      } while(child.getPropertyIndex() <= propIndex);

      return child;
   }

   ReaderEvent2 getNextChild(ReaderEvent2 prevChild) {
      int indx = this.getChildren().indexOf(prevChild);
      ++indx;
      return this.getChildren().size() == indx ? null : (ReaderEvent2)this.getChildren().get(indx);
   }

   ReaderEvent2 getChildOnIndex(int indx) {
      return this.getChildren().size() == indx ? null : (ReaderEvent2)this.getChildren().get(indx);
   }

   int getPropertyIndex() {
      return this.getSchemaHelper().getPropertyIndex(this.getElementName());
   }

   boolean isMergeRuleIgnoreSourceDefined() {
      int propIndex = this.getPropertyIndex();
      return this.getSchemaHelper().isMergeRuleIgnoreSourceDefined(propIndex);
   }

   boolean isMergeRuleIgnoreTargetDefined() {
      int propIndex = this.getPropertyIndex();
      return this.getSchemaHelper().isMergeRuleIgnoreTargetDefined(propIndex);
   }

   boolean isMergeRulePrependDefined() {
      int propIndex = this.getPropertyIndex();
      return this.getSchemaHelper().isMergeRulePrependDefined(propIndex);
   }

   void adoptChildren(ReaderEvent2 source) {
      if (debug) {
         System.out.println("\nadoptChildren from source = " + source);
      }

      if (source.getChildren().size() != 0) {
         Iterator i = source.getChildren().iterator();

         while(i.hasNext()) {
            ReaderEvent2 sourceChild = (ReaderEvent2)i.next();
            sourceChild.setOriginalParent(sourceChild.getParent());
            sourceChild.setParent(this);
         }

         this.getChildren().addAll(source.getChildren());
      }
   }

   void adoptChild(ReaderEvent2 sourceChild, ReaderEvent2 targetChild) {
      if (debug) {
         System.out.println("\nadoptChild from sourceChild = " + sourceChild + ", targetChild = " + targetChild);
      }

      int indx = this.getChildren().indexOf(targetChild);
      int sourceIndex = sourceChild.getPropertyIndex();
      int targetIndex = targetChild.getPropertyIndex();
      if (sourceIndex < targetIndex) {
         this.getChildren().add(indx, sourceChild);
      }

      assert sourceIndex != targetIndex;

      if (sourceIndex > targetIndex) {
         ++indx;
         this.getChildren().add(indx, sourceChild);
      }

      this.orphanChild(sourceChild);
      sourceChild.setOriginalParent(sourceChild.getParent());
      sourceChild.setParent(this);
   }

   private void setPrefixOnAdopt(ReaderEvent2 source, String parentPrefix) {
      if (source.info.getPrefix() == null) {
         source.info.setPrefix(parentPrefix);
      }

      Iterator i1 = source.getChildren().iterator();

      while(i1.hasNext()) {
         ReaderEvent2 child = (ReaderEvent2)i1.next();
         this.setPrefixOnAdopt(child, parentPrefix);
      }

   }

   public static void main(String[] args) throws Exception {
      Location l = new Location() {
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
            return "<no public id>";
         }

         public String getSystemId() {
            return "<no system id>";
         }
      };
      StringBuffer sb = new StringBuffer("/weblogic-connector xmlns=\"http://www.bea.com/ns/weblogic/90\"/outbound-resource-adapter/connection-definition-group/[connection-factory-interface=\"javax.sql.DataSource\"]/connection-instance/jndi-name");
      System.out.println("\nxpath = " + sb + "\nresults in:\n");
      (new ReaderEvent2(sb, new ReaderEvent2(7, l), (SchemaHelper)null, "val1", l)).toXML(System.out);
   }
}
