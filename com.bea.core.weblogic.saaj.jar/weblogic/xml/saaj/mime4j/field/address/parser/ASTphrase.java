package weblogic.xml.saaj.mime4j.field.address.parser;

public class ASTphrase extends SimpleNode {
   public ASTphrase(int id) {
      super(id);
   }

   public ASTphrase(AddressListParser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(AddressListParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }
}
