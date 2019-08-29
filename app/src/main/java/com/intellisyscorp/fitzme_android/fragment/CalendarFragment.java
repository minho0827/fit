package com.intellisyscorp.fitzme_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.adapter.CalendarListAdapter;
import com.intellisyscorp.fitzme_android.models.CalendarVO;
import com.intellisyscorp.fitzme_android.models.GarmentVO;
import com.intellisyscorp.fitzme_android.models.OutfitVO;
import com.intellisyscorp.fitzme_android.network.CommonApiCall;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarFragment extends Fragment {
    Context mContext;
    //    private Toolbar mToolbar;
    static final boolean GRID_LAYOUT = false;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    // CodiListAdapter mCodiListAdapter;
    private SwipeRefreshLayout mSwipeContainer;
    private static final String TAG = "CalendarFragment";
    private List<OutfitVO> mNotiList = new ArrayList<>();
    FrameLayout mLayoutLoggedOut;
    LinearLayout mLayoutLoggedIn;
    TextView mNotLoggedInText;
    ImageView mAlarmImg;
    Button btnLogin;
    MaterialCalendarView calendarView;
    private AppCompatTextView mTvDate;
    private CardView mCardView;

//    Toolbar toolbar;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        setHasOptionsMenu(true);
        mRecyclerView = v.findViewById(R.id.recycler_view);

//        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("알림");
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = getActivity();

        mLayoutManager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(mLayoutManager);

        calendarView = ((FragmentActivity) mContext).findViewById(R.id.calendar_view);

        CommonApiCall.getUserCalendar(mContext, TAG, view, this);


//        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//            }
//        });
//        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

//        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("알림");
//        mToolbar.setTitleTextColor(Color.BLACK);
    }

    public void setCalendarView(View v, List<CalendarVO> body) {
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month = date.getMonth();
                int day = date.getDay();
                List<CalendarVO> filteredBody = body.stream().filter(data -> {
                    String[] splitedDate = data.getDate().split("-");
                    int _year = Integer.parseInt(splitedDate[0]);
                    int _month = Integer.parseInt(splitedDate[1]);
                    int _day = Integer.parseInt(splitedDate[2]);

                    return year == _year && month == _month && day == _day;
                }).collect(Collectors.toList());

                CalendarListAdapter adapter = new CalendarListAdapter(v.getContext(), filteredBody);
                mRecyclerView.setAdapter(adapter);
            }
        });

        HashSet<String> used_date = new HashSet<>();
        for (int i = body.size() - 1; i >= 0; --i) {
            CalendarVO date = body.get(i);

            if (used_date.contains(date.getDate())) {
                continue;
            }
            used_date.add(date.getDate());

            String[] tokens = date.getDate().split("-");
            int year = Integer.parseInt(tokens[0]);
            int month = Integer.parseInt(tokens[1]);
            int day = Integer.parseInt(tokens[2]);

            CalendarDay today = CalendarDay.from(year, month, day);
            if (date.getObj().getGarment() != null) {
                calendarView.addDecorator(new GarmentDecorator(today, date.getObj().getGarment()));
            } else if (date.getObj().getOutfit() != null) {
                calendarView.addDecorator(new OutfitDecorator(today, date.getObj().getOutfit()));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initUI();
    }

    public void initUI() {

    }
}

class GarmentDecorator implements DayViewDecorator {
    private CalendarDay date;
    private GarmentVO garment;

    public GarmentDecorator(CalendarDay date, GarmentVO garment) {
        this.date = date;
        this.garment = garment;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setImageLayout("garment");
        view.addImage(garment.getImage());
    }
}

class OutfitDecorator implements DayViewDecorator {
    private CalendarDay date;
    private OutfitVO outfit;

    public OutfitDecorator(CalendarDay date, OutfitVO outfit) {
        this.date = date;
        this.outfit = outfit;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        if (outfit.getDress() != null) {
            view.setImageLayout("outfit_dress");
            if (outfit.getOuter() != null)
                view.addImage(outfit.getOuter().getImage());
            else
                view.addImage(null);

            view.addImage(outfit.getDress().getImage());

            if (outfit.getShoes() != null)
                view.addImage(outfit.getShoes().getImage());
            else
                view.addImage(null);
        } else {
            view.setImageLayout("outfit");
            if (outfit.getOuter() != null)
                view.addImage(outfit.getOuter().getImage());
            else
                view.addImage(null);

            if (outfit.getTop() != null)
                view.addImage(outfit.getTop().getImage());
            else
                view.addImage(null);

            if (outfit.getShoes() != null)
                view.addImage(outfit.getShoes().getImage());
            else
                view.addImage(null);

            if (outfit.getBottom() != null)
                view.addImage(outfit.getBottom().getImage());
            else
                view.addImage(null);
        }
    }
}
