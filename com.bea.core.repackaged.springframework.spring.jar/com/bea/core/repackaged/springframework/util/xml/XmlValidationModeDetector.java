package com.bea.core.repackaged.springframework.util.xml;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.BufferedReader;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class XmlValidationModeDetector {
   public static final int VALIDATION_NONE = 0;
   public static final int VALIDATION_AUTO = 1;
   public static final int VALIDATION_DTD = 2;
   public static final int VALIDATION_XSD = 3;
   private static final String DOCTYPE = "DOCTYPE";
   private static final String START_COMMENT = "<!--";
   private static final String END_COMMENT = "-->";
   private boolean inComment;

   public int detectValidationMode(InputStream inputStream) throws IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      byte var4;
      try {
         boolean isDtdValidated = false;

         while(true) {
            String content;
            if ((content = reader.readLine()) != null) {
               content = this.consumeCommentTokens(content);
               if (this.inComment || !StringUtils.hasText(content)) {
                  continue;
               }

               if (this.hasDoctype(content)) {
                  isDtdValidated = true;
               } else if (!this.hasOpeningTag(content)) {
                  continue;
               }
            }

            int var5 = isDtdValidated ? 2 : 3;
            return var5;
         }
      } catch (CharConversionException var9) {
         var4 = 1;
      } finally {
         reader.close();
      }

      return var4;
   }

   private boolean hasDoctype(String content) {
      return content.contains("DOCTYPE");
   }

   private boolean hasOpeningTag(String content) {
      if (this.inComment) {
         return false;
      } else {
         int openTagIndex = content.indexOf(60);
         return openTagIndex > -1 && content.length() > openTagIndex + 1 && Character.isLetter(content.charAt(openTagIndex + 1));
      }
   }

   @Nullable
   private String consumeCommentTokens(String line) {
      int indexOfStartComment = line.indexOf("<!--");
      if (indexOfStartComment == -1 && !line.contains("-->")) {
         return line;
      } else {
         String result = "";
         String currLine = line;
         if (indexOfStartComment >= 0) {
            result = line.substring(0, indexOfStartComment);
            currLine = line.substring(indexOfStartComment);
         }

         do {
            if ((currLine = this.consume(currLine)) == null) {
               return null;
            }
         } while(this.inComment || currLine.trim().startsWith("<!--"));

         return result + currLine;
      }
   }

   @Nullable
   private String consume(String line) {
      int index = this.inComment ? this.endComment(line) : this.startComment(line);
      return index == -1 ? null : line.substring(index);
   }

   private int startComment(String line) {
      return this.commentToken(line, "<!--", true);
   }

   private int endComment(String line) {
      return this.commentToken(line, "-->", false);
   }

   private int commentToken(String line, String token, boolean inCommentIfPresent) {
      int index = line.indexOf(token);
      if (index > -1) {
         this.inComment = inCommentIfPresent;
      }

      return index == -1 ? index : index + token.length();
   }
}
