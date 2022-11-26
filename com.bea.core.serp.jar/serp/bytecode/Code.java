package serp.bytecode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import serp.bytecode.visitor.BCVisitor;

public class Code extends Attribute {
   private final CodeEntry _head;
   private final CodeEntry _tail;
   private CodeIterator _ci;
   private int _maxStack;
   private int _maxLocals;
   private int _size;
   private Collection _handlers;
   private Collection _attrs;
   private boolean _byteIndexesValid;

   Code(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
      this._maxStack = 0;
      this._maxLocals = 0;
      this._size = 0;
      this._handlers = new LinkedList();
      this._attrs = new LinkedList();
      this._head = new CodeEntry();
      this._tail = new CodeEntry();
      this._head.next = this._tail;
      this._tail.prev = this._head;
      this._ci = new CodeIterator(this._head, -1);
   }

   public Code() {
      this(0, (new Project()).loadClass((String)"", (ClassLoader)null).declareMethod("", (Class)Void.TYPE, (Class[])null));
   }

   public BCMethod getMethod() {
      return (BCMethod)this.getOwner();
   }

   Collection getAttributesHolder() {
      return this._attrs;
   }

   public int getMaxStack() {
      return this._maxStack;
   }

   public void setMaxStack(int max) {
      this._maxStack = max;
   }

   public int getMaxLocals() {
      return this._maxLocals;
   }

   public void setMaxLocals(int max) {
      this._maxLocals = max;
   }

   public int getLocalsIndex(int paramIndex) {
      if (paramIndex < 0) {
         return -1;
      } else {
         int pos = 0;
         if (!this.getMethod().isStatic()) {
            pos = 1;
         }

         String[] params = this.getMethod().getParamNames();

         for(int i = 0; i < paramIndex; ++pos) {
            if (i == params.length) {
               return -1;
            }

            if (params[i].equals(Long.TYPE.getName()) || params[i].equals(Double.TYPE.getName())) {
               ++pos;
            }

            ++i;
         }

         return pos;
      }
   }

   public int getParamsIndex(int localIndex) {
      int pos = 0;
      if (!this.getMethod().isStatic()) {
         pos = 1;
      }

      String[] params = this.getMethod().getParamNames();

      for(int i = 0; i < params.length; ++pos) {
         if (localIndex == pos) {
            return i;
         }

         if (params[i].equals(Long.TYPE.getName()) || params[i].equals(Double.TYPE.getName())) {
            ++pos;
         }

         ++i;
      }

      return -1;
   }

   public int getNextLocalsIndex() {
      this.calculateMaxLocals();
      return this.getMaxLocals();
   }

   public void calculateMaxLocals() {
      String[] params = this.getMethod().getParamNames();
      int max = 0;
      if (params.length == 0 && !this.getMethod().isStatic()) {
         max = 1;
      } else if (params.length > 0) {
         max = this.getLocalsIndex(params.length - 1) + 1;
         if (params[params.length - 1].equals(Long.TYPE.getName()) || params[params.length - 1].equals(Double.TYPE.getName())) {
            ++max;
         }
      }

      for(CodeEntry entry = this._head.next; entry != this._tail; entry = entry.next) {
         int current = false;
         if (entry instanceof StoreInstruction) {
            StoreInstruction store = (StoreInstruction)entry;
            int current = store.getLocal() + 1;
            if (store.getType().equals(Long.TYPE) || store.getType().equals(Double.TYPE)) {
               ++current;
            }

            if (current > max) {
               max = current;
            }
         }
      }

      this.setMaxLocals(max);
   }

   public void calculateMaxStack() {
      int stack = 0;
      int max = 0;
      ExceptionHandler[] handlers = this.getExceptionHandlers();

      for(CodeEntry entry = this._head.next; entry != this._tail; entry = entry.next) {
         Instruction ins = (Instruction)entry;
         stack += ins.getStackChange();

         for(int j = 0; j < handlers.length; ++j) {
            if (handlers[j].getTryStart() == ins) {
               ++stack;
            }
         }

         if (stack > max) {
            max = stack;
         }
      }

      this.setMaxStack(max);
   }

   public ExceptionHandler[] getExceptionHandlers() {
      return (ExceptionHandler[])((ExceptionHandler[])this._handlers.toArray(new ExceptionHandler[this._handlers.size()]));
   }

   public ExceptionHandler getExceptionHandler(String catchType) {
      catchType = this.getProject().getNameCache().getExternalForm(catchType, false);
      ExceptionHandler[] handlers = this.getExceptionHandlers();

      for(int i = 0; i < handlers.length; ++i) {
         String type = handlers[i].getCatchName();
         if (type == null && catchType == null || type != null && type.equals(catchType)) {
            return handlers[i];
         }
      }

      return null;
   }

   public ExceptionHandler getExceptionHandler(Class catchType) {
      return catchType == null ? this.getExceptionHandler((String)null) : this.getExceptionHandler(catchType.getName());
   }

   public ExceptionHandler getExceptionHandler(BCClass catchType) {
      return catchType == null ? this.getExceptionHandler((String)null) : this.getExceptionHandler(catchType.getName());
   }

   public ExceptionHandler[] getExceptionHandlers(String catchType) {
      catchType = this.getProject().getNameCache().getExternalForm(catchType, false);
      List matches = new LinkedList();
      ExceptionHandler[] handlers = this.getExceptionHandlers();

      for(int i = 0; i < handlers.length; ++i) {
         String type = handlers[i].getCatchName();
         if (type == null && catchType == null || type != null && type.equals(catchType)) {
            matches.add(handlers[i]);
         }
      }

      return (ExceptionHandler[])((ExceptionHandler[])matches.toArray(new ExceptionHandler[matches.size()]));
   }

