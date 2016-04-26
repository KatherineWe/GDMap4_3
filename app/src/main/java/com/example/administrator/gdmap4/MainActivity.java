package com.example.administrator.gdmap4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationListener;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.maps2d.model.Text;
import com.amap.api.maps2d.model.TextOptions;
import com.amap.api.services.core.PoiItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements LocationSource,
        AMapLocationListener{

    private MapView mapView;
    private AMap aMap;

    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private LocationListener locationListener;

    private Button locate_btn;

    private MarkerOptions option=new MarkerOptions();

    //private MarkerOptions markerOption;

    //private Marker marker_c12=new Marker(markerOption);
    private LatLng latLng_c12=new LatLng(113.400453,23.051629);
    private Bitmap bitmap;
    private BitmapDescriptor bitmapDescriptor;

    private List<LatLng> latLngList_5points;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.gdmapview);
        locate_btn = (Button) findViewById(R.id.btn_locate);
        Button btn = (Button) findViewById(R.id.btn_locate);
        btn .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                init();
            }
        });
        mapView.onCreate(savedInstanceState);

        init();
        handler.post(task);//立即调用

//        option.position(new LatLng(113.400453,23.051629));
//        option.icon(BitmapDescriptorFactory.defaultMarker());
//        Marker marker_C12=aMap.addMarker(option);
//        marker_C12.showInfoWindow();

//        Marker marker = aMap.addMarker(new MarkerOptions()
//                .position(new LatLng(113.400453,23.051629))
//                .title("C12")
//                .icon(BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
//                .draggable(true));
//        marker.showInfoWindow();

        //latLngList_5points.set(0, new LatLng(113.400453, 23.051629));//c12
        //latLngList_5points.set(1, new LatLng(113.403294, 23.051701));//二饭

    }


    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();

            //setUpMarkers(aMap,latLngList_5points);
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource((LocationSource) this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        //aMap.setMyLocationType()
        aMap.getUiSettings().setScaleControlsEnabled(true);//显示比例尺
        aMap.moveCamera(CameraUpdateFactory.zoomTo(aMap.getMaxZoomLevel()));//自动放大到当前最大比例尺

        addMarkersToMap();

    }

    private void addMarkersToMap(){
//        TextOptions textOptions = new TextOptions()
//                .position(new LatLng(113.400453,23.051629))
//                .text("Text")
//                .fontColor(Color.BLACK)
//                .backgroundColor(Color.BLUE)
//                .fontSize(30)
//                .rotate(20)
//                .align(Text.ALIGN_CENTER_HORIZONTAL, Text.ALIGN_CENTER_VERTICAL)
//                .zIndex(1.f)
//                .typeface(Typeface.DEFAULT_BOLD);
//        aMap.addText(textOptions);

        Marker marker = aMap.addMarker(
                new MarkerOptions()
                        .title("C5")

                        .icon(
                                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                        )
                        .draggable(true)
        );
        marker.setRotateAngle(0);
        marker.setPosition(new LatLng(113.401167,23.048243));
        marker.showInfoWindow();

    }

    //设置定时循环执行任务
    private Handler handler = new Handler();

    private Runnable task =new Runnable() {
        public void run() {
            // TODOAuto-generated method stub
            handler.postDelayed(this,2*1000);//设置延迟时间，此处是2秒
            aMap.setMyLocationEnabled(true);
        }
    };


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        init();
        handler.post(task);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 定位成功后回调函数
     */
    //@Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    /**
     * 激活定位
     */
    //@Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener((AMapLocationListener) this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    //@Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

}

