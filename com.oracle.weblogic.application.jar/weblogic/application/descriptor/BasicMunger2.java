package weblogic.application.descriptor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import weblogic.descriptor.BeanCreationInterceptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorException;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.descriptor.wl.VariableAssignmentBean;
import weblogic.j2ee.descriptor.wl.VariableBean;
import weblogic.j2ee.descriptor.wl.VariableDefinitionBean;
import weblogic.logging.Loggable;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.io.UnsyncByteArrayOutputStream;
import weblogic.xml.stax.XMLStreamInputFactory;

public class BasicMunger2 extends DebugStreamReaderDelegate implements BeanCreationInterceptor {
   protected boolean debug;
   protected boolean merge;
   protected boolean dbgValue;
   protected static final XMLInputFactory xiFactory = new XMLStreamInputFactory();
   private boolean hasDTD;
   private String dtdNamespaceURI;
   protected ReaderEvent2 root;
   protected ReaderEvent2 currentEvent;
   protected Stack helpers;
   protected Stack helperScopedNames;
   private InputStream in;
   private String absolutePath;
   protected String versionInfo;
   protected boolean disableReorder;
   private static final String TRUE_STR = "true";
   private static final String FALSE_STR = "false";
   HashMap valueTable;
   HashMap locationTable;
   HashMap symbolTable;

   public BasicMunger2(InputStream in, String absolutePath) throws XMLStreamException {
      super(xiFactory.createXMLStreamReader(in));
      this.debug = Debug.getCategory("weblogic.descriptor.munger").isEnabled();
      this.merge = Debug.getCategory("weblogic.descriptor.merge").isEnabled();
      this.dbgValue = Debug.getCategory("weblogic.descriptor.valuetable").isEnabled();
      this.hasDTD = false;
      this.helpers = new Stack();
      this.helperScopedNames = new Stack();
      this.in = in;
      this.absolutePath = absolutePath;
      Location l = super.getLocation();
      this.root = new ReaderEvent2(7, new MyLocation(l));
      this.currentEvent = this.root;
   }

   public BasicMunger2(InputStream in, AbstractDescriptorLoader2 l) throws XMLStreamException {
      this(in, l.getAbsolutePath());
      this.initValueTable(l.getDeploymentPlan(), l.getModuleName(), l.getDocumentURI());
   }

   String getAbsolutePath() {
      return this.absolutePath;
   }

   public ReaderEvent2 getCurrentEvent() {
      return this.currentEvent;
   }

   public String getDtdNamespaceURI() {
      return null;
   }

   public boolean hasDTD() {
      return this.hasDTD;
   }

   public PlaybackReader getPlaybackReader() throws XMLStreamException {
      PlaybackReader p = new PlaybackReader(this.root, this.getAbsolutePath());
      if (this.hasDTD()) {
         p.setDtdNamespaceURI(this.getDtdNamespaceURI());
      }

      return p;
   }

   public void initDtdText(String dtdText) {
   }

   public void toXML(PrintStream out) {
      this.root.toXML(out);
   }

   public void logError(List errors) {
      if (!errors.isEmpty()) {
         MungerLogger.logDescriptorParseError(StackTraceUtils.throwable2StackTrace(new DescriptorException("VALIDATION PROBLEMS WERE FOUND FOR: " + this.getAbsolutePath(), errors)));
      }
   }

   protected void setNamespace(String prefix, String nameSpaceURI, ReaderEvent2 event) {
      ReaderEventInfo currentInfo = event.getReaderEventInfo();
      if (prefix != null) {
         currentInfo.setPrefix(prefix);
      }

      currentInfo.setNamespaceCount(1);
      currentInfo.setNamespaceURI(prefix, nameSpaceURI);
      Iterator i = event.getChildren().iterator();

      while(i.hasNext()) {
         ReaderEvent2 child = (ReaderEvent2)i.next();
         ReaderEventInfo childInfo = child.getReaderEventInfo();
         if (prefix != null) {
            childInfo.setPrefix(prefix);
         }

         childInfo.setNamespaceCount(1);
         childInfo.setNamespaceURI(prefix, nameSpaceURI);
      }

   }

   public PlaybackReader merge(BasicMunger2 otherMunger, DescriptorBean otherBean) throws XMLStreamException {
      String prefix = this.getPrefix();
      String nameSpaceURI = this.getNamespaceURI();
      this.root.merge(otherMunger.root, -1, false);
      this.root.orderChildren();
      this.setNamespace(prefix, nameSpaceURI, this.root);
      return new PlaybackReader(this.root, this.getAbsolutePath());
   }

