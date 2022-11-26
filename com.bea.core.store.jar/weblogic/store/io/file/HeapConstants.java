package weblogic.store.io.file;

public interface HeapConstants {
   int MAX_FILE_NUM = 32767;
   int HDD_SECTOR_SIZE = 512;
   int DIGEST_BITS = 512;
   int FILE_HEADER_SIZE = 512;
   int SIGNATURE_POS = 30;
   int SIGNATURE_SIZE = 64;
   int MAX_BLOCK_SIZE = 8192;
   int OLD_BLOCK_SIZE = 256;
   int MAX_FILE_SIZE = 1342177280;
   int MIN_IO_SIZE64 = 8388608;
   int MIN_IO_SIZE32 = 1048576;
   int MIN_MAP_SIZE64 = 262144;
   int MIN_MAP_SIZE32 = 65536;
   int MAX_MAP_SIZE64 = 268435456;
   int MAX_MAP_SIZE32 = 4194304;
   int DEFAULT_EXTENT_SIZE = 1048576;
}
