package weblogic.xml.domimpl;

import org.w3c.dom.CDATASection;

class CDATASectionImpl extends TextImpl implements CDATASection {
   private static final long serialVersionUID = -890333322057505196L;

   public CDATASectionImpl(DocumentImpl owner_doc, String data) {
      super(owner_doc, data);
   }

   public short getNodeType() {
      return 4;
   }

   public String getNodeName() {
      return "#cdata-section";
   }
}
