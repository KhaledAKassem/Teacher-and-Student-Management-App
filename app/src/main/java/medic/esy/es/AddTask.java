package medic.esy.es;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TextInputLayout addQuestionInput,firstChooseInput,secondChooseInput,thirdChooseInput;
    EditText addQuestionText,firstChooseText,secondChooseText,thirdChooseText;
    Spinner spinnerChooseCorrectAnswer;

    int correctAnswer = 1;
    public String n=null;
    String ques="",ans="";

    File file = null;
    FileOutputStream fileOutputStreamQues = null;
    FileOutputStream fileOutputStreamAns = null;

    StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

         n = getIntent().getStringExtra("name");





        addQuestionInput = (TextInputLayout) findViewById(R.id.addQuestionInput);
        firstChooseInput = (TextInputLayout) findViewById(R.id.firstChooseInput);
        secondChooseInput = (TextInputLayout) findViewById(R.id.secondChooseInput);
        thirdChooseInput = (TextInputLayout) findViewById(R.id.thirdChooseInput);


        addQuestionText = (EditText) findViewById(R.id.addQuestionText);
        firstChooseText = (EditText) findViewById(R.id.firstChooseText);
        secondChooseText = (EditText) findViewById(R.id.secondChooseText);
        thirdChooseText = (EditText) findViewById(R.id.thirdChooseText);


        spinnerChooseCorrectAnswer = (Spinner) findViewById(R.id.spinnerChooseCorrectAnswer);
        spinnerChooseCorrectAnswer.setOnItemSelectedListener(this);


        // Spinner Drop down elements
        List<String> num = new ArrayList<String>();
        num.add("1");
        num.add("2");
        num.add("3");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, num);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerChooseCorrectAnswer.setAdapter(dataAdapter);

        mStorage = FirebaseStorage.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();

        try {

            file = getFilesDir();
            fileOutputStreamQues = openFileOutput("taskques.txt", Context.MODE_PRIVATE);
            fileOutputStreamAns = openFileOutput("taskans.txt", Context.MODE_PRIVATE);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean validQuestion() {
        String question = addQuestionText.getText().toString().trim();
        String first = firstChooseText.getText().toString().trim();
        String second = secondChooseText.getText().toString().trim();
        String third = thirdChooseText.getText().toString().trim();

        if (question.length() < 1){
            addQuestionInput.setError("You Should Enter Question");
            return false;
        }
        if (first.length() < 1){
            firstChooseText.setError("You Should Enter First Choose");
            return false;
        }
        if (second.length() < 1){
            secondChooseText.setError("You Should Enter Second Choose");
            return false;
        }
        if (third.length() < 1){
            thirdChooseText.setError("You Should Enter Third Choose");
            return false;
        }



        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        correctAnswer = i+1;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void addQuestion(View view) {
        ques = null;
        ans = null;
        if (validQuestion()){
            ques =firstChooseText.getText().toString()+","+ secondChooseText.getText().toString()
        +","+thirdChooseText.getText().toString()+","+correctAnswer+"\n";
            ans =  addQuestionText.getText().toString()+"\n";
            try {
                fileOutputStreamQues.write(ques.getBytes());
                fileOutputStreamAns.write(ans.getBytes());

                addQuestionText.setText("");
                firstChooseText.setText("");
                secondChooseText.setText("");
                thirdChooseText.setText("");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void UploadTask(View view) {
        try {
            fileOutputStreamQues.close();
            fileOutputStreamAns.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        uploadFileQues();
        uploadFileAns();

//
//        Intent i = new Intent(AddTask.this,StartTask.class);
//        startActivity(i);

        file.delete();

    }

    private void uploadFileQues() {
        //if there is a file to upload
//        StorageReference filePath = mStorage.child("files").child("test");

            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            Uri uri = Uri.fromFile(new File(file+"/taskques.txt"));
            StorageReference riversRef = mStorage.child("files/"+n+"/"+uri.getLastPathSegment());

// Register observers to listen for when the download is done or if it fails
            riversRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //if the upload is successfull
                    //hiding the progress dialog
                    progressDialog.dismiss();

                    //and displaying a success toast
                    Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });

            Toast.makeText(getBaseContext(), "Success Upload", Toast.LENGTH_SHORT).show();

    }


    private void uploadFileAns() {
        //if there is a file to upload
//        StorageReference filePath = mStorage.child("files").child("test");

        //displaying a progress dialog while upload is going on
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

//            InputStream stream = null;
//            try {
//                stream = new FileInputStream(new File("task.txt"));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }

        Uri uri = Uri.fromFile(new File(file+"/taskans.txt"));
        StorageReference riversRef = mStorage.child("files/" +n+"/"+ uri.getLastPathSegment());


// Register observers to listen for when the download is done or if it fails
        riversRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //if the upload is successfull
                //hiding the progress dialog
                progressDialog.dismiss();

                //and displaying a success toast
                Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //if the upload is not successfull
                        //hiding the progress dialog
                        progressDialog.dismiss();

                        //and displaying error message
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //calculating progress percentage
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        //displaying percentage in progress dialog
                        progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                    }
                });

        Toast.makeText(getBaseContext(), "Success Upload", Toast.LENGTH_SHORT).show();

    }


}
