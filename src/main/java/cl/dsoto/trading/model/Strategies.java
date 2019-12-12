package cl.dsoto.trading.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Andrés Farías on 8/23/16.
 */
public class Strategies implements Serializable {

    public static final String GlobalExtremaStrategy = "GlobalExtremaStrategy";
    public static final String CCICorrectionStrategy = "CCICorrectionStrategy";
    public static final String MovingMomentumStrategy = "MovingMomentumStrategy";
    public static final String RSI2Strategy = "RSI2Strategy";
    public static final String MACDStrategy = "MACDStrategy";
    public static final String StochasticStrategy = "StochasticStrategy";
    public static final String ParabolicSARStrategy = "ParabolicSARStrategy";
    public static final String MovingAveragesStrategy = "MovingAveragesStrategy";
    public static final String BagovinoStrategy = "BagovinoStrategy";
    public static final String FXBootCampStrategy = "FXBootCampStrategy";
    public static final String TunnelStrategy = "TunnelStrategy";
    public static final String WinslowStrategy = "WinslowStrategy";

    public static final int CCICorrectionStrategyId = 0;
    public static final int GlobalExtremaStrategyId = 1;
    public static final int MovingMomentumStrategyId = 2;
    public static final int RSI2StrategyId = 3;
    public static final int MACDStrategyId = 4;
    public static final int StochasticStrategyId = 5;
    public static final int ParabolicSARStrategyId = 6;
    public static final int MovingAveragesStrategyId = 7;
    public static final int BagovinoStrategyId = 8;
    public static final int FXBootCampStrategyId = 9;
    public static final int TunnelStrategyId = 10;
    public static final int WinslowStrategyId = 11;

}

