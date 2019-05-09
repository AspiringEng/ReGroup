package com.example.regroup.Profile_package;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.regroup.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Date;

public class MyProfileFragment extends Fragment {

    TextView txtv;

    Button bioButton;
    Button locationButton;
    Button favActivitiesButton;

    ImageView profileIMG;
    String uid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://regroup-7c1c2.appspot.com/");


    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        txtv = view.findViewById(R.id.Name);
        bioButton = view.findViewById(R.id.bioButton);
        locationButton = view.findViewById(R.id.locationButton);
        profileIMG = view.findViewById(R.id.profileIMG);
        favActivitiesButton = view.findViewById(R.id.favoriteActivitiesButton);

        uid = currentFirebaseUser.getUid();

        getName(uid);
        getProfilePic(uid);

        txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment nameInputDialog = new NameInputDialog();
                Bundle bundle = new Bundle();
                bundle.putString("uid", uid);
                nameInputDialog.setArguments(bundle);
                nameInputDialog.show(getFragmentManager(), "nameInputDialog");
            }
        });

        bioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment bioDialog = new BioDialog();
                Bundle bundle = new Bundle();
                bundle.putString("uid", uid);
                bioDialog.setArguments(bundle);
                bioDialog.show(getFragmentManager(), "bioDialog");
            }
        });

        profileIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment profilePictureDialog = new ProfilePictureDialog();
                profilePictureDialog.show(getFragmentManager(), "profilePictureDialog");
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment profileLocationInput = new ProfileLocationInput();
                profileLocationInput.show(getFragmentManager(), "profileLocationInput");
            }
        });

        favActivitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment favoriteActivityInput = new FavoriteActivityInput();
                favoriteActivityInput.show(getFragmentManager(), "favoriteActivityInput");
            }
        });

        return view;
    }

    private String getAge(int year, int month, int day){

        String ageS = "";
        try {
            Calendar dob = Calendar.getInstance();
            Calendar today = Calendar.getInstance();
            dob.set(year, month, day);

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
                age--;
            }

            Integer ageInt = new Integer(age);
            ageS = ageInt.toString();
        } catch (Exception e) {
            return "";
        }

        return ageS;
    }

    public void getName(String uid){
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Date gimimoData = documentSnapshot.getDate("Gimimo data");
                    System.out.println("Gimimo data: " + (gimimoData.getYear() + 1900) + " " + (gimimoData.getMonth() + 1) + " " + gimimoData.getDate());
                    String age = getAge((gimimoData.getYear() + 1900), (gimimoData.getMonth() + 1), gimimoData.getDate());
                    txtv.setText(documentSnapshot.getString("Vardas") + " " + documentSnapshot.getString("Pavarde") + ", " + age);
                }
            }
        });
    }

    public void getProfilePic(String uid){
        final StorageReference imagesRef = storageRef.child("Users_Images/" + uid + ".jpeg");
        final long ONE_MEGABYTE = 1024 * 1024;
        imagesRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                System.out.println("getProfilePic: success");
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profileIMG.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("getProfilePic: failure exception: " + e.toString() + " location: " + storageRef.getName());
            }
        });
    }
}
