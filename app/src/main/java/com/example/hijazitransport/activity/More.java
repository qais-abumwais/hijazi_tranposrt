package com.example.hijazitransport.activity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hijazitransport.R;
import com.example.hijazitransport.adapter.MoreRecyclerAdapter;
import com.example.hijazitransport.util.MyDividerItemDecoration;

public class More extends Base {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        //binding view
        recyclerView = findViewById(R.id.recycler_more);

        //create more screen as list of screens
        createListOfMore();
    }

    @Override
    protected void initScreen(Toolbar toolbar) {
        toolbar.setTitle(R.string.more);
        setSupportActionBar(toolbar);
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.more;
    }

    private void createListOfMore() {
        MoreRecyclerAdapter moreRecyclerAdapter = new MoreRecyclerAdapter(this, com.example.hijazitransport.model.More.getMoreMenu());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(More.this);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL, 0));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(moreRecyclerAdapter);
    }
}
