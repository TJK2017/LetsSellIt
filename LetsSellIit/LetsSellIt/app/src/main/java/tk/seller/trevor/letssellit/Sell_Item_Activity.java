package tk.seller.trevor.letssellit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Sell_Item_Activity extends AppCompatActivity implements View.OnClickListener {

    public  Uri photoURI;
    private ImageView mImageView;
    private ProgressDialog mProgress;
    private StorageReference mStorageReference;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mAuth;
    private EditText sellItem;
    private EditText description;
    private  EditText cost;
    private Button save;
    public static final int REQUEST_TAKE_PHOTO = 1;
    private static int RESULT_LOAD_IMAGE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        setContentView(R.layout.activity_sell__item_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        selectImage();
        mProgress = new ProgressDialog(this);

        mImageView = (ImageView) findViewById(R.id.image);
        sellItem = (EditText) findViewById(R.id.sellItem);
        description = (EditText) findViewById(R.id.Descrip);
        cost = (EditText)findViewById(R.id.cost);
        save = (Button)findViewById(R.id.btn_save_sell);

        save.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_save_sell:
                Intent intent = new Intent(Sell_Item_Activity.this, MainActivity.class);
                startActivity(intent);
                break;


        }
    }


private void selectImage() {
    final CharSequence[] items = { "Take Photo", "Choose from Library",
            "Cancel" };
    AlertDialog.Builder builder = new AlertDialog.Builder(Sell_Item_Activity.this);
    builder.setTitle("Add Photo!");
    builder.setItems(items, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int item) {


            if (items[item].equals("Take Photo")) {
                dispatchTakePictureIntent();
            } else if (items[item].equals("Choose from Library")) {
                galleryIntent();
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        }
    });
    builder.show();
}

    private void galleryIntent()
    {

        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*");
        startActivityForResult(pickPhoto, RESULT_LOAD_IMAGE);
    }



    File photoFile = null;
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                 photoURI = FileProvider.getUriForFile(this,
                        "tk.seller.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @SuppressWarnings("VisibleForTests")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            mProgress.setMessage("Upload in progress");
            mProgress.show();


            Uri uri = photoURI;
            StorageReference filepath = mStorageReference.child("Photos").child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgress.dismiss();
                    Uri download = taskSnapshot.getDownloadUrl();
                    Picasso.with(Sell_Item_Activity.this).load(download).into(mImageView);
                    Toast.makeText(Sell_Item_Activity.this, "Upload Done", Toast.LENGTH_LONG).show();



                    Upload upload = new Upload(
                            sellItem.getText().toString().trim(),
                            description.getText().toString().trim(),
                            cost.getText().toString().trim(),
                            taskSnapshot.getDownloadUrl().toString());

                    String uploadId = mDatabase.push().getKey();
                    mDatabase.child(uploadId).setValue(upload);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Sell_Item_Activity.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK ) {

            mProgress.setMessage("Upload in progress");
            mProgress.show();
            Uri uri = data.getData();
            StorageReference filepath = mStorageReference.child("Photos").child(uri.getLastPathSegment());


            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgress.dismiss();
                    Uri download = taskSnapshot.getDownloadUrl();
                    Picasso.with(Sell_Item_Activity.this).load(download).into(mImageView);
                    Toast.makeText(Sell_Item_Activity.this, "Upload Done", Toast.LENGTH_LONG).show();


                    Upload upload = new Upload(
                            sellItem.getText().toString().trim(),
                            description.getText().toString().trim(),
                            cost.getText().toString().trim(),
                            taskSnapshot.getDownloadUrl().toString());
                    String uploadId = mDatabase.push().getKey();
                    mDatabase.child(uploadId).setValue(upload);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Sell_Item_Activity.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
                }
            });
            }

        }




    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}