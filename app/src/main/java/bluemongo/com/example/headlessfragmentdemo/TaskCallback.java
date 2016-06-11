package bluemongo.com.example.headlessfragmentdemo;

/**
 * Created by glenn on 11/06/16.
 */
public interface TaskCallback {
    void onPreExecute();
    void onProgressUpdate(int percent);
    void onCancelled();
    void onPostExecute();
}

