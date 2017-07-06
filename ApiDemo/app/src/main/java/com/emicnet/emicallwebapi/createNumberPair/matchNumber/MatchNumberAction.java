package com.emicnet.emicallwebapi.createNumberPair.matchNumber;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.matchNumber.MatchNumberResponse;

/**
 * Created by ShengWang on 2017/6/15.
 */

public class MatchNumberAction extends Action<MatchNumberResponse>{
    //type
    public static final String MATCH_NUMBER_PAIR = "MATCH_NUMBER_PAIR";

    public MatchNumberAction(String type, MatchNumberResponse data) {
        super(type, data);
    }
}
