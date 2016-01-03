package com.example.add.fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.add.R;
import com.example.add.adapter.GalleryAdapter;
import com.example.add.adapter.PopAdapter;
import com.example.add.bean.Bean;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;


/**
 * Created by myself on 15/8/9.
 */
public class OtherFragment extends Fragment {

    private View v;
    private ImageView iv;
    private Button down,btn_pop;
    private BitmapUtils bitmapUtils;
    private ListView list;
    private PopupWindow pop;
    private Gallery gal;
    private Context context;
    private ArrayList<Bean> arr;
    PopAdapter popAdapter;
    GalleryAdapter adapter;
    private String url = "http://img.my.csdn.net/uploads/201308/31/1377949578_8744.jpg";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bitmapUtils = new BitmapUtils(getActivity());
        bitmapUtils.configDiskCachePath(Environment.getExternalStorageDirectory()+"/Add/");
        bitmapUtils.configLoadingImage(R.mipmap.btn_down);
        bitmapUtils.configLoadfailImage(R.mipmap.btn_online);
        context = getActivity();

        arr = new ArrayList<Bean>();
        for(int i=0;i<6;i++){
            Bean bean = new Bean("wode"+i,true);
            arr.add(bean);
        }
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.otherfragment,null);
        iv = (ImageView) v.findViewById(R.id.iv);
        down = (Button) v.findViewById(R.id.btn_down);
        btn_pop = (Button) v.findViewById(R.id.btn_pop);
        gal = (Gallery) v.findViewById(R.id.gallery);
        initPop(inflater);
        initGal(gal);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmapUtils.display(iv, url);
            }
        });

        return v;
    }

    private void initGal(Gallery gal) {
//        ArrayList<Bean> arr = new ArrayList<Bean>();
//        for(int i=0;i<5;i++){
//            Bean bean = new Bean("nide"+i,true);
//            arr.add(bean);
//        }
         adapter = new GalleryAdapter(arr,getActivity());
        gal.setAdapter(adapter);
    }

    private void initPop(final LayoutInflater inflater) {

        final View v = inflater.inflate(R.layout.pop_layout, null);
        list = (ListView) v.findViewById(R.id.list_pop);
        final Button btn_sure = (Button) v.findViewById(R.id.btn_sure);
        btn_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //View v = LayoutInflater.from(context).inflate(R.layout.pop_layout,null);

               //list.addFooterView(inflater.inflate(R.layout.foot, null));

                btn_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pop.dismiss();
                    }
                });

                popAdapter = new PopAdapter(getActivity(),arr);
                list.setAdapter(popAdapter);
                pop = new PopupWindow(v, WindowManager.LayoutParams.MATCH_PARENT, 400);
                pop.setFocusable(true);
                pop.setOutsideTouchable(true);
                pop.setBackgroundDrawable(new BitmapDrawable());
                pop.showAsDropDown(btn_pop);
                pop.setOnDismissListener(new MyDismiss());
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(arr.get(i).isChecked()==true){
                            arr.get(i).setChecked(false);
                        }else{
                            arr.get(i).setChecked(true);
                        }

                        popAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

   public class MyDismiss implements PopupWindow.OnDismissListener {

       @Override
       public void onDismiss() {
        ArrayList<Bean> list = new ArrayList<Bean>();
           for(int i=0;i<arr.size();i++){
               if(arr.get(i).isChecked()){
                   list.add(arr.get(i));
               }
           }
         adapter.setData(list);
         adapter.notifyDataSetChanged();
       }
   }
}
