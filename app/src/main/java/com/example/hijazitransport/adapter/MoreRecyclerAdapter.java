package com.example.hijazitransport.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hijazitransport.R;
import com.example.hijazitransport.activity.Login;
import com.example.hijazitransport.model.More;
import com.example.hijazitransport.model.MoreEnum;
import com.example.hijazitransport.util.UserLoginFlag;

import java.util.List;


public class MoreRecyclerAdapter extends RecyclerView.Adapter<MoreRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<More> moreModelList;


    public MoreRecyclerAdapter(Context context, List<More> moreModdleList) {
        this.context = context;
        this.moreModelList = moreModdleList;
    }

    @NonNull
    @Override
    public MoreRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.more_item_row, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoreRecyclerAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.title.setTextColor(context.getResources().getColor(R.color.black));

        viewHolder.title.setText(moreModelList.get(i).getLabel());
        viewHolder.icon.setImageResource(moreModelList.get(i).getIcon());

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (moreModelList.get(i).getMoreModelEnum().equals(MoreEnum.Activity)) {
                    intent = new Intent(context, moreModelList.get(i).getRoot().getClass());
                    context.startActivity(intent);
                } else if (moreModelList.get(i).getMoreModelEnum().equals(MoreEnum.LogOut)) {
                    confrimLogout();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return moreModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        LinearLayout linearLayout;
        ImageView icon;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_of_more_text_view);
            linearLayout = itemView.findViewById(R.id.more_linear_layout);
            icon = itemView.findViewById(R.id.icon_of_more);
        }

    }


    private void confrimLogout() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(context.getResources().getString(R.string.are_you_sure));
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                context.getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserLoginFlag userLoginFlag = new UserLoginFlag(context);
                        userLoginFlag.setFlag(false);

                        Intent intent = new Intent(context, Login.class);
                        context.startActivity(intent);
                    }
                });

        builder1.setNegativeButton(
                context.getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
