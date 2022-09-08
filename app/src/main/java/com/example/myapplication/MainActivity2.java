package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Bean.CourseDao;
import com.example.myapplication.Bean.CourseListBean;
import com.example.myapplication.adapter.CourseAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity2 extends AppCompatActivity {
    private ListView listView;
    private List<CourseListBean> courseListBeanList;
    private ImageView imageView;
    private TextView textView;
    private TextView textView1;
    private CourseDao courseDao;
    private Spinner spinner1;
    private Spinner spinner2;
    private String[] array1;
    private String[] array2;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private int rownum;
    private int columnnum;
    private PieChart pieChart1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView = (TextView) findViewById(R.id.infoName);
        imageView = (ImageView) findViewById(R.id.infoImg);



        array1 = getResources().getStringArray(R.array.day_value_array);
        array2 = getResources().getStringArray(R.array.time_value_array);



        Intent intent = getIntent();
        String infoName = intent.getStringExtra("userId");
        String infoimg = intent.getStringExtra("imgsrc");

        textView.setText(infoName);
        imageView.setImageResource(Integer.valueOf(infoimg));

        courseDao = new CourseDao(this);
        listView = findViewById(R.id.lv);
        courseInit();


        spinner1 = (Spinner) findViewById(R.id.sp1);
        spinner2 = (Spinner) findViewById(R.id.sp2);
        textView1 = (TextView) findViewById(R.id.editcoursename);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity2.this,array1[i],Toast.LENGTH_SHORT).show();
                columnnum = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity2.this,array2[i],Toast.LENGTH_SHORT).show();
                rownum = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn1 = (Button) findViewById(R.id.addbtn);
        btn2 = (Button) findViewById(R.id.deletebtn);
        btn3 = (Button) findViewById(R.id.updatebtn);
        btn4 = (Button) findViewById(R.id.querybtn);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coursenametext = textView1.getText().toString();
                if (coursenametext.equals("")){
                    Toast.makeText(MainActivity2.this,"课程名不能为空",Toast.LENGTH_SHORT).show();
                } else if (coursenametext.equals("ERROR")){
                    Toast.makeText(MainActivity2.this,"课程名不合法",Toast.LENGTH_SHORT).show();
                } else if (!courseDao.queryCourse(rownum,columnnum).equals("ERROR")){
                    Toast.makeText(MainActivity2.this,"该时间段已有课程，请使用其他修改按钮",Toast.LENGTH_SHORT).show();
                }  else {
                    courseDao.insertCourse(rownum,columnnum,coursenametext);
                    updateListView();
                    updateBingTu();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courseDao.queryCourse(rownum,columnnum).equals("ERROR")){
                    Toast.makeText(MainActivity2.this,"该时间段暂无课程",Toast.LENGTH_SHORT).show();
                } else {
                    courseDao.deleteCourse(rownum,columnnum);
                    updateListView();
                    updateBingTu();
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coursenametext = textView1.getText().toString();
                if (coursenametext.equals("")){
                    Toast.makeText(MainActivity2.this,"课程名不能为空",Toast.LENGTH_SHORT).show();
                } else if (coursenametext.equals("ERROR")){
                    Toast.makeText(MainActivity2.this,"课程名不合法",Toast.LENGTH_SHORT).show();
                }else if (courseDao.queryCourse(rownum,columnnum).equals("ERROR")){
                    Toast.makeText(MainActivity2.this,"该时间段暂无课程",Toast.LENGTH_SHORT).show();
                } else {
                    courseDao.updateCourse(rownum,columnnum,coursenametext);
                    updateListView();
                    updateBingTu();
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String queryResult = courseDao.queryCourse(rownum,columnnum);
                if (queryResult.equals("ERROR")){
                    Toast.makeText(MainActivity2.this,"该时间段暂无课程",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity2.this,"该时间段的课程为:"+queryResult,Toast.LENGTH_SHORT).show();
                }
            }
        });

        pieChart1 = findViewById(R.id.consume_pie1_chart);
        bingTu1();

    }

    private void courseInit() {
        //数据从后台请求；
        String[][] initialData = {{"语文", "数学", "英语", "自习", "自习"}, {"语文", "数学", "英语", "自习", "自习"}, {"语文", "数学", "英语", "自习", "自习"}, {"语文", "数学", "英语", "自习", "自习"}, {"语文", "数学", "英语", "自习", "自习"}, {"语文", "数学", "英语", "自习", "自习"}};
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (courseDao.queryCourse(i,j).equals("ERROR")){
                    courseDao.insertCourse(i,j,initialData[i][j]);
                } else {
                    courseDao.updateCourse(i,j,initialData[i][j]);
                }
            }
        }
        updateListView();
    }
    private void updateListView(){
        String[] timeQuantuamList = {"第一节","第二节","第三节","第四节","第五节","第六节"};
        courseListBeanList  = new ArrayList<>();
        courseListBeanList.add(new CourseListBean("上午", " ", " ", " ", " ", " "));
        for (int i=0;i<6;i++){
            String monday = courseDao.queryCourse(i,0);
            String tuesday = courseDao.queryCourse(i,1);
            String wednesday = courseDao.queryCourse(i,2);
            String thursday = courseDao.queryCourse(i,3);
            String friday = courseDao.queryCourse(i,4);
            monday = monday.equals("ERROR")?"":monday;
            tuesday = tuesday.equals("ERROR")?"":tuesday;
            wednesday = wednesday.equals("ERROR")?"":wednesday;
            thursday = thursday.equals("ERROR")?"":thursday;
            friday = friday.equals("ERROR")?"":friday;
            courseListBeanList.add(new CourseListBean(timeQuantuamList[i], monday, tuesday,wednesday, thursday, friday));
            if (i==3){
                courseListBeanList.add(new CourseListBean("下午", " ", " ", " ", " ", " "));
            }

        }
        listView.setAdapter(new CourseAdapter(MainActivity2.this, courseListBeanList));
    }

    private void bingTu1() {
        pieChart1.setUsePercentValues(true); //设置为显示百分比
        pieChart1.setDescription("各科目占比饼状统计图");//设置描述
        pieChart1.setDescriptionTextSize(20f);
        // pieChart1.setExtraOffsets(5, 5, 5, 5);//设置饼状图距离上下左右的偏移量
        pieChart1.setDrawCenterText(true); //设置可以绘制中间的文字
        pieChart1.setCenterTextColor(Color.BLACK); //中间的文本颜色
        pieChart1.setCenterTextSize(16);  //设置中间文本文字的大小
        pieChart1.setDrawHoleEnabled(true); //绘制中间的圆形
        pieChart1.setHoleColor(Color.WHITE);//饼状图中间的圆的绘制颜色
        pieChart1.setHoleRadius(40f);//饼状图中间的圆的半径大小
        pieChart1.setTransparentCircleColor(Color.BLACK);//设置圆环的颜色
        pieChart1.setTransparentCircleAlpha(100);//设置圆环的透明度[0,255]
        pieChart1.setTransparentCircleRadius(40f);//设置圆环的半径值
        pieChart1.setRotationEnabled(false);//设置饼状图是否可以旋转(默认为true)
        pieChart1.setRotationAngle(10);//设置饼状图旋转的角度

        Legend l = pieChart1.getLegend(); //设置比例图
        l.setMaxSizePercent(100);
        l.setTextSize(14);
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);//设置每个tab的显示位置（这个位置是指下图右边小方框部分的位置 ）
        l.setXEntrySpace(10f);
        l.setYEntrySpace(5f);//设置tab之间Y轴方向上的空白间距值
        l.setYOffset(0f);

        //饼状图上字体的设置
        pieChart1.setDrawEntryLabels(false);//设置是否绘制Label
        // pieChart1.setEntryLabelColor(Color.BLACK);//设置绘制Label的颜色
        pieChart1.setEntryLabelTextSize(20f);//设置绘制Label的字体大小

        // pieChart1.animateY(100, Easing.EasingOption.EaseInQuad);//设置Y轴上的绘制动画
        updateBingTu();

    }
    private void updateBingTu(){
        Map<String,Integer>  map = new HashMap<>();
        for (int i=0;i<6;i++){
            for (int j=0;j<5;j++){
                String coursename =courseDao.queryCourse(i,j);
                System.out.println(coursename);
                if (!coursename.equals("ERROR")){
                    if (map.containsKey(coursename)){
                        System.out.println(coursename);
                        map.put(coursename,map.get(coursename)+1);
                    } else {
                        map.put(coursename,1);
                    }
                }
            }
        }
        Set<String> set = map.keySet();
        System.out.println(132132321);
        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
        for (String key :set){
            pieEntries.add(new PieEntry(map.get(key), key));
            System.out.println(key);
            System.out.println(map.get(key));
        }
        //设置数据百分比和描述
//        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
//        pieEntries.add(new PieEntry(15, "游戏"));
//        pieEntries.add(new PieEntry(20, "做家务"));



        String centerText = "各科目占比";
        pieChart1.setCenterText(centerText);//设置圆环中间的文字
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        ArrayList<Integer> colors = new ArrayList<>();

        // 饼图颜色
        colors.add(Color.rgb(0, 255, 0));
        colors.add(Color.rgb(255, 255, 0));
        colors.add(Color.rgb(255, 0, 0));
        colors.add(Color.rgb(255, 0, 255));
        colors.add(Color.rgb(244, 164, 96));
        colors.add(Color.rgb(30, 144, 255));
        colors.add(Color.rgb(123, 123, 123));
        pieDataSet.setColors(colors);

        pieDataSet.setSliceSpace(0f);//设置选中的Tab离两边的距离
        pieDataSet.setSelectionShift(5f);//设置选中的tab的多出来的
        PieData pieData = new PieData();
        pieData.setDataSet(pieDataSet);

        //各个饼状图所占比例数字的设置
        pieData.setValueFormatter(new PercentFormatter());//设置%
        pieData.setValueTextSize(18f);
        pieData.setValueTextColor(Color.BLUE);

        pieChart1.setData(pieData);
        pieChart1.highlightValues(null);
        pieChart1.invalidate();
        updateListView();
    }
}

