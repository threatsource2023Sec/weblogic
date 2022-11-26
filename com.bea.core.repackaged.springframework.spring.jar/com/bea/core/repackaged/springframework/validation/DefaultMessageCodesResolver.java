package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DefaultMessageCodesResolver implements MessageCodesResolver, Serializable {
   public static final String CODE_SEPARATOR = ".";
   private static final MessageCodeFormatter DEFAULT_FORMATTER;
   private String prefix = "";
   private MessageCodeFormatter formatter;

   public DefaultMessageCodesResolver() {
      this.formatter = DEFAULT_FORMATTER;
   }

   public void setPrefix(@Nullable String prefix) {
      this.prefix = prefix != null ? prefix : "";
   }

   protected String getPrefix() {
      return this.prefix;
   }

   public void setMessageCodeFormatter(@Nullable MessageCodeFormatter formatter) {
      this.formatter = formatter != null ? formatter : DEFAULT_FORMATTER;
   }

   public String[] resolveMessageCodes(String errorCode, String objectName) {
      return this.resolveMessageCodes(errorCode, objectName, "", (Class)null);
   }

   public String[] resolveMessageCodes(String errorCode, String objectName, String field, @Nullable Class fieldType) {
      Set codeList = new LinkedHashSet();
      List fieldList = new ArrayList();
      this.buildFieldList(field, fieldList);
      this.addCodes(codeList, errorCode, objectName, fieldList);
      int dotIndex = field.lastIndexOf(46);
      if (dotIndex != -1) {
         this.buildFieldList(field.substring(dotIndex + 1), fieldList);
      }

      this.addCodes(codeList, errorCode, (String)null, fieldList);
      if (fieldType != null) {
         this.addCode(codeList, errorCode, (String)null, fieldType.getName());
      }

      this.addCode(codeList, errorCode, (String)null, (String)null);
      return StringUtils.toStringArray((Collection)codeList);
   }

   private void addCodes(Collection codeList, String errorCode, @Nullable String objectName, Iterable fields) {
      Iterator var5 = fields.iterator();

      while(var5.hasNext()) {
         String field = (String)var5.next();
         this.addCode(codeList, errorCode, objectName, field);
      }

   }

   private void addCode(Collection codeList, String errorCode, @Nullable String objectName, @Nullable String field) {
      codeList.add(this.postProcessMessageCode(this.formatter.format(errorCode, objectName, field)));
   }

   protected void buildFieldList(String field, List fieldList) {
      fieldList.add(field);
      String plainField = field;
      int keyIndex = field.lastIndexOf(91);

      while(keyIndex != -1) {
         int endKeyIndex = plainField.indexOf(93, keyIndex);
         if (endKeyIndex != -1) {
            plainField = plainField.substring(0, keyIndex) + plainField.substring(endKeyIndex + 1);
            fieldList.add(plainField);
            keyIndex = plainField.lastIndexOf(91);
         } else {
            keyIndex = -1;
         }
      }

   }

   protected String postProcessMessageCode(String code) {
      return this.getPrefix() + code;
   }

   static {
      DEFAULT_FORMATTER = DefaultMessageCodesResolver.Format.PREFIX_ERROR_CODE;
   }

   public static enum Format implements MessageCodeFormatter {
      PREFIX_ERROR_CODE {
         public String format(String errorCode, @Nullable String objectName, @Nullable String field) {
            return toDelimitedString(new String[]{errorCode, objectName, field});
         }
      },
      POSTFIX_ERROR_CODE {
         public String format(String errorCode, @Nullable String objectName, @Nullable String field) {
            return toDelimitedString(new String[]{objectName, field, errorCode});
         }
      };

      private Format() {
      }

      public static String toDelimitedString(String... elements) {
         StringBuilder rtn = new StringBuilder();
         String[] var2 = elements;
         int var3 = elements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String element = var2[var4];
            if (StringUtils.hasLength(element)) {
               rtn.append(rtn.length() == 0 ? "" : ".");
               rtn.append(element);
            }
         }

         return rtn.toString();
      }

      // $FF: synthetic method
      Format(Object x2) {
         this();
      }
   }
}
