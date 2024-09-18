package com.HouseMovers.UserApp.Complain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.HouseMovers.UserApp.Dashboard.HomeDashboardActivity.HomeDashboardActivity;
import com.HouseMovers.UserApp.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ComplainActivity extends AppCompatActivity implements View.OnClickListener   {

    private ImageView image;
    private EditText rollNumber, desicription, urlEtd;
    private Spinner productQuality, productCategory;
    private Button addProductBtn;
    private Uri resultUri;
    static int PICK_REQUEST = 101;
    private FirebaseAuth mAuth;
    private DatabaseReference complainRef, notificationRef,homeworkRef;


    private String stTitle,stDescription,classId,sectionId,subjectId
            ,userId,teacherId,imageHome,title,description,rollNumberS;
    public static final int MY_PERMISSIONS_REQUEST = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        mAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser().getUid());
        complainRef= FirebaseDatabase.getInstance().getReference().child("Complains");


        // initialization
        image = findViewById(R.id.image);
        rollNumber = findViewById(R.id.rollNumber);
        desicription = findViewById(R.id.desicription);
        addProductBtn = findViewById(R.id.addProductBtn);


        // click listeners
        addProductBtn.setOnClickListener(this);
        image.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v==image)
        {
            checkAndroidVersion();
        }
        if (v==addProductBtn)
        {

            stDescription = desicription.getText().toString();
            rollNumberS   = rollNumber.getText().toString();
            if (stDescription.isEmpty() && rollNumberS.isEmpty())
            {
                Toast.makeText(this, "Please Enter Values", Toast.LENGTH_SHORT).show();
            }
            else
            {
                complainSubmit();
            }




        }


    }

    private void complainSubmit() {

        final ProgressDialog progressDialog = ProgressDialog.show(ComplainActivity.this, "Sending....",
                "Please Wait...", false, false);


        //progressDialog

        if (resultUri != null) {

            final StorageReference filePath = FirebaseStorage.getInstance().getReference().child("Complain").child(resultUri.getLastPathSegment());
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filePath.putBytes(data);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    return;
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            final Map<String, Object> addComplain = new HashMap<String, Object>();


                            String pushId = complainRef.push().getKey();
                            addComplain.put("body", stDescription);
                            addComplain.put("name",rollNumberS);
                            addComplain.put("image", uri.toString());
                            addComplain.put("complainUserId",userId);
                            addComplain.put("complainId",pushId);
                            addComplain.put("timestamp", ServerValue.TIMESTAMP);

                            complainRef.child(pushId).setValue(addComplain).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(ComplainActivity.this, "Send", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ComplainActivity.this, HomeDashboardActivity.class));
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ComplainActivity.this, "Error", Toast.LENGTH_SHORT).show();

                                }
                            });

                            return;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            return;
                        }
                    });
                }
            });
        } else {

            final Map<String, Object> addComplain = new HashMap<String, Object>();


            String pushId = complainRef.push().getKey();
            addComplain.put("body", stDescription);
            addComplain.put("name",rollNumberS);
            addComplain.put("complainUserId",userId);
            addComplain.put("complainId",pushId);
            addComplain.put("timestamp", ServerValue.TIMESTAMP);

            complainRef.child(pushId).setValue(addComplain).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(ComplainActivity.this, "Send", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ComplainActivity.this, HomeDashboardActivity.class));
                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ComplainActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }
            });

        }







    }




    public void checkAndroidVersion() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 555);
            } catch (Exception e) {


            }
        } else {
            pickImage();
        }


    }

    public void pickImage() {
        CropImage.startPickImageActivity(this);
    }

    private void croprequest(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start (this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //RESULT FROM SELECTED IMAGE
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(ComplainActivity.this, data);
            croprequest(imageUri);
        }

        //RESULT FROM CROPING ACTIVITY
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(ComplainActivity.this.getContentResolver(), result.getUri());

                    resultUri = getImageUri(ComplainActivity.this, bitmap);
//                    ((profileImage)).setImageBitmap(bitmap);
                    Glide.with(this).load(bitmap).into(image);
//                    getImageResource(profileImage);
//                    Toast.makeText(this, ""+ profileImage.getDrawable().toString(), Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        } else {

            checkAndroidVersion();

        }
    }
}