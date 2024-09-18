package com.HouseMovers.UserApp.Dashboard.HomeDashboardFragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.HouseMovers.UserApp.R;
import com.skydoves.elasticviews.ElasticButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements View.OnClickListener {


    private CircleImageView profileImage;
    private EditText firstName,lastName,number,address,userWasite,email,userShirtSize;


    private ElasticButton updateBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String userId;
    private ImageView back;
    private String firstNameS,lastNameS,userEmail,userEmployment,
            userImage,userPhone;
    private AwesomeValidation awesomeValidation;
    private TextView mobile,name,dateOfBirth;
    private RadioButton maleRadio, femaleRadio, otherRadio;
    private String userWasiteS= "", userShirtSizeS ="",userAddress ="",titleS = "", nationalityS ="",crushNameS ="",userBioS ="";


    static int PICK_REQUEST = 101;
    private Uri resultUri;

    private Animation animBlink;

    final Calendar[] calendar = new Calendar[1];
    final DatePickerDialog[] datePickerDialog = new DatePickerDialog[1];
    String orderDate="", genderType = "", accessToken;


    private View view;
    private Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);


        // firebase Reference
        mAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser().getUid());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);


        // initialization
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        email = view.findViewById(R.id.email);
        number = view.findViewById(R.id.number);
        address = view.findViewById(R.id.userAddress);
        profileImage = view.findViewById(R.id.profile_photo);
        updateBtn = view.findViewById(R.id.updateBtn);
        mobile = view.findViewById(R.id.mobile);
        name = view.findViewById(R.id.name);


        maleRadio = view.findViewById(R.id.radio1);
        femaleRadio = view.findViewById(R.id.radio2);
        otherRadio = view.findViewById(R.id.radio3);












        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {

                    if (dataSnapshot.hasChild("image"))
                    {
                        userImage = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                        // Picasso.with(EditProfileActivity.this).load(imageDb).placeholder(R.drawable.men).fit().centerCrop().into(userProfile);

                        if (mActivity == null) {
                            return;
                        }
                        Glide.with(mActivity).load(userImage).into(profileImage);
                    }

                    firstNameS=Objects.requireNonNull(dataSnapshot.child("firstName").getValue().toString());
                    lastNameS=Objects.requireNonNull(dataSnapshot.child("lastName").getValue().toString());
                    userPhone=Objects.requireNonNull(dataSnapshot.child("mobile").getValue().toString());
                    userEmail=Objects.requireNonNull(dataSnapshot.child("email").getValue().toString());

                    if (dataSnapshot.hasChild("address"))
                    {
                        userAddress = dataSnapshot.child("address").getValue().toString();
                    }


                    if (dataSnapshot.hasChild("gender"))
                    {
                        genderType = dataSnapshot.child("gender").getValue().toString();
                    }




                    name .setText(firstNameS+lastNameS);
                    mobile.setText(userPhone);
                    firstName.setText(firstNameS);
                    lastName.setText(lastNameS);
                    number.setText(userPhone);
                    email.setText(userEmail);
                    address.setText(userAddress);





                    if (genderType.equals("Male"))
                    {
                        maleRadio.isChecked();
                    }
                    if (genderType.equals("Female"))
                    {
                        femaleRadio.isChecked();
                    }
                    if (genderType.equals("Other"))
                    {
                        otherRadio.isChecked();
                    }




                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // set on click listeners
        profileImage.setOnClickListener(this);
        updateBtn.setOnClickListener(this);






        return  view;




    }


    @Override
    public void onClick(View v) {
        if (v == profileImage)
        {
            checkAndroidVersion();
        }
        if (v == updateBtn)
        {
            updateUserProfile();
        }

    }

    private void updateUserProfile() {

        userAddress = address.getText().toString();


        if (maleRadio.isChecked()) {
            genderType = maleRadio.getText().toString();


        }

        if (femaleRadio.isChecked()) {
            genderType = femaleRadio.getText().toString();


        }
        if (otherRadio.isChecked()) {
            genderType = otherRadio.getText().toString();

        }

        //progressDialog
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Update....",
                "Please Wait...", false, false);
        Map<String, Object> updateInfo = new HashMap<String, Object>();
        updateInfo.put("firstName", firstName.getText().toString());
        updateInfo.put("lastName", lastName.getText().toString());
        updateInfo.put("mobile", number.getText().toString());
        updateInfo.put("address", userAddress);
        updateInfo.put("gender", genderType);

        if (resultUri != null) {

            final StorageReference filePath = FirebaseStorage.getInstance().getReference().child("Users").child(resultUri.getLastPathSegment());
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
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
                            Map<String, Object> newImage = new HashMap<String, Object>();
                            newImage.put("image", uri.toString());
                            databaseReference.updateChildren(newImage).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "update successfully", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();



                        }
                    });
                }
            });
        } else {

        }


        databaseReference.updateChildren(updateInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), " update successfully", Toast.LENGTH_SHORT).show();
            }
        });




    }

    public void checkAndroidVersion() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 555);
            } catch (Exception e) {


            }
        } else {
            pickImage();
        }

    }



    public void pickImage() {
        CropImage.startPickImageActivity(getContext(), this);
    }

    private void croprequest(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(getContext(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        //RESULT FROM SELECTED IMAGE
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(getContext(), data);
            croprequest(imageUri);
        }

        //RESULT FROM CROPING ACTIVITY
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), result.getUri());

                    resultUri = getImageUri(getContext(), bitmap);
//                    ((profileImage)).setImageBitmap(bitmap);
                    Glide.with(this).load(bitmap).into(profileImage);
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
//            selectVideo();
            pickImage();

        } else {

            checkAndroidVersion();

        }
    }






    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    private void doAction() {
        if (mActivity == null) {
            return;
        }
    }





}