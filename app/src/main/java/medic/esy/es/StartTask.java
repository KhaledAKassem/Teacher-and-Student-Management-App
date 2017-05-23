package medic.esy.es;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class StartTask extends AppCompatActivity {

    TextView qu, An_1, An_2, An_3, result;
    ArrayList<String> listQu = new ArrayList<>();
    ArrayList<String> listAn = new ArrayList<>();
    ArrayList<String> listShowq = new ArrayList<>();
    int AnNUM, ReNUM;
    public String n;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_task);

        n = getIntent().getStringExtra("name2");
        qu = (TextView) findViewById(R.id.textView_qu);
        An_1 = (TextView) findViewById(R.id.textView_an1);
        An_2 = (TextView) findViewById(R.id.textView_an2);
        An_3 = (TextView) findViewById(R.id.textView_an3);
        result = (TextView) findViewById(R.id.textView_result);


        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        StorageReference islandRefQues = storageRef.child("files/"+n+"t/askques.txt");
        StorageReference islandRefAns = storageRef.child("files/"+n+"/taskans.txt");



        islandRefQues.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                downloadProgress(uri);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });

        islandRefAns.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {


                downloadProgress(uri);

                mProgressDialog.show();

                solveTask();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });



    }

    public void downloadProgress(Uri uri){
        mProgressDialog = new ProgressDialog(StartTask .this);
        mProgressDialog.setMessage("file is start downloading");
        mProgressDialog.setTitle("File Download ");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

// execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(StartTask.this);
        downloadTask.execute(uri.toString());

    }


    public void solveTask(){

        try {

            FileInputStream fin = openFileInput("taskans.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fin);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                listQu.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fin = openFileInput("taskques.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fin);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                listAn.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listShowq.add("");
        ShowTXT();
    }

    public void ShowTXT() {
        if (listShowq.size() < listQu.size()) {
            Random R1 = new Random();
            int Rnd_Qu = R1.nextInt(listQu.size());
            String Qus = listQu.get(Rnd_Qu);
            Boolean a = true;
            for (int i = 0; i < listShowq.size(); i++) {
                if (Qus.equals(listShowq.get(i))) {
                    ShowTXT();
                    a = false;
                    listShowq.add(listQu.get(Rnd_Qu));
                    break;
                }
            }
            if (a) {
                qu.setText(Qus);
                Toast.makeText(StartTask.this, listAn.get(Rnd_Qu), Toast.LENGTH_SHORT).show();

                String[] Aan = listAn.get(Rnd_Qu).split(",");
                An_1.setText(Aan[0]);
                An_2.setText(Aan[1]);
                An_3.setText(Aan[2]);
                Toast.makeText(StartTask.this, Aan.length+"", Toast.LENGTH_SHORT).show();
                AnNUM = Integer.parseInt(Aan[3]);
                listShowq.add(listQu.get(Rnd_Qu));
            }
        }else{
            Toast.makeText(StartTask.this, "انتهت الأسئلة", Toast.LENGTH_SHORT).show();
            qu.setText("");
            An_1.setText("");
            An_2.setText("");
            An_3.setText("");
        }
    }
    public void textView_an1(View view) {
        if (AnNUM == 1) {
            Toast.makeText(StartTask.this, "الاجابة صحيحة", Toast.LENGTH_SHORT).show();
            ReNUM++;
            result.setText("الاجابات الصحيحة : " + ReNUM);
            ShowTXT();
        } else {
            Toast.makeText(StartTask.this, "الاجابة خاطئة", Toast.LENGTH_SHORT).show();
            ShowTXT();
        }
    }
    public void textView_an2(View view) {
        if (AnNUM == 2) {
            Toast.makeText(StartTask.this, "الاجابة صحيحة", Toast.LENGTH_SHORT).show();
            ReNUM++;
            result.setText("الاجابات الصحيحة : " + ReNUM);
            ShowTXT();
        } else {
            Toast.makeText(StartTask.this, "الاجابة خاطئة", Toast.LENGTH_SHORT).show();
            ShowTXT();
        }
    }
    public void textView_an3(View view) {
        if (AnNUM == 3) {
            Toast.makeText(StartTask.this, "الاجابة صحيحة", Toast.LENGTH_SHORT).show();
            ReNUM++;
            result.setText("الاجابات الصحيحة : " + ReNUM);
            ShowTXT();
        } else {
            Toast.makeText(StartTask.this, "الاجابة خاطئة", Toast.LENGTH_SHORT).show();
            ShowTXT();
        }
    }







 class DownloadTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private PowerManager.WakeLock mWakeLock;

    public DownloadTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        FileOutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            int fileLength = connection.getContentLength();
// get file name;
            String[] filen=sUrl[0].split("/");
            // download the file
            input = connection.getInputStream();


            output = openFileOutput(filen[filen.length-1],MODE_WORLD_READABLE);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {

                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;

                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
        mWakeLock.acquire();

    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();
        mProgressDialog.dismiss();
        if (result != null)
            Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
    }
}
}
