package tk.seller.trevor.letssellit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowImgActivity extends AppCompatActivity {

    //recyclerview object
    private RecyclerView mRecyclerView;

    //mAdapter object
    private RecyclerView.Adapter mAdapter;

    //database reference
    private DatabaseReference mDatabase;

    //progress dialog
    private ProgressDialog mProgressDialog;

    //list to hold all the uploaded images
    private List<User> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_img);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mProgressDialog = new ProgressDialog(this);

       uploads = new ArrayList<>();

        //displaying progress dialog while fetching images
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("downloadurl");
        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                mProgressDialog.dismiss();

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User upload = postSnapshot.getValue(User.class);
                    uploads.add(upload);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressDialog.dismiss();
            }
        });

    }

}
