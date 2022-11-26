package org.apache.oro.text.awk;

import java.io.Serializable;
import java.util.BitSet;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.oro.text.regex.Pattern;

public final class AwkPattern implements Pattern, Serializable {
   static final int _INVALID_STATE = -1;
   static final int _START_STATE = 1;
   int _numStates;
   int _endPosition;
   int _options;
   String _expression;
   Vector _Dtrans;
   Vector[] _nodeList;
   Vector _stateList;
   BitSet _U;
   BitSet _emptySet;
   BitSet[] _followSet;
   BitSet _endStates;
   Hashtable _stateMap;
   boolean _matchesNullString;
   boolean[] _fastMap;
   boolean _hasBeginAnchor = false;
   boolean _hasEndAnchor = false;

   AwkPattern(String var1, SyntaxTree var2) {
      this._expression = var1;
      this._endPosition = var2._positions - 1;
      this._followSet = var2._followSet;
      this._Dtrans = new Vector();
      this._stateList = new Vector();
      this._endStates = new BitSet();
      this._U = new BitSet(var2._positions);
      this._U.or(var2._root._firstPosition());
      int[] var5 = new int[256];
      this._Dtrans.addElement(var5);
      this._Dtrans.addElement(var5);
      this._numStates = 1;
      if (this._U.get(this._endPosition)) {
         this._endStates.set(this._numStates);
      }

      DFAState var6 = new DFAState((BitSet)this._U.clone(), this._numStates);
      this._stateMap = new Hashtable();
      this._stateMap.put(var6._state, var6);
      this._stateList.addElement(var6);
      this._stateList.addElement(var6);
      ++this._numStates;
      this._U.xor(this._U);
      this._emptySet = new BitSet(var2._positions);
      this._nodeList = new Vector[256];

      for(int var3 = 0; var3 < 256; ++var3) {
         this._nodeList[var3] = new Vector();

         for(int var4 = 0; var4 < var2._positions; ++var4) {
            if (var2._nodes[var4]._matches((char)var3)) {
               this._nodeList[var3].addElement(var2._nodes[var4]);
            }
         }
      }

      this._fastMap = var2.createFastMap();
      this._matchesNullString = this._endStates.get(1);
   }

   void _createNewState(int var1, int var2, int[] var3) {
      DFAState var6 = (DFAState)this._stateList.elementAt(var1);
      int var4 = this._nodeList[var2].size();
      this._U.xor(this._U);

      while(var4-- > 0) {
         int var5 = ((LeafNode)this._nodeList[var2].elementAt(var4))._position;
         if (var6._state.get(var5)) {
            this._U.or(this._followSet[var5]);
         }
      }

      if (!this._stateMap.containsKey(this._U)) {
         DFAState var7 = new DFAState((BitSet)this._U.clone(), this._numStates++);
         this._stateList.addElement(var7);
         this._stateMap.put(var7._state, var7);
         this._Dtrans.addElement(new int[256]);
         if (!this._U.equals(this._emptySet)) {
            var3[var2] = this._numStates - 1;
            if (this._U.get(this._endPosition)) {
               this._endStates.set(this._numStates - 1);
            }
         } else {
            var3[var2] = -1;
         }
      } else if (this._U.equals(this._emptySet)) {
         var3[var2] = -1;
      } else {
         var3[var2] = ((DFAState)this._stateMap.get(this._U))._stateNumber;
      }

   }

   int[] _getStateArray(int var1) {
      return (int[])this._Dtrans.elementAt(var1);
   }

   public String getPattern() {
      return this._expression;
   }

   public int getOptions() {
      return this._options;
   }
}
