package com.emicnet.emicallwebapi.createNumberPair.queryFreeNumbers;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.queryFreeNumbers.QueryFreeNumbersResponse;

/**
 * Created by ShengWang on 2017/6/15.
 */

public class QueryFreeNumbersAction extends Action<QueryFreeNumbersResponse> {
    public static final String QUERY_FREE_NUMBERS = "QUERY_FREE_NUMBERS";

    public QueryFreeNumbersAction(String type, QueryFreeNumbersResponse data) {
        super(type, data);
    }
}
