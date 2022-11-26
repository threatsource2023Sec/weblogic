package weblogic.application.descriptor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.utils.Debug;

public class VersionMunger extends BasicMunger2 {
   protected boolean debug;
   protected boolean dump;
   private Map elementNameChanges;
   protected Stack stack;
   protected String absolutePath;
   protected String schemaHelperClassName;
   protected boolean isOldSchema;
   protected boolean forceSkipParent;
   private String rootElementName;
   protected String newNamespaceURI;
   ReaderEvent2 playback;
   protected ReaderEvent2 lastEvent;
   ReaderEvent2 anchorEvent;
   protected static final Continuation CONTINUE = new Continuation() {
      public int nextToken(int c) {
         return c;
      }
   };
   protected final Continuation USE_BUFFER;
   protected final Continuation SKIP;
   SchemaHelper schemaHelper;
   private static final String TRUE_STR = "true";
   private static final String FALSE_STR = "false";
   protected String tranformedNamespace;
   protected ReaderEvent2 skippedEvent;
   private static final int MAX_ELEMENTS = 10;

   public VersionMunger(InputStream in, AbstractDescriptorLoader2 loader, String schemaHelperClassName) throws XMLStreamException {
      this(in, loader, schemaHelperClassName, Collections.EMPTY_MAP, false);
   }

   public VersionMunger(InputStream in, AbstractDescriptorLoader2 loader, String schemaHelperClassName, boolean disableReorder) throws XMLStreamException {
      this(in, loader, schemaHelperClassName, Collections.EMPTY_MAP, disableReorder);
   }

   public VersionMunger(InputStream in, AbstractDescriptorLoader2 loader, String schemaHelperClassName, Map elementNameChanges) throws XMLStreamException {
      this(in, loader, schemaHelperClassName, elementNameChanges, false);
   }

   public VersionMunger(InputStream in, AbstractDescriptorLoader2 loader, String schemaHelperClassName, Map elementNameChanges, boolean disableReorder) throws XMLStreamException {
      this(in, loader, schemaHelperClassName, elementNameChanges, disableReorder, (String)null);
   }

   public VersionMunger(InputStream in, AbstractDescriptorLoader2 loader, String schemaHelperClassName, Map elementNameChanges, String newNamespaceURI) throws XMLStreamException {
      this(in, loader, schemaHelperClassName, elementNameChanges, false, newNamespaceURI);
   }

   public VersionMunger(InputStream in, AbstractDescriptorLoader2 loader, String schemaHelperClassName, String newNamespaceURI) throws XMLStreamException {
      this(in, loader, schemaHelperClassName, Collections.EMPTY_MAP, false, newNamespaceURI);
   }

   public VersionMunger(InputStream in, AbstractDescriptorLoader2 loader, String schemaHelperClassName, Map elementNameChanges, boolean disableReorder, String newNamespaceURI) throws XMLStreamException {
      super(in, loader);
      this.debug = Debug.getCategory("weblogic.descriptor.versionmunger").isEnabled();
      this.dump = Debug.getCategory("weblogic.descriptor.versionmunger.dump").isEnabled();
      this.elementNameChanges = Collections.EMPTY_MAP;
      this.stack = new Stack();
      this.forceSkipParent = false;
      this.rootElementName = null;
      this.newNamespaceURI = null;
      this.playback = null;
      this.lastEvent = null;
      this.anchorEvent = null;
      this.USE_BUFFER = new Continuation() {
         public int nextToken(int c) throws XMLStreamException {
            assert VersionMunger.this.anchorEvent != null;

            assert VersionMunger.this.playback != null;

            VersionMunger.this.playback.setParent(VersionMunger.this.anchorEvent);
            VersionMunger.this.playback.setSchemaHelper(VersionMunger.this.anchorEvent.helper);
            VersionMunger.this.anchorEvent = null;
            VersionMunger.this.playback = null;
            return c;
         }
      };
      this.SKIP = new Continuation() {
         public int nextToken(int c) throws XMLStreamException {
            if (c == 1) {
               VersionMunger.this.currentEvent.setDiscard();
            }

            return VersionMunger.this.next();
         }
      };
      this.schemaHelper = null;
      this.tranformedNamespace = null;
      this.skippedEvent = null;
      this.setElementNameChanges(elementNameChanges);
      this.absolutePath = loader.getAbsolutePath();
      this.schemaHelperClassName = schemaHelperClassName;
      this.disableReorder = disableReorder;
      this.rootElementName = this.getTopLevelSchemaHelper().getRootElementName();
      this.newNamespaceURI = newNamespaceURI;
      this.initialize();
      this.init(loader);
   }