   public ExceptionHandler[] getExceptionHandlers(Class catchType) {
      return catchType == null ? this.getExceptionHandlers((String)null) : this.getExceptionHandlers(catchType.getName());
   }

   public ExceptionHandler[] getExceptionHandlers(BCClass catchType) {
      return catchType == null ? this.getExceptionHandlers((String)null) : this.getExceptionHandlers(catchType.getName());
   }

   public void setExceptionHandlers(ExceptionHandler[] handlers) {
      this.clearExceptionHandlers();
      if (handlers != null) {
         for(int i = 0; i < handlers.length; ++i) {
            this.addExceptionHandler(handlers[i]);
         }
      }

   }

   public ExceptionHandler addExceptionHandler(ExceptionHandler handler) {
      ExceptionHandler newHandler = this.addExceptionHandler();
      newHandler.read(handler);
      return newHandler;
   }

   public ExceptionHandler addExceptionHandler() {
      ExceptionHandler handler = new ExceptionHandler(this);
      this._handlers.add(handler);
      return handler;
   }

   public ExceptionHandler addExceptionHandler(Instruction tryStart, Instruction tryEnd, Instruction handlerStart, String catchType) {
      ExceptionHandler handler = this.addExceptionHandler();
      handler.setTryStart(tryStart);
      handler.setTryEnd(tryEnd);
      handler.setHandlerStart(handlerStart);
      handler.setCatch(catchType);
      return handler;
   }

   public ExceptionHandler addExceptionHandler(Instruction tryStart, Instruction tryEnd, Instruction handlerStart, Class catchType) {
      String catchName = null;
      if (catchType != null) {
         catchName = catchType.getName();
      }

      return this.addExceptionHandler(tryStart, tryEnd, handlerStart, catchName);
   }

   public ExceptionHandler addExceptionHandler(Instruction tryStart, Instruction tryEnd, Instruction handlerStart, BCClass catchType) {
      String catchName = null;
      if (catchType != null) {
         catchName = catchType.getName();
      }

      return this.addExceptionHandler(tryStart, tryEnd, handlerStart, catchName);
   }

   public void clearExceptionHandlers() {
      Iterator itr = this._handlers.iterator();

      while(itr.hasNext()) {
         ExceptionHandler handler = (ExceptionHandler)itr.next();
         itr.remove();
         handler.invalidate();
      }

   }

   public boolean removeExceptionHandler(String catchType) {
      return this.removeExceptionHandler(this.getExceptionHandler(catchType));
   }

   public boolean removeExceptionHandler(Class catchType) {
      return catchType == null ? this.removeExceptionHandler((String)null) : this.removeExceptionHandler(catchType.getName());
   }

   public boolean removeExceptionHandler(BCClass catchType) {
      return catchType == null ? this.removeExceptionHandler((String)null) : this.removeExceptionHandler(catchType.getName());
   }

   public boolean removeExceptionHandler(ExceptionHandler handler) {
      if (handler != null && this._handlers.remove(handler)) {
         handler.invalidate();
         return true;
      } else {
         return false;
      }
   }

   public int size() {
      return this._size;
   }

   public void beforeFirst() {
      this._ci = new CodeIterator(this._head, -1);
   }

   public void afterLast() {
      if (this._size == 0) {
         this._ci = new CodeIterator(this._head, -1);
      } else {
         this._ci = new CodeIterator(this._tail.prev, this._size - 1);
      }

   }

   public void before(Instruction ins) {
      if (ins.getCode() != this) {
         throw new IllegalArgumentException("ins.code != this");
      } else {
         this._ci = new CodeIterator(ins.prev, -99);
      }
   }

   public void after(Instruction ins) {
      this.before(ins);
      this.next();
   }

   public boolean hasNext() {
      return this._ci.hasNext();
   }

   public boolean hasPrevious() {
      return this._ci.hasPrevious();
   }

   public Instruction next() {
      return (Instruction)this._ci.next();
   }

   public int nextIndex() {
      return this._ci.nextIndex();
   }

   public Instruction previous() {
      return (Instruction)this._ci.previous();
   }

   public int previousIndex() {
      return this._ci.previousIndex();
   }

   public void before(int index) {
      if (index >= 0 && index < this._size) {
         CodeEntry entry = this._head;

         for(int i = 0; i < index; ++i) {
            entry = entry.next;
         }

         this._ci = new CodeIterator(entry, index - 1);
      } else {
         throw new IndexOutOfBoundsException(String.valueOf(index));
      }
   }

   public void after(int index) {
      this.before(index);
      this.next();
   }

   public boolean searchForward(Instruction template) {
      if (template == null) {
         return false;
      } else {
         do {
            if (!this.hasNext()) {
               return false;
            }
         } while(!template.equalsInstruction(this.next()));

         return true;
      }
   }

   public boolean searchBackward(Instruction template) {
      if (template == null) {
         return false;
      } else {
         do {
            if (!this.hasPrevious()) {
               return false;
            }
         } while(!template.equalsInstruction(this.previous()));

         return true;
      }
   }

   public Instruction add(Instruction ins) {
      Instruction newIns = this.createInstruction(ins.getOpcode());
      newIns.read(ins);
      this._ci.add(newIns);
      return newIns;
   }

   public Instruction set(Instruction ins) {
      Instruction newIns = this.createInstruction(ins.getOpcode());
      newIns.read(ins);
      this._ci.set(newIns);
      return newIns;
   }

