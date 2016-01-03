package com.example.add.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.add.R;
import com.example.add.fragment.MyFragment;
import com.example.add.fragment.RecyFragment;
import com.example.add.fragment.TabLayoutFragment;
import com.example.add.fragment.NuoMiFragment;
import com.example.add.fragment.ZhiHuFragemnt;
import com.example.add.fragment.ZhiHuListFragment;
import com.example.add.utils.Utils;
import com.example.add.view.CircularImage;
import com.example.add.view.XListView;
import com.lidroid.xutils.BitmapUtils;
import com.wunderlist.slidinglayer.SlidingLayer;


import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SlidingLayer layer;
    private boolean flag = true;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private android.support.v4.app.FragmentManager fm;
    private CircularImage head_image;
    private int position;
    private ArrayList<Fragment> mFragments;
    private NavigationView navigationView;
    private BitmapUtils bitmapUtils;
    private Animation anim;
    private XListView list;
    private Handler handler;
    private ArrayAdapter<String> adapter;
    private ImageView iv;
    private LinearLayout ll;
    private boolean isToolsHide = false;
    private String url = "http://img.my.csdn.net/uploads/201308/31/1377949578_8744.jpg";
    private String mUserImageName = "my.jpg";
    private String mFUserImageName = "you.jpg";
    private File mFImageFile;


    // 照相的tag值
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    // 从相册取相片的tag值
    public static final int GET_PICTURE_FROM_XIANGCE_CODE = 2;
    // 取得裁剪后的照片
    public static final int GET_CUT_PICTURE_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("主页");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(false); //设置返回键可用


        position = 4;
        ZhiHuListFragment zhihuFragment = new ZhiHuListFragment();
        zhihuFragment.setToolBar(toolbar);
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new ZhiHuFragemnt());
        mFragments.add(new NuoMiFragment());
        mFragments.add(new ZhiHuListFragment());
        mFragments.add(new RecyFragment());
        mFragments.add(new TabLayoutFragment(getSupportFragmentManager()));
        getSupportFragmentManager().beginTransaction().replace(R.id.fram_drawer, mFragments.get(2)).commit();
