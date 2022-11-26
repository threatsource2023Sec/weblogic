package weblogic.application.descriptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import weblogic.descriptor.BeanCreationInterceptor;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.descriptor.wl.VariableAssignmentBean;
import weblogic.j2ee.descriptor.wl.VariableBean;
import weblogic.j2ee.descriptor.wl.VariableDefinitionBean;
import weblogic.utils.Debug;
import weblogic.xml.stax.XMLStreamInputFactory;

public class BasicMunger extends StreamReaderDelegate implements XMLStreamReader, Munger, BeanCreationInterceptor {
   protected boolean debug;
   protected boolean merge;
   private static final XMLInputFactory xiFactory = new XMLStreamInputFactory();
   private AbstractDescriptorLoader loader;
   private DescriptorImpl descriptor;
   private boolean usingDTD;
   private boolean hasDTD;
   private String dtdNamespaceURI;
   protected boolean playbackToggle;
   protected boolean playback;
   protected boolean forceNoBaseStreamHasNext;
   ReaderEvent queuedEvent;
   protected ArrayList queuedEvents;
   protected ReaderEvent root;
   protected ReaderEvent top;
   protected Stack stack;
   protected Stack beans;
   HashMap valueTable;
   HashMap locationTable;
   HashMap symbolTable;
   Map parentToPath;
   Map pathToParent;
   public static final String NON_VALIDATING_PARSER = "weblogic.NonValidatingParser";
   private static final String TRUE_STR = "true";
   private static final String FALSE_STR = "false";
   public static final int OP_DEFAULT = -1;
   public static final int OP_ADD = 1;
   public static final int OP_REMOVE = 2;
   public static final int OP_REPLACE = 3;

   public BasicMunger(XMLStreamReader delegate, AbstractDescriptorLoader loader) {
      super(delegate);
      this.debug = Debug.getCategory("weblogic.descriptor").isEnabled();
      this.merge = Debug.getCategory("weblogic.merge").isEnabled();
      this.usingDTD = false;
      this.hasDTD = false;
      this.playbackToggle = false;
      this.playback = false;
      this.forceNoBaseStreamHasNext = false;
      this.queuedEvent = null;
      this.top = new ReaderEvent(7, (Object)null, (Location)null, this);
      this.stack = new Stack();
      this.beans = new Stack();
      this.parentToPath = new HashMap();
      this.pathToParent = new LinkedHashMap();
      this.loader = loader;
   }

   public BasicMunger(InputStream in, AbstractDescriptorLoader loader) throws XMLStreamException {
      super(xiFactory.createXMLStreamReader(in));
      this.debug = Debug.getCategory("weblogic.descriptor").isEnabled();
      this.merge = Debug.getCategory("weblogic.merge").isEnabled();
      this.usingDTD = false;
      this.hasDTD = false;
      this.playbackToggle = false;
      this.playback = false;
      this.forceNoBaseStreamHasNext = false;
      this.queuedEvent = null;
      this.top = new ReaderEvent(7, (Object)null, (Location)null, this);
      this.stack = new Stack();
      this.beans = new Stack();
      this.parentToPath = new HashMap();
      this.pathToParent = new LinkedHashMap();
      this.loader = loader;
   }

   public BasicMunger(XMLStreamReader delegate, AbstractDescriptorLoader loader, DeploymentPlanBean plan, String moduleName, String type, String documentURI) {
      this(delegate, loader);
      this.initValueTable(plan, moduleName, documentURI);
   }

   public BasicMunger(InputStream in, AbstractDescriptorLoader loader, DeploymentPlanBean plan, String moduleName, String type, String documentURI) throws XMLStreamException {
      this(xiFactory.createXMLStreamReader(in), loader);
      this.initValueTable(plan, moduleName, documentURI);
   }

   public Map getLocalNameMap() {
      return Collections.EMPTY_MAP;
   }

   public void setDtdNamespaceURI(String ns) {
      this.dtdNamespaceURI = ns;
   }

   public String getDtdNamespaceURI() {
      return this.dtdNamespaceURI;
   }

   public void initDtdText(String dtdText) {
   }

   public void toXML(PrintStream out) {
      this.root.toXML(out);
   }

   public void logError(List errors) {
      if (!errors.isEmpty()) {
         J2EELogger.logDescriptorParseError((new DescriptorException("VALIDATION PROBLEMS " + this.loader.getAbsolutePath(), errors)).getMessage());
      }
   }

   public boolean isValidationEnabled() {
      return !"true".equals(System.getProperty("weblogic.NonValidatingParser"));
   }

   public void merge(BasicMunger otherMunger) {
      try {
         this.resetDescriptor(this.loader, this.loader.getDescriptor());
      } catch (IOException var3) {
         throw new AssertionError(var3);
      } catch (XMLStreamException var4) {
         throw new AssertionError(var4);
      }

      this.merge(otherMunger.root, false);
   }

   public void merge(ReaderEvent source, boolean planMerge) {
      this.parentToPath.clear();
      this.pathToParent.clear();
      this.merge(this.root, source, planMerge);
   }