   public int replace(Instruction template, Instruction with) {
      this.beforeFirst();

      int count;
      for(count = 0; this.searchForward(template); ++count) {
         this.set(with);
      }

      return count;
   }

   public int replace(Instruction[] templates, Instruction[] with) {
      if (templates != null && with != null) {
         int count = 0;

         for(int i = 0; i < templates.length; ++i) {
            if (with == null) {
               count += this.replace((Instruction)templates[i], (Instruction)null);
            } else {
               count += this.replace(templates[i], with[i]);
            }
         }

         return count;
      } else {
         return 0;
      }
   }

   public void remove() {
      this._ci.remove();
   }

   public ClassConstantInstruction classconstant() {
      return new ClassConstantInstruction(this.getMethod().getDeclarer(), this, this.nop());
   }

   public Instruction nop() {
      return this.addInstruction(0);
   }

   public ConstantInstruction constant() {
      return (ConstantInstruction)this.addInstruction(new ConstantInstruction(this));
   }

   public LoadInstruction xload() {
      return (LoadInstruction)this.addInstruction(new LoadInstruction(this));
   }

   public LoadInstruction iload() {
      return (LoadInstruction)this.addInstruction((new LoadInstruction(this)).setType(Integer.TYPE));
   }

   public LoadInstruction lload() {
      return (LoadInstruction)this.addInstruction((new LoadInstruction(this)).setType(Long.TYPE));
   }

   public LoadInstruction fload() {
      return (LoadInstruction)this.addInstruction((new LoadInstruction(this)).setType(Float.TYPE));
   }

   public LoadInstruction dload() {
      return (LoadInstruction)this.addInstruction((new LoadInstruction(this)).setType(Double.TYPE));
   }

   public LoadInstruction aload() {
      return (LoadInstruction)this.addInstruction((new LoadInstruction(this)).setType(Object.class));
   }

   public StoreInstruction xstore() {
      return (StoreInstruction)this.addInstruction(new StoreInstruction(this));
   }

   public StoreInstruction istore() {
      return (StoreInstruction)this.addInstruction((new StoreInstruction(this)).setType(Integer.TYPE));
   }

   public StoreInstruction lstore() {
      return (StoreInstruction)this.addInstruction((new StoreInstruction(this)).setType(Long.TYPE));
   }

   public StoreInstruction fstore() {
      return (StoreInstruction)this.addInstruction((new StoreInstruction(this)).setType(Float.TYPE));
   }

   public StoreInstruction dstore() {
      return (StoreInstruction)this.addInstruction((new StoreInstruction(this)).setType(Double.TYPE));
   }

   public StoreInstruction astore() {
      return (StoreInstruction)this.addInstruction((new StoreInstruction(this)).setType(Object.class));
   }

   public RetInstruction ret() {
      return (RetInstruction)this.addInstruction(169);
   }

   public IIncInstruction iinc() {
      return (IIncInstruction)this.addInstruction(132);
   }

   public WideInstruction wide() {
      return (WideInstruction)this.addInstruction(196);
   }

   public ArrayLoadInstruction xaload() {
      return (ArrayLoadInstruction)this.addInstruction(new ArrayLoadInstruction(this));
   }

   public ArrayLoadInstruction iaload() {
      return (ArrayLoadInstruction)this.addInstruction(46);
   }

   public ArrayLoadInstruction laload() {
      return (ArrayLoadInstruction)this.addInstruction(47);
   }

   public ArrayLoadInstruction faload() {
      return (ArrayLoadInstruction)this.addInstruction(48);
   }

   public ArrayLoadInstruction daload() {
      return (ArrayLoadInstruction)this.addInstruction(49);
   }

   public ArrayLoadInstruction aaload() {
      return (ArrayLoadInstruction)this.addInstruction(50);
   }

   public ArrayLoadInstruction baload() {
      return (ArrayLoadInstruction)this.addInstruction(51);
   }

   public ArrayLoadInstruction caload() {
      return (ArrayLoadInstruction)this.addInstruction(52);
   }

   public ArrayLoadInstruction saload() {
      return (ArrayLoadInstruction)this.addInstruction(53);
   }

   public ArrayStoreInstruction xastore() {
      return (ArrayStoreInstruction)this.addInstruction(new ArrayStoreInstruction(this));
   }

   public ArrayStoreInstruction iastore() {
      return (ArrayStoreInstruction)this.addInstruction(79);
   }

   public ArrayStoreInstruction lastore() {
      return (ArrayStoreInstruction)this.addInstruction(80);
   }

   public ArrayStoreInstruction fastore() {
      return (ArrayStoreInstruction)this.addInstruction(81);
   }

   public ArrayStoreInstruction dastore() {
      return (ArrayStoreInstruction)this.addInstruction(82);
   }

   public ArrayStoreInstruction aastore() {
      return (ArrayStoreInstruction)this.addInstruction(83);
   }

   public ArrayStoreInstruction bastore() {
      return (ArrayStoreInstruction)this.addInstruction(84);
   }

   public ArrayStoreInstruction castore() {
      return (ArrayStoreInstruction)this.addInstruction(85);
   }

   public ArrayStoreInstruction sastore() {
      return (ArrayStoreInstruction)this.addInstruction(86);
   }

   public StackInstruction pop() {
      return (StackInstruction)this.addInstruction(87);
   }

   public StackInstruction pop2() {
      return (StackInstruction)this.addInstruction(88);
   }

   public StackInstruction dup() {
      return (StackInstruction)this.addInstruction(89);
   }

