package tk.seller.trevor.letssellit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends ActionBarActivity {
  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

       String title = getIntent().getStringExtra("Url");
        ImageView imageView = (ImageView) findViewById(R.id.image_preview);


        Picasso.with(DetailsActivity.this).load(title).into(imageView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, Chat_Activity.class);
                startActivity(intent);
            }
        });
    }
}