   protected void pushStartElement(String localName) {
      this.anchorEvent = this.lastEvent;
      this.queueEvent(1, localName);
   }

   protected void pushStartElementLastEvent(String localName) {
      this.anchorEvent = this.lastEvent;
      this.queueEvent(1, localName, this.lastEvent);
   }

   protected void pushCharacters(String s) {
      this.pushCharacters(s.toCharArray());
   }

   protected void pushStartElementWithStackAsParent(String localName) {
      this.anchorEvent = (ReaderEvent2)this.stack.peek();
      this.queueEvent(1, localName, this.anchorEvent);
   }

   protected void pushCharacters(char[] chars) {
      ReaderEvent2 event = (ReaderEvent2)this.stack.peek();
      event.getReaderEventInfo().setCharacters(chars);
   }

   protected void pushEndElement(String localName) {
      if (this.anchorEvent == null) {
         this.anchorEvent = this.currentEvent;
      }

      this.dequeueEvent(localName);
   }

   protected void pushEndElementLastEvent(String localName) {
      this.anchorEvent = this.lastEvent;
      this.dequeueEvent(localName);
   }

   private void dequeueEvent(String localName) {
      if (!this.stack.empty()) {
         this.playback = (ReaderEvent2)this.stack.pop();
      }

      if (!this.helperScopedNames.empty() && this.playback.getReaderEventInfo().getElementName().equals(this.helperScopedNames.peek())) {
         this.helperScopedNames.pop();
         this.helpers.pop();
      }

   }

   protected boolean enableCallbacksOnSchema() {
      return false;
   }

   protected Continuation onStartElement(String localName) {
      return CONTINUE;
   }

   protected Continuation onCharacters(String text) {
      return CONTINUE;
   }

   protected Continuation onEndElement(String localName) {
      return CONTINUE;
   }

   protected Continuation onEndDocument() {
      return CONTINUE;
   }

   public final int next() throws XMLStreamException {
      int r = this._next();
      if (this.debug) {
         Debug.say("** Returning..." + Utils.type2Str(r, this));
      }

      return r;
   }

   public Map getElementNameChanges() {
      return this.elementNameChanges;
   }

   public void setElementNameChanges(Map elementNameChanges) {
      this.elementNameChanges = elementNameChanges;
   }

   protected SchemaHelper getTopLevelSchemaHelper() {
      if (this.schemaHelper == null) {
         try {
            ClassLoader loader = this.getClass().getClassLoader();
            this.schemaHelper = (SchemaHelper)Class.forName(this.schemaHelperClassName, false, loader).newInstance();
         } catch (Exception var2) {
            throw new AssertionError(var2);
         }
      }

      return this.schemaHelper;
   }

   public String getLocalName() {
      String n = super.getLocalName();
      if (this.hasDTD()) {
         String r = (String)this.getElementNameChanges().get(n);
         return r != null ? r : n;
      } else {
         return n;
      }
   }

   public char[] getTextCharacters() {
      char[] c = super.getTextCharacters();
      if (this.hasDTD()) {
         String s = new String(c);
         if ("true".equalsIgnoreCase(s.trim())) {
            return s.toLowerCase(Locale.US).toCharArray();
         }

         if ("false".equalsIgnoreCase(s.trim())) {
            return s.toLowerCase(Locale.US).toCharArray();
         }
      }

      return c;
   }

   protected String getLatestSchemaVersion() {
      return null;
   }

   public String getDtdNamespaceURI() {
      return this.newNamespaceURI;
   }

   protected void transformOldSchema() throws XMLStreamException {
      if (this.currentEvent.getElementName().equals(this.rootElementName)) {
         this.transformNamespace(this.newNamespaceURI, this.currentEvent);
      }

      this.tranformedNamespace = this.newNamespaceURI;
   }

   protected boolean isOldSchema() {
      return this.rootElementName != null && this.newNamespaceURI != null ? this.isOldNamespaceURI(this.getNamespaceURI()) : false;
   }

   protected boolean isOldNamespaceURI(String namespaceURI) {
      return namespaceURI != null && (namespaceURI.equals("http://www.bea.com/ns/weblogic/90") || namespaceURI.equals("http://www.bea.com/ns/weblogic/10.0") || namespaceURI.equals("http://www.bea.com/ns/weblogic/10.0/persistence") || this.newNamespaceURI != null && !namespaceURI.equals(this.newNamespaceURI) && !namespaceURI.startsWith("http://www.w3.org/") && !namespaceURI.startsWith("http://java.sun.com/xml") && !namespaceURI.startsWith("http://xmlns.jcp.org/xml/ns/javaee"));
   }

