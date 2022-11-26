package org.apache.log.format;

import java.util.Date;
import org.apache.log.LogEvent;

public class XMLFormatter implements Formatter, org.apache.log.Formatter {
   private static final String EOL = System.getProperty("line.separator", "\n");
   private boolean m_printTime = true;
   private boolean m_printRelativeTime = false;
   private boolean m_printPriority = true;
   private boolean m_printCategory = true;
   private boolean m_printContext = true;
   private boolean m_printMessage = true;
   private boolean m_printException = true;
   private boolean m_printNumericTime = true;

   public void setPrintTime(boolean printTime) {
      this.m_printTime = printTime;
   }

   public void setPrintRelativeTime(boolean printRelativeTime) {
      this.m_printRelativeTime = printRelativeTime;
   }

   public void setPrintPriority(boolean printPriority) {
      this.m_printPriority = printPriority;
   }

   public void setPrintCategory(boolean printCategory) {
      this.m_printCategory = printCategory;
   }

   public void setPrintContext(boolean printContext) {
      this.m_printContext = printContext;
   }

   public void setPrintMessage(boolean printMessage) {
      this.m_printMessage = printMessage;
   }

   public void setPrintException(boolean printException) {
      this.m_printException = printException;
   }

   public String format(LogEvent event) {
      StringBuffer sb = new StringBuffer(400);
      sb.append("<log-entry>");
      sb.append(EOL);
      if (this.m_printTime) {
         sb.append("  <time>");
         if (this.m_printNumericTime) {
            sb.append(event.getTime());
         } else {
            sb.append(new Date(event.getTime()));
         }

         sb.append("</time>");
         sb.append(EOL);
      }

      if (this.m_printRelativeTime) {
         sb.append("  <relative-time>");
         sb.append(event.getRelativeTime());
         sb.append("</relative-time>");
         sb.append(EOL);
      }

      if (this.m_printPriority) {
         sb.append("  <priority>");
         sb.append(event.getPriority().getName());
         sb.append("</priority>");
         sb.append(EOL);
      }

      if (this.m_printCategory) {
         sb.append("  <category>");
         sb.append(event.getCategory());
         sb.append("</category>");
         sb.append(EOL);
      }

      if (this.m_printContext && null != event.getContextStack()) {
         sb.append("  <context-stack>");
         sb.append(event.getContextStack());
         sb.append("</context-stack>");
         sb.append(EOL);
      }

      if (this.m_printMessage && null != event.getMessage()) {
         sb.append("  <message><![CDATA[");
         sb.append(event.getMessage());
         sb.append("]]></message>");
         sb.append(EOL);
      }

      if (this.m_printException && null != event.getThrowable()) {
         sb.append("  <exception><![CDATA[");
         sb.append("]]></exception>");
         sb.append(EOL);
      }

      sb.append("</log-entry>");
      sb.append(EOL);
      return sb.toString();
   }
}
