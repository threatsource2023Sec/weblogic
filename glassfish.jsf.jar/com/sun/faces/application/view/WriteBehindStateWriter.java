package com.sun.faces.application.view;

import com.sun.faces.io.FastStringWriter;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.Writer;
import javax.faces.application.StateManager;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

final class WriteBehindStateWriter extends Writer {
   private static final int STATE_MARKER_LEN = "~com.sun.faces.saveStateFieldMarker~".length();
   private static final ThreadLocal CUR_WRITER = new ThreadLocal();
   private Writer out;
   private Writer orig;
   private FastStringWriter fWriter;
   private boolean stateWritten;
   private int bufSize;
   private char[] buf;
   private FacesContext context;
   private Object state;

   public WriteBehindStateWriter(Writer out, FacesContext context, int bufSize) {
      this.out = out;
      this.orig = out;
      this.context = context;
      this.bufSize = bufSize;
      this.buf = new char[bufSize];
      CUR_WRITER.set(this);
   }

   public void write(int c) throws IOException {
      this.out.write(c);
   }

   public void write(char[] cbuf) throws IOException {
      this.out.write(cbuf);
   }

   public void write(String str) throws IOException {
      this.out.write(str);
   }

   public void write(String str, int off, int len) throws IOException {
      this.out.write(str, off, len);
   }

   public void write(char[] cbuf, int off, int len) throws IOException {
      this.out.write(cbuf, off, len);
   }

   public void flush() throws IOException {
   }

   public void close() throws IOException {
   }

   public static WriteBehindStateWriter getCurrentInstance() {
      return (WriteBehindStateWriter)CUR_WRITER.get();
   }

   public void release() {
      CUR_WRITER.remove();
   }

   public void writingState() {
      if (!this.stateWritten) {
         this.stateWritten = true;
         this.out = this.fWriter = new FastStringWriter(1024);
      }

   }

   public boolean stateWritten() {
      return this.stateWritten;
   }

   public void flushToWriter() throws IOException {
      StateManager stateManager = Util.getStateManager(this.context);
      ResponseWriter origWriter = this.context.getResponseWriter();
      StringBuilder stateBuilder = this.getState(stateManager, origWriter);
      StringBuilder builder = this.fWriter.getBuffer();
      int totalLen = builder.length();
      int stateLen = stateBuilder.length();
      int pos = 0;
      int tildeIdx = getNextDelimiterIndex(builder, pos);

      while(true) {
         while(true) {
            while(pos < totalLen) {
               int len;
               if (tildeIdx != -1) {
                  if (tildeIdx > pos && tildeIdx - pos > this.bufSize) {
                     builder.getChars(pos, pos + this.bufSize, this.buf, 0);
                     this.orig.write(this.buf);
                     pos += this.bufSize;
                  } else {
                     builder.getChars(pos, tildeIdx, this.buf, 0);
                     len = tildeIdx - pos;
                     this.orig.write(this.buf, 0, len);
                     if (builder.indexOf("~com.sun.faces.saveStateFieldMarker~", pos) != tildeIdx) {
                        pos = tildeIdx;
                        tildeIdx = getNextDelimiterIndex(builder, tildeIdx + 1);
                     } else {
                        int statePos = 0;

                        while(statePos < stateLen) {
                           if (stateLen - statePos > this.bufSize) {
                              stateBuilder.getChars(statePos, statePos + this.bufSize, this.buf, 0);
                              this.orig.write(this.buf);
                              statePos += this.bufSize;
                           } else {
                              int slen = stateLen - statePos;
                              stateBuilder.getChars(statePos, stateLen, this.buf, 0);
                              this.orig.write(this.buf, 0, slen);
                              statePos += slen;
                           }
                        }

                        pos += len + STATE_MARKER_LEN;
                        tildeIdx = getNextDelimiterIndex(builder, pos);
                        stateBuilder = this.getState(stateManager, origWriter);
                        stateLen = stateBuilder.length();
                     }
                  }
               } else if (totalLen - pos > this.bufSize) {
                  builder.getChars(pos, pos + this.bufSize, this.buf, 0);
                  this.orig.write(this.buf);
                  pos += this.bufSize;
               } else {
                  builder.getChars(pos, totalLen, this.buf, 0);
                  len = totalLen - pos;
                  this.orig.write(this.buf, 0, len);
                  pos += len + 1;
               }
            }

            this.out = this.orig;
            return;
         }
      }
   }

   private StringBuilder getState(StateManager stateManager, ResponseWriter origWriter) throws IOException {
      FastStringWriter stateWriter = new FastStringWriter(stateManager.isSavingStateInClient(this.context) ? this.bufSize : 128);
      this.context.setResponseWriter(origWriter.cloneWithWriter(stateWriter));
      if (this.state == null) {
         this.state = stateManager.saveView(this.context);
      }

      stateManager.writeState(this.context, this.state);
      this.context.setResponseWriter(origWriter);
      StringBuilder stateBuilder = stateWriter.getBuffer();
      return stateBuilder;
   }

   private static int getNextDelimiterIndex(StringBuilder builder, int offset) {
      return builder.indexOf("~", offset);
   }
}