   private final int _next() throws XMLStreamException {
      this.lastEvent = this.currentEvent;
      if (this.lastEvent == this.skippedEvent) {
         this.lastEvent = this.lastEvent.getParent();
      }

      int next = super.next();
      if (!this.hasDTD()) {
         if (next == 1) {
            if (this.isOldSchema()) {
               this.transformOldSchema();
            } else {
               String verInfo = this.getLatestSchemaVersion();
               if (verInfo != null && this.versionInfo == null) {
                  this.versionInfo = verInfo;
               }
            }
         }

         if (!this.enableCallbacksOnSchema()) {
            return next;
         }
      }

      Continuation c;
      switch (next) {
         case 1:
            c = this.onStartElement(this.getLocalName());
            if (c == this.SKIP) {
               this.skippedEvent = this.currentEvent;
            }
            break;
         case 2:
            c = this.onEndElement(this.getLocalName());
            break;
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            c = CONTINUE;
            break;
         case 4:
            c = this.onCharacters(this.getText());
            break;
         case 8:
            c = this.onEndDocument();
      }

      return c.nextToken(next);
   }

   public String getNamespaceURI() {
      if (this.debug) {
         System.out.println("->getNamespaceURI: usingDTD() =" + this.hasDTD());
      }

      if (this.tranformedNamespace != null) {
         return this.tranformedNamespace;
      } else {
         return this.hasDTD() ? this.getDtdNamespaceURI() : super.getNamespaceURI();
      }
   }

   public String getNamespaceURI(int index) {
      String originalURI = super.getNamespaceURI(index);
      String prefix = super.getNamespacePrefix(index);
      return this.newNamespaceURI != null && this.isOldNamespaceURI(originalURI) && !this.isGrandChildNamespaceURI(prefix) ? this.newNamespaceURI : originalURI;
   }

   public String getNamespaceURI(String prefix) {
      String originalURI = super.getNamespaceURI(prefix);
      return this.newNamespaceURI != null && this.isOldNamespaceURI(originalURI) && !this.isGrandChildNamespaceURI(prefix) ? this.newNamespaceURI : originalURI;
   }

   private boolean isGrandChildNamespaceURI(String prefix) {
      boolean found = false;
      Iterator i = this.root.getChildren().iterator();

      while(i.hasNext()) {
         ReaderEvent2 child = (ReaderEvent2)i.next();
         if (child.getChildren().isEmpty()) {
            return false;
         }

         found = this.foundGrandchildNameSpacePrefix(prefix, child);
         if (found) {
            break;
         }
      }

      return found;
   }

   private boolean foundGrandchildNameSpacePrefix(String prefix, ReaderEvent2 child) {
      boolean found = false;
      Iterator j = child.getChildren().iterator();

      while(j.hasNext()) {
         ReaderEvent2 grandChild = (ReaderEvent2)j.next();
         ReaderEventInfo grandChildInfo = grandChild.getReaderEventInfo();
         if (prefix != null && prefix.equals(grandChildInfo.getPrefix())) {
            return true;
         }

         found = this.foundGrandchildNameSpacePrefix(prefix, grandChild);
         if (found) {
            break;
         }
      }

      return found;
   }

   protected void initialize() {
   }

   private void init(AbstractDescriptorLoader2 loader) throws XMLStreamException {
      this.consumeInputStream();
      if (!this.hasDTD() && !this.isOldSchema) {
         this.transformNamespace(this.getDtdNamespaceURI(), this.root);
      }

      if (this.dump) {
         this.root.toXML(System.out);
      }

   }

   protected void transformNamespace(String uri, ReaderEvent2 event) {
      this.transformNamespace(uri, event, (String)null);
   }

   protected void transformNamespace(String uri, ReaderEvent2 event, String oldUri) {
      int idx = 0;
      boolean fnd = false;
      ReaderEventInfo.Namespaces namespaces = event.getReaderEventInfo().getNamespaces();

      for(int i = 0; !fnd && i < namespaces.getNamespaceCount(); ++i) {
         String namespaceURI = namespaces.getNamespaceURI(i);
         if (this.isOldNamespaceURI(namespaceURI) || namespaceURI.equals(uri) || namespaceURI.equals(oldUri)) {
            idx = i;
            fnd = true;
         }
      }

      String prefix = event.getReaderEventInfo().getNamespaces().getNamespacePrefix(idx);
      ReaderEventInfo var10002 = event.getReaderEventInfo();
      var10002.getClass();
      ReaderEventInfo.Namespaces oldNSs = new ReaderEventInfo.Namespaces(var10002);
      oldNSs.copy(event.getReaderEventInfo().getNamespaces());
      event.getReaderEventInfo().clearNamespaces();
      if (oldNSs.getNamespaceCount() < 2) {
         this.setNamespace(prefix, uri, event);
      } else {
         event.getReaderEventInfo().setNamespaceCount(oldNSs.getNamespaceCount());

         for(int i = 0; i < oldNSs.getNamespaceCount(); ++i) {
            String p = oldNSs.getNamespacePrefix(i);
            if ((p != null || prefix != null) && (p == null || !p.equals(prefix))) {
               this.setNamespace2(p, oldNSs.getNamespaceURI(i), event, false);
            } else {
               this.setNamespace2(prefix, uri, event, true);
            }
         }

      }
   }

