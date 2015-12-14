package kr.ac.ajou.media.sap6.activity;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import kr.ac.ajou.media.sap6.R;

/**
 * Created by mingeummaegbug on 15. 12. 14..
 */
public class MyWidgetProvider extends AppWidgetProvider {

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
        }


        @Override
        public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                             int[] appWidgetIds) {
            appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
            for (int i = 0; i < appWidgetIds.length; i++) {
                updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
            }

            super.onUpdate(context, appWidgetManager, appWidgetIds);
        }
    public static void updateAppWidget(Context context,
                                       AppWidgetManager appWidgetManager, int appWidgetId) {
        /**
         * 현재 시간 정보를 가져오기 위한 Calendar
         */
        Calendar mCalendar = Calendar.getInstance();
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.KOREA);


        RemoteViews updateViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_item);

        updateViews.setTextViewText(R.id.mText,
                mFormat.format(mCalendar.getTime()));

        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
    }

        @Override
        public void onEnabled(Context context) {
            super.onEnabled(context);
        }


        @Override
        public void onDisabled(Context context) {
            super.onDisabled(context);
        }

        @Override
        public void onDeleted(Context context, int[] appWidgetIds) {
            super.onDeleted(context, appWidgetIds);
        }
    }