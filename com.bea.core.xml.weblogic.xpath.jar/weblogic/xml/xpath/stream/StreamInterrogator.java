package weblogic.xml.xpath.stream;

import java.util.Iterator;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.XMLName;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Interrogator;

public final class StreamInterrogator implements Interrogator {
   public static final Interrogator INSTANCE = new StreamInterrogator();

   private StreamInterrogator() {
   }

   public String getNodeStringValue(Object node) {
      return ((StreamNode)node).getNodeStringValue();
   }

   public String getLocalName(Object node) {
      XMLName name = ((StreamNode)node).getNodeName();
      return name == null ? null : name.getLocalName();
   }

   public String getPrefix(Object node) {
      XMLName name = ((StreamNode)node).getNodeName();
      return name == null ? null : name.getPrefix();
   }

   public String getExpandedName(Object node) {
      XMLName name = ((StreamNode)node).getNodeName();
      return name == null ? null : name.getQualifiedName();
   }

   public String getNamespaceURI(Object node) {
      XMLName name = ((StreamNode)node).getNodeName();
      return name == null ? null : name.getNamespaceUri();
   }

   public String getAttributeValue(Object node, String nsPrefix, String attName) {
      Attribute att = ((StreamNode)node).getAttributeByName(ElementFactory.createXMLName(nsPrefix, attName));
      return att == null ? null : att.getValue();
   }

   public boolean isComment(Object node) {
      return ((StreamNode)node).getNodeType() == 32;
   }

   public boolean isProcessingInstruction(Object node) {
      return ((StreamNode)node).getNodeType() == 8;
   }

   public boolean isText(Object node) {
      return ((StreamNode)node).getNodeType() == 16;
   }

   public boolean isNode(Object node) {
      throw new IllegalStateException();
   }

   public boolean isElement(Object node) {
      throw new IllegalStateException();
   }

   public Object getParent(Object node) {
      throw new IllegalStateException();
   }

   public Object getNodeById(Context ctx, String id) {
      throw new IllegalStateException();
   }

   public Iterator getChildren(Object node) {
      throw new IllegalStateException();
   }
}