   public void merge(ReaderEvent target, ReaderEvent source, boolean planMerge) {
      this.initKeyedParentToPath(target);
      this.initPathToParent(source);
      if (this.debug || this.merge) {
         this.dumpStartMerge(source);
      }

      this.replaceMatchingXpathsByKey(target, planMerge);
      if (this.debug || this.merge) {
         System.out.println("--- after replaceMatchingXpathsByKey ----");
         this.dumpStartMerge(source);
      }

      Iterator j = this.pathToParent.values().iterator();

      while(true) {
         while(j.hasNext()) {
            ReaderEvent sourceChild = (ReaderEvent)j.next();
            if (this.debug || this.merge) {
               System.out.println("--- call adoptUnmatchedKeyedReaderEvents with: " + sourceChild.getPath());
            }

            if (target.searchSubTree(sourceChild)) {
               if (this.merge) {
                  System.out.println("--- adoptUnmatchedKeyedReaderEvents: source already exists in target.. skipping");
                  target.toXML(System.out);
               }
            } else {
               ReaderEvent sibling = this.scanForSiblingReaderEvent(target, sourceChild);
               if (this.debug || this.merge) {
                  System.out.println("--- call adoptUnmatchedKeyedReaderEvents with: " + sourceChild.getPath());
                  System.out.println("--- and: " + sibling.getPath());
               }

               ReaderEvent newParent = sibling.getParent();
               if (planMerge && (newParent == null || newParent.getEventType() == 7)) {
                  newParent = sibling;
               }

               ReaderEvent oldParent;
               if (!sourceChild.getLocalName().equals(sibling.getLocalName())) {
                  while(sourceChild.getParent().getLocalName() != null && !sourceChild.getParent().getLocalName().equals(sibling.getParent().getLocalName())) {
                     oldParent = sourceChild.getParent();
                     if (oldParent == null || oldParent.getEventType() == 7 || oldParent.getLocalName() == null) {
                        break;
                     }

                     sourceChild = oldParent;
                  }
               }

               oldParent = sourceChild.getParent();
               oldParent.getChildren().remove(sourceChild);
               if (this.debug || this.merge && oldParent != null && oldParent.getChildren() != null) {
                  System.out.println(" zap old parent iff size == 0, " + oldParent.getChildren().size());
               }

               while(oldParent != null && oldParent.getChildren() != null && oldParent.getChildren().size() == 0) {
                  oldParent.setDiscard();
                  Stack children = oldParent.getChildren();
                  if (children != null) {
                     children.remove(oldParent);
                  }

                  oldParent = oldParent.getParent();
                  if ((this.debug || this.merge) && oldParent != null && oldParent.getChildren() != null) {
                     System.out.println(" zap old parent iff size == 0, " + oldParent.getChildren().size());
                  }
               }

               newParent.getChildren().add(sourceChild);
               sourceChild.fixParents(newParent);
            }
         }

         if (this.debug || this.merge) {
            System.out.println("--- after adoptUnmatchedKeyedReaderEvents ----");
            this.dumpStartMerge(source);
         }

         if (target.searchSubTree(source)) {
            if (this.merge) {
               System.out.println("Skipping replaceMatchingXpaths, source already exists in target");
            }
         } else {
            this.parentToPath.clear();
            this.initParentToPath(source);
            if (this.debug || this.merge) {
               System.out.println("before replaceMatchingXpaths:");
               System.out.println("target = ");
               target.toXML(System.out);
               System.out.println("source = ");
               source.toXML(System.out);
               this.dumpParent2Path();
               System.out.println("-------------");
            }

            this.replaceMatchingXpaths(target, planMerge);
            if (this.debug || this.merge) {
               System.out.print("\n\nafter replaceMatchingXpaths: ");
               System.out.println("target = ");
               target.toXML(System.out);
               System.out.println("source = ");
               source.toXML(System.out);
            }
         }

         if (this.debug || this.merge) {
            System.out.print("\n\n\n... before adoptUnmatchedReaderEvents ");
            this.root.toXML(System.out);
         }

         this.adoptUnmatchedReaderEvents(source);
         if (this.debug || this.merge) {
            System.out.print("\n\n\n... after adoptUnmatchedReaderEvents ");
            this.root.toXML(System.out);
         }

         return;
      }
   }

   DescriptorBean mergeDescriptorBeanWithPlan(AbstractDescriptorLoader loader) throws IOException {
      ArrayList q = new ArrayList();
      this.mergePlan(q);
      this.setQueuedEvents(q);
      Descriptor d = loader.getDescriptorManager().createDescriptor(this);

      assert d != null;

      return d.getRootBean();
   }

   public void mergePlan(ArrayList q) throws IOException {
      if (this.valueTable != null) {
         ArrayList xData = new ArrayList();
         Iterator i = this.valueTable.entrySet().iterator();

         while(true) {
            String xpath;
            String val;
            int operation;
            do {
               if (!i.hasNext()) {
                  ReaderEvent xd;
                  for(i = xData.iterator(); i.hasNext(); this.merge(xd, true)) {
                     xd = (ReaderEvent)i.next();
                     AbstractDescriptorBean bean = (AbstractDescriptorBean)this.descriptor.getRootBean();
                     SchemaHelper helper = bean._getSchemaHelper2();
                     xd.validate(helper);
                     if (this.debug || this.merge) {
                        System.out.println("\n\nBasicMunger: ReaderEvent to merge into plan: ");
                        xd.toXML(System.out);
                        System.out.println("----------- end ReaderEvent --------");
                     }
                  }

                  if (this.debug || this.merge) {
                     System.out.println("\n\nBasicMunger: Current descriptor after subtree has been merged: ");
                     this.root.toXML(System.out);
                  }

                  this.root.toQueuedEvents(q);
                  return;
               }

               Map.Entry e = (Map.Entry)i.next();
               xpath = (String)e.getKey();
               VariableAssignment vassignment = (VariableAssignment)e.getValue();
               val = vassignment.getName();
               operation = vassignment.getOperation();
            } while(val == null);

            if (this.debug || this.merge) {
               System.out.println("BasicMunger: xpath = " + xpath + ", val = " + val);
            }

            xData.add(new ReaderEvent(new StringBuffer(xpath), new ReaderEvent(7, (Object)null, (Location)null, this), val, operation, this, (Location)this.getLocationTable().get(xpath)));
         }
      }
   }

   public AbstractDescriptorBean getCurrentOrEventBean(ReaderEvent event) {
      ReaderEvent currentBeanEvent = this.getCurrentBeanEvent();
      if (currentBeanEvent == null) {
         currentBeanEvent = event.getBeanEvent();
      }

      return currentBeanEvent == null ? null : currentBeanEvent.getBean();
   }

   public ReaderEvent getParentReaderEvent(ReaderEvent event) {
      AbstractDescriptorBean bean = this.getCurrentOrEventBean(event);
      return bean == null ? event.getParentReaderEvent() : (ReaderEvent)bean._getParentReaderEvent(event);
   }

   public DescriptorBean beanCreated(DescriptorBean bean, DescriptorBean parent) {
      if (parent == null) {
         this.descriptor = (DescriptorImpl)bean.getDescriptor();
      }

      if (this.top != null) {
         this.top.setBean((AbstractDescriptorBean)bean);
         ((AbstractDescriptorBean)bean)._setElementName(this.top.getElementName());
         this.beans.push(this.top);
      }

      return bean;
   }

