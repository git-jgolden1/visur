package DataClass;

import java.util.HashMap;

public class CompoundDataClassBrick extends DataClassBrick {
  private CompoundDataClass cdc;
  HashMap<String, DataClassBrick> inners;
  private CompoundDataClassBrick(String name, CompoundDataClassBrick outer, CompoundDataClass cdc, HashMap<String, DataClassBrick> inners) {
      super(cdc, outer, name);
      this.cdc = cdc;
      this.inners = inners;
  }
  public static CompoundDataClassBrick make(String name, CompoundDataClassBrick outer, CompoundDataClass cdc, HashMap<String, DataClassBrick> inners) {
      return new CompoundDataClassBrick(name, outer, cdc, inners);
  }
  public DataClassBrick getInner(String name) {
      return inners.get(name);
  }
  public CompoundDataClass getCDC() {
    return cdc;
  }

  @Override
  public Result<DataClassBrick> getOrCalc(String name, DCHolder dcHolder) {
    DataClassBrick inner = getInner(name);
    Result<DataClassBrick> r;
    //if inner's value is set, return result whose value equals getInner(name)
    if(inner == null) {
      r = calc(name, dcHolder);
    } else {
      r = Result.make(inner, null);
    }
    CompoundDataClassBrick outer = getOuter();
    if(r.getError() != null && outer != null) {
      r = outer.getOrCalc(name, dcHolder);
    }
    return r;
  }

  public Result putInner(String name, Object val) {
    String error = null;
    if(inners.containsKey(name)) {
      DataClassBrick inner = inners.get(name);
      if(inner instanceof PrimitiveDataClassBrick) {
        PrimitiveDataClassBrick innerAsPDCB = (PrimitiveDataClassBrick) inner;
        PrimitiveDataClass pdc = innerAsPDCB.getPDC();
        PrimitiveDataClassBrick pdcb = pdc.makeBrick(name, val, this);
        inners.put(name, pdcb);
      } else {
        error = "inner is cdcb and therefore unsettable"; //this should not be possible, because putSafe should only be able to be called on pdcb, and therefore, the inner of an outer that was called by a pdcb should always be a pdcb, and if not, an error occurred
      }
    } else {
      error = "name not recognized";
    }
    return Result.make(null, error);
  }

  public Result<DataClassBrick> calc(String name, DCHolder dcHolder) {
    Result r;
    CompoundDataClassBrick outerBrick = getOuter();
    boolean canSet = cdc.checkCanSet(this, outerBrick, dcHolder);
    if(canSet) {
      r = cdc.calcInternal(name, this);
      if (r.getError() != null && outer != null) {
        r = outer.calc(name, dcHolder);
      }
    } else {
      r = Result.make(null, "can't set");
    }
    return r;
  }

  @Override
  public boolean isComplete() {
    int numberOfSetValues = 0;
    for(DataClassBrick inner : inners.values()) {
      if(inner.isComplete()) {
        numberOfSetValues++;
      }
    }
    return numberOfSetValues >= cdc.minimumRequiredSetValues;
  }

  public Result removeInner(String name) {
    DataClassBrick inner = inners.get(name);
    if(inner instanceof PrimitiveDataClassBrick) {
      PrimitiveDataClassBrick innerAsPDCB = (PrimitiveDataClassBrick) inner;
      innerAsPDCB.putDFB(null);
      inners.put(name, innerAsPDCB);
    } else if(inner instanceof CompoundDataClassBrick){
      CompoundDataClassBrick innerAsCDCB = (CompoundDataClassBrick) inner;
      for(String innerInnerName : innerAsCDCB.inners.keySet()) {
        innerAsCDCB.removeInner(innerInnerName);
      }
    }
    return Result.make();
  }

  public CompoundDataClassBrick initInners(HashMap<String, DataClassBrick> cursorPositionDCBInners) {
    inners = cursorPositionDCBInners;
    return this;
  }
}