   public StackInstruction dupx1() {
      return (StackInstruction)this.addInstruction(90);
   }

   public StackInstruction dupx2() {
      return (StackInstruction)this.addInstruction(91);
   }

   public StackInstruction dup2() {
      return (StackInstruction)this.addInstruction(92);
   }

   public StackInstruction dup2x1() {
      return (StackInstruction)this.addInstruction(93);
   }

   public StackInstruction dup2x2() {
      return (StackInstruction)this.addInstruction(94);
   }

   public StackInstruction swap() {
      return (StackInstruction)this.addInstruction(95);
   }

   public MathInstruction math() {
      return (MathInstruction)this.addInstruction(new MathInstruction(this));
   }

   public MathInstruction xadd() {
      MathInstruction mi = this.math();
      return mi.setOperation(96);
   }

   public MathInstruction iadd() {
      return (MathInstruction)this.addInstruction(96);
   }

   public MathInstruction ladd() {
      return (MathInstruction)this.addInstruction(97);
   }

   public MathInstruction fadd() {
      return (MathInstruction)this.addInstruction(98);
   }

   public MathInstruction dadd() {
      return (MathInstruction)this.addInstruction(99);
   }

   public MathInstruction xsub() {
      MathInstruction mi = this.math();
      return mi.setOperation(100);
   }

   public MathInstruction isub() {
      return (MathInstruction)this.addInstruction(100);
   }

   public MathInstruction lsub() {
      return (MathInstruction)this.addInstruction(101);
   }

   public MathInstruction fsub() {
      return (MathInstruction)this.addInstruction(102);
   }

   public MathInstruction dsub() {
      return (MathInstruction)this.addInstruction(103);
   }

   public MathInstruction xmul() {
      MathInstruction mi = this.math();
      return mi.setOperation(104);
   }

   public MathInstruction imul() {
      return (MathInstruction)this.addInstruction(104);
   }

   public MathInstruction lmul() {
      return (MathInstruction)this.addInstruction(105);
   }

   public MathInstruction fmul() {
      return (MathInstruction)this.addInstruction(106);
   }

   public MathInstruction dmul() {
      return (MathInstruction)this.addInstruction(107);
   }

   public MathInstruction xdiv() {
      MathInstruction mi = this.math();
      return mi.setOperation(108);
   }

   public MathInstruction idiv() {
      return (MathInstruction)this.addInstruction(108);
   }

   public MathInstruction ldiv() {
      return (MathInstruction)this.addInstruction(109);
   }

   public MathInstruction fdiv() {
      return (MathInstruction)this.addInstruction(110);
   }

   public MathInstruction ddiv() {
      return (MathInstruction)this.addInstruction(111);
   }

   public MathInstruction xrem() {
      MathInstruction mi = this.math();
      return mi.setOperation(112);
   }

   public MathInstruction irem() {
      return (MathInstruction)this.addInstruction(112);
   }

   public MathInstruction lrem() {
      return (MathInstruction)this.addInstruction(113);
   }

   public MathInstruction frem() {
      return (MathInstruction)this.addInstruction(114);
   }

   public MathInstruction drem() {
      return (MathInstruction)this.addInstruction(115);
   }

   public MathInstruction xneg() {
      MathInstruction mi = this.math();
      return mi.setOperation(116);
   }

   public MathInstruction ineg() {
      return (MathInstruction)this.addInstruction(116);
   }

   public MathInstruction lneg() {
      return (MathInstruction)this.addInstruction(117);
   }

   public MathInstruction fneg() {
      return (MathInstruction)this.addInstruction(118);
   }

   public MathInstruction dneg() {
      return (MathInstruction)this.addInstruction(119);
   }

   public MathInstruction xshl() {
      MathInstruction mi = this.math();
      return mi.setOperation(120);
   }

   public MathInstruction ishl() {
      return (MathInstruction)this.addInstruction(120);
   }

   public MathInstruction lshl() {
      return (MathInstruction)this.addInstruction(121);
   }

   public MathInstruction xshr() {
      MathInstruction mi = this.math();
      return mi.setOperation(122);
   }

   public MathInstruction ishr() {
      return (MathInstruction)this.addInstruction(122);
   }

   public MathInstruction lshr() {
      return (MathInstruction)this.addInstruction(123);
   }

   public MathInstruction xushr() {
      MathInstruction mi = this.math();
      return mi.setOperation(124);
   }

   public MathInstruction iushr() {
      return (MathInstruction)this.addInstruction(124);
   }

   public MathInstruction lushr() {
      return (MathInstruction)this.addInstruction(125);
   }

   public MathInstruction xand() {
      MathInstruction mi = this.math();
      return mi.setOperation(126);
   }

   public MathInstruction iand() {
      return (MathInstruction)this.addInstruction(126);
   }

   public MathInstruction land() {
      return (MathInstruction)this.addInstruction(127);
   }

   public MathInstruction xor() {
      MathInstruction mi = this.math();
      return mi.setOperation(128);
   }

   public MathInstruction ior() {
      return (MathInstruction)this.addInstruction(128);
   }

   public MathInstruction lor() {
      return (MathInstruction)this.addInstruction(129);
   }

   public MathInstruction xxor() {
      MathInstruction mi = this.math();
      return mi.setOperation(130);
   }

   public MathInstruction ixor() {
      return (MathInstruction)this.addInstruction(130);
   }

   public MathInstruction lxor() {
      return (MathInstruction)this.addInstruction(131);
   }

   public ConvertInstruction convert() {
      return (ConvertInstruction)this.addInstruction(new ConvertInstruction(this));
   }