   public String getLocalName() {
      if (this.playback) {
         return this.queuedEvent.getEventType() == 4 ? new String((char[])this.queuedEvent.getCharacters()) : this.queuedEvent.getLocalName();
      } else {
         String n = super.getLocalName();
         if (n == null) {
            return null;
         } else if (this.usingDTD) {
            String r = (String)this.getLocalNameMap().get(n);
            return r != null ? r : n;
         } else {
            return n;
         }
      }
   }

   public char[] getTextCharacters() {
      if (this.playback && this.queuedEvent != null) {
         return (char[])this.queuedEvent.getCharacters();
      } else {
         char[] c = super.getTextCharacters();
         if (this.top != null) {
            this.top.setCharacters(c);
         }

         if (!this.usingDTD) {
            return c;
         } else {
            String s = new String(c);
            if ("true".equalsIgnoreCase(s.trim())) {
               return s.toLowerCase(Locale.US).toCharArray();
            } else {
               return "false".equalsIgnoreCase(s.trim()) ? s.toLowerCase(Locale.US).toCharArray() : c;
            }
         }
      }
   }

   protected void setPlayback(boolean b) {
      if (b != this.playback) {
         if (b) {
            if (this.queuedEvents.size() <= 0) {
               return;
            }

            if (this.forceNoBaseStreamHasNext) {
               this.queuedEvent = (ReaderEvent)this.queuedEvents.remove(0);
               this.playback = true;
               return;
            }

            this.playbackToggle = true;
         } else {
            this.playbackToggle = true;
         }

      }
   }

   public int next() throws XMLStreamException {
      if (this.playbackToggle) {
         this.playback = !this.playback;
         this.playbackToggle = false;
      }

      int n;
      if (this.playback) {
         if (this.queuedEvents.size() > 0) {
            this.queuedEvent = (ReaderEvent)this.queuedEvents.remove(0);
            if (this.debug) {
               System.out.println("-> next: play = " + this.type2Str(this.queuedEvent.getEventType()) + " event queue size = " + this.queuedEvents.size());
            }

            switch (this.queuedEvent.getEventType()) {
               case 1:
                  this.push();
                  break;
               case 2:
                  this.pop();
               case 3:
               case 5:
               case 6:
               default:
                  break;
               case 4:
                  new String(this.getTextCharacters());
                  if (this.top != null) {
                     this.top.setCharacters((char[])this.queuedEvent.getCharacters());
                  }
                  break;
               case 7:
                  this.push();
                  break;
               case 8:
                  this.push();
            }

            n = this.queuedEvent.getEventType();
            return n;
         }

         if (this.debug) {
            System.out.println("playback played out, delegate to reader...");
         }
      }

      this.playback = false;
      this.queuedEvent = null;
      n = super.next();
      if (this.debug) {
         System.out.println("->next = " + this.type2Str(n));
      }

      ReaderEvent currentBeanEvent;
      switch (n) {
         case 1:
            this.push();
            break;
         case 2:
            currentBeanEvent = this.getCurrentBeanEvent();
            if (currentBeanEvent != null && currentBeanEvent.getBean() != null && this.getLocalName().equals(currentBeanEvent.getBean()._getElementName())) {
               this.beans.pop();
            }

            this.pop();
         case 3:
         case 5:
         case 6:
         case 9:
         case 10:
         default:
            break;
         case 4:
            this.getTextCharacters();
            break;
         case 7:
            this.push();
            break;
         case 8:
            this.push();
            currentBeanEvent = this.getCurrentBeanEvent();
            if (currentBeanEvent != null && currentBeanEvent.getBean() != null) {
               this.beans.pop();
            }

            this.pop();
            break;
         case 11:
            this.hasDTD = true;
            this.usingDTD = true;
            this.initDtdText(this.getText());
            return this.next();
      }

      return n;
   }

   private ReaderEvent getCurrentBeanEvent() {
      return this.beans.empty() ? null : (ReaderEvent)this.beans.peek();
   }

   public void push() {
      this.stack.push(this.top);
      Location l = this.getLocation();
      ReaderEvent d = new ReaderEvent(this.getEventType() == 8 ? null : this.getLocalName(), this.top, this, l, this.getCurrentBeanEvent());
      if (this.top.getEventType() == 7) {
         this.root = d;
      }

      this.top = d;
      ReaderEvent beanEvent = this.getCurrentBeanEvent();
      if (beanEvent != null) {
         this.top.setBean(beanEvent.getBean());
      }

      if (this.getEventType() == 1 && this.getAttributeCount() > 0) {
         d.setAttributeCount(this.getAttributeCount());

         for(int i = 0; i < this.getAttributeCount(); ++i) {
            d.setAttributeValue(this.getAttributeValue(i), i);
         }
      }

   }

   public void pop() {
      if (this.stack.empty()) {
         if (this.debug) {
            System.out.println("\n\nStack is empty!!!!!");
         }

      } else {
         ReaderEvent parent = (ReaderEvent)this.stack.pop();
         if (parent != null) {
            parent.getChildren().push(this.top);
            this.top = parent;
         }

      }
   }

   public void setBean(AbstractDescriptorBean bean) {
      if (this.top != null) {
         ReaderEvent cur = (ReaderEvent)this.top.getChildren().lastElement();
         cur.setBean(bean);
      }

   }

   public int skip(int event) throws XMLStreamException {
      switch (event) {
         case 1:
            this.top.setDiscard();
         case 2:
         default:
            if (this.debug) {
               System.out.println("skipped...");
            }

            return this.next();
      }
   }

   public String getNamespaceURI() {
      if (this.debug) {
         System.out.println("->getNamespaceURI: usingDTD() =" + this.usingDTD());
      }

      return this.usingDTD() ? this.getDtdNamespaceURI() : super.getNamespaceURI();
   }

   public boolean usingDTD() {
      return this.usingDTD;
   }

   public boolean hasDTD() {
      return this.hasDTD || super.getNamespaceURI() == null;
   }

   public void setParent(XMLStreamReader a) {
      if (this.debug) {
         System.out.println("->setParent");
      }

      super.setParent(a);
   }

   public XMLStreamReader getParent() {
      if (this.debug) {
         System.out.println("->getParent");
      }

      return super.getParent();
   }

   public int nextTag() throws XMLStreamException {
      if (this.debug) {
         System.out.println("->nextTag");
      }

      return this.playback ? this.next() : super.nextTag();
   }

