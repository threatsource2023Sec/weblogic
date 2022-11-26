package weblogic.common.internal;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelStream;
import weblogic.utils.io.Replacer;

public final class ReplacerObjectOutputStream extends ObjectOutputStream implements ServerChannelStream {
   private static final boolean DEBUG = false;
   private final Replacer replacer;
   private ServerChannel channel;

   public ReplacerObjectOutputStream(OutputStream os, Replacer r) throws IOException {
      super(os);
      this.enableReplaceObject(r != null);
      this.replacer = r;
   }

   public final void setServerChannel(ServerChannel sc) {
      this.channel = sc;
   }

   public ServerChannel getServerChannel() {
      return this.channel;
   }

   protected Object replaceObject(Object obj) throws IOException {
      Object newobj = this.replacer.replaceObject(obj);
      return newobj;
   }

   private static void say(String s) {
      System.err.println(s);
   }
}
