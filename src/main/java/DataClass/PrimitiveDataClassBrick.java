package DataClass;


public class PrimitiveDataClassBrick extends DataClassBrick {
  private final PrimitiveDataClass pdc;
  private DataFormBrick dfb;
    private PrimitiveDataClassBrick(String name, DataFormBrick dfb, PrimitiveDataClass pdc, CompoundDataClassBrick outer) {
        super(pdc, outer, name);
        this.pdc = pdc;
        this.dfb = dfb;
    }
    public static PrimitiveDataClassBrick make(String name, DataFormBrick dfb, PrimitiveDataClass pdc, CompoundDataClassBrick outer) {
        return new PrimitiveDataClassBrick(name, dfb, pdc, outer);
    }
    public DataFormBrick getDFB() {
        return dfb;
    }
    public PrimitiveDataClass getPDC() {
      return pdc;
    }

    public Result<DataClassBrick> calc() {
      return getOuter().calc(getName());
    }

  public Result<DataClassBrick> getOrCalc() {
    return outer.getOrCalc(name);
  }

  public Result putSafe(Object val) {
    Result r;
    CompoundDataClassBrick outerDCB = getOuter();
    boolean previousValWasSet = isComplete();
    Object oldVal = null;
    if(previousValWasSet) {
      oldVal = getVal();
    }
    outerDCB.putInner(name, val);
    if(outerDCB.getCDC().conflicts(outerDCB)) {
      if(previousValWasSet) {
        putSafe(oldVal);
      } else {
        putDFB(null);
      }
      r = Result.make(null, "inners conflict");
    } else {
      DataFormBrick newValAsDFB = DataFormBrick.make(getPDC().defaultDF, val);
      putDFB(newValAsDFB);
      r = Result.make();
    }
    return r;
  }

  public Result putForce(Object val) {
    CompoundDataClassBrick outerDCB = getOuter();
    outerDCB.putInner(name, val);
    if(outerDCB.getCDC().conflicts(outerDCB)) {
      String otherName = name.equals("ca") ? "cxcy" : "ca";
      if(otherName.equals("ca")) {
        CompoundDataClassBrick cxcycaDCB = outerDCB.getOuter();
        PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner(otherName);
        caDCB.remove();
        String otherOtherName = name == "cx" ? "cy" : "cx";
        PrimitiveDataClassBrick cxORcyDCB = (PrimitiveDataClassBrick) outerDCB.getInner(otherOtherName);
        cxORcyDCB.remove();
      } else if(otherName.equals("cxcy")) {
        CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) outerDCB.getInner("cxcy");
        cxcyDCB.remove();
      }
    }
    DataFormBrick newValAsDFB = DataFormBrick.make(getPDC().defaultDF, val);
    putDFB(newValAsDFB);
    return Result.make();
  }

  @Override
  public boolean isComplete() {
    return getDFB() != null;
  }

  public Object getVal() {
    return getDFB().getVal();
  }

  public Result<Object> get() {
    Object v = null;
    String error = null;
    if (getDFB() != null) {
      v = getDFB().getVal();
    } else {
      error = "value not set";
    }
    return Result.make(v, error);
  }

  public void putDFB(DataFormBrick newDFB) {
    dfb = newDFB;
  }

}
