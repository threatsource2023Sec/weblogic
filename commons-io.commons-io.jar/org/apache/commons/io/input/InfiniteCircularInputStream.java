package org.apache.commons.io.input;

import java.io.InputStream;

public class InfiniteCircularInputStream extends InputStream {
   private final byte[] repeatedContent;
   private int position = -1;

   public InfiniteCircularInputStream(byte[] repeatedContent) {
      this.repeatedContent = repeatedContent;
   }

   public int read() {
      this.position = (this.position + 1) % this.repeatedContent.length;
      return this.repeatedContent[this.position] & 255;
   }
}
