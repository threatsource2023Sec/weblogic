package weblogic.servlet.http2.hpack;

import java.io.ByteArrayOutputStream;

public class HuffmanDecoder {
   private static final Node root = new Node();

   public static byte[] decode(byte[] buf) throws HpackException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Node currentNode = root;
      int current = 0;
      int bytesRemain = 0;

      int i;
      for(i = 0; i < buf.length; ++i) {
         int b = buf[i] & 255;
         current = current << 8 | b;
         bytesRemain += 8;

         while(bytesRemain >= 8) {
            int c = current >>> bytesRemain - 8 & 255;
            currentNode = currentNode.subNodes[c];
            bytesRemain -= currentNode.huffmanCode.getLenInBits();
            if (currentNode.hasSubNodes()) {
               if (currentNode.huffmanCode.getHexCode() == 256) {
                  throw new HpackException("HuffmanDecoder found EOS at index [" + i + "] of the buf.");
               }

               baos.write(currentNode.huffmanCode.getHexCode());
               currentNode = root;
            }
         }
      }

      while(bytesRemain > 0) {
         i = current << 8 - bytesRemain & 255;
         currentNode = currentNode.subNodes[i];
         if (!currentNode.hasSubNodes() || currentNode.huffmanCode.getLenInBits() > bytesRemain) {
            break;
         }

         bytesRemain -= currentNode.huffmanCode.getLenInBits();
         baos.write(currentNode.huffmanCode.getHexCode());
         currentNode = root;
      }

      i = (1 << bytesRemain) - 1;
      if ((current & i) != i) {
         throw new HpackException("HuffmanDecoder found Invalid Padding " + current);
      } else {
         return baos.toByteArray();
      }
   }

   private static void addNode(Node root, int symbol, HuffmanCode huffmanCode) {
      Node currentNode = root;
      int length = huffmanCode.getLenInBits();

      int code;
      int shift;
      for(code = huffmanCode.getHexCode(); length > 8; currentNode = currentNode.subNodes[shift]) {
         length -= 8;
         shift = code >>> length & 255;
         if (currentNode.subNodes[shift] == null) {
            currentNode.subNodes[shift] = new Node();
         }
      }

      shift = 8 - length;
      int offset = code << shift & 255;

      for(int i = 0; i < 1 << shift; ++i) {
         currentNode.subNodes[offset + i] = new Node(new HuffmanCode(symbol, length));
      }

   }

   static {
      for(int i = 0; i < HuffmanTable.HUFFMAN_TABLE.length; ++i) {
         addNode(root, i, HuffmanTable.getHuffmanCode(i));
      }

   }

   private static final class Node {
      private final HuffmanCode huffmanCode;
      private final Node[] subNodes;

      private Node() {
         this.huffmanCode = new HuffmanCode(0, 8);
         this.subNodes = new Node[256];
      }

      private Node(HuffmanCode code) {
         this.huffmanCode = code;
         this.subNodes = null;
      }

      private boolean hasSubNodes() {
         return this.subNodes == null;
      }

      // $FF: synthetic method
      Node(Object x0) {
         this();
      }

      // $FF: synthetic method
      Node(HuffmanCode x0, Object x1) {
         this(x0);
      }
   }
}
