package weblogic.management.provider.internal.federatedconfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

public class FederatedConfigFragmentsProcessor {
   private static final String XMLNS = "xmlns";
   private static final String MODE = " mode='final' ";
   static final String DOMAIN_NAMESPACE_URI = "http://xmlns.oracle.com/weblogic/domain";
   static final String JMS_NAMESPACE_URI = "http://xmlns.oracle.com/weblogic/weblogic-jms";
   static final String JDBC_NAMESPACE_URI = "http://xmlns.oracle.com/weblogic/jdbc-data-source";
   static final String DIAGNOSTICS_NAMESPACE_URI = "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   private static final String NAME = "name";
   static final String XSL_PREFIX = "xsl";
   static final String EXSL_PREFIX = "exsl";
   private static final String STYLESHEET_PREAMBLE = "<?xml version='1.0' encoding='UTF-8'?>";
   private static final String STYLESHEET_ROOT_FORMAT = "<xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns:exsl='http://exslt.org/common' xmlns:__ns='http://www.oracle.com/weblogic/__nsLookup' version='1.0' %s>";
   private static final String STYLESHEET_TAIL = "</xsl:stylesheet>";
   private static final String APPLY_TEMPLATES = "<xsl:apply-templates " + mode() + "select=\"@* | node()\" />";
   static final String IDENTITY_TRANSFORMATION_MATCH_EXPR = "@* | node()";
   private static final String IDENTITY_TRANSFORMATION_WITH_ROOT;
   private static final String CONFIG_FRAGMENT_NAMESPACE_URI = "http://xmlns.oracle.com/weblogic/domain-fragment";
   private static final QName COMBINE_MODE_QNAME;
   private static final QName DOMAIN_FRAGMENT_QNAME;
   private static final QName DOMAIN_QNAME;
   private static final String JMS_FRAGMENT_NAMESPACE_URI = "http://xmlns.oracle.com/weblogic/weblogic-jms-fragment";
   private static final QName JMS_COMBINE_MODE_QNAME;
   private static final QName JMS_FRAGMENT_QNAME;
   private static final QName JMS_QNAME;
   private static final String JDBC_FRAGMENT_NAMESPACE_URI = "http://xmlns.oracle.com/weblogic/jdbc-data-source-fragment";
   private static final QName JDBC_COMBINE_MODE_QNAME;
   private static final QName JDBC_FRAGMENT_QNAME;
   private static final QName JDBC_QNAME;
   private static final String DIAGNOSTICS_FRAGMENT_NAMESPACE_URI = "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-fragment";
   private static final QName DIAGNOSTICS_COMBINE_MODE_QNAME;
   private static final QName DIAGNOSTICS_FRAGMENT_QNAME;
   private static final QName DIAGNOSTICS_QNAME;
   private final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
   private final NamespaceManager namespaceManager = new NamespaceManager();
   private NamespaceInfo topLevelNamespace = null;
   private Map requiredPaths = new HashMap();
   private TreeNode currentElement = null;

   static String mode() {
      return " mode='final' ";
   }

   static boolean isDebug() {
      return FederatedConfigImpl.isDebug();
   }

   static void debug(String msg) {
      FederatedConfigImpl.debug(msg);
   }