   private String checkNewBeanCreationPattern(String xpath) {
      int endBrIndex = xpath.lastIndexOf("]");
      if (endBrIndex == -1) {
         return xpath;
      } else {
         int strBrIndex = xpath.lastIndexOf("[");
         int elementSep = xpath.lastIndexOf("/");
         String leafElement = xpath.substring(elementSep + 1, xpath.length());
         String strInsideBrackets = xpath.substring(strBrIndex + 1, endBrIndex);
         return strInsideBrackets.indexOf(leafElement + "=") != -1 ? xpath.substring(0, elementSep + 1) : xpath;
      }
   }

   public PlaybackReader mergeDescriptorBeanWithPlan(DescriptorBean bean) throws XMLStreamException {
      ArrayList xData = new ArrayList();
      if (this.valueTable == null) {
         return new PlaybackReader(this.root, this.getAbsolutePath());
      } else {
         Iterator j = this.valueTable.entrySet().iterator();

         while(j.hasNext()) {
            Map.Entry e = (Map.Entry)j.next();
            String xpath = (String)e.getKey();
            VariableAssignment vassignment = (VariableAssignment)e.getValue();
            String val = vassignment.getValue();
            int operation = vassignment.getOperation();
            if (val != null) {
               String xpathForLocation = xpath;
               xpath = this.checkNewBeanCreationPattern(xpath);
               SchemaHelper h = this.root.findRootSchemaHelper();
               ReaderEvent2 parent = new ReaderEvent2(7, (Location)this.getLocationTable().get(xpathForLocation));
               ReaderEvent2 x = new ReaderEvent2(new StringBuffer(xpath), parent, h, val, (Location)this.getLocationTable().get(xpathForLocation));
               parent.getChildren().add(x);
               xData.add(new ReaderEventOperation(x, operation));
            }
         }

         SchemaHelper helper = this.root.findRootSchemaHelper();
         Iterator i = xData.iterator();

         while(i.hasNext()) {
            ReaderEventOperation reo = (ReaderEventOperation)i.next();
            ReaderEvent2 xd = reo.getReaderEvent();
            int operation = reo.getOperation();
            xd.validate(helper);
            if (this.merge) {
               System.out.println("\nBasicMunger: ReaderEvent to merge into root from plan: ");
               xd.toXML(System.out);
               System.out.println("----------- end ReaderEvent --------");
            }

            this.root.merge(xd.getParent(), operation, true);
            if (this.merge) {
               System.out.println("\nBasicMunger: root ReaderEvent after merge from plan: ");
               this.root.toXML(System.out);
               System.out.println("----------- end ReaderEvent --------");
            }
         }

         this.root.orderChildren();
         return new PlaybackReader(this.root, this.getAbsolutePath());
      }
   }

   private void dump(ReaderEvent2 root) {
      System.out.println("****** root Element name " + root.getElementName() + " size of children " + root.getChildren().size());
      Iterator i = root.getChildren().iterator();

      while(i.hasNext()) {
         ReaderEvent2 target = (ReaderEvent2)i.next();
         switch (target.getEventType()) {
            case 1:
               System.out.println("*** child " + target.getElementName() + " child.getXpath() " + target.getXpath());
               this.dump(target);
            case 5:
            case 11:
         }
      }

   }

   public PlaybackReader mergeDescriptorBeanWithPlan(DeploymentPlanBean plan, String moduleName, String documentURI) throws XMLStreamException {
      this.initValueTable(plan, moduleName, documentURI);
      return this.mergeDescriptorBeanWithPlan((DescriptorBean)null);
   }

   public DescriptorBean beanCreated(DescriptorBean bean, DescriptorBean parent) {
      SchemaHelper h = ((AbstractDescriptorBean)bean)._getSchemaHelper2();
      this.currentEvent.setSchemaHelper(h);
      this.helpers.push(h);
      this.helperScopedNames.push(this.getLocalName());
      if (this.debug) {
         System.out.println("   set schema helper for: " + this.currentEvent.getSchemaHelper());
      }

      return bean;
   }

   String getHelperScopedNames() {
      return (String)this.helperScopedNames.peek();
   }

   protected SchemaHelper getTopLevelSchemaHelper() {
      return null;
   }

   public void close() throws XMLStreamException {
      super.close();
      if (this.in != null) {
         try {
            this.in.close();
         } catch (Exception var2) {
         }
      }

      this.in = null;
   }

   public Location getLocation() {
      Location l = new MyLocation(super.getLocation());
      if (this.debug) {
         System.out.println("->getLocation: " + l);
      }

      return l;
   }

   public int next() throws XMLStreamException {
      int next = super.next();
      if (this.debug) {
         System.out.println("->next = " + Utils.type2Str(next, this));
      }

      switch (next) {
         case 1:
            this._onStartElement();
            break;
         case 2:
            this._onEndElement();
         case 3:
         case 6:
         case 7:
         case 9:
         case 10:
         default:
            break;
         case 4:
            this._onCharacters();
            break;
         case 5:
            this._onComment();
            break;
         case 8:
            this._onEndDocument();
            break;
         case 11:
            this._onDTD();
            next = this.next();
      }

      return next;
   }