   public CmpInstruction xcmp() {
      return (CmpInstruction)this.addInstruction(new CmpInstruction(this));
   }

   public CmpInstruction lcmp() {
      return (CmpInstruction)this.addInstruction(148);
   }

   public CmpInstruction fcmpl() {
      return (CmpInstruction)this.addInstruction(149);
   }

   public CmpInstruction fcmpg() {
      return (CmpInstruction)this.addInstruction(150);
   }

   public CmpInstruction dcmpl() {
      return (CmpInstruction)this.addInstruction(151);
   }

   public CmpInstruction dcmpg() {
      return (CmpInstruction)this.addInstruction(152);
   }

   public IfInstruction ifeq() {
      return (IfInstruction)this.addInstruction(153);
   }

   public IfInstruction ifne() {
      return (IfInstruction)this.addInstruction(154);
   }

   public IfInstruction iflt() {
      return (IfInstruction)this.addInstruction(155);
   }

   public IfInstruction ifge() {
      return (IfInstruction)this.addInstruction(156);
   }

   public IfInstruction ifgt() {
      return (IfInstruction)this.addInstruction(157);
   }

   public IfInstruction ifle() {
      return (IfInstruction)this.addInstruction(158);
   }

   public IfInstruction ificmpeq() {
      return (IfInstruction)this.addInstruction(159);
   }

   public IfInstruction ificmpne() {
      return (IfInstruction)this.addInstruction(160);
   }

   public IfInstruction ificmplt() {
      return (IfInstruction)this.addInstruction(161);
   }

   public IfInstruction ificmpge() {
      return (IfInstruction)this.addInstruction(162);
   }

   public IfInstruction ificmpgt() {
      return (IfInstruction)this.addInstruction(163);
   }

   public IfInstruction ificmple() {
      return (IfInstruction)this.addInstruction(164);
   }

   public IfInstruction ifacmpeq() {
      return (IfInstruction)this.addInstruction(165);
   }

   public IfInstruction ifacmpne() {
      return (IfInstruction)this.addInstruction(166);
   }

   public IfInstruction ifnull() {
      return (IfInstruction)this.addInstruction(198);
   }

   public IfInstruction ifnonnull() {
      return (IfInstruction)this.addInstruction(199);
   }

   public JumpInstruction go2() {
      return (JumpInstruction)this.addInstruction(167);
   }

   public JumpInstruction jsr() {
      return (JumpInstruction)this.addInstruction(168);
   }

   public TableSwitchInstruction tableswitch() {
      return (TableSwitchInstruction)this.addInstruction(170);
   }

   public LookupSwitchInstruction lookupswitch() {
      return (LookupSwitchInstruction)this.addInstruction(171);
   }

   public ReturnInstruction xreturn() {
      return (ReturnInstruction)this.addInstruction(new ReturnInstruction(this));
   }

   public ReturnInstruction vreturn() {
      return (ReturnInstruction)this.addInstruction(177);
   }

   public ReturnInstruction ireturn() {
      return (ReturnInstruction)this.addInstruction(172);
   }

   public ReturnInstruction lreturn() {
      return (ReturnInstruction)this.addInstruction(173);
   }

   public ReturnInstruction freturn() {
      return (ReturnInstruction)this.addInstruction(174);
   }

   public ReturnInstruction dreturn() {
      return (ReturnInstruction)this.addInstruction(175);
   }

   public ReturnInstruction areturn() {
      return (ReturnInstruction)this.addInstruction(176);
   }

   public GetFieldInstruction getfield() {
      return (GetFieldInstruction)this.addInstruction(180);
   }

   public GetFieldInstruction getstatic() {
      return (GetFieldInstruction)this.addInstruction(178);
   }

   public PutFieldInstruction putfield() {
      return (PutFieldInstruction)this.addInstruction(181);
   }

   public PutFieldInstruction putstatic() {
      return (PutFieldInstruction)this.addInstruction(179);
   }

   public MethodInstruction invokevirtual() {
      return (MethodInstruction)this.addInstruction(182);
   }

   public MethodInstruction invokespecial() {
      return (MethodInstruction)this.addInstruction(183);
   }

   public MethodInstruction invokeinterface() {
      return (MethodInstruction)this.addInstruction(185);
   }

   public MethodInstruction invokestatic() {
      return (MethodInstruction)this.addInstruction(184);
   }

   public MethodInstruction invokedynamic() {
      return (MethodInstruction)this.addInstruction(186);
   }

   public ClassInstruction anew() {
      return (ClassInstruction)this.addInstruction(187);
   }

   public ClassInstruction anewarray() {
      return (ClassInstruction)this.addInstruction(189);
   }

   public ClassInstruction checkcast() {
      return (ClassInstruction)this.addInstruction(192);
   }

   public ClassInstruction isinstance() {
      return (ClassInstruction)this.addInstruction(193);
   }

   public MultiANewArrayInstruction multianewarray() {
      return (MultiANewArrayInstruction)this.addInstruction(197);
   }

   public NewArrayInstruction newarray() {
      return (NewArrayInstruction)this.addInstruction(188);
   }

   public Instruction arraylength() {
      return this.addInstruction(190);
   }

   public Instruction athrow() {
      return this.addInstruction(191);
   }

   public MonitorEnterInstruction monitorenter() {
      return (MonitorEnterInstruction)this.addInstruction(194);
   }

   public MonitorExitInstruction monitorexit() {
      return (MonitorExitInstruction)this.addInstruction(195);
   }

