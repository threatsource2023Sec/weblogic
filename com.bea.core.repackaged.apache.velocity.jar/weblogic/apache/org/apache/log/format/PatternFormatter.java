package weblogic.apache.org.apache.log.format;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import weblogic.apache.org.apache.log.ContextMap;
import weblogic.apache.org.apache.log.ContextStack;
import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.Priority;

public class PatternFormatter implements Formatter, weblogic.apache.org.apache.log.Formatter {
   private static final int TYPE_TEXT = 1;
   private static final int TYPE_CATEGORY = 2;
   private static final int TYPE_CONTEXT = 3;
   private static final int TYPE_MESSAGE = 4;
   private static final int TYPE_TIME = 5;
   private static final int TYPE_RELATIVE_TIME = 6;
   private static final int TYPE_THROWABLE = 7;
   private static final int TYPE_PRIORITY = 8;
   protected static final int MAX_TYPE = 8;
   private static final String TYPE_CATEGORY_STR = "category";
   private static final String TYPE_CONTEXT_STR = "context";
   private static final String TYPE_MESSAGE_STR = "message";
   private static final String TYPE_TIME_STR = "time";
   private static final String TYPE_RELATIVE_TIME_STR = "rtime";
   private static final String TYPE_THROWABLE_STR = "throwable";
   private static final String TYPE_PRIORITY_STR = "priority";
   private static final String SPACE_16 = "                ";
   private static final String SPACE_8 = "        ";
   private static final String SPACE_4 = "    ";
   private static final String SPACE_2 = "  ";
   private static final String SPACE_1 = " ";
   private static final String EOL = System.getProperty("line.separator", "\n");
   private PatternRun[] m_formatSpecification;
   private SimpleDateFormat m_simpleDateFormat;
   private final Date m_date = new Date();

   /** @deprecated */
   public PatternFormatter() {
   }

   public PatternFormatter(String pattern) {
      this.parse(pattern);
   }

   private int addPatternRun(Stack stack, char[] pattern, int index) {
      PatternRun run = new PatternRun();
      int start = index++;
      if ('+' == pattern[index]) {
         ++index;
      } else if ('-' == pattern[index]) {
         run.m_rightJustify = true;
         ++index;
      }

      int typeStart;
      if (Character.isDigit(pattern[index])) {
         for(typeStart = 0; Character.isDigit(pattern[index]); ++index) {
            typeStart = typeStart * 10 + (pattern[index] - 48);
         }

         run.m_minSize = typeStart;
      }

      if (index < pattern.length && '.' == pattern[index]) {
         ++index;
         if (Character.isDigit(pattern[index])) {
            for(typeStart = 0; Character.isDigit(pattern[index]); ++index) {
               typeStart = typeStart * 10 + (pattern[index] - 48);
            }

            run.m_maxSize = typeStart;
         }
      }

      if (index < pattern.length && '{' == pattern[index]) {
         for(typeStart = index; index < pattern.length && pattern[index] != ':' && pattern[index] != '}'; ++index) {
         }

         int typeEnd = index - 1;
         String type = new String(pattern, typeStart + 1, typeEnd - typeStart);
         run.m_type = this.getTypeIdFor(type);
         if (index < pattern.length && pattern[index] == ':') {
            ++index;

            while(index < pattern.length && pattern[index] != '}') {
               ++index;
            }

            int length = index - typeEnd - 2;
            if (0 != length) {
               run.m_format = new String(pattern, typeEnd + 2, length);
            }
         }

         if (index < pattern.length && '}' == pattern[index]) {
            ++index;
            stack.push(run);
            return index - start;
         } else {
            throw new IllegalArgumentException("Unterminated type in pattern at character " + index);
         }
      } else {
         throw new IllegalArgumentException("Badly formed pattern at character " + index);
      }
   }

   private int addTextRun(Stack stack, char[] pattern, int index) {
      PatternRun run = new PatternRun();
      int start = index;
      boolean escapeMode = false;
      if ('%' == pattern[index]) {
         ++index;
      }

      StringBuffer sb;
      for(sb = new StringBuffer(); index < pattern.length && pattern[index] != '%'; ++index) {
         if (escapeMode) {
            if ('n' == pattern[index]) {
               sb.append(EOL);
            } else if ('t' == pattern[index]) {
               sb.append('\t');
            } else {
               sb.append(pattern[index]);
            }

            escapeMode = false;
         } else if ('\\' == pattern[index]) {
            escapeMode = true;
         } else {
            sb.append(pattern[index]);
         }
      }

      run.m_data = sb.toString();
      run.m_type = 1;
      stack.push(run);
      return index - start;
   }

   private void append(StringBuffer sb, int minSize, int maxSize, boolean rightJustify, String output) {
      int size = output.length();
      if (size < minSize) {
         if (rightJustify) {
            this.appendWhiteSpace(sb, minSize - size);
            sb.append(output);
         } else {
            sb.append(output);
            this.appendWhiteSpace(sb, minSize - size);
         }
      } else if (maxSize > 0 && maxSize < size) {
         if (rightJustify) {
            sb.append(output.substring(size - maxSize));
         } else {
            sb.append(output.substring(0, maxSize));
         }
      } else {
         sb.append(output);
      }

   }