   protected void setNamespace2(String prefix, String nameSpaceURI, ReaderEvent2 event, boolean toSetPrefix) {
      ReaderEventInfo currentInfo = event.getReaderEventInfo();
      if (prefix != null && toSetPrefix) {
         currentInfo.setPrefix(prefix);
      }

      currentInfo.setNamespaceURI(prefix, nameSpaceURI);

      ReaderEventInfo childInfo;
      for(Iterator i = event.getChildren().iterator(); i.hasNext(); childInfo.setNamespaceURI(prefix, nameSpaceURI)) {
         ReaderEvent2 child = (ReaderEvent2)i.next();
         childInfo = child.getReaderEventInfo();
         if (prefix != null && toSetPrefix) {
            childInfo.setPrefix(prefix);
         }
      }

   }

   protected boolean lookForDTD(AbstractDescriptorLoader2 loader) throws XMLStreamException {
      InputStream in = null;

      try {
         in = loader.getInputStream();
      } catch (IOException var16) {
         return false;
      }

      if (in == null) {
         return false;
      } else {
         XMLStreamReader r = xiFactory.createXMLStreamReader(in);

         try {
            for(int i = 0; i < 10 && r.hasNext(); ++i) {
               boolean var5;
               if (r.isStartElement()) {
                  var5 = false;
                  return var5;
               }

               if (r.next() == 11) {
                  var5 = true;
                  return var5;
               }
            }

            boolean var18 = false;
            return var18;
         } finally {
            try {
               r.close();
               in.close();
            } catch (IOException var15) {
            }

         }
      }
   }

   private void consumeInputStream() throws XMLStreamException {
      while(this.next() != 8) {
      }

   }

   void queueEvent(int eventType, String elementName) {
      this.queueEvent(eventType, elementName, this.currentEvent.getParent());
   }

   void queueEvent(int eventType, String elementName, ReaderEvent2 parent) {
      SchemaHelper schemaHelper = null;
      if (parent == this.skippedEvent) {
         schemaHelper = parent.getSchemaHelper();
         parent = parent.getParent();
      }

      ReaderEvent2 d = new ReaderEvent2(eventType, elementName, parent, this.currentEvent.getReaderEventInfo().getLocation());
      parent.getChildren().add(d);
      ReaderEventInfo info = d.getReaderEventInfo();
      ReaderEventInfo parentInfo = parent.getReaderEventInfo();
      if (parentInfo.getPrefix() != null) {
         info.setPrefix(parentInfo.getPrefix());
      }

      int i;
      if (parentInfo.getNamespaceCount() > 0) {
         info.setNamespaceCount(parentInfo.getNamespaceCount());

         for(i = 0; i < parentInfo.getNamespaceCount(); ++i) {
            info.setNamespaceURI(parentInfo.getNamespacePrefix(i), parentInfo.getNamespaceURI(i));
         }
      }

      if (parentInfo.getAttributeCount() > 0 && !this.forceSkipParent) {
         info.setAttributeCount(parentInfo.getAttributeCount());

         for(i = 0; i < parentInfo.getAttributeCount(); ++i) {
            info.setAttributeValue(parentInfo.getAttributeValue(i), parentInfo.getAttributeNamespace(i), parentInfo.getAttributeLocalName(i));
         }
      }

      if (!this.helpers.empty()) {
         d.setSchemaHelper((SchemaHelper)this.helpers.peek());
      } else if (schemaHelper != null) {
         d.setSchemaHelper(schemaHelper);
      } else {
         d.setSchemaHelper(parent.getSchemaHelper());
      }

      this.stack.push(d);
   }

   public ReaderEvent2 getQueuedEvent(int eventType, Object data) {
      return null;
   }

   protected void pushReaderEvent(ReaderEvent2 event) {
   }

   protected void pushReaderEvents(List l) {
   }

