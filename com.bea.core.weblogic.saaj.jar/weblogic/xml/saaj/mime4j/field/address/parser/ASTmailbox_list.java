package weblogic.xml.saaj.mime4j.field.address.parser;

public class ASTmailbox_list extends SimpleNode {
   public ASTmailbox_list(int id) {
      super(id);
   }

   public ASTmailbox_list(AddressListParser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(AddressListParserVisitor visitor, Object data) {
      return visitor.visit((SimpleNode)this, data);
   }
}
