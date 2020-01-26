package com.example.hijazitransport.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hijazitransport.R;
import com.example.hijazitransport.adapter.MoreRecyclerAdapter;
import com.example.hijazitransport.model.MoreModel;
import com.example.hijazitransport.util.MyDividerItemDecoration;


import java.util.List;

public class More extends Base {

    private RecyclerView recyclerView;
    private MoreRecyclerAdapter moreRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        recyclerView=findViewById(R.id.recycler_more);
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

    private void createListOfMore(){
        moreRecyclerAdapter = new MoreRecyclerAdapter(this, MoreModel.getMoreMenu());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(More.this);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL, 0));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(moreRecyclerAdapter);
    }
}
