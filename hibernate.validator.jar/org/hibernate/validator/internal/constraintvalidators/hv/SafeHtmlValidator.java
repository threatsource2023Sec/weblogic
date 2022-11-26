package org.hibernate.validator.internal.constraintvalidators.hv;

import java.util.Iterator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.SafeHtml;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

public class SafeHtmlValidator implements ConstraintValidator {
   private Whitelist whitelist;
   private String baseURI;

   public void initialize(SafeHtml safeHtmlAnnotation) {
      switch (safeHtmlAnnotation.whitelistType()) {
         case BASIC:
            this.whitelist = Whitelist.basic();
            break;
         case BASIC_WITH_IMAGES:
            this.whitelist = Whitelist.basicWithImages();
            break;
         case NONE:
            this.whitelist = Whitelist.none();
            break;
         case RELAXED:
            this.whitelist = Whitelist.relaxed();
            break;
         case SIMPLE_TEXT:
            this.whitelist = Whitelist.simpleText();
      }

      this.baseURI = safeHtmlAnnotation.baseURI();
      this.whitelist.addTags(safeHtmlAnnotation.additionalTags());
      SafeHtml.Tag[] var2 = safeHtmlAnnotation.additionalTagsWithAttributes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         SafeHtml.Tag tag = var2[var4];
         this.whitelist.addTags(new String[]{tag.name()});
         if (tag.attributes().length > 0) {
            this.whitelist.addAttributes(tag.name(), tag.attributes());
         }

         SafeHtml.Attribute[] var6 = tag.attributesWithProtocols();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            SafeHtml.Attribute attribute = var6[var8];
            this.whitelist.addAttributes(tag.name(), new String[]{attribute.name()});
            if (attribute.protocols().length > 0) {
               this.whitelist.addProtocols(tag.name(), attribute.name(), attribute.protocols());
            }
         }
      }

   }

   public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
      return value == null ? true : (new Cleaner(this.whitelist)).isValid(this.getFragmentAsDocument(value));
   }

   private Document getFragmentAsDocument(CharSequence value) {
      Document fragment = Jsoup.parse(value.toString(), this.baseURI, Parser.xmlParser());
      Document document = Document.createShell(this.baseURI);
      Iterator nodes = fragment.children().iterator();

      while(nodes.hasNext()) {
         document.body().appendChild((Node)nodes.next());
      }

      return document;
   }
}