   private void _onDTD() {
      this.hasDTD = true;
      this.versionInfo = "DTD";
      this.initDtdText(this.getText());
   }

   private void _onStartElement() {
      ReaderEvent2 d = new ReaderEvent2(this.getEventType(), this.getLocalName(), this.currentEvent, super.getLocation());
      String characterEncodingScheme = this.getCharacterEncodingScheme();
      if (characterEncodingScheme != null) {
         d.getReaderEventInfo().setCharacterEncodingScheme(characterEncodingScheme.toUpperCase(Locale.US));
      }

      int propIndex;
      if (this.getTopLevelSchemaHelper() != null) {
         if (d.getElementName().equals(this.getTopLevelSchemaHelper().getRootElementName())) {
            d.setSchemaHelper(this.getTopLevelSchemaHelper());
            this.currentEvent.addChild(d);
         } else {
            ReaderEvent2 adoptiveParent = this.currentEvent;
            SchemaHelper parentSchemaHelper = this.currentEvent.helper;
            if (this.debug) {
               System.out.println("using parent [" + adoptiveParent.getElementName() + "] to set helper for " + d.getElementName() + " to " + parentSchemaHelper);
            }

            try {
               propIndex = parentSchemaHelper.getPropertyIndex(d.getElementName());
            } catch (MissingRootElementException var9) {
               Loggable l = MungerLogger.logMissingRootElementLoggable(this.getTopLevelSchemaHelper().getRootElementName(), this.getAbsolutePath());
               throw new MissingRootElementException(l.getMessage());
            }

            if (this.debug) {
               System.out.println("helper.getPropertyIndex = " + propIndex + " is bean? " + parentSchemaHelper.isBean(propIndex));
            }

            if (parentSchemaHelper.isBean(propIndex)) {
               d.setSchemaHelper(parentSchemaHelper.getSchemaHelper(propIndex));
            } else {
               d.setSchemaHelper(parentSchemaHelper);
            }

            if (propIndex >= 0) {
               boolean reorder = true;
               if (!this.hasDTD() && this.disableReorder) {
                  reorder = false;
               }

               adoptiveParent.adopt(d, parentSchemaHelper, reorder);
            } else {
               adoptiveParent.addChild(d);
            }

            if (parentSchemaHelper.isBean(propIndex)) {
               d.setSchemaHelper(parentSchemaHelper.getSchemaHelper(propIndex));
            }
         }
      } else {
         this.currentEvent.addChild(d);
      }

      this.currentEvent = d;
      ReaderEventInfo currentInfo = d.getReaderEventInfo();
      if (this.getPrefix() != null) {
         currentInfo.setPrefix(this.getPrefix());
      }

      int i;
      if (this.getNamespaceCount() > 0) {
         currentInfo.setNamespaceCount(this.getNamespaceCount());

         for(i = 0; i < this.getNamespaceCount(); ++i) {
            currentInfo.setNamespaceURI(this.getNamespacePrefix(i), this.getNamespaceURI(i));
         }
      }

      if (this.getAttributeCount() > 0) {
         currentInfo.setAttributeCount(this.getAttributeCount());

         for(i = 0; i < this.getAttributeCount(); ++i) {
            currentInfo.setAttributeValue(this.getAttributeValue(i), this.getAttributeNamespace(i), this.getAttributeLocalName(i));
         }
      }

      String elementName = currentInfo.getElementName();
      if (this.getTopLevelSchemaHelper() != null && this.getTopLevelSchemaHelper().getRootElementName().equals(elementName) && !this.hasDTD() && ("application".equals(elementName) || "web-app".equals(elementName) || "ejb-jar".equals(elementName) || "application-client".equals(elementName) || "webservices".equals(elementName) || "permissions".equals(elementName) || "persistence".equals(elementName))) {
         propIndex = currentInfo.getAttributeCount();
         int counter = 0;
         String versionValue = null;

         for(boolean versionAttributeFound = false; counter < propIndex && !versionAttributeFound; ++counter) {
            if ("version".equals(currentInfo.getAttributeLocalName(counter))) {
               versionAttributeFound = true;
               versionValue = currentInfo.getAttributeValue(counter);
               if (this.debug) {
                  System.out.println("version:  " + versionValue);
               }
            }
         }

         if (versionValue == null) {
            MungerLogger.logMissingVersionAttribute(elementName, this.getAbsolutePath());
         }
      }

      if (this.debug) {
         System.out.println("++ onStartElement: " + this.getLocalName());
      }

      if (!this.helpers.empty()) {
         this.currentEvent.setSchemaHelper((SchemaHelper)this.helpers.peek());
      }

   }

