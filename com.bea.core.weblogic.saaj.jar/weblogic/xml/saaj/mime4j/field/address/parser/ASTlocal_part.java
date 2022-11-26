package weblogic.xml.saaj.mime4j.field.address.parser;

public class ASTlocal_part extends SimpleNode {
   public String image;

   public ASTlocal_part(int id) {
      super(id);
   }

   public ASTlocal_part(AddressListParser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(AddressListParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }
}
