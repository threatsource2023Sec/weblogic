package com.bea.xml_.impl.jam.annotation;

import com.bea.xml_.impl.jam.mutable.MAnnotatedElement;
import com.bea.xml_.impl.jam.mutable.MAnnotation;
import com.sun.javadoc.Tag;
import java.util.Enumeration;
import java.util.Properties;

public class WhitespaceDelimitedTagParser extends JavadocTagParser {
   public void parse(MAnnotatedElement target, Tag tag) {
      MAnnotation[] anns = this.createAnnotations(target, tag);
      String tagText = tag.text();
      if (tagText != null) {
         tagText = tagText.trim();
         if (tagText.length() != 0) {
            Properties props = new Properties();
            this.parseAssignments(props, tagText);
            if (props.size() > 0) {
               Enumeration names = props.propertyNames();

               while(names.hasMoreElements()) {
                  String name = (String)names.nextElement();
                  this.setValue(anns, name, props.getProperty(name));
               }
            } else {
               this.setSingleValueText(anns, tag);
            }

         }
      }
   }

   private void parseAssignments(Properties out, String line) {
      this.getLogger().verbose((String)("PARSING LINE " + line), this);
      line = this.removeComments(line);

      while(null != line && -1 != line.indexOf("=")) {
         int keyStart = true;
         int keyEnd = true;
         int ind = 0;

         char c;
         for(c = line.charAt(ind); this.isBlank(c); c = line.charAt(ind)) {
            ++ind;
         }

         int keyStart;
         for(keyStart = ind; this.isLegal(line.charAt(ind)); ++ind) {
         }

         String key = line.substring(keyStart, ind);
         ind = line.indexOf("=");
         if (ind == -1) {
            return;
         }

         ++ind;

         try {
            c = line.charAt(ind);
         } catch (StringIndexOutOfBoundsException var12) {
            var12.printStackTrace();
         }

         while(this.isBlank(c)) {
            ++ind;
            c = line.charAt(ind);
         }

         int valueStart = true;
         int valueEnd = true;
         int valueStart;
         int valueEnd;
         if (c == '"') {
            ++ind;
            valueStart = ind;

            while('"' != line.charAt(ind)) {
               ++ind;
               if (ind >= line.length()) {
                  this.getLogger().verbose((String)("missing double quotes on line " + line), this);
               }
            }

            valueEnd = ind;
         } else {
            for(valueStart = ind++; ind < line.length() && this.isLegal(line.charAt(ind)); ++ind) {
            }

            valueEnd = ind;
         }

         String value = line.substring(valueStart, valueEnd);
         if (ind < line.length()) {
            line = line.substring(ind + 1);
         } else {
            line = null;
         }

         this.getLogger().verbose((String)("SETTING KEY:" + key + " VALUE:" + value), this);
         out.setProperty(key, value);
      }

   }

   private String removeComments(String value) {
      String result = "";
      int size = value.length();
      String current = value;
      int currentIndex = 0;
      int beginning = value.indexOf("//");
      int doubleQuotesIndex = value.indexOf("\"");
      if (-1 != doubleQuotesIndex && doubleQuotesIndex < beginning) {
         result = value;
      } else {
         while(true) {
            if (currentIndex >= size || beginning == -1) {
               result = result + current;
               break;
            }

            beginning = value.indexOf("//", currentIndex);
            if (-1 != beginning) {
               if (beginning > 0 && value.charAt(beginning - 1) == ':') {
                  currentIndex = beginning + 2;
               } else {
                  int end = value.indexOf(10, beginning);
                  if (-1 == end) {
                     end = size;
                  }

                  result = result + value.substring(currentIndex, beginning).trim() + "\n";
                  current = value.substring(end);
                  currentIndex = end;
               }
            }
         }
      }

      return result.trim();
   }

   private boolean isBlank(char c) {
      return c == ' ' || c == '\t' || c == '\n';
   }

   private boolean isLegal(char c) {
      return !this.isBlank(c) && c != '=';
   }
}
