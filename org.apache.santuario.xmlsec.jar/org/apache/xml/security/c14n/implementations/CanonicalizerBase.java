package org.apache.xml.security.c14n.implementations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.CanonicalizerSpi;
import org.apache.xml.security.c14n.helper.AttrCompare;
import org.apache.xml.security.signature.NodeFilter;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.UnsyncByteArrayOutputStream;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.xml.sax.SAXException;

public abstract class CanonicalizerBase extends CanonicalizerSpi {
   public static final String XML = "xml";
   public static final String XMLNS = "xmlns";
   public static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";
   public static final String XML_LANG_URI = "http://www.w3.org/XML/1998/namespace";
   protected static final AttrCompare COMPARE = new AttrCompare();
   private static final byte[] END_PI = new byte[]{63, 62};
   private static final byte[] BEGIN_PI = new byte[]{60, 63};
   private static final byte[] END_COMM = new byte[]{45, 45, 62};
   private static final byte[] BEGIN_COMM = new byte[]{60, 33, 45, 45};
   private static final byte[] XA = new byte[]{38, 35, 120, 65, 59};
   private static final byte[] X9 = new byte[]{38, 35, 120, 57, 59};
   private static final byte[] QUOT = new byte[]{38, 113, 117, 111, 116, 59};
   private static final byte[] XD = new byte[]{38, 35, 120, 68, 59};
   private static final byte[] GT = new byte[]{38, 103, 116, 59};
   private static final byte[] LT = new byte[]{38, 108, 116, 59};
   private static final byte[] END_TAG = new byte[]{60, 47};
   private static final byte[] AMP = new byte[]{38, 97, 109, 112, 59};
   private static final byte[] EQUALS_STR = new byte[]{61, 34};
   protected static final int NODE_BEFORE_DOCUMENT_ELEMENT = -1;
   protected static final int NODE_NOT_BEFORE_OR_AFTER_DOCUMENT_ELEMENT = 0;
   protected static final int NODE_AFTER_DOCUMENT_ELEMENT = 1;
   private List nodeFilter;
   private boolean includeComments;
   private Set xpathNodeSet;
   private Node excludeNode;
   private OutputStream writer = new ByteArrayOutputStream();
   private Attr nullNode;

   public CanonicalizerBase(boolean includeComments) {
      this.includeComments = includeComments;
   }

   public byte[] engineCanonicalizeSubTree(Node rootNode) throws CanonicalizationException {
      return this.engineCanonicalizeSubTree(rootNode, (Node)null);
   }

   public byte[] engineCanonicalizeXPathNodeSet(Set xpathNodeSet) throws CanonicalizationException {
      this.xpathNodeSet = xpathNodeSet;
      return this.engineCanonicalizeXPathNodeSetInternal(XMLUtils.getOwnerDocument(this.xpathNodeSet));
   }

   public byte[] engineCanonicalize(XMLSignatureInput input) throws CanonicalizationException {
      try {
         if (input.isExcludeComments()) {
            this.includeComments = false;
         }

         if (input.isOctetStream()) {
            return this.engineCanonicalize(input.getBytes());
         } else if (input.isElement()) {
            return this.engineCanonicalizeSubTree(input.getSubNode(), input.getExcludeNode());
         } else if (input.isNodeSet()) {
            this.nodeFilter = input.getNodeFilters();
            this.circumventBugIfNeeded(input);
            return input.getSubNode() != null ? this.engineCanonicalizeXPathNodeSetInternal(input.getSubNode()) : this.engineCanonicalizeXPathNodeSet(input.getNodeSet());
         } else {
            return null;
         }
      } catch (ParserConfigurationException var3) {
         throw new CanonicalizationException(var3);
      } catch (IOException var4) {
         throw new CanonicalizationException(var4);
      } catch (SAXException var5) {
         throw new CanonicalizationException(var5);
      }
   }

   public void setWriter(OutputStream writer) {
      this.writer = writer;
   }

   protected OutputStream getWriter() {
      return this.writer;
   }

