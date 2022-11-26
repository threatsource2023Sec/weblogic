package jnr.posix;

public interface FileStat {
   int S_IFIFO = 4096;
   int S_IFCHR = 8192;
   int S_IFDIR = 16384;
   int S_IFBLK = 24576;
   int S_IFREG = 32768;
   int S_IFLNK = 40960;
   int S_IFSOCK = 49152;
   int S_IFMT = 61440;
   int S_ISUID = 2048;
   int S_ISGID = 1024;
   int S_ISVTX = 512;
   int S_IRUSR = 256;
   int S_IWUSR = 128;
   int S_IXUSR = 64;
   int S_IRGRP = 32;
   int S_IWGRP = 16;
   int S_IXGRP = 8;
   int S_IROTH = 4;
   int S_IWOTH = 2;
   int S_IXOTH = 1;
   int ALL_READ = 292;
   int ALL_WRITE = 146;
   int S_IXUGO = 73;

   long atime();

   long blocks();

   long blockSize();

   long ctime();

   long dev();

   String ftype();

   int gid();

   boolean groupMember(int var1);

   long ino();

   boolean isBlockDev();

   boolean isCharDev();

   boolean isDirectory();

   boolean isEmpty();

   boolean isExecutable();

   boolean isExecutableReal();

   boolean isFifo();

   boolean isFile();

   boolean isGroupOwned();

   boolean isIdentical(FileStat var1);

   boolean isNamedPipe();

   boolean isOwned();

   boolean isROwned();

   boolean isReadable();

   boolean isReadableReal();

   boolean isWritable();

   boolean isWritableReal();

   boolean isSetgid();

   boolean isSetuid();

   boolean isSocket();

   boolean isSticky();

   boolean isSymlink();

   int major(long var1);

   int minor(long var1);

   int mode();

   long mtime();

   int nlink();

   long rdev();

   long st_size();

   int uid();
}
