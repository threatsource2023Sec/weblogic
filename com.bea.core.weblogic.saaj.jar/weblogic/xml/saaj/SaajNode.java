package weblogic.xml.saaj;

import weblogic.xml.domimpl.ChildNode;
import weblogic.xml.domimpl.NodeImpl;

public interface SaajNode {
   SaajNode createAndAppendSaajElement(NodeImpl var1, String var2, String var3, String var4, int var5);

   ChildNode fixChildSaajType(ChildNode var1);

   boolean isSaajTyped();
}
