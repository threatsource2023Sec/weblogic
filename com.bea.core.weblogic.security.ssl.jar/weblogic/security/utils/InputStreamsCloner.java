package weblogic.security.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class InputStreamsCloner {
   private InputStreamCloner[] cloners = null;
   private int hashcode = 0;

   public InputStreamsCloner(InputStream[] streams) {
      this.cloners = new InputStreamCloner[streams.length];

      for(int i = 0; i < streams.length; ++i) {
         this.cloners[i] = new InputStreamCloner(streams[i]);
      }

   }

   public InputStream[] cloneStreams() throws IOException {
      InputStream[] streams = new InputStream[this.cloners.length];

      for(int i = 0; i < this.cloners.length; ++i) {
         streams[i] = this.cloners[i].cloneStream();
      }

      return streams;
   }

   public boolean equals(Object obj) {
      return this == obj || obj != null && obj instanceof InputStreamsCloner && Arrays.equals(this.cloners, ((InputStreamsCloner)obj).cloners);
   }

   public int hashCode() {
      if (this.hashcode == 0) {
         int hash = 1;

         for(int i = 0; i < this.cloners.length; ++i) {
            hash = hash * 31 + this.cloners[i].hashCode();
         }

         this.hashcode = hash;
      }

      return this.hashcode;
   }

   public int size() {
      return this.cloners.length;
   }
}
