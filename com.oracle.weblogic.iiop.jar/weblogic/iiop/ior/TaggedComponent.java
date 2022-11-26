package weblogic.iiop.ior;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.protocol.ServerIdentity;
import weblogic.utils.Hex;

public class TaggedComponent {
   public static final int TAG_CODE_SETS = 1;
   public static final int TAG_POLICIES = 2;
   public static final int TAG_SSL_SEC_TRANS = 20;
   public static final int TAG_JAVA_CODEBASE = 25;
   public static final int TAG_TRANSACTION_POLICY = 26;
   public static final int TAG_OTS_POLICY = 31;
   public static final int TAG_INV_POLICY = 32;
   public static final int TAG_CSI_SEC_MECH_LIST = 33;
   public static final int TAG_NULL_TAG = 34;
   public static final int TAG_TLS_SEC_TRANS = 36;
   public static final int TAG_RMI_CUSTOM_MAX_STREAM_FORMAT = 38;
   private static final int TAG_WLS_VERSION = 1111834880;
   public static final int TAG_WLS_CLUSTER_KEY = 1111834883;
   public static final int TAG_WLS_ASYNC_KEY = 1111834884;
   protected final int tag;
   private byte[] component_data;

   public TaggedComponent(int tag) {
      this.tag = tag;
   }

   public TaggedComponent(int tag, CorbaInputStream in) {
      this.tag = tag;
      this.read(in);
   }

   final int getTag() {
      return this.tag;
   }

   static TaggedComponent readComponent(CorbaInputStream in, ServerIdentity target) {
      int tag = in.read_long();
      switch (tag) {
         case 1:
            return new CodeSetsComponent(in);
         case 2:
            return new MessagingPolicyComponent(in);
         case 20:
            return new SSLSecTransComponent(in);
         case 25:
            return new CodebaseComponent(in);
         case 31:
         case 32:
            return new TransactionPolicyComponent(in, tag);
         case 33:
            return new CompoundSecMechList(in, target);
         case 36:
            return new TLSSecTransComponent(in, target);
         case 38:
            return new SFVComponent(in);
         case 1111834883:
            return new ClusterComponent(in);
         case 1111834884:
            return new AsyncComponent(in);
         default:
            return new TaggedComponent(tag, in);
      }
   }

   public void read(CorbaInputStream in) {
      this.component_data = in.read_octet_sequence();
   }

   public void write(CorbaOutputStream out) {
      out.write_ulong(this.tag);
      out.write_octet_sequence(this.component_data);
   }

   protected static void p(String s) {
      System.err.println("<TaggedComponent> " + s);
   }

   public String toString() {
      return Integer.toHexString(this.tag) + ": " + (this.component_data != null ? Hex.dump(this.component_data, 0, this.component_data.length) : " ");
   }
}
