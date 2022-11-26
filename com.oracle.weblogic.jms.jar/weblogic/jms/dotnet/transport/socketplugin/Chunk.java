package weblogic.jms.dotnet.transport.socketplugin;

public class Chunk {
   private static final int CHUNK_SIZE = 4096;
   private static Chunk head;
   private boolean isFree;
   private Chunk next;
   private byte[] buf;
   private static int allocated;
   private static int freed;

   private Chunk(int size) {
      this.buf = new byte[size];
   }

   public byte[] getBuffer() {
      return this.buf;
   }

   public static synchronized Chunk alloc() {
      Chunk ret = head;
      if (head != null) {
         head = head.next;
         ret.isFree = false;
         return ret;
      } else {
         ++allocated;
         System.out.println("Allocating new Chunk, alloc count=" + allocated + "free count=" + freed);
         return new Chunk(4096);
      }
   }

   private static synchronized void free(Chunk c) {
      ++freed;
      if (c.isFree) {
         throw new AssertionError();
      } else {
         c.isFree = true;
         c.next = head;
         head = c;
      }
   }

   public void free() {
      free(this);
   }

   static {
      for(int i = 0; i < 10000; ++i) {
         Chunk c = new Chunk(4096);
         free(c);
      }

   }
}