//        layer = (SlidingLayer) findViewById(R.id.slidingLayer1);
//        layer.setStickTo(SlidingLayer.STICK_TO_BOTTOM);
//        layer.setOffsetDistance(0);
//        list = (XListView) findViewById(R.id.xlist);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_about:
                        showFragmen(4);
                        toolbar.setTitle("其他");
                        break;
                    case R.id.navigation_item_blog:
                        showFragmen(1);
                        toolbar.setTitle("百度糯米");
                        break;
                    case R.id.navigation_item_book:
                        showFragmen(0);
                        toolbar.setTitle("知乎");
                        break;
                    case R.id.navigation_item_example:
                        showFragmen(2);
                        toolbar.setTitle("知乎完美版");
                        break;
                    case R.id.navigation_item_recycle:
                        showFragmen(3);
                        toolbar.setTitle("控件");
                        break;
                }
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        head_image = (CircularImage) findViewById(R.id.head_image);
            Bitmap bitmap = BitmapFactory.decodeFile(getDir()+mFUserImageName);
            head_image.setImageBitmap(bitmap);
        head_image.setOnClickListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.abc_action_bar_home_description,
                R.string.abc_action_bar_home_description);
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void showFragmen(int mposition){
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //为fragment切换设置动画
        //ft.setCustomAnimations(R.anim.push_down_in,R.anim.push_down_out);
        if(mposition == position){
            return;
        }
        //ft.replace(R.id.fram_drawer,mFragments.get(mposition)).commit();
        ft.hide(mFragments.get(position));
        if(mFragments.get(mposition).isAdded()){
            ft.show(mFragments.get(mposition));
        }else{
            ft.add(R.id.fram_drawer,mFragments.get(mposition));
        }
        ft.commit();
         position = mposition;
    }

    @Override
    public void onClick(View view) {
        showChangeHeadImageDialog();
    }

    private void showChangeHeadImageDialog() {
        final AlertDialog mAlertDialog = new AlertDialog.Builder(this).create();
        mAlertDialog.show();
        Window window = mAlertDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialogAnimation);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_user_center_change_image, new RelativeLayout(this), false);
        window.setContentView(contentView);

        Button btn_user_center_change_image_photograph = (Button) contentView.findViewById(R.id.btn_user_center_change_image_photograph);
        Button btn_user_center_change_image_location = (Button) contentView.findViewById(R.id.btn_user_center_change_image_location);
        Button btn_user_center_change_image_cancel = (Button) contentView.findViewById(R.id.btn_user_center_change_image_cancel);

        btn_user_center_change_image_photograph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getoSystemCamera();
                mAlertDialog.dismiss();
            }
        });
        btn_user_center_change_image_location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getoSystemPhoto();
                mAlertDialog.dismiss();
            }
        });
        btn_user_center_change_image_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mAlertDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE: // 系统相机
                if (resultCode == Activity.RESULT_OK) {
                    if (intent != null && intent.getData() != null) {
                        Utils.startPhotoZoom(this, intent.getData());
                    } else {
                        Uri uri = Uri.fromFile(new File(Utils.getCachePath(this), mUserImageName));
                        Utils.startPhotoZoom(this, uri);
                    }
                }
                break;
            case GET_PICTURE_FROM_XIANGCE_CODE: // 系统相册回调
                if (resultCode == Activity.RESULT_OK) {
                    Utils.startPhotoZoom(this, intent.getData());
                }
                break;
            case GET_CUT_PICTURE_CODE: // 剪后图片之后回调
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap bm = intent.getParcelableExtra("data");
                    Utils.bmpToFile(bm, getDir(), mFUserImageName);
                    head_image.setImageBitmap(Utils.getRoundedCornerBitmap(bm));
                }
                break;
            default:
                break;
        }
    }

    private String getDir(){
        String path = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Add/";
            Log.i("bcd",path);
        }
        return path;
    }
    /**
     * 调用系统的照相机
     */
    private void getoSystemCamera() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Utils.getCachePath(this), mUserImageName);
        Uri mCameraUri = Uri.fromFile(file);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraUri);
        startActivityForResult(openCameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    /**
     * 从相册里面获取图片。
     */
    private void getoSystemPhoto() {
        Intent intent_pick = new Intent(Intent.ACTION_PICK, null);
        intent_pick.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Utils.IMAGE_UNSPECIFIED);
        startActivityForResult(intent_pick, GET_PICTURE_FROM_XIANGCE_CODE);
    }

}



//        initListTouch();
//        iv = (ImageView) findViewById(R.id.iv_main);
//        bitmapUtils.display(iv,url);
//        ll = (LinearLayout) findViewById(R.id.ll_tools);
//        ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "你点击了", Toast.LENGTH_SHORT).show();
//            }
//        });
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, RecycleActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//

//        final SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
//        handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message message) {
//
//                switch (message.what){
//                    case 0:
//                        adapter.add("上拉刷新了");
//                        adapter.notifyDataSetChanged();
//                        list.stopRefresh();
//                        Date date = new Date(System.currentTimeMillis());
//                        String dt = format.format(date);
//                        list.setRefreshTime(dt);
//                        break;
//                    case 1:
//
//                        if(i>4){
//                            Log.i("listView",""+i);
//                            View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.xlistview_footer,null);
//                            TextView text = (TextView) v.findViewById(R.id.xlistview_footer_hint_textview);
//                            Toast.makeText(MainActivity.this,"已没有更多数据了",Toast.LENGTH_SHORT).show();
//                            text.setText("已没有更多数据了");
//                            list.setPullLoadEnable(false);
//                            list.stopLoadMore();
//                            v.setEnabled(false);
//                            return false;
//                        }else{
//                            adapter.add("下啦刷新了");
//                            adapter.notifyDataSetChanged();
//                            list.stopLoadMore();
//                        }
//
//                        break;
//                }
//                return false;
//            }
//        });

