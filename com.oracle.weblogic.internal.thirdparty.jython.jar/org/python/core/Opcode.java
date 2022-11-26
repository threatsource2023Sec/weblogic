package org.python.core;

public interface Opcode {
   int STOP_CODE = 0;
   int POP_TOP = 1;
   int ROT_TWO = 2;
   int ROT_THREE = 3;
   int DUP_TOP = 4;
   int ROT_FOUR = 5;
   int NOP = 9;
   int UNARY_POSITIVE = 10;
   int UNARY_NEGATIVE = 11;
   int UNARY_NOT = 12;
   int UNARY_CONVERT = 13;
   int UNARY_INVERT = 15;
   int BINARY_POWER = 19;
   int BINARY_MULTIPLY = 20;
   int BINARY_DIVIDE = 21;
   int BINARY_MODULO = 22;
   int BINARY_ADD = 23;
   int BINARY_SUBTRACT = 24;
   int BINARY_SUBSCR = 25;
   int BINARY_FLOOR_DIVIDE = 26;
   int BINARY_TRUE_DIVIDE = 27;
   int INPLACE_FLOOR_DIVIDE = 28;
   int INPLACE_TRUE_DIVIDE = 29;
   int SLICE = 30;
   int SLICE_1 = 31;
   int SLICE_2 = 32;
   int SLICE_3 = 33;
   int STORE_SLICE = 40;
   int STORE_SLICE_1 = 41;
   int STORE_SLICE_2 = 42;
   int STORE_SLICE_3 = 43;
   int DELETE_SLICE = 50;
   int DELETE_SLICE_1 = 51;
   int DELETE_SLICE_2 = 52;
   int DELETE_SLICE_3 = 53;
   int STORE_MAP = 54;
   int INPLACE_ADD = 55;
   int INPLACE_SUBTRACT = 56;
   int INPLACE_MULTIPLY = 57;
   int INPLACE_DIVIDE = 58;
   int INPLACE_MODULO = 59;
   int STORE_SUBSCR = 60;
   int DELETE_SUBSCR = 61;
   int BINARY_LSHIFT = 62;
   int BINARY_RSHIFT = 63;
   int BINARY_AND = 64;
   int BINARY_XOR = 65;
   int BINARY_OR = 66;
   int INPLACE_POWER = 67;
   int GET_ITER = 68;
   int PRINT_EXPR = 70;
   int PRINT_ITEM = 71;
   int PRINT_NEWLINE = 72;
   int PRINT_ITEM_TO = 73;
   int PRINT_NEWLINE_TO = 74;
   int INPLACE_LSHIFT = 75;
   int INPLACE_RSHIFT = 76;
   int INPLACE_AND = 77;
   int INPLACE_XOR = 78;
   int INPLACE_OR = 79;
   int BREAK_LOOP = 80;
   int WITH_CLEANUP = 81;
   int LOAD_LOCALS = 82;
   int RETURN_VALUE = 83;
   int IMPORT_STAR = 84;
   int EXEC_STMT = 85;
   int YIELD_VALUE = 86;
   int POP_BLOCK = 87;
   int END_FINALLY = 88;
   int BUILD_CLASS = 89;
   int HAVE_ARGUMENT = 90;
   int STORE_NAME = 90;
   int DELETE_NAME = 91;
   int UNPACK_SEQUENCE = 92;
   int FOR_ITER = 93;
   int LIST_APPEND = 94;
   int STORE_ATTR = 95;
   int DELETE_ATTR = 96;
   int STORE_GLOBAL = 97;
   int DELETE_GLOBAL = 98;
   int DUP_TOPX = 99;
   int LOAD_CONST = 100;
   int LOAD_NAME = 101;
   int BUILD_TUPLE = 102;
   int BUILD_LIST = 103;
   int BUILD_SET = 104;
   int BUILD_MAP = 105;
   int LOAD_ATTR = 106;
   int COMPARE_OP = 107;
   int IMPORT_NAME = 108;
   int IMPORT_FROM = 109;
   int JUMP_FORWARD = 110;
   int JUMP_IF_FALSE_OR_POP = 111;
   int JUMP_IF_TRUE_OR_POP = 112;
   int JUMP_ABSOLUTE = 113;
   int POP_JUMP_IF_FALSE = 114;
   int POP_JUMP_IF_TRUE = 115;
   int LOAD_GLOBAL = 116;
   int CONTINUE_LOOP = 119;
   int SETUP_LOOP = 120;
   int SETUP_EXCEPT = 121;
   int SETUP_FINALLY = 122;
   int LOAD_FAST = 124;
   int STORE_FAST = 125;
   int DELETE_FAST = 126;
   int RAISE_VARARGS = 130;
   int CALL_FUNCTION = 131;
   int MAKE_FUNCTION = 132;
   int BUILD_SLICE = 133;
   int MAKE_CLOSURE = 134;
   int LOAD_CLOSURE = 135;
   int LOAD_DEREF = 136;
   int STORE_DEREF = 137;
   int CALL_FUNCTION_VAR = 140;
   int CALL_FUNCTION_KW = 141;
   int CALL_FUNCTION_VAR_KW = 142;
   int SETUP_WITH = 143;
   int EXTENDED_ARG = 145;
   int SET_ADD = 146;
   int MAP_ADD = 147;
   int PyCmp_LT = 0;
   int PyCmp_LE = 1;
   int PyCmp_EQ = 2;
   int PyCmp_NE = 3;
   int PyCmp_GT = 4;
   int PyCmp_GE = 5;
   int PyCmp_IN = 6;
   int PyCmp_NOT_IN = 7;
   int PyCmp_IS = 8;
   int PyCmp_IS_NOT = 9;
   int PyCmp_EXC_MATCH = 10;
   int PyCmp_BAD = 11;
}