   protected byte[] engineCanonicalizeSubTree(Node rootNode, Node excludeNode) throws CanonicalizationException {
      this.excludeNode = excludeNode;

      try {
         NameSpaceSymbTable ns = new NameSpaceSymbTable();
         int nodeLevel = -1;
         if (rootNode != null && 1 == rootNode.getNodeType()) {
            this.getParentNameSpaces((Element)rootNode, ns);
            nodeLevel = 0;
         }

         this.canonicalizeSubTree(rootNode, ns, rootNode, nodeLevel);
         this.writer.flush();
         byte[] result;
         if (this.writer instanceof ByteArrayOutputStream) {
            result = ((ByteArrayOutputStream)this.writer).toByteArray();
            if (this.reset) {
               ((ByteArrayOutputStream)this.writer).reset();
            } else {
               this.writer.close();
            }

            return result;
         } else if (this.writer instanceof UnsyncByteArrayOutputStream) {
            result = ((UnsyncByteArrayOutputStream)this.writer).toByteArray();
            if (this.reset) {
               ((UnsyncByteArrayOutputStream)this.writer).reset();
            } else {
               this.writer.close();
            }

            return result;
         } else {
            this.writer.close();
            return null;
         }
      } catch (UnsupportedEncodingException var6) {
         throw new CanonicalizationException(var6);
      } catch (IOException var7) {
         throw new CanonicalizationException(var7);
      }
   }

   protected final void canonicalizeSubTree(Node currentNode, NameSpaceSymbTable ns, Node endnode, int documentLevel) throws CanonicalizationException, IOException {
      if (currentNode != null && this.isVisibleInt(currentNode) != -1) {
         Node sibling = null;
         Node parentNode = null;
         OutputStream writer = this.writer;
         Node excludeNode = this.excludeNode;
         boolean includeComments = this.includeComments;
         Map cache = new HashMap();

         label56:
         while(true) {
            switch (currentNode.getNodeType()) {
               case 1:
                  documentLevel = 0;
                  if (currentNode != excludeNode) {
                     Element currentElement = (Element)currentNode;
                     ns.outputNodePush();
                     writer.write(60);
                     String name = currentElement.getTagName();
                     UtfHelpper.writeByte(name, writer, cache);
                     this.outputAttributesSubtree(currentElement, ns, cache);
                     writer.write(62);
                     sibling = currentNode.getFirstChild();
                     if (sibling == null) {
                        writer.write((byte[])END_TAG.clone());
                        UtfHelpper.writeStringToUtf8(name, writer);
                        writer.write(62);
                        ns.outputNodePop();
                        if (parentNode != null) {
                           sibling = currentNode.getNextSibling();
                        }
                     } else {
                        parentNode = currentElement;
                     }
                  }
                  break;
               case 2:
               case 6:
               case 12:
                  throw new CanonicalizationException("empty", new Object[]{"illegal node type during traversal"});
               case 3:
               case 4:
                  outputTextToWriter(currentNode.getNodeValue(), writer);
               case 5:
               case 10:
               default:
                  break;
               case 7:
                  this.outputPItoWriter((ProcessingInstruction)currentNode, writer, documentLevel);
                  break;
               case 8:
                  if (includeComments) {
                     this.outputCommentToWriter((Comment)currentNode, writer, documentLevel);
                  }
                  break;
               case 9:
               case 11:
                  ns.outputNodePush();
                  sibling = currentNode.getFirstChild();
            }

            while(true) {
               do {
                  if (sibling != null || parentNode == null) {
                     if (sibling == null) {
                        return;
                     }

                     currentNode = sibling;
                     sibling = sibling.getNextSibling();
                     continue label56;
                  }

                  writer.write((byte[])END_TAG.clone());
                  UtfHelpper.writeByte(((Element)parentNode).getTagName(), writer, cache);
                  writer.write(62);
                  ns.outputNodePop();
                  if (parentNode == endnode) {
                     return;
                  }

                  sibling = ((Node)parentNode).getNextSibling();
                  parentNode = ((Node)parentNode).getParentNode();
               } while(parentNode != null && 1 == ((Node)parentNode).getNodeType());

               documentLevel = 1;
               parentNode = null;
            }
         }
      }
   }