   public String getElementText() throws XMLStreamException {
      if (this.debug) {
         System.out.println("->getElementText");
      }

      return super.getElementText();
   }

   public void require(int a, String b, String c) throws XMLStreamException {
      if (this.debug) {
         System.out.println("->require");
      }

      super.require(a, b, c);
   }

   public void setForceNoBaseStreamHasNext(boolean b) {
      this.forceNoBaseStreamHasNext = b;
   }

   public boolean hasNext() throws XMLStreamException {
      boolean effectiveHasNextPlayback = this.playbackToggle ? !this.playback : this.playback;
      boolean b;
      if (effectiveHasNextPlayback && this.queuedEvents.size() > 0) {
         b = true;
      } else if (this.forceNoBaseStreamHasNext) {
         b = false;
      } else {
         b = super.hasNext();
      }

      if (this.debug) {
         System.out.println("->hasNext: = " + b + ", playback = " + this.playback);
      }

      return b;
   }

   public void close() throws XMLStreamException {
      if (this.debug) {
         System.out.println("->close");
      }

      super.close();
   }

   public String getNamespaceURI(String a) {
      if (this.debug) {
         System.out.println("->getNamespaceURI(String)");
      }

      return super.getNamespaceURI(a);
   }

   public NamespaceContext getNamespaceContext() {
      if (this.debug) {
         System.out.println("->getNamespaceContext");
      }

      return super.getNamespaceContext();
   }

   public boolean isStartElement() {
      boolean b = this.playback ? this.queuedEvent.getEventType() == 1 : super.isStartElement();
      if (this.debug) {
         System.out.println("->isStartElement: " + b);
      }

      return b;
   }

   public boolean isEndElement() {
      if (this.debug) {
         System.out.println("->isEndElement playback=" + this.playback);
      }

      if (this.playback) {
         return this.queuedEvent.getEventType() == 2;
      } else {
         return super.isEndElement();
      }
   }

   public boolean isCharacters() {
      if (this.debug) {
         System.out.println("->isCharacters");
      }

      if (this.playback) {
         return this.queuedEvent.getEventType() == 4;
      } else {
         return super.isCharacters();
      }
   }

   private static boolean isSpace(char c) {
      return c == ' ' || c == '\t' || c == '\n' || c == '\r';
   }

   public boolean isWhiteSpace() {
      if (this.debug) {
         System.out.println("->isWhiteSpace");
      }

      if (this.playback) {
         if (this.queuedEvent.getEventType() == 4) {
            char[] c = (char[])this.queuedEvent.getCharacters();

            for(int i = 0; i < c.length; ++i) {
               if (!isSpace(c[i])) {
                  return false;
               }
            }

            return true;
         } else {
            throw new IllegalStateException("isWhiteSpace on type " + this.type2Str(this.queuedEvent.getEventType()));
         }
      } else {
         return super.isWhiteSpace();
      }
   }

   public String getAttributeValue(String a, String b) {
      String s = this.playback ? this.queuedEvent.getAttributeValue(a, b) : super.getAttributeValue(a, b);
      if (!this.playback && this.top != null) {
         this.top.setAttributeValue(s, a, b);
      }

      if (this.debug) {
         System.out.println("->getAttributeValue(" + a + ", " + b + ") returns: " + s);
      }

      return s;
   }

   public int getAttributeCount() {
      int c = this.playback ? this.queuedEvent.getAttributeCount() : super.getAttributeCount();
      if (this.debug) {
         System.out.println("->getAttributeCount() returns " + c);
      }

      if (!this.playback && this.top != null) {
         this.top.setAttributeCount(c);
      }

      return c;
   }

   public QName getAttributeName(int index) {
      QName q = this.playback ? this.queuedEvent.getAttributeName(index) : super.getAttributeName(index);
      if (this.debug) {
         System.out.println("->getAttributeName(" + index + ") returns: " + q);
      }

      return q;
   }

   public String getAttributePrefix(int index) {
      String s = this.playback ? this.queuedEvent.getAttributePrefix(index) : super.getAttributePrefix(index);
      if (!this.playback && this.top != null) {
         this.top.setAttributePrefix(s, index);
      }

      if (this.debug) {
         System.out.println("->getAttributePrefix(" + index + ") return " + s);
      }

      return s;
   }

   public String getAttributeNamespace(int index) {
      String s = this.playback ? this.queuedEvent.getAttributeNamespace(index) : super.getAttributeNamespace(index);
      if (!this.playback && this.top != null) {
         this.top.setAttributeNamespace(s, index);
      }

      if (this.debug) {
         System.out.println("->getAttributeNamespace(" + index + ") returns " + s);
      }

      return s;
   }

   public String getAttributeLocalName(int index) {
      String s = this.playback ? this.queuedEvent.getAttributeLocalName(index) : super.getAttributeLocalName(index);
      if (!this.playback && this.top != null) {
         this.top.setAttributeLocalName(s, index);
      }

      if (this.debug) {
         System.out.println("->getAttributeLocalName(" + index + ") returns " + s);
      }

      return s;
   }

   public String getAttributeType(int a) {
      if (this.debug) {
         System.out.println("->getAttributeType returns CDATA");
      }

      return "CDATA";
   }

   public String getAttributeValue(int index) {
      String s = this.playback ? this.queuedEvent.getAttributeValue(index) : super.getAttributeValue(index);
      if (!this.playback && this.top != null) {
         this.top.setAttributeValue(s, index);
      }

      if (this.debug) {
         System.out.println("->getAttributeValue(" + index + ") returns: " + s);
      }

      return s;
   }

   public boolean isAttributeSpecified(int index) {
      boolean b = this.playback ? this.queuedEvent.isAttributeSpecified(index) : super.isAttributeSpecified(index);
      if (this.debug) {
         System.out.println("->isAttributeSpecified(" + index + ") returns " + b);
      }

      return b;
   }

   public int getNamespaceCount() {
      int c = this.playback ? this.queuedEvent.getNamespaceCount() : super.getNamespaceCount();
      if (!this.playback && this.top != null) {
         this.top.setNamespaceCount(c);
      }

      if (this.debug) {
         System.out.println("->getNamespaceCount return " + c);
      }

      return c;
   }

