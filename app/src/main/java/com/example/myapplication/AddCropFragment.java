package com.example.myapplication;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCropFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCropFragment extends Fragment {
    ImageView cropImg;
    EditText name,desc,categories,qty,price;
    Button addimg,addcrop;
    DatabaseReference cropDbRef;
    Uri uri;
    String imageUrl;
    FirebaseDatabase database;
    DatabaseReference reference;
    View RootView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public AddCropFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCropFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCropFragment newInstance(String param1, String param2) {
        AddCropFragment fragment = new AddCropFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //icon=view.findViewById(R.id.productIcon);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_crop, container, false);
         RootView = inflater.inflate(R.layout.fragment_add_crop, container, false);
        name=RootView.findViewById(R.id.title);
        desc=RootView.findViewById(R.id.description);
        categories=RootView.findViewById(R.id.category);
        qty=RootView.findViewById(R.id.quantity);
        price=RootView.findViewById(R.id.price);
        addimg=RootView.findViewById(R.id.addimg);
        addcrop=RootView.findViewById(R.id.addCropbtn);
        cropImg=RootView.findViewById(R.id.productIcon);
//      storagePermissions=new String[](Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // cropDbRef= FirebaseDatabase.getInstance().getReference().child("Products");
//        ActivityResultLauncher<Intent>activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if(result.getResultCode()== Activity.RESULT_OK)
//                        {
//                            Intent data=result.getData();
//                            uri=data.getData();
//                            cropImg.setImageURI(uri);
//                        }else {
//                            Toast.makeText(getActivity(),"NO Image Added",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker=new Intent(Intent.ACTION_PICK);
                photoPicker.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(photoPicker,100);
            }
        });
        addcrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    insertCropData();
            }
        });
            return RootView;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==100)
        {
            if(requestCode==100)
            {
                cropImg.setImageURI(data.getData());
            }
        }
    }

    private void insertCropData(){
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Products");
//        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("Crop Images").child(uri.getLastPathSegment());
//        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Task<Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
//                while(!uriTask.isComplete());
//                Uri urlImage=uriTask.getResult();
//                imageUrl=urlImage.toString();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getActivity(),"Failed to Upload Image",Toast.LENGTH_SHORT).show();
//            }
//        });
        String n,d,c,q,p,i;
        n=name.getText().toString();
        d=desc.getText().toString();
        c=categories.getText().toString();
        q=qty.getText().toString();
        p=price.getText().toString();
        i=imageUrl;
        String dz="";
        int a=1;
        if(TextUtils.isEmpty(n) || TextUtils.isEmpty(d) || TextUtils.isEmpty(c)|| TextUtils.isEmpty(q)|| TextUtils.isEmpty(p))
        {
            Toast.makeText(getActivity(),"Please Enter Empty Parameters!",Toast.LENGTH_LONG).show();
        }
        else
        {
//            Crops crops2 = new Crops(n,d,c,a,p,i,"a","a",dz);
//            //        cropDbRef.push().setValue(crops2);
//            String crop=reference.push().getKey();
//            crop+=n;
//            reference.child(crop).setValue(crops2).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    Toast.makeText(getActivity()," Crop Added!!",Toast.LENGTH_LONG).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(getActivity(),"Failed to Add!!",Toast.LENGTH_LONG).show();
//                }
//            });
        }
    }
    }