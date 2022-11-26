package org.apache.log.output;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.log.format.Formatter;
import org.apache.log.format.PatternFormatter;
import org.apache.log.output.io.WriterTarget;

/** @deprecated */
public class DefaultOutputLogTarget extends WriterTarget {
   private static final String FORMAT = "%7.7{priority} %5.5{time}   [%8.8{category}] (%{context}): %{message}\\n%{throwable}";

   /** @deprecated */
   protected void initPattern() {
   }

   public DefaultOutputLogTarget(Formatter formatter) {
      this(new OutputStreamWriter(System.out), formatter);
   }

   public DefaultOutputLogTarget() {
      this((Writer)(new OutputStreamWriter(System.out)));
   }

   public DefaultOutputLogTarget(OutputStream output) {
      this((Writer)(new OutputStreamWriter(output)));
   }

   public DefaultOutputLogTarget(Writer writer) {
      this(writer, new PatternFormatter("%7.7{priority} %5.5{time}   [%8.8{category}] (%{context}): %{message}\\n%{throwable}"));
   }

   public DefaultOutputLogTarget(Writer writer, Formatter formatter) {
      super(writer, formatter);
      this.initPattern();
   }

   /** @deprecated */
   public void setFormat(String format) {
      ((PatternFormatter)this.m_formatter).setFormat(format);
   }
}