   public Instruction[] getInstructions() {
      Instruction[] arr = new Instruction[this._size];
      int i = 0;

      for(CodeEntry entry = this._head.next; entry != this._tail; entry = entry.next) {
         arr[i++] = (Instruction)entry;
      }

      return arr;
   }

   int getLength() {
      int length = 12;
      Instruction last = this.getLastInstruction();
      if (last != null) {
         length += last.getByteIndex() + last.getLength();
      }

      length += 8 * this._handlers.size();
      Attribute[] attrs = this.getAttributes();

      for(int i = 0; i < attrs.length; ++i) {
         length += attrs[i].getLength() + 6;
      }

      return length;
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterCode(this);

      for(CodeEntry entry = this._head.next; entry != this._tail; entry = entry.next) {
         Instruction ins = (Instruction)entry;
         visit.enterInstruction(ins);
         ins.acceptVisit(visit);
         visit.exitInstruction(ins);
      }

      Iterator i = this._handlers.iterator();

      while(i.hasNext()) {
         ((ExceptionHandler)i.next()).acceptVisit(visit);
      }

      this.visitAttributes(visit);
      visit.exitCode(this);
   }

   public LineNumberTable getLineNumberTable(boolean add) {
      LineNumberTable attr = (LineNumberTable)this.getAttribute("LineNumberTable");
      return add && attr == null ? (LineNumberTable)this.addAttribute("LineNumberTable") : attr;
   }

   public boolean removeLineNumberTable() {
      return this.removeAttribute("LineNumberTable");
   }

   public LocalVariableTable getLocalVariableTable(boolean add) {
      LocalVariableTable attr = (LocalVariableTable)this.getAttribute("LocalVariableTable");
      return add && attr == null ? (LocalVariableTable)this.addAttribute("LocalVariableTable") : attr;
   }

   public boolean removeLocalVariableTables() {
      return this.removeAttribute("LocalVariableTable");
   }

   public LocalVariableTypeTable getLocalVariableTypeTable(boolean add) {
      LocalVariableTypeTable attr = (LocalVariableTypeTable)this.getAttribute("LocalVariableTypeTable");
      return add && attr == null ? (LocalVariableTypeTable)this.addAttribute("LocalVariableTypeTable") : attr;
   }

   public boolean removeLocalVariableTypeTables() {
      return this.removeAttribute("LocalVariableTypeTable");
   }

   void read(Attribute attr) {
      Code orig = (Code)attr;
      this._maxStack = orig.getMaxStack();
      this._maxLocals = orig.getMaxLocals();
      this._head.next = this._tail;
      this._tail.prev = this._head;
      this._size = 0;
      this._byteIndexesValid = false;
      this.beforeFirst();
      this._handlers.clear();

      for(CodeEntry entry = orig._head.next; entry != orig._tail; entry = entry.next) {
         Instruction origIns = (Instruction)entry;
         Instruction ins = this.createInstruction(origIns.getOpcode());
         this._ci.addInternal(ins);
         if (!(ins instanceof ConstantInstruction)) {
            ins.read(origIns);
         }
      }

      ExceptionHandler[] origHandlers = orig.getExceptionHandlers();

      for(int i = 0; i < origHandlers.length; ++i) {
         ExceptionHandler handler = this.addExceptionHandler();
         handler.read(origHandlers[i]);
         handler.updateTargets();
      }

      this.updateInstructionPointers();
      this.setAttributes(orig.getAttributes());
      LocalVariableTable locals = this.getLocalVariableTable(false);
      if (locals != null) {
         locals.updateTargets();
      }

      LocalVariableTypeTable localTypes = this.getLocalVariableTypeTable(false);
      if (localTypes != null) {
         localTypes.updateTargets();
      }

      LineNumberTable lines = this.getLineNumberTable(false);
      if (lines != null) {
         lines.updateTargets();
      }

      CodeEntry copy = this._head.next;

      for(CodeEntry entry = orig._head.next; entry != orig._tail; copy = copy.next) {
         if (entry instanceof ConstantInstruction) {
            ((ConstantInstruction)copy).read((Instruction)entry);
         }

         entry = entry.next;
      }

      this.beforeFirst();
   }

   void read(DataInput in, int length) throws IOException {
      this._maxStack = in.readUnsignedShort();
      this._maxLocals = in.readUnsignedShort();
      this.readCode(in, in.readInt());
      this._handlers.clear();
      int exceptionCount = in.readUnsignedShort();

      for(int i = 0; i < exceptionCount; ++i) {
         ExceptionHandler excep = this.addExceptionHandler();
         excep.read(in);
         excep.updateTargets();
      }

      this.readAttributes(in);
      LocalVariableTable locals = this.getLocalVariableTable(false);
      if (locals != null) {
         locals.updateTargets();
      }

      LocalVariableTypeTable localTypes = this.getLocalVariableTypeTable(false);
      if (localTypes != null) {
         localTypes.updateTargets();
      }

      LineNumberTable lines = this.getLineNumberTable(false);
      if (lines != null) {
         lines.updateTargets();
      }

   }

   void write(DataOutput out, int length) throws IOException {
      out.writeShort(this._maxStack);
      out.writeShort(this._maxLocals);
      byte[] code = this.toByteArray();
      out.writeInt(code.length);
      out.write(code);
      out.writeShort(this._handlers.size());
      Iterator itr = this._handlers.iterator();

      while(itr.hasNext()) {
         ((ExceptionHandler)itr.next()).write(out);
      }

      this.writeAttributes(out);
   }

