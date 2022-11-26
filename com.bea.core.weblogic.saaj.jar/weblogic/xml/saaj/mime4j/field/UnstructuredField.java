package weblogic.xml.saaj.mime4j.field;

import weblogic.xml.saaj.mime4j.decoder.DecoderUtil;

public class UnstructuredField extends Field {
   private String value;

   protected UnstructuredField() {
   }

   protected void parseBody(String body) {
      this.value = DecoderUtil.decodeEncodedWords(body);
   }

   public String getValue() {
      return this.value;
   }
}
