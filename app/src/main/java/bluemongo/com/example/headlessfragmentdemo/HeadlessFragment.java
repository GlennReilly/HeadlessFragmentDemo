package bluemongo.com.example.headlessfragmentdemo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;


public class HeadlessFragment extends Fragment  {
    private static final String TAG_HEADLESS = HeadlessFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private int totalCount;
    private TaskCallback mTaskCallbacks; //used by the task to communicate back to this fragment
    private TaskThing taskThing;

    public void incrementTotalCount(){
        totalCount++;
        if(mListener != null) {
            mListener.updateTotalCountDisplay(totalCount);
        }
    }

    public void resetTotalCount(){
        totalCount = 0;
    }

    public void restartTaskThing(){
        taskThing.cancel(true);
        taskThing = new TaskThing();
        taskThing.execute();
        Log.i(TAG_HEADLESS, "taskThing restarted");
    }

    public void stopTaskThing(){
        taskThing.cancel(true);
        Log.i(TAG_HEADLESS, "taskThing stopped");
    }

    public int getCurrentCount() {
        return totalCount;
    }

    public HeadlessFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        taskThing = new TaskThing();
        taskThing.execute();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            mTaskCallbacks = (TaskCallback) context;
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
            mTaskCallbacks = (TaskCallback) context;
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


    private class TaskThing extends AsyncTask<Void, Integer, Void>
    {
        private final String TAG_TASK_THING = TaskThing.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG_TASK_THING, "task starting");
            for (int i=0; i<100; i++){
                if (!isCancelled()) {
                    SystemClock.sleep(300);
                    publishProgress(i);
                }else{
                    Log.i(TAG_TASK_THING, "task cancelled");
                    return null;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... percentComplete) {
            if (mTaskCallbacks != null) {
                mTaskCallbacks.onProgressUpdate(percentComplete[0]);
            }
        }

        @Override
        protected void onCancelled() {
            if (mTaskCallbacks != null) {
                mTaskCallbacks.onCancelled();
            }
        }

        @Override
        protected void onPostExecute(Void ignore) {
            if (mTaskCallbacks != null) {
                mTaskCallbacks.onPostExecute();
            }
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void updateTotalCountDisplay(int totalCount);
        int getCurrentCount();
        void resetTotalCount();
        void restartTaskThing();
        void stopTaskThing();
    }
}
