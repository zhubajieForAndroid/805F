package com.cheanda.a8805.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.cheanda.a8805.R;
import com.cheanda.a8805.activity.ResultActivity;
import com.cheanda.a8805.adapter.ResultListAdapter;
import com.cheanda.a8805.bean.ResultBean;
import com.cheanda.a8805.conf.Constants;
import com.cheanda.a8805.utils.Protocol;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttributeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "BaseFragment";
    private ResultBean mResultBean;
    private View mView;
    private List<ResultBean.ResponseBean> mList = new ArrayList<>();
    private ResultListAdapter mAdapter;

    private String mId;
    public View mError;
    public View mEmpty;
    public View mLoading;
    private Map<String, Object> mParms = new HashMap<>();
    private Protocol mProtocol;
    private List<ResultBean.ResponseBean> mResponse;
    private Handler mHandler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ResultActivity activity = (ResultActivity) getActivity();
        mId = activity.getID();
        mHandler = new Handler();
        mView = inflater.inflate(R.layout.view_attribet, null);
        initView();
        return mView;
    }

    private void initView() {
        if (!TextUtils.isEmpty(Constants.USER_NAME)) {
            loadData();
        }
        mEmpty = mView.findViewById(R.id.empty);
        mError = mView.findViewById(R.id.error);
        mLoading = mView.findViewById(R.id.loading);
        View view = mError.findViewById(R.id.error_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        ListView listView = (ListView) mView.findViewById(R.id.result_listview);
        mAdapter = new ResultListAdapter(getContext(), mList);
        listView.setAdapter(mAdapter);
    }

    private void loadData() {
        mParms.put("tbvehicletypeid", mId);
        mParms.put("faccount", Constants.USER_NAME);
        mParms.put("url", Constants.URLS.RESULT1_URL);
        mProtocol = new Protocol() {
            @Override
            public void errorManage(IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoading.setVisibility(View.GONE);
                        mError.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void parseData(Gson gson, String s) {
                if (s.contains("请求成功")) {
                    mResultBean = gson.fromJson(s, ResultBean.class);
                    mResponse = mResultBean.getResponse();
                    if (mResponse.size() <= 0) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mEmpty.setVisibility(View.VISIBLE);
                                mLoading.setVisibility(View.GONE);
                            }
                        });

                    } else {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mLoading.setVisibility(View.GONE);
                                mAdapter.setDataList(mResponse);
                            }
                        });
                    }
                }else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLoading.setVisibility(View.GONE);
                            mError.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(),"网络异常",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        };
        mProtocol.setParams(mParms);
        mProtocol.loadDataFromNet();
    }


    @Override
    public void onRefresh() {
        if (!TextUtils.isEmpty(Constants.USER_NAME)) {
            loadData();
        }
    }
}