   private void _onCharacters() {
      String s = super.getText();
      char[] chars = null;
      if (s != null) {
         if ("true".equalsIgnoreCase(s.trim())) {
            chars = s.toLowerCase(Locale.US).toCharArray();
         } else if ("false".equalsIgnoreCase(s.trim())) {
            chars = s.toLowerCase(Locale.US).toCharArray();
         } else {
            chars = s.toCharArray();
         }
      }

      this.currentEvent.getReaderEventInfo().appendCharacters(chars);
   }

   private void _onComment() {
      if (!this.isWhiteSpace()) {
         this.currentEvent.getReaderEventInfo().setComments(super.getText());
      }

   }

   private void _onEndElement() {
      if (this.debug) {
         System.out.println("-- onEndElement: local name = " + this.getLocalName());
         System.out.println("  currentEvent.getElementName : " + this.currentEvent.getReaderEventInfo().getElementName());
         System.out.println("  new scope: " + this.currentEvent.getParent());
      }

      if (!this.helperScopedNames.empty() && this.currentEvent.getReaderEventInfo().getElementName().equals(this.helperScopedNames.peek())) {
         this.helperScopedNames.pop();
         if (!this.helpers.empty()) {
            this.helpers.pop();
         }
      }

      this.currentEvent = this.currentEvent.getParent() == null ? this.currentEvent : this.currentEvent.getParent();
   }

   private void _onEndDocument() {
   }

   protected void orderChildren() {
      this.root.orderChildren();
   }

   private void initValueTable(final DeploymentPlanBean plan, String moduleName, String documentURI) {
      if (plan != null && moduleName != null && documentURI != null) {
         boolean match = false;
         ModuleOverrideBean[] mos = plan.getModuleOverrides();

         for(int i = 0; i < mos.length; ++i) {
            if (this.dbgValue) {
               System.out.println("initValueTable: mos[i].getModuleType() = " + mos[i].getModuleType() + ",\n mos[i].getModuleName() = " + mos[i].getModuleName());
            }

            if (mos[i].getModuleName().equals(moduleName)) {
               ModuleDescriptorBean[] mdb = mos[i].getModuleDescriptors();

               for(int j = 0; j < mdb.length; ++j) {
                  if (mdb[j].getUri().equals(documentURI)) {
                     match = true;
                     VariableAssignmentBean[] vabs = mdb[j].getVariableAssignments();
                     if (this.dbgValue) {
                        System.out.println("initValueTable: vabs.length = " + vabs.length);
                     }

                     for(int k = 0; k < vabs.length; ++k) {
                        if (this.dbgValue) {
                           System.out.println("initValueTable: " + vabs[k].getXpath() + ", " + vabs[k].getName());
                        }

                        VariableAssignment tableEntry = new VariableAssignment(vabs[k].getName(), (String)this.getSymbolTable(plan).get(vabs[k].getName()), vabs[k].getOperation());
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

               if (this.dbgValue) {
                  System.out.println("\n");
               }
            }
         }

         if (match) {
            MungerLogger.logValidPlanMerged(moduleName, documentURI);
            if (this.dbgValue) {
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
            if (this.dbgValue) {
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

   String getVersionInfo() {
      return this.versionInfo;
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
      System.out.print("\nxpath = " + sb + "\nresults in:\n");
      UnsyncByteArrayOutputStream os = new UnsyncByteArrayOutputStream();
      (new ReaderEvent2(sb, new ReaderEvent2(7, l), (SchemaHelper)null, "val1", l)).toXML(new PrintStream(os));
      byte[] bytes = os.toRawBytes();
      InputStream in = null;
      String path = null;
      if (args.length == 0) {
         in = new ByteArrayInputStream(bytes);
         path = "local buff";
      } else {
         File f = new File(args[0]);
         in = new FileInputStream(f);
         path = args[0];
      }

      System.out.println("stamp out munger...");
      System.out.flush();
      BasicMunger2 reader = new BasicMunger2((InputStream)in, path);
      System.out.println("hand munger to descriptor manger...");
      System.out.flush();
      (new DescriptorManager()).createDescriptor(reader).toXML(System.out);
   }

   private static void usage() {
      System.err.println("usage: java weblogic.application.descriptor.BasicMunger2 <descriptor file name>");
      System.exit(0);
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
         return BasicMunger2.this.getAbsolutePath() + ":" + this.l.getLineNumber() + ":" + this.l.getColumnNumber();
      }

      public String getSystemId() {
         return this.l.getSystemId();
      }
   }

   private static class ReaderEventOperation {
      ReaderEvent2 ev;
      int operation;

      ReaderEventOperation(ReaderEvent2 ev, int operation) {
         this.ev = ev;
         this.operation = operation;
      }

      ReaderEvent2 getReaderEvent() {
         return this.ev;
      }

      int getOperation() {
         return this.operation;
      }
   }
}