   private void readCode(DataInput in, int len) throws IOException {
      this._head.next = this._tail;
      this._tail.prev = this._head;
      this._size = 0;
      this._byteIndexesValid = true;
      this.beforeFirst();

      Instruction ins;
      for(int byteIndex = 0; byteIndex < len; byteIndex += ins.getLength()) {
         ins = this.createInstruction(in.readUnsignedByte());
         this._ci.addInternal(ins);
         ins.byteIndex = byteIndex;
         ins.read(in);
      }

      this.updateInstructionPointers();
      this.beforeFirst();
      if (!this._byteIndexesValid) {
         throw new IllegalStateException();
      }
   }

   private void updateInstructionPointers() {
      for(CodeEntry entry = this._head.next; entry != this._tail; entry = entry.next) {
         if (entry instanceof InstructionPtr) {
            ((InstructionPtr)entry).updateTargets();
         }
      }

   }

   int getByteIndex(Instruction ins) {
      if (this._byteIndexesValid && ins.byteIndex != -1) {
         return ins.byteIndex;
      } else {
         int byteIndex = 0;

         for(CodeEntry entry = this._head.next; entry != this._tail; entry = entry.next) {
            if (entry == ins) {
               return byteIndex;
            }

            byteIndex += ((Instruction)entry).getLength();
         }

         throw new IllegalArgumentException("ins.owner != this");
      }
   }

   void invalidateByteIndexes() {
      this._byteIndexesValid = false;
   }

   Instruction getInstruction(int byteIndex) {
      if (byteIndex < 0) {
         return null;
      } else {
         int curIndex = 0;

         for(CodeEntry entry = this._head.next; entry != this._tail; entry = entry.next) {
            if (byteIndex == curIndex) {
               return (Instruction)entry;
            }

            curIndex += ((Instruction)entry).getLength();
         }

         if (byteIndex == curIndex) {
            return null;
         } else {
            throw new IllegalArgumentException(String.valueOf(byteIndex));
         }
      }
   }

   Instruction getFirstInstruction() {
      return (Instruction)this._head.next;
   }

   Instruction getLastInstruction() {
      return (Instruction)this._tail.prev;
   }

   private int indexOf(Instruction ins) {
      int i = 0;

      for(CodeEntry entry = this._head.next; entry != this._tail; ++i) {
         if (entry == ins) {
            return i;
         }

         entry = entry.next;
      }

      throw new IllegalArgumentException("ins.code != this");
   }

   private void writeCode(DataOutput out) throws IOException {
      for(CodeEntry entry = this._head.next; entry != this._tail; entry = entry.next) {
         Instruction ins = (Instruction)entry;
         out.writeByte(ins.getOpcode());
         ins.write(out);
      }

   }

   private byte[] toByteArray() throws IOException {
      ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
      DataOutputStream stream = new DataOutputStream(byteStream);

      byte[] var3;
      try {
         this.writeCode(stream);
         var3 = byteStream.toByteArray();
      } finally {
         try {
            stream.close();
         } catch (Exception var10) {
         }

      }

      return var3;
   }

   private void fromByteArray(byte[] code) throws IOException {
      if (code == null) {
         this._head.next = this._tail;
         this._tail.prev = this._head;
         this._size = 0;
      } else {
         DataInputStream stream = new DataInputStream(new ByteArrayInputStream(code));

         try {
            this.readCode(stream, code.length);
         } finally {
            try {
               stream.close();
            } catch (Exception var9) {
            }

         }
      }

   }

   private Instruction addInstruction(Instruction ins) {
      this._ci.add(ins);
      return ins;
   }

   private Instruction addInstruction(int opcode) {
      return this.addInstruction(this.createInstruction(opcode));
   }

   private Instruction createInstruction(int opcode) {
      switch (opcode) {
         case 0:
         case 190:
         case 191:
            return new Instruction(this, opcode);
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
            return new ConstantInstruction(this, opcode);
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
            return new LoadInstruction(this, opcode);
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
            return new ArrayLoadInstruction(this, opcode);
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
         case 64:
         case 65:
         case 66:
         case 67:
         case 68:
         case 69:
         case 70:
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
         case 76:
         case 77:
         case 78:
            return new StoreInstruction(this, opcode);
         case 79:
         case 80:
         case 81:
         case 82:
         case 83:
         case 84:
         case 85:
         case 86:
            return new ArrayStoreInstruction(this, opcode);
         case 87:
         case 88:
         case 89:
         case 90:
         case 91:
         case 92:
         case 93:
         case 94:
         case 95:
            return new StackInstruction(this, opcode);
         case 96:
         case 97:
         case 98:
         case 99:
         case 100:
         case 101:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 112:
         case 113:
         case 114:
         case 115:
         case 116:
         case 117:
         case 118:
         case 119:
         case 120:
         case 121:
         case 122:
         case 123:
         case 124:
         case 125:
         case 126:
         case 127:
         case 128:
         case 129:
         case 130:
         case 131:
            return new MathInstruction(this, opcode);
         case 132:
            return new IIncInstruction(this);
         case 133:
         case 134:
         case 135:
         case 136:
         case 137:
         case 138:
         case 139:
         case 140:
         case 141:
         case 142:
         case 143:
         case 144:
         case 145:
         case 146:
         case 147:
            return new ConvertInstruction(this, opcode);
         case 148:
         case 149:
         case 150:
         case 151:
         case 152:
            return new CmpInstruction(this, opcode);
         case 153:
         case 154:
         case 155:
         case 156:
         case 157:
         case 158:
         case 159:
         case 160:
         case 161:
         case 162:
         case 163:
         case 164:
         case 165:
         case 166:
         case 198:
         case 199:
            return new IfInstruction(this, opcode);
         case 167:
         case 168:
         case 200:
         case 201:
            return new GotoInstruction(this, opcode);
         case 169:
            return new RetInstruction(this);
         case 170:
            return new TableSwitchInstruction(this);
         case 171:
            return new LookupSwitchInstruction(this);
         case 172:
         case 173:
         case 174:
         case 175:
         case 176:
         case 177:
            return new ReturnInstruction(this, opcode);
         case 178:
         case 180:
            return new GetFieldInstruction(this, opcode);
         case 179:
         case 181:
            return new PutFieldInstruction(this, opcode);
         case 182:
         case 183:
         case 184:
         case 185:
         case 186:
            return new MethodInstruction(this, opcode);
         case 187:
         case 189:
         case 192:
         case 193:
            return new ClassInstruction(this, opcode);
         case 188:
            return new NewArrayInstruction(this);
         case 194:
            return new MonitorEnterInstruction(this);
         case 195:
            return new MonitorExitInstruction(this);
         case 196:
            return new WideInstruction(this);
         case 197:
            return new MultiANewArrayInstruction(this);
         default:
            throw new IllegalArgumentException("Illegal opcode: " + opcode);
      }
   }

