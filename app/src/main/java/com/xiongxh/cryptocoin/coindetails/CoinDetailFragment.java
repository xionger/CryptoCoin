package com.xiongxh.cryptocoin.coindetails;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.xiongxh.cryptocoin.R;
import com.xiongxh.cryptocoin.data.CoinDbContract;
import com.xiongxh.cryptocoin.data.CoinLoader;
import com.xiongxh.cryptocoin.model.Coin;
import com.xiongxh.cryptocoin.model.History;
import com.xiongxh.cryptocoin.utilities.CoinJsonUtils;
import com.xiongxh.cryptocoin.utilities.ConstantsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class CoinDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String SYMBOL_LABEL = "SYMBOL";

    private String mSymbol;

    private Cursor mCursor;

    @BindView(R.id.coin_logo)
    ImageView mLogoImageView;
    @BindView(R.id.current_price)
    TextView mPriceTextView;
    @BindView(R.id.change_value)
    TextView mChangeValueTextView;
    @BindView(R.id.change_percent)
    TextView mChangePerTextView;

    @BindView(R.id.tv_open_24h)
    TextView mOpenTextView;
    @BindView(R.id.tv_low_24h)
    TextView mLowTextView;
    @BindView(R.id.tv_high_24h)
    TextView mHighTextView;
    @BindView(R.id.tv_volume_24h)
    TextView mVolumeTextView;
    @BindView(R.id.tv_volume_24h_to)
    TextView mVolumeToTextView;
    @BindView(R.id.tv_mkt_cap)
    TextView mMktCapTextView;
    @BindView(R.id.tv_curr_spply)
    TextView mCurrSupplyTextView;
    @BindView(R.id.tv_max_spply)
    TextView mMaxSupplyTextView;
    @BindView(R.id.tv_algorithm)
    TextView mAlgorithmTextView;
    @BindView(R.id.tv_proof_type)
    TextView mProofTypeTextView;
    @BindView(R.id.tv_sponsor)
    TextView mSponsorTextView;

    @BindView(R.id.chart_historical)
    CandleStickChart mChart;

    private Unbinder unbinder;

    //private OnFragmentInteractionListener mListener;

    public CoinDetailFragment() {
        // Required empty public constructor
    }

    public static CoinDetailFragment newInstance(String symbol) {
        CoinDetailFragment fragment = new CoinDetailFragment();
        Bundle args = new Bundle();
        args.putString(SYMBOL_LABEL, symbol);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSymbol = getArguments().getString(SYMBOL_LABEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_coin_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    private void bindViews(){
        if (mCursor != null){

            String histoStr = mCursor.getString(ConstantsUtils.POSITION_HISTO);

            if (histoStr != null ) {
                Timber.d("First 500 of histo: " + histoStr.substring(0, 500));

                //mChart.setBackgroundColor(getResources().getColor(R.color.colorBlueGrey800));
                mChart.setBackgroundColor(Color.WHITE);
                mChart.getLegend().setEnabled(false);
                mChart.setMaxVisibleValueCount(30);
                mChart.setPinchZoom(false);
                mChart.setDrawGridBackground(false);
                XAxis xAxis = mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
                //xAxis.setValueFormatter(iAxisValueFormatter);
                YAxis leftAxis = mChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);

                CandleData histoCandleData = getCandleData(histoStr);

                mChart.setData(histoCandleData);
                mChart.invalidate();
            }

            String newsStr = mCursor.getString(ConstantsUtils.POSITION_NEWS);
            if (newsStr != null){
                Timber.d("news string: " + newsStr);
            }

            mPriceTextView.setText("$" + mCursor.getString(ConstantsUtils.POSITION_PRICE));
            mChangeValueTextView.setText(mCursor.getString(ConstantsUtils.POSITION_CHANGE));
            mChangePerTextView.setText(mCursor.getString(ConstantsUtils.POSITION_TREND) + "%");

            //image here
            mOpenTextView.setText(mCursor.getString(ConstantsUtils.POSITION_OPEN24H));
            mLowTextView.setText(mCursor.getString(ConstantsUtils.POSITION_LOW24H));
            mHighTextView.setText(mCursor.getString(ConstantsUtils.POSITION_HIGH24H));
            mVolumeTextView.setText(mCursor.getString(ConstantsUtils.POSITION_VOL24H));
            mVolumeToTextView.setText(mCursor.getString(ConstantsUtils.POSITION_VOL24H2));
            mMktCapTextView.setText(mCursor.getString(ConstantsUtils.POSITION_MKTCAP));
            mAlgorithmTextView.setText(mCursor.getString(ConstantsUtils.POSITION_ALGORITHM));
            mProofTypeTextView.setText(mCursor.getString(ConstantsUtils.POSITION_PROOF_TYPE));

            int sponsor = Integer.valueOf(mCursor.getString(ConstantsUtils.POSITION_SPONSOR));
            if (sponsor == 1){
                mSponsorTextView.setText("Yes");
            }else {
                mSponsorTextView.setText("No");
            }

            String currSup = mCursor.getString(ConstantsUtils.POSITION_SUPPLY);
            if (currSup != null && !currSup.isEmpty() && !currSup.equals("0")){
                mCurrSupplyTextView.setText(currSup);
            }else {
                mCurrSupplyTextView.setText("N/A");
            }

            String maxSup = mCursor.getString(ConstantsUtils.POSITION_TOTAL_SUPPLY);

            if (maxSup != null && !maxSup.isEmpty() && !maxSup.equals("0")){
                mMaxSupplyTextView.setText(maxSup);
            }else {
                mMaxSupplyTextView.setText("N/A");
            }

        }
    }

    private CandleData getCandleData(String histoJsonStr){

        ArrayList<CandleEntry> histoEntries = new ArrayList<CandleEntry>();
        try {
            JSONObject baseJsonObject = new JSONObject(histoJsonStr);
            JSONArray histoArray = baseJsonObject.getJSONArray("Data");

            for (int i = 0; i < histoArray.length(); i++){
                JSONObject histoObject = histoArray.getJSONObject(i);

                History history = new History();


                CandleEntry histoEntry = new CandleEntry(
                        (float) histoObject.getDouble("time"),
                        (float) histoObject.getDouble("high"),
                        (float) histoObject.getDouble("low"),
                        (float) histoObject.getDouble("open"),
                        (float) histoObject.getDouble("close")
                );

                histoEntries.add(histoEntry);
            }
        }catch (JSONException e){
            e.getStackTrace();
        }

        CandleDataSet set1 = new CandleDataSet(histoEntries, "Data Set");

        //set1.setDrawIcons(true);
        //set1.setAxisDependency(AxisDependency.LEFT);
        //set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(Color.DKGRAY);
        set1.setShadowWidth(0.7f);
        set1.setDecreasingColor(Color.RED);
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(Color.rgb(122, 242, 84));
        set1.setIncreasingPaintStyle(Paint.Style.STROKE);
        set1.setNeutralColor(Color.BLUE);

        CandleData data = new CandleData(set1);

        return data;
    }

//    private final IAxisValueFormatter iAxisValueFormatter = new IAxisValueFormatter() {
//        @Override
//        public String getFormattedValue(float value, AxisBase axis) {
//            return labels.get((int) value);
//        }
//
//        @Override
//        public int getDecimalDigits() {
//            return 0;
//        }
//    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return CoinLoader.newInstanceForCoinSymbol(getActivity(), mSymbol);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursor = cursor;
        if (mCursor != null && mCursor.moveToFirst()){
            Timber.d("Successfully loaded data for: " + mSymbol);
            bindViews();
        }else {
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

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
