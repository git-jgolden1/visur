package com.ple.visur;

import CursorPositionDC.*;
import DataClass.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestDC {
  CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();
  @Test void dcConstrainsDFs() {
    CompoundDataClassBrick cursorPosDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick(cursorPositionDCHolder, null);
    PrimitiveDataClassBrick aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(-1, cursorPosDCB);

    assertNull(aDCB);

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(0, cursorPosDCB);

    assertEquals(0, aDCB.getVal().getVal());

    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cursorPositionDCHolder, cursorPosDCB);
    cursorPosDCB.putInner("a", aDCB);
    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(-1, cxcyDCB);
    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(2, cxcyDCB);
    cxcyDCB.putInner("cx", cxDCB);
    cxcyDCB.putInner("cy", cyDCB);

    assertNull(cxDCB);

    assertEquals(2, cyDCB.getVal().getVal());

    long stupidlyLongNumber = 9876543210L;

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(stupidlyLongNumber, cursorPosDCB);

    assertNull(aDCB);

    cursorPosDCB.putInner("cxcy", cxcyDCB);
  }

  @Test void cdcbCalculateInner() {

  }
  @Test void calculateInnerFailsWithNotEnoughInfo() {

  }

}
