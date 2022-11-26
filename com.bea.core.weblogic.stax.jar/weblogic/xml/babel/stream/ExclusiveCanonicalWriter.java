package weblogic.xml.babel.stream;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLStreamException;

public class ExclusiveCanonicalWriter extends CanonicalWriter {
   private boolean firstElement = true;
   private static final boolean DEBUG = false;
   private ArrayList inclusiveNamespacesPrefixList = new ArrayList();

   public ExclusiveCanonicalWriter(Writer writer) {
      super(writer);
   }

   public ExclusiveCanonicalWriter(Writer writer, Map extNS, String[] prefixList) {
      super(writer, extNS);
      this.setInclusiveNamespacesPrefixList(prefixList);
   }

   public ExclusiveCanonicalWriter(Writer writer, String[] prefixList) {
      super(writer);
      this.setInclusiveNamespacesPrefixList(prefixList);
   }

   private void setInclusiveNamespacesPrefixList(String[] prefixList) {
      if (prefixList != null) {
         for(int i = 0; i < prefixList.length; ++i) {
            this.inclusiveNamespacesPrefixList.add(prefixList[i]);
         }

         debugSay(" +++ inclusiveNamespacesPrefixList : " + this.inclusiveNamespacesPrefixList);
      }
   }

   private List addNSAttribute(String prefix, String uri, List list) {
      if (prefix == null) {
         return (List)list;
      } else {
         this.getScopedNamespaces().checkPrefixMap(prefix, uri);
         if (this.getScopedNamespaces().needToWriteNS(prefix)) {
            if (list == null) {
               list = new ArrayList();
            }

            if ("".equals(prefix)) {
               prefix = null;
            }

            ((List)list).add(ElementFactory.createNamespaceAttribute(prefix, uri));
         }

         return (List)list;
      }
   }

   public List checkPrefix(String prefix, String uri, List list) {
      return this.checkPrefix(prefix, uri, list, false);
   }

   public List checkPrefix(String prefix, String uri, List list, boolean attribute) {
      if (!attribute && uri == null && prefix == null) {
         return this.addNSAttribute("", "", list);
      } else if (uri == null) {
         return list;
      } else if (prefix == null) {
         return this.addNSAttribute("", uri, list);
      } else {
         return "xml".equals(prefix) ? list : this.addNSAttribute(prefix, uri, list);
      }
   }

   public List checkAndDeclareNamespacePrefixes(StartElement element) throws XMLStreamException {
      String prefix = element.getName().getPrefix();
      String uri = element.getName().getNamespaceUri();
      List results = this.checkPrefix(prefix, uri, (List)null);
      boolean defaultDeclared = false;
      if (results != null) {
         Attribute a = (Attribute)results.get(0);
         if ("xmlns".equals(a.getName().getLocalName())) {
            defaultDeclared = true;
         }
      }

      Attribute a;
      for(AttributeIterator ai = element.getAttributes(); ai.hasNext(); results = this.checkPrefix(a.getName().getPrefix(), a.getName().getNamespaceUri(), results, true)) {
         a = ai.next();
      }

      return results;
   }

   public void write(StartElement element) throws XMLStreamException {
      this.incrementLevel();
      this.getScopedNamespaces().openScope();
      this.write('<');
      this.write(element.getName());
      List newDeclarations = this.checkAndDeclareNamespacePrefixes(element);
      if (this.elementLevel == 1 && "".equals(this.getScopedNamespaces().getNamespaceURI("xmlns"))) {
         this.getScopedNamespaces().put("xmlns", (String)null);
      }

      List requiredFromPrefixList = this.findRequiredNSFromInclusivePrefixList(element);
      if (this.elementLevel == 1 && null == this.getScopedNamespaces().getNamespaceURI("xmlns")) {
         this.getScopedNamespaces().put("xmlns", "");
      }

      if (newDeclarations != null) {
         if (this.writeAugmented && this.augmentedElementTracks.contains(element.getName().getQualifiedName())) {
            this.writeEmptyDefaultNS(CanonicalUtils.mergeNamespaces(new AttributeIterator[]{ElementFactory.createAttributeIterator(newDeclarations.iterator()), ElementFactory.createAttributeIterator(requiredFromPrefixList.iterator())}));
         }

         this.writeNamespaces(CanonicalUtils.sortNamespaces(ElementFactory.createAttributeIterator(newDeclarations.iterator()), ElementFactory.createAttributeIterator(requiredFromPrefixList.iterator())));
      } else {
         if (this.writeAugmented && this.augmentedElementTracks.contains(element.getName().getQualifiedName())) {
            this.writeEmptyDefaultNS(ElementFactory.createAttributeIterator(requiredFromPrefixList.iterator()));
         }

         this.writeNamespaces(CanonicalUtils.sortNamespaces(ElementFactory.createAttributeIterator(requiredFromPrefixList.iterator())));
      }

      this.write(CanonicalUtils.sortAttributes(element.getAttributes()));
      this.write('>');
   }

