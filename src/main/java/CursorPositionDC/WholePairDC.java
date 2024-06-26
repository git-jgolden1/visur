package CursorPositionDC;

import DataClass.*;

import java.util.HashMap;

public class WholePairDC extends CompoundDataClass {
  public WholePairDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public DataClassBrick makeBrick() {
    return null;
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    HashMap<String, DataClassBrick> cxcyDCBInners = new HashMap<>();
    CompoundDataClassBrick cxcyDCB = CompoundDataClassBrick.make(name, outer, this, cxcyDCBInners);
    PrimitiveDataClass wholeNumberDC = (PrimitiveDataClass) getInner("wholeNumber");
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick) wholeNumberDC.makeBrick("cx", cxcyDCB);
    cxcyDCBInners.put("cx", cxDCB);
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) wholeNumberDC.makeBrick("cy", cxcyDCB);
    cxcyDCBInners.put("cy", cyDCB);
    return cxcyDCB.initInners(cxcyDCBInners);
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick) {
    return null;
  }

  @Override
  public boolean conflictsCheck(CompoundDataClassBrick brick, String targetName, Object targetVal) {
    return false;
  }

}
