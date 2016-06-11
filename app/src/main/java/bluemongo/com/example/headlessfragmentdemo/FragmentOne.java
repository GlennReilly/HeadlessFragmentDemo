package bluemongo.com.example.headlessfragmentdemo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class FragmentOne extends Fragment {

    private static final String TAG_FRAG_ONE = FragmentOne.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private TextView tvTotalCount;
    private Button buttonUpdateCount;
    private Button buttonRotateDisplay;
    private Button buttonRestart;
    private Button buttonStop;
    private ProgressBar progressBar;

    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewRoot = inflater.inflate(R.layout.fragment_one, container, false);
        tvTotalCount = (TextView) viewRoot.findViewById(R.id.tvTotalCount);
        if (mListener != null) {
            tvTotalCount.setText(String.valueOf(mListener.getCurrentCount()));
        }
        progressBar = (ProgressBar) viewRoot.findViewById(R.id.progressbar);
        progressBar.setIndeterminate(false);

        buttonUpdateCount = (Button)viewRoot.findViewById(R.id.btnUpdateCount);
        buttonUpdateCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doButtonClick();
            }
        });

        buttonRotateDisplay = (Button)viewRoot.findViewById(R.id.btnRotateDisplay);
        buttonRotateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG_FRAG_ONE, "rotate button clicked");
                if(getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    Log.i(TAG_FRAG_ONE, "rotating to landscape.");
                }else{
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    Log.i(TAG_FRAG_ONE, "rotating to portrait.");
                }
            }
        });

        buttonRestart = (Button) viewRoot.findViewById(R.id.btnRestart);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRestart();
            }
        });

        buttonStop = (Button)viewRoot.findViewById(R.id.btnStop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStop();
            }
        });

        return viewRoot;
    }

    private void doStop() {
        mListener.stopTaskThing();
        progressBar.setProgress(0);
        mListener.resetTotalCount();
        tvTotalCount.setText(String.valueOf(0));
    }

    private void doRestart() {
        mListener.restartTaskThing();
        progressBar.setProgress(0);
        mListener.resetTotalCount();
        tvTotalCount.setText(String.valueOf(0));
    }

    private void doButtonClick() {
        if(mListener != null) {
            mListener.onButtonClickToHeadlessFragment();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateTotalCountDisplay(int totalCount) {
        if (tvTotalCount != null) {
            tvTotalCount.setText(String.valueOf(totalCount));
        }
    }

    public void updateProgressBar(int percent) {
        progressBar.setProgress(percent);
    }


    public interface OnFragmentInteractionListener {
        void onButtonClickToHeadlessFragment();
        void resetTotalCount();
        int getCurrentCount();
        void restartTaskThing();
        void stopTaskThing();
    }
}
