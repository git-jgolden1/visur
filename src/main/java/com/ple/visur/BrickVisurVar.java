package com.ple.visur;

import CursorPositionDC.CursorPositionDCHolder;
import DataClass.CompoundDataClassBrick;
import DataClass.DataClassBrick;
import DataClass.PrimitiveDataClassBrick;
import DataClass.Result;

public class BrickVisurVar implements VisurVar {
  DataClassBrick val;
  static CursorPositionDCHolder cursorPositionDCHolder = ServiceHolder.editorModelCoupler.getCursorPositionDCHolder();

  public static BrickVisurVar make(DataClassBrick val) {
    return new BrickVisurVar(val);
  }

  public static BrickVisurVar make(String name, int val) {
//    DataClassBrick brick = null;
//    return new BrickVisurVar(cursorPositionDCHolder.wholeNumberDC.makeBrick(val));
    return null;
  }

  public BrickVisurVar(DataClassBrick val) {
    this.val = val;
  }

  @Override
  public Integer getVal() {
    String brickName = val.getName();
    Result<DataClassBrick> r = val.getOrCalculate(brickName, cursorPositionDCHolder);
    PrimitiveDataClassBrick dcb = (PrimitiveDataClassBrick) r.getVal();
    return (Integer) dcb.getDFB().getVal();
  }

  @Override
  public void putVal(Object o) {

  }

}
