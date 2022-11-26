package weblogic.iiop.messages;

public interface MessageHeaderConstants {
   int MSG_HEADER_LENGTH = 12;
   int MAGIC_POS = 0;
   int MAGIC_LENGTH = 4;
   int VERSION_POS = 4;
   int VERSION_LENGTH = 2;
   int FLAGS_POS = 6;
   int FLAGS_LENGTH = 1;
   int MSG_TYPE_POS = 7;
   int MSG_TYPE_LENGTH = 1;
   int MSG_SIZE_POS = 8;
   int MSG_SIZE_LENGTH = 4;
   int MAGIC = 1195986768;
   byte[] MAGIC_VALUE = new byte[]{71, 73, 79, 80};
   byte[] GIOP_VERSION_1_0 = new byte[]{1, 0};
   byte[] GIOP_VERSION_1_1 = new byte[]{1, 1};
   byte[] GIOP_VERSION_1_2 = new byte[]{1, 2};
   byte BIG_ENDIAN_BYTE_ORDER = 0;
   byte LITTLE_ENDIAN_BYTE_ORDER = 1;
   byte FRAGMENTED = 2;
   byte NOT_FRAGMENTED = 0;
}
