package com.pgw.parallaxrecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private RecyclerView rv;
	private List<String> data=new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rv=findViewById(R.id.rv);
		generData();
		final LinearLayoutManager layoutManager=new LinearLayoutManager(this);
		rv.setLayoutManager(layoutManager);

		final ParallaxRvAdapter adapter=new ParallaxRvAdapter(data);
		rv.setAdapter(adapter);
		rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				int fv=layoutManager.findFirstVisibleItemPosition();
				int lv=layoutManager.findLastVisibleItemPosition();
				for (int i = fv; i < lv; i++) {
					if (adapter.getItemViewType(i)==1){
						LinearLayout ll= (LinearLayout) layoutManager.findViewByPosition(i);
						ParallaxView view= (ParallaxView) ll.getChildAt(0);
						if (dy<0){
							view.changeScroll(0,20);
						}else {
							view.changeScroll(0,-20);
						}

					}
				}
			}
		});
	}

	private void generData(){
		for (int i = 0; i < 20; i++) {
			data.add("数据:"+i);
		}
	}
}
