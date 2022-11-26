package weblogic.xml.xpath.common;

import java.util.Iterator;

public interface Interrogator {
   String getNodeStringValue(Object var1);

   String getLocalName(Object var1);

   String getPrefix(Object var1);

   String getAttributeValue(Object var1, String var2, String var3);

   String getNamespaceURI(Object var1);

   String getExpandedName(Object var1);

   Object getParent(Object var1);

   Object getNodeById(Context var1, String var2);

   Iterator getChildren(Object var1);

   boolean isComment(Object var1);

   boolean isNode(Object var1);

   boolean isProcessingInstruction(Object var1);

   boolean isText(Object var1);

   boolean isElement(Object var1);
}
