package tk.seller.trevor.letssellit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerGridAdapter mRecyclerGridAdapter;
    private List<categories_class> mCategoriesclassList;

    String[] listTitle = {"Art", "Books", "Computers",
            "Travel", "HouseHold", "Sports"};

    int[] listContent = {R.drawable.ic_draw,
            R.drawable.ic_reading,
            R.drawable.ic_computer,
            R.drawable.ic_travel,
            R.drawable.ic_health,
            R.drawable.ic_cycling

    };

    int sourceColors[] ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        //getting color array from colors.xml
        sourceColors= getApplicationContext().getResources().getIntArray(R.array.androidcolors);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerSettings);

        mGridLayoutManager = new GridLayoutManager(this,2); //gridlayout with 2 items
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        putTestData();
        mRecyclerGridAdapter = new RecyclerGridAdapter(this, mCategoriesclassList);
        mRecyclerView.setAdapter(mRecyclerGridAdapter);

        mRecyclerGridAdapter.setOnItemClickListener(new RecyclerGridAdapter.onGridItemClickListener() {
            @Override
            public void onItemClick(View v, int postion) {
                categories_class categoriesclass = mCategoriesclassList.get(postion);
                Toast.makeText(Categories.this, categoriesclass.getListName() + " is clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //method to add the sample data
    private void putTestData() {
        mCategoriesclassList = new ArrayList<categories_class>();
        for (int i = 0; i < 6; i++) {
            categories_class categoriesclass = new categories_class();
            categoriesclass.setListImage(listContent[i]);
            categoriesclass.setListName(listTitle[i]);
            categoriesclass.setListColor(sourceColors[i]);
            mCategoriesclassList.add(categoriesclass);
        }
    }

}



