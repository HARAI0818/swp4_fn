package com.example.main3;


import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ItemViewHolder>  {

    // adapter에 들어갈 list 입니다.
    private ArrayList<NewsData> listData = new ArrayList<>();
    private Context context;
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    public NewsRecyclerAdapter(ArrayList<NewsData> list) {
        this.listData = list;
    }


//

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsitem, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position), position);
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(NewsData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView news_textView1;
        private TextView news_textView2;
        private ImageView news_imageView1;
        private ImageView news_imageView2;
        private NewsData data;
        private int position;

        ItemViewHolder(View itemView) {
            super(itemView);

            news_textView1 = itemView.findViewById(R.id.news_textView1);
            news_textView2 = itemView.findViewById(R.id.news_textView2);
            news_imageView1 = itemView.findViewById(R.id.news_imageView1);
            news_imageView2 = itemView.findViewById(R.id.news_imageView2);
        }

        void onBind(NewsData data, int position) {
            this.data = data;
            this.position = position;


            news_textView1.setText(data.getNewsTitle());
            news_textView2.setText(data.getNewsContent());
            //imageView1.setImageResource(data.getResId());
            Glide.with(context).load(data.getNewsResId()).into(news_imageView1);
            //imageView2.setImageResource(data.getResId());
            Glide.with(context).load(data.getNewsResId()).into(news_imageView2);
            changeVisibility(selectedItems.get(position));

            itemView.setOnClickListener(this);
            news_textView1.setOnClickListener(this);
            news_textView2.setOnClickListener(this);
            news_imageView1.setOnClickListener(this);
        }








        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.news_linearItem:
                case R.id.news_textView1:
                case R.id.news_textView2:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition);
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;
                // 해당 포지션의 변화를 알림
                // 클릭된 position 저장

            }
        }

        /**
         * 클릭된 Item의 상태 변경
         * @param isExpanded Item을 펼칠 것인지 여부
         */
        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            //NewsData newsdata = null;
            int dpValue =  500; //Integer.parseInt(newsdata.getContent());
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);


            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(500);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView의 높이 변경
                    news_imageView2.getLayoutParams().height = value;
                    news_imageView2.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    news_imageView2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

                    //textView2.getLayoutParams().height = value;
                    //textView2.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    news_textView2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }
    }
}






