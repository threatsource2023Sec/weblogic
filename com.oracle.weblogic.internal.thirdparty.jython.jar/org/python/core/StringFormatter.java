package org.python.core;

import org.python.core.stringlib.FloatFormatter;
import org.python.core.stringlib.IntegerFormatter;
import org.python.core.stringlib.InternalFormat;
import org.python.core.stringlib.TextFormatter;

final class StringFormatter {
   int index;
   String format;
   StringBuilder buffer;
   int argIndex;
   PyObject args;
   boolean needUnicode;

   final char pop() {
      try {
         return this.format.charAt(this.index++);
      } catch (StringIndexOutOfBoundsException var2) {
         throw Py.ValueError("incomplete format");
      }
   }

   final char peek() {
      return this.format.charAt(this.index);
   }

   final void push() {
      --this.index;
   }

   public StringFormatter(String format) {
      this(format, false);
   }

   public StringFormatter(String format, boolean unicodeCoercion) {
      this.index = 0;
      this.format = format;
      this.needUnicode = unicodeCoercion;
      this.buffer = new StringBuilder(format.length() + 100);
   }

   PyObject getarg() {
      PyObject ret = null;
      switch (this.argIndex) {
         case -3:
            return this.args;
         case -1:
            this.argIndex = -2;
            return this.args;
         default:
            ret = this.args.__finditem__(this.argIndex++);
         case -2:
            if (ret == null) {
               throw Py.TypeError("not enough arguments for format string");
            } else {
               return ret;
            }
      }
   }

   int getNumber() {
      char c = this.pop();
      if (c == '*') {
         PyObject o = this.getarg();
         if (o instanceof PyInteger) {
            return ((PyInteger)o).getValue();
         } else {
            throw Py.TypeError("* wants int");
         }
      } else if (!Character.isDigit(c)) {
         --this.index;
         return 0;
      } else {
         int numStart = this.index - 1;

         while(Character.isDigit(this.pop())) {
         }

         --this.index;
         Integer i = Integer.valueOf(this.format.substring(numStart, this.index));
         return i;
      }
   }

   private PyObject asNumber(PyObject arg) {
      if (!(arg instanceof PyInteger) && !(arg instanceof PyLong)) {
         if (arg.getClass() == PyFloat.class) {
            return arg.__int__();
         } else {
            try {
               return arg.__getattr__("__int__").__call__();
            } catch (PyException var4) {
               try {
                  return arg.__getattr__("__long__").__call__();
               } catch (PyException var3) {
                  return arg;
               }
            }
         }
      } else {
         return arg;
      }
   }

   private PyObject asFloat(PyObject arg) {
      if (arg instanceof PyFloat) {
         return arg;
      } else if (arg.getClass() == PyFloat.class) {
         return arg.__float__();
      } else {
         try {
            return arg.__getattr__("__float__").__call__();
         } catch (PyException var3) {
            return arg;
         }
      }
   }

   private PyString asText(PyObject arg) {
      if (arg instanceof PyUnicode) {
         this.needUnicode = true;
         return (PyUnicode)arg;
      } else if (this.needUnicode) {
         return arg.__unicode__();
      } else if (arg instanceof PyString) {
         return (PyString)arg;
      } else {
         PyString s = arg.__str__();
         if (s instanceof PyUnicode) {
            this.needUnicode = true;
         }

         return s;
      }
   }

