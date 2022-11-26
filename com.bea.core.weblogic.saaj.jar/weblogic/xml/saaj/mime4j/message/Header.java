package weblogic.xml.saaj.mime4j.message;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import weblogic.xml.saaj.mime4j.AbstractContentHandler;
import weblogic.xml.saaj.mime4j.MimeStreamParser;
import weblogic.xml.saaj.mime4j.field.Field;

public class Header {
   private List fields = new LinkedList();
   private HashMap fieldMap = new HashMap();

   public Header() {
   }

   public Header(InputStream is) throws IOException {
      final MimeStreamParser parser = new MimeStreamParser();
      parser.setContentHandler(new AbstractContentHandler() {
         public void endHeader() {
            parser.stop();
         }

         public void field(String fieldData) {
            Header.this.addField(Field.parse(fieldData));
         }
      });
      parser.parse(is);
   }

   public void addField(Field field) {
      List values = (List)this.fieldMap.get(field.getName().toLowerCase());
      if (values == null) {
         values = new LinkedList();
         this.fieldMap.put(field.getName().toLowerCase(), values);
      }

      ((List)values).add(field);
      this.fields.add(field);
   }

   public List getFields() {
      return Collections.unmodifiableList(this.fields);
   }

   public Field getField(String name) {
      List l = (List)this.fieldMap.get(name.toLowerCase());
      return l != null && !l.isEmpty() ? (Field)l.get(0) : null;
   }

   public List getFields(String name) {
      List l = (List)this.fieldMap.get(name.toLowerCase());
      return Collections.unmodifiableList(l);
   }

   public String toString() {
      StringBuffer str = new StringBuffer();
      Iterator it = this.fields.iterator();

      while(it.hasNext()) {
         str.append(it.next().toString());
         str.append("\r\n");
      }

      return str.toString();
   }
}