   public String getNamespacePrefix(int index) {
      String s = this.playback ? null : super.getNamespacePrefix(index);
      if (this.debug) {
         System.out.println("->getNamespacePrefix(" + index + ") return " + s);
      }

      return s;
   }

   public String getNamespaceURI(int a) {
      if (this.debug) {
         System.out.println("->getNamespaceURI(int)");
      }

      return super.getNamespaceURI(a);
   }

   public int getEventType() {
      int c = this.playback ? this.queuedEvent.getEventType() : super.getEventType();
      if (this.debug) {
         System.out.println("->getEventType: " + this.type2Str(c));
      }

      return c;
   }

   public String getText() {
      if (this.debug) {
         System.out.println("->getText");
      }

      return this.playback ? new String(this.getTextCharacters()) : super.getText();
   }

   public int getTextCharacters(int a, char[] b, int c, int d) throws XMLStreamException {
      throw new UnsupportedOperationException();
   }

   public int getTextStart() {
      if (this.debug) {
         System.out.println("->getTextStart");
      }

      return super.getTextStart();
   }

   public int getTextLength() {
      if (this.debug) {
         System.out.println("->getTextLength playback=" + this.playback + " queuedEvent=" + this.queuedEvent);
      }

      return this.getTextCharacters().length;
   }

   public String getEncoding() {
      if (this.debug) {
         System.out.println("->getEncoding");
      }

      return super.getEncoding();
   }

   public boolean hasText() {
      if (this.debug) {
         System.out.println("->hasText");
      }

      if (this.playback) {
         return this.queuedEvent.getEventType() == 4 || this.queuedEvent.getEventType() == 6 || this.queuedEvent.getEventType() == 11 || this.queuedEvent.getEventType() == 9 || this.queuedEvent.getEventType() == 5;
      } else {
         return super.hasText();
      }
   }

   public Location getLocation() {
      Location l = this.getEventType() == 8 ? this.top.getLocation() : (this.playback ? this.top.getLocation() : new MyLocation(super.getLocation()));
      if (!this.playback && this.top != null) {
         this.top.setLocation((Location)l);
      }

      if (this.debug) {
         System.out.println("->getLocation: " + l);
      }

      return (Location)l;
   }

   public QName getName() {
      if (this.debug) {
         System.out.println("->getName");
      }

      return this.playback ? new QName(this.getNamespaceURI(), this.getLocalName()) : super.getName();
   }

   public boolean hasName() {
      if (this.debug) {
         System.out.println("->hasName");
      }

      if (!this.playback) {
         return super.hasName();
      } else {
         return this.queuedEvent.getEventType() == 1 || this.queuedEvent.getEventType() == 2;
      }
   }

   public String getPrefix() {
      if (this.debug) {
         System.out.println("->getPrefix");
      }

      return super.getPrefix();
   }

   public String getVersion() {
      if (this.debug) {
         System.out.println("->getVersion");
      }

      return super.getVersion();
   }

   public boolean isStandalone() {
      if (this.debug) {
         System.out.println("->isStandalone");
      }

      return super.isStandalone();
   }

   public boolean standaloneSet() {
      if (this.debug) {
         System.out.println("->standaloneSet");
      }

      return super.standaloneSet();
   }

   public String getCharacterEncodingScheme() {
      if (this.debug) {
         System.out.println("->getCharacterEncodingScheme");
      }

      return super.getCharacterEncodingScheme();
   }

   public String getPITarget() {
      if (this.debug) {
         System.out.println("->getPITarget");
      }

      return super.getPITarget();
   }

   public String getPIData() {
      if (this.debug) {
         System.out.println("->getPIData");
      }

      return super.getPIData();
   }

   public Object getProperty(String a) {
      if (this.debug) {
         System.out.println("->getProperty");
      }

      return super.getProperty(a);
   }

