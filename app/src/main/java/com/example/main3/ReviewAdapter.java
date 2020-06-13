package com.example.main3;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<MemberDTO> memberList;
    String Review_user;
    Context mContext;


    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
          TextView tv_reviewtitle;
          TextView tv_reviewuser;
          TextView tv_reviewtime ;
          TextView tv_reviewcontents ;
          RatingBar tv_reviewscore;
          Button btn_delete;


        public ReviewViewHolder(View view) {
                super(view);
                tv_reviewtitle =(TextView)view.findViewById(R.id.tv_reviewtitle);
                tv_reviewuser=(TextView)view.findViewById(R.id.tv_reviewuser);
                tv_reviewtime =(TextView)view.findViewById(R.id.tv_reviewtime);
                tv_reviewcontents =(TextView)view.findViewById(R.id.tv_reviewcontents);
                tv_reviewscore =(RatingBar) view.findViewById(R.id.tv_score);
                btn_delete = (Button) view.findViewById(R.id.btn_delete);

        }
    }


    public ReviewAdapter(ArrayList<MemberDTO> list , String Review_user , Context mContext) {
        this.memberList = list;
        this.Review_user = Review_user;
        this.mContext = mContext;
    }



    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {


        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reviews, viewGroup, false);

        ReviewViewHolder viewHolder = new ReviewViewHolder(view);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull final ReviewViewHolder viewholder, final int position) {

        viewholder.tv_reviewtitle.setText("제목  : " +memberList.get(position).getReview_title());
        viewholder.tv_reviewuser.setText("글작성자 : "+ memberList.get(position).getReview_user());
        viewholder.tv_reviewtime.setText("작성시간: " + memberList.get(position).getReview_time());
        viewholder.tv_reviewscore.setRating(memberList.get(position).getReview_score());
        viewholder.tv_reviewcontents.setText("내용 : " + memberList.get(position).getReview_contents());

        if (memberList.get(position).getReview_user().equals(Review_user)){
            viewholder.btn_delete.setVisibility(View.VISIBLE);
        }
        else{
            viewholder.btn_delete.setVisibility(View.GONE);
        }


       viewholder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("삭제");
                    builder.setMessage("해당 항목을 삭제하시겠습니까?");
                    builder.setPositiveButton("아니오",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.setNegativeButton("예",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Response.Listener<String> responseListener = new Response.Listener<String>(){

                                        @Override
                                        public void onResponse(String response) {
                                            String result = null;
                                            try{
                                                JSONObject jsonResponse = new JSONObject(response);
                                                boolean success = jsonResponse.getBoolean("success");
                                                if(success) {

                                                    Toast.makeText(mContext,"리뷰가 정상적으로 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                                                    memberList.remove(position);
                                                    notifyItemRemoved(position);
                                                    notifyItemRangeChanged(position, memberList.size());
                                                }
                                                else{
                                                    Toast.makeText(mContext,"리뷰 삭제가 실패되었습니다.",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            catch(Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    };//Response.Listener 완료

                                    //Volley 라이브러리를 이용해서 실제 서버와 통신을 구현하는 부분
                                    ReviewdeRequest reviewderequest = new ReviewdeRequest(memberList.get(position).getReview_title(),Review_user,
                                            memberList.get(position).getReview_time(), responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(mContext);
                                    queue.add(reviewderequest);

                                }
                            });
                    builder.show();

                }
            });
    }


    @Override
    public int getItemCount() {
        return memberList.size();
    }





}
