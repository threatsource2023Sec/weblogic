package org.stringtemplate.v4.compiler;

public class Bytecode {
   public static final int MAX_OPNDS = 2;
   public static final int OPND_SIZE_IN_BYTES = 2;
   public static final short INSTR_LOAD_STR = 1;
   public static final short INSTR_LOAD_ATTR = 2;
   public static final short INSTR_LOAD_LOCAL = 3;
   public static final short INSTR_LOAD_PROP = 4;
   public static final short INSTR_LOAD_PROP_IND = 5;
   public static final short INSTR_STORE_OPTION = 6;
   public static final short INSTR_STORE_ARG = 7;
   public static final short INSTR_NEW = 8;
   public static final short INSTR_NEW_IND = 9;
   public static final short INSTR_NEW_BOX_ARGS = 10;
   public static final short INSTR_SUPER_NEW = 11;
   public static final short INSTR_SUPER_NEW_BOX_ARGS = 12;
   public static final short INSTR_WRITE = 13;
   public static final short INSTR_WRITE_OPT = 14;
   public static final short INSTR_MAP = 15;
   public static final short INSTR_ROT_MAP = 16;
   public static final short INSTR_ZIP_MAP = 17;
   public static final short INSTR_BR = 18;
   public static final short INSTR_BRF = 19;
   public static final short INSTR_OPTIONS = 20;
   public static final short INSTR_ARGS = 21;
   public static final short INSTR_PASSTHRU = 22;
   public static final short INSTR_LIST = 24;
   public static final short INSTR_ADD = 25;
   public static final short INSTR_TOSTR = 26;
   public static final short INSTR_FIRST = 27;
   public static final short INSTR_LAST = 28;
   public static final short INSTR_REST = 29;
   public static final short INSTR_TRUNC = 30;
   public static final short INSTR_STRIP = 31;
   public static final short INSTR_TRIM = 32;
   public static final short INSTR_LENGTH = 33;
   public static final short INSTR_STRLEN = 34;
   public static final short INSTR_REVERSE = 35;
   public static final short INSTR_NOT = 36;
   public static final short INSTR_OR = 37;
   public static final short INSTR_AND = 38;
   public static final short INSTR_INDENT = 39;
   public static final short INSTR_DEDENT = 40;
   public static final short INSTR_NEWLINE = 41;
   public static final short INSTR_NOOP = 42;
   public static final short INSTR_POP = 43;
   public static final short INSTR_NULL = 44;
   public static final short INSTR_TRUE = 45;
   public static final short INSTR_FALSE = 46;
   public static final short INSTR_WRITE_STR = 47;
   public static final short INSTR_WRITE_LOCAL = 48;
   public static final short MAX_BYTECODE = 48;
   public static Instruction[] instructions;

   static {
      instructions = new Instruction[]{null, new Instruction("load_str", Bytecode.OperandType.STRING), new Instruction("load_attr", Bytecode.OperandType.STRING), new Instruction("load_local", Bytecode.OperandType.INT), new Instruction("load_prop", Bytecode.OperandType.STRING), new Instruction("load_prop_ind"), new Instruction("store_option", Bytecode.OperandType.INT), new Instruction("store_arg", Bytecode.OperandType.STRING), new Instruction("new", Bytecode.OperandType.STRING, Bytecode.OperandType.INT), new Instruction("new_ind", Bytecode.OperandType.INT), new Instruction("new_box_args", Bytecode.OperandType.STRING), new Instruction("super_new", Bytecode.OperandType.STRING, Bytecode.OperandType.INT), new Instruction("super_new_box_args", Bytecode.OperandType.STRING), new Instruction("write"), new Instruction("write_opt"), new Instruction("map"), new Instruction("rot_map", Bytecode.OperandType.INT), new Instruction("zip_map", Bytecode.OperandType.INT), new Instruction("br", Bytecode.OperandType.ADDR), new Instruction("brf", Bytecode.OperandType.ADDR), new Instruction("options"), new Instruction("args"), new Instruction("passthru", Bytecode.OperandType.STRING), null, new Instruction("list"), new Instruction("add"), new Instruction("tostr"), new Instruction("first"), new Instruction("last"), new Instruction("rest"), new Instruction("trunc"), new Instruction("strip"), new Instruction("trim"), new Instruction("length"), new Instruction("strlen"), new Instruction("reverse"), new Instruction("not"), new Instruction("or"), new Instruction("and"), new Instruction("indent", Bytecode.OperandType.STRING), new Instruction("dedent"), new Instruction("newline"), new Instruction("noop"), new Instruction("pop"), new Instruction("null"), new Instruction("true"), new Instruction("false"), new Instruction("write_str", Bytecode.OperandType.STRING), new Instruction("write_local", Bytecode.OperandType.INT)};
   }

   public static class Instruction {
      public String name;
      public OperandType[] type;
      public int nopnds;

      public Instruction(String name) {
         this(name, Bytecode.OperandType.NONE, Bytecode.OperandType.NONE);
         this.nopnds = 0;
      }

      public Instruction(String name, OperandType a) {
         this(name, a, Bytecode.OperandType.NONE);
         this.nopnds = 1;
      }

      public Instruction(String name, OperandType a, OperandType b) {
         this.type = new OperandType[2];
         this.nopnds = 0;
         this.name = name;
         this.type[0] = a;
         this.type[1] = b;
         this.nopnds = 2;
      }
   }

   public static enum OperandType {
      NONE,
      STRING,
      ADDR,
      INT;
   }
}
