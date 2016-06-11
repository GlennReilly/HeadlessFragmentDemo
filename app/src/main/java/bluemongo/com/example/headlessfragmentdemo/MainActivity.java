package bluemongo.com.example.headlessfragmentdemo;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity
        implements FragmentOne.OnFragmentInteractionListener,
                    HeadlessFragment.OnFragmentInteractionListener,
                    TaskCallback{
    private static final String TAG_HEADLESS_FRAGMENT = "TAG_HEADLESS_FRAGMENT";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private HeadlessFragment headlessFragment;
    private FragmentOne fragmentOne;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentOne = new FragmentOne();
        fragmentTransaction.replace(R.id.container, fragmentOne);
        fragmentTransaction.addToBackStack(null);

        if( getFragmentManager().findFragmentByTag(TAG_HEADLESS_FRAGMENT) == null) {
            headlessFragment = new HeadlessFragment();
            fragmentTransaction.add(headlessFragment, TAG_HEADLESS_FRAGMENT);
            Log.i(LOG_TAG, "New headlessFragment created in onCreate.");
        }
        else{
            headlessFragment = (HeadlessFragment)getFragmentManager().findFragmentByTag(TAG_HEADLESS_FRAGMENT);
            Log.i(LOG_TAG, "HeadlessFragment already exists in onCreate.");
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void updateTotalCountDisplay(int totalCount) {
        if (fragmentOne != null) {
            fragmentOne.updateTotalCountDisplay(totalCount);
        }
    }

    @Override
    public int getCurrentCount() {
        int returnVal = 0;
        if (headlessFragment != null) {
            returnVal = headlessFragment.getCurrentCount();
        }
        return returnVal;
    }

    @Override
    public void resetTotalCount() {
        if (headlessFragment != null) {
            headlessFragment.resetTotalCount();
        }
    }

    @Override
    public void restartTaskThing() {
        if (headlessFragment != null) {
            headlessFragment.restartTaskThing();
        }
    }

    @Override
    public void stopTaskThing() {
        if (headlessFragment != null) {
            headlessFragment.stopTaskThing();
        }
    }

    @Override
    public void onButtonClickToHeadlessFragment() {
        if(headlessFragment != null){
            headlessFragment.incrementTotalCount();
        }
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(int percent) {
        if (fragmentOne != null) {
            fragmentOne.updateProgressBar(percent);
        }
    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPostExecute() {

    }
}