   private byte[] engineCanonicalizeXPathNodeSetInternal(Node doc) throws CanonicalizationException {
      try {
         this.canonicalizeXPathNodeSet(doc, doc);
         this.writer.flush();
         byte[] result;
         if (this.writer instanceof ByteArrayOutputStream) {
            result = ((ByteArrayOutputStream)this.writer).toByteArray();
            if (this.reset) {
               ((ByteArrayOutputStream)this.writer).reset();
            } else {
               this.writer.close();
            }

            return result;
         } else if (this.writer instanceof UnsyncByteArrayOutputStream) {
            result = ((UnsyncByteArrayOutputStream)this.writer).toByteArray();
            if (this.reset) {
               ((UnsyncByteArrayOutputStream)this.writer).reset();
            } else {
               this.writer.close();
            }

            return result;
         } else {
            this.writer.close();
            return null;
         }
      } catch (UnsupportedEncodingException var3) {
         throw new CanonicalizationException(var3);
      } catch (IOException var4) {
         throw new CanonicalizationException(var4);
      }
   }

   protected final void canonicalizeXPathNodeSet(Node currentNode, Node endnode) throws CanonicalizationException, IOException {
      if (this.isVisibleInt(currentNode) != -1) {
         boolean currentNodeIsVisible = false;
         NameSpaceSymbTable ns = new NameSpaceSymbTable();
         if (currentNode != null && 1 == currentNode.getNodeType()) {
            this.getParentNameSpaces((Element)currentNode, ns);
         }

         if (currentNode != null) {
            Node sibling = null;
            Node parentNode = null;
            int documentLevel = -1;
            Map cache = new HashMap();

            while(true) {
               switch (currentNode.getNodeType()) {
                  case 1:
                     documentLevel = 0;
                     Element currentElement = (Element)currentNode;
                     String name = null;
                     int i = this.isVisibleDO(currentNode, ns.getLevel());
                     if (i == -1) {
                        sibling = currentNode.getNextSibling();
                     } else {
                        currentNodeIsVisible = i == 1;
                        if (currentNodeIsVisible) {
                           ns.outputNodePush();
                           this.writer.write(60);
                           name = currentElement.getTagName();
                           UtfHelpper.writeByte(name, this.writer, cache);
                        } else {
                           ns.push();
                        }

                        this.outputAttributes(currentElement, ns, cache);
                        if (currentNodeIsVisible) {
                           this.writer.write(62);
                        }

                        sibling = currentNode.getFirstChild();
                        if (sibling == null) {
                           if (currentNodeIsVisible) {
                              this.writer.write((byte[])END_TAG.clone());
                              UtfHelpper.writeByte(name, this.writer, cache);
                              this.writer.write(62);
                              ns.outputNodePop();
                           } else {
                              ns.pop();
                           }

                           if (parentNode != null) {
                              sibling = currentNode.getNextSibling();
                           }
                        } else {
                           parentNode = currentElement;
                        }
                     }
                     break;
                  case 2:
                  case 6:
                  case 12:
                     throw new CanonicalizationException("empty", new Object[]{"illegal node type during traversal"});
                  case 3:
                  case 4:
                     if (this.isVisible(currentNode)) {
                        outputTextToWriter(currentNode.getNodeValue(), this.writer);

                        for(Node nextSibling = currentNode.getNextSibling(); nextSibling != null && (nextSibling.getNodeType() == 3 || nextSibling.getNodeType() == 4); nextSibling = nextSibling.getNextSibling()) {
                           outputTextToWriter(nextSibling.getNodeValue(), this.writer);
                           sibling = nextSibling.getNextSibling();
                        }
                     }
                  case 5:
                  case 10:
                  default:
                     break;
                  case 7:
                     if (this.isVisible(currentNode)) {
                        this.outputPItoWriter((ProcessingInstruction)currentNode, this.writer, documentLevel);
                     }
                     break;
                  case 8:
                     if (this.includeComments && this.isVisibleDO(currentNode, ns.getLevel()) == 1) {
                        this.outputCommentToWriter((Comment)currentNode, this.writer, documentLevel);
                     }
                     break;
                  case 9:
                  case 11:
                     ns.outputNodePush();
                     sibling = currentNode.getFirstChild();
               }

               while(sibling == null && parentNode != null) {
                  if (this.isVisible((Node)parentNode)) {
                     this.writer.write((byte[])END_TAG.clone());
                     UtfHelpper.writeByte(((Element)parentNode).getTagName(), this.writer, cache);
                     this.writer.write(62);
                     ns.outputNodePop();
                  } else {
                     ns.pop();
                  }

                  if (parentNode == endnode) {
                     return;
                  }

                  sibling = ((Node)parentNode).getNextSibling();
                  parentNode = ((Node)parentNode).getParentNode();
                  if (parentNode == null || 1 != ((Node)parentNode).getNodeType()) {
                     parentNode = null;
                     documentLevel = 1;
                  }
               }

               if (sibling == null) {
                  return;
               }

               currentNode = sibling;
               sibling = sibling.getNextSibling();
            }
         }
      }
   }