   private void appendWhiteSpace(StringBuffer sb, int length) {
      while(length >= 16) {
         sb.append("                ");
         length -= 16;
      }

      if (length >= 8) {
         sb.append("        ");
         length -= 8;
      }

      if (length >= 4) {
         sb.append("    ");
         length -= 4;
      }

      if (length >= 2) {
         sb.append("  ");
         length -= 2;
      }

      if (length >= 1) {
         sb.append(" ");
         --length;
      }

   }

   public String format(LogEvent event) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < this.m_formatSpecification.length; ++i) {
         PatternRun run = this.m_formatSpecification[i];
         if (run.m_type == 1) {
            sb.append(run.m_data);
         } else {
            String data = this.formatPatternRun(event, run);
            if (null != data) {
               this.append(sb, run.m_minSize, run.m_maxSize, run.m_rightJustify, data);
            }
         }
      }

      return sb.toString();
   }

   protected String formatPatternRun(LogEvent event, PatternRun run) {
      switch (run.m_type) {
         case 2:
            return this.getCategory(event.getCategory(), run.m_format);
         case 3:
            if (null != run.m_format && !run.m_format.startsWith("stack")) {
               return this.getContextMap(event.getContextMap(), run.m_format);
            }

            return this.getContext(event.getContextStack(), run.m_format);
         case 4:
            return this.getMessage(event.getMessage(), run.m_format);
         case 5:
            return this.getTime(event.getTime(), run.m_format);
         case 6:
            return this.getRTime(event.getRelativeTime(), run.m_format);
         case 7:
            return this.getStackTrace(event.getThrowable(), run.m_format);
         case 8:
            return this.getPriority(event.getPriority(), run.m_format);
         default:
            throw new IllegalStateException("Unknown Pattern specification." + run.m_type);
      }
   }

   protected String getCategory(String category, String format) {
      return category;
   }

   protected String getPriority(Priority priority, String format) {
      return priority.getName();
   }

   /** @deprecated */
   protected String getContext(ContextStack stack, String format) {
      return this.getContextStack(stack, format);
   }

   protected String getContextStack(ContextStack stack, String format) {
      return null == stack ? "" : stack.toString(Integer.MAX_VALUE);
   }

   protected String getContextMap(ContextMap map, String format) {
      return null == map ? "" : map.get(format, "").toString();
   }

   protected String getMessage(String message, String format) {
      return message;
   }

   protected String getStackTrace(Throwable throwable, String format) {
      if (null == throwable) {
         return "";
      } else {
         StringWriter sw = new StringWriter();
         throwable.printStackTrace(new PrintWriter(sw));
         return sw.toString();
      }
   }

   protected String getRTime(long time, String format) {
      return this.getTime(time, format);
   }

   protected String getTime(long time, String format) {
      if (null == format) {
         return Long.toString(time);
      } else {
         Date var4 = this.m_date;
         synchronized(var4) {
            if (null == this.m_simpleDateFormat) {
               this.m_simpleDateFormat = new SimpleDateFormat(format);
            }

            this.m_date.setTime(time);
            String var5 = this.m_simpleDateFormat.format(this.m_date);
            return var5;
         }
      }
   }

   protected int getTypeIdFor(String type) {
      if (type.equalsIgnoreCase("category")) {
         return 2;
      } else if (type.equalsIgnoreCase("context")) {
         return 3;
      } else if (type.equalsIgnoreCase("message")) {
         return 4;
      } else if (type.equalsIgnoreCase("priority")) {
         return 8;
      } else if (type.equalsIgnoreCase("time")) {
         return 5;
      } else if (type.equalsIgnoreCase("rtime")) {
         return 6;
      } else if (type.equalsIgnoreCase("throwable")) {
         return 7;
      } else {
         throw new IllegalArgumentException("Unknown Type in pattern - " + type);
      }
   }

   protected final void parse(String patternString) {
      Stack stack = new Stack();
      int size = patternString.length();
      char[] pattern = new char[size];
      int index = 0;
      patternString.getChars(0, size, pattern, 0);

      while(true) {
         while(index < size) {
            if (pattern[index] == '%' && (index == size - 1 || pattern[index + 1] != '%')) {
               index += this.addPatternRun(stack, pattern, index);
            } else {
               index += this.addTextRun(stack, pattern, index);
            }
         }

         int elementCount = stack.size();
         this.m_formatSpecification = new PatternRun[elementCount];

         for(int i = 0; i < elementCount; ++i) {
            this.m_formatSpecification[i] = (PatternRun)stack.elementAt(i);
         }

         return;
      }
   }

   /** @deprecated */
   public void setFormat(String format) {
      this.parse(format);
   }

   protected static class PatternRun {
      public String m_data;
      public boolean m_rightJustify;
      public int m_minSize;
      public int m_maxSize;
      public int m_type;
      public String m_format;
   }
}
