package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ClassFormatException extends Exception {
   public static final int ErrBadMagic = 1;
   public static final int ErrBadMinorVersion = 2;
   public static final int ErrBadMajorVersion = 3;
   public static final int ErrBadConstantClass = 4;
   public static final int ErrBadConstantString = 5;
   public static final int ErrBadConstantNameAndType = 6;
   public static final int ErrBadConstantFieldRef = 7;
   public static final int ErrBadConstantMethodRef = 8;
   public static final int ErrBadConstantInterfaceMethodRef = 9;
   public static final int ErrBadConstantPoolIndex = 10;
   public static final int ErrBadSuperclassName = 11;
   public static final int ErrInterfaceCannotBeFinal = 12;
   public static final int ErrInterfaceMustBeAbstract = 13;
   public static final int ErrBadModifiers = 14;
   public static final int ErrClassCannotBeAbstractFinal = 15;
   public static final int ErrBadClassname = 16;
   public static final int ErrBadFieldInfo = 17;
   public static final int ErrBadMethodInfo = 17;
   public static final int ErrEmptyConstantPool = 18;
   public static final int ErrMalformedUtf8 = 19;
   public static final int ErrUnknownConstantTag = 20;
   public static final int ErrTruncatedInput = 21;
   public static final int ErrMethodMustBeAbstract = 22;
   public static final int ErrMalformedAttribute = 23;
   public static final int ErrBadInterface = 24;
   public static final int ErrInterfaceMustSubclassObject = 25;
   public static final int ErrIncorrectInterfaceMethods = 26;
   public static final int ErrInvalidMethodName = 27;
   public static final int ErrInvalidMethodSignature = 28;
   private static final long serialVersionUID = 6667458511042774540L;
   private int errorCode;
   private int bufferPosition;
   private Exception nestedException;
   private char[] fileName;

   public ClassFormatException(RuntimeException e, char[] fileName) {
      this.nestedException = e;
      this.fileName = fileName;
   }

   public ClassFormatException(int code) {
      this.errorCode = code;
   }

   public ClassFormatException(Exception e, char[] fileName, int code, int bufPos) {
      this.nestedException = e;
      this.fileName = fileName;
      this.errorCode = code;
      this.bufferPosition = bufPos;
   }

   public int getErrorCode() {
      return this.errorCode;
   }

   public int getBufferPosition() {
      return this.bufferPosition;
   }

   public Throwable getException() {
      return this.nestedException;
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   public void printStackTrace(PrintStream output) {
      synchronized(output) {
         super.printStackTrace(output);
         Throwable throwable = this.getException();
         if (throwable != null) {
            if (this.fileName != null) {
               output.print("Caused in ");
               output.print(this.fileName);
               output.print(" by: ");
            } else {
               output.print("Caused by: ");
            }

            throwable.printStackTrace(output);
         }

      }
   }

   public void printStackTrace(PrintWriter output) {
      synchronized(output) {
         super.printStackTrace(output);
         Throwable throwable = this.getException();
         if (throwable != null) {
            if (this.fileName != null) {
               output.print("Caused in ");
               output.print(this.fileName);
               output.print(" by: ");
            } else {
               output.print("Caused by: ");
            }

            throwable.printStackTrace(output);
         }

      }
   }
}