//        anim = AnimationUtils.loadAnimation(this,R.anim.rota);
//        LinearInterpolator li = new LinearInterpolator();
//        anim.setFillAfter(true);
//        anim.setInterpolator(li);
//        btn = (Button) findViewById(btn_add);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (flag) {
//                    if (layer.isOpened()) {
//                        flag = !flag;
//                        layer.closeLayer(true);
//                        // btn.startAnimation(anim);
//                        return;
//                    }
//                    layer.openLayer(true);
//                    flag = !flag;
//                    // btn.startAnimation(anim);
//
//                } else {
//                    if (layer.isClosed()) {
//                        flag = !flag;
//                        layer.openLayer(true);
//                        //  btn.clearAnimation();;
//                        return;
//                    }
//                    flag = !flag;
//                    layer.closeLayer(true);
//                    // btn.clearAnimation();
//                }
//            }
//        });
//
//        layer.setOnInteractListener(new SlidingLayer.OnInteractListener() {
//            @Override
//            public void onOpen() {
//                btn.startAnimation(anim);
//            }
//
//            @Override
//            public void onShowPreview() {
//
//            }
//
//            @Override
//            public void onClose() {
//                btn.clearAnimation();
//            }
//
//            @Override
//            public void onOpened() {
//
//            }
//
//            @Override
//            public void onPreviewShowed() {
//
//            }
//
//            @Override
//            public void onClosed() {
//
//                Toast.makeText(MainActivity.this, "你点击了layer", Toast.LENGTH_SHORT).show();
//
//            }
//        });


//        adapter = new ArrayAdapter<String>(this,R.layout.item,R.id.text);
//        for (int i=0;i<50;i++){
//            adapter.add(i+"条数据");
//        }
//        list.setAdapter(adapter);
//        list.setXListViewListener(this);
//        list.setPullLoadEnable(true);
//        list.setPullRefreshEnable(true);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(MainActivity.this, TwoActivitty.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//    }

//    private void initListTouch() {
//        list.setOnTouchListener(new View.OnTouchListener() {
//            float downY;
//            float disY;
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        downY = motionEvent.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        disY = motionEvent.getY() - downY;
//                        float juli = ViewConfiguration.get(MainActivity.this).getScaledTouchSlop();
//                        if (Math.abs(disY) > juli) {
//                            boolean isUp = disY < 0;
//                            if (isUp) {
//                                if (!isToolsHide) {
//                                    hideTools();
//                                }
//                            } else {
//                                if (isToolsHide) {
//                                    showTools();
//                                }
//                            }
//                        }
//                        downY = motionEvent.getY();
//                        break;
//                }
//                return false;
//            }
//        });
//
//    }



//
//    switch (event.getAction()) {
//
//        case MotionEvent.ACTION_DOWN:
//            lastY = event.getY();
//            break;
//        case MotionEvent.ACTION_MOVE:
//
//            float disY = event.getY() - lastY;
//
//            //垂直方向滑动
//            if (Math.abs(disY) > viewSlop) {
//                //是否向上滑动
//                isUpSlide = disY < 0;
//
//                //实现底部tools的显示与隐藏
//                if (isUpSlide) {
//                    if (!isToolsHide)
//                        hideTools();
//                } else {
//                    if (isToolsHide)
//                        showTools();
//                }
//            }
//
//            break;
//    }
//
//    return false;

//    @Override
//    public void onRefresh() {
//
//        Message msg = handler.obtainMessage();
//        msg.what = 0;
//
//        handler.sendMessageDelayed(msg,2000);
//    }
//
//    @Override
//    public void onLoadMore() {
//
//        Message msg = handler.obtainMessage();
//        msg.what = 1;
//        i++;
//        handler.sendMessageDelayed(msg,2000);
//    }
//
//
//    /**
//     * 显示工具栏
//     */
//    private void showTools() {
//
//        ObjectAnimator anim = ObjectAnimator.ofFloat(ll, "y", ll.getY(),
//                ll.getY() - ll.getHeight());
//        anim.setDuration(300);
//        anim.start();
//
//        isToolsHide = false;
//    }
//
//    /**
//     * 隐藏工具栏
//     */
//    private void hideTools() {
//
//        ObjectAnimator anim = ObjectAnimator.ofFloat(ll, "y", ll.getY(),
//                ll.getY() + ll.getHeight());
//        anim.setDuration(300);
//        anim.start();
//
//        isToolsHide = true;
//
//    }
//}