   public ListIterator listIterator() {
      return new CodeIterator(this._head, -1);
   }

   private class CodeIterator implements ListIterator {
      public static final int UNSET = -99;
      private CodeEntry _bn = null;
      private Instruction _last = null;
      private int _index = -99;

      public CodeIterator(CodeEntry entry, int index) {
         this._bn = entry;
         this._index = index;
      }

      public boolean hasNext() {
         return this._bn.next != Code.this._tail;
      }

      public boolean hasPrevious() {
         return this._bn != Code.this._head;
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this._bn = this._bn.next;
            this._last = (Instruction)this._bn;
            if (this._index != -99) {
               ++this._index;
            }

            return this._last;
         }
      }

      public int nextIndex() {
         return this.initIndex() + 1;
      }

      public Object previous() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            this._last = (Instruction)this._bn;
            this._bn = this._bn.prev;
            if (this._index != -99) {
               --this._index;
            }

            return this._last;
         }
      }

      public int previousIndex() {
         return this.initIndex();
      }

      private int initIndex() {
         if (this._index == -99) {
            if (this._bn == Code.this._head) {
               this._index = -1;
            } else {
               this._index = Code.this.indexOf((Instruction)this._bn);
            }
         }

         return this._index;
      }

      public void add(Object obj) {
         this.addInternal(obj);
         Code.this.invalidateByteIndexes();
      }

      private void addInternal(Object obj) {
         if (obj == null) {
            throw new NullPointerException("obj = null");
         } else {
            Instruction ins = (Instruction)obj;
            if (Code.this._size == 0) {
               Code.this._head.next = ins;
               Code.this._tail.prev = ins;
               ins.prev = Code.this._head;
               ins.next = Code.this._tail;
               this._index = 0;
            } else {
               CodeEntry next = this._bn.next;
               this._bn.next = ins;
               next.prev = ins;
               ins.prev = this._bn;
               ins.next = next;
               if (this._index != -99) {
                  ++this._index;
               }
            }

            this._bn = ins;
            this._last = ins;
            Code.this._size++;
         }
      }

      public void set(Object obj) {
         if (obj == null) {
            throw new NullPointerException("obj = null");
         } else if (this._last == null) {
            throw new IllegalStateException();
         } else {
            Instruction ins = (Instruction)obj;
            ins.prev = this._last.prev;
            ins.next = this._last.next;
            ins.prev.next = ins;
            ins.next.prev = ins;
            this.replaceTarget(this._last, ins);
            this._last.invalidate();
            if (this._bn == this._last) {
               this._bn = ins;
            }

            this._last = ins;
            Code.this.invalidateByteIndexes();
         }
      }

      public void remove() {
         if (this._last == null) {
            throw new IllegalStateException();
         } else {
            if (this._bn == this._last) {
               this._bn = this._last.prev;
            }

            --this._index;
            this._last.prev.next = this._last.next;
            this._last.next.prev = this._last.prev;
            Code.this._size--;
            Instruction orig = this._last;
            Instruction replace = null;
            if (orig.next != Code.this._tail) {
               replace = (Instruction)orig.next;
            } else {
               replace = Code.this.nop();
            }

            this.replaceTarget(orig, replace);
            orig.invalidate();
            this._last = null;
            Code.this.invalidateByteIndexes();
         }
      }

      private void replaceTarget(Instruction orig, Instruction replace) {
         for(CodeEntry entry = Code.this._head.next; entry != Code.this._tail; entry = entry.next) {
            if (entry instanceof InstructionPtr) {
               ((InstructionPtr)entry).replaceTarget(orig, replace);
            }
         }

         ExceptionHandler[] handlers = Code.this.getExceptionHandlers();

         for(int i = 0; i < handlers.length; ++i) {
            handlers[i].replaceTarget(orig, replace);
         }

         LineNumberTable lineNumbers = Code.this.getLineNumberTable(false);
         if (lineNumbers != null) {
            lineNumbers.replaceTarget(orig, replace);
         }

         LocalVariableTable variables = Code.this.getLocalVariableTable(false);
         if (variables != null) {
            variables.replaceTarget(orig, replace);
         }

         LocalVariableTypeTable types = Code.this.getLocalVariableTypeTable(false);
         if (types != null) {
            types.replaceTarget(orig, replace);
         }

      }
   }
}