   private List findRequiredNSFromInclusivePrefixList(StartElement element) {
      if (this.inclusiveNamespacesPrefixList != null && !this.inclusiveNamespacesPrefixList.isEmpty()) {
         List result = new ArrayList();
         AttributeIterator ni = element.getNamespaces();
         debugSay(" +++ Verifying NameSpaces for Element: " + element);

         while(ni.hasNext()) {
            Attribute n = ni.next();
            String nsPrefix = n.getName().getLocalName();
            if (nsPrefix.equals("xmlns")) {
               nsPrefix = "";
            }

            debugSay(" +++ NameSpace Name: " + n.getName() + ", prefix: " + n.getName().getPrefix() + ", localName: " + nsPrefix + ",  Value is : " + n.getValue());
            if (this.isAttrInPrefixList(n)) {
               result = this.addNSAttribute(nsPrefix, n.getValue(), (List)result);
               debugSay(" +++ adding into added list:" + n.getName().getLocalName());
            }
         }

         List result = this.checkAncestorNamespacesInPrefixList(element, (List)result);
         return result;
      } else {
         debugSay(" +++ Empty PrefixList to check. So returning empty ArrayList from here");
         return new ArrayList();
      }
   }

   private List checkAncestorNamespacesInPrefixList(StartElement element, List result) {
      Map ancestorMap = this.getExternalNamespaces();
      debugSay(" ++++ Printing nameSpaceMap on StartElement: " + ancestorMap);
      if (ancestorMap != null) {
         Iterator iter = ancestorMap.keySet().iterator();

         while(iter.hasNext()) {
            String ancestorPrefix = (String)iter.next();
            if (this.isPrefixInPrefixList(ancestorPrefix) && !this.isPrefixInNamespaceMap(ancestorPrefix)) {
               debugSay(" +++ Prefix '" + ancestorPrefix + "' is in inclusivePrefixList but not in namespace map");
               result = this.addNSAttribute(ancestorPrefix, (String)ancestorMap.get(ancestorPrefix), result);
            }
         }
      }

      return result;
   }

   private boolean isPrefixInPrefixList(String nsPrefix) {
      boolean result = false;
      if (nsPrefix.equals("xmlns")) {
         nsPrefix = "";
      }

      if (nsPrefix.equals("")) {
         if (this.inclusiveNamespacesPrefixList.contains("#default")) {
            result = true;
         }
      } else {
         result = this.inclusiveNamespacesPrefixList.contains(nsPrefix);
      }

      debugSay(" ++++ prefixMap contains Key(" + nsPrefix + ") : " + result);
      return result;
   }

   private boolean isAttrInPrefixList(Attribute nsAttr) {
      String nsPrefix = nsAttr.getName().getLocalName();
      return this.isPrefixInPrefixList(nsPrefix);
   }

   private boolean isPrefixInNamespaceMap(String prefix) {
      return this.getScopedNamespaces().isPrefixInMap(prefix);
   }

   public AttributeIterator declareParentNamespaces(Map namespaces) {
      SortedMap map = new TreeMap();
      if (namespaces != null) {
         Iterator iter = namespaces.entrySet().iterator();

         while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            String uri = (String)entry.getValue();
            if (!uri.equals("http://www.w3.org/XML/1998/namespace")) {
               String prefix = (String)entry.getKey();
               if (prefix.equals("")) {
                  prefix = null;
               }

               Attribute newNS = ElementFactory.createNamespaceAttribute(prefix, uri);
               map.put(newNS.getName().getQualifiedName(), newNS);
            }
         }
      }

      return ElementFactory.createAttributeIterator(map.values().iterator());
   }

   private static void debugSay(String s) {
   }

   public static void main(String[] args) throws Exception {
      String[] inclusiveNamesSpaces = new String[]{"test"};
      XMLWriter writer = new ExclusiveCanonicalWriter(new OutputStreamWriter(new FileOutputStream("out.xml"), "utf-8"), inclusiveNamesSpaces);
      XMLInputStreamBase root = new XMLInputStreamBase();
      root.openValidating(SAXElementFactory.createInputSource(args[0]));
      root.skip(2);
      root.next();
      XMLOutputStreamBase output = new XMLOutputStreamBase(writer);
      output.add(root.getSubStream());
      output.flush();
   }

   private boolean isVisiblyUtilized(String prefix, StartElement element) {
      if (prefix.equals(element.getName().getPrefix())) {
         return true;
      } else {
         AttributeIterator ai = element.getAttributes();

         Attribute a;
         do {
            if (!ai.hasNext()) {
               return false;
            }

            a = ai.next();
         } while(!prefix.equals(a.getName().getPrefix()));

         return true;
      }
   }
}
