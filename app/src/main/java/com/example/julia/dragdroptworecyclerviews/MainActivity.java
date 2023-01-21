package com.example.julia.dragdroptworecyclerviews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Listener {

    @BindView(R.id.rvTop)
    RecyclerView rvTop;
    @BindView(R.id.rvBottom)
    RecyclerView rvBottom;
    @BindView(R.id.tvEmptyListTop)
    TextView tvEmptyListTop;
    @BindView(R.id.tvEmptyListBottom)
    TextView tvEmptyListBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initTopRecyclerView();
       // initBottomRecyclerView();

        tvEmptyListTop.setVisibility(View.GONE);
        tvEmptyListBottom.setVisibility(View.GONE);
    }

    private void initTopRecyclerView() {
        rvTop.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));

        List<Bean> topList = new ArrayList<>();
       Bean bean =new  Bean("t",1);

        topList.add(new Bean("t",1));
        topList.add(new Bean("t",1));
        topList.add(new Bean("a",0));




        topList.add(new Bean("t",0));
        topList.add(new Bean("t",0));

        final ListAdapter topListAdapter = new ListAdapter(topList, this);
        rvTop.setAdapter(topListAdapter);
        ItemTouchHelper mIth  = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN ,
                        ItemTouchHelper.LEFT) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();

                        Bean bean = topListAdapter.getList().get(toPos);
                        Bean beansource = topListAdapter.getList().get(fromPos);
                        if((beansource.getTitle() == 1 && bean.getTitle() == 0 && bean.getText().equals("a"))){
                            beansource.setTitle(0);///// title
                            bean.setTitle(0);///// title
                        }else if( (beansource.getTitle() == 0 && bean.getTitle() == 0) && bean.getText().equals("a")){
                                beansource.setTitle(1);/// title
                            bean.setTitle(0);///// title
                        }
                        Collections.swap(topListAdapter.getList(), fromPos, toPos);
                        topListAdapter.notifyItemMoved(fromPos, toPos);
                        //topListAdapter.notifyItemRangeChanged();
                        return true;// true if moved, false otherwise

                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    }

                    @Override
                    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                        if(topListAdapter.getList().get(viewHolder.getAdapterPosition()).getText().equals("a")){
                            return makeMovementFlags(0,0);
                        }
                        return super.getMovementFlags(recyclerView, viewHolder);
                    }

                    @Override
                    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                        super.clearView(recyclerView, viewHolder);
                        topListAdapter.notifyDataSetChanged();
                    }
                });

        mIth.attachToRecyclerView(rvTop);

        rvTop.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
               // if (e.getPointerCount() > 1) return true;
                return true;
//                int action = e.getAction();
//                int x = (int)e.getX();
//                int y = (int)e.getY();
//
//                int touchPosition = rv.getChildAdapterPosition(rv.findChildViewUnder(x, y));
//                if(touchPosition == 2){
//                    return  true;
//                }
//                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        //tvEmptyListTop.setOnDragListener(topListAdapter.getDragInstance());
        //rvTop.setOnDragListener(topListAdapter.getDragInstance());
    }

    private void initBottomRecyclerView() {
        rvBottom.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));

        List<Bean> bottomList = new ArrayList<>();
//        bottomList.add("C");
//        bottomList.add("D");

        ListAdapter bottomListAdapter = new ListAdapter(bottomList, this);
        rvBottom.setAdapter(bottomListAdapter);
        tvEmptyListBottom.setOnDragListener(bottomListAdapter.getDragInstance());
        rvBottom.setOnDragListener(bottomListAdapter.getDragInstance());
    }

    @Override
    public void setEmptyListTop(boolean visibility) {
        tvEmptyListTop.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvTop.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setEmptyListBottom(boolean visibility) {
        tvEmptyListBottom.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvBottom.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }
}
class Bean{
    String text;
    int title;
    Bean(String text,int title){
        this.text = text;
        this.title = title;
    };
    public String getText() {
        return text;
    }

    public int getTitle() {
        return title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(int title) {
        this.title = title;
    }
}