   protected int isVisibleDO(Node currentNode, int level) {
      if (this.nodeFilter != null) {
         Iterator it = this.nodeFilter.iterator();

         while(it.hasNext()) {
            int i = ((NodeFilter)it.next()).isNodeIncludeDO(currentNode, level);
            if (i != 1) {
               return i;
            }
         }
      }

      return this.xpathNodeSet != null && !this.xpathNodeSet.contains(currentNode) ? 0 : 1;
   }

   protected int isVisibleInt(Node currentNode) {
      if (this.nodeFilter != null) {
         Iterator it = this.nodeFilter.iterator();

         while(it.hasNext()) {
            int i = ((NodeFilter)it.next()).isNodeInclude(currentNode);
            if (i != 1) {
               return i;
            }
         }
      }

      return this.xpathNodeSet != null && !this.xpathNodeSet.contains(currentNode) ? 0 : 1;
   }

   protected boolean isVisible(Node currentNode) {
      if (this.nodeFilter != null) {
         Iterator it = this.nodeFilter.iterator();

         while(it.hasNext()) {
            if (((NodeFilter)it.next()).isNodeInclude(currentNode) != 1) {
               return false;
            }
         }
      }

      return this.xpathNodeSet == null || this.xpathNodeSet.contains(currentNode);
   }

   protected void handleParent(Element e, NameSpaceSymbTable ns) {
      if (e.hasAttributes() || e.getNamespaceURI() != null) {
         NamedNodeMap attrs = e.getAttributes();
         int attrsLength = attrs.getLength();

         String Name;
         for(int i = 0; i < attrsLength; ++i) {
            Attr attribute = (Attr)attrs.item(i);
            Name = attribute.getLocalName();
            String NValue = attribute.getNodeValue();
            if ("http://www.w3.org/2000/xmlns/".equals(attribute.getNamespaceURI()) && (!"xml".equals(Name) || !"http://www.w3.org/XML/1998/namespace".equals(NValue))) {
               ns.addMapping(Name, NValue, attribute);
            }
         }

         if (e.getNamespaceURI() != null) {
            String NName = e.getPrefix();
            String NValue = e.getNamespaceURI();
            if (NName != null && !NName.equals("")) {
               Name = "xmlns:" + NName;
            } else {
               NName = "xmlns";
               Name = "xmlns";
            }

            Attr n = e.getOwnerDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", Name);
            n.setValue(NValue);
            ns.addMapping(NName, NValue, n);
         }

      }
   }

   protected final void getParentNameSpaces(Element el, NameSpaceSymbTable ns) {
      Node n1 = el.getParentNode();
      if (n1 != null && 1 == n1.getNodeType()) {
         List parents = new ArrayList();

         for(Node parent = n1; parent != null && 1 == parent.getNodeType(); parent = parent.getParentNode()) {
            parents.add((Element)parent);
         }

         ListIterator it = parents.listIterator(parents.size());

         while(it.hasPrevious()) {
            Element ele = (Element)it.previous();
            this.handleParent(ele, ns);
         }

         parents.clear();
         Attr nsprefix = ns.getMappingWithoutRendered("xmlns");
         if (nsprefix != null && "".equals(nsprefix.getValue())) {
            ns.addMappingAndRender("xmlns", "", this.getNullNode(nsprefix.getOwnerDocument()));
         }

      }
   }

   abstract void outputAttributes(Element var1, NameSpaceSymbTable var2, Map var3) throws CanonicalizationException, DOMException, IOException;

   abstract void outputAttributesSubtree(Element var1, NameSpaceSymbTable var2, Map var3) throws CanonicalizationException, DOMException, IOException;

   abstract void circumventBugIfNeeded(XMLSignatureInput var1) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException;

