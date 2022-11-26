package weblogic.diagnostics.harvester.internal;

import java.io.StringReader;
import weblogic.diagnostics.harvester.AttributeNameNormalizer;
import weblogic.diagnostics.i18n.DiagnosticsTextHarvesterTextFormatter;

public class HarvesterDefaultAttributeNormalizer implements AttributeNameNormalizer {
   private static final String INVALID_ATTRIBUTE_SPEC_TEXT = DiagnosticsTextHarvesterTextFormatter.getInstance().getInvalidAttributeSpecText();

   public String getNormalizedAttributeName(String rawAttributeSpec) {
      HarvesterDefaultAttributeNormalizerLexer l = new HarvesterDefaultAttributeNormalizerLexer(new StringReader(rawAttributeSpec));
      HarvesterDefaultAttributeNormalizerParser p = new HarvesterDefaultAttributeNormalizerParser(l);

      try {
         String s = p.normalizeAttributeSpec();
         return s;
      } catch (Exception var6) {
         throw new IllegalArgumentException(INVALID_ATTRIBUTE_SPEC_TEXT + var6.getMessage(), var6);
      }
   }

   public String getAttributeName(String rawAttributeSpec) {
      HarvesterDefaultAttributeNormalizerLexer l = new HarvesterDefaultAttributeNormalizerLexer(new StringReader(rawAttributeSpec));
      HarvesterDefaultAttributeNormalizerParser p = new HarvesterDefaultAttributeNormalizerParser(l);

      try {
         String s = p.attributeNameSpec();
         return s;
      } catch (Exception var6) {
         throw new IllegalArgumentException(INVALID_ATTRIBUTE_SPEC_TEXT + var6.getMessage(), var6);
      }
   }
}
