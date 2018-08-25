package com.pgw.parallaxrecycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * <pre>
 *     author : Administrator
 *     e-mail : 89925977@qq.com
 *     time   : 2018/08/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ParallaxRvAdapter extends RecyclerView.Adapter<ParallaxRvAdapter.ViewHolder> {
	private Context mContext;
	private List<String> dataList;
	public ParallaxRvAdapter(List<String> list){
		dataList=list;
	}


	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (mContext==null){
			mContext=parent.getContext();
		}
		ViewHolder holder = null;
		switch (viewType){
			case 0:
				View view1= LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
				holder=new ViewHolder(view1,0);
				break;
			case 1:
				View view2= LayoutInflater.from(mContext).inflate(R.layout.item_image,parent,false);
				holder=new ViewHolder(view2,1);
				break;
		}

		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		switch (getItemViewType(position)){
			case 0:
				holder.textView.setText(dataList.get(position));
				break;
			case 1:
				holder.rectView.setBackgroundResource(R.drawable.car);

				break;
		}
	}

	@Override
	public int getItemCount() {
		return dataList.size();
	}

	@Override
	public int getItemViewType(int position) {
		return position%10==0?1:0;
	}

	class ViewHolder extends RecyclerView.ViewHolder{
		TextView textView;
		ParallaxView rectView;
		public ViewHolder(View itemView , int type) {
			super(itemView);
			if (type==0){
				textView=itemView.findViewById(R.id.tv);

			}else if (type==1){
				rectView=itemView.findViewById(R.id.img_rv);
			}

		}
	}
}
