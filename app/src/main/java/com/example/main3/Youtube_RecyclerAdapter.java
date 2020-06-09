package com.example.main3;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Youtube_RecyclerAdapter extends RecyclerView.Adapter<Youtube_RecyclerAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Youtube_Data> youtube_listData = new ArrayList<>();
    private Context context;

    public Youtube_RecyclerAdapter(ArrayList<Youtube_Data> list) {
        this.youtube_listData = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(youtube_listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return youtube_listData.size();
    }

    void addItem(Youtube_Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        youtube_listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView textView1;
        private TextView textView2;
        private ImageView imageView;
        private Youtube_Data data;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.youtube_textView1);
            textView2 = itemView.findViewById(R.id.youtube_textView2);
            imageView = itemView.findViewById(R.id.youtube_imageView);
        }

        void onBind(Youtube_Data data) {
            this.data = data;

            textView1.setText(data.getYoutubeTitle());
            textView2.setText(data.getYoutubeContent());
            //imageView.setImageResource(data.getYoutubeResId());
            Glide.with(context).load(data.getYoutubeResId()).into(imageView);

            itemView.setOnClickListener(this);
            textView1.setOnClickListener(this);
            textView2.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

//
            Intent intent = new Intent(context.getApplicationContext(), Youtube_WebviewActivity.class);
            intent.putExtra("youtube_webview", data.getYoutubeContent());
            context.startActivity(intent);
        }


    }



}