   public Source getTransformation(File searchPathElement, final Iterator xmlFilePaths, String descriptorFileName) throws IOException, XMLStreamException {
      return this.getTransformationViaReaders(searchPathElement.toURI(), new Iterator() {
         public boolean hasNext() {
            return xmlFilePaths.hasNext();
         }

         public Reader next() {
            try {
               CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
               Reader reader = new InputStreamReader(new FileInputStream((File)xmlFilePaths.next()), decoder);
               return new BufferedReader(reader);
            } catch (IOException var3) {
               throw new RuntimeException(var3);
            }
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      }, descriptorFileName);
   }

   public Source getTransformation(File searchPathElement, Iterator xmlFilePaths) throws IOException, XMLStreamException {
      return this.getTransformation(searchPathElement, xmlFilePaths, (String)null);
   }

   Source getTransformationViaReaders(URI searchPathElementURI, Iterator xmlReaders, String descriptorFileName) throws IOException, XMLStreamException {
      List transformations = new ArrayList();
      AddedContentManager addedContentManager = new AddedContentManager();
      transformations.add(IDENTITY_TRANSFORMATION_WITH_ROOT);

      while(xmlReaders.hasNext()) {
         FragmentProcessor np = new FragmentProcessor(addedContentManager);
         Reader reader = (Reader)xmlReaders.next();
         np.process(this.xmlInputFactory.createXMLStreamReader(reader));
         transformations.addAll(np.transformations());
      }

      StringBuilder stylesheet = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?>");
      String stylesheetRoot = String.format("<xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns:exsl='http://exslt.org/common' xmlns:__ns='http://www.oracle.com/weblogic/__nsLookup' version='1.0' %s>", this.namespaceManager.declarations());
      stylesheet.append(stylesheetRoot).append(FederatedConfigUtils.LINE_SEP);
      this.processForDuplicateNodes(this.requiredPaths);
      stylesheet.append(this.addTransformationsToEnsureRequiredElements(this.requiredPaths));
      Iterator var8 = transformations.iterator();

      String systemID;
      while(var8.hasNext()) {
         systemID = (String)var8.next();
         stylesheet.append(systemID).append(FederatedConfigUtils.LINE_SEP);
      }

      var8 = addedContentManager.additions().iterator();

      while(var8.hasNext()) {
         NewContent newContent = (NewContent)var8.next();
         stylesheet.append(this.formatNewContentTransformation(newContent.xPath, newContent.content.toString()));
      }

      stylesheet.append("</xsl:stylesheet>");
      URI systemIDURI = searchPathElementURI;

      try {
         systemIDURI = new URI("virtual", "//" + systemIDURI.getPath() + "-generated.xslt", (String)null);
      } catch (URISyntaxException var10) {
         throw new IOException("URISyntax exception: " + var10.getMessage());
      }

      systemID = systemIDURI.toASCIIString();
      if (isDebug()) {
         debug("Content of " + systemID + FederatedConfigUtils.LINE_SEP + stylesheet.toString());
      }

      return new StreamSource(new StringReader(stylesheet.toString()), systemID);
   }

   String getPrefix(String namespaceURI) {
      return this.namespaceManager.getPrefix(namespaceURI);
   }

   private String addTransformationsToEnsureRequiredElements(Map requiredPaths) {
      RequiredElementsTransformationGenerator gen = new RequiredElementsTransformationGenerator(requiredPaths, this, this.namespaceManager.lookupMapForStylesheet());
      return gen.run();
   }

   private void processForDuplicateNodes(Map requiredPathsMap) {
      if (requiredPathsMap != null && !requiredPathsMap.isEmpty()) {
         Iterator var2 = requiredPathsMap.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry requiredForGivenRoot = (Map.Entry)var2.next();
            TreeNode requiredPaths = (TreeNode)requiredForGivenRoot.getValue();
            this.combineDuplicateNodes(requiredPaths.getChildren());
         }

      }
   }

   private void combineDuplicateNodes(List children) {
      if (children != null && !children.isEmpty()) {
         int firstIdx = 0;

         while(true) {
            TreeNode secondChild;
            while(firstIdx < children.size()) {
               TreeNode firstChild = (TreeNode)children.get(firstIdx);
               secondChild = null;
               int secondIdx = firstIdx;

               for(int j = firstIdx + 1; j < children.size(); ++j) {
                  secondChild = (TreeNode)children.get(j);
                  if (((Step)firstChild.value()).equals(secondChild.value())) {
                     secondIdx = j;
                  }
               }

               if (firstIdx != secondIdx) {
                  List firstGrandChildren = firstChild.getChildren();
                  secondChild = (TreeNode)children.get(secondIdx);
                  Iterator var7 = firstGrandChildren.iterator();

                  while(var7.hasNext()) {
                     TreeNode firstGrandChild = (TreeNode)var7.next();
                     secondChild.addChild(firstGrandChild.value());
                  }

                  if (isDebug()) {
                     debug("Removed idx: " + firstIdx + " Child: " + firstChild.value());
                  }

                  children.remove(firstIdx);
               } else {
                  ++firstIdx;
               }
            }

            Iterator var9 = children.iterator();

            while(var9.hasNext()) {
               secondChild = (TreeNode)var9.next();
               this.combineDuplicateNodes(secondChild.getChildren());
            }

            return;
         }
      }
   }

   private void pushStepToRequiredPathsTree(Action.Operation currentOp, FederatedConfigDeque steps, Step newStep) {
      if (!this.isName(newStep) && currentOp != FederatedConfigFragmentsProcessor.Action.Operation.ADD) {
         if (steps.size() == 1) {
            TreeNode requiredPathsThisRoot = (TreeNode)this.requiredPaths.get(newStep.qName);
            if (requiredPathsThisRoot == null) {
               requiredPathsThisRoot = new TreeNodeImpl((TreeNode)null, newStep);
               this.requiredPaths.put(newStep.qName, requiredPathsThisRoot);
            }

            this.currentElement = (TreeNode)requiredPathsThisRoot;
         } else {
            TreeNode nextCurrentElement = this.findMatchingChild(this.currentElement, newStep);
            if (nextCurrentElement == null) {
               nextCurrentElement = this.currentElement.addChild(newStep);
            }

            this.currentElement = nextCurrentElement;
         }

      }
   }

   private TreeNode findMatchingChild(TreeNode parent, Step candidateChildValue) {
      Iterator var3 = parent.getChildren().iterator();

      TreeNode child;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         child = (TreeNode)var3.next();
      } while(!((Step)child.value()).equals(candidateChildValue));