   protected void replaceSlashWithPeriod(boolean waitAfterPoundSign) {
      boolean waitToReplace = waitAfterPoundSign;
      boolean replaced = false;
      char[] charName = this.currentEvent.getReaderEventInfo().getCharacters();
      if (charName != null && charName.length > 0) {
         for(int i = 0; i < charName.length; ++i) {
            if (charName[i] == '#') {
               waitToReplace = false;
            }

            if (!waitToReplace && charName[i] == '/') {
               charName[i] = '.';
               replaced = true;
            }
         }

         if (replaced) {
            this.currentEvent.getReaderEventInfo().setCharacters(charName);
         }
      }

   }

   protected void checkAndUpdateVersionAttribute() {
      String latestVersion = this.getLatestSchemaVersion();
      if (latestVersion != null) {
         ReaderEventInfo info = this.currentEvent.getReaderEventInfo();
         int count = info.getAttributeCount();
         int counter = 0;

         boolean versionAttributeFound;
         for(versionAttributeFound = false; counter < count && !versionAttributeFound; ++counter) {
            if ("version".equals(info.getAttributeLocalName(counter))) {
               versionAttributeFound = true;
               String versionValue = info.getAttributeValue(counter);
               if (versionValue == null || versionValue != null && !versionValue.equals(latestVersion)) {
                  info.setAttributeValue(latestVersion, counter);
                  if (this.debug) {
                     Debug.say("application version is reset to " + latestVersion + " for namespace " + info.getNamespaceURI(0));
                  }
               }
            }
         }

         if (!versionAttributeFound) {
            info.setAttributeValue(latestVersion, (String)null, "version");
            if (this.debug) {
               Debug.say("application version is set to " + latestVersion + " for namespace " + info.getNamespaceURI(0));
            }
         }

      }
   }

   public static void main(String[] args) throws Exception {
      if (args.length == 0) {
         usage();
         System.exit(-1);
      }

      String ddPath = args[0];
      String planPath = args.length > 1 && args[1].endsWith("plan.xml") ? args[1] : null;
      File altDD = new File(ddPath);
      InputStream in = null;
      File configDir = new File(".");
      DeploymentPlanBean plan = null;
      String moduleName = args.length > 2 ? args[2] : null;
      final String schemaHelperClassName = planPath == null && args.length > 1 ? args[1] : null;
      final String nameSpace = planPath == null && args.length > 2 ? args[2] : null;
      AbstractDescriptorLoader2 planLoader;
      if (planPath != null) {
         if (moduleName == null) {
            usage();
            System.exit(-1);
         }

         planLoader = new AbstractDescriptorLoader2(new File(planPath), planPath) {
         };
         plan = (DeploymentPlanBean)planLoader.loadDescriptorBean();
      }

      planLoader = new AbstractDescriptorLoader2(altDD, configDir, plan, moduleName, ddPath) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return new VersionMunger(is, this, schemaHelperClassName) {
               private boolean inWeb;
               private boolean hasSetContextRoot;

               public String getDtdNamespaceURI() {
                  return nameSpace != null ? nameSpace : "http://java.sun.com/xml/ns/j2ee";
               }

               protected Continuation onStartElement(String localName) {
                  if (localName.equals("skip")) {
                     return this.SKIP;
                  } else {
                     if ("web".equals(localName)) {
                        this.inWeb = true;
                        this.hasSetContextRoot = false;
                     } else if ("context-root".equals(localName)) {
                        this.hasSetContextRoot = true;
                     }

                     return CONTINUE;
                  }
               }

               protected Continuation onEndElement(String localName) {
                  if (localName.equals("skip")) {
                     return this.SKIP;
                  } else {
                     if ("web".equals(localName)) {
                        assert this.inWeb;

                        this.inWeb = false;
                        if (!this.hasSetContextRoot) {
                           this.pushStartElement("context-root");
                           this.pushCharacters("CONTEXT_ROOT");
                           this.pushEndElement("context-root");
                           this.pushEndElement("web");
                           return this.USE_BUFFER;
                        }
                     }

                     return CONTINUE;
                  }
               }
            };
         }
      };
      System.out.println("stamp out version munger...");
      System.out.flush();
      DescriptorBean db = planLoader.loadDescriptorBean();
      Descriptor d = db.getDescriptor();
      d.toXML(System.out);
   }

   private static void usage() {
      System.out.print("java weblogic.application.descriptor.VersionMunger <dd-filename> <schema-helper-name> <name-space>|| <dd-filename> <plan-filename> <module-name> <schema-helper-name>");
   }

   protected interface Continuation {
      int nextToken(int var1) throws XMLStreamException;
   }
}
