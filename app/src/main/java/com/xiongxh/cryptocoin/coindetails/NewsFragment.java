package com.xiongxh.cryptocoin.coindetails;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiongxh.cryptocoin.R;
import com.xiongxh.cryptocoin.data.CoinLoader;
import com.xiongxh.cryptocoin.model.News;
import com.xiongxh.cryptocoin.utilities.CoinJsonUtils;
import com.xiongxh.cryptocoin.utilities.ConstantsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;


public class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String SYMBOL_LABEL = "SYMBOL";

    private String mSymbol;
    private Cursor mCursor;
    private Context mContext;
    private NewsAdapter mNewsAdapter;

    @BindView(R.id.list_news)
    ListView mNewsListView;
    @BindView(R.id.news_empty_view)
    TextView mEmptyTextView;
    @BindView(R.id.loading_indicator)
    View loadingIndicator;

    private Unbinder unbinder;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(String symbol) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(SYMBOL_LABEL, symbol);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSymbol = getArguments().getString(SYMBOL_LABEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        mNewsAdapter = new NewsAdapter(getContext(), new ArrayList<News>());

        mNewsListView.setAdapter(mNewsAdapter);


        mNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){

                News currentNews = mNewsAdapter.getItem(position);
                Uri NewsUri = Uri.parse(currentNews.getNewsUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, NewsUri);

                startActivity(websiteIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return CoinLoader.newInstanceForCoinSymbol(getActivity(), mSymbol);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        mNewsAdapter.clear();

        loadingIndicator.setVisibility(View.VISIBLE);
        mEmptyTextView.setText(R.string.no_news);

        mCursor = cursor;
        if (mCursor != null && mCursor.moveToFirst()){
            Timber.d("Successfully loaded data for: " + mSymbol);
            mEmptyTextView.setVisibility(View.INVISIBLE);
            loadingIndicator.setVisibility(View.INVISIBLE);

            String newsJsonStr = mCursor.getString(ConstantsUtils.POSITION_NEWS);
            List<News> newses = CoinJsonUtils.extractNewsFromJson(newsJsonStr);

            if(newses != null) {
                mNewsAdapter.addAll(newses);
            }
        }else {
            mEmptyTextView.setVisibility(View.VISIBLE);

            mCursor.close();
            mCursor = null;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }

}
