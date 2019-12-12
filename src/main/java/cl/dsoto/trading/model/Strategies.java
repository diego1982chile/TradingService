package cl.dsoto.trading.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Andrés Farías on 8/23/16.
 */
public class Strategies implements Serializable {

    static final String GlobalExtremaStrategy = "GlobalExtremaStrategy";
    static final String CCICorrectionStrategy = "CCICorrectionStrategy";
    static final String MovingMomentumStrategy = "MovingMomentumStrategy";
    static final String RSI2Strategy = "RSI2Strategy";
    static final String MACDStrategy = "MACDStrategy";
    static final String StochasticStrategy = "StochasticStrategy";
    static final String ParabolicSARStrategy = "ParabolicSARStrategy";
    static final String MovingAveragesStrategy = "MovingAveragesStrategy";
    static final String BagovinoStrategy = "BagovinoStrategy";
    static final String FXBootCampStrategy = "FXBootCampStrategy";
    static final String TunnelStrategy = "TunnelStrategy";
    static final String WinslowStrategy = "WinslowStrategy";

    static final int GlobalExtremaStrategyId = 0;
    static final int CCICorrectionStrategyId = 1;
    static final int MovingMomentumStrategyId = 2;
    static final int RSI2StrategyId = 3;
    static final int MACDStrategyId = 4;
    static final int StochasticStrategyId = 5;
    static final int ParabolicSARStrategyId = 6;
    static final int MovingAveragesStrategyId = 7;
    static final int BagovinoStrategyId = 8;
    static final int FXBootCampStrategyId = 9;
    static final int TunnelStrategyId = 10;
    static final int WinslowStrategyId = 11;

}