   private void initValueTable(final DeploymentPlanBean plan, String moduleName, String documentURI) {
      if (plan != null && moduleName != null && documentURI != null) {
         boolean match = false;
         ModuleOverrideBean[] mos = plan.getModuleOverrides();

         for(int i = 0; i < mos.length; ++i) {
            if (this.debug) {
               System.out.println("initValueTable: mos[i].getModuleType() = " + mos[i].getModuleType() + ",\n mos[i].getModuleName() = " + mos[i].getModuleName());
            }

            if (mos[i].getModuleName().equals(moduleName)) {
               ModuleDescriptorBean[] mdb = mos[i].getModuleDescriptors();

               for(int j = 0; j < mdb.length; ++j) {
                  if (mdb[j].getUri().equals(documentURI)) {
                     match = true;
                     VariableAssignmentBean[] vabs = mdb[j].getVariableAssignments();
                     if (this.debug) {
                        System.out.println("initValueTable: vabs.length = " + vabs.length);
                     }

                     for(int k = 0; k < vabs.length; ++k) {
                        if (this.debug) {
                           System.out.println("initValueTable: " + vabs[k].getXpath() + ", " + vabs[k].getName());
                        }

                        VariableAssignment tableEntry = new VariableAssignment((String)this.getSymbolTable(plan).get(vabs[k].getName()), vabs[k].getOperation());
                        this.getValueTable().put(vabs[k].getXpath(), tableEntry);
                        this.getLocationTable().put(vabs[k].getXpath(), new Location() {
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
                              return plan.getConfigRoot() + "plan.xml";
                           }

                           public String getSystemId() {
                              return this.getPublicId();
                           }
                        });
                     }
                  }
               }

               if (this.debug) {
                  System.out.println("\n");
               }
            }
         }

         if (match) {
            J2EELogger.logValidPlanMerged(moduleName, documentURI);
            if (this.debug) {
               try {
                  ((DescriptorBean)plan).getDescriptor().toXML(System.out);
               } catch (IOException var12) {
                  var12.printStackTrace();
               }
            }
         }

      }
   }

   private HashMap getValueTable() {
      if (this.valueTable == null) {
         this.valueTable = new LinkedHashMap();
      }

      return this.valueTable;
   }

   private HashMap getSymbolTable(DeploymentPlanBean plan) {
      if (this.symbolTable == null) {
         this.symbolTable = new HashMap();
         VariableDefinitionBean vdb = plan.getVariableDefinition();
         VariableBean[] vbs = vdb.getVariables();

         for(int i = 0; i < vbs.length; ++i) {
            if (this.debug) {
               System.out.println("getSymbolTable: " + vbs[i].getName() + ", " + vbs[i].getValue());
            }

            this.symbolTable.put(vbs[i].getName(), vbs[i].getValue());
         }
      }

      return this.symbolTable;
   }

   private HashMap getLocationTable() {
      if (this.locationTable == null) {
         this.locationTable = new HashMap();
      }

      return this.locationTable;
   }

   private void initKeyedParentToPath(ReaderEvent to) {
      Iterator i = to.getChildren().iterator();

      while(true) {
         ReaderEvent toChild;
         do {
            if (!i.hasNext()) {
               return;
            }

            toChild = (ReaderEvent)i.next();
         } while(toChild.isDiscarded());

         if (toChild.isKey() || toChild.isKeyComponent()) {
            if (toChild.getParent().getParentReaderEvent() != null && toChild.getAttributeCount() != 1) {
               this.parentToPath.put(toChild.getParent(), toChild.getPath());
            } else {
               this.parentToPath.put(toChild, toChild.getPath());
            }
         }

         if (toChild.getChildren() != null) {
            this.initKeyedParentToPath(toChild);
         }
      }
   }

   private void initParentToPath(ReaderEvent to) {
      Iterator i = to.getChildren().iterator();

      while(i.hasNext()) {
         ReaderEvent toChild = (ReaderEvent)i.next();
         if (!toChild.isDiscarded()) {
            if (toChild.getParent().getParentReaderEvent() == null) {
               this.parentToPath.put(toChild, toChild.getPath());
            } else {
               this.parentToPath.put(toChild.getParent(), toChild.getPath());
            }

            if (toChild.getChildren() != null) {
               this.initParentToPath(toChild);
            }
         }
      }

   }

   private void initPathToParent(ReaderEvent from) {
      Iterator i = from.getChildren().iterator();

      while(true) {
         ReaderEvent fromChild;
         do {
            if (!i.hasNext()) {
               return;
            }

            fromChild = (ReaderEvent)i.next();
         } while(fromChild.isDiscarded());

         if (fromChild.isKey() || fromChild.isKeyComponent()) {
            if (fromChild.isKeyAnAttribute()) {
               this.pathToParent.put(fromChild.getPath(), fromChild);
            } else if (fromChild.getParent().getParentReaderEvent() == null) {
               this.pathToParent.put(fromChild.getPath(), fromChild);
            } else {
               this.pathToParent.put(fromChild.getPath(), fromChild.getParent());
            }
         }

         if (fromChild.getChildren() != null) {
            this.initPathToParent(fromChild);
         }
      }
   }

   private void replaceMatchingXpathsByKey(ReaderEvent target, boolean mergeChildren) {
      Iterator i = ((Collection)target.getChildren().clone()).iterator();

      while(true) {
         while(true) {
            ReaderEvent targetChild;
            do {
               if (!i.hasNext()) {
                  return;
               }

               targetChild = (ReaderEvent)i.next();
            } while(targetChild.isDiscarded());

            String path = (String)this.parentToPath.get(targetChild);
            if (this.debug || this.merge) {
               System.out.println("targetChild = " + targetChild + ", path = " + targetChild.getPath() + ", paretnToPath() = " + path);
            }

            if (path != null) {
               ReaderEvent source = (ReaderEvent)this.pathToParent.get(path);
               if (source != null && targetChild.hasBeanCompositeKey() && !ReaderEvent.compareKeys(targetChild.getBeanCompositeKey(), source.getBeanCompositeKey(targetChild.getBean()._getSchemaHelper2()))) {
                  continue;
               }

               source = (ReaderEvent)this.pathToParent.remove(path);
               if (source == null || source.isDiscarded()) {
                  continue;
               }

               if (this.debug || this.merge) {
                  System.out.println("replaceMatchingXpathsByKey.path = " + path);
                  System.out.println("source = " + source);
               }

               if (source != null) {
                  if (this.debug || this.merge) {
                     System.out.println("replaceMatchingXpathsByKey.source = " + source);
                     System.out.println("replaceMatchingXpathsByKey.source.getParent() = " + source.getParent().getPath());
                  }

                  source.getParent().getChildren().remove(source);
                  if (source.getParent().getChildren().empty()) {
                     for(ReaderEvent deadBranch = source; deadBranch != null && deadBranch.getParent() != null && deadBranch.getParent().getChildren() != null && deadBranch.getParent().getChildren().empty(); deadBranch = deadBranch.getParent()) {
                        deadBranch.getParent().setDiscard();
                     }
                  }

                  if (this.debug || this.merge) {
                     System.out.println("replaceMatchingXpathsByKey.targetChild.getParent() = " + targetChild.getParent().getPath());
                     System.out.println("replaceMatchingXpathsByKey.targetChild() = " + targetChild.getPath());
                     System.out.println("before...");
                     Iterator ii = targetChild.getChildren().iterator();

                     while(ii.hasNext()) {
                        System.out.println("targetChild.child = " + ((ReaderEvent)ii.next()).getPath());
                     }
                  }

                  if (mergeChildren) {
                     this.mergeSiblings(source, targetChild, mergeChildren);
                  }

                  source.fixParents(targetChild.getParent());
                  if (source.getBean() == null) {
                     source.setBean(targetChild.getBean());
                  }

                  target.getChildren().set(target.getChildren().indexOf(targetChild), source);
                  if (!mergeChildren) {
                     targetChild.discard();
                  }
                  continue;
               }
            }

            if (targetChild.getChildren() != null && targetChild.getChildren().size() > 0) {
               this.replaceMatchingXpathsByKey(targetChild, mergeChildren);
            }
         }
      }
   }

   private boolean mergeSiblings(ReaderEvent source, ReaderEvent targetChild, boolean mergeChildren) {
      if (this.merge) {
         System.out.println("mergeSiblings(begin)... source");
         source.toXML(System.out);
         System.out.println("mergeSiblings(begin)... targetChild");
         targetChild.toXML(System.out);
      }

      if (source.getBean() == null) {
         source.setBean(targetChild.getBean());
      }

      SchemaHelper helper = targetChild.getBean() == null ? targetChild.getSchemaHelper() : targetChild.getBean()._getSchemaHelper2();
      HashMap h = new HashMap();

      ReaderEvent s;
      for(Iterator ii = ((Collection)source.getChildren().clone()).iterator(); ii.hasNext(); h.put(s.getLocalName(), s)) {
         s = (ReaderEvent)ii.next();
         int s_operation = s.getOperation();
         if (s_operation == 3 || s_operation == 2) {
            targetChild.removeNamedChildren(s.getLocalName());
         }

         if (s_operation == 2) {
            source.getChildren().remove(s);
         }

         if (s_operation == 3 && s.isBeanKey(helper) && !s.isKey() && h.containsKey(s.getLocalName())) {
            source.getChildren().remove(h.get(s.getLocalName()));
            s.setOperation(-1);
         }

         if (s.isBeanKey(helper) && (!mergeChildren || s_operation != 3)) {
            Object otherKey = targetChild.getBeanKey(helper);
            if (!ReaderEvent.compareKeys(s.getCharactersAsString(), otherKey) && !ReaderEvent.compareKeys(this.trimWS(s.getCharactersAsString()), this.trimWS(otherKey))) {
               if (this.merge) {
                  System.out.println("mergeSiblings... Refusal to merge, keys different: " + s.getElementName() + " values " + s.getCharactersAsString() + ", " + otherKey);
               }

               return false;
            }
         }

         if (h.containsKey(s.getLocalName())) {
            String arrayPath = s.getLocalName();
            h.remove(s.getLocalName());

            while(ii.hasNext()) {
               s = (ReaderEvent)ii.next();
               if (!s.getLocalName().equals(arrayPath)) {
                  break;
               }
            }

            if (!ii.hasNext()) {
               break;
            }
         }
      }

      List additionalChildren = new ArrayList();
      Iterator ii = targetChild.getChildren().iterator();

      while(true) {
         while(true) {
            ReaderEvent tChild;
            do {
               if (!ii.hasNext()) {
                  if (additionalChildren.size() > 0) {
                     source.getChildren().addAll(additionalChildren);
                  }

                  if (this.merge) {
                     System.out.println("mergeSiblings(end)... source");
                     source.toXML(System.out);
                     System.out.println("mergeSiblings(end)... targetChild");
                     targetChild.toXML(System.out);
                  }

                  return true;
               }

               tChild = (ReaderEvent)ii.next();
            } while(tChild.isDiscarded());

            if (h.remove(tChild.getLocalName()) != null) {
               if (helper == null) {
                  System.out.println("no helper... continue");
                  continue;
               }

               int index = helper.getPropertyIndex(tChild.getElementName());
               if (!helper.isBean(index) && !helper.isArray(index)) {
                  continue;
               }
            }

            boolean matching = false;
            Iterator jj = source.getChildren().iterator();

            while(jj.hasNext()) {
               ReaderEvent sChild = (ReaderEvent)jj.next();
               if (sChild.getLocalName().equals(tChild.getLocalName()) && helper != null) {
                  int index = helper.getPropertyIndex(sChild.getElementName());
                  if (helper.isBean(index)) {
                     if (sChild.getChildren().size() > 0) {
                        if (this.mergeSiblings(sChild, tChild, mergeChildren)) {
                           matching = true;
                        }
                     } else if (this.merge) {
                        System.out.println("mergeSiblings(end)... source empty, nothing to merge");
                     }
                  } else if (sChild.isSingleton()) {
                     matching = true;
                  }
               }
            }

            if (!matching) {
               additionalChildren.add(tChild);
            }
         }
      }
   }

   private void replaceMatchingXpaths(ReaderEvent target, boolean mergeChildren) {
      if (!target.isDiscarded() && this.parentToPath.size() > 0) {
         String targetPath = target.getPath();
         Iterator j = this.parentToPath.entrySet().iterator();

         ReaderEvent source;
         do {
            String sourcePath;
            do {
               if (!j.hasNext()) {
                  for(int i = 0; i < target.getChildren().size(); ++i) {
                     ReaderEvent targetChild = (ReaderEvent)target.getChildren().elementAt(i);
                     this.replaceMatchingXpaths(targetChild, mergeChildren);
                  }

                  return;
               }

               Map.Entry e = (Map.Entry)j.next();
               source = (ReaderEvent)e.getKey();
               sourcePath = source.getPath();
            } while(targetPath.compareTo(sourcePath) != 0);

            if (this.debug || this.merge) {
               System.out.println(".. matched " + targetPath);
            }
         } while(!this.mergeSiblings(source, target, mergeChildren));

         target.replaceAndMoveChildren(source);
         this.pathToParent.remove(source);
      }
   }

   private ReaderEvent scanForSiblingReaderEvent(ReaderEvent to, ReaderEvent from) {
      ReaderEvent sibling = to;
      Iterator j = to.getChildren().iterator();

      while(j.hasNext()) {
         sibling = (ReaderEvent)j.next();
         if (this.debug || this.merge) {
            System.out.println("compare sibling [" + sibling.getLocalName() + "] to [" + from.getLocalName() + "]");
         }

         if (sibling.getLocalName().equals(from.getLocalName()) && this.pathsMatch(sibling, from)) {
            return sibling;
         }

         if (sibling.getChildren() != null) {
            sibling = this.scanForSiblingReaderEvent(sibling, from);
         }
      }

      return sibling;
   }

   private void adoptUnmatchedReaderEvents(ReaderEvent from) {
      Iterator i = from.getChildren().iterator();

      while(true) {
         ReaderEvent fromChild;
         do {
            if (!i.hasNext()) {
               return;
            }

            fromChild = (ReaderEvent)i.next();
         } while(fromChild.isDiscarded());

         boolean append = true;
         Iterator j;
         ReaderEvent sibling;
         if (fromChild.isSingleton()) {
            j = this.root.getChildren().iterator();

            while(j.hasNext()) {
               sibling = (ReaderEvent)j.next();
               if (sibling.getLocalName() != null && fromChild.getLocalName() != null && sibling.getLocalName().equals(fromChild.getLocalName())) {
                  fromChild.fixParents(this.root);
                  fromChild.discard();
                  sibling.getChildren().addAll(fromChild.getChildren());
                  append = false;
                  break;
               }
            }
         }

         if (fromChild.isAdditive()) {
            j = this.root.getChildren().iterator();

            while(j.hasNext()) {
               sibling = (ReaderEvent)j.next();
               if (sibling.getLocalName().equals(fromChild.getLocalName())) {
                  fromChild.fixParents(sibling.getParentReaderEvent());
                  this.root.getChildren().set(this.root.getChildren().indexOf(sibling), fromChild);
                  append = false;
                  break;
               }
            }
         }

         if (append) {
            fromChild.fixParents(this.root);
            this.root.getChildren().add(fromChild);
         }
      }
   }

   private void resetDescriptor(AbstractDescriptorLoader l, Descriptor d) throws IOException, XMLStreamException {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      l.getDescriptorManager().writeDescriptorAsXML(d, out);
      byte[] bits = out.toByteArray();
      InputStream in = new ByteArrayInputStream(bits);
      BasicMunger hackedReader = l.createXMLStreamReader(in);
      l.getDescriptorManager().createDescriptor(hackedReader);
      this.root = hackedReader.root;
      this.top = hackedReader.top;
      this.stack = hackedReader.stack;
      this.descriptor = hackedReader.descriptor;
   }

   private boolean pathsMatch(ReaderEvent to, ReaderEvent from) {
      while(to != null && from != null && to.getEventType() != 7 && from.getEventType() != 7) {
         String toName = to.getLocalName();
         String fromName = from.getLocalName();
         if (!toName.equals(fromName)) {
            return false;
         }

         to = to.getParent();
         from = from.getParent();
      }

      if ((to != null || from == null) && (to == null || from != null) && to.getEventType() == from.getEventType()) {
         return true;
      } else {
         return false;
      }
   }

   protected String type2Str(int t) {
      switch (t) {
         case 1:
            return "START_ELEMENT[" + this.getLocalName() + "]";
         case 2:
            return "END_ELEMENT[" + this.getLocalName() + "]";
         case 3:
            return "PROCESSING_INSTRUCTION";
         case 4:
            return "CHARACTERS: [" + new String(this.getTextCharacters()) + "]";
         case 5:
            return "COMMENT";
         case 6:
            return "SPACE[6]";
         case 7:
            return "START_DOCUMENT[7]";
         case 8:
            return "END_DOCUMENT[8]";
         case 9:
            return "ENTITY_REFERENCE[9]";
         case 10:
            return "ATTRIBUTE";
         case 11:
            return "DTD";
         case 12:
            return "CDATA[12]";
         case 13:
            return "NAMESPACE[13]";
         case 14:
            return "NOTATION_DECLARATION";
         case 15:
            return "ENTITY_DECLARATION";
         default:
            throw new AssertionError("Unexpected type " + t);
      }
   }

   private void dumpStartMerge(ReaderEvent from) {
      System.out.println("BasicMunger: start merge...");
      System.out.println("BasicMunger: current ReaderEvent: = ");
      this.root.toXML(System.out);
      this.dumpParent2Path();
      System.out.println("-----------");
      System.out.println("BasicMunger: ReaderEvent to merge into current: = ");
      from.toXML(System.out);
      this.dumpPath2Parents();
      System.out.println("-----------");
      System.out.println("BasicMunger: ... continue merge...");
   }

   private void dumpParent2Path() {
      System.out.println("\ndump parentToPath:");
      Iterator j = this.parentToPath.entrySet().iterator();

      while(j.hasNext()) {
         Map.Entry e = (Map.Entry)j.next();
         ReaderEvent k = (ReaderEvent)e.getKey();
         String v = (String)e.getValue();
         System.out.println("instance = (" + k + ")=" + k.getPath() + " mapped to key =" + v);
      }

   }

   private void dumpPath2Parents() {
      System.out.println("\ndump pathToParent:");
      Iterator j = this.pathToParent.entrySet().iterator();

      while(j.hasNext()) {
         Map.Entry e = (Map.Entry)j.next();
         String v = (String)e.getKey();
         ReaderEvent k = (ReaderEvent)e.getValue();
         System.out.println("key =" + v + " mapped to instance = (" + k + ")=" + k.getPath());
      }

   }

   public void setQueuedEvents(ArrayList q) {
      this.queuedEvents = q;
      this.setPlayback(true);
      this.usingDTD = true;
   }

   public void toQueuedEvents(ArrayList events) {
      if (this.descriptor == null) {
         this.root.toQueuedEvents(events);
      } else {
         this.root.toQueuedEvents(events);
      }
   }

   public ReaderEvent getQueuedEvent(int type, Object data) {
      return new ReaderEvent(type, data, this.getLocation(), this);
   }

   public boolean supportsValidation() {
      return false;
   }

   private Object trimWS(Object keyObj) {
      if (!(keyObj instanceof String)) {
         return keyObj;
      } else {
         String orig = (String)keyObj;
         int start = 0;
         int end = orig.length();

         int i;
         for(i = 0; i < orig.length() && Character.isWhitespace(orig.charAt(i)); ++i) {
            ++start;
         }

         for(i = end; i > 0 && i > start && Character.isWhitespace(orig.charAt(i - 1)); --i) {
            --end;
         }

         return start == 0 && end == orig.length() ? orig : orig.substring(start, end);
      }
   }

   public static class VariableAssignment {
      String name;
      int op;

      public VariableAssignment(String name, String operation) {
         this.name = name;
         this.setOperation(operation);
      }

      public void setOperation(String value) {
         if (value.equals("add")) {
            this.op = 1;
         } else if (value.equals("remove")) {
            this.op = 2;
         } else if (value.equals("replace")) {
            this.op = 3;
         } else {
            this.op = -1;
         }

      }

      public String getName() {
         return this.name;
      }

      public int getOperation() {
         return this.op;
      }
   }

   private class MyLocation implements Location {
      Location l;

      MyLocation(Location l) {
         this.l = l;
      }

      public int getLineNumber() {
         return this.l.getLineNumber();
      }

      public int getColumnNumber() {
         return this.l.getColumnNumber();
      }

      public int getCharacterOffset() {
         return this.l.getCharacterOffset();
      }

      public String getPublicId() {
         return BasicMunger.this.loader.getAbsolutePath() + ":" + this.l.getLineNumber() + ":" + this.l.getColumnNumber();
      }

      public String getSystemId() {
         return this.l.getSystemId();
      }
   }
}