   protected static final void outputAttrToWriter(String name, String value, OutputStream writer, Map cache) throws IOException {
      writer.write(32);
      UtfHelpper.writeByte(name, writer, cache);
      writer.write((byte[])EQUALS_STR.clone());
      int length = value.length();
      int i = 0;

      while(true) {
         byte[] toWrite;
         label23:
         while(true) {
            if (i >= length) {
               writer.write(34);
               return;
            }

            int c = value.codePointAt(i);
            i += Character.charCount(c);
            switch (c) {
               case 9:
                  toWrite = (byte[])X9.clone();
                  break label23;
               case 10:
                  toWrite = (byte[])XA.clone();
                  break label23;
               case 13:
                  toWrite = (byte[])XD.clone();
                  break label23;
               case 34:
                  toWrite = (byte[])QUOT.clone();
                  break label23;
               case 38:
                  toWrite = (byte[])AMP.clone();
                  break label23;
               case 60:
                  toWrite = (byte[])LT.clone();
                  break label23;
               default:
                  if (c < 128) {
                     writer.write(c);
                  } else {
                     UtfHelpper.writeCodePointToUtf8(c, writer);
                  }
            }
         }

         writer.write(toWrite);
      }
   }

   protected void outputPItoWriter(ProcessingInstruction currentPI, OutputStream writer, int position) throws IOException {
      if (position == 1) {
         writer.write(10);
      }

      writer.write((byte[])BEGIN_PI.clone());
      String target = currentPI.getTarget();
      int length = target.length();
      int i = 0;

      int i;
      while(i < length) {
         i = target.codePointAt(i);
         i += Character.charCount(i);
         if (i == 13) {
            writer.write((byte[])XD.clone());
         } else if (i < 128) {
            writer.write(i);
         } else {
            UtfHelpper.writeCodePointToUtf8(i, writer);
         }
      }

      String data = currentPI.getData();
      length = data.length();
      if (length > 0) {
         writer.write(32);
         i = 0;

         while(i < length) {
            int c = data.codePointAt(i);
            i += Character.charCount(c);
            if (c == 13) {
               writer.write((byte[])XD.clone());
            } else {
               UtfHelpper.writeCodePointToUtf8(c, writer);
            }
         }
      }

      writer.write((byte[])END_PI.clone());
      if (position == -1) {
         writer.write(10);
      }

   }

   protected void outputCommentToWriter(Comment currentComment, OutputStream writer, int position) throws IOException {
      if (position == 1) {
         writer.write(10);
      }

      writer.write((byte[])BEGIN_COMM.clone());
      String data = currentComment.getData();
      int length = data.length();
      int i = 0;

      while(i < length) {
         int c = data.codePointAt(i);
         i += Character.charCount(c);
         if (c == 13) {
            writer.write((byte[])XD.clone());
         } else if (c < 128) {
            writer.write(c);
         } else {
            UtfHelpper.writeCodePointToUtf8(c, writer);
         }
      }

      writer.write((byte[])END_COMM.clone());
      if (position == -1) {
         writer.write(10);
      }

   }

   protected static final void outputTextToWriter(String text, OutputStream writer) throws IOException {
      int length = text.length();
      int i = 0;

      while(true) {
         byte[] toWrite;
         label21:
         while(true) {
            if (i >= length) {
               return;
            }

            int c = text.codePointAt(i);
            i += Character.charCount(c);
            switch (c) {
               case 13:
                  toWrite = (byte[])XD.clone();
                  break label21;
               case 38:
                  toWrite = (byte[])AMP.clone();
                  break label21;
               case 60:
                  toWrite = (byte[])LT.clone();
                  break label21;
               case 62:
                  toWrite = (byte[])GT.clone();
                  break label21;
               default:
                  if (c < 128) {
                     writer.write(c);
                  } else {
                     UtfHelpper.writeCodePointToUtf8(c, writer);
                  }
            }
         }

         writer.write(toWrite);
      }
   }

   protected Attr getNullNode(Document ownerDocument) {
      if (this.nullNode == null) {
         try {
            this.nullNode = ownerDocument.createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns");
            this.nullNode.setValue("");
         } catch (Exception var3) {
            throw new RuntimeException("Unable to create nullNode: " + var3);
         }
      }

      return this.nullNode;
   }
}
