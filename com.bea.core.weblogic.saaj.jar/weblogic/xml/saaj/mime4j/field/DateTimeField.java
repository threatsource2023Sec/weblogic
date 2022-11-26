package weblogic.xml.saaj.mime4j.field;

import java.util.Date;
import weblogic.xml.saaj.mime4j.field.datetime.DateTime;
import weblogic.xml.saaj.mime4j.field.datetime.parser.ParseException;

public class DateTimeField extends Field {
   private Date date;
   private ParseException parseException;

   public Date getDate() {
      return this.date;
   }

   public ParseException getParseException() {
      return this.parseException;
   }

   protected void parseBody(String body) {
      try {
         this.date = DateTime.parse(body).getDate();
      } catch (ParseException var3) {
         this.parseException = var3;
      }

   }
}