   public PyString format(PyObject args) {
      PyObject dict = null;
      this.args = args;
      if (args instanceof PyTuple) {
         this.argIndex = 0;
      } else {
         this.argIndex = -1;
         if (args instanceof AbstractDict || !(args instanceof PySequence) && args.object___findattr__("__getitem__".intern()) != null) {
            dict = args;
            this.argIndex = -3;
         }
      }

      while(true) {
         label140:
         while(this.index < this.format.length()) {
            char c = this.pop();
            if (c == '%') {
               boolean altFlag = false;
               char sign = '\uffff';
               char fill = ' ';
               char align = '>';
               int width = true;
               int precision = -1;
               c = this.pop();
               if (c != '(') {
                  this.push();
               } else {
                  if (dict == null) {
                     throw Py.TypeError("format requires a mapping");
                  }

                  int parens = 1;
                  int keyStart = this.index;

                  while(parens > 0) {
                     c = this.pop();
                     if (c == ')') {
                        --parens;
                     } else if (c == '(') {
                        ++parens;
                     }
                  }

                  String tmp = this.format.substring(keyStart, this.index - 1);
                  this.args = dict.__getitem__((PyObject)(this.needUnicode ? new PyUnicode(tmp) : new PyString(tmp)));
               }

               while(true) {
                  while(true) {
                     switch (this.pop()) {
                        case ' ':
                           if (!InternalFormat.Spec.specified(sign)) {
                              sign = ' ';
                           }
                           break;
                        case '#':
                           altFlag = true;
                           break;
                        case '+':
                           sign = '+';
                           break;
                        case '-':
                           align = '<';
                           break;
                        case '0':
                           fill = '0';
                           break;
                        default:
                           this.push();
                           int width = this.getNumber();
                           if (width < 0) {
                              width = -width;
                              align = '<';
                           }

                           c = this.pop();
                           if (c == '.') {
                              precision = this.getNumber();
                              if (precision < -1) {
                                 precision = 0;
                              }

                              c = this.pop();
                           }

                           if (c == 'h' || c == 'l' || c == 'L') {
                              c = this.pop();
                           }

                           switch (c) {
                              case '%':
                              case 'c':
                              case 'r':
                              case 's':
                                 fill = ' ';
                                 break;
                              default:
                                 if (fill == '0' && align == '>') {
                                    align = '=';
                                 } else {
                                    fill = ' ';
                                 }
                           }

                           InternalFormat.Spec spec = new InternalFormat.Spec(fill, align, sign, altFlag, width, false, precision, c);
                           PyObject arg;
                           Object f;
                           PyObject argAsNumber;
                           IntegerFormatter.Traditional fi;
                           switch (spec.type) {
                              case '%':
                                 f = fi = new IntegerFormatter.Traditional(this.buffer, spec);
                                 fi.setBytes(!this.needUnicode);
                                 fi.format(37);
                                 break;
                              case 'E':
                              case 'F':
                              case 'G':
                              case 'e':
                              case 'f':
                              case 'g':
                                 FloatFormatter ff;
                                 f = ff = new FloatFormatter(this.buffer, spec);
                                 ff.setBytes(!this.needUnicode);
                                 arg = this.getarg();
                                 argAsNumber = this.asFloat(arg);
                                 if (!(argAsNumber instanceof PyFloat)) {
                                    throw Py.TypeError("float argument required, not " + arg.getType().fastGetName());
                                 }

                                 ff.format(((PyFloat)argAsNumber).getValue());
                                 break;
                              case 'X':
                              case 'c':
                              case 'd':
                              case 'i':
                              case 'o':
                              case 'u':
                              case 'x':
                                 f = fi = new IntegerFormatter.Traditional(this.buffer, spec);
                                 fi.setBytes(!this.needUnicode);
                                 arg = this.getarg();
                                 if (arg instanceof PyString && spec.type == 'c') {
                                    if (arg.__len__() != 1) {
                                       throw Py.TypeError("%c requires int or char");
                                    }

                                    if (!this.needUnicode && arg instanceof PyUnicode) {
                                       this.needUnicode = true;
                                       fi.setBytes(false);
                                    }

                                    fi.format(((PyString)arg).getString().codePointAt(0));
                                 } else {
                                    argAsNumber = this.asNumber(arg);
                                    if (argAsNumber instanceof PyInteger) {
                                       fi.format(((PyInteger)argAsNumber).getValue());
                                    } else {
                                       if (!(argAsNumber instanceof PyLong)) {
                                          throw Py.TypeError("%" + spec.type + " format: a number is required, not " + arg.getType().fastGetName());
                                       }

                                       fi.format(((PyLong)argAsNumber).getValue());
                                    }
                                 }
                                 break;
                              case 'r':
                              case 's':
                                 arg = this.getarg();
                                 PyString argAsString = this.asText((PyObject)(spec.type == 's' ? arg : arg.__repr__()));
                                 TextFormatter ft;
                                 f = ft = new TextFormatter(this.buffer, spec);
                                 ft.setBytes(!this.needUnicode);
                                 ft.format(argAsString.getString());
                                 break;
                              default:
                                 throw Py.ValueError("unsupported format character '" + codecs.encode(Py.newUnicode(spec.type), (String)null, "replace") + "' (0x" + Integer.toHexString(spec.type) + ") at index " + (this.index - 1));
                           }

                           ((InternalFormat.Formatter)f).pad();
                           continue label140;
                     }
                  }
               }
            } else {
               this.buffer.append(c);
            }
         }

         if (this.argIndex == -1 || this.argIndex >= 0 && args.__finditem__(this.argIndex) != null) {
            throw Py.TypeError("not all arguments converted during string formatting");
         }

         return (PyString)(this.needUnicode ? new PyUnicode(this.buffer) : new PyString(this.buffer));
      }
   }
}
