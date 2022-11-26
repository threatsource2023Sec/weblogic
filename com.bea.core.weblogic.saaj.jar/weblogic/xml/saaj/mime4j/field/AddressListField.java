package weblogic.xml.saaj.mime4j.field;

import weblogic.xml.saaj.mime4j.field.address.AddressList;
import weblogic.xml.saaj.mime4j.field.address.parser.ParseException;

public class AddressListField extends Field {
   private AddressList addressList;
   private ParseException parseException;

   public AddressList getAddressList() {
      return this.addressList;
   }

   public ParseException getParseException() {
      return this.parseException;
   }

   protected void parseBody(String body) {
      try {
         this.addressList = AddressList.parse(body);
      } catch (ParseException var3) {
         this.parseException = var3;
      }

   }
}
