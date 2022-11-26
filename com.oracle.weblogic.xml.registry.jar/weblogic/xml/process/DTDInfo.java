package weblogic.xml.process;

import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import weblogic.utils.Debug;
import weblogic.utils.StringUtils;

public class DTDInfo {
   private static final boolean verbose = false;
   private static final boolean debug = false;
   private static final String ELEMENT_DECL_START = "<!ELEMENT";
   private static final String ELEMENT_DECL_END = ">";
   private DTDNode top;
   private static final int MAX_RECURSION = 10;
   private Map elementsCount;

   public DTDInfo(File dtdFile) throws DTDParsingException, IOException {
      this((Reader)(new FileReader(dtdFile)));
   }

   public DTDInfo(String dtdFilePath) throws DTDParsingException, IOException {
      this((Reader)(new FileReader(dtdFilePath)));
   }

   public DTDInfo(Reader dtdStream) throws DTDParsingException, IOException {
      this.elementsCount = new HashMap();
      this.readDTD(dtdStream);
   }

   public DTDNode getNodeTree() {
      return this.top;
   }

   private void readDTD(Reader dtd) throws DTDParsingException, IOException {
      Map elements = readElementDecls(dtd);
      String rootElementName = findRoot(elements);
      this.top = this.buildTree(elements, (DTDNode)null, rootElementName);
   }

   private DTDNode buildTree(Map elements, DTDNode parentNode, String eltName) throws DTDParsingException {
      Debug.assertion(elements != null);
      Debug.assertion(eltName != null);
      DTDNode n = new DTDNode(eltName);
      Integer eltCount = (Integer)this.elementsCount.get(eltName);
      if (eltCount == null) {
         eltCount = new Integer(0);
      }

      if (eltCount >= 10) {
         return null;
      } else {
         this.elementsCount.put(eltName, new Integer(eltCount + 1));
         if (parentNode != null) {
            n.setParent(parentNode);
            parentNode.addChild(n);
         }

         String content = (String)elements.get(eltName);
         if (content == null) {
            throw new DTDParsingException("DTD parser: could not locate DTD production rule for element " + eltName);
         } else {
            String[] products = parseContent(content);

            for(int i = 0; i < products.length; ++i) {
               this.buildTree(elements, n, products[i]);
            }

            this.elementsCount.put(eltName, new Integer(eltCount));
            return n;
         }
      }
   }

   private static String findRoot(Map elements) throws DTDParsingException {
      Debug.assertion(elements != null);
      Set topCandidates = new HashSet(elements.keySet());
      Iterator ei = elements.values().iterator();

      while(ei.hasNext()) {
         String content = (String)ei.next();
         String[] products = parseContent(content);

         for(int i = 0; i < products.length; ++i) {
            topCandidates.remove(products[i]);
         }
      }

      String[] topCandidatesArr = (String[])((String[])topCandidates.toArray(new String[0]));
      if (topCandidatesArr.length == 0) {
         throw new DTDParsingException("There appear to be no top-level elements in the DTD");
      } else if (topCandidatesArr.length > 1) {
         throw new DTDParsingException("There appear to be multiple elements in the DTD that are not part\nof production rules:\n" + StringUtils.join(topCandidatesArr, ","));
      } else {
         return topCandidatesArr[0];
      }
   }

   private static String[] parseContent(String content) {
      Debug.assertion(content != null);
      String tokDelims = "\t\r\n\f()+,|*?";
      Set excludedProductions = new HashSet(Arrays.asList((Object[])(new String[]{"EMPTY", "#PCDATA"})));
      StringTokenizer toker = new StringTokenizer(content, "\t\r\n\f()+,|*?");
      List toks = new ArrayList();

      while(toker.hasMoreTokens()) {
         String tok = toker.nextToken().trim();
         if (tok.length() > 0 && !excludedProductions.contains(tok)) {
            toks.add(tok);
         }
      }

      return (String[])((String[])toks.toArray(new String[0]));
   }

   private static Map readElementDecls(Reader reader) throws IOException {
      Map elements = new HashMap();
      PushbackReader r = new PushbackReader(reader, 20);

      while(true) {
         try {
            ParsingUtils.read(r, "<!ELEMENT", true);
            ParsingUtils.readWS(r);
            String elementName = ParsingUtils.readUntilWS(r);
            ParsingUtils.readWS(r);
            String contentModel = ParsingUtils.read(r, ">", false);
            elements.put(elementName, contentModel);
         } catch (EOFException var5) {
            return elements;
         }
      }
   }

   public static void main(String[] args) throws Exception {
      DTDInfo dtd = new DTDInfo(args[0]);
      DTDNode tree = dtd.getNodeTree();
      Iterator i = tree.iterator();

      while(i.hasNext()) {
         System.out.println((DTDNode)i.next());
      }

   }

   public static class DTDNode {
      private DTDNode parent;
      private Map children = new HashMap();
      String elementName;

      public DTDNode(String name) {
         this.setName(name);
      }

      public void setName(String name) {
         this.elementName = name;
      }

      public String getName() {
         return this.elementName;
      }

      public void addChild(DTDNode n) {
         this.children.put(n.getName(), n);
         n.setParent(this);
      }

      public Collection getChildren() {
         return this.children.values();
      }

      public DTDNode getChild(String tagName) {
         return (DTDNode)this.children.get(tagName);
      }

      public void setParent(DTDNode n) {
         this.parent = n;
      }

      public DTDNode getParent() {
         return this.parent;
      }

      public Iterator iterator() {
         List nodeList = new ArrayList();
         this.addNodes(nodeList, this);
         return nodeList.iterator();
      }

      private void addNodes(List l, DTDNode n) {
         l.add(n);
         Iterator i = n.getChildren().iterator();

         while(i.hasNext()) {
            this.addNodes(l, (DTDNode)i.next());
         }

      }

      public String toString() {
         StringBuffer sbuf = new StringBuffer();
         sbuf.append("Node[" + this.hashCode() + "]: " + this.elementName);
         if (this.parent == null) {
            sbuf.append("\nParent: none");
         } else {
            sbuf.append("\nParent[" + this.parent.hashCode() + "]:" + this.parent.getName());
         }

         sbuf.append("\nChildren: ");
         Iterator i = this.getChildren().iterator();

         while(i.hasNext()) {
            sbuf.append(" " + ((DTDNode)i.next()).getName());
         }

         return sbuf.toString();
      }
   }
}
