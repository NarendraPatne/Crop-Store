package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddCropActivity extends AppCompatActivity {

    ImageView cropImg;
    EditText name, desc, qty, price;
    Spinner categories,u;
    Button addcrop;
    ImageView back;
    Uri imguri;
    FirebaseDatabase database;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);
        name = findViewById(R.id.title);
        desc = findViewById(R.id.description);
        categories = findViewById(R.id.category);
        qty = findViewById(R.id.quantity);
        price = findViewById(R.id.price);
        back=findViewById(R.id.back);
        //addimg = findViewById(R.id.addimg);
        addcrop = findViewById(R.id.addCropbtn);
        cropImg = findViewById(R.id.productIcon);
        u=findViewById(R.id.unit);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference().child("Crops");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AddCropActivity.this,ActivityMain.class);
                startActivity(i);
            }
        });
        cropImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(photoPicker, 100);
            }
        });
        addcrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imguri != null) {
                    insertCropData();
                }
                else {
                    Toast.makeText(AddCropActivity.this, "Select Image!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            cropImg.setImageURI(data.getData());
            imguri = data.getData();
        }
    }

    private void insertCropData() {
        String n, d, c, q, p,un;
        n = name.getText().toString();
        d = desc.getText().toString();
        c = categories.getSelectedItem().toString();
        q = qty.getText().toString();
        p = price.getText().toString();
        un=u.getSelectedItem().toString();
        String s= FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Products");
        if (TextUtils.isEmpty(n) || TextUtils.isEmpty(d) || TextUtils.isEmpty(c) || TextUtils.isEmpty(q) || TextUtils.isEmpty(p))
        {
            Toast.makeText(AddCropActivity.this, "Please Enter Empty Parameters!", Toast.LENGTH_LONG).show();
        }
        else
        {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imguri));
            fileReference.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String crop = reference.push().getKey();
                            crop += n;
                            Crops crops2 = new Crops(n, d, c, q, p,uri.toString(),s,crop,un);
                            //        cropDbRef.push().setValue(crops2);
                            DatabaseReference ref=FirebaseDatabase.getInstance().getReference("users/"+s+"/");
                            ref.child("My Crops").child(crops2.getCid()).setValue(crops2);
                            reference.child(crop).setValue(crops2);
                            Toast.makeText(AddCropActivity.this, " Crop Added!!", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(getIntent());
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(AddCropActivity.this, " Crop Added!!", Toast.LENGTH_LONG).show();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(AddCropActivity.this, "Failed to Add!!", Toast.LENGTH_LONG).show();
//                                }
//                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddCropActivity.this, "Failed to Add!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public String getFileExtension (Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}