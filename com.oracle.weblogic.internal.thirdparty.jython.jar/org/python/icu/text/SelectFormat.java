package org.python.icu.text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import org.python.icu.impl.PatternProps;

public class SelectFormat extends Format {
   private static final long serialVersionUID = 2993154333257524984L;
   private String pattern = null;
   private transient MessagePattern msgPattern;

   public SelectFormat(String pattern) {
      this.applyPattern(pattern);
   }

   private void reset() {
      this.pattern = null;
      if (this.msgPattern != null) {
         this.msgPattern.clear();
      }

   }

   public void applyPattern(String pattern) {
      this.pattern = pattern;
      if (this.msgPattern == null) {
         this.msgPattern = new MessagePattern();
      }

      try {
         this.msgPattern.parseSelectStyle(pattern);
      } catch (RuntimeException var3) {
         this.reset();
         throw var3;
      }
   }

   public String toPattern() {
      return this.pattern;
   }

   static int findSubMessage(MessagePattern pattern, int partIndex, String keyword) {
      int count = pattern.countParts();
      int msgStart = 0;

      while(true) {
         MessagePattern.Part part = pattern.getPart(partIndex++);
         MessagePattern.Part.Type type = part.getType();
         if (type != MessagePattern.Part.Type.ARG_LIMIT) {
            assert type == MessagePattern.Part.Type.ARG_SELECTOR;

            if (pattern.partSubstringMatches(part, keyword)) {
               return partIndex;
            }

            if (msgStart == 0 && pattern.partSubstringMatches(part, "other")) {
               msgStart = partIndex;
            }

            partIndex = pattern.getLimitPartIndex(partIndex);
            ++partIndex;
            if (partIndex < count) {
               continue;
            }
         }

         return msgStart;
      }
   }

   public final String format(String keyword) {
      if (!PatternProps.isIdentifier(keyword)) {
         throw new IllegalArgumentException("Invalid formatting argument.");
      } else if (this.msgPattern != null && this.msgPattern.countParts() != 0) {
         int msgStart = findSubMessage(this.msgPattern, 0, keyword);
         if (!this.msgPattern.jdkAposMode()) {
            int msgLimit = this.msgPattern.getLimitPartIndex(msgStart);
            return this.msgPattern.getPatternString().substring(this.msgPattern.getPart(msgStart).getLimit(), this.msgPattern.getPatternIndex(msgLimit));
         } else {
            StringBuilder result = null;
            int prevIndex = this.msgPattern.getPart(msgStart).getLimit();
            int i = msgStart;

            while(true) {
               ++i;
               MessagePattern.Part part = this.msgPattern.getPart(i);
               MessagePattern.Part.Type type = part.getType();
               int index = part.getIndex();
               if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                  if (result == null) {
                     return this.pattern.substring(prevIndex, index);
                  }

                  return result.append(this.pattern, prevIndex, index).toString();
               }

               if (type == MessagePattern.Part.Type.SKIP_SYNTAX) {
                  if (result == null) {
                     result = new StringBuilder();
                  }

                  result.append(this.pattern, prevIndex, index);
                  prevIndex = part.getLimit();
               } else if (type == MessagePattern.Part.Type.ARG_START) {
                  if (result == null) {
                     result = new StringBuilder();
                  }

                  result.append(this.pattern, prevIndex, index);
                  prevIndex = index;
                  i = this.msgPattern.getLimitPartIndex(i);
                  index = this.msgPattern.getPart(i).getLimit();
                  MessagePattern.appendReducedApostrophes(this.pattern, prevIndex, index, result);
                  prevIndex = index;
               }
            }
         }
      } else {
         throw new IllegalStateException("Invalid format error.");
      }
   }

   public StringBuffer format(Object keyword, StringBuffer toAppendTo, FieldPosition pos) {
      if (keyword instanceof String) {
         toAppendTo.append(this.format((String)keyword));
         return toAppendTo;
      } else {
         throw new IllegalArgumentException("'" + keyword + "' is not a String");
      }
   }

   public Object parseObject(String source, ParsePosition pos) {
      throw new UnsupportedOperationException();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj != null && this.getClass() == obj.getClass()) {
         SelectFormat sf = (SelectFormat)obj;
         return this.msgPattern == null ? sf.msgPattern == null : this.msgPattern.equals(sf.msgPattern);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.pattern != null ? this.pattern.hashCode() : 0;
   }

   public String toString() {
      return "pattern='" + this.pattern + "'";
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      if (this.pattern != null) {
         this.applyPattern(this.pattern);
      }

   }
}
