package org.python.core;

public interface PyBUF {
   int MAX_NDIM = 64;
   int WRITABLE = 1;
   int SIMPLE = 0;
   int FORMAT = 4;
   int ND = 8;
   int STRIDES = 24;
   int C_CONTIGUOUS = 56;
   int F_CONTIGUOUS = 88;
   int ANY_CONTIGUOUS = 152;
   int INDIRECT = 280;
   int CONTIG = 9;
   int CONTIG_RO = 8;
   int STRIDED = 25;
   int STRIDED_RO = 24;
   int RECORDS = 29;
   int RECORDS_RO = 28;
   int FULL = 285;
   int FULL_RO = 284;
   int AS_ARRAY = 268435456;
   int NAVIGATION = 280;
   int IS_C_CONTIGUOUS = 32;
   int IS_F_CONTIGUOUS = 64;
   int CONTIGUITY = 224;

   boolean isReadonly();

   int getNdim();

   int[] getShape();

   int getItemsize();

   int getLen();

   int[] getStrides();

   int[] getSuboffsets();

   boolean isContiguous(char var1);
}
