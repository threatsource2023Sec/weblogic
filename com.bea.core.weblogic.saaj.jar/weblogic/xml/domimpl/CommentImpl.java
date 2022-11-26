package weblogic.xml.domimpl;

import org.w3c.dom.Comment;

public class CommentImpl extends CharacterDataImpl implements Comment {
   private static final long serialVersionUID = -2905030271825202266L;

   public CommentImpl(DocumentImpl owner_doc, String data) {
      super(owner_doc, data);
   }

   public String getNodeName() {
      return "#comment";
   }

   public short getNodeType() {
      return 8;
   }
}