      return child;
   }

   private void popCurrentRequiredPath(Action.Operation currentOp, XMLStreamReader reader) {
      if (!this.isName(reader) && currentOp != FederatedConfigFragmentsProcessor.Action.Operation.ADD) {
         this.currentElement = this.currentElement.parent();
      }

   }

   private String replaceOrDeleteTransformation(FederatedConfigDeque steps, String content) {
      StringBuilder result = new StringBuilder();
      this.navPath(steps);
      result.append("<xsl:template ").append(mode()).append("match=\"").append(this.navPath(steps)).append("\">");
      result.append(content.toString()).append("</xsl:template>");
      return result.toString();
   }

   private String formatNewContentTransformation(String parentXPath, String addedContent) {
      StringBuilder xform = new StringBuilder("<xsl:template ");
      xform.append(mode()).append("match=\"");
      xform.append(parentXPath).append("\">").append(FederatedConfigUtils.LINE_SEP).append("<").append("xsl").append(":copy>\n").append("  ").append(APPLY_TEMPLATES).append(FederatedConfigUtils.LINE_SEP).append(addedContent).append(FederatedConfigUtils.LINE_SEP).append("</").append("xsl").append(":copy>\n").append("</xsl:template>").append(FederatedConfigUtils.LINE_SEP);
      return xform.toString();
   }

   private String navPath(FederatedConfigDeque steps) {
      return navPath(steps.descendingIterator());
   }

   static String navPath(Iterator stepIt) {
      StringBuilder result = new StringBuilder();

      while(stepIt.hasNext()) {
         Step s = (Step)stepIt.next();
         result.append("/").append(XPathElementAndName(s));
      }

      return result.toString();
   }

   static String XPathElementAndName(Step step) {
      StringBuilder result = new StringBuilder();
      result.append(step.getElementName());
      if (step.nameValue != null) {
         String namePrefix = step.nameQName.getPrefix();
         if (FederatedConfigUtils.isEmpty(namePrefix)) {
            namePrefix = step.nsPrefixForXPaths;
         }

         namePrefix = namePrefix + ":";
         result.append("[" + namePrefix + "name" + "='" + step.nameValue + "']");
      }

      return result.toString();
   }

   private boolean isName(XMLStreamReader reader) {
      return reader.getName().getLocalPart().equals("name");
   }

   private boolean isName(Step step) {
      return step.qName.getLocalPart().equals("name");
   }

   String choosePrefix(QName qName) {
      String result = qName.getPrefix();
      if (result == null || FederatedConfigUtils.isEmpty(result)) {
         result = this.namespaceManager.getPrefix(qName.getNamespaceURI());
      }

      if (result == null || FederatedConfigUtils.isEmpty(result)) {
         result = "dom";
      }

      return result;
   }

   static String getPrefixedName(String defaultNSPrefix, QName qName) {
      if (qName.equals(DOMAIN_FRAGMENT_QNAME)) {
         return defaultNSPrefix + ":" + DOMAIN_QNAME.getLocalPart();
      } else if (qName.equals(JMS_FRAGMENT_QNAME)) {
         return defaultNSPrefix + ":" + JMS_QNAME.getLocalPart();
      } else if (qName.equals(JDBC_FRAGMENT_QNAME)) {
         return defaultNSPrefix + ":" + JDBC_QNAME.getLocalPart();
      } else if (qName.equals(DIAGNOSTICS_FRAGMENT_QNAME)) {
         return defaultNSPrefix + ":" + DIAGNOSTICS_QNAME.getLocalPart();
      } else {
         String prefix = qName.getPrefix();
         if (prefix == null || FederatedConfigUtils.isEmpty(prefix)) {
            prefix = defaultNSPrefix;
         }

         prefix = prefix + ":";
         return prefix + qName.getLocalPart();
      }
   }

   static String getPrefixedName(QName qName) {
      if (qName.equals(DOMAIN_FRAGMENT_QNAME)) {
         return DOMAIN_QNAME.getLocalPart();
      } else if (qName.equals(JMS_FRAGMENT_QNAME)) {
         return JMS_QNAME.getLocalPart();
      } else if (qName.equals(JDBC_FRAGMENT_QNAME)) {
         return JDBC_QNAME.getLocalPart();
      } else if (qName.equals(DIAGNOSTICS_FRAGMENT_QNAME)) {
         return DIAGNOSTICS_QNAME.getLocalPart();
      } else {
         String prefix = qName.getPrefix();
         if (prefix != null && !FederatedConfigUtils.isEmpty(prefix)) {
            prefix = prefix + ":";
         }

         return prefix + qName.getLocalPart();
      }
   }

   private String currentElementName(XMLStreamReader reader) {
      return this.prefix(reader.getPrefix()) + reader.getLocalName();
   }

   private String prefix(String prefix) {
      return prefix != null && !FederatedConfigUtils.isEmpty(prefix) ? prefix + ":" : "";
   }

   private String attrAssignment(XMLStreamReader reader, int attrIndex) {
      return " " + (reader.isAttributeSpecified(attrIndex) ? getPrefixedName(reader.getAttributeName(attrIndex)) + "='" + reader.getAttributeValue(attrIndex) + "'" : "");
   }

   Action.Operation attributeToOperation(XMLStreamReader delta) {
      for(int i = 0; i < delta.getAttributeCount(); ++i) {
         if (delta.getAttributeName(i).equals(COMBINE_MODE_QNAME) || delta.getAttributeName(i).equals(JMS_COMBINE_MODE_QNAME) || delta.getAttributeName(i).equals(JDBC_COMBINE_MODE_QNAME) || delta.getAttributeName(i).equals(DIAGNOSTICS_COMBINE_MODE_QNAME)) {
            return FederatedConfigFragmentsProcessor.Action.Operation.valueOf(delta.getAttributeValue(i).toUpperCase());
         }
      }

      return FederatedConfigFragmentsProcessor.Action.Operation.NAV;
   }

   private static String formatPrefixForXMLNS(String prefix) {
      return FederatedConfigUtils.isEmpty(prefix) ? "" : ":" + prefix;
   }

   static {
      IDENTITY_TRANSFORMATION_WITH_ROOT = "<xsl:template " + mode() + "match='/ | " + "@* | node()" + "'>\n <" + "xsl" + ":copy>\n    " + APPLY_TEMPLATES + "\n </" + "xsl" + ":copy>\n</" + "xsl" + ":template>";
      COMBINE_MODE_QNAME = new QName("http://xmlns.oracle.com/weblogic/domain-fragment", "combine-mode");
      DOMAIN_FRAGMENT_QNAME = new QName("http://xmlns.oracle.com/weblogic/domain-fragment", "domain-fragment");
      DOMAIN_QNAME = new QName("http://xmlns.oracle.com/weblogic/domain", "domain");
      JMS_COMBINE_MODE_QNAME = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms-fragment", "combine-mode");
      JMS_FRAGMENT_QNAME = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms-fragment", "weblogic-jms-fragment");
      JMS_QNAME = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "weblogic-jms");
      JDBC_COMBINE_MODE_QNAME = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source-fragment", "combine-mode");
      JDBC_FRAGMENT_QNAME = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source-fragment", "jdbc-data-source-fragment");
      JDBC_QNAME = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "jdbc-data-source");
      DIAGNOSTICS_COMBINE_MODE_QNAME = new QName("http://xmlns.oracle.com/weblogic/weblogic-diagnostics-fragment", "combine-mode");
      DIAGNOSTICS_FRAGMENT_QNAME = new QName("http://xmlns.oracle.com/weblogic/weblogic-diagnostics-fragment", "weblogic-diagnostics-fragment");
      DIAGNOSTICS_QNAME = new QName("http://xmlns.oracle.com/weblogic/weblogic-diagnostics", "weblogic-diagnostics");
   }

   static class NamespaceManager {
      private static final String GENERATED_PREFIX_PREFIX = "__ns_";
      static final String NS_MAP_VARIABLE_NAME = "__nsMap";
      static final String NAMESPACE_LOOKUP_MAP_ELEMENT_NAME = "namespace-def";
      private static final String NAMESPACE_LOOKUP_MAP_NS = "http://www.oracle.com/weblogic/__nsLookup";
      static final String NAMESPACE_LOOKUP_MAP_PREFIX = "__ns";
      private static final String NAMESPACE_LOOKUP_FORMAT = String.format("<%1$s:variable name='%2$s'>\n    %%s\n</%1$s:variable>", "xsl", "__nsMap");
      private static final String NAMESPACE_LOOKUP_MAP_ENTRY_FORMAT = String.format("<%1$s:%2$s %1$s:namespace='%%s' %1$s:prefix='%%s'/>", "__ns", "namespace-def");
      private final Map namespaceURIToGeneratedPrefix = new HashMap();
      private final Map explicitlyDeclaredInFragments = new HashMap();
      private int nextIndex = 0;

      String getPrefix(String namespaceURI) {
         String result = (String)this.namespaceURIToGeneratedPrefix.get(namespaceURI);
         if (result == null && !FederatedConfigUtils.isEmpty(namespaceURI)) {
            result = this.nextGeneratedPrefix();
            this.namespaceURIToGeneratedPrefix.put(namespaceURI, result);
         }

         return result;
      }

      void addExplicitDeclaration(String uri, String prefix) {
         this.explicitlyDeclaredInFragments.put(uri, prefix);
      }

      private String nextGeneratedPrefix() {
         return "__ns_" + Integer.toString(this.nextIndex++);
      }

      private String declarations() {
         StringBuilder sb = new StringBuilder();
         Iterator var2 = this.namespaceURIToGeneratedPrefix.entrySet().iterator();

         Map.Entry entry;
         while(var2.hasNext()) {
            entry = (Map.Entry)var2.next();
            sb.append(this.formatEntry((String)entry.getValue(), (String)entry.getKey()));
         }

         var2 = this.explicitlyDeclaredInFragments.entrySet().iterator();

         while(var2.hasNext()) {
            entry = (Map.Entry)var2.next();
            sb.append(this.formatEntry((String)entry.getValue(), (String)entry.getKey()));
         }

         return sb.toString();
      }

      String lookupMapForStylesheet() {
         StringBuilder subelements = new StringBuilder();
         Iterator var2 = this.namespaceURIToGeneratedPrefix.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            subelements.append("  ").append(String.format(NAMESPACE_LOOKUP_MAP_ENTRY_FORMAT, entry.getKey(), entry.getValue())).append("\n");
         }

         return String.format(NAMESPACE_LOOKUP_FORMAT, subelements.toString());
      }

      private String formatEntry(String prefix, String URI) {
         StringBuilder result = new StringBuilder(" xmlns");
         result.append(FederatedConfigFragmentsProcessor.formatPrefixForXMLNS(prefix)).append("=\"").append(URI).append("\"");
         return result.toString();
      }
   }

   private class AddedContentManager {
      private final Map addedContentByParent;

      private AddedContentManager() {
         this.addedContentByParent = new HashMap();
      }

      void saveAddedContent(QName qName, String parentXPath, String newContent) {
         NewContent nc = (NewContent)this.addedContentByParent.get(parentXPath);
         if (nc == null) {
            nc = FederatedConfigFragmentsProcessor.this.new NewContent(qName, parentXPath);
            this.addedContentByParent.put(parentXPath, nc);
         }

         nc.append(newContent + FederatedConfigUtils.LINE_SEP);
      }

      Iterable additions() {
         return this.addedContentByParent.values();
      }

      // $FF: synthetic method
      AddedContentManager(Object x1) {
         this();
      }
   }

   private class NewContent {
      private final QName parentName;
      private final StringBuilder content;
      private final String xPath;

      private NewContent(QName parentName, String xPath) {
         this.content = new StringBuilder();
         this.parentName = parentName;
         this.xPath = xPath;
      }

      private void append(String addedContent) {
         this.content.append(addedContent);
      }

      // $FF: synthetic method
      NewContent(QName x1, String x2, Object x3) {
         this(x1, x2);
      }
   }

   private static class Action {
      private final QName name;
      private final Operation op;
      private final String value;

      private Action(QName name, Operation op, String value) {
         this.name = name;
         this.op = op;
         this.value = value;
      }

      private static enum Operation {
         NAV,
         ADD,
         REPLACE,
         DELETE;
      }
   }

   private static class NamespaceInfo {
      private final String prefix;
      private final String uri;

      private NamespaceInfo(String prefix, String uri) {
         this.prefix = prefix;
         this.uri = uri;
      }

      private String asXMLNS() {
         return "xmlns" + FederatedConfigFragmentsProcessor.formatPrefixForXMLNS(this.prefix) + "=\"" + this.uri + "\"";
      }

      // $FF: synthetic method
      NamespaceInfo(String x0, String x1, Object x2) {
         this(x0, x1);
      }
   }

   class Step {
      private final QName qName;
      private QName nameQName;
      private String nameValue;
      private final String nsPrefixForXPaths;
      private final Action.Operation operation;
      private final Collection actions;
      private final List namespaceInfos;
      private final Set requiredChildren;

      private Step(QName qName, Action.Operation op) {
         this.actions = new ArrayList();
         this.namespaceInfos = new ArrayList();
         this.requiredChildren = new HashSet();
         this.qName = qName;
         this.nsPrefixForXPaths = FederatedConfigFragmentsProcessor.this.choosePrefix(qName);
         this.operation = op;
      }

      private void setNameValue(QName nameQName, String nameValue) {
         this.nameQName = nameQName;
         if (!this.isWildcard(nameValue)) {
            this.nameValue = nameValue;
         }

      }

      private boolean isWildcard(String nameValue) {
         return nameValue != null && nameValue.contains("*") && nameValue.length() == 3 && (nameValue.charAt(0) == '\'' || nameValue.charAt(0) == '"') && nameValue.charAt(0) == nameValue.charAt(2);
      }

      String getNameValue() {
         return this.nameValue;
      }

      private String getElementName() {
         return FederatedConfigFragmentsProcessor.getPrefixedName(this.nsPrefixForXPaths, this.qName);
      }

      private void addRequiredChild(QName child) {
         this.requiredChildren.add(child);
      }

      QName getName() {
         return this.qName;
      }

      QName getNameName() {
         return this.nameQName;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            Step step = (Step)o;
            if (this.qName != null) {
               if (!this.qName.equals(step.qName)) {
                  return false;
               }
            } else if (step.qName != null) {
               return false;
            }

            label46: {
               if (this.nameQName != null) {
                  if (this.nameQName.equals(step.nameQName)) {
                     break label46;
                  }
               } else if (step.nameQName == null) {
                  break label46;
               }

               return false;
            }

            if (this.nameValue != null) {
               if (this.nameValue.equals(step.nameValue)) {
                  return this.nsPrefixForXPaths != null ? this.nsPrefixForXPaths.equals(step.nsPrefixForXPaths) : step.nsPrefixForXPaths == null;
               }
            } else if (step.nameValue == null) {
               return this.nsPrefixForXPaths != null ? this.nsPrefixForXPaths.equals(step.nsPrefixForXPaths) : step.nsPrefixForXPaths == null;
            }

            return false;
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.qName != null ? this.qName.hashCode() : 0;
         result = 31 * result + (this.nameQName != null ? this.nameQName.hashCode() : 0);
         result = 31 * result + (this.nameValue != null ? this.nameValue.hashCode() : 0);
         result = 31 * result + (this.nsPrefixForXPaths != null ? this.nsPrefixForXPaths.hashCode() : 0);
         return result;
      }

      // $FF: synthetic method
      Step(QName x1, Action.Operation x2, Object x3) {
         this(x1, x2);
      }
   }

   private class DeleteProcessor extends AddOrReplaceOrDeleteProcessor {
      DeleteProcessor(FederatedConfigDeque steps) {
         super(steps);
      }

      Action.Operation getOperation() {
         return FederatedConfigFragmentsProcessor.Action.Operation.DELETE;
      }

      public void process(XMLStreamReader reader) throws XMLStreamException {
         int elementDepth = 1;

         while(reader.hasNext()) {
            switch (reader.next()) {
               case 1:
                  this.resetChars();
                  ++elementDepth;
                  break;
               case 2:
                  if (FederatedConfigFragmentsProcessor.this.isName(reader) && elementDepth == 2) {
                     ((Step)this.steps.peek()).setNameValue(reader.getName(), this.useChars());
                  }

                  --elementDepth;
                  if (elementDepth == 0) {
                     this.transformations().add(this.transformation());
                     this.popStepIfNeeded(reader);
                     return;
                  }
               case 3:
               default:
                  break;
               case 4:
                  this.consumeChars(reader);
            }
         }

      }

      String transformation() {
         return FederatedConfigFragmentsProcessor.this.replaceOrDeleteTransformation(this.steps, "");
      }
   }

   class FragmentProcessor extends StreamProcessorImpl {
      private final AddedContentManager addedContentManager;
      private final List transformations = new ArrayList();

      FragmentProcessor(AddedContentManager addedContentManager) {
         super(new FederatedConfigDeque());
         this.addedContentManager = addedContentManager;
      }

      public void process(XMLStreamReader reader) throws XMLStreamException {
         Action.Operation currentOp = null;
         List procs = new ArrayList();

         while(true) {
            while(reader.hasNext()) {
               int eventType = reader.next();
               this.preprocessCurrentEvent(reader);
               switch (eventType) {
                  case 1:
                     currentOp = FederatedConfigFragmentsProcessor.this.attributeToOperation(reader);
                     Step newStep = this.createStepAndPushIfNeeded(reader, currentOp);
                     List nsi = this.collectNamespaces(reader);
                     newStep.namespaceInfos.addAll(nsi);
                     this.recordDeclaredNamespaces(nsi);
                     FederatedConfigFragmentsProcessor.this.pushStepToRequiredPathsTree(currentOp, this.steps, newStep);
                     if (this.steps.size() == 1) {
                        FederatedConfigFragmentsProcessor.this.topLevelNamespace = new NamespaceInfo(((Step)this.steps.peek()).qName.getPrefix(), ((Step)this.steps.peek()).qName.getNamespaceURI());
                     }

                     if (currentOp != FederatedConfigFragmentsProcessor.Action.Operation.NAV) {
                        StreamProcessor spx = this.chooseNewProcessor(currentOp, reader, this.steps);
                        procs.add(spx);
                        spx.process(reader);
                        this.transformations.addAll(spx.transformations());
                     }

                     this.currentStep = newStep;
                     break;
                  case 2:
                     if (FederatedConfigFragmentsProcessor.this.isName(reader)) {
                        ((Step)this.steps.peek()).setNameValue(reader.getName(), this.useChars());
                     } else {
                        Iterator var7 = procs.iterator();

                        while(var7.hasNext()) {
                           StreamProcessor sp = (StreamProcessor)var7.next();
                           String addedFromThisProcessor = sp.addedContent();
                           if (!FederatedConfigUtils.isEmpty(addedFromThisProcessor)) {
                              this.addedContentManager.saveAddedContent(reader.getName(), sp.getParentXPath(), addedFromThisProcessor);
                           }
                        }

                        procs.clear();
                     }

                     this.popStepIfNeeded(reader);
                     if (this.steps.size() > 0) {
                        currentOp = ((Step)this.steps.getFirst()).operation;
                     }

                     FederatedConfigFragmentsProcessor.this.popCurrentRequiredPath(currentOp, reader);
               }
            }

            return;
         }
      }

      private StreamProcessor chooseNewProcessor(Action.Operation currentOp, XMLStreamReader reader, FederatedConfigDeque steps) {
         switch (currentOp) {
            case ADD:
               return FederatedConfigFragmentsProcessor.this.new AddProcessor(steps);
            case DELETE:
               return FederatedConfigFragmentsProcessor.this.new DeleteProcessor(steps);
            case REPLACE:
               return FederatedConfigFragmentsProcessor.this.new ReplaceProcessor(steps);
            default:
               throw new IllegalArgumentException("Cannot choose subtree processor for specified operation " + currentOp.name());
         }
      }

      public List transformations() {
         return this.transformations;
      }
   }

   class AddProcessor extends AddOrReplaceProcessor {
      AddProcessor(FederatedConfigDeque steps) {
         super(steps);
      }

      public String addedContent() {
         return this.content();
      }

      String transformation() {
         return null;
      }

      Action.Operation getOperation() {
         return FederatedConfigFragmentsProcessor.Action.Operation.ADD;
      }
   }

   class ReplaceProcessor extends AddOrReplaceProcessor {
      ReplaceProcessor(FederatedConfigDeque steps) {
         super(steps);
      }

      String transformation() {
         return FederatedConfigFragmentsProcessor.this.replaceOrDeleteTransformation(this.steps, this.content.toString());
      }

      Action.Operation getOperation() {
         return FederatedConfigFragmentsProcessor.Action.Operation.REPLACE;
      }
   }

   abstract class AddOrReplaceProcessor extends AddOrReplaceOrDeleteProcessor {
      protected String elementName;
      protected List namespaceInfos;

      AddOrReplaceProcessor(FederatedConfigDeque steps) {
         super(steps);
      }

      public void process(XMLStreamReader reader) throws XMLStreamException {
         int elementDepth = 0;
         int eventID = reader.getEventType();

         while(true) {
            String xform;
            switch (eventID) {
               case 1:
                  ++elementDepth;
                  List nsi = this.collectNamespaces(reader);
                  if (elementDepth == 1) {
                     this.elementName = FederatedConfigFragmentsProcessor.this.currentElementName(reader);
                     this.setPaths(this.steps);
                     this.namespaceInfos = nsi;
                  }

                  this.recordDeclaredNamespaces(nsi);
                  this.content.append("<").append(FederatedConfigFragmentsProcessor.this.currentElementName(reader)).append(" ").append(this.formatNamespaces(this.namespaceInfos));

                  for(int i = 0; i < reader.getAttributeCount(); ++i) {
                     if (!reader.getAttributeName(i).getNamespaceURI().equals("http://xmlns.oracle.com/weblogic/domain-fragment") && !reader.getAttributeName(i).getNamespaceURI().equals("http://xmlns.oracle.com/weblogic/weblogic-jms-fragment") && !reader.getAttributeName(i).getNamespaceURI().equals("http://xmlns.oracle.com/weblogic/jdbc-data-source-fragment") && !reader.getAttributeName(i).getNamespaceURI().equals("http://xmlns.oracle.com/weblogic/weblogic-diagnostics-fragment")) {
                        this.content.append(FederatedConfigFragmentsProcessor.this.attrAssignment(reader, i));
                     }
                  }

                  this.content.append(">");
                  break;
               case 2:
                  this.content.append("</").append(FederatedConfigFragmentsProcessor.this.currentElementName(reader)).append(">");
                  if (FederatedConfigFragmentsProcessor.this.isName(reader) && elementDepth == 1) {
                     ((Step)this.steps.peek()).setNameValue(reader.getName(), this.useChars());
                  }

                  --elementDepth;
                  if (elementDepth == 0) {
                     xform = this.transformation();
                     if (xform != null) {
                        this.transformations.add(xform);
                     }

                     this.popStepIfNeeded(reader);
                     FederatedConfigFragmentsProcessor.this.popCurrentRequiredPath(this.getOperation(), reader);
                     return;
                  }
               case 3:
               case 9:
               case 10:
               case 13:
               default:
                  break;
               case 4:
                  xform = reader.getText();
                  this.content.append(xform);
                  this.consumeChars(xform);
                  break;
               case 5:
                  this.content.append("!<--").append(reader.getText()).append("-->");
                  break;
               case 6:
                  this.content.append(reader.getText());
                  break;
               case 7:
                  throw new IllegalStateException("START_DOCUMENT found in mid-fragment");
               case 8:
                  throw new IllegalStateException("END_DOCUMENT found in mid-fragment");
               case 11:
                  throw new IllegalStateException("DTD found in mid-fragment");
               case 12:
                  this.content.append("<![CDATA[").append(reader.getText()).append("]]>");
            }

            if (!reader.hasNext()) {
               return;
            }

            eventID = reader.next();
         }
      }

      String content() {
         return this.content.toString();
      }
   }

   abstract class AddOrReplaceOrDeleteProcessor extends StreamProcessorImpl {
      final StringBuilder content = new StringBuilder();

      AddOrReplaceOrDeleteProcessor(FederatedConfigDeque steps) {
         super(steps);
         this.setPaths(steps);
      }

      abstract String transformation();

      abstract Action.Operation getOperation();
   }

   private abstract class StreamProcessorImpl implements StreamProcessor {
      private StringBuilder currentChars = new StringBuilder();
      protected final FederatedConfigDeque steps;
      protected Step currentStep = null;
      protected List transformations = new ArrayList();
      protected String xPath;
      protected String parentXPath;

      protected StreamProcessorImpl(FederatedConfigDeque steps) {
         this.steps = steps;
      }

      protected void setPaths(FederatedConfigDeque steps) {
         this.xPath = FederatedConfigFragmentsProcessor.this.navPath(steps);
         this.parentXPath = FederatedConfigFragmentsProcessor.this.navPath(this.withoutTopStep(steps));
      }

      private FederatedConfigDeque withoutTopStep(FederatedConfigDeque steps) {
         FederatedConfigDeque shorterSteps = new FederatedConfigDeque(steps);
         shorterSteps.removeFirst();
         return shorterSteps;
      }

      void consumeChars(XMLStreamReader reader) {
         this.currentChars.append(reader.getText());
      }

      void consumeChars(String text) {
         this.currentChars.append(text);
      }

      String useChars() {
         String result = this.currentChars.toString();
         this.resetChars();
         return result;
      }

      void resetChars() {
         this.currentChars.delete(0, this.currentChars.length());
      }

      void preprocessCurrentEvent(XMLStreamReader reader) {
         int eventType = reader.getEventType();
         switch (eventType) {
            case 1:
               this.resetChars();
               break;
            case 4:
               this.consumeChars(reader);
         }

      }

      protected List collectNamespaces(XMLStreamReader reader) {
         List result = new ArrayList();
         boolean foundDefault = false;

         for(int i = 0; i < reader.getNamespaceCount(); ++i) {
            NamespaceInfo newNSInfo = new NamespaceInfo(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
            result.add(newNSInfo);
            foundDefault |= newNSInfo.prefix != null && FederatedConfigUtils.isEmpty(newNSInfo.prefix);
         }

         if (!foundDefault) {
            QName qName = reader.getName();
            if (!FederatedConfigUtils.isEmpty(qName.getNamespaceURI())) {
               result.add(0, new NamespaceInfo(qName.getPrefix(), qName.getNamespaceURI()));
            }
         }

         return result;
      }

      protected String formatNamespaces(List namespaces) {
         StringBuilder result = new StringBuilder();

         NamespaceInfo ni;
         for(Iterator var3 = namespaces.iterator(); var3.hasNext(); result.append("xmlns" + (ni.prefix != null && !FederatedConfigUtils.isEmpty(ni.prefix) ? ":" + ni.prefix : "") + "='" + ni.uri + "'")) {
            ni = (NamespaceInfo)var3.next();
            if (result.length() > 0) {
               result.append(" ");
            }
         }

         return result.toString();
      }

      protected void recordDeclaredNamespaces(List nsi) {
         Iterator var2 = nsi.iterator();

         while(var2.hasNext()) {
            NamespaceInfo info = (NamespaceInfo)var2.next();
            if (info.uri != null && !FederatedConfigUtils.isEmpty(info.uri)) {
               FederatedConfigFragmentsProcessor.this.namespaceManager.addExplicitDeclaration(info.uri, info.prefix);
            }
         }

      }

      public List transformations() {
         return this.transformations;
      }

      Step createStepAndPushIfNeeded(XMLStreamReader reader, Action.Operation op) {
         Step newStep = FederatedConfigFragmentsProcessor.this.new Step(reader.getName(), op);
         if (!FederatedConfigFragmentsProcessor.this.isName(newStep)) {
            this.steps.addFirst(newStep);
         }

         return newStep;
      }

      public String addedContent() {
         return "";
      }

      void popStepIfNeeded(XMLStreamReader reader) {
         if (!FederatedConfigFragmentsProcessor.this.isName(reader)) {
            this.steps.removeFirst();
         }

      }

      public String getXPath() {
         return this.xPath;
      }

      public String getParentXPath() {
         return this.parentXPath;
      }
   }

   private interface StreamProcessor {
      void process(XMLStreamReader var1) throws XMLStreamException;

      List transformations();

      String getXPath();

      String getParentXPath();

      String addedContent();
   }
}
