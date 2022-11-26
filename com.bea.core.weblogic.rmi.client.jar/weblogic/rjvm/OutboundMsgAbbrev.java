package weblogic.rjvm;

import java.io.IOException;
import java.io.ObjectOutputStream;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelStream;
import weblogic.utils.AssertionError;
import weblogic.utils.collections.Stack;

final class OutboundMsgAbbrev implements AbbrevConstants {
   private static final boolean DEBUG = false;
   private final MsgAbbrevOutputStream out;
   private final Stack abbrevs;
   private final Stack headerAbbrevs;

   OutboundMsgAbbrev(MsgAbbrevOutputStream out) {
      this.out = out;
      this.abbrevs = new Stack();
      this.headerAbbrevs = new Stack();
   }

   void write(BubblingAbbrever at) throws IOException {
      int numAbbrevs = this.abbrevs.size() + this.headerAbbrevs.size();
      this.out.writeLength(numAbbrevs);
      this.writeAbbrevs(at, this.abbrevs);
      this.writeAbbrevs(at, this.headerAbbrevs);
   }

   private void writeAbbrevs(BubblingAbbrever at, Stack s) throws IOException {
      int n = s.size();

      for(int i = 0; i < n; ++i) {
         Object o = s.pop();
         int abbrev = at.getAbbrev(o);
         this.out.writeLength(abbrev);
         if (abbrev > at.getCapacity()) {
            this.writeObject(o);
         }
      }

   }

   private void writeObject(Object o) throws IOException {
      if (o instanceof String) {
         this.out.write(1);
         this.out.writeASCII((String)o);
      } else {
         this.out.write(0);
         ObjectOutputStream oos = new DelegatedObjectOutputStream(this.out);
         oos.writeObject(o);
         oos.flush();
      }
   }

   void reset() {
      this.abbrevs.clear();
      this.headerAbbrevs.clear();
   }

   void addAbbrev(Object o) {
      this.addAbbrev(o, false);
   }

   void addAbbrev(Object o, boolean header) {
      if (o instanceof InteropWriteReplaceable) {
         PeerInfo pi = this.out.getPeerInfo();

         try {
            o = ((InteropWriteReplaceable)o).interopWriteReplace(pi);
         } catch (IOException var5) {
            throw new AssertionError(var5);
         }
      }

      if (header) {
         this.headerAbbrevs.push(o);
      } else {
         this.abbrevs.push(o);
      }

   }

   public String toString() {
      return super.toString() + " - abbrevs: '" + this.abbrevs + "' header abbrevs: '" + this.headerAbbrevs + "'";
   }

   private static final class DelegatedObjectOutputStream extends ObjectOutputStream implements ServerChannelStream {
      private MsgAbbrevOutputStream delegate;

      public DelegatedObjectOutputStream(MsgAbbrevOutputStream oos) throws IOException {
         super(oos);
         this.delegate = oos;
      }

      public final ServerChannel getServerChannel() {
         return this.delegate.getServerChannel();
      }
   }
}
