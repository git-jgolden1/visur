package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;

public class LLCYAndNLDC extends CompoundDataClass {
  public LLCYAndNLDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    CompoundDataClassBrick cyAndNLDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> cyAndNLInners = new HashMap<>();

    PrimitiveDataClassBrick nlDCB = reusablePDCBs[0];
    PrimitiveDataClassBrick cyDCB = reusablePDCBs[1];
    PrimitiveDataClassBrick llDCB = reusablePDCBs[2];

    nlDCB.putOuter(cyAndNLDCB);
    cyDCB.putOuter(cyAndNLDCB);
    llDCB.putOuter(cyAndNLDCB);

    cyAndNLInners.put("nl", nlDCB);
    cyAndNLInners.put("cy", cyDCB);
    cyAndNLInners.put("ll", llDCB);

    return cyAndNLDCB.getInitializedBrickFromInners(cyAndNLInners);
  }

  @Override
  public Result<Object> calcInternal(String name, OuterDataClassBrick thisAsBrick) {
    return null;
  }
}
